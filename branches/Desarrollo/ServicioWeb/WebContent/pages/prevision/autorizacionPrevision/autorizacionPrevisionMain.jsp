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
	<a4j:jsFunction name="selecAutorizacionPrevision"
		actionListener="#{autorizacionPrevisionController.setSelectedExpedientePrevision}">
		<f:param name="rowExpedientePrevision" />
	</a4j:jsFunction>
</a4j:region>


<h:form id="frmBusqPrevSocial">
    	<h:outputText value="#{autorizacionPrevisionController.limpiarAutorizacion}"/>
 

<rich:panel
	style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<div align="center">
			<b>AUTORIZACIÓN DE PREVISIÓN SOCIAL </b>
		</div>
		
		
		<h:panelGroup layout="block" style="padding:15px">
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
				<h:selectOneMenu value="#{autorizacionPrevisionController.intBusqTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="66" value="#{autorizacionPrevisionController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Sucursal Admin:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:125px" value="#{autorizacionPrevisionController.intBusqSucursal}" id="somSucursal">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionPrevisionController.listaSucursalBusqueda}"
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
					<h:selectOneMenu value="#{autorizacionPrevisionController.intBusqTipoPrevision}"
						style="width:150px" onchange="selecTipoSolicitudBusqueda(#{applicationScope.Constante.ONCHANGE_VALUE})">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{autorizacionPrevisionController.listaTablaTipoDocumento}"
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
				</rich:column>
	
				<rich:column id="clnSubOperacionBusqueda">
					<h:selectOneMenu value="#{autorizacionPrevisionController.intBusqSubTipoPrevision}" style="width: 120px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione..." />
					<tumih:selectItems var="sel" value="#{autorizacionPrevisionController.listaSubTipoSolicitudBusqueda}"
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
				<!-- Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014  -->
				<h:selectOneMenu value="#{autorizacionPrevisionController.intBusqEstado}" style="width: 120px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione..." />
					<tumih:selectItems var="sel" value="#{autorizacionPrevisionController.listaEstadoSolicitud}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionPrevisionController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cDesde"/>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{autorizacionPrevisionController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:85px;" id="cHasta"/>
			</rich:column>
		</h:panelGrid>


		<h:panelGrid id="pgBusq3" columns="4" border="0">
			
			
			<rich:column  width="350px">
			</rich:column>
			<rich:column  width="350px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{autorizacionPrevisionController.buscarSolicitudPrevision}" styleClass="btnEstilos" reRender="divPrevisionBusq"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{autorizacionPrevisionController.limpiarFiltrosGrilla}" styleClass="btnEstilos" reRender="somEstado,somSucursal,cDesde, cHasta,pgBusq1,pgBusq2,pgBusq3,divPrevisionBusq"/>
			</rich:column>
			
		</h:panelGrid>


		</h:panelGroup>
		
		<rich:spacer height="10px"/>
		
		<h:panelGroup id="divPrevisionBusq">
			<h:panelGroup>		
				<rich:extendedDataTable id="tblSolPrev" enableContextMenu="false" sortMode="single" 
				               			var="itemExpedientes" value="#{autorizacionPrevisionController.listaExpedientePrevisionComp}" 
										rowKeyVar="rowKey" rows="5" width="970px" height="180px" align="center">
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
					actionListener="#{autorizacionPrevisionController.seleccionarRegistro}"
					reRender="pgTipoOperacion">
                       	<f:attribute name="itemExpedientes" value="#{itemExpedientes}"/>
                 </a4j:support>
                   	
				</rich:extendedDataTable>
			</h:panelGroup>
		
			<h:panelGrid columns="2">
				<h:outputLink value="#" id="linkAutorizacionPrevision">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateDeleteAutorizacionPrevision"
						attachTo="linkAutorizacionPrevision" operation="show" event="onclick" />
				</h:outputLink>
				<h:outputText value="Para Autorizar o Ver una SOLICITUD DE PREVISIÓN SOCIAL  hacer click en el Registro" style="color:#8ca0bd" />
			</h:panelGrid>
		</h:panelGroup>
	</rich:panel>


	<h:panelGrid id="pgControls" columns="2">
        <a4j:commandButton value="Grabar" actionListener="#{autorizacionPrevisionController.grabarAutorizacionPrevision}" 
        					styleClass="btnEstilos" 
        					reRender="pgAutoricPrevision"/>
		<a4j:commandButton value="Cancelar" actionListener="#{autorizacionPrevisionController.cancelarGrabarAutorizacionPrevision}"
							styleClass="btnEstilos" 
							reRender="pgAutoricPrevision" />
	</h:panelGrid>
    
    <h:panelGrid id="pgAutoricPrevision">
   		<a4j:include viewId="/pages/prevision/autorizacionPrevision/autorizacionPrevisionEdit.jsp"/>
    </h:panelGrid>
</rich:panel>

</h:form>