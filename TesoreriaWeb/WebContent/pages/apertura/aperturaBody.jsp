<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modificación: Junior Chavez 16.12.2013	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->

<rich:modalPanel id="pSeleccionarResponsable" width="550" height="300"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Responsable"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionarResponsable" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/apertura/popup/seleccionResponsable.jsp"/>
</rich:modalPanel>

<!-- Autor: jchavez / Tarea: Creación / Fecha: 23.08.2014 /  -->
<!-- Autor: jchavez / Tarea: Modificación / Fecha: 08.09.2014 / Se vuelve a su estado original - OBS. TESORERIA -->
<!-- 
<rich:modalPanel id="pBuscarCuentaBancariaAFF" width="450" height="200"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Cuenta Bancaria"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCuentaBancariaAFF" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/apertura/popup/buscarCuentaBancariaAperturaFondos.jsp"/>
</rich:modalPanel>
-->
<h:outputText value="#{aperturaController.inicioPage}" />
<h:form id="frmApertura">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="APERTURA DE FONDOS FIJOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">              	
            	<rich:column width="70">
            		<h:outputText value="Sucursal : "/>
            	</rich:column>
            	<rich:column width="270">
					<h:selectOneMenu
						style="width: 250px;"
						value="#{aperturaController.controlFondosFijosFiltro.id.intSucuIdSucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{aperturaController.listaSucursalBus}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="80">
            		<h:outputText value="Fondo Fijo : "/>
            	</rich:column>
            	<rich:column width="200">
					<h:selectOneMenu
						style="width: 150px;"
						value="#{aperturaController.controlFondosFijosFiltro.id.intParaTipoFondoFijo}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{aperturaController.listaTipoFondoFijo}" 
							itemValue="#{sel.intIdDetalle}"	itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			  	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaResultados,panelMensaje"
                    	action="#{aperturaController.buscar}" style="width:100px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		rendered="#{aperturaController.controlFondosFijosFiltro.id.intParaTipoFondoFijo!=applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR}"
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{aperturaController.listaControlFondosFijos}"  
					rowKeyVar="rowKey"
					rows="5"
					width="1110px"
					height="160px"
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
                  	<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nro de Apertura"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.anterior.strNumeroApertura}"/>
                  	</rich:column>
                    <rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto utilizado"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.anterior.bdMontoUtilizado}">
                      	<f:converter converterId="ConvertidorMontos"/></h:outputText>
                  	</rich:column>                    
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Saldo"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.anterior.bdMontoSaldo}">
                        <f:converter converterId="ConvertidorMontos"/></h:outputText>
                  	</rich:column>
                  	<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="FyH Cierre"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.anterior.tsFechaCierre}">
                      		<f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Nro de Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.strNumeroApertura}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto utlizado"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoUtilizado}">
                        <f:converter converterId="ConvertidorMontos"/></h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoSaldo}">
                        <f:converter converterId="ConvertidorMontos"/></h:outputText>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOFONDO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intEstadoFondo}"/>
                  	</rich:column>
                  	<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="FyH Cierre"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaCierre}">
                      		<f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Opciones"/>
                        </f:facet>
                         <a4j:commandLink
                        	rendered="#{item.intEstadoFondo==applicationScope.Constante.PARAM_T_ESTADOFONDO_CERRADO}"
							value="Aperturar"
					        actionListener="#{aperturaController.aperturar}"
							reRender="contPanelInferior">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>			
                   	
            	</rich:extendedDataTable>
            	
            	
            	<rich:extendedDataTable id="tblResultado2" 
	          		rendered="#{aperturaController.controlFondosFijosFiltro.id.intParaTipoFondoFijo==applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR}"
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{aperturaController.listaControlFondosFijos}"  
					rowKeyVar="rowKey"
					rows="5"
					width="1110px"
					height="160px"
					align="center">
                    
					<rich:column width="30px" style="text-align: center">
						<f:facet name="header">
                        	<h:outputText value=""/>
                      	</f:facet>
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
                  	<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                	<rich:column width="520px">
                	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Nro de Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.strNumeroApertura}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto utlizado"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoUtilizado}">
                        <f:converter converterId="ConvertidorMontos"/></h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoSaldo}">
                        <f:converter converterId="ConvertidorMontos"/></h:outputText>
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
                  	<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="FyH Cierre"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaCierre}">
                      		<f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Opciones"/>
                        </f:facet>
                         <a4j:commandLink
                        	rendered="#{item.intEstadoFondo==applicationScope.Constante.PARAM_T_ESTADOFONDO_CERRADO}"
							value="Aperturar"
					        actionListener="#{aperturaController.aperturar}"
							reRender="contPanelInferior">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado2" maxPages="10"/>   
					</f:facet>			
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Imprimir un  FONDO FIJO hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{aperturaController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{aperturaController.mostrarMensajeExito}"/>
			<h:outputText value="#{aperturaController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{aperturaController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{aperturaController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{aperturaController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
			    	disabled="#{!aperturaController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{aperturaController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{aperturaController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGroup rendered="#{!aperturaController.datosValidados}">
			
				<h:panelGrid columns="8" id="panelSucursal">
					<rich:column width="80" style="text-align: left;">
						<h:outputText value="Fondo Fijo : "/>
			        </rich:column>
			        <rich:column width="210">
						<h:selectOneMenu
							style="width: 150px;"
							value="#{aperturaController.intTipoFondoFijoValidar}">
							<tumih:selectItems var="sel" 
								value="#{aperturaController.listaTipoFondoFijo}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
			        </rich:column>
					<rich:column width="70" style="text-align: center;">
						<h:outputText value="Sucursal : "/>
				  	</rich:column>
				  	<rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							value="#{aperturaController.accesoValidar.intSucuIdSucursal}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{aperturaController.listaSucursal}"
								itemValue="#{sel.id.intIdSucursal}"
								itemLabel="#{sel.juridica.strSiglas}"/>
							<a4j:support event="onchange" action="#{aperturaController.seleccionarSucursal}" reRender="panelSucursal"  />
						</h:selectOneMenu>
				 	</rich:column>
				    <rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							value="#{aperturaController.accesoValidar.intSudeIdSubsucursal}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{aperturaController.listaSubsucursal}"
								itemValue="#{sel.id.intIdSubSucursal}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	                </rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="8">
					<rich:column width="80" style="text-align: left;">
						<h:outputText value="Moneda : "/>
			        </rich:column>
			        <rich:column width="150">
						<h:selectOneMenu
							style="width: 150px;"
							value="#{aperturaController.intTipoMonedaValidar}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="130" style="text-align: center;">
						<h:outputText value="Tipo de Documento :"/>
			        </rich:column>
			        <rich:column width="360">
						<h:selectOneMenu
							style="width: 360px;"
							value="#{aperturaController.intTipoDocumentoValidar}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel" 
								value="#{aperturaController.listaTipoDocumento}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>						
			        </rich:column>
				</h:panelGrid>
				
				<rich:spacer height="15px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="865">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Validar Datos" reRender="contPanelInferior,panelBotones,panelMensaje"
	                    	action="#{aperturaController.validarDatos}" style="width:865px"/>
				  	</rich:column>
				</h:panelGrid>
			
			</h:panelGroup>
			
			<a4j:outputPanel>			
				<h:panelGroup rendered="#{aperturaController.datosValidados}">
					<h:panelGrid columns="6" rendered="#{aperturaController.deshabilitarNuevo}">
						<rich:column width=	"140">
							<h:outputText value="Número de Egreso : "/>
						</rich:column>
						<rich:column width="200">
							<h:inputText size="26" value="#{aperturaController.controlFondosFijosNuevo.egreso.strNumeroEgreso}"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						<rich:column width="120" style="text-align: left">
							<h:outputText value="Número de Asiento : "/>
						</rich:column>
						<rich:column width="140">
							<h:inputText size="17" value="#{aperturaController.controlFondosFijosNuevo.egreso.libroDiario.strNumeroAsiento}"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						<rich:column width="125">
							<h:outputText value="Número de Apertura :"/>
						</rich:column>
						<rich:column width="100">
							<h:inputText size="17" value="#{aperturaController.controlFondosFijosNuevo.strNumeroApertura}"
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>				
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					
					<h:panelGroup rendered="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA) 
										||(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)}">
				
						<h:panelGrid columns="4" id="panelCuentaBancariaCG">
							<rich:column width="140">
								<h:outputText value="Cuenta Bancaria :"/>
							</rich:column>
							<rich:column width="185">
								<h:selectOneMenu 
									style="width: 185px;"
									disabled="#{aperturaController.deshabilitarNuevo}"
									value="#{aperturaController.intBancoSeleccionado}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>
									<a4j:support event="onchange" action="#{aperturaController.seleccionarBanco}" reRender="panelCuentaBancariaCG"  />
								</h:selectOneMenu>
			                </rich:column>
			                <rich:column width="400">
			                	<h:selectOneMenu 
			                		rendered="#{aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA}"
									style="width: 400px;"
									value="#{aperturaController.intBancoCuentaSeleccionado}"
									disabled="#{aperturaController.deshabilitarNuevo}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel"
										value="#{aperturaController.listaBancoCuenta}" 
										itemValue="#{sel.id.intItembancocuenta}" 
										itemLabel="#{sel.strEtiqueta}"/>
								</h:selectOneMenu>							
								<h:selectOneMenu 
			                		rendered="#{aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA}"
									style="width: 400px;"
									value="#{aperturaController.intBancoCuentaSeleccionado}"
									disabled="#{aperturaController.deshabilitarNuevo}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel"
										value="#{aperturaController.listaBancoCuenta}" 
										itemValue="#{sel.id.intItembancocuenta}" 
										itemLabel="#{sel.strEtiqueta}"/>
									<a4j:support event="onchange" action="#{aperturaController.seleccionarBancoCuenta}" reRender="panelCheque"/>
								</h:selectOneMenu>
			              	</rich:column>		              	             	
						</h:panelGrid>
						
						<rich:spacer height="10px"/>
						
						<h:panelGrid id="panelCheque" columns="4" rendered="#{aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA}">
							<rich:column width="140">
								<h:outputText value="Número de Cheque :"/>
							</rich:column>
							<rich:column width="585">
								<h:selectOneMenu
									style="width: 585px;"
									disabled="#{aperturaController.deshabilitarNuevo}"
									value="#{aperturaController.intChequeSeleccionado}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel"
										value="#{aperturaController.listaBancoCuentaCheque}" 
										itemValue="#{sel.id.intItembancuencheque}" 
										itemLabel="#{sel.strEtiqueta}"/>
										<a4j:support event="onchange" action="#{aperturaController.mostrarNumeroCheque}" reRender="txtNroChequeTransf"  />					
								</h:selectOneMenu>
			                </rich:column>
						</h:panelGrid>
					
					</h:panelGroup>
					
					
					<h:panelGroup rendered="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)}">
						
						<h:panelGrid columns="4" id="panelFondoFijoRendicion">
							<rich:column width="140">
								<h:outputText value="Fondo Fijo :"/>
							</rich:column>
							<rich:column width="185">
								<h:selectOneMenu 
									style="width: 185px;"
									disabled="#{aperturaController.deshabilitarNuevo}"
									value="#{aperturaController.intTipoFondoFijoRendicion}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel" 
										value="#{aperturaController.listaTipoFondoFijoRendicion}" 
										itemValue="#{sel.intIdDetalle}"
										itemLabel="#{sel.strDescripcion}"/>
									<a4j:support event="onchange" action="#{aperturaController.seleccionarFondoFijoRendicion}" 
										reRender="panelFondoFijoRendicion, panelCuentaAFF,pgTipoDocNroDoc"/>
								</h:selectOneMenu>
			                </rich:column>
			                <rich:column width="400">
			                	<h:selectOneMenu 
			                		style="width: 400px;"
									value="#{aperturaController.intControlSeleccionado}"
									disabled="#{aperturaController.deshabilitarNuevo}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
									<tumih:selectItems var="sel"
										value="#{aperturaController.listaControlFondosFijosRendicion}" 
										itemValue="#{sel.id.intItemFondoFijo}"
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
			              	</rich:column>		              	             	
						</h:panelGrid>
						
					</h:panelGroup>
					
					<h:panelGroup rendered="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)}">
				
						<h:panelGrid columns="4" id="panelCuentaBancariaT">
							<rich:column width="140">
								<h:outputText value="Cuenta Bancaria :"/>
							</rich:column>
							<rich:column width="185">
								<h:inputText value="Hermes" style="width: 178px;background-color: #BFBFBF;" readonly="true"/>
			                </rich:column>
			                <rich:column width="400">
			                	<h:inputText value="#{aperturaController.bancoCuentaHermes.strEtiqueta}"
			                		style="width: 395px;background-color: #BFBFBF;" readonly="true"/>
			              	</rich:column>
						</h:panelGrid>
					
					</h:panelGroup>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="6" id="pgTipoDocNroDoc">
						<rich:column width="140">
							<h:outputText value="Tipo de Documento : "/>
						</rich:column>
						<rich:column width="200">
							<tumih:inputText property="#{aperturaController.intTipoDocumentoValidar}"
								style="width: 178px;background-color: #BFBFBF;" readonly="true"
								itemLabel="strDescripcion" itemValue="intIdDetalle"  
								cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"/>
						</rich:column>
						<rich:column width="120" style="text-align: left">
							<h:outputText value="Número de Cheque : " rendered="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA || aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA || aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA)}"/>
							<h:outputText value="Número de Transf : " rendered="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS) || (aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION && aperturaController.intTipoFondoFijoRendicion == applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)}"/>
						</rich:column>
						<rich:column width="150" rendered="#{aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA || aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUEGERENCIA || aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS || (aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION && aperturaController.intTipoFondoFijoRendicion == applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)}">
							<h:inputText id="txtNroChequeTransf" size="22" value="#{aperturaController.intNumeroCheque}"
								disabled="#{aperturaController.deshabilitarNuevo}" onkeypress="return soloNumerosNaturales(event)" 
								readonly="#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)}" 
								style="if(#{(aperturaController.intTipoDocumentoValidar == applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)}){background-color: #BFBFBF;font-weight:bold;}"/>
						</rich:column>
						<rich:column width="115">
							<h:outputText value="Fecha de Egreso :"/>
						</rich:column>
						<rich:column width="100">
							<h:inputText size="17" value="#{aperturaController.dtFechaActual}" 
								readonly="true" style="background-color: #BFBFBF;font-weight:bold;">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="6">
						<rich:column width="140">
							<h:outputText value="Fondo Fijo : "/>
						</rich:column>
						<rich:column width="200">
							<tumih:inputText property="#{aperturaController.controlFondosFijosNuevo.id.intParaTipoFondoFijo}"
								style="width: 178px;background-color: #BFBFBF;" readonly="true"
								itemLabel="strDescripcion" itemValue="intIdDetalle"  
								cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"/>
						</rich:column>
						<rich:column width="120" style="text-align: left">
							<h:outputText value="Sucursal(Origen) : "/>
						</rich:column>
						<rich:column width="150">
							<h:inputText size="22" value="#{aperturaController.sucursalSedeCentral.juridica.strSiglas}" 
								readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="150">
							<h:inputText size="18" value="#{aperturaController.subsucursalSedeCentral.strDescripcion}" 
								readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
						
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="5">
						<rich:column width="140">
							<h:outputText value="Sub Tipo de Operación : "/>
						</rich:column>
						<rich:column width="200">
							<tumih:inputText property="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION_APERTURA}"
								style="width: 178px;background-color: #BFBFBF;" readonly="true"
								itemLabel="strDescripcion" itemValue="intIdDetalle"  
								cache="#{applicationScope.Constante.PARAM_T_TIPODESUBOPERACION}"/>
						</rich:column>
						<rich:column width="120" style="text-align: left">
							<h:outputText value="Sucursal(Destino) : "/>
						</rich:column>
						<rich:column width="150">
							<h:inputText size="22" value="#{aperturaController.controlFondosFijosNuevo.sucursal.juridica.strSiglas}" 
								readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="150">
							<h:inputText size="18" value="#{aperturaController.controlFondosFijosNuevo.subsucursal.strDescripcion}" 
								readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
						
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid columns="5" id="panelResponsableCG">
						<rich:column width="140">
							<h:outputText value="Responsable : "/>
						</rich:column>
						<rich:column>
							<h:inputText size="112" value="#{aperturaController.controlFondosFijosNuevo.strDescripcionPersona}" 
								readonly="true" style="background-color: #BFBFBF;"/>
						</rich:column>
						<rich:column width="130">
							<a4j:commandButton styleClass="btnEstilos"
		                		value="Agregar"
		                		disabled="#{aperturaController.deshabilitarNuevo}"
		                		reRender="pSeleccionarResponsable"
		                		oncomplete="Richfaces.showModalPanel('pSeleccionarResponsable')"
		                    	action="#{aperturaController.buscar}" style="width:130px"/>
						</rich:column>						
					</h:panelGrid>
					
					<rich:spacer height="5px"/>
					
					<rich:spacer height="5px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="140">
							<h:outputText value="Fondo Anterior : "/>
						</rich:column>
						<rich:column>
							<h:inputText size="112" readonly="true" style="background-color: #BFBFBF;"
								value="#{aperturaController.controlFondosFijosNuevo.anterior.strDescripcion}"/>
						</rich:column>						
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="140">
							<h:outputText value="Monto a Girar : "/>
						</rich:column>
						<rich:column width="180">
							<h:inputText size="26" readonly="true" style="background-color: #BFBFBF;font-weight:bold;"
								value="#{aperturaController.controlFondosFijosNuevo.bdMontoGirado}">							
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="4" rendered="#{aperturaController.deshabilitarNuevo}">
						<rich:column width="140">							
						</rich:column>
						<rich:column width="180">
							<h:inputText size="85" readonly="true" style="background-color: #BFBFBF;font-weight:bold;"
								value="#{aperturaController.controlFondosFijosNuevo.strMontoLetras}">								
							</h:inputText>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="10px"/>
					
					<h:panelGrid columns="4">
						<rich:column width="140" style="vertical-align: top">
							<h:outputText value="Observación : "/>
						</rich:column>
						<rich:column>
							<h:inputTextarea cols="112" rows="3" value="#{aperturaController.strObservacion}" 
								disabled="#{aperturaController.deshabilitarNuevo}"/>
						</rich:column>						
					</h:panelGrid>
				</h:panelGroup>
			</a4j:outputPanel>
		</rich:panel>
		
	</h:panelGroup>	

</h:panelGroup>
</h:form>