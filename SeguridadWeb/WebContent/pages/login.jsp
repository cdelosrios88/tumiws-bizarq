<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />


	<!-- <link  rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset-fonts-grids.css"/> -->
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/default.css" />
	<script type="text/javascript">
A4J.AJAX.onError = function(req, status, message) {
	window.alert("Custom onError handler " + message);
}
</script>
</head>

<f:subview id="svBody">
	<a4j:form id="frmLoginUsuarios">
		<div id="dvMarco" align="center"
			style="margin-top: 1px; background-color: #dbe5f1">
			<h:graphicImage id="image" alt="Tumi"
				url="/images/icons/cabeceraTumiV2.jpg">
			</h:graphicImage>
			<rich:spacer height="15px" />
			<rich:spacer height="15px" />
			<rich:spacer height="15px" />
			<rich:spacer height="15px" />
			<rich:panel id="pMarco" style="margin-top:10px;width: 270px;height: 130px; background-repeat:no-repeat;border:1px solid #38610B;border-radius: 10px;box-shadow:rgba(0,0,0,0.4)10px 10px ; height : 140px;background-color:#dbe5f1">
				<h:panelGrid columns="2" border="0">
					<h:outputText id="lblUsuario" value="Usuario:"
						style="color:#2e5b90 ;padding-right:40px;" styleClass="spacio" />
					<h:inputText id="txtUsuario"
						value="#{loginController.beanBusqueda.codigo}">
						<f:validateLength minimum="1" maximum="30" />
					</h:inputText>
					<rich:spacer height="15px" />
					<rich:spacer height="15px" />
					<h:outputText id="lblContraseña" value="Contraseña: "
						style="color:#2e5b90 ;padding-right:10px;" styleClass="spacio" />
					<h:inputSecret id="txtsContraseña"
						value="#{loginController.beanBusqueda.contrasenha}">
						<f:validateLength minimum="1" maximum="30" />
					</h:inputSecret>
				</h:panelGrid>
			</rich:panel>
			<rich:spacer height="15px" />
			<rich:spacer height="15px" />
			<h:panelGrid columns="3" border="0" style="margin:0 auto">
				<h:commandButton value=" Ingresar "
					action="#{loginController.login}" styleClass="btnEstilos" />
				<rich:column style="width: 20px"></rich:column>
				<h:commandButton value=" Limpiar" action="#" styleClass="btnEstilos" />
			</h:panelGrid>
			<h:messages />
		</div>
		</div>
	</a4j:form>
</f:subview>