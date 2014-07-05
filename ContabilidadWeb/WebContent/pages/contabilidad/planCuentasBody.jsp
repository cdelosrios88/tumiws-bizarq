<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

<rich:modalPanel id="mpUpDelPlanCuentas" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Plan de Cuentas" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTipoCambio"/>
               <rich:componentControl for="mpUpDelPlanCuentas" attachTo="hidelinkTipoCambio" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formUpDelPlanCuentas">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
         <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
         </h:panelGrid>
         <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" actionListener="#{planCuentasController.obtenerPlanCuentas}" styleClass="btnEstilos"
              				reRender="divFormPlanCuentas" oncomplete="Richfaces.hideModalPanel('mpUpDelPlanCuentas')"></a4j:commandButton>
              <a4j:commandButton value="Eliminar" actionListener="#{planCuentasController.eliminarPlanCuentas}" styleClass="btnEstilos"
              				reRender="divTblPlanCuentas" oncomplete="Richfaces.hideModalPanel('mpUpDelPlanCuentas')"></a4j:commandButton>
          	</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>
<a4j:include viewId="/pages/contabilidad/popup/seleccionCuentaBody.jsp"/>

<a4j:region>
	<a4j:jsFunction name="selecPlanCuentas" 
		actionListener="#{planCuentasController.setSelectedPlanCuentas}">
		<f:param name="rowPlanCuentas"></f:param>
	</a4j:jsFunction>
	<%-- 
	<a4j:jsFunction name="selecEstadoFinanciero" 
		actionListener="#{planCuentasController.setSelectedEstadoFinanciero}">
		<f:param name="rowEstadoFinanciero"></f:param>
	</a4j:jsFunction>
	--%>
	<a4j:jsFunction name="selecFiltroCombo" reRender="txtFiltroBusq"
		actionListener="#{planCuentasController.disableTextBusq}">
		<f:param name="pFiltroCombo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoCuenta" reRender="txtCuentaBusq"
		actionListener="#{planCuentasController.disableTxtCuentaBusq}">
		<f:param name="pCboTipoCuentaBusq"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmPlanCuentas">
   	<h:panelGroup id="divPlanCuentas" layout="block" style="padding:15px">
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="PLAN DE CUENTAS CONTABLE" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
    		<rich:columnGroup>
    			<rich:column>
    				<h:selectOneMenu value="#{planCuentasController.intFiltroSelect}" onchange="selecFiltroCombo(#{applicationScope.Constante.ONCHANGE_VALUE})">
    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<h:inputText id="txtFiltroBusq" value="#{planCuentasController.strFiltroBusq}" disabled="#{planCuentasController.isDisabledTextBusq}"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:selectManyCheckbox value="#{planCuentasController.strFiltroCheck}">
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROCHECKPLANCUENTAS}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    				</h:selectManyCheckbox>
    			</rich:column>
    			<!-- Added by cdelosrios on 13/09/2013 -->
    			<rich:column>
    				<h:selectOneMenu value="#{planCuentasController.intPeriodoCuenta}">
       					<f:selectItems value="#{planCuentasController.cboPeriodos}"></f:selectItems>
       				</h:selectOneMenu>
    			</rich:column>
    			<!-- End added by cdelosrios on 13/09/2013 -->
    			<rich:column>
    				<a4j:commandButton actionListener="#{planCuentasController.buscarPlanCuentas}" value="Buscar" 
    								reRender="divTblPlanCuentas" styleClass="btnEstilos"></a4j:commandButton>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGroup id="divTblPlanCuentas" layout="block" style="padding:15px">
    		<rich:extendedDataTable id="tblPlanCuentas" 
    					enableContextMenu="false" 
    					style="margin:0 auto"
                        var="item" 
                        value="#{planCuentasController.listPlanCuentas}" 
                        onRowClick="selecPlanCuentas('#{rowKey}')"
		  		 		rowKeyVar="rowKey" 
		  		 		rows="5" 
		  		 		sortMode="single" 
		  		 		width="980px" 
		  		 		height="182px">
		                           
				 	<rich:column width="40px">
				 		<f:facet name="header">
		                	<h:outputText value="Nro."></h:outputText>
		                </f:facet>
		                <h:outputText value="#{rowKey + 1}"></h:outputText>
		            </rich:column>
		            <rich:column width="70px">
                        <f:facet name="header">
                            <h:outputText value="Periodo"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.intPeriodoCuenta}"></h:outputText>
		            </rich:column> 
		            <rich:column width="120" style="text-align: left">
                        <f:facet name="header">
                            <h:outputText value="Cuenta"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.strNumeroCuenta}"></h:outputText>
		            </rich:column>
		            <rich:column width="340">
                        <f:facet name="header">
                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.strDescripcion}"></h:outputText>
		            </rich:column>
		            <rich:column width="100">
                        <f:facet name="header">
                            <h:outputText value="Operacional" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="sí" rendered="#{item.intMovimiento==1}"></h:outputText>
		            </rich:column>
		            <rich:column width="90">
                        <f:facet name="header">
                            <h:outputText value="Extranjero" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="sí" rendered="#{item.intIdentificadorExtranjero==1}"></h:outputText>
		            </rich:column>
					<rich:column width="150">
                        <f:facet name="header">
                            <h:outputText value="Ctas. Destino" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="sí" rendered="#{item.strNumeroCuentaDestino!=null}"></h:outputText>
		            </rich:column>
		            <rich:column width="70">
                        <f:facet name="header">
                            <h:outputText value="Estado" style="font-weight:bold"></h:outputText>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intEstadoCod}"/>
		            </rich:column>
		                    
			   <f:facet name="footer">
			   		 <h:panelGroup layout="block">
				   		 <rich:datascroller for="tblPlanCuentas" maxPages="10"/>
				   		 <h:panelGroup layout="block" style="margin:0 auto; width:360px">
				   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelPlanCuentas')">
								<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
							</a4j:commandLink>
							<h:outputText value="Para modificar el Plan de Cuentas hacer click en el Registro" style="color:#8ca0bd"/>
				   		 </h:panelGroup>
			   		 </h:panelGroup>  
		       </f:facet>
            </rich:extendedDataTable>
    	</h:panelGroup>
    	
    	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
           
           <h:panelGrid columns="3">
           		<rich:column>
           			<a4j:commandButton value="Nuevo" actionListener="#{planCuentasController.nuevoPlanCuentas}" 
           							reRender="divFormPlanCuentas" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Grabar" actionListener="#{planCuentasController.grabarPlanCuentas}" 
           							reRender="divTblPlanCuentas,divFormPlanCuentas,mpMessage" styleClass="btnEstilos"
           							oncomplete="if(#{planCuentasController.isValidPlanCuentas==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Cancelar" actionListener="#{planCuentasController.cancelarNuevo}" 
           							reRender="divFormPlanCuentas" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           </h:panelGrid>
           
        <h:panelGroup id="divFormPlanCuentas" layout="block">
           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px"
           				rendered="#{planCuentasController.blnShowDivFormPlanCuentas}">
           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
           			<rich:columnGroup>
           				<rich:column>
	           				<h:outputText value="Período"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:selectOneMenu value="#{planCuentasController.beanPlanCuentas.id.intPeriodoCuenta}">
	           					<f:selectItems value="#{planCuentasController.cboPeriodos}"></f:selectItems>
	           				</h:selectOneMenu>
	           			</rich:column>
	           			<rich:column>
	           				<h:outputText value="Estado"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:selectOneMenu value="#{planCuentasController.beanPlanCuentas.intEstadoCod}">
	           					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	           					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	           				</h:selectOneMenu>
	           			</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Cuenta Contable"></h:outputText>
           				</rich:column>
           				<rich:column colspan="3">
           					<h:inputText value="#{planCuentasController.beanPlanCuentas.id.strNumeroCuenta}" 
           								onkeydown="return validarEnteros(event)" disabled="#{planCuentasController.blnUpdating}"></h:inputText>
           				</rich:column>
           				<rich:column>
           					<h:selectBooleanCheckbox value="#{planCuentasController.beanPlanCuentas.blnTieneMovimiento}">
           					</h:selectBooleanCheckbox>
           					<h:outputLabel value="Nivel Operacional"></h:outputLabel>
           				</rich:column>
           				<rich:column>
           					<h:selectBooleanCheckbox value="#{planCuentasController.beanPlanCuentas.blnTieneIdExtranjero}">
           					</h:selectBooleanCheckbox>
           					<h:outputLabel value="Operaciones extranjeras"></h:outputLabel>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Descripción"></h:outputText>
           				</rich:column>
           				<rich:column colspan="3">
           					<h:inputText value="#{planCuentasController.beanPlanCuentas.strDescripcion}"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Cuenta Origen"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText id="txtCuentaOrigen" value="#{planCuentasController.beanPlanCuentas.strNumeroCuentaOrigen}"disabled="true"></h:inputText>
           				</rich:column>
           				<rich:column>
           					<a4j:commandButton value="Buscar" actionListener="#{planCuentasController.buscarCuentaOrigen}" styleClass="btnEstilos"
           									reRender="mpSeleccionCuenta" styleClass="btnEstilos"
           									oncomplete="Richfaces.showModalPanel('mpSeleccionCuenta')"></a4j:commandButton>
           				</rich:column>
           				<rich:column>
           					<h:outputText value="Cuenta Destino"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText id="txtCuentaDestino" value="#{planCuentasController.beanPlanCuentas.strNumeroCuentaDestino}" disabled="true"/>
           				</rich:column>
           				<rich:column>
           					<a4j:commandButton value="Buscar" actionListener="#{planCuentasController.buscarCuentaDestino}" styleClass="btnEstilos"
								reRender="mpSeleccionCuenta" styleClass="btnEstilos"
								oncomplete="Richfaces.showModalPanel('mpSeleccionCuenta')"/>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Comentario"></h:outputText>
           				</rich:column>
           				<rich:column colspan="5">
           					<h:inputText value="#{planCuentasController.beanPlanCuentas.strComentario}"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           		
           		<rich:spacer height="10px"></rich:spacer>
	           <h:panelGroup layout="block">
	           		<h:panelGrid>
	           			<rich:columnGroup>
	           				<rich:column>
	           					<h:outputText value="Estados Financieros" style="font-weight:bold"></h:outputText>
	           				</rich:column>
	           			</rich:columnGroup>
	           			<rich:columnGroup>
	           				<!-- Modificado por cdelosrios, 13/09/2013 -->
	           				<%--
	           				<rich:column>
	           					<h:selectOneMenu value="#{planCuentasController.intEstadoFinancieroBusq}" disabled="#{planCuentasController.blnUpdating}">
		           					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		           					<tumih:selectItems var="sel" value="#{planCuentasController.listEstadoFinanciero}" 
								  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		           				</h:selectOneMenu>
	           				</rich:column>
	           				--%>
	           				
	           				<rich:column>
	           					<a4j:commandButton value="Agregar" actionListener="#{planCuentasController.abrirPopUpElemento}" styleClass="btnEstilos"
	           									reRender="pSeleccionElemento" styleClass="btnEstilos"
	           									oncomplete="Richfaces.showModalPanel('pSeleccionElemento')">
	           					</a4j:commandButton>
	           				</rich:column>
	           				<%--
	           				<rich:column>
	           					<a4j:commandButton value="Buscar" actionListener="#{planCuentasController.buscarEstadoFinanciero}"
	           									reRender="divTblEstadoFinanciero" styleClass="btnEstilos"/>
	           				</rich:column>--%>
	           				<!-- Fin modificación, cdelosrios, 13/09/2013 -->
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		
	           		<h:panelGrid id="divTblEstadoFinanciero" style="padding:15px">
	           			<rich:dataTable id="dtEstadoFinanciero" value="#{anexoPopupController.listaAnexoDetalleCuenta}"
		    					rows="#{fn:length(anexoPopupController.listaAnexoDetalleCuenta)}" 
		    					var="item" rowKeyVar="rowKey" sortMode="single" 
		    					rendered="#{not empty anexoPopupController.listaAnexoDetalleCuenta}"> 
		    				<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		    					<rich:column style="text-align: center">
		    						<h:outputText value="#{rowKey+1}"/>
		    					</rich:column>
		    					<rich:column width="600">
					            	<f:facet name="header">
					                	<h:outputText value="Elemento"/>
					                </f:facet>
					                <h:outputText value="#{item.strTexto}"/>
							   	</rich:column>
							   	<rich:column>
					            	<a4j:commandLink id="lnkQuitarElemento"
				               			actionListener="#{planCuentasController.removeElemento}" reRender="divTblEstadoFinanciero">
				   						<f:param name="rowKeyElemento" value="#{rowKey}" />
				   						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
				   						<rich:toolTip for="lnkQuitarElemento" value="Eliminar" followMouse="true"/>
				   					</a4j:commandLink>
					            </rich:column>
		    				</rich:columnGroup>
		    				
			                <f:facet name="footer">
			                    <rich:datascroller for="dtEstadoFinanciero" maxPages="5"/>
			                </f:facet> 
		                </rich:dataTable>
			    	</h:panelGrid>
	           </h:panelGroup>
           </h:panelGroup>
        </h:panelGroup>
   	</h:panelGroup>
</h:form>