<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Junior Chavez   	-->
	<!-- Modulo    : TESORERIA        	-->
	<!-- Fecha     : 19.08.2014			-->


<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="TELECRÉDITO" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="10" width="1050" id="panelPersonaTiltroT">
        		<rich:column width="110">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column width="137" style="text-align: left;">
					<h:selectOneMenu
						value="#{telecreditoController.intTipoPersonaFiltro}"
						style="width: 137px; height : 22px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPersonaTiltroT"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="80" style="text-align: left;">
					<h:selectOneMenu
						rendered="#{telecreditoController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{telecreditoController.intTipoBusquedaPersona}"
						style="width: 80px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{telecreditoController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{telecreditoController.intTipoBusquedaPersona}"
						style="width: 80px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="3" itemLabel="RUC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="135" style="text-align: left;">
					<h:inputText size="20"
						value="#{telecreditoController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="50" style="text-align: left;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column width="80">
					<h:selectOneMenu
						value="#{telecreditoController.egresoFiltro.intParaEstado}"
						style="width: 80px;">
						<tumih:selectItems var="sel" 
							value="#{telecreditoController.listaTablaEstado}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="110" style="text-align: left;">
                	<h:outputText value="Fecha de Egreso: "/>
            	</rich:column>
            	<rich:column width="110" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{telecreditoController.dtDesdeFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="110" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{telecreditoController.dtHastaFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="70" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosT,panelMensajeT"
                    	action="#{telecreditoController.buscar}" 
                    	style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
            
            <h:panelGrid id="panelTablaResultadosT">
	        	<rich:extendedDataTable id="tblResultadoT"
	          		enableContextMenu="false"
	          		sortMode="single"
                    var="item"
                    value="#{telecreditoController.listaEgreso}"
					rowKeyVar="rowKey"
					rows="5"
					width="1020px"
					height="160px"
					align="center">
                                
                    <rich:column width="20" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="150px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro Egreso"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroEgreso}"/>
                	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.dtFechaEgreso}">
                      		<f:convertDateTime pattern="dd/MM/yyyy"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="250" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nombre"/>                      		
                      	</f:facet>
                      	<h:outputText 
							rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="#{item.personaApoderado.natural.strNombreCompleto}"
							title="#{item.personaApoderado.natural.strNombreCompleto}"
						/>
						<h:outputText
							rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="#{item.personaApoderado.juridica.strRazonSocial}"
							title="#{item.personaApoderado.juridica.strRazonSocial}"
						/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Número"/>
                        </f:facet>
                        
                  	</rich:column>
                    <rich:column width="150" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.controlFondosFijos.strNumeroApertura}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto"/>
                        </f:facet>                        
                        <h:outputText value="#{item.bdMontoTotal}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaEstado}"/>
                  	</rich:column>
                   	
                   	<a4j:support event="onRowClick"
                   		actionListener="#{telecreditoController.verRegistro}"
						reRender="panelMensajeT, panelBotonesT, contPanelInferiorT">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoT" maxPages="10"/>   
					</f:facet>
					
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para IMPRIMIR un MOVIMIENTO DE TELECRÉDITO hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeT" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{telecreditoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{telecreditoController.mostrarMensajeExito}"/>
			<h:outputText value="#{telecreditoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{telecreditoController.mostrarMensajeError}"/>	
		</h:panelGroup>
			<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
				id="panelBotonesT">
				<h:panelGrid columns="3">
					<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
						action="#{telecreditoController.habilitarPanelInferior}"
						reRender="contPanelInferiorT,panelMensajeT,panelBotonesT" />                     
				    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
				    	action="#{telecreditoController.grabar}" 
				    	reRender="contPanelInferiorT,panelMensajeT,panelBotonesT,panelTablaResultados"
				    	disabled="#{!telecreditoController.habilitarGrabar}"/>
				    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
				    	action="#{telecreditoController.deshabilitarPanelInferior}"
				    	reRender="contPanelInferiorT,panelMensajeT,panelBotonesT"/>
				</h:panelGrid>
			</h:panelGroup>
			
			
		<a4j:outputPanel id="contPanelInferiorT">
			<rich:panel rendered="#{telecreditoController.mostrarPanelInferior}" style="border:1px solid #17356f;background-color:#DEEBF5;">
			
				<h:panelGroup>
					
					<h:panelGrid columns="1">
						<rich:column width="120">
							<h:outputText value="FONDO FIJO"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="8">
						<rich:column width="175">
							<tumih:inputText
								cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"							
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{telecreditoController.intTipoFondoFijoValidar}"
								readonly="true"
								style="background-color: #BFBFBF;"
								size="25"/>
						</rich:column>
						<rich:column width="130" style="text-align: left;">
							<h:outputText value="Tipo de Documento :"/>
						</rich:column>
						<rich:column width="150">
							<tumih:inputText
								cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION}"
								readonly="true"
								style="background-color: #BFBFBF;"
								size="30"/>
						</rich:column>
						<rich:column width="100" style="text-align: left;" rendered="#{!telecreditoController.habilitarGrabar}">
							<h:outputText value="Egreso :"/>
						</rich:column>
						<rich:column width="150" rendered="#{!telecreditoController.habilitarGrabar}">
							<h:inputText readonly="true"
								value="#{telecreditoController.egresoGeneradoTrasGrabacion.strNumeroEgreso}"
								style="background-color: #BFBFBF; text-align:center;"
								size="23">
							</h:inputText>
						</rich:column>
						<rich:column width="100" style="text-align: left;" rendered="#{!telecreditoController.habilitarGrabar}">
							<h:outputText value="Asiento :"/>
						</rich:column>
						<rich:column width="150" rendered="#{!telecreditoController.habilitarGrabar}">
							<h:inputText readonly="true"
								value="#{telecreditoController.egresoGeneradoTrasGrabacion.strNumeroLibro}"
								style="background-color: #BFBFBF; text-align:center;"
								size="23">
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="8">
						<rich:column width="120">
							<h:outputText value="Sucursal :"/>
						</rich:column>
						<rich:column width="300">
							<h:inputText readonly="true"
								value="#{telecreditoController.sucursalUsuario.juridica.strSiglas} - #{telecreditoController.subsucursalUsuario.strDescripcion}"
								style="background-color: #BFBFBF;"
								size="60"/>
						</rich:column>
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Fecha de Egreso :"/>
						</rich:column>
						<rich:column width="150">
							<h:inputText readonly="true"
								value="#{telecreditoController.egresoNuevo.dtFechaEgreso}"
								style="background-color: #BFBFBF;"
								size="30">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:inputText>
						</rich:column>
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Forma de Pago :"/>
						</rich:column>
						<rich:column width="150">
							<h:inputText readonly="true"
								value="Tranferencia" 
								style="background-color: #BFBFBF;"
								size="20"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="8px"/>
					
					<h:panelGrid columns="1">
						<rich:column width="200">
							<h:outputText value="DOCUMENTO A CANCELAR"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGroup id="panelPersonaT">
					
					<h:panelGrid columns="11" >
						<rich:column width="120">
							<h:outputText value="Persona :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText readonly="true"
								rendered="#{empty telecreditoController.personaSeleccionada}"
								style="background-color: #BFBFBF;"
								size="124"/>
							<h:inputText readonly="true"
								rendered="#{(telecreditoController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)
								&& (empty telecreditoController.cuentaActual)}"
								value="DNI : #{telecreditoController.personaSeleccionada.documento.strNumeroIdentidad} - #{telecreditoController.personaSeleccionada.natural.strNombreCompleto}"
								style="background-color: #BFBFBF;"
								size="124"/>
							<h:inputText readonly="true"
								rendered="#{(telecreditoController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)
								&& (not empty telecreditoController.cuentaActual)}"
								value="DNI : #{telecreditoController.personaSeleccionada.documento.strNumeroIdentidad} - #{telecreditoController.personaSeleccionada.natural.strNombreCompleto} - Cuenta : #{telecreditoController.cuentaActual.strNumeroCuenta}"
								style="background-color: #BFBFBF;"
								size="124"/>
							<h:inputText readonly="true"
								rendered="#{telecreditoController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
								value="RUC : #{telecreditoController.personaSeleccionada.strRuc} - #{telecreditoController.personaSeleccionada.juridica.strRazonSocial}"
								style="background-color: #BFBFBF;"
								size="124"/>
						</rich:column>
		            	<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		rendered="#{empty telecreditoController.personaSeleccionada}"
		                		value="Buscar"
		                		reRender="pBuscarPersonaT"
		                    	action="#{telecreditoController.abrirPopUpBuscarPersona}"
		                    	oncomplete="Richfaces.showModalPanel('pBuscarPersonaT')"
		                    	style="width:80x; height : 24px;"/>
		                    <a4j:commandButton styleClass="btnEstilos"
		                		rendered="#{not empty telecreditoController.personaSeleccionada}"
		                		value="Quitar"
		                		reRender="contPanelInferiorT"
		                    	action="#{telecreditoController.quitarPersonaSeleccionada}"
		                    	disabled="#{telecreditoController.deshabilitarNuevo}"
		                    	style="width:80x"/>
		            	</rich:column>
		            </h:panelGrid>
		            
		            <rich:spacer height="3px"/>
		            
		            <h:panelGrid id="panelCuentaT" columns="6">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText size="110" 
								rendered="#{empty telecreditoController.cuentaBancariaSeleccionada}"
								style="background-color: #BFBFBF;font-weight:bold;"
								readonly="true"/>
							<h:inputText size="110" 
								rendered="#{not empty telecreditoController.cuentaBancariaSeleccionada}"
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{telecreditoController.cuentaBancariaSeleccionada.strEtiqueta}"/>
						</rich:column>
						<rich:column width="80">
							<a4j:commandButton styleClass="btnEstilos"
                				disabled="#{telecreditoController.deshabilitarNuevo}"
                				value="Buscar"
                				reRender="pBuscarCuentaBancariaT"
				        		oncomplete="Richfaces.showModalPanel('pBuscarCuentaBancariaT')"
                    			style="width:80px"/>
						</rich:column>
					</h:panelGrid>
		            
		            <rich:spacer height="3px"/>
		            
		            <h:panelGrid columns="11" rendered="#{telecreditoController.habilitarGrabar}">
						<rich:column width="120">
							<h:outputText value="Roles :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText readonly="true"
								value="#{telecreditoController.strListaPersonaRolUsar}"
								style="background-color: #BFBFBF;"
								size="124"/>
						</rich:column>
					</h:panelGrid>
		            
		            </h:panelGroup>
		            
		            <rich:spacer height="3px"/>
		            
		            
		            <h:panelGroup id="panelDocumentoT">
		            
		            <h:panelGrid columns="8">
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Tipo de Documento : "/>
				        </rich:column>
				        <rich:column width="175">
							<h:selectOneMenu
								style="width: 160px;"
								disabled="#{(not empty telecreditoController.listaEgresoDetalleInterfazAgregado)||(telecreditoController.deshabilitarNuevo)}"
								value="#{telecreditoController.intTipoDocumentoAgregar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{telecreditoController.listaTablaTipoDocumento}"
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>							
				        </rich:column>
				        <rich:column width="503">
							<h:inputText size="89" 
								readonly="true"
								style="background-color: #BFBFBF;"
								value="#{telecreditoController.documentoGeneralSeleccionado.strEtiqueta}"/>
						</rich:column>
						<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty telecreditoController.personaSeleccionada) || (telecreditoController.deshabilitarNuevo)}"
		                		value="Buscar"
		                		reRender="frmBuscarDocumentoT"
		                    	action="#{telecreditoController.abrirPopUpBuscarDocumento}"
		                    	oncomplete="Richfaces.showModalPanel('pBuscarDocumentoT')"
		                    	style="width:80px"/>
		            	</rich:column>
		            	<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty telecreditoController.documentoGeneralSeleccionado) || (telecreditoController.deshabilitarNuevo || telecreditoController.blnBeneficiarioSinAutorizacion)}"
		                		value="Agregar"
		                		reRender="contPanelInferiorT,panelMontoT,panelDocumentosAgregadosT,panelDocumentoT,panelMensajeT"
		                    	action="#{telecreditoController.agregarDocumento}"
		                    	style="width:80px"/>
		            	</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="8"
						rendered="#{(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{telecreditoController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{telecreditoController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{telecreditoController.listaBeneficiarioPrevision}"
									itemValue="#{sel.id.intItemBeneficiario}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange"
									action="#{telecreditoController.seleccionarBeneficiario}" 
									reRender="panelApoderadoT,pgMsgErrorBeneficiarioF,panelDocumentosAgregadosT,panelDocumentoT"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="8"
						rendered="#{(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{telecreditoController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{telecreditoController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{telecreditoController.listaBeneficiarioPersona}"
									itemValue="#{sel.intIdPersona}"
									itemLabel="#{sel.strEtiqueta}"/>
							
								<a4j:support event="onchange"
									action="#{telecreditoController.seleccionarBeneficiario}" 
									reRender="panelApoderadoT,pgMsgErrorBeneficiarioF,panelDocumentosAgregadosT,panelDocumentoT"/>
							</h:selectOneMenu>						
						</rich:column>
					</h:panelGrid>
	
					<h:panelGrid id="pgMsgErrorBeneficiarioF" columns="8">
	   					<rich:column width="800" style="text-align: left;">
	   						<h:outputText value="#{telecreditoController.strMensajeErrorPorBeneficiario}" styleClass="msgError"/>
	   					</rich:column>
	    			</h:panelGrid>
	    			
					<h:panelGrid columns="8" id="panelApoderadoT"
						rendered="#{(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120">
							<h:outputText value="Apoderado :"/>
						</rich:column>
						<rich:column width="360">
							<h:inputText rendered="#{empty telecreditoController.personaApoderado}" 
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
							<h:inputText rendered="#{not empty telecreditoController.personaApoderado}" 
								value="DNI : #{telecreditoController.personaApoderado.documento.strNumeroIdentidad} - #{telecreditoController.personaApoderado.natural.strNombreCompleto}"
								size="60" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton
								rendered=	"#{(empty telecreditoController.personaApoderado) && telecreditoController.blnVerBotonApoderado}"
								disabled="#{telecreditoController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Buscar Persona"
				           		action="#{telecreditoController.abrirPopUpBuscarApoderado}"
				           		reRender="pBuscarPersona"
				           		oncomplete="Richfaces.showModalPanel('pBuscarPersona')"
				           		style="width:130px"/>
				           	<a4j:commandButton
								rendered="#{(not empty telecreditoController.personaApoderado) && telecreditoController.blnVerBotonApoderado}"
								disabled="#{telecreditoController.deshabilitarNuevo}"
				                styleClass="btnEstilos"
				                value="Quitar"
				                action="#{telecreditoController.quitarPersonaApoderado}"
				                reRender="panelApoderadoT,panelCartaPoderT"
				                style="width:130px"/>
						</rich:column>					
					</h:panelGrid>
					
					<h:panelGrid columns="8" id="panelCartaPoderT"
						rendered="#{(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(telecreditoController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120">
						</rich:column>
						<rich:column width="360">
							<h:inputText rendered="#{empty telecreditoController.archivoCartaPoder}" 
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
							<h:inputText rendered="#{not empty telecreditoController.archivoCartaPoder}"
								value="#{telecreditoController.archivoCartaPoder.strNombrearchivo}"
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton
								rendered="#{(empty telecreditoController.archivoCartaPoder) && telecreditoController.blnVerBotonApoderado}"
								disabled="#{(empty telecreditoController.personaApoderado) || telecreditoController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Adjuntar Carta Poder"
				           		reRender="pAdjuntarCartaPoderF,panelCartaPoderT"
				           		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoderF')"
				           		style="width:130px"/>
				           	<a4j:commandButton
								rendered="#{(not empty telecreditoController.archivoCartaPoder) && telecreditoController.blnVerBotonApoderado}"
								disabled="#{telecreditoController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Quitar Carta Poder"
				           		reRender="pAdjuntarCartaPoderF,panelCartaPoderT"
				           		action="#{telecreditoController.quitarCartaPoder}"
				           		style="width:130px"/>
						</rich:column>
						<rich:column width="130" 
							rendered="#{(not empty telecreditoController.archivoCartaPoder)&&(telecreditoController.deshabilitarNuevo)}">
							<h:commandLink  value=" Descargar"		
								actionListener="#{fileUploadController.descargarArchivo}">
								<f:attribute name="archivo" value="#{telecreditoController.archivoCartaPoder}"/>							
							</h:commandLink>
						</rich:column>
					</h:panelGrid>
					
					</h:panelGroup>
					
					<rich:spacer height="3px"/>
					
					<h:panelGroup id="panelMontoT">
					
					<h:panelGrid columns="6">
						<rich:column width="120">
							<h:outputText value="Monto a Girar :"/>
						</rich:column>
						<rich:column width="175">
							<h:inputText size="20"	
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{telecreditoController.bdMontoGirar}">
								<f:converter converterId="ConvertidorMontos" />
							</h:inputText>
						</rich:column>
						<rich:column width="350">
							<h:inputText size="74"
								readonly="true"
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{telecreditoController.strMontoGirarDescripcion}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6" rendered="#{telecreditoController.bdDiferencialGirar > 0}">
						<rich:column width="120">
							<h:outputText value="Diferencial Cambiario :"/>
						</rich:column>
						<rich:column width="175">
							<h:inputText size="20"
								readonly="true"
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{telecreditoController.bdDiferencialGirar}">
								<f:converter converterId="ConvertidorMontos" />
							</h:inputText>
						</rich:column>
						<rich:column width="350">
							<h:inputText size="74"
								readonly="true"
								style="background-color: #BFBFBF;font-weight:bold; width : 546px;"
								value="#{telecreditoController.strDiferencialGirarDescripcion}"/>
						</rich:column>
					</h:panelGrid>
					
					</h:panelGroup>
					
					<rich:spacer height="3px"/>
					<h:panelGroup>
						<h:panelGrid columns="6">
							<rich:column width="120" style="vertical-align: top">
								<h:outputText value="Observación :"/>
							</rich:column>
							<rich:column>
								<h:inputTextarea rows="2" cols="124"
									value="#{telecreditoController.strObservacion}"
									disabled="#{telecreditoController.deshabilitarNuevo && telecreditoController.blnHabilitarObservacion}"/>
							</rich:column>
						</h:panelGrid>	
						
						<rich:spacer height="8px"/>
						
						<h:panelGrid columns="1" id="panelDocumentosAgregadosT">
							<rich:column>
								<rich:dataTable
						    		var="item"
						    		styleClass="datatable"
						            value="#{telecreditoController.listaEgresoDetalleInterfazAgregado}"
							 		sortMode="single"
								  	width="950px"
					                rows="#{fn:length(telecreditoController.listaEgresoDetalleInterfazAgregado)}">
					                    
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
					                   	<h:outputText value="#{item.persona.natural.strNombreCompleto}"
					                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
					                   	<h:outputText value="#{item.persona.juridica.strRazonSocial}" 
					                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>	
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
										<h:commandLink  value=" Descargar" rendered="#{item.archivoAdjuntoGiro != null}"
											actionListener="#{fileUploadController.descargarArchivo}">
											<f:attribute name="archivo" value="#{item.archivoAdjuntoGiro}"/>
										</h:commandLink>
									</rich:column>			               	
					           	</rich:dataTable>
							</rich:column>
						</h:panelGrid>				
					</h:panelGroup>
					
				</h:panelGroup>
				
			</rich:panel>
	
		</a4j:outputPanel>
	</rich:panel>
</h:form>
