<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO MODELO CONTABLE      -->
	<!-- Fecha     : 17/05/2012               		-->

<rich:modalPanel id="mpUpDelModelo" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Modelo Contable" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkModelo"/>
               <rich:componentControl for="mpUpDelModelo" attachTo="hidelinkModelo" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formUpDelModelo">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
         <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
         </h:panelGrid>
         <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" actionListener="#{modeloController.obtenerModelo}" styleClass="btnEstilos"
              				reRender="divFormModelo" oncomplete="Richfaces.hideModalPanel('mpUpDelModelo')"></a4j:commandButton>
              <a4j:commandButton value="Eliminar" actionListener="#{modeloController.eliminarModelo}" styleClass="btnEstilos"
              				reRender="divTblModelo" oncomplete="Richfaces.hideModalPanel('mpUpDelModelo')"></a4j:commandButton>
          	</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>
<a4j:include viewId="/pages/contabilidad/popup/mpModeloDetalleBody.jsp"/>
<a4j:include viewId="/pages/contabilidad/popup/mpModeloDetalleNivelBody.jsp"/>
<a4j:include viewId="/pages/mensajes/confirmDialogBody.jsp"/>

<a4j:region>
	<a4j:jsFunction name="selecModelo" 
		actionListener="#{modeloController.setSelectedModelo}">
		<f:param name="rowModelo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecModeloDetalle" 
		actionListener="#{modeloController.setSelectedModeloDetalle}">
		<f:param name="rowModeloDetalle"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecModeloDetNivel" 
		actionListener="#{modeloController.setSelectedModeloDetNivel}">
		<f:param name="rowModeloDetNivel"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoCuenta" reRender="txtCuentaBusq"
		actionListener="#{modeloController.disableTxtCuentaBusq}">
		<f:param name="pCboTipoCuentaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoRegistroNivel" reRender="txtDatoTablas,txtDatoArgumento,txtValor"
		actionListener="#{modeloController.disableTipoRegistro}">
		<f:param name="pRdoTipoRegistro"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoMovimiento" reRender="divFormModeloDetalleNivel"
		actionListener="#{modeloController.cleanModeloDetalleNivel}">
		<f:param name="pRdoTipoRegistro"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="acceptMessage" reRender="divTblModeloDetalle"
		actionListener="#{modeloController.deleteModeloDetalle}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="eliminarModeloDetNivel" reRender="divTblDetalleNivel"
		actionListener="#{modeloController.deleteModeloDetNivel}">
	</a4j:jsFunction>
</a4j:region>

<rich:jQuery name="disableDetNivelInputs" selector="#divFormModeloDetalleNivel :input" query="attr('disabled','disabled');" timing="onJScall" />

<h:form id="frmModelo">
   	<h:panelGroup id="divModelo" layout="block" style="padding:15px">
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="MODELOS CONTABLES" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="Tipo"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:selectOneMenu id="cboTipoModeloBusq" value="#{modeloController.beanBusqueda.intTipoModeloContable}">
    					<f:selectItem itemValue="0" itemLabel="Todos"/>
    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMODELOCONTABLE}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{modeloController.beanBusqueda.strDescripcion}"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Periodo"></h:outputText>
    			</rich:column>
    			<rich:column>
       				<h:selectOneMenu id="cboContPeriodoBusq" value="#{modeloController.beanBusqueda.intPeriodo}">
       					<f:selectItems value="#{modeloController.cboPeriodos}"></f:selectItems>
       				</h:selectOneMenu>
       			</rich:column>
       			<rich:column>
       				<h:outputText value="Estado"></h:outputText>
       			</rich:column>
       			<rich:column>
       				<h:selectOneMenu id="cboEstadoBusq" value="#{modeloController.beanBusqueda.intEstado}">
	   					<f:selectItem itemValue="0" itemLabel="Todos"/>
	   					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	   				</h:selectOneMenu>
       			</rich:column>
    			<rich:column>
    				<a4j:commandButton actionListener="#{modeloController.buscarModelo}" value="Buscar" 
    								reRender="divTblModelo" styleClass="btnEstilos"></a4j:commandButton>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGroup id="divTblModelo" layout="block" style="padding:15px">
    		<rich:extendedDataTable id="tblModelo" enableContextMenu="false" style="margin:0 auto"
    					value="#{modeloController.listModelo}" rendered="#{not empty modeloController.listModelo}"
                        var="item" onRowClick="selecModelo('#{rowKey}')"
		  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="653px" height="205px">
		                           
				 	<rich:column width="31">
		            	<h:outputText value="#{rowKey + 1}"></h:outputText>
		            </rich:column>
		                    
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Código"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.intCodigoModelo}"></h:outputText>
		            </rich:column> 
		            <rich:column width="140">
                        <f:facet name="header">
                            <h:outputText value="Tipo"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMODELOCONTABLE}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intTipoModeloContable}"/>
		            </rich:column>
		            <rich:column width="150">
                        <f:facet name="header">
                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.strDescripcion}"></h:outputText>
		            </rich:column>
R		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Periodo" style="font-weight:bold"></h:outputText>
                        </f:facet>
                        <tumih:outputText value="#{modeloController.cboPeriodos}" 
							itemValue="value" itemLabel="label" 
							property="#{item.intPeriodo}"/>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Estado" style="font-weight:bold"></h:outputText>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intEstado}"/>
		            </rich:column>
		                    
			   <f:facet name="footer">
			   		 <h:panelGroup layout="block">
				   		 <rich:datascroller for="tblModelo" maxPages="10"/>
				   		 <h:panelGroup layout="block" style="margin:0 auto; width:410px">
				   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelModelo')">
								<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
							</a4j:commandLink>
							<h:outputText value="Para Modificar o Eliminar el Modelo Contable hacer click en el Registro" style="color:#8ca0bd"/>
				   		 </h:panelGroup>
			   		 </h:panelGroup>  
		       </f:facet>
            </rich:extendedDataTable>
    	</h:panelGroup>
           
       	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
           
           <h:panelGrid columns="3">
           		<rich:column>
           			<a4j:commandButton value="Nuevo" actionListener="#{modeloController.nuevoModelo}" 
           							reRender="divFormModelo" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Grabar" actionListener="#{modeloController.grabarModelo}" 
           							reRender="divTblModelo,divFormModelo,mpMessage" styleClass="btnEstilos"
           							oncomplete="if(#{modeloController.isValidModelo==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Cancelar" actionListener="#{modeloController.cancelarNuevo}" 
           							reRender="divFormModelo" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           </h:panelGrid>
           
        <h:panelGroup id="divFormModelo" layout="block">
           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px"
           				rendered="#{modeloController.blnShowDivFormModelo}">
           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
           			<rich:columnGroup>
           				<rich:column>
	           				<h:outputText value="Código"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:inputText value="#{modeloController.beanModelo.id.intCodigoModelo}" disabled="true"></h:inputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:outputText value="Estado"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:selectOneMenu id="cboEstadoModelo" value="#{modeloController.beanModelo.intEstado}">
	           					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	           					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	           				</h:selectOneMenu>
	           			</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Tipo de Modelo"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:selectOneMenu id="cboTipoModelo" value="#{modeloController.beanModelo.intTipoModeloContable}">
		    					<f:selectItem itemValue="0" itemLabel="Todos"/>
		    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMODELOCONTABLE}" 
									  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		    				</h:selectOneMenu>
           				</rich:column>
           				<rich:column>
           					<h:outputText value="Periodo"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<h:selectOneMenu id="cboPeriodoModelo" value="#{modeloController.beanModelo.intPeriodo}">
		    					<f:selectItems value="#{modeloController.cboPeriodos}"></f:selectItems>
		    				</h:selectOneMenu>
           				</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Descripción"></h:outputText>
           				</rich:column>
           				<rich:column colspan="2">
           					<h:inputText value="#{modeloController.beanModelo.strDescripcion}"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           		
           		<h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px">
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Busqueda y Selección de Cuenta"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<a4j:commandButton value="Agregar cuenta" styleClass="btnEstilos1" actionListener="#{modeloController.buscarPlanCuenta}"
           									oncomplete="Richfaces.showModalPanel('mpModeloDetalle')" reRender="divModeloDetalle"></a4j:commandButton>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           		
           		<rich:spacer height="10px"></rich:spacer>
           		<h:panelGroup id="divTblModeloDetalle" layout="block">
           			<rich:extendedDataTable id="tblModeloDetalle" enableContextMenu="false" style="margin:0 auto"
           						rendered="#{not empty modeloController.beanModelo.listModeloDetalle}"
		                        var="item" value="#{modeloController.beanModelo.listModeloDetalle}" onRowClick="selecModeloDetalle('#{rowKey}')"
				  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="753px" height="205px">
				    		
				   			<rich:column width="31" filterExpression="#{item.isDeleted==null || !item.isDeleted}">
				                <h:outputText value="#{rowKey + 1}"></h:outputText>
				            </rich:column>
				                    
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Período"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.id.intContPeriodoCuenta}"></h:outputText>
				            </rich:column> 
				            <rich:column width="120">
		                        <f:facet name="header">
		                            <h:outputText value="Cuenta Contable"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.id.strContNumeroCuenta}"></h:outputText>
				            </rich:column>
				            <rich:column width="200">
		                        <f:facet name="header">
		                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.planCuenta.strDescripcion}"></h:outputText>
				            </rich:column>
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Debe"></h:outputText>
		                        </f:facet>
								<h:graphicImage value="/images/icons/icon_check.png"
											rendered="#{item.intParaOpcionDebeHaber == applicationScope.Constante.PARAM_T_OPCIONDEBEHABER_DEBE}"></h:graphicImage>
				            </rich:column>
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Haber"></h:outputText>
		                        </f:facet>
								<h:graphicImage value="/images/icons/icon_check.png"
											rendered="#{item.intParaOpcionDebeHaber == applicationScope.Constante.PARAM_T_OPCIONDEBEHABER_HABER}"></h:graphicImage>
				            </rich:column>
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Detalle"></h:outputText>
		                        </f:facet>
		                        <a4j:commandLink value="Ver" actionListener="#{modeloController.verModeloDetalleNivel}"
		                        				oncomplete="Richfaces.showModalPanel('mpModeloDetalleNivel')" reRender="divModeloDetalleNivel">
		                        	<f:param name="rowModeloDetalle" value="#{rowKey}"></f:param>
		                        </a4j:commandLink>
				            </rich:column>
				                    
					   <f:facet name="footer">
					   		 <h:panelGroup layout="block">
						   		 <rich:datascroller for="tblModeloDetalle" maxPages="10"/>
						   		 <h:panelGroup layout="block" style="margin:0 auto; width:420px">
						   		 	<h:outputText value="Para " style="color:#38749C"></h:outputText>
						   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpModeloDetalleNivel')"
						   		 					actionListener="#{modeloController.obtenerModeloDetalle}" reRender="mpModeloDetalleNivel">
										<h:graphicImage value="/images/icons/icon_edit.png" style="border:0px"/>
										<h:outputText value="Modificar"></h:outputText>
									</a4j:commandLink>
									<h:outputText value=" o " style="color:#38749C"></h:outputText>
									<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpConfirmMessage')"
													actionListener="#{modeloController.onConfirmDeleteModeloDet}" reRender="mpConfirmMessage">
										<h:graphicImage value="/images/icons/icon_trash.gif" style="border:0px"/>
										<h:outputText value="Eliminar"></h:outputText>
									</a4j:commandLink>
									<h:outputText value=" una configuración hacer click en el Registro" style="color:#38749C"/>
						   		 </h:panelGroup>
					   		 </h:panelGroup>  
				       </f:facet>
		            </rich:extendedDataTable>
           		</h:panelGroup>
           </h:panelGroup>
        </h:panelGroup>
   	</h:panelGroup>
</h:form>