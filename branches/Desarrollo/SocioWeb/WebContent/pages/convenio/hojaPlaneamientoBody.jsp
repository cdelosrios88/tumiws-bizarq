<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->
	<!-- Fecha     : 03/04/2012               -->
	
	<script type="text/javascript">
		function jsSeleccionHojaPlan(itemIdConvenio, itemIdItemConvenio){
			document.getElementById("frmHPModalPanel:hiddenIdConvenio").value=itemIdConvenio;
			document.getElementById("frmHPModalPanel:hiddenIdItemConvenio").value=itemIdItemConvenio;
			document.getElementById("frmPrincipal:hiddenIdConvenio").value=itemIdItemConvenio;
			document.getElementById("frmPrincipal:hiddenIdItemConvenio").value=itemIdItemConvenio;
		}
		
		function jsSeleccionConvenio(itemIdConvenio, itemIdItemConvenio){
			document.getElementById("frmConvenioModalPanel:hiddenConvenioId").value=itemIdConvenio;
			document.getElementById("frmConvenioModalPanel:hiddenItemConvenioId").value=itemIdItemConvenio;
		}
		
		function jsSeleccionEstructura(itemIdNivel, itemIdCodigo, itemIdCaso, itemIdItemCaso){
			document.getElementById("frmEstructHojaPlan:hiddenIdNivel").value	= itemIdNivel;
			document.getElementById("frmEstructHojaPlan:hiddenIdCodigo").value	= itemIdCodigo;
			document.getElementById("frmEstructHojaPlan:hiddenIdCaso").value	= itemIdCaso;
			document.getElementById("frmEstructHojaPlan:hiddenIdItemCaso").value= itemIdItemCaso;
		}
		
		function jsSeleccionPopUpHojaPlan(itemIdConvenio, itemIdItemConvenio){
			document.getElementById("frmHojaPlaneamiento:intIdConvenio").value	= itemIdConvenio;
			document.getElementById("frmHojaPlaneamiento:intIdItemConvenio").value	= itemIdItemConvenio;
		}
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteHojaPlan" autosized="true"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Hoja de Planeamiento"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkHP"/>
               <rich:componentControl for="panelUpdateDeleteHojaPlan" attachTo="hidelinkHP" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmHPModalPanel">
        	 <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width:400px;">                 
                <h:panelGrid border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Modificar o Eliminar una Hoja de Planeamiento?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <br/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <a4j:commandButton value="Modificar" actionListener="#{hojaPlaneamientoController.irModificarHojaPlaneamiento}" 
                    	onclick="Richfaces.hideModalPanel('panelUpdateDeleteHojaPlan')" 
                    	reRender="frmHPModalPanel,pHojaPlan, pgControlsHojaPlaneamiento,pgFormHojaPlaneamiento,pgHojaPlaneamiento" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{hojaPlaneamientoController.eliminarHojaPlaneamiento}" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdConvenio"/>
				<h:inputHidden id="hiddenIdItemConvenio"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteConvenio" autosized="true"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar Convenio"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkConvenio"/>
               <rich:componentControl for="panelUpdateDeleteConvenio" attachTo="hidelinkConvenio" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmConvenioModalPanel">
        	 <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width:400px;">                 
                <h:panelGrid border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Modificar el Convenio?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <br/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <a4j:commandButton value="Modificar" actionListener="#{convenioController.irModificarConvenio}" 
                    	onclick="Richfaces.hideModalPanel('panelUpdateDeleteConvenio')"
                    	styleClass="btnEstilos" reRender="frmConvenioModalPanel,pConvenio,pgControlsConvenios"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenConvenioId"/>
				<h:inputHidden id="hiddenItemConvenioId"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="pgHojaPlaneamiento" width="960" height="580" 
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Hoja de Planeamiento"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelHP" value="/images/icons/remove_20.png" styleClass="hidelink" />
	               <rich:componentControl for="pgHojaPlaneamiento" attachTo="hidePanelHP" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
			<h:form id="frmHojaPlaneamientoView">
        		<a4j:include viewId="/pages/convenio/hojaPlaneamiento/tabHojaPlaneamientoView.jsp"/>
        	</h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelEstructura" autosized="true" resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Seleccionar Estructura de Entidad"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTablas"/>
               <rich:componentControl for="panelEstructura" attachTo="hidelinkTablas" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmEstructHojaPlan">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
        		<h:panelGrid columns="3">
        			<rich:column width="90px"><h:outputText value="Estructura"/></rich:column>
        			<rich:column><h:outputText value=":"/></rich:column>
        			<rich:column style="padding-left:10px;">
        				<h:inputText value="#{hojaPlaneamientoController.strNombreEstructura}" size="50"/>
					</rich:column>
        		</h:panelGrid>
        		
        		<h:panelGrid columns="3">
        			<rich:column width="90px"><h:outputText value="Nro RUC:"/></rich:column>
        			<rich:column><h:outputText value=":"/></rich:column>
        			<rich:column style="padding-left:10px;">
        				<h:inputText value="#{hojaPlaneamientoController.strNroRuc}" onkeydown="return validarEnteros(event);" maxlength="11"/>
					</rich:column>
        		</h:panelGrid>
        		
        		<h:panelGrid columns="4">
        			<rich:column width="90px"><h:outputText value="Nivel"/></rich:column>
        			<rich:column><h:outputText value=":"/></rich:column>
        			<rich:column style="padding-left:10px;">
        				<h:selectOneMenu value="#{hojaPlaneamientoController.intCboNivelEntidad}">
	                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
        				</h:selectOneMenu>
					</rich:column>
					<rich:column style="padding-left:125px;">
						<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.listarEstructuras}" reRender="pgTablas,tbTablasMenu"/>
					</rich:column>
        		</h:panelGrid>
        		
                <h:panelGrid id="pgTablas">
                	<rich:extendedDataTable id="tbTablasMenu" value="#{hojaPlaneamientoController.listaEstructuraComp}" rows="10" 
	                    enableContextMenu="false" var="item" rowKeyVar="rowKey" sortMode="single" width="600px" height="290px"
	                    onRowClick="jsSeleccionEstructura('#{item.estructura.id.intNivel}', '#{item.estructura.id.intCodigo}',
	                    			'#{item.estructuraDetalle.id.intCaso}', '#{item.estructuraDetalle.id.intItemCaso}')">
	                	<rich:column width="40px">
	                		<h:selectBooleanCheckbox value="#{item.chkEstructura}"/>
	                	</rich:column>
	                	<rich:column width="25px">
		                	<f:facet name="header">
		                    	<h:outputText />
		                    </f:facet>
		                    <h:outputText value="#{rowKey+1}"/>
		                </rich:column>
	                	<rich:column width="250px">
		                	<f:facet name="header">
		                    	<h:outputText value="Entidad"/>
		                    </f:facet>
		                    <div align="justify"><h:outputText value="#{item.estructura.juridica.strRazonSocial}"/></div>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Nivel"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
	                            itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                            property="#{item.estructura.id.intNivel}"/>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Tipo de Socio"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
	                            itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                            property="#{item.estructuraDetalle.intParaTipoSocioCod}"/>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Modalidad"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
	                            itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                            property="#{item.estructuraDetalle.intParaModalidadCod}"/>
		                </rich:column>
		                
		                <f:facet name="footer">
		                    <rich:datascroller for="tbTablasMenu" maxPages="5"/>   
		                </f:facet>
	                </rich:extendedDataTable>
                </h:panelGrid>
                <h:panelGrid>
	             	<rich:column>
	             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
	             			oncomplete="Richfaces.hideModalPanel('panelEstructura')"
	   						actionListener="#{hojaPlaneamientoController.listarEstructuraDet}"
	   						reRender="panelEstructura,pgListEstructHojaPlan,pLstResumenPob"/>
	             	</rich:column>
             	</h:panelGrid>
                <h:inputHidden id="hiddenIdNivel"/>
	            <h:inputHidden id="hiddenIdCodigo"/>
	            <h:inputHidden id="hiddenIdCaso"/>
	            <h:inputHidden id="hiddenIdItemCaso"/>
             </rich:panel>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelHojaPlaneamiento" autosized="true" resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Seleccionar Hoja de Planeamiento"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkHojaPlan"/>
               <rich:componentControl for="panelHojaPlaneamiento" attachTo="hidelinkHojaPlan" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmHojaPlaneamiento">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
        		<h:panelGrid columns="1">
        			<rich:column style="padding-left:125px;">
						<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{convenioController.listarHojaPlaneamiento}" reRender="pgTablas,tbTablasMenu"/>
					</rich:column>
        		</h:panelGrid>
        		<h:inputHidden id="intIdConvenio"/>
        		<h:inputHidden id="intIdItemConvenio"/>
        		
                <h:panelGrid id="pgTablas">
                	<rich:extendedDataTable id="tbHojaPlan" value="#{convenioController.listaHojaPlaneamientoComp}" rows="#{convenioController.rows}"
                		onRowClick="jsSeleccionPopUpHojaPlan('#{item.adenda.id.intConvenio}', '#{item.adenda.id.intItemConvenio}')" 
	                    enableContextMenu="false" var="item" rowKeyVar="rowKey" sortMode="single" width="560px" height="280px">
	                	<rich:column width="20px">
		                	<f:facet name="header">
		                    	<h:outputText />
		                    </f:facet>
		                    <h:outputText value="#{rowKey+1}"/>
		                </rich:column>
	                	<rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Código"/>
		                    </f:facet>
		                    <h:outputText value="#{item.adenda.id.intConvenio}"/>
		                </rich:column>
		                <rich:column width="140px">
		                	<f:facet name="header">
		                		<h:outputText value="Sucursal"/>
		                	</f:facet>
		                	<tumih:outputText value="#{convenioController.listJuridicaSucursal}" 
                                             itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                             property="#{item.adenda.intSeguSucursalPk}"/>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="F. Inicio"/>
		                    </f:facet>
		                    <h:outputText value="#{item.adenda.strDtInicio}"/>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="F. Fin"/>
		                    </f:facet>
	                    	<h:outputText value="#{item.adenda.strDtCese}"/>
		                </rich:column>
		                <rich:column>
		                	<f:facet name="header">
		                    	<h:outputText value="Plazo"/>
		                    </f:facet>
	                    	<h:outputText value="#{item.intDiferenciaFecha} días" />
		                </rich:column>
		                <f:facet name="footer">
		                    <rich:datascroller for="tbHojaPlan" maxPages="5"/>   
		                </f:facet>
	                </rich:extendedDataTable>
                </h:panelGrid>
                <h:panelGrid>
	             	<rich:column>
	             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
	             			oncomplete="Richfaces.hideModalPanel('panelHojaPlaneamiento')"
	   						actionListener="#{convenioController.getHojaPlaneamiento}"
	   						reRender="panelHojaPlaneamiento,pgListEstructOrganica,pgDatosHojaPlan,pgDatosHojaPlan2,pgRetencPlla,pgListFirmantes,pgDocAdjuntos"/>
	             	</rich:column>
             	</h:panelGrid>
             </rich:panel>
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelFirmantes" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Firmantes"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelFirmante" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelFirmantes" attachTo="hidePanelFirmante" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/popupFirmante.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelAportacion" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Aportaciones"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelAportacion" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelAportacion" attachTo="hidePanelAportacion" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/TiposConcepto/popupAportacion.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelFondoSepelio" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Fondo de Sepelio"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelFondoSepelio" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelFondoSepelio" attachTo="hidePanelFondoSepelio" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/TiposConcepto/popupFondoSepelio.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelFondoRetiro" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Fondo de Retiro"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelFondoRetiro" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelFondoRetiro" attachTo="hidePanelFondoRetiro" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/TiposConcepto/popupFondoRetiro.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelMantCuenta" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Mantenimiento de Cuenta"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelMantCuenta" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelMantCuenta" attachTo="hidePanelMantCuenta" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/TiposConcepto/popupMantCuenta.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelCredito" autosized="true"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Créditos"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage id="hidePanelCredito" value="/images/icons/remove_20.png" styleClass="hidelink"/>
	               <rich:componentControl for="panelCredito" attachTo="hidePanelCredito" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/convenio/convenio/TiposConcepto/popupCredito.jsp"/>
    </rich:modalPanel>
    
    <h:outputText value="#{hojaPlaneamientoController.limpiarHojaPlaneamiento}" />
	<h:outputText value="#{controlProcesoController.limpiarHojaControl}" />
	<h:outputText value="#{convenioController.limpiarConvenio}" />
    
    <h:form id="frmPrincipal">
		<h:inputHidden id="hiddenIdConvenio"/>
		<h:inputHidden id="hiddenIdItemConvenio"/>
      <rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
        <rich:tab label="Hoja de Planeamiento" disabled="#{!hojaPlaneamientoController.poseePermiso}">
         	<a4j:include id="idTabHojaPlaneamiento" viewId="/pages/convenio/hojaPlaneamiento/tabHojaPlaneamientoMain.jsp"/>
    	</rich:tab>
    	
    	<rich:tab label="Hoja de Control" disabled="#{!controlProcesoController.poseePermiso}">
	    	<a4j:include viewId="/pages/convenio/tabHojaControl.jsp"/>
	    </rich:tab>
	    
	    <rich:tab label="Convenios" disabled="#{!convenioController.poseePermiso}">
	    	<a4j:include viewId="/pages/convenio/tabConvenio.jsp"/>
	    </rich:tab>
      </rich:tabPanel>
   </h:form>