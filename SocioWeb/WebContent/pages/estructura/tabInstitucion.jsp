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
	
    <h:form id="frmInstitucion">
         	<rich:panel style="border:1px solid #17356f">
         		<rich:panel styleClass="rich-tabcell-noborder">
         		<a4j:region id="rgBusqEstructura">
         			<h:panelGrid columns="10" border="0">
                        <rich:column width="100"><h:outputText value="Tipo de Entidad"/></rich:column>
                        <rich:column width="115">
							<h:selectOneMenu required="true"
			                     style="width: 100px;"
			                     value="#{estrucOrgController.intCboTipoEntidad}">
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOENTIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
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
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CASOESTRUCTURA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="15">
                    	<rich:column width="100"><h:outputText value="Tipo de Socio"/></rich:column>
                    	<rich:column>
                        	<h:selectOneMenu id="cboTipoSocioEstruc" style="width: 100px;"
				                 value="#{estrucOrgController.intCboTipoSocio}">
								 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
                        </rich:column>
                        
                        <rich:column width="110">
                        	<h:outputText style="padding-left: 15px" value="Modalidad"/>
                       	</rich:column>
                    	<rich:column width="114">
							<h:selectOneMenu id="cboModalidadEstruc" style="width: 100px;"
				                  value="#{estrucOrgController.intCboModalidadEstructura}">
								  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
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
                        <rich:column>
		                    <h:inputText value="#{estrucOrgController.intFechaDesde}" size="10"></h:inputText>
		                </rich:column>
		                <rich:column>
		                    <h:inputText value="#{estrucOrgController.intFechaHasta}" size="10"></h:inputText>
		                </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="10" border="0">
                    	<rich:column width="100"><h:outputText value="Sucursal"/></rich:column>
                        <rich:column>	
							<h:selectOneMenu id="cboSucursalBusqueda" style="width: 100px;"
								  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
				                  value="#{estrucOrgController.intCboSucursales}">
				                  <f:selectItem itemValue="-1" itemLabel="Todas las Sucursales"/>
				                  <f:selectItem itemValue="-2" itemLabel="Todas las Agencias"/>
				                  <f:selectItem itemValue="-3" itemLabel="Todas las Filiales"/>
				                  <f:selectItem itemValue="-4" itemLabel="Todas las Oficinas Principales"/>
								  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
								  <a4j:support event="onchange" reRender="cboEstrucSubSucur" ajaxSingle="true"></a4j:support>
							</h:selectOneMenu>
                        </rich:column>
                        
                    	<rich:column width="110"><h:outputText style="padding-left: 15px" value="Sub-Sucursal"/></rich:column>
                        <rich:column width="115">
							<h:selectOneMenu id="cboEstrucSubSucur" style="width: 100px;"
				                  value="#{estrucOrgController.intCboSubsucursales}">
				                  <f:selectItem itemValue="-1" itemLabel="Todos"/>
								  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSubsucursal}"
									itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        
                        <rich:column width="25"><h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckCodigo}"/></rich:column>
                    	<rich:column width="50"><h:outputText value="Código"/></rich:column>
                    	<rich:column width="220">
                        	<h:inputText value="#{estrucOrgController.strCodigoExterno}" size="35" />
                        </rich:column>
                        <a4j:commandButton id="btnBusquedaInsti" value="Buscar" actionListener="#{estrucOrgController.buscarInstitucion}" reRender="divEstrucOrg" styleClass="btnEstilos"/>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    
                    <rich:spacer height="4px"/><rich:spacer/>
                    <rich:spacer height="4px"/><rich:spacer/>
                    <h:panelGroup id="divEstrucOrg" layout="block">
	          	 	<rich:extendedDataTable id="tblEstrucOrg" enableContextMenu="false" sortMode="single" 
	          	 			 rendered="#{not empty estrucOrgController.beanListInstitucion}"
                             var="item" value="#{estrucOrgController.beanListInstitucion}"  
					  		 rowKeyVar="rowKey" rows="5" width="900px" height="186px" 
					  		 onRowClick="jsSelectEstructura('#{item.estructura.id.intCodigo},#{item.estructura.id.intNivel},#{item.estructura.intPersPersonaPk},#{item.estructura.intPersEmpresaPk}');">
                                
					  		 	<rich:column width="29px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column width="90">
                                    <f:facet name="header">
                                        <h:outputText  value="Código"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.id.intCodigo}"></h:outputText>
                                </rich:column>

                                <rich:column width="230">
                                    <f:facet name="header">
                                        <h:outputText value="Nombre de Entidad"></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
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
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.estructura.intParaEstadoCod}"/>
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
									<h:panelGroup layout="block">
										<rich:datascroller for="tblEstrucOrg" maxPages="10"/> 
										<h:panelGroup layout="block" style="margin:0 auto; width:750px">
											<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelEstrucOrg')">
												<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
											</a4j:commandLink>
											<h:outputText style="color:#8ca0bd"
												value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón."/>
										</h:panelGroup>
									</h:panelGroup>
								</f:facet>
                     </rich:extendedDataTable>
	          	 </h:panelGroup>
				 </a4j:region>
				 </rich:panel>
				 
				 <rich:spacer height="8px"/>
				 <rich:panel id="pFormEstruc" style="border:none">
		   	    	 <h:panelGrid columns="3">
		                 <a4j:commandButton value="Nuevo" actionListener="#{estrucOrgController.showFormInstitucion}" styleClass="btnEstilos" reRender="pFormEstruc"/>                     
		                 <a4j:commandButton value="Guardar" actionListener="#{estrucOrgController.grabarInstitucion}" styleClass="btnEstilos" reRender="pFormEstruc,tblEstrucOrg"/>												                 
		                 <a4j:commandButton value="Cancelar" actionListener="#{estrucOrgController.hideFormInstitucion}" styleClass="btnEstilos" reRender="pFormEstruc"/>
		          	 </h:panelGrid>
		          	 
			         <rich:panel id="pEstrucOrg" rendered="#{estrucOrgController.blnShowFormInstitucion}" style="border:1px solid #17356f;background-color:#DEEBF5;">
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
			          	 			<h:inputText value="#{perJuridicaController.beanPerJuridicaN1.juridica.strRazonSocial}" size="40" disabled="true"/>
			          	 		</rich:column>
			          	 		<rich:column width="160">
			          	 			<a4j:commandButton id="btnAgregarPerJuriN1" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
			          	 								actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}" reRender="frmPerJuriConve">
			          	 				<rich:componentControl for="pAgregarPerJuri" attachTo="btnAgregarPerJuriN1" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton id="btnAgregarConfigN1" value="#{estrucOrgController.strBtnAgregarN1}" actionListener="#{estrucOrgController.showConfigN1}" 
			          	 							reRender="pConfigN1,pProcesPlanillaN1,pgProcesPlanillaN1,pAdministraN1,pgAdministraN1,pCobChequesN1,pgCobChequesN1,btnAgregarConfigN1" 
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1"></a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
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
			          	 			<h:selectOneMenu style="width: 100px;" disabled="#{estrucOrgController.onNewDisabledInsti}"
					                  value="#{estrucOrgController.beanInsti.intParaEstadoCod}">
									  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									  <a4j:support ajaxSingle="true" event="onchange"></a4j:support>
									</h:selectOneMenu>
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
			          	 <h:panelGroup id="pProcesPlanillaN1" rendered="#{estrucOrgController.renderConfigN1}" layout="block" style="border: none">
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
				          	 <h:panelGroup id="pConfigProcesPlanillaN1" rendered="#{estrucOrgController.renderProcesPlanillaN1}" style="border: none">
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
								                 value="#{estrucOrgController.beanInstiPlanilla.intParaTipoSocioCod}">
												 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadPlanillaN1" style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiPlanilla.intParaModalidadCod}">
											  <f:selectItem itemValue="-1" itemLabel="Todos"/>
											  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalPlanillaN1" style="width: 100px;" 
												  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
								                  value="#{estrucOrgController.beanInstiPlanilla.intSeguSucursalPk}">
												  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
													itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
							                  	value="#{estrucOrgController.beanInstiPlanilla.intSeguSubSucursalPk}">
											  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								                <tumih:selectItems var="sel" value="#{estrucOrgController.listSubsucursalPlanilla}"
													itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	<h:panelGrid id="pg2ConfigPlanilaN1" columns="10">
					          	 		<rich:column width="90">
					          	 			<h:outputText value="Fecha Enviado"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiPlanilla.intDiaEnviado}" size="6"
					          	 						onkeydown="return validarEnteros(event)"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiPlanilla.intSaltoEnviado}">
											  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="100">
					          	 			<h:outputText value="Fecha efectuado"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiPlanilla.intDiaEfectuado}" size="6"
					          	 						onkeydown="return validarEnteros(event)"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiPlanilla.intSaltoEfectuado}">
											  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
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
					          	 			<a4j:commandButton id="btnAgregarPlanillaN1" value="Agregar" reRender="divProcesPlanillaN1"  
					          	 							   actionListener="#{estrucOrgController.agregarPlanillaInsti}" styleClass="btnEstilos">
					          	 			</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 	<h:panelGroup id="divProcesPlanillaN1" layout="block">
				          	 		<rich:extendedDataTable id="tblProcesPlanillaN1" enableContextMenu="false" 
				          	 				 rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}"
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}" style="margin:0 auto"
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="860px" height="186px"
									  		 onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaTipoSocioCod}"/>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaModalidadCod}"/>
				                                </rich:column>
				
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Enviado"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaEnviado}"></h:outputText>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intSaltoEnviado}"/>
				                                </rich:column>
				                                
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Efectuado"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaEfectuado}"></h:outputText>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intSaltoEfectuado}"/>
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
				                                    <tumih:outputText value="#{estrucOrgController.listJuridicaSucursal}" 
														itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
														property="#{item.intSeguSucursalPk}"/>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
													<tumih:outputText value="#{item.listaSubsucursal}" 
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
				                                </rich:column>
										        <f:facet name="footer">
													<h:panelGroup layout="block">
														<rich:datascroller for="tblProcesPlanillaN1" maxPages="10"/>
														<h:panelGroup layout="block" style="margin:0 auto; width:360px">
															<a4j:commandLink id="linkUpDelProcesPlanillaN1" action="#" 
																			oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblProcesPlanillaN1')">
																<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
															</a4j:commandLink>
															<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>
														</h:panelGroup>
													</h:panelGroup>
												</f:facet>
				                     </rich:extendedDataTable>
				          	 	</h:panelGroup>
							</h:panelGroup>
			          	 </h:panelGroup>
			          	 
			          	 <!--------------------------------------------- Administra Nivel 1 --------------------------------------------->	
			          	 <h:panelGroup id="pAdministraN1" rendered="#{estrucOrgController.renderConfigN1}" layout="block" style="border:none; margin-top:15px">
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
				          	 <h:panelGroup id="pConfigAdministraN1" rendered="#{estrucOrgController.renderAdministraN1}" style="border: none">
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
												 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadAdministraN1" style="width: 100px;"
							                  value="#{estrucOrgController.beanInstiAdministra.intParaModalidadCod}">
											  <f:selectItem itemValue="-1" itemLabel="Todos"/>
											  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalAdministraN1" style="width: 100px;"
												  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
								                  value="#{estrucOrgController.beanInstiAdministra.intSeguSucursalPk}">
												  <f:selectItem itemValue="-1" itemLabel="Todas las Sucursales"/>
								                  <f:selectItem itemValue="-2" itemLabel="Todas las Agencias"/>
								                  <f:selectItem itemValue="-3" itemLabel="Todas las Filiales"/>
								                  <f:selectItem itemValue="-4" itemLabel="Todas las Oficinas Principales"/>
												  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
													itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
								                <tumih:selectItems var="sel" value="#{estrucOrgController.listSubsucursalAdministra}"
													itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="2">
					          	 		<rich:column>
					          	 			<a4j:commandButton id="btnAgregarAdministraN1" value="Agregar" reRender="divAdministraN1"  
					          	 							   actionListener="#{estrucOrgController.agregarAdministraInsti}" styleClass="btnEstilos">
						          	 		</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer> 	 	
	
								<h:panelGroup id="divAdministraN1">
									<rich:extendedDataTable id="tblAdministraN1" enableContextMenu="false" style="margin:0 auto;"
				                             rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}"
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}" 
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="490px" height="186px"
									  		 onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaTipoSocioCod}"/>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaModalidadCod}"/>
				                                </rich:column>
				                                
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sucursal"></h:outputText>
				                                    </f:facet>
													<tumih:outputText value="#{estrucOrgController.listJuridicaSucursal}" 
														itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
														property="#{item.intSeguSucursalPk}"/>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
													<tumih:outputText value="#{item.listaSubsucursal}" 
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
				                                </rich:column>
										        <f:facet name="footer">
													<h:panelGroup layout="block">
														<rich:datascroller for="tblAdministraN1" maxPages="10"/> 
														<h:panelGroup layout="block" style="margin:0 auto; width:360px">
															<a4j:commandLink id="linkUpDelAdministraN1" action="#" 
																			oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblAdministraN1')">
																<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
															</a4j:commandLink>
															<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>
														</h:panelGroup>
													</h:panelGroup>
												</f:facet>
				                     </rich:extendedDataTable>
				          	 	</h:panelGroup>
							</h:panelGroup>
			          	 </h:panelGroup>
			          	 
			          	 <!--------------------------------------------- Cobranza de Cheques Nivel 1 --------------------------------------------->
			          	 <h:panelGroup id="pCobChequesN1" rendered="#{estrucOrgController.renderConfigN1}" layout="block" style="border:none; margin-top:10px">
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
				          	 <h:panelGroup id="pConfigCobChequesN1" rendered="#{estrucOrgController.renderCobChequesN1}" style="border: none">
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
												 	 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
													   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="65">
					          	 			<h:outputText value="Modalidad"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu id="cboModalidadCobraN1" style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiCobra.intParaModalidadCod}">
											  	<f:selectItem itemValue="0" itemLabel="Todos"/>
											  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 		<rich:column width="60">
					          	 			<h:outputText value="Sucursal"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column width="120px">
					          	 			<h:selectOneMenu id="cboSucursalCobraN1" style="width: 100px;"
												  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
								                  value="#{estrucOrgController.beanInstiCobra.intSeguSucursalPk}">
												  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
												  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
													itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
								                <tumih:selectItems var="sel" value="#{estrucOrgController.listSubsucursalCobra}"
													itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
											</h:selectOneMenu>
					          	 		</rich:column>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="10">
					          	 		<rich:column width="110">
					          	 			<h:outputText value="Fecha de Cheque"></h:outputText>
					          	 		</rich:column>
					          	 		<rich:column>
					          	 			<h:inputText value="#{estrucOrgController.beanInstiCobra.intDiaCheque}" size="6"
					          	 						onkeydown="return validarEnteros(event)"></h:inputText>
					          	 		</rich:column>
					          	 		<rich:column width="120">
					          	 			<h:selectOneMenu style="width: 100px;"
							                  	value="#{estrucOrgController.beanInstiCobra.intSaltoCheque}">
											  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
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
					          	 			<a4j:commandButton id="btnAgregarCobraN1" value="Agregar" reRender="divCobChequeN1"  
					          	 							   actionListener="#{estrucOrgController.agregarCobraInsti}" styleClass="btnEstilos">
						          	 		</a4j:commandButton>
					          	 		</rich:column>
					          	 	</h:panelGrid>
				          	 	</a4j:region>
				          	 	<rich:spacer height="8px"></rich:spacer>
				          	 	
				          	 	<h:panelGroup id="divCobChequeN1" layout="block">
				          	 		<rich:extendedDataTable id="tblCobChequeN1" enableContextMenu="false" sortMode="single" 
				                             rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}"
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}" 
									  		 rowKeyVar="rowKey" rows="5" width="764px" height="186px" style="margin:0 auto"
									  		 onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">
				                                
									  		 	<rich:column width="29px">
				                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                                </rich:column>
									  		 
				                                <rich:column width="110">
				                                    <f:facet name="header">
				                                        <h:outputText  value="Tipo de Socio"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaTipoSocioCod}"/>
				                                </rich:column>
				
				                                <rich:column>
				                                    <f:facet name="header">
				                                        <h:outputText value="Modalidad"></h:outputText>
				                                    </f:facet>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intParaModalidadCod}"/>
				                                </rich:column>
				
				                                <rich:column width="165">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Cobro de Cheque"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaCheque}  "/>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}"
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intSaltoCheque}"/>
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
													<tumih:outputText value="#{estrucOrgController.listJuridicaSucursal}"
														itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
														property="#{item.intSeguSucursalPk}"/>
				                                </rich:column>
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Sub Sucursal"></h:outputText>
				                                    </f:facet>
													<tumih:outputText value="#{item.listaSubsucursal}"
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
				                                </rich:column>
										        <f:facet name="footer">
													<h:panelGroup layout="block">
														<rich:datascroller for="tblCobChequeN1" maxPages="10"/>
														<h:panelGroup layout="block" style="margin:0 auto; width:360px">
															<a4j:commandLink id="linkUpDelCobChequeN1" action="#" 
																			oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblCobChequeN1')">
																<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
															</a4j:commandLink>
															<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>
														</h:panelGroup>
													</h:panelGroup>
												</f:facet>
				                     </rich:extendedDataTable>
				                </h:panelGroup>
				          	 </h:panelGroup>
			          	 </h:panelGroup>
			          </rich:panel>
			          	 	
			       </rich:panel>
		       </rich:panel>
			</rich:panel>
   </h:form>