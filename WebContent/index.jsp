<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%
String ctx = request.getContextPath();
List soruceList = (List)request.getAttribute("soruceList");
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
<div>
<table border="1" bordercolor="black" style="width:400px;float:left">
	<tr>
		<td>ID</td>
		<td>V_NAME</td>
		<td>VALID</td>
		<td>CREATE_TIME</td>
	</tr>
	<%
	if(soruceList!=null){
		for(int i=0;i<soruceList.size();i++){ 
			Object[] rowArr = (Object[])soruceList.get(i);
			
	%>
	<tr>
		<td><%=rowArr[0] %></td>
		<td><%=rowArr[1] %></td>
		<td><%=rowArr[2] %></td>
		<td><%=rowArr[3] %></td>
	</tr>
	<% 
		}
	}
	%>
</table>

<table border="1" bordercolor="black" style="width:400px;float:right">
	<tr>
		<td>ID</td>
		<td>V_NAME</td>
		<td>VALID</td>
		<td>CREATE_TIME</td>
	</tr>
	
	<%
	if(targetList!=null){
		for(int i=0;i<targetList.size();i++){ 
			Object[] rowArr = (Object[])targetList.get(i);
			
	%>
	<tr>
		<td><%=rowArr[0] %></td>
		<td><%=rowArr[1] %></td>
		<td><%=rowArr[2] %></td>
		<td><%=rowArr[3] %></td>
	</tr>
	<% 
		}
	}
	%>
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