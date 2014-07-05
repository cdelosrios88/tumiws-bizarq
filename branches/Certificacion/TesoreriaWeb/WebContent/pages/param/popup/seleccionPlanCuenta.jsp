<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmSeleccionPlanCuenta">
	   	<h:panelGroup id="divSeleccionCuenta" layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="5">
	    			<rich:column width="120">
	    				<h:selectOneMenu style="width: 120px;" 
	    					value="#{bancoFondoController.intTipoBusquedaPlanCuenta}">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
	    					<tumih:selectItems var="sel" 
	    						cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								action="#{bancoFondoController.seleccionarTipoBusquedaPlanCuenta}" 
								reRender="colTextoPlanCuenta"  />
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Periodo"></h:outputText>
           			</rich:column>
           			<rich:column width="80">
           				<h:selectOneMenu style="width: 80px;" 
           					disabled="#{!bancoFondoController.habilitarPeriodoPlanCuenta}"
           					value="#{bancoFondoController.planCuentaFiltro.id.intPeriodoCuenta}">
           					<f:selectItem itemValue="-1" itemLabel="Todos"/>
           					<tumih:selectItems var="sel"
								value="#{bancoFondoController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
           				</h:selectOneMenu>
           			</rich:column>
	    			<rich:column id="colTextoPlanCuenta">
	    				<h:inputText
	    					value="#{bancoFondoController.planCuentaFiltro.strComentario}"
	    					disabled="#{!bancoFondoController.habilitarTextoPlanCuenta}" 
	    					size="30"/>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{bancoFondoController.buscarPlanCuenta}" 
	    					value="Buscar" 
	    					reRender="frmSeleccionPlanCuenta" 
	    					styleClass="btnEstilos">
	    				</a4j:commandButton>
	    			</rich:column>	    		
	    	</h:panelGrid>
	    	
	    	
	    	<h:panelGroup id="panelDesPlanCuenta">
	    		
	    		<h:panelGroup rendered="#{bancoFondoController.intTipoPopUpPlanCuenta==applicationScope.Constante.BANCOCUENTA}">
	    			<h:panelGroup rendered="#{empty bancoFondoController.bancoCuentaAgregar.strNumerocuenta}">
			    		<h:outputText value="No posee una cuenta contable seleccionada actualemente"/>
			    	</h:panelGroup>
			    	<h:panelGroup rendered="#{!empty bancoFondoController.bancoCuentaAgregar.strNumerocuenta}">
			    		<h:outputText value="Cuenta contable seleccionada actualemente : '#{bancoFondoController.bancoCuentaAgregar.strNumerocuenta}' "/>
			    		<a4j:commandLink 
			    			value="Deseleccionar"
							action="#{bancoFondoController.deseleccionarPlanCuenta}"
							reRender="frmSeleccionCuentaBancaria,panelDesPlanCuenta">
						</a4j:commandLink>
			    	</h:panelGroup>
	    		</h:panelGroup>
				
				<h:panelGroup rendered="#{bancoFondoController.intTipoPopUpPlanCuenta==applicationScope.Constante.BANCOCUENTACHEQUE}">
	    			<h:panelGroup rendered="#{empty bancoFondoController.bancoCuentaChequeAgregar.strNumerocuenta}">
			    		<h:outputText value="No posee una cuenta contable seleccionada actualemente"/>
			    	</h:panelGroup>
			    	<h:panelGroup rendered="#{!empty bancoFondoController.bancoCuentaChequeAgregar.strNumerocuenta}">
			    		<h:outputText value="Cuenta contable seleccionada actualemente : '#{bancoFondoController.bancoCuentaChequeAgregar.strNumerocuenta}' "/>
			    		<a4j:commandLink 
			    			value="Deseleccionar"
							action="#{bancoFondoController.deseleccionarPlanCuenta}"
							reRender="frmSeleccionCuentaCheques,panelDesPlanCuenta">
						</a4j:commandLink>
			    	</h:panelGroup>
	    		</h:panelGroup>
	    		
	    		<h:panelGroup rendered="#{bancoFondoController.intTipoPopUpPlanCuenta==applicationScope.Constante.FONDOFIJOCUENTA}">
	    			<h:panelGroup rendered="#{empty bancoFondoController.fondoDetalleAgregar.strNumerocuenta}">
			    		<h:outputText value="No posee una cuenta contable seleccionada actualemente"/>
			    	</h:panelGroup>
			    	<h:panelGroup rendered="#{!empty bancoFondoController.fondoDetalleAgregar.strNumerocuenta}">
			    		<h:outputText value="Cuenta contable seleccionada actualemente : '#{bancoFondoController.fondoDetalleAgregar.strNumerocuenta}' "/>
			    		<a4j:commandLink 
			    			value="Deseleccionar"
							action="#{bancoFondoController.deseleccionarPlanCuenta}"
							reRender="frmSeleccionFondoCuenta,panelDesPlanCuenta">
						</a4j:commandLink>
			    	</h:panelGroup>
	    		</h:panelGroup>
	    		
	    		<h:panelGroup rendered="#{bancoFondoController.intTipoPopUpPlanCuenta==applicationScope.Constante.FONDOFIJOSOBREGIRO}">
	    			<h:panelGroup rendered="#{empty bancoFondoController.fondoDetalleAgregar.strNumerocuenta}">
			    		<h:outputText value="No posee una cuenta contable seleccionada actualemente"/>
			    	</h:panelGroup>
			    	<h:panelGroup rendered="#{!empty bancoFondoController.fondoDetalleAgregar.strNumerocuenta}">
			    		<h:outputText value="Cuenta contable seleccionada actualemente : '#{bancoFondoController.fondoDetalleAgregar.strNumerocuenta}' "/>
			    		<a4j:commandLink 
			    			value="Deseleccionar"
							action="#{bancoFondoController.deseleccionarPlanCuenta}"
							reRender="frmSeleccionFondoSobregiro,panelDesPlanCuenta">
						</a4j:commandLink>
			    	</h:panelGroup>
	    		</h:panelGroup>
	    		
		    </h:panelGroup>		  
		    	
	    	<h:panelGroup id="divTblSeleccionCuentarATIO" layout="block" style="padding:15px">
	    		<rich:dataTable id="tblSeleccionCuenta" 
	    			var="item" 
	                value="#{bancoFondoController.listaPlanCuenta}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="660px">
			                           
					 	<rich:column width="30px">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column width="80">
	                        <f:facet name="header">
	                            <h:outputText value="Periodo"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.intPeriodoCuenta}"></h:outputText>
			            </rich:column> 
			            <rich:column width="150">
	                        <f:facet name="header">
	                            <h:outputText value="Cuenta"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.strNumeroCuenta}"></h:outputText>
			            </rich:column>
			            <rich:column width="300" style="text-align: left">
	                        <f:facet name="header">
	                            <h:outputText value="Descripción"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strDescripcion}"></h:outputText>
			            </rich:column>
			            <rich:column width="100" style="text-align: center">
	                        <f:facet name="header">
	                            <h:outputText value="Seleccionar"></h:outputText>
	                        </f:facet>
							<h:selectOneRadio>
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick"
									actionListener="#{bancoFondoController.seleccionarPlanCuenta}" 
									reRender="frmSeleccionCuentaBancaria,panelChequesDiferidos,frmSeleccionFondoCuenta,frmSeleccionFondoSobregiro" 
									ajaxSingle="true" 
									oncomplete="Richfaces.hideModalPanel('pSeleccionPlanCuenta')">
									<f:attribute name="item" value="#{item}"/>
								</a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
					   <f:facet name="footer">
					   		 <h:panelGroup layout="block">
						   		 <rich:datascroller for="tblSeleccionCuenta" maxPages="10"/>
					   		 </h:panelGroup>  
				       </f:facet>
	            </rich:dataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	</h:form>