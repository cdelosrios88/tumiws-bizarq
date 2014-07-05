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

<h:form id="frmPlanillaDescuento">
   	<rich:panel id="rpTabPllaDesagregado" style="border:1px solid #17356f;width: 99%;background-color:#DEEBF5">
		<h:panelGrid>
			<div align="center">
				<b>CONTROL DE PLANILLAS DESAGREGADO</b>
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
								<h:selectOneMenu id="cboSucursal" style="width: 140px;"
									onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})"
									value="#{planillaDescuentoController.intIdSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{planillaDescuentoController.listJuridicaSucursal}"
										itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Sub Sucursal *:"/>
							</td>
							<td width="120px" align="left">
								<h:selectOneMenu id="cboSubSucursal" style="width: 110px;"
						            value="#{planillaDescuentoController.intIdSubSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{planillaDescuentoController.listJuridicaSubsucursal}"
										itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Tipo de Socio :"/>
							</td>
							<td>
								<h:selectOneMenu id="idTipoSocio" value="#{planillaDescuentoController.intTipoSocio}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							<td width="95px" align="left">
								<h:outputText value="Modalidad :"/>
							</td>
							<td>
								<h:selectOneMenu id="idModalidad" value="#{planillaDescuentoController.intModalidad}">
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
							<td width="100px">
								<h:outputText value="Tipo de Sucursal:"/>
							</td>
							<td align="left">
								<h:selectOneMenu id="cboTipoSucursal" value="#{planillaDescuentoController.intTipoSucursal}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItem itemLabel="Socio" itemValue="1"/>
									<f:selectItem itemLabel="Planilla" itemValue="2"/>
								</h:selectOneMenu>
							</td>
							<td width="100px">
								<h:outputText value="Periodo *:"/>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboAnioBusq" value="#{planillaDescuentoController.intAnioBusqueda}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{planillaDescuentoController.listYears}"/>
								</h:selectOneMenu>
							</td>
							<td width="100px" align="left">
								<h:selectOneMenu id="cboMesDesde" value="#{planillaDescuentoController.intMes}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								</h:selectOneMenu>
							</td>
							
							<td>
								<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
									action="#{planillaDescuentoController.consultarPlanillaDescuento}"
	          	 					reRender="dtPlanillaDescuento,mpMessage"
	          	 					oncomplete="if(#{planillaDescuentoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="5"/>
								<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{planillaDescuentoController.limpiarTabCaptaciones}" 
									reRender="rpTabPllaDesagregado"/>
								<rich:spacer width="5"/>
								<h:commandButton id="btnImprimir" value="Reporte" styleClass="btnEstilos1" action="#{planillaDescuentoController.imprimirPlanillaDescuento}"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</rich:columnGroup>
			
			<rich:spacer height="10px"/>
			<h:panelGrid>
				<rich:dataTable value="#{planillaDescuentoController.listaPlanillaDescuentoDesagregado}"
								styleClass="dataTable1" id="dtPlanillaDescuento"
								rowKeyVar="rowKey" sortMode="single" style="margin:0 auto text-align: center"
								var="item">
					<f:facet name="header">
						<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column width="400px" colspan="3" style="text-align: center">
								<h:outputText value="Unidad Ejecutora / Institución" />
							</rich:column>
							<rich:column width="200px" colspan="2" style="text-align: center">
								<h:outputText value="Enviado" />
							</rich:column>
							<rich:column width="200px" colspan="2" style="text-align: center">
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column width="200px" colspan="2" style="text-align: center">
								<h:outputText value="Diferencia" />
							</rich:column>
							<rich:column width="350px" breakBefore="true" style="text-align: center">
								<h:outputText value="Nombre" />
							</rich:column>
							<rich:column width="70px" style="text-align: center">
								<h:outputText value="Tipo" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="Modalidad" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Monto" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Nro. Socios" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Monto" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Nro. Socios" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Monto" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="Nro. Socios" />
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					
					<rich:column width="150px" style="text-align: center">
						<h:outputText value="#{item.strUnidadEjecutora}"/>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.strTipo}"/>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.strModalidad}"/>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdMontoEnviado}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.intNroSociosEnviado}"/>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.bdMontoEfectuado}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.intNroSociosEfectuado}"/>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:panelGroup rendered="#{item.bdMontoDiferencia>=0}">
							<h:outputText value="#{item.bdMontoDiferencia}"
								style="color:red">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</h:panelGroup>
						<h:panelGroup rendered="#{item.bdMontoDiferencia<0}">
							<h:outputText value="#{item.bdMontoDiferencia}"
								style="color:blue">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</h:panelGroup>
					</rich:column>
					<rich:column width="100px" style="text-align: center">
						<h:outputText value="#{item.intNroSociosDiferencia}"/>
					</rich:column>
					
					<f:facet name="footer">     
						<rich:columnGroup>
							<rich:column width="400px" colspan="3" style="text-align: center">
								<b><h:outputText value="TOTAL" /></b>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<b><h:outputText value="#{planillaDescuentoController.bdMontoEnviadoTotal}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<b><h:outputText value="#{planillaDescuentoController.intNroSociosEnviadoTotal}" /></b>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<b><h:outputText value="#{planillaDescuentoController.bdMontoEfectuadoTotal}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText></b>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<b><h:outputText value="#{planillaDescuentoController.intNroSociosEfectuadoTotal}" /></b>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:panelGroup rendered="#{planillaDescuentoController.bdMontoDiferenciaTotal>=0}">
									<b><h:outputText value="#{planillaDescuentoController.bdMontoDiferenciaTotal}"
										style="color:red">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText></b>
								</h:panelGroup>
								<h:panelGroup rendered="#{planillaDescuentoController.bdMontoDiferenciaTotal<0}">
									<b><h:outputText value="#{planillaDescuentoController.bdMontoDiferenciaTotal}"
										style="color:blue">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText></b>
								</h:panelGroup>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<b><h:outputText value="#{planillaDescuentoController.intNroSociosDiferenciaTotal}" /></b>
							</rich:column>
						</rich:columnGroup>
				   	</f:facet>
				</rich:dataTable>
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
</h:form>