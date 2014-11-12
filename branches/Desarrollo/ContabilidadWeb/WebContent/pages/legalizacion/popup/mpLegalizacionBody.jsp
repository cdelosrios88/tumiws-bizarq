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

<a4j:include viewId="/pages/legalizacion/popup/popAdjuntarDocumento.jsp" />
<a4j:include viewId="/pages/legalizacion/popup/mpBuscarPersonaJuridicaBody.jsp" />

<rich:modalPanel id="mpLegalizacionAgregar" width="950" height="290"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Agregar Legalización"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideLegalizacionAgregar" />
			<rich:componentControl for="mpLegalizacionAgregar"
				attachTo="hideLegalizacionAgregar" operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>

	<h:form id="frmLegalizacionAgregar">
		<h:panelGroup id="divLegalizacionAgregar" layout="block"
			style="padding-left:5px; padding-right:5px">
			<h:panelGrid styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column>
						<a4j:commandButton
							action="#{controlLibroContableController.grabarLegalizacion}"
							oncomplete="if(#{controlLibroContableController.strMsgError == ''}){Richfaces.hideModalPanel('mpLegalizacionAgregar')}"
							value="Grabar" reRender="divTblLibroContable,divTblLegalizacion,divTblLegalizacionPeriodo,msgError"
							styleClass="btnEstilos"></a4j:commandButton>
					</rich:column>
					<rich:column>
						<a4j:commandButton
							actionListener="#{controlLibroContableController.cancelarLegalizacion}"
							oncomplete="Richfaces.hideModalPanel('mpLegalizacionAgregar')"
							value="Cancelar"
							reRender="divTblLibroContable,divTblLegalizacion"
							styleClass="btnEstilos"></a4j:commandButton>
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
				<h:panelGrid columns="7" styleClass="tableCellBorder4">
					<rich:column>
						<h:outputText value="Notaria Pública"></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText readonly="true" id="txtNotariaPublica"
							value="#{controlLibroContableController.strNotariaPublica}"
							style="width: 200px; background-color: #BFBFBF;"></h:inputText>
					</rich:column>
					<rich:column>
						<a4j:commandButton styleClass="btnEstilos" value="Buscar"
							rendered="#{empty controlLibroContableController.personaSeleccionada}"
							action="#{controlLibroContableController.abrirPopUpBuscarPersonaJuridica}"
							oncomplete="Richfaces.showModalPanel('pBuscarPersonaJuridica')"
							reRender="panelPopUpBuscarPersonaJuridica, tablaPersona"
							style="width:80x" />
					</rich:column>
					<rich:column>
						<h:outputText value="Fecha Legalización"></h:outputText>
					</rich:column>
					<rich:column style="border: none">
						<rich:calendar
							value="#{controlLibroContableController.libroLegalizacion.dtFechaLegalizacion}"
							datePattern="dd/MM/yyyy" style="width:50px">
						</rich:calendar>
					</rich:column>
					<rich:column>
						<h:outputText value="Certificado"></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText
							onkeydown="return validarNumDocIdentidad(this,event,10)"
							value="#{controlLibroContableController.libroLegalizacion.intNroCertificado}"
							style=" width : 50px;"></h:inputText>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="10px"></rich:spacer>
				<!--inicio descargar archivo-->
				<h:panelGrid columns="8" styleClass="tableCellBorder4" id="panelAdjuntoCertificadoLeg">
					<rich:column>
						<h:outputText value="Número de Folio : Inicio "></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText
							onkeydown="return validarNumDocIdentidad(this,event,10)"
							value="#{controlLibroContableController.libroLegalizacion.intFolioInicio}"
							style=" width : 80px;"></h:inputText>
					</rich:column>
					<rich:column>
						<h:outputText value="Fin "></h:outputText>
					</rich:column>
					<rich:column>
						<h:inputText
							onkeydown="return validarNumDocIdentidad(this,event,10)"
							value="#{controlLibroContableController.libroLegalizacion.intFolioFin}"
							style=" width : 80px;"></h:inputText>
					</rich:column>
	           			<rich:column >
	           				<h:outputText value="Adjuntar Certificado"></h:outputText>
	           			</rich:column>
					<rich:column width="118">
						<h:inputText 
							value="#{controlLibroContableController.archivoAdjuntoCertificadoLegalizacion.strNombrearchivo}"
							size="77"
							readonly="true" 
							style="background-color: #BFBFBF; width : 200px;"/>
					</rich:column>
					<rich:column width="120">
						<a4j:commandButton
							rendered="#{empty controlLibroContableController.archivoAdjuntoCertificadoLegalizacion}"
	                		styleClass="btnEstilos1"
	                		value="Adjuntar Certificado"
	                		oncomplete="Richfaces.showModalPanel('pAdjuntarCertificadoLeg')"
	                		reRender="divAdjuntoCertificadoLeg,panelAdjuntoCertificadoLeg, "
	                		style="width:130px"/>                 		
	                	<a4j:commandButton
							rendered="#{not empty controlLibroContableController.archivoAdjuntoCertificadoLegalizacion}"
	                		styleClass="btnEstilos"
	                		value="Quitar Adjunto"
	                		reRender="pAdjuntarCertificadoLeg,panelAdjuntoCertificadoLeg"
	                		action="#{controlLibroContableController.quitarAdjuntoCertificadoLegalizacion}"
	                		style="width:130px"/>
					</rich:column>
					<rich:column  
						rendered="#{not empty controlLibroContableController.archivoAdjuntoCertificadoLegalizacion}">
						<h:commandLink  value=" Descargar" 
										target="_blank"
										actionListener="#{fileUploadControllerContabilidad.descargarArchivo}">
							<f:attribute name="archivo" value="#{controlLibroContableController.archivoAdjuntoCertificadoLegalizacion}"/>
						</h:commandLink>
					</rich:column>
				</h:panelGrid>
	           <!--fin descargar archivo-->
				<rich:spacer height="10px"></rich:spacer>
				<!--inicio descargar archivo-->
				<h:panelGrid columns="2" styleClass="tableCellBorder4" id="panelEnlaceLegal" rendered="#{controlLibroContableController.isEnlazarNulo}">
					<rich:column>
						<h:outputText value="Legalizaciones Pendientes de Enlazar "></h:outputText>
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="cboLegalizacionNulo" style="width:350px" value="#{controlLibroContableController.strPkLibroContableDetalle}">
							<f:selectItem itemValue="0" itemLabel="No Enlazar" />
							<tumih:selectItems var="sel"
							value="#{controlLibroContableController.listaLibroContableDetalleNulo}"
							itemValue="#{sel.libroContableDetalle.id.intEmpresaPk}-#{sel.libroContableDetalle.id.intLibroContable}-#{sel.libroContableDetalle.id.intItemLibroContableDet}"
							itemLabel="#{sel.libroContableDetalle.id.intItemLibroContableDet} - Periodo : #{sel.strLicdPeriodoNombre} - Inicio : #{sel.libroContableDetalle.intFolioInicio} Fin : #{sel.libroContableDetalle.intFolioFin}"/>
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
			</h:panelGroup>
		</h:panelGroup>
	</h:form>
</rich:modalPanel>