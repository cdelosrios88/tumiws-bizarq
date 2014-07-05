<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO              -->
<!-- PROTOTIPO : PRESUPUESTO - POPUP ADD CUENTA CONTABLE			  -->			
<!-- FECHA     : 22.10.2013               -->

<rich:modalPanel id="mpAddCtaContable" width="655" height="270"	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Seleccionar Cuenta..."></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideAddCtaContable" />
			<rich:componentControl for="mpAddCtaContable" attachTo="hideAddCtaContable"
				operation="hide" event="onclick"/>
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmAddCtaContable">
	   	<h:panelGroup id="divAddCtaContable" layout="block" style="padding-left:5px; padding-right:5px">
	    	
	    	<h:panelGrid styleClass="tableCellBorder4">
	    		<rich:columnGroup>
	    			<rich:column style="padding-left:-5px">
	    				<h:selectOneMenu id="cboTipoCuentaBusq" value="#{presupuestoController.mpIntTipoCuentaBusq}" onchange="selecTipoCuenta(#{applicationScope.Constante.ONCHANGE_VALUE})">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Período"></h:outputText>
           			</rich:column>
           			<rich:column>
           				<h:inputText id="txtPeriodoProyectado" value="#{presupuestoController.intAnioGrabacion}" disabled="TRUE" size="6"/>
           			</rich:column>
	    			<rich:column>
	    				<h:inputText id="txtCuentaBusq" value="#{presupuestoController.mpStrCuentaBusq}" 
	    							disabled="#{presupuestoController.blnDisabledTxtCuentaBusq}" size="35"></h:inputText>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{presupuestoController.buscarPlanCuenta}" value="Buscar" 
	    								reRender="divTblCuentas" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblCuentas" layout="block" style="padding:13px">
	    		<rich:extendedDataTable id="tblCuentas" enableContextMenu="false" style="margin:0 auto"
	    					rendered="#{not empty presupuestoController.listaPlanCuenta}"
	                        var="item" value="#{presupuestoController.listaPlanCuenta}"  
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="560px" height="165px">
			                           
					 	<rich:column width="41">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Periodo"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.intPeriodoCuenta}"></h:outputText>
			            </rich:column> 
			            <rich:column width="120">
	                        <f:facet name="header">
	                            <h:outputText value="Cuenta"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.strNumeroCuenta}"></h:outputText>
			            </rich:column>
			            <rich:column width="200" id="colDescripcionCuenta">
	                        <f:facet name="header">
	                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strDescripcion}"></h:outputText>
							<rich:toolTip value="#{item.strDescripcion}" for="colDescripcionCuenta"></rich:toolTip>
			            </rich:column>
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Seleccionar"></h:outputText>
	                        </f:facet>
	                        <h:selectOneRadio valueChangeListener="#{presupuestoController.seleccionarCuenta}" onclick="selectRbRowCtaContable('#{rowKey}')">
								<f:selectItem itemValue="#{rowKey}"/>
							</h:selectOneRadio>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblCuentas" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>