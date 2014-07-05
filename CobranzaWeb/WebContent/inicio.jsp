<%@page import="pe.com.tumi.framework.servicio.seguridad.dto.Ticket"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina de inicio</title>
</head>
<body leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" topmargin="0" bottommargin="0">
<table id="tabla" width="100%"; border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="center" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>&nbsp;</td>
			<td><a id="idLinkInicio" href="<%=request.getContextPath()%><%=request.getSession().getAttribute("pagina")%>" style="display:none;"></a>
			</td>
		</tr>	
		</table>
	</td>
</tr>
</table>
<script>
var lControl = document.getElementById("idLinkInicio");
if(lControl){
	    lControl.click();
}
</script>
</body>
</html>