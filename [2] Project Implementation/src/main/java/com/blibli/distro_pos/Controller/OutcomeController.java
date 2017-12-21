package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.outcome.OutcomeDAO;
import com.blibli.distro_pos.Model.outcome.Outcome;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/outcome")
public class OutcomeController {
    private final OutcomeDAO outcomeDAO;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

    @Autowired
    public OutcomeController(OutcomeDAO outcomeDAO) {
        this.outcomeDAO = outcomeDAO;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = fetch(1);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("outcome/form");
        Outcome outcome = new Outcome();
        modelAndView.addObject("outcome", outcome);

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("outcome") Outcome outcome, Authentication authentication) {
        outcome.setUsername(authentication.getName());
        ModelAndView modelAndView = validateAndExecute(outcome, STORE);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("outcome/form");
        Outcome outcome;
        try {
            outcome = outcomeDAO.getOne(id);
            System.out.println(outcome.getDate());
            modelAndView.addObject("outcome", outcome);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("outcome") Outcome outcome) {
        ModelAndView modelAndView = validateAndExecute(outcome, UPDATE);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcomeDAO.softDelete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcomeDAO.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    @RequestMapping(value = "/search/page/{page}", method = GET)
    public ModelAndView search(@RequestParam("key") String key, @PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("outcome/index");
        Map<String, Object> map;
        int currentPage = page;
        int pageCount;
        int outcomeCount;
        try {
            map = outcomeDAO.search(key, currentPage);
            outcomeCount = (int) map.get("count");
            pageCount = (outcomeCount / 10) + 1;
            modelAndView.addObject("outcomes", map.get(outcomeDAO.LIST));
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("count", outcomeCount);
            modelAndView.addObject("search", true);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView fetch(int page) {
        ModelAndView modelAndView = new ModelAndView("outcome/index");
        List<Outcome> outcomeList;
        int currentPage = page;
        int pageCount;
        int outcomeCount;
        try {
            outcomeList = outcomeDAO.paginate(currentPage);
            outcomeCount = outcomeDAO.count();
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
                    outcomeDAO.save(outcome);
                } else if (action.equals(UPDATE)) {
                    outcomeDAO.update(outcome);
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
}