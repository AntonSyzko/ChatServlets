<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 22.12.14
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
      Приветствую, ${sessionScope.login}!
      <c:if test="${sessionScope.isAdmin eq true}">
        <a href="/adminpanel">[Панель администратора]</a>
      </c:if>
      <a href="/logout">[Выход]</a>
      <h4>Интернет магазин</h4>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-offset-2 col-sm-3">
      <h4>Корзина</h4>

      <c:choose>
        <c:when test="${empty shoppingCartMap}">
          [пусто]
        </c:when>
        <c:otherwise>
          <table class="table">
            <thead>
            <tr>
              <th>№</th>
              <th>Название</th>
              <th>Кол</th>
              <th>Цена</th>
              <th></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="counter" value="1"></c:set>
            <c:set var="sum" value="0"></c:set>
            <c:forEach items="${shoppingCartMap}" var="product">
              <tr>
                <td>${counter}</td>
                <td>${product.key.name}</td>
                <td>${product.value}</td>
                <td>
                  <fmt:setLocale value="en_US"/>
                  <fmt:formatNumber maxFractionDigits="2" value="${product.key.price*product.value}"/>
                </td>
                <td><a href="/deletefromshoppingcart?productindex=${counter}"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></a></td>
              </tr>
              <c:set var="counter" value="${counter+1}"></c:set>
              <c:set var="sum" value="${sum+product.key.price*product.value}"></c:set>
            </c:forEach>
            <tr>
              <td></td>
              <td>Всего</td>
              <td></td>
              <td>
                <fmt:setLocale value="en_US"/>
                <fmt:formatNumber maxFractionDigits="2" value="${sum}"/></td>
              <td></td>
            </tr>
            </tbody>
          </table>
          <form action="/commitorder" method="GET">
            <button type="submit" class="btn btn-primary">Подтвердить заказ</button>
          </form>
        </c:otherwise>
      </c:choose>
    </div>


    <div class="col-sm-5">
      <h4>Список доступных товаров</h4>
      <table class="table">
        <thead>
        <tr>
          <th>№</th>
          <th>Название</th>
          <th>Цена</th>
          <th>В корзину</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="counter" value="1"></c:set>
        <c:forEach items="${productList}" var="product">
          <tr>
            <td>${counter}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td><a href="/addtoshoppingcart?productid=${product.id}"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a></td>
          </tr>
          <c:set var="counter" value="${counter+1}"></c:set>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>


<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
