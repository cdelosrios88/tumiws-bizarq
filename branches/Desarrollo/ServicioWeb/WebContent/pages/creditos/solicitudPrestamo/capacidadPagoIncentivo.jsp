<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi         				-->
	<!-- Autor     : Christian De los R�os    					-->
	<!-- Modulo    : Cr�ditos - Garant�as     					-->
	<!-- Prototipo : PROTOTIPO POPUP CAPACIDAD DE PAGOS - CAS	-->
	<!-- Fecha     : 16/07/2012               					-->
	
	<script type="text/javascript">
		
	</script>
	
	<a4j:region>
		<a4j:jsFunction name="addTotalIngreso" reRender="idBaseCalculo"
			actionListener="#{capacidadPagoController.getBaseCalculo}">
			<f:param name="bdTotalIngreso"/>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="addMontoTumi" reRender="idBaseCalculo"
			actionListener="#{capacidadPagoController.getBaseCalculo}">
			<f:param name="bdMontoTumi"/>
		</a4j:jsFunction>
	</a4j:region>
	
    	<rich:panel  style="border: 0px solid #17356f;background-color:#DEEBF5;width:920px;">
    		<h:panelGrid columns="6">
    			<rich:column>
    				<h:outputText value="Socio :"/>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.persona.intIdPersona} - #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudPrestamoController.beanSocioComp.persona.natural.strNombres} - DNI : #{solicitudPrestamoController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="70" disabled="true"/>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Nro. Cuenta :"/>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.cuenta.strNumeroCuenta}" size="30" disabled="true"/>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Condici�n :"/>
    			</rich:column>
    			<rich:column>
    				<rich:column>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
			                              itemValue="intIdDetalle" itemLabel="strDescripcion"
			                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaCondicionCuentaCod}"/> -
			            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
			                              itemValue="intIdDetalle" itemLabel="strDescripcion"
			                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}"/>
					</rich:column>
    			</rich:column>
    		</h:panelGrid>
    		
    		<h:panelGrid columns="2">
    			<rich:column>
    				<h:outputText value="Tipo de Incentivo :"/>
    			</rich:column>
    			
    			<h:selectOneMenu value="#{capacidadPagoController.beanCapacidadCredito.intParaTipoCapacidadCod}">
    				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
    				<f:selectItem itemLabel="Incentivo General" itemValue="#{applicationScope.Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_GENERAL}"/>
    				<f:selectItem itemLabel="Incentivo Prorrateo" itemValue="#{applicationScope.Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_PRORRATEO}"/>
                    <a4j:support actionListener="#{capacidadPagoController.showTipoIncentivo}" event="onchange" reRender="pgIncentivoGral,pgIncentivoProrrateo"/>
    			</h:selectOneMenu>
    		</h:panelGrid>
    		
    		<h:panelGrid columns="3">
    			<rich:column>
    				<a4j:commandButton value="Grabar" actionListener="#{capacidadPagoController.grabarCapacidadCredito}" 
    					oncomplete="if(#{capacidadPagoController.validCapacidadCredito}){Richfaces.hideModalPanel('mpCapacidadCredito')}"
    					styleClass="btnEstilos" reRender="frmCapacidadPago,pgListCapacidadCredito,pgListCapacidadCredito,pgDatosSocio,pgSolicCredito,pgRequisitos,pgEvaluacion,pgPrestamo1,pgPrestamo2,pgCondiciones,pgMsgErrorEvaluacion,pgListDocAdjuntos"/>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton value="Cancelar" styleClass="btnEstilos"/>
    			</rich:column>
    		</h:panelGrid>
    		
    		<h:panelGroup id="pgIncentivoGral" layout="block">
	    		<a4j:include viewId="/pages/creditos/solicitudPrestamo/capacidadPagoIncentivo/capacidadPagoIncentivoGral.jsp"/>
    		</h:panelGroup>
    		
    		<h:panelGroup id="pgIncentivoProrrateo" layout="block">
    			<a4j:include viewId="/pages/creditos/solicitudPrestamo/capacidadPagoIncentivo/capacidadPagoIncentivoProrrateo.jsp"/>
    		</h:panelGroup>
    	</rich:panel>