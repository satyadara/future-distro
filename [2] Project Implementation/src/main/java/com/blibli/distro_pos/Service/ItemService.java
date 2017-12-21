package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.item.ItemColorDAO;
import com.blibli.distro_pos.DAO.item.ItemDAO;
import com.blibli.distro_pos.DAO.item.ItemMerkDAO;
import com.blibli.distro_pos.DAO.item.ItemTypeDAO;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import com.blibli.distro_pos.Model.item.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private ItemDAO itemDAO;
    private ItemTypeDAO itemTypeDAO;
    private ItemColorDAO itemColorDAO;
    private ItemMerkDAO itemMerkDAO;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

    @Autowired
    public ItemService(ItemDAO itemDAO, ItemTypeDAO itemTypeDAO, ItemColorDAO itemColorDAO, ItemMerkDAO itemMerkDAO) {
        this.itemDAO = itemDAO;
        this.itemTypeDAO = itemTypeDAO;
        this.itemColorDAO = itemColorDAO;
        this.itemMerkDAO = itemMerkDAO;
    }

    public ModelAndView index() {
        return fetch(1);
    }

    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item = new Item();
        List<ItemType> itemTypeList;
        List<ItemColor> itemColorList;
        List<ItemMerk> itemMerkList;
        try {
            itemTypeList = itemTypeDAO.getAll();
            itemColorList = itemColorDAO.getAll();
            itemMerkList = itemMerkDAO.getAll();
            modelAndView.addObject("item", item);
            modelAndView.addObject("types", itemTypeList);
            modelAndView.addObject("colors", itemColorList);
            modelAndView.addObject("merks", itemMerkList);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView store(Item item, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            String id_item = item.getMerk() + "-" + item.getType() + "-" + item.getSize();
            item.setId_item(id_item);
            item.setUsername(authentication.getName());
            item.setImage("default");
            modelAndView = validateAndExecution(item, STORE);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        modelAndView.addObject("message", "success");
        return modelAndView;
    }

    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item;
        List<ItemType> itemTypeList;
        List<ItemColor> itemColorList;
        List<ItemMerk> itemMerkList;
        try {
            item = itemDAO.getOne(id);
            itemTypeList = itemTypeDAO.getAll();
            itemColorList = itemColorDAO.getAll();
            itemMerkList = itemMerkDAO.getAll();
            System.out.println(id + " " + item.getId_item());
            modelAndView.addObject("message", "success");
            modelAndView.addObject("item", item);
            modelAndView.addObject("types", itemTypeList);
            modelAndView.addObject("colors", itemColorList);
            modelAndView.addObject("merks", itemMerkList);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }


        return modelAndView;
    }

    public ModelAndView update(Item item) {
        ModelAndView modelAndView = validateAndExecution(item, UPDATE);
        return modelAndView;
    }

    public ModelAndView delete(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            itemDAO.softDelete(id);
            modelAndView.addObject("message", "success delete");
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView paginate(int page) {
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    public ModelAndView active(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            itemDAO.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView search(String key, int page) {
        ModelAndView modelAndView = new ModelAndView("item/index");
        Map<String, Object> map;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            map = itemDAO.search(key, page);
            count = (int) map.get("count");
            pageCount = (count / 10) + 1;
            modelAndView.addObject("items", map.get(itemDAO.LIST));
            modelAndView.addObject("count", count);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("search", true);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView fetch(int page) {
        ModelAndView modelAndView = new ModelAndView("item/index");
        List<Item> itemList;
        int itemCount;
        int pageCount;
        int currentpage = page;
        try {
            itemList = itemDAO.paginate(currentpage);
            itemCount = itemDAO.count();
            pageCount = (itemCount / 10) + 1;
            modelAndView.addObject("items", itemList);
            modelAndView.addObject("count", itemCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentpage);
            modelAndView.addObject("search", false);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView validateAndExecution(Item item, String action) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        List<String> errors = new ArrayList<>();
        Map<String, String> errors2 = new HashMap<>();
        List<ItemType> itemTypeList;
        List<ItemColor> itemColorList;
        List<ItemMerk> itemMerkList;
        try {
            if (!(item.getStock() < 0 || item.getPrice() < 0)) {
                if (action.equals(STORE)) {
                    itemDAO.save(item);
                } else if (action.equals(UPDATE)) {
                    itemDAO.update(item);
                }
            } else {
                modelAndView.setViewName("item/form");
                modelAndView.addObject("item", item);
                if (item.getPrice() < 0) {
                    errors.add("Harga barang tidak boleh negatif !");
                    errors2.put("price", "Harga barang tidak boleh negatif !");
                }
                if (item.getStock() < 0) {
                    errors.add("Stok barang tidak boleh negatif !");
                    errors2.put("stock", "Stok barang tidak boleh negatif !");
                }
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("errors2", errors2);
            }

            itemTypeList = itemTypeDAO.getAll();
            itemColorList = itemColorDAO.getAll();
            itemMerkList = itemMerkDAO.getAll();
            modelAndView.addObject("types", itemTypeList);
            modelAndView.addObject("colors", itemColorList);
            modelAndView.addObject("merks", itemMerkList);

        } catch (Exception e) {
            System.out.println("something error : ");
        }
        return modelAndView;
    }
}
