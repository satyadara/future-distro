package com.blibli.distro_pos.DAO.user.Interface;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.Model.user.Role;
import com.blibli.distro_pos.Model.user.User;

public interface UserInterface extends BasicDAO<User, String> {

    int insertUser (User user, Role role);
    int insertUserRole(String username, String role);
    int editUser (User user, Role role);
    int editUserWithoutPassword (User user, Role role);
    int editUserRole(String username, String role);
    int deleteUser (String username);
    User getUserByUsername (String username);
    String getUserRoleByUsername (String username);


}
