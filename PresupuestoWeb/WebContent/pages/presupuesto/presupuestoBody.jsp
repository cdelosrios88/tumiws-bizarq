<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO              -->
<!-- PROTOTIPO : PRESUPUESTO - BODY       -->			
<!-- FECHA     : 10.10.2013               -->

<rich:modalPanel id="mpModificaEnProcesoDeGrabacion" width="600" height="150" 
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Confirmar modificación..." styleClass="tamanioLetra"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<h:form id="frmModificaEnProcesoDeGrabacion">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >
			<h:panelGrid border="0">
				<h:outputText value="#{presupuestoController.strMsgTxtExisteRegistro}" styleClass="tamanioLetra"/>
				<h:outputText value="¿Desea modificarlos?" styleClass="tamanioLetra"/>
			</h:panelGrid>
				<rich:spacer height="16px"/>
			<h:panelGrid columns="4" border="0" style="margin:0 auto; width: 100%">
				<a4j:commandButton value="Modificar" action="#{presupuestoController.modificarEnProcesoGrabacion}" styleClass="btnEstilos1"
					reRender="rpProcesos" oncomplete="Richfaces.hideModalPanel('mpModificaEnProcesoDeGrabacion')"/>
				<a4j:commandButton value="Cancelar" 
					action="#{presupuestoController.cancelarModificadion}" 
					styleClass="btnEstilos1" reRender="rpProcesos" 
					oncomplete="Richfaces.hideModalPanel('mpModificaEnProcesoDeGrabacion')"/>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="mpEditarPresupuesto" width="600" height="150"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Opciones" styleClass="tamanioLetra"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkPresupuesto"/>
			<rich:componentControl for="mpEditarPresupuesto" attachTo="hidelinkPresupuesto" operation="hide" event="onclick"/>
		</h:panelGroup>
	</f:facet>
	<h:form id="formEditPresupuesto">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >
			<h:panelGrid border="0">
				<h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
			</h:panelGrid>
			<rich:spacer height="16px"/>
			<h:panelGrid columns="4" border="0" style="margin:0 auto; width: 100%">
				<a4j:commandButton id="btnModificar" value="Modificar Presupuesto" action="#{presupuestoController.modificarPresupuestoPorPartida}" 
				styleClass="btnEstilos1" reRender="opProcesarPorPartida,btnGrabar" oncomplete="Richfaces.hideModalPanel('mpEditarPresupuesto')"/>
				<a4j:commandButton id="btnEliminar" value="Eliminar Presupuesto" action="#{presupuestoController.eliminarPresupuesto}" 
				styleClass="btnEstilos1" reRender="opProcesarPorPartida" oncomplete="Richfaces.hideModalPanel('mpEditarPresupuesto')"/>
			</h:panelGrid>
		</rich:panel>
		<rich:spacer height="4px"/><rich:spacer height="8px"/>
	</h:form>    
</rich:modalPanel>

<a4j:include viewId="/pages/presupuesto/popup/mpAddCuentaContable.jsp"/>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<a4j:region>
	<a4j:jsFunction name="selectedPresupuesto" action="#{presupuestoController.setSelectedPresupuesto}">
		<f:param name="rowPresupuesto"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbCtaContable" reRender="btnAddCtaContableEnBloque"
		action="#{presupuestoController.disabledBtnAddCtaContable}">
		<f:param name="pRbCtaContable"></f:param>
	</a4j:jsFunction>  
	
	<a4j:jsFunction name="selectYear" reRender="btnAddCtaContablePorPartida"
		action="#{presupuestoController.isValidAnioProyectado}">
		<f:param name="pCbYears"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectMoneda" reRender="txtCtaContableGrbPorPartida"
		action="#{presupuestoController.cleanStrCuentaGrb}">
		<f:param name="pCbMoneda"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbRowCtaContable" reRender="txtCtaContableGrbEnBloque,txtCtaContableGrbPorPartida"
		action="#{presupuestoController.seleccionarCuenta}"
		oncomplete="Richfaces.hideModalPanel('mpAddCtaContable')">
		<f:param name="rowCtaContable"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoCuenta" reRender="txtCuentaBusq"
		action="#{presupuestoController.disabledTxtCuentaBusq}">
		<f:param name="pCboTipoCuentaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbSucursal" reRender="cboSucPanelEnBloque, cboSubSucPanelEnBloque"
		action="#{presupuestoController.disabledCboSucursal}">
		<f:param name="pRbSucursal"></f:param>
	</a4j:jsFunction>
	
	<!-- Ajax para los combos de Sucursal - SubSucursal en los panels Por Indicador, En Bloque y Búsqueda -->
	<a4j:jsFunction name="getSubSucursalPanelEnBloque" reRender="cboSubSucPanelEnBloque"
		action="#{presupuestoController.getListSubSucursalDeSucGrb}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>

	<a4j:jsFunction name="getSubSucursalPanelPorPartida" reRender="cboSubSucPanelPorPartida"
		action="#{presupuestoController.getListSubSucursalDeSucGrb}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>

	<a4j:jsFunction name="getSubCucursalBusq" reRender="cboSubSucBusq"
		action="#{presupuestoController.getListSubSucursalDeSucBusq}">
		<f:param name="pCboSucursalBusq"></f:param>
	</a4j:jsFunction>
	<!-- ------------------------------------------------------------------------------------------------ -->

	<a4j:jsFunction name="getAnioProyectado" reRender="txtAnioProyectado"
		action="#{presupuestoController.getAnioProyectado}">
		<f:param name="pCboAnioBase"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; " id="frmPrincipal">
	<h:outputLabel value="#{presupuestoController.inicioPage}"/>
	<rich:panel id="rpPrincipal" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
		<div align="center">
			<b>PRESUPUESTO</b>
		</div>
		<rich:spacer width="10px"/>
		<!-- FILTROS DE BUSQUEDA -->		
		<h:panelGroup>
		
			<a4j:include viewId="/pages/presupuesto/filtrosBusqueda.jsp"/>
			
			<rich:spacer height="8px"/>
			<h:panelGrid id="pgPanelControlPresupuesto" columns="4">
				<rich:column width="1200px" style="text-align: left">
					<table>
						<tr>
							<td align="left">
								<a4j:commandButton value="En Bloque" styleClass="btnEstilos1" action="#{presupuestoController.showPanelEnBloque}" 
									reRender="rpProcesos,btnGrabar"/>
								<rich:spacer width="4px"/>
							</td>
							<td align="left">
								<a4j:commandButton value="Por Partida" styleClass="btnEstilos1" action="#{presupuestoController.showPanelPorPartida}" 
									reRender="rpProcesos,btnGrabar"/>
								<rich:spacer width="4px"/>
							</td>							
							<td align="left">
								<a4j:commandButton id="btnGrabar" value="Grabar" styleClass="btnEstilos1" action="#{presupuestoController.procesarEvento}" 
									disabled="#{presupuestoController.blnDisabledGrabar}"
									reRender="rpProcesos,mpMessage,frmModificaEnProcesoDeGrabacion"
									oncomplete="if(#{not empty presupuestoController.listaValidaPresupuesto}){Richfaces.showModalPanel('mpModificaEnProcesoDeGrabacion')}
												else
												if(#{presupuestoController.intTipoGrabacion == applicationScope.Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE && (presupuestoController.intErrorPresupuestoPorAnioBase == 1 || presupuestoController.intErrorPlanCuenta == 1)}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="4px"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="8px"/>
			<rich:panel id="rpProcesos" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">				
			
				<h:panelGrid id="pgMsgErrorPresupuesto">
					<h:outputText value="#{presupuestoController.strMsgTxtPeriodo}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtTipoMoneda}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtMes}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtRangoMeses}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtPorcentajeCrecimiento}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtAddCtaContable}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtSucursal}" styleClass="msgError" />
					<h:outputText value="#{presupuestoController.strMsgTxtSubSucursal}" styleClass="msgError" />
				</h:panelGrid>
				
				<a4j:outputPanel id="opProcesarEnBloque">
					<h:panelGrid rendered="#{presupuestoController.blnShowPanelTabEnBloque}">
						<a4j:include viewId="/pages/presupuesto/grabacion/enBloque.jsp"/> 
					</h:panelGrid>
				</a4j:outputPanel>
				
				<a4j:outputPanel id="opProcesarPorPartida">
					<h:panelGrid rendered="#{presupuestoController.blnShowPanelTabPorPartida}">
						<a4j:include viewId="/pages/presupuesto/grabacion/porPartida.jsp"/> 
					</h:panelGrid>
				</a4j:outputPanel>	
			</rich:panel>
		</h:panelGroup>
	</rich:panel>
</h:form>

