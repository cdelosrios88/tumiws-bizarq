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
	<!-- Modificaci�n : Junior Chavez   	-->




<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CAJA" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">
            	<rich:column width="90" style="text-align: left;">
					<h:outputText value="Documento :"/>
				</rich:column>
				<rich:column width="200">
	               	<h:selectOneMenu
						style="width: 190px;"
						value="#{cajaController.ingresoFiltro.intParaDocumentoGeneral}">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA}" itemLabel="Ingreso de Caja"/>
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO}" itemLabel="Dep�sito a Banco"/>
					</h:selectOneMenu>
				</rich:column>
            	<rich:column width="120">
					<h:outputText value="N�mero de Operaci�n :"/>
				</rich:column>
				<rich:column width="220">
					<h:inputText size="35" value="#{cajaController.ingresoFiltro.strNumeroOperacion}"/>
				</rich:column>
            	<rich:column width="95" style="text-align: left;">
                	<h:outputText value="Fecha de Ingreso: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{cajaController.ingresoFiltro.dtDechaDesde}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{cajaController.ingresoFiltro.dtDechaHasta}"
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	
            </h:panelGrid>
            
            
        	<h:panelGrid columns="11" id="panelPersonaFiltroC">
            	<rich:column width="90">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column width="200" style="text-align: left;">
					<h:selectOneMenu
						value="#{cajaController.intTipoPersonaFiltro}"
						style="width: 190px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPersonaFiltroC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120" style="text-align: left;">
					<h:selectOneMenu
						rendered="#{cajaController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{cajaController.intTipoBusquedaPersonaFiltro}"
						style="width: 120px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{cajaController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{cajaController.intTipoBusquedaPersonaFiltro}"
						style="width: 120px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="3" itemLabel="RUC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="220" style="text-align: left;">
					<h:inputText size="35"
						value="#{cajaController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="60" style="text-align: left;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column width="135">
					<h:selectOneMenu
						value="#{cajaController.ingresoFiltro.intParaEstado}"
						style="width: 135px;">
						<f:selectItem itemValue="1" itemLabel="Activo"/>
						<f:selectItem itemValue="3" itemLabel="Anulado"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosC,panelMensajeC"
                    	action="#{cajaController.buscar}" 
                    	style="width:100px"/>
            	</rich:column>
            </h:panelGrid>
            
            
			<h:panelGrid columns="8" id="panelGiroSucursalBus">
				<rich:column width="90">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="200">
					<h:selectOneMenu
						value="#{cajaController.ingresoFiltro.intSucuIdSucursal}"
						style="width: 190px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cajaController.listaTablaSucursal}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange"
							action="#{cajaController.seleccionarSucursal}" 
							reRender="panelGiroSucursalBus"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Sub-Sucursal :" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						value="#{cajaController.ingresoFiltro.intSudeIdSubsucursal}"
						style="width: 120px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cajaController.listaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
            
            <h:panelGrid id="panelTablaResultadosC">
            	<rich:extendedDataTable id="tblResultadoC"
	          		enableContextMenu="false"
	          		sortMode="single"
                    var="item"
                    value="#{cajaController.listaIngreso}"
					rowKeyVar="rowKey"
					rows="5"
					width="1020px"
					height="160px"
					align="center">
                                
                    <rich:column width="20" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro Ingreso"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroIngreso}"/>
                	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.dtFechaIngreso}">
                      		<f:convertDateTime pattern="dd/MM/yyyy"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Documento"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaDocumentoGeneral}"/>
                  	</rich:column>
                  	<rich:column width="300" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Nombre"/>
                        </f:facet>
                        <h:outputText
							rendered="#{(item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)}"
							value="DNI : #{item.persona.documento.strNumeroIdentidad} - #{item.persona.natural.strNombreCompleto}"
							title="DNI : #{item.persona.documento.strNumeroIdentidad} - #{item.persona.natural.strNombreCompleto}"/>
						<h:outputText
							rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{item.persona.strRuc} - #{item.persona.juridica.strRazonSocial}"
							title="RUC : #{item.persona.strRuc} - #{item.persona.juridica.strRazonSocial}"/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Banco"/>
                        </f:facet>
                        <h:panelGroup 
                        	rendered="#{item.intParaDocumentoGeneral==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO}">
                        	<tumih:outputText 
	                         	cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{item.bancoFondo.intBancoCod}"/>
                        </h:panelGroup>                         
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
                   		actionListener="#{cajaController.verRegistro}"
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
				<h:outputText value="Para IMPRIMIR un MOVIMIENTO DE CAJA hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeC" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{cajaController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{cajaController.mostrarMensajeExito}"/>
			<h:outputText value="#{cajaController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{cajaController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesC">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{cajaController.habilitarPanelInferior}"
					reRender="contPanelInferiorC,panelMensajeC,panelBotonesC" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{cajaController.grabar}" 
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC,panelTablaResultadosC"
			    	disabled="#{!cajaController.habilitarGrabar}"/>
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{cajaController.deshabilitarPanelInferior}"
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC"/>
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorC">
			
		<rich:panel  rendered="#{cajaController.mostrarPanelInferior}" style="border:1px solid #17356f;background-color:#DEEBF5;">
			
			<h:panelGroup rendered="#{!cajaController.datosValidados}">
				<h:panelGrid columns="12" id="panelValidarC">
					<rich:column width="80" style="text-align: left;">
						<h:outputText value="Documento :"/>
					</rich:column>
					<rich:column width="200">
	                	<h:selectOneMenu
							style="width: 190px;"
							value="#{cajaController.intTipoDocumentoValidar}">
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA}" itemLabel="Ingreso de Caja"/>
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO}" itemLabel="Dep�sito a Banco"/>
						</h:selectOneMenu>
					</rich:column>
	            	<rich:column width="60" style="text-align: left;">
						<h:outputText value="Moneda :"/>
					</rich:column>
					<rich:column width="100">
	                	<h:selectOneMenu
	                		value="#{cajaController.intMonedaValidar}"
							style="width: 90px;">
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_TIPOMONEDA_SOLES}" itemLabel="Soles"/>
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_TIPOMONEDA_DOLARES}" itemLabel="D�lares"/>
						</h:selectOneMenu>
	            	</rich:column>	
	            	<rich:column width="110" style="text-align: left;">
						<h:outputText value="Forma de Pago :"/>
					</rich:column>
					<rich:column width="150">
	                	<h:selectOneMenu
	                		value="#{cajaController.intFormaPagoValidar}"
							style="width: 140px;">
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_PAGOINGRESO_EFECTIVO}" itemLabel="Efectivo"/>
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_PAGOINGRESO_CHEQUE}" itemLabel="Cheque"/>
						</h:selectOneMenu>
	            	</rich:column>
	            	<rich:column width="60" style="text-align: left;">
						<h:outputText value="Sucursal :"/>
					</rich:column>
					<rich:column width="200">
	                	<h:inputText 
	                		value="#{cajaController.sucursalUsuario.juridica.strSiglas}-#{cajaController.subsucursalUsuario.strDescripcion}" 
	                		readonly="true"
							style="background-color: #BFBFBF;"
							size="25"/>
	            	</rich:column>	            	
            	</h:panelGrid>
            	
            	<rich:spacer height="12px"/>
            	
				<h:panelGrid columns="1" rendered="#{!cajaController.datosValidados}">
					<rich:column width="950">
						<a4j:commandButton styleClass="btnEstilos"
		                	value="Validar Datos"
		                	reRender="contPanelInferiorC,panelBotonesC,panelMensajeC"
		                    action="#{cajaController.validarDatos}" 
		                    style="width:950px"/>
					</rich:column>
				</h:panelGrid>			
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{(cajaController.datosValidados) 
							&& (cajaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)}">
				
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
							value="Caja - #{cajaController.strMontoSaldoCaja}"
							style="background-color: #BFBFBF;"
							size="25"/>
					</rich:column>
					<rich:column width="100" style="text-align: left;">
						<h:outputText rendered="#{cajaController.intFormaPagoValidar==applicationScope.Constante.PARAM_T_PAGOINGRESO_CHEQUE}"
							value="Nro de Cheque :"/>
					</rich:column>
					<rich:column width="120">
						<h:inputText rendered="#{cajaController.intFormaPagoValidar==applicationScope.Constante.PARAM_T_PAGOINGRESO_CHEQUE}" 
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.strNumeroCheque}"
							size="15">
						</h:inputText>
					</rich:column>
					<rich:column width="110" style="text-align: left;">
						<h:outputText value="Operaci�n :"/>
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
							value="#{cajaController.sucursalIngreso.juridica.strSiglas} - #{cajaController.subsucursalIngreso.strDescripcion}"
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
							property="#{cajaController.intFormaPagoValidar}"
							readonly="true"
							style="background-color: #BFBFBF;"
							size="15"/>
					</rich:column>
					<rich:column width="110" style="text-align: left;">
						<h:outputText value="Fecha de Ingreso :"/>
					</rich:column>
					<rich:column width="150">
						<h:inputText readonly="true"
							value="#{cajaController.dtFechaActual}"
							style="background-color: #BFBFBF; text-align:center;"
							size="23">
							<f:convertDateTime pattern="dd/MM/yyyy"/>
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="8px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="200">
						<h:outputText value="PERSONA QUE REALIZO EL INGRESO"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGroup id="panelPersonaC">
				
				<h:panelGrid columns="11" >
					<rich:column width="140">
						<h:outputText value="Tipo de Persona :"/>
					</rich:column>
					<!--  -->
					<rich:column width="100">
						<h:selectOneMenu id="cboTipoPersonaC"
							onchange="getPersonaRolC(#{applicationScope.Constante.ONCHANGE_VALUE})"
							style="width: 100px;"
							value="#{cajaController.intTipoPersonaC}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<!--  -->
					<rich:column width="130">
						<h:selectOneMenu id="cboPersonaRolC"
							onchange="getTipoDocumentoC(#{applicationScope.Constante.ONCHANGE_VALUE})"
							style="width: 130px;"
							value="#{cajaController.intPersonaRolC}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel"
								value="#{cajaController.lstPersonaRol}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="630">
						<h:inputText readonly="true"
							rendered="#{empty cajaController.personaSeleccionada && empty cajaController.entidadSeleccionada}"
							style="background-color: #BFBFBF;"
							size="100"/>
						<h:inputText readonly="true"
							rendered="#{(cajaController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)}"
							value="DNI : #{cajaController.personaSeleccionada.documento.strNumeroIdentidad} - #{cajaController.personaSeleccionada.natural.strNombreCompleto}
							 - Roles : #{cajaController.strListaPersonaRolUsar}"
							style="background-color: #BFBFBF;"
							size="100"/>
						<h:inputText readonly="true"
							rendered="#{cajaController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{cajaController.personaSeleccionada.strRuc} - #{cajaController.personaSeleccionada.juridica.strRazonSocial}
							 - Roles : #{cajaController.strListaPersonaRolUsar}"
							style="background-color: #BFBFBF;"
							size="100"/>
						<h:inputText readonly="true"
							rendered="#{(cajaController.intTipoPersonaC==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA && cajaController.intPersonaRolC==applicationScope.Constante.PARAM_T_TIPOROL_ENTIDAD && cajaController.entidadSeleccionada.strSucursalConcatenado!=null)}"
							value="RUC: #{cajaController.entidadSeleccionada.strSucursalConcatenado} - Tipo Socio: #{cajaController.entidadSeleccionada.strTipoSocioConcatenado} - Modalidad: #{cajaController.entidadSeleccionada.strModalidadConcatenado}"
							style="background-color: #BFBFBF;"
							size="100"/>
					</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{empty cajaController.entidadSeleccionada}"
	                		value="Buscar"
	                		reRender="pBuscarPersonaCaja"
	                    	action="#{cajaController.abrirPopUpBuscarEntidad}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarEntidadCaja')"
	                    	reRender="panelPopUpBuscarEntidadC"
	                    	style="width:80x"/>
	                    	<!-- 
	                    <a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{empty cajaController.personaSeleccionada}"
	                		value="Buscar"
	                		reRender="pBuscarPersonaCaja"
	                    	action="#{cajaController.abrirPopUpBuscarPersona}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarPersonaCaja')"
	                    	style="width:80x"/> -->
	                    <a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{not empty cajaController.personaSeleccionada}"
	                		value="Quitar"
	                		reRender="contPanelInferiorC"
	                    	action="#{cajaController.quitarPersonaSeleccionada}"
	                    	disabled="#{cajaController.deshabilitarNuevo}"
	                    	style="width:80x"/>
	            	</rich:column>
	            </h:panelGrid>
	            	            
	            </h:panelGroup>
	            
	            <rich:spacer height="3px"/>
				
				
				<h:panelGroup id="panelDocumentoC">
	            
	            <h:panelGrid columns="8">
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Tipo de Documento : "/>
			        </rich:column>
			        <!--  ||(cajaController.deshabilitarTipoDocumento) -->
			        <rich:column width="175">
						<h:selectOneMenu id="cboTipoDocumentoC"
							style="width: 150px;"
							disabled="#{(not empty cajaController.listaIngresoDetalleInterfaz)||(cajaController.deshabilitarNuevo)}"
							value="#{cajaController.intTipoDocumentoAgregar}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
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
	                		value="Buscar"
	                		reRender="pBuscarDocPlanillaEfectuadaCaja"
	                    	action="#{cajaController.abrirPopUpBuscarDocumento}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarDocPlanillaEfectuadaCaja')"
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
				<a4j:outputPanel id="opCuentaSocioC">
					<h:panelGrid id="pgCuentaSocioC" columns="6" rendered="#{cajaController.blnEsNaturalSocio}" >
					<rich:column width="120" style="text-align: left;">
						<h:outputText value="Cuenta Socio / Cliente :"/>
					</rich:column>
					<rich:column width="175">
						
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
	                		value="Detalle"
	                		reRender="pBuscarDocumentoCaja"
	                    	action="#{cajaController.abrirPopUpBuscarDocumento}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarDocumentoCaja')"
	                    	style="width:80px"/>
	            	</rich:column>
				</h:panelGrid>
				</a4j:outputPanel>
				
				
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
					<rich:column width="650">
						<h:inputText readonly="true"
							rendered="#{empty cajaController.gestorCobranzaSeleccionado}"
							style="background-color: #BFBFBF;"
							size="100"/>
						<h:inputText readonly="true"
							rendered="#{(not empty cajaController.gestorCobranzaSeleccionado)}"
							value="DNI : #{cajaController.gestorCobranzaSeleccionado.persona.documento.strNumeroIdentidad} - #{cajaController.gestorCobranzaSeleccionado.persona.natural.strNombreCompleto} - Recibo Manual: #{cajaController.reciboManual.intSerieRecibo} - #{cajaController.reciboManual.intNumeroActual}"
							style="background-color: #BFBFBF;"
							size="100"/>
					</rich:column>
	            	<rich:column width="80">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		rendered="#{(empty cajaController.gestorCobranzaSeleccionado)}"
	                		value="Buscar"
	                		reRender="pBuscarGestorIngresoCaja"
	                    	action="#{cajaController.abrirPopUpBuscarGestor}"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarGestorIngresoCaja')"
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
	            	<rich:column width="90">
						<h:outputText value="Recibo manual :"/>
					</rich:column>
					<rich:column width="100">
						<h:inputText size="10"
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.bdMontoIngresar}"
							onkeypress="return soloNumerosDecimales(this)">							
						</h:inputText>
					</rich:column>
					<rich:column width="120">
						<h:inputText size="15"
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.bdMontoIngresar}"
							onkeypress="return soloNumerosDecimales(this)">							
						</h:inputText>
					</rich:column>
	            </h:panelGrid>
	            
	            <rich:spacer height="3px"/>
	            
	            </h:panelGroup>
				
				
				<h:panelGrid columns="6">
					<rich:column width="120" style="vertical-align: top">
						<h:outputText value="Observaci�n :"/>
					</rich:column>
					<rich:column>
						<h:inputTextarea rows="2" cols="140"
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
			                    	<h:outputText value="Descripci�n"/>                      		
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
			
			
			<h:panelGroup rendered="#{(cajaController.datosValidados) 
							&& (cajaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)}">
				
				<h:panelGrid columns="1">
					<rich:column width="120">
						<h:outputText value="DEP�SITO DE BANCO"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8" id="panelCuentaBancaria">
					<rich:column width="120">
						<h:outputText value="Cuenta :"/>
					</rich:column>
					<rich:column width="297">
						<h:selectOneMenu
							style="width: 290px;"
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.intBancoSeleccionado}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel" 
								value="#{cajaController.listaBancoFondo}"
								itemValue="#{sel.id.intItembancofondo}"
								itemLabel="#{sel.personaEmpresa.persona.juridica.strRazonSocial}"/>
							<a4j:support event="onchange"
								action="#{cajaController.seleccionarBanco}" 
								reRender="panelCuentaBancaria"/>
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="380">
			        	<h:selectOneMenu
			            	style="width: 372px;"
							value="#{cajaController.intBancoCuentaSeleccionado}"
							disabled="#{cajaController.deshabilitarNuevo}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{cajaController.listaBancoCuenta}"
								itemValue="#{sel.id.intItembancocuenta}"
								itemLabel="#{sel.strEtiqueta}"/>
						</h:selectOneMenu>
			       	</rich:column>		
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8">
					<rich:column width="120">
						<h:outputText value="Sucursal :"/>
					</rich:column>
					<rich:column width="180">
						<h:inputText readonly="true"
							value="#{cajaController.sucursalIngreso.juridica.strSiglas} - #{cajaController.subsucursalIngreso.strDescripcion}"
							style="background-color: #BFBFBF;"
							size="30"/>
					</rich:column>
					<rich:column width="110" style="text-align: left;">
						<h:outputText value="Fecha de Dep�sito :"/>
					</rich:column>
					<rich:column width="100">
						<h:inputText readonly="true"
							value="#{cajaController.dtFechaActual}"
							style="background-color: #BFBFBF;"
							size="15">
							<f:convertDateTime pattern="dd/MM/yyyy"/>
						</h:inputText>
					</rich:column>
					<rich:column width="110" style="text-align: left;">
						<h:outputText value="Tipo de Documento :"/>
					</rich:column>
					<rich:column width="150">
						<tumih:inputText
							cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO}"
							readonly="true"
							style="background-color: #BFBFBF;"
							size="23"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8">
					<rich:column width="120">
						<h:outputText value="Nro de Operaci�n :"/>
					</rich:column>
					<rich:column width="180">
						<h:inputText
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.strNumeroOperacion}"
							size="30"/>
					</rich:column>
					<rich:column width="110" style="text-align: left;">
						<h:outputText value="Otros Ingresos :"/>
					</rich:column>
					<rich:column width="100">
						<h:inputText
							disabled="#{cajaController.deshabilitarNuevo}"
							value="#{cajaController.bdOtrosIngresos}"
							size="15"/>
					</rich:column>					
				</h:panelGrid>
				
				<rich:spacer height="8px"/>
				
				<h:panelGroup id="panelIngresosDepositar">
				
				<h:panelGrid columns="1">
					<rich:column width="300">
						<h:outputText value="DOCUMENTOS ASOCIADOS AL DEP�SITO"/>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="1">
					<rich:column>
						<rich:dataTable
				    		var="item"
				    		styleClass="datatable"
				            value="#{cajaController.listaIngresoDepositar}"
					 		sortMode="single"
						  	width="753px"
			                rows="#{fn:length(cajaController.listaIngresoDepositar)}">
			                    
							<rich:column width="120" style="text-align: left">
			                   	<f:facet name="header">
			                		<h:outputText value="Nro. Ingreso"/>
			                   	</f:facet>
			                   	<h:outputText value="#{item.strNumeroIngreso}"/>
			                </rich:column>
			                <rich:column width="80" style="text-align: center">
			                   	<f:facet name="header">
			                   		<h:outputText value="Fecha"/>
			                   	</f:facet>
			                  	<h:outputText value="#{item.dtFechaIngreso}">
                      				<f:convertDateTime pattern="dd/MM/yyyy"/>
                      			</h:outputText>
			                </rich:column>
			                <rich:column width="70" style="text-align: center">
			                   	<f:facet name="header">
			                   		<h:outputText value="Persona"/>
			                   	</f:facet>
			                  	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}"
									itemValue="intIdDetalle" 
									itemLabel="strDescripcion"
									property="#{item.persona.intTipoPersonaCod}"/>
			                </rich:column>
			                <rich:column width="213" style="text-align: left">
			                  	<f:facet name="header">
			                   		<h:outputText value="Nombre"/>
			                   	</f:facet>
			                   	<h:outputText
			                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			                   		value="#{item.persona.natural.strNombreCompleto}"/>
								<h:outputText
			                   		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			                   		value="#{item.persona.juridica.strRazonSocial}"/>
			                </rich:column>
			                <rich:column width="50" style="text-align: center">
			                   	<f:facet name="header">
			                   		<h:outputText value="Moneda"/>
			                   	</f:facet>
			                  	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
									itemValue="intIdDetalle" 
									itemLabel="strDescripcion"
									property="#{cajaController.intMonedaValidar}"/>
			                </rich:column>
			                <rich:column width="80" style="text-align: right">
			                    <f:facet name="header">
			                    	<h:outputText value="Depositable"/>                      		
			                    </f:facet>
			                    <h:outputText rendered="#{!cajaController.deshabilitarNuevo}" 
			                    	value="#{item.bdMontoDepositable}">
			                      	<f:converter converterId="ConvertidorMontos" />
			                    </h:outputText>
			              	</rich:column>
			              	<rich:column width="80" style="text-align: right">
			                    <f:facet name="header">
			                    	<h:outputText value="Depositar"/>                      		
			                    </f:facet>
			                    <h:inputText size="10"
			                    	disabled="#{cajaController.deshabilitarNuevo}"
			                    	value="#{item.bdMontoDepositar}" 
			                    	onkeypress="return soloNumerosDecimales(this)">
			                    </h:inputText>
			              	</rich:column>
			              	<rich:column width="50" style="text-align: center">
			                    <f:facet name="header">
			                    	<h:outputText value="Acci�n"/>      		
			                    </f:facet>
			                    <a4j:commandLink
									value="Quitar"
					            	actionListener="#{cajaController.quitarIngresoDeposito}"
									reRender="panelIngresosDepositar"
									disabled="#{cajaController.deshabilitarNuevo}">
									<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
			              	</rich:column>			               
			           	</rich:dataTable>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Monto a Depositar :"/>
					</rich:column>
					<rich:column width="180">
						<h:inputText size="24"	
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.bdMontoDepositarTotal}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="73"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.strMontoDepositarTotalDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120">
						<h:outputText value="Ajuste de Redondeo :"/>
					</rich:column>
					<rich:column width="180">
						<h:inputText size="24"	
							readonly="true" 
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.bdMontoAjuste}">
							<f:converter converterId="ConvertidorMontos" />
						</h:inputText>
					</rich:column>
					<rich:column width="350">
						<h:inputText size="73"
							readonly="true"
							style="background-color: #BFBFBF;font-weight:bold;"
							value="#{cajaController.strMontoAjusteDescripcion}"/>
					</rich:column>
				</h:panelGrid>
				</h:panelGroup>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="11" id="panelVoucher">
					<rich:column width="120">
						<h:outputText value="Anexar voucher :"/>
					</rich:column>
					<rich:column width="685">
						<h:inputText 
							rendered="#{empty cajaController.archivoVoucher}"
							size="124"
							readonly="true"
							style="background-color: #BFBFBF;"/>
						<h:inputText 
							rendered="#{not empty cajaController.archivoVoucher}"
							value="#{cajaController.archivoVoucher.strNombrearchivo}"
							size="124"
							readonly="true"
							style="background-color: #BFBFBF;"/>
					</rich:column>
	            	<rich:column width="120">
	                	<a4j:commandButton
							rendered="#{(empty cajaController.archivoVoucher)&&(!cajaController.deshabilitarNuevo)}"
							disabled="#{cajaController.deshabilitarNuevo}"
	                		styleClass="btnEstilos"
	                		value="Adjuntar Voucher"
	                		reRender="pAdjuntarVoucher"
	                		oncomplete="Richfaces.showModalPanel('pAdjuntarVoucher')"
	                		style="width:120px"/>
	                	<a4j:commandButton
							rendered="#{(not empty cajaController.archivoVoucher)&&(!cajaController.deshabilitarNuevo)}"
							disabled="#{cajaController.deshabilitarNuevo}"
	                		styleClass="btnEstilos"
	                		value="Quitar Voucher"
	                		reRender="panelVoucher,pAdjuntarVoucher"
	                		action="#{cajaController.quitarVoucher}"
	                		style="width:120px"/>
	                	<h:commandLink  value=" Descargar"
		                 	rendered="#{(not empty cajaController.archivoVoucher)&&(cajaController.deshabilitarNuevo)}"
							actionListener="#{fileUploadController.descargarArchivo}">
							<f:attribute name="archivo" value="#{cajaController.archivoVoucher}"/>
						</h:commandLink>
	            	</rich:column>
	            </h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="6">
					<rich:column width="120" style="vertical-align: top">
						<h:outputText value="Observaci�n :"/>
					</rich:column>
					<rich:column>
						<h:inputTextarea rows="2" cols="124"
							value="#{cajaController.strObservacion}"
							disabled="#{cajaController.deshabilitarNuevo}"/>
					</rich:column>
				</h:panelGrid>
				
			</h:panelGroup>
			
		</rich:panel>
		
	</h:panelGroup>	

	</rich:panel>
</h:form>
