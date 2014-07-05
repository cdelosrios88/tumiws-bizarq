<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="<%=request.getContextPath()%>/remoting/?parametro=style.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.columna{
 text-align: center;
 vertical-align: middle;
}
</style>
<script src="<%=request.getContextPath()%>/remoting/?parametro=script.js" type="text/javascript"></script>
</head>
<body leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" topmargin="0" bottommargin="0">
<f:view>
<h:form id="frmPrincipal">
<h:panelGrid id="tabla" columns="1" style="border:0px;width:100%;" columnClasses="columna">
<h:column>
<table id="tabla" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>		
	<td align="center" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0" style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;">
		<tr>
			<td style="width:70px;"></td>
			<td style="width:150px;"></td>			
			<td style="width:100px;"></td>
			<td style="width:70px;"></td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr class="etiquetaNormal">
			<td></td>
			<td align="left"><h:outputText value ="USUARIO:" style="width:150px;"/></td>
			<td><h:inputText value="#{loginController.strUsuario}" style="width:150px;" disabled="true"/></td>
			<td></td>
		</tr>
		<tr class="etiquetaNormal">
			<td></td>
			<td align="left"><h:outputText value ="PASSWORD ANTERIOR:" style="width:150px;"/></td>
			<td><h:inputSecret value="#{loginController.strContrasena}" style="width:150px;" redisplay="true"/></td>
			<td></td>
		</tr>
		<h:panelGroup rendered="#{not empty loginController.msgCambio.contrasena}">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<h:outputText value="#{loginController.msgCambio.contrasena}" styleClass="msgError" 
				        	rendered="#{not empty loginController.msgCambio.contrasena}"/>
			</td>
		</tr>
		</h:panelGroup>
		<tr class="etiquetaNormal">
			<td></td>
			<td align="left"><h:outputText value ="PASSWORD NUEVO:" style="width:150px;"/></td>
			<td><h:inputSecret value="#{loginController.strContrasenaNueva}" style="width:150px;" redisplay="true"/></td>
			<td></td>
		</tr>
		<h:panelGroup rendered="#{not empty loginController.msgCambio.contrasenaNueva}">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<h:outputText value="#{loginController.msgCambio.contrasenaNueva}" styleClass="msgError" 
				        	rendered="#{not empty loginController.msgCambio.contrasenaNueva}"/>
			</td>
		</tr>
		</h:panelGroup>
		<tr class="etiquetaNormal">
			<td></td>
			<td align="left"><h:outputText value ="PASSWORD VALIDACION:" style="width:150px;"/></td>
			<td><h:inputSecret value="#{loginController.strContrasenaValidacion}" style="width:150px;" redisplay="true"/></td>
			<td></td>
		</tr>
		<h:panelGroup rendered="#{not empty loginController.msgCambio.contrasenaValida}">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<h:outputText value="#{loginController.msgCambio.contrasenaValida}" styleClass="msgError" 
				        	rendered="#{not empty loginController.msgCambio.contrasenaValida}"/>
			</td>
		</tr>
		</h:panelGroup>
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<td colspan="2"></td>
			<td>
				<a4j:commandButton value="Aceptar" actionListener="#{loginController.modificarCambioClave}" reRender="frmPrincipal" styleClass="btnEstilos" style="width:65px"/>
				<h:commandButton id="idBtnIrModulo" action="#{loginController.irModulo}" style="display:none;"/>
				<h:panelGroup rendered="#{loginController.bolCambioClave}">
					<script>
					var lo = document.getElementById('frmPrincipal:idBtnIrModulo');
					if(lo){lo.click();}
					</script>
				</h:panelGroup>				
			</td>
			<td></td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
		</table>
	</td>
</tr>
</table>
</h:column>
</h:panelGrid>
<script>j$.setAlturaDePantallaPorId('frmPrincipal:tabla');</script>
<a4j:commandLink id="idLinkInicio" actionListener="#{loginController.cambiarClave}" reRender="frmPrincipal" style="display:none;"/>
</h:form>
<script>
var lControl = document.getElementById('frmPrincipal:idLinkInicio');
if(lControl){
    lControl.click();
}
</script>
</f:view>
</body>
</html>