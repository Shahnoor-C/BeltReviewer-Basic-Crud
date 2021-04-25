<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form:form action="/groups/create" method="post" modelAttribute="group">
    <p>
        <form:label path="name">Name</form:label>
        <form:errors path="name"/>
        <form:input path="name"/>
    </p>
    <p>
        <form:label path="description">Description</form:label>
        <form:errors path="description"/>
        <form:textarea path="description"/>
    </p>
   
   <p>
   <form:label path="vp">Select the VP:</form:label>
   <form:errors path="vp"/>
   <form:select path = "vp">
   	<c:forEach items="${allusers}" var = "u">
   	<option value="${u.id}">${u.firstName}</option>
   	</c:forEach>
   </form:select>
   
   
   
   </p>   
    <input type="submit" value="Submit"/>
</form:form>

</body>
</html>