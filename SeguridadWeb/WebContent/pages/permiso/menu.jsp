<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fecha     : 19/08/2011               -->
	
<script type="text/javascript">
      
      function jsSeleccionMenu(itemId){
      		var id = itemId.toString();
      		//alert("itemId: "+id);
            document.getElementById("frmMenuPanel:hiddenIdMenu").value=id;
      }
      
      function jsSelectSolicitud(itemId){
      		var idSol = itemId.toString();
      		//alert("itemId: "+idSol);
            document.getElementById("frmSolicitudPanel:hiddenIdSolicitud").value=idSol;
      }

</script>	

	<rich:modalPanel id="mpUpDelSolicitud" width="380" height="120"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Solicitud" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSol"/>
               <rich:componentControl for="mpUpDelSolicitud" attachTo="hidelinkSol" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmSolicitudPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar una Solicitud?"    style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{adminMenuController.modificarSolicitudCambio}" styleClass="btnEstilos"  ></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{adminMenuController.eliminarSolicitudCambio}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdSolicitud"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
<rich:modalPanel id="mpUpdateDeleteMenu" width="380" height="150" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;"> <h:outputText value="Actualizar/Eliminar Menu" /></rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkMenu"/>
           <rich:componentControl for="mpUpdateDeleteMenu" attachTo="hidelinkMenu" operation="hide" event="onclick"/>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmMenuPanel">
    	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 380px; " >                 
            <h:panelGrid columns="2"  border="0" cellspacing="4" >
                <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar un Menu?"    style="padding-right: 35px;"></h:outputText>
            </h:panelGrid>
            <rich:spacer height="16px"/>
            <h:panelGrid columns="2" border="0"  style="width: 200px;">
                <h:commandButton value="Actualizar" actionListener="#{adminMenuController.irModificarMenu}" styleClass="btnEstilos"  ></h:commandButton>
                <%--
                <h:commandButton value="Eliminar" actionListener="#{adminMenuController.eliminarMenu}" styleClass="btnEstilos"></h:commandButton>
                --%>
            </h:panelGrid>
            <h:panelGrid>
            	<h:inputHidden id="hiddenIdMenu" value="#{adminMenuController.indiceMenuBusqueda}"/>
			</h:panelGrid>
        </rich:panel>
        <rich:spacer height="4px"/><rich:spacer height="8px"/>
    </h:form>    
</rich:modalPanel>
    
    <rich:modalPanel id="mpTablas" width="380" height="410"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Seleccionar Tablas" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTablas"/>
               <rich:componentControl for="mpTablas" attachTo="hidelinkTablas" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form>
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar un Menu?"    style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                
                <h:panelGrid id="pgTablas">
                	<rich:dataTable id="tbTablasMenu" value="#{adminMenuController.beanListDataObjects}" rows="10" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single" width="240"> 
	                	<rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Nombre Tabla" ></h:outputText>
		                    </f:facet>
		                    <h:outputText value="#{item.strNombreObjeto}"></h:outputText>
		                </rich:column>
		                <rich:column>
		                    <h:selectBooleanCheckbox value="#{item.blnSelectedObjecto}"></h:selectBooleanCheckbox>
		                </rich:column>
		                <f:facet name="footer">   
		                    <rich:datascroller for="tbTablasMenu" maxPages="5"/>   
		                </f:facet> 
	                </rich:dataTable>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 200px;">
                    <a4j:commandButton value="Seleccionar" styleClass="btnEstilos" actionListener="#{adminMenuController.adjuntarDataObjects}" 
                     			 	   reRender="mpTablas,txtAplicacionesSoli,txtTablasSoli,txtVistasSoli,txtTriggersSoli,txtAplicacionesMenu,txtTablasMenu,txtVistasMenu,txtTriggersMenu"></a4j:commandButton>
                    <h:commandButton value="Todos" styleClass="btnEstilos"></h:commandButton>
                    <h:commandButton value="Limpiar" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdTablas"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>

<rich:modalPanel id="pAgregarSubMenu" width="700" height="310" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column><h:outputText value="Menu"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideAgregarSubMenu"/>
          <rich:componentControl for="pAgregarSubMenu" attachTo="hideAgregarSubMenu" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmAgregarSubMenu">
   <a4j:include id="incAgregarSubMenu" viewId="/pages/permiso/menu/popup/dato_edicion.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="pConsultarSubMenu" width="700" height="310" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
       <h:panelGrid><rich:column><h:outputText value="Menu"/></rich:column></h:panelGrid>
   </f:facet>
   <f:facet name="controls">
       <h:panelGroup>
          <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hideConsultarSubMenu"/>
          <rich:componentControl for="pConsultarSubMenu" attachTo="hideConsultarSubMenu" operation="hide" event="onclick"/>
      </h:panelGroup>
   </f:facet>
   <h:form id="frmConsultarSubMenu">
   <a4j:include id="incConsultarSubMenu" viewId="/pages/permiso/menu/popup/dato_vista.jsp"/>
   </h:form>
</rich:modalPanel>
	
 <rich:modalPanel id="panel" minWidth="700" minHeight="400"  autosized="false" resizeable="false"  style="background-color:#DEEBF5;" >
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Busqueda" ></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelink"/>
                <rich:componentControl for="panel" attachTo="hidelink" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        
        <h:form id="frmSucursal11">
            <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >
                <f:facet name="header">
                    <h:outputText  value="Filtros" ></h:outputText>
                </f:facet>
                                   
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="Cógigo:"    style="padding-right: 35px;"  > </h:outputText>
                    <h:inputText  id="txtCodigo" size="60"    ></h:inputText>
                    <h:outputText id="lblRuc"    value="RUC :"   style="padding-right: 49px;"> </h:outputText>
                    <h:inputText  id="txtRuc" size="60"></h:inputText>
                    <h:outputText id="lblNombre" value="Razón Social :"   style="padding-left:6px;" > </h:outputText>
                    <h:inputText  id="txtRazonSocial" size="60"></h:inputText>
                </h:panelGrid>
                <rich:spacer height="4px"/><rich:spacer height="4px"/>
               <rich:spacer height="4px"/><rich:spacer height="4px"/>
               <rich:separator height="5px"></rich:separator>
               <rich:spacer height="4px"/><rich:spacer height="4px"/>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton id="btnBuscar" value="Buscar"  styleClass="btnEstilos"  ></h:commandButton>
                    <h:commandButton id="btnLimpiar" value="Limpiar" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
             </rich:panel > 
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>             
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >
                <f:facet name="header">
                   <h:outputText  value="Listado"  ></h:outputText>
                </f:facet>
                        <h:panelGrid  columns="1">
                            <rich:dataTable id="tblArea1" var="item" rowKeyVar="rowkey" rows="10">
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Código" ></h:outputText>
                                    </f:facet>
                                    <h:outputText  value="xfghdfhghdgf"></h:outputText>
                                </rich:column>

                                 <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="RUC" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="zxgddhbd"></h:outputText>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Razón Social" ></h:outputText>
                                    </f:facet>
                                    <h:outputText  value="zdsgsdgfdg"></h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller for="tblArea1" maxPages="15"></rich:datascroller>
                            
                    </h:panelGrid>
                </rich:panel >
                 
        </h:form>

        
    </rich:modalPanel>

	
<h:form id="frmPrincipal">
   <rich:tabPanel id="tbMonitor" switchType="ajax" activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
      <rich:tab  id="tSolCambio" label="Solicitud de Cambio"  disabled="#{adminMenuController.bolSolicitudCambio}">
      		<a4j:include id="incTabSolicitud" viewId="/pages/permiso/solicitud/tabSolicitud.jsp"/>
      </rich:tab>
      
      <rich:tab label="Administración de Menú" actionListener="#{adminMenuController.inicioMenu}"  disabled="#{adminMenuController.bolAdministracionMenu}">
            <a4j:include id="incTabMenu" viewId="/pages/permiso/menu/tabMenu.jsp"/>        
       </rich:tab>
   
   </rich:tabPanel>
</h:form>
	