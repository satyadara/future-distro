package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.item.ItemColorDAO;
import com.blibli.distro_pos.DAO.item.ItemDAO;
import com.blibli.distro_pos.DAO.item.ItemMerkDAO;
import com.blibli.distro_pos.DAO.item.ItemTypeDAO;
import com.blibli.distro_pos.Model.discount.Discount;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import com.blibli.distro_pos.Model.item.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/item")
public class ItemController {
    private ItemDAO itemDAO;
    private ItemTypeDAO itemTypeDAO;
    private ItemColorDAO itemColorDAO;
    private ItemMerkDAO itemMerkDAO;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

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
        ModelAndView modelAndView = fetch(1);
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
        ModelAndView modelAndView = validateAndExecution(item, UPDATE);
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
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {
            itemDAO.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    private ModelAndView fetch(int page) {
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
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView validateAndExecution(Item item, String action) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");
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


    /**
     * ITEM TYPE
     **/
    @RequestMapping(value = "/type", method = GET)
    public ModelAndView indexType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        int currentPage = 1;
        String content = "type";
        try {
            types = itemTypeDAO.paginate(currentPage);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/type/create", method = GET)
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

    @RequestMapping(value = "/type/create", method = POST)
    public ModelAndView storeType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeDAO.save(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/type/{id}/edit", method = GET)
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

    @RequestMapping(value = "/type/{id}/edit", method = POST)
    public ModelAndView updateType(@ModelAttribute("type") ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeDAO.update(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/type/page/{page}", method = GET)
    public ModelAndView paginateType(@PathVariable(name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemType> types;
        int typeCount;
        int pageCount;
        String content = "type";
        try {
            types = itemTypeDAO.paginate(1);
            typeCount = itemTypeDAO.count();
            pageCount = (typeCount / 10) + 1;
            modelAndView.addObject("datas", types);
            modelAndView.addObject("count", typeCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
    /*******************************************************************************/

    /**
     * ITEM MERK
     **/
    @RequestMapping(value = "/merk", method = GET)
    public ModelAndView indexMerk() {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemMerk> merks;
        int merkCount;
        int pageCount;
        int currentPage = 1;
        String content = "merk";
        try {
            merks = itemMerkDAO.paginate(currentPage);
            merkCount = itemMerkDAO.count();
            pageCount = (merkCount / 10) + 1;
            modelAndView.addObject("datas", merks);
            modelAndView.addObject("count", merkCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/create", method = GET)
    public ModelAndView createMerk() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            ItemMerk merk = new ItemMerk();
            modelAndView.addObject("datas", merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/merk/create", method = POST)
    public ModelAndView storeMerk(@ModelAttribute("merk") ItemMerk merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkDAO.save(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/{id}/edit", method = GET)
    public ModelAndView editMerk(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        ItemMerk merk;
        try {
            merk = itemMerkDAO.getOne(id);
            System.out.println(merk.getId() + " " + merk.getName());
            modelAndView.addObject("data", merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/{id}/edit", method = POST)
    public ModelAndView updateMerk(@ModelAttribute("merk") ItemMerk merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkDAO.update(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/merk/page/{page}", method = GET)
    public ModelAndView paginateMerk(@PathVariable(name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemMerk> merks;
        int merkCount;
        int pageCount;
        String content = "merk";
        try {
            merks = itemMerkDAO.paginate(1);
            merkCount = itemMerkDAO.count();
            pageCount = (merkCount / 10) + 1;
            modelAndView.addObject("datas", merks);
            modelAndView.addObject("count", merkCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
    /*******************************************************************************/


    /**
     * ITEM COLOR
     **/
    @RequestMapping(value = "/color", method = GET)
    public ModelAndView indexColor() {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemColor> colors;
        int merkCount;
        int pageCount;
        int currentPage = 1;
        String content = "color";
        try {
            colors = itemColorDAO.paginate(currentPage);
            merkCount = itemMerkDAO.count();
            pageCount = (merkCount / 10) + 1;
            modelAndView.addObject("datas", colors);
            modelAndView.addObject("count", merkCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/color/create", method = GET)
    public ModelAndView createColor() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            ItemColor color = new ItemColor();
            modelAndView.addObject("datas", color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/color/create", method = POST)
    public ModelAndView storeColor(@ModelAttribute("merk") ItemColor color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorDAO.save(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/color/{id}/edit", method = GET)
    public ModelAndView editColor(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        ItemColor color;
        try {
            color = itemColorDAO.getOne(id);
            System.out.println(color.getId() + " " + color.getName());
            modelAndView.addObject("data", color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/color/{id}/edit", method = POST)
    public ModelAndView updateColor(@ModelAttribute("merk") ItemColor color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorDAO.update(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/color/page/{page}", method = GET)
    public ModelAndView paginateColor(@PathVariable(name = "page") String page) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<ItemColor> colors;
        int colorCount;
        int pageCount;
        String content = "color";
        try {
            colors = itemColorDAO.paginate(1);
            colorCount = itemColorDAO.count();
            pageCount = (colorCount / 10) + 1;
            modelAndView.addObject("datas", colors);
            modelAndView.addObject("count", colorCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
    /*******************************************************************************/


}