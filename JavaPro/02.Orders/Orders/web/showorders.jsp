<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 23.12.14
  Time: 1:59
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
        <a href="/adminpanel">[Назад]</a>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-offset-2 col-sm-8">
        <h4>Заказы</h4>
        <table class="table">
          <thead>
            <tr>
              <th>Дата</th>
              <th>Пользователь</th>
              <th>Заказ №</th>
              <th>Товары</th>
              <th>Удалить</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${orderList}" var="order">
              <tr>
                <td>${order.datetime}</td>
                <td>${order.login}</td>
                <td>${order.id}</td>

                <td>
                  <c:forEach items="${order.orderData}" var="product">
                    ${product.name} ${product.price}<br>
                  </c:forEach>
                </td>
                <td><a href="/deleteorder?orderid=${order.id}"><span class="glyphicon glyphicon-minus"/></a></td>
              </tr>
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
