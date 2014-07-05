<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Cobranza : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico	  		 -->
	<!-- Modulo    : Cobranza                -->

<rich:modalPanel id="pMontoEnvio" width="750" height="225"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Monto de Envio" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pMontoEnvio" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:spacer height="3px" />

	<h:form id="fEnvioMonto">
		<h:panelGrid columns="2" id="panelSocio">
			<rich:column width="110">
				<h:outputText value="Socio : " />
			</rich:column>
			<rich:column width="500">
				<h:inputText value="#{envioController.registroSeleccionado.strCodigoPersona} - 					
					#{envioController.registroSeleccionado.socio.strApePatSoc} #{envioController.registroSeleccionado.socio.strApeMatSoc} 
					#{envioController.registroSeleccionado.socio.strNombreSoc}-#{envioController.registroSeleccionado.documento.strNumeroIdentidad}"					
					size="71" readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
		</h:panelGrid>
			
		<h:panelGrid columns="2" id="panelDependencia">
			<rich:column width="110">
				<h:outputText value="Dependencia : " />
			</rich:column>
			<rich:column width="500">
				<h:inputText
					value="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial} - 
					#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}-Haberes"
					size="71" readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="2" id="panelDependencia2">
			<rich:column width="110">
			</rich:column>
			<rich:column width="500">
				<h:inputText
					value="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial} - 
					#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}-Incentivos"
					size="71" readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
		</h:panelGrid>
			
		<h:panelGrid columns="5" id="panelGeneral">
			<rich:column width="110">
				<h:outputText value="Montos: " />
			</rich:column>
			
			<rich:column rowspan="5" width="120">				
				<h:selectOneRadio   layout="pageDirection" 
					valueChangeListener="#{envioController.seleccionarHaber}">					 
					<f:selectItem itemLabel="Haberes" itemValue="0" />					
					<f:selectItem itemLabel="Incentivos" itemValue="1"/>
					<a4j:support event="onchange" 
					reRender="panelGeneral, panelCajaHaber,panelCajaIncentivo, panelCalcular"/>
				</h:selectOneRadio>
								
				<h:outputText value="Total: " style="font-weight:bold; font-size:10px"/>
										
			</rich:column>
			
			<rich:column rowspan="5" width="380">
				<h:panelGrid id="panelCajaHaber">
							<h:inputText value="#{envioController.registroSeleccionado.bdHaberes}" 
							 onkeyup="return extractNumber(this,2,false)" onblur="extractNumber(this,2,false);"
							 onkeypress="return soloNumerosDecimalesPositivos(this);" size="10"
							 disabled="#{!envioController.habilitarModalidadHaberMontoEnvio}"/>
				</h:panelGrid>
				
				<h:panelGrid id="panelCajaIncentivo">
							<h:inputText value="#{envioController.registroSeleccionado.bdIncentivos}" size="10" 
							onblur="extractNumber(this,2,false);" onkeyup="return extractNumber(this,2,false)"
							onkeypress="return soloNumerosDecimalesPositivos(this);"
							disabled="#{!envioController.habilitarModalidadIncentivoMontoEnvio}" />
				</h:panelGrid>
				<h:panelGrid>   
						<h:outputText style="font-weight:bold; font-size:10px"
								 value="#{envioController.registroSeleccionado.bdtotalHaberIncentivo}">
						<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
				</h:panelGrid>				 
			</rich:column>
			<rich:column rowspan="5" width="380">
			</rich:column>
			<rich:column rowspan="5" width="380">
				<h:panelGrid id="panelCalcular">
				<a4j:commandButton value="Calcular" actionListener="#{envioController.calcularMontoEnvio}" 
							styleClass="btnEstilos" reRender="panelGrabar, panelCajaIncentivo, panelCajaHaber,panelGeneral"
					  		disabled="#{!envioController.habilitarCalcularMontoEnvio}"/>
				</h:panelGrid>
				<h:panelGrid id="panelGrabar">      
					<a4j:commandButton value="Grabar" actionListener="#{envioController.grabarMontoEnvio}" 
							reRender="idGrillaDePlanillaEnvio" oncomplete = "Richfaces.hideModalPanel('pMontoEnvio')"
							styleClass="btnEstilos"  disabled="#{!envioController.habilitarGrabarMontoEnvio}"/>
				</h:panelGrid>
			</rich:column>
			
		</h:panelGrid>
		
			
	</h:form>
</rich:modalPanel>
