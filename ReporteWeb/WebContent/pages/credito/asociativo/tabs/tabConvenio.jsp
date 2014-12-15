<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : REPORTE                 -->
<!-- PROTOTIPO : ASOCIATIVO - TAB CONVENIOS      -->			
<!-- FECHA     : 18.11.2013               -->

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; ">
	<rich:panel id="rpTabConvenios" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5" rendered="#{asociativoController.blnShowPanelTabConvenios}">
		<div align="center">
			<b>CONVENIOS</b>
		</div>
		<br/>
		<table width="520px" border="0" align="center">
			<tr>
				<td width="65px" align="left">
					<b><h:outputText value="Sucursal:"/></b>
				</td>
					<h:selectOneMenu id="cboSucursalC" style="width: 140px;"
						value="#{asociativoController.intIdSucursal}"
						disabled="#{asociativoController.blnDisabledSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{asociativoController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
					</h:selectOneMenu>
				</td>
				<td width="300px" align="left">
					<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
						action="#{asociativoController.consultarConvenios}"
        	 					reRender="rpTabConvenios"/>
					<rich:spacer width="5"/>
					<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{asociativoController.limpiarTabConvenios}" 
						reRender="rpTabConvenios"/>
					<rich:spacer width="5"/>
					<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{asociativoController.imprimirReporteConvenios}"/>
				</td>
			</tr>
		</table>
		<br/>
		<div align="center">
			<table>
				<tr>
					<td align="center">
						<!-- Autor: jchavez / Tarea: modificación de cabecera / Fecha: 20.09.2014 -->
						<rich:extendedDataTable id="edtConvenios" enableContextMenu="false" sortMode="single" 
										var="itemGrilla" value="#{asociativoController.listaConvenios}" 
						                rendered="#{not empty asociativoController.listaConvenios}" 
						                rowKeyVar="rowKey" width="1320px" height="400px">
							<f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="300px" rowspan="2" style="text-align: center">
										<h:outputText value="Entidad de Descuento" />
									</rich:column>
									<rich:column width="100px" rowspan="2" style="text-align: center">
										<h:outputText value="RUC" />
									</rich:column>
									<rich:column width="100px" rowspan="2" style="text-align: center">
										<h:outputText value="Tipo Socio" />
									</rich:column>
									<rich:column width="150px" colspan="3" style="text-align: center">
										<h:outputText value="Modalidad" />
									</rich:column>
									<rich:column width="400px" colspan="3" style="text-align: center">
										<h:outputText value="Vigencia" />
									</rich:column>
									<rich:column width="100px" colspan="2" style="text-align: center">
										<h:outputText value="Retención" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Clausula Cobranza" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Documento Físico" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Por Vencer (90 días)" />
									</rich:column>
									<rich:column width="50px" breakBefore="true" style="text-align: center">
										<h:outputText value="Hab." />
									</rich:column>
									<rich:column width="50px" style="text-align: center">
										<h:outputText value="Inc." />
									</rich:column>
									<rich:column width="50px" style="text-align: center">
										<h:outputText value="CAS" />
									</rich:column>
									<rich:column width="220px" style="text-align: center">
										<h:outputText value="Tiempo" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="F.Inicio" />
									</rich:column>
									<rich:column width="100px" style="text-align: center">
										<h:outputText value="F.Final" />
									</rich:column>
									<rich:column width="50px" style="text-align: center">
										<h:outputText value="%" />
									</rich:column>
									<rich:column width="50px" style="text-align: center">
										<h:outputText value="S/." />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
								<rich:column width="300px" style="text-align: center">
									<h:outputText id="lblEntidadDesc" value="#{itemGrilla.strEntidadDesc}" />
									<rich:toolTip for="lblEntidadDesc" value="#{itemGrilla.strEntidadDesc}"></rich:toolTip>
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strNroRuc}" />
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
						   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
									    property="#{itemGrilla.intTipoSocio}"/>
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strModalidadHaberes}" />
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strModalidadIncentivos}" />
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strModalidadCas}" />
								</rich:column>
								<rich:column width="120px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strTiempoVigencia}" />
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strFechaInicio}" />
								</rich:column>
								<rich:column width="100px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strFechaCese}" />
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strRetencionPorcentaje}" />
								</rich:column>
								<rich:column width="50px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strRetencionMonto}" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strClausulaCobranza}" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="#{itemGrilla.strDocumentoFisico}" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<b><h:outputText style="color: red" value="#{itemGrilla.strAlertaPorVencimiento}" /></b>
								</rich:column>
						</rich:extendedDataTable>
					</td>
				</tr>
			</table>
		</div>
	</rich:panel>
</h:form>