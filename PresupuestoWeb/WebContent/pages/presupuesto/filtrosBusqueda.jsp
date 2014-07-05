<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : PRESUPUESTO - FILTROS DE BUSQUEDA -->			
<!-- FECHA     : 21.10.2013               -->
<a4j:outputPanel id="opFiltrosDeBusqueda">
	<h:panelGrid>
		<rich:column width="1200px" style="text-align: left">
			<table>
				<tr>
					<td width="70px" align="center">
						<b><h:outputText value="Año:"/></b>
					</td>
					<td width="80px" align="center">
						<h:selectOneMenu id="idAnioBusq" value="#{presupuestoController.intAnioBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<f:selectItems id="lstYears" value="#{presupuestoController.listYears}"/>
						</h:selectOneMenu>
					</td>
					<td width="120px" align="center">
						<b><h:outputText value="Cta.Contable:"/></b>
					</td>
					<td width="100px" align="center">
						<h:selectOneMenu id="cboTipoCuentaBusq" value="#{presupuestoController.intCboTipoCuentaBusq}">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    				</h:selectOneMenu>
					</td>
					<td width="170px" align="center">
						<h:inputText id="txtCuentaBusq" value="#{presupuestoController.strCuentaBusq}" style="width:160px"></h:inputText>
			    	</td>
					<td width="80px" align="center">
						<b><h:outputText value="Sucursal:"/></b>
					</td>
					<td width="110px" align="center">
						<h:selectOneMenu id="cboSucBusq" style="width: 100px;" 
							onchange="getSubCucursalBusq(#{applicationScope.Constante.ONCHANGE_VALUE})"
							value="#{presupuestoController.intIdSucursalBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{presupuestoController.listJuridicaSucursal}"
								itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
						</h:selectOneMenu>
					</td>
					<td width="120px" align="center">
						<b><h:outputText value="Sub-sucursal"/></b>
					</td>
					<td width="110px" align="center">
						<h:selectOneMenu id="cboSubSucBusq" style="width: 100px;"
							value="#{presupuestoController.intIdSubSucursalBusqueda}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{presupuestoController.listJuridicaSubsucursalBusq}"
								itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
						</h:selectOneMenu>
					</td>
					<td width="120px" align="center">
						<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" action="#{presupuestoController.obtenerListaPresupuestosPorFiltros}"
							reRender="pgMsgErrorPresupuesto,opListaPresupuestosPorFiltroBusqueda,opProcesarEnBloque,opProcesarPorPartida,btnGrabar"/>
					</td>
					<td width="120px" align="center">
						<a4j:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos1" action="#{presupuestoController.limpiarFormularioPresupuesto}" 
							reRender="pgMsgErrorPresupuesto,opFiltrosDeBusqueda,opListaPresupuestosPorFiltroBusqueda,opProcesarEnBloque,opProcesarPorPartida,btnGrabar"/>
					</td>
				</tr>
			</table>
		</rich:column>
	</h:panelGrid>
</a4j:outputPanel>

<rich:spacer height="10px"/>

<a4j:outputPanel id="opListaPresupuestosPorFiltroBusqueda">
	<h:panelGrid rendered="#{not empty presupuestoController.listaPresupuestosPorFiltros}">	
		<rich:extendedDataTable id="edtPresupuestosPorFiltroBusqueda" enableContextMenu="false" sortMode="single" 
			var="itemGrilla" value="#{presupuestoController.listaPresupuestosPorFiltros}"
			rowKeyVar="rowKey" rows="5" width="930px" height="195px"
			onRowClick="selectedPresupuesto('#{rowKey}')">
			
			<rich:column width="30" style="text-align: center">
				<h:outputText value="#{rowKey + 1}"/>
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Año"></h:outputText>
				</f:facet>
				<h:outputText value="#{itemGrilla.id.intPeriodoPresupuesto}"/>
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Mes"></h:outputText>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
	   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
				    property="#{itemGrilla.id.intMesPresupuesto}"/>
			</rich:column>
			
			<rich:column width="100" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Cuenta"/>
				</f:facet>
				<h:outputText value="#{itemGrilla.id.strNumeroCuenta}"/>				  
			</rich:column>
			
			<rich:column width="180" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Descripción"/>
				</f:facet>
				<h:outputText id="lblCtaDescripcion" value="#{itemGrilla.strDescripcionCuenta}"/>				  
				<rich:toolTip for="lblCtaDescripcion" value="#{itemGrilla.strDescripcionCuenta}"></rich:toolTip>
			</rich:column>		
			
			<rich:column width="150" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Sucursal"/>
				</f:facet>
				<tumih:outputText value="#{presupuestoController.listJuridicaSucursal}" 
					itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
					property="#{itemGrilla.id.intIdSucursal}"/>
			</rich:column>
			
			<rich:column width="150" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="SubSucursal"/>
				</f:facet> 
				<h:outputText value="#{itemGrilla.strDescripcionSubSucursal}"/>
			</rich:column>
			
			<rich:column width="80" style="text-align: center">
				<f:facet name="header">
					<h:outputText value="Monto"/>
				</f:facet>
				<h:outputText id="mtoSoles" value="#{itemGrilla.bdMontoProyecSoles}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:outputText>
				<h:outputText id="mtoExtranjero" value="#{itemGrilla.bdMontoProyecExtranjero}">
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
					<rich:datascroller for="edtPresupuestosPorFiltroBusqueda" maxPages="10"/>
					<h:panelGroup layout="block" style="margin:0 auto; width:840px; text-align: center">
						<a4j:commandLink 
							action ="#{presupuestoController.isValidaEstadoCierre}" 
							oncomplete="if(#{presupuestoController.presupuestoSelected.intParaEstadoCierre == applicationScope.Constante.PARAM_T_ESTADOCIERRE_PENDIENTE}){Richfaces.showModalPanel('mpEditarPresupuesto')}
										else {Richfaces.showModalPanel('mpMessage')}"
							reRender="mpMessage,formEditPresupuesto,opProcesarPorPartida">
							<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
						</a4j:commandLink>
						<h:outputText value="Para EDITAR POR PARTIDA un presupuesto, hacer click en el Registro" style="color:#8ca0bd"/>
					</h:panelGroup>
				</h:panelGroup>  
			</f:facet>
		</rich:extendedDataTable>	
</h:panelGrid>
</a4j:outputPanel>

