package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.item.ItemColorDAO;
import com.blibli.distro_pos.DAO.item.ItemDAO;
import com.blibli.distro_pos.DAO.item.ItemMerkDAO;
import com.blibli.distro_pos.DAO.item.ItemTypeDAO;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import com.blibli.distro_pos.Model.item.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
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
        int pageCount;
        int currentpage = 1;
        try {
            itemList = itemDAO.paginate(currentpage);
            itemCount = itemDAO.count();
            pageCount = (itemCount / 10) + 1;
            modelAndView.addObject("items", itemList);
            modelAndView.addObject("count", itemCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentpage);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = GET)
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

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute(name = "item") Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            String id_item = item.getMerk() + "-" + item.getType() + "-" + item.getSize();
            item.setId_item(id_item);
            item.setId_emp("EMP-1002");
            item.setImage("default");
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

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute(name = "item") Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            itemDAO.update(item);
            modelAndView.addObject("message", "success");
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            itemDAO.softDelete(id);
            modelAndView.addObject("message", "success delete");
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable(name = "page") int page) {
        ModelAndView modelAndView = new ModelAndView("item/index");
        List<Item> itemList;
        int itemCount;
        int pageCount;
        try {
            itemList = itemDAO.paginate(page);
            itemCount = itemDAO.count();
            pageCount = (itemCount / 10) + 1;
            modelAndView.addObject("items", itemList);
            modelAndView.addObject("count", itemCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    /** ITEM TYPE **/
    @RequestMapping(value = "/tipe", method = GET)
    public ModelAndView indexType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        int currentPage = 1;
        try {
            types = itemTypeDAO.paginate(currentPage);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentPage);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/tipe/create", method = GET)
    public ModelAndView createType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            ItemType type = new ItemType();
            modelAndView.addObject("datas", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/tipe/create", method = POST)
    public ModelAndView storeType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/tipe");
        try {
            itemTypeDAO.save(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/tipe/{id}/edit", method = GET)
    public ModelAndView editType(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        ItemType type;
        try {
            type = itemTypeDAO.getOne(id);
            modelAndView.addObject("data", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/tipe/{id}/edit", method = POST)
    public ModelAndView updateType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/tipe");
        try {
            itemTypeDAO.update(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/tipe/page/{page}", method = GET)
    public ModelAndView paginateType(@PathVariable(name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        try {
            types = itemTypeDAO.paginate(1);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
    /*******************************************************************************/

    /** ITEM MERK **/
    @RequestMapping(value = "/merk", method = GET)
    public ModelAndView indexType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        int currentPage = 1;
        try {
            types = itemTypeDAO.paginate(currentPage);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentPage);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/create", method = GET)
    public ModelAndView createType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            ItemType type = new ItemType();
            modelAndView.addObject("datas", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/tipe/create", method = POST)
    public ModelAndView storeType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/tipe");
        try {
            itemTypeDAO.save(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/{id}/edit", method = GET)
    public ModelAndView editType(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        ItemType type;
        try {
            type = itemTypeDAO.getOne(id);
            modelAndView.addObject("data", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/{id}/edit", method = POST)
    public ModelAndView updateType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/tipe");
        try {
            itemTypeDAO.update(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/page/{page}", method = GET)
    public ModelAndView paginateType(@PathVariable(name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        try {
            types = itemTypeDAO.paginate(1);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
    /*******************************************************************************/


}