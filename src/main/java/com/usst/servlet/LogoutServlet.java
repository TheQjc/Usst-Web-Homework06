package com.usst.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 销毁Session，实现用户退出
        HttpSession session = request.getSession();
        session.invalidate();
        
        // 重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}