<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         				  -->
	<!-- Autor     : Rodolfo Villarreal     				  -->
	<!-- Modulo    : Contabilidad / Reclamos y/o DEvoluciones -->
	<!-- Fecha     : 11/08/2014               				  -->


<rich:modalPanel id="pAdjuntarCertificadoLeg" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Archivo"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarCertificadoLeg" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	    <h:form>
	    	<h:panelGroup style="background-color:#DEEBF5" id="divAdjuntoCertificadoLeg">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
        				<rich:fileUpload id="upload" 
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar" 
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen" 
				             listHeight="65" 
				             listWidth="320"
				             fileUploadListener="#{fileUploadControllerContabilidad.adjuntarCertificadoLegalizacion}"
							 maxFilesQuantity="1"
							 doneLabel="Archivo cargado correctamente"
							 immediateUpload="false"
							 acceptedTypes="jpg, gif, png, bmp">
							 <f:facet name="label">
							 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							 </f:facet>
						</rich:fileUpload>
		        	</rich:column>
		    	</h:panelGrid>
		    	<h:panelGrid columns="2">
					<rich:column width="100">
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Aceptar" 
				    		reRender="panelAdjuntoCertificadoLeg"
				    		styleClass="btnEstilos"
				    		action="#{controlLibroContableController.aceptarAdjuntarCertificadoLegalizacion}"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCertificadoLeg')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
	</rich:modalPanel>