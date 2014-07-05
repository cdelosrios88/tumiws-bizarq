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
   	<rich:panel id="rpTabPendienteCobro" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>PENDIENTES DE COBRO</b>
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
									onchange="getSubSucursalPC(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{pendienteCobroController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{pendienteCobroController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Sub Sucursal *:"/>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursalPC" style="width: 110px;"
						            value="#{pendienteCobroController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{pendienteCobroController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Modalidad :"/>
							</td>
							<td>
								<h:selectOneMenu id="idModalidad" value="#{pendienteCobroController.intModalidad}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Fecha de Consulta *:"/>
							</td>
							<td>
								<rich:calendar datePattern="dd/MM/yyyy"  
									value="#{pendienteCobroController.dtFechaConsulta}"  
									jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
							</td>
						</tr>
					</table>
				</rich:column>			
			</rich:columnGroup>
			
			<div align="left">
				<h:outputText value="* NOTA: SE ESTÁ CONSIDERANDO LA SUCURSAL PLANILLA" style="color:blue;font-weight:bold;"/>
			</div>
			
			<rich:spacer height="6px"/>
			
			<rich:columnGroup>
				<rich:column width="1200px" style="text-align: center">
					<table>
						<tr>
							<td>
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{pendienteCobroController.consultarPendienteCobro}"
	          	 					reRender="dtPendienteCobroActivo,dtPendienteCobroCesante,pgTotGeneral,mpMessage"
	          	 					oncomplete="if(#{pendienteCobroController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{pendienteCobroController.limpiarTabCaptaciones}" 
									reRender="rpTabPllaDesagregado"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{pendienteCobroController.imprimirPendienteCobro}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			<h:panelGrid>
				<rich:dataTable value="#{pendienteCobroController.listaPendienteCobroActivos}"
								styleClass="dataTable1" id="dtPendienteCobroActivo"
								rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
								var="item">
					<f:facet name="header">
						<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column width="900px" colspan="7" style="text-align: center">
								<h:outputText value="ACTIVOS" />
							</rich:column>
							<rich:column width="350px" breakBefore="true" style="text-align: center">
								<h:outputText value="Unidad Ejecutora / Institución" />
							</rich:column>
							<rich:column width="70px" style="text-align: center">
								<h:outputText value="Periodo" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="Modalidad" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Pagado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Saldo" />
							</rich:column>
							<rich:column width="100px" style="text-align: center"/>
						</rich:columnGroup>
					</f:facet>
					
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strUnidadEjecutoraPC}"/>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strPeriodoPC}"/>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strModalidadPC}"/>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdEfectuadoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdPagadoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdSaldoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText />
					</rich:column>
					
					<f:facet name="footer">     
						<rich:columnGroup>
							<rich:column width="350px" colspan="3" style="text-align: center">
								<b><h:outputText value="Total Activos" /></b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotEfectuadoActivo}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotPagadoActivo}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotSaldoActivo}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
				</rich:dataTable>
				
				<br/>
				
				<rich:dataTable value="#{pendienteCobroController.listaPendienteCobroCesantes}"
								styleClass="dataTable1" id="dtPendienteCobroCesante"
								rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
								var="item">
					<f:facet name="header">
						<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column width="900px" colspan="7" style="text-align: center">
								<h:outputText value="CESANTES" />
							</rich:column>
							<rich:column width="350px" breakBefore="true" style="text-align: center">
								<h:outputText value="Unidad Ejecutora / Institución" />
							</rich:column>
							<rich:column width="70px" style="text-align: center">
								<h:outputText value="Periodo" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="Modalidad" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Pagado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Saldo" />
							</rich:column>
							<rich:column width="100px" style="text-align: center"/>
						</rich:columnGroup>
					</f:facet>
					
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strUnidadEjecutoraPC}"/>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strPeriodoPC}"/>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{item.strModalidadPC}"/>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdEfectuadoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdPagadoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: right">
						<h:outputText value="#{item.bdSaldoPC}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText />
					</rich:column>
					
					<f:facet name="footer">     
						<rich:columnGroup>
							<rich:column width="350px" colspan="3" style="text-align: center">
								<b><h:outputText value="Total Cesantes" /></b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotEfectuadoCesante}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
								</b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotPagadoCesante}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
								</b>
							</rich:column>
							<rich:column style="text-align: right">
								<b><h:outputText value="#{pendienteCobroController.bdTotSaldoCesante}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
				</rich:dataTable>
				<br/>
				
				<h:panelGrid id="pgTotGeneral" columns="4">					
					<rich:column width="520px" style="text-align: center">
						<b><h:outputText value="TOTAL GENERAL" /></b>
					</rich:column>
					<rich:column width="100px" style="text-align: right">
						<b><h:outputText value="#{pendienteCobroController.bdTotEfectuado}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: right">
						<b><h:outputText value="#{pendienteCobroController.bdTotPagado}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: right">
						<b><h:outputText value="#{pendienteCobroController.bdTotSaldo}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
				</h:panelGrid>
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
</h:form>