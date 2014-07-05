<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Convenio                 -->
	<!-- Prototipo : EDICIÓN DEL CONVENIO 	  -->
	<!-- Fecha     : 05/06/2012               -->

	<script type="text/javascript">
		
	</script>
	
	<rich:panel id="pnConvenio" rendered="#{convenioController.formConvenioRendered}" style="width:975px;border:1px solid #17356f;background-color:#DEEBF5;">
	 	<h:panelGrid columns="8">
	 		<rich:column width="150"><h:outputText value="Tipo de Convenio" /></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column>
		 		<h:selectOneRadio value="#{convenioController.intRbTipoConvenio}" disabled="#{convenioController.formTipoConvEnabled}">
	         		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONVENIO}"
	         			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
	         			propertySort="intOrden"/>
					<a4j:support event="onclick" reRender="pgBusqHojaPlan,tbTablasMenu" actionListener="#{convenioController.enableDisableControls}"/>
	          	</h:selectOneRadio>
	        </rich:column>
	        <rich:column><h:outputText value="Estado del Convenio :" /></rich:column>
	 		<rich:column>
		 		<h:selectOneMenu value="#{convenioController.beanAdenda.intParaEstadoConvenioCod}">
	         		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
	         			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	</h:selectOneMenu>
	        </rich:column>
	        <rich:column width="100">
	        	<h:outputText value="#{convenioController.msgTipoConvenio}" styleClass="msgError"/>
	        </rich:column>
	        <rich:column width="150px" style="padding-left:10px;"><h:outputText value="Código de Convenio :"  style="padding-left:10px;font-weight:bold;"/></rich:column>
	        <rich:column><h:outputText style="font-weight:bold;" value="#{convenioController.beanAdenda.id.intConvenio}"/></rich:column>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid>
	 		<h:outputText value="#{convenioController.msgTxtNuevaAdenda}" styleClass="msgError"/>
	 	</h:panelGrid>
	 	
	 	<h:panelGrid id="pgBusqHojaPlan" columns="3">
	 		<rich:column width="150"><h:outputText value="Hoja de Planeamiento"/></rich:column>
	 		<rich:column><h:outputText value=":"/></rich:column>
	 		<rich:column>
	 			<a4j:commandButton value="Buscar" actionListener="#{convenioController.listarHojaPlaneamiento}"
	 				oncomplete="Richfaces.showModalPanel('panelHojaPlaneamiento').show()" reRender="pgBusqHojaPlan,tbHojaPlan" 
	         		disabled="#{convenioController.btnBuscarEnabled}" styleClass="btnEstilos"/>
	        </rich:column>
	 	</h:panelGrid>
	 	
	 	<rich:panel style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
	 		<h:panelGrid id="pgListEstructOrganica" columns="3">
	 			<rich:column width="120px">
	 				<h:outputText value="Estructura Orgánica"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<rich:extendedDataTable id="tbEstructuraHojaPlan" value="#{convenioController.listaConvenioEstructuraDet}"
						var="item" rowKeyVar="rowKey" rows="5" enableContextMenu="false"
						rendered="#{not empty convenioController.listaConvenioEstructuraDet}"
						width="600px" height="170px">
		                <rich:column width="15">
		                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
		                </rich:column>
		                <rich:column width="250px">
		                    <f:facet name="header">
		                        <h:outputText value="Ministerio"/>
		                    </f:facet>
		                    <h:outputText value="#{item.estructura.juridica.strRazonSocial}"/>
		                </rich:column>
		                <rich:column width="150px">
		                    <f:facet name="header">
		                        <h:outputText value="Unidad Ejecutora"/>
		                    </f:facet>
		                    <h:outputText />
		                </rich:column>
		                <rich:column width="95px">
		                    <f:facet name="header">
		                        <h:outputText value="Modalidad"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.estructuraDetalle.intParaModalidadCod}"/>
		                </rich:column>
		                <rich:column>
		                    <f:facet name="header">
		                        <h:outputText value="Tipo Socio"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.estructuraDetalle.intParaTipoSocioCod}"/>
		                </rich:column>
		                <f:facet name="footer">
		                	<rich:datascroller for="tbEstructuraHojaPlan" maxPages="30" />
		                </f:facet>
		            </rich:extendedDataTable>
	 			</rich:column>
	 		</h:panelGrid>
	 		
	 		<h:panelGrid id="pgDatosHojaPlan" columns="9">
	 			<rich:column width="120px">
	 				<h:outputText value="Fecha de Inicio"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:inputText value="#{convenioController.strDtFecInicio}" size="20" disabled="true"/>
	 			</rich:column>
	 			<rich:column width="120px">
	 				<h:outputText value="Fecha de Fin"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:inputText value="#{convenioController.strDtFecFin}" size="20" disabled="true"/>
	 			</rich:column>
	 			<rich:column width="120px">
	 				<h:outputText value="Límite de Convenio"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 			
	 				<h:inputText size="30" value="Fecha de última cuota del cronograma" 
	 					rendered="#{convenioController.beanAdenda.intOpcionFiltroCredito ==1}" disabled="true"/>
	 				<h:inputText size="30" value="Fecha de solicitud de crédito" 
	 					rendered="#{convenioController.beanAdenda.intOpcionFiltroCredito ==2}" disabled="true"/>
	 			</rich:column>
	 		</h:panelGrid>
	 		
	 		<h:panelGrid id="pgDatosHojaPlan2" columns="9">
	 			<rich:column width="120px">
	 				<h:outputText value="Fecha de Suscripción"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:inputText value="#{convenioController.strDtFecSuscripcion}" size="20" disabled="true"/>
	 			</rich:column>
	 			<rich:column width="120px">
	 				<h:outputText value="Plazo del Convenio"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:inputText value="#{convenioController.strPlazoConvenio}" size="20" disabled="true"/>
	 			</rich:column>
	 			<rich:column width="120px">
	 				<h:outputText value="Sucursal que gestiona"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<tumih:outputText value="#{convenioController.listSucursalHojaPlan}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{convenioController.intSucursalHojaPlan}"/>
	 			</rich:column>
	 		</h:panelGrid>
	 		
	 		<h:panelGrid id="pgDocAdjuntos" columns="8">
	 			<rich:column width="120px">
	 				<h:outputText value="Doc. Adjuntos"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value=":"/>
	 			</rich:column>
	 			<rich:column>
	 				<h:outputText value="Carta de Presentación :"/>
	 			</rich:column>
	 			<!-- Modificado por cdelosrios, 08/12/2013 -->
	 			<rich:column rendered="#{not empty convenioController.beanAdenda.archivoDocumentoCartaPresent}">
	 				<%--<h:commandLink value="#{convenioController.strCartaPresentacion}" actionListener="#{convenioController.descargarCartaPresentacion}">
					   <f:param name="fileCartaPresentacion" value="#{convenioController.strCartaPresentacion}" />
					</h:commandLink>--%>
					<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank">
						<f:attribute name="archivo" value="#{convenioController.beanAdenda.archivoDocumentoCartaPresent}"/>		
					</h:commandLink>
	 			</rich:column>
				<!-- Fin modificado por cdelosrios, 08/12/2013 -->
	 			<rich:column>
	 				<h:outputText value="Convenio Sugerido :"/>
	 			</rich:column>
	 			<!-- Modificado por cdelosrios, 08/12/2013 -->
	 			<rich:column rendered="#{not empty convenioController.beanAdenda.archivoDocumentoConvSugerido}">
	 				<%--<h:commandLink id="idConvSugerido" value="#{convenioController.strConvenioSugerido}" actionListener="#{convenioController.descargarConvenioOAdendaSugerida}">
					   <f:param name="fileConvSugerido" value="#{convenioController.strConvenioSugerido}" />
					</h:commandLink>--%>
					<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank">
						<f:attribute name="archivo" value="#{convenioController.beanAdenda.archivoDocumentoConvSugerido}"/>		
					</h:commandLink>
	 			</rich:column>
	 			<!-- Fin modificado por cdelosrios, 08/12/2013 -->
	 			<rich:column>
	 				<h:outputText value="Adenda Sugerida :"/>
	 			</rich:column>
	 			<!-- Modificado por cdelosrios, 08/12/2013 -->
	 			<rich:column rendered="#{not empty convenioController.beanAdenda.archivoDocumentoAdendaSugerida}">
	 				<%--<h:commandLink id="idAdenSugerida" value="#{convenioController.strAdendaSugerida}" actionListener="#{convenioController.descargarConvenioOAdendaSugerida}">
					   <f:param name="fileAdendaSugerida" value="#{convenioController.strAdendaSugerida}"/>
					</h:commandLink>--%>
					<h:commandLink  value=" Descargar" actionListener="#{fileUploadController.descargarArchivo}" target="_blank">
						<f:attribute name="archivo" value="#{convenioController.beanAdenda.archivoDocumentoAdendaSugerida}"/>		
					</h:commandLink>
	 			</rich:column>
	 			<!-- Fin modificado por cdelosrios, 08/12/2013 -->
	 		</h:panelGrid>
	 	</rich:panel>
	 	<br/>
	 	<h:selectBooleanCheckbox value="#{convenioController.chkRenovacionAutomatica}"/> Renovación Automática :
	 	
	 	<rich:panel style="border:none;">
		 	<h:panelGrid id="pgRetencPlla" columns="7">
		 		<rich:column>
		 			<h:selectBooleanCheckbox value="#{convenioController.chkRetencionPlla}">
		 				<a4j:support event="onclick" actionListener="#{convenioController.enableDisableControls}" reRender="pgRetencPlla"/>
		 			</h:selectBooleanCheckbox>
		 		</rich:column>
		 		<rich:column width="130px">
		 			<h:outputText value="Retención de Planilla"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:outputText value=":"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:inputText value="#{convenioController.beanAdenda.bdRetencion}" size="6" disabled="#{convenioController.formRetenPlla}"/>
		 		</rich:column>
		 		<rich:column>
		 			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORETENCION}" 
		                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                              property="#{convenioController.beanAdenda.intParaTipoRetencionCod}"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:selectManyCheckbox value="#{convenioController.lstModalidadTipoSocio}" layout="pageDirection">
		 				<tumih:selectItems var="sel" value="#{convenioController.listaConvenioEstructuraDet}"
		 					itemValue="#{sel.strCsvPkModalidadTipoSocio}" itemLabel="#{sel.strModalidadTipoSocio}"/>
		 			</h:selectManyCheckbox>
		 		</rich:column>
		 		<rich:column>
		 			<a4j:commandButton value="Generar Cronograma" actionListener="#{convenioController.generarCronograma}" styleClass="btnEstilos1" reRender="pgListRetencPlla"
		 								disabled="#{empty convenioController.listaConvenioEstructuraDet}"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:outputText value="#{convenioController.msgTxtRetencionPlla}" styleClass="msgError"/>
		 		</rich:column>
		 	</h:panelGrid>
		 	<h:panelGrid id="pgListRetencPlla" columns="2">
		 		<rich:column width="140px"/>
		 		<rich:column>
		 			<rich:extendedDataTable id="tbRetencPlla" value="#{convenioController.listaRetencPlla}"
						var="item" rowKeyVar="rowKey" width="660px" height="195px" enableContextMenu="false"
						rendered="#{not empty convenioController.listaRetencPlla}"
		                rows="#{convenioController.rows}">
		                <rich:column width="15">
		                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
		                </rich:column>
		                <div align="center">
		                <rich:column width="70px">
		                    <f:facet name="header">
		                        <h:outputText value="Solicitud"/>
		                    </f:facet>
		                    <h:panelGroup rendered="#{item.id.intItemRetPlla == null}">
		                    	<h:outputText value="#{rowKey+1}"/>
		                    </h:panelGroup>
		                    <h:panelGroup rendered="#{item.id.intItemRetPlla != null}">
		                    	<h:outputText value="#{item.id.intItemRetPlla}"/>
		                    </h:panelGroup>
		                </rich:column>
		                </div>
		                <%--
		                <rich:column width="130px">
		                    <f:facet name="header">
		                        <h:outputText value="Ministerio"/>
		                    </f:facet>
		                    <h:outputText />
		                </rich:column> --%>
		                <rich:column width="130px">
		                    <f:facet name="header">
		                        <h:outputText value="Período de Planilla"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
		                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                              property="#{item.intParaMesCod}"/>
							<h:outputText value=" - #{item.intAnio}"/>
		                </rich:column>
		                <rich:column>
		                    <f:facet name="header">
		                        <h:outputText value="Tipo de Socio"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
		                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                              property="#{item.intParaTipoSocioCod}"/>
		                </rich:column>
		                <rich:column width="80px">
		                    <f:facet name="header">
		                        <h:outputText value="Modalidad"/>
		                    </f:facet>
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
		                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                              property="#{item.intParaModalidadCod}"/>
		                </rich:column>
		                <rich:column width="130px">
		                    <f:facet name="header">
		                        <h:outputText value="Monto"/>
		                    </f:facet>
		                    <h:outputText value="#{convenioController.bdRetencion} "/> 
		                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORETENCION}" 
		                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                              property="#{convenioController.intParaTipoRetencion}"/>
		                </rich:column>
		                <rich:column>
		                    <f:facet name="header">
		                        <h:outputText value="Est. de Pago"/>
		                    </f:facet>
		                    <h:selectOneMenu value="#{item.intParaEstPagoCod}">
			                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICITUDPAGO}" 
			                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
			                       propertySort="intOrden"/>
			                </h:selectOneMenu>
		                </rich:column>
		                <%--
		                <rich:column>
		                	<a4j:commandLink id="lnkDeleteRetencPlla" styleClass="no-decor" reRender="pgListRetencPlla"
			            		actionListener="#{convenioController.eliminarRetencPlla}"
			            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
			                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
			                    <rich:toolTip for="lnkDeleteRetencPlla" value="Eliminar" followMouse="true"/>
			            	</a4j:commandLink>
			            </rich:column>--%>
		                
		                <f:facet name="footer">
		               		<rich:datascroller for="tbRetencPlla" maxPages="10"/>   
		                </f:facet>
		            </rich:extendedDataTable>
		 		</rich:column>
		 	</h:panelGrid>
		 	
		 	<h:panelGrid id="pgListDonacionRegalia" columns="3">
		 		<rich:column width="130px">
		 			<h:outputText value="Donación y Regalías"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:outputText value=":"/>
		 		</rich:column>
		 		<rich:column>
		 			<rich:dataTable id="tbDonacionRegalia" value="#{convenioController.listaDonacionRegalia}"
						var="item" rowKeyVar="rowKey" width="780px"
						rows="#{convenioController.rows}">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column/>
								<rich:column>
									<h:outputText value="Solicitud"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Tipo"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Concepto"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Tipo Moneda"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Monto"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Dcto. Compra"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Est. de Pago"/>
								</rich:column>
								<rich:column>
									<rich:spacer/>
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
							<rich:column width="15">
								<div align="center"><h:outputText value="#{rowKey+1}"/></div>
							</rich:column>
							<rich:column>
								<h:outputText value="---"/>
							</rich:column>
							<rich:column>
								<h:selectOneMenu value="#{item.intParaTipoDonacionCod}">
				                    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODONACIONCONVENIO}" 
				                    itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
				                    propertySort="intOrden"/>
				                </h:selectOneMenu>
							</rich:column>
							<rich:column width="140px">
								<h:inputText value="#{item.strDescripcion}" size="20"/>
							</rich:column>
							<rich:column width="135">
								<h:selectOneMenu value="#{item.intParaTipoMonedaCod}">
				                    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
				                    itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
				                    propertySort="intOrden"/>
				                </h:selectOneMenu>
							</rich:column>
							<rich:column width="80px">
								<h:inputText value="#{item.bdMonto}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" onkeypress="return blockNonNumbers(this, event, true, false);"/>
							</rich:column>
							<rich:column>
								<h:selectOneMenu value="#{item.intDctoCompra}">
				                    <f:selectItem itemLabel="Si" itemValue="1"/>
				                    <f:selectItem itemLabel="No" itemValue="0"/>
				                </h:selectOneMenu>
							</rich:column>
							<rich:column>
								<h:selectOneMenu value="#{item.intParaEstadoPagoCod}">
				                    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICITUDPAGO}" 
				                    itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
				                    propertySort="intOrden"/>
				                </h:selectOneMenu>
							</rich:column>
							<rich:column>
				            	<a4j:commandLink id="lnkDeleteDonacRegal" styleClass="no-decor" reRender="pgListDonacionRegalia"
				            		actionListener="#{convenioController.removeDonacionRegalia}"
				            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
				                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
				                    <f:param name="rowKeyDonacionRegalia" value="#{rowKey}"/>
				            	</a4j:commandLink>
				            </rich:column>
						</rich:columnGroup>
		            </rich:dataTable>
		            <a4j:commandButton value="Agregar Donación y Regalías" actionListener="#{convenioController.addDonacionRegalia}" styleClass="btnEstilos1" style="width:100%" reRender="pgListDonacionRegalia"/>
		 		</rich:column>
		 	</h:panelGrid>
		 	<br/>
		 	
		 	<h:panelGrid id="pgListFirmantes" columns="3">
		 		<rich:column width="130px">
		 			<h:outputText value="Firmantes del Convenio"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:outputText value=":"/>
		 		</rich:column>
		 		<rich:column>
		 			<rich:dataTable id="tbFirmantesConvenio" value="#{convenioController.listaFirmantes}"
		 				var="item" rowKeyVar="rowKey" width="780px" rows="#{convenioController.rows}">
		                <f:facet name="header">
		                	<rich:columnGroup>
		                		<rich:column width="15"/>
		                		<rich:column>
		                			<h:outputText value="Nombre Completo"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="DNI"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Cargo"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Empresa"/>
		                		</rich:column>
		                		<rich:column>
		                			<rich:spacer/>
		                		</rich:column>
		                	</rich:columnGroup>
		                </f:facet>
		                <rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		                	<rich:column width="15">
			                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
			                </rich:column>
			                <rich:column width="300px">
			                	<h:outputText value="#{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno} #{item.persona.natural.strNombres}"/>
			                </rich:column>
			                <rich:column width="80px">
			                	<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"/>
			                </rich:column>
			                <rich:column width="150px">
			                	<h:outputText value="#{item.persona.personaEmpresa.vinculo.intCargoVinculoCod}"/>
			                </rich:column>
			                <rich:column width="200px">
			                	<h:outputText value="#{item.persona.juridica.strRazonSocial}"/>
			                </rich:column>
			                <rich:column>
				            	<a4j:commandLink id="lnkDeleteFirmante" styleClass="no-decor" reRender="pgListFirmantes"
				            		actionListener="#{convenioController.removeFirmante}"
				            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
				                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
				                    <rich:toolTip for="lnkDeleteFirmante" value="Eliminar" direction="top-left" followMouse="true"/>
				                    <f:param name="rowKeyFirmante" value="#{rowKey}"/>
				            	</a4j:commandLink>
				            </rich:column>
		                </rich:columnGroup>
		            </rich:dataTable>
		            <a4j:commandButton value="Agregar Firmantes" style="width:100%" actionListener="#{convenioController.addFirmante}"
		 				oncomplete="Richfaces.showModalPanel('panelFirmantes').show()" reRender="pgListFirmantes,frmFirmante" styleClass="btnEstilos1"
		 				disabled="#{empty convenioController.listaConvenioEstructuraDet}"/>
		 		</rich:column>
		 	</h:panelGrid>
		 	<br/>
		 	
		 	<%--
		 	<h:panelGrid columns="3">
		 		<rich:column width="130px">
		 			<h:outputText value="Configuración de Productos"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:outputText value=":"/>
		 		</rich:column>
		 		<rich:column>
		 			<h:selectOneRadio>
		 				<f:selectItem itemLabel="Heredar Configuración principal" itemValue="1"/>
		 				<f:selectItem itemLabel="Continuar configuración de Adenda anterior" itemValue="2"/>
		 				<f:selectItem itemLabel="Nueva configuración" itemValue="3"/>
		 			</h:selectOneRadio>
		 		</rich:column>
		 	</h:panelGrid>
		 	--%>
		 	
		 	<h:panelGrid columns="2">
		 		<rich:column width="130px"/>
		 		<rich:column>
		 			<rich:panel style="width:800px;border:1px solid #17356f;background-color:#DEEBF5;">
		 				<h:panelGrid columns="3">
		 					<rich:column width="220px">
		 						Aportación
		 					</rich:column>
		 					<rich:column>
		 						<h:outputText value=":"/>
		 					</rich:column>
		 					<rich:column>
		 						<a4j:commandButton id="btnAportacion" value="Agregar" styleClass="btnEstilos" 
		 						oncomplete="Richfaces.showModalPanel('panelAportacion').show()"
		 						actionListener="#{convenioController.listarCaptacion}" reRender="frmAportacion"/>
		 					</rich:column>
		 				</h:panelGrid>
		 				
		 				<h:panelGrid id="pgListAportaciones">
		 					<rich:dataTable id="dtAportaciones" value="#{convenioController.listaAdendaAportacion}"
				 				var="item" rowKeyVar="rowKey" width="500px">
				                <f:facet name="header">
				                	<rich:columnGroup>
				                		<rich:column width="15"/>
				                		<rich:column>
				                			<h:outputText value="Descripción"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Tipo de Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<rich:spacer/>
				                		</rich:column>
				                	</rich:columnGroup>
				                </f:facet>
				                <rich:columnGroup rendered="#{item.captacion.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="15">
					                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
					                </rich:column>
					                <rich:column width="300px">
					                	<h:outputText value="#{item.captacion.strDescripcion}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? '%': 'S/.'}"/>
					                </rich:column>
					                <rich:column width="80px">
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? item.captacion.bdPorcConfiguracion: item.captacion.bdValorConfiguracion}"/>
					                </rich:column>
					                <rich:column>
						            	<a4j:commandLink id="lnkDeleteAportacion" styleClass="no-decor" reRender="pgListAportaciones"
						            		actionListener="#{convenioController.removeAportacion}"
						            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
						                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
						                    <rich:toolTip for="lnkDeleteAportacion" value="Eliminar" followMouse="true"/>
						                    <f:param name="rowKeyAportacion" value="#{rowKey}"/>
						            	</a4j:commandLink>
						            </rich:column>
				                </rich:columnGroup>
				            </rich:dataTable>
		 				</h:panelGrid>
		 				<br/>
		 				
		 				<h:panelGrid columns="3">
		 					<rich:column width="220px">
		 						Fondo de Sepelio
		 					</rich:column>
		 					<rich:column>
		 						<h:outputText value=":"/>
		 					</rich:column>
		 					<rich:column>
		 						<a4j:commandButton id="btnFondoSepelio" value="Agregar" styleClass="btnEstilos"
		 						oncomplete="Richfaces.showModalPanel('panelFondoSepelio').show()"
		 						actionListener="#{convenioController.listarCaptacion}" reRender="frmFondoSepelio"/>
		 					</rich:column>
		 				</h:panelGrid>
		 				
		 				<h:panelGrid id="pgListFondoSepelio">
		 					<rich:dataTable id="dtFondoSepelio" value="#{convenioController.listaAdendaFondoSepelio}"
				 				var="item" rowKeyVar="rowKey" width="500px">
				                <f:facet name="header">
				                	<rich:columnGroup>
				                		<rich:column width="15"/>
				                		<rich:column>
				                			<h:outputText value="Descripción"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Tipo de Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<rich:spacer/>
				                		</rich:column>
				                	</rich:columnGroup>
				                </f:facet>
				                <rich:columnGroup rendered="#{item.captacion.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="15">
					                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
					                </rich:column>
					                <rich:column width="300px">
					                	<h:outputText value="#{item.captacion.strDescripcion}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? '%': 'S/.'}"/>
					                </rich:column>
					                <rich:column width="80px">
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? item.captacion.bdPorcConfiguracion: item.captacion.bdValorConfiguracion}"/>
					                </rich:column>
					                <rich:column>
						            	<a4j:commandLink id="lnkDeleteFondoSepelio" styleClass="no-decor" reRender="pgListFondoSepelio"
						            		actionListener="#{convenioController.removeFondoSepelio}"
						            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
						                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
						                    <rich:toolTip for="lnkDeleteFondoSepelio" value="Eliminar" followMouse="true"/>
						                    <f:param name="rowKeyFondoSepelio" value="#{rowKey}"/>
						            	</a4j:commandLink>
						            </rich:column>
				                </rich:columnGroup>
				            </rich:dataTable>
		 				</h:panelGrid>
		 				<br/>
		 				
		 				<h:panelGrid columns="3">
		 					<rich:column width="220px">
		 						Fondo de Retiro
		 					</rich:column>
		 					<rich:column>
		 						<h:outputText value=":"/>
		 					</rich:column>
		 					<rich:column>
		 						<a4j:commandButton id="btnFondoRetiro" value="Agregar" styleClass="btnEstilos"
		 						oncomplete="Richfaces.showModalPanel('panelFondoRetiro').show()"
		 						actionListener="#{convenioController.listarCaptacion}" reRender="frmFondoRetiro"/>
		 					</rich:column>
		 				</h:panelGrid>
		 				
		 				<h:panelGrid id="pgListFondoRetiro">
		 					<rich:dataTable id="dtFondoRetiro" value="#{convenioController.listaAdendaFondoRetiro}"
				 				var="item" rowKeyVar="rowKey" width="500px">
				                <f:facet name="header">
				                	<rich:columnGroup>
				                		<rich:column width="15"/>
				                		<rich:column>
				                			<h:outputText value="Descripción"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Tipo de Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<rich:spacer/>
				                		</rich:column>
				                	</rich:columnGroup>
				                </f:facet>
				                <rich:columnGroup rendered="#{item.captacion.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="15">
					                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
					                </rich:column>
					                <rich:column width="300px">
					                	<h:outputText value="#{item.captacion.strDescripcion}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? '%': 'S/.'}"/>
					                </rich:column>
					                <rich:column width="80px">
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? item.captacion.bdPorcConfiguracion: item.captacion.bdValorConfiguracion}"/>
					                </rich:column>
					                <rich:column>
						            	<a4j:commandLink id="lnkDeleteFondoRetiro" styleClass="no-decor" reRender="pgListFondoRetiro"
						            		actionListener="#{convenioController.removeFondoRetiro}"
						            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
						                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
						                    <rich:toolTip for="lnkDeleteFondoRetiro" value="Eliminar" followMouse="true"/>
						                    <f:param name="rowKeyFondoRetiro" value="#{rowKey}"/>
						            	</a4j:commandLink>
						            </rich:column>
				                </rich:columnGroup>
				            </rich:dataTable>
		 				</h:panelGrid>
		 				<br/>
		 				
		 				<h:panelGrid columns="3">
		 					<rich:column width="220px">
		 						Mantenimiento de Cuenta
		 					</rich:column>
		 					<rich:column>
		 						<h:outputText value=":"/>
		 					</rich:column>
		 					<rich:column>
		 						<a4j:commandButton id="btnMantCuenta" value="Agregar" styleClass="btnEstilos"
		 						oncomplete="Richfaces.showModalPanel('panelMantCuenta').show()"
		 						actionListener="#{convenioController.listarCaptacion}" reRender="frmMantCuenta"/>
		 					</rich:column>
		 				</h:panelGrid>
		 				
		 				<h:panelGrid id="pgListMantCuenta">
		 					<rich:dataTable id="dtMantCuenta" value="#{convenioController.listaAdendaMantCta}"
				 				var="item" rowKeyVar="rowKey" width="500px">
				                <f:facet name="header">
				                	<rich:columnGroup>
				                		<rich:column width="15"/>
				                		<rich:column>
				                			<h:outputText value="Descripción"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Tipo de Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Valor"/>
				                		</rich:column>
				                		<rich:column>
				                			<rich:spacer/>
				                		</rich:column>
				                	</rich:columnGroup>
				                </f:facet>
				                <rich:columnGroup rendered="#{item.captacion.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="15">
					                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
					                </rich:column>
					                <rich:column width="300px">
					                	<h:outputText value="#{item.captacion.strDescripcion}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? '%': 'S/.'}"/>
					                </rich:column>
					                <rich:column width="80px">
					                	<h:outputText value="#{item.captacion.bdValorConfiguracion==null? item.captacion.bdPorcConfiguracion: item.captacion.bdValorConfiguracion}"/>
					                </rich:column>
					                <rich:column>
						            	<a4j:commandLink id="lnkDeleteMantCuenta" styleClass="no-decor" reRender="pgListMantCuenta"
						            		actionListener="#{convenioController.removeMantCuenta}"
						            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
						                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
						                    <rich:toolTip for="lnkDeleteMantCuenta" value="Eliminar" followMouse="true"/>
						                    <f:param name="rowKeyMantCuenta" value="#{rowKey}"/>
						            	</a4j:commandLink>
						            </rich:column>
				                </rich:columnGroup>
				            </rich:dataTable>
		 				</h:panelGrid>
		 				<br/>
		 				
		 				<h:panelGrid columns="3">
		 					<rich:column width="220px">
		 						Créditos
		 						<h:selectOneMenu value="#{convenioController.intParaTipoCreditoCod}">
		 							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
				                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
				                       propertySort="intOrden" />
		 						</h:selectOneMenu>
		 					</rich:column>
		 					<rich:column>
		 						<h:outputText value=":"/>
		 					</rich:column>
		 					<rich:column>
		 						<a4j:commandButton value="Agregar" styleClass="btnEstilos"
		 						oncomplete="Richfaces.showModalPanel('panelCredito').show()"
		 						actionListener="#{convenioController.listarCreditosPorTipoCredito}" reRender="frmCredito"/>
		 					</rich:column>
		 				</h:panelGrid>
		 				<br/>
		 				<h:panelGrid id="pgListCredito">
		 					<rich:dataTable id="dtCredito" value="#{convenioController.listaAdendaCredito}"
				 				var="item" rowKeyVar="rowKey" width="600px">
				                <f:facet name="header">
				                	<rich:columnGroup>
				                		<rich:column width="15"/>
				                		<rich:column>
				                			<h:outputText value="Tipo de Crédito"/>
				                		</rich:column>
				                		<rich:column width="120px">
				                			<h:outputText value="Según Empresa"/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Monto Min."/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="% Min."/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="Monto Max."/>
				                		</rich:column>
				                		<rich:column>
				                			<h:outputText value="% Max."/>
				                		</rich:column>
				                		<rich:column><rich:spacer/></rich:column>
				                	</rich:columnGroup>
				                </f:facet>
				                <rich:columnGroup rendered="#{item.credito.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="15">
					                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
					                </rich:column>
					                <rich:column>
					                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
	                                     itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                     property="#{item.credito.id.intParaTipoCreditoCod}"/>
					                </rich:column>
					                <rich:column>
					                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
	                                     itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                     property="#{item.credito.intParaTipoCreditoEmpresa}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.credito.bdMontoMinimo}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.credito.bdPorcMinimo}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.credito.bdMontoMaximo}"/>
					                </rich:column>
					                <rich:column>
					                	<h:outputText value="#{item.credito.bdPorcMaximo}"/>
					                </rich:column>
					                <rich:column>
						            	<a4j:commandLink id="lnkDeleteCredito" styleClass="no-decor" reRender="pgListCredito"
						            		actionListener="#{convenioController.removeCredito}"
						            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
						                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
						                    <rich:toolTip for="lnkDeleteCredito" value="Eliminar" followMouse="true"/>
						                    <f:param name="rowKeyCredito" value="#{rowKey}"/>
						            	</a4j:commandLink>
						            </rich:column>
				                </rich:columnGroup>
				            </rich:dataTable>
		 				</h:panelGrid>
		 				
		 			</rich:panel>
		 		</rich:column>
		 	</h:panelGrid>
	 	</rich:panel>
	</rich:panel>