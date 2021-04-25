<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<h1>Welcome ${loggedinuser.firstName}</h1>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Creator</th>
      <th scope="col">Vice President</th>
       <th scope="col">Number of Members</th>
       <th scope="col">Action</th>
      
    </tr>
  </thead>
  <tbody>
    <c:forEach items = "${allgroups}" var="g">
    <tr>
     
      <td><a href= "/groups/${g.id}">${g.name}</a></td>
      <td>${g.creator.firstName}</td>
      <td>${g.vp.firstName}</td>
      <td>${g.members.size()}</td>
      <td>${g.vp.firstName}</td>
      <td><a href="/">Join</a></td>
      
    </tr>
</c:forEach>


  </tbody>
</table>
<a href="/groups/new"><button class= "btn btn-primary">Add a new group</button></a>
</body>
</html>