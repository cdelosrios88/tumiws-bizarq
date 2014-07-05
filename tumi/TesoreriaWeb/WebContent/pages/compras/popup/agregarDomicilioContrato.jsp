<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pAgregarDomicilioContrato" width="850" height="450" resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		 <h:panelGrid>
	    	<rich:column style="border: none;">
				<h:outputText value="Agregar Domicilio"/>
	    	</rich:column>
	  	</h:panelGrid>
	</f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarDomicilioContrato" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>

	<h:form>   
            
		<h:panelGrid columns="6" style="border:0px">
			<rich:column width="100">
				<h:outputText value="Tipo de Domicilio:"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu id="cboTipoDomicilio" 
					value="#{contratoController.domicilioNuevo.intTipoDomicilioCod}"
					style="width: 150px;">
					<f:selectItem itemLabel="Seleccione" itemValue="0"/>
					<tumih:selectItems var="sel" 
						cache="#{applicationScope.Constante.PARAM_T_TIPODOMICILIO}" 
						itemValue="#{sel.intIdDetalle}" 
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Tipo Dirección : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu id="cboTipoDireccion" 
					value="#{contratoController.domicilioNuevo.intTipoDireccionCod}"
					style="width: 150px;">
					<f:selectItem itemLabel="Seleccione" itemValue="0"/>
					<tumih:selectItems var="sel" 
						cache="#{applicationScope.Constante.PARAM_T_TIPODIRECCION}" 
						itemValue="#{sel.intIdDetalle}" 
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Tipo Vivienda : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu id="cboTipoVivienda" 
					value="#{contratoController.domicilioNuevo.intTipoViviendaCod}"
					style="width: 150px;">
					<f:selectItem itemLabel="Seleccione" itemValue="0"/>
					<tumih:selectItems var="sel" 
						cache="#{applicationScope.Constante.PARAM_T_TIPOVIVIENDA}" 
						itemValue="#{sel.intIdDetalle}" 
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
					
		<h:panelGrid columns="4">
			<rich:column width="100">
				<h:outputText value="Tipo de vía:"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					value="#{contratoController.domicilioNuevo.intTipoViaCod}"
					style="width: 150px;">
					<f:selectItem itemLabel="Seleccione" itemValue="0"/>
					<tumih:selectItems var="sel" 
						cache="#{applicationScope.Constante.PARAM_T_TIPOVIA}" 
						itemValue="#{sel.intIdDetalle}" 
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Nombre Vía :"/>
			</rich:column>
			<rich:column>
				<h:inputText id="nombreVia" 
					label="nombreVia" 
					value="#{contratoController.domicilioNuevo.strNombreVia}" 
					size="74"/>
			</rich:column>
		</h:panelGrid>
					
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Número Vía : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText value="#{contratoController.domicilioNuevo.intNumeroVia}"
					size="24"
					onkeydown="return validarEnteros(event)" 
					maxlength="5"/>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Interior:"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{contratoController.domicilioNuevo.strInterior}"
					size="74"/>
			</rich:column>
		</h:panelGrid>
			
					<h:panelGrid columns="4" id="domicilioPanel">
						<rich:column width="100">
							<h:outputText value="Tipo de Zona : "/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu 
						  		value="#{contratoController.domicilioNuevo.intTipoZonaCod}" 
						  		style="width: 150px;">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOZONA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="100">
							<h:outputText value="Nombre Zona:"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{contratoController.domicilioNuevo.strNombreZona}" 
								size="74"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="2">
						<rich:column width="100">
							<h:outputText value="Referencia:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:inputText value="#{contratoController.domicilioNuevo.strReferencia}" 
								size="75"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="6" id="ubicacionPanel">
						<rich:column width="100">
							<h:outputText value="Departamento:"/>
						</rich:column>
						<rich:column width="150">
							<h:selectOneMenu id="cboDepartamento"
								value="#{contratoController.domicilioNuevo.intParaUbigeoPkDpto}" 
								style="width:150px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{contratoController.listaUbigeoDepartamento}"
									itemValue="#{sel.intIdUbigeo}" 
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" 
									reRender="ubicacionPanel" 
									action="#{contratoController.seleccionarDepartamento}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="100">
							<h:outputText value="Provincia:"/>
						</rich:column>
						<rich:column width="150">
							<h:selectOneMenu id="cboProvincia"
								value="#{contratoController.domicilioNuevo.intParaUbigeoPkProvincia}"
								style="width:150px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{contratoController.listaUbigeoProvincia}"
									itemValue="#{sel.intIdUbigeo}"
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" 
									reRender="ubicacionPanel" 
									action="#{contratoController.seleccionarProvincia}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="100">
							<h:outputText value="Distrito:"/>
						</rich:column>
						<rich:column width="150">
							<h:selectOneMenu id="cboDistrito"
								value="#{contratoController.domicilioNuevo.intParaUbigeoPkDistrito}" 
								style="width:150px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{contratoController.listaUbigeoDistrito}"
									itemValue="#{sel.intIdUbigeo}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>							
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column width="100">
							<h:selectBooleanCheckbox value="#{contratoController.domicilioNuevo.fgCroquis}"/>
							<h:outputText value="Croquis"/>
						</rich:column>
						<rich:column width="150">
							<h:selectOneMenu
								value="#{contratoController.domicilioNuevo.intIdDirUrl}" 
								style="width:150px;">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOREFERENCIA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="padding-left:10px;">
							<h:inputText id="txtDirUrl" 
								value="#{contratoController.domicilioNuevo.strDirUrl}" 
								size="44"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGroup rendered="#{!(empty contratoController.domicilioNuevo.intParaItemArchivo)}">
						<h:outputText value="Archivo asociado: #{contratoController.domicilioNuevo.archivo.strNombrearchivo}"/>
					</h:panelGroup>
					<h:inputHidden value="#{fileUploadController.strTitle}"/>
					
					<h:panelGrid id="cboPnlUpload" columns="1" columnClasses="top,top">
			            <rich:fileUpload id="upload" 
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar" 
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen" 
				             listHeight="65" 
				             listWidth="320"
				             fileUploadListener="#{fileUploadController.adjuntarCroquis}"
							 maxFilesQuantity="1" 
							 doneLabel="Archivo cargado correctamente"
							 immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							 acceptedTypes="jpg, gif, png, bmp">
							 <f:facet name="label">
							 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							 </f:facet>
						</rich:fileUpload>
			        </h:panelGrid>
			        
					<h:panelGrid id="correspPanel" columns="1">
						<rich:column>
							<h:selectBooleanCheckbox value="#{contratoController.domicilioNuevo.fgCorrespondencia}"/>
							<h:outputText value="Envíar Correspondencia"/>							
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="2">
						<rich:column width="100" style="vertical-align: top">
							<h:outputText value="Observación : "/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="3" cols="80"
								value="#{contratoController.domicilioNuevo.strObservacion}"/>
						</rich:column>
					</h:panelGrid>
	       		
	       		 <h:panelGrid border="0" columns="4">
		            <rich:column width="250">
		            </rich:column>
		            <rich:column>
		            	<a4j:commandButton value="Grabar" 
		            		action="#{contratoController.aceptarAgregarDomicilio}" 
		            		styleClass="btnEstilos"
		            		reRender="panelDomicilioC"
		            		oncomplete="Richfaces.hideModalPanel('pAgregarDomicilioContrato')"/>
		            </rich:column>
		            <rich:column style="border:none">
		            	<a4j:commandButton value="Cancelar"
		            		styleClass="btnEstilos"
		            		oncomplete="Richfaces.hideModalPanel('pAgregarDomicilioContrato')">
				    	</a4j:commandButton>
				    </rich:column>
	            </h:panelGrid>
    </h:form>
</rich:modalPanel>