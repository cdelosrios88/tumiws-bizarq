<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : REPORTE                 -->
<!-- PROTOTIPO : ASOCIATIVO - TAB PADRONES DE TRABAJADORES      -->			
<!-- FECHA     : 08.11.2013               -->

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; ">
	<h:outputLabel value="#{asociativoController.inicioPage}"/>
	<rich:panel id="rpTabPadrones" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>PADRONES DE TRABAJADORES</b>
			</div>
			<rich:spacer height="18px"/>
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td width="10px" align="left">
								<h:selectOneRadio id="rbOpcAP" value="#{asociativoController.rbPeriodoOpcA}" onclick="selectRbBusqueda(this.value)">								
				  	 				<f:selectItem id="itemR1" itemValue="1"/>
				  	 			</h:selectOneRadio>
				  	 		</td>
							<td width="65px" align="left">
								<b><h:outputText value="Sucursal:"/></b>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="cboSucursalP" style="width: 140px;"
									value="#{asociativoController.intIdSucursal}"
									disabled="#{asociativoController.blnDisabledSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{asociativoController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="10px" align="left">
								<h:selectOneRadio id="rbOpcBP" value="#{asociativoController.rbPeriodoOpcB}" onclick="selectRbBusqueda(this.value)">								
				  	 				<f:selectItem id="itemR2" itemValue="2"/>
				  	 			</h:selectOneRadio>
				  	 		</td>
							<td width="100px" align="left">
								<b><h:outputText value="Departamento:"/></b>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="cboDepartamento" style="width: 140px;"
									value="#{asociativoController.intDepartamento}" 
									disabled="#{asociativoController.blnDisabledDepartamento}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<b><h:outputText value="Tipo Reporte:"/></b>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu value="#{asociativoController.intTipoSocio}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
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
									action="#{asociativoController.consultarRenuncias}"
	          	 					reRender="opGrillaPadrones,mpMessage"
	          	 					oncomplete="if(#{asociativoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{asociativoController.limpiarTabPadrones}" 
									reRender="rpTabPadrones"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{asociativoController.imprimirReportePadrones}"/>
							</td>
						</tr>
					</table>
				</rich:column>				
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			
			<a4j:outputPanel id="opGrillaPadrones">
				<h:panelGrid>
						<rich:dataTable value="#{asociativoController.listaRenuncias}" 
										styleClass="dataTable1" id="dtResumenPadrones"
										rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
										var="itemGrilla">
							<f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="1200px" colspan="9" style="text-align: center">
										<h:outputText value="Ministerio: " />
									</rich:column>
									<rich:column width="400px" breakBefore="true" rowspan="2" style="text-align: center">
										<h:outputText value="Unidad Ejecutora" />
									</rich:column>
									<rich:column width="400px" colspan="4" style="text-align: center">
										<h:outputText value="Padrón" />
									</rich:column>
									<rich:column width="400px" colspan="4" style="text-align: center">
										<h:outputText value="Socios" />
									</rich:column>
									<rich:column width="100px" breakBefore="true" style="text-align: center">
										<h:outputText value="Total" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Activos" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Cesantes" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="CAS" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Total" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Activos" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Cesantes" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="CAS" />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
						</rich:dataTable>
				</h:panelGrid>
			</a4j:outputPanel>
		</h:panelGrid>
	</rich:panel>
</h:form>