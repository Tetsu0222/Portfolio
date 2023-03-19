<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="UTF-8">

<head>                                                                                
    <title>ログイン画面</title>                                                         
</head>                                                                               
    <body>                                                                                
     	<h1>ログイン</h1>
     	<form action="<%=request.getContextPath()%>/ExecuteLogin" method="post">                                   
    		<p>ユーザーID：<br>                                                               
    			<input type="text" name="USER_ID" maxlength="20" id="ID_USER_ID">       
    		</p>                                                                              
    		<p>パスワード：<br>                                                               
    			<input type="password" name="PASSWORD" maxlength="20" id="ID_PASSWORD"> 
    		</p>                                                                              
    			<input type="submit" value="ログイン" id="ID_SUBMIT">
    			<li><a href="index.html">Top</a></li>                       
    	</form>                                                                             
    	<script type="text/javascript" src="login.js"></script>                      
    </body>                                                                               
</html>