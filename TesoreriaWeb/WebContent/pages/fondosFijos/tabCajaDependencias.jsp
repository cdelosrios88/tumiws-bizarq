<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi		-->
	<!-- Autor     : Junior Chavez   		-->
	<!-- Modulo    : Ingreso - Dependencias	-->
	<!-- Fecha Creación   : 17.06.2014		-->

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; " id="frmIngresoCajaDependencias">
   	<rich:panel style="border:1px solid #17356f;background-color:#DEEBF5;">
		<a4j:outputPanel id="opIngresoCajaDependencias">
		
			<h:panelGroup rendered="#{(cajaController.datosValidados) 
							&& (cajaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)}">
				
			            
	            <rich:spacer height="3px"/>
				
				
				<h:panelGroup id="panelDocumentoC">
	            
	            <h:panelGrid columns="8">
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Tipo de Documento : "/>
			        </rich:column>
			        <rich:column width="175">
						<h:selectOneMenu
							style="width: 150px;"
							disabled="#{(not empty cajaController.listaIngresoDetalleInterfaz)||(cajaController.deshabilitarNuevo)}"
							value="#{cajaController.intTipoDocumentoAgregar}">
							<tumih:selectItems var="sel"
								value="#{cajaController.listaTablaDocumentoGeneral}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="503">
						<h:inputText size="89" 
							readonly="true"
							style="background-color: #BFBFBF;"
							value="#{cajaController.documentoGeneralSeleccionado.strEtiqueta}"/>
					</rich:column>
					<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		disabled="#{(empty cajaController.personaSeleccionada) || (cajaController.deshabilitarNuevo)}"
	                		value="Buscar"
	                		reRender="pBuscarDocumentoCaja"
	                    	action="#{cajaController.abrirPopUpBuscarDocumento}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarDocumentoCaja')"
	                    	style="width:80px"/>
	            	</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		disabled="#{(empty cajaController.documentoGeneralSeleccionado) || (cajaController.deshabilitarNuevo)}"
	                		value="Agregar"
	                		reRender="panelMontoC,panelDocumentosAgregadosC,panelDocumentoC,panelMensajeC"
	                    	action="#{cajaController.agregarDocumento}"
	                    	style="width:80px"/>
	            	</rich:column>				
				</h:panelGrid>				
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6" rendered="#{!cajaController.deshabilitarNuevo}">
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Monto a Ingresar :"/>
					</rich:column>
					<rich:column width="200">
						<h:inputText size="24"
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.bdMontoIngresar}"
							onkeypress="return soloNumerosDecimales(this)">							
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Monto Total :"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="20"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.bdMontoIngresarTotal}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="74"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.strMontoIngresarTotalDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				
				</h:panelGroup>
				
				<rich:spacer height="3px"/>
				
				
				
				<h:panelGroup id="panelGestorC">
				
				<h:panelGrid columns="11" >
					<rich:column width="120">
						<h:outputText value="Gestor de Ingreso :"/>
					</rich:column>
					<rich:column width="685">
						<h:inputText readonly="true"
							rendered="#{empty cajaController.gestorCobranzaSeleccionado}"
							style="background-color: #BFBFBF;"
							size="124"/>
						<h:inputText readonly="true"
							rendered="#{(not empty cajaController.gestorCobranzaSeleccionado)}"
							value="DNI : #{cajaController.gestorCobranzaSeleccionado.persona.documento.strNumeroIdentidad} - #{cajaController.gestorCobranzaSeleccionado.persona.natural.strNombreCompleto} - Recibo Manual: #{cajaController.reciboManual.intSerieRecibo} - #{cajaController.reciboManual.intNumeroActual}"
							style="background-color: #BFBFBF;"
							size="124"/>
					</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{(empty cajaController.gestorCobranzaSeleccionado)}"
	                		value="Buscar"
	                		reRender="pBuscarPersonaCaja"
	                    	action="#{cajaController.abrirPopUpBuscarGestor}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarPersonaCaja')"
	                    	disabled="#{cajaController.deshabilitarNuevo}"
	                    	style="width:80x"/>
	                    <a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{not empty cajaController.gestorCobranzaSeleccionado}"
	                		value="Quitar"
	                		reRender="contPanelInferiorC"
	                    	action="#{cajaController.quitarGestorSeleccionado}"
	                    	disabled="#{cajaController.deshabilitarNuevo}"
	                    	style="width:80x"/>
	            	</rich:column>
	            </h:panelGrid>
	            
	            <rich:spacer height="3px"/>
	            
	            </h:panelGroup>
				
				
				<h:panelGrid columns="6">
					<rich:column width="120" style="vertical-align: top">
						<h:outputText value="Observación :"/>
					</rich:column>
					<rich:column>
						<h:inputTextarea rows="2" cols="124"
							value="#{cajaController.strObservacion}"
							disabled="#{cajaController.deshabilitarNuevo}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="1" id="panelDocumentosAgregadosC">
					<rich:column>
						<rich:dataTable
				    		var="item"
				    		styleClass="datatable"
				            value="#{cajaController.listaIngresoDetalleInterfaz}"
					 		sortMode="single"
						  	width="950px"
			                rows="#{fn:length(cajaController.listaIngresoDetalleInterfaz)}">
			                    
							<rich:column width="20px" style="text-align: left">
			            		<h:outputText value="#{item.intOrden}"/>
			            	</rich:column>
			            	<rich:column width="120" style="text-align: left">
			                   	<f:facet name="header">
			                		<h:outputText value="Documento"/>
			                   	</f:facet>
			                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{item.intDocumentoGeneral}"/>
			                </rich:column>
			                <rich:column width="100" style="text-align: left">
			                   	<f:facet name="header">
			                   		<h:outputText value="Nro de Documento"/>
			                   	</f:facet>
			                  	<h:outputText value="#{item.strNroDocumento}"/>
			                </rich:column>
			                <rich:column width="150" style="text-align: left">
			                  	<f:facet name="header">
			                   		<h:outputText value="Persona"/>
			                   	</f:facet>
			                   	<h:outputText
			                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			                   		value="#{item.persona.natural.strNombreCompleto}"/>
								<h:outputText
			                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			                   		value="#{item.persona.juridica.strRazonSocial}"/>
			                </rich:column>
			                <rich:column width="160" style="text-align: left">
			                    <f:facet name="header">
			                    	<h:outputText value="Sucursal"/>                      		
			                    </f:facet>
			                    <h:outputText value="#{item.sucursal.juridica.strSiglas} - #{item.subsucursal.strDescripcion}"/>
			               	</rich:column>
			                <rich:column width="100" style="text-align: left">
			                    <f:facet name="header">
			                    	<h:outputText value="Descripción"/>                      		
			                    </f:facet>
			                    <h:outputText value="#{item.strDescripcion}"/>
			              	</rich:column>
			              	<rich:column width="80" style="text-align: right">
			                    <f:facet name="header">
			                    	<h:outputText value="Monto"/>                      		
			                    </f:facet>
			                    <h:outputText value="#{item.bdMonto}">
			                      	<f:converter converterId="ConvertidorMontos" />
			                    </h:outputText>
			              	</rich:column>
			               	<rich:column width="80" style="text-align: right">
			                    <f:facet name="header">
			                      	<h:outputText value="Sub Total"/>                      		
			                    </f:facet>
			                    <h:outputText value="#{item.bdSubtotal}">
			                      	<f:converter converterId="ConvertidorMontos" />
			                     </h:outputText>
			               	</rich:column>
			               	<rich:column width="110" style="text-align: right">
			                    <f:facet name="header">
			                      	<h:outputText value="Asiento"/>                      		
			                    </f:facet>
			                    <h:outputText rendered="#{not empty item.libroDiario}" 
			                    	value="#{item.libroDiario.strNumeroAsiento}"/>
			               	</rich:column>
			           	</rich:dataTable>
					</rich:column>
				</h:panelGrid>
			</h:panelGroup>
		</a4j:outputPanel>
	</rich:panel>
</h:form>
