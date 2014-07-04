<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Modulo    : Nueva Solicitud de Crédito     -->
<!-- Prototipo : Empresas			      		-->
<!-- Fecha     : 10/09/2012               		-->

<a4j:region>
	<a4j:jsFunction name="selecAutorizacionPrestamo"
		actionListener="#{autorizacionRefinanController.setSelectedExpedienteCredito}">
		<f:param name="rowExpedienteCredito" />
	</a4j:jsFunction>
</a4j:region>

<rich:panel
	style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>AUTORIZACIÓN DE SOLICITUD DE REFINANCIAMIENTO O REPROGRAMACIÓN</b>
		</div>
		<h:panelGrid id="pgBusq0" columns="1" border="0">
			<rich:column>
			</rich:column>
		</h:panelGrid>
		<h:panelGrid id="pgBusq1" columns="7" border="0">
			<rich:column width="120px">
				<h:outputText value="Datos de Socio" styleClass="estiloLetra1" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{autorizacionRefinanController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{autorizacionRefinanController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{autorizacionRefinanController.strBusqNroSol}" />
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pgBusq2" columns="9" border="0">
			<rich:column width="120px">
				<h:outputText value="Sucursal Admin." styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:150px" value="#{autorizacionRefinanController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionRefinanController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100px">
				<h:outputText value="Tipo Préstamo :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column width="150px">
				<h:selectOneMenu value="#{autorizacionRefinanController.intBusqTipoCreditoEmpresa}" style="width:120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{autorizacionRefinanController.listaTablaCreditoEmpresa}"
						 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{autorizacionRefinanController.intBusqEstado}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionRefinanController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionRefinanController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq3" columns="11" border="0">
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="100px">
			</rich:column>
			<rich:column width="150px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{autorizacionRefinanController.listarExpedientesRefinanciamiento}" styleClass="btnEstilos" reRender="pgListAutorizacionCreditoRef"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{autorizacionRefinanController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,pgListAutorizacionCreditoRef"/>
			</rich:column> 
		</h:panelGrid>
		
		<rich:spacer height="4px"/><rich:spacer height="8px"/>
		
		<h:panelGrid id="pgListAutorizacionCreditoRef" border="0">
			<rich:extendedDataTable id="edtAutorizacionCreditoRefinan" rows="5"
				value="#{autorizacionRefinanController.listaAutorizacionCreditoComp}"
				enableContextMenu="false" var="item" rowKeyVar="rowKey"
				onRowClick="selecAutorizacionRefinan('#{rowKey}')"
				width="950px" height="200px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column rowspan="2" width="15px" />
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Nro. de Cuenta" />
						</rich:column>
						<rich:column rowspan="2" width="150px">
							<h:outputText value="Nombre Completo" />
						</rich:column>
						<rich:column rowspan="2" width="60px">
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Tipo de Préstamo" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Crédito" />
						</rich:column>
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Sucursal que procesó Crédito"/>
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Estado de Solicitud" />
						</rich:column>
						<rich:column colspan="3">
							<h:outputText value="Fechas" />
						</rich:column>
						<rich:column breakBefore="true">
							<h:outputText value="Requisito" />
						</rich:column>
						<rich:column>
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column>
							<h:outputText value="Autorización" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				
				<rich:column width="15px">
					<div align="center">
						<h:outputText value="#{rowKey+1}" />
					</div>
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.expedienteCredito.strNroCuentaExpediente}"/>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="#{item.expedienteCredito.strNombreCompletoPersona}" title="#{item.expedienteCredito.strNombreCompletoPersona}"/>
				</rich:column>
				<rich:column width="60px">						
						<h:outputText value="#{item.expedienteCredito.id.intItemExpediente}-#{item.expedienteCredito.id.intItemDetExpediente}" 
								rendered="#{!item.blnLinkDetalle}"/>
						<a4j:commandLink value="#{item.expedienteCredito.id.intItemExpediente}-#{item.expedienteCredito.id.intItemDetExpediente}"
							rendered="#{item.blnLinkDetalle}"
							actionListener="#{autorizacionRefinanController.irDetalleRefinanciamientoLink}"
							reRender="mpDetalleRefinanciamiento" 
							oncomplete="Richfaces.showModalPanel('mpDetalleRefinanciamiento')">
						</a4j:commandLink>
						
				</rich:column>
				<rich:column width="80px"> 
					<tumih:outputText value="#{autorizacionRefinanController.listaTablaCreditoEmpresa}" 
                                      itemValue="intIdDetalle" itemLabel="strAbreviatura" 
                                      property="#{item.expedienteCredito.intParaTipoCreditoEmpresa}"/>
                </rich:column>
                <rich:column width="80px">
					<h:outputText value="#{item.expedienteCredito.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column>
					<tumih:outputText value="#{autorizacionRefinanController.listSucursal}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{item.expedienteCredito.intSucursalEstadoInicial}"
                                      style="title='que procesó el crédito'"/>
				</rich:column>
				<rich:column >
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteCredito.intEstadoCreditoUltimo}"/>
				</rich:column>
				<rich:column width="95px">
					<h:outputText value="#{item.strFechaRequisito}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaSolicitud}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaAutorizacion}" />
				</rich:column>
				<a4j:support event="onRowClick"  
					actionListener="#{autorizacionRefinanController.seleccionarRegistro}"
					reRender="pgTipoOperacion">
                       	<f:attribute name="itemRefinan" value="#{item}"/>
                </a4j:support>
                 
				<f:facet name="footer">
					<rich:datascroller for="edtAutorizacionCreditoRefinan" maxPages="10" />
				</f:facet>
			</rich:extendedDataTable>
		</h:panelGrid>

		<h:panelGrid columns="2">
			<h:outputLink value="#" id="linkAutorizacionRefinan">
				<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
				<rich:componentControl for="panelUpdateDeleteAutorizacionRefinan"
					attachTo="linkAutorizacionRefinan" operation="show" event="onclick" />
			</h:outputLink>
			<h:outputText value="Para Autorizar una SOLICITUD DE REFINANCIAMIENTO / REPROGRAMACION hacer click en el Registro" style="color:#8ca0bd" />
		</h:panelGrid>
	</rich:panel>

	<h:panelGroup id="panelMensajeRefinanciamiento" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
		styleClass="rich-tabcell-noborder">
		<h:outputText value="#{autorizacionRefinanController.mensajeOperacion}" 
			styleClass="msgInfo"
			style="font-weight:bold"
			rendered="#{autorizacionRefinanController.mostrarMensajeExito}"/>
		<h:outputText value="#{autorizacionRefinanController.mensajeOperacion}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{autorizacionRefinanController.mostrarMensajeError}"/>	
	</h:panelGroup>
		
	<h:panelGrid id="pgControls" columns="2">
        <a4j:commandButton value="Grabar" actionListener="#{autorizacionRefinanController.grabarAutorizacionRefinan}" styleClass="btnEstilos" reRender="pgAutoricRefinan, pgListAutorizacionCreditoRef,panelMensajeRefinanciamiento"/>
		<a4j:commandButton value="Cancelar" actionListener="#{autorizacionRefinanController.cancelarGrabarAutorizacionRefinan}"
			styleClass="btnEstilos" reRender="pgAutoricRefinan,panelMensajeRefinanciamiento " />
	</h:panelGrid>
    
    <h:panelGrid id="pgAutoricRefinan">
   		<a4j:include viewId="/pages/reprogRefinan/Autorizacion/autorizacionRefinanEdit.jsp"/>
    </h:panelGrid>
</rich:panel>