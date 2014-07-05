<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
	
	
	<!-- <link  rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset-fonts-grids.css"/> -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/> 
	<script type="text/javascript">
		 A4J.AJAX.onError = function(req, status, message){
			window.alert("Custom onError handler "+message);
		}
		function getJSSelectionEmpresa(){
		  	var idEmpresa = document.frmLoginUsuarios["frmLoginUsuarios:cboEmpresa"].selectedIndex;
		  	var vcEmpresa = document.frmLoginUsuarios["frmLoginUsuarios:cboEmpresa"][idEmpresa].text;	  	
		  	document.frmLoginUsuarios["frmLoginUsuarios:hiddenStrEmpresa"].value=vcEmpresa;
		  }
	</script>
</head>
	  
<f:subview id = "svBody" >
   <a4j:form id = "frmLoginUsuarios">   
      <div id="dvMarco" align="center" style="margin-top: 1px ; background-color:#dbe5f1" >
      	  <h:graphicImage id="image" alt="Tumi"  url="/images/icons/cabeceraTumiV2.jpg" >
		  </h:graphicImage>
	      <rich:spacer height="15px"/><rich:spacer height="15px"/>
		  <rich:spacer height="15px"/><rich:spacer height="15px"/>          
          <rich:panel id="pMarco1" style="margin-top:10px;width: 270px;height: 130px; background-repeat:no-repeat;border:1px solid #38610B;border-radius: 10px;box-shadow:rgba(0,0,0,0.4)10px 10px ; height : 140px;background-color:#dbe5f1">
          		<h:panelGrid columns="2" border = "0" >
	          		<h:outputText id = "lblEmpresa" value="Empresas:"  style="color:#2e5b90 ;padding-right:25px;"  styleClass="spacio"/>
					<h:selectOneMenu id="cboEmpresa" style="width: 180px;" value="#{loginController.beanBusqueda.empresa}" valueChangeListener="#{controllerFiller.reloadCboSucursales}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{loginController.listaJuridicaEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
						<a4j:support event="onchange" reRender="cboSucursal" ajaxSingle="true"/>
					</h:selectOneMenu>
					<rich:spacer height="15px"/><rich:spacer height="15px"/>
					<h:outputText id = "lblSucursal" value="Sucursal:"  style="color:#2e5b90" styleClass="spacio"/>
					<h:selectOneMenu id="cboSucursal" style="width: 180px;" value="#{loginController.beanBusqueda.sucursal}" valueChangeListener="#{loginController.reloadCboPerfusuario}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listaSucursal}"
								itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
						<a4j:support event="onchange" reRender="cboPerfiles" ajaxSingle="true"/>
					</h:selectOneMenu>
					<rich:spacer height="15px"/><rich:spacer height="15px"/>
					<h:outputText id = "lblPerfil" value="Perfiles:"  style="color:#2e5b90" styleClass="spacio"/>
					<h:selectOneMenu id="cboPerfiles" style="width:160px;" value="#{loginController.beanBusqueda.perfil}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{loginController.listPerfil}"
								itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescPerfil}"/>
					</h:selectOneMenu>
					<h:inputHidden id="hiddenStrUsuario" value="#{loginController.beanBusqueda.codigo}" ></h:inputHidden>
					<h:inputHidden id="hiddenStrContrasena" value="#{loginController.beanBusqueda.contrasenha}" ></h:inputHidden>
					<h:inputHidden id="hiddenStrEmpresa" value="#{loginController.beanBusqueda.empresa}" ></h:inputHidden>
					<rich:spacer height="15px"/><rich:spacer height="15px"/>
          		</h:panelGrid>
          	</rich:panel>
          	<rich:spacer height="15px"/><rich:spacer height="15px"/>
          	<h:panelGrid columns="3" style="margin:0 auto">
			    <h:commandButton value=" Ingresar " action="#{loginController.login2}" styleClass="btnEstilos"/>
			    <rich:column style="width: 20px"></rich:column>
			    <h:commandButton value=" Limpiar" action="#" styleClass="btnEstilos"/>   
			</h:panelGrid>	
          </div>
		  	  		
		<!--<a4j:commandLink  action="#{loginController.login}">
			<h:graphicImage value="/images/icons/actualizarEstados.png" style="border:0px" id="imgPdf"/>
		</a4j:commandLink>-->						    		 
      </div>
   </a4j:form>
</f:subview>