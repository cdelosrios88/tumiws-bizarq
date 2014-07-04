<%@page import="pe.com.tumi.framework.servicio.seguridad.dto.Ticket"%>
<html>
<head>
<link href="<%=request.getContextPath()%>/remoting/?parametro=style.css" rel="stylesheet" type="text/css" />
<script>
var strContextPath = '<%=request.getContextPath()%>';
</script>
<script src="<%=request.getContextPath()%>/remoting/?parametro=script.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/remoting/?parametro=menu.js" type="text/javascript"></script>
</head>
<body onload="j$.setControlMenu();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="idControlMenu" style="width:15px; position:relative; left:0px; top:0px; z-index:9;">
 <input id="idBtnControlMenu" type="button" value="&lt;" class="controlBotonMenu" onclick ="j$.controlarMenu();"/>
</div>
<table id="tabla" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="left" valign="top">&nbsp;</td>
</tr>
</table>
<script>j$.setAlturaDePantallaPorId('tabla');</script>	
<input type="hidden" value="estadoOculto" id="hddEstado"/>
</body>
</html>