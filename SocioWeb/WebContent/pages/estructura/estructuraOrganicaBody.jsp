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

<script type="text/javascript">
	function jsSelectEstructura(estructuraKeys){
		//alert("Estructura keys: "+estructuraKeys);
        document.getElementById("formUpDelEstrucOrg:hiddenIdEstrucOrg").value=estructuraKeys;
    }
</script>

	<rich:modalPanel id="mpUpDelEstrucOrg" width="355" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Estructura Orgánica" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkEstrucOrg"/>
               <rich:componentControl for="mpUpDelEstrucOrg" attachTo="hidelinkEstrucOrg" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formUpDelEstrucOrg">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid border="0">
                    <h:outputText id="lblCodigo" value="¿Desea Actualizar o Eliminar Estructura Orgánica?" styleClass="tamanioLetra"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                	<h:panelGrid columns="2" border="0" style="margin:0 auto; width: 100%">
	                    <h:commandButton value="Actualizar" actionListener="#{estrucOrgController.modificarEstructura}" styleClass="btnEstilos"></h:commandButton>
	                    <h:commandButton value="Eliminar" actionListener="#{registroPcController.eliminarRegistroPc}" styleClass="btnEstilos"></h:commandButton>
	                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdEstrucOrg"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
	<rich:modalPanel id="pAgregarPerJuri" width="935" height="550"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Persona Jurídica"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidePanelPerJuri"/>
               <rich:componentControl for="pAgregarPerJuri" attachTo="hidePanelPerJuri" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclPerJuri" viewId="/pages/Creditos/perJuriConveBody.jsp"/>
    </rich:modalPanel>
	
    <h:form id="frmEstrucOrg">
      <rich:tabPanel>
        <rich:tab label="Estructura Organica">
         	<rich:panel style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder">
         		<a4j:region id="rgBusqEstructura">
         			<h:panelGrid columns="10" border="0">
                        <rich:column width="130"><h:outputText value="Tipo de Entidad"/></rich:column>
                        <rich:column>
							<h:selectOneMenu required="true"
			                     style="width: 100px;"
			                     value="#{estrucOrgController.intCboTipoEntidad}">
			                     <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOENTIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column width="150"><h:outputText style="padding-left: 15px" value="Nivel de Entidad"/></rich:column>
                        <rich:column width="120">
							<h:selectOneMenu 
			                     style="width: 100px;"
			                     value="#{estrucOrgController.intCboNivelEntidad}">
			                     <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column width="25"><h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckNombreEntidad}"/></rich:column>
                        <rich:column width="55"><h:outputText value="Nombre"/></rich:column>
                        <rich:column width="220">
                        	<h:inputText value="#{estrucOrgController.strNombreEntidad}" size="35"/>
                        </rich:column>
                        <rich:column width="90"><h:outputText value="Configuración"/></rich:column>
                    	<rich:column>
							<h:selectOneMenu id="cboConfigEstruc" style="width: 100px;"
			                     value="#{estrucOrgController.intCboConfigEstructura}">
			                     <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CASOESTRUCTURA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="15">
                    	<rich:column width="95"><h:outputText value="Tipo de Socio"/></rich:column>
                    	<rich:column>
                        	<h:selectOneMenu id="cboTipoSocioEstruc" style="width: 100px;"
				                 value="#{estrucOrgController.intCboTipoSocio}">
				                 <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        
                        <rich:column width="110">
                        	<h:outputText style="padding-left: 15px" value="Modalidad"/>
                       	</rich:column>
                    	<rich:column width="114">
							<h:selectOneMenu id="cboModalidadEstruc" style="width: 100px;"
				                  value="#{estrucOrgController.intCboModalidadEstructura}">
				                  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                    	
                    	<rich:column width="25"><h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckFechas}"/></rich:column>
                        <rich:column width="50"><h:outputText value="Fecha"/></rich:column>
                        <rich:column width="110">
							<h:selectOneMenu style="width: 100px;"
				                  value="#{estrucOrgController.intCboEstadoPlanilla}">
				                  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOPLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="border:0px;width:100px;">
		                   <rich:calendar id="idFecIniPerfUsu" popup="true"
		                   	value="#{estrucOrgController.dtFechaDesde}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		                </rich:column>
		                <rich:column style="border:0px;">
		                   <rich:calendar id="idFecFinPerfUsu" popup="true"
		                   	value="#{estrucOrgController.dtFechaHasta}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;" 
		                    cellWidth="10px" cellHeight="20px"/>
		                </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="10" border="0">
                    	<rich:column width="95"><h:outputText value="Sucursal"/></rich:column>
                        <rich:column>	
							<h:selectOneMenu style="width: 100px;"
								  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
				                  value="#{estrucOrgController.intCboSucursales}">
				                  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
								  <a4j:support event="onchange" reRender="cboEstrucSubSucur" ajaxSingle="true"></a4j:support>
							</h:selectOneMenu>
                        </rich:column>
                        
                    	<rich:column width="110"><h:outputText style="padding-left: 15px" value="Sub-Sucursal"/></rich:column>
                        <rich:column width="115">
							<h:selectOneMenu id="cboEstrucSubSucur" style="width: 100px;"
				                  value="#{estrucOrgController.intCboSubsucursales}">
				                  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
				                  <tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
									itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
							</h:selectOneMenu>
                        </rich:column>
                        
                        <rich:column width="25"><h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigo}"/></rich:column>
                    	<rich:column width="50"><h:outputText value="Código"/></rich:column>
                    	<rich:column width="220">
                        	<h:inputText value="#{estrucOrgController.strCodigoExterno}" size="35" />
                        </rich:column>
                        <a4j:commandButton value="Buscar" actionListener="#{estrucOrgController.listarEstructuraOrg}" reRender="tblEstrucOrg" styleClass="btnEstilos"/>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    
                    <rich:spacer height="4px"/><rich:spacer/>
                    <rich:spacer height="4px"/><rich:spacer/>
                    <h:panelGrid>
	          	 	<rich:extendedDataTable id="tblEstrucOrg" enableContextMenu="false" sortMode="single" 
                             var="item" value="#{estrucOrgController.beanListEstructuraOrg}"  
					  		 rowKeyVar="rowKey" rows="5" width="900px" height="165px" 
					  		 onRowClick="jsSelectEstructura('#{item.estructura.id.intCodigo},#{item.estructura.intPersEmpresaPk},#{item.estructura.id.intNivel}');">
                                
					  		 	<rich:column width="29px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column width="90">
                                    <f:facet name="header">
                                        <h:outputText  value="Código"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.id.intCodigo}"></h:outputText>
                                </rich:column>

                                <rich:column width="140">
                                    <f:facet name="header">
                                        <h:outputText value="Nombre de Entidad"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
                                </rich:column>

                                <rich:column width="90">
                                    <f:facet name="header">
                                        <h:outputText value="Nivel"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.id.intNivel}"></h:outputText>
                                </rich:column>
                                <rich:column width="110">
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de Socio"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strTipoSocioConcatenado}"></h:outputText>
                                </rich:column>
                                <rich:column width="90">
                                    <f:facet name="header">
                                        <h:outputText value="Estado"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.intParaEstadoCod}"></h:outputText>
                                </rich:column>
                                <rich:column width="110">
                                    <f:facet name="header">
                                        <h:outputText value="Configuración"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strConfiguracionConcatenado}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Modalidad"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strModalidadConcatenado}"></h:outputText>
                                </rich:column>
                                <rich:column width="140">
                                    <f:facet name="header">
                                        <h:outputText value="Fecha de Registro"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.strFechaRegistro}"></h:outputText>
                                </rich:column>
								<f:facet name="footer">   
						        	<rich:datascroller for="tblEstrucOrg" maxPages="10"/>   
						        </f:facet>
                     </rich:extendedDataTable>
	          	 </h:panelGrid>
	          	 
	          	 <rich:spacer height="8px"/>
                    <h:panelGrid columns="2">
						<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelEstrucOrg')">
							<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
						</a4j:commandLink>
						<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
				    </h:panelGrid>
				 </a4j:region>
				 </rich:panel>
				 
				 <rich:spacer height="8px"/>
				 <rich:panel id="pFormEstruc" style="border:none">
		   	    	 <h:panelGrid columns="3">
		                 <a4j:commandButton value="Nuevo" actionListener="#{estrucOrgController.showFormEstructura}" styleClass="btnEstilos" reRender="frmEstrucOrg"/>                     
		                 <a4j:commandButton value="Guardar" actionListener="#{estrucOrgController.grabarEstructura}" styleClass="btnEstilos" reRender="pFormEstruc,tblEstrucOrg"/>												                 
		                 <a4j:commandButton value="Cancelar" actionListener="#{estrucOrgController.hideFormEstructura}" styleClass="btnEstilos" reRender="pFormEstruc"/>
		          	 </h:panelGrid>
		          	 
			         <rich:panel id="pEstrucOrg" rendered="#{estrucOrgController.blnShowFormEstructura}" style="border:1px solid #17356f;background-color:#DEEBF5;">
			          	 	<h:panelGrid id="pgTipoEntidad">
			          	 		<h:selectOneRadio style="width: 200px" value="#{estrucOrgController.beanInsti.intIdGrupo}">
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOENTIDAD}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			          	 			<a4j:support ajaxSingle="true" event="onchange"></a4j:support>
			          	 		</h:selectOneRadio>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid id="pgEstadoEstruc" columns="3">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Estado de Entidad"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<h:selectOneMenu style="width: 100px;"
					                  value="#{estrucOrgController.beanInsti.intParaEstadoCod}">
									  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									  <a4j:support ajaxSingle="true" event="onchange"></a4j:support>
									</h:selectOneMenu>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<!--------------------------------------------- NIVEL 1: Instititucion --------------------------------------------->
			          	 	<h:panelGrid>
			          	 		<h:outputText value="Nivel 1: Institución" styleClass="estiloLetra1"></h:outputText>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid id="pgRazonSocN1" columns="6">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Nombre"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column width="240">
			          	 			<h:inputText value="#{perJuridicaController.beanInsti.juridica.strRazonSocial}" size="40">
			          	 				<a4j:support ajaxSingle="true" event="onblur"></a4j:support>
			          	 			</h:inputText>
			          	 		</rich:column>
			          	 		<rich:column width="160">
			          	 			<a4j:commandButton id="btnAgregarPerJuriN1" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
			          	 							   actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}" reRender="frmPerJuriConve">
			          	 				<rich:componentControl for="pAgregarPerJuri" attachTo="btnAgregarPerJuriN1" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton id="btnAgregarConfigN1" value="#{estrucOrgController.strBtnAgregarN1}" actionListener="#{estrucOrgController.showConfigN1}" 
			          	 							reRender="pConfigN1,pProcesPlanillaN1,pgProcesPlanillaN1,pAdministraN1,pgAdministraN1,pCobChequesN1,pgCobChequesN1"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1"></a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid id="pgTipoTercerosN1" columns="4">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Tipo"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<h:selectOneMenu style="width: 100px;"
					                  value="#{estrucOrgController.beanInsti.intParaTipoTerceroCod}">
									  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ENTIDADTERCERO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									  <a4j:support ajaxSingle="true" event="onchange"></a4j:support>
									</h:selectOneMenu>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	
			          <!--------------------------------------------- Configuracion Nivel 1 --------------------------------------------->
			          <rich:panel id="pConfigN1" style="border: none">
			          	 
			          	 <!--------------------------------------------- Procesar Planilla Nivel 1 --------------------------------------------->
			          	 <rich:panel id="pProcesPlanillaN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none">
			          	 		<h:panelGrid id="pgProcesPlanillaN1" columns="4">
				          	 		<rich:column width="25">
				          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkProcesPlanillaN1}">
				          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showProcesPlanillaN1}" reRender="pProcesPlanillaN1,pConfigProcesPlanillaN1" ajaxSingle="true"></a4j:support>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column>
				          	 			<h:outputText value="Procesar Planilla"></h:outputText>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 <!--------------------------------------------- Configuracion Procesar Planilla Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigProcesPlanillaN1" rendered="#{estrucOrgController.renderProcesPlanillaN1}" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/>
				          	 	<a4j:region id="regConfigPlanilaN1">
					          	 	<h:panelGrid id ="pg1ConfigPlanilaN1" columns="10">
					          	 		<rich:column width="90">
					          	 			<h:outputText value="Tipo de Socio"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboTipoSocioPlanillaN1" style="width: 100px;" 
								                 value="#{estrucOrgController.beanInstiPlanilla.intTipoSocio}">
												 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadPlanillaN1" style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiPlanilla.intModalidad}">
											  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalPlanillaN1" style="width: 100px;" 
												  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
								                  value="#{estrucOrgController.beanInstiPlanilla.intSucursal}">
												  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
												  <a4j:support event="onchange" reRender="cboSubSucurPlanillaN1" ajaxSingle="true"></a4j:support>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalInstiPlanilla}"></h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column width="80">
					          	 			<h:outputText value="Sub Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:selectOneMenu id="cboSubSucurPlanillaN1" style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiPlanilla.intSubsucursal}">
											  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                <tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
													itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	<h:panelGrid id="pg2ConfigPlanilaN1" columns="10">
					          	 		<rich:column width="90">
					          	 			<h:outputText value="Fecha Enviado"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiPlanilla.intDiaEnviado}" size="6"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiPlanilla.intFechaEnviado}">
											  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="100">
					          	 			<h:outputText value="Fecha efectuado"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiPlanilla.intDiaEfectuado}" size="6"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiPlanilla.intFechaEfectuado}">
											  <f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="20">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoInstiPlanilla}"></h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column width="40">
					          	 			<h:outputText value="Código"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:inputText value="#{estrucOrgController.beanInstiPlanilla.strCodigoExterno}" size="15"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarPlanillaInsti}"
					          	 							   reRender="tblProcesPlanillaN1" styleClass="btnEstilos">
					          	 			</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 	<h:panelGrid>
				          	 		<rich:extendedDataTable id="tblProcesPlanillaN1" enableContextMenu="false" 
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}" style="margin:0 auto"
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="860px" height="165px">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
				                                </rich:column>
				
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Enviado"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaEnviado}"></h:outputText>
				                                </rich:column>
				                                
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Efectuado"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaEfectuado}"></h:outputText>
				                                </rich:column>
				                                
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Código"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblProcesPlanillaN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
				          	 	</h:panelGrid>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 	<h:panelGrid columns="2" style="margin:0 auto">
									<h:outputLink value="#" id="linkUpDelProcesPlanillaN1">
								        <h:graphicImage value="/images/icons/mensaje1.jpg"
											style="border:0px"/>
								        <rich:componentControl for="pnUpDelProcesPlanilla" attachTo="linkUpDelProcesPlanillaN1" operation="show" event="onclick"/>
									</h:outputLink>
									<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
								</h:panelGrid>
							</rich:panel>
			          	 </rich:panel>
			          	 
			          	 <!--------------------------------------------- Administra Nivel 1 --------------------------------------------->	
			          	 <rich:panel id="pAdministraN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none;">
			          	 		<h:panelGrid id="pgAdministraN1" columns="4">
				          	 		<rich:column width="25">
				          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkAdministraN1}">
				          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showAdministraN1}" reRender="pAdministraN1,pConfigAdministraN1" ajaxSingle="true"></a4j:support>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column>
				          	 			<h:outputText value="Administra"></h:outputText>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 
				          	 <!--------------------------------------------- Configuracion Administra Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigAdministraN1" rendered="#{estrucOrgController.renderAdministraN1}" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/>
				          	 	<a4j:region id="regConfigAdministraN1">
					          	 	<h:panelGrid id="pgConfigAdministraN1" columns="10">
					          	 		<rich:column width="90">
					          	 			<h:outputText value="Tipo de Socio"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboTipoSocioAdministraN1" style="width: 100px;"
								                 value="#{estrucOrgController.beanInstiAdministra.intParaTipoSocioCod}">
												 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadAdministraN1" style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiAdministra.intParaModalidadCod}">
											  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalAdministraN1" style="width: 100px;"
												  valueChangeListener="#{blnCheckSubsucursalInstiAdministra}"
								                  value="#{estrucOrgController.beanInstiAdministra.intSeguSucursalPk}">
												  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
												  <a4j:support event="onchange" reRender="cboSubSucurAdministraN1" ajaxSingle="true"></a4j:support>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalInstiAdministra}"></h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column width="80">
					          	 			<h:outputText value="Sub Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:selectOneMenu id="cboSubSucurAdministraN1" style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiAdministra.intSeguSubSucursalPk}">
											  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                <tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
													itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="2">
					          	 		<rich:column>
					          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarAdministraInsti}"
						          	 						   reRender="tblAdministraN1" styleClass="btnEstilos">
						          	 		</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer> 	 	
	
									<rich:extendedDataTable id="tblAdministraN1" enableContextMenu="false" style="margin:0 auto;"
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}" 
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="490px" height="165px">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
				                                </rich:column>
				                                
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblAdministraN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
				          	 	
				          	 	<h:panelGroup layout="block" style="border: none; margin:0 auto; width: 480px;">
					          	 	<h:panelGrid columns="2">
										<h:outputLink value="#" id="linkUpDelAdministraN1">
									        <h:graphicImage value="/images/icons/mensaje1.jpg"
												style="border:0px"/>
									        <rich:componentControl for="pnUpDelAdministra" attachTo="linkUpDelAdministraN1" operation="show" event="onclick"/>
										</h:outputLink>
										<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
								    </h:panelGrid>
							    </h:panelGroup>
							</rich:panel>
			          	 </rich:panel>
			          	 
			          	 <!--------------------------------------------- Cobranza de Cheques Nivel 1 --------------------------------------------->
			          	 <rich:panel id="pCobChequesN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none">
			          	 		<h:panelGrid id="pgCobChequesN1" columns="4">
				          	 		<rich:column width="25">
				          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkCobChequesN1}">
				          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showCobChequesN1}" 
				          	 							 reRender="pCobChequesN1,pConfigCobChequesN1" ajaxSingle="true">
				          	 				</a4j:support>
				          	 			</h:selectBooleanCheckbox>
				          	 		</rich:column>
				          	 		<rich:column>
				          	 			<h:outputText value="Cobranza de Cheques"></h:outputText>
				          	 		</rich:column>
				          	 	</h:panelGrid>
				          	 
				          	 <!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigCobChequesN1" rendered="#{estrucOrgController.renderCobChequesN1}" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/>
				          	 	<a4j:region id="regConfigCobChequesN1">
					          	 	<h:panelGrid columns="10">
					          	 		<rich:column width="90">
					          	 			<h:outputText value="Tipo de Socio"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboTipoSocioCobraN1" style="width: 100px;"
									                 value="#{estrucOrgController.beanInstiCobra.intParaTipoSocioCod}">
													 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadCobraN1" style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiCobra.intParaModalidadCod}">
											  	<f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalCobraN1" style="width: 100px;"
												  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
								                  value="#{estrucOrgController.beanInstiCobra.intSeguSucursalPk}">
												  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
												  <a4j:support event="onchange" reRender="cboSubSucurCobraN1" ajaxSingle="true"></a4j:support>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalInstiCobra}"></h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column width="80">
					          	 			<h:outputText value="Sub Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:selectOneMenu id="cboSubSucurCobraN1" style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiCobra.intSeguSubSucursalPk}">
											  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                <tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
													itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="10">
					          	 		<rich:column width="110">
					          	 			<h:outputText value="Fecha de Cheque"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiCobra.intDiaCheque}" size="6"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiCobra.intSaltoCheque}">
											  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="20">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoInstiCobra}"></h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column width="40">
					          	 			<h:outputText value="Código"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:inputText value="#{estrucOrgController.beanInstiCobra.strCodigoExterno}" size="15"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarCobraInsti}"
						          	 						   reRender="tblCobChequeN1" styleClass="btnEstilos">
						          	 		</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 		<rich:extendedDataTable id="tblCobChequeN1" enableContextMenu="false" sortMode="single" 
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}" 
									  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
				                                </rich:column>
				
				                                <rich:column width="165">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Cobro de Cheque"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaCheque}"></h:outputText>
				                                </rich:column>
				                                
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Código"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblCobChequeN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 	<h:panelGroup layout="block" style="margin:0 auto; width: 480px">
				          	 		<h:panelGrid columns="2">
										<h:outputLink value="#" id="linkUpDelCobChequeN1">
									        <h:graphicImage value="/images/icons/mensaje1.jpg"
												style="border:0px"/>
									        <rich:componentControl for="pnCobCheque" attachTo="linkUpDelCobChequeN1" operation="show" event="onclick"/>
										</h:outputLink>
										<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
								    </h:panelGrid>
				          	 	</h:panelGroup>
				          	 </rich:panel>
			          	 </rich:panel>
			          </rich:panel>
			          	 	
			          	 	<!--------------------------------------------- NIVEL 2: Unidad Ejecutora --------------------------------------------->
			          	 	<rich:separator></rich:separator>
			          	 	<rich:spacer height="8px"></rich:spacer>
			          	 	<h:panelGrid>
			          	 		<h:outputText value="Nivel 2: Unidad Ejecutora" styleClass="estiloLetra1"></h:outputText>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid id="pgRazonSocN2" columns="6">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Nombre"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column width="240">
			          	 			<h:inputText value="#{beanUniEjecu.juridica.strRazonSocial}" size="40">
			          	 				<a4j:support ajaxSingle="true" event="onblur"></a4j:support>
			          	 			</h:inputText>
			          	 		</rich:column>
			          	 		<rich:column width="160">
			          	 			<a4j:commandButton id="btnAgregarPerJuriN2" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
			          	 							   actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}">
			          	 				<rich:componentControl for="pAgregarPerJuri" attachTo="btnAgregarPerJuriN2" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton value="#{estrucOrgController.strBtnAgregarN2}" actionListener="#{estrucOrgController.showConfigN2}" 
			          	 							reRender="pConfigN2,pgN2,pProcesPlanillaN2,pgProcesPlanillaN2,pAdministraN2,pgAdministraN2,pCobChequesN2,pgCobChequesN2"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 
			          	 <!--------------------------------------------- Configuracion Nivel 2 --------------------------------------------->
			          	 <rich:panel id="pConfigN2" style="border: none">
			          	 
				          	 <!--------------------------------------------- Procesar Planilla Nivel 2 --------------------------------------------->
				          	 <rich:panel id="pProcesPlanillaN2" rendered="#{estrucOrgController.renderConfigN2}" style="border: none">
				          	 		<h:panelGrid id="pgProcesPlanillaN2" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkProcesPlanillaN2}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showProcesPlanillaN2}" reRender="pProcesPlanillaN2,pConfigProcesPlanillaN2" ajaxSingle="true"></a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Procesar Planilla"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 <!--------------------------------------------- Configuracion Procesar Planilla Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigProcesPlanillaN2" rendered="#{estrucOrgController.renderProcesPlanillaN2}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigPlanillaN2">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboTipoSocioPlanillaN2" style="width: 100px;"
									                 value="#{estrucOrgController.beanUniEjecuPlanilla.intParaTipoSocioCod}">
													 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadPlanillaN2" style="width: 100px;"
								                  value="#{estrucOrgController.beanUniEjecuPlanilla.intParaModalidadCod}">
												  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalPlanillaN2" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanUniEjecuPlanilla.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurPlanillaN2" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalUniEjecuPlanilla}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurPlanillaN2" style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuPlanilla.intSeguSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Fecha Enviado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanUniEjecuPlanilla.intDiaEnviado}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuPlanilla.intSaltoEnviado}">
												  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="100">
						          	 			<h:outputText value="Fecha efectuado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanUniEjecuPlanilla.intDiaEfectuado}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  value="#{estrucOrgController.beanUniEjecuPlanilla.intSaltoEfectuado}">
												  <f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="20">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoUniEjecuPlanilla}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="40">
						          	 			<h:outputText value="Código"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:inputText value="#{estrucOrgController.beanUniEjecuPlanilla.strCodigoExterno}" size="15"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarPlanillaUniEjecu}"
						          	 						   reRender="tblProcesPlanillaN2" styleClass="btnEstilos">
						          	 			</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid>
					          	 		<rich:extendedDataTable id="tblProcesPlanillaN2" enableContextMenu="false" 
					                             var="item" value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetallePlanilla}" style="margin:0 auto"
										  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="860px" height="165px">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Enviado"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaEnviado}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Efectuado"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaEfectuado}"></h:outputText>
					                                </rich:column>
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Código"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblProcesPlanillaN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="2" style="margin:0 auto">
										<h:outputLink value="#" id="linkUpDelProcesPlanillaN2">
									        <h:graphicImage value="/images/icons/mensaje1.jpg"
												style="border:0px"/>
									        <rich:componentControl for="pnUpDelProcesPlanilla" attachTo="linkUpDelProcesPlanillaN2" operation="show" event="onclick"/>
										</h:outputLink>
										<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
								    </h:panelGrid>
								</rich:panel>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Administra Nivel 2 --------------------------------------------->	
				          	 <rich:panel id="pAdministraN2" rendered="#{estrucOrgController.renderConfigN2}" style="border: none;">
				          	 		<h:panelGrid id="pgAdministraN2" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkAdministraN2}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showAdministraN2}" reRender="pAdministraN2,pConfigAdministraN2" ajaxSingle="true"></a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Administra"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 
					          	 <!--------------------------------------------- Configuracion Administra Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigAdministraN2" rendered="#{estrucOrgController.renderAdministraN2}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigAdministraN2">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboTipoSocioAdministraN2" style="width: 100px;"
									                 value="#{estrucOrgController.beanUniEjecuAdministra.intParaTipoSocioCod}">
													 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadAdministraN2" style="width: 100px;"
								                  value="#{estrucOrgController.beanUniEjecuAdministra.intParaModalidadCod}">
												  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalAdministraN2" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanUniEjecuAdministra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurAdministraN2" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalUniEjecuAdministra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurAdministraN2" style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuAdministra.intSeguSubSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	
						          	 	<h:panelGrid columns="2">
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarAdministraUniEjecu}"
							          	 						   reRender="tblAdministraN2" styleClass="btnEstilos">
							          	 		</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer> 	 	
		
										<rich:extendedDataTable id="tblAdministraN2" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetalleAdministra}"   
										  		 rowKeyVar="rowKey" rows="5" width="490px" height="165px" style="margin:0 auto;">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk.strSucursal}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk.strSubsucursal}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblAdministraN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	
					          	 	<h:panelGroup layout="block" style="border: none; margin:0 auto; width: 480px;">
						          	 	<h:panelGrid columns="2">
											<h:outputLink value="#" id="linkUpDelAdministraN2">
										        <h:graphicImage value="/images/icons/mensaje1.jpg"
													style="border:0px"/>
										        <rich:componentControl for="pnUpDelAdministra" attachTo="linkUpDelAdministraN2" operation="show" event="onclick"/>
											</h:outputLink>
											<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
									    </h:panelGrid>
								    </h:panelGroup>
								</rich:panel>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Cobranza de Cheques Nivel 2 --------------------------------------------->
				          	 <rich:panel id="pCobChequesN2" rendered="#{estrucOrgController.renderConfigN2}" style="border: none">
				          	 		<h:panelGrid id="pgCobChequesN2" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkCobChequesN2}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showCobChequesN2}" 
					          	 							 reRender="pCobChequesN2,pConfigCobChequesN2" ajaxSingle="true">
					          	 				</a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Cobranza de Cheques"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 
					          	 <!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigCobChequesN2" rendered="#{estrucOrgController.renderCobChequesN2}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigCobChequesN2">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
							          	 			<h:selectOneMenu id="cboTipoSocioCobraN2" style="width: 100px;"
										                 value="#{estrucOrgController.beanUniEjecuCobra.intParaTipoSocioCod}">
														 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
													</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadCobraN2" style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuCobra.intParaModalidadCod}">
												  	<f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalCobraN2" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanUniEjecuCobra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurCobraN2" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalUniEjecuCobra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurCobraN2" style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuCobra.intSeguSubSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="110">
						          	 			<h:outputText value="Fecha de Cheque"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanUniEjecuCobra.intDiaCheque}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanUniEjecuCobra.intSaltoCheque}">
												  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="20">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoUniEjecuCobra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="40">
						          	 			<h:outputText value="Código"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:inputText value="#{estrucOrgController.beanUniEjecuCobra.strCodigoExterno}" size="15"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarCobraUniEjecu}"
						          	 						   reRender="tblCobChequeN2" styleClass="btnEstilos">
						          	 			</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 		<rich:extendedDataTable id="tblCobChequeN2" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanListUniEjecuCobra}" 
										  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column width="165">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Cobro de Cheque"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaCheque}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Código"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblCobChequeN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGroup layout="block" style="margin:0 auto; width: 480px">
					          	 		<h:panelGrid columns="2">
											<h:outputLink value="#" id="linkUpDelCobChequeN2">
										        <h:graphicImage value="/images/icons/mensaje1.jpg"
													style="border:0px"/>
										        <rich:componentControl for="pnCobCheque" attachTo="linkUpDelCobChequeN2" operation="show" event="onclick"/>
											</h:outputLink>
											<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
									    </h:panelGrid>
					          	 	</h:panelGroup>
					          	 </rich:panel>
				          	 </rich:panel>
				          </rich:panel>
			          	 	
			          	 	<!--------------------------------------------- NIVEL 3: Dependencia --------------------------------------------->
			          	 	<rich:separator></rich:separator>
			          	 	<rich:spacer height="8px"></rich:spacer>
			          	 	<h:panelGrid>
			          	 		<h:outputText value="Nivel 3: Dependencia" styleClass="estiloLetra1"></h:outputText>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid id="pgRazonSocN3" columns="6">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Nombre"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column width="240">
			          	 			<h:inputText value="#{beanDepen.juridica.strRazonSocial}" size="40">
			          	 				<a4j:support ajaxSingle="true" event="onblur"></a4j:support>
			          	 			</h:inputText>
			          	 		</rich:column>
			          	 		<rich:column width="160">
			          	 			<a4j:commandButton id="btnAgregarPerJuriN3" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
			          	 							   actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}">
			          	 				<rich:componentControl for="pAgregarPerJuri" attachTo="btnAgregarPerJuriN3" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton value="#{estrucOrgController.strBtnAgregarN3}" actionListener="#{estrucOrgController.showConfigN3}" 
			          	 							reRender="pConfigN3,pgN3,pProcesPlanillaN3,pgProcesPlanillaN3,pAdministraN3,pgAdministraN3,pCobChequesN3,pgCobChequesN3"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 
			          	 <!--------------------------------------------- Configuracion Nivel 3 --------------------------------------------->	
			          	 <rich:panel id="pConfigN3" style="border: none">
			          	 
				          	 <!--------------------------------------------- Procesar Planilla Nivel 3 --------------------------------------------->
				          	 <rich:panel id="pProcesPlanillaN3" rendered="#{estrucOrgController.renderConfigN3}" style="border: none">
				          	 		<h:panelGrid id="pgProcesPlanillaN3" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkProcesPlanillaN3}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showProcesPlanillaN3}" reRender="pProcesPlanillaN3,pConfigProcesPlanillaN3" ajaxSingle="true"></a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Procesar Planilla"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 <!--------------------------------------------- Configuracion Procesar Planilla Nivel 3 --------------------------------------------->
					          	 <rich:panel id="pConfigProcesPlanillaN3" rendered="#{estrucOrgController.renderProcesPlanillaN3}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigPlanillaN3">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboTipoSocioPlanillaN3" style="width: 100px;"
									                 value="#{estrucOrgController.beanDepenPlanilla.intParaTipoSocioCod}">
													 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadPlanillaN3" style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenPlanilla.intParaModalidadCod}">
												  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalPlanillaN3" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanDepenPlanilla.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurPlanillaN3" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalDepenPlanilla}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurPlanillaN3" style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenPlanilla.intSeguSubSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Fecha Enviado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanDepenPlanilla.intDiaEnviado}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenPlanilla.intSaltoEnviado}">
												  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="100">
						          	 			<h:outputText value="Fecha efectuado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanDepenPlanilla.intDiaEfectuado}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenPlanilla.intSaltoEfectuado}">
												  <f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="20">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoDepenPlanilla}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="40">
						          	 			<h:outputText value="Código"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:inputText value="#{estrucOrgController.beanDepenPlanilla.strCodigoExterno}" size="15"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarPlanillaDepen}"
						          	 						   reRender="tblProcesPlanillaN3" styleClass="btnEstilos">
						          	 			</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid>
					          	 		<rich:extendedDataTable id="tblProcesPlanillaN3" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanListDepenPlanilla}" 
										  		 rowKeyVar="rowKey" rows="5" width="860px" height="165px" style="margin:0 auto">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Enviado"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaEnviado}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Efectuado"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaEfectuado}"></h:outputText>
					                                </rich:column>
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Código"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblProcesPlanillaN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="2" style="margin:0 auto">
										<h:outputLink value="#" id="linkUpDelProcesPlanillaN3">
									        <h:graphicImage value="/images/icons/mensaje1.jpg"
												style="border:0px"/>
									        <rich:componentControl for="pnUpDelProcesPlanilla" attachTo="linkUpDelProcesPlanillaN3" operation="show" event="onclick"/>
										</h:outputLink>
										<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
								    </h:panelGrid>
								</rich:panel>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Administra Nivel 3 --------------------------------------------->	
				          	 <rich:panel id="pAdministraN3" rendered="#{estrucOrgController.renderConfigN3}" style="border: none;">
				          	 		<h:panelGrid id="pgAdministraN3" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkAdministraN3}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showAdministraN3}" reRender="pAdministraN3,pConfigAdministraN3" ajaxSingle="true"></a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Administra"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 
					          	 <!--------------------------------------------- Configuracion Administra Nivel 3 --------------------------------------------->
					          	 <rich:panel id="pConfigAdministraN3" rendered="#{estrucOrgController.renderAdministraN3}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigAdministraN3">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboTipoSocioAdministraN3" style="width: 100px;"
									                 value="#{estrucOrgController.beanDepenAdministra.intParaTipoSocioCod}">
													 <f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadAdministraN3" style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenAdministra.intParaModalidadCod}">
												  <f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalAdministraN3" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanDepenAdministra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurAdministraN3" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalDepenAdministra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurAdministraN3" style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenAdministra.intSeguSubSucursalPkl}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	
						          	 	<h:panelGrid columns="2">
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarAdministraDepen}"
							          	 						   reRender="tblAdministraN3" styleClass="btnEstilos">
							          	 		</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer> 	 	
		
										<rich:extendedDataTable id="tblAdministraN3" enableContextMenu="false" sortMode="single" 
					                             var="item" value="#{estrucOrgController.beanDepen.listaEstructuraDetalleAdministra}" 
										  		 rowKeyVar="rowKey" rows="5" width="490px" height="165px" style="margin:0 auto;">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblAdministraN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	
					          	 	<h:panelGroup layout="block" style="border: none; margin:0 auto; width: 480px;">
						          	 	<h:panelGrid columns="2">
											<h:outputLink value="#" id="linkUpDelAdministraN3">
										        <h:graphicImage value="/images/icons/mensaje1.jpg"
													style="border:0px"/>
										        <rich:componentControl for="pnUpDelAdministra" attachTo="linkUpDelAdministraN3" operation="show" event="onclick"/>
											</h:outputLink>
											<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
									    </h:panelGrid>
								    </h:panelGroup>
								</rich:panel>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Cobranza de Cheques Nivel 3 --------------------------------------------->
				          	 <rich:panel id="pCobChequesN3" rendered="#{estrucOrgController.renderConfigN3}" style="border: none">
				          	 		<h:panelGrid id="pgCobChequesN3" columns="4">
					          	 		<rich:column width="25">
					          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.chkCobChequesN3}">
					          	 				<a4j:support event="onclick" actionListener="#{estrucOrgController.showCobChequesN3}" 
					          	 							 reRender="pCobChequesN3,pConfigCobChequesN3" ajaxSingle="true">
					          	 				</a4j:support>
					          	 			</h:selectBooleanCheckbox>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:outputText value="Cobranza de Cheques"></h:outputText>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 
					          	 <!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 3 --------------------------------------------->
					          	 <rich:panel id="pConfigCobChequesN3" rendered="#{estrucOrgController.renderCobChequesN3}" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	<a4j:region id="regConfigCobChequesN3">
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Tipo de Socio"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
							          	 		<h:selectOneMenu id="cboTipoSocioCobraN3" style="width: 100px;"
										            value="#{estrucOrgController.beanDepenCobra.intParaTipoSocioCod}">
													<f:selectItems value="#{parametersController.cboTipoSocio}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu id="cboModalidadCobraN3" style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenCobra.intParaModalidadCod}">
												  	<f:selectItems value="#{parametersController.cboModalidadEstructura}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
						          	 			<h:selectOneMenu id="cboSucursalCobraN3" style="width: 100px;"
													  valueChangeListener="#{controllerFiller.reloadCboSubSucursales}"
									                  value="#{estrucOrgController.beanDepenCobra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  	  <tumih:selectItems var="sel" value="#{controllerFiller.cboSucursalesAuto}"
													  	itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
													  <a4j:support event="onchange" reRender="cboSubSucurCobraN3" ajaxSingle="true"></a4j:support>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="25">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckSubsucursalDepenCobra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="80">
						          	 			<h:outputText value="Sub Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:selectOneMenu id="cboSubSucurCobraN3" style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenCobra.intSeguSubSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                	<tumih:selectItems var="sel" value="#{controllerFiller.cboSubSucursales}"
														itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="110">
						          	 			<h:outputText value="Fecha de Cheque"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanDepenCobra.intDiaCheque}" size="6"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenCobra.intSaltoCheque}">
												  	<f:selectItems value="#{parametersController.cboFechaEnvioCobro}"></f:selectItems>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="20">
						          	 			<h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigoDepenCobra}"></h:selectBooleanCheckbox>
						          	 		</rich:column>
						          	 		<rich:column width="40">
						          	 			<h:outputText value="Código"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
						          	 			<h:inputText value="#{estrucOrgController.beanDepenCobra.strCodigoExterno}" size="15"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<a4j:commandButton value="Agregar" actionListener="#{estrucOrgController.agregarCobraDepen}"
							          	 						   reRender="tblCobChequeN3" styleClass="btnEstilos">
							          	 		</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 		<rich:extendedDataTable id="tblCobChequeN3" enableContextMenu="false" sortMode="single" 
					                             var="item" value="#{estrucOrgController.beanListDepenCobra}" 
										  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto">
					                                
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
					                                </rich:column>
					
					                                <rich:column width="165">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Cobro de Cheque"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaCheque}"></h:outputText>
					                                </rich:column>
					                                
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Código"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.strCodigoExterno}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSucursalPk}"></h:outputText>
					                                </rich:column>
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intSeguSubSucursalPk}"></h:outputText>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblCobChequeN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGroup layout="block" style="margin:0 auto; width: 480px">
					          	 		<h:panelGrid columns="2">
											<h:outputLink value="#" id="linkUpDelCobChequeN3">
										        <h:graphicImage value="/images/icons/mensaje1.jpg"
													style="border:0px"/>
										        <rich:componentControl for="pnCobCheque" attachTo="linkUpDelCobChequeN3" operation="show" event="onclick"/>
											</h:outputLink>
											<h:outputText value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón." style="color:#8ca0bd"/>                                     
									    </h:panelGrid>
					          	 	</h:panelGroup>
					          	 </rich:panel>
				          	 </rich:panel>
				          </rich:panel>
			          	 	
			       </rich:panel>
		       </rich:panel>
			</rich:panel>
    	</rich:tab>
    	
      </rich:tabPanel>
   </h:form>