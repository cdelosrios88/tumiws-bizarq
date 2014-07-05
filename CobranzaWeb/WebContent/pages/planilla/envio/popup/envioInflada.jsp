<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pInflada" width="800" height="450"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Inflada planilla" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pInflada" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:spacer height="3px" />
	

	<h:form id="fInfladaPlanilla">
		
		
		<h:panelGrid columns="2" id="panelGrabarCancelar">
			<rich:column >
				<a4j:commandButton value="Grabar" rendered="#{not empty envioController.planilla}"
				actionListener="#{envioController.grabarInfladaPlanilla}" 
				reRender="idGrillaDePlanillaEnvio,idGrillaDePlanillaEnvioCESANTE"				
				styleClass="btnEstilos" oncomplete = "Richfaces.hideModalPanel('pInflada')"				
				disabled="#{!envioController.habilitarGrabarInflada}"/>
				<a4j:commandButton value="Grabar" rendered="#{not empty envioController.planillaCAS}"
				actionListener="#{envioController.grabarInfladaPlanillaCAS}" 
				reRender="idGrillaDePlanillaEnvioCAS"				
				styleClass="btnEstilos" oncomplete = "Richfaces.hideModalPanel('pInflada')"				
				disabled="#{!envioController.habilitarGrabarInflada}"/>											
			</rich:column>
			<rich:column >
				<a4j:commandButton value="Cancelar" 
					actionListener="#{envioController.cancelarInfladaPlanilla}"  styleClass="btnEstilos">				
					<rich:componentControl for="pInflada" operation="hide" event="onclick"/>
				</a4j:commandButton>
			</rich:column>
		</h:panelGrid>	
			
		<rich:spacer height="4px"/>
        <rich:separator height="5px"/>
		<rich:spacer height="4px"/>
		
		<h:panelGrid columns="2" id="panelSocio">
			<rich:column width="110">
				<h:outputText value="Nombre: " />
			</rich:column>
			<rich:column width="600">
				<h:inputText
					value="#{envioController.registroSeleccionado.intCodigoPersona} #{envioController.registroSeleccionado.socio.strApePatSoc} #{envioController.registroSeleccionado.socio.strApeMatSoc} 
					#{envioController.registroSeleccionado.socio.strNombreSoc} DNI: #{envioController.registroSeleccionado.documento.strNumeroIdentidad}"
					 size="71" readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2" id="panelEntidadPlanilla">
			<rich:column width="110">
				<h:outputText value="Entidad Planilla: " />
			</rich:column>
			<rich:column width="600">
				<h:inputText
					value="#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}"					
					size="71" readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="4" id="panelPeriodo">
			<rich:column width="110">
				<h:outputText value="Periodo: " />
			</rich:column>
			<rich:column width="150">
				<h:inputText
					value="#{envioController.dtoDeEnvio.envioConcepto.intPeriodoplanilla}"					
					 readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
					<rich:column width="100">
				<h:outputText value="Condicion: " />
			</rich:column>
			<rich:column width="120">
				<h:inputText
					value="#{envioController.registroSeleccionado.condicionCuenta}"					
					 readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />					
			</rich:column>
		</h:panelGrid>
		
		
		
		<h:panelGrid columns="4" id="panelTipodeSocio">
			<rich:column width="110">
				<h:outputText value="Tipo Socio: " />
			</rich:column>
			<rich:column width="150">
				<h:inputText  
					value="#{envioController.tipoSocio}"					
					 readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" />
			</rich:column>		
			<rich:column width="100">
				<h:outputText value="Modalidad: " />
			</rich:column>
			<rich:column width="250">
				<h:selectOneMenu value="#{envioController.envioInfladaK.intModalidad}" style="width:150px;" 
								disabled="#{!envioController.habilitarAgregarEnvioinflada}">			
			<tumih:selectItems var="sel" value="#{envioController.listaModalidadPlanillaTemporal}"
			 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>		
		
		<h:panelGrid columns="4" id="panelTipodeInflada">
			<rich:column width="110">
				<h:outputText value="Tipo inflada: " />
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu value="#{envioController.envioInfladaK.intTipoinfladaCod}"
				 style="width:150px;" disabled="#{!envioController.habilitarAgregarEnvioinflada}">
					<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
					<tumih:selectItems var="sel" value="#{envioController.listaTipoInflada}"
						 	itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>	
			<rich:column width="100">
				<h:outputText value="Monto: " />
			</rich:column>
			<rich:column width="150">
				<h:inputText
					value="#{envioController.envioInfladaK.bdMonto}"	onkeypress="return soloNumerosDecimalesPositivos(this)" onkeyup="return extractNumber(this,2,false)"
					 disabled="#{!envioController.habilitarAgregarEnvioinflada}" maxlength="9" onblur="extractNumber(this,2,false);"/>						 
			</rich:column>
		</h:panelGrid>
		<h:panelGrid columns="2" id="panelObservacion">
			<rich:column width="110">
				<h:outputText value="Observacion: " />
			</rich:column>
			<rich:column >
				<h:inputTextarea rows="3" cols="80" value="#{envioController.envioInfladaK.strObservacion}"
				disabled="#{!envioController.habilitarAgregarEnvioinflada}"/>					 
			</rich:column>
		</h:panelGrid>
		
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeExito}"/>
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeError}"/>
		</h:panelGroup>
		
		<h:panelGrid columns="3" id="panelAgregar">
			<rich:column width="110">				
			</rich:column>
			<rich:column width="400">				
			</rich:column>
			<rich:column>	
				<a4j:commandButton value="Agregar" styleClass="btnEstilos" action="#{envioController.addEnvioInflada}"
									reRender="panelGrabarCancelar, tblenvioInflada,panelTipodeInflada,
											 panelObservacion,panelMonto,panelMensaje" 
									disabled="#{!envioController.habilitarAgregarEnvioinflada}">
				</a4j:commandButton>
						    
			</rich:column>
		</h:panelGrid>
		
		<h:panelGroup id="grillaMontoInflada" layout="block" style="width:775px; overflow-x:auto !important; margin:0 auto">
			<rich:extendedDataTable id="tblenvioInflada" enableContextMenu="false"
				sortMode="single" var="item" style="margin:0 auto"				
				value="#{envioController.listaEnvioinflada}"
				rowKeyVar="rowKey" width="750px" height="100px" rows="3">											
				
				<rich:column width="175" style="text-align: left">
					<f:facet name="header">
		           		<h:outputText value="Entidad"/>
		         	</f:facet>
		         	<h:outputText value="#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}"	 />
				</rich:column>
				<rich:column width="40" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Tipo"/>
		         	</f:facet>
		         	<h:outputText value="#{envioController.tipoSocio}"/>
				</rich:column>
				
				<rich:column width="75" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Modalidad"/>
		         	</f:facet>
		         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intModalidad}"/>									
				</rich:column>
		        <rich:column width="120" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Tipo Inflada"/>
		          	</f:facet>
		          	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOINFLADA}"
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intTipoinfladaCod}"/>
		    	</rich:column>
		    	<rich:column width="90" style="text-align: right">
		        	<f:facet name="header">
		            	<h:outputText value="Monto"/>
		          	</f:facet>
		          	<h:outputText value="#{item.bdMonto}">
		          		<f:converter converterId="ConvertidorMontos"/>
		          	</h:outputText>
		    	</rich:column>
		    	<rich:column width="200" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Observacion"/>
		          	</f:facet>
		          	<h:outputText value="#{item.strObservacion}"></h:outputText>		          
		    	</rich:column>	
		    	<rich:column width="50" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Eliminar"/>
		          	</f:facet>
		          <a4j:commandLink value="Eliminar"						
						reRender="grillaMontoInflada,fInfladaPlanilla,panelGrabarCancelar"
						actionListener="#{envioController.eliminarEnvioinflada}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>	          
		    	</rich:column>			
		  	</rich:extendedDataTable>
		</h:panelGroup>
	</h:form>
</rich:modalPanel>
