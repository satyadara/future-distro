package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.ItemDAO;
import com.blibli.distro_pos.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ItemController {

    private ItemDAO itemDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public ItemController() {
    }

    @RequestMapping(value = "/item", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("item/index");
        List<Item> itemList = itemDAO.getAll();
        int itemCount = itemDAO.count();
        modelAndView.addObject("items", itemList);
        modelAndView.addObject("count", itemCount);
//        for (int i = 0; i < itemList.size(); i++) {
//            System.out.println(itemList.get(i).getId_item());
//        }
        return modelAndView;
    }

    @RequestMapping(value = "/item/create", method = GET)
    public ModelAndView goToCreate() {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item = new Item();
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    @RequestMapping(value = "/item/create", method = POST)
    public ModelAndView doCreate(@ModelAttribute("item") Item item) {
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

    @RequestMapping(value = "/item/{id}/edit", method = GET)
    public ModelAndView update(@PathVariable("username") String id) {
        ModelAndView modelAndView = new ModelAndView("form");
        Item item = new Item();
        try {
            item = itemDAO.getOne(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success");
        modelAndView.addObject("user", item);

        return modelAndView;
    }

    @RequestMapping(value = "/user/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute Item item, @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        try {
            itemDAO.update(item);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        modelAndView.addObject("message", "success");
        modelAndView.addObject("user", item);

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
        else if (color.equals("Hijau"))
            result = "HJU";

        return result;
    }

}
