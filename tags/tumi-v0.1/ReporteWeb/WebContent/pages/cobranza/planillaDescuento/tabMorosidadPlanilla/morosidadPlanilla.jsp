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

<h:form id="frmPendienteCobro">
   	<rich:panel id="rpTabMorosidadPlanilla" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>MOROSIDAD DE PLANILLA</b>
			</div>
			<rich:spacer height="18px"/>
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td width="100px" align="left">
								<h:outputText value="Sucursal *:"/>
							</td>
							<td width="150px" align="left">
								<h:selectOneMenu id="cboSucursalPC" style="width: 140px;"
									onchange="getSubSucursalMP(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{morosidadPlanillaController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{morosidadPlanillaController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Sub Sucursal *:"/>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursalMP" style="width: 110px;"
						            value="#{morosidadPlanillaController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{morosidadPlanillaController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td width="100px">
								<h:outputText value="Periodo *:"/>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboAnioBusq" value="#{morosidadPlanillaController.intAnioBusqueda}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{morosidadPlanillaController.listYears}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboMesDesde" value="#{morosidadPlanillaController.intMes}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Tipo Sucursal :"/>
							</td>
							<td>
								<h:selectOneMenu id="idTipoSucursal" value="#{morosidadPlanillaController.intTipoSucursal}">
									<f:selectItem itemLabel="Planilla" itemValue="1"/>
									<f:selectItem itemLabel="Socio" itemValue="2"/>
								</h:selectOneMenu>
							</td>
						</tr>
					</table>
				</rich:column>			
			</rich:columnGroup>
			
			<br/>
			
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td>
								<h:selectBooleanCheckbox /> Adición (Inflada) Referencial
							</td>
							<td>
								<h:selectBooleanCheckbox /> Adición (Inflada) Ejecutada
							</td>
							<td width="400px">
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{morosidadPlanillaController.consultarMorosidadPlanilla}"
	          	 					reRender="dtPendienteCobroActivo,dtPendienteCobroCesante,pgTotGeneral,mpMessage"
	          	 					oncomplete="if(#{morosidadPlanillaController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{morosidadPlanillaController.limpiarTabMorosidadPlanilla}" 
									reRender="rpTabMorosidadPlanilla"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{morosidadPlanillaController.imprimirMorosidadPlanilla}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			<h:panelGrid>
				<rich:dataTable value="#{morosidadPlanillaController.listaMorosidadPlanilla}"
								styleClass="dataTable1" id="dtPendienteCobroActivo"
								rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
								var="item">
					<f:facet name="header">
						<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column width="300px" colspan="4" style="text-align: center">
								<h:outputText value="UNIDAD EJECUTORA / ENTIDAD" />
							</rich:column>
							<rich:column width="300px" colspan="4" style="text-align: center">
								<h:outputText value="ENVIADO" />
							</rich:column>
							<rich:column width="250px" colspan="3" style="text-align: center">
								<h:outputText value="EFECTUADO" />
							</rich:column>
							<rich:column width="150px" colspan="2" style="text-align: center">
								<h:outputText value="MOROSIDAD DE PLANILLA" />
							</rich:column>
							<rich:column width="300px" colspan="3" style="text-align: center">
								<h:outputText value="MOROSIDAD DE CAJA" />
							</rich:column>
							<rich:column width="50px" breakBefore="true" style="text-align: center">
								<h:outputText value="Nro." />
							</rich:column>
							<rich:column width="150px">
								<h:outputText value="Nombre" />
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<h:outputText value="Mod." />
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<h:outputText value="Tipo" />
							</rich:column>
							<rich:column width="75px" style="text-align: center">
								<h:outputText value="Adición Referencial" />
							</rich:column>
							<rich:column width="75px" style="text-align: center">
								<h:outputText value="Planilla Generada" />
							</rich:column>
							<rich:column width="75px" style="text-align: center">
								<h:outputText value="Adición Ejecutada" />
							</rich:column>
							<rich:column width="75px" style="text-align: center">
								<h:outputText value="Total Enviado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Planilla Ingresada" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Montos Adicional" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Total Efectuado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<h:outputText value="%" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Ingresos" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="%" />
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					
					<rich:column width="50px" style="text-align: center">
						<h:outputText value="#{rowKey+1}"/>
					</rich:column>
					<rich:column width="150px">
						<h:outputText value="#{item.strUnidadEjecutoraMP}"/>
					</rich:column>
					<rich:column width="50px" style="text-align: center">
						<h:outputText value="#{item.strModalidadMP}"/>
					</rich:column>
					<rich:column width="50px" style="text-align: center">
						<h:outputText value="#{item.strTipoMP}"/>
					</rich:column>
					<rich:column width="75px" style="text-align: center">
						<h:outputText value="#{item.bdAdicionReferencialMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="75px" style="text-align: center">
						<h:outputText value="#{item.bdPlanillaGeneradaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="75px" style="text-align: center">
						<h:outputText value="#{item.bdAdicionEjecutadaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="75px" style="text-align: center">
						<h:outputText value="#{item.bdTotalEnviadaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdPlanillaIngresadaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdMontoAdicionalMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdTotalEfectuadaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdMorosidadDiferenciaMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="50px" style="text-align: center">
						<h:outputText value="#{item.bdMorosidadPorcMP}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText />
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText />
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText />
					</rich:column>
					
					<f:facet name="footer">
						<rich:columnGroup>
							<rich:column width="300px" colspan="4" style="text-align: center">
								<b><h:outputText value="TOTAL" /></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumAdicReferencial}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumPllaGenerada}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumAdicEjecutada}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumTotalEnviado}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumPllaIngresada}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMontoAdicional}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumTotalEfectuado}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMorPllaDiferencia}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMorPllaPorc}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMorCajaIngresos}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMorCajaDiferencia}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: center">
								<b><h:outputText value="#{morosidadPlanillaController.bdSumMorCajaPorc}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
				</rich:dataTable>
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
</h:form>