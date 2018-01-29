<%--
  Created by zeeker
  Date: 1/28/18
  Time: 3:29 AM
  Description: 实现文件上传
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>upload</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
        <input type="file" name="file" />
        <input type="submit" value="提交"/>
    </form>

</body>
</html>
