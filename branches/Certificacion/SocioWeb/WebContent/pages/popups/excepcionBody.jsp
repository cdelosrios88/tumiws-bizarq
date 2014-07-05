<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Créditos - Excepciones	 				-->
	<!-- Prototipo : PROTOTIPO EXCEPCIONES DE CRÉDITO		-->
	<!-- Fecha     : 11/04/2012               				-->

	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormExcepcionCredito">
	<h:form id="frmExcepcion">
    	<rich:panel  style="border: 0px solid #17356f;width:800px;background-color:#DEEBF5;">
            <h:panelGrid columns="4">
            	<rich:column><h:outputText value="Nombre de Crédito :"/></rich:column>
            	<rich:column><h:inputText value="#{creditoController.beanCredito.strDescripcion}" size="50" disabled="true"/></rich:column>
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
	            	<h:panelGroup rendered="#{creditoExcepcionController.strCreditoExcepcion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoExcepcionController.grabarExcepcion}" 
			            		oncomplete="Richfaces.hideModalPanel('pAgregarExcepciones')"
			            		styleClass="btnEstilos" reRender="pgListExcepcion"/>
		           	</h:panelGroup>
		           	<h:panelGroup rendered="#{creditoExcepcionController.strCreditoExcepcion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoExcepcionController.modificarExcepcion}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarExcepciones')"
		            		styleClass="btnEstilos" reRender="pgListExcepcion"/>
	            	</h:panelGroup>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Cancelar" styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pAgregarExcepciones')"/>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgFormExcepcion">
	            <rich:panel style="border:1px; solid #17356f;background-color:#DEEBF5">
		        	<h:panelGrid columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Tasa de sobregiro"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:inputText value="#{creditoExcepcionController.beanExcepcion.bdTasaSobreGiro}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="20"/>%
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Meses de Gracia"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:inputText value="#{creditoExcepcionController.beanExcepcion.intPeriodoGracia}" onkeydown="return validarEnteros(event);" size="20"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Doble cuota"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column width="120px">
					  		<h:selectManyCheckbox value="#{creditoExcepcionController.lstDobleCuota}" layout="pageDirection">
					  			<f:selectItem itemLabel="Julio" itemValue="1"/>
					  			<f:selectItem itemLabel="Diciembre" itemValue="2"/>
					  		</h:selectManyCheckbox>
					  	</rich:column>
					  	<rich:column>
					  		<h:selectOneMenu value="#{creditoExcepcionController.beanExcepcion.intIndicadorDobleCuota}">
					  			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_INDICADOR_DOBLE_CUOTA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					  		</h:selectOneMenu>
					  	</rich:column>
					</h:panelGrid>
			
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Primer Crédito"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column width="120px">
						  	<h:inputText value="#{creditoExcepcionController.beanExcepcion.intPrimerCredito}" onkeydown="return validarEnteros(event);" size="10"/>días
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoExcepcionController.beanExcepcion.intPrimerCreditoEnvioPlla}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ORDEN_ENVIO_PLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Límite de Cuota Fija"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column width="120px">
						  	<h:inputText value="#{creditoExcepcionController.beanExcepcion.intPorcentajeLimiteCuota}" onkeydown="return validarEnteros(event);" size="10"/>%
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoExcepcionController.beanExcepcion.intCampoLimiteCuota}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_LIMITE_CUOTA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="5">
						<rich:column width="130px">
							<h:outputText value="Aporte no Transferible"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column style="border:1px solid #17356f;background-color:#DEEBF5;">
						  	<h:selectManyCheckbox value="#{creditoExcepcionController.lstExcepcionAporteNoTrans}">
						  		<tumih:selectItems var="sel" value="#{creditoExcepcionController.listaTipoCaptacion}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						  	</h:selectManyCheckbox>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoExcepcionController.beanExcepcion.bdPorcentajeNoTrans}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>%
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoExcepcionController.beanExcepcion.intParaConceptoNoTrans}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONCEPTO_NO_TRANSFERIBLE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Limite de Edad"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:inputText value="#{creditoExcepcionController.beanExcepcion.intEdadLimite}" onkeydown="return validarEnteros(event);"/>años
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoExcepcionController.beanExcepcion.intParaTipoMaxMin}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESCALATIEMPO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Tolerancia de Cartera"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column width="150px">
						  	<h:outputText value="Importe para evaluación"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoExcepcionController.beanExcepcion.bdImporteEvaluacion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 142px;"></rich:column>
					  	<rich:column width="150px">
						  	<h:outputText value="Días de Tolerancia"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoExcepcionController.beanExcepcion.intDiasTolerancia}" onkeydown="return validarEnteros(event);" size="10"/>días
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Cobranza Judicial"/>
						</rich:column>
					  	<rich:column>
						  	<h:outputText value=":"/>
						</rich:column>
						<rich:column>
							<h:selectManyCheckbox value="#{creditoExcepcionController.lstDiasCobranza}" layout="pageDirection">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_DIAS_COBRANZA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectManyCheckbox>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="1">
						<rich:column>
							<h:selectBooleanCheckbox value="#{creditoExcepcionController.beanExcepcion.boAplicaCastigadas}"/>Aplica solo a cuentas castigadas
						</rich:column>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>