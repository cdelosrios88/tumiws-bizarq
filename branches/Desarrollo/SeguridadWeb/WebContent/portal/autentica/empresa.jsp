<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!-- Inicio REQ14-001 bizarq 15-07-2014 -->
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Fin REQ14-001 bizarq 15-07-2014 -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@page import="pe.com.tumi.common.util.Constante"%>
<% 
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires","-1");
	
%>
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, no-store, must-revalidate, post-check=0, pre-check=0">  
<META HTTP-EQUIV="Expires" CONTENT="-1"> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Empresa</title>
<link href="<%=request.getContextPath()%>/css/default.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/remoting/?parametro=style.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.columna {
	text-align: center;
	vertical-align: middle;
}
</style>
<script
	src="<%=request.getContextPath()%>/remoting/?parametro=script.js"
	type="text/javascript"></script>
<!-- Inicio REQ14-001 bizarq 14-07-2014 -->
<c:choose>
    <c:when test="${(loginController.activaPopup != null) and (loginController.activaPopup=='1')}">
        <script type="text/javascript">
       		window.onload=function(){setTimeout(function(){mostrarPanel()},1000);};
       		function mostrarPanel (){
       			Richfaces.showModalPanel('macAddressValid');
       		}
		</script>
    </c:when>
    <c:otherwise>
		<script type="text/javascript" src="/SeguridadWeb/js/jquery19.js"></script>
		<script type="text/javascript">
			$(document).ready(function () {
				jQuery.ajax({ 
					type: 'GET', 
					url: 'http://localhost/servicio/servicio.php', 
					data: { get_param: 'value' }, 
					dataType: 'json',
					success: function (data) { 
						var names = data.name;
						document.getElementById('frmPrincipal:strMacAddress').value = names;
					}
				});
			});
		</script>
    </c:otherwise>
</c:choose>
<!-- Fin REQ14-001 bizarq 15-07-2014 -->
</head>
<body leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0"
	topmargin="0" bottommargin="0">
	<f:view>
		<!-- Inicio REQ14-001 bizarq 15-07-2014 -->
		<rich:modalPanel id="macAddressValid" minWidth="300"
			minHeight="100" autosized="true" resizeable="false"
			style="background-color:#DEEBF5;">
			<f:facet name="header">
				<h:panelGroup>
					<h:outputText value="Validación de MAC Address"></h:outputText>
				</h:panelGroup>
			</f:facet>
			<f:facet name="controls">
				<h:panelGroup>
					<h:graphicImage value="/images/icons/remove_20.png"
						styleClass="hidelink" id="hidelinkPerm" />
					<rich:componentControl for="macAddressValid"
						attachTo="hidelinkPerm" operation="hide" event="onclick" />
				</h:panelGroup>
			</f:facet>

			<h:form id="frmPermModalPanel">
				<rich:panel
					style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;width:450px;">
					<h:panelGrid columns="2" border="0" cellspacing="4">
						<h:outputText id="lblCodigo"
							value="#{loginController.strMessageValidMAC}"
							style="padding-right:35px;text-align:center;"></h:outputText>
					</h:panelGrid>
					<rich:separator height="5px"></rich:separator>
					<h:panelGrid columns="2" border="0" style="width: 200px;">
						<a4j:commandButton value="Aceptar"
							actionListener="#{loginController.limpiar}" onclick = "document.getElementById('linkLogin').click();"
							styleClass="btnEstilos" id="btnCancelarSesion"  />
					</h:panelGrid>
				</rich:panel>
			</h:form>
		</rich:modalPanel>
		<a id="linkLogin" href="<%=request.getContextPath()%>/portal/autentica/login.jsf" style="display:none;"> Login </a>  
		<!--  Fin REQ14-001 bizarq 15-07-2014 -->
			
		<h:form id="frmPrincipal">
			<!-- Inicio: REQ14-001 - bizarq - 15/07/2014 -->
			<h:inputHidden id="strMacAddress" value="#{loginController.strMacAddress}" />
			<!-- Fin: REQ14-001 - bizarq - 15/07/2014 -->
			<h:panelGrid id="tabla" columns="1" style="border:0px;width:100%;"
				columnClasses="columna">
				<h:column>
					<table id="tabla" width="100%" ; border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td align="center" valign="middle">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td style="width: 340px;"></td>
									</tr>
									<tr height="150px">
										<td align="center" valign="middle"><img
											src="<%=request.getContextPath()%>/images/icons/tumi.jpg">
										</td>
									</tr>
								</table>
							<td>
						</tr>
						<tr>
							<td align="center" valign="middle">
								<table border="0" cellpadding="0" cellspacing="0"
									style="border-width: 1px; border-style: solid; border-color: #17356f; background-color: #DEEBF5;">
									<tr>
										<td style="width: 70px"></td>
										<td style="width: 100px"></td>
										<td style="width: 100px"></td>
										<td style="width: 100px"></td>
										<td style="width: 70px"></td>
									</tr>
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
									<tr class="etiquetaNormal">
										<td></td>
										<td align="left"><h:outputText value="EMPRESA:"
												style="width:100px;" />
										</td>
										<td colspan="2"><h:selectOneMenu
												value="#{loginController.intIdEmpresa}" style="width:200px"
												tabindex="1">
												<f:selectItem itemLabel="Seleccione.." itemValue="0" />
												<tumih:selectItems var="sel"
													value="#{loginController.listaJuridicaEmpresa}"
													itemValue="#{sel.intIdPersona}"
													itemLabel="#{sel.strSiglas}" />
												<a4j:support event="onchange"
													actionListener="#{loginController.onChangeEmpresa}"
													reRender="frmPrincipal" />
											</h:selectOneMenu></td>
										<td></td>
									</tr>
									<h:panelGroup
										rendered="#{not empty loginController.msgPortal.idEmpresa}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="4"><h:outputText
													value="#{loginController.msgPortal.idEmpresa}"
													styleClass="msgError"
													rendered="#{not empty loginController.msgPortal.idEmpresa}" />
											</td>
										</tr>
									</h:panelGroup>
									<tr class="etiquetaNormal">
										<td></td>
										<td align="left"><h:outputText value="SUCURSAL:"
												style="width:100px;" />
										</td>
										<td colspan="2"><h:panelGroup
												rendered="#{not empty loginController.listaJuridicaSucursal}">
												<h:selectOneMenu
													value="#{loginController.intIdSucursalPersona}"
													style="width:200px" tabindex="2">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
													<tumih:selectItems var="sel"
														value="#{loginController.listaJuridicaSucursal}"
														itemValue="#{sel.intIdPersona}"
														itemLabel="#{sel.strSiglas}" />
													<a4j:support event="onchange"
														actionListener="#{loginController.onChangeSucursal}"
														reRender="frmPrincipal" />
												</h:selectOneMenu>
											</h:panelGroup> <h:panelGroup
												rendered="#{empty loginController.listaJuridicaSucursal}">
												<h:selectOneMenu
													value="#{loginController.intIdSucursalPersona}"
													style="width:200px" tabindex="2">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
												</h:selectOneMenu>
											</h:panelGroup></td>
										<td></td>
									</tr>
									<h:panelGroup
										rendered="#{not empty loginController.msgPortal.idSucursal}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="4"><h:outputText
													value="#{loginController.msgPortal.idSucursal}"
													styleClass="msgError"
													rendered="#{not empty loginController.msgPortal.idSucursal}" />
											</td>
										</tr>
									</h:panelGroup>
									<tr class="etiquetaNormal">
										<td></td>
										<td align="left"><h:outputText value="SUBSUCURSAL:"
												style="width:100px;" />
										</td>
										<td colspan="2"><h:panelGroup
												rendered="#{not empty loginController.listaSubSucursal}">
												<h:selectOneMenu value="#{loginController.intIdSubSucursal}"
													style="width:200px" tabindex="3">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
													<tumih:selectItems var="sel"
														value="#{loginController.listaSubSucursal}"
														itemValue="#{sel.id.intIdSubSucursal}"
														itemLabel="#{sel.strDescripcion}" />
												</h:selectOneMenu>
											</h:panelGroup> <h:panelGroup
												rendered="#{empty loginController.listaSubSucursal}">
												<h:selectOneMenu value="#{loginController.intIdSubSucursal}"
													style="width:200px" tabindex="3">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
												</h:selectOneMenu>
											</h:panelGroup></td>
										<td></td>
									</tr>
									<h:panelGroup
										rendered="#{not empty loginController.msgPortal.idSubSucursal}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="4"><h:outputText
													value="#{loginController.msgPortal.idSubSucursal}"
													styleClass="msgError"
													rendered="#{not empty loginController.msgPortal.idSubSucursal}" />
											</td>
										</tr>
									</h:panelGroup>
									<tr class="etiquetaNormal">
										<td></td>
										<td align="left"><h:outputText value="PERFIL:"
												style="width:100px;" />
										</td>
										<td colspan="2"><h:panelGroup
												rendered="#{not empty loginController.listaPerfil}">
												<h:selectOneMenu value="#{loginController.intIdPerfil}"
													style="width:200px" tabindex="4">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
													<tumih:selectItems var="sel"
														value="#{loginController.listaPerfil}"
														itemValue="#{sel.id.intIdPerfil}"
														itemLabel="#{sel.strDescripcion}" />
												</h:selectOneMenu>
											</h:panelGroup> <h:panelGroup
												rendered="#{empty loginController.listaPerfil}">
												<h:selectOneMenu value="#{loginController.intIdPerfil}"
													style="width:200px" tabindex="4">
													<f:selectItem itemLabel="Seleccione.." itemValue="0" />
												</h:selectOneMenu>
											</h:panelGroup></td>
										<td></td>
									</tr>
									<h:panelGroup
										rendered="#{not empty loginController.msgPortal.idPerfil}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="4"><h:outputText
													value="#{loginController.msgPortal.idPerfil}"
													styleClass="msgError"
													rendered="#{not empty loginController.msgPortal.idPerfil}" />
											</td>
										</tr>
									</h:panelGroup>
									<h:panelGroup
										rendered="#{not empty loginController.msgPortal.contrasena}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="3"><h:outputText
													value="#{loginController.msgPortal.contrasena}"
													styleClass="msgError"
													rendered="#{not empty loginController.msgPortal.contrasena}" />
											</td>
										</tr>
									</h:panelGroup>
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td align="center"><a4j:commandButton value="Aceptar"
												action="#{loginController.autorizar}"
												styleClass="btnEstilos" style="width:65px" tabindex="5" /></td>
										<td align="center"><a4j:commandButton value="Limpiar"
												actionListener="#{loginController.limpiar}" reRender="frmPrincipal"
												styleClass="btnEstilos" style="width:65px" tabindex="6" /></td>
										<td></td>
									</tr>
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
								</table></td>
						</tr>
					</table>
				</h:column>
			</h:panelGrid>
			<script>
				j$.setAlturaDePantallaPorId('frmPrincipal:tabla');
			</script>
		</h:form>
	</f:view>
</body>
</html>