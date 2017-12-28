package com.blibli.distro_pos.Service;

import com.blibli.distro_pos.DAO.ledger.Interface.LedgerInterface;
import com.blibli.distro_pos.DAO.summary.Interface.SummaryInterface;
import com.blibli.distro_pos.DAO.user.UserDao;
import com.blibli.distro_pos.Model.ledger.Ledger;
import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;
import com.blibli.distro_pos.Model.user.Role;
import com.blibli.distro_pos.Model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public ModelAndView indexUser() {
        List<User> userList = UserDao.getAllUser();
        return new ModelAndView("manager/view_user", "userList", userList);
    }

    public ModelAndView createUser() {
        return new ModelAndView("manager/add_user");
    }

    public ModelAndView storeUser(User user, Role role) {

        user.setEnabled(true);

        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());
        int status = UserDao.insertUser(user, role);

        if (status == 1) {
            System.out.println("BERHASIL");
            return new ModelAndView("redirect:/user");
        } else {
            System.out.println("GAGAL!");
            return new ModelAndView("redirect:/user");
        }
    }

    public ModelAndView editUser(String username) {
        User user = UserDao.getUserByUsername(username);
        String role = UserDao.getUserRoleByUsername(user.getUsername());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager/edit_user");
        mav.addObject("user", user);
        mav.addObject("role", role);

        return mav;
    }

    public ModelAndView updateUser(User user, Role role) {
        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());

        int status;

        try {
            if (user.getPassword() == "") {
                status = UserDao.editUserWithoutPassword(user, role);
                System.out.println("password:" + user.getPassword());
            } else {
                status = UserDao.editUser(user, role);
            }
            if (status == 1) {
                System.out.println("Berhasil edit user");
                return new ModelAndView("redirect:/user");
            } else {
                System.out.println("Gagal edit user" + status);
                return new ModelAndView("redirect:/user");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }

    }

    public ModelAndView deleteUser(String username) {
        int status = UserDao.deleteUser(username);
        if (status == 1) {
            System.out.println("User with username: " + username + " is deleted");
            return new ModelAndView("redirect:/user");
        } else {
            System.out.println("Failed to delete " + username);
            return new ModelAndView("redirect:/user");
        }
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
        modelAndView.addObject("search", false);
        modelAndView.addObject("totalIn", "-");
        modelAndView.addObject("totalOut", '-');
        modelAndView.addObject("totalEnd", "-");
        return modelAndView;
    }

    public ModelAndView managerLedgerBetween(int page, String date_from, String date_to) {
        ModelAndView modelAndView = new ModelAndView("ledger/index");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        try {
            List<Ledger> ledgers = ledgerInterface.getFilter(date_from, date_to, (page - 1) * 10);
            int ledgerCount = ledgerInterface.count(date_from, date_to);
            double totalIn = ledgerInterface.getTotalIn(date_from, date_to);
            double totalOut = ledgerInterface.getTotalOut(date_from, date_to);
            int pageCount = (ledgerCount / 10) + 1;
            modelAndView.addObject("ledgers", ledgers);
            modelAndView.addObject("date_from", date_from);
            modelAndView.addObject("date_to", date_to);
            modelAndView.addObject("count", ledgerCount);
            modelAndView.addObject("pages", pageCount);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("search", true);
            modelAndView.addObject("totalIn", totalIn);
            modelAndView.addObject("totalOut", totalOut);
            modelAndView.addObject("totalEnd", totalIn - totalOut);
        } catch (Exception e) {
            System.out.println("#CTRLR PAGINATE# something error : " + e.toString());
        }
        return modelAndView;
    }

    public String chartJson()   {
        String data = summaryInterface.getChartByMonth();
        return data;
    }
}
