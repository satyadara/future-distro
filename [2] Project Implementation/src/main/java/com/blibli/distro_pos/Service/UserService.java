package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.ledger.Interface.LedgerInterface;
import com.blibli.distro_pos.DAO.summary.Interface.SummaryInterface;
import com.blibli.distro_pos.Model.ledger.Ledger;
import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class UserService {
    private final SummaryInterface summaryInterface;
    private final LedgerInterface ledgerInterface;

    @Autowired
    public UserService(SummaryInterface summaryInterface, LedgerInterface ledgerInterface) {
        this.summaryInterface = summaryInterface;
        this.ledgerInterface = ledgerInterface;
    }

    public ModelAndView index(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication != null) {
            if (authentication.getAuthorities().toString().equals("[MANAGER]"))
                modelAndView.setViewName("redirect:/dashboard");
            else if (authentication.getAuthorities().toString().equals("[CASHIER]"))
                modelAndView.setViewName("redirect:/cashier");
        } else {
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    public ModelAndView managerDashboard(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("manager/admin");
        String username = authentication.getName();
        List<MostSoldItem> mostSoldItemsMonth = summaryInterface.getMostSoldItemThisMonth();
        List<MostSoldItem> mostSoldItemsYear = summaryInterface.getMostSoldItemThisYear();
        List<LoyalCustomer> loyalCustomersMonth = summaryInterface.getLoyalCustomerThisMonth();
        List<LoyalCustomer> loyalCustomersYear = summaryInterface.getLoyalCustomerThisYear();

        modelAndView.addObject("username", username);
        modelAndView.addObject("items_month", mostSoldItemsMonth);
        modelAndView.addObject("items_year", mostSoldItemsYear);
        modelAndView.addObject("customers_month", loyalCustomersMonth);
        modelAndView.addObject("customers_year", loyalCustomersYear);

        return modelAndView;
    }

    public ModelAndView cashierDashboard(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("cashier/dashboard");
        String username = authentication.getName();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    public ModelAndView managerLedger() {
        ModelAndView modelAndView = new ModelAndView("ledger/index");
        List<Ledger> ledgers = ledgerInterface.getIndex();
        modelAndView.addObject("ledgers", ledgers);
        return modelAndView;
    }

    public ModelAndView managerLedgerBetween(int page, String date_from, String date_to) {
        ModelAndView modelAndView = new ModelAndView("ledger/index");
        try {
            List<Ledger> ledgers = ledgerInterface.getFilter(date_from, date_to, (page-1)*10);
            modelAndView.addObject("ledgers", ledgers);
            modelAndView.addObject("date_from", date_from);
            modelAndView.addObject("date_to", date_to);
        } catch (Exception e) {
            System.out.println("#CTRLR PAGINATE# something error : " + e.toString());
        }
        return modelAndView;
    }
}
