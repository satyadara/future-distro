package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.ledger.GeneralLedgerDAO;
import com.blibli.distro_pos.Model.ledger.Ledger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ledger")
public class LedgerController {
    private final GeneralLedgerDAO generalLedgerDAO;

    @Autowired
    public LedgerController(GeneralLedgerDAO generalLedgerDAO) {
        this.generalLedgerDAO = generalLedgerDAO;
    }

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("ledger/index");
        List<Ledger> ledgers = generalLedgerDAO.getIndex();
        modelAndView.addObject("ledgers", ledgers);
        return modelAndView;
    }

    @RequestMapping("/filter/page/{page}")
    public ModelAndView between(@PathVariable("page") int page,
                                @RequestParam("date_from") String date_from,
                                @RequestParam("date_to") String date_to) {
        ModelAndView modelAndView = new ModelAndView("ledger/index");
        try {
            List<Ledger> ledgers = generalLedgerDAO.getFilter(date_from, date_to, (page-1)*10);
            modelAndView.addObject("ledgers", ledgers);
            modelAndView.addObject("date_from", date_from);
            modelAndView.addObject("date_to", date_to);
        } catch (Exception e) {
            System.out.println("#CTRLR PAGINATE# something error : " + e.toString());
        }
        return modelAndView;
    }
}
