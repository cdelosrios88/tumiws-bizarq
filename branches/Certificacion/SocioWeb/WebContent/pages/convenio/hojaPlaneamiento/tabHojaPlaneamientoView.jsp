<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->
	<!-- Fecha     : 03/04/2012               -->

	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormHojaPlaneamiento">
	<rich:panel style="width: 920px;border:1px solid #17356f;background-color:#DEEBF5;">
		<h:outputText value="DATOS GENERALES"  style="font-weight:bold;"/>
		<rich:panel style="width: 890px;border:1px solid #17356f;background-color:#DEEBF5;">
	 	<h:panelGrid columns="6">
	 		<rich:column width="150"><h:outputText value="Tipo de Convenio" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column style="padding-left:10px;">
		 		<h:selectOneRadio id="rbTipoConvenio" value="#{hojaPlaneamientoController.strTipoConvenio}" disabled="true">
	         		<f:selectItem itemValue="1" itemLabel="Nuevo Convenio"/>
					<f:selectItem itemValue="2" itemLabel="Adenda del Convenio"/>
					<a4j:support event="onclick" reRender="pgBusqEntidad,tbTablasMenu" actionListener="#{hojaPlaneamientoController.enableDisableControls}"/>
	          	</h:selectOneRadio>
	         </rich:column>
	         <rich:column width="100">
	         	<h:outputText value="#{hojaPlaneamientoController.msgTipoConvenio}" styleClass="msgError"/>
	         </rich:column>
	         <rich:column width="190px" style="padding-left:10px;"><h:outputText value="Nro. Hoja de Planeamiento :"  style="padding-left:10px;font-weight:bold;"/></rich:column>
	         <rich:column><h:outputText style="font-weight:bold;" value="#{hojaPlaneamientoController.beanAdenda.id.intConvenio}"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid>
	 		<h:outputText value="#{hojaPlaneamientoController.msgTxtNuevaAdenda}" styleClass="msgError"/>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="4">
	         <rich:column width="150"><h:outputText value="Estado de Convenio"/></rich:column>
	         <rich:column><h:outputText value=":"/></rich:column>
	         <rich:column style="padding-left:10px;">
         		<h:selectOneMenu value="#{hojaPlaneamientoController.beanAdenda.intParaEstadoHojaPlan}" disabled="true">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden"/>
				</h:selectOneMenu>
	         </rich:column>
	         <rich:column><h:outputText value="#{hojaPlaneamientoController.msgEstadoConvenio}" styleClass="msgError"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid id="pgBusqEntidad" columns="3">
	 		<rich:column width="150"><h:outputText value="Entidad" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column style="padding-left:10px;">
	 			<a4j:commandButton value="Buscar" actionListener="#{hojaPlaneamientoController.listarEstructuras}"
	 				disabled="true" styleClass="btnEstilos"/>
	         </rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid id="pgEstructHojaPlan">
	 		<rich:dataTable id="tbEstructHojaPlan" 
	 			value="#{hojaPlaneamientoController.listaConvEstructuraDetComp}" 
	 			rows="#{hojaPlaneamientoController.intCantFilasActivas}"
	            var="item" 
	            rowKeyVar="rowKey" 
	            sortMode="single" 
	            rendered="#{not empty hojaPlaneamientoController.listaConvEstructuraDetComp}">
	      	<f:facet name="header">
	      		<rich:columnGroup>
	      			<rich:column><rich:spacer/></rich:column>
		       		<rich:column>
		       			<h:outputText value="Nivel de Entidad" />
		       		</rich:column>
		       		<rich:column>
		       			<h:outputText value="Nombre" />
		       		</rich:column>
		       		<rich:column>
		       			<h:outputText value="Modalidad" />
		       		</rich:column>
		       		<rich:column>
		       			<h:outputText value="Tipo de Socio" />
		       		</rich:column>
		       		<rich:column><rich:spacer/></rich:column>
	       		</rich:columnGroup>
	      	</f:facet>
	      	<rich:columnGroup rendered="#{item.convenioEstructuraDetalle.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
	      		<rich:column>
	        	<h:outputText value="#{rowKey+1}"/>
		        </rich:column>
		       	<rich:column>
		            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
		                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                property="#{item.estructura.id.intNivel}"/>
		        </rich:column>
		        <rich:column width="200px">
		        	<h:outputText value="#{item.estructura.juridica.strRazonSocial}"/>
		        </rich:column>
		        <rich:column>
		        	<f:facet name="header">
		            	<h:outputText value="Modalidad"/>
		            </f:facet>
		            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
		                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                property="#{item.estructuraDetalle.intParaModalidadCod}"/>
		        </rich:column>
		        <rich:column>
		        	<f:facet name="header">
		            	<h:outputText value="Tipo de Socio"/>
		            </f:facet>
		            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
		                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                property="#{item.estructuraDetalle.intParaTipoSocioCod}"/>
		        </rich:column>
		        <rich:column>
		        	<a4j:commandLink id="lnkQuitarConvEstDet" actionListener="#{hojaPlaneamientoController.removeConvenioEstructuraDet}" reRender="pgEstructHojaPlan" disabled="true">
   						<f:param name="rowKeyConvEstructDet" value="#{rowKey}"></f:param>
   						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
   						<rich:toolTip for="lnkQuitarConvEstDet" value="Quitar" followMouse="true"/>
   					</a4j:commandLink>
		        </rich:column>
	      	</rich:columnGroup>
	      	
	      </rich:dataTable>
	      <h:outputText value="#{hojaPlaneamientoController.msgTxtConvEstructura}" styleClass="msgError"/>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="3">
	 		<rich:column width="150"><h:outputText value="Sucursal que gestiona" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column style="padding-left:10px;">
		 		<h:selectOneMenu id="idCboSucursal" value="#{hojaPlaneamientoController.beanAdenda.intSeguSucursalPk}" disabled="true">
					<tumih:selectItems var="sel" value="#{hojaPlaneamientoController.listJuridicaSucursal}"
					itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				</h:selectOneMenu>
	        </rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid id="pFecDuracion" columns="7">
	 		<rich:column width="150"><h:outputText value="Tiempo de Duración" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column style="padding-left:10px;"><h:outputText value="Fecha de Inicio" /></rich:column>
	 		<rich:column>
		 		<rich:calendar popup="true" disabled="true"
		          value="#{hojaPlaneamientoController.beanAdenda.dtInicio}"
		          datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		          cellWidth="10px" cellHeight="20px"/>
	        </rich:column>
	        <rich:column>
		 		<h:selectOneRadio value="#{hojaPlaneamientoController.rbTiempoDurac}" disabled="true">
	         		<f:selectItem itemValue="1" itemLabel="Fecha de Cese"/>
	         		<f:selectItem itemValue="2" itemLabel="Indeterminado"/>
	         		<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pFecDuracion"/>
	          	</h:selectOneRadio>
	        </rich:column>
	        <rich:column style="padding-left:10px;">
	         	<rich:calendar popup="true" disabled="true"
		          rendered="#{hojaPlaneamientoController.formFecFinDurac}"
		          value="#{hojaPlaneamientoController.beanAdenda.dtCese}"
		          datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		          cellWidth="10px" cellHeight="20px"/>
	        </rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="1" border="0">
	 		<rich:column style="padding-left:355px;">
		 		<h:selectOneRadio id="rbOpcionFiltro" value="#{hojaPlaneamientoController.beanAdenda.intOpcionFiltroCredito}" disabled="true">
	         		<f:selectItem itemValue="1" itemLabel="Fecha de última cuota del cronograma"/>
	         		<f:selectItem itemValue="2" itemLabel="Fecha de solicitud de crédito"/>
	          	</h:selectOneRadio>
	         </rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="2">
	 		<rich:column><h:outputText value="#{hojaPlaneamientoController.msgFecInicio}" styleClass="msgError"/></rich:column>
	 		<h:outputText value="#{hojaPlaneamientoController.msgOpcionFiltroCred}" styleClass="msgError"/>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="4" border="0">
	 		<rich:column width="150"><h:outputText value="Fecha de Suscripción" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column style="padding-left:10px;">
		 		<rich:calendar popup="true" disabled="true"
		        	value="#{hojaPlaneamientoController.beanAdenda.dtSuscripcion}"
		          	datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		          	cellWidth="10px" cellHeight="20px"/>
	        </rich:column>
	        <rich:column><h:outputText value="#{hojaPlaneamientoController.msgFecSuscripcion}" styleClass="msgError"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid id="pgRetenPlan" columns="3" border="0">
	 		<rich:column width="150px"><h:outputText value="Retención de Planilla"/></rich:column>
	 		<rich:column>
		 		<h:selectOneRadio value="#{hojaPlaneamientoController.beanAdenda.intParaTipoRetencionCod}" disabled="true">
	         		<f:selectItem itemValue="1" itemLabel="% Planilla Efectuada"/>
	         		<f:selectItem itemValue="2" itemLabel="S/. por cada socio"/>
	          	</h:selectOneRadio>
	         </rich:column>
	         <rich:column><h:inputText size="10" value="#{hojaPlaneamientoController.beanAdenda.bdRetencion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" disabled="true"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="4" border="0">
	 		<rich:column>
	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.beanAdenda.boCartaAutorizacion}" disabled="true"/>
	 		</rich:column>
	 		<rich:column><h:outputText value="Carta de Autorización"/></rich:column>
	 		<rich:column style="padding-left:20px;">
	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.beanAdenda.boDonacion}" disabled="true"/>
	 		</rich:column>
	 		<rich:column><h:outputText value="Donación"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid columns="2" border="0">
	 		<rich:column>
	 			<h:outputText value="Documentos adjuntos:" />
	 		</rich:column>
	 		
	 		<rich:column style="padding-left:20px;">
		 		<h:panelGrid id="pUpldCartaPresent" columns="3" border="0">
		 	 		<rich:column>
		 	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkCartaPresent}" disabled="true">
		 	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldCartaPresent"/>
		 	 			</h:selectBooleanCheckbox>
		 	 		</rich:column>
		 	 		<rich:column width="150"><h:outputText value="Carta de Presentación:"/></rich:column>
		 	 		<rich:column>
		  	 		<rich:fileUpload id="uploadCartaPresent" addControlLabel="Adjuntar Archivo"
						clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
						disabled="#{hojaPlaneamientoController.upldCartaPresent}"
						uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
						fileUploadListener="#{hojaPlaneamientoController.adjuntarArchivo}"
						maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
						immediateUpload="#{applicationScope.FileUtil.autoUpload}"
						acceptedTypes="#{applicationScope.FileUtil.acceptedTypes}">
						<f:facet name="label">
						<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
						</f:facet>
					</rich:fileUpload>
					</rich:column>
		 	 	</h:panelGrid>
		 	 	<h:panelGrid>
		 	 		<!--Modificado por cdelosrios, 08/12/2013 -->
		 	 		<rich:column style="padding-left:205px;" 
		 	 			rendered="#{hojaPlaneamientoController.formCartaPresent}">
		 	 			<%--<h:inputText value="#{hojaPlaneamientoController.strNombreCartaPresentacion}" disabled="true" 
		 	 				rendered="#{hojaPlaneamientoController.formCartaPresent}" size="80"/>--%>
		 	 			<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank"> 
							<f:attribute name="archivo" value="#{hojaPlaneamientoController.beanAdenda.archivoDocumentoCartaPresent}"/>		
						</h:commandLink>
		 	 		</rich:column>
		 	 		<!--Fin modificado por cdelosrios, 08/12/2013 -->
		 	 	</h:panelGrid>
	 	 	
		 	 	<h:panelGrid id="pUpldConvSugerido" columns="3" border="0">
		 	 		<rich:column>
		 	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkConvSugerido}" disabled="true">
		 	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldConvSugerido"/>
		 	 			</h:selectBooleanCheckbox>
		 	 		</rich:column>
		 	 		<rich:column width="150"><h:outputText value="Convenio Sugerido:"/></rich:column>
		 	 		<rich:column>
						<rich:fileUpload id="uploadConvSug" addControlLabel="Adjuntar Archivo"
					        clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					        disabled="#{hojaPlaneamientoController.upldConvSugerido}"
					        uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
					        fileUploadListener="#{hojaPlaneamientoController.adjuntarArchivo}"
							maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
							immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							acceptedTypes="#{applicationScope.FileUtil.acceptedTypes}">
							<f:facet name="label">
							<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
						</rich:fileUpload>
					</rich:column>
		 	 	</h:panelGrid>
		 	 	<h:panelGrid>
		 	 		<!--Modificado por cdelosrios, 08/12/2013 -->
		 	 		<rich:column style="padding-left:205px;"
		 	 			rendered="#{hojaPlaneamientoController.formConvSugerido}">
		 	 			<%--
		 	 			<h:inputText value="#{hojaPlaneamientoController.strNombreConvenioSugerido}" disabled="true"  
		 	 				rendered="#{hojaPlaneamientoController.formConvSugerido}" size="80"/>--%>
		 	 			<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank">
							<f:attribute name="archivo" value="#{hojaPlaneamientoController.beanAdenda.archivoDocumentoConvSugerido}"/>		
						</h:commandLink>
		 	 		</rich:column>
		 	 		<!--Fin modificado por cdelosrios, 08/12/2013 -->
		 	 	</h:panelGrid>
	 	 	
		 	 	<h:panelGrid id="pUpldAdendaSugerida" columns="3" border="0">
		 	 		<rich:column>
		 	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkAdendaSugerida}" disabled="true">
		 	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldAdendaSugerida"/>
		 	 			</h:selectBooleanCheckbox>
		 	 		</rich:column>
		 	 		<rich:column width="150"><h:outputText value="Adenda Sugerida:"/></rich:column>
		 	 		<rich:column>
			  	 		<rich:fileUpload id="uploadAdenda" addControlLabel="Adjuntar Archivo"
							clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
							uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
							disabled="#{hojaPlaneamientoController.upldAdendaSugerida}"
							fileUploadListener="#{hojaPlaneamientoController.adjuntarArchivo}"
							maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
							immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							acceptedTypes="#{applicationScope.FileUtil.acceptedTypes}">
							<f:facet name="label">
							<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
						</rich:fileUpload>
					</rich:column>
		 	 	</h:panelGrid>
		 	 	<h:panelGrid>
		 	 		<!-- Agregado por cdelosrios, 08/12/2013 -->
		 	 		<rich:column style="padding-left:205px;"
		 	 			rendered="#{hojaPlaneamientoController.formAdendaSugerida}">
		 	 			<%--
		 	 			<h:inputText value="#{hojaPlaneamientoController.strNombreAdendaSugerida}" disabled="true"  
		 	 				rendered="#{hojaPlaneamientoController.formAdendaSugerida}" size="80"/>--%>
		 	 			<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank">
							<f:attribute name="archivo" value="#{hojaPlaneamientoController.beanAdenda.archivoDocumentoAdendaSugerida}"/>		
						</h:commandLink>
		 	 		</rich:column>
		 	 		<!-- Fin agregado por cdelosrios, 08/12/2013 -->
		 	 	</h:panelGrid>
	 	 	
		 	 	<h:panelGrid columns="1">
		 	 		<rich:column>
		 	 			<h:outputText value="#{hojaPlaneamientoController.msgConvSugerido}" styleClass="msgError"/>
		 	 		</rich:column>
		 	 	</h:panelGrid>
		 	</rich:column>
		</h:panelGrid>
		</rich:panel>
		
		<h:panelGrid>
		 	<h:outputText value="ESTUDIO DE MERCADO DE LA ENTIDAD"  style="font-weight: bold"/>
		 	<rich:spacer height="8px"/>
		 	<h:outputText value="POBLACIONAL"  style="font-weight: bold"/>
		</h:panelGrid>
	
		<rich:panel style="width: 890px;border:1px solid #17356f;background-color:#DEEBF5;">
			<div align="center">
			<h:panelGrid id="pLstPoblacion" border="0" cellspacing="0" cellpadding="0" >
				<rich:dataTable var="item" rowKeyVar="rowKey" width="300px"
						value="#{hojaPlaneamientoController.listaPoblacion}"
						rendered="#{not empty hojaPlaneamientoController.listaPoblacion}">
	 				<f:facet name="header">
		 				<rich:columnGroup>
	 						<rich:column>
	 							<rich:spacer/>
	 						</rich:column>
	 						<rich:column>
		 						<h:outputText value="Centros de Trabajo"/>
	 						</rich:column>
	 						<rich:column>
								 	<rich:spacer/>
	 						</rich:column>
	 						<rich:column>
								 	<rich:spacer/>
	 						</rich:column>
	 						<rich:column>
	 							<h:outputText value="Padrón"/>
	                      </rich:column>
	                      <rich:column>
	                      	<h:outputText value="Distancia a llegar (Tiempo)"/>
	                      </rich:column>
	                      <rich:column>
	                      	<rich:spacer/>
	                      </rich:column>
	 					</rich:columnGroup>
	 				</f:facet>
					<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
	                   <rich:column width="20">
	                       <h:outputText value="#{rowKey+1}"/>
	                   </rich:column>
	                   <rich:column>
	                       <h:inputTextarea cols="50" rows="5" value="#{item.strDescripcion}" disabled="true"/>
	                   </rich:column>
	                   <rich:column width="80px">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
				                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
				                               property="#{item.listaPoblacionDetalle[0].id.intTipoTrabajador}"/><br/>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
				                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
				                               property="#{item.listaPoblacionDetalle[1].id.intTipoTrabajador}"/><br/>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
				                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
				                               property="#{item.listaPoblacionDetalle[2].id.intTipoTrabajador}"/>
	                   </rich:column>
	                   <rich:column width="80px">
		                   	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
	                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                               property="#{item.listaPoblacionDetalle[0].id.intTipoSocio}"/><br/>
	                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
	                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                               property="#{item.listaPoblacionDetalle[1].id.intTipoSocio}"/><br/>
							<h:selectOneMenu value="#{item.listaPoblacionDetalle[2].id.intTipoSocio}" disabled="true">
								<f:selectItem itemValue="0" itemLabel="---"/>
				            	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
	                   </rich:column>
	                   <rich:column width="60px">
	                       <f:facet name="header">
	                           <h:outputText value="Padrón"/>
	                       </f:facet>
	                       <h:inputText value="#{item.listaPoblacionDetalle[0].intPadron}" size="5" onkeydown="return validarEnteros(event);" disabled="true"/>
	                       <h:inputText value="#{item.listaPoblacionDetalle[1].intPadron}" size="5" onkeydown="return validarEnteros(event);" disabled="true"/>
	                       <h:inputText value="#{item.listaPoblacionDetalle[2].intPadron}" size="5" onkeydown="return validarEnteros(event);" disabled="true"/>
	                   </rich:column>
	                   <rich:column width="280px">
	                       <h:inputTextarea cols="50" rows="5" value="#{item.bdDistancia}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" style="text-align:center" disabled="true"/>
	                   </rich:column>
	                   <rich:column>
			           		<a4j:commandLink id="lnkQuitarPoblacion" actionListener="#{hojaPlaneamientoController.removePoblacion}" reRender="pLstPoblacion" disabled="true">
	    						<f:param name="rowKeyPoblacion" value="#{rowKey}"></f:param>
	    						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
	    						<rich:toolTip for="lnkQuitarPoblacion" value="Quitar" followMouse="true"/>
	    					</a4j:commandLink>
			          	</rich:column>
			        </rich:columnGroup>
	               </rich:dataTable>
			</h:panelGrid>
			<br/>
			
			<h:panelGrid columns="3">
				<a4j:commandButton value="Agregar" 	styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.agregarPoblacion}" reRender="pLstPoblacion" disabled="true"/>
				<a4j:commandButton value="Calcular" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.calcularPoblacion}" reRender="pLstResumenPob" disabled="true"/>
				<a4j:commandButton value="Cancelar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.cancelarPoblacion}" reRender="pLstPoblacion" disabled="true"/>
			</h:panelGrid>
			<br/>
			
			<h:panelGrid id="pLstResumenPob">
	 			<rich:dataTable var="item" rowKeyVar="rowKey" width="655px" style="background-color:#ECF2E0;"
					value="#{hojaPlaneamientoController.listaResumenPoblacionDet}" 
					rendered="#{not empty hojaPlaneamientoController.listaResumenPoblacionDet}">
	              <rich:column>
	               	<f:facet name="header">
	                   <h:outputText value="Resumen Poblacional"/>
	               	</f:facet>
	               	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
	                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                               property="#{item.id.intTipoTrabajador}"/>
	              </rich:column>
	              <rich:column>
	                 <f:facet name="header">
	                     <h:outputText></h:outputText>
	                 </f:facet>
	                 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
	                               itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                               property="#{item.id.intTipoSocio}"/>
	              </rich:column>
	              <rich:column width="110px">
	                  <f:facet name="header">
	                      <h:outputText value="Padrón"/>
	                  </f:facet>
	                  <div align="center"><h:outputText value="#{item.intPadron}"/></div>
	              </rich:column>
	              
	              <rich:column width="110px">
	                  <f:facet name="header">
	                      <h:outputText value="Socios"/>
	                  </f:facet>
	                  <h:outputText />
	              </rich:column>
	              
	              <rich:column width="120px">
	                  <f:facet name="header">
	                      <h:outputText value="%1er Descuento"/>
	                  </f:facet>
	                  <h:outputText />
	              </rich:column>
	              
	              <rich:column width="110px">
	                  <f:facet name="header">
	                      <h:outputText value="%Morosidad"/>
	                  </f:facet>
	                  <h:outputText />
	              </rich:column>
	            </rich:dataTable>
			</h:panelGrid>
			</div>
		</rich:panel>
		
		<h:panelGrid>
			<h:outputText value="COMPETENCIA" style="font-weight:bold;"/>
		</h:panelGrid>
		
		<rich:panel style="width: 890px;border:1px solid #17356f;background-color:#DEEBF5;">
			<div align="center">
			<h:panelGrid id="pLstCompetencia">
				<rich:dataTable var="item" rowKeyVar="rowKey" width="810px"
					value="#{hojaPlaneamientoController.listaCompetencia}" 
					rendered="#{not empty hojaPlaneamientoController.listaCompetencia}">
					<f:facet name="header">
	 				<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
	 					<rich:column><rich:spacer/></rich:column>
	 					<rich:column>
	 						<h:outputText value="Convenio con otras Coop. o Ent. Financ."/>
	 					</rich:column>
	 					<rich:column>
	 						<h:outputText value="# de Socios"/>
	 					</rich:column>
	 					<rich:column>
	 						<h:outputText value="Plazos de Préstamos"/>
	 					</rich:column>
	 					<rich:column>
	 						<h:outputText value="Interés(%)"/>
	 					</rich:column>
	 					<rich:column>
	 						<h:outputText value="Monto de Aporte"/>
	 					</rich:column>
	 					<rich:column>
	 						<h:outputText value="Serv. que ofrece"/>
	 					</rich:column>
	 					<rich:column>
	 						<rich:spacer/>
	 					</rich:column>
	 				</rich:columnGroup>
					</f:facet>
					<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
						<rich:column width="20">
	                    	<div align="center"><h:outputText value="#{rowKey+1}"/></div>
	                 	</rich:column>
	                  	<rich:column width="260px">
	                      	<h:inputText value="#{item.strDescripcionEntidad}" size="45" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column width="80px">
	                      	<h:inputText value="#{item.intCantidad}" size="9" onkeydown="return validarEnteros(event);" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column width="140px">
	                      	<h:inputText value="#{item.bdPlazoPrestamo}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column width="80">
	                      	<h:inputText value="#{item.bdTasaPrestamo}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" size="9" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column width="115">
	                      	<h:inputText value="#{item.bdAporte}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" size="14" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column width="140px">
	                      	<h:inputText value="#{item.strServicio}" size="14" disabled="true"/>
	                  	</rich:column>
	                  	<rich:column>
	                  		<a4j:commandLink id="lnkQuitarCompetencia" actionListener="#{hojaPlaneamientoController.removeCompetencia}" reRender="pLstCompetencia" disabled="true">
	    						<f:param name="rowKeyCompetencia" value="#{rowKey}"></f:param>
	    						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
	    						<rich:toolTip for="lnkQuitarCompetencia" value="Quitar" followMouse="true"/>
	    					</a4j:commandLink>
	         			</rich:column>
					</rich:columnGroup>
	               </rich:dataTable>
			</h:panelGrid>
			<br/>
			<h:panelGrid columns="2">
				<a4j:commandButton value="Agregar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.addCompetencia}" reRender="pLstCompetencia" disabled="true"/>
            </h:panelGrid>
			</div>
		</rich:panel>
	</rich:panel>
</h:panelGrid>	