<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Autor     : Arturo Julca   			-->
<!-- Modulo    :                			-->
<!-- Prototipo :  Acceso fuera de hora		-->
<!-- Fecha     :                			-->

<rich:panel style="border: 0px solid #17356f;"
	styleClass="rich-tabcell-noborder">

	<h:panelGrid columns="6">
		<rich:column style="width: 110px">
			<h:outputText value="Empresa : " />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="cboEmpresa" style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.intPersEmpresa}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaEmpresas}"
					itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}" />
				<a4j:support event="onchange" reRender="idCboSucursalEmp" actionListener= "#{liquidateSessionController.reloadCboSucursales}"
					ajaxSingle="true" />
			</h:selectOneMenu>
		</rich:column>
		<rich:column style="width: 110px">
			<h:outputText value="Usuario : " />
		</rich:column>
		<rich:column>
			<h:inputText style="width: 150px;"
				value=" #{liquidateSessionController.objLiqSess.strUsuario}" />
		</rich:column>

		<rich:column style="width: 110px">
			<h:outputText value="Estado : " />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="cboEstado" style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.intEstado}">
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaEstados}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>
	<h:panelGrid columns="4">
		<rich:column style="width: 110px">
			<h:outputText value="Sucursal:"  />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="idCboSucursalEmp" style="width: 200px;"
				value="#{liquidateSessionController.objLiqSess.intCboSucursalEmp}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaJuridicaSucursal}"
					itemValue="#{sel.intPersPersonaPk}"
					itemLabel="#{sel.juridica.strRazonSocial}" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>
	<h:panelGrid columns="4">

	</h:panelGrid>

	<rich:spacer height="12px" />

	<h:panelGrid id="panelTablaAccesosFH">


	</h:panelGrid>


	<h:panelGrid columns="1" style="margin-left: auto; margin-right: auto">
		<h:outputText
			value="Para Modificar o Eliminar hacer click en el registro"
			style="color:#8ca0bd" />
	</h:panelGrid>

</rich:panel>
