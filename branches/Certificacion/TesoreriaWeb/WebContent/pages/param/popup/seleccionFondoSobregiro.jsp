<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmSeleccionFondoSobregiro">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 100px">
         			<h:outputText value="Sucursal : "/>
         		</rich:column>
         		<rich:column>
					<h:selectOneMenu id="SucursalCuenta" 
						style="width: 250px;"
						value="#{bancoFondoController.fondoDetalleAgregar.intIdsucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{bancoFondoController.listaSucursalFondoFijo}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" action="#{bancoFondoController.seleccionarSucursal}" reRender="frmSeleccionFondoSobregiro"  />
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>
		
	    	<h:panelGrid columns="5">
         		<rich:column  style="width: 100px">
         			<h:outputText value="SubSucursal : "/>
         		</rich:column>
                <rich:column>
					<h:selectOneMenu id="SUBSucursalCuenta" 
						style="width: 250px;"
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
	    		<rich:column width="100">
           			<h:outputText value="Cuenta Contable: "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.fondoDetalleAgregar.strNumerocuenta}" size="45" readonly="true"/>
           		</rich:column>
           		<rich:column width="70">
                    <a4j:commandButton styleClass="btnEstilos"
						value="Agregar"
				        actionListener="#{bancoFondoController.abrirPopUpPlanCuenta}"
						reRender="pSeleccionPlanCuenta"
						oncomplete="Richfaces.showModalPanel('pSeleccionPlanCuenta')"
						disabled="#{bancoFondoController.deshabilitarNuevo}"
						style="width:70px">
						<f:attribute name="tipo" value="#{applicationScope.Constante.FONDOFIJOSOBREGIRO}"/>
					</a4j:commandButton>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="100">
           			<h:outputText value="Monto Sobregiro: "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.fondoDetalleAgregar.bdMontosobregiro}" size="45"
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
			    		action="#{bancoFondoController.aceptarSeleccionFondoSobregiro}"
		        		oncomplete="if(#{bancoFondoController.mostrarMensajePopUp}){rendPopUp5();}else{Richfaces.hideModalPanel('pSeleccionSobregiro');rendTabla5();}"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pSeleccionSobregiro')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla5" reRender="panelListaSobregiro" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp5" reRender="frmSeleccionFondoSobregiro" ajaxSingle="true"/>
	   	</h:panelGroup>
	</h:form>