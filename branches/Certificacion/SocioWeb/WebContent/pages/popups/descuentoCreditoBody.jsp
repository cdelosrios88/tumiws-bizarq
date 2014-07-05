<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Créditos - Descuentos	 				-->
	<!-- Prototipo : PROTOTIPO DESCUENTOS DE CRÉDITO		-->
	<!-- Fecha     : 11/04/2012               				-->

	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormDsctoCredito">
	<h:form id="frmDescuento">
    	<rich:panel  style="border: 0px solid #17356f;width:700px;background-color:#DEEBF5;">
            <h:panelGrid columns="4">
            	<rich:column><h:outputText value="Nombre de Crédito :"/></rich:column>
            	<rich:column><h:inputText value="#{creditoController.beanCredito.strDescripcion}" disabled="true" size="50"/></rich:column>
            	<rich:column><h:outputText value="Estado :"/></rich:column>
            	<rich:column>
	            	<h:selectOneMenu value="#{creditoController.beanCredito.intParaEstadoCod}" disabled="true">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgBtnDscto" border="0" columns="2">
	            <rich:column>
	            	<h:panelGroup rendered="#{creditoDescuentoController.strCreditoDescuento == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoDescuentoController.grabarDescuento}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarDescuentos')"
		            		styleClass="btnEstilos" reRender="pgListDscto"/>
	            	</h:panelGroup>
	            	<h:panelGroup rendered="#{creditoDescuentoController.strCreditoDescuento == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoDescuentoController.modificarDescuento}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarDescuentos')"
		            		styleClass="btnEstilos" reRender="pgListDscto"/>
	            	</h:panelGroup>
	            </rich:column>
	            <rich:column>
	            	<h:commandButton value="Cancelar" styleClass="btnEstilos"/>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgFormCreditoGarantia">
	            <rich:panel style="border:1px; solid #17356f;background-color:#DEEBF5">
		        	<h:panelGrid columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Concepto"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:inputText value="#{creditoDescuentoController.beanDescuento.strConcepto}" maxlength="50" size="50"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="2">
						<rich:column style="width: 130px;"/>
					  	<rich:column>
					  		<h:selectManyCheckbox value="#{creditoDescuentoController.lstDsctoCaptacion}" layout="pageDirection">
						  		<tumih:selectItems var="sel" value="#{creditoDescuentoController.listaTipoCaptacion}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						  		<%--<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_APLICACION_CAPTACION_CREDITOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>--%>
						  	</h:selectManyCheckbox>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Cantidad de descuento"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column width="90px">
					  		<h:outputText value="Monto"/> 
					  	</rich:column>
					  	<rich:column>
					  		<h:inputText value="#{creditoDescuentoController.beanDescuento.bdMonto}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>
					  	</rich:column>
					</h:panelGrid>
			
					<h:panelGrid columns="3">
						<rich:column style="width: 142px;"></rich:column>
					  	<rich:column width="90px">
					  		<h:outputText value="Porcentaje"/>
					  	</rich:column>
					  	<rich:column>
					  		<h:inputText value="#{creditoDescuentoController.beanDescuento.bdPorcentaje}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>%
					  	</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgFecFinRender" columns="6">
						<rich:column style="width: 130px;">
							<h:outputText value="Plazo de Descuento"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value="Fecha de Inicio :"/>
					  	</rich:column>
					  	<rich:column>
					  		<rich:calendar popup="true" enableManualInput="true"
					  		value="#{creditoDescuentoController.beanDescuento.dtFechaIni}"
					  		datePattern="dd/MM/yyyy" inputStyle="width:70px;"/>
					  	</rich:column>
					  	<rich:column>
					  		<h:selectOneRadio value="#{creditoDescuentoController.rbFecFinIndeterm}">
					  			<f:selectItem itemLabel="Fecha de Fin" 	itemValue="1"/>
					  			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
					  			<a4j:support event="onclick" actionListener="#{creditoDescuentoController.enableDisableControls}" reRender="pgFecFinRender"/>
					  		</h:selectOneRadio>
					  	</rich:column>
					  	<rich:column>
					  		<rich:calendar popup="true" enableManualInput="true"
					  		value="#{creditoDescuentoController.beanDescuento.dtFechaFin}"
					  		rendered="#{creditoDescuentoController.fecFinCreditoRendered}"
					  		datePattern="dd/MM/yyyy" inputStyle="width:70px;"/>
					  	</rich:column>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>