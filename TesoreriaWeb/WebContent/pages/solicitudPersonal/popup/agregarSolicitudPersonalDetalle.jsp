<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarDetalle" width="870" height="250"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Detalle"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarDetalle" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarDetalle">
	   
	    <rich:spacer height="3px"/>


		
		<h:panelGrid columns="8" id="panelPersonaDetalle">
			<rich:column width=	"110">
				<h:outputText value="Persona : "/>
			</rich:column>
			<rich:column width="520">
				<h:inputText 
					rendered="#{empty solicitudPersonalController.solicitudPersonalDetalleNuevo.persona}"
					size="71"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText
					rendered="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
					value="DNI : #{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.documento.strNumeroIdentidad} - #{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.natural.strNombreCompleto}"
					size="71"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText
					rendered="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
					value="RUC : #{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.strRuc} - #{solicitudPersonalController.solicitudPersonalDetalleNuevo.persona.juridica.strRazonSocial}"
					size="71"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>	
			</rich:column>
			<rich:column width="150">
				<a4j:commandButton styleClass="btnEstilos"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="Buscar Persona"
	                reRender="pBuscarPersona"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersona')"
	                action="#{solicitudPersonalController.abrirPopUpBuscarPersonaDetalle}"
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="9" id="panelSucursalDetalle">
			<rich:column width="110">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="200">
				<h:selectOneMenu
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.intSucuIdSucursal}"
					style="width: 190px;">
					<tumih:selectItems var="sel"
						value="#{solicitudPersonalController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" 
						action="#{solicitudPersonalController.seleccionarSucursalDetalle}" 
						reRender="panelSucursalDetalle"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="110">
				<h:outputText value="Subsucursal : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.intSudeIdSubsucursal}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.sucursal.listaSubSucursal}"
						itemValue="#{sel.id.intIdSubSucursal}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="40">
				<h:outputText value="Área : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.intIdArea}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="8">
			<rich:column width=	"110">
				<h:outputText value="Monto : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText 
					rendered="#{empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.bdMonto}"
					onkeypress="return soloNumerosDecimalesPositivos(this)"
					size="30"/>
				<h:inputText 
					rendered="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.bdMonto}"
					disabled="true"
					size="30">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>					
			</rich:column>
			<rich:column width=	"110">
				<h:outputText value="Tipo : "/>				
			</rich:column>
			<rich:column width=	"150">
				<h:selectOneMenu
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.intParaOpcionDebeHaber}"
					style="width: 150px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_OPCIONDEBEHABER}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="8" id="panelPlanCuentaDetalle">
			<rich:column width=	"110">
				<h:outputText value="Cuenta Contable : "/>
			</rich:column>
			<rich:column width="520">
				<h:inputText 
					rendered="#{empty solicitudPersonalController.solicitudPersonalDetalleNuevo.planCuenta}"
					size="71"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText
					rendered="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.planCuenta}"
					value="#{solicitudPersonalController.solicitudPersonalDetalleNuevo.planCuenta.id.intPeriodoCuenta} - 
					#{solicitudPersonalController.solicitudPersonalDetalleNuevo.planCuenta.id.strNumeroCuenta} - 
					#{solicitudPersonalController.solicitudPersonalDetalleNuevo.planCuenta.strDescripcion}"
					size="71"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width="150">
				<a4j:commandButton styleClass="btnEstilos"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
					value="Buscar Cuenta"
	                reRender="pBuscarPlanCuenta"
	                oncomplete="Richfaces.showModalPanel('pBuscarPlanCuenta')"
	                action="#{solicitudPersonalController.abrirPopUpBuscarPlanCuenta}"
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
			
		<rich:spacer height="5px"/>
		<h:outputText value="#{solicitudPersonalController.strMensajeDetalle}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{not empty solicitudPersonalController.strMensajeDetalle}"/>
		<rich:spacer height="10px"
			rendered="#{empty solicitudPersonalController.strMensajeDetalle}"/>
		
		<h:panelGrid columns="6">
			<rich:column width="250" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	disabled="#{not empty solicitudPersonalController.solicitudPersonalDetalleNuevo.id.intItemSolicitudPersonalDetalle}"
                	value="Aceptar"
                	oncomplete="if(#{solicitudPersonalController.mostrarMensajeDetalle}){rendPopUp2();}else{Richfaces.hideModalPanel('pAgregarDetalle');rendTabla2();}"
                   	action="#{solicitudPersonalController.agregarSolicitudPersonalDetalle}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarDetalle')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	

    	<a4j:jsFunction name="rendTabla2" reRender="panelDetalle,panelMonto" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp2" reRender="fAgregarDetalle" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	