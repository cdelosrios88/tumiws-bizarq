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
<style type="text/css">
      .active-row {background-color: #DAE8FB !important;
	               cursor: pointer;
	              }
      .tabla {border: 1px;
	          }
   /* Definicion de los estilos para la tabla*/
      .encabezado {text-align: center;
   				   font: 11px Arial, sans-serif;
   				   font-weight: bold;
                   color: Snow;
                   background:  #87CEFA;
                   }
   /* Definicion de estilos para la columnas */
      .primero {text-align: center;
   				font: 11px Arial, sans-serif;
   				background: #A2CD5A;
				}
      .ultimo {font: 11px Arial, sans-serif;
   			   text-align: center;
   			   background: #BCEE68;
			  }

</style>

<script type="text/javascript">	  
	  
      function jsSeleccionEmpresa(itemId){
               document.getElementById("frmEmprModalPanel:hiddenId").value=itemId;
      }
      
      function jsSeleccionZonal(itemId){
    	       
               document.getElementById("frmZonalPanel:hiddenIdZonal").value=itemId;
      }
      
      function jsSeleccionSucursal(itemId){
               document.getElementById("frmSucModalPanel:hiddenIdSucursal").value=itemId;
      }
      
      function jsSeleccionArea(itemIdEmp,itemIdSuc,itemIdArea,estado){
    	        
    	  
                document.getElementById("frmAreaModalPanel:hiddenIdEmpArea").value=itemIdEmp;
                document.getElementById("frmAreaModalPanel:hiddenIdSucArea").value=itemIdSuc;
                document.getElementById("frmAreaModalPanel:hiddenIdArea").value=itemIdArea;
                
                if (estado == 3){	
                    document.getElementById("frmAreaModalPanel:buttonElimArea").style.display = 'none';
                }
                else{
                    document.getElementById("frmAreaModalPanel:buttonElimArea").style.display = 'block';
                }
                
                
      }

	  function jsClickRadioIntentos(){
               var radioIntentos = document.frmPrincipal["frmPrincipal:frmEmpresa:radioIntentosIngreso"][1].checked;
               if(radioIntentos==true){
               		document.frmPrincipal["frmPrincipal:txtIntentos"].value='';
               		document.frmPrincipal["frmPrincipal:txtIntentos"].disabled=true;
               }else{
               		document.frmPrincipal["frmPrincipal:txtIntentos"].disabled=false;
               }
	  }
      function jsClickRadioVigencia(){
               var radioVigencia = document.frmPrincipal["frmPrincipal:frmEmpresa:radioVigenciaClaves"][0].checked;
               if(radioVigencia==true){
               		document.frmPrincipal["frmPrincipal:txtVigenciaDias"].value='';
               		document.frmPrincipal["frmPrincipal:txtVigenciaDias"].disabled=true;
               		document.frmPrincipal["frmPrincipal:txtCaducidad"].value='';
               		document.frmPrincipal["frmPrincipal:txtCaducidad"].disabled=true;
               }else{
               		document.frmPrincipal["frmPrincipal:txtVigenciaDias"].disabled=false;
               		document.frmPrincipal["frmPrincipal:txtCaducidad"].disabled=false;
               }
      }
	
</script>
	<rich:modalPanel id="panelUpdateDelete" width="380" height="130"
	 resizeable="false" style="background-color:#DEEBF5" autosized="true">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Empresa" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="panelUpdateDelete" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmEmprModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar una empresa?" style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{empresaController.modificarEmpresa}" styleClass="btnEstilos"  ></h:commandButton>
                    
                    <h:commandButton value="Eliminar" 
                      actionListener="#{empresaController.eliminarEmpresa}" styleClass="btnEstilos" >
                     
                    </h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenId"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteZonal" width="380" height="130"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Zonal" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkzonal"/>
               <rich:componentControl for="panelUpdateDeleteZonal" attachTo="hidelinkzonal" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmZonalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar esta zonal?" style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{empresaController.irModificarZonal}" styleClass="btnEstilos"></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{empresaController.eliminarZonal}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdZonal" value="#{empresaController.strIdZonal}"/>
                </h:panelGrid>
             </rich:panel>
        </h:form>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteSuc" minWidth="300" minHeight="100"
	 autosized="true" resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Actualizar/Eliminar Sucursal" ></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelink2"/>
               <rich:componentControl for="panelUpdateDeleteSuc" attachTo="hidelink2" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        
        <h:form id="frmSucModalPanel">  
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar una sucursal?"  style="padding-right:35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="4px"/><rich:spacer height="4px"/>
                <rich:separator height="5px"></rich:separator>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{empresaController.irModificarSucursal}" styleClass="btnEstilos"  ></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{empresaController.eliminarSucursal}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
                	<h:inputHidden id="hiddenIdSucursal"></h:inputHidden>
                </h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteArea" width="380" height="140"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Área" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelink4"/>
               <rich:componentControl for="panelUpdateDeleteArea" attachTo="hidelink4" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        
        <h:form id="frmAreaModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Área?"  style="padding-right:35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{empresaController.irModificarArea}"  styleClass="btnEstilos" ></h:commandButton>
                   
                    <h:commandButton id="buttonElimArea"   value="Eliminar" actionListener="#{empresaController.eliminarArea}" styleClass="btnEstilos" ></h:commandButton>
                   
                </h:panelGrid>
                <h:panelGrid>
                	<h:inputHidden id="hiddenIdArea"></h:inputHidden>
                	<h:inputHidden id="hiddenIdEmpArea"></h:inputHidden>
                	<h:inputHidden id="hiddenIdSucArea"></h:inputHidden>
                </h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
        
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarDomicilio" width="710" height="535"
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
	               <h:graphicImage id="hidePanelDomicilio" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pAgregarDomicilio" attachTo="hidePanelDomicilio" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/domicilioBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pViewDomicilio" autosized="true"
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
	               <h:graphicImage id="hidePanelViewDomicilio" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pViewDomicilio" attachTo="hidePanelViewDomicilio" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/domicilioView.jsp"/>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <h:outputText value="#{empresaController.inicializarPagina}"/>		
      <rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
     	 <rich:tab label="Empresa" disabled="#{empresaController.bolEmpresa}">
            <a4j:include viewId="/pages/Monitor/empresa/tabEmpresa.jsp"/>
     	 </rich:tab>
	     <rich:tab label="Sucursal" disabled="#{empresaController.bolSucursal}">
	     	<a4j:include viewId="/pages/Monitor/empresa/tabSucursal.jsp"/>
	     </rich:tab>
	     <rich:tab label="Área" disabled="#{empresaController.bolArea}">
	     	<a4j:include viewId="/pages/Monitor/empresa/tabArea.jsp"/>
	     </rich:tab>
	     <rich:tab label="Zonal" disabled="#{empresaController.bolZonal}" actionListener="#{empresaController.inicioZonal}">
	     	<a4j:include viewId="/pages/Monitor/empresa/tabZonal.jsp"/>
	     </rich:tab>
     </rich:tabPanel>           
   </h:form>