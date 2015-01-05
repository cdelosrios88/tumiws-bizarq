<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Modulo    : Nueva Solicitud de Crédito     -->
<!-- Prototipo : Empresas			      		-->
<!-- Fecha     : 10/09/2012               		-->

<a4j:region>
	<a4j:jsFunction name="selecAutorizacionLiquidacion"
		actionListener="#{autorizacionLiquidacionController.setSelectedExpedienteLiquidacion}">
		<f:param name="rowExpedienteLiquidacion" />
	</a4j:jsFunction>
</a4j:region>


<h:form id="frmBusqAutLiq">
<h:outputText value="#{autorizacionLiquidacionController.limpiarAutorizacion}"/>
<rich:panel
	style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>AUTORIZACIÓN DE LIQUIDACIÓN DE CUENTAS</b>
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
				<h:selectOneMenu value="#{autorizacionLiquidacionController.intBusquedaTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{autorizacionLiquidacionController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{autorizacionLiquidacionController.strBusqNroSol}" />
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
				<h:selectOneMenu style="width:150px" value="#{autorizacionLiquidacionController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionLiquidacionController.listSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="70px">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						value="#{autorizacionLiquidacionController.intBusqTipoLiquidacion}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{autorizacionLiquidacionController.listaTablaTipoDocumento}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.intIdDetalle}-#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<!-- Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014  -->
				<h:selectOneMenu value="#{autorizacionLiquidacionController.intBusqEstado}" style="width: 120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione..." />
					<tumih:selectItems var="sel" value="#{autorizacionLiquidacionController.listaEstadoSolicitud}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionLiquidacionController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionLiquidacionController.dtBusqFechaEstadoHasta}"
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
				<a4j:commandButton value="Buscar" actionListener="#{autorizacionLiquidacionController.buscarSolicitudLiquidacion}" styleClass="btnEstilos" reRender="divLiquidacionBusq"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{autorizacionLiquidacionController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,divLiquidacionBusq,tblResultado"/>
			</rich:column> 
		</h:panelGrid>

		
		<rich:spacer height="10px"/>
		
		<h:panelGroup id="divLiquidacionBusq">
			<h:panelGroup>
				<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{autorizacionLiquidacionController.listaExpedienteLiquidacionComp}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="970px" 
					height="180px" 
					align="center">
					
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
						<rich:column rowspan="2" width="110px">
							<h:outputText value="Sucursal" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Tipo de Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Liquidación" />
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
					<h:outputText value="#{item.expedienteLiquidacion.strNroCuentaExpediente}"/>
				</rich:column>
				<rich:column width="180px">
					<h:outputText value="#{item.expedienteLiquidacion.strNombreCompletoPersona}" title="#{item.expedienteLiquidacion.strNombreCompletoPersona}"/> 
				</rich:column>
				<rich:column width="60px">
						<h:outputText value="#{item.expedienteLiquidacion.id.intItemExpediente}" />
				</rich:column>
				<rich:column width="110px">
					<tumih:outputText value="#{autorizacionLiquidacionController.listSucursal}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{item.expedienteLiquidacion.intSucursalEstadoInicial}"/>
				</rich:column>
				
				<rich:column width="80px">
									     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUBOPERACION}"
										itemValue="intIdDetalle"
										itemLabel="strDescripcion"
										property="#{item.expedienteLiquidacion.intParaSubTipoOperacion}"/>
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="#{item.expedienteLiquidacion.bdMontoBrutoLiquidacion}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
				</rich:column>
				
				
				
				<rich:column width="80px">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteLiquidacion.intEstadoCreditoUltimo}"/>
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
						<rich:datascroller for="tblResultado" maxPages="10"/>
					</f:facet>
					
					<a4j:support event="onRowClick" reRender="panelUpdateDeleteAutorizacionLiquidacion,frmAutorizacionLiquidacionModalPanel,somTipoCambio, pgCambiosEnSolicitud"
						actionListener="#{autorizacionLiquidacionController.seleccionarRegistro}">
                       	<f:attribute name="item" value="#{item}"/>
                	 </a4j:support>
                	 
            	</rich:extendedDataTable>
			</h:panelGroup>
		
			<h:panelGrid columns="2">
				<h:outputLink value="#" id="linkAutorizacionLiquidacion">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateDeleteAutorizacionLiquidacion"
						attachTo="linkAutorizacionLiquidacion" operation="show" event="onclick" />
				</h:outputLink>
				<h:outputText value="Para Autorizar o Ver una SOLICITUD DE LIQUIDACIÓN hacer click en el Registro" style="color:#8ca0bd" />
			</h:panelGrid>
		</h:panelGroup>
	</rich:panel>

	<h:panelGroup id="panelMensajeAutorizacion" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
		styleClass="rich-tabcell-noborder">
		<h:outputText value="#{autorizacionLiquidacionController.mensajeOperacion}" 
			styleClass="msgInfo"
			style="font-weight:bold"
			rendered="#{autorizacionLiquidacionController.mostrarMensajeExito}"/>
		<h:outputText value="#{autorizacionLiquidacionController.mensajeOperacion}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{autorizacionLiquidacionController.mostrarMensajeError}"/>	
	</h:panelGroup>
		
	<h:panelGrid id="pgControls" columns="2">
        <a4j:commandButton value="Grabar" actionListener="#{autorizacionLiquidacionController.grabarAutorizacionLiquidacion}" 
        					styleClass="btnEstilos" 
        					reRender="pgAutoricLiquidacion,pgPersonasEncargadas,pgListDocAdjuntosVerificacionLiqui,panelMensajeAutorizacion"/>
		<a4j:commandButton value="Cancelar" actionListener="#{autorizacionLiquidacionController.cancelarGrabarAutorizacionLiquidacion}"
							styleClass="btnEstilos" 
							reRender="pgAutoricLiquidacion,pgPersonasEncargadas,pgListDocAdjuntosVerificacionLiqui,panelMensajeAutorizacion" />
	</h:panelGrid>
    
    <h:panelGrid id="pgAutoricLiquidacion">
   		<a4j:include viewId="/pages/liquidacion/autorizacionLiquidacion/autorizacionLiquidacionEdit.jsp"/>
    </h:panelGrid>
</rich:panel>

</h:form>