package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.cashier.OrderLineImpl;
import com.blibli.distro_pos.DAO.cashier.ShoppingCartImpl;
import com.blibli.distro_pos.DAO.cashier.TransactionImpl;
import com.blibli.distro_pos.DAO.item.ItemImpl;
import com.blibli.distro_pos.Model.cashier.OrderLine;
import com.blibli.distro_pos.Model.cashier.ShoppingCart;
import com.blibli.distro_pos.Model.cashier.Transaction;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class TransactionService {

    private final TransactionImpl transactionImpl;
    private final ShoppingCartImpl shoppingCartImpl;
    private final OrderLineImpl orderLineImpl;
    private final ItemImpl itemImpl;

    @Autowired
    public TransactionService(TransactionImpl transactionImpl, ShoppingCartImpl shoppingCartImpl,
                              OrderLineImpl orderLineImpl, ItemImpl itemImpl) {
        this.transactionImpl = transactionImpl;
        this.shoppingCartImpl = shoppingCartImpl;
        this.orderLineImpl = orderLineImpl;
        this.itemImpl = itemImpl;
    }

    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("cashier/dashboard");
        List<ShoppingCart> list = shoppingCartImpl.getAll();
        List<Item> itemList = itemImpl.paginate(1);

        for (ShoppingCart cart : list) {

        }

        modelAndView.addObject("cart", list);
        modelAndView.addObject("items", itemList);
        return modelAndView;
    }

    public ModelAndView addCart(String id, String quantity, String item_name, double price_item, int stock_item,
                                Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        ShoppingCart shoppingCart = shoppingCartImpl.getOne(id);
        if (quantity.isEmpty()) {
            return modelAndView;
        }
        int qty = Integer.parseInt(quantity);
        if (stock_item < qty ) {
            return modelAndView;
        }
        if (shoppingCart.getId_item() != null) {
            System.out.println("update");

            qty += shoppingCart.getQuantity();
            if (qty < 1) {
                return modelAndView;
            }
            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartImpl.update(shoppingCart);
        } else {
            System.out.println("save");
            if (qty < 1) {
                return modelAndView;
            }
            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartImpl.save(shoppingCart);
        }
        itemImpl.addOrMinStock(id, qty * -1);

        return modelAndView;
    }

    public ModelAndView checkout(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        List<ShoppingCart> list = shoppingCartImpl.getAll();
        String id_trans = transactionImpl.getTransID();
        Double total_trans = 0.0;
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (id_trans.equals("")) {
            return modelAndView;
        }
        Transaction transaction = new Transaction(id_trans, null, authentication.getName(), "-", 0.0, simpleDateFormat.format(today), "Aktif");
        transactionImpl.save(transaction);

        for (ShoppingCart cart : list) {
            Item item = itemImpl.getOne(cart.getId_item());
            Double subtotal = (double) (item.getPrice() * cart.getQuantity());
            OrderLine orderLine = new OrderLine(null, id_trans, cart.getId_item(), item.getPrice(),
                    subtotal, cart.getQuantity());
            orderLineImpl.save(orderLine);
            total_trans += subtotal;
        }
        transaction.setTotal_trans(total_trans);
        transactionImpl.update(transaction);
        shoppingCartImpl.clear(authentication.getName());
        return modelAndView;
    }

    public ModelAndView cancelCartItem(String id, int qty, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        System.out.println(qty);
        itemImpl.addOrMinStock(id, qty);
        shoppingCartImpl.cancel(id, authentication.getName());
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
