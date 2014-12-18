<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Java ServletBased Chat</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <div class="row"  style="margin-top: 10%;">
    <div class="col-sm-3 col-md-4"></div>
    <div class="col-sm-6 col-md-4">
      <h3 class="text-center">Java Servlet Chat</h3>


      <c:if test="${requestScope.errorAuth ne null}">
        <div class="alert alert-danger" role="alert">
          <strong>Error: </strong><c:out value="${requestScope.errorAuth}"></c:out>
        </div>
      </c:if>
      <form class="form-horizontal" action="/login" method="POST">
        <div class="form-group">
          <label for="inputLogin" class="control-label col-sm-3">Login</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="inputLogin" placeholder="Enter login" name="login" value="${requestScope.lastLogin}">
          </div>
        </div>
        <div class="form-group">
          <label for="inputPassword" class="control-label col-sm-3">Password</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" id="inputPassword" placeholder="Enter password" name="password">
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-3 col-sm-9">
            <button type="submit" class="btn btn-primary">Sign in/Sign up</button>
          </div>
        </div>
      </form>

      <div class="bg-info" style="padding: 8px;">
        <h4><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> Howto</h4>
        <p>It is very simple!</p>
        <p><strong>Sign in</strong> - enter your login/password and enjoy chatting!</p>
        <p><strong>Sign up</strong> - just enter your unique new login/password and enjoy chatting!</p>
        <p>Some demo credentials: </p>
        <table class="table small">
          <tr><td>Login</td><td>Password</td></tr>
          <tr><td>user1</td><td>user1</td></tr>
          <tr><td>user2</td><td>user2</td></tr>
          <tr><td>user3</td><td>user3</td></tr>
        </table>
      </div>
    </div>
    <div class="col-sm-3 col-md-4"></div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>