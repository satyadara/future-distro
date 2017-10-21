package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.ItemDAO;
import com.blibli.distro_pos.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    private ItemDAO itemDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
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
        modelAndView.addObject("item", item);
        modelAndView.addObject("method", "POST");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView doCreate(@ModelAttribute(name = "item") Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");

        try {
            String id_item = getTypeCode(item.getType()) + "-" + getColorCode(item.getColor()) + "-" + item.getSize();
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
        try {
            item = itemDAO.getOne(id);
            System.out.println(id + " " + item.getId_item());
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success");
        modelAndView.addObject("item", item);

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

    private String getTypeCode(String type) {
        String result = "";
        if (type.equals("Pakaian"))
            result = "PKN";
        else if (type.equals("Celana"))
            result = "CLN";

        return result;
    }

    private String getColorCode(String color) {
        String result = "";
        if (color.equals("Merah"))
            result = "MRH";
        else if (color.equals("Kuning"))
            result = "KNG";
        else if (color.equals("Hijau"))
            result = "HJU";

        return result;
    }

}