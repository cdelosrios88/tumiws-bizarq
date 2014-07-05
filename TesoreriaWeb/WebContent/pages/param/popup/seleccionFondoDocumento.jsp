<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmSeleccionFondoDocumento">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 130px">
         			<h:outputText value="Sucursal : "/>
         		</rich:column>
         		<rich:column>
					<h:selectOneMenu id="SucursalCuenta" 
						style="width: 300px;"
						value="#{bancoFondoController.fondoDetalleAgregar.intIdsucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{bancoFondoController.listaSucursalFondoFijo}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" action="#{bancoFondoController.seleccionarSucursal}" reRender="frmSeleccionFondoDocumento"  />
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>
		
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 130px">
         			<h:outputText value="SubSucursal : "/>
         		</rich:column>
                <rich:column>
					<h:selectOneMenu id="SUBSucursalCuenta" 
						style="width: 300px;"
						value="#{bancoFondoController.fondoDetalleAgregar.intIdsubsucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>	
						<tumih:selectItems var="sel"
							value="#{bancoFondoController.listaSubSucursal}" 
							itemValue="#{sel.id.intIdSubSucursal}" 
							itemLabel="#{sel.strDescripcion}"/>					
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>	    	
    	
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 130px">
         			<h:outputText value="Documento General : "/>
         		</rich:column>
                <rich:column>
					<h:selectOneMenu
						style="width: 300px;"
						value="#{bancoFondoController.fondoDetalleAgregar.intDocumentogeneralCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>						
					<a4j:support event="onchange" reRender="panelTipoComprobante"/>
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>	    	

	    	<h:panelGrid columns="5" id="panelTipoComprobante">
         		<rich:column  style="width: 130px">
         			<h:outputText value="Tipo de Comprobante : "/>
         		</rich:column>
                <rich:column>
					<h:selectOneMenu
						style="width: 300px;"
						disabled="#{!(bancoFondoController.fondoDetalleAgregar.intDocumentogeneralCod==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)}"
						value="#{bancoFondoController.fondoDetalleAgregar.intTipocomprobanteCod}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 130px">
         			<h:outputText value="Monto Máximo : "/>
         		</rich:column>
               <rich:column width="300">
           			<h:inputText value="#{bancoFondoController.fondoDetalleAgregar.bdMontomaxdocumento}" size="54"
           				onkeypress="return soloNumerosDecimales(this)"/>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup rendered="#{bancoFondoController.mostrarMensajePopUp}">
	    		<h:outputText value="#{bancoFondoController.mensajePopUp}" 
					styleClass="msgError"
					style="font-weight:bold"/>
	    	</h:panelGroup>
	    	<h:panelGroup rendered="#{!bancoFondoController.mostrarMensajePopUp}">
	    		<rich:spacer height="20px"/>
	    	</h:panelGroup>
			
			<h:panelGrid columns="2">
				<rich:column width="150">
			    </rich:column>
			    <rich:column style="border:none" id="colBtnModificar">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{bancoFondoController.aceptarSeleccionFondoDocumento}"
		        		oncomplete="if(#{bancoFondoController.mostrarMensajePopUp}){rendPopUp4();}else{Richfaces.hideModalPanel('pSeleccionDocumentoCancelar');rendTabla4();}"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pSeleccionDocumentoCancelar')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla4" reRender="panelListaDocumentoCancelar" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp4" reRender="frmSeleccionFondoDocumento" ajaxSingle="true"/>
	   	</h:panelGroup>
	</h:form>