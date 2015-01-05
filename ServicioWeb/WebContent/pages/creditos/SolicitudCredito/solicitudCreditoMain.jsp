<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Modulo    : Nueva Solicitud de Crédito     -->
<!-- Prototipo : Empresas			      		-->
<!-- Fecha     : 20/07/2012               		-->
<!-- Autor: jchavez / Tarea: Modificación / Fecha: 29.08.2014 -->



		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
			<div align="center">
				<b>INGRESO DE SOLICITUD DE PRÉSTAMO</b>
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
					<h:selectOneMenu value="#{solicitudPrestamoController.intBusqTipo}" style="width:150px"> 
						<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:inputText size="67" value="#{solicitudPrestamoController.strBusqCadena}"/>
				</rich:column>
				<rich:column>
					<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.strBusqNroSol}" />
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
					<h:selectOneMenu style="width:150px" value="#{solicitudPrestamoController.intBusqSucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
						<tumih:selectItems var="sel" value="#{solicitudPrestamoController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="Tipo Préstamo :" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column width="150px">
					<h:selectOneMenu value="#{solicitudPrestamoController.intBusqTipoCreditoEmpresa}" style="width:120px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel" value="#{solicitudPrestamoController.listaTablaCreditoEmpresa}"
							 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:outputText value="Estado :" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu value="#{solicitudPrestamoController.intBusqEstado}">
						<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" enableManualInput="true" value="#{solicitudPrestamoController.dtBusqFechaEstadoDesde}"
						datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" enableManualInput="true" value="#{solicitudPrestamoController.dtBusqFechaEstadoHasta}"
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
					<a4j:commandButton value="Buscar" actionListener="#{solicitudPrestamoController.listarSolicitudPrestamo}" styleClass="btnEstilos" reRender="divPrestamoBusq"/>
				</rich:column>
				<rich:column>
					<a4j:commandButton value="Limpiar" actionListener="#{solicitudPrestamoController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,pgListSolicitudCredito"/>
				</rich:column> 
			</h:panelGrid>
			
			<rich:spacer height="10px"/>
			
			<h:panelGroup id="divPrestamoBusq">
				<h:panelGroup>
					<rich:extendedDataTable id="edtSolicitudCredito" rows="5"
						value="#{solicitudPrestamoController.listaExpedienteCreditoComp}"
						enableContextMenu="false" var="item" rowKeyVar="rowKey"
						width="970px" height="195px">
						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column rowspan="2" width="15px" />
								<rich:column rowspan="2" width="90px">
									<h:outputText value="Nro. de Cuenta" />
								</rich:column>
								<rich:column rowspan="2" width="150px">
									<h:outputText value="Nombre Completo" />
								</rich:column>
								<rich:column rowspan="2" width="80px">
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
						<rich:column width="80px">
								<h:outputText value="#{item.expedienteCredito.id.intItemExpediente} - #{item.expedienteCredito.id.intItemDetExpediente}" />
						</rich:column>
						<rich:column width="80px">
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
		                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                      property="#{item.expedienteCredito.intParaTipoCreditoEmpresa}"/>
		                </rich:column>
		                <rich:column width="80px">
							<h:outputText value="#{item.expedienteCredito.bdMontoTotal}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						<rich:column>
							<tumih:outputText value="#{solicitudPrestamoController.listSucursal}" 
		                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
		                                      property="#{item.expedienteCredito.intSucursalEstadoInicial}"
		                                      style="title:que procesó el crédito"/>
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
							<h:panelGroup layout="block">
								<rich:datascroller for="edtSolicitudCredito" maxPages="10" />
							</h:panelGroup>
						</f:facet>
						
						<a4j:support event="onRowClick"  
							actionListener="#{solicitudPrestamoController.seleccionarRegistro}" reRender="frmSolicitudPrestamoModalPanel" >
		                       	<f:attribute name="itemExpCredito" value="#{item}"/>
		                 </a4j:support>		                 
					</rich:extendedDataTable>
				</h:panelGroup>
				
			<h:panelGroup layout="block" style="margin:0 auto; width:580px">
				<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('panelUpdateDeleteSolicitudPrestamo')">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver, Editar, Anular o Imprimir Formatos una SOLICITUD DE PRESTAMO hacer click en el Registro" style="color:#8ca0bd" />
			</h:panelGroup>

		</h:panelGroup>
	
		<h:panelGrid id="pgControlsPrestamo" columns="9">
			<a4j:commandButton value="Nuevo" actionListener="#{solicitudPrestamoController.habilitarGrabarSolicitud}" styleClass="btnEstilos" reRender="pgControls,pgSolicCredito,pgMsgErrorGrabar,pgControlsPrestamo" />
			
			<h:panelGroup rendered="#{solicitudPrestamoController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	           	<a4j:commandButton value="Grabar" actionListener="#{solicitudPrestamoController.grabarSolicitudPrestamo}" styleClass="btnEstilos" reRender="pgSolicCredito,pgMsgErrorGrabar,pgControlsPrestamo"/>
	        </h:panelGroup>
	        <h:panelGroup rendered="#{solicitudPrestamoController.strSolicitudPrestamo == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	           	<a4j:commandButton value="Grabar" actionListener="#{solicitudPrestamoController.modificarSolicitudPrestamo}" styleClass="btnEstilos" reRender="pgSolicCredito,pgMsgErrorGrabar,pgControlsPrestamo"/>
	        </h:panelGroup>
			
			<a4j:commandButton value="Cancelar" actionListener="#{solicitudPrestamoController.cancelarGrabarSolicitudPrestamo}"
				styleClass="btnEstilos" reRender="pgControlsPrestamo,pgSolicCredito" />
				
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
				<a4j:commandButton value="Imprimir" styleClass="btnEstilos" oncomplete="Richfaces.showModalPanel('formEnBlanco')"
				reRender="pgFormEnBlancoSolicitudCredito,frmEnBlancoSolicitudCredito"/>
		</h:panelGrid>
	    
	    <h:panelGrid id="pgSolicCredito">
		    <h:panelGrid id="pgMsgErrorGrabar">
				<h:outputText value="#{solicitudPrestamoController.strMensajeGarantesObservacion}" styleClass="msgError"/>
				
				<h:outputText value="#{solicitudPrestamoController.strErrorGrabar}" styleClass="msgError"/>
			</h:panelGrid>
			<a4j:outputPanel id="opSolicitudCreditoUpdate">
				<a4j:include viewId="/pages/creditos/SolicitudCredito/solicitudCreditoEdit.jsp"/>
	   	 	</a4j:outputPanel>
	   	 	
	   	 	<a4j:outputPanel id="opSolicitudCreditoView">
	   	 		<a4j:include viewId="/pages/creditos/SolicitudCredito/solicitudCreditoView.jsp"/>
		 	</a4j:outputPanel>
	    </h:panelGrid>