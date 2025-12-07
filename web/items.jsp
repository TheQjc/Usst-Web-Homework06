<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>物品列表 - 二手物品交易平台</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        header {
            background-color: #333;
            color: white;
            padding: 1em 0;
            text-align: center;
        }
        nav {
            background-color: #666;
            overflow: hidden;
        }
        nav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        nav a:hover {
            background-color: #ddd;
            color: black;
        }
        .search-bar {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .search-bar form {
            display: flex;
            gap: 10px;
        }
        .search-bar input[type="text"] {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-bar button {
            padding: 10px 20px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .items {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .item-card {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .item-name {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .item-price {
            color: #e74c3c;
            font-weight: bold;
            font-size: 1.1em;
            margin-bottom: 10px;
        }
        .item-description {
            color: #666;
            margin-bottom: 10px;
        }
        .item-meta {
            font-size: 0.9em;
            color: #999;
            display: flex;
            justify-content: space-between;
        }
        .no-items {
            text-align: center;
            padding: 40px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .actions {
            margin-top: 10px;
        }
        .actions a {
            display: inline-block;
            margin-right: 10px;
            padding: 5px 10px;
            background-color: #333;
            color: white;
            text-decoration: none;
            border-radius: 3px;
            font-size: 0.9em;
        }
        .actions a:hover {
            background-color: #555;
        }
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 1em 0;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <header>
        <h1>二手物品交易平台</h1>
    </header>
    
    <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/item/list">浏览物品</a>
        <c:if test="${sessionScope.user != null}">
            <a href="${pageContext.request.contextPath}/item/my">我的物品</a>
            <a href="${pageContext.request.contextPath}/item/add">发布物品</a>
            <a href="${pageContext.request.contextPath}/logout" style="float:right;">退出 (${sessionScope.user.username})</a>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <a href="${pageContext.request.contextPath}/login.jsp" style="float:right;">登录</a>
            <a href="${pageContext.request.contextPath}/register.jsp" style="float:right;">注册</a>
        </c:if>
    </nav>
    
    <div class="container">
        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/item/search" method="post">
                <input type="text" name="keyword" placeholder="搜索物品..." value="${keyword}" />
                <button type="submit">搜索</button>
            </form>
        </div>
        
        <h2>
            <c:choose>
                <c:when test="${myItems}">我的物品</c:when>
                <c:otherwise>所有物品</c:otherwise>
            </c:choose>
        </h2>
        
        <c:if test="${not empty items}">
            <div class="items">
                <c:forEach var="item" items="${items}">
                    <div class="item-card">
                        <div class="item-name">${item.name}</div>
                        <div class="item-price">￥${item.price}</div>
                        <div class="item-description">${item.description}</div>
                        <div class="item-meta">
                            <span>发布者: ${item.username}</span>
                            <span>时间: ${item.createdAt}</span>
                        </div>
                        <c:if test="${myItems}">
                            <div class="actions">
                                <a href="${pageContext.request.contextPath}/item/edit/${item.id}">编辑</a>
                                <a href="${pageContext.request.contextPath}/item/delete/${item.id}" 
                                   onclick="return confirm('确定要删除这个物品吗？')">删除</a>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        
        <c:if test="${empty items}">
            <div class="no-items">
                <h3>暂无物品信息</h3>
                <p>
                    <c:choose>
                        <c:when test="${myItems}">您还没有发布任何物品。</c:when>
                        <c:otherwise>当前没有可浏览的物品。</c:otherwise>
                    </c:choose>
                </p>
                <c:if test="${myItems}">
                    <a href="${pageContext.request.contextPath}/item/add">立即发布</a>
                </c:if>
            </div>
        </c:if>
    </div>
    
    <footer>
        <p>&copy; 2025 二手物品交易平台. usst.</p>
    </footer>
</body>
</html>