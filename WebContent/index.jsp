<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ page import="java.util.*" %>    
<%
String ctx = request.getContextPath();
List sourceList = (List)request.getAttribute("sourceList");
List targetList = (List)request.getAttribute("targetList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function busSubmit(is_query){
		if(is_query===true){
			document.getElementById("_method_type").value="query";
		}else{
			document.getElementById("_method_type").value="modify";
		}
		document.getElementById("queryForm").submit();
	}
</script>
</head>
<body>


<c:set var="salary" scope="request" value="${2000*2}"/>
<c:choose>
    <c:when test="${salary <= 0}">
       Salary is very low to survive.
    </c:when>
    <c:when test="${salary > 1000}">
        Salary is very good.
    </c:when>
    <c:otherwise>
        No comment sir...
    </c:otherwise>
</c:choose>

<c:if test="${salary > 2000}">
   <p>My salary is: <c:out value="${salary}"/><p>
</c:if>




<div>
<table border="1" bordercolor="black" style="width:400px;float:left">
	<tr>
		<td>ID</td>
		<td>V_NAME</td>
		<td>VALID</td>
		<td>CREATE_TIME</td>
	</tr>
	<c:forEach items="${sourceList}" var="rows" >
	<tr>
	  <c:forEach items="${rows }" var="col" >
		<td><c:out value="${col}" /></td>
	  </c:forEach>
	</tr>
	</c:forEach>
</table>

<table border="1" bordercolor="black" style="width:400px;float:right">
	<tr>
		<td>ID</td>
		<td>V_NAME</td>
		<td>VALID</td>
		<td>CREATE_TIME</td>
	</tr>
	
	<c:forEach items="${targetList}" var="rows1" >
	<tr>
	  <c:forEach items="${rows1 }" var="col1" >
		<td><c:out value="${col1}" /></td>
	  </c:forEach>
	</tr>
	</c:forEach>
</table>
</div>
<br/>
<center>
<form id="queryForm" action="<%=ctx %>/busOpServlet.do" method="POST">
	<input type="hidden" id="_method_type" name="_method_type" value="query" />
	<button onclick="busSubmit(true);">刷新</button>
	<button onclick="busSubmit(false);">复制</button>
</form>
</center>



</body>
</html>