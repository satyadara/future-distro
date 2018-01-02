package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.cashier.Interface.OrderLineInterface;
import com.blibli.distro_pos.DAO.cashier.Interface.ShoppingCartInterface;
import com.blibli.distro_pos.DAO.cashier.Interface.TransactionInterface;
import com.blibli.distro_pos.DAO.item.Interface.ItemInterface;
import com.blibli.distro_pos.DAO.item.Interface.ItemTypeInterface;
import com.blibli.distro_pos.Model.cashier.OrderLine;
import com.blibli.distro_pos.Model.cashier.ShoppingCart;
import com.blibli.distro_pos.Model.cashier.Transaction;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.SubItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class TransactionService {
    private final TransactionInterface transactionInterface;
    private final ShoppingCartInterface shoppingCartInterface;
    private final OrderLineInterface orderLineInterface;
    private final ItemInterface itemInterface;
    private final ItemTypeInterface itemTypeInterface;

    @Autowired
    public TransactionService(TransactionInterface transactionInterface,
                              ShoppingCartInterface shoppingCartInterface, OrderLineInterface orderLineInterface,
                              ItemInterface itemInterface, ItemTypeInterface itemTypeInterface) {
        this.transactionInterface = transactionInterface;
        this.shoppingCartInterface = shoppingCartInterface;
        this.orderLineInterface = orderLineInterface;
        this.itemInterface = itemInterface;
        this.itemTypeInterface = itemTypeInterface;
    }

    public ModelAndView index(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("cashier/dashboard");
        List<ShoppingCart> list = shoppingCartInterface.getAll(authentication.getName());
        List<SubItem> subItemList = itemTypeInterface.getAll();
        List<List<Item>> itemList = new ArrayList<>();
        Map<String, List> map = new HashMap<>();

        for (SubItem subItem : subItemList) {
            List<Item> iList = itemInterface.getByType(subItem.getId());
            itemList.add(iList);
        }

        for (Item list1 : itemList.get(0)) {
            System.out.println(list1.getId_item());
        }
        map.put("types", subItemList);
        map.put("items", itemList);

        modelAndView.addObject("cart", list);
        modelAndView.addObject("itemMap", map);
        return modelAndView;
    }

    public ModelAndView indexIransaction(int page) {
        ModelAndView modelAndView = new ModelAndView("transaction/index");
        List<Transaction> transactionList;
        int transactionCount;
        int pageCount;
        int currentpage = page;
        try {
            transactionList = transactionInterface.paginate(currentpage);
            transactionCount = transactionInterface.count();
            pageCount = (transactionCount / 10) + 1;
            modelAndView.addObject("transactions", transactionList);
            modelAndView.addObject("count", transactionCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentpage);
            modelAndView.addObject("search", false);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView search(String key, int page) {
        ModelAndView modelAndView = new ModelAndView("transaction/index");
        Map<String, Object> map;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            map = transactionInterface.search(key, page);
            count = (int) map.get("count");
            pageCount = (count / 10) + 1;
            modelAndView.addObject("transactions", map.get(transactionInterface.getListString()));
            modelAndView.addObject("count", count);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("search", true);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView addCart(String id, String quantity, String item_name, double price_item, int stock_item,
                                Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        ShoppingCart shoppingCart = shoppingCartInterface.getOne(id);
        if (quantity.isEmpty()) {
            return modelAndView;
        }
        int qty = Integer.parseInt(quantity);
        if (stock_item < qty) {
            return modelAndView;
        }
        if (shoppingCart.getId_item() != null) {
            System.out.println("update");

            itemInterface.addOrMinStock(id, qty * -1);
            qty += shoppingCart.getQuantity();
            if (qty < 1) {
                return modelAndView;
            }
            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartInterface.update(shoppingCart);
        } else {
            System.out.println("save");
            if (qty < 1) {
                return modelAndView;
            }
            shoppingCart = setShoppingCart(id, authentication.getName(), qty, item_name, price_item);
            shoppingCartInterface.save(shoppingCart);
            itemInterface.addOrMinStock(id, qty * -1);
        }

        return modelAndView;
    }

    public ModelAndView editCart(String id, int quantity, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        ShoppingCart shoppingCart = shoppingCartInterface.getOne(id);
        Item item = itemInterface.getOne(id);
        if (quantity == 0) {
            return modelAndView;
        }

        if (item.getStock() < quantity) {
            return modelAndView;
        }

        itemInterface.addOrMinStock(id, quantity * -1);
        quantity += shoppingCart.getQuantity();
        if (quantity < 1) {
            return modelAndView;
        }
        shoppingCart = setShoppingCart(id, authentication.getName(), quantity, shoppingCart.getItem_name(),
                item.getPrice());
        shoppingCartInterface.update(shoppingCart);


        return modelAndView;
    }

    public ModelAndView checkout(String username, String id_disc, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        List<ShoppingCart> list = shoppingCartInterface.getAll(authentication.getName());
        String id_trans = transactionInterface.getTransID();
        Double total_trans = 0.0;
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (id_trans.equals("")) {
            return modelAndView;
        }

        Transaction transaction = new Transaction(id_trans, id_disc, authentication.getName(),
                username, 0.0, simpleDateFormat.format(today), "Aktif");
        transactionInterface.save(transaction);

        for (ShoppingCart cart : list) {
            Item item = itemInterface.getOne(cart.getId_item());
            Double subtotal = (double) (item.getPrice() * cart.getQuantity());
            OrderLine orderLine = new OrderLine(null, id_trans, cart.getId_item(), item.getPrice(),
                    subtotal, cart.getQuantity());
            orderLineInterface.save(orderLine);
            total_trans += subtotal;
        }
        transaction.setTotal_trans(total_trans);
        transactionInterface.update(transaction);
        shoppingCartInterface.clear(authentication.getName());
        return modelAndView;
    }

    public ModelAndView cancelCartItem(String id, int qty, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");
        System.out.println(qty);
        itemInterface.addOrMinStock(id, qty);
        shoppingCartInterface.cancel(id, authentication.getName());
        return modelAndView;
    }

    public ModelAndView cancelOrder(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cashier");

        //Stok kembali setelah cart dihapus
        for (ShoppingCart shoppingCart : shoppingCartInterface.getAll(authentication.getName())) {
            itemInterface.addOrMinStock(shoppingCart.getId_item(), shoppingCart.getQuantity());
        }
        shoppingCartInterface.clear(authentication.getName());

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
