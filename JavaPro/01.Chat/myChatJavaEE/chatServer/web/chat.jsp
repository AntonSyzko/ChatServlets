<%--
  Created by IntelliJ IDEA.
  User: kaeltas
  Date: 16.12.14
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.login eq null}">
  <c:redirect url="/login"></c:redirect>
</c:if>
<html>
<head>
  <title>Java Servlet Chat</title>

  <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">

  <script>
    function setUser(userName) {
      $("#touser").val(userName);
    }

    function getUsers() {
      $.ajax({
        url: "/getusers",
        dataType: "json"
      }).done(function (data) {
        $("#users").html("");
        for (var i = 0; i < data.users.length; i++) {
          var userName = data.users[i];
          $("#users").append("<tr><td onclick=\"setUser('"+userName+"');\">" + userName + "</td></tr>");
        }
      });
    }

    //refresh users every 5 seconds
    getUsers();
    setInterval(getUsers(), 5000);

    var fromN = 0;
    $("#messages").html("");

    function getMessages() {
      $.ajax({
        url: "/getmessages",
        data: "fromn=" + fromN,
        dataType: "json"
      }).done(function (data) {
        for (var i = 0; i < data.msgList.length; i++) {
          var msg = data.msgList[i];
          if (msg.to == "") {
            $("#messages").append("<small>" + "[" + msg.date + "] " + msg.from + ">>> " + msg.text + "</small><br>");
          } else {
            $("#messages").append("<small style=\"color:blueviolet;\"><em>" + "[" + msg.date + "] {private} " + msg.from + " -> " + msg.to + ">>> " + msg.text + "</em></small><br>");
          }
        }
        var fromNnew = data.lastProcessedMessageNum;
        if (fromN != fromNnew) {
          $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }
        fromN = fromNnew;
      });
    }

    //refresh messages every 2 seconds
    getMessages();
    setInterval(function () {
      getMessages();
    }, 2000);

  </script>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="col-sm-offset-1 col-sm-7">
      <h3>Hi, <%= session.getAttribute("login") %>! <a class="small" href="/logout">(Logout)</a></h3>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-1"></div>
    <div class="col-sm-7">
      <h4>Chat-chat-chatting</h4>
      <table class="table table-bordered" style="width: 100%">
        <tr>
          <td style="vertical-align: top">
            <div id="messages" class="pre-scrollable" style="height: 350px; max-height: 350px">
              <!-- ajax will insert data here -->
            </div>
          </td>
        </tr>
      </table>


      <form id="sendMessage">
        <div class="form-group">
          <input id="touser" class="form-control" type="text" placeholder="To user"><br>
          <input id="msgtext" class="form-control" type="text" placeholder="Message"><br>
          <button class="btn btn-primary" type="submit">Send</button>
        </div>
      </form>

      <script>
        $("#sendMessage").submit(function(ev) {
          ev.preventDefault(); //prevent default action -> disable page refresh
          var touser = $("#touser").val();
          var msgtext = $("#msgtext").val();
          $.ajax({
            type: "POST",
            url: "/add",
            data: "{\"to\":\""+touser+"\",\"text\":\""+msgtext+"\"}"
          }).done(function (data) {
            //clear msgtext field
            $("#msgtext").val("");
          });
        });
      </script>
    </div>

    <div class="col-sm-3">
      <h4>Users</h4>
      <table class="table-bordered" style="width: 100%">
        <tr>
          <td style="vertical-align: top; padding: 8px;">
            <div class="pre-scrollable" style="height:350px; max-height: 350px">
            <table id="users" class="table table-hover">
              <!-- ajax will insert data here -->
            </table>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <div class="col-sm-1"></div>
  </div>
</div>

</body>
</html>
