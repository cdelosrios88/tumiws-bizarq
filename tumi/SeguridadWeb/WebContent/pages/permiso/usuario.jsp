<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%--@ taglib uri="http://java.sun.com/jsf/core" prefix="c" --%>
<%--@ taglib uri="http://java.sun.com/jstl/core" prefix="c"--%>
<%--@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"--%> 
<%--@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"--%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Seguridad : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos	  -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Usuarios-Perfiles        -->
	<!-- Fecha     : 11/10/2011               -->

<script language="JavaScript" src="/js/main.js"  type="text/javascript"></script>

<script type="text/javascript">
	  function jsSeleccionUsuario(itemId){
         document.getElementById("frmPrincipal:idTabUsuario:hiddenIndexUsuario").value=itemId;
         var lControl = document.getElementById("frmPrincipal:idTabUsuario:hiddenBtnSeleccionUsuario");
         if(lControl){
      	   lControl.click();
         }
      }
	
      function jsSeleccionPerfil(itemId){
    	  document.getElementById("frmPrincipal:idIncludeTabPerfil:hiddenIndexPerfil").value=itemId;
    	  var lControl = document.getElementById("frmPrincipal:idIncludeTabPerfil:idBtnIrMantenimiento")
    	  if(lControl){
       	    lControl.click();
          }
      }
      
      function jsSeleccionPermiso(itemIdPersona, itemIdEmpresa){
      	document.getElementById("frmPermModalPanel:hiddenIdPersonaPerm").value=itemIdPersona;
      	document.getElementById("frmPermModalPanel:hiddenIdEmpresaPerm").value=itemIdEmpresa;
      }
      
	  function getJSSelectionEmpresa(){
	  	var idEmpresa = document.frmPrincipal["frmPrincipal:idCboEmpresa"].selectedIndex;
	  	var vcEmpresa = document.frmPrincipal["frmPrincipal:idCboEmpresa"][idEmpresa].text;
	  	
	  	document.frmPrincipal["frmPrincipal:hiddenStrEmpresa"].value=vcEmpresa;
	  }
	  
	  function getJSSelectionPerfil(){
	  	var idPerfil = document.frmPrincipal["frmPrincipal:cboPerfilUsuario"].selectedIndex;
	  	var vcPerfil = document.frmPrincipal["frmPrincipal:cboPerfilUsuario"][idPerfil].text;
	  	
	  	document.frmPrincipal["frmPrincipal:hiddenStrPerfil"].value=vcPerfil;
	  }
	  
	  function getJSSelectionPerfilUsuario(){
	  	var idPerfil = document.frmPrincipal["frmPrincipal:idCboPerfilUsuario"].selectedIndex;
	  	var vcPerfil = document.frmPrincipal["frmPrincipal:idCboPerfilUsuario"][idPerfil].value;
	  	
	  	document.frmPrincipal["frmPrincipal:hiddenIdPerfilPerm"].value=vcPerfil;
	  }
	  
	  function getJSSelectionSucursal(){
	  	var idSucursal = document.frmPrincipal["frmPrincipal:idCboSucursal"].selectedIndex;
	  	var vcSucursal = document.frmPrincipal["frmPrincipal:idCboSucursal"][idSucursal].text;
	  	
	  	document.frmPrincipal["frmPrincipal:hiddenStrSucursal"].value=vcSucursal;
	  }
	  
	  function getJSSelectionSubSucursal(){
	  	var idSubSucursal = document.frmPrincipal["frmPrincipal:idCboSubSucursal"].selectedIndex;
	  	var vcSubSucursal = document.frmPrincipal["frmPrincipal:idCboSubSucursal"][idSubSucursal].text;
	  	
	  	document.frmPrincipal["frmPrincipal:hiddenStrSubSucursal"].value=vcSubSucursal;
	  }
	  
	  function validaForm(){
		return true;
	  }
</script>
<rich:modalPanel id="panelUpdateDeleteUsuario" width="380" height="130" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Actualizar/Eliminar Usuario" ></h:outputText></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="linkMatenimientoUsuario"/>
         <rich:componentControl for="panelUpdateDeleteUsuario" attachTo="linkMatenimientoUsuario" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="frmUsrModalPanel">
  	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;">                 
       <h:panelGrid columns="2"  border="0" cellspacing="4" >
           <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Usuario?"  style="padding-right: 35px;"></h:outputText>
       </h:panelGrid>
       <rich:spacer height="16px"/>
       <h:panelGrid columns="2" border="0"  style="width: 200px;">
           <h:commandButton value="Actualizar" actionListener="#{usuarioPerfilController.irModificarUsuario}" styleClass="btnEstilos"></h:commandButton>
           <h:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarUsuario}" styleClass="btnEstilos"></h:commandButton>
       </h:panelGrid>
   </rich:panel>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="panelConsultarUsuario" width="380" height="130" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Consultar Usuario" ></h:outputText></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="linkConsultarUsuario"/>
         <rich:componentControl for="panelConsultarUsuario" attachTo="linkConsultarUsuario" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form>
  	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;">                 
       <h:panelGrid columns="2"  border="0" cellspacing="4" >
           <h:outputText value="¿Desea Consultar el Usuario?"  style="padding-right: 35px;"></h:outputText>
       </h:panelGrid>
       <rich:spacer height="16px"/>
       <h:panelGrid columns="2" border="0"  style="width: 200px;">
           <h:commandButton value="Consultar" actionListener="#{usuarioPerfilController.irConsultarUsuario}" styleClass="btnEstilos"/>
       </h:panelGrid>
   </rich:panel>
   </h:form>
</rich:modalPanel>	

<rich:modalPanel id="pUpdateDeletePerfil" minWidth="300" minHeight="100" autosized="true" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGroup><h:outputText value="Modificar/Eliminar Perfil"/></h:panelGroup>
    </f:facet>
    <f:facet name="controls">
           <h:graphicImage value="/images/icons/remove_20.png" onclick="Richfaces.hideModalPanel('pUpdateDeletePerfil')" styleClass="hidelink"/>
    </f:facet>
    
    <h:form>
    	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;width:450px;">                 
            <h:panelGrid columns="2"  border="0" cellspacing="4" >
                <h:outputText id="lblCodigo" value="¿Desea Ud. Modificar o Eliminar un Perfil?"  style="padding-right:35px;"></h:outputText>
            </h:panelGrid>
            <rich:separator height="5px"/>
            <h:panelGrid columns="2" border="0"  style="width: 200px;">
                <h:commandButton value="Modificar" actionListener="#{usuarioPerfilController.irModificarPerfil}" styleClass="btnEstilos"/>
                <h:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarPerfil}" styleClass="btnEstilos"/>
            </h:panelGrid>
         </rich:panel>
         <rich:spacer height="4px"/><rich:spacer height="4px"/>
    </h:form>
</rich:modalPanel>
    
<rich:modalPanel id="pConsultarPerfil" minWidth="300" minHeight="100" autosized="true" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGroup><h:outputText value="Consultar Perfil"/></h:panelGroup>
    </f:facet>
    <f:facet name="controls">
           <h:graphicImage value="/images/icons/remove_20.png" onclick="Richfaces.hideModalPanel('pConsultarPerfil')" styleClass="hidelink"/>
    </f:facet>
    
    <h:form>
    	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;width:450px;">                 
            <h:panelGrid columns="2"  border="0" cellspacing="4" >
                <h:outputText id="lblCodigo" value="¿Desea Ud. Consultar un Perfil?"  style="padding-right:35px;"/>
            </h:panelGrid>
            <rich:separator height="5px"/>
            <h:panelGrid columns="2" border="0"  style="width: 200px;">
                <h:commandButton value="Actualizar" actionListener="#{usuarioPerfilController.irConsultarPerfil}" styleClass="btnEstilos"/>
            </h:panelGrid>
         </rich:panel>
         <rich:spacer height="4px"/><rich:spacer height="4px"/>
    </h:form>
</rich:modalPanel>

    <rich:modalPanel id="panelUpdateDeletePermiso" minWidth="300" minHeight="100"
	 autosized="true" resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Actualizar/Eliminar Perfil" ></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkPerm"/>
               <rich:componentControl for="panelUpdateDeletePermiso" attachTo="hidelinkPerm" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        
        <h:form id="frmPermModalPanel">
        	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;width:450px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar un Perfil?"  style="padding-right:35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="4px"/><rich:spacer height="4px"/>
                <rich:separator height="5px"></rich:separator>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{usuarioPerfilController.modificarPermiso}" styleClass="btnEstilos"  ></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarPermiso}" styleClass="btnEstilos" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Permiso?')) return false;"></h:commandButton>
                </h:panelGrid>
                <h:inputHidden id="hiddenIdPersonaPerm"></h:inputHidden>
                <h:inputHidden id="hiddenIdEmpresaPerm"></h:inputHidden>                
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
        </h:form>
    </rich:modalPanel>
    
<rich:modalPanel id="pAgregarDomicilio" width="650" height="450" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Domicilio"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideAgregarDomicilio"/>
          <rich:componentControl for="pAgregarDomicilio" attachTo="hideAgregarDomicilio" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmDomicilioAgregar">
  <a4j:include id="incDomicilioAgregar" viewId="/pages/permiso/usuario/popup/domicilio_edicion.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="pConsultarDomicilio" width="650" height="450" resizeable="false" style="background-color:#DEEBF5;width:600px;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Domicilio"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideConsultarDomicilio"/>
          <rich:componentControl for="pConsultarDomicilio" attachTo="hideConsultarDomicilio" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmDomicilioConsultar">
   <a4j:include viewId="/pages/permiso/usuario/popup/domicilio_vista.jsp"/>
   </h:form>
</rich:modalPanel>
    
<rich:modalPanel id="pAgregarComunicacion" width="780" height="310" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Comunicación"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideAgregarComunicacion"/>
          <rich:componentControl for="pAgregarComunicacion" attachTo="hideAgregarComunicacion" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmComunicacionAgregar">
   <a4j:include id="incComunicacionAgregar" viewId="/pages/permiso/usuario/popup/comunicacion_edicion.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="pConsultarComunicacion" width="780" height="310" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Comunicación"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideConsultarComunicacion"/>
          <rich:componentControl for="pConsultarComunicacion" attachTo="hideConsultarComunicacion" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmComunicacionConsultar">
   <a4j:include viewId="/pages/permiso/usuario/popup/comunicacion_vista.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="pPermisoEdicion" width="580" height="250" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Permiso"/></rich:column></h:panelGrid>
	</f:facet>
	<f:facet name="controls">
	   <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink"  onclick="Richfaces.hideModalPanel('pPermisoEdicion')"/>
	</f:facet>
    <h:form id="idFormPopupPermisoEdicion">
   		<a4j:include id="idPopupPermisoEdicion" viewId="/pages/permiso/perfil/popup/permiso_edicion.jsp"/>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pPermisoConsulta" width="580" height="250" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column style="border: none;"><h:outputText value="Permiso"/></rich:column></h:panelGrid>
	</f:facet>
	<f:facet name="controls">
	   <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink"  onclick="Richfaces.hideModalPanel('pPermisoConsulta')"/>
	</f:facet>
    <h:form id="idFormPopupPermisoConsulta">
   		<a4j:include id="idPopupPermisoEdicion" viewId="/pages/permiso/perfil/popup/permiso_vista.jsp"/>
    </h:form>
</rich:modalPanel>
	
<h:form id="frmPrincipal" >
  	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
    <rich:tab label="Usuarios" disabled="#{usuarioPerfilController.bolUsuario}">
     	<a4j:include id="idTabUsuario" viewId="/pages/permiso/usuario/tabUsuario.jsp"/>
  	</rich:tab>
  	<rich:tab label="Perfiles" actionListener="#{usuarioPerfilController.inicioPerfil}"  disabled="#{usuarioPerfilController.bolPerfil}">
  		<a4j:include id="idIncludeTabPerfil" viewId="/pages/permiso/perfil/tabPerfil.jsp"/>
     </rich:tab>
     <rich:tab label="Permisos" disabled="#{usuarioPerfilController.bolPermiso}">
     	<a4j:include id="idTabPermiso" viewId="/pages/permiso/permiso/tabPermiso.jsp"/>
     </rich:tab>
  	</rich:tabPanel>
</h:form>