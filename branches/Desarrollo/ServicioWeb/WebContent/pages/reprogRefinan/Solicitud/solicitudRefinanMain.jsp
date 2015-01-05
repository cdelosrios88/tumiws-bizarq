<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!--================================================================	
		Empresa   : TUMI
 		Modulo    : REFINANCIAMIENTO
 		Prototipo : SOLICITUD DE REFINANCIAMIENTO
 		Fecha     : 22/02/2013

		1. Contiene la grilla de busqueda, 
		2. Resultado de busqueda
		3. Controles(Nuevo/Grabar/Cancelar) 
		4. Formulario de Validacion de datos de socio y 
		5. Rediraccionamiento de paginas a mostrar(Edit o View) 
==================================================================-->


<rich:panel style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
	
	<!---------------------------------------- 
	1.  
	------------------------------------------>
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>INGRESO DE SOLICITUD DE REFINANCIAMIENTO / REPROGRAMACION</b>
		</div>
		
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
				<h:selectOneMenu value="#{solicitudRefinanController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{solicitudRefinanController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudRefinanController.strBusqNroSol}" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq2" columns="9" border="0">
			<rich:column width="120px">
				<h:outputText value="Sucursal Admin." styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:150px" value="#{solicitudRefinanController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudRefinanController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100px">
				<h:outputText value="Tipo Préstamo :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column width="150px">
				<h:selectOneMenu value="#{solicitudRefinanController.intBusqTipoCreditoEmpresa}" style="width:120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{solicitudRefinanController.listaTablaCreditoEmpresa}"
						 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudRefinanController.intBusqEstado}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudRefinanController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudRefinanController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq3" columns="11" border="0">
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="100px">
			</rich:column>
			<rich:column width="150px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{solicitudRefinanController.listarExpedientesRefinanciamiento}" styleClass="btnEstilos" reRender="pgListSolicitudRefinan"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{solicitudRefinanController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,pgListSolicitudRefinan"/>
			</rich:column> 
		</h:panelGrid>

		<rich:spacer height="4px"/><rich:spacer height="8px"/>
		
		<!---------------------------------------------------------- 
		2.  
		----------------------------------------------------------->
	
		<h:panelGrid id="pgListSolicitudRefinan" border="0">
			<rich:extendedDataTable id="edtSolicitudRefinan"
				value="#{solicitudRefinanController.listaExpedienteCreditoComp}"
				enableContextMenu="false" var="item" rowKeyVar="rowKey"
				onRowClick="jsSeleccionSolicitudPrestamo(#{item.expedienteCredito.id.intPersEmpresaPk}, #{item.expedienteCredito.id.intCuentaPk}, #{item.expedienteCredito.id.intItemExpediente}, #{item.expedienteCredito.id.intItemDetExpediente});"
				width="950px" height="200px">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column rowspan="2" width="15px" />
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Nro. de Cuenta" />
						</rich:column>
						<rich:column rowspan="2" width="150px">
							<h:outputText value="Nombre Completo" />
						</rich:column>
						<rich:column rowspan="2" width="60px">
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Tipo de Préstamo" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Crédito" />
						</rich:column>
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Sucursal que procesó Crédito"/>
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
					<h:outputText value="#{item.expedienteCredito.strNroCuentaExpediente}"/>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="#{item.expedienteCredito.strNombreCompletoPersona}" title="#{item.expedienteCredito.strNombreCompletoPersona}"/>
				</rich:column>
				<rich:column width="60px">						
						<h:outputText value="#{item.expedienteCredito.id.intItemExpediente}-#{item.expedienteCredito.id.intItemDetExpediente}" 
								rendered="#{!item.blnLinkDetalle}"/>
						<a4j:commandLink value="#{item.expedienteCredito.id.intItemExpediente}-#{item.expedienteCredito.id.intItemDetExpediente}"
							rendered="#{item.blnLinkDetalle}"
							actionListener="#{solicitudRefinanController.irDetalleRefinanciamientoLink}"
							reRender="mpDetalleRefinanciamiento" 
							oncomplete="Richfaces.showModalPanel('mpDetalleRefinanciamiento')">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
						
				</rich:column>
				<rich:column width="80px"> 
					<tumih:outputText value="#{solicitudRefinanController.listaTablaCreditoEmpresa}" 
                                      itemValue="intIdDetalle" itemLabel="strAbreviatura" 
                                      property="#{item.expedienteCredito.intParaTipoCreditoEmpresa}"/>
                </rich:column>
                <rich:column width="80px">
					<h:outputText value="#{item.expedienteCredito.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column>
					<tumih:outputText value="#{solicitudRefinanController.listSucursal}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{item.expedienteCredito.intSucursalEstadoInicial}"
                                      style="title='que procesó el crédito'"/>
				</rich:column>
				<rich:column >
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteCredito.intEstadoCreditoUltimo}"/>
				</rich:column>
				<rich:column width="95px">
					<h:outputText value="#{item.strFechaRequisito}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaSolicitud}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaAutorizacion}" />
				</rich:column>

				<f:facet name="footer">
					<rich:datascroller for="edtSolicitudRefinan" maxPages="10" />
				</f:facet>			

				<a4j:support event="onRowClick"  reRender="panelUpdateDeleteSolicitudRefinan"
					actionListener="#{solicitudRefinanController.seleccionarRegistro}">
                       	<f:attribute name="itemExpRef" value="#{item}"/>
                </a4j:support>
			</rich:extendedDataTable>
			
			<h:panelGrid columns="2">
				<h:outputLink value="#" id="linkSolicitudRefinan">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateDeleteSolicitudRefinan"
						attachTo="linkSolicitudRefinan" operation="show" event="onclick" />
				</h:outputLink>
				<h:outputText value="Para Editar, Anular o Imprimir Formatos de una SOLICITUD DE REFINANCIAMIENTO/REPROGRAMACION  hacer click en el Registro" style="color:#8ca0bd" />
			</h:panelGrid>
			
		</h:panelGrid>

	</rich:panel>
	
	<h:panelGrid id="pgMsgErrorBusqueda">
		<h:outputText value="#{solicitudRefinanController.strMsgErrorExisteRefinanciamiento}" styleClass="msgError" />
	</h:panelGrid>
	<!--------------------------------------------------------- 
	3.  
	---------------------------------------------------------->
	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
		
			
	<h:panelGrid id="pgControls" columns="9">
		<a4j:commandButton value="Nuevo" actionListener="#{solicitudRefinanController.nuevaSolicitudRefinan}" styleClass="btnEstilos" reRender="pgControls,divFormRefinan,pgValidarDatos,pgSolicRefinan" />
		<h:panelGroup>
           	<a4j:commandButton value="Grabar" actionListener="#{solicitudRefinanController.grabarExpedienteRefinanciamiento}" styleClass="btnEstilos" reRender="pgSolicRefinan,pgMsgErrorGarantesObs"/>
        </h:panelGroup>
		<a4j:commandButton value="Cancelar" actionListener="#{solicitudRefinanController.cancelarGrabarSolicitud}"
			styleClass="btnEstilos" reRender="pgControls,pgSolicRefinan,pgMsgErrorGarantesObs,divFormRefinan" />
			
			<rich:column width="100px">
					</rich:column>
					<rich:column width="150px">
					</rich:column>
				<rich:column width="120px">
					</rich:column>
					<rich:column width="120px">
					</rich:column>
				<rich:column width="200px" style="margin-left: auto; margin-right: auto">
				<h:outputText
					value="Hacer clic para imprimir formatos en blanco"
					style="color:#8ca0bd" />
				</rich:column>
					<%--<a4j:commandButton value="Imprimir" styleClass="btnEstilos" onclick="#{rich:component('popup')}.show()"/>--%>
				<a4j:commandButton value="Imprimir" styleClass="btnEstilos" oncomplete="Richfaces.showModalPanel('formEnBlancoRefi')"
				reRender="pgFormEnBlancoSolicitudRefi,frmEnBlancoSolicitudRefi"/>
	</h:panelGrid>
    
    
	<!----------------------------------------------------------- 
	4.  
	------------------------------------------------------------->    
    
    
    <h:panelGroup layout="block" id="divFormRefinan"> 
			<rich:panel style="border:1px solid #17356f; width: 966px; background-color:#DEEBF5">
			<h:panelGroup layout="block" rendered="#{solicitudRefinanController.blnShowValidarDatos}" id="pgValidarDatos">
				<h:panelGrid columns="9">
					<rich:column style="border: none">
						<h:outputText value="Relación : "></h:outputText>
					</rich:column>
					
					<rich:column>
						<h:selectOneMenu value="#{solicitudRefinanController.intTipoRelacion}">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudRefinanController.listaTipoRelacion}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					
					
					<rich:column style="border: none">
						<h:outputText value="Tipo de Operación : "></h:outputText>
					</rich:column>
					
					<rich:column>
						<h:selectOneMenu value="#{solicitudRefinanController.intSubTipoOperacion}"
										onchange="seleccionarTipoOperacion(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudRefinanController.listaTipoOperacion}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					
					
					<rich:column width="100">
						<h:outputText value="Tipo de Persona: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu value="#{solicitudRefinanController.personaValida.intTipoPersonaCod}"
										 onchange="selecTipoPersonaRefinan(#{applicationScope.Constante.ONCHANGE_VALUE})">
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
						<h:selectOneMenu id="cboTipoIdentidad" style="width:110px" value="#{solicitudRefinanController.personaValida.documento.intTipoIdentidadCod}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{solicitudRefinanController.listDocumentoBusq}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>  
					</rich:column>
					<rich:column>
						<h:inputText size="15" value="#{solicitudRefinanController.personaValida.documento.strNumeroIdentidad}"></h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid style="width: 100%">
					<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
									   actionListener="#{solicitudRefinanController.validarDatos2}" reRender="pgSolicRefinan,divFormRefinan"/>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid id="pgMsgErrorValidarDatos">
					<h:outputText value="#{solicitudRefinanController.strMsgErrorValidarDatos}" styleClass="msgError"/>
				</h:panelGrid>
			
			</h:panelGroup>

			</rich:panel>
		</h:panelGroup>
		
		
	<!------------------------------------------------------------------
	5.  
	------------------------------------------------------------------->	
	
    <h:panelGrid id="pgSolicRefinan" >
   	 	<h:panelGroup rendered="#{solicitudRefinanController.strSolicitudRefinan == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
   	 		<a4j:include viewId="/pages/reprogRefinan/Solicitud/solicitudRefinanEdit.jsp"/>
   	 	</h:panelGroup>
   	 	<h:panelGroup rendered="#{solicitudRefinanController.strSolicitudRefinan == applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
   	 		<a4j:include viewId="/pages/reprogRefinan/Solicitud/SolicitudRefinanView.jsp"/>
   	 	</h:panelGroup>
	 
    </h:panelGrid>
</rich:panel>