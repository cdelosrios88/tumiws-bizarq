<%@page import="pe.com.tumi.framework.servicio.seguridad.dto.Ticket"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<html>
<head>
<link href="<%=request.getContextPath()%>/remoting/?parametro=style.css" rel="stylesheet" type="text/css"/>
<script>
<%Ticket ticket =((Ticket)request.getSession().getAttribute("ticket"));
String strTicket = "";
if(ticket!= null){
	 strTicket=ticket.getId(); 
}%>
var strContextPath = '<%=request.getContextPath()%>';
var strIdTicket = '<%=strTicket%>';
</script>
<script src="<%=request.getContextPath()%>/remoting/?parametro=script.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/remoting/?parametro=tree.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/main.js"  type="text/javascript"></script>
</head>
<body  onmousemove="moverMouse();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<f:view>
<table id="tabla" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="left" valign="top">
		<tumih:treeLink id="idMenu" value="#{loginController.listaTransaccion}"
			linkOnClick="j$.irModulo(this,'strModulo','strPagina')"
			linkTarget="modulo"
			linkLabel="strNombre" 
			property="listaTransaccion"/>
	</td>
</tr>
</table>
</f:view>
</body>
</html>