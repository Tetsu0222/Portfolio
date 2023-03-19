<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>メッセージ一覧</title>
  </head>
  <body>
    <h2>メッセージ一覧</h2>
    <table class="surbey_list" border=1>
      <tr bgcolor="#c0c0c0">
        <th>名前</th>
        <th>Email</th>
        <th>ご意見・ご感想</th>
        <th>回答時間</th>
      </tr>

<c:forEach var="dto" items="${shinrinDtoList}">
      <tr>
        <td><c:out value="${ dto.name    }" /></td>
        <td><c:out value="${ dto.email   }" /></td>
        <td><c:out value="${ dto.message }" /></td>
        <td><c:out value="${ dto.time    }" /></td>
      </tr>
</c:forEach>
    </table>
    <br>
    <form action="<%=request.getContextPath()%>/ExecuteLogout" method="post">
    <input type="submit" class="button" value="ログアウト" id="ID_SUBMIT">
    </form>
  </body>
</html>