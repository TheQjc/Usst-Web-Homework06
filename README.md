# 账号/密码
username: qiao
password: 1234
可注册

# 二手物品交易平台

这是一个基于Java Web技术构建的简单二手物品交易平台。

## 功能特点

1. 用户注册和登录功能（密码加密存储）
2. 物品发布功能
3. 物品搜索功能（支持模糊匹配）
4. 物品管理功能（编辑和删除）
5. 基于MVC架构设计

## 技术栈

- 后端：Java Servlet/JSP
- 前端：原生JavaScript、HTML、CSS
- 数据库：MySQL
- 构建工具：Maven
- 加密：Apache Commons Codec (SHA-256)

## 数据库设计

系统包含两个主要的数据表：

### 用户表 (users)
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 物品表 (items)
```sql
CREATE TABLE items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/usst/
│   │       ├── entity/      # 实体类
│   │       ├── dao/         # 数据访问对象
│   │       ├── service/     # 业务逻辑层
│   │       ├── servlet/     # 控制器
│   │       └── util/        # 工具类
│   └── resources/
└── web/
    ├── WEB-INF/
    │   └── web.xml         # 配置文件
    └── *.jsp               # 视图页面
```

## 功能模块

### 1. 用户管理模块
- 用户注册：[/register](src/main/java/com/usst/servlet/RegisterServlet.java)
- 用户登录：[/login](src/main/java/com/usst/servlet/LoginServlet.java)
- 用户退出：[/logout](src/main/java/com/usst/servlet/LogoutServlet.java)

### 2. 物品管理模块
- 物品展示：[/item](src/main/java/com/usst/servlet/ItemServlet.java)
- 发布物品：[/item/add](src/main/java/com/usst/servlet/ItemServlet.java)
- 搜索物品：[/item/search](src/main/java/com/usst/servlet/ItemServlet.java)
- 编辑物品：[/item/edit/{id}](src/main/java/com/usst/servlet/ItemServlet.java)
- 删除物品：[/item/delete/{id}](src/main/java/com/usst/servlet/ItemServlet.java)
- 我的物品：[/item/my](src/main/java/com/usst/servlet/ItemServlet.java)

## 部署说明

1. 创建MySQL数据库：
   ```sql
   CREATE DATABASE secondhand_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. 执行上述数据表创建语句

3. 修改 [DatabaseConnection.java](src/main/java/com/usst/dao/DatabaseConnection.java) 中的数据库连接信息：
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/secondhand_platform?useSSL=false&serverTimezone=UTC";
   private static final String USERNAME = "username";
   private static final String PASSWORD = "password";
   ```

4. 使用Maven构建项目：
   ```bash
   mvn clean package
   ```

5. 将生成的WAR包部署到Tomcat等Servlet容器中

## 安全特性

- 密码使用SHA-256算法加密存储
- 用户会话管理
- 权限控制（只能编辑/删除自己发布的物品）
