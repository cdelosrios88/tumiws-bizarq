<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SELECCIONAR CUENTA MODELO     -->
	<!-- Fecha     : 17/05/2012               		-->

<rich:modalPanel id="mpHojaManualDetalle" width="780" height="350"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Nota Contable Detalle"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideHojaManualDetalle" />
			<rich:componentControl for="mpHojaManualDetalle" attachTo="hideHojaManualDetalle"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmHojaManualDetalle">
	   	<h:panelGroup id="divHojaManualDetalle" layout="block" style="padding-left:5px; padding-right:5px">
	   		<h:panelGrid styleClass="tableCellBorder4">
	   			<rich:columnGroup>
	   				<rich:column style="padding-left:-5px">
	   					<a4j:commandButton value="Grabar" actionListener="#{hojaManualController.grabarManualDetalle}" 
	   									reRender="divTblHojaManualDetalle,msgErrorTipoDocumento,msgErrorTipoNumero,msgErrorTipoNombre,msgErrorTipoSucursal,msgErrorTipoSubSucursal,msgErrorTipoSerieDocumento,msgErrorTipoNumeroDocumento,msgErrorTipoMonedaDocumento,msgErrorTipoOpcionDebeHaber,msgErrorTipoMontoSoles" styleClass="btnEstilos1"
	   									oncomplete="if(#{hojaManualController.strPlop == '' && hojaManualController.strNumero=='' && hojaManualController.strNombre=='' && hojaManualController.strSucursal=='' && hojaManualController.strSubSucursal=='' && hojaManualController.strSerieDocumento=='' && hojaManualController.strNumeroDocumento=='' && hojaManualController.strMonedaDocumento=='' && hojaManualController.strOpcionDebeHaber=='' && hojaManualController.strMontoSoles==''}){Richfaces.hideModalPanel('mpHojaManualDetalle')}"
	   									></a4j:commandButton>
	   					<a4j:commandButton value="Cancelar" styleClass="btnEstilos1"
	   									oncomplete="Richfaces.hideModalPanel('mpHojaManualDetalle')"></a4j:commandButton>
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoDocumento" value="#{hojaManualController.strPlop}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strPlop}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoNumero" value="#{hojaManualController.strNumero}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strNumero}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoNombre" value="#{hojaManualController.strNombre}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strNombre}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoSucursal" value="#{hojaManualController.strSucursal}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strSucursal}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoSubSucursal" value="#{hojaManualController.strSubSucursal}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strSubSucursal}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoSerieDocumento" value="#{hojaManualController.strSerieDocumento}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strSerieDocumento}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoNumeroDocumento" value="#{hojaManualController.strNumeroDocumento}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strNumeroDocumento}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoMonedaDocumento" value="#{hojaManualController.strMonedaDocumento}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strMonedaDocumento}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoOpcionDebeHaber" value="#{hojaManualController.strOpcionDebeHaber}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strOpcionDebeHaber}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   			<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgErrorTipoMontoSoles" value="#{hojaManualController.strMontoSoles}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!hojaManualController.strMontoSoles}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
	   		</h:panelGrid>
	    	
	    	<rich:panel style="border:1px solid #17356f">
                
                <h:panelGrid columns="6" styleClass="tableCellBorder4">
                	<rich:columnGroup>
                		<rich:column width="110">
                			<h:outputText value="Cuenta Contable"></h:outputText>
                		</rich:column>
                		<rich:column colspan="4">
                			<h:inputText id="txtNumeroCuenta" value="#{hojaManualController.hojaManualDetalle.planCuenta.id.strNumeroCuenta}" disabled="true" size="90"></h:inputText>
                		</rich:column>
                		<rich:column>
                			<a4j:commandButton 	value="Agregar Cuenta"
                								action="#{hojaManualController.limpiarCuenta}" 
                								styleClass="btnEstilos1" 
                								reRender="mpSeleccionCuenta"
                								onclick="searchCuentaContable()" 
                								oncomplete="Richfaces.showModalPanel('mpSeleccionCuenta')"></a4j:commandButton>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Persona"></h:outputText>
                		</rich:column>
                		<rich:column colspan="4">
                			<h:inputText id="txtFullNamePersona" value="#{hojaManualController.hojaManualDetalle.persona.strEtiqueta}" disabled="true" size="90"></h:inputText>
                		</rich:column>
                		<rich:column>
                			<a4j:commandButton value="Agregar Persona"
                							   action="#{hojaManualController.limpiarPersona}" 
                							   styleClass="btnEstilos1" 
                							   reRender="mpSeleccionPersona"
                							   onclick="searchPersona()" 
                							   oncomplete="Richfaces.showModalPanel('mpSeleccionPersona')"></a4j:commandButton>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Tipo Documento"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:selectOneMenu value="#{hojaManualController.hojaManualDetalle.intParaDocumentoGeneralCod}">
		    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
									  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		    				</h:selectOneMenu>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Sucursal"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:selectOneMenu value="#{hojaManualController.hojaManualDetalle.intSucuIdSucursalPk}"
                							onchange="changeSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})">
                				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
                				<tumih:selectItems var="sel" value="#{hojaManualController.listSucursal}" 
							  					itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
                			</h:selectOneMenu>
                		</rich:column>
                		<rich:column>
                			<h:outputText value="Subsucursal"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:selectOneMenu id="cboSubsucursalHMDe" value="#{hojaManualController.hojaManualDetalle.intSudeIdSubsucursalPk}">
                				<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							  	<tumih:selectItems var="sel" value="#{hojaManualController.listSubsucursal}"
												itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
                			</h:selectOneMenu>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Serie"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:inputText value="#{hojaManualController.hojaManualDetalle.strHmdeSerieDocumento}"></h:inputText>
                		</rich:column>
                		<rich:column>
                			<h:outputText value="Número"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:inputText value="#{hojaManualController.hojaManualDetalle.strHmdeNumeroDocumento}"></h:inputText>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Tipo Moneda"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:selectOneMenu value="#{hojaManualController.hojaManualDetalle.intParaMonedaDocumento}"
                							onchange="changeTipoMoneda(#{applicationScope.Constante.ONCHANGE_VALUE})">
		    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
									  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		    				</h:selectOneMenu>
                		</rich:column>
                		<rich:column>
                			<h:outputText value="Tipo de Cambio"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:inputText id="txtTipoCambio" value="#{hojaManualController.hojaManualDetalle.intHmdeTipoCambio}"
                						disabled="true"></h:inputText>
                		</rich:column>
                		<rich:column>
                			<h:outputText value="Tipo"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:selectOneMenu value="#{hojaManualController.hojaManualDetalle.intOpcionDebeHaber}">
		    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_OPCIONDEBEHABER}" 
									  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		    				</h:selectOneMenu>
                		</rich:column>
                	</rich:columnGroup>
                	<rich:columnGroup>
                		<rich:column>
                			<h:outputText value="Monto Soles"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:inputText id="txtMontoSoles" value="#{hojaManualController.hojaManualDetalle.bdMontoSoles}" 
                						disabled="#{hojaManualController.hojaManualDetalle.intParaMonedaDocumento==null || hojaManualController.hojaManualDetalle.intParaMonedaDocumento==0}"></h:inputText>
                		</rich:column>
                		<rich:column width="110">
                			<h:outputText value="Moneda Extranjera"></h:outputText>
                		</rich:column>
                		<rich:column>
                			<h:inputText id="txtMonedaExtranjera" value="#{hojaManualController.hojaManualDetalle.bdMonedaExtranjero}"
                						disabled="#{!hojaManualController.isMonedaExtranjera}"></h:inputText>
                		</rich:column>
                	</rich:columnGroup>
	    		</h:panelGrid>
	    	</rich:panel>
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>