<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Here is the information about the group: ${groupToShow.name}</h1>
<p>Creator: ${groupToShow.creator.firstName}</p>
<p>Description: ${groupToShow.description}</p>
<p>Vice President: ${groupToShow.vp.firstName}</p>
<p>Group Members: ${groupToShow.members}</p>
<h3>Members</h3>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Number of groups the person belongs to</th>
    </tr>
  
  </thead>
  <tbody>
    <c:forEach items = "${groupToShow.members}" var= "user">
    <tr>
      <td>${user.firstName}</td>
      <td>${user.groupsBelongTo.size()}</td>
    </tr>
   </c:forEach>
  </tbody>
</table>

<a href="/edit/${groupToShow.id}"><button class = "btn btn-warning">Edit</button></a>
<a href="/delete/${groupToShow.id}"><button class = "btn btn-danger" >Delete</button></a>

</body>
</html>