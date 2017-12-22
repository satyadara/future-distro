package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.discount.Interface.DiscountInterface;
import com.blibli.distro_pos.Model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DiscountService {
    private final DiscountInterface discountInterface;
    private static final String STORE = "store";
    private static final String UPDATE = "update";

    @Autowired
    public DiscountService(DiscountInterface discountInterface) {
        this.discountInterface = discountInterface;
    }

    public ModelAndView index() {
        ModelAndView modelAndView = fetch(1);
        return modelAndView;
    }

    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount = new Discount();
        modelAndView.addObject("discount", discount);
        return modelAndView;
    }

    public ModelAndView store(Discount discount, Authentication authentication) {
        discount.setId_disc("TEST");
        discount.setUsername(authentication.getName());
        ModelAndView modelAndView = validateAndExecution(discount, STORE);
        return modelAndView;
    }

    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount;
        try {
            discount = discountInterface.getOne(id);
            modelAndView.addObject("discount", discount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView update(Discount discount) {
        ModelAndView modelAndView = validateAndExecution(discount, UPDATE);

        return modelAndView;
    }

    public ModelAndView delete(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            discountInterface.softDelete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView active(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("Referer"));
        try {
            discountInterface.setActive(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView paginate(int page) {
        ModelAndView modelAndView = fetch(page);
        return modelAndView;
    }

    public ModelAndView search(String key, int page) {
        ModelAndView modelAndView = new ModelAndView("discount/index");
        Map<String, Object> map;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            map = discountInterface.search(key, page);
            count = (int) map.get("count");
            pageCount = (count / 10) + 1;
            modelAndView.addObject("discounts", map.get(discountInterface.getListString()));
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
        ModelAndView modelAndView = new ModelAndView("discount/index");
        List<Discount> discountList;
        int count;
        int pageCount;
        int currentPage = page;
        try {
            discountList = discountInterface.paginate(currentPage);
            count = discountInterface.count();
            pageCount = (count / 10) + 1;
            modelAndView.addObject("discounts", discountList);
            modelAndView.addObject("count", count);
            modelAndView.addObject("currentPage", currentPage);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("search", false);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    public ModelAndView validateAndExecution(Discount discount, String action) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> errors = new ArrayList<>();
        Map<String, String> errors2 = new HashMap<>();
        try {
            Date startDate = simpleDateFormat.parse(discount.getStart_date());
            Date endDate = simpleDateFormat.parse(discount.getEnd_date());
            if (!endDate.before(startDate) && discount.getPercentage() <= 100 && action.equals(STORE)) {
                discountInterface.save(discount);
            } else if (!endDate.before(startDate) && discount.getPercentage() <= 100 && action.equals(UPDATE)) {
                discountInterface.update(discount);
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
                } else if (discount.getPercentage() <= 0)    {

                    errors.add("Persentase tidak boleh kurang atau sama dengan dari 0%");
                    errors2.put("percentage", "Persentase tidak boleh kurang atau sama dengan dari 0%");
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
