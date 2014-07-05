<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 27/11/2013               	-->

<h:form id="frmPrestamos">
   	<rich:panel id="rpTabSaldoCaptaciones" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>SALDOS DE SERVICIOS</b>
			</div>
			<rich:spacer height="18px"/>
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td width="65px" align="left">
								<b><h:outputText value="Sucursal:"/></b>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="cboSucursal" style="width: 140px;"
									onchange="getSubSucursalSaldoServ(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{saldoServicioController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{saldoServicioController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<b><h:outputText value="Sub Sucursal:"/></b>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursalSaldo" style="width: 110px;"
						            value="#{saldoServicioController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{saldoServicioController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td align="left">
								<b><h:outputText value="Tipo de Servicio:"/></b>
							</td>
							<td>
								<h:selectOneMenu value="#{saldoServicioController.intTipoServicio}"
									onchange="getTipoCreditoEmpresa(#{applicationScope.Constante.ONCHANGE_VALUE})">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</td>
							<td>
								<h:selectOneMenu id="cboCreditoTipoEmpresa" value="#{saldoServicioController.intTipoCreditoEmpresa}">
				                       <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				                       <tumih:selectItems var="sel" value="#{saldoServicioController.listaTipoCreditoEmpresa}" 
				                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
				                       propertySort="intOrden"/>
				                </h:selectOneMenu>
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
									action="#{saldoServicioController.consultarSaldos}"
	          	 					reRender="opGrillaSaldoServicios,mpMessage"
	          	 					oncomplete="if(#{saldoServicioController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{saldoServicioController.limpiarTabCaptaciones}" 
									reRender="rpTabCaptaciones"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{saldoServicioController.imprimirSaldoServicio}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			
			<a4j:outputPanel id="opGrillaSaldoServicios">
				<h:panelGrid>
						<rich:dataTable id="dtSaldoServicios" styleClass="dataTable1"
							value="#{saldoServicioController.listaSaldoServicios}"
	                   		rows="5" 
	                   		rendered="#{not empty saldoServicioController.listaSaldoServicios}"
	                   		var="item" rowKeyVar="rowKey" width="1050px">
	                   			<f:facet name="header">
									<rich:columnGroup columnClasses="rich-sdt-header-cell">
										<rich:column width="60px" rowspan="2" style="text-align: center">
											<h:outputText value="Nro." />
										</rich:column>
										<rich:column width="300px" rowspan="2" style="text-align: center">
											<h:outputText value="Cuenta - Apellido y Nombre" />
										</rich:column>
										<rich:column width="300px" colspan="3" style="text-align: center">
											<h:outputText value="Solicitud de Servicio" />
										</rich:column>
										<rich:column width="300px" colspan="3" style="text-align: center">
											<h:outputText value="Monto" />
										</rich:column>
										<rich:column width="200px" colspan="2" style="text-align: center">
											<h:outputText value="Número de Cuotas" />
										</rich:column>
										<rich:column width="100px" breakBefore="true" style="text-align: center">
											<h:outputText value="Fecha" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Tipo" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Número" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Solicitud" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Cancelado" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Saldo" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Totales" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Faltantes" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
	                            <rich:column style="text-align: center">
									<h:outputText value="#{rowKey+1}"/>
								</rich:column>
								<rich:column>
									<h:outputText value="#{item.strCuentaSaldo}"/>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.strFechaSolicitudSaldo}"/>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.strTipoSaldo}"/>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.strNumeroSolicitudServicio}"/>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.bdMontoSolicitud}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.bdMontoCancelado}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.bdMontoSaldo}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.intNroCuotasTotales}"/>
								</rich:column>
								<rich:column style="text-align: center">
									<h:outputText value="#{item.intCuotasFaltantes}"/>
								</rich:column>
								<f:facet name="footer">
	                            	<rich:datascroller for="dtSaldoServicios" maxPages="10"/>
	                            </f:facet>
		                </rich:dataTable>
				</h:panelGrid>
			</a4j:outputPanel>
		</h:panelGrid>
	</rich:panel>
</h:form>