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

<rich:modalPanel id="mpUpDelTarifas" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Tarifas" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTarifas"/>
               <rich:componentControl for="mpUpDelTarifas" attachTo="hidelinkTarifas" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formUpDelTarifas">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
         <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
         </h:panelGrid>
         <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" actionListener="#{tarifaController.obtenerTarifa}" styleClass="btnEstilos"
              				reRender="divFormTarifa" oncomplete="Richfaces.hideModalPanel('mpUpDelTarifas')"></a4j:commandButton>
          	</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>

<a4j:region>
	<a4j:jsFunction name="selecTarifa" 
		actionListener="#{tarifaController.setSelectedTarifa}">
		<f:param name="rowTarifa"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmTarifa">
   	<h:panelGroup id="divTarifa" layout="block" style="padding:15px">
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="TARIFAS GENERALES" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="Tipo de Tarifa"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:selectOneMenu value="#{tarifaController.tarifaBusq.id.intParaTipoTarifaCod}">
    					<f:selectItem itemValue="0" itemLabel="Todos"/>
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOTARIFA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton actionListener="#{tarifaController.buscarTarifa}" value="Buscar" 
    								reRender="divTblTarifas" styleClass="btnEstilos"></a4j:commandButton>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGroup id="divTblTarifas" layout="block" style="padding:15px">
    		<rich:extendedDataTable id="tblTarifas" enableContextMenu="false" style="margin:0 auto"
                        var="item" value="#{tarifaController.listTarifa}" onRowClick="selecTarifa('#{rowKey}')"
		  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="800px" height="165px">
		                           
				 	<rich:column width="31px">
		                <h:outputText value="#{rowKey + 1}"></h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Tipo de Tarifa"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOTARIFA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaTipoTarifaCod}"/>
		            </rich:column> 
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Fecha Inicio"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.dtParaTarifaDesde}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Fecha Fin"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.dtTarifaHasta}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Forma"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FORMAAPLICAR}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaFormaAplicar}"/>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Valor"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.bdValor}"></h:outputText>
		            </rich:column>
		            <rich:column width="130">
                        <f:facet name="header">
                            <h:outputText value="Fecha de Registro"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.tsFechaRegistro}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column>

		                    
			   <f:facet name="footer">
			   		 <h:panelGroup layout="block">
				   		 <rich:datascroller for="tblTarifas" maxPages="10"/>
				   		 <h:panelGroup layout="block" style="margin:0 auto; width:360px">
				   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelTarifas')">
								<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
							</a4j:commandLink>
							<h:outputText value="Para modificar el registro seleccione la fila haciendo click." style="color:#8ca0bd"/>
				   		 </h:panelGroup>
			   		 </h:panelGroup>  
		       </f:facet>
            </rich:extendedDataTable>
    	</h:panelGroup>
    	
    	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
           
           <h:panelGrid columns="3">
           		<rich:column>
           			<a4j:commandButton value="Nuevo" actionListener="#{tarifaController.nuevaTarifa}" 
           							reRender="divFormTarifa" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Grabar" actionListener="#{tarifaController.grabarTarifa}"  
           							reRender="divTblTarifas,divFormTarifa,mpMessage" styleClass="btnEstilos"
           							oncomplete="if(#{tarifaController.isValidTarifa==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Cancelar" actionListener="#{tarifaController.cancelarNuevo}" 
           							reRender="divFormTarifa" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           </h:panelGrid>
           
        <h:panelGroup id="divFormTarifa" layout="block">
           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px"
           				rendered="#{tarifaController.blnShowDivFormTarifa}">
           		<h:panelGrid columns="3" styleClass="tableCellBorder4">
           			<rich:columnGroup>
           				<rich:column>
	           				<h:outputText value="Tipo de tarifa"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:selectOneMenu value="#{tarifaController.beanTarifa.id.intParaTipoTarifaCod}" disabled="#{tarifaController.blnUpdating}">
           						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
           						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOTARIFA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
           					</h:selectOneMenu>
	           			</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Rango de tiempo"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:outputLabel value="Desde"></h:outputLabel>
           					<rich:calendar value="#{tarifaController.beanTarifa.id.dtParaTarifaDesde}" 
    							datePattern="dd/MM/yyyy" style="width:76px" disabled="#{tarifaController.blnUpdating}"></rich:calendar>
           				</rich:column>
           				<rich:column>
           					<h:outputLabel value="Hasta"></h:outputLabel>
           					<rich:calendar value="#{tarifaController.beanTarifa.dtTarifaHasta}" 
    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Forma de Aplicar"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:selectOneMenu value="#{tarifaController.beanTarifa.intParaFormaAplicar}">
           						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
           						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FORMAAPLICAR}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
           					</h:selectOneMenu>
           				</rich:column>
           				<rich:column>
           					<h:outputLabel value="Valor"></h:outputLabel>
           					<h:inputText value="#{tarifaController.beanTarifa.bdValor}" 
           								onkeyup="return extractNumber(this,3,false)" onblur="return extractNumber(this,3,false)"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Comentario"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:inputText value="#{tarifaController.beanTarifa.strComentario}"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           </h:panelGroup>
        </h:panelGroup>
   	</h:panelGroup>
</h:form>