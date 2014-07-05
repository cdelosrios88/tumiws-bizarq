<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Empresa   : Cooperativa Tumi	                    -->
<!-- Autor     : Arturo Julca   	                    -->
<!-- Modulo    :                	                    -->
<!-- Prototipo : Configuracion de Cartera de Credito    -->
<!-- Fecha     :                	                    -->
<!-- Modif.    : Gutenberg Torres Brousset              -->
<!-- Fecha Mod.: 2014.02.07                             -->
<!-- Se actualizó la tabla que muestra la configuración -->
<!-- de las Provisiones de Crédito.                     -->

<h:form id="frmConfigCartera">

	<rich:panel style="border:1px solid #17356f;">

		<rich:panel
			style="border: 0px solid #17356f;background-color:#DEEBF5;"
			styleClass="rich-tabcell-noborder">

			<h:panelGrid columns="1" width="100%">
				<rich:column>
					<h:outputText style="text-align:center;font-weight:bold"
						value="CONFIGURACIÓN DE CARTERA DE CRÉDITOS" />
				</rich:column>
			</h:panelGrid>

			<h:panelGrid columns="8">
				<rich:column style="width: 120px;text-align: left;">
					<h:outputText value="Nombre de Cartera : " />
				</rich:column>
				<rich:column style="text-align: left;">
					<h:inputText size="20"
						value="#{confCarteraController.carteraFiltro.strNombre}" />
				</rich:column>
				<rich:column style="text-align: center;width: 120px;">
					<h:outputText value="Tipo de Productos : " />
				</rich:column>
				<rich:column>
					<h:selectOneMenu style="width: 160px;"
						value="#{confCarteraController.strNombreProductoFiltro}">
						<f:selectItem itemValue="Todos" itemLabel="Todos" />
						<tumih:selectItems var="sel"
							value="#{confCarteraController.listaProducto}"
							itemValue="#{sel.strDescripcion}"
							itemLabel="#{sel.strDescripcion}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="text-align: center;width: 80px;">
					<h:outputText value="Estado : " />
				</rich:column>
				<rich:column>
					<h:selectOneMenu style="width: 100px;"
						value="#{confCarteraController.carteraFiltro.intParaEstadoCod}">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="width: 40px;">
				</rich:column>
				<rich:column>
					<a4j:commandButton styleClass="btnEstilos" value="Buscar"
						reRender="panelTablaCartera, panelBotonesCartera, contPanelInferiorCartera, panelMensajeCartera"
						action="#{confCarteraController.buscar}" />
				</rich:column>
			</h:panelGrid>

			<rich:spacer height="12px" />

			<h:panelGrid id="panelTablaCartera">
				<rich:extendedDataTable id="tblCartera" sortMode="single"
					enableContextMenu="false" var="item"
					value="#{confCarteraController.listaCartera}" rowKeyVar="rowKey"
					rows="5" width="910px" height="165px" align="center">

					<rich:column width="25px" style="text-align: right">
						<h:outputText value="#{rowKey + 1}" />
					</rich:column>
					<rich:column sortBy="#{item.strNombre}" width="185px" style="text-align: left">
						<f:facet name="header">
							<h:outputText value="Nombre de Cartera" />
						</f:facet>
						<h:outputText value="#{item.strNombre}" />
					</rich:column>
					<rich:column width="360" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Tipo de Productos" />
						</f:facet>
						<h:outputText value="#{item.listaStrProductos}" />
					</rich:column>
					<rich:column sortBy="#{item.dtFechaInicio}" width="80" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Fec. Inicio" />
						</f:facet>
						<h:outputText value="#{item.dtFechaInicio}">
							<f:convertDateTime pattern="dd/MM/yyyy" />
						</h:outputText>
					</rich:column>
					<rich:column width="80" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Fec. Fin" />
						</f:facet>
						<h:panelGroup rendered="#{empty item.dtFechaFin}">
							<h:outputText value="Indeterminado" />
						</h:panelGroup>
						<h:panelGroup rendered="#{!empty item.dtFechaFin}">
							<h:outputText value="#{item.dtFechaFin}">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:outputText>
						</h:panelGroup>
					</rich:column>
					<rich:column width="60" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Estado" />
						</f:facet>
						<tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_INT}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.intParaEstadoCod}" />
					</rich:column>
					<rich:column sortBy="#{item.dtFechaRegistra}" width="120" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Fec. Registro" />
						</f:facet>
						<h:outputText value="#{item.dtFechaRegistra}">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" />
						</h:outputText>
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller for="tblCartera" maxPages="10" />
					</f:facet>
					<a4j:support event="onRowClick"
						actionListener="#{confCarteraController.seleccionarRegistro}"
						reRender="frmAlertaRegistroCartera,contPanelInferiorCartera,panelBotones"
						oncomplete="if(#{confCarteraController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistroCartera')}">
						<f:attribute name="item" value="#{item}" />
					</a4j:support>
				</rich:extendedDataTable>
			</h:panelGrid>

			<h:panelGrid columns="1"
				style="margin-left: auto; margin-right: auto">
				<h:outputText
					value="Para Modificar o Eliminar hacer clic en el registro"
					style="color:#8ca0bd" />
			</h:panelGrid>

		</rich:panel>

		<h:panelGroup id="panelMensajeCartera"
			style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;margin-left: auto; margin-right: auto;width:500px;"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{confCarteraController.mensajeOperacion}"
				styleClass="msgInfo" style="font-weight:bold"
				rendered="#{confCarteraController.mostrarMensajeExito}" />
			<h:outputText value="#{confCarteraController.mensajeOperacion}"
				styleClass="msgError" style="font-weight:bold"
				rendered="#{confCarteraController.mostrarMensajeError}" />
		</h:panelGroup>

		<rich:panel
			style="border: 0px solid #17356f;background-color:#DEEBF5;"
			styleClass="rich-tabcell-noborder" id="panelBotonesCartera">
			<h:panelGrid columns="3">
				<rich:column>
					<a4j:commandButton value="Nuevo" styleClass="btnEstilos"
						style="width:90px"
						actionListener="#{confCarteraController.habilitarPanelInferior}"
						reRender="contPanelInferiorCartera,panelMensajeCartera,panelBotonesCartera" />
				</rich:column>
				<rich:column visible="#{confCarteraController.habilitarGrabar}">
					<a4j:commandButton value="Grabar" styleClass="btnEstilos"
						style="width:90px" action="#{confCarteraController.grabar}"
						reRender="contPanelInferiorCartera,panelMensajeCartera,panelBotonesCartera,panelTablaCartera"
						disabled="#{!confCarteraController.habilitarGrabar}" />
				</rich:column>
				<rich:column visible="#{confCarteraController.habilitarGrabar}">
					<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
						style="width:90px"
						actionListener="#{confCarteraController.deshabilitarPanelInferior}"
						reRender="contPanelInferiorCartera,panelMensajeCartera,panelBotonesCartera" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>

		<rich:panel id="contPanelInferiorCartera" style="border:0px;">

			<rich:panel id="panelInferiorCartera"
				style="border:1px solid #17356f;"
				rendered="#{confCarteraController.mostrarPanelInferior}">

				<h:panelGrid columns="2">
					<rich:column style="width:130px">
						<h:outputText value="Nombre de Cartera: " />
					</rich:column>
					<rich:column>
						<h:inputText size="20"
							value="#{confCarteraController.carteraNuevo.strNombre}"
							disabled="#{confCarteraController.deshabilitarNuevo}" />
					</rich:column>
				</h:panelGrid>
				<h:panelGrid columns="2">
					<rich:column style="width:130px">
						<h:outputText value="Tipo de Productos: " />
					</rich:column>
					<rich:column>
						<rich:dataTable value="#{confCarteraController.listaProducto}"
							var="item" width="250px">
							<h:column>
								<h:selectBooleanCheckbox value="#{item.checked}"
									disabled="#{confCarteraController.deshabilitarNuevo}">
									<a4j:support event="onclick"
										action="#{confCarteraController.seleccionarProducto}"
										reRender="filaOtrosProductos">
									</a4j:support>
								</h:selectBooleanCheckbox>
								<h:outputText value="#{item.strDescripcion}" />
							</h:column>
						</rich:dataTable>
					</rich:column>
				</h:panelGrid>
				<h:panelGrid columns="2">
					<rich:column style="width:130px">
						<h:outputText value="Tipo de Cobranza: " />
					</rich:column>
					<rich:column style="width:300px">
						<h:selectOneRadio
							value="#{confCarteraController.carteraNuevo.intParaTipoCobranzaCod}"
							disabled="#{confCarteraController.deshabilitarNuevo}">
							<f:selectItem
								itemValue="#{applicationScope.Constante.PARAM_T_TIPOCUENTA_ACTIVA}"
								itemLabel=" Activa " />
							<f:selectItem
								itemValue="#{applicationScope.Constante.PARAM_T_TIPOCUENTA_PASIVA}"
								itemLabel=" Pasiva " />
							<f:selectItem
								itemValue="#{applicationScope.Constante.PARAM_T_TIPOCUENTA_NINGUNA}"
								itemLabel=" Ninguna " />
						</h:selectOneRadio>
					</rich:column>
				</h:panelGrid>
				<h:panelGrid id="panelRangoFechas" columns="4">
					<rich:column style="width:130px">
						<h:outputText value="Rango de Fechas: " />
					</rich:column>
					<rich:column>
						<h:outputText value="Inicio: " />
						<rich:calendar datePattern="dd/MM/yyyy"
							value="#{confCarteraController.carteraNuevo.dtFechaInicio}"
							disabled="#{confCarteraController.deshabilitarNuevo}"
							inputSize="10" showApplyButton="true" />
					</rich:column>
					<rich:column visible="#{confCarteraController.habilitarFechaFin}">
						<h:outputText value="Fin: " />
						<rich:calendar datePattern="dd/MM/yyyy"
							value="#{confCarteraController.carteraNuevo.dtFechaFin}"
							disabled="#{confCarteraController.deshabilitarNuevo   || !confCarteraController.habilitarFechaFin}"
							inputSize="10" showApplyButton="true" />
					</rich:column>
					<rich:column>
						<h:selectBooleanCheckbox
							value="#{confCarteraController.seleccionaIndeterminado}"
							disabled="#{confCarteraController.deshabilitarNuevo}">
							<a4j:support event="onclick"
								action="#{confCarteraController.manejarIndeterminado}"
								reRender="panelRangoFechas" />
						</h:selectBooleanCheckbox>
						<h:outputText value="Indeterminado" />
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="10px" />

				<h:panelGrid columns="4">
					<a4j:commandButton value="Provisión de Créditos"
						styleClass="btnEstilos" style="width:150px"
						action="#{confCarteraController.mostrarProvisionCreditos}"
						reRender="panelContenidoNuevo" />
					<a4j:commandButton value="Provisión Procíclica"
						styleClass="btnEstilos" style="width:150px"
						action="#{confCarteraController.mostrarProvisionProciclica}"
						reRender="panelContenidoNuevo" />
					<a4j:commandButton value="Tiempo de Evaluación"
						styleClass="btnEstilos" style="width:150px"
						action="#{confCarteraController.mostrarTiempoEvaluacion}"
						reRender="panelContenidoNuevo" />
					<a4j:commandButton value="Modelos Contables"
						styleClass="btnEstilos" style="width:150px"
						action="#{confCarteraController.mostrarModelosContables}"
						reRender="panelContenidoNuevo" disabled="true" />
				</h:panelGrid>

				<script language=Javascript>
			       function isNumberKey(evt)
			       {
			          var charCode = (evt.which) ? evt.which : event.keyCode;
			          if (charCode != 46 && charCode > 31 
			            && (charCode < 48 || charCode > 57))
			             return false;
			
			          return true;
			       }
			       function isInteger(evt) {
			    	    evt = (evt) ? evt : window.event;
			    	    var charCode = (evt.which) ? evt.which : evt.keyCode;
			    	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			    	        return false;
			    	    }
			    	    return true;
			    	}
			    </script>

				<h:panelGroup id="panelContenidoNuevo">

					<h:panelGroup
						rendered="#{confCarteraController.habilitarProvisionCreditos}">
						<rich:spacer height="12px" />

						<table>
							<thead style="font-size: 12px; font-weight: bold;">
								<tr>
									<th rowspan="2"
										style="border: solid 1px blue; width: 25px; text-align: center">
										<h:outputText value="Nro." /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 90px; text-align: center">
										<h:outputText value="Conceptos" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 375px; text-align: center"
										colspan="2"><h:outputText value="Especificaciones" /></th>
									<th colspan="5"
										style="border: solid 1px blue; width: 400px; height: 30px; text-align: center">
										<h:outputText value="Categoría de Cartera de Créditos" /></th>
								</tr>
								<tr>
									<th
										style="border: solid 1px blue; width: 80px; text-align: center">
										<h:outputText value="Normal" /></th>
									<th
										style="border: solid 1px blue; width: 80px; text-align: center">
										<h:outputText value="CPP" /></th>
									<th
										style="border: solid 1px blue; width: 80px; text-align: center">
										<h:outputText value="Deficiente" /></th>
									<th
										style="border: solid 1px blue; width: 80px; text-align: center">
										<h:outputText value="Dudoso" /></th>
									<th
										style="border: solid 1px blue; width: 80px; text-align: center; height: 30px">
										<h:outputText value="Pérdida" /></th>
								</tr>
							</thead>
							<tbody style="font-size: 11px;">
								<!-- 01. Crédito Corporativo -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center; width: 25px">
										<h:outputText value="01" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left; width: 90px">
										<h:outputText value="Crédito Corporativo" /></td>
									<td
										style="border: solid 1px blue; text-align: left; width: 255px; height: 30px">
										<h:outputText value="Garantía Preferida Autoliquidable" />
									</td>
									<td
										style="border: solid 1px blue; text-align: left; width: 120px">
										<h:outputText value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center; width: 80px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[0].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td
										style="border: solid 1px blue; text-align: center; width: 80px;">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[0].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td
										style="border: solid 1px blue; text-align: center; width: 80px;">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[0].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td
										style="border: solid 1px blue; text-align: center; width: 80px;">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[0].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td
										style="border: solid 1px blue; text-align: center; width: 80px;">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[0].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[1].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[1].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[1].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[1].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[2].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[2].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[2].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[2].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[3].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[3].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[3].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[3].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 02. Grandes Empresas -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="02" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Grandes Empresas" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[4].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[4].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[4].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[4].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[4].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[5].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[5].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[5].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[5].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[6].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[6].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[6].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[6].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[7].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[7].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[7].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[7].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 03. Medianas Empresas -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="03" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Medianas Empresas" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[8].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[8].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[8].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[8].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[8].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[9].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[9].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[9].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[9].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[10].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[10].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[10].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[10].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[11].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[11].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[11].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[11].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 04. Pequeñas Empresas -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="04" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Pequeñas Empresas" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[12].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[12].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[12].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[12].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[12].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[13].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[13].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[13].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[13].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[14].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[14].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[14].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[14].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[15].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[15].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[15].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[15].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 05. Microempresas -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="05" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Microempresas" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[16].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[16].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[16].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[16].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[16].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[17].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[17].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[17].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[17].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[18].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[18].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[18].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[18].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[19].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[19].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[19].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[19].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 06. Consumo Revolvente -->
								<tr>
									<td rowspan="3"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="06" /></td>
									<td rowspan="3"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Consumo Revolvente" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="3"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[20].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[20].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[20].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[20].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[20].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<!-- <tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr> -->
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[21].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Pignoraticios - Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[22].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[22].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[22].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[22].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 07. Consumo No Revolvente -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="07" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Consumo No Revolvente" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[23].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[23].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[23].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[23].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[23].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<!-- <tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[25].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[25].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[25].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[25].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr> -->
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[24].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[24].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[24].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[24].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Pignoraticios - Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[26].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[26].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[26].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[26].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Contrato elegible referido a convenio de descuento. Por planilla." />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Cumplir requisitos Anexo 01" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[28].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[28].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[28].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[28].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 08. Hipotecario para Vivienda -->
								<tr>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="08" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Hipotecario para Vivienda" /></td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td rowspan="4"
										style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[29].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[29].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[29].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[29].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[29].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantía Preferida de Rápida y Muy Rápida Realización" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[30].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[30].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[30].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[30].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" /></td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Real" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[31].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[31].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[31].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[31].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Garantia Preferida Personal" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción No Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[32].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[32].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[32].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[32].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 09. Todos los Créditos -->
								<tr>
									<td style="border: solid 1px blue; text-align: center;"
										rowspan="2"><h:outputText value="09" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"
										rowspan="2"><h:outputText value="Todos los Créditos" />
									</td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Cualquier Garantía - Categoría Dudoso" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Más de 36 meses" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"
										rowspan="2"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[33].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[33].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[33].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[33].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[33].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"><h:outputText
											value="Cualquier Garantía - Categoría Pérdida" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Más de 24 meses" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[34].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[34].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[34].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[34].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

								<!-- 10. Otros Productos -->
								<tr>
									<td style="border: solid 1px blue; text-align: center;"><h:outputText
											value="10" />
									</td>
									<td
										style="border: solid 1px blue; text-align: left; height: 30px"
										colspan="3"><h:outputText
											value="Otros Productos (actividades, fondos de sepelio, mantenimiento de cuenta)" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[35].listaProvision[0].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[35].listaProvision[1].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[35].listaProvision[2].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[35].listaProvision[3].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazCredito[35].listaProvision[4].bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>

							</tbody>
						</table>
					</h:panelGroup>

					<h:panelGroup
						rendered="#{confCarteraController.habilitarProvisionProciclica}">
						<rich:spacer height="12px" />
						<table>
							<thead style="font-size: 12px; font-weight: bold;">
								<tr>
									<th rowspan="2"
										style="border: solid 1px blue; width: 25px; text-align: center">
										<h:outputText value="Nro." /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:outputText value="Conceptos" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 400px; text-align: center"
										colspan="2"><h:outputText
											value="Tasa de Provisión Procíclica - Especificaciones" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:outputText value="Componente Procíclico" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 90px; text-align: center">
										<h:outputText value="Mes 02" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 90px; text-align: center">
										<h:outputText value="Mes 04" /></th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 90px; text-align: center; height: 60px;">
										<h:outputText value="Mes 06" /></th>
								</tr>
							</thead>
							<tbody style="font-size: 11px;">
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center;"><h:outputText
											value="01" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Crédito Corporativo" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[0].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[0].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[0].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[0].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[1].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[1].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[1].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[1].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="02" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Grandes Empresas" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[2].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[2].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[2].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[2].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[3].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[3].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[3].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[3].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="03" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Medianas Empresas" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[4].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[4].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[4].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[4].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[5].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[5].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[5].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[5].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="04" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Pequeñas Empresas" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[6].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[6].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[6].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[6].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[7].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[7].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[7].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[7].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="05" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="MicroEmpresas" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[8].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[8].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[8].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[8].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[9].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[9].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[9].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[9].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="06" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Consumo revolvente" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía prefererida autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[10].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[10].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[10].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[10].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[11].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[11].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[11].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[11].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="3"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="07" />
									</td>
									<td rowspan="3"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Consumo No Revolvente" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía Prefererida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center;"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[12].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[12].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[12].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[12].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText
											value="Contratos Referidos a Convenios Elegibles" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[13].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[13].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[13].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[13].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td style="border: solid 1px blue; text-align: left;"
										colspan="2"><h:outputText value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[14].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[14].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[14].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[14].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: center"><h:outputText
											value="08" />
									</td>
									<td rowspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Hipotecario Para Vivienda" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Garantía Prefererida Autoliquidable" />
									</td>
									<td style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Porción Cubierta" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[15].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[15].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[15].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[15].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td colspan="2"
										style="border: solid 1px blue; text-align: left;"><h:outputText
											value="Todas las demás" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[16].prociclico.bdMontoProvision}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[16].prociclico.bdMontoConstitucion2}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center"><h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[16].prociclico.bdMontoConstitucion4}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td style="border: solid 1px blue; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaEspecificacionInterfazProciclico[16].prociclico.bdMontoConstitucion6}"
											onkeypress="return isNumberKey(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
							</tbody>
						</table>
					</h:panelGroup>

					<h:panelGroup
						rendered="#{confCarteraController.habilitarTiempoEvaluacion}">
						<rich:spacer height="12px" />

						<table>
							<thead style="font-size: 12px; font-weight: bold;">
								<tr>
									<th rowspan="2"
										style="border: solid 1px blue; width: 30px; text-align: center">Nro</th>
									<th rowspan="2"
										style="border: solid 1px blue; width: 150px; text-align: center">Conceptos</th>
									<th colspan="5"
										style="border: solid 1px blue; width: 500px; height: 30px; text-align: center">Tiempo
										a Considerar (Unidad = Días)</th>
								</tr>
								<tr>
									<th
										style="border: solid 1px blue; width: 100px; text-align: center">Normal</th>
									<th
										style="border: solid 1px blue; width: 100px; text-align: center">CPP</th>
									<th
										style="border: solid 1px blue; width: 100px; text-align: center">Deficiente</th>
									<th
										style="border: solid 1px blue; width: 100px; text-align: center">Dudoso</th>
									<th
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">Perdida</th>
								</tr>
							</thead>
							<tbody style="font-size: 11px;">
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="01" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Crédito Corporativo" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[0].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[1].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[2].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[3].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; height: 30px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[4].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="02" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Grandes Empresas" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[5].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[6].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[7].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[8].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[9].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="03" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Medianas Empresas" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[10].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[11].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[12].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[13].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[14].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="04" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Pequeñas Empresas" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[15].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[16].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[17].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[18].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[19].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="05" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Microempresas" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[20].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[21].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[22].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[23].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[24].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="06" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Consumo Revolvente" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[25].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[26].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[27].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[28].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[29].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="07" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Consumo No Revolvente" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[30].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[31].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[32].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[33].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[34].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
								<tr>
									<td
										style="border: solid 1px blue; width: 30px; text-align: center">
										<h:outputText value="08" />
									</td>
									<td
										style="border: solid 1px blue; width: 150px; text-align: center">
										<h:outputText value="Hipotecario para Vivienda" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[35].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[36].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[37].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[38].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
									<td
										style="border: solid 1px blue; width: 100px; text-align: center; height: 30px">
										<h:inputText
											value="#{confCarteraController.listaTiempoInterfaz[39].intTiempo}"
											onkeypress="return isInteger(event)" size="9"
											disabled="#{confCarteraController.deshabilitarNuevo}" />
									</td>
								</tr>
							</tbody>
						</table>
						<h:panelGroup id="filaOtrosProductos">
							<h:panelGroup
								rendered="#{confCarteraController.mostrarFilaOtrosProductos}">
								<table>
									<tr>
										<td
											style="border: solid 1px blue; width: 30px; text-align: center">
											<h:outputText value="10" />
										</td>
										<td
											style="border: solid 1px blue; width: 150px; text-align: center">
											<h:outputText value="Otros productos" />
										</td>
										<td
											style="border: solid 1px blue; width: 100px; text-align: center">
											<h:inputText
												value="#{confCarteraController.listaTiempoInterfaz[40].intTiempo}"
												onkeypress="return isInteger(event)" size="9"
												disabled="#{confCarteraController.deshabilitarNuevo}" />
										</td>
										<td
											style="border: solid 1px blue; width: 100px; text-align: center">
											<h:inputText
												value="#{confCarteraController.listaTiempoInterfaz[41].intTiempo}"
												onkeypress="return isInteger(event)" size="9"
												disabled="#{confCarteraController.deshabilitarNuevo}" />
										</td>
										<td
											style="border: solid 1px blue; width: 100px; text-align: center">
											<h:inputText
												value="#{confCarteraController.listaTiempoInterfaz[42].intTiempo}"
												onkeypress="return isInteger(event)" size="9"
												disabled="#{confCarteraController.deshabilitarNuevo}" />
										</td>
										<td
											style="border: solid 1px blue; width: 100px; text-align: center">
											<h:inputText
												value="#{confCarteraController.listaTiempoInterfaz[43].intTiempo}"
												onkeypress="return isInteger(event)" size="9"
												disabled="#{confCarteraController.deshabilitarNuevo}" />
										</td>
										<td
											style="border: solid 1px blue; width: 100px; height: 30px; text-align: center">
											<h:inputText
												value="#{confCarteraController.listaTiempoInterfaz[44].intTiempo}"
												onkeypress="return isInteger(event)" size="9"
												disabled="#{confCarteraController.deshabilitarNuevo}" />
										</td>
									</tr>
								</table>
							</h:panelGroup>
						</h:panelGroup>
					</h:panelGroup>

					<h:panelGroup
						rendered="#{confCarteraController.habilitarModelosContables}">
						<h:outputText value="Modelos Contables" />
					</h:panelGroup>

				</h:panelGroup>
			</rich:panel>
		</rich:panel>

	</rich:panel>
</h:form>