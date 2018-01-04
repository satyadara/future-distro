package com.blibli.distro_pos.DAO.user.Interface;

import com.blibli.distro_pos.Model.user.Role;
import com.blibli.distro_pos.Model.user.User;

import java.util.List;

public interface UserInterface {
    int insertUser(User user, Role role);
    int insertUserRole(String username, String role);

    int editUser(User user, Role role);

    int editUserWithoutPassword(User user, Role role);

    int editUserRole(String username, String role);

    List<User> getAllUser();

    User getUserByUsername(String username);

    String getUserRoleByUsername(String username);

    int deleteUser(String username);
}
