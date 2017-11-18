package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.discount.DiscountDAO;
import com.blibli.distro_pos.Model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/discount")
public class DiscountController {
    private DiscountDAO discountDAO;
    private final String STORE = "store";
    private final String UPDATE = "update";

    @Autowired
    public DiscountController(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = fetch(1);
        return modelAndView;
    }


    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount = new Discount();
        modelAndView.addObject("discount", discount);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("discount") Discount discount) {
        discount.setId_disc("TEST");
        discount.setId_emp("EMP-1002");
        ModelAndView modelAndView = validateAndExecution(discount, STORE);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount;
        try {
            discount = discountDAO.getOne(id);
            modelAndView.addObject("discount", discount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("discount") Discount discount) {
        ModelAndView modelAndView = validateAndExecution(discount, UPDATE);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            discountDAO.softDelete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable("id") String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            discountDAO.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    @RequestMapping(value = "/search/page/{page}", method = GET)
    public ModelAndView search(@RequestParam("key") String key,@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("discount/index");
        Map<String, Object> map;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            map = discountDAO.search(key, page);
            count = (int) map.get("count");
            pageCount = (count / 10) + 1;
            modelAndView.addObject("discounts", map.get("discountList"));
            modelAndView.addObject("count", count);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView fetch(int page) {
        ModelAndView modelAndView = new ModelAndView("discount/index");
        List<Discount> discountList;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            discountList = discountDAO.paginate(currentPage);
            count = discountDAO.count();
            pageCount = (count / 10) + 1;
            modelAndView.addObject("discounts", discountList);
            modelAndView.addObject("count", count);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    private ModelAndView validateAndExecution(Discount discount, String action) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> errors = new ArrayList<>();
        Map<String, String> errors2 = new HashMap<>();
        try {
            Date startDate = simpleDateFormat.parse(discount.getStart_date());
            Date endDate = simpleDateFormat.parse(discount.getEnd_date());
            if (!endDate.before(startDate) && discount.getPercentage() <= 100 && action.equals(STORE)) {
                discountDAO.save(discount);
            } else if (!endDate.before(startDate) && discount.getPercentage() <= 100 && action.equals(UPDATE)) {
                discountDAO.update(discount);
            } else {
                modelAndView.setViewName("discount/form");
                modelAndView.addObject("discount", discount);
                if (endDate.before(startDate)) {
                    errors.add("Tanggal selesai tidak boleh kurang dari tanggal mulai !");
                    errors2.put("date", "Tanggal selesai tidak boleh kurang dari tanggal mulai !");
                }
                if (discount.getPercentage() > 100) {
                    errors.add("Persentase tidak boleh lebih dari 100.00%");
                    errors2.put("percentage", "Persentase tidak boleh lebih dari 100.00%");
                }
                modelAndView.addObject("errors", errors);
                modelAndView.addObject("errors2", errors2);
            }

        } catch (Exception e) {
            System.out.println("something error : ");
        }
        return modelAndView;
    }

}
