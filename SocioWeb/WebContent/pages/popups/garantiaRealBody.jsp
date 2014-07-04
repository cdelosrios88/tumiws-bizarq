<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Créditos - Garantías     				-->
	<!-- Prototipo : PROTOTIPO GARANTÍA PREFERIDA REAL		-->
	<!-- Fecha     : 11/04/2012               				-->

	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormGarantiaReal">
	<h:form id="frmGarantia">
    	<rich:panel  style="border: 0px solid #17356f;background-color:#DEEBF5;">
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
            
            <h:panelGrid id="pgBtnGarantia" border="0" columns="2">
	            <rich:column>
	            	<h:panelGroup rendered="#{creditoGarantiaRealController.strCreditoGarantiaReal == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaRealController.grabarGarantiaReal}" 
			            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaReal')"
			            		styleClass="btnEstilos" reRender="pgListGarantiaReal"/>
		           	</h:panelGroup>
		           	<h:panelGroup rendered="#{creditoGarantiaRealController.strCreditoGarantiaReal == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaRealController.modificarGarantiaReal}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaReal')"
		            		styleClass="btnEstilos" reRender="pgListGarantiaReal"/>
	            	</h:panelGroup>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Cancelar" styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaReal')"/>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgFormGarantiaReal">
	            <rich:panel style="border:1px; solid #17356f;background-color:#DEEBF5">
		        	<h:panelGrid id="pgClaseGarantia" columns="6">
						<rich:column style="width:130px;">
							<h:outputText value="Clase de Garantía"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu value="#{creditoGarantiaRealController.beanGarantiaReal.intParaClaseCod}" style="width:280px;"
						  		valueChangeListener="#{creditoGarantiaRealController.reloadCboSubClase}">
	          	 				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{creditoGarantiaRealController.listaClaseGarantia}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="pgClaseGarantia"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
						  	<h:selectOneMenu value="#{creditoGarantiaRealController.beanGarantiaReal.intParaSubClaseCod}" style="width:300px;">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{creditoGarantiaRealController.listaSubClaseGarantia}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
							<h:selectBooleanCheckbox value="#{creditoGarantiaRealController.beanGarantiaReal.boDocAdjunto}"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Adjuntar Documento"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Naturaleza de Garantía"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod != applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
					  			<h:selectOneMenu value="#{creditoGarantiaRealController.beanGarantiaReal.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" value="#{creditoGarantiaRealController.listaNaturalezaGarantia}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
					  		<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
						  		<h:selectOneMenu value="#{creditoGarantiaRealController.beanGarantiaReal.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Valor de Realización"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectBooleanCheckbox value="#{creditoGarantiaRealController.beanGarantiaReal.boOpcionValorVenta}"/>Valor de Venta del Mercado
						</rich:column>
						<rich:column>
						  	<h:selectManyCheckbox value="#{creditoGarantiaRealController.lstValorRealizacion}" layout="pageDirection">
						  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_VALOR_VENTA_MERCADO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						  	</h:selectManyCheckbox>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column>
							<h:selectBooleanCheckbox value="#{creditoGarantiaRealController.beanGarantiaReal.boValuadorInscrito}"/>Valor inscrito
					  	</rich:column>
					  	<rich:column>
							<h:selectBooleanCheckbox value="#{creditoGarantiaRealController.beanGarantiaReal.boTasacionComercial}"/>Tasación Comercial
					  	</rich:column>
					  	<rich:column>
						  	<h:selectBooleanCheckbox value="#{creditoGarantiaRealController.beanGarantiaReal.boNegociacionEndoso}"/>Negociación por endoso
						</rich:column>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>