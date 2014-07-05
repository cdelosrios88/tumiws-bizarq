<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

<rich:modalPanel id="mpUpDelTipoCambio" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Tipo Cambio" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTipoCambio"/>
               <rich:componentControl for="mpUpDelTipoCambio" attachTo="hidelinkTipoCambio" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formUpDelTipoCambio">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
         <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
         </h:panelGrid>
         <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" actionListener="#{tipoCambioController.obtenerTipoCambio}" styleClass="btnEstilos"
              				reRender="divFormTipoCambio" oncomplete="Richfaces.hideModalPanel('mpUpDelTipoCambio')"></a4j:commandButton>
          	</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>

<a4j:region>
	<a4j:jsFunction name="selecTipoCambio" 
		actionListener="#{tipoCambioController.setSelectedTipoCambio}">
		<f:param name="rowTipoCambio"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmTipoCambio">
   	<h:panelGroup id="divTipoCambio" layout="block" style="padding:15px">
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="TIPO DE CAMBIO" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="Fecha"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<rich:calendar value="#{tipoCambioController.tipoCambioBusq.dtFechaTCDesde}" 
    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
    			</rich:column>
    			<rich:column>
    				<rich:calendar value="#{tipoCambioController.tipoCambioBusq.dtFechaTCHasta}" 
    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Tipo de Moneda"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:selectOneMenu value="#{tipoCambioController.tipoCambioBusq.id.intParaMoneda}">
    					<f:selectItem itemValue="0" itemLabel="Todos"/>
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							  				
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Tipo de Cambio"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:selectOneMenu value="#{tipoCambioController.tipoCambioBusq.id.intParaClaseTipoCambio}">
    					<f:selectItem itemValue="0" itemLabel="Todos"/>
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCAMBIO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton actionListener="#{tipoCambioController.buscarTipoCambio}" value="Buscar" 
    								reRender="divTblTipoCambio" styleClass="btnEstilos"></a4j:commandButton>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGroup id="divTblTipoCambio" layout="block" style="padding:15px">
    		<rich:extendedDataTable id="tblTipoCambio" enableContextMenu="false" style="margin:0 auto"
                        var="item" value="#{tipoCambioController.listTipoCambio}" onRowClick="selecTipoCambio('#{rowKey}')"
		  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="800px" height="165px">
		                           
				 	<rich:column width="31px">
				 		<f:facet name="header">
		                            <h:outputText value="Nro."></h:outputText>
		                        </f:facet>
		                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
		            </rich:column>
		                    
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Fecha"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.dtParaFecha}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column> 
		            <rich:column width="120">
                        <f:facet name="header">
                            <h:outputText value="Tipo de moneda"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaMoneda}"/>
		            </rich:column>
		            <rich:column width="120">
                        <f:facet name="header">
                            <h:outputText value="Tipo de cambio" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCAMBIO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaClaseTipoCambio}"/>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Compra" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.bdCompra}"></h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Venta" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.bdVenta}"></h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Promedio" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.bdPromedio}"></h:outputText>
		            </rich:column>
		            <rich:column width="130">
                        <f:facet name="header">
                            <h:outputText value="Fecha de Registro" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.tsFechaRegistro}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column>

		                    
			   <f:facet name="footer">
			   		 <h:panelGroup layout="block">
				   		 <rich:datascroller for="tblTipoCambio" maxPages="10"/>
				   		 <h:panelGroup layout="block" style="margin:0 auto; width:360px">
				   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelTipoCambio')">
								<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
							</a4j:commandLink>
							<h:outputText value="Para modificar el Tipo de Cambio hacer click en el Registro" style="color:#8ca0bd"/>
				   		 </h:panelGroup>
			   		 </h:panelGroup>  
		       </f:facet>
            </rich:extendedDataTable>
    	</h:panelGroup>
           
        <a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
           
           <h:panelGrid columns="3">
           		<rich:column>
           			<a4j:commandButton value="Nuevo" actionListener="#{tipoCambioController.nuevoTipoCambio}" 
           							reRender="divFormTipoCambio" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Grabar" actionListener="#{tipoCambioController.grabarTipoCambio}" 
           							reRender="divTblTipoCambio,divFormTipoCambio,mpMessage" styleClass="btnEstilos"
           							oncomplete="if(#{tipoCambioController.isValidTipoCambio==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Cancelar" actionListener="#{tipoCambioController.cancelarNuevo}" 
           							reRender="divFormTipoCambio" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           </h:panelGrid>
           
        <h:panelGroup id="divFormTipoCambio" layout="block">
           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px"
           				rendered="#{tipoCambioController.blnShowDivFormTipoCambio}">
           		<h:panelGrid columns="2" styleClass="tableCellBorder4">
           			<rich:columnGroup>
           				<rich:column>
	           				<h:outputText value="Fecha"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<rich:calendar value="#{tipoCambioController.beanTipoCambio.id.dtParaFecha}" 
	           							datePattern="dd/MM/yyyy" style="width:76px" disabled="#{tipoCambioController.blnUpdating}"></rich:calendar>
	           			</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Tipo de Moneda"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:selectOneMenu value="#{tipoCambioController.beanTipoCambio.id.intParaMoneda}" disabled="#{tipoCambioController.blnUpdating}">
           						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
           						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
           					</h:selectOneMenu>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Tipo de Cambio"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:selectOneMenu value="#{tipoCambioController.beanTipoCambio.id.intParaClaseTipoCambio}" disabled="#{tipoCambioController.blnUpdating}">
           						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
           						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCAMBIO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
           					</h:selectOneMenu>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Compra"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText value="#{tipoCambioController.beanTipoCambio.bdCompra}" 
           								onkeyup="return extractNumber(this,3,false)" onblur="return extractNumber(this,3,false)"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Venta"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText value="#{tipoCambioController.beanTipoCambio.bdVenta}" 
           								onkeyup="return extractNumber(this,3,false)" onblur="return extractNumber(this,3,false)"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Promedio"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText value="#{tipoCambioController.beanTipoCambio.bdPromedio}" 
           								onkeyup="return extractNumber(this,3,false)" onblur="return extractNumber(this,3,false)"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           </h:panelGroup>
        </h:panelGroup>
   	</h:panelGroup>
</h:form>