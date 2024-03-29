<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	
		<h:form>
	    	<h:panelGroup style="background-color:#DEEBF5">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
        				<rich:fileUpload id="upload"
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar"
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen"
				             listHeight="65" 
				             listWidth="320"
				             fileUploadListener="#{fileUploadController.adjuntarVoucher}"
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
		    	<h:panelGrid columns="3">
					<rich:column width="100">
					</rich:column>
					<rich:column width="100">
						<a4j:commandButton value="Aceptar" 
				    		styleClass="btnEstilos"
				    		action="#{cajaController.aceptarAdjuntarVoucher}"
				    		reRender="panelVoucher"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarVoucher')"/>
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		reRender="panelVoucher"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarVoucher')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>