<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!--================================================================	
		Empresa   : TUMI
 		Modulo    : ACTIVIDAD
 		Prototipo : AUTORIZACION DE ACTIVIDAD
 		Fecha     : 18/04/2013

		1. Contiene la grilla de busqueda, 
		2. Resultado de busqueda
		3. Controles(Grabar/Cancelar) 
		4. Rediraccionamiento de paginas a mostrar(Edit o View) 
==================================================================-->


	<rich:panel style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
			<div align="center"> <b>AUTORIZACIÓN DE SOLICITUD DE ACTIVIDAD</b> </div>
			
<!--- 1. Grilla de busqueda -------------------------------------------------------------------------------------->			
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
				<h:selectOneMenu value="#{autorizacionActividadController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{autorizacionActividadController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{autorizacionActividadController.strBusqNroSol}" />
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
				<h:selectOneMenu style="width:150px" value="#{autorizacionActividadController.intBusqTipoCreditoEmpresa}"
								onchange="cargarComboSubActividadBusquedaAut(#{applicationScope.Constante.ONCHANGE_VALUE})">
					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					<tumih:selectItems var="sel" value="#{autorizacionActividadController.listaTiposActividad}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="120px">	
				<h:selectOneMenu id="cboSubActividadBusqAut" style="width:150px" value="#{autorizacionActividadController.intBusqItemCredito}">
						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{autorizacionActividadController.listaBusqTiposSubActividad}"
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
				<!-- Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014  -->
				<h:selectOneMenu value="#{autorizacionActividadController.intBusqEstado}" style="width: 120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione..." />
					<tumih:selectItems var="sel" value="#{autorizacionActividadController.listaEstadoSolicitud}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionActividadController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cDesde"/>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionActividadController.dtBusqFechaEstadoHasta}"
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
				<h:selectOneMenu style="width:150px" value="#{autorizacionActividadController.intBusqSucursal}" id="somSucursal">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionActividadController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column  width="200px">
			</rich:column>
			<rich:column  width="200px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{autorizacionActividadController.buscarSolicitudesPorAutorizar}" styleClass="btnEstilos" reRender="pgListAutorizacionActividad"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{autorizacionActividadController.limpiarFiltros}" styleClass="btnEstilos" reRender="somEstado,somSucursal,cDesde, cHasta,pgBusq1,pgBusq2,pgBusq3,pgListAutorizacionActividad"/>
			</rich:column>
			
		</h:panelGrid>
			
			<rich:spacer height="4px"/><rich:spacer height="8px"/>
			
			
<!--- 2. Resultado de busqueda -------------------------------------------------------------------------------------->
			<h:panelGrid id="pgListAutorizacionActividad" border="0">
				<rich:extendedDataTable id="edtAutorizacionActividad" 
										rows="5"
										value="#{autorizacionActividadController.listaAutorizacionCreditoComp}"
										enableContextMenu="false" 
										var="item" 
										rowKeyVar="rowKey"
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
					<tumih:outputText value="#{autorizacionActividadController.listaTablaDescConfCredito}"
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
						<rich:datascroller for="edtAutorizacionActividad" maxPages="10" />
					</f:facet>
					
					<a4j:support event="onRowClick"  
						actionListener="#{autorizacionActividadController.seleccionarRegistro}" reRender="panelUpdateDeleteAutorizacionActividad">
                       	<f:attribute name="itemExpActividadPorAut" value="#{item}"/>
                 </a4j:support>
                 
				</rich:extendedDataTable>
			</h:panelGrid>
			
			
			<h:panelGrid columns="2">
				<h:outputLink value="#" id="linkAutorizacionActividad">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateDeleteAutorizacionActividad"
											attachTo="linkAutorizacionActividad" 
											operation="show" 
											event="onclick" />
				</h:outputLink>
				
				<h:outputText value="Para Autorizar, Cambio de Tipo de Actividad o Imprimir Formatos de una  SOLICITUD DE ACTIVIDAD hacer click en el Registro" style="color:#8ca0bd" />
			
			</h:panelGrid>
		</rich:panel>

<!--- 3. Controles(Grabar/Cancelar)  -------------------------------------------------------------------------------------->		
		<h:panelGrid id="pgControls" columns="2">
			<a4j:commandButton value="Grabar" 
								actionListener="#{autorizacionActividadController.grabarAutorizacionActividad}" 
								styleClass="btnEstilos" 
								reRender="pgAutoricActividad, pgListAutorizacionActividad, pgTipoOperacion, cboTipoOperacionAut"/>
			<a4j:commandButton value="Cancelar" 
								actionListener="#{autorizacionActividadController.cancelarGrabarAutorizacionPrestamo}"
								styleClass="btnEstilos" 
								reRender="pgAutoricActividad, pgListAutorizacionActividad, pgTipoOperacion, cboTipoOperacionAut" />
		</h:panelGrid>
		

<!--- 4. Rediraccionamiento de paginas a mostrar(Edit o View)   -------------------------------------------------------------------------------------->
		<h:panelGrid id="pgAutoricActividad">
			<h:panelGroup rendered="#{autorizacionActividadController.strSolicitudAutorizacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	   	 		<a4j:include viewId="/pages/actividades/autorizacionActividadEdit.jsp"/>
	   	 	</h:panelGroup>
	   	 	<h:panelGroup rendered="#{autorizacionActividadController.strSolicitudAutorizacion == applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
		 		<a4j:include viewId="/pages/actividades/autorizacionActividadView.jsp"/>>
		 	</h:panelGroup>
		</h:panelGrid>
	</rich:panel>