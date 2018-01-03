package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Model.outcome.Outcome;
import com.blibli.distro_pos.Service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/outcome")
public class OutcomeController {
    private final OutcomeService outcomeService;

    @Autowired
    public OutcomeController(OutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        return outcomeService.fetch(1);
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        return outcomeService.create();
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("outcome") Outcome outcome, Authentication authentication) {
        return outcomeService.store(outcome, authentication);
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        return outcomeService.edit(id);
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("outcome") Outcome outcome) {
        return outcomeService.update(outcome);
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id") String id) {
        return outcomeService.delete(id);
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable("id") String id) {
        return outcomeService.active(id);
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        return outcomeService.paginate(page);
    }

    @RequestMapping(value = "/search/page/{page}", method = GET)
    public ModelAndView search(@RequestParam("key") String key, @PathVariable("page") int page) {
        return outcomeService.search(key, page);
    }

    @RequestMapping(value = "/{id}/desc")
    @ResponseBody
    public String getDesc(@PathVariable("id") String id) {
        return outcomeService.getDescription(id);
    }
}