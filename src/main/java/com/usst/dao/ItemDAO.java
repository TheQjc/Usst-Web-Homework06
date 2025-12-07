package com.usst.dao;

import com.usst.entity.Item;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    
    public Item findById(Integer id) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i JOIN users u ON i.user_id = u.id WHERE i.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setUserId(rs.getInt("user_id"));
                    item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    item.setUsername(rs.getString("username"));
                    return item;
                }
            }
        }
        return null;
    }

    public List<Item> findAll() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, u.username FROM items i JOIN users u ON i.user_id = u.id ORDER BY i.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setUserId(rs.getInt("user_id"));
                item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                item.setUsername(rs.getString("username"));
                items.add(item);
            }
        }
        return items;
    }

    public List<Item> findByUserId(Integer userId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, u.username FROM items i JOIN users u ON i.user_id = u.id WHERE i.user_id = ? ORDER BY i.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setUserId(rs.getInt("user_id"));
                    item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    item.setUsername(rs.getString("username"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public List<Item> searchByName(String keyword) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, u.username FROM items i JOIN users u ON i.user_id = u.id WHERE i.name LIKE ? ORDER BY i.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setUserId(rs.getInt("user_id"));
                    item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    item.setUsername(rs.getString("username"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public boolean insert(Item item) throws SQLException {
        String sql = "INSERT INTO items (name, description, price, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setBigDecimal(3, item.getPrice());
            stmt.setInt(4, item.getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean update(Item item) throws SQLException {
        String sql = "UPDATE items SET name = ?, description = ?, price = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setBigDecimal(3, item.getPrice());
            stmt.setInt(4, item.getId());
            stmt.setInt(5, item.getUserId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(Integer id, Integer userId) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }
}