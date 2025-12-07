package com.usst.service;

import com.usst.dao.ItemDAO;
import com.usst.entity.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    /**
     * 发布物品
     * @param item 物品对象
     * @return 是否发布成功
     */
    public boolean addItem(Item item) {
        try {
            return itemDAO.insert(item);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有物品
     * @return 物品列表
     */
    public List<Item> getAllItems() {
        try {
            return itemDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据用户ID获取物品
     * @param userId 用户ID
     * @return 物品列表
     */
    public List<Item> getItemsByUserId(Integer userId) {
        try {
            return itemDAO.findByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据ID获取物品
     * @param id 物品ID
     * @return 物品对象
     */
    public Item getItemById(Integer id) {
        try {
            return itemDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜索物品（按名称模糊匹配）
     * @param keyword 关键词
     * @return 物品列表
     */
    public List<Item> searchItems(String keyword) {
        try {
            return itemDAO.searchByName(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新物品信息
     * @param item 物品对象
     * @return 是否更新成功
     */
    public boolean updateItem(Item item) {
        try {
            return itemDAO.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除物品
     * @param id 物品ID
     * @param userId 用户ID
     * @return 是否删除成功
     */
    public boolean deleteItem(Integer id, Integer userId) {
        try {
            return itemDAO.delete(id, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}