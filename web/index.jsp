<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>二手物品交易平台</title>
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
        .welcome {
            text-align: center;
            padding: 20px;
            background-color: white;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .features {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
        }
        .feature {
            background-color: white;
            width: 30%;
            margin: 10px;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
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
        <div class="welcome">
            <h2>欢迎来到二手物品交易平台</h2>
            <p>在这里您可以发布和查找各种二手物品信息</p>
        </div>
        
        <div class="features">
            <div class="feature">
                <h3>发布物品</h3>
                <p>您有不需要的物品？发布信息让别人知道吧！</p>
                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/item/add">立即发布</a>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/login.jsp">请先登录</a>
                </c:if>
            </div>
            
            <div class="feature">
                <h3>查找物品</h3>
                <p>想要购买二手物品？来这里看看有没有你需要的！</p>
                <a href="${pageContext.request.contextPath}/item/list">开始查找</a>
            </div>
            
            <div class="feature">
                <h3>账户管理</h3>
                <p>管理您的个人信息和发布的物品</p>
                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/item/my">我的物品</a>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/login.jsp">请先登录</a>
                </c:if>
            </div>
        </div>
    </div>
    
    <footer>
        <p>&copy; 2025 二手物品交易平台. usst.</p>
    </footer>
</body>
</html>