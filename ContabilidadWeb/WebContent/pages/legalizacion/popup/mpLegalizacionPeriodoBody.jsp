<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Autor     : Javier Bermudez    			-->
<!-- Modulo    : Contabilidad                 	-->
<!-- Prototipo : PROTOTIPO AGREGAR LEGALIZACION -->
<!-- Fecha     : 20/07/2014               		-->

<a4j:include viewId="/pages/legalizacion/popup/popAdjuntarDocumentoPDT.jsp" />

<rich:modalPanel id="mpLegalizacionPeriodoAgregar" width="780"
	height="270" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Agregar Periodo Legalización"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideLegalizacionPeriodoAgregar" />
			<rich:componentControl for="mpLegalizacionPeriodoAgregar"
				attachTo="hideLegalizacionPeriodoAgregar" operation="hide"
				event="onclick" />
		</h:panelGroup>
	</f:facet>

	<h:form id="frmLegalizacionPeriodoAgregar">
		<h:panelGroup id="divLegalizacionPeriodoAgregar" layout="block"
			style="padding-left:5px; padding-right:5px">
			<h:panelGrid styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column>
						<a4j:commandButton
							actionListener="#{controlLibroContableController.grabarLegalizacionPeriodo}" 
							oncomplete="if(#{controlLibroContableController.strMsgError == ''}){Richfaces.hideModalPanel('mpLegalizacionPeriodoAgregar')}"
							value="Grabar" reRender="divTblLibroContable,divTblLegalizacion,divTblLegalizacionPeriodo,msgError" styleClass="btnEstilos"></a4j:commandButton>
					</rich:column>
					<rich:column>
						<a4j:commandButton
							actionListener="#{controlLibroContableController.cancelarLegalizacionPeriodo}"
							oncomplete="Richfaces.hideModalPanel('mpLegalizacionPeriodoAgregar')"
							value="Cancelar" styleClass="btnEstilos"></a4j:commandButton>
					</rich:column>
				</rich:columnGroup>
				<rich:columnGroup>
	   				<rich:column style="text-align: left">
		   				<h:outputText id="msgError" value="#{controlLibroContableController.strMsgError}" 
							styleClass="msgError"
							style="font-weight:bold; text-align: left"
							rendered="#{!controlLibroContableController.strMsgError}"/>	
	   				</rich:column>
	   			</rich:columnGroup>
			</h:panelGrid>

			<h:panelGroup layout="block"
				style="border:1px solid #17356f; padding:15px">
				<h:panelGrid columns="5" styleClass="tableCellBorder4">
					<rich:column>
						<h:outputText value="Periodo"></h:outputText>
					</rich:column>
					<rich:column width="90">
						<h:selectOneMenu style="width: 90px;"
							value="#{controlLibroContableController.intMes}">
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" propertySort="intOrden" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="idAnio"
							value="#{controlLibroContableController.intAnio}">
							<f:selectItems id="lstYears"
								value="#{controlLibroContableController.listYears}" />

						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:outputText value="Legalización"></h:outputText>
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="cboTipoLibroBusq" style="width:350px" value="#{controlLibroContableController.strPkLibroLegalizacion}" disabled="#{controlLibroContableController.isStrPkLibroLegalizacion}">
							<tumih:selectItems var="sel"
							value="#{controlLibroContableController.listaLegalizacionesSaldo}"
							itemValue="#{sel.libroLegalizacion.id.intPersEmpresa}-#{sel.libroLegalizacion.id.intParaLibroContable}-#{sel.libroLegalizacion.id.intItemLibroLegalizacion}"
							itemLabel="#{sel.libroLegalizacion.id.intItemLibroLegalizacion} - #{sel.strJuriRazonSocial} - Saldo : #{sel.intSaldolegalizacion}"/>
							<f:selectItem itemValue="0" itemLabel="Ninguna" />
						</h:selectOneMenu>
					</rich:column>

				</h:panelGrid>
				<rich:spacer height="10px"></rich:spacer>
				<h:panelGrid columns="4" styleClass="tableCellBorder4">
					<rich:column>
						<h:outputText value="Inicial : "></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText value="#{controlLibroContableController.libroContableDetalle.intFolioInicio}" style="width: 80px;"></h:inputText>
					</rich:column>
					<rich:column>
						<h:outputText value="Final : "></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText value="#{controlLibroContableController.libroContableDetalle.intFolioFin}" style="width: 80px;"></h:inputText>
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="10px"></rich:spacer>
				<h:panelGrid columns="3" styleClass="tableCellBorder4" id="panelAdjuntoDocumentoPDT">
					<rich:column width="118" rendered = "#{controlLibroContableController.isAdjunto}" >
						<h:inputText 
							value="#{controlLibroContableController.archivoAdjuntoDocumentoPDT.strNombrearchivo}"
							size="77"
							readonly="true" 
							style="background-color: #BFBFBF; width : 450px;"/>
					</rich:column>
					<rich:column width="120" rendered = "#{controlLibroContableController.isAdjunto}" >
						<a4j:commandButton
							rendered="#{empty controlLibroContableController.archivoAdjuntoDocumentoPDT}"
	                		styleClass="btnEstilos1"
	                		value="Adjuntar Declaración PDT"
	                		oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentePDT')"
	                		reRender="divAdjuntoDocumentoPDT,panelAdjuntoDocumentoPDT"
	                		style="width:150px"/>                 		
	                	<a4j:commandButton
							rendered="#{not empty controlLibroContableController.archivoAdjuntoDocumentoPDT}"
	                		styleClass="btnEstilos"
	                		value="Quitar Declaración PDT"
	                		reRender="pAdjuntarDocumentePDT,panelAdjuntoDocumentoPDT"
	                		action="#{controlLibroContableController.quitarAdjuntoDocumentoPDT}"
	                		style="width:150px"/>
					</rich:column>
					<rich:column  
						rendered="#{not empty controlLibroContableController.archivoAdjuntoDocumentoPDT} && #{controlLibroContableController.isAdjunto}">
						<h:commandLink  value=" Descargar" target="_blank"
							actionListener="#{fileUploadControllerContabilidad.descargarArchivo}">
							<f:attribute name="archivo" value="#{controlLibroContableController.archivoAdjuntoDocumentoPDT}"/>
						</h:commandLink>
					</rich:column>
				</h:panelGrid>
			</h:panelGroup>
		</h:panelGroup>
	</h:form>
</rich:modalPanel>