package com.usst.servlet;

import com.usst.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 如果用户已登录，重定向到主页
        if (isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        
        // 验证参数
        if (username == null || username.isEmpty() || 
            password == null || password.isEmpty() || 
            confirmPassword == null || confirmPassword.isEmpty() ||
            email == null || email.isEmpty()) {
            request.setAttribute("error", "所有字段都是必填的");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "两次输入的密码不一致");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // 用户注册
        boolean success = userService.register(username, password, email);
        if (success) {
            // 注册成功，重定向到登录页面
            request.setAttribute("message", "注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // 注册失败
            request.setAttribute("error", "注册失败，用户名可能已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}