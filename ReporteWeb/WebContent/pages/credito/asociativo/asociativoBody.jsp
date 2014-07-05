<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : REPORTE                 -->
<!-- PROTOTIPO :  - BODY       -->			
<!-- FECHA     : 07.11.2013               -->


<rich:modalPanel id="mpDetalleCaptacionesEjecutadas" width="1090" height="500" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGroup>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="REPORTE DE SOCIOS - CAPTACIÓN"></h:outputText>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="#{asociativoController.strFmtPeriodo}"></h:outputText>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:columnGroup>
					<rich:column style="text-align: center">
						<h:outputText value="Sucursal:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strSucursal}"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="Tipo Socio:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strTipoSocio}"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="Modalidad:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strModalidad}"></h:outputText>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
		</h:panelGroup>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mpDetalleCaptacionesEjecutadas" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet> 
		<rich:extendedDataTable id="edtDetalleCaptaciones" enableContextMenu="false" sortMode="single" 
				var="itemGrilla" value="#{asociativoController.listaDetalleCaptaciones}" 
                rendered="#{not empty asociativoController.listaDetalleCaptaciones}" 
                rowKeyVar="rowKey" width="1040px" height="350px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column width="20px" style="text-align: center">
							<h:outputText value="Nro." />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Cuenta" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Apellidos y Nombres" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Fecha de Apertura" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="Cta.Aporte" />
						</rich:column>
						<rich:column width="90px" style="text-align: center">
							<h:outputText value="Cta.Fdo.Retiro" />
						</rich:column>
						<rich:column width="180px" style="text-align: center">
							<h:outputText value="Unidad Ejecutora" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="Tipo Socio" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="Modalidad" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Sucursal" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column width="20px" style="text-align: center">
					<h:outputText style="text-align: center" value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNroCuenta}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<h:outputText id="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
					<rich:toolTip for="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaApertura}"/>
				</rich:column>
				<rich:column width="80px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdMontoAporte}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="90px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdMontoFdoRetiro}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="180px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strUnidadEjecutora}"/>
				</rich:column>
				<rich:column width="80px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intTipoSocio}"/>
				</rich:column>
				<rich:column width="80px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intModalidad}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<tumih:outputText value="#{asociativoController.listJuridicaSucursal}" 
					itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
					property="#{itemGrilla.intIdSucursal}"/>
				</rich:column>
		</rich:extendedDataTable>
</rich:modalPanel>

<rich:modalPanel id="mpDetalleRenunciasEjecutadas" width="1000" height="500" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGroup>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="REPORTE DE SOCIOS - LIQUIDACIÓN"></h:outputText>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:column style="text-align: center">
					<h:outputText value="#{asociativoController.strFmtPeriodo}"></h:outputText>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid>
				<rich:columnGroup>
					<rich:column style="text-align: center">
						<h:outputText value="Sucursal:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strSucursal}"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="Tipo Socio:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strTipoSocio}"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="Modalidad:"></h:outputText>
					</rich:column>
					<rich:column style="text-align: center">
						<h:outputText value="#{asociativoController.strModalidad}"></h:outputText>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
		</h:panelGroup>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mpDetalleRenunciasEjecutadas" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet> 
		<rich:extendedDataTable id="edtDetalleRenuncias" enableContextMenu="false" sortMode="single" 
				var="itemGrilla" value="#{asociativoController.listaDetalleRenuncias}" 
                rendered="#{not empty asociativoController.listaDetalleRenuncias}" 
                rowKeyVar="rowKey" width="980px" height="350px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column width="20px" style="text-align: center">
							<h:outputText value="Nro." />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Cuenta" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Apellidos y Nombres" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Fecha de Apertura" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Fecha Liquidación" />
						</rich:column>
						<rich:column width="180px" style="text-align: center">
							<h:outputText value="Unidad Ejecutora" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="Tipo Socio" />
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<h:outputText value="Modalidad" />
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<h:outputText value="Sucursal" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column width="20px" style="text-align: center">
					<h:outputText style="text-align: center" value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNroCuenta}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<h:outputText id="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
					<rich:toolTip for="lblNombreSocio" value="#{itemGrilla.strNombreSocio}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaApertura}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaLiquidacion}"/>
				</rich:column>
				<rich:column width="180px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strUnidadEjecutora}"/>
				</rich:column>
				<rich:column width="80px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intTipoSocio}"/>
				</rich:column>
				<rich:column width="80px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intModalidad}"/>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<tumih:outputText value="#{asociativoController.listJuridicaSucursal}" 
					itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
					property="#{itemGrilla.intIdSucursal}"/>
				</rich:column>
		</rich:extendedDataTable>
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{asociativoController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbPeriodo" reRender="cboMesDesde, cboMesHasta, cboAnioBusq, cboRangoPeriodo, rbOpcA, rbOpcB"
		action="#{asociativoController.disabledCboPeriodo}">
		<f:param name="pRbPeriodo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalR" reRender="cboSubSucursalR"
		action="#{asociativoController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbPeriodoR" reRender="cboMesDesdeR, cboMesHastaR, cboAnioBusqR, cboRangoPeriodoR, rbOpcAR, rbOpcBR"
		action="#{asociativoController.disabledCboPeriodo}">
		<f:param name="pRbPeriodo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbBusqueda" reRender="cboSucursalP, cboDepartamento, rbOpcAP, rbOpcBP"
		action="#{asociativoController.disabledCboBusqPadrones}">
		<f:param name="pRbBusqueda"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:outputText value="#{asociativoController.inicioPage}" />
<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Captaciones" disabled="#{!asociativoController.poseePermisoCaptaciones}">
		<a4j:include id="idIncludeTabCaptaciones" viewId="/pages/credito/asociativo/tabs/tabCaptaciones.jsp"/>
	</rich:tab>
	<rich:tab label="Renuncias (Liquidaciones Cta.)" disabled="#{!asociativoController.poseePermisoRenuncias}">
		<a4j:include id="idIncludeTabRenuncias" viewId="/pages/credito/asociativo/tabs/tabRenuncias.jsp"/>
	</rich:tab>
	<rich:tab label="Padrón de Trabajadores" disabled="#{!asociativoController.poseePermisoPadron}">
		<a4j:include id="idIncludeTabPadronTrabajadores" viewId="/pages/credito/asociativo/tabs/tabPadronTrabajadores.jsp"/>
	</rich:tab>
	<rich:tab label="Convenios" disabled="#{!asociativoController.poseePermisoConvenios}">
		<a4j:include id="idIncludeTabConvenio" viewId="/pages/credito/asociativo/tabs/tabConvenio.jsp"/>
	</rich:tab>
</rich:tabPanel>

