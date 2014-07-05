<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI               -->
<!-- AUTOR     : GUTENBERG TORRES BROUSSET PACHECO -->
<!-- MODULO    : RIESGO                            -->
<!-- PROTOTIPO : ...                               -->
<!-- FECHA     : 21.03.2014                        -->

<h:form id="frmCarteraManual">

	<rich:panel style="border:1px solid #17356f;">
		<rich:panel
			style="border: 0px solid #17356f;background-color:#DEEBF5;"
			styleClass="rich-tabcell-noborder">
			<h:panelGrid columns="1" width="100%" style="text-align:center">
				<rich:column>
					<h:outputText style="font-weight:bold" value="CARTERA DE CRÉDITOS" />
				</rich:column>
			</h:panelGrid>

			<rich:spacer height="12px" />

			<h:panelGrid columns="9">
				<rich:column style="width:60px; text-align:left;">
					<h:outputText value="Periodo: " />
				</rich:column>
				<rich:column>
					<h:selectOneMenu style="width: 120px;"
						value="#{carteraManualController.intMesFiltro}">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="somAnioFiltro" style="width: 80px;"
						value="#{carteraManualController.intAnioFiltro}">
						<f:selectItems value="#{carteraManualController.listaAnios}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="text-align: center;width: 120px;">
					<h:outputText value="Tipo de Cartera: " />
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="somCarteraCreditosFiltro"
						style="width: 120px;"
						value="#{carteraManualController.intTipoCarteraFiltro}">
						<f:selectItem id="sliCarteraCreditosFiltro" itemValue="1"
							itemLabel="Cartera de Créditos" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="text-align: center;width: 60px;">
					<h:outputText value="Estado: " />
				</rich:column>
				<rich:column>
					<h:selectOneMenu style="width: 160px;"
						value="#{carteraManualController.intEstadoFiltro}">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOCIERRE}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="width: 20px;" />
				<rich:column>
					<a4j:commandButton styleClass="btnEstilos" value="Buscar"
						reRender="pnlTablaCarteraManual,pnlBotonesCarteraManual,pngCarteraManual4,pngCarteraManual5"
						action="#{carteraManualController.buscar}" />
				</rich:column>
			</h:panelGrid>

			<rich:spacer height="12px" />

			<h:panelGrid id="pnlTablaCarteraManual">
				<rich:extendedDataTable id="tblCarteraManual" sortMode="single"
					enableContextMenu="false" var="item" selectionMode="none"
					value="#{carteraManualController.listaCarteraManual}"
					rowKeyVar="rowKey" rows="5" width="926px" height="165px"
					align="center" noDataLabel="No existen registros">

					<rich:column width="25px" style="text-align: right">
						<h:outputText value="#{rowKey + 1}" />
					</rich:column>
					<rich:column sortBy="#{item.intPeriodo}" width="120px"
						style="text-align: left">
						<f:facet name="header">
							<h:outputText value="Periodo" />
						</f:facet>
						<h:outputText
							value="#{item.intMes == 1 ? 'Enero' :
							 		item.intMes == 2 ? 'Febrero' :
							 		item.intMes == 3 ? 'Marzo' :
							 		item.intMes == 4 ? 'Abril' :
							 		item.intMes == 5 ? 'Mayo' :
							 		item.intMes == 6 ? 'Junio' :
							 		item.intMes == 7 ? 'Julio' :
							 		item.intMes == 8 ? 'Agosto' :
							 		item.intMes == 9 ? 'Setiembre' :
							 		item.intMes == 10 ? 'Octubre' :
							 		item.intMes == 11 ? 'Noviembre' :
							  		'Diciembre'} - #{item.intAnio}" />
					</rich:column>
					<rich:column width="220px" style="text-align:center">
						<f:facet name="header">
							<h:outputText value="Tipo de Cartera" />
						</f:facet>
						<h:outputText value="Cartera de Créditos" />
					</rich:column>
					<rich:column sortBy="#{item.douMntProvCredito}" width="140px"
						style="text-align:right">
						<f:facet name="header">
							<h:outputText value="Prov. Crédito" />
						</f:facet>
						<h:outputText value="#{item.douMntProvCredito}" />
					</rich:column>
					<rich:column sortBy="#{item.douMntProvProciclica}" width="140px"
						style="text-align:right">
						<f:facet name="header">
							<h:outputText value="Prov. Procíclica" />
						</f:facet>
						<h:outputText value="#{item.douMntProvProciclica}" />
					</rich:column>
					<rich:column sortBy="#{item.intEstadoCierre}" width="80px"
						style="text-align:center">
						<f:facet name="header">
							<h:outputText value="Estado" />
						</f:facet>
						<h:outputText
							value="#{item.intEstadoCierre == 1 ? 'Abierto' : 'Cerrado'}" />
					</rich:column>
					<rich:column sortBy="#{item.dtFechaRegistro}" width="140"
						style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Fec. Registro" />
						</f:facet>
						<h:outputText value="#{item.dtFechaRegistro}">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" />
						</h:outputText>
					</rich:column>
					<rich:column width="62" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Asiento" />
						</f:facet>
						<a4j:commandLink value="Ver"
							action="#{carteraManualController.verAsientoCartera}"
							oncomplete="Richfaces.showModalPanel('mdpAsientoCartera')"
							reRender="edtLibroCartera,frmAsientoCartera">
							<f:setPropertyActionListener value="#{rowKey}"
								target="#{carteraManualController.indListaCarteraManual}" />
						</a4j:commandLink>
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller for="tblCarteraManual" maxPages="10" />
					</f:facet>
				</rich:extendedDataTable>
			</h:panelGrid>

			<rich:spacer height="12px" />

			<h:panelGrid columns="2" id="pnlBotonesCarteraManual">
				<rich:column>
					<a4j:commandButton value="Generar" styleClass="btnEstilos"
						style="width:90px" id="btnGenerar"
						action="#{carteraManualController.validaContrasenia}"
						reRender="pnlInferior, pngCarteraManual1, pngCarteraManual2, 
							pngCarteraManual3, pngCarteraManual4, pngCarteraManual5"
						oncomplete="if(#{carteraManualController.mostrarConfirmacion}){Richfaces.showModalPanel('mdpConfirmaGenerar')}" />
				</rich:column>
				<rich:column>
					<a4j:commandButton value="Cerrar Cartera" styleClass="btnEstilos"
						style="width:90px" id="btnCerrar"
						action="#{carteraManualController.validaContrasenia}"
						reRender="pnlInferior, pngCarteraManual1, pngCarteraManual2, 
							pngCarteraManual3, pngCarteraManual4, pngCarteraManual5"
						oncomplete="if(#{carteraManualController.mostrarConfirmacion}){Richfaces.showModalPanel('mdpConfirmaCerrar')}" />
				</rich:column>
			</h:panelGrid>

			<rich:spacer height="12px" />

			<rich:panel id="pnlInferior" style="border:1px solid #17356f;">
				<h:panelGrid id="pngCarteraManual1" columns="7">
					<rich:column style="width:100px">
						<h:outputText value="Periodo: " />
					</rich:column>
					<rich:column>
						<h:selectOneMenu style="width:120px;background-color:#EBEBE4;"
							value="#{carteraManualController.carteraCreditoManual.intMes}"
							disabled="true">
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:selectOneMenu style="width:80px;background-color:#EBEBE4;"
							value="#{carteraManualController.carteraCreditoManual.intAnio}"
							disabled="true">
							<f:selectItems value="#{carteraManualController.listaAnios}" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column style="width:100px;text-align:right;">
						<h:outputText value="Tipo de Cartera: " />
					</rich:column>
					<rich:column>
						<h:selectOneMenu style="width:160px;background-color:#EBEBE4;"
							value="#{carteraManualController.carteraCreditoManual.intTipoCartera}"
							disabled="true">
							<f:selectItem itemValue="1" itemLabel="Cartera de Créditos" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column style="width:100px;text-align:right;">
						<h:outputText value="Tipo de Cobranza: " />
					</rich:column>
					<rich:column>
						<h:inputText size="20"
							value="#{carteraManualController.strTipoCobranza}"
							disabled="true" />
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="12px" />

				<h:panelGrid id="pngCarteraManual2" columns="2">
					<rich:column style="width:100px">
						<h:outputText value="Estado Cierre: " />
					</rich:column>
					<rich:column>
						<h:selectOneMenu style="width:120px;background-color:#EBEBE4;"
							value="#{carteraManualController.carteraCreditoManual.intEstadoCierre}"
							disabled="true">
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_ESTADOCIERRE}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" />
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="12px" />

				<h:panelGrid id="pngCarteraManual3" columns="5">
					<rich:column style="width:100px">
						<h:outputText value="Monto Provisión: " />
					</rich:column>
					<rich:column style="width:120px; text-align:right;">
						<h:outputText value="Crédito: " />
					</rich:column>
					<rich:column>
						<h:inputText size="10"
							value="#{carteraManualController.carteraCreditoManual.douMntProvCredito}"
							disabled="true" style="text-align:right;" />
					</rich:column>
					<rich:column style="width:100px; text-align:right;">
						<h:outputText value="Procíclica: " />
					</rich:column>
					<rich:column>
						<h:inputText size="10"
							value="#{carteraManualController.carteraCreditoManual.douMntProvProciclica}"
							disabled="true" style="text-align:right;" />
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="12px" />

				<h:panelGrid id="pngCarteraManual4" columns="2">
					<rich:column style="width:100px">
						<h:outputText value="Contraseña: " />
					</rich:column>
					<rich:column>
						<h:inputSecret size="20"
							value="#{carteraManualController.strContrasenia}" maxlength="20" />
					</rich:column>
				</h:panelGrid>

				<h:panelGrid id="pngCarteraManual5" columns="1">
					<rich:column style="color:#{carteraManualController.colorMensaje}; font-weight:bold;"
						visible="#{carteraManualController.mostrarMensaje}">
						<h:outputText value="#{carteraManualController.mensaje}" />
					</rich:column>
				</h:panelGrid>
			</rich:panel>

		</rich:panel>
	</rich:panel>
</h:form>