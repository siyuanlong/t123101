<%--
  Created by syl
  User: Administrator
  Date: 2018/12/31
  Time: 15:02
  专业找bug
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <form action="/uploadfile" method="post" enctype="multipart/form-data">
            文件:<input type="file" name="myfile"/>
                 <input type="submit" name="保存"/>
        </form>
</body>
</html>
