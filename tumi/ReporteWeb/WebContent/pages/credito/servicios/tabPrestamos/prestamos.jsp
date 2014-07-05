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
   	<rich:panel id="rpTabCaptaciones" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>PRÉSTAMOS</b>
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
									onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{prestamoController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{prestamoController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<b><h:outputText value="Sub Sucursal:"/></b>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursal" style="width: 110px;"
						            value="#{prestamoController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{prestamoController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td width="145px" align="left">
								<h:selectBooleanCheckbox id="chkRefinanciados" value="#{prestamoController.blnRefinanciados}"/>Considerar Refinanciados
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
							<td width="50px" align="left">
								<b><h:outputText value="Periodo:"/></b>
							</td>
							<td width="10px" align="left">
								<h:selectOneRadio id="rbOpcA" value="#{prestamoController.rbPeriodoOpcA}" onclick="selectRbPeriodo(this.value)">								
				  	 				<f:selectItem id="item1" itemValue="1"/>
				  	 			</h:selectOneRadio>
				  	 		</td>
				  	 		<td width="100px" align="left">
								<h:selectOneMenu id="cboMesDesde" value="#{prestamoController.intMesDesde}"
									disabled="#{prestamoController.blnDisabledMesDesde}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboMesHasta" value="#{prestamoController.intMesHasta}"
									disabled="#{prestamoController.blnDisabledMesHasta}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboAnioBusq" value="#{prestamoController.intAnioBusqueda}"
									disabled="#{prestamoController.blnDisabledAnioBusqueda}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{prestamoController.listYears}"/>
								</h:selectOneMenu>
							</td>
							<td width="10px" align="left">
								<h:selectOneRadio id="rbOpcB" value="#{prestamoController.rbPeriodoOpcB}" onclick="selectRbPeriodo(this.value)">								
				  	 				<f:selectItem id="item2" itemValue="2"/>
				  	 			</h:selectOneRadio>
				  	 		</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboRangoPeriodo" value="#{prestamoController.intRangoPeriodo}"
									disabled="#{prestamoController.blnDisabledRangoPeriodo}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FREQCONSULTA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td>
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{prestamoController.consultarPrestamo}"
	          	 					reRender="opGrillaCaptaciones,mpMessage"
	          	 					oncomplete="if(#{prestamoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{prestamoController.limpiarTabCaptaciones}" 
									reRender="rpTabCaptaciones"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{prestamoController.imprimirPrestamo}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			
			<a4j:outputPanel id="opGrillaCaptaciones">
				<h:panelGrid rendered="#{not empty prestamoController.listaCaptaciones}">
						<rich:dataTable value="#{prestamoController.listaCaptaciones}" 
										styleClass="dataTable1" id="dtCaptaciones"
										rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
										var="itemGrilla">
							<f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="150px" rowspan="2" style="text-align: center">
										<h:outputText value="Mes - Año" />
									</rich:column>
									<rich:column width="300px" colspan="3" style="text-align: center">
										<h:outputText value="Préstamos" />
									</rich:column>
									<rich:column width="100px" breakBefore="true" style="text-align: center">
										<h:outputText value="Proyectado" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Ejecutado" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="Diferencia" />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
							<rich:column width="150px" style="text-align: center">
								<h:outputText value="#{itemGrilla.strFmtPeriodo}"/>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="#{itemGrilla.bdProyectado}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<a4j:commandLink id="lnkDetalle" value="#{itemGrilla.bdMontoEjecutado}" 
									actionListener="#{prestamoController.detalleCaptacionesEjecutadas}" 
									oncomplete="Richfaces.showModalPanel('mpDetalleCaptacionesEjecutadas')"
									reRender="mpDetalleCaptacionesEjecutadas">
									<f:attribute name="itemGrilla" value="#{itemGrilla}"/>
								</a4j:commandLink>
							</rich:column>							
							<rich:column width="100px" style="text-align: center">
								<h:outputText style="color: blue" value="#{itemGrilla.bdDiferencia}" rendered="#{itemGrilla.bdDiferencia > 0}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
								<h:outputText style="color: green" value="#{itemGrilla.bdDiferencia}" rendered="#{itemGrilla.bdDiferencia == 0}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
								<h:outputText style="color: red" value="#{itemGrilla.bdDiferencia}" rendered="#{itemGrilla.bdDiferencia < 0}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>

							<f:facet name="footer">     
								<rich:columnGroup>
									<rich:column width="150px" style="text-align: center">
										<b><h:outputText value="TOTAL" /></b>
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<b><h:outputText value="#{prestamoController.bdSumatoriaProyectado}" >
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText></b>
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<b><h:outputText value="#{prestamoController.bdSumatoriaEjecutado}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText></b>
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<b><h:outputText style="color: blue" value="#{prestamoController.bdSumatoriaDiferencia}" rendered="#{prestamoController.bdSumatoriaDiferencia > 0}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText></b>
										<b><h:outputText style="color: green" value="#{prestamoController.bdSumatoriaDiferencia}" rendered="#{prestamoController.bdSumatoriaDiferencia == 0}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText></b>
										<b><h:outputText style="color: red" value="#{prestamoController.bdSumatoriaDiferencia}" rendered="#{prestamoController.bdSumatoriaDiferencia < 0}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText></b>
									</rich:column>
								</rich:columnGroup>
						   	</f:facet>
						</rich:dataTable>
				</h:panelGrid>
			</a4j:outputPanel>
		</h:panelGrid>
	</rich:panel>
</h:form>