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

<h:panelGrid id="pgFormGarantiaAutoliquidable">
	<h:form id="frmGarantia">
    	<rich:panel  style="border: 0px solid #17356f;background-color:#DEEBF5;width:670px;">
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
	            	<h:panelGroup rendered="#{creditoGarantiaAutoliquidableController.strCreditoGarantiaAutoliquidable == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaAutoliquidableController.grabarGarantiaAutoliquidable}" 
			            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaAutoliquidable')"
			            		styleClass="btnEstilos" reRender="pgListGarantiaAutoliquidable"/>
		           	</h:panelGroup>
		           	<h:panelGroup rendered="#{creditoGarantiaAutoliquidableController.strCreditoGarantiaAutoliquidable == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaAutoliquidableController.modificarGarantiaAutoliquidable}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaAutoliquidable')"
		            		styleClass="btnEstilos" reRender="pgListGarantiaAutoliquidable"/>
	            	</h:panelGroup>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Cancelar" styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaAutoliquidable')"/>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgFormGarantiaAutoliquidable">
	            <rich:panel style="border:1px; solid #17356f;width:670px;background-color:#DEEBF5">
		        	<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Naturaleza de Garantía"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod != applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
					  			<h:selectOneMenu value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" value="#{creditoGarantiaAutoliquidableController.listaNaturalezaGarantia}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
					  		<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
						  		<h:selectOneMenu value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
						</rich:column>
						<rich:column>
							<h:selectBooleanCheckbox value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.boDocAdjunto}"/>Documento Adjunto
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgCuentaDeposito" columns="2">
						<rich:column width="140px;">
							<h:selectOneRadio value="#{creditoGarantiaAutoliquidableController.rbTipoCreditoAutoliquidable}" layout="pageDirection">
								<f:selectItem itemLabel="Cuenta Depósitos" 	itemValue="1"/>
								<f:selectItem itemLabel="Derechos de Carta" itemValue="2"/>
								<a4j:support event="onclick" actionListener="#{creditoGarantiaAutoliquidableController.enableDisableGarantiaAutoliq}" reRender="pgCuentaDeposito"/>
							</h:selectOneRadio>
						</rich:column>
						<rich:column>
							<h:panelGrid columns="5">
								<rich:column>
									<h:selectBooleanCheckbox value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.boOpcionMinimoCta}" rendered="#{creditoGarantiaAutoliquidableController.boCtaDepositoRendered}"/>
							  	</rich:column>
							  	<rich:column>
							  		<h:outputText value="Monto mínimo de la Cuenta" rendered="#{creditoGarantiaAutoliquidableController.boCtaDepositoRendered}"/>
							  	</rich:column>
							  	<rich:column>
									<h:inputText value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.bdPorcCuenta}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" rendered="#{creditoGarantiaAutoliquidableController.boCtaDepositoRendered}" size="5"/>
							  	</rich:column>
							  	<rich:column>
							  		<h:outputText value="%" rendered="#{creditoGarantiaAutoliquidableController.boCtaDepositoRendered}"/>
							  	</rich:column>
							  	<rich:column>
							  		<h:selectOneMenu rendered="#{creditoGarantiaAutoliquidableController.boCtaDepositoRendered}">
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_COBERTURA_CUENTA_DEPOSITO}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							  		</h:selectOneMenu>
					  			</rich:column>
							</h:panelGrid>
							<h:panelGrid columns="1">
								<rich:column>
									<h:selectOneMenu value="#{creditoGarantiaAutoliquidableController.beanGarantiaAutoliquidable.intParaDerechoCarta}" rendered="#{creditoGarantiaAutoliquidableController.boDerechoCartaRendered}">
							  			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_GARANTIA_DERECHOS_CARTA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							  		</h:selectOneMenu>
								</rich:column>
							</h:panelGrid>
						</rich:column>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>