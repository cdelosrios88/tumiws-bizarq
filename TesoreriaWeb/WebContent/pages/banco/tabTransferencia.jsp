<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modificación: Junior Chávez 23.12.2013   	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->




<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="TRANSFERENCIAS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">

            	<rich:column width="90" style="text-align: left;">
                	<h:outputText value="Tipo Documento :"/>
            	</rich:column>
            	<rich:column width="210">
            		<h:selectOneMenu
						style="width: 200px;"
						value="#{transferenciaController.egresoFiltro.intParaDocumentoGeneral}">
						<tumih:selectItems var="sel"
							value="#{transferenciaController.listaTipoDocumento}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="115">
                	<h:outputText value="Número de Transf. :"/>
            	</rich:column>
            	<rich:column width="90">
            		<h:inputText size="12" 
            			value="#{transferenciaController.egresoFiltro.intNumeroTransferencia}" 
            			onkeypress="return soloNumeroNaturales(event)"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<h:outputText value="Fecha de Egreso: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{transferenciaController.dtDesdeFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{transferenciaController.dtHastaFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="50" style="text-align: left;">
                	<h:outputText value="Monto :"/>
            	</rich:column>
            	<rich:column>
            		<h:inputText size="10" value="#{transferenciaController.egresoFiltro.bdMontoTotal}" onkeypress="return soloNumerosDecimales(this)"/>
            	</rich:column>
            </h:panelGrid>
            
        	<h:panelGrid columns="11" id="panelPersonaFiltroF">
        		<rich:column width="90">
					<h:outputText value="Tipo de Persona : "/>
				</rich:column>
				<rich:column width="160" style="text-align: left;">
					<h:selectOneMenu
						value="#{transferenciaController.intTipoPersonaFiltro}"
						style="width: 160px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPersonaFiltroF"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="110" style="text-align: left;">
					<h:selectOneMenu
						rendered="#{transferenciaController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
						value="#{transferenciaController.intTipoBusquedaPersona}"
						style="width: 110px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{transferenciaController.intTipoPersonaFiltro==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
						value="#{transferenciaController.intTipoBusquedaPersona}"
						style="width: 110px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="3" itemLabel="RUC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="150" style="text-align: left;">
					<h:inputText size="23"
						value="#{transferenciaController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="95" style="text-align: left;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{transferenciaController.egresoFiltro.intParaEstado}"
						style="width: 95px;">
						<f:selectItem itemValue="1" itemLabel="Activo"/>
						<f:selectItem itemValue="3" itemLabel="Anulado"/>
					</h:selectOneMenu>
				</rich:column>				
            	<rich:column width="160" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaResultadosT,panelMensajeT"
                    	action="#{transferenciaController.buscar}" style="width:160px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosT">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{transferenciaController.listaEgreso}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="165px" 
					align="center">
                                
					<rich:column width="30" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
					<rich:column width="120" style="text-align: center">
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
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Documento"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaDocumentoGeneral}"/>
                  	</rich:column>
                  	<rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Cuenta"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.listaEgresoDetalle[0].strNumeroDocumento}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Transferencia"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.intNumeroTransferencia}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotal}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{transferenciaController.verRegistro}"
						reRender="panelMensajeT, panelBotones, contPanelInferior">
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
				<h:outputText value="Para Imprimir una transferencia hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeT" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{transferenciaController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{transferenciaController.mostrarMensajeExito}"/>
			<h:outputText value="#{transferenciaController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{transferenciaController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{transferenciaController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensajeT,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{transferenciaController.grabar}" 
			    	reRender="contPanelInferior,panelMensajeT,panelBotones,panelTablaResultadosT"
			    	disabled="#{!transferenciaController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{transferenciaController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensajeT,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{transferenciaController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<rich:spacer height="3px"/>
			
			<h:panelGroup rendered="#{!transferenciaController.datosValidados}">
				
				<h:panelGrid columns="8" id="panelValidacion">
					<rich:column width="140" style="text-align: left;">
						<h:outputText value="Tipo de Documento : "/>
			        </rich:column>
			        <rich:column width="200">
						<h:selectOneMenu
							style="width: 200px;"
							value="#{transferenciaController.intTipoDocumentoValidar}">
							<tumih:selectItems var="sel"
								value="#{transferenciaController.listaTipoDocumento}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="panelValidacion"  />	
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="90" style="text-align: center;">
						<h:outputText value="Sucursal :"/>
			        </rich:column>
			        <rich:column width="200">
						<h:inputText size="35" value="#{transferenciaController.sucursalUsuario.juridica.strSiglas} - #{transferenciaController.subsucursalUsuario.strDescripcion}" 
							readonly="true" style="background-color: #BFBFBF;"/>					
			        </rich:column>
			        <rich:column width="150" 
			        	style="text-align: center;" 
			        	rendered="#{transferenciaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS}">
						<h:selectBooleanCheckbox value="#{transferenciaController.seleccionaTelecredito}"/>
						<h:outputText value="Telecrédito"/>
			        </rich:column>
				</h:panelGrid>
				
				<rich:spacer height="15px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="865">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Validar Datos" reRender="contPanelInferior,panelBotones,panelMensajeT"
	                    	action="#{transferenciaController.validarDatos}" style="width:865px"/>
				  	</rich:column>
				</h:panelGrid>
			
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{transferenciaController.datosValidados}">
			
				
				<h:panelGroup rendered="#{transferenciaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS}">
					
					<h:panelGrid columns="8" rendered="#{transferenciaController.deshabilitarNuevo}">
						<rich:column width=	"120">
							<h:outputText value="Fecha de Transf : "/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="26" value="#{transferenciaController.egresoNuevo.dtFechaEgreso}"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:inputText>
						</rich:column>
						<rich:column width="120">
							<h:outputText value="Número de Egreso : "/>
						</rich:column>						
						<rich:column width="140">
							<h:inputText size="28" 
								value="#{transferenciaController.egresoNuevo.strNumeroEgreso}"
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						<rich:column width="125">
							<h:outputText value="Número de Asiento :"/>
						</rich:column>
						<rich:column width="100">
							<h:inputText size="17" 
								rendered="#{empty transferenciaController.libroDiario}"
								readonly="true"
								style="background-color: #BFBFBF;font-weight:bold;"/>
							<h:inputText size="17" 
								rendered="#{not empty transferenciaController.libroDiario}"
								value="#{transferenciaController.libroDiario.strNumeroAsiento}"
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"/>								
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column width="120">
							<h:outputText value="Fecha de Egreso :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="26" value="#{transferenciaController.egresoNuevo.dtFechaEgreso}" 
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:inputText>
						</rich:column>
						<rich:column width="120" style="text-align: rigth">
							<h:outputText value="Tipo de Documento :"/>
						</rich:column>
						<rich:column width="300">
							<tumih:inputText property="#{transferenciaController.intTipoDocumentoValidar}"
								size="32"
								style="background-color: #BFBFBF;" readonly="true"
								itemLabel="strDescripcion" itemValue="intIdDetalle"
								cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="20px"/>
					
					<h:panelGrid columns="2">
						<h:outputText value="BANCO DE ORIGEN DE TRANSFERENCIA"/>
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid id="panelCuentaBancariaOrigen" columns="6">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="190">
							<h:selectOneMenu
								style="width: 190px;"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intBancoSeleccionadoOrigen}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoOrigen}" 
									reRender="panelCuentaBancariaOrigen"/>
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="400">
			                <h:selectOneMenu
			                	style="width: 400px;"
								value="#{transferenciaController.intBancoCuentaSeleccionadoOrigen}"
								disabled="#{transferenciaController.deshabilitarNuevo}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBancoCuentaOrigen}"
									itemValue="#{sel.id.intItembancocuenta}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoCuentaOrigen}" 
									reRender="panelCuentaBancariaOrigen,panelCuentaBancariaDestino"/>
							</h:selectOneMenu>
			       		</rich:column>		              	             	
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid columns="2">
						<rich:column width="120">
							<h:outputText value="Sucursal :"/>
						</rich:column>
						<rich:column>
							<h:inputText size="32" 
								value="#{transferenciaController.sucursalUsuario.juridica.strSiglas} - #{transferenciaController.subsucursalUsuario.strDescripcion}" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>							
					</h:panelGrid>
					
					<rich:spacer height="20px"/>
					
					<h:panelGrid columns="2">
						<h:outputText value="BANCO DE DESTINO DE TRANSFERENCIA"/>
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid id="panelCuentaBancariaDestino" columns="6">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="190">
							<h:selectOneMenu 
								style="width: 190px;"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intBancoSeleccionadoDestino}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoDestino}" 
									reRender="panelCuentaBancariaDestino"/>
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="400">
			                <h:selectOneMenu 
			                	style="width: 400px;"
								value="#{transferenciaController.intBancoCuentaSeleccionadoDestino}"
								disabled="#{transferenciaController.deshabilitarNuevo}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBancoCuentaDestino}"
									itemValue="#{sel.id.intItembancocuenta}" 
									itemLabel="#{sel.strEtiqueta}"/>
							</h:selectOneMenu>
			       		</rich:column>		              	             	
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="2">
						<rich:column width="120">
							<h:outputText value="Sucursal :"/>
						</rich:column>
						<rich:column>
							<h:inputText size="32" 
								value="#{transferenciaController.sucursalUsuario.juridica.strSiglas} - #{transferenciaController.subsucursalUsuario.strDescripcion}" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>							
					</h:panelGrid>
					
					<rich:spacer height="20px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="120">
							<h:outputText value="Número de Transf :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="32" 
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intNumeroTransferencia}" 
								onkeypress="return soloNumerosNaturales(event)"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="5">
						<rich:column width="120">
							<h:outputText value="Monto a Girar :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="32"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								rendered="#{empty transferenciaController.egresoNuevo.id.intItemEgresoGeneral}"
								value="#{transferenciaController.bdMontoGirar}"
								onkeypress="return soloNumerosDecimales(this)"/>
							<h:inputText size="32"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								rendered="#{not empty transferenciaController.egresoNuevo.id.intItemEgresoGeneral}"
								value="#{transferenciaController.bdMontoGirar}"
								readonly="true">
								<f:converter converterId="ConvertidorMontos"/>
							</h:inputText>
						</rich:column>
						<rich:column width="130">
							<h:outputText value="Tipo Operación :"/>
						</rich:column>
						<rich:column width="300" rendered="#{empty transferenciaController.egresoNuevo.id.intItemEgresoGeneral}">
							<h:selectOneMenu								
								style="width: 265px;"
								value="#{transferenciaController.egresoNuevo.intParaSubTipoOperacion}">
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaSubTipoOperacion}"
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="300" rendered="#{not empty transferenciaController.egresoNuevo.id.intItemEgresoGeneral}">
							<tumih:outputText 
								cache="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION}" 
								style="width: 265px;"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{transferenciaController.egresoNuevo.intParaSubTipoOperacion}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="120">
							<h:outputText value="Descripción :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="112" readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="120">
							<h:outputText value="Observación :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputTextarea rows="2" cols="113" 
								value="#{transferenciaController.strObservacion}"
								disabled="#{transferenciaController.deshabilitarNuevo && transferenciaController.blnFiltrosGrabOk}"/>
						</rich:column>
					</h:panelGrid>
					
				</h:panelGroup>
				
				
				
				
				<h:panelGroup rendered="#{transferenciaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS && !transferenciaController.seleccionaTelecredito}">
					
					<h:panelGrid columns="6">
						<rich:column>
							<h:outputText value="BANCO DE ORIGEN DE TRANSFERENCIA"/>
						</rich:column>						
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid id="panelCuentaOrigen" columns="6">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="160">
							<h:selectOneMenu
								style="width: 160px;"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intBancoSeleccionadoOrigen}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoOrigen}" 
									reRender="panelCuentaOrigen"/>
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="380">
			                <h:selectOneMenu
			                	style="width: 380px;"
								value="#{transferenciaController.intBancoCuentaSeleccionadoOrigen}"
								disabled="#{transferenciaController.deshabilitarNuevo}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBancoCuentaOrigen}"
									itemValue="#{sel.id.intItembancocuenta}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoCuentaOrigen}" 
									reRender="panelCuentaOrigen"/>
							</h:selectOneMenu>
			       		</rich:column>
			       		<rich:column width="100" style="text-align: left;">
							<h:outputText value="Documento : "/>
			       		</rich:column>
			        	<rich:column width="200">
							<tumih:inputText property="#{transferenciaController.intTipoDocumentoValidar}"
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
							<h:inputText size="27" 
								value="#{transferenciaController.sucursalUsuario.juridica.strSiglas} - #{transferenciaController.subsucursalUsuario.strDescripcion}" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="165" style="text-align: left;">
							<h:outputText value="Número de Transferencia : "/>
						</rich:column>						
						<rich:column width="205">
							<h:inputText size="34" value="#{transferenciaController.intNumeroTransferencia}"/>
						</rich:column>
						<rich:column width="98" style="text-align: left;">
							<h:outputText value="Fecha de Egreso :"/>
						</rich:column>
						<rich:column width="200">
							<h:inputText size="22" 
								value="#{transferenciaController.egresoNuevo.dtFechaEgreso}" 
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="20px"/>
					
					<h:panelGrid columns="6">
						<rich:column>
							<h:outputText value="BANCO DE DESTINO DE TRANSFERENCIA"/>
						</rich:column>						
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
				
					<h:panelGrid columns="11" id="panelPersonaT">
						<rich:column width="120">
							<h:outputText value="Persona :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText readonly="true"
								rendered="#{empty transferenciaController.personaSeleccionada}"
								style="background-color: #BFBFBF;"
								size="124"/>
							<h:inputText readonly="true"
								rendered="#{transferenciaController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
								value="DNI : #{transferenciaController.personaSeleccionada.documento.strNumeroIdentidad} - #{transferenciaController.personaSeleccionada.natural.strNombreCompleto}"
								style="background-color: #BFBFBF;"
								size="124"/>
							<h:inputText readonly="true"
								rendered="#{transferenciaController.personaSeleccionada.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
								value="RUC : #{transferenciaController.personaSeleccionada.strRuc} - #{transferenciaController.personaSeleccionada.juridica.strRazonSocial} - Cuenta : #{transferenciaController.cuentaActual.strNumeroCuenta}"
								style="background-color: #BFBFBF;"
								size="124"/>
						</rich:column>
		            	<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		rendered="#{empty transferenciaController.personaSeleccionada}"
		                		value="Buscar"
		                		reRender="pBuscarPersonaTransferencia"
		                    	action="#{transferenciaController.abrirPopUpBuscarPersona}"
		                    	oncomplete="Richfaces.showModalPanel('pBuscarPersonaTransferencia')"
		                    	style="width:80x"/>
		                    <a4j:commandButton styleClass="btnEstilos"
		                		rendered="#{not empty transferenciaController.personaSeleccionada}"
		                		value="Quitar"
		                		reRender="contPanelInferior"
		                    	action="#{transferenciaController.quitarPersonaSeleccionada}"
		                    	disabled="#{transferenciaController.deshabilitarNuevo}"
		                    	style="width:80x"/>
		            	</rich:column>
		            </h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid id="panelCuentaT" columns="6" rendered="#{transferenciaController.blnVerPanelCuentaBancariaDestino}">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText size="124" 
								rendered="#{empty transferenciaController.cuentaBancariaSeleccionada}"
								readonly="true"/>
							<h:inputText size="124" 
								rendered="#{not empty transferenciaController.cuentaBancariaSeleccionada}"
								readonly="true" 
								value="#{transferenciaController.cuentaBancariaSeleccionada.strNroCuentaBancaria}"/>
						</rich:column>
						<rich:column width="80">
							<a4j:commandButton styleClass="btnEstilos"
                				disabled="#{transferenciaController.deshabilitarNuevo}"
                				value="Buscar"
                				reRender="pBuscarCuentaBancaria"
				        		oncomplete="Richfaces.showModalPanel('pBuscarCuentaBancaria')"
                    			style="width:80px"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGroup id="panelDocumentoT">
					
		            <h:panelGrid columns="8">
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Tipo de Documento : "/>
				        </rich:column>
				        <rich:column width="175">
							<h:selectOneMenu
								style="width: 160px;"
								disabled="#{(not empty transferenciaController.listaEgresoDetalleInterfazAgregado)||(transferenciaController.deshabilitarNuevo)}"
								value="#{transferenciaController.intTipoDocumentoAgregar}">
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
								value="#{transferenciaController.documentoGeneralSeleccionado.strEtiqueta}"/>
						</rich:column>
						<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty transferenciaController.personaSeleccionada) || (transferenciaController.deshabilitarNuevo)}"
		                		value="Buscar"
		                		reRender="frmBuscarDocumentoT"
		                    	action="#{transferenciaController.abrirPopUpBuscarDocumento}"
		                    	oncomplete="Richfaces.showModalPanel('pBuscarDocumentoTransferencia')"
		                    	style="width:80px"/>
		            	</rich:column>
		            	<rich:column width="80">
		                	<a4j:commandButton styleClass="btnEstilos"
		                		disabled="#{(empty transferenciaController.documentoGeneralSeleccionado) || (transferenciaController.deshabilitarNuevo || transferenciaController.blnBeneficiarioSinAutorizacion)}"
		                		value="Agregar"
		                		reRender="contPanelInferiorT,panelMontoT,panelDocumentosAgregadosT,panelDocumentoT,panelMensajeT"
		                    	action="#{transferenciaController.agregarDocumento}"
		                    	style="width:80px"/>
		            	</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="8"
						rendered="#{(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{transferenciaController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{transferenciaController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBeneficiarioPrevision}"
									itemValue="#{sel.id.intItemBeneficiario}"
									itemLabel="#{sel.strEtiqueta}"/>
								<a4j:support event="onchange"
									action="#{transferenciaController.seleccionarBeneficiario}" 
									reRender="panelDocumentoT,pgMsgErrorBeneficiarioT,panelCuentaBancariaBeneficiario"/>
							</h:selectOneMenu>
						</rich:column>

					</h:panelGrid>
										
					<h:panelGrid columns="8"
						rendered="#{(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)}">
						
						<rich:column width="120" style="text-align: left;">
							<h:outputText value="Beneficiario : "/>
					    </rich:column>
					    <rich:column width="680">
							<h:selectOneMenu
								disabled="#{transferenciaController.deshabilitarNuevo}"
								style="width: 678px;"
								value="#{transferenciaController.intBeneficiarioSeleccionar}">
								<f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBeneficiarioPersona}"
									itemValue="#{sel.intIdPersona}"
									itemLabel="#{sel.strEtiqueta}"/>
							
								<a4j:support event="onchange"
									action="#{transferenciaController.seleccionarBeneficiario}" 
									reRender="panelDocumentoT,pgMsgErrorBeneficiarioT,panelCuentaBancariaBeneficiario"/>
							</h:selectOneMenu>						
						</rich:column>
					</h:panelGrid>
					
					<!-- Agregado 22.05.2014 Cuenta Bancaria del beneficiario -->
					<h:panelGrid id="panelCuentaBancariaBeneficiario" columns="6" rendered="#{transferenciaController.blnVerPanelCuentaBancariaBeneficiario}">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="685">
							<h:inputText size="124" 
								rendered="#{empty transferenciaController.cuentaBancariaBeneficiarioSeleccionada}"
								readonly="true"/>
							<h:inputText size="124" 
								rendered="#{not empty transferenciaController.cuentaBancariaBeneficiarioSeleccionada}"
								readonly="true" 
								value="#{transferenciaController.cuentaBancariaBeneficiarioSeleccionada.strNroCuentaBancaria}"/>
						</rich:column>
						<rich:column width="80">
							<a4j:commandButton styleClass="btnEstilos"
                				disabled="#{empty transferenciaController.personaSeleccionada}"
                				value="Buscar"
                				reRender="pBuscarCuentaBancariaBeneficiario"
				        		oncomplete="Richfaces.showModalPanel('pBuscarCuentaBancariaBeneficiario')"
                    			style="width:80px"/>
						</rich:column>
					</h:panelGrid>
					<h:panelGrid id="pgMsgErrorBeneficiarioT" columns="8">
    					<rich:column width="800" style="text-align: left;">
    						<h:outputText value="#{transferenciaController.strMensajeErrorPorBeneficiario}" styleClass="msgError"/>
    					</rich:column>
	    			</h:panelGrid>
	    								
					<h:panelGrid columns="8" id="panelApoderadoT"
						rendered="#{(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120">
							<h:outputText value="Apoderado :"/>
						</rich:column>
						<rich:column width="360">
							<h:inputText rendered="#{empty transferenciaController.personaApoderado}" 
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
							<h:inputText rendered="#{not empty transferenciaController.personaApoderado}" 
								value="DNI : #{transferenciaController.personaApoderado.documento.strNumeroIdentidad} - #{transferenciaController.personaApoderado.natural.strNombreCompleto}"
								size="60" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton
								rendered="#{(empty transferenciaController.personaApoderado) && transferenciaController.blnVerBotonApoderado}"
								disabled="#{transferenciaController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Buscar Persona"
				           		action="#{transferenciaController.abrirPopUpBuscarApoderado}"
				           		reRender="pBuscarPersonaTransferencia"
				           		oncomplete="Richfaces.showModalPanel('pBuscarPersonaTransferencia')"
				           		style="width:130px"/>
				           	<a4j:commandButton
								rendered="#{(not empty transferenciaController.personaApoderado) && transferenciaController.blnVerBotonApoderado}"
								disabled="#{transferenciaController.deshabilitarNuevo}"
				                styleClass="btnEstilos"
				                value="Quitar"
				                action="#{transferenciaController.quitarPersonaApoderado}"
				                reRender="panelApoderadoT,panelCartaPoderT"
				                style="width:130px"/>
						</rich:column>					
					</h:panelGrid>
					
					<h:panelGrid columns="8" id="panelCartaPoderT"
						rendered="#{(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
						||	(transferenciaController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}">
						
						<rich:column width="120">
						</rich:column>
						<rich:column width="360">
							<h:inputText rendered="#{empty transferenciaController.archivoCartaPoder}" 
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
							<h:inputText rendered="#{not empty transferenciaController.archivoCartaPoder}"
								value="#{transferenciaController.archivoCartaPoder.strNombrearchivo}"
								size="60"
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton
								rendered="#{(empty transferenciaController.archivoCartaPoder) && transferenciaController.blnVerBotonApoderado}"
								disabled="#{(empty transferenciaController.personaApoderado) || transferenciaController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Adjuntar Carta Poder"
				           		reRender="panelCartaPoderT,pAdjuntarCartaPoderT"
				           		oncomplete="Richfaces.showModalPanel('pAdjuntarCartaPoderT')"
				           		style="width:130px"/>
				           	<a4j:commandButton
								rendered="#{(not empty transferenciaController.archivoCartaPoder) && transferenciaController.blnVerBotonApoderado}"
								disabled="#{transferenciaController.deshabilitarNuevo}"
				           		styleClass="btnEstilos"
				           		value="Quitar Carta Poder"
				           		reRender="panelCartaPoderT,pAdjuntarCartaPoderT"
				           		action="#{transferenciaController.quitarCartaPoder}"
				           		style="width:130px"/>
						</rich:column>
						<rich:column width="130" 
							rendered="#{(not empty transferenciaController.archivoCartaPoder) && (transferenciaController.deshabilitarNuevo)}">
							<h:commandLink  value=" Descargar"		
								actionListener="#{fileUploadController.descargarArchivo}">
								<f:attribute name="archivo" value="#{transferenciaController.archivoCartaPoder}"/>							
							</h:commandLink>
						</rich:column>
					</h:panelGrid>				
					
					</h:panelGroup>		
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6" id="panelMontoT">
						<rich:column width="120">
							<h:outputText value="Monto a Girar :"/>
						</rich:column>
						<rich:column width="175">
							<h:inputText size="22"	
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{transferenciaController.bdMontoGirar}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:inputText>
						</rich:column>
						<rich:column>
							<h:inputText size="89" 
								readonly="true" 
								value="#{transferenciaController.strMontoGirarDescripcion}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="120">
							<h:outputText value="Diferencial Cambiario :"/>
						</rich:column>
						<rich:column width="175">
							<h:inputText size="22"
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="120" style="vertical-align: top">
							<h:outputText value="Observación :"/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="2" cols="106"
								value="#{transferenciaController.strObservacion}"
								disabled="#{transferenciaController.deshabilitarNuevo && transferenciaController.blnFiltrosGrabOk}"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="1" id="panelDocumentosAgregadosT">
						<rich:column>
							<rich:dataTable
					    		var="item"
					    		styleClass="datatable"
					            value="#{transferenciaController.listaEgresoDetalleInterfazAgregado}"
						 		sortMode="single"
							  	width="950px"
				                rows="#{fn:length(transferenciaController.listaEgresoDetalleInterfazAgregado)}">
				                    
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
			
				
				
				<h:panelGroup rendered="#{transferenciaController.intTipoDocumentoValidar==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS && transferenciaController.seleccionaTelecredito}">
					
					<h:panelGrid columns="6">
						<rich:column>
							<h:outputText value="BANCO DESTINO DE TRANSFERENCIA"/>
						</rich:column>						
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid id="panelCuentaOrigenTT" columns="6">
						<rich:column width="120">
							<h:outputText value="Cuenta Bancaria :"/>
						</rich:column>
						<rich:column width="160">
							<h:selectOneMenu
								style="width: 160px;"
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intBancoSeleccionadoOrigen}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" action="#{transferenciaController.seleccionarBancoOrigen}" 
									reRender="panelCuentaOrigenTT"/>
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="380">
			                <h:selectOneMenu
			                	style="width: 380px;"
								value="#{transferenciaController.intBancoCuentaSeleccionadoOrigen}"
								disabled="#{transferenciaController.deshabilitarNuevo}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel"
									value="#{transferenciaController.listaBancoCuentaOrigen}"
									itemValue="#{sel.id.intItembancocuenta}"
									itemLabel="#{sel.strEtiqueta}"/>
							</h:selectOneMenu>
			       		</rich:column>
			       		<rich:column width="100" style="text-align: left;">
							<h:outputText value="Documento : "/>
			       		</rich:column>
			        	<rich:column width="200">
							<tumih:inputText property="#{transferenciaController.intTipoDocumentoValidar}"
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
							<h:inputText size="27" 
								value="#{transferenciaController.sucursalUsuario.juridica.strSiglas} - #{transferenciaController.subsucursalUsuario.strDescripcion}" 
								readonly="true" 
								style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="155" style="text-align: left;">
							<h:outputText value="Operación de Telecrédito : "/>
						</rich:column>						
						<rich:column width="210">
							<h:inputText size="36" 
								disabled="#{transferenciaController.deshabilitarNuevo}"
								value="#{transferenciaController.intNumeroTransferencia}"/>
						</rich:column>
						<rich:column width="100" style="text-align: left;">
							<h:outputText value="Fecha de Egreso :"/>
						</rich:column>
						<rich:column width="190">
							<h:inputText size="22" value="#{transferenciaController.egresoNuevo.dtFechaEgreso}" 
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="6">
						<rich:column>
							<h:outputText value="DOCUMENTOS CANCELADOS CON TELECREDITO"/>
						</rich:column>
					</h:panelGrid>	
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid columns="6" id="panelDocumentoTT">
						<rich:column width="120">
							<h:outputText value="Documento Generado :"/>
						</rich:column>						
						<rich:column>
							<h:inputText size="52" 
								readonly="true"/>
						</rich:column>
						<rich:column width="90">
							<a4j:commandButton styleClass="btnEstilos"
                				oncomplete="Richfaces.showModalPanel('pBuscarEgreso')"
                				value="Buscar" 
                				reRender="frmBuscarEgreso"
                    			disabled="#{transferenciaController.deshabilitarNuevo}"
                    			action="#{transferenciaController.abrirPopUpBuscarEgreso}" 
                    			style="width:80px"/>
						</rich:column>
					</h:panelGrid>					
					
					<rich:spacer height="3px"/>
					
					
					<h:panelGrid columns="1" id="panelDocumentosAgregadosTT">
						<rich:column>
							<rich:dataTable
				    			id="tablaEgresoAgregado"
				    			var="item" 
				                value="#{transferenciaController.listaEgresoAgregado}" 
						  		rowKeyVar="rowKey" 
						  		rows="5" 
						  		sortMode="single" 
						  		width="600px">
						                           
								<rich:column width="100px" style="text-align: center;">
									<f:facet name="header">
				                    	<h:outputText value="Egreso"/>
				                 	</f:facet>	                 	
						        	<h:outputText value="#{item.strNumeroEgreso}"/>
						      	</rich:column>
								<rich:column width="100px" style="text-align: center;">
									<f:facet name="header">
				                    	<h:outputText value="Fecha"/>
				                 	</f:facet>
				                 	<h:outputText value="#{item.dtFechaEgreso}">
				                 		<f:convertDateTime pattern="dd/MM/yyyy"/>
				                 	</h:outputText>
						      	</rich:column>
								<rich:column width="70px" style="text-align: center;">
									<f:facet name="header">
				                    	<h:outputText value="Persona"/>
				                 	</f:facet>
				                 	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.personaApoderado.intTipoPersonaCod}"/>
						      	</rich:column>
						      	<rich:column width="200px" style="text-align: center;">
									<f:facet name="header">
				                    	<h:outputText value="Nombre"/>
				                 	</f:facet>
				                 	<h:outputText
						        		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
						        		value="#{item.personaApoderado.natural.strNombreCompleto}"/>
						        	<h:outputText
						        		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
						        		value="#{item.personaApoderado.juridica.strRazonSocial}"/>
						      	</rich:column>
						      	<rich:column width="100px" style="text-align: center;">
									<f:facet name="header">
				                    	<h:outputText value="Monto"/>
				                 	</f:facet>
				                 	<h:outputText value="#{item.bdMontoTotal}">
				                 		<f:converter converterId="ConvertidorMontos"/>
				                 	</h:outputText>
						      	</rich:column>								
				            </rich:dataTable>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6" id="panelMontoTT">
						<rich:column width="120">
							<h:outputText value="Monto a Girar :"/>
						</rich:column>
						<rich:column>
							<h:inputText size="12"	
								readonly="true" 
								style="background-color: #BFBFBF;font-weight:bold;"
								value="#{transferenciaController.bdMontoGirar}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:inputText>
						</rich:column>
						<rich:column>
							<h:inputText size="20"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						<rich:column>
							<h:inputText size="52" 
								readonly="true" 
								value="#{transferenciaController.strMontoGirarDescripcion}"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="120">
							<h:outputText value="Diferencial Cambiario :"/>
						</rich:column>
						<rich:column>
							<h:inputText size="12"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						<rich:column>
							<h:inputText size="20"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="3px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="120" style="vertical-align: top">
							<h:outputText value="Observación1 :"/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="2" cols="106" 
								disabled="#{transferenciaController.deshabilitarNuevo && transferenciaController.blnFiltrosGrabOk}"
								value="#{transferenciaController.strObservacion}"/>
						</rich:column>
					</h:panelGrid>
					
				</h:panelGroup>
			
			</h:panelGroup>


		</rich:panel>
		
	</h:panelGroup>	

</rich:panel>
</h:form>