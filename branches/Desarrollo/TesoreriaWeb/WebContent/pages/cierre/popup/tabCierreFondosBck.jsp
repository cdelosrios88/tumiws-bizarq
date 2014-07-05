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





<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CIERRE DE FONDOS FIJOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">              	
            	<rich:column width="100">
            		<h:outputText value="Fondo Fijo : "/>
            	</rich:column>
            	<rich:column width="120">
					<h:selectOneMenu
						style="width: 110px;"
						value="#{cierreFondosController.intTipoFondoFijo}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" action="#{cierreFondosController.seleccionarSucursal}"
							reRender="columnaNroApertura"  />	
					</h:selectOneMenu>
			  	</rich:column>
			  	<rich:column width="60">
            		<h:outputText value="Sucursal : "/>
            	</rich:column>
            	<rich:column width="170">
					<h:selectOneMenu
						style="width: 160px;"
						value="#{cierreFondosController.intIdSucursal}"
						disabled="#{cierreFondosController.blnDisabledSucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cierreFondosController.listaSucursalBus}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" action="#{cierreFondosController.seleccionarSucursal}"
							reRender="columnaNroApertura"  />
					</h:selectOneMenu>
            	</rich:column>
				<rich:column width="110">
            		<h:outputText value="Nro. Apertura : "/>
            	</rich:column>
            	<rich:column width="180" id="columnaNroApertura">
					<h:selectOneMenu
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cierreFondosController.listaControlFondosFijosBus}"
							itemValue="#{sel.id.intItemFondoFijo}" 
							itemLabel="#{sel.strNumeroApertura} - S/#{sel.bdMontoApertura}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="85">
					<h:selectOneMenu id="cboAnioBusq" value="#{cierreFondosController.intAnioBusqueda}" style="width: 80px;">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<f:selectItems id="lstYears" value="#{cierreFondosController.listYears}"/>
						<a4j:support event="onchange" action="#{cierreFondosController.seleccionarSucursal}"
							reRender="columnaNroApertura"  />
					</h:selectOneMenu>
                </rich:column>
                <rich:column width="50">
            		<h:outputText value="Estado : "/>
            	</rich:column>
            	<rich:column width="90">
                	<h:selectOneMenu
						style="width: 90px;"
						value="#{cierreFondosController.controlFondosFijosFiltro.intEstadoFondo}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_ESTADOFONDO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
              	</rich:column>            	
            	<rich:column width="80" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultados,panelMensaje"
                    	action="#{cierreFondosController.buscar}" 
                    	style="width:80px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>
                
            <h:panelGrid id="panelTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{cierreFondosController.listaControlFondosFijos}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1040px" 
					height="160px" 
					align="center">
                                
					<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Fondo"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.id.intParaTipoFondoFijo}"/>
                  	</rich:column>
                    <rich:column width="250" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Movimiento Anterior"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.anterior.strDescripcion}" title="#{item.anterior.strDescripcion}"/>                      	                      	
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Apertura"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroApertura}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
                      		<f:convertDateTime pattern="dd/MM/yyyy"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="110" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoApertura}">
                        	<f:converter converterId="ConvertidorMontos"/>
                        </h:outputText>
                  	</rich:column>
                    <rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoSaldo}">
                        	<f:converter converterId="ConvertidorMontos"/>
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOFONDO}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.intEstadoFondo}"/>
                  	</rich:column>
                  	<rich:column width="50" style="text-align: center">
                   		<a4j:commandLink
                   			rendered="#{item.intEstadoFondo==applicationScope.Constante.PARAM_T_ESTADOFONDO_ABIERTO}"
							value="Cerrar"
					        actionListener="#{cierreFondosController.cerrar}"
							reRender="contPanelInferior">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<a4j:commandLink
							rendered="#{item.intEstadoFondo==applicationScope.Constante.PARAM_T_ESTADOFONDO_CERRADO}"
							value="Anular Cierre"
					        actionListener="#{cierreFondosController.anularCierre}"
							reRender="contPanelInferior">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
                  	</rich:column>
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>
					
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{cierreFondosController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{cierreFondosController.mostrarMensajeExito}"/>
			<h:outputText value="#{cierreFondosController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{cierreFondosController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{cierreFondosController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
			    	disabled="#{!cierreFondosController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{cierreFondosController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{cierreFondosController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGrid columns="2">
				<rich:column width="120">
					<h:outputText value="Tipo de Cierre : "/>
				</rich:column>
				<rich:column width="200">
                	<h:selectOneMenu
						style="width: 200px;"
						disabled="#{cierreFondosController.datosValidados}"
						value="#{cierreFondosController.intTipoCierre}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_CIERREFONDOFIJO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>	
					</h:selectOneMenu>
            	</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="12px"/>
			
            <h:panelGrid>
	        	<rich:dataTable
	          		sortMode="single"
                    var="item"
                    value="#{cierreFondosController.listaControlFondosFijosCerrar}"  
					rowKeyVar="rowKey"
					rows="1"
					width="820px"
					rows="#{fn:length(cierreFondosController.listaControlFondosFijosCerrar)}">
                                
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Fondo"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.id.intParaTipoFondoFijo}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Apertura"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroApertura}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
                      		<f:convertDateTime pattern="dd/MM/yyyy"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoApertura}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto Rendido"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoUtilizado}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoSaldo}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOFONDO}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.intEstadoFondo}"/>
                  	</rich:column>
					
            	</rich:dataTable>
            	
         	</h:panelGrid>
			
			<rich:spacer height="12px"/>
			
			
			<h:panelGroup id="panelValidar">

			<h:panelGroup rendered="#{!cierreFondosController.datosValidados}">
				
				<h:panelGrid columns="1">
					<rich:column width="820">
						<a4j:commandButton styleClass="btnEstilos"	
			               	value="Validar Datos" 
			               	reRender="contPanelInferior,panelBotones"
			               	action="#{cierreFondosController.validarDatos}" 
			               	style="width:820px"/>
					</rich:column>
				</h:panelGrid>
				
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{cierreFondosController.datosValidados}">
			
				<rich:spacer height="10px"/>
				
				<h:panelGrid columns="2">
					<rich:column width="120">
						<h:outputText value="Responsable : "/>
					</rich:column>
					<rich:column>
						<h:inputText size="100" 
							value="#{cierreFondosController.controlFondosFijosCerrar.strDescripcionPersona}" 
							readonly="true" 
							style="background-color: #BFBFBF;"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="2">
					<rich:column width="120">
						<h:outputText value="Fecha y Hora Cierre : "/>
					</rich:column>
					<rich:column>
						<h:inputText size="100" value="#{cierreFondosController.controlFondosFijosCerrar.tsFechaCierre}" 
							readonly="true" style="background-color: #BFBFBF;">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss"/>
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGroup rendered="#{cierreFondosController.intTipoCierre==applicationScope.Constante.PARAM_T_CIERREFONDOFIJO_LIQUIDACION}">
					
					<rich:spacer height="10px"/>
									
					<h:panelGrid columns="1">
						<rich:column width="120">
							<h:outputText value="INGRESO A CAJA"/>
						</rich:column>
					</h:panelGrid>
						
					<rich:spacer height="3px"/>
						
					<h:panelGrid columns="8">
						<rich:column width="120">
							<h:outputText value="Caja :"/>
						</rich:column>
						<rich:column width="170">
							<h:inputText readonly="true"
								value="Caja"
								style="background-color: #BFBFBF;"
								size="25"/>
						</rich:column>
						<rich:column width="225" style="text-align: left;">
						</rich:column>
						<rich:column width="110" style="text-align: left;">
							<h:outputText value="Tipo de Documento :"/>
						</rich:column>
						<rich:column width="150">
							<tumih:inputText
								cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA}"
								readonly="true"
								style="background-color: #BFBFBF;"
								size="23"/>
						</rich:column>
					</h:panelGrid>
						
					<rich:spacer height="3px"/>
						
					<h:panelGrid columns="8">
						<rich:column width="120">
							<h:outputText value="Sucursal :"/>
						</rich:column>
						<rich:column width="170">
							<h:inputText readonly="true"
								value="#{cierreFondosController.usuario.sucursal.juridica.strSiglas} - #{cierreFondosController.usuario.subSucursal.strDescripcion}"
								style="background-color: #BFBFBF;"
								size="25"/>
						</rich:column>
						<rich:column width="100" style="text-align: left;">
							<h:outputText value="Forma de Pago :"/>
						</rich:column>
						<rich:column width="120">
							<tumih:inputText
								cache="#{applicationScope.Constante.PARAM_T_PAGOINGRESO}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{applicationScope.Constante.PARAM_T_PAGOINGRESO_EFECTIVO}"
								readonly="true"
								style="background-color: #BFBFBF;"
								size="15"/>
						</rich:column>
						<rich:column width="110" style="text-align: left;">
							<h:outputText value="Fecha de Ingreso :"/>
						</rich:column>
						<rich:column width="150">
							<h:inputText readonly="true"
								value="#{cierreFondosController.controlFondosFijosCerrar.tsFechaCierre}"
								style="background-color: #BFBFBF;"
								size="23">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
						
					<h:panelGrid columns="8">
						<rich:column width="120">
							<h:outputText value="Fondo Fijo :"/>
						</rich:column>
						<rich:column width="170">
							<h:inputText readonly="true"
								value="#{cierreFondosController.controlFondosFijosCerrar.strNumeroApertura}"
								style="background-color: #BFBFBF;"
								size="25"/>
						</rich:column>					
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="8">
						<rich:column width="120">
							<h:outputText value="Monto a Girar :"/>
						</rich:column>
						<rich:column width="170">
							<h:inputText readonly="true"
								value="#{cierreFondosController.controlFondosFijosCerrar.bdMontoSaldo}"
								style="background-color: #BFBFBF;"
								size="25">
								<f:converter converterId="ConvertidorMontos"/>
							</h:inputText>
						</rich:column>
						<rich:column width="350">
							<h:inputText size="74"
								readonly="true"
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{cierreFondosController.strMontoGirar}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="120" style="vertical-align: top">
							<h:outputText value="Observación :"/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="2" cols="124"
								value="#{cierreFondosController.strObservacion}"
								disabled="#{cierreFondosController.deshabilitarNuevo}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="1" id="panelDocumentosAgregadosC">
						<rich:column>
							<rich:dataTable
					    		var="item"
					    		styleClass="datatable"
					            value="#{cierreFondosController.listaIngresoDetalleInterfaz}"
						 		sortMode="single"
							  	width="840px"
				                rows="#{fn:length(cierreFondosController.listaIngresoDetalleInterfaz)}">
				                    
								<rich:column width="20px" style="text-align: left">
				            		<h:outputText value="#{item.intOrden}"/>
				            	</rich:column>
				            	<rich:column width="120" style="text-align: left">
				                   	<f:facet name="header">
				                		<h:outputText value="Documento"/>
				                   	</f:facet>
				                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"
										itemValue="intIdDetalle" 
										itemLabel="strDescripcion"
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
				           	</rich:dataTable>
						</rich:column>
					</h:panelGrid>
				
				</h:panelGroup>
				
				</h:panelGroup>
			
			</h:panelGroup>
		
		</rich:panel>
		
	</h:panelGroup>	

	</rich:panel>
</h:form>
