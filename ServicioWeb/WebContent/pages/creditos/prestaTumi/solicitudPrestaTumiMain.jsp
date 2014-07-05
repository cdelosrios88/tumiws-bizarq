<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         			-->
<!-- Modulo    : Servicio    						-->
<!-- Prototipo : Solicitud Presta Tumi - Main		-->
<!-- Fecha     : 30/11/2013               			-->

<rich:panel style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">

	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>INGRESO DE SOLICITUD ESPECIAL DE PRÉSTAMO</b>
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
				<h:selectOneMenu value="#{solicitudEspecialController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{solicitudEspecialController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.strBusqNroSol}" />
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
				<h:selectOneMenu style="width:150px" value="#{solicitudEspecialController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudEspecialController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100px">
				<h:outputText value="Tipo Préstamo :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column width="150px">
				<h:selectOneMenu value="#{solicitudEspecialController.intBusqTipoCreditoEmpresa}" style="width:120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{solicitudEspecialController.listaTablaCreditoEmpresa}"
						 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudEspecialController.intBusqEstado}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudEspecialController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudEspecialController.dtBusqFechaEstadoHasta}"
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
				<a4j:commandButton value="Buscar" actionListener="#{solicitudEspecialController.buscarSolicitudPrestamoEspecial}" styleClass="btnEstilos" reRender="pgListSolicitudCreditoEspecial,edtSolicitudCreditoEspecial"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{solicitudEspecialController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,pgListSolicitudCreditoEspecial,edtSolicitudCreditoEspecial"/>
			</rich:column> 
		</h:panelGrid>
		
		<h:panelGrid id="pgListSolicitudCreditoEspecial" border="0">
			<rich:extendedDataTable id="edtSolicitudCreditoEspecial" rows="5"
				value="#{solicitudEspecialController.listaExpedienteCreditoComp}"
				enableContextMenu="false" var="item" rowKeyVar="rowKey"
				width="970px" height="195px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column rowspan="2" width="15px" />
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Nro. de Cuenta" />
						</rich:column>
						<rich:column rowspan="2" width="150px">
							<h:outputText value="Nombre Completo" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
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
				<rich:column width="80px">
						<h:outputText value="#{item.expedienteCredito.id.intItemExpediente} - #{item.expedienteCredito.id.intItemDetExpediente}" />
				</rich:column>
				<rich:column width="80px">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteCredito.intParaTipoCreditoEmpresa}"/>
                </rich:column>
                <rich:column width="80px">
					<h:outputText value="#{item.expedienteCredito.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column>
					<tumih:outputText value="#{solicitudEspecialController.listSucursal}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{item.expedienteCredito.intSucursalEstadoInicial}"
                                      style="title:que procesó el crédito"/>
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

				<f:facet name="footer">
					<rich:datascroller for="edtSolicitudCreditoEspecial" maxPages="10" />
				</f:facet>
				<a4j:support event="onRowClick"  
					actionListener="#{solicitudEspecialController.seleccionarRegistro}" reRender="panelAccionesSolicitudPrestamoEspecial,frmAccionesSolicitudPrestamoEspecial">
                       	<f:attribute name="itemExpCredito" value="#{item}"/>
                 </a4j:support>
			</rich:extendedDataTable>
		</h:panelGrid>

		<h:panelGrid columns="2">
			<h:outputLink value="#" id="linkSolicitudCreditoEspecial">
				<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
				<rich:componentControl for="panelAccionesSolicitudPrestamoEspecial"
					attachTo="linkSolicitudCreditoEspecial" operation="show" event="onclick" />
			</h:outputLink>
			<h:outputText value="Para Ver, Editar, Anular o Imprimir Formatos una SOLICITUD DE PRESTAMO hacer click en el Registro" style="color:#8ca0bd" />
		</h:panelGrid>
	</rich:panel>

	<h:panelGrid id="pgControlsEspecial" columns="3">
		<a4j:commandButton value="Nuevo" actionListener="#{solicitudEspecialController.nuevoExpediente}" styleClass="btnEstilos" reRender="pgControls,pgSolicCreditoEspecial,pgMsgErrorGrabar,pgControlsEspecial" />
		
		<h:panelGroup rendered="#{solicitudEspecialController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
           	<a4j:commandButton value="Grabar" actionListener="#{solicitudEspecialController.grabarSolicitudPrestamo}" styleClass="btnEstilos" reRender="pgSolicCreditoEspecial,pgMsgErrorGrabar,pgControlsEspecial"/>
        </h:panelGroup>
        <h:panelGroup rendered="#{solicitudEspecialController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
           	<a4j:commandButton value="Grabar" actionListener="#{solicitudEspecialController.modificarSolicitudPrestamo}" styleClass="btnEstilos" reRender="pgSolicCreditoEspecial,pgMsgErrorGrabar,pgControlsEspecial"/>
        </h:panelGroup>
		
		<a4j:commandButton value="Cancelar" actionListener="#{solicitudEspecialController.cancelarGrabarSolicitudPrestamo}"
			styleClass="btnEstilos" reRender="pgControlsEspecial,pgSolicCreditoEspecial" />
	</h:panelGrid>
    
    <h:panelGrid id="pgSolicCreditoEspecial">
	    <h:panelGrid id="pgMsgErrorGrabar">
			
			<h:outputText value="#{solicitudEspecialController.strErrorGrabar}" styleClass="msgError"/>
		</h:panelGrid>
		<h:panelGroup rendered="#{solicitudEspecialController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
				 				solicitudEspecialController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
   	 		<a4j:include viewId="/pages/creditos/prestaTumi/solicitudPrestaTumiEdit.jsp"/>
   	 	</h:panelGroup>
   	 	<h:panelGroup rendered="#{solicitudEspecialController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
	 		<a4j:include viewId="/pages/creditos/prestaTumi/solicitudPrestaTumiView.jsp"/>
	 	</h:panelGroup>
    </h:panelGrid>
    
</rich:panel>



