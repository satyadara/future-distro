package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping("/page/{page}")
    public ModelAndView index(@PathVariable("page") int page) {
        return transactionService.indexIransaction(page);
    }

    @RequestMapping("/search/{page}")
    public ModelAndView search(@RequestParam("key") String key, @PathVariable("page") int page) {
        return transactionService.search(key, page);
    }

    @RequestMapping("/{id_trans}")
    public ModelAndView invoice(@PathVariable("id_trans") String id_trans) {
        return transactionService.invoice(id_trans);
    }
}
