<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!--================================================================	
		Empresa   : TUMI
 		Modulo    : CREDITOS
 		Prototipo : SOLICITUD DE ACTIVIDAD
 		Fecha     : 08/04/2013

		1. Contiene la grilla de busqueda, 
		2. Resultado de busqueda
		3. Controles(Nuevo/Grabar/Cancelar) 
		4. Formulario de Validación de datos de socio y 
		5. Rediraccionamiento de paginas a mostrar(Edit o View) 
==================================================================-->


<rich:panel style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
	
	<!---------------------------------------- 
	1.  
	------------------------------------------>
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>INGRESO DE SOLICITUD DE ACTIVIDAD</b>
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
				<h:selectOneMenu value="#{solicitudActividadController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{solicitudActividadController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudActividadController.strBusqNroSol}" />
			</rich:column>
		</h:panelGrid>
	
	<h:panelGrid id="pgBusq2" columns="9" border="0">
		<rich:column width="120px">
			<h:outputText value="Tipo de Actividad"  styleClass="estiloLetra1"/>
		</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column width="120px">
				<h:selectOneMenu style="width:150px" value="#{solicitudActividadController.intBusqTipoCreditoEmpresa}"
								onchange="cargarComboSubActividadBusqueda(#{applicationScope.Constante.ONCHANGE_VALUE})">
					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					<tumih:selectItems var="sel" value="#{solicitudActividadController.listaTiposActividad}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="120px">	
				<h:selectOneMenu id="cboSubActividadBusq" style="width:150px" value="#{solicitudActividadController.intBusqItemCredito}">
						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{solicitudActividadController.listaBusqTiposSubActividad}"
											itemValue="#{sel.id.intItemCredito}" itemLabel="#{sel.strDescripcion}"
											propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudActividadController.intBusqEstado}" style="width:140px" id="somEstado">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudActividadController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cDesde"/>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudActividadController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cHasta"/>
			</rich:column>
		</h:panelGrid>


		<h:panelGrid id="pgBusq3" columns="7" border="0">
			
			<rich:column width="120px">
				<h:outputText value="Sucursal Admin." styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:150px" value="#{solicitudActividadController.intBusqSucursal}" id="somSucursal">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudActividadController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column  width="200px">
			</rich:column>
			<rich:column  width="200px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{solicitudActividadController.buscarExpedientesActividad}" styleClass="btnEstilos" reRender="pgListSolicitudActividad"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{solicitudActividadController.limpiarFiltros}" styleClass="btnEstilos" reRender="somEstado,somSucursal,cDesde, cHasta,pgBusq1,pgBusq2,pgBusq3,pgListSolicitudActividad"/>
			</rich:column>
			
		</h:panelGrid>

		<rich:spacer height="4px"/><rich:spacer height="8px"/>
		
		<!---------------------------------------------------------- 
		2.
		----------------------------------------------------------->
	
		<h:panelGrid id="pgListSolicitudActividad" border="0">
			<rich:extendedDataTable id="edtSolicitudActividad" rows="5"
				value="#{solicitudActividadController.listaExpedienteCreditoComp}"
				enableContextMenu="false" var="item" rowKeyVar="rowKey"
				onRowClick="jsSeleccionSolicitudPrestamo(#{item.expedienteCredito.id.intPersEmpresaPk}, #{item.expedienteCredito.id.intCuentaPk}, #{item.expedienteCredito.id.intItemExpediente}, #{item.expedienteCredito.id.intItemDetExpediente});"
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
						<rich:column rowspan="2" width="110px">
							<h:outputText value="Actividad" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Crédito" />
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
				<rich:column width="180px">
					<h:outputText value="#{item.expedienteCredito.strNombreCompletoPersona}" title="#{item.expedienteCredito.strNombreCompletoPersona}"/> 
				</rich:column>
				<rich:column width="60px">
						<h:outputText value="#{item.expedienteCredito.id.intItemExpediente}" />
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="#{item.strDescripcionActividad}" title="#{item.strDescripcionActividad}"/>
				</rich:column>
				<rich:column width="110px">
					<tumih:outputText value="#{solicitudActividadController.listaTablaDescConfCredito}"
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteCredito.intItemCredito}"/>
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="#{item.expedienteCredito.bdMontoTotal}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
				</rich:column>
				<rich:column width="80px">
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
					<rich:datascroller for="edtSolicitudActividad" maxPages="10" />
				</f:facet>
				
				<a4j:support event="onRowClick"  
					actionListener="#{solicitudActividadController.seleccionarRegistro}" reRender="panelUpdateDeleteSolicitudActividad">
                       	<f:attribute name="itemExpAct" value="#{item}"/>
                 </a4j:support>
			</rich:extendedDataTable>
			
			<h:panelGrid columns="2" >
				<h:outputLink value="#" id="linkSolicitudActividad">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateDeleteSolicitudActividad"
						attachTo="linkSolicitudActividad" operation="show" event="onclick" />
				</h:outputLink>
				
				<h:outputText value="Para Ver o Imprimir Formatos de una SOLICITUD DE ACTIVIDAD  hacer click en el Registro" style="color:#8ca0bd" />
			</h:panelGrid>
		</h:panelGrid>
	</rich:panel>
	
	
	<!--------------------------------------------------------- 
	3.  
	---------------------------------------------------------->
	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
	
	<h:panelGrid id="pgControls" columns="3">
		<a4j:commandButton value="Nuevo" actionListener="#{solicitudActividadController.nuevaSolicitudActividad}" styleClass="btnEstilos" reRender="pgControls,divFormActividad,pgValidarDatos,pgSolicActividad" />
		<h:panelGroup>
           	<a4j:commandButton value="Grabar" actionListener="#{solicitudActividadController.grabarExpedienteActividad}" styleClass="btnEstilos" reRender="pgSolicActividad"/>
        </h:panelGroup>
		<a4j:commandButton value="Cancelar" actionListener="#{solicitudActividadController.cancelarGrabarSolicitud}"
			styleClass="btnEstilos" reRender="pgControls,divFormActividad,pgValidarDatos,pgSolicActividad" />
	</h:panelGrid>
    
    
	<!----------------------------------------------------------- 
	4.  
	------------------------------------------------------------->    
    
    
    <h:panelGroup layout="block" id="divFormActividad"> 
			<rich:panel style="border:1px solid #17356f; width: 966px; background-color:#DEEBF5">
			<h:panelGroup layout="block" rendered="#{solicitudActividadController.blnShowValidarDatos}" id="pgValidarDatos">
				<h:panelGrid columns="9">
					<rich:column style="border: none">
						<h:outputText value="Tipo de Relación : "></h:outputText>
					</rich:column>
					<rich:column>
						<h:selectOneMenu value="#{solicitudActividadController.intTipoRelacion}">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudActividadController.listaTipoRelacion}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>

					<rich:column style="border: none">
						<h:outputText value="Tipo de Solicitud : "></h:outputText>
					</rich:column>
					
					<rich:column>
						<h:selectOneMenu value="#{solicitudActividadController.intValidarDatosTipoSolicitud}"
										onchange="definirTipoDeSolicitud(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudActividadController.listaValidarDatosTipoSolicitud}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					
					<rich:column width="144">
						<h:outputText value="Número de Documento: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu id="cboTipoIdentidad" style="width:110px" value="#{solicitudActividadController.personaValida.documento.intTipoIdentidadCod}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{solicitudActividadController.listDocumentoBusq}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>  
					</rich:column>
					<rich:column>
						<h:inputText size="15" value="#{solicitudActividadController.personaValida.documento.strNumeroIdentidad}"></h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid style="width: 100%">
					<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
									   actionListener="#{solicitudActividadController.validarDatos}" reRender="pgSolicActividad,divFormActividad"/>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid id="pgMsgErrorValidarDatos">
					<h:outputText value="#{solicitudActividadController.strMsgErrorValidarDatos}" styleClass="msgError"/>
				</h:panelGrid>
			
			</h:panelGroup>

			</rich:panel>
		</h:panelGroup>
		
		
	<!------------------------------------------------------------------
	5.  
	------------------------------------------------------------------->	
	
    <h:panelGrid id="pgSolicActividad" >
   	 	<h:panelGroup rendered="#{solicitudActividadController.strSolicitudActividad == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
   	 		<a4j:include viewId="/pages/actividades/solicitudActividadEdit.jsp"/>
   	 	</h:panelGroup>
   	 	<h:panelGroup rendered="#{solicitudActividadController.strSolicitudActividad == applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
   	 		<a4j:include viewId="/pages/actividades/solicitudActividadView.jsp"/>
   	 	</h:panelGroup>
	 
    </h:panelGrid>
</rich:panel>