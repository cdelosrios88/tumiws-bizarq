<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 23/03/2014               	-->

<h:form id="frmPendienteCobro">
   	<rich:panel id="rpTabSociosDiferencia" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>SOCIOS CON DIFERENCIA</b>
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
								<h:selectOneMenu id="cboSucursalSD" style="width: 140px;"
									onchange="getSubSucursalSD(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{socioDiferenciaController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{socioDiferenciaController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Sub Sucursal *:"/>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursalSD" style="width: 110px;"
									onchange="getListUnidEjecutora(#{applicationScope.Constante.ONCHANGE_VALUE})"
						            value="#{socioDiferenciaController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{socioDiferenciaController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td width="150px" align="left">
								<h:outputText value="Unidad Ejecutora / Entidad (administra) :"/>
							</td>
							<td>
								<h:selectOneMenu id="cboEntidad" style="width:300px;"
									value="#{socioDiferenciaController.intIdUnidadEjecutora}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{socioDiferenciaController.listaUnidadEjecutora}"
										itemValue="#{sel.intIdUnidadEjecutora}" itemLabel="#{sel.strDescUnidadEjecutora}" propertySort="strDescUnidadEjecutora"/>
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
							<td width="100px">
								<h:outputText value="Periodo de Consulta*:"/>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboMes" value="#{socioDiferenciaController.intMes}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboAnioBusq" value="#{socioDiferenciaController.intAnioBusqueda}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{socioDiferenciaController.listYears}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px">
								<h:outputText value="Tipo de Diferencia :"/>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="idTipoDiferencia" value="#{socioDiferenciaController.intIdTipoDiferencia}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<f:selectItem itemLabel="Descuento Normal" itemValue="1"/>
			          	 			<f:selectItem itemLabel="Diferencia de Menos" itemValue="2"/>
			          	 			<f:selectItem itemLabel="Exceso de Descuento" itemValue="3"/>
			          	 			<f:selectItem itemLabel="No descontados" itemValue="4"/>
								</h:selectOneMenu>
							</td>
							<td width="100px">
								<h:outputText value="Tipo de Socio :"/>
							</td>
							<td align="left">
								<h:selectOneMenu id="idTipoSocio" value="#{socioDiferenciaController.intParaTipoSocio}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px">
								<h:outputText value="Modalidad :"/>
							</td>
							<td align="left">
								<h:selectOneMenu id="idModalidad" value="#{socioDiferenciaController.intParaModalidad}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
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
							<td width="400px">
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{socioDiferenciaController.consultarSocioDiferencia}"
	          	 					reRender="dtSociosDiferencia,mpMessage"
	          	 					oncomplete="if(#{socioDiferenciaController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{socioDiferenciaController.limpiarTabSocioDiferencia}"
									reRender="rpTabSociosDiferencia"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{socioDiferenciaController.imprimirSocioDiferencia}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<br/>
			
			<h:panelGrid>
				<rich:dataTable value="#{socioDiferenciaController.listaSocioDiferencia}"
								styleClass="dataTable1" id="dtSociosDiferencia"
								rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
								var="item" rows="25">
					<f:facet name="header">
						<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column width="50px" rowspan="2" style="text-align: center">
								<h:outputText value="Nro."/>
							</rich:column>
							<rich:column width="300px" rowspan="2" style="text-align: center">
								<h:outputText value="Código - Apellidos y Nombres" />
							</rich:column>
							<rich:column width="50px" rowspan="2" style="text-align: center">
								<h:outputText value="Tipo" />
							</rich:column>
							<rich:column width="50px" rowspan="2" style="text-align: center">
								<h:outputText value="Modalidad" />
							</rich:column>
							<rich:column width="300px" colspan="3" style="text-align: center">
								<h:outputText value="Planilla" />
							</rich:column>
							<rich:column width="200px" colspan="2" style="text-align: center">
								<h:outputText value="Ingresos por Caja" />
							</rich:column>
							<rich:column width="200px" colspan="2" style="text-align: center">
								<h:outputText value="Transferencias" />
							</rich:column>
							<rich:column width="100px" breakBefore="true" style="text-align: center">
								<h:outputText value="Enviado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Ingresos" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Ingreso" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					
					<rich:column style="text-align:center">
						<h:outputText value="#{rowKey+1}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strCuenta}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strTipoSD}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strModalidadSD}"/>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdEnviadoSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdEfectuadoSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdDiferenciaPllaSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdIngresoCajaSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdDiferenciaCajaSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdIngresoTransferenciaSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column style="text-align:center">
						<h:outputText value="#{item.bdDiferenciaTransferenciaSD}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller for="dtSociosDiferencia" maxPages="10"/>
					</f:facet>
				</rich:dataTable>
				
				<%--<rich:columnGroup>
					<rich:column width="450px" colspan="4" style="text-align: center">
						<b><h:outputText value="TOTAL" /></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumPllaEnviada}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumPllaEfectuada}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumPllaDiferencia}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumIngresoCaja}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumIngresoCajaDiferencia}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumTransferenciaIngreso}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<b><h:outputText value="#{socioDiferenciaController.bdSumTransferenciaIngresoDiferencia}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText></b>
					</rich:column>
				</rich:columnGroup>--%>
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
</h:form>