<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         	-->
	<!-- Autor     : Christian De los Ríos    	-->
	<!-- Modulo    : Créditos                 	-->
	<!-- Prototipo : HOJA DE CONTROL DE PROCESO -->
	<!-- Fecha     : 24/05/2012               	-->

	<script type="text/javascript">
	</script>
	
	<rich:panel style="border:1px solid #17356f ;width: 930px; background-color:#DEEBF5">
		<div align="center"><h:outputText value="ANALISIS Y APROBACION DE CONVENIO INSTITUCIONAL" style="font-weight:bold;"/></div>
			<h:panelGrid columns="6" style="width: 800px" border="0">
				<!-- Agregado por cdelosrios, 08/12/2013 -->
				<rich:column width="120"><h:outputText value="Código" /></rich:column>
				<rich:column>
					<h:inputText value="#{controlProcesoController.intIdFiltroConvenio}" size="15"/>
				</rich:column>
				<%--
				<rich:column width="120"><h:outputText value="Tipo de Convenio" /></rich:column>
				<rich:column>
					<h:selectOneMenu value="#{controlProcesoController.intIdTipoConvenio}">
						<f:selectItem itemValue="" itemLabel="Todos.."/>
						<f:selectItem itemValue="0" itemLabel="Nuevo Convenio"/>
						<f:selectItem itemValue="1" itemLabel="Adenda"/>
					</h:selectOneMenu>
				</rich:column>--%>
				<!-- Fin agregado por cdelosrios, 08/12/2013 -->
				<rich:column><h:outputText value="Sucursal" /></rich:column>
				<rich:column>
					<!-- Modificado por cdelosrios, 08/12/2013 -->
					<%--<h:selectOneMenu value="#{controlProcesoController.intSucursalConv}">
                      	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>--%>
					<h:selectOneMenu value="#{controlProcesoController.intSucursalConv}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel" value="#{controlProcesoController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" />
					</h:selectOneMenu>
					<!-- Fin modificado por cdelosrios, 08/12/2013 -->
				</rich:column>
				<%--
				<rich:column><h:outputText value="Estado" /></rich:column>
				
				<rich:column>
					<h:selectOneMenu value="#{controlProcesoController.intEstadoConvenio}">
                       <f:selectItem itemLabel="Seleccionar.." 	itemValue="0"/>
                       <f:selectItem itemLabel="Aprobado"	 	itemValue="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO_APROBADO}"/>
                       <f:selectItem itemLabel="Rechazado" 		itemValue="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO_RECHAZADO}"/>
                       <%--
                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
	                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
	                       tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
	                       propertySort="intOrden"/>
					</h:selectOneMenu>
				</rich:column>
				--%>
			</h:panelGrid>
			
			<h:panelGrid columns="3" style="width: 800px" border="0">
				<rich:column width="120"><h:outputText value="Nombre de Entidad" /></rich:column>
				<rich:column>
					<h:inputText value="#{controlProcesoController.strEntidad}" size="100"/>
				</rich:column>
				<rich:column>
					<a4j:commandButton value="Buscar" actionListener="#{controlProcesoController.listarControlProceso}" styleClass="btnEstilos" reRender="pgControlProceso"/>
				</rich:column>
			</h:panelGrid>
			
			<div align="center">
				<h:panelGrid id="pgControlProceso" columns="1"  border="0">
				<rich:extendedDataTable id="tbControlProceso" value="#{controlProcesoController.listaEstructHojaPlaneamientoComp}"
					rendered="#{not empty controlProcesoController.listaEstructHojaPlaneamientoComp}"
					var="item" rowKeyVar="rowKey" width="910px" height="180px" enableContextMenu="false" rows="5">
				    <rich:column width="80">
				        <f:facet name="header">
				            <h:outputText value="Código"/>
				        </f:facet>
				        <div align="center">
				        	<h:outputText value="#{item.adenda.id.intConvenio}"/>
				        </div>
				    </rich:column>
				    <rich:column width="220px">
				        <f:facet name="header">
				            <h:outputText value="Nombre de Entidad"/>
				        </f:facet>
				        <h:outputText id="strEntidad" value="#{item.estructuraDetalle.estructura.juridica.strRazonSocial}">
				        	<rich:toolTip for="strEntidad" value="#{item.estructuraDetalle.estructura.juridica.strRazonSocial}" followMouse="true"/>
				        </h:outputText>
				    </rich:column>
				    <rich:column>
	                    <f:facet name="header">
	                    	<h:outputText value="Nivel"/>
	                    </f:facet>
						<h:outputText id="idStrNivel" value="#{item.strNivel}">
							<rich:toolTip for="idStrNivel" value="#{item.strNivel}" followMouse="true" />
						</h:outputText>
	                </rich:column>
				    <rich:column width="95px">
				        <f:facet name="header">
				            <h:outputText value="T. Convenio"/>
				        </f:facet>
				        <h:outputText value="#{item.adenda.id.intItemConvenio==0 ? 'Nuevo Convenio' : 'Adenda'}"/>
				    </rich:column>
				    <rich:column width="80px">
				        <f:facet name="header">
				            <h:outputText value="Estado"/>
				        </f:facet>
				        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
                                          itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                          property="#{item.adenda.intParaEstadoHojaPlan}"/>
				    </rich:column>
				    <rich:column width="120px">
                        <f:facet name="header">
                            <h:outputText value="Sucursal"/>
                        </f:facet>
                        <tumih:outputText value="#{hojaPlaneamientoController.listJuridicaSucursal}" 
                                   itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                   property="#{item.adenda.intSeguSucursalPk}"/>
                    </rich:column>
                    <rich:column width="90px">
                        <f:facet name="header">
                            <h:outputText value="Modalidad"/>
                        </f:facet>
                        <h:outputText value="#{item.strModalidad}"/>
                    </rich:column>
                    <!-- Modificado por cdelosrios, 08/12/2013 -->
                    <%--
                    <rich:column width="80px">
                        <f:facet name="header">
                            <h:outputText value="T. Socio"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
                                   itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                   property="#{item.estructuraDetalle.intParaTipoSocioCod}"/>
                    </rich:column>--%>
                    <!-- Fin modificado por cdelosrios, 08/12/2013 -->
                    <rich:column width="120px">
                        <f:facet name="header">
                            <h:outputText value="F. Registro"/>
                        </f:facet>
                        <h:outputText value="#{item.adenda.strFechaRegistro}"/>
                    </rich:column>
				    <f:facet name="footer">
						<rich:datascroller for="tbControlProceso" maxPages="5"/>   
					</f:facet>
					<a4j:support event="onRowClick" actionListener="#{controlProcesoController.listarPerfilConvenio}" reRender="pControlProceso,btnPanel">
						<f:param name="intIdConvenio" 		value="#{item.adenda.id.intConvenio}"/>
	                 	<f:param name="intIdItemConvenio" 	value="#{item.adenda.id.intItemConvenio}"/>
	                 	<f:param name="intIdEstadoConv" 	value="#{item.adenda.intParaEstadoHojaPlan}"/>
	                </a4j:support>
				</rich:extendedDataTable>
				</h:panelGrid>
			</div>
			
			<h:panelGrid columns="2">
				<a4j:commandLink styleClass="no-decor" reRender="idTabHojaPlaneamiento, pgFormHojaPlaneamiento,pgHojaPlaneamiento, pHojaPlan"
					actionListener="#{hojaPlaneamientoController.irModificarHojaPlaneamientoView}" 
					oncomplete="#{rich:component('pgHojaPlaneamiento')}.show()">
	                <h:graphicImage value="/images/icons/mensaje1.jpg" alt="edit"/>
	            </a4j:commandLink>
				<h:outputText value="Para Analizar y Ver PLANEAMIENTO DE CONVENIO o Imprimir la HOJA DE CONTROL DE CONVENIO hacer click en el Registro" style="color:#8ca0bd" />                                     
			</h:panelGrid>
		</rich:panel>
       	
  		<h:panelGrid id="btnPanel" columns="4">
          <a4j:commandButton id="btnValidar" value="Validar" styleClass="btnEstilos" disabled="#{controlProcesoController.enableDisableBtnValidar}" actionListener="#{controlProcesoController.validarConvenio}" reRender="pControlProceso,pgControlProceso"/>
          <a4j:commandButton id="btnAprobar" value="Aprobar" styleClass="btnEstilos" disabled="#{controlProcesoController.enableDisableBtnAprobar}" actionListener="#{controlProcesoController.aprobarRechazarConvenio}" reRender="pControlProceso,pgControlProceso"/>
          <a4j:commandButton id="btnRechazar" value="Rechazar" styleClass="btnEstilos" disabled="#{controlProcesoController.enableDisableBtnRechazar}" actionListener="#{controlProcesoController.aprobarRechazarConvenio}" reRender="pControlProceso,pgControlProceso"/>
          <a4j:commandButton id="btnCancelar" value="Cancelar" styleClass="btnEstilos" disabled="#{controlProcesoController.enableDisableBtnCancelar}" actionListener="#{controlProcesoController.cancelarConvenio}" reRender="pControlProceso"/>
    	</h:panelGrid>
    	
    	<h:panelGrid id="pControlProceso">
	    	<rich:panel style="width: 930px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{controlProcesoController.formHojaControlRendered}">
	    		<h:panelGrid>
	    			<h:outputText value="ANALISIS Y VALIDACION"  style="font-weight:bold;"/>
	    		</h:panelGrid>
	    		<rich:spacer height="4px;"/>
	    		<h:panelGrid>
	    			<h:outputText value="AREA" style="font-weight:bold;padding-left:20px;"/>
	    		</h:panelGrid>
	    		<h:panelGrid columns="4">
	    			<rich:column width="120px" style="padding-left:20px;">
	    				<h:outputText value="Perfil"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:inputText value="#{controlProcesoController.usuario.perfil.strDescripcion}" size="80" disabled="true"/>
	    			</rich:column>
	    			<rich:column style="padding-left:20px;padding-right:20px;">
	    				<h:outputText value="Estado"/>
	    			</rich:column>
	    			<rich:column width="150px" style="border-style:solid;border-width:1px;border-color:#000099;font-style:italic;">
	    				<div align="center">
	    					<h:outputText value="#{controlProcesoController.intEstConvenio == applicationScope.Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO ? 'Pendiente de Validar' :
	    											controlProcesoController.intEstConvenio == applicationScope.Constante.PARAM_T_ESTADODOCUMENTO_APROBADO ? 'Aprobado' : 
	    											controlProcesoController.intEstConvenio == applicationScope.Constante.PARAM_T_ESTADODOCUMENTO_RECHAZADO ? 'Rechazado' : ''}"/>
	    					<%--<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
	                            itemValue="intIdDetalle" itemLabel="strDescripcion"
	                            property="#{controlProcesoController.intEstConvenio}"/>--%>
	    				</div>
	    			</rich:column>
	    		</h:panelGrid>
	    		
	    		<h:panelGrid columns="2">
	    			<rich:column width="120px" style="padding-left:20px;">
	    				<h:outputText value="Nombre"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value="#{controlProcesoController.usuario.persona.natural.strApellidoPaterno} #{controlProcesoController.usuario.persona.natural.strApellidoMaterno} #{controlProcesoController.usuario.persona.natural.strNombres}"/>
	    			</rich:column>
	    		</h:panelGrid>
	    		
	    		<h:panelGrid id="pgMsgError">
		   			<h:outputText value="#{controlProcesoController.msgTxtEncargadoConvenio}" styleClass="msgError"/>
		   			<h:outputText value="#{controlProcesoController.msgTxtJefaturaCredito}"   styleClass="msgError"/>
		   			<h:outputText value="#{controlProcesoController.msgTxtJefaturaCobranza}"  styleClass="msgError"/>
		   			<h:outputText value="#{controlProcesoController.msgTxtAsesorLegal}" 	  styleClass="msgError"/>
		   			<h:outputText value="#{controlProcesoController.msgTxtGerenciaGeneral}"   styleClass="msgError"/>
		   		</h:panelGrid>
	    		
	    		<h:panelGrid columns="3">
	    			<rich:column>
	    				<rich:extendedDataTable id="dtEncConv" value="#{controlProcesoController.listaEncargadoConvenio}"
			                   	var="item" rowKeyVar="rowKey" width="260px" height="250px" enableContextMenu="false">
	                         <rich:column width="35">
	                             <h:selectBooleanCheckbox value="#{item.chkValor}" disabled="#{controlProcesoController.enableDisableEncConv}"/>
	                         </rich:column>
	                         <rich:column width="220px">
	                            <f:facet name="header">
	                            	<h:outputText value="Encargado Convenios"/>
	                            </f:facet>
	                            <h:panelGroup rendered="#{item.chkValor == null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilValidacion.id.intParaValidacionCod}"/>
                                </h:panelGroup>
                                <h:panelGroup rendered="#{item.chkValor != null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilDetalle.id.intParaValidacionCod}"/>
                                </h:panelGroup>
	                         </rich:column>
			             </rich:extendedDataTable>
			             <h:outputText value="Observación:" /><br/>
			             <h:inputTextarea rows="5" cols="48" value="#{controlProcesoController.strObsEncargConv}" disabled="#{controlProcesoController.enableDisableEncConv}"/>
	    			</rich:column>
	    			
	    			<rich:column>
	    				<rich:extendedDataTable id="dtJefCred" value="#{controlProcesoController.listaJefaturaCredito}"
			                   	var="item" rowKeyVar="rowKey" width="310px" height="250px" enableContextMenu="false">
	                         <rich:column width="35">
	                             <h:selectBooleanCheckbox value="#{item.chkValor}" disabled="#{controlProcesoController.enableDisableJefCred}"/>
	                         </rich:column>
	                         <rich:column width="270px">
	                             <f:facet name="header">
	                                 <h:outputText value="Jefatura de Créditos"/>
	                             </f:facet>
	                             <h:panelGroup rendered="#{item.chkValor == null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilValidacion.id.intParaValidacionCod}"/>
                                </h:panelGroup>
                                <h:panelGroup rendered="#{item.chkValor != null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilDetalle.id.intParaValidacionCod}"/>
                                </h:panelGroup>
	                         </rich:column>
			             </rich:extendedDataTable>
			             <h:outputText value="Observación:" /><br/>
			             <h:inputTextarea rows="5" cols="58" value="#{controlProcesoController.strObsJefatCred}" disabled="#{controlProcesoController.enableDisableJefCred}"/>
	    			</rich:column>
	    			
	    			<rich:column>
	    				<rich:extendedDataTable id="dtJefCob" value="#{controlProcesoController.listaJefaturaCobranza}"
		                   	var="item" rowKeyVar="rowKey" width="260px" height="250px" enableContextMenu="false">
		                         <rich:column width="35">
		                             <h:selectBooleanCheckbox value="#{item.chkValor}" disabled="#{controlProcesoController.enableDisableJefCobr}"/>
		                         </rich:column>
		                         <rich:column width="220px">
		                             <f:facet name="header">
		                                 <h:outputText value="Jefatura de Cobranza"/>
		                             </f:facet>
		                             <h:panelGroup rendered="#{item.chkValor == null}">
		                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
	                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                             property="#{item.perfilValidacion.id.intParaValidacionCod}"/>
	                                 </h:panelGroup>
	                                 <h:panelGroup rendered="#{item.chkValor != null}">
		                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
	                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                             property="#{item.perfilDetalle.id.intParaValidacionCod}"/>
	                                 </h:panelGroup>
		                         </rich:column>
			             </rich:extendedDataTable>
			             <h:outputText value="Observación:" /> <br/>
			             <h:inputTextarea  rows="5" cols="48" value="#{controlProcesoController.strObsJefatCobr}" disabled="#{controlProcesoController.enableDisableJefCobr}"/>
	    			</rich:column>
	    		</h:panelGrid>
	    		
	    		<h:panelGrid columns="2">
	    			<rich:column>
	    				<rich:extendedDataTable  id="dtAsesLegal" value="#{controlProcesoController.listaAsesorLegal}"
			                   	var="item" rowKeyVar="rowKey" width="510px" height="250px" enableContextMenu="false">
	                         <rich:column width="35">
	                             <h:selectBooleanCheckbox value="#{item.chkValor}" disabled="#{controlProcesoController.enableDisableAsesLeg}"/>
	                         </rich:column>
	                         <rich:column width="470px">
	                             <f:facet name="header">
	                                 <h:outputText value="Asesor Legal"/>
	                             </f:facet>
	                             <h:panelGroup rendered="#{item.chkValor == null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilValidacion.id.intParaValidacionCod}"/>
                                 </h:panelGroup>
                                 <h:panelGroup rendered="#{item.chkValor != null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilDetalle.id.intParaValidacionCod}"/>
                                 </h:panelGroup>
	                         </rich:column>
			             </rich:extendedDataTable>
			             <h:outputText value="Observación:" /><br/>
			             <h:inputTextarea rows="5" cols="98" value="#{controlProcesoController.strObsAsesLegal}" disabled="#{controlProcesoController.enableDisableAsesLeg}"/>
	    			</rich:column>
	    			
	    			<rich:column>
	    				<rich:extendedDataTable id="dtGerencia" value="#{controlProcesoController.listaGerenciaGeneral}"
			                   	var="item" rowKeyVar="rowKey" width="250px" height="250px" enableContextMenu="false" 
			                   	noDataLabel="No Hay Datos." >
	                         <rich:column width="220px">
	                             <f:facet name="header">
	                                 <h:outputText value="Gerencia General"/>
	                             </f:facet>
	                             <h:panelGroup rendered="#{item.chkValor == null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilValidacion.id.intParaValidacionCod}"/>
                                 </h:panelGroup>
                                 <h:panelGroup rendered="#{item.chkValor != null}">
	                            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ITEMSVALIDACONVENIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.perfilDetalle.id.intParaValidacionCod}"/>
                                 </h:panelGroup>
	                         </rich:column>
			             </rich:extendedDataTable>
			             <h:outputText value="Observación:"/> <br/>
			             <h:inputTextarea  rows="5" cols="46" value="#{controlProcesoController.strObsGerencGral}" disabled="#{controlProcesoController.enableDisableAsistGer}"/>
	    			</rich:column>
	    		</h:panelGrid>
	    	</rich:panel>
   		</h:panelGrid>