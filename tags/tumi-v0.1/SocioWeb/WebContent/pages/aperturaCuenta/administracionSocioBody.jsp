<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->

<rich:modalPanel id="mpUpDelSocioNatu" width="490" height="260" 
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
				<h:outputText value="Opciones" />
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
			   		<rich:componentControl for="mpUpDelSocioNatu" operation="hide" event="onclick" />
			   </h:graphicImage>
           </h:panelGroup>
        </f:facet>
		<h:form id="formUpDelEstrucOrg">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 450px;">                
         <h:panelGrid columns="1" border="0" cellspacing="4" >
             <h:outputText value="¿Qué operación desea realizar con el registro seleccionado?" style="padding-left: 120px;"/>
         </h:panelGrid>
         <rich:spacer height="16px"/>
			<h:panelGrid columns="3" border="0"  style="width: 450px;">
             	<a4j:commandButton value="Ver" actionListener="#{socioController.obtenerSocioEstructura}" styleClass="btnEstilos"
              			reRender="divFormSocio,divPanelControlSocio" oncomplete="Richfaces.hideModalPanel('mpUpDelSocioNatu')" />
              	<h:commandButton value="Apertura de Cuentas" actionListener="#{aperturaCuentaController.showFormAperturaCuenta}" 
              			styleClass="btnEstilos1"/>
              	<h:commandButton value="Ver Apertura de Cuenta" actionListener="#{aperturaCuentaController.irModificarAperturaCuenta}" 
              			styleClass="btnEstilos1"/>
             </h:panelGrid>
		</rich:panel>
		<!-- Botones de las impreciones -->
              <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 400px;" id="rpBotonesImpresion">    
					<h:panelGrid columns="1"  border="0" cellspacing="4" >
                   		<h:outputText value="Imprimir Formatos" style="padding-right: 2px;"/>
                <rich:spacer height="16px"/>
              	<h:panelGrid columns="3" border="0"  style="width: 390px;">
         		<rich:column><h:commandButton id="btnImprimir1" value="Solicitud Ingreso" action="#{socioController.imprimirSolicitudIngreso}" styleClass="btnEstilos1" /></rich:column>
              	<rich:column  rendered="#{socioController.mostrarBoton}"><h:commandButton id="btnImprimir2" value="Solicitud Fonret" styleClass="btnEstilos1"
				action="#{socioController.imprimirSolicitudDeAfiliacion}"/></rich:column>
         		<!--<rich:column rendered="#{socioController.mostrarBoton}"><h:commandButton id="btnImprimir3" value="Solicitud Renuncia Fonret" action="#{socioController.renunciaFonret}" styleClass="btnEstilos1" /></rich:column>-->
              	</h:panelGrid>
				</h:panelGrid>
             </rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="pAgregarDomicilio" width="720" height="540" 
	resizeable="false" style="background-color:#DEEBF5;" onhide="enableDomicilio()">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Domicilio"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="pAgregarDomicilio" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <a4j:include viewId="/pages/popup/domicilioBody.jsp" layout="block"/>
</rich:modalPanel>

<rich:modalPanel id="pViewDomicilio" width="720" height="540" 
	resizeable="false" style="background-color:#DEEBF5;">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Domicilio"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="pViewDomicilio" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <a4j:include viewId="/pages/popup/domicilioViewBody.jsp" layout="block"/>
</rich:modalPanel>
    
<rich:modalPanel id="pAgregarComunicacion" width="780" height="212"
resizeable="false" style="background-color:#DEEBF5;" onhide="enableComunicacion()">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Comunicación"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarComunicacion" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include id="inclComunicacion" viewId="/pages/popup/comunicacionBody.jsp" layout="block"/>
</rich:modalPanel>
	
<rich:modalPanel id="mpCuentaBancaria" width="890" height="405"
	 resizeable="false" style="background-color:#DEEBF5" onhide="enableCtaBancaria()">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cuenta Bancaria"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpCuentaBancaria" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclCtaBancaria" viewId="/pages/popup/cuentaBancariaBody.jsp" layout="block"/>
</rich:modalPanel>

<rich:modalPanel id="mpSocioEmpresa" width="940" height="520"
	 resizeable="false" style="background-color:#DEEBF5" onhide="enableSocioEmpresa()">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Empresa del Socio"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpSocioEmpresa" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclSocioEmpresa" viewId="/pages/aperturaCuenta/popup/socioEmpresaBody.jsp" layout="block"/>
</rich:modalPanel>
	
<rich:modalPanel id="mpSocioEstructura" width="696" height="320"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Unidades Ejecutoras"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpSocioEstructura" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclSocioEstructura" viewId="/pages/aperturaCuenta/popup/socioDependenciaBody.jsp" layout="block"/>
</rich:modalPanel>

<rich:modalPanel id="mpRoles" width="254" height="270"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Roles Actuales"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpRoles" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclRolesPopup" viewId="/pages/aperturaCuenta/popup/rolesBody.jsp" layout="block"/>
</rich:modalPanel>

<rich:modalPanel id="mpSocioPadron" width="712" height="270"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Datos de Padron MINSA" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSocioPadron"/>
               <rich:componentControl for="mpSocioPadron" attachTo="hidelinkSocioPadron" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formSocioPadron">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
               <h:panelGrid border="0" columns="6">
               	  <rich:columnGroup>
               		<rich:column colspan="6" style="text-align:justify; text-justify:auto">
               			<h:outputText id="lblCodigo" value="Los siguientes datos se han obtenido de los registros del MINSA. Por favor, 
                   									complete correctamente los campos de Nombre, Apellido Paterno y Apellido Materno. 
                   									Luego, elija Aceptar para continuar con el registro del socio." styleClass="tamanioLetra"></h:outputText>
               		</rich:column>
               	  </rich:columnGroup>
               	  <rich:columnGroup>
               	  	<rich:column>
               	  		<h:outputText value="Lib. Electoral"></h:outputText>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:inputText value="#{socioController.beanSocioComp.padron.strLibEle}" disabled="true"></h:inputText>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:outputText value="Fecha Nac."></h:outputText>
               	  	</rich:column>
               	  	<rich:column>
               	  		<rich:calendar value="#{socioController.beanSocioComp.padron.dtFecNac}" 
               	  					datePattern="dd/MM/yyyy" inputStyle="width:76px disabled=" disabled="true"/>
               	  	</rich:column>
               	  </rich:columnGroup>
               	  <rich:columnGroup>
               	  	<rich:column>
               	  		<h:outputText value="Nombre Completo"/>
               	  	</rich:column>
               	  	<rich:column colspan="3">
               	  		<h:inputText size="62" value="#{socioController.beanSocioComp.padron.strNombre}" disabled="true"></h:inputText>
               	  	</rich:column>
               	  </rich:columnGroup>
               	  <rich:columnGroup>
               	  	<rich:column>
               	  		<h:outputText value="Ap. Paterno"/>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:inputText value="#{socioController.beanSocioComp.socio.strApePatSoc}"/>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:outputText value="Ap. Materno"/>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:inputText value="#{socioController.beanSocioComp.socio.strApeMatSoc}"/>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:outputText value="Nombres"/>
               	  	</rich:column>
               	  	<rich:column>
               	  		<h:inputText value="#{socioController.beanSocioComp.socio.strNombreSoc}"/>
               	  	</rich:column>
               	  </rich:columnGroup>
               </h:panelGrid>
               <rich:spacer height="16px"/>
               	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
                    <a4j:commandButton value="Aceptar" styleClass="btnEstilos" actionListener="#{socioController.completeNombreReniec}"
                    				reRender="divFormSocio" oncomplete="Richfaces.hideModalPanel('mpSocioPadron')"/>
                </h:panelGrid>
               <h:panelGrid>
				<h:inputHidden id="hiddenIdEstrucOrg"/>
			</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>

<rich:modalPanel id="mpFichaSocio" width="990" height="540" 
	 	style="background-color:#DEEBF5;" onhide="enableFichaSocio()">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Ficha de Socio"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="mpFichaSocio" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <h:form id="frmFichaSocio">
	     <h:panelGroup layout="block">
	     	<a4j:include viewId="/pages/aperturaCuenta/socioNaturalBody.jsp" layout="block"/>
	     </h:panelGroup>
     </h:form>
</rich:modalPanel>

<rich:modalPanel id="mpBeneficiario" width="960" height="450" style="background-color:#DEEBF5;">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Beneficiario"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="mpBeneficiario" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <h:panelGroup layout="block">
     	<a4j:include viewId="/pages/aperturaCuenta/popup/socioBeneficiarioBody.jsp" layout="block"/>
     </h:panelGroup>
</rich:modalPanel>

<rich:modalPanel id="mpAdjuntoPersona" width="600" height="250" style="background-color:#DEEBF5;">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Adjuntar Firma y Foto de Socio"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="mpAdjuntoPersona" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <h:panelGroup layout="block">
     	<a4j:include viewId="/pages/aperturaCuenta/popup/adjuntoArchivoPersonaBody.jsp" layout="block"/>
     </h:panelGroup>
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/confirmDialogBody.jsp"/>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>

<a4j:include viewId="/pages/popup/tercerosBody.jsp"/>

<a4j:region>
<!--  CGD 28.10.2013
	<a4j:jsFunction name="selecSocioNatu"
		actionListener="#{socioController.setSelectedSocioNatu}">
		<f:param name="rowSocioNatu"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectSocioAperCta" 
		actionListener="#{aperturaCuentaController.setSelectedSocioNatu}">
		<f:param name="rowSocioApertCta"/>
	</a4j:jsFunction
-->
<!-- AGREGADO PARA OPTIMIZACIONDE BUSQUEDA DE SOCIO CON FILTROS -->



	<a4j:jsFunction name="selecSocioNatuYSocioAperCta"
		actionListener="#{socioController.selectSocioBusqueda}">
		<f:param name="item"></f:param>
	</a4j:jsFunction>

	
	<a4j:jsFunction name="selectSocioAperCta" 
		actionListener="#{socioController.setearSocioNatuEnAperturaCuentaController}">
		<f:param name="rowSocioApertCta"/>
	</a4j:jsFunction>
	
	
	<a4j:jsFunction name="selecTipoPersona" reRender="cboTipoIdentidad"
		actionListener="#{socioController.loadListDocumentoBusq}">
		<f:param name="pIntTipoPersona"/>
	</a4j:jsFunction>
	
	<%-- 
	<a4j:jsFunction name="selecTipoDocIdentidad" reRender="pgDocumentos,txtOtroDocumento"
		actionListener="#{socioController.resetDocIdentidadMaxLength}">
		<f:param name="pIntTipoDocIdentidad"></f:param>
	</a4j:jsFunction>--%>
	
	<a4j:jsFunction name="selecTipoContrato" reRender="calIniContrato,calFinContrato"
		actionListener="#{socioController.resetFechasContrato}">
		<f:param name="pIntTipoContrato"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="putFile" reRender="divFormSocioNatu,divContrato,divFirma,divFoto"
		actionListener="#{socioController.putFile}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="putFileBeneficiario" reRender="pgImages,divFirmaBeneficiario,divFotoBeneficiario,frmAdjuntoPersona"
		actionListener="#{beneficiarioController.putFile}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="putFileDomicilio" reRender="pgFormDomicilio"
		actionListener="#{domicilioController.putFile}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="acceptMessage" 
		actionListener="#{messageController.acceptMessage}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="rejectMessage" reRender="divFormSocio,pgFirmante"
		actionListener="#{messageController.rejectMessage}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="validarMensaje" reRender="divFormSocio,pgFirmante"
		actionListener="#{socioController.validarMensaje}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="validarMensajeBeneficiario" reRender="pBeneficiario"
		actionListener="#{beneficiarioController.validarMensaje}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="validarSocioPadron" reRender="mpSocioPadron"
		actionListener="#{socioController.validarSocioPadron}"
		oncomplete="Richfaces.showModalPanel('mpSocioPadron')">
	</a4j:jsFunction>
</a4j:region>

<rich:jQuery name="disableSocioInputs" selector="#divFormSocio :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableSocioInputs" selector="#divFormSocio :input" query="removeAttr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableDomicilio" selector="#frmDomicilio :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableDomicilio" selector="#frmDomicilio :input" query="removeAttr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableComunicacion" selector="#frmComunicacion :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableComunicacion" selector="#frmComunicacion :input" query="removeAttr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableCtaBancaria" selector="#frmCtaBancaria :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableCtaBancaria" selector="#frmCtaBancaria :input" query="removeAttr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableSocioEmpresa" selector="#frmSocioEmpresa :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableSocioEmpresa" selector="#frmSocioEmpresa :input" query="removeAttr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableFichaSocio" selector="#frmFichaSocio :input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="disableFormBeneficiario" selector="#frmBeneficiario :input" query="attr('disabled','disabled');" timing="onJScall" />

<h:form id="frmSocioNatural">
    <rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
     	 <rich:tab label="Socio / Cliente" disabled="#{socioController.blnSocioCliente}">
            <a4j:include viewId="/pages/aperturaCuenta/socioBody.jsp"/>
     	 </rich:tab>
	     <rich:tab label="Modificación de Datos" disabled="#{socioController.blnModificacion}">
	     	
	     </rich:tab>
	     <rich:tab label="Archivamiento" disabled="#{socioController.blnArchivamiento}">
	     	
	     </rich:tab>
     </rich:tabPanel>
</h:form>