<!-- 
/************************************************************************/
/* Nombre de componente: header.jsp */
/* Descripción: Componente utilizado en la cabecera del login de la aplicación
/* Cod. Req.: REQ14-002   */
/* Autor : Christian De los Ríos */
/* Versión : V1.1 - modificación */
/* Fecha creación : 30/07/2014 */
/* ********************************************************************* */
-->

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<html>
<head>
<script>
var intSegundoSession = 0;
</script>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/main.js"  type="text/javascript"></script>
<!-- Inicio REQ14-002 bizarq 22-07-2014 -->
<script type="text/javascript" language="javascript">
		window.onbeforeunload = OnBeforeUnLoad;
        function OnBeforeUnLoad () {
            if(document.getElementById("hdnIndSesionSalir").value=="0")
				return 'Esta seguro de salir de la aplicación, se cerrará la sesión';
        }
		
		/*window.onbeforeunload=function(){
			if(document.getElementById("hdnIndSesionSalir").value=="0")
				return 'Esta seguro de salir de la aplicación, se cerrará la sesión';
		};*/
		window.onunload = function (){
			
		};
</script>
<!-- Fin REQ14-002 bizarq 22-07-2014 -->
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css"/>
</head>
<body onmousemove="intSegundoSession=0;" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" topmargin="0" bottommargin="0">
<f:view>
<h:form id="frmPrincipal">
		<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td style="width:200px;"></td>
			<td></td>
		</tr>
		<tr>
			<td height="75px">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
					<tr>
					<td><h:graphicImage value="/images/icons/tumi.jpg" width="200px" height="75px"/></td>
					</tr>
				</table>
			</td>
			<td height="75px">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
				<tr bgcolor="#89D887" height="15px">
					<td style="width:5px;"></td>
					<td style="width:35px;" align="left">
						<h:outputText value="USUARIO:" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
					</td>
					<td style="width:250px;" align="left">
						<h:outputText value=" #{fn:toUpperCase(loginController.usuario.persona.natural.strApellidoPaterno)}
											  #{fn:toUpperCase(loginController.usuario.persona.natural.strApellidoMaterno)}
											  #{fn:toUpperCase(loginController.usuario.persona.natural.strNombres)}" 
									  style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"
									  rendered="#{loginController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
						<h:outputText value=" #{fn:toUpperCase(loginController.usuario.persona.juridica.strSiglas)}"
									  style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"
									  rendered="#{loginController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>									  
					</td>
					<td style="width:150px;" align="left">
						<h:outputText value="SUCURSAL: " style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
						<h:commandLink value="#{fn:toUpperCase(loginController.usuario.sucursal.juridica.strSiglas)} " actionListener="#{loginController.irEmpresa}" onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkEmpresa').click();" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>			  
					</td>
					<td style="width:250px;" align="left">
						<h:outputText value="SUBSUCURSAL: " style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
						<h:commandLink value="#{fn:toUpperCase(loginController.usuario.subSucursal.strDescripcion)} " actionListener="#{loginController.irEmpresa}" onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkEmpresa').click();" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>			  
					</td>
				</tr>
				<tr bgcolor="#89D887" height="15px">
					<td style="width:5px;"></td>
					<td style="width:35px;" align="left">
						<h:outputText value="PERFIL:" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
					</td>
					<td style="width:250px;" align="left">
						<h:commandLink value=" #{fn:toUpperCase(loginController.usuario.perfil.strDescripcion)} " actionListener="#{loginController.irEmpresa}" onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkEmpresa').click();" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>						
					</td>
					<td>&nbsp;</td>
					<td align="right">
						
						<h:panelGroup rendered="#{empty loginController.usuario.empresaUsuario.intCambioClave}">
						<h:panelGroup rendered="#{not empty loginController.usuario.empresa.intControlCambioClave &&
												  loginController.usuario.empresa.intControlCambioClave == 1}">
						<h:commandLink value=" CAMBIAR CLAVE " actionListener="#{loginController.irEmpresa}" onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkCambioClave').click();" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
						</h:panelGroup>
						</h:panelGroup>
						<h:panelGroup rendered="#{not empty loginController.usuario.empresaUsuario.intCambioClave &&
												 loginController.usuario.empresaUsuario.intCambioClave == 1}">
						<h:commandLink value=" CAMBIAR CLAVE " actionListener="#{loginController.irEmpresa}" onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkCambioClave').click();" style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>						
						</h:panelGroup>
						<!-- Inicio REQ14-001 bizarq 15-07-2014 -->
						
						<%--<h:commandLink value=" SALIR " actionListener="#{loginController.cerrarSession}" 
						onclick="document.getElementById('hdnIndSesionSalir').value=1;document.getElementById('linkLogin').click();" 
						style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>--%>
						<a4j:commandLink value=" SALIR " action="#{loginController.closeSession}"
							onclick="document.getElementById('hdnIndSesionSalir').value=1;"
							oncomplete="document.getElementById('linkLogin').click();"
							style="color:#ffffff;padding-left: 5px;font-size:11px;font-weight:bold;"/>
						
						<input type="hidden" id="hdnIndSesionSalir" value ="0" />
						<%-- <h:commandLink id="btnSalirSesion" actionListener="#{loginController.cerrarSession}" onclick="window.top.close();" style="display:none;"/>--%>
						<!-- Fin REQ14-001 bizarq 15-07-2014 -->
						<h:commandLink id="linkCerrar" actionListener="#{loginController.cerrarSession}" onclick="top.close();" style="display:none;"/>
						<a id="linkLogin" href="<%=request.getContextPath()%>/portal/autentica/login.jsf" style="display:none;" target="_top"> Login </a>  
						<a id="linkEmpresa" href="<%=request.getContextPath()%>/portal/autentica/empresa.jsf" style="display:none;" target="_top"> Empresa </a>
						<a id="linkCambioClave" href="<%=request.getContextPath()%>/portal/adenda/usuario.jsf" style="display:none;" target="modulo"> Cambio Clave </a>
						<div id="idContador"></div>
					</td>
				</tr>
				<tr bgcolor="#C3D79C">
					<!--<td><a4j:commandButton value="Est. Cuenta" styleClass="btnEstilos"
					onclick="window.open('/SocioWeb/pages/estadoCuenta/estadoCuenta.jsf','popup','scrollbars=yes,toolbar=no,menubar=no,status=no,width=500,height=400')"/></td>	
					--><!--
					<td><h:commandButton value="Est. Cuenta" styleClass="btnEstilos"
					onclick="window.open('/SeguridadWeb/pages/Monitor/empresa.jsf','popup','dependent=yes,scrollbars=yes,toolbar=no,menubar=no,status=yes,width=500,height=400')"/></td>
					<td colspan="4">&nbsp;</td>						
				-->
				<td colspan="3">
					 <a4j:jsFunction name="openWindow"  
                     oncomplete="window.open('/SeguridadWeb/pages/estadoCuenta/estadoCuenta.jsf','popup','scrollbars=yes,toolbar=no,menubar=no,status=no,width=1300,height=600')"/>
					 <a4j:commandButton value="Est. Cuenta" styleClass="btnEstilos" action="#{loginController.iniciarEstCuenta}"
					 oncomplete="openWindow()"/>
				</td>
				<td colspan="2">&nbsp;</td>	
				</tr>
				</table>	
			</td>
		</tr>
		</table>
	<script>
	var fechaInicio = '<h:outputText value="#{sessionScope.ticket.tsFechaInicio}"><f:convertDateTime pattern="yyyy.MM.dd" timeZone="America/Bogota"/></h:outputText>';
	var horaInicio = '<h:outputText value="#{sessionScope.ticket.tsFechaInicio}"><f:convertDateTime pattern="HH.mm.ss" timeZone="America/Bogota"/></h:outputText>';
	var horaSession = '<h:outputText value="#{sessionScope.usuario.empresa.dtTiempoSesion}"><f:convertDateTime pattern="HH.mm.ss" timeZone="America/Bogota"/></h:outputText>';
	var horaAlertaSession = '<h:outputText value="#{sessionScope.usuario.empresa.dtAlertaSesion}"><f:convertDateTime pattern="HH.mm.ss" timeZone="America/Bogota"/></h:outputText>';
	//var horaSession = '00.02.00';
	//var horaAlertaSession = '00.01.00';
	var handleSegundo = null;
	var handleSession = null;
	var arrayFechaInicio = null;
	var arrayHoraInicio = null;
	var fechaHoraInicio = null;
	
	function inicializar(){
		if(fechaInicio != ''){
			arrayFechaInicio = fechaInicio.split('.');
			arrayHoraInicio = horaInicio.split('.');
			fechaHoraInicio = new Date(arrayFechaInicio[0],arrayFechaInicio[1]-1,arrayFechaInicio[2],arrayHoraInicio[0],arrayHoraInicio[1],arrayHoraInicio[2]);
			if(handleSegundo == null){
				handleSegundo = window.setInterval('segundoSession()', 1000);
			}
			if(handleSession == null){
				handleSession = window.setInterval('fechaSession()', 10*1000);
			}
		}
	}
	
	function fechaSession(){
	  var intSegundo = 0;
	  intSegundo = fechaHoraInicio.getTime() + intSegundoSession*1000;
	  var fechaHoraTranscurrido = new Date(intSegundo);
	  intSegundo = fechaHoraInicio.getTime() + getSegundos(horaSession);
	  var fechaHoraSession = new Date(intSegundo);

	  if(fechaHoraTranscurrido>fechaHoraSession){
	  	  //Inicio REQ14-002 bizarq 04-08-2014
	  	  document.getElementById('hdnIndSesionSalir').value="1";
	  	  //Fin REQ14-002 bizarq 04-08-2014
		  document.getElementById('frmPrincipal:linkCerrar').click();
		  window.clearInterval(handleSegundo);
		  window.clearInterval(handleSession);
		  handleSegundo = null;
		  handleSession = null;
	  }
	}

	function segundoSession(){
	 intSegundoSession++;
	 //document.getElementById("idContador").innerHTML = intSegundoSession;
	}
	inicializar();
	</script>		
	</h:form>
</f:view>
</body>
</html>