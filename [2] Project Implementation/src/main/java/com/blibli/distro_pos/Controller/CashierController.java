package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.cashier.OrderLineDAO;
import com.blibli.distro_pos.DAO.cashier.ShoppingCartDAO;
import com.blibli.distro_pos.DAO.cashier.TransactionDAO;
import com.blibli.distro_pos.DAO.item.ItemDAO;
import com.blibli.distro_pos.Model.cashier.OrderLine;
import com.blibli.distro_pos.Model.cashier.ShoppingCart;
import com.blibli.distro_pos.Model.cashier.Transaction;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/cashier")
public class CashierController {

    private final TransactionDAO transactionDAO;
    private final ShoppingCartDAO shoppingCartDAO;
    private final OrderLineDAO orderLineDAO;
    private final ItemDAO itemDAO;

    @Autowired
    public CashierController(TransactionDAO transactionDAO, ShoppingCartDAO shoppingCartDAO, OrderLineDAO orderLineDAO, ItemDAO itemDAO) {
        this.transactionDAO = transactionDAO;
        this.shoppingCartDAO = shoppingCartDAO;
        this.orderLineDAO = orderLineDAO;
        this.itemDAO = itemDAO;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("cashier/dashboard");
        List<ShoppingCart> list = shoppingCartDAO.getAll();
        List<Item> itemList = itemDAO.getAll();
        for (ShoppingCart cart : list) {
            System.out.println(cart.toString());
        }
        modelAndView.addObject("cart", list);
        modelAndView.addObject("items", itemList);
        return modelAndView;
    }

    @RequestMapping(value = "/cart/{id}")
    public ModelAndView addCart(@PathVariable("id") String id,
                                @RequestParam("quantity") int qty,
                                @RequestParam("item_name") String item_name,
                                Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId_item(id);
        shoppingCart.setUsername(authentication.getName());
        shoppingCart.setQuantity(qty);
        shoppingCart.setItem_name(item_name);
        shoppingCartDAO.save(shoppingCart);
        return modelAndView;
    }

    @RequestMapping(value = "/checkout", method = GET)
    public ModelAndView checkout(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        List<ShoppingCart> list = shoppingCartDAO.getAll();
        String id_trans = transactionDAO.getTransID();
        Double total_trans = 0.0;
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (id_trans.equals("")) {
            return modelAndView;
        }
        Transaction transaction = new Transaction(id_trans, "-", authentication.getName(), "-", 0.0, simpleDateFormat.format(today), "Aktif");
        transactionDAO.save(transaction);

        for (ShoppingCart cart : list) {
            Item item = itemDAO.getOne(cart.getId_item());
            Double subtotal = (double) (item.getPrice() * cart.getQuantity());
            OrderLine orderLine = new OrderLine(null, id_trans, cart.getId_item(), item.getPrice(),
                    subtotal, cart.getQuantity());
            orderLineDAO.save(orderLine);
            total_trans += subtotal;
        }
        transaction.setTotal_trans(total_trans);
        transactionDAO.update(transaction);

        return modelAndView;
    }
}
