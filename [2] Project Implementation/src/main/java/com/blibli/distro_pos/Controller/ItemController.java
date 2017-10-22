package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.ItemColorDAO;
import com.blibli.distro_pos.DAO.ItemDAO;
import com.blibli.distro_pos.DAO.ItemMerkDAO;
import com.blibli.distro_pos.DAO.ItemTypeDAO;
import com.blibli.distro_pos.Model.Item;
import com.blibli.distro_pos.Model.ItemColor;
import com.blibli.distro_pos.Model.ItemMerk;
import com.blibli.distro_pos.Model.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    private ItemDAO itemDAO;
    private ItemTypeDAO itemTypeDAO;
    private ItemColorDAO itemColorDAO;
    private ItemMerkDAO itemMerkDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO, ItemTypeDAO itemTypeDAO, ItemColorDAO itemColorDAO, ItemMerkDAO itemMerkDAO) {
        this.itemDAO = itemDAO;
        this.itemTypeDAO = itemTypeDAO;
        this.itemColorDAO = itemColorDAO;
        this.itemMerkDAO = itemMerkDAO;
    }

    public ItemController() {
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("item/index");
        List<Item> itemList;
        int itemCount;
        try {
            itemList = itemDAO.getAll();
            itemCount = itemDAO.count();
            modelAndView.addObject("items", itemList);
            modelAndView.addObject("count", itemCount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView goToCreate() {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item = new Item();
        List<ItemType> itemTypeList = new ArrayList<>();
        List<ItemColor> itemColorList = new ArrayList<>();
        List<ItemMerk> itemMerkList = new ArrayList<>();
        try {
            itemTypeList = itemTypeDAO.getAll();
            itemColorList = itemColorDAO.getAll();
            itemMerkList = itemMerkDAO.getAll();
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        modelAndView.addObject("item", item);
        modelAndView.addObject("types", itemTypeList);
        modelAndView.addObject("colors", itemColorList);
        modelAndView.addObject("merks", itemMerkList);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView doCreate(@ModelAttribute(name = "item") Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            String id_item = item.getMerk() + "-" + item.getType() + "-" + item.getSize();
            item.setId_item(id_item);
            itemDAO.save(item);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        modelAndView.addObject("message", "success");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item = new Item();
        List<ItemType> itemTypeList = new ArrayList<>();
        List<ItemColor> itemColorList = new ArrayList<>();
        List<ItemMerk> itemMerkList = new ArrayList<>();
        try {
            item = itemDAO.getOne(id);
            itemTypeList = itemTypeDAO.getAll();
            itemColorList = itemColorDAO.getAll();
            itemMerkList = itemMerkDAO.getAll();
            System.out.println(id + " " + item.getId_item());
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success");
        modelAndView.addObject("item", item);
        modelAndView.addObject("types", itemTypeList);
        modelAndView.addObject("colors", itemColorList);
        modelAndView.addObject("merks", itemMerkList);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute(name = "item") Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            itemDAO.update(item);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        System.out.println("ssss " + id);
        try {
            itemDAO.delete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success delete");

        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable(name = "page") int page) {
        ModelAndView modelAndView = new ModelAndView("item/index");
        List<Item> itemList;
        int itemCount;
        try {
            itemList = itemDAO.paginate(page);
            itemCount = itemDAO.count();

            modelAndView.addObject("items", itemList);
            modelAndView.addObject("count", itemCount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }


}