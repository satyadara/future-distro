package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.outcome.OutcomeDAO;
import com.blibli.distro_pos.Model.outcome.Outcome;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/outcome")
public class OutcomeController {
    private final OutcomeDAO outcomeDAO;

    @Autowired
    public OutcomeController(OutcomeDAO outcomeDAO) {
        this.outcomeDAO = outcomeDAO;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("outcome/index");
        List<Outcome> outcomeList;
        int currentPage = 1;
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
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
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
    public ModelAndView store(@ModelAttribute("outcome") Outcome outcome) {
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcome.setId_emp("EMP-1002");
            outcomeDAO.save(outcome);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
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
        ModelAndView modelAndView = new ModelAndView("redirect:/outcome");
        try {
            outcomeDAO.update(outcome);
            System.out.println(outcome.getDate());
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
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
            modelAndView.addObject("count", outcomeCount);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }
}
