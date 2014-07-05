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
		actionListener="#{autorizacionPrestamoController.setSelectedExpedienteCredito}">
		<f:param name="rowExpedienteCredito" />
	</a4j:jsFunction>
</a4j:region>

<rich:panel style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">

	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>AUTORIZACIÓN DE SOLICITUD DE PRÉSTAMO</b>
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
				<h:selectOneMenu value="#{autorizacionPrestamoController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{autorizacionPrestamoController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{autorizacionPrestamoController.strBusqNroSol}" />
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
				<h:selectOneMenu style="width:150px" value="#{autorizacionPrestamoController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionPrestamoController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100px">
				<h:outputText value="Tipo Préstamo :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column width="150px">
				<h:selectOneMenu value="#{autorizacionPrestamoController.intBusqTipoCreditoEmpresa}" style="width:120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{autorizacionPrestamoController.listaTablaCreditoEmpresa}"
						 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{autorizacionPrestamoController.intBusqEstado}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionPrestamoController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionPrestamoController.dtBusqFechaEstadoHasta}"
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
				<a4j:commandButton value="Buscar" actionListener="#{autorizacionPrestamoController.listarAutorizacionPrestamo}" styleClass="btnEstilos" reRender="pgListAutorizacionCredito"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{autorizacionPrestamoController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,pgListAutorizacionCredito"/>
			</rich:column> 
		</h:panelGrid>
		
            <rich:spacer height="12px"/>  
            
	<h:panelGrid id="pgListAutorizacionCredito" border="0">
		<rich:extendedDataTable id="edtAutorizacionCredito" rows="5"
				value="#{autorizacionPrestamoController.listaExpedienteCreditoComp}"
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
							<h:outputText value="Sucursal que procesó Crédito" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Estado de Solicitud" />
						</rich:column>
						<rich:column colspan="3">
							<h:outputText value="Fecha" />
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
					<tumih:outputText value="#{autorizacionPrestamoController.listSucursal}" 
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
					<rich:datascroller for="edtAutorizacionCredito" maxPages="10" />
				</f:facet>
				<a4j:support event="onRowClick"  
					actionListener="#{autorizacionPrestamoController.seleccionarRegistro}" reRender="panelUpdateDeleteAutorizacionPrestamo,frmAutorizacionPrestamoModalPanel">
                       	<f:attribute name="itemExpCreditoAut" value="#{item}"/>
                 </a4j:support>
			</rich:extendedDataTable>
		</h:panelGrid>
			

		<h:panelGrid columns="2">
			<h:outputLink value="#" id="linkAutorizacionCredito">
				<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
				<rich:componentControl for="panelUpdateDeleteAutorizacionPrestamo"
					attachTo="linkAutorizacionCredito" operation="show" event="onclick" />
			</h:outputLink>
			<h:outputText value="Para Autorizar o Ver una SOLICITUD DE PRESTAMO hacer click en el Registro" style="color:#8ca0bd" />
		</h:panelGrid>
	</rich:panel>


	<h:panelGrid id="pgControls" columns="2">
        <a4j:commandButton value="Grabar" actionListener="#{autorizacionPrestamoController.grabarAutorizacionPrestamo}" styleClass="btnEstilos" 
        		reRender="pgAutoricCredito,pgBusq1, pgBusq2, pgBusq3"/>
		<a4j:commandButton value="Cancelar" actionListener="#{autorizacionPrestamoController.cancelarGrabarAutorizacionPrestamo}"
			styleClass="btnEstilos" reRender="pgAutoricCredito,pgBusq1, pgBusq2, pgBusq3" />
	</h:panelGrid>
    
    <h:panelGrid id="pgAutoricCredito">
   		<a4j:include viewId="/pages/creditos/AutorizacionCredito/autorizacionCreditoEdit.jsp"/>
    </h:panelGrid>
</rich:panel>