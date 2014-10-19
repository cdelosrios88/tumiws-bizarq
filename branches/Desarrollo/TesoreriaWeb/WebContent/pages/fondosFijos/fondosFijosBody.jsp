<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->			
	<!-- Fecha     :                		  -->
	<!-- Modificado por : Junior Chávez  -->
	<!-- Fecha modificación : 13.05.2014 -->	

<rich:modalPanel id="pBuscarEntidadCaja" width="580" height="170"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Dependencia"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarEntidadCaja" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarEntidadCaja.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarPersonaCaja" width="580" height="170"
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
           		<rich:componentControl for="pBuscarPersonaCaja" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarPersonaCaja.jsp"/>
</rich:modalPanel>

<!-- jchavez 23.06.2014 Popup buscar Gestor Ingreso-->
<rich:modalPanel id="pBuscarGestorIngresoCaja" width="680" height="170"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Gestor Ingreso"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarGestorIngresoCaja" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarGestorIngresoCaja.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarPersona" width="580" height="170"
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
           		<rich:componentControl for="pBuscarPersona" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarPersona.jsp"/>
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
   <a4j:include viewId="/pages/fondosFijos/popup/buscarDocumento.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pBuscarDocumentoCaja" width="630" height="230"
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
           		<rich:componentControl for="pBuscarDocumentoCaja" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarDocumentoCaja.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarMovimientoIngresoSocio" width="1100" height="300"
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
           		<rich:componentControl for="pBuscarMovimientoIngresoSocio" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarMovimientoIngresoSocio.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarAportacionIngresoSocio" width="600" height="250"
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
           		<rich:componentControl for="pBuscarAportacionIngresoSocio" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarAportacionIngresoSocio.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarDocPlanillaEfectuadaCaja" width="1000" height="280"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header"> 
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Planilla Efectuada"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDocPlanillaEfectuadaCaja" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarPlanillaEfectuadaCaja.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarCartaPoderF" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
				    		action="#{fondosFijosController.aceptarAdjuntarCartaPoder}"
				    		reRender="panelCartaPoderF"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderF')"/>
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		reRender="panelCartaPoderF"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarCartaPoderF')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarVoucher" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		 <h:panelGrid>
	    	<rich:column style="border: none;">
				<h:outputText value="Adjuntar Voucher"/>
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

</rich:modalPanel>

<rich:modalPanel id="pAdjuntarChequeIngreso" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Adjuntar Cheque"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAdjuntarChequeIngreso" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
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
			             fileUploadListener="#{fileUploadController.adjuntarCheque}"
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
			    		reRender="panelAdjuntoChequeIngreso"
			    		styleClass="btnEstilos"
			    		action="#{cajaController.aceptarAdjuntarCheque}"
		        		oncomplete="Richfaces.hideModalPanel('pAdjuntarChequeIngreso')"/>
			 	</rich:column>
			</h:panelGrid>
    	</h:panelGroup>
    </h:form>
  
</rich:modalPanel>	

<rich:modalPanel id="pBuscarPersonaT" width="580" height="170"
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
           		<rich:componentControl for="pBuscarPersonaT" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarPersonaTelecredito.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarDocumentoT" width="630" height="230"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Documento T"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarDocumentoT" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarDocumentoTelecredito.jsp"/>
</rich:modalPanel>

<!-- Autor: jchavez / Tarea: Creación / Fecha: 21.08.2014 /  -->
<rich:modalPanel id="pBuscarCuentaBancariaT" width="650" height="200"
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
           		<rich:componentControl for="pBuscarCuentaBancariaT" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/fondosFijos/popup/buscarCuentaBancariaTelecredito.jsp"/>
</rich:modalPanel>

	<a4j:region>
		<a4j:jsFunction name="getPersonaRolC" reRender="cboPersonaRolC,opDatosPersonaSelect"
			action="#{cajaController.cargarListaPersonaRol}">
			<f:param name="pCboTipoPersonaC"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="getTipoDocumentoC" reRender="cboTipoDocumentoC,opCuentaSocioC,panelDocumentoC,opDatosPersonaSelect,btnBuscarEntidadC,btnBuscarPersonaC"
			action="#{cajaController.cargarListaTipoDocumento}">
			<f:param name="pCboPersonaRolC"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="selecRegistroDocumento" reRender="txtMontoAPagar,linkSelect,rbOpcDoc"
			actionListener="#{cajaController.disableRegistroDocumento}">
			<f:param name="pRdoRegistroDocumento"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="getFiltroBusqC" reRender="panelPopUpBuscarPersonaC"
			action="#{cajaController.habilitarFiltroBusqueda}">
			<f:param name="pCboFiltroBusqC"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="getCuentaC" reRender="pgModalidadC, cboModalidadC"
			actionListener="#{cajaController.seleccionarCuenta}">
			<f:param name="pCboCuentaC"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="getValorModalidadC" reRender="msgErrorSubCondCta,pgModalidadC"
			actionListener="#{cajaController.seleccionarModalidad}">
			<f:param name="pCboModalidadC"></f:param>
		</a4j:jsFunction>

		<a4j:jsFunction name="addTotalIngresoSocioC" reRender="txtMontoIngresadoTotalC"
			actionListener="#{cajaController.getBaseCalculo}">
			<f:param name="bdTotalIngreso"/>
		</a4j:jsFunction>
	</a4j:region>

	<h:outputLabel value="#{fondosFijosController.inicioPage}"/>
	<h:outputLabel value="#{cajaController.inicioPage}"/>
	<h:outputLabel value="#{telecreditoController.inicioPage}"/>
	
	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
		<rich:tab label="Fondos Fijos" 
			disabled="#{!fondosFijosController.poseePermiso}">
            <a4j:include viewId="/pages/fondosFijos/tabFondosFijos.jsp"/>
     	</rich:tab>
     	
     	<rich:tab label="Caja" disabled="#{!cajaController.permiso}">
        	<a4j:include viewId="/pages/fondosFijos/tabCaja.jsp"/>
    	</rich:tab>
    	
    	<rich:tab label="Telecrédito" disabled="#{!telecreditoController.permiso}" rendered="#{telecreditoController.estadoTab}">
        	<a4j:include viewId="/pages/fondosFijos/tabTelecredito.jsp"/>
    	</rich:tab>
	</rich:tabPanel>  
