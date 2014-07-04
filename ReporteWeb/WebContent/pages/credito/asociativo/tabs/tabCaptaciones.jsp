<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : REPORTE                 -->
<!-- PROTOTIPO : ASOCIATIVO - TAB CAPTACIONES      -->			
<!-- FECHA     : 08.11.2013               -->

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; ">
	<rich:panel id="rpTabCaptaciones" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5" rendered="#{asociativoController.blnShowPanelTabCaptaciones}">
		<div align="center">
			<b>CAPTACIONES</b>
		</div>
		<br/>
		<table width="900px" border="0" align="center">
			<tr>
				<td width="65px" align="left">
					<b><h:outputText value="Sucursal:"/></b>
				</td>
				<td width="150px" align="left">
					<h:selectOneMenu id="cboSucursal" style="width: 140px;"
						onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})"
						value="#{asociativoController.intIdSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{asociativoController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
					</h:selectOneMenu>
				</td>
				<td width="95px" align="left">
					<b><h:outputText value="Sub Sucursal:"/></b>
				</td>
				<td width="120px" align="left">
					<h:selectOneMenu id="cboSubSucursal" style="width: 110px;"
			            value="#{asociativoController.intIdSubSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{asociativoController.listJuridicaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</td>
				<td width="95px" align="left">
					<b><h:outputText value="Tipo de Socio:"/></b>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="idTipoSocio" value="#{asociativoController.intTipoSocio}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="75px" align="left">
					<b><h:outputText value="Modalidad:"/></b>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="idModalidad" value="#{asociativoController.intModalidad}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
			</tr>
		</table>
		<br/>
		<table width="900px" border="0" align="center">
			<tr>
				<td width="50px" align="left">
					<b><h:outputText value="Periodo:"/></b>
				</td>
				<td width="10px" align="left">
					<h:selectOneRadio id="rbOpcA" value="#{asociativoController.rbPeriodoOpcA}" onclick="selectRbPeriodo(this.value)">								
	  	 				<f:selectItem id="item1" itemValue="1"/>
	  	 			</h:selectOneRadio>
	  	 		</td>
	  	 		<td width="100px" align="left">
					<h:selectOneMenu id="cboMesDesde" value="#{asociativoController.intMesDesde}"
						disabled="#{asociativoController.blnDisabledMesDesde}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboMesHasta" value="#{asociativoController.intMesHasta}"
						disabled="#{asociativoController.blnDisabledMesHasta}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboAnioBusq" value="#{asociativoController.intAnioBusqueda}"
						disabled="#{asociativoController.blnDisabledAnioBusqueda}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<f:selectItems id="lstYears" value="#{asociativoController.listYears}"/>
					</h:selectOneMenu>
				</td>
				<td width="10px" align="left">
					<h:selectOneRadio id="rbOpcB" value="#{asociativoController.rbPeriodoOpcB}" onclick="selectRbPeriodo(this.value)">								
	  	 				<f:selectItem id="item2" itemValue="2"/>
	  	 			</h:selectOneRadio>
	  	 		</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboRangoPeriodo" value="#{asociativoController.intRangoPeriodo}"
						disabled="#{asociativoController.blnDisabledRangoPeriodo}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FREQCONSULTA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="300px" align="left">
					<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
						action="#{asociativoController.consultarCaptaciones}"
        	 					reRender="opGrillaCaptaciones,mpMessage"
        	 					oncomplete="if(#{asociativoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
					<rich:spacer width="5"/>
					<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{asociativoController.limpiarTabCaptaciones}" 
						reRender="rpTabCaptaciones"/>
					<rich:spacer width="5"/>
					<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{asociativoController.imprimirReporteCaptaciones}"/>
				</td>
			</tr>
		</table>
		<br/>
		<div align="center">
			<table>
				<tr>
					<td align="center">
						<a4j:outputPanel id="opGrillaCaptaciones">
							<rich:dataTable value="#{asociativoController.listaCaptaciones}" 
											styleClass="dataTable1" id="dtCaptaciones"
											rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
											var="itemGrilla"
											rendered="#{not empty asociativoController.listaCaptaciones}">
								<f:facet name="header">
									<rich:columnGroup columnClasses="rich-sdt-header-cell">
										<rich:column width="150px" rowspan="2" style="text-align: center">
											<h:outputText value="Mes - Año" />
										</rich:column>
										<rich:column width="300px" colspan="3" style="text-align: center">
											<h:outputText value="Captaciones" />
										</rich:column>
										<rich:column width="200px" colspan="2" style="text-align: center">
											<h:outputText value="Cambio de Condición" />
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
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Altas" />
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<h:outputText value="Total" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
								<rich:column width="150px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strFmtPeriodo}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intCantProyectadas}"/>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<a4j:commandLink id="lnkDetalle" value="#{itemGrilla.intCantEjecutadas}" 
										actionListener="#{asociativoController.detalleCaptacionesEjecutadas}" 
										oncomplete="Richfaces.showModalPanel('mpDetalleCaptacionesEjecutadas')"
										reRender="mpDetalleCaptacionesEjecutadas">
										<f:attribute name="itemGrilla" value="#{itemGrilla}"/>
									</a4j:commandLink>
								</rich:column>							
								<rich:column width="100px" style="text-align: center">
									<h:outputText style="color: blue" value="#{itemGrilla.intDiferencia}" rendered="#{itemGrilla.intDiferencia > 0}"/>
									<h:outputText style="color: green" value="#{itemGrilla.intDiferencia}" rendered="#{itemGrilla.intDiferencia == 0}"/>
									<h:outputText style="color: red" value="#{itemGrilla.intDiferencia}" rendered="#{itemGrilla.intDiferencia < 0}"/>
								</rich:column>
								
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.intCantCondAltas}"/>
								</rich:column>
								
								<rich:column width="100px" style="text-align: center;">
									<h:outputText style="color: blue" value="#{itemGrilla.intTotalCaptaciones}" rendered="#{itemGrilla.intTotalCaptaciones > 0}"/>
									<h:outputText style="color: green" value="#{itemGrilla.intTotalCaptaciones}" rendered="#{itemGrilla.intTotalCaptaciones == 0}"/>
									<h:outputText style="color: red" value="#{itemGrilla.intTotalCaptaciones}" rendered="#{itemGrilla.intTotalCaptaciones < 0}"/>
								</rich:column>
	
								<f:facet name="footer">     
									<rich:columnGroup>
										<rich:column width="150px" style="text-align: center">
											<b><h:outputText value="TOTAL" /></b>
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<b><h:outputText value="#{asociativoController.intSumatoriaProyectado}" /></b>
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<b><h:outputText value="#{asociativoController.intSumatoriaEjecutado}" /></b>
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<b><h:outputText style="color: blue" value="#{asociativoController.intSumatoriaDiferencia}" rendered="#{asociativoController.intSumatoriaDiferencia > 0}"/></b>
											<b><h:outputText style="color: green" value="#{asociativoController.intSumatoriaDiferencia}" rendered="#{asociativoController.intSumatoriaDiferencia == 0}"/></b>
											<b><h:outputText style="color: red" value="#{asociativoController.intSumatoriaDiferencia}" rendered="#{asociativoController.intSumatoriaDiferencia < 0}"/></b>
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<b><h:outputText value="#{asociativoController.intSumatoriaAltas}" /></b>
										</rich:column>
										<rich:column width="100px" style="text-align: center">
											<b><h:outputText style="color: blue" value="#{asociativoController.intSumatoriaTotal}" rendered="#{asociativoController.intSumatoriaTotal > 0}"/></b>
											<b><h:outputText style="color: green" value="#{asociativoController.intSumatoriaTotal}" rendered="#{asociativoController.intSumatoriaTotal == 0}"/></b>
											<b><h:outputText style="color: red" value="#{asociativoController.intSumatoriaTotal}" rendered="#{asociativoController.intSumatoriaTotal < 0}"/></b>
										</rich:column>
									</rich:columnGroup>
							   	</f:facet>
							</rich:dataTable>
						</a4j:outputPanel>
					</td>
				</tr>
			</table>
		</div>
	</rich:panel>
</h:form>