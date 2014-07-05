<html>
<head>
<script language="JavaScript" type="text/javascript">
var strContextPath= "<%=request.getContextPath()%>";
</script>
<script src="<%=request.getContextPath()%>/remoting/?parametro=script.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/main.js"  type="text/javascript"></script>
</head>
<body onmousemove="moverMouse();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table id="tabla" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="center" valign="middle">
		<img src="<%=request.getContextPath()%>/images/icons/tumi.jpg">
	</td>
</tr>
</table>
<script>j$.setAlturaDePantallaPorId('tabla');</script>	
</body>
</html>
