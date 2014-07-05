<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->
	<!-- Fecha     : 27/12/2011               -->

	<script language="JavaScript" src="/tumi/js/main.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function jsSeleccionHojaPlan(itemIdConvenio, itemIdAmpliacion){
			document.getElementById("frmHPModalPanel:hiddenIdConvenio").value=itemIdConvenio;
			document.getElementById("frmHPModalPanel:hiddenIdAmpliacion").value=itemIdAmpliacion;
			document.getElementById("frmPrincipal:hiddenConvenioId").value=itemIdConvenio;
			document.getElementById("frmPrincipal:hiddenAmpliacionId").value=itemIdAmpliacion;
		}
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteHojaPlan" width="420" height="120"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Hoja de Planeamiento"></h:outputText>
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
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar una Hoja de Planeamiento?" styleClass="tamanioLetra" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{hojaPlaneamientoController.modificarHojaPlaneamiento}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarUsuario}" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdConvenio"/>
				<h:inputHidden id="hiddenIdAmpliacion"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
	<rich:modalPanel id="panelEstructura" width="500" height="400"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Seleccionar Estructura de Entidad" styleClass="tamanioLetra"></h:outputText>
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
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px;">
        		<h:panelGrid columns="3">
        			<rich:column width="90px"><h:outputText value="Entidad"/></rich:column>
        			<rich:column><h:outputText value=":"/></rich:column>
        			<rich:column style="padding-left:10px;">
        				<h:inputText value="#{hojaPlaneamientoController.sNombEntidad}" size="60"/>
					</rich:column>
        		</h:panelGrid>
        		<rich:spacer height="8px"/>
        		
        		<h:panelGrid columns="4">
        			<rich:column width="90px"><h:outputText value="Nivel"/></rich:column>
        			<rich:column><h:outputText value=":"/></rich:column>
        			<rich:column style="padding-left:10px;">
        				<h:selectOneMenu value="#{hojaPlaneamientoController.cboNivelEntidad}">
        					<f:selectItems value="#{parametersController.cboNivelEntidad}" />
        				</h:selectOneMenu>
					</rich:column>
					<rich:column style="padding-left:125px;">
						<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.listarEstructuras}" reRender="pgTablas,tbTablasMenu"/>
					</rich:column>
        		</h:panelGrid>
        		<rich:spacer height="8px"/>
        		
                <h:panelGrid id="pgTablas">
                	<rich:extendedDataTable id="tbTablasMenu" value="#{hojaPlaneamientoController.beanListEstructHojaPlan}" rows="10"  
	                    enableContextMenu="false" var="item" rowKeyVar="rowKey" sortMode="single" width="420px" height="280px">
	                	<rich:column width="20px">
		                	<f:facet name="header">
		                    	<h:outputText />
		                    </f:facet>
		                    <h:outputText value="#{rowKey+1}"/>
		                </rich:column>
	                	
	                	<rich:column width="250px">
		                	<f:facet name="header">
		                    	<h:outputText value="Entidad"></h:outputText>
		                    </f:facet>
		                    <div align="justify"><h:outputText value="#{item.sRazonSocial}"/></div>
		                </rich:column>
		                
		                <rich:column width="150px">
		                	<f:facet name="header">
		                    	<h:outputText value="Caso"></h:outputText>
		                    </f:facet>
		                    <div align="justify"><h:outputText value="#{item.sCaso}"/></div>
		                </rich:column>
		                
		                <f:facet name="footer">
		                    <rich:datascroller for="tbTablasMenu" maxPages="5"/>   
		                </f:facet>
		                <a4j:support event="onRowDblClick" actionListener="#{hojaPlaneamientoController.listarEstructuraDet}" reRender="panelEstructura,pgEstructHojaPlan,pLstResumenPob">
		                	<f:param id="nIdCodigo"  name="nIdCodigo" 	value="#{item.nIdCodigo}"/>
		                	<f:param id="nIdEmpresa" name="nIdEmpresa" 	value="#{item.nIdEmpresa}"/>
		                	<f:param id="nIdNivel" 	 name="nIdNivel" 	value="#{item.nIdNivel}"/>
		                	<f:param id="nIdCaso" 	 name="nIdCaso" 	value="#{item.nIdCaso}"/>
		                </a4j:support>
	                </rich:extendedDataTable>
                </h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
	<h:form id="frmPrincipal">
      <rich:tabPanel>
        <rich:tab label="Hoja de Planeamiento">
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:945px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<h:panelGrid columns="10" style="width: 900px" border="0">
                        <rich:column width="130" style="padding-left:5px;"><h:outputText id="lblbusqTipoConv" value="Tipo de Convenio" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.nTipoConvenio}">
							  <f:selectItem itemValue="" itemLabel="Todos.."/>
							  <f:selectItem itemValue="0" itemLabel="Nuevo Convenio"/>
							  <f:selectItem itemValue="1" itemLabel="Adenda"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column width="120" style="padding-left:10px;"><h:outputText value="Nivel de Entidad" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.nNivelEntidad}">
								<f:selectItems value="#{parametersController.cboNivelEntidad}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Estados" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu id="cboEstadoHojaPlan" value="#{hojaPlaneamientoController.nEstadoConv}">
								<f:selectItems value="#{parametersController.cboEstadoDocumento}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Sucursal" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.nSucursalConv}">
                        		<f:selectItems value="#{controllerFiller.cboSucursalesZonal}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Modalidad" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{hojaPlaneamientoController.nModalidad}">
								<f:selectItems value="#{parametersController.cboModalidadEstructura}"/>
							</h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="10px"/> 
                    
                    <h:panelGrid columns="6" border="0">
                        <rich:column width="120"><h:outputText value="Nombre de Entidad" /></rich:column>
                        <rich:column>
                        	<h:inputText size="60" value="#{hojaPlaneamientoController.sNombreEntidad}"/>
                        </rich:column>
                        <rich:column width="110"><h:outputText value="Tipo de Socio" style="padding-left:30px;"/></rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{hojaPlaneamientoController.nTipoSocio}">
								<f:selectItems value="#{parametersController.cboTipoSocio}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkDonacion}" style="padding-left:30px;"/></rich:column>
                        <rich:column><h:outputText value="Donación" /></rich:column>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid id="pRangoFec" columns="11" style="width: 850px" border="0">
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkRangoFec}">
                        		<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pRangoFec"/>
                        	</h:selectBooleanCheckbox>
                        </rich:column>
                        <rich:column><h:outputText value="Rango de Fecha" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{hojaPlaneamientoController.nRanFecha}" disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}">
								<f:selectItems value="#{parametersController.cboRangoFecha}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="border:0px;width:100px;">
		                   <rich:calendar id="idFecIniPerfUsu" popup="true"
		                   	value="#{hojaPlaneamientoController.dFecIni}"
		                   	disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		                </rich:column>
		                <rich:column style="border:0px;width:100px;">
		                   <rich:calendar id="idFecFinPerfUsu" popup="true"
		                   	value="#{hojaPlaneamientoController.dFecFin}"
		                   	disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		                </rich:column>
		                <rich:column><h:selectBooleanCheckbox /></rich:column>
                        <rich:column><h:outputText value="Indeterminado" /></rich:column>
                        <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkDocAdjuntos}"/></rich:column>
                        <rich:column><h:outputText value="Documentos Adjuntos" /></rich:column>
                        <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkCartaAutorizacion}"/></rich:column>
                        <rich:column><h:outputText value="Carta de Autorización"/></rich:column>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid>
                    	<h:outputText value="#{hojaPlaneamientoController.msgTxtRangoFec}" styleClass="msgError"/>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid>
                    	<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.listarConvenio}" reRender="pgConvenio"/>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid id="pgConvenio" columns="1"  border="0">
                    	<rich:extendedDataTable id="tbAdminMenu" value="#{hojaPlaneamientoController.beanListConvenio}"
	                       	var="item" rowKeyVar="rowKey" width="910px" height="180px" enableContextMenu="false"
	                       	onRowClick="jsSeleccionHojaPlan(#{item.nIdConvenio}, #{item.nIdAmpliacion});" rows="5"
	                       	rendered="#{not empty hojaPlaneamientoController.beanListConvenio}">
                              <rich:column width="15">
                                  <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Código"/>
                                  </f:facet>
                                  <h:outputText value="#{item.nIdCodigo}"/>
                              </rich:column>
                              <rich:column width="135px">
                                  <f:facet name="header">
                                      <h:outputText value="Nombre de Entidad"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sRazonSocial}"/>
                              </rich:column>
                              <rich:column>
                                  <f:facet name="header">
                                      <h:outputText value="Nivel"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sNivel}"/>
                              </rich:column>
                              <rich:column width="135px">
                                  <f:facet name="header">
                                      <h:outputText value="Tipo de Convenio"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sTipoConvenio}"/>
                              </rich:column>
                              <rich:column width="60px">
                                  <f:facet name="header">
                                      <h:outputText value="Estado"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sEstConv}"/>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Sucursal"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sSucursal}"/>
                              </rich:column>
                              <rich:column width="80px">
                                  <f:facet name="header">
                                      <h:outputText value="Modalidad"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sModalidad}"/>
                              </rich:column>
                              <rich:column width="120px">
                                  <f:facet name="header">
                                      <h:outputText value="Tipo de Socio"/>
                                  </f:facet>
                                  <h:outputText value="#{item.sTipoSocio}"/>
                              </rich:column>
                              <rich:column width="130px">
                                  <f:facet name="header">
                                      <h:outputText value="Fecha de Registro"/>
                                  </f:facet>
                                  <h:outputText value="#{item.daFecRegistro}"/>
                              </rich:column>
                              
                              <f:facet name="footer">
			                  	<rich:datascroller for="tbAdminMenu" maxPages="5"/>   
			                  </f:facet>
		                </rich:extendedDataTable>
		            </h:panelGrid>
                    <rich:spacer height="4px"/><rich:spacer/>
                    <rich:spacer height="4px"/><rich:spacer/>
                    <h:panelGrid columns="2">
						<h:outputLink value="#" id="linkHojaPlan">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteHojaPlan" attachTo="linkHojaPlan" operation="show" event="onclick"/>
						</h:outputLink>
						<h:outputText value="Para Eliminar, Modificar o Imprimir la HOJA DE PLANEAMIENTO hacer click en el Registro" style="color:#8ca0bd" />                                     
				    </h:panelGrid>
				 </rich:panel>
				 
				 <rich:spacer height="10px"/>
	   	    	 <h:panelGrid columns="3">
	                 <a4j:commandButton value="Nuevo" actionListener="#{hojaPlaneamientoController.habilitarGrabarHojaPlan}" styleClass="btnEstilos" reRender="pHojaPlan"/>
	                 <a4j:commandButton id="btnGuardarHojaPlan" value="Grabar" actionListener="#{hojaPlaneamientoController.grabarHojaPlaneamiento}" styleClass="btnEstilos" reRender="pHojaPlan,pgConvenio"/>
	                 <a4j:commandButton value="Cancelar" actionListener="#{hojaPlaneamientoController.cancelarGrabarHojaPlaneamiento}" styleClass="btnEstilos" reRender="pHojaPlan"/>
	          	 </h:panelGrid>
		         
	          	 <h:panelGrid id="pHojaPlan">
	          	 <rich:panel rendered="#{hojaPlaneamientoController.bFormHojaPlaneamientoRendered}" style="width: 850px;border:1px solid #17356f;background-color:#DEEBF5;">
	          	 	<h:outputText value="DATOS GENERALES"  style="font-weight:bold;"/>
	          	 	<rich:spacer height="4px"/>
	          	 	<rich:panel style="width: 830px;border:1px solid #17356f;background-color:#DEEBF5;">
		          	 	<h:panelGrid columns="6">
		          	 		<rich:column width="150"><h:outputText value="Tipo de Convenio" /></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column style="padding-left:10px;">
			          	 		<h:selectOneRadio id="rbTipoConvenio" value="#{hojaPlaneamientoController.sTipoConvenio}" disabled="#{hojaPlaneamientoController.formTipoConvEnabled}">
			                   		<f:selectItem itemValue="1" itemLabel="Nuevo Convenio"/>
									<f:selectItem itemValue="2" itemLabel="Adenda del Convenio"/>
									<a4j:support event="onclick" reRender="pgBusqEntidad,tbTablasMenu" actionListener="#{hojaPlaneamientoController.enableDisableControls}"/>
			                    </h:selectOneRadio>
		                    </rich:column>
		                    <rich:column width="100">
		                    	<h:outputText value="#{hojaPlaneamientoController.msgTipoConvenio}" styleClass="msgError"/>
		                    </rich:column>
		                    <rich:column width="190px" style="padding-left:10px;"><h:outputText value="Nro. Hoja de Planeamiento :"  style="padding-left:10px;font-weight:bold;"/></rich:column>
		                    <rich:column><h:outputText style="font-weight:bold;" value="#{hojaPlaneamientoController.beanHojaPlaneam.nIdConvenio}"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="4">
		                    <rich:column width="150"><h:outputText value="Estado de Convenio"/></rich:column>
		                    <rich:column><h:outputText value=":"/></rich:column>
		                    <rich:column style="padding-left:10px;">
		                    	<h:selectOneMenu id="cboEstado" value="#{hojaPlaneamientoController.beanHojaPlaneam.nIdEstado}">
									<f:selectItems value="#{parametersController.cboEstadoDocumento}"/>
								</h:selectOneMenu>
		                    </rich:column>
		                    <rich:column><h:outputText value="#{hojaPlaneamientoController.msgEstadoConvenio}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid id="pgBusqEntidad" columns="4">
		          	 		<rich:column width="150"><h:outputText value="Entidad" /></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column style="padding-left:10px;">
		                    	<a4j:commandLink actionListener="#{hojaPlaneamientoController.listarEstructuras}" oncomplete="Richfaces.showModalPanel('panelEstructura').show()" reRender="pgBusqEntidad,tbTablasMenu" disabled="#{hojaPlaneamientoController.btnBuscarEnabled}" immediate="true" >
			                       	<h:graphicImage value="/images/icons/buscar.jpg" style="border:0px"/>
			                    </a4j:commandLink>
		                    </rich:column>
		                    <rich:column><h:outputText value="#{hojaPlaneamientoController.msgEntidad}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid id="pgEstructHojaPlan">
		          	 		<rich:extendedDataTable id="tbEstructHojaPlan" value="#{hojaPlaneamientoController.beanListEstructDet}" rows="5"
                            	var="item" rowKeyVar="rowKey" sortMode="single" width="800px" height="#{hojaPlaneamientoController.sAlturaEstructDet}"
                            	rendered="#{not empty hojaPlaneamientoController.beanListEstructDet}">
			                	<rich:column width="25px">
				                	<f:facet name="header">
				                    	<h:outputText />
				                    </f:facet>
				                    <h:outputText value="#{rowKey+1}"/>
				                </rich:column>
			                	
			                	<rich:column width="200px">
				                	<f:facet name="header">
				                    	<h:outputText value="Nivel de Entidad" />
				                    </f:facet>
				                    <h:outputText value="#{item.sNivelDesc}"/>
				                </rich:column>
				                
				                <rich:column width="300px">
				                	<f:facet name="header">
				                    	<h:outputText value="Nombre" />
				                    </f:facet>
				                    <h:outputText value="#{item.sNombrePersona}"/>
				                </rich:column>
				                
				                <rich:column width="100px">
				                	<f:facet name="header">
				                    	<h:outputText value="Modalidad"/>
				                    </f:facet>
				                    <h:outputText value="#{item.sModalidadDesc}"/>
				                </rich:column>
				                
				                <rich:column width="200px">
				                	<f:facet name="header">
				                    	<h:outputText value="Tipo de Socio"/>
				                    </f:facet>
				                    <h:outputText value="#{item.sTipoSocioDesc}"/>
				                </rich:column>
			                </rich:extendedDataTable>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="150"><h:outputText value="Sucursal que gestiona" /></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column style="padding-left:10px;">
			          	 		<h:selectOneMenu id="idCboSucursal" value="#{hojaPlaneamientoController.beanHojaPlaneam.nIdSucursal}">
									<f:selectItems value="#{controlsFiller.cboSucursalesZonal}"></f:selectItems>
								</h:selectOneMenu>
		                    </rich:column>
		                    <rich:column><h:outputText value="#{hojaPlaneamientoController.msgSucursal}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid id="pFecDuracion" columns="6">
		          	 		<rich:column width="150"><h:outputText value="Tiempo de Duración" /></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column style="padding-left:10px;"><h:outputText value="Fecha de Inicio" /></rich:column>
		          	 		<rich:column>
			          	 		<rich:calendar popup="true"
			                   	value="#{hojaPlaneamientoController.dFecIniDurac}"
			                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
			                    cellWidth="10px" cellHeight="20px"/>
		                    </rich:column>
		                    <rich:column>
			          	 		<h:selectOneRadio value="#{hojaPlaneamientoController.rbTiempoDurac}">
			                   		<f:selectItem itemValue="1" itemLabel="Fecha de Cese"/>
			                   		<f:selectItem itemValue="2" itemLabel="Indeterminado"/>
			                   		<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pFecDuracion"/>
			                    </h:selectOneRadio>
		                    </rich:column>
		                    <rich:column style="padding-left:10px;">
		                    	<rich:calendar popup="true"
		                    	rendered="#{hojaPlaneamientoController.formFecFinDurac}"
			                   	value="#{hojaPlaneamientoController.dFecFinDurac}"
			                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
			                    cellWidth="10px" cellHeight="20px"/>
		                    </rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="1" border="0">
		          	 		<rich:column style="padding-left:343px;">
			          	 		<h:selectOneRadio id="rbOpcionFiltro" value="#{hojaPlaneamientoController.beanHojaPlaneam.nOpcionFiltroCredito}">
			                   		<f:selectItem itemValue="1" itemLabel="Fecha de última cuota del cronograma"/>
			                   		<f:selectItem itemValue="2" itemLabel="Fecha de solicitud de crédito"/>
			                    </h:selectOneRadio>
		                    </rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="2">
		          	 		<rich:column><h:outputText value="#{hojaPlaneamientoController.msgFecDuracion}" styleClass="msgError"/></rich:column>
		          	 		<rich:column style="padding-left:10px;"><h:outputText value="#{hojaPlaneamientoController.msgOpcionFiltroCred}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="4" border="0">
		          	 		<rich:column width="150"><h:outputText value="Fecha de Suscripción" /></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column style="padding-left:10px;">
			          	 		<rich:calendar popup="true"
			                   	value="#{hojaPlaneamientoController.daFecSuscripcion}"
			                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
			                    cellWidth="10px" cellHeight="20px"/>
		                    </rich:column>
		                    <rich:column><h:outputText value="#{hojaPlaneamientoController.msgFecSuscripcion}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid id="pgRetenPlan" columns="5" border="0">
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkRetencion}">
		          	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pgRetenPlan"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="150px"><h:outputText value="Retención de Planilla"/></rich:column>
		          	 		<rich:column>
			          	 		<h:selectOneRadio value="#{hojaPlaneamientoController.beanHojaPlaneam.nTipoRetencion}" disabled="#{hojaPlaneamientoController.formRetPlan}">
			                   		<f:selectItem itemValue="1" itemLabel="% Planilla Efectuada"/>
			                   		<f:selectItem itemValue="2" itemLabel="S/. por cada socio"/>
			                    </h:selectOneRadio>
		                    </rich:column>
		                    <rich:column style="padding-left:10px;"><h:inputText size="10" value="#{hojaPlaneamientoController.beanHojaPlaneam.flRetencion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" disabled="#{hojaPlaneamientoController.formRetPlan}"/></rich:column>
		                    <rich:column style="padding-left:10px;"><h:outputText value="#{hojaPlaneamientoController.msgRetencPlanilla}" styleClass="msgError"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="4" border="0">
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.beanHojaPlaneam.bAutorizacion}"/>
		          	 		</rich:column>
		          	 		<rich:column><h:outputText value="Carta de Autorización"/></rich:column>
		          	 		<rich:column style="padding-left:20px;">
		          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.beanHojaPlaneam.bDonacion}"/>
		          	 		</rich:column>
		          	 		<rich:column><h:outputText value="Donación"/></rich:column>
		          	 	</h:panelGrid>
		          	 	<rich:spacer height="8px"/>
		          	 	
		          	 	<h:panelGrid columns="2" border="0">
		          	 		<rich:column>
		          	 			<h:outputText value="Documentos adjuntos:" />
		          	 		</rich:column>
		          	 		
		          	 		<rich:column style="padding-left:20px;">
			          	 		<h:panelGrid id="pUpldCartaPresent" columns="3" border="0">
				          	 		<rich:column>
				          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkCartaPresent}">
				          	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldCartaPresent"/>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column width="150"><h:outputText value="Carta de Presentación:"/></rich:column>
				          	 		<rich:column>
					          	 		<rich:fileUpload id="uploadCartaPresent" addControlLabel="Adjuntar Archivo"
								             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
								             disabled="#{hojaPlaneamientoController.upldCartaPresent}"
								             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
								             fileUploadListener="#{fileUploadController.listener}"
											 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
											 immediateUpload="#{fileUploadController.autoUpload}"
											 acceptedTypes="pdf">
											 <f:facet name="label">
											 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
											 </f:facet>
										</rich:fileUpload>
									</rich:column>
				          	 	</h:panelGrid>
				          	 	<h:panelGrid>
				          	 		<rich:column style="padding-left:165px;">
				          	 			<h:inputText value="#{hojaPlaneamientoController.beanHojaPlaneam.sCartaPresent}" 
				          	 				rendered="#{hojaPlaneamientoController.formCartaPresent}" size="90"/>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 	
				          	 	<rich:spacer height="8px;"/>
				          	 	
				          	 	<h:panelGrid id="pUpldConvSugerido" columns="3" border="0">
				          	 		<rich:column>
				          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkConvSugerido}">
				          	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldConvSugerido"/>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column width="150"><h:outputText value="Convenio Sugerido:"/></rich:column>
				          	 		<rich:column>
					          	 		<rich:fileUpload id="uploadConvSug" addControlLabel="Adjuntar Archivo"
								             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
								             disabled="#{hojaPlaneamientoController.upldConvSugerido}"
								             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
								             fileUploadListener="#{fileUploadController.listener}"
											 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
											 immediateUpload="#{fileUploadController.autoUpload}"
											 acceptedTypes="pdf">
											 <f:facet name="label">
											 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
											 </f:facet>
										</rich:fileUpload>
									</rich:column>
				          	 	</h:panelGrid>
				          	 	<h:panelGrid>
				          	 		<rich:column style="padding-left:165px;">
				          	 			<h:inputText value="#{hojaPlaneamientoController.beanHojaPlaneam.sConvSugerido}" 
				          	 				rendered="#{hojaPlaneamientoController.formConvSugerido}" size="90"/>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 	<rich:spacer height="8px;"/>
				          	 	
				          	 	<h:panelGrid id="pUpldAdendaSugerida" columns="3" border="0">
				          	 		<rich:column>
				          	 			<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkAdendaSugerida}">
				          	 				<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pUpldAdendaSugerida"/>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column width="150"><h:outputText value="Adenda Sugerida:"/></rich:column>
				          	 		<rich:column>
					          	 		<rich:fileUpload id="uploadAdenda" addControlLabel="Adjuntar Archivo"
								             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
								             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
								             disabled="#{hojaPlaneamientoController.upldAdendaSugerida}"
								             fileUploadListener="#{fileUploadController.listener}"
											 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
											 immediateUpload="#{fileUploadController.autoUpload}"
											 acceptedTypes="pdf">
											 <f:facet name="label">
											 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
											 </f:facet>
										</rich:fileUpload>
									</rich:column>
				          	 	</h:panelGrid>
				          	 	<h:panelGrid>
				          	 		<rich:column style="padding-left:165px;">
				          	 			<h:inputText value="#{hojaPlaneamientoController.beanHojaPlaneam.sAdendaSugerida}" 
				          	 				rendered="#{hojaPlaneamientoController.formAdendaSugerida}" size="90"/>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 	<rich:spacer height="4px"/>
				          	 	
				          	 	<h:panelGrid columns="1">
				          	 		<rich:column>
				          	 			<h:outputText value="#{hojaPlaneamientoController.msgConvSugerido}" styleClass="msgError"/>
				          	 		</rich:column>
				          	 	</h:panelGrid>
			          	 	</rich:column>
			          	</h:panelGrid>
	          	 	</rich:panel>
	          	 	
	          	 	<rich:spacer height="8px"/>
	          	 	<h:panelGrid>
		          	 	<h:outputText value="ESTUDIO DE MERCADO DE LA ENTIDAD"  style="font-weight: bold"/>
		          	 	<rich:spacer height="8px"/>
		          	 	<h:outputText value="POBLACIONAL"  style="font-weight: bold"/>
		          	</h:panelGrid>
		          	<rich:spacer height="8px"/>
		          	
	          	 	<rich:panel style="width: 830px;border:1px solid #17356f;background-color:#DEEBF5;">
	          	 		<div align="center">
	          	 		<h:panelGrid id="pLstPoblacion" columns="3">
	          	 			<rich:column>
	          	 				<rich:extendedDataTable var="item" rowKeyVar="rowKey" width="300px" height="#{hojaPlaneamientoController.sAlturaMercPoblacion}" enableContextMenu="false"
		          	 				value="#{hojaPlaneamientoController.beanListPoblacion}" rendered="#{not empty hojaPlaneamientoController.beanListPoblacion}">
	                              <rich:column width="20">
	                                  <h:outputText value="#{rowKey+1}"/>
	                              </rich:column>
	                              
	                              <rich:column width="280px" style="height:78px;">
	                                  <f:facet name="header">
	                                      <h:outputText value="Centros de Trabajo"/>
	                                  </f:facet>
	                                  <h:inputTextarea cols="50" rows="5" value="#{item.sCentroTrabajo}"/>
	                              </rich:column>
	                            </rich:extendedDataTable>
	          	 			</rich:column>
	          	 			
	          	 			<rich:column>
	          	 				<rich:extendedDataTable var="item" rowKeyVar="rowKey" width="165px" height="#{hojaPlaneamientoController.sAlturaMercPoblacion}" enableContextMenu="false"
	          	 				value="#{hojaPlaneamientoController.beanListPoblacionDet}" rendered="#{not empty hojaPlaneamientoController.beanListPoblacionDet}">
	                              <rich:column width="80px">
	                               	<f:facet name="header">
	                                   <h:outputText/>
	                               	</f:facet>
	                               	<h:outputText value="#{item.sTipoTrabajador}"/>
	                              </rich:column>
	                              
	                              <rich:column width="25px">
	                                 <f:facet name="header">
	                                     <h:outputText></h:outputText>
	                                 </f:facet>
	                                 <h:outputText value="#{item.sTipoSocio}"/>
	                              </rich:column>
	                              
	                              <rich:column width="60px">
	                                  <f:facet name="header">
	                                      <h:outputText value="Padrón"/>
	                                  </f:facet>
	                                  <h:inputText value="#{item.nPadron}" size="5" onkeypress="return soloNumeros(event);"/>
	                              </rich:column>
	                            </rich:extendedDataTable>
	          	 			</rich:column>
	          	 			
	          	 			<rich:column>
	          	 				<rich:extendedDataTable var="item" rowKeyVar="rowKey" width="280px" height="#{hojaPlaneamientoController.sAlturaMercPoblacion}" enableContextMenu="false"
		          	 				value="#{hojaPlaneamientoController.beanListPoblacion}" rendered="#{not empty hojaPlaneamientoController.beanListPoblacion}">
	                              <rich:column width="280px" style="height:78px;">
	                                  <f:facet name="header">
	                                      <h:outputText value="Distancia a llegar (Tiempo)"/>
	                                  </f:facet>
	                                  <h:inputTextarea cols="50" rows="5" value="#{item.flDistancia}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" style="text-align:center"/>
	                              </rich:column>
	                            </rich:extendedDataTable>
	          	 			</rich:column>
                            <rich:spacer height="4px"/>
	          	 		</h:panelGrid>
	          	 		
	          	 		<h:panelGrid columns="5">
	          	 			<a4j:commandButton value="Agregar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.agregarPoblacion}" reRender="pLstPoblacion"/>
	          	 			<rich:spacer width="10px"/>
	          	 			<a4j:commandButton value="Cancelar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.cancelarPoblacion}" reRender="pLstPoblacion"/>
	          	 		</h:panelGrid>
	          	 		<rich:spacer height="10px"/>
	          	 		
	          	 		<h:panelGrid id="pLstResumenPob">
		          	 		<rich:extendedDataTable var="item" rowKeyVar="rowKey" width="655px" height="93px" enableContextMenu="false"
	      	 				value="#{hojaPlaneamientoController.beanListConvenioEstructDet}" 
	      	 				rendered="#{not empty hojaPlaneamientoController.beanListConvenioEstructDet}">
	                          <rich:column width="180px">
	                           	<f:facet name="header">
	                               <h:outputText value="Resumen Poblacional"/>
	                           	</f:facet>
	                           	<h:outputText value="#{item.sDescripcion}"/>
	                          </rich:column>
	                          
	                          <rich:column width="25px">
	                             <f:facet name="header">
	                                 <h:outputText></h:outputText>
	                             </f:facet>
	                             <h:outputText value="#{item.sTipoNombrado}"/>
	                          </rich:column>
	                          
	                          <rich:column width="110px">
	                              <f:facet name="header">
	                                  <h:outputText value="Padrón"/>
	                              </f:facet>
	                              <h:outputText value="#{item.nPadron}"/>
	                          </rich:column>
	                          
	                          <rich:column width="110px">
	                              <f:facet name="header">
	                                  <h:outputText value="Socios"/>
	                              </f:facet>
	                              <h:outputText value="#{item.nSocios}"/>
	                          </rich:column>
	                          
	                          <rich:column width="120px">
	                              <f:facet name="header">
	                                  <h:outputText value="%1er Descuento"/>
	                              </f:facet>
	                              <h:outputText value="#{item.doPorcDcto}"/>
	                          </rich:column>
	                          
	                          <rich:column width="110px">
	                              <f:facet name="header">
	                                  <h:outputText value="%Morosidad"/>
	                              </f:facet>
	                              <h:outputText value="#{item.doPorcMoros}"/>
	                          </rich:column>
	                        </rich:extendedDataTable>
	          	 		</h:panelGrid>
	          	 		</div>
	          	 	</rich:panel>
	          	 	<rich:spacer height="8px"/>
	          	 	
	          	 	<h:panelGrid>
	          	 		<h:outputText value="COMPETENCIA"  style="font-weight:bold;"/>
	          	 	</h:panelGrid>
	          	 	
	          	 	<rich:spacer height="8px"/>
	          	 	<rich:panel style="width: 830px;border:1px solid #17356f;background-color:#DEEBF5;">
	          	 		<div align="center">
	          	 		<h:panelGrid id="pLstCompetencia">
	          	 			<rich:extendedDataTable var="item" rowKeyVar="rowKey" width="810px" height="#{hojaPlaneamientoController.sAlturaCompetencia}"
	          	 				value="#{hojaPlaneamientoController.beanListCompetencia}" enableContextMenu="false" 
	          	 				rendered="#{not empty hojaPlaneamientoController.beanListCompetencia}">
                              <rich:column width="25px">
					          	<h:selectBooleanCheckbox value="#{item.chkCompetencia}"/>
	                          </rich:column>
                              
                              <rich:column width="20">
                                  <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                              </rich:column>
                              
                              <rich:column width="260px">
                                  <f:facet name="header">
                                      <h:outputText value="Convenio con otras Coop. o Ent. Financ."/>
                                  </f:facet>
                                  <h:inputText value="#{item.sEntidadFinanc}" size="45"/>
                              </rich:column>
                              
                              <rich:column width="80px">
                                  <f:facet name="header">
                                      <h:outputText value="# de Socios"/>
                                  </f:facet>
                                  <h:inputText value="#{item.nSocios}" size="9" onkeypress="return soloNumeros(event);"/>
                              </rich:column>
                              
                              <rich:column width="140px">
                                  <f:facet name="header">
                                      <h:outputText value="Plazos de Préstamos"/>
                                  </f:facet>
                                  <h:inputText value="#{item.flPlazoPrestamo}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);"/>
                              </rich:column>
                              
                              <rich:column width="80">
                                  <f:facet name="header">
                                      <h:outputText value="Interés(%)"/>
                                  </f:facet>
                                  <h:inputText value="#{item.flInteres}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" size="9"/>
                              </rich:column>
                              
                              <rich:column width="115">
                                  <f:facet name="header">
                                      <h:outputText value="Monto de Aporte"/>
                                  </f:facet>
                                  <h:inputText value="#{item.flMontoAporte}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);" size="14"/>
                              </rich:column>
                              
                              <rich:column width="140px">
                                  <f:facet name="header">
                                      <h:outputText value="Serv. que ofrece"/>
                                  </f:facet>
                                  <h:inputText value="#{item.sServOfrec}" size="14"/>
                              </rich:column>
                            </rich:extendedDataTable>
                            <rich:spacer height="4px"/>
	          	 		</h:panelGrid>
	          	 			<a4j:commandButton value="Agregar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.addCompetencia}" reRender="pLstCompetencia"/>
                            <rich:spacer width="10px"/>
                            <a4j:commandButton value="Quitar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.removeCompetencia}" reRender="pLstCompetencia"/>
	          	 		</div>
	          	 	</rich:panel>
	          	 </rich:panel>
	          	 </h:panelGrid>
			</rich:panel>
    	</rich:tab>
    	
    	<!-- Control de Proceso -->
     	<rich:tab label="Control de Proceso"  >
    		<rich:panel style="border:1px solid #17356f ;width: 930px; background-color:#DEEBF5">
        		 <div align="center"><h:outputText value="ANALISIS Y APROBACION DE CONVENIO INSTITUCIONAL" style="font-weight:bold;"/></div>
        		 <rich:spacer height="10px;"/>
    			 <h:panelGrid columns="8" style="width: 800px" border="0">
                    <rich:column width="100"><h:outputText value="Tipo de Convenio" /></rich:column>
                    <rich:column>
                    	<h:selectOneMenu value="#{controlProcesoController.nTipoConvenio}">
						  <f:selectItem itemValue="" itemLabel="Todos.."/>
						  <f:selectItem itemValue="0" itemLabel="Nuevo Convenio"/>
						  <f:selectItem itemValue="1" itemLabel="Adenda"/>
						</h:selectOneMenu>
                    </rich:column>
                    <rich:column><h:outputText value="Convenio" /></rich:column>
                    <rich:column>
                    	<h:selectOneMenu value="#{controlProcesoController.inIdConvenio}">
							<f:selectItems value="#{controllerFiller.cboConvenio}"/>
						</h:selectOneMenu>
                    </rich:column>
                    
                    <rich:column><h:outputText value="Sucursal" /></rich:column>
                    <rich:column>
                    	<h:selectOneMenu value="#{controlProcesoController.nIdSucursal}">
							<f:selectItems value="#{controllerFiller.cboSucursalesZonal}"/>
						</h:selectOneMenu>
                    </rich:column>
                    
                    <rich:column><h:outputText value="Estado" /></rich:column>
                    <rich:column>
                    	<h:selectOneMenu value="#{controlProcesoController.nIdEstado}">
							<f:selectItems value="#{parametersController.cboEstadoDocumento}"/>
						</h:selectOneMenu>
                    </rich:column>
                </h:panelGrid>
	            <rich:spacer height="8px"/>
	            
                <h:panelGrid columns="3" style="width: 800px" border="0">
                    <rich:column width="120"><h:outputText value="Nombre de Entidad" /></rich:column>
                    <rich:column>
                    	<h:inputText value="#{controlProcesoController.sNombEntidad}" size="100"/>
                    </rich:column>
                    <rich:column>
                     <a4j:commandButton value="Buscar" actionListener="#{controlProcesoController.listarControlProceso}" styleClass="btnEstilos" reRender="pgControlProceso"/>
                    </rich:column>
                </h:panelGrid>
                <rich:spacer height="8px"/>
                
                <h:inputHidden id="hiddenConvenioId"/>
                <h:inputHidden id="hiddenAmpliacionId"/>
                
                <div align="center">
                <h:panelGrid id="pgControlProceso" columns="1"  border="0">
                   	<rich:extendedDataTable value="#{controlProcesoController.beanListControlProceso}"
                   		var="item" rowKeyVar="rowKey" width="470px" height="160px" enableContextMenu="false" rows="5"
                       	rendered="#{not empty controlProcesoController.beanListControlProceso}">
                             <rich:column width="25">
                                 <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                             </rich:column>
                             <rich:column width="135px">
                                 <f:facet name="header">
                                     <h:outputText value="Tipo de Convenio"/>
                                 </f:facet>
                                 <h:outputText value="#{item.stTipoConvenio}"/>
                             </rich:column>
                             <rich:column width="80px">
                                 <f:facet name="header">
                                     <h:outputText value="Estado"/>
                                 </f:facet>
                                 <h:outputText value="#{item.stEstConvenio}"/>
                             </rich:column>
                             <rich:column width="70px">
                                 <f:facet name="header">
                                     <h:outputText value="Código"/>
                                 </f:facet>
                                 <h:outputText value="#{item.inIdConvenio}"/>
                             </rich:column>
                             <rich:column width="160px">
                                 <f:facet name="header">
                                     <h:outputText value="Nombre de Entidad"/>
                                 </f:facet>
                                 <h:outputText value="#{item.stNombreEntidad}"/>
                             </rich:column>
                             <f:facet name="footer">
			                 	<rich:datascroller for="tbAdminMenu" maxPages="5"/>   
			                 </f:facet>
			                 <a4j:support event="onRowClick" actionListener="#{controlProcesoController.listarPerfilConvenio}" reRender="pControlProceso,btnPanel" oncomplete="jsSeleccionHojaPlan(#{item.inIdConvenio}, #{item.inIdAmpliacion});">
			                 	<f:param name="idConvenio" value="#{item.inIdConvenio}"/>
			                 	<f:param name="idAmpliacion" value="#{item.inIdAmpliacion}"/>
			                 	<f:param name="idEstadoConv" value="#{item.stEstConvenio}"/>
			                 </a4j:support>
	                </rich:extendedDataTable>
	            </h:panelGrid>
	            </div>
	            <rich:spacer height="8px"/><rich:spacer/>
                <h:panelGrid columns="2">
					<h:outputLink value="#" id="linkControlProceso">
				        <h:graphicImage value="/images/icons/mensaje1.jpg"
							style="border:0px"/>
				        <rich:componentControl for="panelUpdateDeleteHojaPlan" attachTo="linkControlProceso" operation="show" event="onclick"/>
					</h:outputLink>
				<h:outputText value="Para Analizar y Ver PLANEAMIENTO DE CONVENIO o Imprimir la HOJA DE CONTROL DE CONVENIO hacer click en el Registro" style="color:#8ca0bd" />                                     
			    </h:panelGrid>
         	</rich:panel>
         	
         	<rich:spacer height="10px"/>
   	    	<h:panelGrid id="btnPanel" columns="4">
                <a4j:commandButton id="btnValidar" value="Validar" disabled="#{controlProcesoController.enableDisableBtnValidar}"  styleClass="btnEstilos" actionListener="#{controlProcesoController.validarConvenio}" reRender="pControlProceso"/>
                <a4j:commandButton id="btnAprobar" value="Aprobar" disabled="#{controlProcesoController.enableDisableBtnAprobar}" styleClass="btnEstilos" actionListener="#{controlProcesoController.aprobarRechazarConvenio}" reRender="pControlProceso,pgControlProceso"/>
                <a4j:commandButton id="btnRechazar" value="Rechazar" disabled="#{controlProcesoController.enableDisableBtnRechazar}" styleClass="btnEstilos" actionListener="#{controlProcesoController.aprobarRechazarConvenio}" reRender="pControlProceso,pgControlProceso"/>
                <a4j:commandButton id="btnCancelar" value="Cancelar" disabled="#{controlProcesoController.enableDisableBtnCancelar}" styleClass="btnEstilos" actionListener="#{controlProcesoController.cancelarConvenio}" reRender="pControlProceso"/>
          	</h:panelGrid>
          	
          	<h:panelGrid id="pControlProceso">
	         	<rich:panel style="width: 930px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{controlProcesoController.enableDisableFormControl}">
	         		<h:panelGrid>
	         			<h:outputText value="#{controlProcesoController.msgApruebaRechaza}" styleClass="msgError" style="font-weight:bold;"/>
	         		</h:panelGrid>
	         		<h:panelGrid>
	         			<h:outputText value="#{controlProcesoController.msgObservacion}" styleClass="msgError" style="font-weight:bold;"/>
	         		</h:panelGrid>
	         		<h:panelGrid>
	         			<h:outputText value="ANALISIS Y VALIDACION"  style="font-weight:bold;"/>
	         		</h:panelGrid>
	         		<rich:spacer height="4px;"/>
	         		<h:panelGrid>
	         			<h:outputText value="AREA" style="font-weight:bold;padding-left:20px;"/>
	         		</h:panelGrid>
	         		<rich:spacer height="4px;"/>
	         		<h:panelGrid columns="4">
	         			<rich:column width="120px" style="padding-left:20px;">
	         				<h:outputText value="Perfil"/>
	         			</rich:column>
	         			<rich:column>
	         				<h:inputText value="#{controlProcesoController.stPerfil}" size="80" disabled="true"/>
	         			</rich:column>
	         			<rich:column style="padding-left:20px;padding-right:20px;">
	         				<h:outputText value="Estado"/>
	         			</rich:column>
	         			<rich:column width="150px" style="border-style:solid;border-width:1px;border-color:#000099;font-style:italic;">
	         				<div align="center"><h:outputText value="#{controlProcesoController.stEstConvenio}"/></div>
	         			</rich:column>
	         		</h:panelGrid>
	         		<rich:spacer height="4px;"/>
	         		
	         		<h:panelGrid columns="2">
	         			<rich:column width="120px" style="padding-left:20px;">
	         				<h:outputText value="Nombre"/>
	         			</rich:column>
	         			<rich:column>
	         				<h:outputText value="#{controlProcesoController.stNombUsuario}"/>
	         			</rich:column>
	         		</h:panelGrid>
	         		<rich:spacer height="8px;"/>
	         		
	         		<h:panelGrid columns="5">
	         			<rich:column>
	         				<rich:extendedDataTable id="dtEncConv" value="#{controlProcesoController.beanListEncargConvenio}"
		                       	var="item" rowKeyVar="rowKey" width="180px" height="250px" enableContextMenu="false"
		                       	rendered="#{not empty controlProcesoController.beanListEncargConvenio}">
		                             <rich:column width="25">
		                                 <h:selectBooleanCheckbox value="#{item.inEstadoPerf}" disabled="#{controlProcesoController.enableDisableEncConv}"/>
		                             </rich:column>
		                             <rich:column width="185px">
		                                 <f:facet name="header">
		                                     <h:outputText value="Encargado de Convenios"/>
		                                 </f:facet>
		                                 <h:outputText id="txtEncConv" value="#{item.stDescripcion}">
		                                 	<rich:toolTip followMouse="true" direction="top-left" for="txtEncConv" layout="block" horizontalOffset="5" verticalOffset="5" value="#{item.stDescripcion}"/>
		                                 </h:outputText>
		                             </rich:column>
			                </rich:extendedDataTable>
			                <h:outputText value="Observación:" rendered="#{not empty controlProcesoController.beanListEncargConvenio}"/> <rich:spacer height="4px;"/>
			                <h:inputTextarea value="#{controlProcesoController.stObsEncargConv}" rows="5" cols="32" rendered="#{not empty controlProcesoController.beanListEncargConvenio}" disabled="#{controlProcesoController.enableDisableEncConv}"/>
	         			</rich:column>
	         			
	         			<rich:column>
	         				<rich:extendedDataTable id="dtJefCred" value="#{controlProcesoController.beanListJefatCreditos}"
		                       	var="item" rowKeyVar="rowKey" width="180px" height="250px" enableContextMenu="false"
		                       	rendered="#{not empty controlProcesoController.beanListJefatCreditos}">
		                             <rich:column width="25">
		                                 <h:selectBooleanCheckbox value="#{item.inEstadoPerf}" disabled="#{controlProcesoController.enableDisableJefCred}"/>
		                             </rich:column>
		                             <rich:column width="170px">
		                                 <f:facet name="header">
		                                     <h:outputText value="Jefatura de Créditos"/>
		                                 </f:facet>
		                                 <h:outputText id="txtJefCred" value="#{item.stDescripcion}">
		                                 	<rich:toolTip followMouse="true" direction="top-left" for="txtJefCred" layout="block" horizontalOffset="5" verticalOffset="5" value="#{item.stDescripcion}"/>
		                                 </h:outputText>
		                             </rich:column>
			                </rich:extendedDataTable>
			                <h:outputText value="Observación:" rendered="#{not empty controlProcesoController.beanListJefatCreditos}"/> <rich:spacer height="4px;"/>
			                <h:inputTextarea value="#{controlProcesoController.stObsJefatCred}" rows="5" cols="32" rendered="#{not empty controlProcesoController.beanListJefatCreditos}" disabled="#{controlProcesoController.enableDisableJefCred}"/>
	         			</rich:column>
	         			
	         			<rich:column>
	         				<rich:extendedDataTable id="dtJefCob" value="#{controlProcesoController.beanListJefatCobranza}"
		                       	var="item" rowKeyVar="rowKey" width="180px" height="250px" enableContextMenu="false"
		                       	rendered="#{not empty controlProcesoController.beanListJefatCobranza}">
		                             <rich:column width="25">
		                                 <h:selectBooleanCheckbox value="#{item.inEstadoPerf}" disabled="#{controlProcesoController.enableDisableJefCobr}"/>
		                             </rich:column>
		                             <rich:column width="170px">
		                                 <f:facet name="header">
		                                     <h:outputText value="Jefatura de Cobranza"/>
		                                 </f:facet>
		                                 <h:outputText id="txtJefCob" value="#{item.stDescripcion}">
		                                 	<rich:toolTip followMouse="true" direction="top-left" for="txtJefCob" layout="block" horizontalOffset="5" verticalOffset="5" value="#{item.stDescripcion}"/>
		                                 </h:outputText>
		                             </rich:column>
			                </rich:extendedDataTable>
			                <h:outputText value="Observación:" rendered="#{not empty controlProcesoController.beanListJefatCobranza}"/> <rich:spacer height="4px;"/>
			                <h:inputTextarea value="#{controlProcesoController.stObsJefatCobr}" rows="5" cols="32" rendered="#{not empty controlProcesoController.beanListJefatCobranza}" disabled="#{controlProcesoController.enableDisableJefCobr}"/>
	         			</rich:column>
	         			
	         			<rich:column>
	         				<rich:extendedDataTable id="dtAsesLegal" value="#{controlProcesoController.beanListAsesorLegal}"
		                       	var="item" rowKeyVar="rowKey" width="180px" height="250px" enableContextMenu="false"
		                       	rendered="#{not empty controlProcesoController.beanListAsesorLegal}">
		                             <rich:column width="25">
		                                 <h:selectBooleanCheckbox value="#{item.inEstadoPerf}" disabled="#{controlProcesoController.enableDisableAsesLeg}"/>
		                             </rich:column>
		                             <rich:column width="170px">
		                                 <f:facet name="header">
		                                     <div align="left"><h:outputText value="Asesor Legal"/></div>
		                                 </f:facet>
		                                 <h:outputText id="txtAseLeg" value="#{item.stDescripcion}">
		                                 	<rich:toolTip followMouse="true" direction="top-left" for="txtAseLeg" layout="block" horizontalOffset="5" verticalOffset="5" value="#{item.stDescripcion}"/>
		                                 </h:outputText>
		                             </rich:column>
			                </rich:extendedDataTable>
			                <h:outputText value="Observación:" rendered="#{not empty controlProcesoController.beanListAsesorLegal}"/> <rich:spacer height="4px;"/>
			                <h:inputTextarea value="#{controlProcesoController.stObsAsesLegal}" rows="5" cols="32" rendered="#{not empty controlProcesoController.beanListAsesorLegal}" disabled="#{controlProcesoController.enableDisableAsesLeg}"/>
	         			</rich:column>
	         			
	         			<rich:column>
	         				<rich:extendedDataTable id="dtGerencia" value="#{controlProcesoController.beanListAsistGerencia}"
		                       	var="item" rowKeyVar="rowKey" width="180px" height="250px" enableContextMenu="false"
		                       	noDataLabel="No Hay Datos." >
		                             <rich:column width="170px">
		                                 <f:facet name="header">
		                                     <h:outputText value="Gerencia General"/>
		                                 </f:facet>
		                                 <h:outputText id="txtGerenc" value="#{item.stDescripcion}">
		                                 	<rich:toolTip followMouse="true" direction="top-left" for="txtGerenc" layout="block" horizontalOffset="5" verticalOffset="5" value="#{item.stDescripcion}"/>
		                                 </h:outputText>
		                             </rich:column>
			                </rich:extendedDataTable>
			                <h:outputText value="Observación:"/> <rich:spacer height="4px;"/>
			                <h:inputTextarea value="#{controlProcesoController.stObsGerencGral}" rows="5" cols="32" disabled="#{controlProcesoController.enableDisableAsistGer}"/>
	         			</rich:column>
	         			
	         		</h:panelGrid>
	         		
	         	</rich:panel>
         	</h:panelGrid>
   		</rich:tab>
      </rich:tabPanel>
   </h:form>