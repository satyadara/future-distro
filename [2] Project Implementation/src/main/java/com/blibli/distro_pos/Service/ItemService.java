package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.item.Interface.ItemInterface;
import com.blibli.distro_pos.DAO.item.ItemColorDAO;
import com.blibli.distro_pos.DAO.item.ItemMerkDAO;
import com.blibli.distro_pos.DAO.item.ItemTypeDAO;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import com.blibli.distro_pos.Model.item.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final ItemInterface itemInterface;
    private final ItemTypeDAO itemTypeDAO;
    private final ItemColorDAO itemColorDAO;
    private final ItemMerkDAO itemMerkDAO;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    public ItemService(ItemInterface itemInterface, ItemTypeDAO itemTypeDAO, ItemColorDAO itemColorDAO, ItemMerkDAO itemMerkDAO) {
        this.itemInterface = itemInterface;
        this.itemTypeDAO = itemTypeDAO;
        this.itemColorDAO = itemColorDAO;
        this.itemMerkDAO = itemMerkDAO;
    }

    /************************ITEM MASTER************************/
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

    public ModelAndView store(Item item, MultipartFile image, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");
        try {

            String id_item = item.getMerk() + "-" + item.getType() + "-" + item.getSize();
            item.setId_item(id_item);
            item.setUsername(authentication.getName());
            //Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + id_item + ".jpg");
            Files.write(path, bytes);
            item.setImage(id_item + ".jpg");
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
            item = itemInterface.getOne(id);
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
        List<ItemType> itemTypeList;
        List<ItemColor> itemColorList;
        List<ItemMerk> itemMerkList;
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

    /************************SUB-ITEM TYPE************************/
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

    public ModelAndView storeType(ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeDAO.save(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editType(String id) {
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

    public ModelAndView updateType(ItemType type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/type");
        try {
            itemTypeDAO.update(type);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateType(String page) {
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

    /************************SUB-ITEM MERK************************/
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

    public ModelAndView storeMerk(ItemMerk merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkDAO.save(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editMerk(String id) {
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

    public ModelAndView updateMerk(ItemMerk merk) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/merk");
        try {
            itemMerkDAO.update(merk);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateMerk(String page) {
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

    /************************SUB-ITEM COLOR************************/
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

    public ModelAndView storeColor(ItemColor color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorDAO.save(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView editColor(String id) {
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

    public ModelAndView updateColor(ItemColor color) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/color");
        try {
            itemColorDAO.update(color);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginateColor(String page) {
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
}
