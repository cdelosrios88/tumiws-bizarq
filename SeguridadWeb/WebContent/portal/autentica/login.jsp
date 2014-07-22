<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<c:if test="${loginController.activaPopup=='1'}">		
			<a4j:include viewId="/portal/autentica/modalValidacion.jsp"/>
			<script type="text/javascript">Richfaces.showModalPanel('panelMostrarMensaje');
			</script>
	</c:if>
<h:outputText value="#{loginController.inicializarPagina}"/>
<h:panelGrid id="tabla" columns="1" style="border:0px;width:100%;" columnClasses="columna">
<h:column>
<table id="tabla" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="center" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="width:340px;"></td>
		</tr>	
		<tr height="150px">
			<td align="center" valign="middle">
				<img src="<%=request.getContextPath()%>/images/icons/tumi.jpg">
			</td>
		</tr>
		</table>
	<td>
</tr>
<tr>		
	<td align="center" valign="middle">
		<table border="0" cellpadding="0" cellspacing="0" style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;">
		<tr>
			<td style="width:70px;"></td>
			<td style="width:100px;"></td>			
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
			<td align="left"><h:outputText value ="USUARIO:" style="width:100px;"/></td>
			<td><h:inputText value="#{loginController.usuario.strUsuario}" style="width:100px;"/></td>
			<td></td>
		</tr>
		<!--<h:panelGroup rendered="#{not empty loginController.msgPortal.usuario}">
		--><tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<h:outputText value="#{loginController.msgPortal.usuario}" styleClass="msgError"/> 
				        	<!--  rendered="#{not empty loginController.msgPortal.usuario}"-->
			</td>
		</tr>
		<!--</h:panelGroup>
		--><tr class="etiquetaNormal">
			<td></td>
			<td align="left"><h:outputText value ="PASSWORD:" style="width:100px;"/></td>
			<td><h:inputSecret value="#{loginController.usuario.strContrasena}" style="width:100px;"/></td>
			<td></td>
		</tr>
		<!--<h:panelGroup rendered="#{not empty loginController.msgPortal.contrasena}">
		--><tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<h:outputText value="#{loginController.msgPortal.contrasena}" styleClass="msgError"/><!--
				        	rendered="#{not empty loginController.msgPortal.contrasena}"
			--></td>
		</tr>
		
		<!-- Inicio -->
		<tr>
			<td>&nbsp;</td>
			<td colspan="4">
				<h:outputText value="#{loginController.msgPortal.contrasena}" styleClass="msgError"/>
			</td>
		</tr>
		
		<!--</h:panelGroup>
		-->
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<td colspan="2"></td>
			<td>
				<a4j:commandButton value="Aceptar" action="#{loginController.autenticar}" styleClass="btnEstilos" style="width:65px"
				reRender="tabla"/>			
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
<!--<a4j:commandLink id="idLinkInicio" actionListener="#{loginController.inicio}" style="display:none;"/>-->	
	
</h:form>
</f:view>
</body>
</html>