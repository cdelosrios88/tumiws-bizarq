<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 24/01/2014               	-->

<h:form id="frmPrimerDescuento">
   	<rich:panel id="rpTabPrimerDscto" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>ANÁLISIS PRIMER DESCUENTO DE PRÉSTAMO</b>
			</div>
			<rich:spacer height="18px"/>
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td width="130px" align="left">
								<h:outputText value="Tipo Socio:"/>
							</td>
							<td align="left">
								<h:selectOneMenu id="idTipoSocio" value="#{primerDescuentoController.intParaTipoSucursal}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</td>
							<td width="65px" align="left">
								<h:outputText value="Sucursal:"/>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="cboSucursal" style="width: 140px;"
									onchange="getSubSucursalPrimerDscto(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{primerDescuentoController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{primerDescuentoController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Sub Sucursal:"/>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursalPrimerDscto" style="width: 110px;"
						            value="#{primerDescuentoController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{primerDescuentoController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td align="left">
								<h:outputText value="Tipo de Préstamo:"/>
							</td>
							<td>
								<h:selectOneMenu id="idTipoServicio" value="#{primerDescuentoController.intTipoServicio}"
									onchange="getTipoPrestamo(#{applicationScope.Constante.ONCHANGE_VALUE})">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{primerDescuentoController.listaTipoPrestamo}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td>
								<h:selectOneMenu id="cboSubTipoPrestamo" value="#{primerDescuentoController.intTipoCreditoEmpresa}">
				                       <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				                       <tumih:selectItems var="sel" value="#{primerDescuentoController.listaTipoCreditoEmpresa}" 
				                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
				                       propertySort="intOrden"/>
				                </h:selectOneMenu>
							</td>
						</tr>
						<tr>
							<td width="130px" align="left">
								<h:outputText value="Tipo Diferencia:"/>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="idTipoDiferencia" value="#{primerDescuentoController.intIdTipoDiferencia}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<f:selectItem itemLabel="Descuento Normal" itemValue="1"/>
			          	 			<f:selectItem itemLabel="Diferencia de Menos" itemValue="2"/>
			          	 			<f:selectItem itemLabel="Exceso de Descuento" itemValue="3"/>
			          	 			<f:selectItem itemLabel="No descontados" itemValue="4"/>
								</h:selectOneMenu>
							</td>
							<td width="85px" align="left">
								<h:outputText value="Fecha:"/>
							</td>
							<td align="left">
								<h:selectOneMenu id="idTipoFecha" value="#{primerDescuentoController.intIdTipoFecha}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<f:selectItem itemLabel="Solicitud del Préstamo" itemValue="1"/>
			          	 			<f:selectItem itemLabel="Primer Descuento" itemValue="2"/>
								</h:selectOneMenu>
							</td>
							<td>
								<h:selectOneMenu id="cboMes" value="#{primerDescuentoController.intIdMes}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td>
								<h:selectOneMenu id="cboAnio" value="#{primerDescuentoController.intIdAnio}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{primerDescuentoController.listYears}"/>
								</h:selectOneMenu>
							</td>
							<td width="90px" align="center">
								<h:outputText value="Rango de Montos :"/>
							</td>
							<td>
								<h:inputText id="idMontoMin" value="#{primerDescuentoController.bdMontoMin}" size="10"/>
							</td>
							<td>
								<h:inputText id="idMontoMax" value="#{primerDescuentoController.bdMontoMax}" size="10"/>
							</td>
							<td width="130px" align="left">
								<h:selectBooleanCheckbox id="chkReprestamo" value="#{primerDescuentoController.blnReprestamo}"/>Solo Represtamo
							</td>
						</tr>
					</table>
				</rich:column>			
			</rich:columnGroup>
			
			<rich:spacer height="6px"/>
			
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td>
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{primerDescuentoController.consultarPrimerDscto}"
									process="idTipoSocio,cboSucursal,cboSubSucursalPrimerDscto,idTipoServicio,cboSubTipoPrestamo,
											 idTipoDiferencia,idTipoFecha,cboMes,cboAnio,idMontoMin,idMontoMax,chkReprestamo"
	          	 					reRender="dtPrimerDescuento,mpMessage"
	          	 					ajaxSingle="true"
	          	 					oncomplete="if(#{primerDescuentoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{primerDescuentoController.limpiarPrimerDscto}" 
									reRender="dtPrimerDescuento"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{primerDescuentoController.imprimirPrimerDscto}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			
			<h:panelGrid>
					<rich:dataTable id="dtPrimerDescuento" styleClass="dataTable1"
						value="#{primerDescuentoController.listaPrimerDescuento}"
                   		
                   		var="item" rowKeyVar="rowKey" width="1050px">
                   			<f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="60px" rowspan="2" style="text-align: center">
										<h:outputText value="Nro." />
									</rich:column>
									<rich:column width="300px" rowspan="2" style="text-align: center">
										<h:outputText value="Cuenta - Apellido y Nombre" />
									</rich:column>
									<rich:column width="60px" rowspan="2" style="text-align: center">
										<h:outputText value="Tipo de Socio" />
									</rich:column>
									<rich:column width="150px" colspan="2" style="text-align: center">
										<h:outputText value="Sucursal" />
									</rich:column>
									<rich:column width="500px" colspan="8" style="text-align: center">
										<h:outputText value="Solicitud" />
									</rich:column>
									<rich:column width="200px" colspan="3" style="text-align: center">
										<h:outputText value="Descuento por Planilla" />
									</rich:column>
									<rich:column width="150px" colspan="2" style="text-align: center">
										<h:outputText value="Descuento por Caja" />
									</rich:column>
									<rich:column width="75px" breakBefore="true" style="text-align: center">
										<h:outputText value="Socio" />
									</rich:column>
									<rich:column width="75px" style="text-align: center">
										<h:outputText value="Atención" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Tipo" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Nº Expediente" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Représtamo" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Fecha" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="1er Mes" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Monto" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Capacidad" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Cuota Fija" />
									</rich:column>
									<rich:column width="65px" style="text-align: center">
										<h:outputText value="Enviado" />
									</rich:column>
									<rich:column width="65px" style="text-align: center">
										<h:outputText value="Efectuado" />
									</rich:column>
									<rich:column width="65px" style="text-align: center">
										<h:outputText value="Diferencia" />
									</rich:column>
									<rich:column width="75px" style="text-align: center">
										<h:outputText value="Ingreso" />
									</rich:column>
									<rich:column width="75px" style="text-align: center">
										<h:outputText value="Diferencia" />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
                            <rich:column style="text-align: center">
								<h:outputText value="#{rowKey+1}"/>
							</rich:column>
							<rich:column>
								<h:outputText value="#{item.strCuentaPrimerDscto}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="intIdDetalle"
									itemLabel="strAbreviatura"
									property="#{item.strTipoSocio}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<tumih:outputText value="#{primerDescuentoController.listJuridicaSucursal}" 
									itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
									property="#{item.intIdSucursalSocio}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<tumih:outputText value="#{primerDescuentoController.listJuridicaSucursal}" 
									itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
									property="#{item.intIdSucursalAtencion}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.strTipoSolicitud}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.strNroSolicitud}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.strReprestamo}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.strFechaSolicitudPrimerDscto}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.strPrimerMes}"/>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdMontoSolicitudPrimerDscto}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdCapacidadSolicitudPrimerDscto}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdCuotaFijaSolicitudPrimerDscto}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdDsctoEnviado}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdDsctoEfectuado}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdDiferenciaDscto}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdIngresoCaja}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column style="text-align: center">
								<h:outputText value="#{item.bdDiferenciaCaja}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							
							<f:facet name="footer">
								
								<rich:columnGroup>
									<rich:column colspan="8" style="text-align: center">
										<h:outputText value="TOTALES" />
									</rich:column>
									<rich:column colspan="5" style="text-align: center">
										<h:outputText value="#{primerDescuentoController.bdTotMontoSolicitud}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{primerDescuentoController.bdTotEnviado}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{primerDescuentoController.bdTotEfectuado}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{primerDescuentoController.bdTotDiferenciaPlla}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="" />
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="" />
									</rich:column>
								</rich:columnGroup>	
                            </f:facet>
	                </rich:dataTable>
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
</h:form>