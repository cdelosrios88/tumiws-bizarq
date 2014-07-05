<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	 -->
	<!-- Autor     : Arturo Julca   	 -->
	<!-- Modulo    :                	 -->
	<!-- Fecha     :                	 -->
	<!-- Modificado por : Junior Chávez  -->
	<!-- Fecha modificación : 24.12.2013 -->



   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="GIRO DE SOLICITUD DE PRÉSTAMO" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>        	

			<h:panelGrid columns="7" id="panelPersonaFiltro">
				<rich:column width="105">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroCreditoController.intTipoPersonaFiltro}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
						<a4j:support event="onchange" reRender="panelPersonaFiltro"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						rendered="#{giroCreditoController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{giroCreditoController.intTipoBusquedaPersonaFiltro}"
						style="width: 170px;">
						<f:selectItem itemLabel="Codigo" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA}"/>
						<f:selectItem itemLabel="Apellidos y Nombres" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_APELLIDOSNOMBRES}"/>
						<f:selectItem itemLabel="Documento" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO}"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{giroCreditoController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{giroCreditoController.intTipoBusquedaPersonaFiltro}"
						style="width: 170px;">
						<f:selectItem itemLabel="Codigo" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA}"/>
						<f:selectItem itemLabel="Documento" itemValue="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:inputText size="54" value="#{giroCreditoController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="80">
					<h:outputText value="Nro. Solicitud	:"/>
				</rich:column>
				<rich:column>
					<h:inputText size="25" value="#{giroCreditoController.intItemExpedienteFiltro}"/>
				</rich:column>
			</h:panelGrid>
            
            <h:panelGrid columns="8" id="panelSucursalBus">
				<rich:column width="105">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroCreditoController.intTipoBusquedaSucursal}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaTipoBusquedaSucursal}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>					
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="172">
					<h:selectOneMenu
						value="#{giroCreditoController.intIdSucursalFiltro}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaTablaSucursal}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" 
							action="#{giroCreditoController.seleccionarSucursal}" 
							reRender="panelSucursalBus"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="93">
					<h:outputText value="Sub-Sucursal :" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroCreditoController.intIdSubsucursalFiltro}"
						style="width: 205px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="7">
				<rich:column width="105px">
					<h:outputText value="Tipo de Préstamo :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroCreditoController.intTipoCreditoFiltro}"
						style="width: 150px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{giroCreditoController.estadoCreditoFiltro.intParaEstadoCreditoCod}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaTablaEstadoPago}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="115">
					<rich:calendar popup="true" 
						enableManualInput="true"
						datePattern="dd/MM/yyyy" 
						inputStyle="width:70px;"
						value="#{giroCreditoController.estadoCreditoFiltro.dtFechaEstadoDesde}"/>
				</rich:column>
				<rich:column width="115">
					<rich:calendar popup="true" 
						enableManualInput="true"
						datePattern="dd/MM/yyyy" 
						inputStyle="width:70px;"
						value="#{giroCreditoController.estadoCreditoFiltro.dtFechaEstadoHasta}"/>
				</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton 
                		styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultados,panelMensajeGiro"
                    	action="#{giroCreditoController.buscar}" 
                    	style="width:100px"/>
            	</rich:column>
			</h:panelGrid>
		
        	<h:panelGrid columns="11">
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{giroCreditoController.listaExpedienteCredito}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                                
					<rich:column width="20px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro. Cuenta"/>
                      	</f:facet>
                      	<h:outputText value="#{item.cuenta.strNumeroCuenta}"/>
                    </rich:column>
                  	<rich:column width="170" style="text-align: center">
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
                      		<h:outputText value="Nro. Solicitud"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemExpediente}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Préstamo"/>          		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoCreditoEmpresa}"/>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                    		
                      	</f:facet>                      	
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.estadoCreditoUltimo.intParaEstadoCreditoCod}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.estadoCreditoUltimo.sucursal.juridica.strSiglas}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Aprobación"/>
                      	</f:facet>
                      	<h:outputText value="#{item.estadoCreditoAprobado.tsFechaEstado}">
                      		<f:convertDateTime pattern="dd-MM-yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Dcto. Giro"/>
                      	</f:facet>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Giro"/>
                      	</f:facet>
                      	<h:outputText value="#{item.estadoCreditoUltimo.tsFechaEstado}"
                      		rendered="#{item.estadoCreditoUltimo.intParaEstadoCreditoCod==applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO}">
                      		<f:convertDateTime pattern="dd-MM-yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Crédito"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotal}">
                      		 <f:converter converterId="ConvertidorMontos" />
                      	</h:outputText>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>
					</f:facet>
					
                   	<a4j:support event="onRowClick"
                   		actionListener="#{giroCreditoController.verRegistro}"
						reRender="contPanelInferior,panelMensajeGiro,panelBotones">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			
				          	             
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver o Girar una SOLICITUD DE PRÉSTAMO hacer click en el Registro." style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeGiro" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{giroCreditoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{giroCreditoController.mostrarMensajeExito}"/>
			<h:outputText value="#{giroCreditoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{giroCreditoController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
					rendered="#{!giroCreditoController.habilitarGrabarRequisito}"
			    	action="#{giroCreditoController.grabar}" 
			    	reRender="contPanelInferior,panelMensajeGiro,panelBotones,panelTablaResultados"
			    	disabled="#{!giroCreditoController.habilitarGrabar}"/>
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	rendered="#{giroCreditoController.habilitarGrabarRequisito}"
			    	action="#{giroCreditoController.grabarRequisito}" 
			    	reRender="contPanelInferior,panelMensajeGiro,panelBotones,panelTablaResultados"
			    	disabled="#{!giroCreditoController.habilitarGrabarRequisito}"/>     			     												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{giroCreditoController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensajeGiro,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
		<!-- JCHAVEZ 01.02.2014 Adjunto de Requisito para el Giro de Credito -->
		<rich:panel  rendered="#{giroCreditoController.mostrarPanelAdjuntoGiro}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
			   <h:panelGrid columns="8" id="panelAdjuntoRequisito">
					<rich:column width="120">
					</rich:column>
					<rich:column width="150">
						<h:inputText rendered="#{empty giroCreditoController.archivoAdjuntoGiro}" 
							size="77"
							readonly="true" 
							style="background-color: #BFBFBF;"/>
						<h:inputText rendered="#{not empty giroCreditoController.archivoAdjuntoGiro}"
							value="#{giroCreditoController.archivoAdjuntoGiro.strNombrearchivo}"
							size="77"
							readonly="true" 
							style="background-color: #BFBFBF;"/>
					</rich:column>
					<rich:column width="120">
						<a4j:commandButton
							rendered="#{empty giroCreditoController.archivoAdjuntoGiro}"
	                		styleClass="btnEstilos1"
	                		value="Adjunto de Giro"
	                		reRender="pAdjuntarGiroCredito,panelAdjuntoRequisito"
	                		oncomplete="Richfaces.showModalPanel('pAdjuntarGiroCredito')"
	                		style="width:130px"/>                 		
	                	<a4j:commandButton
							rendered="#{not empty giroCreditoController.archivoAdjuntoGiro}"
							disabled="#{giroCreditoController.deshabilitarNuevo}"
	                		styleClass="btnEstilos"
	                		value="Quitar Adjunto de Giro"
	                		reRender="pAdjuntarGiroCredito,panelAdjuntoRequisito"
	                		action="#{giroCreditoController.quitarAdjuntoGiro}"
	                		style="width:130px"/>
					</rich:column>
					<rich:column width="130" 
						rendered="#{(not empty giroCreditoController.archivoAdjuntoGiro)&&(giroCreditoController.deshabilitarNuevo)}">
						<h:commandLink  value=" Descargar"
							actionListener="#{fileUploadController.descargarArchivo}">
							<f:attribute name="archivo" value="#{giroCreditoController.archivoAdjuntoGiro}"/>
						</h:commandLink>
					</rich:column>
	           </h:panelGrid>
		</rich:panel>
		
		<rich:panel  rendered="#{giroCreditoController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
			<rich:spacer height="3px"/>

			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Fecha de Giro :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroCreditoController.dtFechaActual}" 
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
						property="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Sucursal de Giro :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroCreditoController.controlFondosFijosGirar.sucursal.juridica.strSiglas}"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Subsucursal :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText value="#{giroCreditoController.controlFondosFijosGirar.subsucursal.strDescripcion}"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" rendered="#{giroCreditoController.intTipoMostrarFondoCambio==giroCreditoController.MOSTRAR_FONDOCAMBIO_SINGULAR}">
				<rich:column width="120">
					<h:outputText value="Fondo de Cambio :"/>
				</rich:column>
				<rich:column width="270">
					<h:inputText size="77"
						value="#{giroCreditoController.controlFondosFijosGirar.strNumeroApertura}"
						readonly="true"
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="250">
					<h:outputText value="Monto Asignado : "/>
					<h:outputText value="#{giroCreditoController.controlFondosFijosGirar.bdMontoApertura}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
					<h:outputText value=" Saldo : "/>
					<h:outputText value="#{giroCreditoController.controlFondosFijosGirar.bdMontoSaldo}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" rendered="#{giroCreditoController.intTipoMostrarFondoCambio==giroCreditoController.MOSTRAR_FONDOCAMBIO_PLURAL}"
				id="panelFondoPlural">
				<rich:column width="120">
					<h:outputText value="Fondo de Cambio :"/>
				</rich:column>
				<rich:column width="270">
					<h:selectOneMenu
						value="#{giroCreditoController.intItemControlFondoSeleccionar}"
						style="width: 260px;">
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaControl}"
							itemValue="#{sel.id.intItemFondoFijo}"
							itemLabel="#{sel.strNumeroApertura}"/>
						<a4j:support event="onchange" 
							reRender="panelFondoPlural"
							action="#{giroCreditoController.seleccionarControlFondo}"/>					
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="250">
					<h:outputText value="Monto Asignado : "/>
					<h:outputText rendered ="#{not empty giroCreditoController.controlFondosFijosGirar.bdMontoApertura}" 
						value="#{giroCreditoController.controlFondosFijosGirar.bdMontoApertura}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
					<h:outputText value=" Saldo : "/>
					<h:outputText rendered ="#{not empty giroCreditoController.controlFondosFijosGirar.bdMontoSaldo}" 
						value="#{giroCreditoController.controlFondosFijosGirar.bdMontoSaldo}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
			</h:panelGrid>			

			<h:panelGrid columns="8" rendered="#{giroCreditoController.intTipoMostrarSocio==giroCreditoController.MOSTRAR_SOCIO_SINGULAR}">
				<rich:column width="120">
					<h:outputText value="Socio/Cliente :"/>
				</rich:column>
				<rich:column>
					<h:inputText rendered="#{giroCreditoController.personaGirar.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="DNI : #{giroCreditoController.personaGirar.documento.strNumeroIdentidad} - #{giroCreditoController.personaGirar.natural.strNombreCompleto}" 
						size="77"
						readonly="true"
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{giroCreditoController.personaGirar.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{giroCreditoController.personaGirar.juridica.strRazonSocial}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" rendered="#{giroCreditoController.intTipoMostrarSocio==giroCreditoController.MOSTRAR_SOCIO_PLURAL}">
				<rich:column width="120">
					<h:outputText value="Socio/Cliente :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						rendered="#{giroCreditoController.expedienteCreditoGirar.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{giroCreditoController.intIdPersonaGirarSeleccionar}"
						disabled="#{giroCreditoController.deshabilitarNuevo}"
						style="width: 420px;">
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaPersonaGirar}"
							itemValue="#{sel.intIdPersona}"
							itemLabel="DNI : #{sel.documento.strNumeroIdentidad} - #{sel.natural.strNombreCompleto}"/>				
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{giroCreditoController.expedienteCreditoGirar.personaAdministra.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{giroCreditoController.intIdPersonaGirarSeleccionar}"
						disabled="#{giroCreditoController.deshabilitarNuevo}"
						style="width: 420px;">
						<tumih:selectItems var="sel"
							value="#{giroCreditoController.listaPersonaGirar}"
							itemValue="#{sel.intIdPersona}"
							itemLabel="#{sel.juridica.strRazonSocial}"/>				
					</h:selectOneMenu>					
				</rich:column>
			</h:panelGrid>
			

			
			
			<h:panelGrid columns="8" id="panelApoderadoG">
				<rich:column width="120">
					<h:outputText value="Apoderado :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText rendered="#{empty giroCreditoController.personaApoderado}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty giroCreditoController.personaApoderado}" 
						value="DNI : #{giroCreditoController.personaApoderado.documento.strNumeroIdentidad} - #{giroCreditoController.personaApoderado.natural.strNombreCompleto}"
						size="77" 
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
                	<a4j:commandButton
						rendered="#{empty giroCreditoController.personaApoderado}"
						disabled="#{giroCreditoController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Buscar Apoderado"
                		action="#{giroCreditoController.abrirPopUpBuscarApoderado}"
                		reRender="pBuscarApoderado"
                		oncomplete="Richfaces.showModalPanel('pBuscarApoderado')"
                		style="width:130px"/>
                	<a4j:commandButton
						rendered="#{not empty giroCreditoController.personaApoderado}"
						disabled="#{giroCreditoController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Desleccionar Apoderado"
                		action="#{giroCreditoController.deseleccionarPersonaApoderado}"
                		reRender="panelApoderadoG,panelCartaPoderG"
                		style="width:130px"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" id="panelCartaPoderG">
				<rich:column width="120">
				</rich:column>
				<rich:column width="150">
					<h:inputText rendered="#{empty giroCreditoController.archivoCartaPoder}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty giroCreditoController.archivoCartaPoder}"
						value="#{giroCreditoController.archivoCartaPoder.strNombrearchivo}"
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width="120">
					<a4j:commandButton
						rendered="#{empty giroCreditoController.archivoCartaPoder}"
						disabled="#{(empty giroCreditoController.personaApoderado) || giroCreditoController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
               		value="Adjuntar Carta Poder"
                		reRender="frmAjuntarCartaPoder"
                		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoder')"
                		style="width:130px"/>
                	<a4j:commandButton
						rendered="#{not empty giroCreditoController.archivoCartaPoder}"
						disabled="#{giroCreditoController.deshabilitarNuevo}"
                		styleClass="btnEstilos"
                		value="Quitar Carta Poder"
                		reRender="panelCartaPoderG"
                		action="#{giroCreditoController.quitarCartaPoder}"
                		style="width:130px"/>
				</rich:column>
				<rich:column width="130" 
					rendered="#{(not empty giroCreditoController.archivoCartaPoder)&&(giroCreditoController.deshabilitarNuevo)}">
					<h:commandLink  value=" Descargar"		
						actionListener="#{fileUploadController.descargarArchivo}">
						<f:attribute name="archivo" value="#{giroCreditoController.archivoCartaPoder}"/>		
					</h:commandLink>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120" style="vertical-align: top">
					<h:outputText value="Glosa del Egreso :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputTextarea rendered="#{empty giroCreditoController.glosaDelEgreso}"
						disabled="#{giroCreditoController.deshabilitarNuevo}" 
						cols="77" 
						rows="2" 
						value="#{giroCreditoController.strGlosaEgreso}"/>
					<h:inputTextarea rendered="#{not empty giroCreditoController.glosaDelEgreso}" 
						value="#{giroCreditoController.glosaDelEgreso}"
						cols="77" 
						rows="2" 
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Detalle del Egreso :"/>
				</rich:column>				
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120"/>
				<rich:column>
					<h:panelGrid>
			        	<rich:dataTable
			          		sortMode="single" 
		                    var="item"
		                    value="#{giroCreditoController.listaEgresoDetalleInterfaz}"  
							rowKeyVar="rowKey"
							width="800px"
							align="center"
							rows="#{fn:length(giroCreditoController.listaEgresoDetalleInterfaz)}">
		                                
							<rich:column width="20px" style="text-align: center">
		                    	<h:outputText value="#{rowKey + 1}"/>
		                    </rich:column>
							<rich:column width="100px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Concepto"/>
		                      	</f:facet>
		                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
									itemValue="intIdDetalle" 
									itemLabel="strDescripcion" 
									property="#{item.intParaConcepto}"/>
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
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Interés"/>      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdInteres}"></h:outputText>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Gravamen"/>                    		
		                      	</f:facet>                      	
		                      	<h:outputText value="#{item.bdGravamen}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Aporte"/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdAporte}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Otros"/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdOtros}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		                  	<rich:column width="100" style="text-align: center">
		                    	<f:facet name="header">
		                      		<h:outputText value="Subtotal"/>                      		
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdSubTotal}">
		                      		<f:converter converterId="ConvertidorMontos" />
		                      	</h:outputText>
		                  	</rich:column>
		            	</rich:dataTable>
		         	</h:panelGrid>
		         	
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="120">
					<h:outputText value="Total General :"/>
				</rich:column>
				<rich:column>
					<h:inputText size ="15" value="#{giroCreditoController.bdTotalEgresoDetalleInterfaz}" 
						readonly="true" 
						style="background-color: #BFBFBF;">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:panelGroup>	

</rich:panel>


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