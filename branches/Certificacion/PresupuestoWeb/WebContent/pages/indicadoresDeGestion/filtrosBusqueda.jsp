<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : INDICADORES DE GESTION - FILTROS DE BUSQUEDA -->			
<!-- FECHA     : 10.10.2013               -->
<a4j:outputPanel id="opFiltrosDeBusqueda" >
	<h:panelGrid>
		<rich:column width="1200px" style="text-align: left">
			<table>
				<tr>
					<td width="70px" align="center">
						<b><h:outputText value="Año:"/></b>
					</td>
					<td width="80px" align="center">
						<h:selectOneMenu id="idAnioBusq" value="#{indicadoresDeGestionController.intAnioBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<f:selectItems id="lstYears" value="#{indicadoresDeGestionController.listYears}"/>
						</h:selectOneMenu>
					</td>
					<td width="50px" align="center">
						<b><h:outputText value="Mes:"/></b>
					</td>
					<td width="100px" align="center">
						<h:selectOneMenu id="idMesBusq" value="#{indicadoresDeGestionController.intMesBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
					</td>
					<td width="110px" align="center">
						<b><h:outputText value="Tipo Indicador:"/></b>
					</td>
					<td width="130px" align="center">
						<h:selectOneMenu id="idTipoIndBusq" value="#{indicadoresDeGestionController.intTipoIndicadorBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINDICADOR}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
					</td>
					<td width="80px" align="center">
						<b><h:outputText value="Sucursal:"/></b>
					</td>
					<td width="110px" align="center">
						<h:selectOneMenu id="cboSucBusq" style="width: 100px;" 
							onchange="getSubCucursalBusq(#{applicationScope.Constante.ONCHANGE_VALUE})"
							value="#{indicadoresDeGestionController.intIdSucursalBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSucursal}"
								itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
						</h:selectOneMenu>
					</td>
					<td width="120px" align="center">
						<b><h:outputText value="Sub-sucursal"/></b>
					</td>
					<td width="110px" align="center">
						<h:selectOneMenu id="cboSubSucBusq" style="width: 100px;"
							value="#{indicadoresDeGestionController.intIdSubSucursalBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSubsucursalBusq}"
								itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
						</h:selectOneMenu>
					</td>
					<td width="120px" align="center">
						<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" action="#{indicadoresDeGestionController.obtenerListaIndicadoresPorFiltros}"
							reRender="opListaIndGestionPorFiltroBusqueda"/>
					</td>
					<td width="120px" align="center">
						<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{indicadoresDeGestionController.limpiarFormularioIndicadoresDeGestion}" 
							reRender="rpPrincipal"/>
					</td>
				</tr>
			</table>
		</rich:column>
	</h:panelGrid>
</a4j:outputPanel>

<rich:spacer height="10px"/>

<a4j:outputPanel id="opListaIndGestionPorFiltroBusqueda" >
	<h:panelGrid rendered="#{not empty indicadoresDeGestionController.listaIndicadoresPorFiltros}">	
		<rich:extendedDataTable id="edtIndGestionPorFiltroBusqueda" enableContextMenu="false" sortMode="single" 
			var="itemGrilla" value="#{indicadoresDeGestionController.listaIndicadoresPorFiltros}"
			rowKeyVar="rowKey" rows="5" width="950px" height="195px"
			onRowClick="selectedIndicador('#{rowKey}')">
			
			<rich:column width="30" style="text-align: center">
				<h:outputText value="#{rowKey + 1}"/>
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Año"></h:outputText>
				</f:facet>
				<h:outputText value="#{itemGrilla.id.intPeriodoIndicador}"/>
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Mes"></h:outputText>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{itemGrilla.id.intMesIndicador}"/>	
			</rich:column>
			
			<rich:column width="200" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Indicador"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOINDICADOR}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{itemGrilla.id.intParaTipoIndicador}"/>				  
			</rich:column>
			
			<rich:column width="150" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Sucursal"/>
				</f:facet>
				<tumih:outputText value="#{indicadoresDeGestionController.listJuridicaSucursal}" 
					itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
					property="#{itemGrilla.id.intIdSucursal}"/>
			</rich:column>
			
			<rich:column width="150" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="SubSucursal"/>
				</f:facet> 
				<h:outputText value="#{itemGrilla.strIdSubSucursal}"/>
			</rich:column>
			
			<rich:column width="100" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Valor"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVALOR}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{itemGrilla.intParaTipoValor}"/>	
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Monto"/>
				</f:facet>
				<h:outputText value="#{itemGrilla.bdMontoProyectado}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:outputText>
			</rich:column>
					
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Estado"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOCIERRE}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{itemGrilla.intParaEstadoCierre}"/>	
			</rich:column>
			
			<f:facet name="footer">     
				<h:panelGroup layout="block">
					<rich:datascroller for="edtIndGestionPorFiltroBusqueda" maxPages="10"/>
					<h:panelGroup layout="block" style="margin:0 auto; width:940px; text-align: center">
						<a4j:commandLink 
							action ="#{indicadoresDeGestionController.isValidaEstadoCierre}" 
							oncomplete="if(#{indicadoresDeGestionController.indicadorSelected.intParaEstadoCierre == applicationScope.Constante.PARAM_T_ESTADOCIERRE_PENDIENTE}){Richfaces.showModalPanel('mpEditarIndicador')}
										else {Richfaces.showModalPanel('mpMessage')}"
							reRender="mpMessage,formEditIndicador">
							<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
						</a4j:commandLink>
						<h:outputText value="Para EDITAR un Indicador de Gestión anual hacer click en el Registro" style="color:#8ca0bd"/>
					</h:panelGroup>
				</h:panelGroup>  
			</f:facet>
		</rich:extendedDataTable>	
	</h:panelGrid>
</a4j:outputPanel>

