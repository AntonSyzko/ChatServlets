<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 23.12.14
  Time: 1:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <title></title>
</head>
<body>

  <div class="container">
    <div class="row">
      <div class="col-sm-offset-4 col-sm-4">
        <h3>Заказ успешно оформлен</h3>
        Вы будете автоматически перенаправлены на главную страницу через несколько секунд<br/>
        <a href="/">Главная</a>
      </div>
    </div>
  </div>

  <script type="text/javascript"><!--

    function Redirect() {
      location.href = '/';
    }
    setTimeout(Redirect, 4000);
  // --></script>
  <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
