<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<script src="<%=request.getContextPath()%>/remoting/?parametro=script.js" type="text/javascript"></script>
</head>
<body leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" topmargin="0" bottommargin="0">
<f:view>
<h:form>
<table id="idTabla" border="0" width="100%" cellpadding="0" cellspacing="0">
   	<tr bgcolor="#C3D79C" height="5px">
     	<td>&nbsp;</td>
  		<td>&nbsp;</td>
 	</tr>
 	<tr bgcolor="#89D887">
 		<td style="width:500px" align="left">
	 		<h:outputText value="COOPERATIVA TUMI" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
	 		<h:outputText value=" - " style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
			<h:outputText value="TODOS LOS DERECHOS RESERVADOS" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/> 		
 		</td>
  		<td align="right">
			<h:outputText value="ULTIMO ACCESO: " style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
   		</td>
 	</tr>
</table>
<script>j$.setAlturaDePantallaPorId('idTabla');</script>
</h:form>
</f:view>
</body>
</html>
