package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/cashier")
public class CashierController {
    private final TransactionService transactionService;

    @Autowired
    public CashierController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index(Authentication authentication) {
        return transactionService.index(authentication);
    }

    @RequestMapping(value = "/cart/{id}", method = GET)
    public ModelAndView addCart(@PathVariable("id") String id,
                                @RequestParam("quantity") String qty,
                                @RequestParam("item_name") String item_name,
                                @RequestParam("price_item") double price_item,
                                @RequestParam("stock_item") int stock_item,
                                Authentication authentication) {
        return transactionService.addCart(id, qty, item_name, price_item, stock_item, authentication);
    }

    @RequestMapping(value = "/cart/{id}/edit", method = GET)
    public ModelAndView editCart(@PathVariable("id") String id,
                                 @RequestParam("quantity") int qty,
                                 Authentication authentication)  {
        return transactionService.editCart(id,qty, authentication);
    }

    @RequestMapping(value = "/checkout", method = GET)
    public ModelAndView checkout(@RequestParam("username") String username, @RequestParam("disc") String id_disc, Authentication authentication) {
        return transactionService.checkout(username, id_disc, authentication);
    }

    @RequestMapping(value = "/cart/{id}/cancel", method = GET)
    public ModelAndView cancelCartItem(@PathVariable("id") String id,
                                       @RequestParam("quantity") int qty,
                                       Authentication authentication) {
        return transactionService.cancelCartItem(id, qty, authentication);
    }
}
