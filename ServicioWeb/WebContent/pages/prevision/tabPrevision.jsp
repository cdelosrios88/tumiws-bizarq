<%@ taglib uri="http://java.sun.com/jsf/core" 	prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" 	prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" 		prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" 		prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" 	prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" 	prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         -->
<!-- Modulo    : Prevision                -->
<!-- Prototipo : Solicitud Preevision     -->

	<h:form id="frmBusqPrevSocial">
		
	<h:outputText value="#{solicitudPrevisionController.limpiarPrevision}"/>

		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
			<div align="center">
				<b>INGRESO DE SOLICITUD DE PREVISIÓN  SOCIAL</b>
			</div>
		</rich:panel>
		
		<h:panelGrid id="pgBusq0" columns="1" border="0">
			<rich:column>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq1" columns="7" border="0">
			<rich:column width="120px">
				<h:outputText value="Datos de Socio" styleClass="estiloLetra1" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudPrevisionController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="66" value="#{solicitudPrevisionController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Sucursal Admin:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:125px" value="#{solicitudPrevisionController.intBusqSucursal}" id="somSucursal">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudPrevisionController.listaSucursalBusqueda}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		
		<h:panelGrid id="pgBusq2" columns="9" border="0">
						<rich:column width="120px">
					<h:outputText value="Tipo Previsión" styleClass="estiloLetra1" />
				</rich:column>
				<rich:column>
					<h:outputText value=":" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu value="#{solicitudPrevisionController.intBusqTipoPrevision}"
						style="width:150px" onchange="selecTipoSolicitudBusqueda(#{applicationScope.Constante.ONCHANGE_VALUE})">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudPrevisionController.listaTablaTipoDocumento}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
				</rich:column>
	
				<rich:column id="clnSubOperacionBusqueda">
					<h:selectOneMenu value="#{solicitudPrevisionController.intBusqSubTipoPrevision}" style="width: 120px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione..." />
					<tumih:selectItems var="sel" value="#{solicitudPrevisionController.listaSubTipoSolicitudBusqueda}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				
			<rich:column>
				<h:outputText value="Estado" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:120px"
							value="#{solicitudPrevisionController.intBusqEstado}"
							style="width: 170px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
											propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudPrevisionController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cDesde"/>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudPrevisionController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cHasta"/>
			</rich:column>
		</h:panelGrid>


		<h:panelGrid id="pgBusq3" columns="4" border="0">
			<rich:column  width="350px">
			</rich:column>
			<rich:column  width="350px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{solicitudPrevisionController.buscarSolicitudPrevision}" styleClass="btnEstilos" reRender="divPrevisionBusq"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{solicitudPrevisionController.limpiarFiltrosGrilla}" styleClass="btnEstilos" reRender="somEstado,somSucursal,cDesde, cHasta,pgBusq1,pgBusq2,pgBusq3,divPrevisionBusq"/>
			</rich:column>
			
		</h:panelGrid>

		
		<rich:spacer height="10px"/>
		
		<h:panelGroup id="divPrevisionBusq">
			<h:panelGroup>
			
				<rich:extendedDataTable id="tblSolPrev" enableContextMenu="false" sortMode="single" 
				               			var="itemExpedientes" value="#{solicitudPrevisionController.listaExpedientePrevisionBusq}" 
										rowKeyVar="rowKey" rows="5" align="center"
														width="970px" height="180px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column rowspan="2" width="15px" />
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Nro. de Cuenta" />
						</rich:column>
						<rich:column rowspan="2" width="180px">
							<h:outputText value="Nombre Completo" />
						</rich:column>
						<rich:column rowspan="2" width="60px">
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Tipo de Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Crédito" />
						</rich:column>
						<rich:column rowspan="2" width="110px">
							<h:outputText value="Sucursal" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Estado de Solicitud" />
						</rich:column>
						<rich:column colspan="3">
							<h:outputText value="Fechas" />
						</rich:column>
						<rich:column breakBefore="true">
							<h:outputText value="Requisito" />
						</rich:column>
						<rich:column>
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column>
							<h:outputText value="Autorización" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				
				
				<rich:column width="15px">
				<div align="center">
					<h:outputText value="#{rowKey+1}" />
				</div>
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{itemExpedientes.expedientePrevision.strNroCuentaExpediente}"/>
				</rich:column>
				<rich:column width="180px">
					<h:outputText value="#{itemExpedientes.expedientePrevision.strNombreCompletoPersona}" title="#{itemExpedientes.expedientePrevision.strNombreCompletoPersona}"/> 
				</rich:column>
				<rich:column width="60px">
						<h:outputText value="#{itemExpedientes.expedientePrevision.id.intItemExpediente}" />
				</rich:column>
				<rich:column width="80px">
									     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
										itemValue="intIdDetalle"
										itemLabel="strDescripcion"
										property="#{itemExpedientes.expedientePrevision.intParaDocumentoGeneral}"/>
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="#{itemExpedientes.expedientePrevision.bdMontoBrutoBeneficio}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
				</rich:column>
				
				<rich:column width="110px">
					<tumih:outputText value="#{solicitudPrevisionController.listaSucursalBusqueda}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{itemExpedientes.expedientePrevision.intSucursalEstadoInicial}"/>
				</rich:column>
				
				<rich:column width="80px">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{itemExpedientes.expedientePrevision.intEstadoCreditoUltimo}"/>
				</rich:column>
				<rich:column width="95px">
					<h:outputText value="#{itemExpedientes.strFechaRequisito}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{itemExpedientes.strFechaSolicitud}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{itemExpedientes.strFechaAutorizacion}" />
			</rich:column>

				<f:facet name="footer">     
					<h:panelGroup layout="block">
						<rich:datascroller for="tblSolPrev" maxPages="10"/>
					</h:panelGroup>  
				</f:facet>
				
				<a4j:support event="onRowClick"  
					actionListener="#{solicitudPrevisionController.seleccionarRegistro}" reRender="frmSolicitudPrevisionModalPanel" >
                       	<f:attribute name="itemExpedientes" value="#{itemExpedientes}"/>
                 </a4j:support>
                   	
				</rich:extendedDataTable>
			</h:panelGroup>
		
			<h:panelGroup layout="block" style="margin:0 auto; width:580px">
				<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelSolPrevision')">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver, Editar, Anular o Imprimir Formatos una SOLICITUD DE PREVISION SOCIAL hacer click en el Registro" style="color:#8ca0bd"/>
			</h:panelGroup>
		</h:panelGroup>
		
		<h:panelGrid id="divPanelControlPrevision" columns="3"> 
			<a4j:commandButton value="Nuevo" actionListener="#{solicitudPrevisionController.nuevaSolicitudPrevision}" styleClass="btnEstilos"  
			  					reRender="divFormPrevision, pgSolicitudPrevision,pgSoloAplicaSepelio"/>
			<a4j:commandButton value="Guardar" actionListener="#{solicitudPrevisionController.grabarSolicitud}" styleClass="btnEstilos"
								reRender="pgMsgErrorSolPrev, divFormPrevision"/>
			<a4j:commandButton value="Cancelar" actionListener="#{solicitudPrevisionController.cancelarGrabarSolicitud}" styleClass="btnEstilos"
								reRender="divFormPrevision, pgSolicitudPrevision,pgSoloAplicaSepelio,clnFechaFallecimiento">
			</a4j:commandButton>
		</h:panelGrid>
		    
		<br/>
		  
		<h:panelGroup layout="block" id="divFormPrevision"> 
			<rich:panel style="border:1px solid #17356f; width: 966px; background-color:#DEEBF5">
			<h:panelGroup layout="block" rendered="#{solicitudPrevisionController.blnShowValidarDatos}">
				<h:panelGrid columns="7">
					<rich:column style="border: none">
						<h:outputText value="Tipo de Relación : "></h:outputText>
					</rich:column>
					
					<rich:column>
						<h:selectOneMenu value="#{solicitudPrevisionController.intTipoRelacion}">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudPrevisionController.listaTipoRelacion}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
	
					<rich:column width="100">
						<h:outputText value="Tipo de Persona: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu value="#{solicitudPrevisionController.personaBusqueda.intTipoPersonaCod}"
										onchange="selecTipoPersona(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="144">
						<h:outputText value="Número de Documento: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu id="cboTipoIdentidad" style="width:110px" value="#{solicitudPrevisionController.personaBusqueda.documento.intTipoIdentidadCod}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{solicitudPrevisionController.listDocumentoBusq}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>  
					</rich:column>
					<rich:column>
						<h:inputText size="15" value="#{solicitudPrevisionController.personaBusqueda.documento.strNumeroIdentidad}"></h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid style="width: 100%">
					<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
									   actionListener="#{solicitudPrevisionController.validarDatos}" reRender="divFormPrevision,mpConfirmMessage,mpMessage,pgMsgErrorValidarDatos"/>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid id="pgMsgErrorValidarDatos">
					<h:outputText value="#{solicitudPrevisionController.strMsgErrorValidarDatos}" styleClass="msgError"/>
				</h:panelGrid>
			
			</h:panelGroup>
			
			<h:panelGroup rendered="#{solicitudPrevisionController.strSolicitudPrevision == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
				<a4j:include id="divFormSolicitudPrevision" viewId="/pages/prevision/solicitudPrevisionBody.jsp"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{solicitudPrevisionController.strSolicitudPrevision == applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
	 			<a4j:include viewId="/pages/prevision/solicitudPrevisionView.jsp"/>
	 		</h:panelGroup>
	 	
	 	
			</rich:panel>
		</h:panelGroup>
		<h:panelGroup id="frmSolicitudPrevisionView" rendered="#{solicitudPrevisionController.blnVerRegistroSolExpPrevision}">
			<a4j:include viewId="/pages/prevision/solicitudPrevisionView.jsp"/>
		</h:panelGroup>
	</h:form>

