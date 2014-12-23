<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 22.12.14
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.isAdmin ne true}">
  <c:redirect url="/"/>
</c:if>
<html>
<head>
  <title></title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container">
  <div class="row">
    <div class="col-sm-offset-2 col-sm-8">
      Приветствую, ${sessionScope.login}! <a href="/userpanel">[Панель пользователя]</a> <a href="/logout">[Выход]</a>
      <h4>Панель администратора. Интернет магазин</h4>
      <a href="/addproduct">> Управление товарами</a><br>
      <a href="/showorders">> Просмотр/Удаление заказов</a>

    </div>
  </div>
</div>


<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
