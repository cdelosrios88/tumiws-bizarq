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

<a4j:include viewId="/pages/popup/mpUpdate.jsp"/>
<a4j:include viewId="/pages/operaciones/popup/mpSeleccionCuenta.jsp"/>
<a4j:include viewId="/pages/operaciones/popup/mpSeleccionPersona.jsp"/>
<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>
<a4j:include viewId="/pages/operaciones/popup/mpHojaManualDetalle.jsp"/>
<a4j:include viewId="/pages/mensajes/confirmDialogBody.jsp"/>

<a4j:region>
	<a4j:jsFunction name="updateRow" 
		actionListener="#{hojaManualController.getHojaManual}">
		<f:param name="rowHojaManual"></f:param>
	</a4j:jsFunction>

	<a4j:jsFunction name="selecHojaManualBusq" 
		actionListener="#{hojaManualController.setSelectedHojaManual}">
		<f:param name="rowHojaManualBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecHojaManualDetalle" 
		actionListener="#{hojaManualController.setSelectedHojaManualDet}">
		<f:param name="rowHojaManualDetalle"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="eliminarHojaManualDetalle" reRender="divTblHojaManualDetalle"
		actionListener="#{hojaManualController.deleteHojaManualDetalle}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecSucursal" reRender="cboSubsucursalHMDe"
		actionListener="#{hojaManualController.loadListSubsucursal}">
		<f:param name="pIdSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="changeSucursal" reRender="cboSubsucursalHMDe"
		actionListener="#{hojaManualController.reloadListSubsucursal}">
		<f:param name="pIdSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="changeTipoMoneda" reRender="txtTipoCambio,txtMontoSoles,txtMonedaExtranjera"
		actionListener="#{hojaManualController.renderPorTipoMoneda}">
		<f:param name="pIdTipoMoneda"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoCuenta" reRender="txtCuentaBusq"
		actionListener="#{hojaManualController.disableTxtCuentaBusq}">
		<f:param name="pCboTipoCuentaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarCuenta" reRender="txtNumeroCuenta"
		actionListener="#{hojaManualController.seleccionarCuenta}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionCuenta')">
		<f:param name="pRowCuentaContable"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="searchCuentaContable" 
		actionListener="#{hojaManualController.searchCuentaContable}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="searchPersona" 
		actionListener="#{hojaManualController.searchPersona}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoPersona" reRender="cboFiltroPersona"
		actionListener="#{hojaManualController.reloadCboFiltroPersona}">
		<f:param name="pTipoPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersona" reRender="txtPersonaBusq"
		actionListener="#{hojaManualController.disableTxtPersonaBusq}">
		<f:param name="pFiltroPersonaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarPersona" reRender="txtFullNamePersona"
		actionListener="#{hojaManualController.seleccionarPersona}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionPersona')">
		<f:param name="pRowPersona"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmHojaManual">
   	<h:panelGroup id="divHojaManual" layout="block" style="padding:15px">
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="NOTAS CONTABLES" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="Rango de fecha"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<rich:calendar value="#{hojaManualController.hojaManualDetBusq.hojaManual.tsFechaRegistroDesde}" 
    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
    			</rich:column>
    			<rich:column>
    				<rich:calendar value="#{hojaManualController.hojaManualDetBusq.hojaManual.tsFechaRegistroHasta}" 
    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Código"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{hojaManualController.hojaManualDetBusq.hojaManual.id.intContCodigoHoja}"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Cuenta Contable"></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{hojaManualController.hojaManualDetBusq.strContNumeroCuenta}"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton actionListener="#{hojaManualController.searchHojaManual}" value="Buscar" 
    								reRender="divTblHojaManual" styleClass="btnEstilos"></a4j:commandButton>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGroup id="divTblHojaManual" layout="block" style="padding:15px">
    		<rich:extendedDataTable id="tblHojaManual" enableContextMenu="false" style="margin:0 auto"
    					value="#{hojaManualController.listHojaManual}" rendered="#{not empty hojaManualController.listHojaManual}"
                        var="item" onRowClick="selecHojaManualBusq('#{rowKey}')"
		  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="653px" height="205px">
		                           
				 	<rich:column width="31">
		            	<h:outputText value="#{rowKey + 1}"></h:outputText>
		            </rich:column>
		                    
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Período"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.intContPeriodoHoja}"></h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Código"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.id.intContCodigoHoja}"></h:outputText>
		            </rich:column>  
		            <rich:column>
		            	<f:facet name="header">
                            <h:outputText value="Fecha"></h:outputText>
                        </f:facet>
		            	<h:outputText value="#{item.tsHomaFechaRegistro}">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						</h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Glosa"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.strHomaGlosa}"></h:outputText>
		            </rich:column>
		            <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Monto"></h:outputText>
                        </f:facet>
						<h:outputText value="#{item.bdMonto}"></h:outputText>
		            </rich:column>
		                    
			   <f:facet name="footer">
			   		 <h:panelGroup layout="block">
				   		 <rich:datascroller for="tblHojaManual" maxPages="10"/>
				   		 <h:panelGroup layout="block" style="margin:0 auto; width:420px">
				   		 	<h:outputText value="Para " style="color:#38749C"></h:outputText>
				   		 	<a4j:commandLink action="#" actionListener="#{hojaManualController.getHojaManual}" reRender="divFormHojaManual">
								<h:graphicImage value="/images/icons/icon_edit.png" style="border:0px"/>
								<h:outputText value="Modificar"></h:outputText>
							</a4j:commandLink>
							<h:outputText value=" una NOTA CONTABLE hacer click en el registro" style="color:#38749C"/>
				   		 </h:panelGroup>
			   		 </h:panelGroup>    
		       </f:facet>
            </rich:extendedDataTable>
    	</h:panelGroup>
           
       	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
           
           <h:panelGrid columns="3">
           		<rich:column>
           			<a4j:commandButton value="Nuevo" actionListener="#{hojaManualController.newHojaManual}" 
           							reRender="divFormHojaManual" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Grabar" actionListener="#{hojaManualController.saveHojaManual}" 
           							reRender="divTblHojaManual,divFormHojaManual,mpMessage" styleClass="btnEstilos"
           							oncomplete="if(#{hojaManualController.isValidHojaManual==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton value="Cancelar" actionListener="#{hojaManualController.cancelNew}" 
           							reRender="divFormHojaManual" styleClass="btnEstilos"></a4j:commandButton>
           		</rich:column>
           </h:panelGrid>
           
        <h:panelGroup id="divFormHojaManual" layout="block">
           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px"
           				rendered="#{hojaManualController.blnShowDivFormHojaManual}">
           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
           			<rich:columnGroup>
           				<rich:column>
	           				<h:outputText value="Fecha"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<rich:calendar value="#{hojaManualController.hojaManual.tsHomaFechaRegistro}" 
    							datePattern="dd/MM/yyyy" converter="calendarTimestampConverter" style="width:76px"></rich:calendar>
	           			</rich:column>
	           			<rich:column>
	           				<h:outputText value="Código"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:inputText value="#{hojaManualController.hojaManual.id.intContCodigoHoja}" disabled="true"></h:inputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:outputText value="Asiento Contable"></h:outputText>
	           			</rich:column>
	           			<rich:column>
	           				<h:inputText value="#{hojaManualController.hojaManual.libroDiario.id.intContPeriodoLibro} - #{hojaManualController.hojaManual.libroDiario.id.intContCodigoLibro}" disabled="true"></h:inputText>
	           			</rich:column>
           			</rich:columnGroup>
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Glosa"></h:outputText>
           				</rich:column>
           				<rich:column colspan="5">
           					<h:inputText value="#{hojaManualController.hojaManual.strHomaGlosa}" size="105"></h:inputText>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           		
           		<h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px">
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Cuenta contable"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<a4j:commandButton value="Agregar" styleClass="btnEstilos1" actionListener="#{hojaManualController.newHojaManualDetalle}"
           									oncomplete="if(#{hojaManualController.hojaManual.tsHomaFechaRegistro==null || hojaManualController.hojaManual.strHomaGlosa==null}){
                										Richfaces.showModalPanel('mpMessage')}else{Richfaces.showModalPanel('mpHojaManualDetalle')}" reRender="divHojaManualDetalle,mpMessage"></a4j:commandButton>
           				</rich:column>
           			</rich:columnGroup>
           		</h:panelGrid>
           		
           		<rich:spacer height="10px"></rich:spacer>
           		<h:panelGroup id="divTblHojaManualDetalle" layout="block">
           			<rich:extendedDataTable id="tblHojaManualDetalle" enableContextMenu="false" style="margin:0 auto"
           						rendered="#{not empty hojaManualController.hojaManual.listHojaManualDetalle}"
		                        var="item" value="#{hojaManualController.hojaManual.listHojaManualDetalle}" onRowClick="selecHojaManualDetalle('#{rowKey}')"
				  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="930px" height="205px">
				    		
				   			<rich:column width="31" filterExpression="#{item.isDeleted==null || !item.isDeleted}">
				                <h:outputText value="#{rowKey + 1}"></h:outputText>
				            </rich:column>
				                    
				            <rich:column width="60">
		                        <f:facet name="header">
		                            <h:outputText value="Período"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.id.intContPeriodoHoja}"></h:outputText>
				            </rich:column> 
				            <rich:column width="100">
		                        <f:facet name="header">
		                            <h:outputText value="Cta. Contable"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.planCuenta.id.strNumeroCuenta}"></h:outputText>
				            </rich:column>
				            <rich:column id="colDescripcionCuenta" width="200">
		                        <f:facet name="header">
		                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.planCuenta.strDescripcion}"></h:outputText>
								<rich:toolTip for="colDescripcionCuenta" value="#{item.planCuenta.strDescripcion}"></rich:toolTip>
				            </rich:column>
				            <rich:column id="colNombresPersona" width="220">
		                        <f:facet name="header">
		                            <h:outputText value="Persona" style="font-weight:bold"></h:outputText>
		                        </f:facet>
		                        <h:outputText value="#{item.persona.intIdPersona} - "></h:outputText>
								<h:outputText value="#{item.persona.natural.strNombres} "></h:outputText>
								<h:outputText value="#{item.persona.natural.strApellidoPaterno} "></h:outputText>
								<h:outputText value="#{item.persona.natural.strApellidoMaterno} - "></h:outputText>
								<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"></h:outputText>
								<rich:toolTip for="colNombresPersona" 
											value="#{item.persona.intIdPersona} - 
													#{item.persona.natural.strNombres} 
													#{item.persona.natural.strApellidoPaterno} 
													#{item.persona.natural.strApellidoMaterno} - 
													#{item.persona.documento.strNumeroIdentidad}"></rich:toolTip>
				            </rich:column>
				            <rich:column id="colDocumento">
		                        <f:facet name="header">
		                            <h:outputText value="Documento" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.strHmdeSerieDocumento} - "></h:outputText>
								<h:outputText value="#{item.strHmdeNumeroDocumento}"></h:outputText>
								<rich:toolTip for="colDocumento" value="#{item.strHmdeSerieDocumento} - #{item.strHmdeNumeroDocumento}"></rich:toolTip>
				            </rich:column>
				            <rich:column width="60">
		                        <f:facet name="header">
		                            <h:outputText value="Moneda" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
												itemValue="intIdDetalle" itemLabel="strDescripcion" 
												property="#{item.intParaMonedaDocumento}"/>
				            </rich:column>
				            <rich:column width="80">
		                        <f:facet name="header">
		                            <h:outputText value="Debe" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.bdHmdeDebeSoles}"></h:outputText>
				            </rich:column>
				            <rich:column width="80">
		                        <f:facet name="header">
		                            <h:outputText value="Haber" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.bdHmdeHaberSoles}"></h:outputText>
				            </rich:column>
				                    
					   <f:facet name="footer">
					   		 <h:panelGroup layout="block">
						   		 <rich:datascroller for="tblHojaManualDetalle" maxPages="10"/>
						   		 <h:panelGroup layout="block" style="margin:0 auto; width:420px">
						   		 	<h:outputText value="Para " style="color:#38749C"></h:outputText>
						   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpHojaManualDetalle')"
						   		 					actionListener="#{hojaManualController.getHojaManualDetalle}" reRender="mpHojaManualDetalle">
										<h:graphicImage value="/images/icons/icon_edit.png" style="border:0px"/>
										<h:outputText value="Modificar"></h:outputText>
									</a4j:commandLink>
									<h:outputText value=" o " style="color:#38749C"></h:outputText>
									<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpConfirmMessage')"
													actionListener="#{hojaManualController.confirmDeleteHojaManualDet}" reRender="mpConfirmMessage">
										<h:graphicImage value="/images/icons/icon_trash.gif" style="border:0px"/>
										<h:outputText value="Eliminar"></h:outputText>
									</a4j:commandLink>
									<h:outputText value=" una configuración hacer click en el registro" style="color:#38749C"/>
						   		 </h:panelGroup>
					   		 </h:panelGroup>  
				       </f:facet>
		            </rich:extendedDataTable>
           		</h:panelGroup>
           </h:panelGroup>
        </h:panelGroup>
   	</h:panelGroup>
</h:form>