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
	<!-- Fecha modificación : 27.01.2014 -->



	<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="GIRO DE LIQUIDACIÓN" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>        	

			<h:panelGrid columns="7" id="panelGiroPersonaFiltro">
				<rich:column width="100">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroLiquidacionController.intTipoPersonaFiltro}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
						<a4j:support event="onchange" reRender="panelGiroPersonaFiltro"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						rendered="#{giroLiquidacionController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{giroLiquidacionController.intTipoBusquedaPersonaFiltro}"
						style="width: 170px;">
						<f:selectItem itemLabel="Codigo" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA}"/>
						<f:selectItem itemLabel="Apellidos y Nombres" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_APELLIDOSNOMBRES}"/>
						<f:selectItem itemLabel="Documento" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO}"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{giroLiquidacionController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{giroLiquidacionController.intTipoBusquedaPersonaFiltro}"
						style="width: 170px;">
						<f:selectItem itemLabel="Codigo" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA}"/>
						<f:selectItem itemLabel="Documento" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:inputText size="60" value="#{giroLiquidacionController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column>
					<h:outputText value="Nro. Solicitud	:"/>
				</rich:column>
				<rich:column>
					<h:inputText size="15" value="#{giroLiquidacionController.intItemExpedienteFiltro}"/>
				</rich:column>
			</h:panelGrid>
            
			<h:panelGrid columns="10">
				<rich:column width="100">
					<h:outputText value="Condición :"/>
				</rich:column>
				<rich:column width="155">
					<h:selectOneMenu
						value="#{giroLiquidacionController.estadoLiquidacionFiltro.intParaEstado}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaTablaEstadoPago}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="40">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						value="#{giroLiquidacionController.intTipoCreditoFiltro}"
						style="width: 127px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaTablaTipoDocumento}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroLiquidacionController.intTipoBusquedaFechaFiltro}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaTablaEstadoPago}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="75">
					<h:outputText value="Fecha :"/>
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" 
						enableManualInput="true"
						datePattern="dd/MM/yyyy" 
						inputStyle="width:70px;"
						value="#{giroLiquidacionController.estadoLiquidacionFiltro.dtFechaEstadoDesde}"/>
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" 
						enableManualInput="true"
						datePattern="dd/MM/yyyy" 
						inputStyle="width:70px;"
						value="#{giroLiquidacionController.estadoLiquidacionFiltro.dtFechaEstadoHasta}"/>
				</rich:column>
			</h:panelGrid>
		
			<h:panelGrid columns="8" id="panelGiroSucursalBus">
				<rich:column width="100">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroLiquidacionController.intTipoBusquedaSucursal}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaTipoBusquedaSucursal}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange"
							action="#{giroLiquidacionController.seleccionarTipoBusquedaSucursal}" 
							reRender="panelGiroSucursalBus"/>						
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="172">
					<h:selectOneMenu
						value="#{giroLiquidacionController.intIdSucursalFiltro}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaTablaSucursal}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange"
							action="#{giroLiquidacionController.seleccionarSucursal}" 
							reRender="panelGiroSucursalBus"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="85">
					<h:outputText value="Sub-Sucursal :" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroLiquidacionController.intIdSubsucursalFiltro}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton
                		styleClass="btnEstilos"
                		value="Buscar"
                		reRender="panelGiroTablaResultados,panelMensajeGiro"
                    	action="#{giroLiquidacionController.buscar}"
                    	style="width:100px"/>
            	</rich:column>
			</h:panelGrid>
			
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelGiroTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{giroLiquidacionController.listaExpedienteLiquidacion}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="165px" 
					align="center">
					
					<rich:column width="10" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nro. Solicitud"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemExpediente}"/>
                  	</rich:column>
					<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.personaAdministra.intIdPersona}"/>
                    </rich:column>
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nombre"/>                 		
                      	</f:facet>
                      	<h:outputText rendered="#{item.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="#{item.personaAdministra.natural.strNombreCompleto}" title="#{item.personaAdministra.natural.strNombreCompleto}"/>
						<h:outputText rendered="#{item.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="#{item.personaAdministra.juridica.strRazonSocial}" title="#{item.personaAdministra.juridica.strRazonSocial}"/>						
                  	</rich:column>                  	
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Documento"/>          		
                      	</f:facet>
						<h:outputText rendered="#{item.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="DNI:#{item.personaAdministra.documento.strNumeroIdentidad}" 
							title="DNI:#{item.personaAdministra.documento.strNumeroIdentidad}"/>
						<h:outputText rendered="#{item.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC:#{item.personaAdministra.strRuc}" 
							title="RUC:#{item.personaAdministra.strRuc}"/>						
                  	</rich:column>                  	
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.estadoLiquidacionUltimo.sucursal.juridica.strSiglas}"/>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.estadoLiquidacionUltimo.intParaEstado}"/>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo Cuentas"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strEtiquetaListaCuentas}" title="#{item.strEtiquetaListaCuentas}"/>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto Total"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoBrutoLiquidacion}">
                      		 <f:converter converterId="ConvertidorMontos" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Usuario Giro"/>
                      	</f:facet>
                      	<h:outputText rendered="#{item.estadoLiquidacionUltimo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO}"
                      		value="#{item.estadoLiquidacionUltimo.persona.intIdPersona} - #{item.estadoLiquidacionUltimo.persona.Natural.strNombreCompleto}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Giro"/>
                      	</f:facet>
                      	<h:outputText rendered="#{item.estadoLiquidacionUltimo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO}" 
                      		value="#{item.estadoLiquidacionUltimo.tsFechaEstado}">
                      		<f:convertDateTime pattern="dd-MM-yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>
					</f:facet>
					
                   	<a4j:support event="onRowClick"
                   		actionListener="#{giroLiquidacionController.verRegistro}"
						reRender="contPanelGiroInferior,panelMensajeGiro,panelGiroBotones">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			
				          	             
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver o Girar una SOLICITUD DE LIQUIDACIÓN hacer click en el Registro." style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeGiro" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{giroLiquidacionController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{giroLiquidacionController.mostrarMensajeExito}"/>
			<h:outputText value="#{giroLiquidacionController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{giroLiquidacionController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelGiroBotones">
			<h:panelGrid columns="3">
			<!-- 
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{giroLiquidacionController.grabar}" 
			    	reRender="contPanelGiroInferior,panelMensajeGiro,panelGiroBotones,panelGiroTablaResultados"
			    	disabled="#{!giroLiquidacionController.habilitarGrabar}"/>    
			     -->
			    <!-- JCHAVEZ 08.02.2014 -->
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
					rendered="#{!giroLiquidacionController.visualizarGrabarRequisito}"
			    	action="#{giroLiquidacionController.grabar}" 
			    	reRender="contPanelGiroInferior,panelMensajeGiro,panelGiroBotones,panelGiroTablaResultados"
			    	disabled="#{!giroLiquidacionController.habilitarGrabar}"/>
			    	
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	rendered="#{giroLiquidacionController.visualizarGrabarRequisito}"
			    	action="#{giroLiquidacionController.grabarRequisito}" 
			    	reRender="contPanelGiroInferior,panelMensajeGiro,panelGiroBotones,panelGiroTablaResultados"
			    	disabled="#{!giroLiquidacionController.habilitarGrabarRequisito}"/>  
			    					    	   												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{giroLiquidacionController.deshabilitarPanelInferior}" 
			    	reRender="contPanelGiroInferior,panelMensajeGiro,panelGiroBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelGiroInferior">
		<!-- JCHAVEZ 08.02.2014 Adjunto de Requisito para el Giro de Liquidación -->
		<rich:panel  rendered="#{giroLiquidacionController.mostrarPanelAdjuntoGiro}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
	   		<h:panelGroup id="panelMensajeRequisito" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
				styleClass="rich-tabcell-noborder">
				<h:outputText value="#{giroLiquidacionController.mensajeAdjuntarRequisito}" 
					styleClass="msgError"
					style="font-weight:bold"
					rendered="#{giroLiquidacionController.mostrarMensajeAdjuntarRequisito}"/>	
			</h:panelGroup>	
			   
		   <h:panelGrid columns="8" id="panelAdjuntoRequisito">
				<rich:column width="120">
					<h:outputText value="Adjunto Giro : "/>       
				</rich:column>
				<rich:column width="150">
					<h:inputText rendered="#{empty giroLiquidacionController.archivoAdjuntoGiro}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty giroLiquidacionController.archivoAdjuntoGiro}"
						value="#{giroLiquidacionController.archivoAdjuntoGiro.strNombrearchivo}"
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120" rendered="#{giroLiquidacionController.visualizarGrabarAdjunto}">
					<a4j:commandButton
						rendered="#{empty giroLiquidacionController.archivoAdjuntoGiro}"
                		styleClass="btnEstilos"
                		value="Adjunto de Giro"
                		reRender="pAdjuntarGiroLiquidacion, panelAdjuntoRequisito"
                		oncomplete="Richfaces.showModalPanel('pAdjuntarGiroLiquidacion')"
                		style="width:130px"/>                 		
                	<a4j:commandButton
						rendered="#{not empty giroLiquidacionController.archivoAdjuntoGiro}"
						disabled="#{giroLiquidacionController.deshabilitarNuevoBeneficiario}"
                		styleClass="btnEstilos"
                		value="Quitar Adjunto de Giro"
                		reRender="pAdjuntarGiroLiquidacion,panelAdjuntoRequisito"
                		action="#{giroLiquidacionController.quitarAdjuntoGiro}"
                		style="width:130px"/>
				</rich:column>
				<rich:column width="130" 
					rendered="#{(not empty giroLiquidacionController.archivoAdjuntoGiro)&&(giroLiquidacionController.deshabilitarDescargaAdjuntoGiro)}">
					<h:commandLink  value=" Descargar"
						actionListener="#{fileUploadController.descargarArchivo}">
						<f:attribute name="archivo" value="#{giroLiquidacionController.archivoAdjuntoGiro}"/>
					</h:commandLink>
				</rich:column>
           </h:panelGrid>
		</rich:panel>
		<rich:spacer height="4"></rich:spacer>
		<!-- FIN AGREGADO 08.02.2014 -->
		
		<rich:panel  rendered="#{giroLiquidacionController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Fecha de Giro :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroLiquidacionController.dtFechaActual}" 
						readonly="true" 
						style="background-color: #BFBFBF;">
						<f:convertDateTime pattern="dd/MM/yyyy"/>
					</h:inputText>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Forma de Pago :"/>
				</rich:column>
				<rich:column width="150">
					<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_FORMAPAGOEGRESO}" 
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion"
						readonly="true" 
						style="background-color: #BFBFBF;"
						property="#{applicationScope.Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO}"/>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Tipo de Operación :"/>
				</rich:column>
				<rich:column width="200">
					<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION}" 
						size="30"
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						readonly="true" 
						style="background-color: #BFBFBF;"
						property="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Sucursal de Giro :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroLiquidacionController.controlFondosFijosGirar.sucursal.juridica.strSiglas}"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Subsucursal :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroLiquidacionController.controlFondosFijosGirar.subsucursal.strDescripcion}"
						readonly="true"
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Nro Expediente :"/>
				</rich:column>
				<rich:column width="200">
					<h:inputText value="#{giroLiquidacionController.expedienteLiquidacionGirar.id.intItemExpediente}"
						size="30"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" rendered="#{giroLiquidacionController.intTipoMostrarFondoCambio==giroLiquidacionController.MOSTRAR_FONDOCAMBIO_SINGULAR}">
				<rich:column width="120">
					<h:outputText value="Fondo de Cambio :"/>
				</rich:column>
				<rich:column width="270">
					<h:inputText size="77"
						value="#{giroLiquidacionController.controlFondosFijosGirar.strNumeroApertura}"
						readonly="true"
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="250">
					<h:outputText value="Monto Asignado : "/>
					<h:outputText value="#{giroLiquidacionController.controlFondosFijosGirar.bdMontoApertura}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
					<h:outputText value=" Saldo : "/>
					<h:outputText value="#{giroLiquidacionController.controlFondosFijosGirar.bdMontoSaldo}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" rendered="#{giroLiquidacionController.intTipoMostrarFondoCambio==giroLiquidacionController.MOSTRAR_FONDOCAMBIO_PLURAL}"
				id="panelFondoPlural">
				<rich:column width="120">
					<h:outputText value="Fondo de Cambio :"/>
				</rich:column>
				<rich:column width="270">
					<h:selectOneMenu
						value="#{giroLiquidacionController.intItemControlFondoSeleccionar}"
						style="width: 260px;">
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.listaControl}"
							itemValue="#{sel.id.intItemFondoFijo}"
							itemLabel="#{sel.strNumeroApertura}"/>
						<a4j:support event="onchange" 
							reRender="panelFondoPlural"
							action="#{giroLiquidacionController.seleccionarControlFondo}"/>					
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="250">
					<h:outputText value="Monto Asignado : "/>
					<h:outputText rendered ="#{not empty giroLiquidacionController.controlFondosFijosGirar.bdMontoApertura}" 
						value="#{giroLiquidacionController.controlFondosFijosGirar.bdMontoApertura}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
					<h:outputText value=" Saldo : "/>
					<h:outputText rendered ="#{not empty giroLiquidacionController.controlFondosFijosGirar.bdMontoSaldo}" 
						value="#{giroLiquidacionController.controlFondosFijosGirar.bdMontoSaldo}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
			</h:panelGrid>			

			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Socio/Cliente :"/>
				</rich:column>
				<rich:column>
					<h:inputText rendered="#{giroLiquidacionController.personaGirar.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="DNI : #{giroLiquidacionController.personaGirar.documento.strNumeroIdentidad} - #{giroLiquidacionController.personaGirar.natural.strNombreCompleto}" 
						size="77"
						readonly="true"
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{giroLiquidacionController.personaGirar.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{giroLiquidacionController.personaGirar.juridica.strRazonSocial}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Beneficiario :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroLiquidacionController.intIdPersonaBeneficiarioSeleccionar}"
						disabled="#{giroLiquidacionController.deshabilitarNuevoBeneficiario}" 
						style="width: 500px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroLiquidacionController.expedienteLiquidacionGirar.listaPersona}"
							itemValue="#{sel.intIdPersona}"
							itemLabel="#{sel.strEtiqueta}"/>
						<a4j:support event="onchange"
							reRender="contPanelGiroInferior, panelMensajeRequisito, panelDetalleEgreso,panelTotalGeneral,panelGiroBotones"
							action="#{giroLiquidacionController.seleccionarBeneficiario}"/>					
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>			
			
			<h:panelGrid columns="8" id="panelApoderadoG">
				<rich:column width="120">
					<h:outputText value="Apoderado :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText rendered="#{empty giroLiquidacionController.personaApoderado}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty giroLiquidacionController.personaApoderado}" 
						value="DNI : #{giroLiquidacionController.personaApoderado.documento.strNumeroIdentidad} - #{giroLiquidacionController.personaApoderado.natural.strNombreCompleto}"
						size="77" 
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<a4j:commandButton
						rendered="#{empty giroLiquidacionController.personaApoderado}"
						disabled="#{giroLiquidacionController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Buscar Persona"
                		action="#{giroLiquidacionController.abrirPopUpBuscarApoderado}"
                		reRender="pBuscarApoderado"
                		oncomplete="Richfaces.showModalPanel('pBuscarApoderado')"
                		style="width:130px"/>
                	<a4j:commandButton
						rendered="#{not empty giroLiquidacionController.personaApoderado}"
						disabled="#{giroLiquidacionController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Desleccionar Persona"
                		action="#{giroLiquidacionController.deseleccionarPersonaApoderado}"
                		reRender="panelApoderadoG,panelCartaPoderG"
                		style="width:130px"/>
				</rich:column>
			</h:panelGrid>
			
			
			<h:panelGrid columns="8" id="panelCartaPoderG">
				<rich:column width="120">
				</rich:column>
				<rich:column width="150">
					<h:inputText rendered="#{empty giroLiquidacionController.archivoCartaPoder}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty giroLiquidacionController.archivoCartaPoder}"
						value="#{giroLiquidacionController.archivoCartaPoder.strNombrearchivo}"
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<a4j:commandButton
						rendered="#{empty giroLiquidacionController.archivoCartaPoder}"
						disabled="#{(empty giroLiquidacionController.personaApoderado) || giroLiquidacionController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Adjuntar Carta Poder"
                		reRender="frmAjuntarCartaPoder"
                		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoder')"
                		style="width:130px"/>
                	<a4j:commandButton
						rendered="#{not empty giroLiquidacionController.archivoCartaPoder}"
						disabled="#{giroLiquidacionController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Quitar Carta Poder"
                		reRender="panelCartaPoderG"
                		action="#{giroLiquidacionController.quitarCartaPoder}"
                		style="width:130px"/>
				</rich:column>
			</h:panelGrid>
						
			<h:panelGrid columns="8">
				<rich:column width="120" style="vertical-align: top">
					<h:outputText value="Glosa del Egreso :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputTextarea disabled="#{giroLiquidacionController.deshabilitarNuevo}" 
						cols="77" 
						rows="2" 
						value="#{giroLiquidacionController.strGlosaEgreso}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Detalle del Egreso :"/>
				</rich:column>				
			</h:panelGrid>
			
			<h:panelGrid columns="8" id="panelDetalleEgreso">
				<rich:column width="120"/>
				<rich:column>
					<h:panelGrid>
			        	<rich:dataTable
			          		sortMode="single" 
		                    var="item"
		                    value="#{giroLiquidacionController.listaEgresoDetalleInterfaz}"  
							rowKeyVar="rowKey"
							width="800px"
							align="center"
							rows="#{fn:length(giroLiquidacionController.listaEgresoDetalleInterfaz)}">
		                    
							<rich:column width="20px" style="text-align: center">
		                    	<h:outputText value="#{rowKey + 1}"/>
		                    </rich:column>
							<rich:column width="160px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Concepto"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.expedienteLiquidacionDetalle.cuentaConcepto.detalle.captacion.strDescripcion}"/>
		                    </rich:column>
		                  	<rich:column width="80" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Nro. Solicitud"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.strNroSolicitud}"/>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Capital"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdCapital}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="80" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Interés"/>      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdInteres}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="80" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Mora"/>                    		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdMora}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Gastos Adm."/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdGastosAdministrativos}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="80" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Otros"/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdOtros}"/>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Subtotal"/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdSubTotal}">
		                      		<f:converter converterId="ConvertidorMontos"/>
		                      	</h:outputText>
		                  	</rich:column>
		            	</rich:dataTable>		            	
		         	</h:panelGrid>
		         	
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelTotalGeneral">
				<rich:column width="120">
					<h:outputText value="Total General :"/>
				</rich:column>
				<rich:column>
					<h:inputText size ="15" 
						value="#{giroLiquidacionController.bdTotalEgresoDetalleInterfaz}" 
						readonly="true" 
						style="background-color: #BFBFBF;">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:panelGroup>	

</rich:panel>

</h:form>

<script type="text/javascript">
function soloNumerosNaturales(evt)
{
	var e = (window.event)?event:evt; // for cross browser compatibility
	var charCode = e.which || e.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
		return false;
	}
	return true;
}

function soloNumerosDecimales(evt)
{
	var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
    	return false;
	return true;
}
	
	function validarNumDocIdentidad(obj,event,ancho){
		return ( event.ctrlKey || event.altKey 
	            || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false && obj.value.length<ancho) 
	            || (95<event.keyCode && event.keyCode<106 && obj.value.length<ancho)
	            || (event.keyCode==8) || (event.keyCode==9) 
	            || (event.keyCode>34 && event.keyCode<40) 
	            || (event.keyCode==46) );
	}
	
</script>