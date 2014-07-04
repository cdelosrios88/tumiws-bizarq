<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA CORRIENTE - SOCIO -->			
<!-- FECHA     : 15.07.2013               -->

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; " id="frmPrincipal">
	<h:outputLabel value="#{estadoCuentaController.inicioPage}"/>
	<rich:panel id="formFiltrosBusqueda" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
		<div align="center">
			<b>ESTADO DE CUENTA CORRIENTE</b>
		</div>
		<!-- FILTROS PARA LA BUSQUEDA -->
		<h:panelGroup>		
			<h:panelGrid>
				<rich:column width="1150px" style="text-align: center">
					<table>
						<tr>
							<td width="200px" align="center">
								<b><h:outputText value="Socio / Cliente :"/></b>									
							</td>
							<td width="500px" align="left">
								<h:selectOneMenu id="idTipoDeBusqueda" value="#{estadoCuentaController.intTipoBusqueda}">
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
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" action="#{estadoCuentaController.buscarDatosDelSocio}"
								reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda"/>
							</td>
							<td width="100px" align="center">
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{estadoCuentaController.limpiarCampos}" 
								reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda"/>
							</td>
						</tr>
						<tr>
							<td width="200px" align="center">
								<b><h:outputText value="Ctas. del Socio: "/></b>
							</td>
							<td width="500px" align="left">
								<h:selectOneMenu id="idCtasSocBusq" value="#{estadoCuentaController.intCtasSocBusqueda}" style="width:200px;">
									<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
										<tumih:selectItems var="sel" value="#{estadoCuentaController.lstCtasSocBusqueda}"
							 			itemValue="#{sel.cuentaIntegrante.id.intCuenta}" itemLabel="#{sel.cuenta.strNumeroCuenta} / #{sel.strDescripcionSituacionCuenta}"/>
								</h:selectOneMenu>
							</td>
						</tr>
					</table>
				</rich:column>
			</h:panelGrid>
			
			<rich:spacer width="10"></rich:spacer>
			<!-- BUSQUEDA POR APELLIDOS Y NOMBRES -->
			<a4j:include id="inclBusquedaPorApellidosYNombres" viewId="/pages/estadoCuenta/estCtaBusqPorApeNom.jsp" />	
			<rich:spacer height="10"></rich:spacer>
			<!-- RESULTADOS DE LA BUSQUEDA -->	
			<!-- TAB's -->
			<h:panelGrid id="formCabeceraDatosSocioCuentaYTabs" rendered="#{estadoCuentaController.blnShowPanelDatosDelSocioYCuentas}">
				<rich:columnGroup>
					<rich:column width="1000px">
						<table>
							<tr>
								<td width="1000px" align="left">
									<a4j:commandButton id="btnResumen" value="Resumen" styleClass="btnEstilos1" action="#{estadoCuentaController.retornarResumenEstadoCta}" 
							 		reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formDetalleEstadoCuenta,formTabTerceros,formFiltrosBusqueda"/>
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnDetalle" value="Detalle" styleClass="btnEstilos1" action="#{estadoCuentaController.cargarListaDetalladaBeneficiosYPrestamos}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formDetalleEstadoCuenta,formTabTerceros,formFiltrosBusqueda"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPlanillas" value="Planillas" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPlanilla}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabPlanilla"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnTerceros" value="Terceros" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabDsctoTerceros}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formDetalleEstadoCuenta,formTabTerceros,formFiltrosBusqueda"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnGestiones" value="Gestiones" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabGestiones}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabGestiones"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPrestamos" value="Préstamos" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPrestamos}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabPrestamos"/>							
									<rich:spacer width="5"></rich:spacer>
									<a4j:commandButton id="btnPrevisionSocial" value="Previsión Social" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPrevisionSocial}" 
									 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabPrevisionSocial"/>							
									<rich:spacer width="5"></rich:spacer>
									<h:commandButton id="btnImprimir" value="Imprimir" styleClass="btnEstilos1" action="#{estadoCuentaController.imprimirReporte}"/>							
								</td>
							</tr>
						</table>				
					</rich:column>
		    	</rich:columnGroup>
		       	<rich:spacer height="5"></rich:spacer>
				<!-- CABECERA - BENEFICIOS -->
				<rich:columnGroup>
					<rich:column width="1000">
						<table>
							<tr>
								<td width="120px">
									<b><h:outputText value="Fecha de Emisión:"/></b>
								</td>
								<td width="120px">
									<h:outputText value="#{estadoCuentaController.strFechaYHoraActual}"/> 
								</td>								
							</tr>
						</table>
					</rich:column>
				</rich:columnGroup>	
				<rich:columnGroup>
					<rich:column width="1000px">
						<table>
							<tr>
								<td width="100px">
									<b><h:outputText value="Socio / Cliente :"/></b>
								</td>
								<td width="300px">
									<h:outputText value="#{estadoCuentaController.cuentaComp.cuentaIntegrante.id.intPersonaIntegrante}"/> - 
									<h:outputText value="#{estadoCuentaController.socioComp.socio.strNombreSoc} 
														 #{estadoCuentaController.socioComp.socio.strApePatSoc} 
														 #{estadoCuentaController.socioComp.socio.strApeMatSoc}"/>
								</td>
								<td width="100px">
									<b><h:outputText value="Condición :"/></b>
								</td>
								<td width="200px">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
													  itemValue="intIdDetalle" itemLabel="strDescripcion" 
													  property="#{estadoCuentaController.cuentaComp.cuenta.intParaCondicionCuentaCod}"/> - 
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
													  itemValue="intIdDetalle" itemLabel="strDescripcion" 
													  property="#{estadoCuentaController.cuentaComp.cuenta.intParaSubCondicionCuentaCod}"/>
								</td>
								<td width="110px">
									<b><h:outputText value="Apertura Cta :"/></b>
								</td>
								<td width="100px">
									<h:outputText value="#{estadoCuentaController.strFechaRegistro}"/>	
								</td>
							</tr>
						</table>
					</rich:column>
				</rich:columnGroup>
				<rich:columnGroup>
					<rich:column width="1000px">
						<table align="center">
							<tr>
								<td width="90px">
									<b><h:outputText value="Sucursal :"/></b>
								</td>
								<td width="200px">
			 						<rich:dataList value="#{estadoCuentaController.listaRazonSocialSucursal}" var="item">
									   <h:panelGrid columns="1">
									   	  <h:outputText value="#{item.strRazonSocial}"></h:outputText>							      
					                  </h:panelGrid>
									</rich:dataList>
								</td>
								<td width="130px">
									<b><h:outputText value="Unidad Ejecutora :"/></b>
								</td>
								<td width="420px">
									<rich:dataList value="#{estadoCuentaController.listaUnidadEjecutora}"
																var="item">
										   <h:panelGrid columns="2">
											  <h:outputText value="#{item.strAbreviatura}"></h:outputText>
										  </h:panelGrid>
									</rich:dataList>
								</td>
								<td width="110px">
									<b><h:outputText value="Cta. Aporte :"/></b>
								</td>
								<td width="100px">
									<h:outputText value="#{estadoCuentaController.bdMontoConceptoAporte}">
									<f:converter converterId="ConvertidorMontos"/></h:outputText>
								</td>
							</tr>
						</table>
					</rich:column>
				</rich:columnGroup>		
				<rich:columnGroup>
					<rich:column width="900px">
						<table>
							<tr>
								<td width="100px">
									<b><h:outputText value="Saldo Aporte :"/></b>
								</td>
								<td width="95px">
									<b><h:outputText value="#{estadoCuentaController.bdCucoSaldoAporte}">
									<f:converter converterId="ConvertidorMontos"/></h:outputText></b>
								</td>
								<td width="120px">
									<b><h:outputText value="Fondo de Retiro :"/></b>
								</td>
								<td width="95px">
									<h:outputText value="#{estadoCuentaController.bdCucoSaldoRetiro}">
									<f:converter converterId="ConvertidorMontos"/></h:outputText>
								</td>
								<td width="100px">
									<b><h:outputText value="Pendiente :"/></b>
								</td>
								<td width="100px">
									<h:outputText value="Fondo Sepelio :"/>
								</td>
								<td width="95px">
									<h:outputText value="#{estadoCuentaController.bdMontoSaldoSepelio}">
									<f:converter converterId="ConvertidorMontos"/></h:outputText>
								</td>
								<td width="100px">
									<h:outputText value="Mantenimiento :"/>
								</td>
								<td width="95px">
									<h:outputText value="#{estadoCuentaController.bdMontoSaldoMantenimiento}">
									<f:converter converterId="ConvertidorMontos"/></h:outputText>
								</td>						
							</tr>
						</table>					
					</rich:column>
				</rich:columnGroup>	
				<rich:columnGroup>	
					<rich:column width="900px">
						<table>
							<tr>
								<td width="100px">
									<b><h:outputText value="Nro. de Cuenta :"/></b>
								</td>
								<td width="120px">
									<b><h:outputText value="#{estadoCuentaController.cuentaComp.cuenta.strNumeroCuenta}"/></b>
								</td>
							</tr>
						</table>
					</rich:column>		
				</rich:columnGroup>	
			</h:panelGrid>	
			<rich:spacer height="10"></rich:spacer>
			<h:panelGrid id="formDetalleEstadoCuenta" rendered="#{estadoCuentaController.blnShowPanelTabDetalle}">
				<rich:columnGroup>
					<rich:column width="1000px">
						<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000">
							<tr>
								<td width="60px"  align="center">
									<b><h:outputText value="Fecha" /></b>
								</td>
								<td width="90px"  align="center">
									<b><h:outputText value="Tipo - Nro." /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Aporte" /></b>
								</td>
								<td width="80px" align="center">
									<b><h:outputText value="Préstamos" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Créditos" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Interés" /></b>
								</td>
								<td width="70px" align="center">
									<b><h:outputText value="Mto.Cta." /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Actividad" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Multa" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="Sepelio" /></b>
								</td>
								<td width="60px" align="center">
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
					<rich:column width="1000px" style="text-align: center">
						<rich:dataList value="#{estadoCuentaController.listaSumatoriaEstadoCuentaComp}"
								var="itemGrilla" >	
							<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000">
								<tr>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strFecha}" /></b>
									</td>
									<td width="90px" align="center">
										<b><h:outputText value="#{itemGrilla.strDescripcion}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovAportes}" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovPrestamos}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovCreditos}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovInteres}" /></b>
									</td>
									<td width="70px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovMntCuenta}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovActividad}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovMulta}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovFdoSepelio}" /></b>
									</td>
									<td width="60px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovFdoRetiro}" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovCtaXPagar}" /></b>
									</td>
									<td width="100px" align="center">
										<b><h:outputText value="#{itemGrilla.strMontoMovCtaXCobrar}" /></b>
									</td>
									<td width="80px" align="center">
										<b><h:outputText value="" /></b>
									</td>									
								</tr>
							</table>
						</rich:dataList>
					</rich:column>	
				</rich:columnGroup>
				<rich:columnGroup>
					<rich:column width="1000px" style="text-align: center">
						<rich:dataList value="#{estadoCuentaController.listaEstadoCuentaComp}"
								var="itemGrilla" >	
							<table>
								<tr>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strFecha}" />
									</td>
									<td width="90px" align="center">
										<h:outputText value="#{itemGrilla.strDescripcion}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovAportes}" />
									</td>
									<td width="80px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovPrestamos}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovCreditos}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovInteres}" />
									</td>
									<td width="70px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovMntCuenta}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovActividad}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovMulta}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovFdoSepelio}" />
									</td>
									<td width="60px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovFdoRetiro}" />
									</td>
									<td width="100px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovCtaXPagar}" />
									</td>
									<td width="100px" align="center">
										<h:outputText value="#{itemGrilla.strMontoMovCtaXCobrar}" />
									</td>
									<td width="80px" align="center">
										<h:outputText value="#{itemGrilla.strSumatoriaFilas}" />
									</td>									
								</tr>
							</table>
						</rich:dataList>
					</rich:column>	
				</rich:columnGroup>
				<rich:columnGroup>
					<rich:column width="1000px">
						<table border=1 cellspacing=0 cellpadding=2 bordercolor="000000">
							<tr>
								<td width="150px"  align="center">
									<b><h:outputText value="Saldos Actuales" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovAportes}" /></b>
								</td>
								<td width="80px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovPrestamos}" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovCreditos}" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="" /></b>
								</td>
								<td width="70px" align="center">
									<b><h:outputText value="" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovActividad}" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovMulta}" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="" /></b>
								</td>
								<td width="60px" align="center">
									<b><h:outputText value="#{estadoCuentaController.strSumatoriaMontoMovFdoRetiro}" /></b>
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
			<rich:spacer height="10"></rich:spacer>
			<!-- DETALLE - PRESTAMOS -->	
			<h:panelGrid id="formServiciosCuentaSocio" rendered="#{estadoCuentaController.blnShowPanelResumenEstadoCta}">
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
						<rich:dataList value="#{estadoCuentaController.lstDataBeanEstadoCuentaPrestamos}"
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
				<!-- DETALLE BENEFICIOS -->
				<!-- SUMATORIA DE SALDOS Y APORTES -->
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
									** Sin impresión de Ficha de Socio
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
									** Sin Carta Declaratoria de Herederos
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
									    property="#{estadoCuentaController.intMesPeriodoTemp}"/></b> - 
									<b><h:outputText value="#{estadoCuentaController.intAnioPeriodoTemp}"></h:outputText></b>
								<td width="100px" align="center">
									<b><h:outputText value="#{estadoCuentaController.bdSumatoriaUltimoEnvio}"></h:outputText></b>
								</td>
				   			</tr>
				   		</table>					
					</rich:column>
				</rich:columnGroup>
				<rich:columnGroup>
					<rich:column width="240px">
						<rich:dataList value="#{estadoCuentaController.listaCuentaComp}"
								                var="item">
							<table>
								<tr>
									<td width="120px">
										<u><b><h:outputText value="#{item.cuenta.strNumeroCuenta}"/></b></u>
									</td>
									<td width="120px">
										<h:outputText value="#{item.strFechaRenuncia}"></h:outputText>
									</td>
								</tr>
							</table>
						</rich:dataList>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
			<rich:spacer width="15"></rich:spacer>
			<!-- TAB TERCEROS -->
			<h:panelGrid id="formTabTerceros" rendered="#{estadoCuentaController.blnShowPanelTabTerceros}">
				<a4j:include id="inclTerceros" viewId="/pages/estadoCuenta/tabs/terceros.jsp" />
			</h:panelGrid>
			<rich:spacer height="10"></rich:spacer>
			<!-- TAB GESTIONES -->
			<h:panelGrid id="formTabGestiones" rendered="#{estadoCuentaController.blnShowPanelTabGestiones}">
				<a4j:include id="inclGestiones" viewId="/pages/estadoCuenta/tabs/gestiones.jsp" />
			</h:panelGrid>
			<rich:spacer height="10"></rich:spacer>
			<!-- TAB PRESTAMOS -->
			<h:panelGrid id="formTabPrestamos" rendered="#{estadoCuentaController.blnShowPanelTabPrestamos}">
				<a4j:include id="inclPrestamos" viewId="/pages/estadoCuenta/tabs/prestamos/prestamos.jsp" />
				<a4j:include id="inclGarantizados" viewId="/pages/estadoCuenta/tabs/prestamos/garantizados.jsp" />
			</h:panelGrid>
			<!-- TAB PLANILLA -->
			<h:panelGrid id="formTabPlanilla" border="0" rendered="#{estadoCuentaController.blnShowPanelTabPlanilla}">
				<a4j:include id="inclResumenPlanilla" viewId="/pages/estadoCuenta/tabs/planilla/planilla.jsp" />
				<a4j:include id="inclDiferenciaPlanilla" viewId="/pages/estadoCuenta/tabs/planilla/diferenciaPlanilla.jsp" />
			</h:panelGrid>
			<!-- TAB PREVISION SOCIAL -->
			<h:panelGrid id="formTabPrevisionSocial" rendered="#{estadoCuentaController.blnShowPanelTabPrevisionSocial}">
				<a4j:include id="inclBeneficiosOtorgados" viewId="/pages/estadoCuenta/tabs/previsionSocial/beneficiosOtorgados.jsp" />
				
				<a4j:include id="inclPgoCtasResumenFdoSepelio" viewId="/pages/estadoCuenta/tabs/previsionSocial/pgoBenefFdoSepelio/fdoSepelioResumen.jsp" />
				<!-- POPUP DETALLE PAGO CTAS FDO.SEPELIO -->
				<a4j:include id="inclPgoCtasDetalleFdoSepelio" viewId="/pages/estadoCuenta/tabs/previsionSocial/pgoBenefFdoSepelio/fdoSepelioPopup.jsp" />
				
				<a4j:include id="inclPgoCtasResumenFdoRetiro" viewId="/pages/estadoCuenta/tabs/previsionSocial/pgoBenefFdoRetiro/fdoRetiroResumen.jsp" />
				<!-- POPUP DETALLE PAGO CTAS FDO.RETIRO -->
				<a4j:include id="inclPgoCtasDetalleFdoRetiro" viewId="/pages/estadoCuenta/tabs/previsionSocial/pgoBenefFdoRetiro/fdoRetiroPopup.jsp" />
			</h:panelGrid>
		</h:panelGroup>
	</rich:panel>
	<a4j:region>
		<a4j:jsFunction name="selectedSocioNatural"	actionListener="#{estadoCuentaController.setSelectedSocioNatural}">
			<f:param name="rowSocioNatural"></f:param>
		</a4j:jsFunction>
	</a4j:region>
</h:form>