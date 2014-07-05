<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         -->
<!-- Autor     : Christian De los Ríos    -->
<!-- Modulo    : Créditos                 -->
<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->
<!-- Fecha     : 27/12/2011               -->

<h:form id="frmUnidadEjecutora">
	<rich:panel style="border:1px solid #17356f">
		<rich:panel
			style="border: 0px solid #17356f;background-color:#DEEBF5;"
			styleClass="rich-tabcell-noborder">
			<a4j:region id="rgBusqEstructura">
				<h:panelGrid columns="10" border="0">
					<rich:column width="100">
						<h:outputText value="Intitución" />
					</rich:column>
					<rich:column>
						<h:selectOneMenu style="width: 200px;"
							value="#{estrucOrgController.intCboInstitucion}">
							<f:selectItem itemValue="-1" itemLabel="Todos" />
							<tumih:selectItems var="sel"
								value="#{estrucOrgController.listInstitucion}"
								itemValue="#{sel.id.intCodigo}"
								itemLabel="#{sel.juridica.strRazonSocial}" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="25">
						<h:selectBooleanCheckbox
							value="#{estrucOrgController.blnCheckNombreEntidad}" />
					</rich:column>
					<rich:column width="55">
						<h:outputText value="Nombre" />
					</rich:column>
					<rich:column width="220">
						<h:inputText value="#{estrucOrgController.strNombreEntidad}"
							size="35" />
					</rich:column>
					<rich:column width="90">
						<h:outputText value="Configuración" />
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="cboConfigEstruc" style="width: 100px;"
							value="#{estrucOrgController.intCboConfigEstructura}">
							<f:selectItem itemValue="-1" itemLabel="Todos" />
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_CASOESTRUCTURA}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
								propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="6px" />

				<h:panelGrid columns="15">
					<rich:column width="100">
						<h:outputText value="Tipo de Socio" />
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="cboTipoSocioEstruc" style="width: 100px;"
							value="#{estrucOrgController.intCboTipoSocio}">
							<f:selectItem itemValue="-1" itemLabel="Todos" />
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
								propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>

					<rich:column width="110">
						<h:outputText style="padding-left: 15px" value="Modalidad" />
					</rich:column>
					<rich:column width="114">
						<h:selectOneMenu id="cboModalidadEstruc" style="width: 100px;"
							value="#{estrucOrgController.intCboModalidadEstructura}">
							<f:selectItem itemValue="-1" itemLabel="Todos" />
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
								propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>

					<rich:column width="25">
						<h:selectBooleanCheckbox
							value="#{estrucOrgController.blnCheckFechas}" />
					</rich:column>
					<rich:column width="50">
						<h:outputText value="Fecha" />
					</rich:column>
					<rich:column width="110">
						<h:selectOneMenu style="width: 100px;"
							value="#{estrucOrgController.intCboEstadoPlanilla}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.." />
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_ESTADOPLANILLA}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:inputText value="#{estrucOrgController.intFechaDesde}"
							size="10"></h:inputText>
					</rich:column>
					<rich:column>
						<h:inputText value="#{estrucOrgController.intFechaHasta}"
							size="10"></h:inputText>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="6px" />

				<h:panelGrid columns="10" border="0">
					<rich:column width="95">
						<h:outputText value="Sucursal" />
					</rich:column>
					<rich:column>
						<h:selectOneMenu id="cboSucursalBusqueda" style="width: 100px;"
							valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
							value="#{estrucOrgController.intCboSucursales}">
							<f:selectItem itemValue="-1" itemLabel="Todas las Sucursales" />
							<f:selectItem itemValue="-2" itemLabel="Todas las Agencias" />
							<f:selectItem itemValue="-3" itemLabel="Todas las Filiales" />
							<f:selectItem itemValue="-4"
								itemLabel="Todas las Oficinas Principales" />
							<tumih:selectItems var="sel"
								value="#{estrucOrgController.listJuridicaSucursal}"
								itemValue="#{sel.id.intIdSucursal}"
								itemLabel="#{sel.juridica.strRazonSocial}" />
							<a4j:support event="onchange" reRender="cboEstrucSubSucur"
								ajaxSingle="true"></a4j:support>
						</h:selectOneMenu>
					</rich:column>

					<rich:column width="110">
						<h:outputText style="padding-left: 15px" value="Sub-Sucursal" />
					</rich:column>
					<rich:column width="115">
						<h:selectOneMenu id="cboEstrucSubSucur" style="width: 100px;"
							value="#{estrucOrgController.intCboSubsucursales}">
							<f:selectItem itemValue="-1" itemLabel="Todos" />
							<tumih:selectItems var="sel"
								value="#{controllerFiller.cboSubSucursales}"
								itemValue="#{sel.intIdSubSucursal}" itemLabel="#{sel.strNombre}" />
						</h:selectOneMenu>
					</rich:column>

					<rich:column width="25">
						<h:selectBooleanCheckbox
							value="#{estrucOrgController.blnCheckCodigo}" />
					</rich:column>
					<rich:column width="50">
						<h:outputText value="Código" />
					</rich:column>
					<rich:column width="220">
						<h:inputText value="#{estrucOrgController.strCodigoExterno}"
							size="35" />
					</rich:column>
					<a4j:commandButton value="Buscar" 
						actionListener="#{estrucOrgController.buscarUnidadEjecutora}"
						reRender="divEstructuraBusq" styleClass="btnEstilos" />
				</h:panelGrid>
				<rich:spacer height="10px" />

				<h:panelGroup id="divEstructuraBusq" layout="block">
					<h:panelGrid>
						<rich:extendedDataTable id="tblEstrucOrg" enableContextMenu="false" sortMode="single" var="item"
							rendered="#{not empty estrucOrgController.beanListUnidadEjecutora}"
							value="#{estrucOrgController.beanListUnidadEjecutora}"
							rowKeyVar="rowKey" rows="5" width="900px" height="186px"
							onRowClick="jsSelectEstructura('#{item.estructura.id.intCodigo},#{item.estructura.id.intNivel}');">
	
							<rich:column width="29px">
								<h:outputText value="#{rowKey + 1}"></h:outputText>
							</rich:column>
	
							<rich:column width="90">
								<f:facet name="header">
									<h:outputText value="Código"></h:outputText>
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
									<rich:datascroller for="tblEstrucOrg" maxPages="10" />
									<h:panelGroup layout="block" style="margin:0 auto; width:480px">
										<a4j:commandLink action="#"
											oncomplete="Richfaces.showModalPanel('mpUpDelEstrucOrg')">
											<h:graphicImage value="/images/icons/mensaje1.jpg"
												style="border:0px" />
										</a4j:commandLink>
										<h:outputText style="color:#8ca0bd"
											value="Para Eliminar o Modificar seleccionar una fila y presionar hacer click en el botón."/>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
						</rich:extendedDataTable>
					</h:panelGrid>
				</h:panelGroup>
			</a4j:region>
		</rich:panel>

		<rich:spacer height="8px" />
		<rich:panel id="pFormEstruc" style="border:none">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo"
					actionListener="#{estrucOrgController.showFormUnidadEjecutora}"
					styleClass="btnEstilos" reRender="pFormEstruc" />
				<a4j:commandButton value="Guardar"
					actionListener="#{estrucOrgController.grabarUnidadEjecutora}"
					styleClass="btnEstilos" reRender="pFormEstruc,tblEstrucOrg" />
				<a4j:commandButton value="Cancelar"
					actionListener="#{estrucOrgController.hideFormUnidadEjecutora}"
					styleClass="btnEstilos" reRender="pFormEstruc" />
			</h:panelGrid>

			<rich:panel id="pEstrucOrg"
				rendered="#{estrucOrgController.blnShowFormUnidadEjecutora}"
				style="border:1px solid #17356f;background-color:#DEEBF5;">

				<!--------------------------------------------- NIVEL 1: Instititucion --------------------------------------------->
				<h:panelGrid>
					<h:outputText value="Nivel 1: Institución"
						styleClass="estiloLetra1"></h:outputText>
				</h:panelGrid>
				<rich:spacer height="8px" />

				<h:panelGrid id="pgRazonSocN1" columns="6">
					<rich:column width="110">
						<h:outputText value="Nombre"></h:outputText>
					</rich:column>
					<rich:column width="240">
						<h:selectOneMenu style="width: 225px;" disabled="#{estrucOrgController.blnCboInstitucionDisabled}"
							value="#{estrucOrgController.beanUniEjecu.intIdCodigoRel}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.." />
							<tumih:selectItems var="sel"
								value="#{estrucOrgController.listInstitucion}"
								itemValue="#{sel.id.intCodigo}"
								itemLabel="#{sel.juridica.strRazonSocial}" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<a4j:commandButton
							value="#{estrucOrgController.strBtnVerConfigN1}"
							actionListener="#{estrucOrgController.verConfigN1}"
							reRender="pgRazonSocN1,pConfigN1,pProcesPlanillaN1,pAdministraN1,pCobChequesN1"
							ajaxSingle="true" immediate="true" styleClass="btnEstilos1"></a4j:commandButton>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="8px" />

				<!--------------------------------------------- Configuracion Nivel 1 --------------------------------------------->
				<rich:panel id="pConfigN1" style="border: none">

					<!--------------------------------------------- Procesar Planilla Nivel 1 --------------------------------------------->
					<h:panelGroup id="pProcesPlanillaN1"
						rendered="#{estrucOrgController.renderConfigN1}"
						style="border: none">
						<!--------------------------------------------- Configuracion Procesar Planilla Nivel 1 --------------------------------------------->
						<h:panelGroup id="pConfigProcesPlanillaN1" style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />

							<h:panelGrid>
								<rich:extendedDataTable id="tblProcesPlanillaN1" enableContextMenu="false" 
									rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}"
									value="#{estrucOrgController.beanInsti.listaEstructuraDetallePlanilla}"
									var="item" rowKeyVar="rowKey" rows="5" style="margin:0 auto"
									sortMode="single" width="860px" height="186px">

									<f:facet name="header">
										<h:outputText value="Procesar Planilla"></h:outputText>
									</f:facet>

									<rich:column width="29px">
										<h:outputText value="#{rowKey + 1}"></h:outputText>
									</rich:column>

									<rich:column width="110">
										<f:facet name="header">
											<h:outputText value="Tipo de Socio"></h:outputText>
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
														itemValue="id.intIdSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
									</rich:column>
									<f:facet name="footer">
										<rich:datascroller for="tblProcesPlanillaN1" maxPages="10" />
									</f:facet>
								</rich:extendedDataTable>
							</h:panelGrid>
							<rich:spacer height="8px"></rich:spacer>
						</h:panelGroup>
					</h:panelGroup>

					<!--------------------------------------------- Administra Nivel 1 --------------------------------------------->
					<h:panelGroup id="pAdministraN1"
						rendered="#{estrucOrgController.renderConfigN1}"
						style="border: none; margin-top:15px">
						<!--------------------------------------------- Configuracion Administra Nivel 1 --------------------------------------------->
						<h:panelGroup id="pConfigAdministraN1" style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />

							<rich:extendedDataTable id="tblAdministraN1" enableContextMenu="false" style="margin:0 auto"
								rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}" 
								value="#{estrucOrgController.beanInsti.listaEstructuraDetalleAdministra}"
								var="item" rowKeyVar="rowKey" rows="5" sortMode="single" width="490px" height="186px">

								<f:facet name="header">
									<h:outputText value="Administra"></h:outputText>
								</f:facet>

								<rich:column width="29px">
									<h:outputText value="#{rowKey + 1}"></h:outputText>
								</rich:column>

								<rich:column width="110">
									<f:facet name="header">
										<h:outputText value="Tipo de Socio"></h:outputText>
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
									<rich:datascroller for="tblAdministraN1" maxPages="10" />
								</f:facet>
							</rich:extendedDataTable>
						</h:panelGroup>
					</h:panelGroup>

					<!--------------------------------------------- Cobranza de Cheques Nivel 1 --------------------------------------------->
					<h:panelGroup id="pCobChequesN1"
						rendered="#{estrucOrgController.renderConfigN1}"
						style="border: none; margin-top:15px">
						<!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 1 --------------------------------------------->
						<h:panelGroup id="pConfigCobChequesN1" style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />

							<rich:extendedDataTable id="tblCobChequeN1" enableContextMenu="false" sortMode="single" 
								rendered="#{not empty estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}"
								value="#{estrucOrgController.beanInsti.listaEstructuraDetalleCobranza}"
								var="item" rowKeyVar="rowKey" rows="5" width="764px" height="186px" style="margin:0 auto">

								<f:facet name="header">
									<h:outputText value="Cobranza de Cheques"></h:outputText>
								</f:facet>

								<rich:column width="29px">
									<h:outputText value="#{rowKey + 1}"></h:outputText>
								</rich:column>

								<rich:column width="110">
									<f:facet name="header">
										<h:outputText value="Tipo de Socio"></h:outputText>
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
									<rich:datascroller for="tblCobChequeN1" maxPages="10" />
								</f:facet>
							</rich:extendedDataTable>
							<rich:spacer height="8px"></rich:spacer>
						</h:panelGroup>
					</h:panelGroup>
				</rich:panel>

				<!--------------------------------------------- NIVEL 2: Unidad Ejecutora --------------------------------------------->
				<rich:separator></rich:separator>
				<rich:spacer height="8px"></rich:spacer>
				<h:panelGrid>
					<h:outputText value="Nivel 2: Unidad Ejecutora"
						styleClass="estiloLetra1"></h:outputText>
				</h:panelGrid>
				<rich:spacer height="8px" />

				<h:panelGrid id="pgRazonSocN2" columns="6">
					<rich:column width="110">
						<h:outputText value="Nombre"></h:outputText>
					</rich:column>
					<rich:column width="240">
						<h:inputText value="#{perJuridicaController.beanPerJuridicaN2.juridica.strRazonSocial}" size="40" disabled="true"/>
					</rich:column>
					<rich:column width="160">
						<a4j:commandButton id="btnAgregarPerJuriN2" value="Agregar Persona Jurídica" styleClass="btnEstilos1"
							actionListener="#{perJuridicaController.setIdBtnAgregarPerJuri}" reRender="frmPerJuriConve,mpMessage"
							oncomplete="if(#{estrucOrgController.beanUniEjecu.intIdCodigoRel==null || estrucOrgController.beanUniEjecu.intIdCodigoRel==0}){Richfaces.showModalPanel('mpMessage')}
										else{Richfaces.showModalPanel('pAgregarPerJuri')}">
						</a4j:commandButton>
					</rich:column>
					<rich:column>
						<a4j:commandButton value="#{estrucOrgController.strBtnAgregarN2}"
							actionListener="#{estrucOrgController.showConfigN2}"
							reRender="pgRazonSocN2,pConfigN2,pProcesPlanillaN2,pgProcesPlanillaN2,pAdministraN2,pgAdministraN2,pCobChequesN2,pgCobChequesN2,mpMessage" styleClass="btnEstilos1" 
							oncomplete="if(#{estrucOrgController.beanUniEjecu.intIdCodigoRel==null || estrucOrgController.beanUniEjecu.intIdCodigoRel==0})Richfaces.showModalPanel('mpMessage')">
						</a4j:commandButton>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="8px"/>
				
				<h:panelGrid columns="3">
         	 		<rich:column width="110">
         	 			<h:outputText value="Estado de Entidad"></h:outputText>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu style="width: 100px;" disabled="#{estrucOrgController.onNewDisabledUniEjecu}"
	                  					value="#{estrucOrgController.beanUniEjecu.intParaEstadoCod}">
					  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					  		<a4j:support ajaxSingle="true" event="onchange"></a4j:support>
						</h:selectOneMenu>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<rich:spacer height="8px"/>

				<!--------------------------------------------- Configuracion Nivel 2 --------------------------------------------->
				<rich:panel id="pConfigN2" style="border: none">

					<!--------------------------------------------- Procesar Planilla Nivel 2 --------------------------------------------->
					<h:panelGroup id="pProcesPlanillaN2" layout="block"
						rendered="#{estrucOrgController.renderConfigPlanillaN2}"
						style="border: none">
						<h:panelGrid id="pgProcesPlanillaN2" columns="4">
							<rich:column width="25">
								<h:selectBooleanCheckbox
									value="#{estrucOrgController.chkProcesPlanillaN2}">
									<a4j:support event="onclick"
										actionListener="#{estrucOrgController.showProcesPlanillaN2}"
										reRender="pProcesPlanillaN2,pConfigProcesPlanillaN2"
										ajaxSingle="true"></a4j:support>
								</h:selectBooleanCheckbox>
							</rich:column>
							<rich:column>
								<h:outputText value="Procesar Planilla"></h:outputText>
							</rich:column>
						</h:panelGrid>
						<!--------------------------------------------- Configuracion Procesar Planilla Nivel 2 --------------------------------------------->
						<h:panelGroup id="pConfigProcesPlanillaN2"
							rendered="#{estrucOrgController.renderProcesPlanillaN2}"
							style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />
							<a4j:region id="regConfigPlanillaN2">
								<h:panelGrid columns="10">
									<rich:column width="90">
										<h:outputText value="Tipo de Socio"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboTipoSocioPlanillaN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intParaTipoSocioCod}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="65">
										<h:outputText value="Modalidad"></h:outputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu id="cboModalidadPlanillaN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intParaModalidadCod}">
											<f:selectItem itemValue="0" itemLabel="Todos" />
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="60">
										<h:outputText value="Sucursal"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboSucursalPlanillaN2"
											style="width: 100px;"
											valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intSeguSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listJuridicaSucursal}"
												itemValue="#{sel.id.intIdSucursal}"
												itemLabel="#{sel.juridica.strRazonSocial}" />
											<a4j:support event="onchange"
												reRender="cboSubSucurPlanillaN2" ajaxSingle="true"></a4j:support>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="25">
										<h:selectBooleanCheckbox
											value="#{estrucOrgController.blnCheckSubsucursalUniEjecuPlanilla}"></h:selectBooleanCheckbox>
									</rich:column>
									<rich:column width="80">
										<h:outputText value="Sub Sucursal"></h:outputText>
									</rich:column>
									<rich:column>
										<h:selectOneMenu id="cboSubSucurPlanillaN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intSeguSubSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listSubsucursalPlanilla}"
												itemValue="#{sel.id.intIdSubSucursal}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
								</h:panelGrid>
								<rich:spacer height="8px"></rich:spacer>
								<h:panelGrid columns="10">
									<rich:column width="90">
										<h:outputText value="Fecha Enviado"></h:outputText>
									</rich:column>
									<rich:column>
										<h:inputText
											value="#{estrucOrgController.beanUniEjecuPlanilla.intDiaEnviado}"
											size="6" onkeydown="return validarEnteros(event)"></h:inputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intSaltoEnviado}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="100">
										<h:outputText value="Fecha efectuado"></h:outputText>
									</rich:column>
									<rich:column>
										<h:inputText
											value="#{estrucOrgController.beanUniEjecuPlanilla.intDiaEfectuado}"
											size="6" onkeydown="return validarEnteros(event)"></h:inputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuPlanilla.intSaltoEfectuado}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="20">
										<h:selectBooleanCheckbox
											value="#{estrucOrgController.blnCheckCodigoUniEjecuPlanilla}"></h:selectBooleanCheckbox>
									</rich:column>
									<rich:column width="40">
										<h:outputText value="Código"></h:outputText>
									</rich:column>
									<rich:column width="120">
										<h:inputText
											value="#{estrucOrgController.beanUniEjecuPlanilla.strCodigoExterno}"
											size="15"></h:inputText>
									</rich:column>
									<rich:column>
										<a4j:commandButton id="btnAgregarPlanillaN2" value="Agregar"
											reRender="divProcesPlanillaN2"
											actionListener="#{estrucOrgController.agregarPlanillaUniEjecu}"
											styleClass="btnEstilos">
										</a4j:commandButton>
									</rich:column>
								</h:panelGrid>
							</a4j:region>
							<rich:spacer height="8px"></rich:spacer>

							<h:panelGroup id="divProcesPlanillaN2" layout="block">
								<rich:extendedDataTable id="tblProcesPlanillaN2" enableContextMenu="false" var="item"
									rendered="#{not empty estrucOrgController.beanUniEjecu.listaEstructuraDetallePlanilla}"
									value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetallePlanilla}"
									style="margin:0 auto" rowKeyVar="rowKey" rows="5"
									sortMode="single" width="860px" height="183px"
									onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">

									<rich:column width="29px">
										<h:outputText value="#{rowKey + 1}"></h:outputText>
									</rich:column>

									<rich:column width="110">
										<f:facet name="header">
											<h:outputText value="Tipo de Socio"></h:outputText>
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
										<tumih:outputText value="#{item.listaSubsucursal}"
														itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
														property="#{item.intSeguSubSucursalPk}"/>
									</rich:column>
									<f:facet name="footer">
										<h:panelGroup layout="block">
											<rich:datascroller for="tblProcesPlanillaN2" maxPages="10" />
											<h:panelGroup layout="block" style="margin:0 auto; width:360px">
												<a4j:commandLink id="linkUpDelProcesPlanillaN2" action="#"
													oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblProcesPlanillaN2')">
													<h:graphicImage value="/images/icons/mensaje1.jpg"
														style="border:0px" />
												</a4j:commandLink>
												<h:outputText style="color:#8ca0bd"
													value="Para Eliminar seleccionar una fila y hacer click en el botón."/>
											</h:panelGroup>
										</h:panelGroup>
									</f:facet>
								</rich:extendedDataTable>
							</h:panelGroup>
						</h:panelGroup>
					</h:panelGroup>

					<!--------------------------------------------- Administra Nivel 2 --------------------------------------------->
					<h:panelGroup id="pAdministraN2" layout="block"
						rendered="#{estrucOrgController.renderConfigAdministraN2}"
						style="border: none; margin-top:15px">
						<h:panelGrid id="pgAdministraN2" columns="4">
							<rich:column width="25">
								<h:selectBooleanCheckbox
									value="#{estrucOrgController.chkAdministraN2}">
									<a4j:support event="onclick"
										actionListener="#{estrucOrgController.showAdministraN2}"
										reRender="pAdministraN2,pConfigAdministraN2" ajaxSingle="true"></a4j:support>
								</h:selectBooleanCheckbox>
							</rich:column>
							<rich:column>
								<h:outputText value="Administra"></h:outputText>
							</rich:column>
						</h:panelGrid>

						<!--------------------------------------------- Configuracion Administra Nivel 2 --------------------------------------------->
						<h:panelGroup id="pConfigAdministraN2"
							rendered="#{estrucOrgController.renderAdministraN2}"
							style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />
							<a4j:region id="regConfigAdministraN2">
								<h:panelGrid columns="10">
									<rich:column width="90">
										<h:outputText value="Tipo de Socio"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboTipoSocioAdministraN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuAdministra.intParaTipoSocioCod}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="65">
										<h:outputText value="Modalidad"></h:outputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu id="cboModalidadAdministraN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuAdministra.intParaModalidadCod}">
											<f:selectItem itemValue="0" itemLabel="Todos" />
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="60">
										<h:outputText value="Sucursal"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboSucursalAdministraN2"
											style="width: 100px;"
											valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
											value="#{estrucOrgController.beanUniEjecuAdministra.intSeguSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listJuridicaSucursal}"
												itemValue="#{sel.id.intIdSucursal}"
												itemLabel="#{sel.juridica.strRazonSocial}" />
											<a4j:support event="onchange"
												reRender="cboSubSucurAdministraN2" ajaxSingle="true"></a4j:support>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="25">
										<h:selectBooleanCheckbox
											value="#{estrucOrgController.blnCheckSubsucursalUniEjecuAdministra}"></h:selectBooleanCheckbox>
									</rich:column>
									<rich:column width="80">
										<h:outputText value="Sub Sucursal"></h:outputText>
									</rich:column>
									<rich:column>
										<h:selectOneMenu id="cboSubSucurAdministraN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuAdministra.intSeguSubSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listSubsucursalAdministra}"
												itemValue="#{sel.id.intIdSubSucursal}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
								</h:panelGrid>
								<rich:spacer height="8px"></rich:spacer>

								<h:panelGrid columns="2">
									<rich:column>
										<a4j:commandButton id="btnAgregarAdministraN2" value="Agregar"
											actionListener="#{estrucOrgController.agregarAdministraUniEjecu}"
											reRender="divAdministraN2" styleClass="btnEstilos">
										</a4j:commandButton>
									</rich:column>
								</h:panelGrid>
							</a4j:region>
							<rich:spacer height="8px"></rich:spacer>

							<h:panelGroup id="divAdministraN2" layout="block">
								<rich:extendedDataTable id="tblAdministraN2" enableContextMenu="false" sortMode="single" 
									rendered="#{not empty estrucOrgController.beanUniEjecu.listaEstructuraDetalleAdministra}"
									value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetalleAdministra}"
									var="item" rowKeyVar="rowKey" rows="5" width="490px" height="183px" style="margin:0 auto;"
									onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">
	
									<rich:column width="29px">
										<h:outputText value="#{rowKey + 1}"></h:outputText>
									</rich:column>
	
									<rich:column width="110">
										<f:facet name="header">
											<h:outputText value="Tipo de Socio"></h:outputText>
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
											<rich:datascroller for="tblAdministraN2" maxPages="10" />
											<h:panelGroup layout="block" style="margin:0 auto; width:360px">
												<a4j:commandLink id="linkUpDelAdministraN2" action="#"
													oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblAdministraN2')">
													<h:graphicImage value="/images/icons/mensaje1.jpg"
														style="border:0px" />
												</a4j:commandLink>
												<h:outputText style="color:#8ca0bd"
													value="Para Eliminar seleccionar una fila y hacer click en el botón."/>
											</h:panelGroup>
										</h:panelGroup>
									</f:facet>
							</rich:extendedDataTable>
							</h:panelGroup>
						</h:panelGroup>
					</h:panelGroup>

					<!--------------------------------------------- Cobranza de Cheques Nivel 2 --------------------------------------------->
					<h:panelGroup id="pCobChequesN2" layout="block"
						rendered="#{estrucOrgController.renderConfigCobranzaN2}"
						style="border: none; margin-top:15px">
						<h:panelGrid id="pgCobChequesN2" columns="4">
							<rich:column width="25">
								<h:selectBooleanCheckbox
									value="#{estrucOrgController.chkCobChequesN2}">
									<a4j:support event="onclick"
										actionListener="#{estrucOrgController.showCobChequesN2}"
										reRender="pCobChequesN2,pConfigCobChequesN2" ajaxSingle="true">
									</a4j:support>
								</h:selectBooleanCheckbox>
							</rich:column>
							<rich:column>
								<h:outputText value="Cobranza de Cheques"></h:outputText>
							</rich:column>
						</h:panelGrid>

						<!--------------------------------------------- Configuracion Cobranza de Cheques Nivel 2 --------------------------------------------->
						<h:panelGroup id="pConfigCobChequesN2"
							rendered="#{estrucOrgController.renderCobChequesN2}"
							style="border: none">
							<rich:spacer height="8px" />
							<rich:separator></rich:separator>
							<rich:spacer height="8px" />
							<a4j:region id="regConfigCobChequesN2">
								<h:panelGrid columns="10">
									<rich:column width="90">
										<h:outputText value="Tipo de Socio"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboTipoSocioCobraN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuCobra.intParaTipoSocioCod}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="65">
										<h:outputText value="Modalidad"></h:outputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu id="cboModalidadCobraN2"
											style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuCobra.intParaModalidadCod}">
											<f:selectItem itemValue="0" itemLabel="Todos" />
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="60">
										<h:outputText value="Sucursal"></h:outputText>
									</rich:column>
									<rich:column width="120px">
										<h:selectOneMenu id="cboSucursalCobraN2" style="width: 100px;"
											valueChangeListener="#{estrucOrgController.getListSubsucursalDeSucursal}"
											value="#{estrucOrgController.beanUniEjecuCobra.intSeguSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listJuridicaSucursal}"
												itemValue="#{sel.id.intIdSucursal}"
												itemLabel="#{sel.juridica.strRazonSocial}" />
											<a4j:support event="onchange" reRender="cboSubSucurCobraN2"
												ajaxSingle="true"></a4j:support>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="25">
										<h:selectBooleanCheckbox
											value="#{estrucOrgController.blnCheckSubsucursalUniEjecuCobra}"></h:selectBooleanCheckbox>
									</rich:column>
									<rich:column width="80">
										<h:outputText value="Sub Sucursal"></h:outputText>
									</rich:column>
									<rich:column>
										<h:selectOneMenu id="cboSubSucurCobraN2" style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuCobra.intSeguSubSucursalPk}">
											<f:selectItem itemValue="0" itemLabel="Seleccione.." />
											<tumih:selectItems var="sel"
												value="#{estrucOrgController.listSubsucursalCobra}"
												itemValue="#{sel.id.intIdSubSucursal}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
								</h:panelGrid>
								<rich:spacer height="8px"></rich:spacer>

								<h:panelGrid columns="10">
									<rich:column width="110">
										<h:outputText value="Fecha de Cheque"></h:outputText>
									</rich:column>
									<rich:column>
										<h:inputText
											value="#{estrucOrgController.beanUniEjecuCobra.intDiaCheque}"
											size="6" onkeydown="return validarEnteros(event)"></h:inputText>
									</rich:column>
									<rich:column width="120">
										<h:selectOneMenu style="width: 100px;"
											value="#{estrucOrgController.beanUniEjecuCobra.intSaltoCheque}">
											<tumih:selectItems var="sel"
												cache="#{applicationScope.Constante.PARAM_T_FECHAENVIOCOBRO}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}" />
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="20">
										<h:selectBooleanCheckbox
											value="#{estrucOrgController.blnCheckCodigoUniEjecuCobra}"></h:selectBooleanCheckbox>
									</rich:column>
									<rich:column width="40">
										<h:outputText value="Código"></h:outputText>
									</rich:column>
									<rich:column width="120">
										<h:inputText
											value="#{estrucOrgController.beanUniEjecuCobra.strCodigoExterno}"
											size="15"></h:inputText>
									</rich:column>
									<rich:column>
										<a4j:commandButton id="btnAgregarCobraN2" value="Agregar"
											actionListener="#{estrucOrgController.agregarCobraUniEjecu}"
											reRender="divCobChequeN2" styleClass="btnEstilos">
										</a4j:commandButton>
									</rich:column>
								</h:panelGrid>
							</a4j:region>
							<rich:spacer height="8px"></rich:spacer>

							<h:panelGroup id="divCobChequeN2" layout="block">
								<rich:extendedDataTable id="tblCobChequeN2" enableContextMenu="false" sortMode="single" 
									rendered="#{not empty estrucOrgController.beanUniEjecu.listaEstructuraDetalleCobranza}"
									value="#{estrucOrgController.beanUniEjecu.listaEstructuraDetalleCobranza}"
									var="item" rowKeyVar="rowKey" rows="5" width="764px" height="183px" style="margin:0 auto"
									onRowClick="jsSelectEstructuraDetalle('#{rowKey},#{item.id.intCaso},#{item.id.intNivel}');">
	
									<rich:column width="29px">
										<h:outputText value="#{rowKey + 1}"></h:outputText>
									</rich:column>
	
									<rich:column width="110">
										<f:facet name="header">
											<h:outputText value="Tipo de Socio"></h:outputText>
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
										<h:panelGroup layout="block">
											<rich:datascroller for="tblCobChequeN2" maxPages="10" />
											<h:panelGroup layout="block" style="margin:0 auto; width:360px">
												<a4j:commandLink id="linkUpDelCobChequeN2" action="#"
													oncomplete="Richfaces.showModalPanel('mpUpDelEstructuraDetalle'),jsSetRenderedTable('tblCobChequeN2')">
													<h:graphicImage value="/images/icons/mensaje1.jpg"
														style="border:0px" />
												</a4j:commandLink>
												<h:outputText style="color:#8ca0bd"
													value="Para Eliminar seleccionar una fila y hacer click en el botón."/>
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