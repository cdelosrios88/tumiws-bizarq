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
	
    <h:form id="frmEstrucOrg">
         	<rich:panel style="border:1px solid #17356f">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder">
         		<a4j:region id="rgBusqEstructura">
         			<h:panelGrid columns="4">
         				<rich:column><h:outputText value="Institución"/></rich:column>
                        <rich:column>
							<h:selectOneMenu valueChangeListener="#{estrucOrgController.getListUnidadEjecutora}" style="width: 200px;">
								<f:selectItem itemValue="-1" itemLabel="Todos"/>
								<tumih:selectItems var="sel" value="#{estrucOrgController.listInstitucion}"
									itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}"/>
								<a4j:support event="onchange" reRender="cboUniEjecu"></a4j:support>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column><h:outputText value="Unidad Ejecutora"/></rich:column>
                        <rich:column>
							<h:selectOneMenu id="cboUniEjecu" style="width: 200px;"
											value="#{estrucOrgController.intCboUnidadEjecutora}">
								<f:selectItem itemValue="-1" itemLabel="Todos"/>
								<tumih:selectItems var="sel" value="#{estrucOrgController.listUnidadEjecutora}"
									itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}"/>
							</h:selectOneMenu>
                        </rich:column>
         			</h:panelGrid>
         			<h:panelGrid columns="10" border="0">
                        <rich:column width="25"><h:selectBooleanCheckbox value="#{estrucOrgController.blnCheckNombreEntidad}"/></rich:column>
                        <rich:column width="55"><h:outputText value="Nombre"/></rich:column>
                        <rich:column width="220">
                        	<h:inputText value="#{estrucOrgController.strNombreEntidad}" size="35"/>
                        </rich:column>
                        <rich:column width="90"><h:outputText value="Configuración"/></rich:column>
                    	<rich:column>
							<h:selectOneMenu id="cboConfigEstruc" style="width: 100px;"
			                     value="#{estrucOrgController.intCboConfigEstructura}">
			                     <f:selectItem itemValue="-1" itemLabel="Todos"/>
							     <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CASOESTRUCTURA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="15">
                    	<rich:column width="100"><h:outputText value="Tipo de Socio"/></rich:column>
                    	<rich:column>
                        	<h:selectOneMenu id="cboTipoSocioEstruc" style="width: 100px;"
				                 value="#{estrucOrgController.intCboTipoSocio}">
				                 <f:selectItem itemValue="-1" itemLabel="Todos"/>
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
				                  <f:selectItem itemValue="-1" itemLabel="Todos"/>
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
                        <rich:column>
		                    <h:inputText value="#{estrucOrgController.intFechaDesde}" size="10"></h:inputText>
		                </rich:column>
		                <rich:column>
		                    <h:inputText value="#{estrucOrgController.intFechaHasta}" size="10"></h:inputText>
		                </rich:column>
                    </h:panelGrid>
                    <rich:spacer height="6px"/>
                    
                    <h:panelGrid columns="10" border="0">
                    	<rich:column width="95"><h:outputText value="Sucursal"/></rich:column>
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
                        <a4j:commandButton value="Buscar" actionListener="#{estrucOrgController.buscarDependencia}" reRender="tblEstrucOrg" styleClass="btnEstilos"/>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    
                    <rich:spacer height="4px"/><rich:spacer/>
                    <rich:spacer height="4px"/><rich:spacer/>
                    <h:panelGrid>
	          	 	<rich:extendedDataTable id="tblEstrucOrg" enableContextMenu="false" sortMode="single" 
                             var="item" value="#{estrucOrgController.beanListDependencia}"  
					  		 rowKeyVar="rowKey" rows="5" width="900px" height="186px" 
					  		 onRowClick="jsSelectEstructura('#{item.estructura.id.intCodigo},#{item.estructura.id.intNivel}');">
                                
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
		                 <a4j:commandButton value="Nuevo" actionListener="#{estrucOrgController.showFormDependencia}" styleClass="btnEstilos" reRender="pFormEstruc"/>                     
		                 <a4j:commandButton value="Guardar" actionListener="#{estrucOrgController.grabarDependencia}" styleClass="btnEstilos" reRender="pFormEstruc,tblEstrucOrg"/>												                 
		                 <a4j:commandButton value="Cancelar" actionListener="#{estrucOrgController.hideFormDependencia}" styleClass="btnEstilos" reRender="pFormEstruc"/>
		          	 </h:panelGrid>
		          	 
			         <rich:panel id="pEstrucOrg" rendered="#{estrucOrgController.blnShowFormDependencia}" style="border:1px solid #17356f;background-color:#DEEBF5;">
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
			          	 			<h:selectOneMenu valueChangeListener="#{estrucOrgController.getListUnidadEjecutora}" style="width: 225px;">
										<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
										<tumih:selectItems var="sel" value="#{estrucOrgController.listInstitucion}"
											itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}"/>
										<a4j:support event="onchange" reRender="cboUnidadEjecutora"></a4j:support>
									</h:selectOneMenu>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton value="#{estrucOrgController.strBtnVerConfigN1}" actionListener="#{estrucOrgController.verConfigN1}" 
			          	 							reRender="pgRazonSocN1,pConfigN1,pProcesPlanillaN1,pAdministraN1,pCobChequesN1"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1"></a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	
			          <!--------------------------------------------- Configuracion Nivel 1 --------------------------------------------->
			          <rich:panel id="pConfigN1" style="border: none">
			          	 
			          	 <!--------------------------------------------- Procesar Planilla Nivel 1 --------------------------------------------->
			          	 <rich:panel id="pProcesPlanillaN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none">
				          	 <!--------------------------------------------- Configuracion Procesar Planilla Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigProcesPlanillaN1" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/>
				          	 	
				          	 	<h:panelGrid>
				          	 		<rich:extendedDataTable id="tblProcesPlanillaN1" enableContextMenu="false" 
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}" style="margin:0 auto"
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="860px" height="165px">
				                                
				                             <f:facet name="header">
				                             	<h:outputText value="Procesar Planilla"></h:outputText>
				                             </f:facet>
				                             
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
				                                    <h:outputText value="#{item.intDiaEnviado}  "></h:outputText>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
														itemValue="intIdDetalle" itemLabel="strDescripcion" 
														property="#{item.intDiaEnviado}"/>
				                                </rich:column>
				                                
				                                <rich:column width="130">
				                                    <f:facet name="header">
				                                        <h:outputText value="Fecha Efectuado"></h:outputText>
				                                    </f:facet>
				                                    <h:outputText value="#{item.intDiaEfectuado}  "></h:outputText>
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
													<tumih:outputText value="#{item.intSeguSubSucursalPk}"
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSucursalPk}"/>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblProcesPlanillaN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
				          	 	</h:panelGrid>
				          	 	<rich:spacer height="8px"></rich:spacer>
							</rich:panel>
			          	 </rich:panel>
			          	 
			          	 <!--------------------------------------------- Administra Nivel 1 --------------------------------------------->	
			          	 <rich:panel id="pAdministraN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none;">
				          	 <!--------------------------------------------- Configuracion Administra Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigAdministraN1" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/> 	 	
	
									<rich:extendedDataTable id="tblAdministraN1" enableContextMenu="false" style="margin:0 auto;"
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}" 
									  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="490px" height="165px">
									  		 
									  		 <f:facet name="header">
				                             	<h:outputText value="Administra"></h:outputText>
				                             </f:facet>
				                                
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
				                                    <h:selectOneMenu value="#{item.intSeguSubSucursalPk}" style="width: 100px;">
													  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
										                <tumih:selectItems var="sel" value="#{item.listaSubsucursal}"
															itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
													</h:selectOneMenu>
													<tumih:outputText value="#{item.listaSubsucursal}" 
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblAdministraN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
							</rich:panel>
			          	 </rich:panel>
			          	 
			          	 <!--------------------------------------------- Cobranza de Cheques Nivel 1 --------------------------------------------->
			          	 <rich:panel id="pCobChequesN1" rendered="#{estrucOrgController.renderConfigN1}" style="border: none">
				          	 <!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 1 --------------------------------------------->
				          	 <rich:panel id="pConfigCobChequesN1" style="border: none">
				          	 	<rich:spacer height="8px"/>
				          	 	<rich:separator></rich:separator>
				          	 	<rich:spacer height="8px"/>
				          	 	
				          	 		<rich:extendedDataTable id="tblCobChequeN1" enableContextMenu="false" sortMode="single" 
				                             var="item" value="#{estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}" 
									  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto">
				                             
				                             <f:facet name="header">
				                             	<h:outputText value="Cobranza de Cheques"></h:outputText>
				                             </f:facet>
				                             
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
				                                    <h:outputText value="#{item.intDiaCheque}"></h:outputText>
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
														property="#{item.intSeguSucursalPk}"/>
				                                </rich:column>
												<f:facet name="footer">   
										        	<rich:datascroller for="tblCobChequeN1" maxPages="10"/>   
										        </f:facet>
				                     </rich:extendedDataTable>
				          	 	<rich:spacer height="8px"></rich:spacer>
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
			          	 			<h:selectOneMenu id="cboUnidadEjecutora" style="width: 225px;"
			          	 							value="#{estrucOrgController.beanDepen.intIdCodigoRel}">
										<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
										<tumih:selectItems var="sel" value="#{estrucOrgController.listUnidadEjecutora}"
											itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}"/>
									</h:selectOneMenu>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton value="#{estrucOrgController.strBtnVerConfigN2}" actionListener="#{estrucOrgController.verConfigN2}" 
			          	 							reRender="pgRazonSocN2,pConfigN2,pProcesPlanillaN2,pgProcesPlanillaN2,pAdministraN2,pgAdministraN2,pCobChequesN2,pgCobChequesN2"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 
			          	 <!--------------------------------------------- Configuracion Nivel 2 --------------------------------------------->
			          	 <rich:panel id="pConfigN2" style="border: none">
			          	 
				          	 <!--------------------------------------------- Procesar Planilla Nivel 2 --------------------------------------------->
				          	 <rich:panel id="pProcesPlanillaN2" rendered="#{estrucOrgController.renderConfigPlanillaN2}" style="border: none">
					          	 <!--------------------------------------------- Configuracion Procesar Planilla Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigProcesPlanillaN2" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	
					          	 	<h:panelGrid>
					          	 		<rich:extendedDataTable id="tblProcesPlanillaN2" enableContextMenu="false" 
					                             var="item" value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetallePlanilla}" style="margin:0 auto"
										  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="860px" height="165px">
					                                
					                             <f:facet name="header">
			                                        <h:outputText  value="Procesar Planilla"></h:outputText>
			                                    </f:facet>
					                                    
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
														<tumih:outputText cache="#{item.listaSubsucursal}" 
																		itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
																		property="#{item.intSeguSucursalPk}"/>
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
					                                    <h:outputText value="#{item.intDiaEnviado}  "></h:outputText>
														<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}"
																		itemValue="intIdDetalle" itemLabel="strDescripcion" 
																		property="#{item.intSaltoEnviado}"/>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Fecha Efectuado"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intDiaEfectuado}  "></h:outputText>
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
											        	<rich:datascroller for="tblProcesPlanillaN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Administra Nivel 2 --------------------------------------------->	
				          	 <rich:panel id="pAdministraN2" rendered="#{estrucOrgController.renderConfigAdministraN2}" style="border: none;">
					          	 <!--------------------------------------------- Configuracion Administra Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigAdministraN2" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>	 	
		
										<rich:extendedDataTable id="tblAdministraN2" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetalleAdministra}"   
										  		 rowKeyVar="rowKey" rows="5" width="490px" height="165px" style="margin:0 auto;">
					                                
					                            <f:facet name="header">
			                                        <h:outputText  value="Administra"></h:outputText>
			                                    </f:facet>
					                             
										  		 	<rich:column width="29px">
					                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
					                                </rich:column>
										  		 
					                                <rich:column width="110">
					                                    <f:facet name="header">
					                                        <h:outputText  value="Tipo de Socio"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaTipoSocioCod}"></h:outputText>
														<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
																		itemValue="intIdDetalle" itemLabel="strDescripcion" 
																		property="#{item.intParaTipoSocioCod}"/>
					                                </rich:column>
					
					                                <rich:column>
					                                    <f:facet name="header">
					                                        <h:outputText value="Modalidad"></h:outputText>
					                                    </f:facet>
					                                    <h:outputText value="#{item.intParaModalidadCod}"></h:outputText>
														<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
																		itemValue="intIdDetalle" itemLabel="strDescripcion" 
																		property="#{item.intParaModalidadCod}"/>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:selectOneMenu value="#{item.intSeguSucursalPk}" style="width: 100px;">
															  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
															  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
																itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
														</h:selectOneMenu>
														<tumih:outputText value="#{estrucOrgController.listJuridicaSucursal}"
																		itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
																		property="#{item.intSeguSucursalPk}"/>
					                                </rich:column>
					                                
					                                <rich:column width="130">
					                                    <f:facet name="header">
					                                        <h:outputText value="Sub Sucursal"></h:outputText>
					                                    </f:facet>
					                                    <h:selectOneMenu style="width: 100px;" value="#{item.intSeguSubSucursalPk}">
														  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
											                <tumih:selectItems var="sel" value="#{item.listaSubsucursal}"
																itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
														</h:selectOneMenu>
														<tumih:outputText value="#{item.listaSubsucursal}"
																		itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
																		property="#{item.intSeguSubSucursalPk}"/>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblAdministraN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
								</rich:panel>
				          	 </rich:panel>
				          	 
				          	 <!--------------------------------------------- Cobranza de Cheques Nivel 2 --------------------------------------------->
				          	 <rich:panel id="pCobChequesN2" rendered="#{estrucOrgController.renderConfigCobranzaN2}" style="border: none">
					          	 <!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 2 --------------------------------------------->
					          	 <rich:panel id="pConfigCobChequesN2" style="border: none">
					          	 	<rich:spacer height="8px"/>
					          	 	<rich:separator></rich:separator>
					          	 	<rich:spacer height="8px"/>
					          	 	
					          	 		<rich:extendedDataTable id="tblCobChequeN2" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetalleCobranza}" 
										  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto">
					                             
					                             <f:facet name="header">
			                                        <h:outputText  value="Cobro Cheques"></h:outputText>
			                                    </f:facet>
					                             
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
					                                    <h:outputText value="#{item.intDiaCheque}  "></h:outputText>
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
					                                    <h:selectOneMenu style="width: 100px;" value="#{item.intSeguSubSucursalPk}">
														  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
											                <tumih:selectItems var="sel" value="#{item.listaSubsucursal}"
																itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
														</h:selectOneMenu>
														<tumih:outputText value="#{item.listaSubsucursal}"
																		itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
																		property="#{item.intSeguSubSucursalPk}"/>
					                                </rich:column>
													<f:facet name="footer">   
											        	<rich:datascroller for="tblCobChequeN2" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	<rich:spacer height="8px"></rich:spacer>
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
			          	 			<h:inputText value="#{perJuridicaController.beanPerJuridicaN3.juridica.strRazonSocial}" size="40" disabled="true"/>
			          	 		</rich:column>
			          	 		<rich:column width="160">
			          	 			<a4j:commandButton id="btnAgregarPerJuriN3" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
			          	 							   actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}" reRender="frmPerJuriConve,mpMessage"
			          	 							   oncomplete="if(#{estrucOrgController.beanDepen.intIdCodigoRel==null || estrucOrgController.beanDepen.intIdCodigoRel==0}){Richfaces.showModalPanel('mpMessage')}
																	else{Richfaces.showModalPanel('pAgregarPerJuri')}">
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<a4j:commandButton value="#{estrucOrgController.strBtnAgregarN3}" actionListener="#{estrucOrgController.showConfigN3}" 
			          	 							reRender="pgRazonSocN3,pConfigN3,pProcesPlanillaN3,pgProcesPlanillaN3,pAdministraN3,pgAdministraN3,pCobChequesN3,pgCobChequesN3"
			          	 							ajaxSingle="true" immediate="true" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 	
			          	 	<h:panelGrid columns="3">
			          	 		<rich:column width="110">
			          	 			<h:outputText value="Estado de Entidad"></h:outputText>
			          	 		</rich:column>
			          	 		<rich:column>
			          	 			<h:selectOneMenu style="width: 100px;" disabled="#{estrucOrgController.onNewDisabledDepen}"
					                  				value="#{estrucOrgController.beanDepen.intParaEstadoCod}">
										<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									  	<a4j:support ajaxSingle="true" event="onchange"></a4j:support>
									</h:selectOneMenu>
			          	 		</rich:column>
			          	 	</h:panelGrid>
			          	 	<rich:spacer height="8px"/>
			          	 
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
													 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
														itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu id="cboModalidadPlanillaN3" style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenPlanilla.intParaModalidadCod}">
												  <f:selectItem itemValue="0" itemLabel="Todos"/>
												  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
												<h:selectOneMenu id="cboSucursalPlanillaN3" style="width: 100px;" 
													  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
									                  value="#{estrucOrgController.beanDepenPlanilla.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
													  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
														itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
									                <tumih:selectItems var="sel" value="#{estrucOrgController.listSubsucursalPlanilla}"
														itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	<h:panelGrid columns="10">
						          	 		<rich:column width="90">
						          	 			<h:outputText value="Fecha Enviado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanDepenPlanilla.intDiaEnviado}" size="6"
						          	 						onkeydown="return validarEnteros(event)"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenPlanilla.intSaltoEnviado}">
												  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
														itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="100">
						          	 			<h:outputText value="Fecha efectuado"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column>
						          	 			<h:inputText value="#{estrucOrgController.beanDepenPlanilla.intDiaEfectuado}" size="6"
						          	 						onkeydown="return validarEnteros(event)"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenPlanilla.intSaltoEfectuado}">
												  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
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
						          	 			<a4j:commandButton id="btnAgregarPlanillaN3" value="Agregar" actionListener="#{estrucOrgController.agregarPlanillaDepen}"
						          	 						   reRender="tblProcesPlanillaN3" styleClass="btnEstilos">
						          	 			</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid>
					          	 		<rich:extendedDataTable id="tblProcesPlanillaN3" enableContextMenu="false" sortMode="single"
					                             var="item" value="#{estrucOrgController.beanDepen.listaEstructuraDetallePlanilla}" 
										  		 rowKeyVar="rowKey" rows="5" width="860px" height="165px" style="margin:0 auto"
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
					                                    <h:outputText value="#{item.intDiaEnviado}  "></h:outputText>
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
											        	<rich:datascroller for="tblProcesPlanillaN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	</h:panelGrid>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGrid columns="2" style="margin:0 auto">
										<a4j:commandLink id="linkUpDelProcesPlanillaN3" action="#" 
														oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblProcesPlanillaN3')">
											<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
										</a4j:commandLink>
										<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>                                     
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
													 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
														itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu id="cboModalidadAdministraN3" style="width: 100px;"
								                  value="#{estrucOrgController.beanDepenAdministra.intParaModalidadCod}">
												  <f:selectItem itemValue="0" itemLabel="Todos"/>
												  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
												<h:selectOneMenu id="cboSucursalAdministraN3" style="width: 100px;"
													  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
									                  value="#{estrucOrgController.beanDepenAdministra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
													  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
														itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
								                  	value="#{estrucOrgController.beanDepenAdministra.intSeguSubSucursalPk}">
												  	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
									                <tumih:selectItems var="sel" value="#{estrucOrgController.listSubsucursalAdministra}"
														itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	 	<rich:spacer height="8px"></rich:spacer>
						          	 	
						          	 	<h:panelGrid columns="2">
						          	 		<rich:column>
						          	 			<a4j:commandButton id="btnAgregarAdministraN3" value="Agregar" actionListener="#{estrucOrgController.agregarAdministraDepen}"
							          	 						   reRender="tblAdministraN3" styleClass="btnEstilos">
							          	 		</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
						          	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer> 	 	
		
										<rich:extendedDataTable id="tblAdministraN3" enableContextMenu="false" sortMode="single" 
					                             var="item" value="#{estrucOrgController.beanDepen.listaEstructuraDetalleAdministra}" 
										  		 rowKeyVar="rowKey" rows="5" width="490px" height="165px" style="margin:0 auto;"
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
											        	<rich:datascroller for="tblAdministraN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	
					          	 	<h:panelGroup layout="block" style="border: none; margin:0 auto; width: 480px;">
						          	 	<h:panelGrid columns="2">
											<a4j:commandLink id="linkUpDelAdministraN3" action="#" 
															oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblAdministraN3')">
												<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
											</a4j:commandLink>
											<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>                                     
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
												 	 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
													   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="65">
						          	 			<h:outputText value="Modalidad"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu id="cboModalidadCobraN3" style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenCobra.intParaModalidadCod}">
												  	<f:selectItem itemValue="0" itemLabel="Todos"/>
												  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
														itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
												</h:selectOneMenu>
						          	 		</rich:column>
						          	 		<rich:column width="60">
						          	 			<h:outputText value="Sucursal"></h:outputText>
						          	 		</rich:column>
						          	 		<rich:column width="120px">
												<h:selectOneMenu id="cboSucursalCobraN3" style="width: 100px;"
													  valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
									                  value="#{estrucOrgController.beanDepenCobra.intSeguSucursalPk}">
													  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
													  <tumih:selectItems var="sel" value="#{estrucOrgController.listJuridicaSucursal}"
														itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
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
						          	 			<h:inputText value="#{estrucOrgController.beanDepenCobra.intDiaCheque}" size="6"
						          	 						onkeydown="return validarEnteros(event)"></h:inputText>
						          	 		</rich:column>
						          	 		<rich:column width="120">
												<h:selectOneMenu style="width: 100px;"
								                  	value="#{estrucOrgController.beanDepenCobra.intSaltoCheque}">
												  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}" 
														itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
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
						          	 			<a4j:commandButton id="btnAgregarCobraN3" value="Agregar" actionListener="#{estrucOrgController.agregarCobraDepen}"
							          	 						   reRender="tblCobChequeN3" styleClass="btnEstilos">
							          	 		</a4j:commandButton>
						          	 		</rich:column>
						          	 	</h:panelGrid>
					          	 	</a4j:region>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 		<rich:extendedDataTable id="tblCobChequeN3" enableContextMenu="false" sortMode="single" 
					                             var="item" value="#{estrucOrgController.beanDepen.listaEstructuraDetalleCobranza}" 
										  		 rowKeyVar="rowKey" rows="5" width="764px" height="165px" style="margin:0 auto"
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
					                                    <h:outputText value="#{item.intDiaCheque}  "></h:outputText>
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
											        	<rich:datascroller for="tblCobChequeN3" maxPages="10"/>   
											        </f:facet>
					                     </rich:extendedDataTable>
					          	 	<rich:spacer height="8px"></rich:spacer>
					          	 	
					          	 	<h:panelGroup layout="block" style="margin:0 auto; width: 480px">
					          	 		<h:panelGrid columns="2">
											<a4j:commandLink id="linkUpDelCobChequeN3" action="#" 
															oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblCobChequeN3')">
												<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
											</a4j:commandLink>
											<h:outputText value="Para Eliminar seleccionar una fila y hacer click en el botón." style="color:#8ca0bd"/>                                     
									    </h:panelGrid>
					          	 	</h:panelGroup>
					          	 </rich:panel>
				          	 </rich:panel>
				          </rich:panel>
			          	 	
			       </rich:panel>
		       </rich:panel>
			</rich:panel>
		</rich:panel>
   </h:form>