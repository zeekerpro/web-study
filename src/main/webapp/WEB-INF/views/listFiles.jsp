<%--
  Created by zeeker
  Date: 1/30/18
  Time: 12:04 AM
  Description: 显示上传的所有图片
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>fileLists</title>
</head>
<body>
    <c:forEach items="${requestScope.files}" var="file">
        <c:url value="/file/download" var="fileUrl">
            <c:param name="fileName" value="${file.name}"></c:param>
        </c:url>
        ${file.name} <a href="${pageScope.fileUrl}">下载</a>
    </c:forEach>
</body>
</html>
