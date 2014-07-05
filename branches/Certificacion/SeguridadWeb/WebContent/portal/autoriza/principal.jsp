<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="pe.com.tumi.common.util.Constante"%>
<% 
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires","-1");
	
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, no-store, must-revalidate, post-check=0, pre-check=0" >  
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<TITLE>ERP TUMI</TITLE>
</HEAD>


<frameset rows="15%,78%,7%"> 
   <frame name="alto" noresize frameborder="0" scrolling="no" src="<%=request.getContextPath()%>/portal/autoriza/header.jsf">
   <frameset id="masterModulo" cols="20%,1%,79%"> 
   	<frame name="lateral" noresize frameborder="0" scrolling="no" src="<%=request.getContextPath()%>/portal/autoriza/lateral.jsf">
   	<frame name="control" noresize frameborder="0" scrolling="no" src="<%=request.getContextPath()%>/portal/autoriza/control.jsp">
   	<frame name="modulo" noresize frameborder="0" scrolling="yes" src="<%=request.getContextPath()%>/portal/autoriza/modulo.jsp">
   </frameset>
   <frame name="pie" noresize frameborder="0" scrolling="no" src="<%=request.getContextPath()%>/portal/autoriza/pie.jsf">   
</frameset>
</html>