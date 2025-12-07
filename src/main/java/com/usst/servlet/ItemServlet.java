package com.usst.servlet;

import com.usst.entity.Item;
import com.usst.entity.User;
import com.usst.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/item/*")
public class ItemServlet extends BaseServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 检查用户是否已登录
        if (!checkLogin(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        User currentUser = getCurrentUser(request);
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            // 显示所有物品
            listItems(request, response);
        } else if (pathInfo.equals("/my")) {
            // 显示我的物品
            listMyItems(request, response, currentUser);
        } else if (pathInfo.equals("/add")) {
            // 显示添加物品页面
            showAddForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // 显示编辑物品页面
            try {
                int itemId = Integer.parseInt(pathInfo.substring(6)); // "/edit/".length() = 6
                showEditForm(request, response, itemId, currentUser);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // 删除物品
            try {
                int itemId = Integer.parseInt(pathInfo.substring(8)); // "/delete/".length() = 8
                deleteItem(request, response, itemId, currentUser);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 检查用户是否已登录
        if (!checkLogin(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        User currentUser = getCurrentUser(request);
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/add")) {
            // 添加物品
            addItem(request, response, currentUser);
        } else if (pathInfo.startsWith("/edit/")) {
            // 更新物品
            try {
                int itemId = Integer.parseInt(pathInfo.substring(6)); // "/edit/".length() = 6
                updateItem(request, response, itemId, currentUser);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.equals("/search")) {
            // 搜索物品
            searchItems(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("/items.jsp").forward(request, response);
    }

    private void listMyItems(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        List<Item> items = itemService.getItemsByUserId(user.getId());
        request.setAttribute("items", items);
        request.setAttribute("myItems", true);
        request.getRequestDispatcher("/items.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/add_item.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, int itemId, User user) 
            throws ServletException, IOException {
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 检查物品是否属于当前用户
        if (!item.getUserId().equals(user.getId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        request.setAttribute("item", item);
        request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        // 获取参数
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        
        // 验证参数
        if (name == null || name.isEmpty() || 
            description == null || description.isEmpty() || 
            priceStr == null || priceStr.isEmpty()) {
            request.setAttribute("error", "所有字段都是必填的");
            request.getRequestDispatcher("/add_item.jsp").forward(request, response);
            return;
        }
        
        try {
            BigDecimal price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "价格必须大于0");
                request.getRequestDispatcher("/add_item.jsp").forward(request, response);
                return;
            }
            
            // 创建物品
            Item item = new Item(name, description, price, user.getId());
            boolean success = itemService.addItem(item);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/item/my");
            } else {
                request.setAttribute("error", "发布物品失败");
                request.getRequestDispatcher("/add_item.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "价格格式不正确");
            request.getRequestDispatcher("/add_item.jsp").forward(request, response);
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response, int itemId, User user) 
            throws ServletException, IOException {
        // 获取参数
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        
        // 验证参数
        if (name == null || name.isEmpty() || 
            description == null || description.isEmpty() || 
            priceStr == null || priceStr.isEmpty()) {
            request.setAttribute("error", "所有字段都是必填的");
            request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
            return;
        }
        
        try {
            BigDecimal price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "价格必须大于0");
                request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
                return;
            }
            
            // 获取物品并检查权限
            Item item = itemService.getItemById(itemId);
            if (item == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            if (!item.getUserId().equals(user.getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            
            // 更新物品
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            
            boolean success = itemService.updateItem(item);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/item/my");
            } else {
                request.setAttribute("error", "更新物品失败");
                request.setAttribute("item", item);
                request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "价格格式不正确");
            request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response, int itemId, User user) 
            throws ServletException, IOException {
        boolean success = itemService.deleteItem(itemId, user.getId());
        if (success) {
            response.sendRedirect(request.getContextPath() + "/item/my");
        } else {
            request.setAttribute("error", "删除物品失败");
            listMyItems(request, response, user);
        }
    }

    private void searchItems(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        
        if (keyword != null && !keyword.isEmpty()) {
            List<Item> items = itemService.searchItems(keyword);
            request.setAttribute("items", items);
            request.setAttribute("keyword", keyword);
        } else {
            List<Item> items = itemService.getAllItems();
            request.setAttribute("items", items);
        }
        request.getRequestDispatcher("/items.jsp").forward(request, response);
    }
}