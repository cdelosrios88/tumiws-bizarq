<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@page import="java.math.BigDecimal" %>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SEGURIDAD                -->
<!-- PROTOTIPO : ESTADO DE CUENTA CORRIENTE - SEGURIDAD -->			
<!-- FECHA     : 21.11.2013               -->
<a4j:region>
	<a4j:jsFunction name="selectedSocioNatural"	actionListener="#{estadoCuentaController.setSelectedSocioNatural}">
		<f:param name="rowSocioNatural"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoBusqueda" reRender="strIdPersonaBusqueda"
		action="#{estadoCuentaController.cleanTipoBusqueda}">
		<f:param name="pCboTipoBusqueda"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; " id="frmPrincipal">
	<h:outputLabel value="#{estadoCuentaController.inicioPage}"/>
	<rich:panel id="formFiltrosBusqueda" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
		<div align="center">
			<b>ESTADO DE CUENTA CORRIENTE</b>
		</div>
		<!-- FILTROS PARA LA BUSQUEDA -->
		<a4j:outputPanel id="opFiltrosDeBusqueda">
			<h:panelGrid>
				<rich:column width="1170px" style="text-align: center">
					<table>
						<tr>
							<td width="240px" align="center">
								<b><h:outputText value="Socio / Cliente :"/></b>									
							</td>
							<td width="500px" align="left">
								<h:selectOneMenu id="idTipoDeBusqueda" value="#{estadoCuentaController.intTipoBusqueda}" onchange="selecTipoBusqueda(#{applicationScope.Constante.ONCHANGE_VALUE})">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOBUSQUEDA_SOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>	
								<rich:spacer width="2"></rich:spacer>
								<h:inputText id="strIdPersonaBusqueda" value="#{estadoCuentaController.strIdPersonaBusqueda}" style="width:320px;"></h:inputText>
							</td>
							<td width="100px" align="center">
								<b><h:outputText value="Período : "/></b>
							</td>
							<td width="100px" align="center">
								<h:selectOneMenu id="idMesBusq" value="#{estadoCuentaController.intMesBusqueda}">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="center">
								<h:selectOneMenu id="idAnioBusq" value="#{estadoCuentaController.intAnioBusqueda}">
									<f:selectItems id="lstYears" value="#{estadoCuentaController.listYears}"/>
								</h:selectOneMenu>		 
							</td>
							<td width="130px" align="left">
								<h:selectOneMenu id="idTipoCuentaSocioBusq" value="#{estadoCuentaController.intTipoCuentaBusqueda}">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTASOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="center">
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" action="#{estadoCuentaController.consutar}"
								reRender="formFiltrosBusqueda, rpCabecera, moImagenFirma, moImagenFoto"/>
							</td>
							<td width="100px" align="center">
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{estadoCuentaController.limpiarFormulario}" 
								reRender="formFiltrosBusqueda"/>
							</td>
						</tr>
						<tr>
							<td width="200px" align="center">
								<b><h:outputText value="Ctas. del Socio: "/></b>
							</td>
							<td width="500px" align="left">
								<h:selectOneMenu id="idCtasSocBusq" value="#{estadoCuentaController.intCuentasVigentesYLiquidadas}" style="width:200px;">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
										<tumih:selectItems var="sel" value="#{estadoCuentaController.lstDataBeanEstadoCuentaSocioCuenta}"
							 			itemValue="#{sel.intCuenta}" itemLabel="#{sel.strNumeroCuenta} / #{sel.strDescSituacionCuenta}"/>
								</h:selectOneMenu>
							</td>
						</tr>
					</table>
				</rich:column>
			</h:panelGrid>
		</a4j:outputPanel>
		<rich:spacer height="10"></rich:spacer>
		<a4j:outputPanel id="opBusquedaPorApellidosYNombres">
			<h:panelGrid id="pgBusquedaPorApellidosYNombres" rendered="#{estadoCuentaController.busqPorNombre == 1}">
				<rich:extendedDataTable id="edtSocioNatural" enableContextMenu="false" sortMode="single" 
					var="item" value="#{estadoCuentaController.listaSocioBusquedaPorApeNom}"
					rowKeyVar="rowKey" rows="5" width="950px" height="186px"
					onRowClick="selectedSocioNatural('#{rowKey}')">                     
					<rich:column width="29px">
						<h:outputText value="#{rowKey + 1}"></h:outputText>
					</rich:column>
					<rich:column width="55">
						<f:facet name="header">
							<h:outputText  value="Código"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.intIdPersona}"></h:outputText>
					</rich:column>
					<rich:column width="130">
						<f:facet name="header">
							<h:outputText value="Nombre Persona"></h:outputText>
						</f:facet>
						<h:outputText id="lblSocio" value="#{item.strNombreSocio}"/>
						<rich:toolTip for="lblSocio" value="#{item.strNombreSocio}"></rich:toolTip>
					</rich:column>		
					<rich:column width="115">
						<f:facet name="header">
							<h:outputText value="Nro. Documento"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strNumeroIdentidad}"></h:outputText>
					</rich:column>
					<rich:column width="110">
						<f:facet name="header">
							<h:outputText value="Tipo Socio"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strTipoSocio}"/>
					</rich:column>
					<rich:column width="110">
						<f:facet name="header">
							<h:outputText value="Condición"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strCondicionLaboral}"/>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Sucursal"></h:outputText>
						</f:facet>
						<h:outputText id="lblSucursal" value="#{item.strSucursal}"/>
						<rich:toolTip for="lblSucursal" value="#{item.strSucursal}"></rich:toolTip>
					</rich:column>
					<rich:column width="100">
						<f:facet name="header">
							<h:outputText value="Modalidad"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strModalidad}"/>
					</rich:column>
					<rich:column width="100">
						<f:facet name="header">
							<h:outputText value="Tipos Cuenta"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strTipoCuentaSocio}"/>
					</rich:column>
					<rich:column width="100">
						<f:facet name="header">
							<h:outputText value="Fecha Registo"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strFechaRegistro}"/>
					</rich:column>
					<f:facet name="footer">     
						<h:panelGroup layout="block">
							<rich:datascroller for="edtSocioNatural" maxPages="10"/>
							<h:panelGroup layout="block" style="margin:0 auto; width:580px">
								<a4j:commandLink action="#{estadoCuentaController.buscarDatosSocioPorApellidosYNombres}"
									reRender="opBusquedaPorApellidosYNombres,rpCabecera,rpProcesos,idCtasSocBusq">
									<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
								</a4j:commandLink>
								<h:outputText value="Para Ver el Estado De Cuenta de un SOCIO/CLIENTE hacer click en el Registro" style="color:#8ca0bd"/>
							</h:panelGroup>
						</h:panelGroup>  
					</f:facet>
				</rich:extendedDataTable>
			</h:panelGrid>		
		</a4j:outputPanel>

		<rich:panel id="rpCabecera" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
			<a4j:outputPanel id="opCabecera">
				<h:panelGrid id="pgCabecera" rendered="#{estadoCuentaController.blnShowPanelCabecera}">
					<rich:columnGroup>
						<rich:column width="1150px">
							<table>
								<tr>
									<td width="120px" height="34px">
										<b><h:outputText value="Fecha de Emisión:"/></b>
									</td>
									<td width="120px" height="34px">
										<h:outputText value="#{estadoCuentaController.strFechaYHoraActual}"/> 
									</td>
									<td width="110px" height="34px">
									</td>
									<td width="100px" height="34px">
									</td>		
									<td width="202px" height="102px" rowspan="3">
										<a4j:mediaOutput id="moImagenFirma" element="img" mimeType="image/jpeg" rendered="#{not empty estadoCuentaController.fileFirmaSocio}"
											createContent="#{estadoCuentaController.paintImage}" value="#{estadoCuentaController.fileFirmaSocio}"
											style="width:200px; height:100px;" cacheable="false">  
										</a4j:mediaOutput>
									</td>
									<td width="202px" height="102px" rowspan="3" align="center">
										<a4j:mediaOutput id="moImagenFoto" element="img" mimeType="image/jpeg" rendered="#{not empty estadoCuentaController.fileFotoSocio}"
											createContent="#{estadoCuentaController.paintImageFoto}" value="#{estadoCuentaController.fileFotoSocio}"
											style="width:100px; height:100px;" cacheable="false">
										</a4j:mediaOutput>  
									</td>					
								</tr>
								<tr>
									<td width="100px" height="34px">
										<b><h:outputText value="Socio / Cliente :"/></b>
									</td>
									<td width="300px" height="34px">
										<h:outputText value="#{estadoCuentaController.beanSocioEstructura.intIdPersona}"/> - 
										<h:outputText value="#{estadoCuentaController.beanSocioCuenta.strNombreCompletoSocio}"/> -
										<h:outputText value="DNI: #{estadoCuentaController.beanSocioEstructura.strDocIdent}"/>
									</td>
									<td width="110px" height="34px">
									</td>
									<td width="100px" height="34px">
									</td>
								</tr>
								<tr>
									<td width="100px" height="34px">
										<b><h:outputText value="Condición :"/></b>
									</td>
									<td width="200px" height="34px">
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{estadoCuentaController.beanSocioCuenta.intCondicionCuenta}"/> - 
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{estadoCuentaController.beanSocioCuenta.intSubCondicionCuenta}"/>
									</td>
									<td width="110px" height="34px">
										<b><h:outputText value="Apertura Cta :"/></b>
									</td>
									<td width="100px" height="34px">
										<h:outputText value="#{estadoCuentaController.beanSocioCuenta.strFechaApertura}"/>	
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1100px">
							<table>
								<tr>
									<td width="90px">
										<b><h:outputText value="Sucursal :"/></b>
									</td>
									<td width="200px">
				 						<rich:dataList value="#{estadoCuentaController.lstDataBeanEstadoCuentaSocioEstructura}" var="item">
										   <h:panelGrid columns="1">
										   	  <h:outputText value="#{item.strSucursal}"/>
						                  </h:panelGrid>
										</rich:dataList>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<rich:spacer height="6"></rich:spacer>
					<rich:columnGroup>
						<rich:column width="1100px">
							<table>
								<tr>
									<td width="130px">
										<b><h:outputText value="Unidad Ejecutora :"/></b>
									</td>
									<td width="420px">
										<rich:dataList value="#{estadoCuentaController.lstDataBeanEstadoCuentaSocioEstructura}"
																	var="item">
											   <h:panelGrid columns="8">
												  <h:outputText value="#{item.strNombreEstructura} - "/>
												  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{item.intCondicionLaboral}"/>
												  <h:outputText value=" - "/> 
												  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{item.intModalidad}"/>
												  <h:outputText value=" - "/>
												  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{item.intTipoSocio}"/>													  
											  </h:panelGrid>
										</rich:dataList>
									</td>
									<td width="110px">
										<b><h:outputText value="Cta. Aporte :"/></b>
									</td>
									<td width="100px">
										<h:outputText value="#{estadoCuentaController.beanMontosBeneficios.bdCtaAporte}">
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>		
					<rich:spacer height="6"></rich:spacer>
					<rich:columnGroup>
						<rich:column width="1100px">
							<table>
								<tr>
									<td width="100px">
										<b><h:outputText value="Saldo Aporte :"/></b>
									</td>
									<td width="95px">
										<b><h:outputText value="#{estadoCuentaController.beanMontosBeneficios.bdSaldoAporte}">
										<f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="120px">
										<b><h:outputText value="Fondo de Retiro :"/></b>
									</td>
									<td width="95px">
										<h:outputText value="#{estadoCuentaController.beanMontosBeneficios.bdMontoFdoRetiro}">
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
									<td width="100px">
										<b><h:outputText value="Pendiente :"/></b>
									</td>
									<td width="100px">
										<h:outputText value="Fondo Sepelio :"/>
									</td>
									<td width="95px">
										<h:outputText value="#{estadoCuentaController.beanMontosBeneficios.bdMontoFdoSepelio}">
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
									<td width="100px">
										<h:outputText value="Mantenimiento :"/>
									</td>
									<td width="95px">
										<h:outputText value="#{estadoCuentaController.beanMontosBeneficios.bdMontoMantenimiento!=null?estadoCuentaController.beanMontosBeneficios.bdMontoMantenimiento:estadoCuentaController.bdZero}">
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>						
								</tr>
							</table>					
						</rich:column>
					</rich:columnGroup>
					<rich:spacer height="6"></rich:spacer>
					<rich:columnGroup>	
						<rich:column width="1100px">
							<table>
								<tr>
									<td width="100px">
										<b><h:outputText value="Nro. de Cuenta :"/></b>
									</td>
									<td width="120px">
										<b><h:outputText value="#{estadoCuentaController.beanSocioCuenta.strNumeroCuenta}"/></b>
									</td>
								</tr>
							</table>
						</rich:column>		
					</rich:columnGroup>	
				</h:panelGrid>
			</a4j:outputPanel>
		</rich:panel>
		<rich:panel id="rpProcesos" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
			<rich:spacer height="10"></rich:spacer>
			<a4j:outputPanel id="opTabsPanel">
				<h:panelGrid rendered="#{estadoCuentaController.blnShowPanelTabs}">
					<rich:column width="1000px">
						<table>
							<tr>
								<td width="1100px" align="left">
									<a4j:commandButton id="btnResumen" value="Resumen" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabResumen}" 
							 		reRender="rpProcesos"/>
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnDetalle" value="Detalle" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabDetalle}" 
									 reRender="rpProcesos"/>						
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPlanillas" value="Planillas" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPlanilla}" 
									 reRender="rpProcesos"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnTerceros" value="Terceros" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabDsctoTerceros}" 
									 reRender="rpProcesos"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnGestiones" value="Gestiones" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabGestiones}" 
									 reRender="rpProcesos"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPrestamos" value="Préstamos" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPrestamos}" 
									 reRender="rpProcesos"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPrevisionSocial" value="Previsión Social" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPrevisionSocial}" 
									 reRender="rpProcesos"/>
									 <rich:spacer width="5"></rich:spacer>
									<h:commandButton id="btnImprimir" value="Imprimir" styleClass="btnEstilos1" action="#{estadoCuentaController.imprimirReporte}"/>							
								</td>
							</tr>
						</table>				
					</rich:column>
		    	</h:panelGrid>
		    </a4j:outputPanel>
		    <rich:spacer height="10"></rich:spacer>
		    <a4j:outputPanel id="opTabDetalle">
				<h:panelGrid id="pgDetalleEstadoCuenta" rendered="#{estadoCuentaController.blnShowPanelTabDetalle}">
					<!-- NUEVO GRID PARA EL TAB DE DETALLE -->
					<rich:columnGroup>
						<rich:column>
							<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000" width="1220px">
								<tr>
									<td width="110px"  align="center">
										<b><h:outputText value="Fecha" /></b>
									</td>
									<td width="110px"  align="center">
										<b><h:outputText value="Tipo - Nro." /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Aporte" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Préstamos" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Créditos" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Interés" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Mto.Cta." /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Actividad" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Multa" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Sepelio" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Retiro" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Cta. x Pagar" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Cta. x Cobrar" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="Total" /></b>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column style="text-align: center" rendered="#{not empty estadoCuentaController.listaMovimientoAntesDeFechaIngresada}">
							<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000" width="1220px">
								<tr>
									<td width="110px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.strFechaMovimiento}" /></b>
									</td>
									<td width="110px" align="center">
										<b><h:outputText value="-------------" /></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoAporte}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoPrestamo}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoCredito}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoInteres}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoMantenimiento}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoActividad}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoMulta}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoFondoSepelio}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoFondoRetiro}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoCtaPorPagar}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="#{estadoCuentaController.movimientoAnteriorResumen.bdSumaMontoCtaPorCobrar}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>										
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>									
								</tr>
							</table>
						</rich:column>	
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column style="text-align: center" rendered="#{not empty estadoCuentaController.listaMovimientoDespuesDeFechaIngresada}">
							<rich:dataList value="#{estadoCuentaController.lstDetalleMovimiento}"
									var="itemGrilla" >	
								<table width="1220px">
									<tr>
										<td width="110px" align="center">
											<h:outputText value="#{itemGrilla.strFechaMovimiento}" />
										</td>
										<td width="110px" align="center">
											<h:outputText value="#{itemGrilla.strTipoNumero}" />
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoAporte}" rendered="#{itemGrilla.intSignoMontoAporte == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoAporte}" rendered="#{itemGrilla.intSignoMontoAporte == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoPrestamo}" rendered="#{itemGrilla.intSignoMontoPrestamo == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoPrestamo}" rendered="#{itemGrilla.intSignoMontoPrestamo == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>											
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoCredito}" rendered="#{itemGrilla.intSignoMontoCredito == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoCredito}" rendered="#{itemGrilla.intSignoMontoCredito == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>											
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoInteres}" rendered="#{itemGrilla.intSignoMontoInteres == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoInteres}" rendered="#{itemGrilla.intSignoMontoInteres == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>												
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoMantenimiento}" rendered="#{itemGrilla.intSignoMontoMantenimiento == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoMantenimiento}" rendered="#{itemGrilla.intSignoMontoMantenimiento == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>											
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoActividad}" rendered="#{itemGrilla.intSignoMontoActividad == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoActividad}" rendered="#{itemGrilla.intSignoMontoActividad == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>												
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoMulta}" rendered="#{itemGrilla.intSignoMontoMulta == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoMulta}" rendered="#{itemGrilla.intSignoMontoMulta == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>												
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoFondoSepelio}" rendered="#{itemGrilla.intSignoMontoFondoSepelio == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoFondoSepelio}" rendered="#{itemGrilla.intSignoMontoFondoSepelio == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>											
										</td>
										<td width="80px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoFondoRetiro}" rendered="#{itemGrilla.intSignoMontoFondoRetiro == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoFondoRetiro}" rendered="#{itemGrilla.intSignoMontoFondoRetiro == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>												
										</td>
										<td width="100px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoCtaPorPagar}" rendered="#{itemGrilla.intSignoMontoCtaPorPagar == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoCtaPorPagar}" rendered="#{itemGrilla.intSignoMontoCtaPorPagar == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>												
										</td>
										<td width="100px" align="center">
											<h:outputText style="color: blue" value="#{itemGrilla.bdMontoCtaPorCobrar}" rendered="#{itemGrilla.intSignoMontoCtaPorCobrar == 1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
											<h:outputText style="color: red" value="#{itemGrilla.bdMontoCtaPorCobrar}" rendered="#{itemGrilla.intSignoMontoCtaPorCobrar == -1}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText>											
										</td>
										<td width="80px" align="center">
											<h:outputText value="#{itemGrilla.bdSumaMontoFila}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>									
									</tr>
								</table>
							</rich:dataList>
						</rich:column>	
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column>
							<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000" width="1220px">
								<tr>
									<td width="220px"  align="center">
										<b><h:outputText value="Saldos Actuales" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoAporte}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoPrestamo}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoCredito}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoActividad}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoMulta}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumaMontoFondoRetiro}" >
										   <f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
				</h:panelGrid>
			</a4j:outputPanel>
			<a4j:outputPanel id="opTabResumen">
				<h:panelGrid id="pgTabResumen" rendered="#{estadoCuentaController.blnShowPanelTabResumen}">
					<rich:columnGroup>
						<rich:column width="1000px" style="text-align: center">
							<hr align="left" noshade="noshade" size="1" width="98%" style="color: #000000;" />
						</rich:column>
					</rich:columnGroup>	
					<!-- ENCABEZADO DE LA GRILLA -->		
					<rich:columnGroup>
						<rich:column width="1000px">
							<table>
								<tr>
									<td width="100px" align="center">
										<b><h:outputText value="Fecha" /></b>
									</td>
									<td width="270px" align="center">
										<b><h:outputText value="Conceptos" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Tasa" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Monto Total" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="CT/CP/CA" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Saldo" /></b>
									</td>
									<td width="130px" align="center">
										<b><h:outputText value="Diferencias" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="Último Envío" /></b>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1000px" style="text-align: center">
							<hr align="left" noshade="noshade" size="1" width="98%" style="color: #000000;" />
						</rich:column>
					</rich:columnGroup>
					<!-- DATOS DE LA GRILLA -->	
					<rich:columnGroup>
						<rich:column width="1000px" style="text-align: center">
							<rich:dataList value="#{estadoCuentaController.lstDataBeanEstadoCuentaResumenPrestamos}"
									var="itemGrilla" >	
								<table>
									<tr>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.strFecha}" />
										</td>
										<td width="270px" align="center">
											<h:outputText value="#{itemGrilla.strDescripcion}" />
										</td>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.bdTasaInteres}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.bdMontoTotal}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.strCuotas}" />
										</td>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.bdSaldoCredito}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
										<td width="130px" align="center">
											<h:outputText value="#{itemGrilla.bdDiferencia}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
										<td width="100px" align="center">
											<h:outputText value="#{itemGrilla.bdUltimoEnvio}" >
											<f:converter converterId="ConvertidorMontos"/></h:outputText>
										</td>
									</tr>
								</table>	
							</rich:dataList>
						</rich:column>	
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1000px" style="text-align: center">
							<hr align="left" noshade="noshade" size="1" width="98%" style="color: #000000;" />
						</rich:column>
					</rich:columnGroup>
					
					<rich:columnGroup>
						<rich:column width="1000px">
							<table>
								<tr>
									<td width="670px">
										<b>CP:</b> Cuentas pagadas <b>CT:</b> Cuentas totales <b>CA:</b> Cuentas atrasadas
									</td>			
									<td width="100px" align="center">
										<b><h:outputText style="text-align: center"	value="#{estadoCuentaController.bdSumatoriaSaldo}" >
										<f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
									<td width="130px" align="center">
										<h:outputText style="text-align: center" value="Aportes" />
									</td>
									<td width="100px" align="center">
										<h:outputText style="text-align: center" value="#{estadoCuentaController.bdUltimoEnvioAportes}" >
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<!-- FONDE DE SEPELIO -->
					<rich:columnGroup>
						<rich:column width="1000px">
							<table>
								<tr>
									<td width="770px">
										
									</td>			
									<td width="130px" align="center">
										<h:outputText style="text-align: center" value="Fondo Sepelio" />
									</td>
									<td width="100px" align="center">
										<h:outputText style="text-align: center" value="#{estadoCuentaController.bdUltimoEnvioFdoSepelio}" >
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<!-- FONDO DE RETIRO -->
					<rich:columnGroup>
						<rich:column width="1000px">
							<table>
								<tr>
									<td width="770px">
										
									</td>			
									<td width="130px" align="center">
										<h:outputText style="text-align: center" value="Fondo de Retiro" />
									</td>
									<td width="100px" align="center">
										<h:outputText style="text-align: center" value="#{estadoCuentaController.bdUltimoEnvioFdoRetiro}" >
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
					<!-- MANTENIMIENTO -->
					<rich:columnGroup>
						<rich:column width="1000px">
							<table>
								<tr>
									<td width="770px">
									</td>			
									<td width="130px" align="center">
										<h:outputText style="text-align: center" value="Mantenimiento" />
									</td>
									<td width="100px" align="center">
										<h:outputText style="text-align: center" value="#{estadoCuentaController.bdUltimoEnvioMant}" >
										<f:converter converterId="ConvertidorMontos"/></h:outputText>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>		
					<rich:columnGroup>
					   	<rich:column width="1000px">
					   		<table>
					   			<tr>
					   				<td width="770px">
									</td>	
									<td width="230px" align="center">
										<hr align="left" noshade="noshade" size="1" width="98%" style="color: #000000;" />
									</td>
					   			</tr>
					   		</table>					
						</rich:column>
					</rich:columnGroup>
					<!-- CUENTAS LIQUIDADAS -->
					<rich:columnGroup>
					   	<rich:column width="1000px">
					   		<table>
					   			<tr>
					   				<td width="120px">
					   					<b>Cuenta</b>
									</td>	
									<td width="650px" align="left">
										<b>Fecha de Liquidación</b>
									</td>
									<td width="130px" align="center">
										<b><tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
										    property="#{estadoCuentaController.intMesUltEnvio}"/></b> - 
										<b><h:outputText value="#{estadoCuentaController.intAnioUltEnvio}"></h:outputText></b>
									<td width="100px" align="center">
										<b><h:outputText value="#{estadoCuentaController.bdSumatoriaUltimoEnvio}">
											<f:converter converterId="ConvertidorMontos"/></h:outputText></b>
									</td>
					   			</tr>
					   		</table>					
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="240px">
							<rich:dataList value="#{estadoCuentaController.lstCuentasLiquidadas}"
									                var="item">
								<table>
									<tr>
										<td width="120px">
											<u><b><h:outputText value="#{item.strDescripcion}"/></b></u>
										</td>
										<td width="120px">
											<h:outputText value="#{item.strAbreviatura}"></h:outputText>
										</td>
									</tr>
								</table>
							</rich:dataList>
						</rich:column>
					</rich:columnGroup>
				</h:panelGrid>				
			</a4j:outputPanel>
			
			<!-- Planillas -->
			<a4j:outputPanel id="opTabPlanillas" rendered="#{estadoCuentaController.blnShowPanelTabPlanilla}">
				<h:panelGrid id="pgPlanillaResumen" border="0" rendered="#{estadoCuentaController.blnShowPanelTabPlanillaResumen}">
					<rich:dataTable value="#{estadoCuentaController.lstDataBeanEstadoCuentaPlanillas}" 
									rendered="#{not empty estadoCuentaController.lstDataBeanEstadoCuentaPlanillas}"
									styleClass="dataTable1" id="dtPlanillaResumen"
									rowKeyVar="rowKey" sortMode="single" width="1260px"
									var="itemGrilla">	
				        <f:facet name="header">
				            <rich:columnGroup  columnClasses="rich-sdt-header-cell">
				                <rich:column rowspan="2">
									<b><h:outputText value="Periodo" /></b>
				                </rich:column>
				                <rich:column colspan="3">
				                    <h:outputText value="Total Enviado" />
				                </rich:column>
				                <rich:column rowspan="2">
				                    <h:outputText value="TOTAL" />
				                </rich:column>
								<rich:column colspan="3">
				                    <h:outputText value="Total Efectuado" />
				                </rich:column>
				                <rich:column rowspan="2">
				                    <h:outputText value="TOTAL" />
				                </rich:column>
								<rich:column rowspan="2">
									<b><h:outputText value="Diferencia Planilla" /></b>
				                </rich:column>
				                <rich:column colspan="3">
									<b><h:outputText value="Estado de Pago Dependencia" /></b>
				                </rich:column>
				                <rich:column rowspan="2">
									<b><h:outputText value="Cargos" /></b>
				                </rich:column>
				                <rich:column rowspan="2">
									<b><h:outputText value="Abonos" /></b>
				                </rich:column>
				                <rich:column rowspan="2">
									<b><h:outputText value="Diferencia" /></b>
				                </rich:column>
				                <rich:column breakBefore="true">
				                    <h:outputText value="Hab." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Incent." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="CAS" />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Hab." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Incent." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="CAS" />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Hab." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Incent." />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="CAS" />
				                </rich:column>
				            </rich:columnGroup>
				        </f:facet>
				        <rich:column width="120px" style="text-align: center">
							<b><tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemGrilla.intMesPlanilla}"/></b>-
							<b><h:outputText value="#{itemGrilla.intAnioPlanilla}"></h:outputText></b>		
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEnvioHaberes}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEnvioIncentivos}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEnvioCas}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoTotalEnviado}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEfectuadoHaberes}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEfectuadoIncentivos}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoEfectuadoCas}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoTotalEfectuado}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoTotalDiferencia}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="60px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strEstadoPagoHaberes}"/>
						</rich:column>
						<rich:column width="60px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strEstadoPagoIncentivos}"/>
						</rich:column>
						<rich:column width="60px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strEstadoPagoCas}"/>
						</rich:column>						
						<rich:column width="80px" style="text-align: center">
						</rich:column>
						<rich:column width="80px" style="text-align: center">
						</rich:column>
						<rich:column width="80px" style="text-align: center">
						</rich:column>
						<f:facet name="footer">     
				        	<h:panelGroup layout="block">
						   		 <rich:datascroller for="dtPlanillaResumen" maxPages="10"/>
						   	</h:panelGroup>
					   	</f:facet>
					</rich:dataTable>
					<rich:spacer height="15"></rich:spacer>
					<a4j:commandButton id="btnDiferenciaPlanilla" value="Diferencia Planilla" styleClass="btnEstilos1" action="#{estadoCuentaController.getListaDiferenciaPlanillaPorPeriodo}" 
									 reRender="opTabPlanillas"/> 
				</h:panelGrid>
												
				<h:panelGrid id="pgDiferenciaPlanilla" border="0" rendered="#{estadoCuentaController.blnShowPanelTabPlanillaDiferencia}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="DIFERENCIA PLANILLA" /></b>
						</rich:column>
					</rich:columnGroup>
					<rich:dataTable value="#{estadoCuentaController.listaFilasDiferenciaPlanilla}" 
									styleClass="dataTable1" id="dtDiferenciaPlanilla"
									rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<rich:column style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Periodo"/>
							</f:facet>
							<h:outputText value="#{itemGrilla.strPeriodo}"/>
						</rich:column>
						<rich:columns width="90px" value="#{estadoCuentaController.listaColumnasDiferenciaPlanilla == null ? '':estadoCuentaController.listaColumnasDiferenciaPlanilla}" var="columns" index="ind" style="text-align: center">
				                          <f:facet name="header">
				                              <h:outputText value="#{columns.intOrdenPrioridad} - #{columns.strDescripcionDiferencia}"/>
				                          </f:facet>
								<h:outputText value="#{itemGrilla.bdMontoDiferencia[ind]}">
								<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				                 </rich:columns>
						<rich:column style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Total de Diferencia"></h:outputText>
							</f:facet>
							<h:outputText value="#{itemGrilla.bdSumaDiferenciaPlanilla}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<f:facet name="footer">     
				        	<h:panelGroup layout="block">
						   		 <rich:datascroller for="dtDiferenciaPlanilla" maxPages="10"/>
						   	</h:panelGroup>
					   	</f:facet>
					</rich:dataTable>
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<a4j:commandButton id="btnRetornarPlanilla" value="Volver" styleClass="btnEstilos1" action="#{estadoCuentaController.retornarTabPlanilla}" 
								 reRender="opTabPlanillas"/>
						</rich:column>
					</rich:columnGroup>	 
				</h:panelGrid>			
				
			</a4j:outputPanel>
			<a4j:outputPanel id="opTabTerceros" rendered="#{estadoCuentaController.blnShowPanelTabTerceros}">
				<h:panelGrid id="pgDsctoTrosHaberes" rendered="#{estadoCuentaController.blnShowPanelTabTercerosHaberes}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="PLANILLA DE HABERES"/></b>
						</rich:column>
					</rich:columnGroup>
					<rich:dataTable value="#{estadoCuentaController.lstFilaHaberes}" styleClass="dataTable1" id="dtMontoDsctoTrosHaberes"
									rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Periodo"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strPeriodo}"/>
						</rich:column>
						<rich:columns value="#{estadoCuentaController.lstColumnaHaberes == null ? '':estadoCuentaController.lstColumnaHaberes}" var="columns" index="ind" style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="#{columns.strNomCpto}"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
						</rich:columns>
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Total"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
						</rich:column>
						<f:facet name="footer">
							<h:panelGroup layout="block">
			                      	<rich:datascroller for="dtMontoDsctoTrosHaberes" maxPages="10"/>
			                      </h:panelGroup>
			                     </f:facet>						
					</rich:dataTable>
				</h:panelGrid>
				<!-- ################################################################################################################# -->
				<rich:spacer width="15"></rich:spacer>
				<h:panelGrid id="pgDsctoTrosIncentivos" rendered="#{estadoCuentaController.blnShowPanelTabTercerosIncentivos}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="PLANILLA DE INCENTIVOS"/></b>
						</rich:column>
					</rich:columnGroup>
					<rich:dataTable value="#{estadoCuentaController.lstFilaIncentivos}" styleClass="dataTable1" id="dtMontoDsctoTrosIncentivos"
									rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Periodo"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strPeriodo}"/>
						</rich:column>
						<rich:columns value="#{estadoCuentaController.lstColumnaIncentivos == null ? '':estadoCuentaController.lstColumnaIncentivos}" var="columns" index="ind" style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="#{columns.strNomCpto}"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
						</rich:columns>
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Total"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
						</rich:column>
						<f:facet name="footer">
							<h:panelGroup layout="block">
			                      	<rich:datascroller for="dtMontoDsctoTrosIncentivos" maxPages="10"/>
			                      </h:panelGroup>
			                     </f:facet>	
					</rich:dataTable>
				</h:panelGrid>
				<!-- ################################################################################################################# -->
				<rich:spacer width="15"></rich:spacer>
				<h:panelGrid id="pgDsctoTrosCas" rendered="#{estadoCuentaController.blnShowPanelTabTercerosCas}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="PLANILLA DE CAS"/></b>
						</rich:column>
					</rich:columnGroup>
					<rich:dataTable value="#{estadoCuentaController.lstFilaCas}" styleClass="dataTable1" id="dtMontoDsctoTrosCas"
									rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Periodo"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strPeriodo}"/>
						</rich:column>
						<rich:columns value="#{estadoCuentaController.lstColumnaCas == null ? '':estadoCuentaController.lstColumnaCas}" var="columns" index="ind" style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="#{columns.strNomCpto}"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
						</rich:columns>
						<rich:column style="text-align: center">
							<f:facet name="header">
								<b><h:outputText value="Total"/></b>
							</f:facet>
							<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
						</rich:column>
						<f:facet name="footer">
							<h:panelGroup layout="block">
			                      	<rich:datascroller for="dtMontoDsctoTrosCas" maxPages="10"/>
			                      </h:panelGroup>
			                     </f:facet>	
					</rich:dataTable>
				</h:panelGrid>				
			</a4j:outputPanel>
			
			<a4j:outputPanel id="opTabPrestamos" rendered="#{estadoCuentaController.blnShowPanelTabPrestamos}">
				<h:panelGrid>
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
										<h:selectOneMenu id="idEstadoEstCta" value="#{estadoCuentaController.intEstadoCreditoBusqueda}">
											<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADO_CREDITO}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
										</h:selectOneMenu>
									</td>
									<td width="100px" align="center">
										<a4j:commandButton id="btnConsultar2" value="Consultar" styleClass="btnEstilos1" action="#{estadoCuentaController.listarPrestamosYPersonasGarantizadas}"
										reRender="opTabPrestamos"/>
									</td>
								</tr>
							</table>
						</rich:column>
					</rich:columnGroup>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid id="pgPrestamos" rendered="#{not empty estadoCuentaController.lstDataBeanEstadoCuentaPrestamos}">
					<rich:columnGroup>
						<rich:column width="1170px" style="text-align: left">
							<rich:dataTable value="#{estadoCuentaController.lstDataBeanEstadoCuentaPrestamos}" 
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
									<h:outputText style="text-align: center" value="#{itemGrilla.strDescPrestamo}" />
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.strSolicitud}" />
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.bdPorcetajeInteres}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.bdMontoTotal}">
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
									<h:outputText style="text-align: center" value="#{itemGrilla.bdSaldoCredito}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="110px" style="text-align: center">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCATEGORIADERIESGO}" 
														  itemValue="intIdDetalle" itemLabel="strDescripcion" 
														  property="#{itemGrilla.intCategoriaCartera}"/>
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.strFechaGiro}" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.strNumeroDocumento}" />
								</rich:column>
								<rich:column width="40px" style="text-align: center">
									<h:outputText style="text-align: center" value="#{itemGrilla.strEstadoCredito}" />
								</rich:column>	
							</rich:dataTable>
						</rich:column>
					</rich:columnGroup>
					<!-- SUMATORIA DE SALDOS -->
					<rich:columnGroup>
						<rich:column width="1170px" style="text-align: left">
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
			
				<h:panelGrid id="pgPrestamoGarantizado" rendered="#{not empty estadoCuentaController.lstDataBeanEstadoCuentaPrestamosGarantizados}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="GARANTIZADOS" /></b>
						</rich:column>
					</rich:columnGroup>
					<!-- DATOS DE LA GRILLA -->	
					<rich:dataTable value="#{estadoCuentaController.lstDataBeanEstadoCuentaPrestamosGarantizados}" 
									styleClass="dataTable1" id="dtGarantizados"
									rowKeyVar="rowKey" rows="5" sortMode="single" width="1150px" 
									var="itemGrilla">		
						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column width="60px">
									<h:outputText value="Item" />
								</rich:column>
								<rich:column width="300px">
									<h:outputText value="Socio/Cliente" />
								</rich:column>
								<rich:column width="80px">
									<h:outputText value="Aporte" />
								</rich:column>
								<rich:column width="100px">
									<h:outputText value="Fecha" />
								</rich:column>
								<rich:column width="250px">
									<h:outputText value="Conceptos" />
								</rich:column>
								<rich:column width="100px">
									<h:outputText value="Solicitud" />
								</rich:column>
								<rich:column width="80px">
									<h:outputText value="Monto" />
								</rich:column>
								<rich:column width="100px">
									<h:outputText value="CT/CP/CA" />
								</rich:column>
								<rich:column width="80px">
									<h:outputText value="Saldo" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:column width="60px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{rowKey + 1}" />
						</rich:column>
						<rich:column width="300px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.strPersonaGarantizada}" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
								<h:outputText style="text-align: center" value="#{itemGrilla.bdSaldoAporte}">
								<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.strFechaSolicitud}" />
						</rich:column>
						<rich:column width="250px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.strDescPrestamo}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.strSolicitud}" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.bdMontoTotal}" >
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.strCuotas}" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
								<h:outputText style="text-align: center"
									value="#{itemGrilla.bdSaldoCredito}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<f:facet name="footer">     
							<h:panelGroup layout="block">
								 <rich:datascroller for="dtGarantizados" maxPages="10"/>
							</h:panelGroup>
						</f:facet>	
					</rich:dataTable>
				</h:panelGrid>			
			</a4j:outputPanel>
			
			<a4j:outputPanel id="opTabGestiones">
				<h:panelGrid id="pgTabGestiones" rendered="#{estadoCuentaController.blnShowPanelTabGestiones}">
					<rich:dataTable value="#{estadoCuentaController.lstDataBeanEstadoCuentaGestiones}" 
									rendered="#{not empty estadoCuentaController.lstDataBeanEstadoCuentaGestiones}"
									styleClass="dataTable1" id="dtGestionCobranza"
									rowKeyVar="rowKey" rows="5" sortMode="single" width="1250px"
									var="itemGrilla">	
						<f:facet name="header">
							<rich:columnGroup  columnClasses="rich-sdt-header-cell">
						        <rich:column rowspan="2">
									<b><h:outputText value="Tipo de Gestión" /></b>
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Sub Tipo de Gestión" />
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Tipo" />
						        </rich:column>
								<rich:column rowspan="2">
						            <h:outputText value="Fecha" />
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Hora Inicio" />
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Hora Fin" />
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Lugar de Gestión" />
						        </rich:column>
						        <rich:column rowspan="2">
						            <h:outputText value="Nro. Solicitud" />
						        </rich:column>
						        <rich:column rowspan="2" colspan="2">
						            <h:outputText value="Resultado" />
						        </rich:column>
						        <rich:column rowspan="2" colspan="2">
						            <h:outputText value="Observación" />
						        </rich:column>
						        <rich:column rowspan="2" colspan="2">
						            <h:outputText value="Contacto" />
						        </rich:column>
						        <rich:column rowspan="2" colspan="2">
						            <h:outputText value="Gestor" />
						        </rich:column>
						        <rich:column rowspan="2" colspan="2">
						            <h:outputText value="Sucursal" />
						        </rich:column>
						    </rich:columnGroup>
						</f:facet>
					    <rich:column style="text-align: center">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOGESTION}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemGrilla.intTipoGestionCobCod}"/>
						</rich:column>
						<rich:column style="text-align: center">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBTIPOGESTION}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemGrilla.intSubTipoGestionCobCod}"/>
						</rich:column>
						<rich:column style="text-align: center">	
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_SOCIO}" 
								  itemValue="intIdDetalle" itemLabel="strDescripcion" 
								  property="#{itemGrilla.intTipoPersonaOpe}"/>
						</rich:column>
						<rich:column style="text-align: center">
							<h:outputText value="#{itemGrilla.dtFechaGestion}">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align: center">
							<h:outputText value="#{itemGrilla.dtHoraInicio}">
								<f:convertDateTime pattern="HH:mm:ss"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align: center">
							<h:outputText value="#{itemGrilla.dtHoraFin}">
								<f:convertDateTime pattern="HH:mm:ss"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align: center">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_LUGARGESTION}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemGrilla.intParaLugarGestion}"/>	
						</rich:column>
						<rich:column style="text-align: center">
							<h:outputText value="#{itemGrilla.intCserItemExp}-#{itemGrilla.intCserdeteItemExp}"/>
						</rich:column>
						<rich:column colspan="2" style="text-align: center">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORESULTADO}" 
								  itemValue="intIdDetalle" itemLabel="strDescripcion" 
								  property="#{itemGrilla.intParaTipoResultado}"/>-
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUBRESULTADO}" 
								  itemValue="intIdDetalle" itemLabel="strDescripcion" 
								  property="#{itemGrilla.intParaTipoSubResultado}"/>													  
						</rich:column>
						<rich:column colspan="2" style="text-align: center">
							<h:outputText value="#{itemGrilla.strObservacion}"/>
						</rich:column>
						<rich:column colspan="2" style="text-align: center">
							<h:outputText value="#{itemGrilla.strContacto}"/>
						</rich:column>
						<rich:column colspan="2" style="text-align: center">
							<h:outputText id="lblValGestor" value="#{itemGrilla.strNombreGestor}" />
						</rich:column>
						<rich:column colspan="2" style="text-align: center">
							<h:outputText id="lblValStrSucursal" value="#{itemGrilla.strDescSucursal}"/>
						</rich:column>
						<f:facet name="footer">     
					       	<h:panelGroup layout="block">
						   		 <rich:datascroller for="dtGestionCobranza" maxPages="10"/>
						   	</h:panelGroup>
					   	</f:facet>	
					</rich:dataTable>
				</h:panelGrid>			
			</a4j:outputPanel>
			
			<!-- Previsión Social -->
			<a4j:outputPanel id="opTabPrevisionSocial" rendered="#{estadoCuentaController.blnShowPanelTabPrevisionSocial}">
				<h:panelGrid id="pgBeneficioOtorgado" rendered="#{not empty estadoCuentaController.lstBeneficiosOtorgados}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="BENEFICIOS OTORGADOS" /></b>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1100px" style="text-align: left">
							<rich:extendedDataTable id="edtBeneficioOtorgado" enableContextMenu="false" sortMode="single" 
								var="itemGrilla" value="#{estadoCuentaController.lstBeneficiosOtorgados}" 
				                rendered="#{not empty estadoCuentaController.lstBeneficiosOtorgados}" 
				                rowKeyVar="rowKey" width="1050px" height="210px">
								<f:facet name="header">
									<rich:columnGroup>
										<rich:column width="80px" rowspan="2" style="text-align: center">
											<h:outputText value="Fecha" />
										</rich:column>
										<rich:column width="60px" rowspan="2" style="text-align: center">
											<h:outputText value="Nro. Solicitud" />
										</rich:column>
										<rich:column width="160px" rowspan="2" style="text-align: center">
											<h:outputText value="Tipo de Beneficio" />
										</rich:column>
										<rich:column width="70px" rowspan="2" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="350px" colspan="2" style="text-align: center">
											<h:outputText value="Datos del Fallecido" />
										</rich:column>
										<rich:column width="350px" colspan="2" style="text-align: center">
											<h:outputText value="Datos del Beneficiario" />
										</rich:column>
										<rich:column width="70px" breakBefore="true" style="text-align: center">
											<h:outputText value="Tipo" />
										</rich:column>
										<rich:column width="280px" style="text-align: center">
											<h:outputText value="Nombre y Apellido" />
										</rich:column>
										<rich:column width="70px" style="text-align: center">
											<h:outputText value="Tipo" />
										</rich:column>
										<rich:column width="280px" style="text-align: center">
											<h:outputText value="Nombre y Apellido" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strFechaSolicitud}"/>
								</rich:column>
								<rich:column width="60px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intNumeroSolicitud}"/>
								</rich:column>
								<rich:column width="160px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strDescripcionTipoBeneficio}"/>
								</rich:column>
								<rich:column width="70px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoNeto}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="70px" style="text-align: center">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
						   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
									    property="#{itemGrilla.intCodVinculoFallecido}"/>
								</rich:column>
								<rich:column width="280px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strNombreFallecido}"/>
								</rich:column>
								<rich:column width="70px" style="text-align: center">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
						   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
									    property="#{itemGrilla.intCodVinculoBeneficiario}"/>
								</rich:column>
								<rich:column width="260px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strNombreBeneficiario}"/>
								</rich:column>
							</rich:extendedDataTable>
						</rich:column>
					</rich:columnGroup>        
					<rich:spacer height="15"></rich:spacer>
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="PAGOS DE CUOTAS DEL BENEFICIO" /></b>
						</rich:column>
					</rich:columnGroup>	
				</h:panelGrid>
				
				<h:panelGrid id="pgPagoCuotaFdoSepelio" rendered="#{not empty estadoCuentaController.lstPagoCuotasFondoSepelio}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="FONDO DE SEPELIO" /></b>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<rich:dataTable value="#{estadoCuentaController.lstPagoCuotasFondoSepelio}" 
											styleClass="dataTable1" id="dtPagoCtasResumenFdoSepelio"
											rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
											var="itemGrilla">
								<f:facet name="header">
									<rich:columnGroup columnClasses="rich-sdt-header-cell">
										<rich:column width="70px" rowspan="2" style="text-align: center">
											<h:outputText value="Periodo" />
										</rich:column>
										<rich:column width="200px" colspan="2" style="text-align: center">
											<h:outputText value="Cuota" />
										</rich:column>
										<rich:column width="300px" colspan="3" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="150px" rowspan="2" style="text-align: center">
											<h:outputText value="Opciones" />
										</rich:column>
										<rich:column width="100px" breakBefore="true" style="text-align: center">
											<h:outputText value="Número" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Provisionado" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Cancelado" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Pendiente" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
								<rich:column width="70px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intAnioInicio}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intNumeroCuotas}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdCuotaMensual}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoProvisionado}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoCancelado}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoPendiente}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="150px" style="text-align: center">
									<a4j:commandLink id="lnkDetalle" value="Detalle" 
									actionListener="#{estadoCuentaController.listarDetallePagoCuotasFdoSepelio}" 
									oncomplete="Richfaces.showModalPanel('mpPgoCtasDetalleFdoSepelio')"
									reRender="mpPgoCtasDetalleFdoSepelio">
									<f:attribute name="itemGrilla" value="#{itemGrilla}"/>
									</a4j:commandLink>
								</rich:column>
								<f:facet name="footer">     
						        	<h:panelGroup layout="block">
								   		 <rich:datascroller for="dtPagoCtasResumenFdoSepelio" maxPages="10"/>
								   	</h:panelGroup>
							   	</f:facet>									
							</rich:dataTable>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="Años Aportados = #{estadoCuentaController.intPgoCtaBeneficioFdoSepelio}" /></b>
						</rich:column>
					</rich:columnGroup>
				</h:panelGrid>
								
				<h:panelGrid id="pgPagoCuotaFdoRetiro" rendered="#{not empty estadoCuentaController.lstPagoCuotasFondoRetiro}">
					<rich:columnGroup>
						<rich:column width="1150px" style="text-align: left">
							<b><h:outputText value="FONDO DE RETIRO" /></b>
						</rich:column>
					</rich:columnGroup>
					<rich:columnGroup>
						<rich:column width="500px" style="text-align: left">
							<rich:dataTable value="#{estadoCuentaController.lstPagoCuotasFondoRetiro}" 
											styleClass="dataTable1" id="dtPagoCtasResumenFdoRetiro"
											rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
											var="itemGrilla">
								<f:facet name="header">
									<rich:columnGroup columnClasses="rich-sdt-header-cell">
										<rich:column width="70px" rowspan="2" style="text-align: center">
											<h:outputText value="Periodo" />
										</rich:column>
										<rich:column width="200px" colspan="2" style="text-align: center">
											<h:outputText value="Cuota" />
										</rich:column>
										<rich:column width="200px" colspan="2" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="100px" breakBefore="true" style="text-align: center">
											<h:outputText value="Número" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Cancelado" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Acumulado" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
								<rich:column width="70px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intAnioInicio}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intNumeroCuotas}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdCuotaMensual}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoCancelado}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.bdMontoAcumulado}">
									<f:converter converterId="ConvertidorMontos"  /></h:outputText>
								</rich:column>
								<f:facet name="footer">     
						        	<h:panelGroup layout="block">
								   		 <rich:datascroller for="dtPagoCtasResumenFdoRetiro" maxPages="10"/>
								   	</h:panelGroup>
							   	</f:facet>
							</rich:dataTable>
				      	</rich:column>
				      	<rich:column width="650px" style="text-align: left">
							<a4j:commandButton id="btnPgoCtaDetFdoSepelio" value="Detalle" styleClass="btnEstilos1" 
							action="#{estadoCuentaController.listarDetallePagoCuotasFdoRetiro}" 
							reRender="mpPgoCtasDetalleFdoRetiro"
							oncomplete="Richfaces.showModalPanel('mpPgoCtasDetalleFdoRetiro')"/>
				      	</rich:column>
					</rich:columnGroup>
				</h:panelGrid>
				
				<!-- Popup's -->
				<rich:modalPanel id="mpPgoCtasDetalleFdoSepelio" width="880" height="300" resizeable="false" style="background-color:#DEEBF5">
					<f:facet name="header">
						<h:panelGrid>
							<rich:column style="border: none;">
								<h:outputText value="PAGO DE CUOTAS FDO. SEPELIO - DETALLE"></h:outputText>
							</rich:column>
						</h:panelGrid>
					</f:facet>
					<f:facet name="controls">
						<h:panelGroup>
							<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
								<rich:componentControl for="mpPgoCtasDetalleFdoSepelio" operation="hide" event="onclick"/>
							</h:graphicImage>
						</h:panelGroup>
					</f:facet> 
					<rich:dataTable value="#{estadoCuentaController.lstDetallePagoCuotasFondoSepelio}" 
									styleClass="dataTable1" id="dtPagoCtasDetalleFdoSepelio"
									rowKeyVar="rowKey" rows="10" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column width="150px" rowspan="2" style="text-align: center">
									<h:outputText value="Fecha de Pago" />
								</rich:column>
								<rich:column width="70px" rowspan="2" style="text-align: center">
									<h:outputText value="Periodo" />
								</rich:column>
								<rich:column width="150px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo Movimiento" />
								</rich:column>
								<rich:column width="150px" rowspan="2" style="text-align: center">
									<h:outputText value="Nro. Documento" />
								</rich:column>
								<rich:column width="100px" rowspan="2" style="text-align: center">
									<h:outputText value="Cargo / Abono" />
								</rich:column>
								<rich:column width="100px" rowspan="2" style="text-align: center">
									<h:outputText value="Monto" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
				
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strFechaMovimiento}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<h:outputText value="#{itemGrilla.intPeriodoFechaCptoPago}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strDescTipoMovimiento}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strNumeroDocumento}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="#{itemGrilla.strDescTipoCargoAbono}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="#{itemGrilla.bdMontoMovimiento}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
							
						<f:facet name="footer">     
				        	<h:panelGroup layout="block">
						   		 <rich:datascroller for="dtPagoCtasDetalleFdoSepelio" maxPages="10"/>
						   	</h:panelGroup>
					   	</f:facet>
					</rich:dataTable>
				</rich:modalPanel>
				
				<rich:modalPanel id="mpPgoCtasDetalleFdoRetiro" width="1100" height="300" resizeable="false" style="background-color:#DEEBF5">
					<f:facet name="header">
						<h:panelGrid>
							<rich:column style="border: none;">
								<h:outputText value="PAGO DE CUOTAS FDO. RETIRO - DETALLE"></h:outputText>
							</rich:column>
						</h:panelGrid>
					</f:facet>
					<f:facet name="controls">
						<h:panelGroup>
							<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
								<rich:componentControl for="mpPgoCtasDetalleFdoRetiro" operation="hide" event="onclick"/>
							</h:graphicImage>
						</h:panelGroup>
					</f:facet>
					<rich:dataTable value="#{estadoCuentaController.lstDetallePagoCuotasFondoRetiro}" 
									styleClass="dataTable1" id="dtPagoCtasDetalleFdoRetiro"
									rowKeyVar="rowKey" rows="6" sortMode="single" style="margin:0 auto text-align: left"
									var="itemGrilla">
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">				
								<h:outputText value="AÑO" />
							</f:facet>
							<b><h:outputText value="#{itemGrilla.intPeriodoInicio}"/></b>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="CONCEPTO" />
							</f:facet>
							<h:outputText value="#{itemGrilla.strDescTipoConceptoGral}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="ENE" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoEne}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="FEB" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoFeb}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="MAR" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoMar}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="ABR" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoAbr}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="MAY" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoMay}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="JUN" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoJun}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="JUL" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoJul}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="AGO" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoAgo}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="SET" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoSet}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="OCT" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoOct}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="NOV" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoNov}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="DIC" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoMovimientoDic}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="TOTAL" />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoTotal}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">	
								<h:outputText value="ACUM." />
							</f:facet>
							<h:outputText value="#{itemGrilla.bdMontoAcumulado}">
							<f:converter converterId="ConvertidorMontos"  /></h:outputText>
						</rich:column>		
					<f:facet name="footer">     
				        	<h:panelGroup layout="block">
						   		 <rich:datascroller for="dtPagoCtasDetalleFdoRetiro" maxPages="10"/>
						   	</h:panelGroup>
					   	</f:facet>
					</rich:dataTable>
				</rich:modalPanel>
				

			</a4j:outputPanel>
			
		</rich:panel>		
	</rich:panel>
</h:form>