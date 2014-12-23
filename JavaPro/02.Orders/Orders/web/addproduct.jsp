<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 22.12.14
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.isAdmin ne true}">
  <c:redirect url="/"/>
</c:if>
<html>
<head>
  <title>Добавить товар</title>
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
        <div class="col-sm-offset-4 col-sm-4">
          <h4>Добавить товар</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-offset-4 col-sm-4">
          <form action="addproduct" method="POST">
            <div class="form-group">
              <label for="productname">Название</label>
              <input type="text" id="productname" name="productname" class="form-control" placeholder="Введите название" value="${productname}">
            </div>
            <div class="form-group">
              <label for="productprice">Цена</label>
              <input type="text" id="productprice" name="productprice" class="form-control" placeholder="Введите цену" value="${productprice}">
              <c:if test="${not empty errorprice}">
                <div class="alert-danger">
                  <strong>Ошибка!</strong> ${errorprice}
                </div>
              </c:if>
            </div>

            <button type="submit" class="btn btn-primary">Добавить товар</button>
          </form>
        </div>
      </div>
      <div class="row"><br></div>
      <div class="row">
        <div class="col-sm-offset-2 col-sm-8">
          <h4>Текущий список товаров</h4>
          <table class="table">
            <thead>
              <tr>
                <th>№</th>
                <th>Название</th>
                <th>Цена</th>
                <th>Удалить</th>
              </tr>
            </thead>
            <tbody>
              <c:set var="counter" value="1"></c:set>
              <c:forEach items="${productList}" var="product">
                <tr>
                  <td>${counter}</td>
                  <td>${product.name}</td>
                  <td>${product.price}</td>
                  <td><a href="/deleteproduct?productid=${product.id}"><span class="glyphicon glyphicon-minus"/></a> </td>
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
