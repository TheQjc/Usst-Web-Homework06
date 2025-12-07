package com.usst.servlet;

import com.usst.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    /**
     * 检查用户是否已登录
     * @param request 请求对象
     * @return 是否已登录
     */
    protected boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }

    /**
     * 获取当前登录用户
     * @param request 请求对象
     * @return 当前登录用户
     */
    protected User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    /**
     * 重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    protected void redirectToLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    /**
     * 检查用户是否已登录，未登录则重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @return 是否已登录
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    protected boolean checkLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        if (!isLoggedIn(request)) {
            redirectToLogin(request, response);
            return false;
        }
        return true;
    }
}