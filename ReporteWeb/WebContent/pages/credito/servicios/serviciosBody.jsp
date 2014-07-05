<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
<!-- Empresa   : Cooperativa El Tumi      -->
<!-- Autor     : Christian De los Ríos	  -->
<!-- MODULO    : REPORTE                  -->			
<!-- FECHA     : 06/01/2014               -->

<rich:modalPanel id="mpDetalleCaptacionesEjecutadas" width="990" height="500" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGroup>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="Reporte de Socios - Préstamos"/>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="#{prestamoController.strFmtPeriodo}"/>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:columnGroup>
					<rich:column style="text-align: center">
						<h:outputText value="Sucursal:"/>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{prestamoController.strSucursal}"></h:outputText>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
		</h:panelGroup>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mpDetalleCaptacionesEjecutadas" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet> 
		<rich:dataTable id="dtDetallePrestamos" sortMode="single" 
				var="itemGrilla" value="#{prestamoController.listaDetallePrestamos}" 
                rendered="#{not empty prestamoController.listaDetallePrestamos}" 
                rowKeyVar="rowKey" width="970px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column width="30px" style="text-align: center">
							<h:outputText value="Nro." />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Cuenta" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Apellidos y Nombres" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Fecha de Solicitud" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Fecha de Egreso" />
						</rich:column>
						<rich:column width="90px" style="text-align: center">
							<h:outputText value="Monto de Préstamo" />
						</rich:column>
						<rich:column width="180px" style="text-align: center">
							<h:outputText value="Unidad Ejecutora" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Sucursal" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column width="30px" style="text-align: center">
					<h:outputText style="text-align: center" value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNroCuenta}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<h:outputText id="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
					<rich:toolTip for="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaSolicitud}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaEgreso}"/>
				</rich:column>
				<rich:column width="90px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="180px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strUnidadEjecutora}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<tumih:outputText value="#{prestamoController.listJuridicaSucursal}" 
					itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
					property="#{itemGrilla.intIdSucursal}"/>
				</rich:column>
				
		</rich:dataTable>
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<h:outputText value="#{prestamoController.limpiarPrestamo}"/>
<h:outputText value="#{saldoServicioController.limpiarSaldoServicio}"/>
<h:outputText value="#{primerDescuentoController.limpiarPrimerDescuento}"/>

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{prestamoController.getListSubSucursal}">
		<f:param name="pCboSucursal"/>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalSaldoServ" reRender="cboSubSucursalSaldo"
		action="#{saldoServicioController.getListSubSucursal}">
		<f:param name="pCboSucursal"/>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalPrimerDscto" reRender="cboSubSucursalPrimerDscto"
		action="#{primerDescuentoController.getListSubSucursal}">
		<f:param name="pCboSucursal"/>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbPeriodo" reRender="cboMesDesde, cboMesHasta, cboAnioBusq, cboRangoPeriodo, rbOpcA, rbOpcB"
		action="#{prestamoController.disabledCboPeriodo}">
		<f:param name="pRbPeriodo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getTipoCreditoEmpresa" reRender="cboCreditoTipoEmpresa"
		action="#{saldoServicioController.reloadCboTipoCreditoEmpresa}">
		<f:param name="pCboTipoServicio"/>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getTipoPrestamo" reRender="cboSubTipoPrestamo"
		action="#{primerDescuentoController.reloadCboTipoPrestamo}">
		<f:param name="pCboTipoPrestamo"/>
	</a4j:jsFunction>
</a4j:region>

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Préstamos" disabled="#{!primerDescuentoController.poseePermiso}">
		<a4j:include viewId="/pages/credito/servicios/tabPrestamos/prestamos.jsp"/>
	</rich:tab>
	<rich:tab label="Saldo de Servicios" disabled="#{!primerDescuentoController.poseePermiso}">
		<a4j:include viewId="/pages/credito/servicios/tabSaldoServicios/saldoServicios.jsp"/>
	</rich:tab>
	<rich:tab label="Análisis Primer Descuento" disabled="#{!primerDescuentoController.poseePermiso}">
		<a4j:include viewId="/pages/credito/servicios/tabPrimerDscto/primerDescuento.jsp"/>
		
	</rich:tab>
</rich:tabPanel>