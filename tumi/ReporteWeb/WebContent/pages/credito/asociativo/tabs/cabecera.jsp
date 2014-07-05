<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : REPORTES                 -->
<!-- PROTOTIPO : ASOCIATIVO - CABECERA TAB's      -->			
<!-- FECHA     : 14.11.2013               -->


<h:panelGrid>
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
					<td>
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
		</rich:column>				
	</rich:columnGroup>
</h:panelGrid>
			