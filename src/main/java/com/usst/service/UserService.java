package com.usst.service;

import com.usst.dao.UserDAO;
import com.usst.entity.User;
import com.usst.util.PasswordUtil;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 注册是否成功
     */
    public boolean register(String username, String password, String email) {
        try {
            // 检查用户名是否已存在
            if (userDAO.findByUsername(username) != null) {
                return false;
            }
            
            // 创建新用户
            User user = new User(username, PasswordUtil.encrypt(password), email);
            return userDAO.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象，失败返回null
     */
    public User login(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);
            if (user != null && PasswordUtil.verify(password, user.getPassword())) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户对象
     */
    public User getUserById(Integer id) {
        try {
            return userDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}