package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.item.Interface.*;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.SubItem;
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
    private final ItemInterface itemInterface;
    private final ItemTypeInterface itemTypeInterface;
    private final ItemMerkInterface itemMerkInterface;
    private final ItemColorInterface itemColorInterface;
    private static final String STORE = "store";
    private static final String UPDATE = "update";
    private static final String COLOR = "color";
    private static final String TYPE = "type";
    private static final String MERK = "merk";

    @Autowired
    public ItemService(ItemInterface itemInterface, ItemTypeInterface itemTypeInterface,
                       ItemMerkInterface itemMerkInterface, ItemColorInterface itemColorInterface) {
        this.itemInterface = itemInterface;
        this.itemTypeInterface = itemTypeInterface;
        this.itemMerkInterface = itemMerkInterface;
        this.itemColorInterface = itemColorInterface;
    }

    /************************ITEM MASTER************************/
    public ModelAndView index() {
        return fetch(1);
    }

    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("item/form");
        Item item = new Item();
        List<SubItem> itemTypeList;
        List<SubItem> itemColorList;
        List<SubItem> itemMerkList;
        try {
            itemColorList = itemColorInterface.getAll();
            itemMerkList = itemMerkInterface.getAll();
            itemTypeList = itemTypeInterface.getAll();
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
        List<SubItem> itemTypeList;
        List<SubItem> itemColorList;
        List<SubItem> itemMerkList;
        try {
            itemColorList = itemColorInterface.getAll();
            itemMerkList = itemMerkInterface.getAll();
            itemTypeList = itemTypeInterface.getAll();
            item = itemInterface.getOne(id);
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
            itemInterface.softDelete(id);
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
            itemInterface.setActive(id);
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
            map = itemInterface.search(key, page);
            count = (int) map.get("count");
            pageCount = (count / 10) + 1;
            modelAndView.addObject("items", map.get(itemInterface.getListString()));
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
            itemList = itemInterface.paginate(currentpage);
            itemCount = itemInterface.count();
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
        List<SubItem> itemTypeList;
        List<SubItem> itemColorList;
        List<SubItem> itemMerkList;
        try {
            if (!(item.getStock() < 0 || item.getPrice() < 0)) {
                if (action.equals(STORE)) {
                    itemInterface.save(item);
                } else if (action.equals(UPDATE)) {
                    itemInterface.update(item);
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

            itemTypeList = itemTypeInterface.getAll();
            itemColorList = itemColorInterface.getAll();
            itemMerkList = itemMerkInterface.getAll();
            modelAndView.addObject("types", itemTypeList);
            modelAndView.addObject("colors", itemColorList);
            modelAndView.addObject("merks", itemMerkList);

        } catch (Exception e) {
            System.out.println("something error : ");
        }
        return modelAndView;
    }

    /************************SUB-ITEM TYPE************************/
    public ModelAndView indexType() {
        return paginateType("1");
    }

    public ModelAndView createType() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            SubItem type = new SubItem();
            modelAndView.addObject("datas", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView storeType(SubItem type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeInterface.save(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editType(String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        SubItem type;
        try {
            type = itemTypeInterface.getOne(id);
            modelAndView.addObject("data", type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView updateType(SubItem type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeInterface.update(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateType(String page) {
        return paginate(Integer.parseInt(page), TYPE);
    }

    /************************SUB-ITEM MERK************************/
    public ModelAndView indexMerk() {
        return paginateMerk("1");
    }

    public ModelAndView createMerk() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            SubItem merk = new SubItem();
            modelAndView.addObject("datas", merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView storeMerk(SubItem merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkInterface.save(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editMerk(String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        SubItem merk;
        try {
            merk = itemMerkInterface.getOne(id);
            modelAndView.addObject("data", merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView updateMerk(SubItem merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkInterface.update(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateMerk(String page) {
        return paginate(Integer.parseInt(page), MERK);
    }

    /************************SUB-ITEM COLOR************************/
    public ModelAndView indexColor() {
        return paginateColor("1");
    }

    public ModelAndView createColor() {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            SubItem color = new SubItem();
            modelAndView.addObject("datas", color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    public ModelAndView storeColor(SubItem color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorInterface.save(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editColor(String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        SubItem color;
        try {
            color = itemColorInterface.getOne(id);
            modelAndView.addObject("data", color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView updateColor(SubItem color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorInterface.update(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateColor(String page) {
        return paginate(Integer.parseInt(page), COLOR);
    }

    public ModelAndView paginate(int page, String content) {
        ModelAndView modelAndView = new ModelAndView("item/sub/index");
        List<SubItem> datas = new ArrayList<>();
        int dataCount = 0;
        int pageCount;
        try {
            if (content.equals(COLOR)) {
                datas = itemColorInterface.paginate(page);
                dataCount = itemColorInterface.count();
            } else if (content.equals(TYPE)) {
                datas = itemTypeInterface.paginate(page);
                dataCount = itemTypeInterface.count();
            } else if (content.equals(MERK)) {
                datas = itemMerkInterface.paginate(page);
                dataCount = itemMerkInterface.count();
            }
            pageCount = (dataCount / 10) + 1;
            modelAndView.addObject("datas", datas);
            modelAndView.addObject("count", dataCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("content", content);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return modelAndView;
    }
}
