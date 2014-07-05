<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pTipoPlanilla" width="750" height="315"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Tipo Planilla" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pTipoPlanilla" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:spacer height="3px" />

	<h:form id="fTipoPlanilla">
		<h:panelGrid columns="2" id="panelSocio">
			<rich:column width="110">
				<h:outputText value="Socio : " />
			</rich:column>
			<rich:column width="500">
				<h:inputText
					value="#{envioController.registroSeleccionado.strCodigoPersona} - 
					#{envioController.registroSeleccionado.socio.strApePatSoc} #{envioController.registroSeleccionado.socio.strApeMatSoc} 
					#{envioController.registroSeleccionado.socio.strNombreSoc}- #{envioController.registroSeleccionado.documento.strNumeroIdentidad}
					"
					size="71" readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2" id="panelDependencia">
			<rich:column width="110">
				<h:outputText value="Dependencia : " />
			</rich:column>
			<rich:column width="500">
				<h:inputText
					value="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial} - 
					#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}
					-#{envioController.registroSeleccionado.strhaberIncentivoCas}"
					size="71" readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" />
			</rich:column>
		</h:panelGrid>
	
		<h:panelGrid columns="1" id="panelDestacado">
			<rich:column width="110">
				<h:selectBooleanCheckbox disabled="#{true}"
					value="#{envioController.destacado}">
					<a4j:support event="onclick" reRender="panelSucursal,panelUnidadEjecutora,panelTipoPlanilla"
						actionListener="#{envioController.onclickCheckHaberesDeEnvio}" />
				</h:selectBooleanCheckbox>
				<h:outputText value="Destacado" />
			</rich:column>			
		</h:panelGrid>
		
	    <h:panelGrid columns="2" id="panelSucursal">
			<rich:column width="110">
				<h:outputText value="Tipo de Planilla : " />
			</rich:column>
			<rich:column width="500">
				<h:selectOneMenu
				disabled="#{!envioController.destacado}"
					value="#{envioController.dtoFiltroDePPEjecutoraEnvio.juridicaSucursal.intIdPersona}"
					style="width:490px;">
					<tumih:selectItems var="sel"
						value="#{envioController.listaJuridicaSucursalDePPEjecutoraEnvio}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}" />						
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2" id="panelUnidadEjecutora">
			<rich:column width="110">
			</rich:column>
			<rich:column width="500">     
				<h:selectOneMenu value="#{envioController.dtoDeEnvio.estructura.juridica.intIdPersona}"	
					disabled="#{!envioController.destacado}"			
					style="width:490px;">
					<tumih:selectItems var="sel"
						value="#{envioController.listaBusquedaDePPEjecutoraEnvio}"
						itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}" />
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
	   <h:panelGrid columns="2" id="panelComboBienHaberOincentivo">
			<rich:column width="110">
			</rich:column>
			<rich:column width="500">				
				<h:selectOneMenu 
								 style="width:490px;" disabled="#{true}">
					<tumih:selectItems var="sel" value="#{envioController.listaModalidadTipoPlanilla}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
					tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
	 		 	</h:selectOneMenu>
			</rich:column>			
		</h:panelGrid>
		<h:panelGrid columns="3" id="panelCodigoPlanilla">
			<rich:column width="110">
				<h:outputText value="Codigo Planilla: " />
			</rich:column>
			<rich:column width="500">
				<h:inputText value="#{envioController.intCodigoPlanilla}"
				onkeypress="return soloNumerosDecimalesPositivos(this)" maxlength="7">							
											
				</h:inputText>
			</rich:column>
		</h:panelGrid>
			
		 <h:panelGrid columns="3" id="panelMonto">
			<rich:column width="110">
				<h:outputText value="Monto: " />
			</rich:column>	
					
			<rich:column width="500">
				<h:inputText value="#{envioController.bdMontoTipoPlanilla}"
				onkeypress="return soloNumerosDecimalesPositivos(this)" maxlength="7"
				onkeyup="return extractNumber(this,2,false)"
				onblur="extractNumber(this,2,false);">
											
				</h:inputText>
			</rich:column>
			<rich:column>				
				<a4j:commandButton value= "Validar" actionListener="#{envioController.validarTipoPlanilla}"
				reRender="panelMensaje, panelMonto" styleClass="btnEstilos" disabled="#{!envioController.blnhabilitarAgregar}"/>
				<a4j:commandButton value="Agregar" actionListener="#{envioController.addTipoPlanilla}" 
					styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pTipoPlanilla')"
					reRender="idGrillaDePlanillaEnvio, pgTotal" disabled="#{envioController.blnhabilitarAgregar}"/>					
			</rich:column>
		</h:panelGrid>
		
		<h:panelGroup  style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
						styleClass="rich-tabcell-noborder" id="panelMensaje">
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgInfo" style="font-weight:bold"				
				rendered="#{envioController.mostrarMensajeExito}"/>
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgError" style="font-weight:bold"				
				rendered="#{envioController.mostrarMensajeError}"/>
		</h:panelGroup>

	</h:form>
</rich:modalPanel>
