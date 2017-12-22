package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Model.item.Item;
import com.blibli.distro_pos.Model.item.SubItem;
import com.blibli.distro_pos.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        return itemService.fetch(1);
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        return itemService.create();
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute(name = "item") Item item, Authentication authentication) {
        return itemService.store(item, authentication);
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable(name = "id") String id) {
        return itemService.edit(id);
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute(name = "item") Item item) {
        return itemService.update(item);
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable(name = "id") String id, HttpServletRequest request) {
        return itemService.delete(id, request);
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable(name = "page") int page) {
        return itemService.fetch(page);
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable(name = "id") String id, HttpServletRequest request) {
        return itemService.active(id, request);
    }

    @RequestMapping(value = "/search/page/{page}", method = GET)
    public ModelAndView search(@RequestParam("key") String key, @PathVariable("page") int page) {
        return itemService.search(key, page);
    }

    /**
     * ITEM TYPE
     **/
    @RequestMapping(value = "/type", method = GET)
    public ModelAndView indexType() {
        return itemService.indexType();
    }

    @RequestMapping(value = "/type/create", method = GET)
    public ModelAndView createType() {
        return itemService.createType();
    }

    @RequestMapping(value = "/type/create", method = POST)
    public ModelAndView storeType(@ModelAttribute("type") SubItem type) {
        return itemService.storeType(type);
    }

    @RequestMapping(value = "/type/{id}/edit", method = GET)
    public ModelAndView editType(@PathVariable("id") String id) {
        return itemService.editType(id);
    }

    @RequestMapping(value = "/type/{id}/edit", method = POST)
    public ModelAndView updateType(@ModelAttribute("type") SubItem type) {
        return itemService.updateType(type);
    }

    @RequestMapping(value = "/type/page/{page}", method = GET)
    public ModelAndView paginateType(@PathVariable(name = "page") String page) {
        return itemService.paginateType(page);
    }
    /*******************************************************************************/

    /**
     * ITEM MERK
     **/
    @RequestMapping(value = "/merk", method = GET)
    public ModelAndView indexMerk() {
        return itemService.indexMerk();
    }

    @RequestMapping(value = "/merk/create", method = GET)
    public ModelAndView createMerk() {
        return itemService.createMerk();
    }

    @RequestMapping(value = "/merk/create", method = POST)
    public ModelAndView storeMerk(@ModelAttribute("merk") SubItem merk) {
        return itemService.storeMerk(merk);
    }

    @RequestMapping(value = "/merk/{id}/edit", method = GET)
    public ModelAndView editMerk(@PathVariable("id") String id) {
        return itemService.editMerk(id);
    }

    @RequestMapping(value = "/merk/{id}/edit", method = POST)
    public ModelAndView updateMerk(@ModelAttribute("merk") SubItem merk) {
        return itemService.updateMerk(merk);
    }

    @RequestMapping(value = "/merk/page/{page}", method = GET)
    public ModelAndView paginateMerk(@PathVariable(name = "page") String page) {
        return itemService.paginateMerk(page);
    }
    /*******************************************************************************/


    /**
     * ITEM COLOR
     **/
    @RequestMapping(value = "/color", method = GET)
    public ModelAndView indexColor() {return itemService.indexColor();}

    @RequestMapping(value = "/color/create", method = GET)
    public ModelAndView createColor() {return itemService.createColor();}

    @RequestMapping(value = "/color/create", method = POST)
    public ModelAndView storeColor(@ModelAttribute("merk") SubItem color) {return itemService.storeColor(color);}

    @RequestMapping(value = "/color/{id}/edit", method = GET)
    public ModelAndView editColor(@PathVariable("id") String id) {return itemService.editColor(id);}

    @RequestMapping(value = "/color/{id}/edit", method = POST)
    public ModelAndView updateColor(@ModelAttribute("merk") SubItem color) {return itemService.updateColor(color);}

    @RequestMapping(value = "/color/page/{page}", method = GET)
    public ModelAndView paginateColor(@PathVariable(name = "page") String page) {return itemService.paginateColor(page);}
    /*******************************************************************************/


}