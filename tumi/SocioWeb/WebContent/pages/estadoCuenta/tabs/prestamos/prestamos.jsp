<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PRESTAMOS -->			
<!-- FECHA     : 30.09.2013               -->
<h:panelGroup layout="block" style="padding:15px">
	<h:panelGrid id="formTabPrestamosFiltros">
		<rich:columnGroup>
			<rich:column width="1150px" style="text-align: center">
				<table>
					<tr>
						<td width="300px" align="center">
							<b><h:outputText value="Tipo y Estado de Préstamos y Créditos" /></b>
						</td>
						<td width="120px" align="center">
							<h:selectOneMenu id="idTipoCreditoBusqueda" value="#{estadoCuentaController.intTipoCreditoBusqueda}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
						</td>
						<td width="120px" align="center">
							<h:selectOneMenu id="idEstadoEstCta" value="#{estadoCuentaController.intEstadoEstCtaBusqueda}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONSULTAESTCTA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
						</td>
						<td width="100px" align="center">
							<a4j:commandButton id="btnConsultar2" value="Consultar" styleClass="btnEstilos1" action="#{estadoCuentaController.listarPrestamosSocio}"
							reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formDetallePrestamos,formDetalleGarantiaCredito"/>
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>
	
	<rich:spacer height="10"></rich:spacer>
	
	<h:panelGrid id="formDetallePrestamos" rendered="#{estadoCuentaController.blnShowPanelTabPrestamosResumePrestamos}">
		<rich:columnGroup>
			<rich:column width="1170px" style="text-align: left">
				<rich:dataTable value="#{estadoCuentaController.listaExpedienteComp}" 
						styleClass="dataTable1" id="dtPrestamos"
						rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: left"
						var="itemGrilla">
					<f:facet name="header">
						<rich:columnGroup  columnClasses="rich-sdt-header-cell">
							<rich:column width="100px" rowspan="2">
								<b><h:outputText value="Fecha Solicitud" /></b>
							</rich:column>
							<rich:column width="250px" rowspan="2">
								<b><h:outputText value="Conceptos" /></b>
							</rich:column>
							<rich:column width="100px" rowspan="2">
								<b><h:outputText value="Solicitud" /></b>
							</rich:column>
							<rich:column width="50px" rowspan="2">
								<b><h:outputText value="Tasa" /></b>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<b><h:outputText value="Monto" /></b>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<b><h:outputText value="CT/CP/CA" /></b>
							</rich:column>
							<rich:column width="100px" rowspan="2">
								<b><h:outputText value="Cuota Mensual" /></b>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<b><h:outputText value="Saldo" /></b>
							</rich:column>
							<rich:column width="110px" rowspan="2">
								<b><h:outputText value="Categoria Cartera" /></b>
							</rich:column>
							<rich:column width="160px" colspan="2">
								<b><h:outputText value="Giro" /></b>
							</rich:column>
							<rich:column width="40px" rowspan="2">
								<b><h:outputText value="Estado" /></b>
							</rich:column>
							<rich:column width="80px" breakBefore="true" style="text-align: center">
								<h:outputText value="Fecha" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="Documento" />
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					<rich:column width="100px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strFechaSolicitud}" />
					</rich:column>
					<rich:column width="250px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strDescripcionExpediente}" />
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strNroSolicitud}" />
					</rich:column>
					<rich:column width="50px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.expediente.bdPorcentajeInteres}">
						<f:converter converterId="ConvertidorMontos"  /></h:outputText>
					</rich:column>
					<rich:column width="80px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.expediente.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"  /></h:outputText>						
					</rich:column>
					<rich:column width="80px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strCuotas}" />
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.bdCuotaMensual}">
						<f:converter converterId="ConvertidorMontos"  /></h:outputText>
					</rich:column>
					<rich:column width="80px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.expediente.bdSaldoCredito}">
						<f:converter converterId="ConvertidorMontos"  /></h:outputText>
					</rich:column>
					<rich:column width="110px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strCategoriaCartera}" />
					</rich:column>
					<rich:column width="80px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strFechaGiro}" />
					</rich:column>
					<rich:column width="80px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strDocumentoGiro}" />
					</rich:column>
					<rich:column width="40px" style="text-align: center">
						<h:outputText style="text-align: center" value="#{itemGrilla.strEstadoTipoCredito}" />
					</rich:column>	
				</rich:dataTable>
			</rich:column>
		</rich:columnGroup>
		<!-- SUMATORIA DE SALDOS -->
		<rich:columnGroup>
			<rich:column width="1170px" style="text-align: center">
				<table>
					<tr>
						<td width="730px" align="left">
							<b>CP:</b> Cuentas pagadas <b>CT:</b> Cuentas totales <b>CA:</b> Cuentas atrasadas
						</td>			
						<td width="80px" align="center">
							<b><h:outputText value="#{estadoCuentaController.bdSumatoriaSaldoPrestamo}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText></b>
						</td>
						<td width="310px" align="center">
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>		
	</h:panelGrid>
</h:panelGroup>