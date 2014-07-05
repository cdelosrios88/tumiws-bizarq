<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->
	<!-- Modificado por : Junior Chávez  -->
	<!-- Fecha modificación : 11.02.2014 -->



<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CHEQUE" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="11">
            	<rich:column width="90" style="text-align: left;">
                	<h:outputText value="Tipo Documento :"/>
            	</rich:column>
            	<rich:column width="140">
            		<h:selectOneMenu
						style="width: 137px;"
						value="#{chequesController.egresoFiltro.intParaDocumentoGeneral}">
						<tumih:selectItems var="sel"
							value="#{chequesController.listaTipoDocumento}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="110">
					<h:outputText value="Número de cheque :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{chequesController.egresoFiltro.intNumeroCheque}" size="23"/>
				</rich:column>
				<rich:column width="90" style="text-align: left;">
                	<h:outputText value="Fecha de Egreso: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{chequesController.dtDesdeFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{chequesController.dtHastaFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="50">
					<h:outputText value="Monto :"/>
				</rich:column>
				<rich:column width="100">
					<h:inputText value="#{chequesController.egresoFiltro.bdMontoTotal}" size="13"/>
				</rich:column>
            </h:panelGrid>
            
        	<h:panelGrid columns="11" id="panelPersonaFiltroF">
        		<rich:column width="90">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column width="140" style="text-align: left;">
					<h:selectOneMenu
						value="#{chequesController.intTipoPersonaFiltro}"
						style="width: 137px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPersonaFiltroF"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="110" style="text-align: left;">
					<h:selectOneMenu
						rendered="#{chequesController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{chequesController.intTipoBusquedaPersona}"
						style="width: 110px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{chequesController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{chequesController.intTipoBusquedaPersona}"
						style="width: 110px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="3" itemLabel="RUC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="150" style="text-align: left;">
					<h:inputText size="23"
						value="#{chequesController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="95" style="text-align: left;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{chequesController.egresoFiltro.intParaEstado}"
						style="width: 95px;">
						<f:selectItem itemValue="1" itemLabel="Activo"/>
						<f:selectItem itemValue="3" itemLabel="Anulado"/>
					</h:selectOneMenu>
				</rich:column>				
            	<rich:column width="160" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaResultadosC,panelMensaje"
                    	action="#{chequesController.buscar}" style="width:160px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosC">
	        	<rich:extendedDataTable id="tblResultadoC" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{chequesController.listaEgreso}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="165px" 
					align="center">
                                
					<rich:column width="20" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro. Egreso"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroEgreso}"/>
                    </rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.dtFechaEgreso}">
                      		<f:convertDateTime pattern="dd/MM/yyyy" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Persona"/>                      		
                      	</f:facet>
                      	<h:outputText 
                      		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
                      		value="#{item.personaApoderado.natural.strNombreCompleto}"
                      		title="#{item.personaApoderado.natural.strNombreCompleto}"/>
                      	<h:outputText 
                      		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
                      		value="#{item.personaApoderado.juridica.strRazonSocial}"
                      		title="#{item.personaApoderado.juridica.strRazonSocial}"/>	
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Documento"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.listaEgresoDetalle[0].intParaDocumentoGeneral}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Número"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroEgreso}"/>
                  	</rich:column>
                  	<rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Cuenta"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bancoCuenta.strEtiqueta}"
                      		title="#{item.bancoCuenta.strEtiqueta}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Cheque"/>
                      	</f:facet>
                      	<h:outputText value="#{item.intNumeroCheque}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotal}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{chequesController.verRegistro}"
						reRender="panelMensajeC, panelBotonesC, contPanelInferiorC">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoC" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Imprimir un  CHEQUE hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeC" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{chequesController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{chequesController.mostrarMensajeExito}"/>
			<h:outputText value="#{chequesController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{chequesController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesC">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{chequesController.habilitarPanelInferior}" 
					reRender="contPanelInferiorC,panelMensajeC,panelBotonesC" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{chequesController.grabar}" 
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC,panelTablaResultadosC"
			    	disabled="#{!chequesController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{chequesController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorC">
			
		<rich:panel  rendered="#{chequesController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<rich:spacer height="3px"/>
			
			<h:panelGroup rendered="#{!chequesController.datosValidados}">
				
				<h:panelGrid columns="16">
					<rich:column width="80" style="text-align: left;">
						<h:outputText value="Documento : "/>
			        </rich:column>
			        <rich:column width="203">
						<h:selectOneMenu
							style="width: 200px;"
							value="#{chequesController.intTipoDocumentoValidar}">
							<tumih:selectItems var="sel"
								value="#{chequesController.listaTipoDocumento}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="70" style="text-align: left;">
						<h:outputText value="Sucursal :"/>
			        </rich:column>
			        <rich:column>
						<h:inputText size="53" value="#{chequesController.sucursalUsuario.juridica.strSiglas} - #{chequesController.subsucursalUsuario.strDescripcion}" 
							readonly="true" style="background-color: #BFBFBF;"/>					
			        </rich:column>			        
				</h:panelGrid>
				
				<h:panelGrid columns="8" id="panelBancoValidar">
					<rich:column width="80">
							<h:outputText value="Banco :"/>
					</rich:column>
					<rich:column width="200">
						<h:selectOneMenu
							style="width: 200px;"
							disabled="#{chequesController.deshabilitarNuevo}"
							value="#{chequesController.intBancoSeleccionado}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" action="#{chequesController.seleccionarBanco}" 
								reRender="panelBancoValidar"/>
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="380">
			        	<h:selectOneMenu
			            	style="width: 380px;"
							value="#{chequesController.intBancoCuentaSeleccionado}"
							disabled="#{chequesController.deshabilitarNuevo}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{chequesController.listaBancoCuenta}"
								itemValue="#{sel.id.intItembancocuenta}"
								itemLabel="#{sel.strEtiqueta}"/>
						</h:selectOneMenu>
			       	</rich:column>
				</h:panelGrid>
				
				
				<rich:spacer height="15px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="865">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Validar Datos" reRender="contPanelInferiorC,panelBotonesC,panelMensajeC"
	                    	action="#{chequesController.validarDatos}" style="width:865px"/>
				  	</rich:column>
				</h:panelGrid>
			
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{chequesController.datosValidados}">
					
				<rich:spacer height="5px"/>
					
				<h:panelGrid id="panelCuentaOrigen" columns="6">
					<rich:column width="120">
						<h:outputText value="Cuenta Bancaria :"/>
					</rich:column>
					<rich:column width="160">
						<h:selectOneMenu
							style="width: 160px;"
							disabled="#{chequesController.datosValidados}"
							value="#{chequesController.intBancoSeleccionado}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
			       	</rich:column>
			       	<rich:column width="380">
			               <h:selectOneMenu
			               	style="width: 380px;"
							value="#{chequesController.intBancoCuentaSeleccionado}"
							disabled="#{chequesController.datosValidados}">
							<tumih:selectItems var="sel"
								value="#{chequesController.listaBancoCuenta}"
								itemValue="#{sel.id.intItembancocuenta}"
								itemLabel="#{sel.strEtiqueta}"/>
						</h:selectOneMenu>
			      	</rich:column>
			      	<rich:column width="100" style="text-align: left;">
						<h:outputText value="Documento : "/>
			     	</rich:column>
			        <rich:column width="200">
						<tumih:inputText property="#{chequesController.intTipoDocumentoValidar}"
							size="26"
							style="background-color: #BFBFBF;" readonly="true"
							itemLabel="strDescripcion" itemValue="intIdDetalle"
							cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"/>
			    	</rich:column>	             	
				</h:panelGrid>
					
				<rich:spacer height="3px"/>
					
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Sucursal :"/>
					</rich:column>
					<rich:column width="163">
						<h:inputText size="26" 
							readonly="true" 
							style="background-color: #BFBFBF;"
							value="#{chequesController.sucursalUsuario.juridica.strSiglas} - #{chequesController.subsucursalUsuario.strDescripcion}"/>
					</rich:column>
					<rich:column width="140" style="text-align: left;">
						<h:outputText value="Número de cheque : "/>
					</rich:column>						
					<rich:column width="215">
						<h:inputText size="39"
							value="#{chequesController.intNumeroCheque}" 
							onkeypress="return soloNumerosNaturales(event)"
							disabled="#{chequesController.deshabilitarNuevo || (chequesController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)}"/>
					</rich:column>
					<rich:column width="103" style="text-align: left;">
						<h:outputText value="Fecha de Egreso :"/>
					</rich:column>
					<rich:column width="190">
						<h:inputText size="20" value="#{chequesController.dtFechaActual}" 
							readonly="true" style="background-color: #BFBFBF;font-weight:bold;">
							<f:convertDateTime pattern="dd/MM/yyyy" />
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="5px"/>
				
				<h:panelGrid columns="11" id="panelPersonaC">
					<rich:column width="120">
						<h:outputText value="Persona :"/>
					</rich:column>
					<rich:column width="685">
						<h:inputText readonly="true"
							rendered="#{empty chequesController.personaSeleccionada}"
							style="background-color: #BFBFBF;"
							size="124"/>
						<h:inputText readonly="true"
							rendered="#{chequesController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="DNI : #{chequesController.personaSeleccionada.documento.strNumeroIdentidad} - #{chequesController.personaSeleccionada.natural.strNombreCompleto}"
							style="background-color: #BFBFBF;"
							size="124"/>
						<h:inputText readonly="true"
							rendered="#{chequesController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{chequesController.personaSeleccionada.strRuc} - #{chequesController.personaSeleccionada.juridica.strRazonSocial} - Cuenta : #{chequesController.cuentaActual.strNumeroCuenta}"
							style="background-color: #BFBFBF;"
							size="124"/>
					</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{empty chequesController.personaSeleccionada}"
	                		value="Buscar"
	                		reRender="pBuscarPersonaCheque"
	                    	action="#{chequesController.abrirPopUpBuscarPersona}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarPersonaCheque')"
	                    	style="width:80x"/>
	                    <a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{(not empty chequesController.personaSeleccionada) && transferenciaController.blnVerBotonApoderado}"
	                		value="Quitar"
	                		reRender="contPanelInferiorC"
	                    	action="#{chequesController.quitarPersonaSeleccionada}"
	                    	disabled="#{chequesController.deshabilitarNuevo}"
	                    	style="width:80x"/>
	            	</rich:column>
	            </h:panelGrid>
					
				<rich:spacer height="3px"/>					
				
	            <h:panelGroup id="panelDocumentoC">
	            
		            <h:panelGrid columns="8">
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Tipo de Documento : "/>
				        </rich:column>
				        <rich:column width="175">
							<h:selectOneMenu
								style="width: 160px;"
								disabled="#{(not empty chequesController.listaEgresoDetalleInterfazAgregado)||(chequesController.deshabilitarNuevo)}"
								value="#{chequesController.intTipoDocumentoAgregar}">
								<f:selectItem itemValue="100" itemLabel="Préstamos"/>
								<f:selectItem itemValue="101" itemLabel="AES"/>
								<f:selectItem itemValue="102" itemLabel="Fondo de Sepelio"/>
								<f:selectItem itemValue="103" itemLabel="Fondo de Retiro"/>
								<f:selectItem itemValue="104" itemLabel="Liquidación de cuenta"/>
								<f:selectItem itemValue="305" itemLabel="Planilla de movilidad"/>
							</h:selectOneMenu>
				        </rich:column>
				        <rich:column width="503">
							<h:inputText size="89" 
								readonly="true"
								style="background-color: #BFBFBF;"
								value="#{chequesController.documentoGeneralSeleccionado.strEtiqueta}"/>
						</rich:column>
						<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty chequesController.personaSeleccionada) || (chequesController.deshabilitarNuevo)}"
		                		value="Buscar"
		                		reRender="frmBuscarDocumentoC"
		                    	action="#{chequesController.abrirPopUpBuscarDocumento}"
		                    	oncomplete="Richfaces.showModalPanel('pBuscarDocumentoCheque')"
		                    	style="width:80px"/>
		            	</rich:column>
		            	<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty chequesController.documentoGeneralSeleccionado) || (chequesController.deshabilitarNuevo || chequesController.blnBeneficiarioSinAutorizacion)}"
		                		value="Agregar"
		                		reRender="contPanelInferiorC,panelMontoC,panelDocumentosAgregadosC,panelDocumentoC,panelMensajeC,panelPersonaC"
		                    	action="#{chequesController.agregarDocumento}"
		                    	style="width:80px"/>
		            	</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="8"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{chequesController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{chequesController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{chequesController.listaBeneficiarioPrevision}"
									itemValue="#{sel.id.intItemBeneficiario}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange"
									action="#{chequesController.seleccionarBeneficiario}" 
									reRender="panelDocumentoC,pgMsgErrorBeneficiarioC,panelDocumentosAgregadosC">
								</a4j:support>									
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>

					<h:panelGrid columns="8"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{chequesController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{chequesController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{chequesController.listaBeneficiarioPersona}"
									itemValue="#{sel.intIdPersona}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange"
									action="#{chequesController.seleccionarBeneficiario}" 
									reRender="panelDocumentoC,pgMsgErrorBeneficiarioC,panelDocumentosAgregadosC">
								</a4j:support>		
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgMsgErrorBeneficiarioC" columns="8">
    					<rich:column width="800" style="text-align: left;">
    						<h:outputText value="#{chequesController.strMensajeErrorPorBeneficiario}" styleClass="msgError"/>
    					</rich:column>
	    			</h:panelGrid>					
					
					<!-- Se  suprimio COD01 -->
					<h:panelGrid columns="8" id="panelApoderadoC"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
						<rich:column width="120">
							<h:outputText value="Apoderado :"/>
						</rich:column>
						<rich:column width="360">
							<h:inputText rendered="#{empty chequesController.personaApoderado}" 
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
							<h:inputText rendered="#{not empty chequesController.personaApoderado}" 
								value="DNI : #{chequesController.personaApoderado.documento.strNumeroIdentidad} - #{chequesController.personaApoderado.natural.strNombreCompleto}"
								size="60" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton
								rendered="#{(empty chequesController.personaApoderado) && chequesController.blnVerBotonApoderado}"
								disabled="#{chequesController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Buscar Persona"
				           		action="#{chequesController.abrirPopUpBuscarApoderado}"
				           		reRender="pBuscarPersonaCheque"
				           		oncomplete="Richfaces.showModalPanel('pBuscarPersonaCheque')"
				           		style="width:130px"/>
				           	<a4j:commandButton
								rendered="#{(not empty chequesController.personaApoderado) && chequesController.blnVerBotonApoderado}"
								disabled="#{chequesController.deshabilitarNuevo}"
				                styleClass="btnEstilos"
				                value="Desleccionar Persona"
				                action="#{chequesController.deseleccionarPersonaApoderado}"
				                reRender="panelApoderadoC,panelCartaPoderC"
				                style="width:130px"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="8" id="panelCartaPoderC"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)
						||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
				
							<rich:column width="120">
							</rich:column>
							<rich:column width="360">
								<h:inputText rendered="#{empty chequesController.archivoCartaPoder}" 
									size="60"
									readonly="true" 
									style="background-color: #BFBFBF;"/>
								<h:inputText rendered="#{not empty chequesController.archivoCartaPoder}"
									value="#{chequesController.archivoCartaPoder.strNombrearchivo}"
									size="60"
									readonly="true" 
									style="background-color: #BFBFBF;"/>
							</rich:column>
							<rich:column width="130">
								<a4j:commandButton
									rendered="#{(empty chequesController.archivoCartaPoder) && chequesController.blnVerBotonApoderado}"
									disabled="#{(empty chequesController.personaApoderado) || chequesController.deshabilitarNuevo}"
					           		styleClass="btnEstilos"
					           		value="Adjuntar Carta Poder"
					           		reRender="panelCartaPoderC,pAdjuntarCartaPoderC"
					           		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoderC')"
					           		style="width:130px"/>
					           	<a4j:commandButton
									rendered="#{(not empty chequesController.archivoCartaPoder) && chequesController.blnVerBotonApoderado}"
									disabled="#{chequesController.deshabilitarNuevo}"
					           		styleClass="btnEstilos"
					           		value="Quitar Carta Poder"
					           		reRender="panelCartaPoderC,pAdjuntarCartaPoderC"
					           		action="#{chequesController.quitarCartaPoder}"
					           		style="width:130px"/>
							</rich:column>
							<rich:column width="130" 
								rendered="#{(not empty chequesController.archivoCartaPoder) && (chequesController.deshabilitarNuevo)}">
								<h:commandLink  value=" Descargar"		
									actionListener="#{fileUploadController.descargarArchivo}">
									<f:attribute name="archivo" value="#{chequesController.archivoCartaPoder}"/>							
								</h:commandLink>
							</rich:column>
					</h:panelGrid>
					<h:panelGrid columns="8" id="panelArchivoAdjunto"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)
							||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">

					</h:panelGrid>
					<h:panelGrid columns="6" id="panelPagoBloque"
						rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_REMUNERACION)}">
						<rich:column width="120">						
							<h:outputText value="Pago en Bloque :"/>
						</rich:column>
						<rich:column width="175">
							<h:selectOneMenu
								disabled="#{chequesController.deshabilitarNuevo}"
								style="width: 160px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="503">
							<h:inputText size="83" readonly="true"/>
						</rich:column>
						<rich:column>
							<a4j:commandButton styleClass="btnEstilos"
					       		disabled="#{chequesController.deshabilitarNuevo}"
					       		value="Buscar"
	                   			style="width:80px"/>
						</rich:column>
					</h:panelGrid>
				</h:panelGroup>				
				
				<rich:spacer height="3px"/>				
								
				<!-- Se movio el panel panelPagoBloque a un nivel superior -->
				
				<rich:spacer height="3px"/>
				
				<h:panelGroup id="panelMontoC">
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Monto a Girar :"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="21"	
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{chequesController.bdMontoGirar}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="74"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{chequesController.strMontoGirarDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Diferencial Cambiario :"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="21"
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="20"
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"/>
					</rich:column>
				</h:panelGrid>
				
				</h:panelGroup>
					
				<rich:spacer height="3px"/>
					
				<h:panelGrid columns="6">
					<rich:column width="120" style="vertical-align: top">
						<h:outputText value="Observación :"/>
					</rich:column>
					<rich:column>
						<h:inputTextarea rows="2" cols="126"
							value="#{chequesController.strObservacion}"
							disabled="#{chequesController.deshabilitarNuevo && fondosFijosController.blnHabilitarObservacion}"/>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="1" id="panelDocumentosAgregadosC">
					<rich:column>
						<rich:dataTable
				    		var="item"
				    		styleClass="datatable"
				            value="#{chequesController.listaEgresoDetalleInterfazAgregado}"
					 		sortMode="single"
						  	width="950px"
			                rows="#{fn:length(chequesController.listaEgresoDetalleInterfazAgregado)}">
			                    
							<rich:column width="20px" style="text-align: left">
			            		<h:outputText value="#{item.intOrden}"/>
			            	</rich:column>
			            	<rich:column width="120" style="text-align: left">
			                   	<f:facet name="header">
			                		<h:outputText value="Documento"/>
			                   	</f:facet>
			                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{item.intParaDocumentoGeneral}"/>
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
			                   	<h:outputText value="#{item.persona.natural.strNombreCompleto}"/>
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
			                    <h:outputText value="#{item.bdSubTotal}">
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
			               	<rich:column width="100">
			               		<f:facet name="header">
			                      	<h:outputText value="Adjunto"/>                      		
			                    </f:facet>
								<h:commandLink  value=" Descargar"
									actionListener="#{fileUploadController.descargarArchivo}">
									<f:attribute name="archivo" value="#{item.archivoAdjuntoGiro}"/>
								</h:commandLink>
							</rich:column>
			           	</rich:dataTable>
					</rich:column>
				</h:panelGrid>

			</h:panelGroup>

	</rich:panel>
		
	</h:panelGroup>	

</rich:panel>
</h:form>