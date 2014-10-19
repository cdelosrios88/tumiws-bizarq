<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->			
	<!-- Fecha     : 05/10/2012               -->

<rich:modalPanel id="pBuscarPersonaTransferencia" width="580" height="170"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Persona"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarPersonaTransferencia" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <!-- <a4j:include viewId="/pages/banco/popup/buscarPersonaTransferencia.jsp"/> -->
   	<h:form>
	   	<h:panelGroup>	    	
	    	<h:panelGrid columns="12" id="panelPopUpBuscarPersona">
	    		<rich:column width="100">
					<h:outputText value="Tipo de Persona :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{transferenciaController.intTipoPersona}"
						disabled="#{transferenciaController.intTipoBuscarPersona==2}"
						style="width: 100px;">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelPopUpBuscarPersona"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="30">
           			<h:outputText  value="DNI :"
           				rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"/>
           			<h:outputText  value="RUC :"
           				rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"/>
           		</rich:column>
           		<rich:column width="150">
           			<h:inputText size="20"
           				rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
           				onkeydown="return validarNumDocIdentidad(this,event,8)"
           				value="#{transferenciaController.strFiltroTextoPersona}"/>
           			<h:inputText size="20"
           				rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
           				onkeydown="return validarNumDocIdentidad(this,event,12)"
           				value="#{transferenciaController.strFiltroTextoPersona}"/>
           		</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaPersona"
                    	action="#{transferenciaController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaPersona">
	    		<rich:dataTable
	    			var="item"
	                value="#{transferenciaController.listaPersona}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px">
			        
					<rich:column width="400" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			        		value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
			        	<h:outputText
			        		rendered="#{transferenciaController.intTipoPersona==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			        		value="#{item.juridica.strRazonSocial}"/>
			      	</rich:column>
					<rich:column width="120" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{transferenciaController.seleccionarPersona}"
							oncomplete="Richfaces.hideModalPanel('pBuscarPersonaTransferencia')"
							reRender="panelPersonaT,panelDocumentoT,panelCuentaT,panelCuentaBancariaBeneficiario,contPanelInferior">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="pBuscarCuentaBancaria" width="650" height="200"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Cuenta Bancaria"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCuentaBancaria" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarCuentaBancaria.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarCuentaBancariaBeneficiario" width="450" height="200"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Cuenta Bancaria"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCuentaBancariaBeneficiario" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
	<h:form id="frmBuscarCuentaBancariaBeneficiario">
	   	<h:panelGroup layout="block">
			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaCuentaBeneficiario"
	    			var="item" 
	                value="#{transferenciaController.beneficiarioSeleccionado.listaCuentaBancaria}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="400px">
			                           
					<rich:column width="300px">
						<f:facet name="header">
	                    	<h:outputText value="Numero"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.strNroCuentaBancaria}"/>
			      	</rich:column>
					<rich:column width="100px">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{transferenciaController.seleccionarCuentaBancariaBeneficiario}"
							oncomplete="Richfaces.hideModalPanel('pBuscarCuentaBancariaBeneficiario')"
							reRender="panelCuentaBancariaBeneficiario">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
			      	<f:facet name="footer">
						<rich:datascroller for="tablaCuenta" maxPages="5"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="pBuscarDocumento" width="630" height="230"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Documento"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDocumento" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarDocumento.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarPersonaCheque" width="580" height="170"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Persona"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarPersonaCheque" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarPersonaCheque.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pBuscarDocumentoCheque" width="630" height="230"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Documento"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDocumentoCheque" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarDocumentoCheque.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarCartaPoderC" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		 <h:panelGrid>
	    	<rich:column style="border: none;">
				<h:outputText value="Adjuntar Carta Poder"/>
	    	</rich:column>
	  	</h:panelGrid>
	</f:facet>
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
				             fileUploadListener="#{fileUploadController.adjuntarCartaPoder}"
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
				    		action="#{chequesController.aceptarAdjuntarCartaPoder}"
				    		reRender="panelCartaPoderC"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderC')"/>
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		reRender="panelCartaPoderC"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderC')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pBuscarEgreso" width="740" height="280"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Egreso"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarEgreso" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarEgreso.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarDocumentoTransferencia" width="630" height="230"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Documento"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDocumentoTransferencia" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/banco/popup/buscarDocumentoTransferencia.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarCartaPoderT" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		 <h:panelGrid>
	    	<rich:column style="border: none;">
				<h:outputText value="Adjuntar Carta Poder"/>
	    	</rich:column>
	  	</h:panelGrid>
	</f:facet>
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
				             fileUploadListener="#{fileUploadController.adjuntarCartaPoder}"
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
					    		action="#{transferenciaController.aceptarAdjuntarCartaPoder}"
					    		reRender="panelCartaPoderT"
				        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderT')"/>
					    </rich:column>
					    <rich:column style="border:none">
					    	<a4j:commandButton value="Cancelar" 
					    		styleClass="btnEstilos"
					    		reRender="panelCartaPoderT"
				        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderT')"/>
					 	</rich:column>
					</h:panelGrid>
	    		</h:panelGroup>
	    	</h:form>	
</rich:modalPanel>

<h:outputText value="#{transferenciaController.inicioPage}" />
<h:outputText value="#{chequesController.inicioPage}" />

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">

		<rich:tab label="Cheques" disabled="#{!chequesController.poseePermiso}">
            <a4j:include viewId="/pages/banco/tabCheques.jsp"/>
     	</rich:tab>
     	<rich:tab label="Transferencia" disabled="#{!transferenciaController.poseePermiso}">
        	<a4j:include viewId="/pages/banco/tabTransferencia.jsp"/>
    	</rich:tab>

</rich:tabPanel>