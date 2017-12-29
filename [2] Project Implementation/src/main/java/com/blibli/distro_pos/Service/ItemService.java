package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.item.Interface.ItemColorInterface;
import com.blibli.distro_pos.DAO.item.Interface.ItemInterface;
import com.blibli.distro_pos.DAO.item.Interface.ItemMerkInterface;
import com.blibli.distro_pos.DAO.item.Interface.ItemTypeInterface;
import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.SubItem;
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
    private final ItemTypeInterface itemTypeInterface;
    private final ItemMerkInterface itemMerkInterface;
    private final ItemColorInterface itemColorInterface;
    private static final String STORE = "store";
    private static final String UPDATE = "update";
    private static final String COLOR = "Color";
    private static final String TYPE = "Type";
    private static final String MERK = "Merk";

    //Lokasi buat ngesave gambar
    private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/img/item/";

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

    public ModelAndView store(Item item, MultipartFile image, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item");

        if (image.isEmpty()) {

            return modelAndView;
        }
        try {

            String id_item = item.getMerk() + "-" + item.getType() + "-" + item.getSize();
            item.setId_item(id_item);
            item.setUsername(authentication.getName());

            //Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "image_" + id_item + ".jpg");
            Files.write(path, bytes);
            item.setImage("image_" + id_item + ".jpg");

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

    public ModelAndView update(Item item, MultipartFile image) {

        ModelAndView modelAndView = new ModelAndView("redirect:/item");

        try {

            //Kalo gambarnya ga kosong
            if (!image.isEmpty()) {

                //Get the file and save it somewhere
                byte[] bytes = image.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + "image_" + item.getId_item() + ".jpg");
                Files.write(path, bytes);
                item.setImage("image_" + item.getId_item() + ".jpg");
            }

            modelAndView = validateAndExecution(item, UPDATE);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

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

    public ModelAndView outOfStock(int page) {
        ModelAndView modelAndView = new ModelAndView("item/outofstock");
        List<Item> itemList;
        int itemCount;
        int pageCount;
        int currentpage = page;
        try {
            itemList = itemInterface.getOutOfStock(currentpage);
            itemCount = itemInterface.countOutOfStock();
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

    /************************SUB-ITEM TYPE************************/
    public ModelAndView indexType() {
        return paginateType("1");
    }

    public ModelAndView createType() {
        return create(TYPE);
    }

    public ModelAndView storeType(SubItem type) {
        return subItemValidate(TYPE, STORE, type);
    }

    public ModelAndView editType(String id) {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");
        SubItem type;
        try {
            type = itemTypeInterface.getOne(id);
            modelAndView.addObject("data", type);
            modelAndView.addObject("content", TYPE);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView updateType(SubItem type) {
        return subItemValidate(TYPE, UPDATE, type);
    }

    public ModelAndView paginateType(String page) {
        return paginate(Integer.parseInt(page), TYPE);
    }

    /************************SUB-ITEM MERK************************/
    public ModelAndView indexMerk() {
        return paginateMerk("1");
    }

    public ModelAndView createMerk() {
        return create(MERK);
    }

    public ModelAndView storeMerk(SubItem merk) {
        return subItemValidate(MERK, STORE, merk);
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
        return subItemValidate(MERK, UPDATE, merk);
    }

    public ModelAndView paginateMerk(String page) {
        return paginate(Integer.parseInt(page), MERK);
    }

    /************************SUB-ITEM COLOR************************/
    public ModelAndView indexColor() {
        return paginateColor("1");
    }

    public ModelAndView createColor() {
        return create(COLOR);
    }

    public ModelAndView storeColor(SubItem color) {
        return subItemValidate(COLOR, STORE, color);
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
        return subItemValidate(COLOR, UPDATE, color);
    }

    public ModelAndView paginateColor(String page) {
        return paginate(Integer.parseInt(page), COLOR);
    }

    /********************* SUPPORT METHOD *********************/
    public ModelAndView create(String sub)  {
        ModelAndView modelAndView = new ModelAndView("item/sub/form");

        try {
            SubItem subItem = new SubItem();
            modelAndView.addObject("data", subItem);
            modelAndView.addObject("content", sub);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
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

    private ModelAndView subItemValidate(String content, String action, SubItem subItem) {
        ModelAndView modelAndView = new ModelAndView("redirect:/item/" + content.toLowerCase());
        Map<String, String> errors = new HashMap<>();
        try {
            if (subItem.getId().isEmpty() || subItem.getId().length() != 3 || subItem.getName().isEmpty()) {
                modelAndView.setViewName("item/sub/form");
                modelAndView.addObject("data", subItem);
                if (subItem.getId().isEmpty()) {
                    errors.put("id", "ID tidak boleh kosong !");
                    modelAndView.addObject("content", content);
                } else if (subItem.getId().length() != 3) {
                    errors.put("id", "ID harus 3 digit !");
                } else if (subItem.getName().isEmpty()) {
                    errors.put("name", "Nama tidak boleh kosong !");
                }
                modelAndView.addObject("errors", errors);
            } else {
                if (content.equals(COLOR)) {
                    if (action.equals(STORE)) {
                        itemColorInterface.save(subItem);
                    } else if (action.equals(UPDATE)) {
                        itemColorInterface.update(subItem);
                    }
                } else if (content.equals(TYPE)) {
                    if (action.equals(STORE)) {
                        itemTypeInterface.save(subItem);
                    } else if (action.equals(UPDATE)) {
                        itemTypeInterface.update(subItem);
                    }
                } else if (content.equals(MERK)) {
                    if (action.equals(STORE)) {
                        itemMerkInterface.save(subItem);
                    } else if (action.equals(UPDATE)) {
                        itemMerkInterface.update(subItem);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Sub Item Validate error : " + e.toString());
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
}
