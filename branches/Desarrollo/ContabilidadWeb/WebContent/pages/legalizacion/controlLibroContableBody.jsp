<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<a4j:include viewId="/pages/legalizacion/popup/mpLegalizacionBody.jsp" />
<a4j:include viewId="/pages/legalizacion/popup/mpLegalizacionPeriodoBody.jsp" />
<a4j:include viewId="/pages/legalizacion/popup/popBotonesLegalizacion.jsp" />
<a4j:include viewId="/pages/legalizacion/popup/popBotonesLibroContable.jsp" />

<a4j:region>
	<a4j:jsFunction name="selecTipoLibroContable" reRender="divTblLegalizacion,divTblLegalizacionPeriodo"
		action="#{controlLibroContableController.listadosControlLibroContable}">
		<f:param name="pTipoLibroContable"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmControlLibroContable">
	<h:outputLabel value="#{controlLibroContableController.inicioPage}"/>
	<h:panelGroup id="divCabecera" layout="block" style="padding:15px">
		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
			<rich:columnGroup>
				<rich:column>
					<h:outputText value="CONTROL LIBROS CONTABLES"
						style="font-weight:bold; font-size:14px"></h:outputText>
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>

		<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
			<rich:columnGroup>
				<rich:column>
					<h:outputText value="Libro Contable"></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu 
							value="#{controlLibroContableController.intBuscaParaLibroContable}">
							<f:selectItem itemLabel="..Todas.." itemValue="0"/>
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_TIPOLIBROCONTABLE}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" />
						</h:selectOneMenu>
				</rich:column>

				<rich:column>
					<a4j:commandButton value="Buscar" reRender="divTblLibroContable" action="#{controlLibroContableController.buscarLibrosLegalizacion}"
						styleClass="btnEstilos" style=" width : 80px;"></a4j:commandButton>
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>
	</h:panelGroup>

	<h:panelGroup id="divTblLibroContable" layout="block"
		style="padding:15px">
		<rich:extendedDataTable 
			id="tblLibroContable" 
			style="margin:0 auto" 
			var="item"
			value="#{controlLibroContableController.listaLibrosLegalizaciones}"
			rowKeyVar="rowKey" 
			rows="5" 
			sortMode="single" 
			width="950px"
			height="180px">
			<rich:column width="50px">
				<f:facet name="header">
					<h:outputText value="Item" style="font-weight:bold"></h:outputText>
				</f:facet>
				<h:outputText value="#{rowKey + 1}"></h:outputText>
			</rich:column>
			<rich:column width="350px">
				<f:facet name="header">
					<h:outputText value="Libro Contable" style="font-weight:bold"></h:outputText>
				</f:facet>
				<h:outputText value="#{item.strNombreLibro}"></h:outputText>
			</rich:column>
			<rich:column width="250" style="text-align: left">
				<f:facet name="header">
					<h:outputText value="Ultimo Periodo Legalizado"
						style="font-weight:bold"></h:outputText>
				</f:facet>
				<h:outputText value="#{item.strPeriodoNombre}"></h:outputText>
			</rich:column>
			<rich:column width="150">
				<f:facet name="header">
					<h:outputText value="Saldo" style="font-weight:bold"></h:outputText>
				</f:facet>
				<h:outputText value="#{item.intSaldolegalizacion}"></h:outputText>
			</rich:column>
			<rich:column width="150">
				<f:facet name="header">
					<h:outputText value="Pendiente" style="font-weight:bold"></h:outputText>
				</f:facet>
				<h:outputText value="#{item.intSaldoPendiente}"></h:outputText>
			</rich:column>

			<f:facet name="footer">
				<h:panelGroup layout="block">
					<rich:datascroller for="tblLibroContable" maxPages="10" />
				</h:panelGroup>
			</f:facet>
		</rich:extendedDataTable>
	</h:panelGroup>
	<h:panelGroup>
		<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp" />
	</h:panelGroup>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandButton value="Nuevo"
				reRender="divFormLegalizacion,divTblLegalizacion,divTblLegalizacionPeriodo"
				action="#{controlLibroContableController.nuevoControlLibro}"
				styleClass="btnEstilos"></a4j:commandButton>
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Cancelar"
				reRender="divFormLegalizacion,frmControlLibroContable"
				action="#{controlLibroContableController.cancelarControlLibro}"
				styleClass="btnEstilos"></a4j:commandButton>
		</rich:column>
	</h:panelGrid>
	<h:panelGroup id="divFormLegalizacion" layout="block">
		<h:panelGroup layout="block"
			style="border:1px solid #17356f; padding:15px"
			rendered="#{controlLibroContableController.blnShowDivLegalizacion}">
			<h:panelGrid columns="6" styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column>
						<h:outputText value="Libro Contable"></h:outputText>
					</rich:column>
					<rich:column>
						<h:selectOneMenu 
							value="#{controlLibroContableController.intParaLibroContable}"
							onchange="selecTipoLibroContable(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemLabel="..Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_TIPOLIBROCONTABLE}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" />
						</h:selectOneMenu>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
			<h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px">
				<rich:columnGroup>
					<rich:column>
						<h:outputText value="Legalización"></h:outputText>
					</rich:column>
					<rich:column>
						<a4j:commandButton value="Agregar" styleClass="btnEstilos"
							action="#{controlLibroContableController.agregarLegalizacion}"
							oncomplete="if(#{controlLibroContableController.intParaLibroContable != 0}){Richfaces.showModalPanel('mpLegalizacionAgregar')}"
							reRender="divLegalizacionAgregar"></a4j:commandButton>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
			<rich:spacer height="10px"></rich:spacer>
			<h:panelGroup id="divTblLegalizacion" layout="block">
				<rich:extendedDataTable id="tblLegalizacion" style="margin:0 auto"
					var="item" 
					width="850px" 
					height="170px" 
					enableContextMenu="false" 
					value="#{controlLibroContableController.listaLegalizaciones}"
					rowKeyVar="rowKey"
					rows="5" 
					sortMode="single">
					<rich:column width="40">
						<f:facet name="header">
							<h:outputText value="Item" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{rowKey + 1}"></h:outputText>
					</rich:column>
					<rich:column width="40">
						<f:facet name="header">
							<h:outputText value="Nro." style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroLegalizacion.id.intItemLibroLegalizacion}"></h:outputText>
					</rich:column>
					<rich:column width="350" style="text-align:left">
						<f:facet name="header">
							<h:outputText value="Notaria Pública" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strJuriRazonSocial} - Ruc : #{item.strPersRuc}"></h:outputText>
					</rich:column>
					<rich:column width="100">
						<f:facet name="header">
							<h:outputText value="Legalización" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroLegalizacion.dtFechaLegalizacion}">
							<f:convertDateTime type="date" pattern="dd/MM/yyyy"/></h:outputText>
					</rich:column>
					<rich:column width="80">
						<f:facet name="header">
							<h:outputText value="Certificado" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroLegalizacion.intNroCertificado}"></h:outputText>
					</rich:column>
					<rich:column width="80">
						<f:facet name="header">
							<h:outputText value="Folio Inicio" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroLegalizacion.intFolioInicio}"></h:outputText>
					</rich:column>
					<rich:column width="80">
						<f:facet name="header">
							<h:outputText value="Folio Fin" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroLegalizacion.intFolioFin}"></h:outputText>
					</rich:column>
					<rich:column width="80">
						<f:facet name="header">
							<h:outputText value="Saldo" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.intSaldolegalizacion}"></h:outputText>
					</rich:column>
					<f:facet name="footer">
						<h:panelGroup layout="block">
							<rich:datascroller for="tblLegalizacion" maxPages="10" />
						</h:panelGroup>
					</f:facet>
					<a4j:support event="onRowClick"
						actionListener="#{controlLibroContableController.seleccionarLegalizacion}"
						oncomplete="Richfaces.showModalPanel('mpBotonesLegalizacion')"
						reRender="formBotonesLegalizacion">
						<f:attribute name="itemLegalizacion" value="#{item}" />
					</a4j:support>
				</rich:extendedDataTable>
			</h:panelGroup>
			<h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px">
				<rich:columnGroup>
					<rich:column>
						<h:outputText value="Detalle"></h:outputText>
					</rich:column>
					<rich:column>
						<a4j:commandButton value="Agregar" styleClass="btnEstilos"
							actionListener="#{controlLibroContableController.agregarLegalizacionPeriodo}"
							oncomplete="if(#{controlLibroContableController.intParaLibroContable != 0}){Richfaces.showModalPanel('mpLegalizacionPeriodoAgregar')}"
							reRender="divLegalizacionPeriodoAgregar"></a4j:commandButton>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
			<rich:spacer height="10px"></rich:spacer>
			<h:panelGroup id="8" id="divTblLegalizacionPeriodo">
				<rich:extendedDataTable id="tblLegalizacionPeriodo" style="margin:0 auto"
					var="item" value="#{controlLibroContableController.listaLibroContableDetalle}"
					onRowClick="selecModeloDetalle('#{rowKey}')" rowKeyVar="rowKey"
					rows="5" sortMode="single" width="830px" height="170px">
					<rich:column width="30">
						<f:facet name="header">
							<h:outputText value="Nro." style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{rowKey + 1}"></h:outputText>
					</rich:column>
					<rich:column width="200">
						<f:facet name="header">
							<h:outputText value="Periodo" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strLicdPeriodoNombre}"></h:outputText>
					</rich:column>
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Inicio" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroContableDetalle.intFolioInicio}"></h:outputText>
					</rich:column>
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Fin" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.libroContableDetalle.intFolioFin}"></h:outputText>
					</rich:column>
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Legalización" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strContLibroLegalizacion}"></h:outputText>
					</rich:column>
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Archivo" style="font-weight:bold"></h:outputText>
						</f:facet>
						<h:outputText value="Ver"></h:outputText>
					</rich:column>

					<f:facet name="footer">
						<h:panelGroup layout="block">
							<rich:datascroller for="tblLegalizacionPeriodo" maxPages="10" />
						</h:panelGroup>
					</f:facet>
					<a4j:support event="onRowClick"
						actionListener="#{controlLibroContableController.seleccionarLibroContable}"
						oncomplete="Richfaces.showModalPanel('mpBotonesLibrosContables')"
						reRender="formBotonesLibrosContables">
						<f:attribute name="itemLibro" value="#{item}" />
					</a4j:support>
				</rich:extendedDataTable>
			</h:panelGroup>
		</h:panelGroup>
	</h:panelGroup>
</h:form>