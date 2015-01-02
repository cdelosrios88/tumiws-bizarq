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
	<!-- Modificación : Junior Chavez   	-->





<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="FONDOS FIJOS" styleClass="tagTitulo"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid id="panelCFFFiltroF">
        		<rich:columnGroup>
	            	<rich:column width="100" style="text-align: left;">
	                	<h:outputText value="Tipo Fondo Fijo :"/>
	            	</rich:column>
	            	<rich:column width="140" style="text-align: left;">
	            		<h:selectOneMenu
							style="width: 137px;"
							value="#{fondosFijosController.egresoFiltro.intParaTipoFondoFijo}">
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaTablaTipoFondoFijo}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>						
							<a4j:support event="onchange" 
								action="#{fondosFijosController.obtenerListaNumeroApertura}"
								reRender="panelCFFFiltroF"/>
						</h:selectOneMenu>
	            	</rich:column>
	            	<rich:column width="60" style="text-align: left;">
						<h:outputText value="Sucursal :"/>
					</rich:column>
					<rich:column width="150" style="text-align: left;">
						<h:selectOneMenu
							disabled="#{!fondosFijosController.habilitarFiltroSucursal}"
							value="#{fondosFijosController.egresoFiltro.intSucuIdSucursal}"
							style="width: 150px;">
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaTablaSucursal}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								action="#{fondosFijosController.obtenerListaNumeroApertura}"
								reRender="panelCFFFiltroF"/>												
						</h:selectOneMenu>
					</rich:column>
	            	<rich:column width="110" style="text-align: left;">
	                	<h:outputText value="Nro. de Apertura :"/>
	            	</rich:column>
	            	<rich:column width="210">
	            		<h:selectOneMenu
	            			value="#{fondosFijosController.intItemControlFiltro}"
							style="width: 210px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaControlFondosFijosBusqueda}"
								itemValue="#{sel.intItemFiltro}"
								itemLabel="#{sel.strNumeroApertura}"/>
						</h:selectOneMenu>
	            	</rich:column>
	            	<rich:column width="100">
	            		<h:selectOneMenu
							style="width: 80px;"
							value="#{fondosFijosController.intAñoFiltro}">
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaAño}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								action="#{fondosFijosController.obtenerListaNumeroApertura}"
								reRender="panelCFFFiltroF"/>
						</h:selectOneMenu>					
	            	</rich:column>
	            	<rich:column colspan="3"/>
            	</rich:columnGroup>
            </h:panelGrid>
            
        	<h:panelGrid id="panelPersonaFiltroF">
        		<rich:columnGroup>
	        		<rich:column width="100" style="text-align: left;">
						<h:outputText value="Tipo de Persona :"/>
					</rich:column>
					<rich:column width="95" style="text-align: left;">
						<h:selectOneMenu
							value="#{fondosFijosController.intTipoPersonaFiltro}"
							style="width: 95px;">
							<tumih:selectItems var="sel" 
								cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="panelPersonaFiltroF"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="80" style="text-align: left;">
						<h:selectOneMenu
							rendered="#{fondosFijosController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="#{fondosFijosController.intTipoBusquedaPersona}"
							style="width: 80px;">
							<f:selectItem itemValue="1" itemLabel="Nombre"/>
							<f:selectItem itemValue="2" itemLabel="DNI"/>
						</h:selectOneMenu>
						<h:selectOneMenu
							rendered="#{fondosFijosController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="#{fondosFijosController.intTipoBusquedaPersona}"
							style="width: 80px;">
							<f:selectItem itemValue="1" itemLabel="Nombre"/>
							<f:selectItem itemValue="3" itemLabel="RUC"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="140" style="text-align: left;">
						<h:inputText size="20"
							value="#{fondosFijosController.strTextoPersonaFiltro}"/>
					</rich:column>
					<rich:column width="50" style="text-align: left;">
						<h:outputText value="Estado : "/>
					</rich:column>
					<rich:column width="80" style="text-align: left;">
						<h:selectOneMenu
							value="#{fondosFijosController.egresoFiltro.intParaEstado}"
							style="width: 80px;">
							<tumih:selectItems var="sel" 
								value="#{fondosFijosController.listaTablaEstado}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="100" style="text-align: left;">
	                	<h:outputText value="Fecha de Egreso: "/>
	            	</rich:column>
	            	<rich:column width="110" style="text-align: left;">
	                	<rich:calendar datePattern="dd/MM/yyyy"  
							value="#{fondosFijosController.dtDesdeFiltro}"  
							jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
	            	</rich:column>
	            	<rich:column width="110" style="text-align: left;">
	                	<rich:calendar datePattern="dd/MM/yyyy"  
							value="#{fondosFijosController.dtHastaFiltro}"  
							jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
	            	</rich:column>
	            	
            	</rich:columnGroup>
            	<rich:columnGroup>
            		<rich:column width="1020px" colspan="9" style="text-align: right;" >
	                	<a4j:commandButton styleClass="btnEstilos"
	                		value="Buscar" 
	                		reRender="panelTablaResultados,panelMensajeF"
	                    	action="#{fondosFijosController.buscar}" 
	                    	style="width:70px"/>
	                    <rich:spacer width="10px"/>
	                    <a4j:commandButton styleClass="btnEstilos"
	                		value="Limpiar" 
	                		reRender="panelCFFFiltroF, panelPersonaFiltroF"
	                    	action="#{fondosFijosController.limpiarFiltrosBusqueda}" 
	                    	style="width:70px"/>
	            	</rich:column>
            	</rich:columnGroup>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
            
            <h:panelGrid id="panelTablaResultados">
	        	<rich:extendedDataTable id="tblResultado"
	          		enableContextMenu="false"
	          		sortMode="single"
                    var="item"
                    value="#{fondosFijosController.listaEgreso}"
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
                  	<!-- Autor: jchavez / Tarea: Eliminación / Fecha: 08.09.2014 -->
                  	<rich:column width="150" style="text-align: center" rendered="false">
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
                   		actionListener="#{fondosFijosController.verRegistro}"
						reRender="panelMensajeF, panelBotones, contPanelInferior">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>
					
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para IMPRIMIR un MOVIMIENTO DE FONDO FIJO hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeF" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{fondosFijosController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{fondosFijosController.mostrarMensajeExito}"/>
			<h:outputText value="#{fondosFijosController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{fondosFijosController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{fondosFijosController.habilitarPanelInferior}"
					reRender="panelMensajeF,panelBotones,contPanelInferior" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{fondosFijosController.grabar}" 
			    	reRender="contPanelInferior,panelMensajeF,panelBotones,panelTablaResultados"
			    	disabled="#{!fondosFijosController.habilitarGrabar}"/>
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{fondosFijosController.deshabilitarPanelInferior}"
			    	reRender="contPanelInferior,panelMensajeF,panelBotones"/>
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{fondosFijosController.mostrarPanelInferior}" style="border:1px solid #17356f;background-color:#DEEBF5;">
			
			<h:panelGroup id="pgNvoFondoFijo" rendered="#{!fondosFijosController.datosValidados}">
				<h:panelGrid columns="12" id="panelValidar">
					<rich:column width="120">
						<h:outputText value="Tipo de Fondo Fijo :"/>
					</rich:column>
					<rich:column width="200">
	                	<h:selectOneMenu
							style="width: 190px;"
							value="#{fondosFijosController.intTipoFondoFijoValidar}">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaTablaTipoFondoFijo}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								reRender="panelValidar"
								action="#{fondosFijosController.seleccionarTipoFondoFijo}"/>
						</h:selectOneMenu>
	            	</rich:column>
	            	<rich:column width="110" rendered="#{fondosFijosController.blnActivarNroApertura}">
						<h:outputText value="Nro. de Apertura :"/>
					</rich:column>
					<rich:column width="300" rendered="#{fondosFijosController.blnActivarNroApertura}">
	                	<h:selectOneMenu
	                		value="#{fondosFijosController.intControlSeleccionado}"
							style="width: 290px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaControlFondosFijos}" 
								itemValue="#{sel.intItemFiltro}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	            	</rich:column>
	            	<rich:column width="60">
						<h:outputText value="Sucursal :"/>
					</rich:column>
					<rich:column width="150">
	                	<h:inputText value="#{fondosFijosController.sucursalUsuario.juridica.strSiglas}" 
	                		readonly="true"
							style="background-color: #BFBFBF;"
							size="20"/>
	            	</rich:column>
            	</h:panelGrid>
            	
            	<rich:spacer height="12px"/>
            	
				<h:panelGrid columns="1" rendered="#{!fondosFijosController.datosValidados}">
					<rich:column width="940">
						<a4j:commandButton styleClass="btnEstilos"
		                	value="Validar Datos"
		                	reRender="contPanelInferior,panelBotones"
		                    action="#{fondosFijosController.validarDatos}" 
		                    style="width:940px"/>
					</rich:column>
				</h:panelGrid>			
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{fondosFijosController.datosValidados}">
				
				<h:panelGrid columns="1">
					<rich:column width="120">
						<h:outputText value="FONDO FIJO" styleClass="tagSubTitulo"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8">
					<rich:column width="120" rendered="#{fondosFijosController.blnActivarNroApertura}">
						<h:outputText value="Nro. Apertura"/>
					</rich:column>
					<rich:column width="4" rendered="#{fondosFijosController.blnActivarNroApertura}">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="180" rendered="#{fondosFijosController.blnActivarNroApertura}">
						<h:inputText readonly="true"
							value="#{fondosFijosController.controlFondosFijos.strNumeroApertura}"
							style="background-color: #BFBFBF;"
							size="27"/>
					</rich:column>
					<rich:column width="180">
						<tumih:inputText
							cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"							
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{fondosFijosController.intTipoFondoFijoValidar}"
							readonly="true"
							style="background-color: #BFBFBF;"
							size="27"/>
					</rich:column>
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Tipo de Documento"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
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
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8">
					<rich:column width="120">
						<h:outputText value="Sucursal"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="360">
						<h:inputText readonly="true"
							value="#{fondosFijosController.sucursalUsuario.juridica.strSiglas} - #{fondosFijosController.subsucursalUsuario.strDescripcion}"
							style="background-color: #BFBFBF;"
							size="60"/>
					</rich:column>
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Fecha de Egreso"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="150">
						<h:inputText readonly="true"
							value="#{fondosFijosController.egresoNuevo.dtFechaEgreso}"
							style="background-color: #BFBFBF;"
							size="30">
							<f:convertDateTime pattern="dd/MM/yyyy"/>
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="8px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="200">
						<h:outputText value="DOCUMENTO A CANCELAR" styleClass="tagSubTitulo"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGroup id="panelPersonaF">
				
				<h:panelGrid columns="11" >
					<rich:column width="120">
						<h:outputText value="Persona"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="675">
						<h:inputText readonly="true"
							rendered="#{empty fondosFijosController.personaSeleccionada}"
							style="background-color: #BFBFBF;"
							size="120"/>
						<h:inputText readonly="true"
							rendered="#{(fondosFijosController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)
							&& (empty fondosFijosController.cuentaActual)}"
							value="DNI : #{fondosFijosController.personaSeleccionada.documento.strNumeroIdentidad} - #{fondosFijosController.personaSeleccionada.natural.strNombreCompleto}"
							style="background-color: #BFBFBF;"
							size="120"/>
						<h:inputText readonly="true"
							rendered="#{(fondosFijosController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)
							&& (not empty fondosFijosController.cuentaActual)}"
							value="DNI : #{fondosFijosController.personaSeleccionada.documento.strNumeroIdentidad} - #{fondosFijosController.personaSeleccionada.natural.strNombreCompleto} - Cuenta : #{fondosFijosController.cuentaActual.strNumeroCuenta}"
							style="background-color: #BFBFBF;"
							size="120"/>
						<h:inputText readonly="true"
							rendered="#{fondosFijosController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{fondosFijosController.personaSeleccionada.strRuc} - #{fondosFijosController.personaSeleccionada.juridica.strRazonSocial}"
							style="background-color: #BFBFBF;"
							size="120"/>
					</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{empty fondosFijosController.personaSeleccionada}"
	                		value="Buscar"
	                		reRender="pBuscarPersona"
	                    	action="#{fondosFijosController.abrirPopUpBuscarPersona}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarPersona')"
	                    	style="width:80x"/>
	                    <a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{not empty fondosFijosController.personaSeleccionada}"
	                		value="Quitar"
	                		reRender="contPanelInferior"
	                    	action="#{fondosFijosController.quitarPersonaSeleccionada}"
	                    	disabled="#{fondosFijosController.deshabilitarNuevo}"
	                    	style="width:80x"/>
	            	</rich:column>
	            </h:panelGrid>
	            
	            <rich:spacer height="3px"/>
	            
	            <h:panelGrid columns="11" >
					<rich:column width="120">
						<h:outputText value="Roles"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="685">
						<h:inputText readonly="true"
							value="#{fondosFijosController.strListaPersonaRolUsar}"
							style="background-color: #BFBFBF;"
							size="124"/>
					</rich:column>
				</h:panelGrid>
	            
	            </h:panelGroup>
	            
	            <rich:spacer height="3px"/>
	            
	            
	            <h:panelGroup id="panelDocumentoF">
	            
	            <h:panelGrid columns="8" id="panelTipoDocumento">
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Tipo de Documento : "/>
			        </rich:column>
			        <rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
			        <rich:column width="175">
						<h:selectOneMenu
							style="width: 160px;"
							disabled="#{(not empty fondosFijosController.listaEgresoDetalleInterfazAgregado)||(fondosFijosController.deshabilitarNuevo)}"
							value="#{fondosFijosController.intTipoDocumentoAgregar}">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaTablaTipoDocumento}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"
								propertySort="strDescripcion"/>
								<a4j:support event="onchange" 
									reRender="panelTipoDocumento"
									action="#{fondosFijosController.seleccionarTipoDocumento}"/>

						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="175">
						<h:selectOneMenu
							style="width: 160px;"
							disabled="#{(not empty fondosFijosController.listaEgresoDetalleInterfazAgregado)||(fondosFijosController.deshabilitarNuevo)}"
							value="#{fondosFijosController.intTipoComprobanteAgregar}">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaTablaTipoComprobante}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"
								propertySort="strDescripcion"/>
						</h:selectOneMenu>
			        </rich:column>
			        
				</h:panelGrid>
				
				<h:panelGrid columns="8">
					<rich:column width="124" style="text-align: left;">
						<h:outputText value=""/>
			        </rich:column>
					<rich:column width="500">
						<h:inputText size="78" 
							readonly="true"
							style="background-color: #BFBFBF;"
							value="#{fondosFijosController.documentoGeneralSeleccionado.strEtiqueta}"/>
					</rich:column>
					<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		disabled="#{(empty fondosFijosController.personaSeleccionada) || (fondosFijosController.deshabilitarNuevo)}"
	                		value="Buscar"
	                		reRender="frmBuscarDocumento, frmBuscarDocumentoSunat"
	                    	action="#{fondosFijosController.abrirPopUpBuscarDocumento}"
	                    	oncomplete="if(#{fondosFijosController.intTipoDocumentoAgregar != applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS}){Richfaces.showModalPanel('pBuscarDocumento')}
	                    				else {Richfaces.showModalPanel('pBuscarDocumentoSunat')}"
	                    	style="width:80px"/>
	            	</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		disabled="#{(empty fondosFijosController.documentoGeneralSeleccionado) || (fondosFijosController.deshabilitarNuevo || fondosFijosController.blnBeneficiarioSinAutorizacion)}"
	                		value="Agregar"
	                		reRender="contPanelInferior,panelMontoF,panelDocumentosAgregadosF,panelDocumentoF,panelMensajeF"
	                    	action="#{fondosFijosController.agregarDocumento}"
	                    	style="width:80px"/>
	            	</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="8"
					rendered="#{(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
					
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Beneficiario"/>
				    </rich:column>
				    <rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
				    <rich:column width="680">
						<h:selectOneMenu
							disabled="#{fondosFijosController.deshabilitarNuevo}"
							style="width: 678px;"
							value="#{fondosFijosController.intBeneficiarioSeleccionar}">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaBeneficiarioPrevision}"
								itemValue="#{sel.id.intItemBeneficiario}"
								itemLabel="#{sel.strEtiqueta}"/>
							<a4j:support event="onchange"
								action="#{fondosFijosController.seleccionarBeneficiario}" 
								reRender="panelApoderadoF,pgMsgErrorBeneficiarioF,panelDocumentosAgregadosF,panelDocumentoF"/>
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="8"
					rendered="#{(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
					
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Beneficiario"/>
				    </rich:column>
				    <rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
				    <rich:column width="680">
						<h:selectOneMenu
							disabled="#{fondosFijosController.deshabilitarNuevo}"
							style="width: 678px;"
							value="#{fondosFijosController.intBeneficiarioSeleccionar}">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{fondosFijosController.listaBeneficiarioPersona}"
								itemValue="#{sel.intIdPersona}"
								itemLabel="#{sel.strEtiqueta}"/>
						
							<a4j:support event="onchange"
								action="#{fondosFijosController.seleccionarBeneficiario}" 
								reRender="panelApoderadoF,pgMsgErrorBeneficiarioF,panelDocumentosAgregadosF,panelDocumentoF"/>
						</h:selectOneMenu>						
					</rich:column>
				</h:panelGrid>

				<h:panelGrid id="pgMsgErrorBeneficiarioF" columns="8">
   					<rich:column width="800" style="text-align: left;">
   						<h:outputText value="#{fondosFijosController.strMensajeErrorPorBeneficiario}" styleClass="msgError"/>
   					</rich:column>
    			</h:panelGrid>
    			
				<h:panelGrid columns="8" id="panelApoderadoF"
					rendered="#{(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
					
					<rich:column width="120">
						<h:outputText value="Apoderado"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="360">
						<h:inputText rendered="#{empty fondosFijosController.personaApoderado}" 
							size="60"
							readonly="true" 
							style="background-color: #BFBFBF;"/>
						<h:inputText rendered="#{not empty fondosFijosController.personaApoderado}" 
							value="DNI : #{fondosFijosController.personaApoderado.documento.strNumeroIdentidad} - #{fondosFijosController.personaApoderado.natural.strNombreCompleto}"
							size="60" 
							readonly="true" 
							style="background-color: #BFBFBF;"/>
					</rich:column>
					<rich:column width="130">
						<a4j:commandButton
							rendered=	"#{(empty fondosFijosController.personaApoderado) && fondosFijosController.blnVerBotonApoderado}"
							disabled="#{fondosFijosController.deshabilitarNuevo}"
			           		styleClass="btnEstilos"
			           		value="Buscar Persona"
			           		action="#{fondosFijosController.abrirPopUpBuscarApoderado}"
			           		reRender="pBuscarPersona"
			           		oncomplete="Richfaces.showModalPanel('pBuscarPersona')"
			           		style="width:130px"/>
			           	<a4j:commandButton
							rendered="#{(not empty fondosFijosController.personaApoderado) && fondosFijosController.blnVerBotonApoderado}"
							disabled="#{fondosFijosController.deshabilitarNuevo}"
			                styleClass="btnEstilos"
			                value="Quitar"
			                action="#{fondosFijosController.quitarPersonaApoderado}"
			                reRender="panelApoderadoF,panelCartaPoderF"
			                style="width:130px"/>
					</rich:column>					
				</h:panelGrid>
				
				<h:panelGrid columns="8" id="panelCartaPoderF"
					rendered="#{(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					||	(fondosFijosController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
					
					<rich:column colspan="2" width="124">
					</rich:column>
					<rich:column width="360">
						<h:inputText rendered="#{empty fondosFijosController.archivoCartaPoder}" 
							size="60"
							readonly="true" 
							style="background-color: #BFBFBF;"/>
						<h:inputText rendered="#{not empty fondosFijosController.archivoCartaPoder}"
							value="#{fondosFijosController.archivoCartaPoder.strNombrearchivo}"
							size="60"
							readonly="true" 
							style="background-color: #BFBFBF;"/>
					</rich:column>
					<rich:column width="130">
						<a4j:commandButton
							rendered="#{(empty fondosFijosController.archivoCartaPoder) && fondosFijosController.blnVerBotonApoderado}"
							disabled="#{(empty fondosFijosController.personaApoderado) || fondosFijosController.deshabilitarNuevo}"
			           		styleClass="btnEstilos"
			           		value="Adjuntar Carta Poder"
			           		reRender="pAdjuntarCartaPoderF,panelCartaPoderF"
			           		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoderF')"
			           		style="width:130px"/>
			           	<a4j:commandButton
							rendered="#{(not empty fondosFijosController.archivoCartaPoder) && fondosFijosController.blnVerBotonApoderado}"
							disabled="#{fondosFijosController.deshabilitarNuevo}"
			           		styleClass="btnEstilos"
			           		value="Quitar Carta Poder"
			           		reRender="pAdjuntarCartaPoderF,panelCartaPoderF"
			           		action="#{fondosFijosController.quitarCartaPoder}"
			           		style="width:130px"/>
					</rich:column>
					<rich:column width="130" 
						rendered="#{(not empty fondosFijosController.archivoCartaPoder)&&(fondosFijosController.deshabilitarNuevo)}">
						<h:commandLink  value=" Descargar"		
							actionListener="#{fileUploadController.descargarArchivo}">
							<f:attribute name="archivo" value="#{fondosFijosController.archivoCartaPoder}"/>							
						</h:commandLink>
					</rich:column>
				</h:panelGrid>
				
				</h:panelGroup>
				
				<rich:spacer height="3px"/>
				
				<h:panelGroup id="panelMontoF">
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Monto a Girar"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="20"	
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{fondosFijosController.bdMontoGirar}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="74"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{fondosFijosController.strMontoGirarDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Diferencial Cambiario"/>
					</rich:column>
					<rich:column width="4">
						<h:outputText value=":"/>
					</rich:column>
					<rich:column width="175">
						<h:inputText size="20"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{fondosFijosController.bdDiferencialGirar}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="74"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold; width : 546px;"
							value="#{fondosFijosController.strDiferencialGirarDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				
				</h:panelGroup>
				
				<rich:spacer height="3px"/>
				<h:panelGroup>
					<h:panelGrid columns="6">
						<rich:column width="120" style="vertical-align: top">
							<h:outputText value="Observación"/>
						</rich:column>
						<rich:column width="4">
							<h:outputText value=":"/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="2" cols="124"
								value="#{fondosFijosController.strObservacion}"
								disabled="#{fondosFijosController.deshabilitarNuevo && fondosFijosController.blnHabilitarObservacion}"/>
						</rich:column>
					</h:panelGrid>	
					
					<rich:spacer height="8px"/>
					
					<h:panelGrid columns="1" id="panelDocumentosAgregadosF">
						<rich:column>
							<rich:dataTable
					    		var="item"
					    		styleClass="datatable"
					            value="#{fondosFijosController.listaEgresoDetalleInterfazAgregado}"
						 		sortMode="single"
							  	width="950px"
				                rows="#{fn:length(fondosFijosController.listaEgresoDetalleInterfazAgregado)}">
				                    
								<rich:column width="20px" style="text-align: center">
				            		<h:outputText value="#{item.intOrden}"/>
				            	</rich:column>
				            	<rich:column width="120" style="text-align: center">
				                   	<f:facet name="header">
				                		<h:outputText value="Documento"/>
				                   	</f:facet>
				                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
										itemValue="intIdDetalle" itemLabel="strDescripcion"
										property="#{item.intParaDocumentoGeneral}"/>
				                </rich:column>
				                <rich:column width="100" style="text-align: center">
				                   	<f:facet name="header">
				                   		<h:outputText value="Nro de Documento"/>
				                   	</f:facet>
				                  	<h:outputText value="#{item.strNroDocumento}"/>
				                </rich:column>
				                <rich:column width="150" style="text-align: center">
				                  	<f:facet name="header">
				                   		<h:outputText value="Persona"/>
				                   	</f:facet>
				                   	<h:outputText value="#{item.persona.natural.strNombreCompleto}"
				                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
				                   	<h:outputText value="#{item.persona.juridica.strRazonSocial}" 
				                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>	
				                </rich:column>
				                <rich:column width="160" style="text-align: center">
				                    <f:facet name="header">
				                    	<h:outputText value="Sucursal"/>                      		
				                    </f:facet>
				                    <h:outputText value="#{item.sucursal.juridica.strSiglas} - #{item.subsucursal.strDescripcion}"/>
				               	</rich:column>
				                <rich:column width="100" style="text-align: center">
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
				               	<rich:column width="80" style="text-align: right" rendered="#{fondosFijosController.intTipoDocumentoAgregar != applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS}">
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
									<h:commandLink  value=" Descargar" rendered="#{item.archivoAdjuntoGiro!=null}"
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
		
	</h:panelGroup>	

	</rich:panel>
</h:form>
