<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO DOMICILIOS     -->
	<!-- Fecha     : 25/01/2012               -->

	<script language="JavaScript" src="/SeguridadWeb/js/main.js"  type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormDomicilio">
	<h:form id="frmDomicilio">
    	<rich:panel  style="border: 0px solid #17356f;background-color:#DEEBF5;width:670px;">
            <h:panelGrid id="pgBtnDomicilio" border="0" columns="2">
	            <rich:column>
	            	<a4j:commandButton value="Grabar" 
	            		actionListener="#{domicilioController.grabarDomicilio}" 
	            		styleClass="btnEstilos" 
	            		disabled="#{!domicilioController.habilitarEditar}"
	            		reRender="#{domicilioController.strIdListDomicilio}"
	            		oncomplete="Richfaces.hideModalPanel('#{domicilioController.strIdModalDomicilio}')"/>
	            </rich:column>
	            <rich:column style="border:none">
	            	<a4j:commandButton value="Cancelar" 
	            		disabled="#{!domicilioController.habilitarEditar}"
	            		styleClass="btnEstilos">
			    		<rich:componentControl for="pAgregarDomicilio" operation="hide" event="onclick"/>
			    	</a4j:commandButton>
			    </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/>
            <rich:separator height="5px"/>
            
            <h:panelGrid id="pgFormDomicilio" columns="2" border="0">
	            <rich:panel id="pMarco1" style="border:1px; solid #17356f ;width:660px; background-color:#DEEBF5">
		        	<h:panelGrid columns="6" style="border:0px">
						<rich:column style="width: 123px; border:none">
							<h:outputText value="Tipo de Domicilio:"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu id="cboTipoDomicilio" 
						  		disabled="#{!domicilioController.habilitarEditar}"
						  		value="#{domicilioController.beanDomicilio.intTipoDomicilioCod}">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPODOMICILIO}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						
						<rich:column style="width:110px;">
							<h:outputText value="Tipo de dirección:"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu id="cboTipoDireccion" 
						  		disabled="#{!domicilioController.habilitarEditar}"
						  		value="#{domicilioController.beanDomicilio.intTipoDireccionCod}">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPODIRECCION}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						
						<rich:column>
							<h:outputText value="Tipo de vivienda:"/>
					  	</rich:column>
					  	
					  	<rich:column>
						  	<h:selectOneMenu id="cboTipoVivienda" 
						  		disabled="#{!domicilioController.habilitarEditar}"
						  		value="#{domicilioController.beanDomicilio.intTipoViviendaCod}">
							  	<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOVIVIENDA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width:123px;">
			                 <h:outputText value="Tipo de vía:"/>
			            </rich:column>
			            <rich:column>
						  	<h:selectOneMenu id="cboTipoVia" 
						  		disabled="#{!domicilioController.habilitarEditar}"
						  		value="#{domicilioController.beanDomicilio.intTipoViaCod}">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOVIA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="width:123px;">
							<h:outputText value="Nombre de la vía:"/>
						</rich:column>
						<rich:column>
							<h:inputText id="nombreVia" label="nombreVia" value="#{domicilioController.beanDomicilio.strNombreVia}" 
								size="40" disabled="#{!domicilioController.habilitarEditar}"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="6">
						<rich:column style="width:123px;">
			                <h:outputText />
			            </rich:column>
			            <rich:column>
							<h:outputText value="Número de vía:"/>
					  	</rich:column>
					  	<rich:column style="padding-left:10px">
					  		<h:inputText value="#{domicilioController.beanDomicilio.intNumeroVia}" 
					  			disabled="#{!domicilioController.habilitarEditar}"
					  			size="10" 
					  			style="width:82px" 
					  			onkeydown="return validarEnteros(event)" 
					  			maxlength="5"/>
						</rich:column>
						<rich:column style="padding-left:10px">
							<h:outputText value="Interior:"/>
						</rich:column>
						<rich:column style="padding-left:10px">
							<h:inputText value="#{domicilioController.beanDomicilio.strInterior}" 
								disabled="#{!domicilioController.habilitarEditar}"
								size="10"/>
						</rich:column>
					</h:panelGrid>
			
					<h:panelGrid columns="4" id="domicilioPanel">
						<rich:column style="width: 125px; border:none;">
							<h:outputText value="Tipo de Zona:"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu id="cboTipoZona" 
						  		disabled="#{!domicilioController.habilitarEditar}"
						  		value="#{domicilioController.beanDomicilio.intTipoZonaCod}" styleClass="SelectOption2">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOZONA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="width:130px; border:none;padding-left:10px;">
							<h:outputText value="Nombre de la Zona:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:inputText value="#{domicilioController.beanDomicilio.strNombreZona}" size="30" 
								disabled="#{!domicilioController.habilitarEditar}"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="2">
						<rich:column style="width: 125px;">
							<h:outputText value="Referencia:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:inputText value="#{domicilioController.beanDomicilio.strReferencia}" 
								size="80" disabled="#{!domicilioController.habilitarEditar}"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="6" id="ubicacionPanel">
						<rich:column style="width: 125px;">
							<h:outputText value="Departamento:"/>
						</rich:column>
						<rich:column>
							<h:selectOneMenu id="cboDepartamento" 
								disabled="#{!domicilioController.habilitarEditar}"
								value="#{domicilioController.beanDomicilio.intParaUbigeoPkDpto}" 
								style="width:120px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{domicilioController.listaUbigeoDepartamento}"
									itemValue="#{sel.intIdUbigeo}" 
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="ubicacionPanel" action="#{domicilioController.seleccionarDepartamento}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="width:80px;padding-left:10px;">
							<h:outputText value="Provincia:"/>
						</rich:column>
						<rich:column>
							<h:selectOneMenu id="cboProvincia" 
								disabled="#{!domicilioController.habilitarEditar}"
								value="#{domicilioController.beanDomicilio.intParaUbigeoPkProvincia}"
								style="width:120px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{domicilioController.listaUbigeoProvincia}"
									itemValue="#{sel.intIdUbigeo}"
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="ubicacionPanel" action="#{domicilioController.seleccionarProvincia}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="width:80px;padding-left:10px;">
							<h:outputText value="Distrito:"/>
						</rich:column>
						<rich:column>
							<h:selectOneMenu id="cboDistrito" 
								disabled="#{!domicilioController.habilitarEditar}"
								value="#{domicilioController.beanDomicilio.intParaUbigeoPkDistrito}" 
								style="width:150px;">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									value="#{domicilioController.listaUbigeoDistrito}"
									itemValue="#{sel.intIdUbigeo}" 
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="ubicacionPanel"/>
							</h:selectOneMenu>							
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
						<rich:column style="width:125px;">
							<h:selectBooleanCheckbox value="#{domicilioController.beanDomicilio.fgCroquis}" disabled="#{!domicilioController.habilitarEditar}"/>
							<h:outputText value="Croquis"/>
						</rich:column>
						<rich:column>
							<h:selectOneMenu id="cboDireccion" 
								disabled="#{!domicilioController.habilitarEditar}"
								value="#{domicilioController.beanDomicilio.intIdDirUrl}" 
								styleClass="SelectOption" 
								onchange="changeOption()">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOREFERENCIA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="padding-left:10px;">
							<h:inputText  id="txtDirUrl" value="#{domicilioController.beanDomicilio.strDirUrl}" size="40" 
								disabled="#{!domicilioController.habilitarEditar}"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGroup rendered="#{!(empty domicilioController.beanDomicilio.intParaItemArchivo)}">
						<h:outputText value="Archivo asociado: #{domicilioController.beanDomicilio.archivo.strNombrearchivo}"/>
					</h:panelGroup>
					<h:inputHidden value="#{fileUploadController.strTitle}"/>
					
					<h:panelGrid id="cboPnlUpload" columns="1" columnClasses="top,top">
			            <rich:fileUpload id="upload" 
			            	 disabled="#{!domicilioController.habilitarEditar}"
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
							<h:selectBooleanCheckbox value="#{domicilioController.beanDomicilio.fgCorrespondencia}"
								disabled="#{!domicilioController.habilitarEditar}"/>
							Envíar Correspondencia
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="2" id="observPanel">
						<rich:column style="width: 123px;">
							<h:outputText value="Observación"/>
						</rich:column>
						<rich:column>
							<h:inputTextarea rows="3" cols="80" 
								disabled="#{!domicilioController.habilitarEditar}"
								value="#{domicilioController.beanDomicilio.strObservacion}"/>
						</rich:column>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>