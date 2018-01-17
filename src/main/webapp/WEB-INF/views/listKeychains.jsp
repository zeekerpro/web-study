<%--
  Created by zeeker
  Date: 17/01/2018
  Time: 15:11
  Description: 
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>phone</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="keychain" items="${requestScope.keychains}">
                <tr>
                    <td>${keychain.id}</td>
                    <td>${keychain.name}</td>
                    <td>${keychain.phone}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
