package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.outcome.Interface.OutcomeInterface;
import com.blibli.distro_pos.Model.outcome.Outcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutcomeService {
    private final OutcomeInterface outcomeInterface;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

    @Autowired
    public OutcomeService(OutcomeInterface outcomeInterface) {
        this.outcomeInterface = outcomeInterface;
    }

    public ModelAndView index() {
        ModelAndView modelAndView = fetch(1);
        return modelAndView;
    }

    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("outcome/form");
        Outcome outcome = new Outcome();
        modelAndView.addObject("outcome", outcome);

        return modelAndView;
    }

    public ModelAndView store(Outcome outcome, Authentication authentication) {
        outcome.setUsername(authentication.getName());
        ModelAndView modelAndView = validateAndExecute(outcome, STORE);
        return modelAndView;
    }

    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView("outcome/form");
        Outcome outcome;
        try {
            outcome = outcomeInterface.getOne(id);
            System.out.println(outcome.getDate());
            modelAndView.addObject("outcome", outcome);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView update(Outcome outcome) {
        ModelAndView modelAndView = validateAndExecute(outcome, UPDATE);
        return modelAndView;
    }

    public ModelAndView delete(String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcomeInterface.softDelete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView active(String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcomeInterface.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginate(int page) {
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    public ModelAndView search(String key, int page) {
        ModelAndView modelAndView = new ModelAndView("outcome/index");
        Map<String, Object> map;
        int currentPage = page;
        int pageCount;
        int outcomeCount;
        try {
            map = outcomeInterface.search(key, currentPage);
            outcomeCount = (int) map.get("count");
            pageCount = (outcomeCount / 10) + 1;
            modelAndView.addObject("outcomes", map.get(outcomeInterface.getStringList()));
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("count", outcomeCount);
            modelAndView.addObject("search", true);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView fetch(int page) {
        ModelAndView modelAndView = new ModelAndView("outcome/index");
        List<Outcome> outcomeList;
        int currentPage = page;
        int pageCount;
        int outcomeCount;
        try {
            outcomeList = outcomeInterface.paginate(currentPage);
            outcomeCount = outcomeInterface.count();
            pageCount = (outcomeCount / 10) + 1;
            modelAndView.addObject("outcomes", outcomeList);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("count", outcomeCount);
            modelAndView.addObject("search", false);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView validateAndExecute(Outcome outcome, String action) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        List<String> errors = new ArrayList<>();
        Map<String, String> errors2 = new HashMap<>();
        try {
            if (!(outcome.getAmount() < 0 || outcome.getQuantity() < 0)) {
                if (action.equals(STORE)) {
                    outcomeInterface.save(outcome);
                } else if (action.equals(UPDATE)) {
                    outcomeInterface.update(outcome);
                }
            } else {
                modelAndView.setViewName("outcome/form");
                modelAndView.addObject("outcome", outcome);
                if (outcome.getAmount() < 0) {
                    errors.add("Nominal tidak boleh negatif !");
                    errors2.put("amount", "Nominal tidak boleh negatif !");
                }
                if (outcome.getQuantity() < 0) {
                    errors.add("Kuantitas tidak boleh negatif !");
                    errors2.put("quantity", "Kuantitas tidak boleh negatif !");
                }
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("errors2", errors2);
            }
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public String getDescription(String id) {
        return outcomeInterface.getDescription(id);
    }
}
