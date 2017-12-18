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
        List<Item> itemList = itemDAO.paginate(1);

        for (ShoppingCart cart : list) {

        }

        modelAndView.addObject("cart", list);
        modelAndView.addObject("items", itemList);
        return modelAndView;
    }

    @RequestMapping(value = "/cart/{id}", method = GET)
    public ModelAndView addCart(@PathVariable("id") String id,
                                @RequestParam("quantity") int qty,
                                @RequestParam("item_name") String item_name,
                                @RequestParam("price_item") double price_item,
                                Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        ShoppingCart shoppingCart = shoppingCartDAO.getOne(id);

        if (shoppingCart != null) {
            System.out.println("update");

            qty += shoppingCart.getQuantity();

            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartDAO.update(shoppingCart);
        } else {
            System.out.println("save");
            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartDAO.save(shoppingCart);
        }
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

    private ShoppingCart setShoppingCart(String id, String username, int qty, String item_name, double price_item) {
        ShoppingCart shoppingCart = new ShoppingCart();
        double subtotal;
        shoppingCart.setId_item(id);
        shoppingCart.setUsername(username);
        shoppingCart.setQuantity(qty);
        shoppingCart.setItem_name(item_name);
        subtotal = qty * price_item;
        shoppingCart.setSubtotal(subtotal);

        return shoppingCart;
    }
}
