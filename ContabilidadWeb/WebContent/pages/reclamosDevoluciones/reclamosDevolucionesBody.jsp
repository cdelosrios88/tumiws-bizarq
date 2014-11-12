<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Autor     : Rodolfo Villarreal    			-->
<!-- Modulo    : Tributacion               		-->
<!-- Prototipo : PROTOTIPO TRIBUTACION		    -->
<!-- Fecha     : 25/07/2014               		-->
<!-- Creación     	: Rodolfo Villarreal 		-->
<!-- Fecha Modificación : 08/08/2014            -->
<a4j:include viewId="/pages/reclamosDevoluciones/popup/popPersona.jsp"/>
<a4j:include viewId="/pages/reclamosDevoluciones/popup/popAdjuntarDocumento.jsp"/>
<a4j:include viewId="/pages/reclamosDevoluciones/popup/popBotonesReclamo.jsp"/>

<a4j:region>
		
	<a4j:jsFunction name="selecTipoReclamo" reRender="cboFiltroReclamo"
		action="#{reclamosDevolucionesController.recargarCboFiltroReclamos}">
		<f:param name="pTipoReclamo"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroReclamo"
		action="#{reclamosDevolucionesController.disableTxtReclamoBusq}">
		<f:param name="pFiltroReclamoBusq"></f:param>
	</a4j:jsFunction>
	
	<!--Inicio combos cabecera-->
	<a4j:jsFunction name="selecTipoReclamoCab" reRender="cboFiltroReclamoCab"
		action="#{reclamosDevolucionesController.recargarCboFiltroReclamosCab}">
		<f:param name="pTipoReclamoCab"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroReclamoCab"
		action="#{reclamosDevolucionesController.disableTxtReclamoBusqCab}">
		<f:param name="pFiltroReclamoBusq"></f:param>
	</a4j:jsFunction>
	<!--Fin combos cabecera-->
	
	<a4j:jsFunction name="selecTipoPersona" reRender="cboFiltroPersona"
		actionListener="#{reclamosDevolucionesController.reloadCboFiltroPersona}">
		<f:param name="pTipoPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersona" reRender="txtPersonaBusq"
		actionListener="#{reclamosDevolucionesController.disableTxtPersonaBusq}">
		<f:param name="pFiltroPersonaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarPersRect" reRender="fullNameRect"
		action="#{reclamosDevolucionesController.seleccionarPersRect}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionRect')">
		<f:param name="pRowRect"></f:param>
	</a4j:jsFunction>
	
</a4j:region>

<h:form id="frmTributacion">
	<h:outputLabel value="#{reclamosDevolucionesController.inicioPage}"/>
	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
	   	<h:panelGroup id="divTributacion" layout="block" style="padding:15px">
	   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="RECLAMOS O DEVOLUCIONES" style="font-weight:bold; font-size:14px"></h:outputText>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:selectOneMenu value="#{reclamosDevolucionesController.reclamoBusq.intParaDocumentoGeneral}"
	    								 onchange="selecTipoReclamoCab(#{applicationScope.Constante.ONCHANGE_VALUE})"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{reclamosDevolucionesController.listParametro}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu id="cboFiltroReclamoCab" value="#{reclamosDevolucionesController.reclamoBusq.intReclamoDevolucion}"
	    									onchange="selecFiltroReclamoCab(#{applicationScope.Constante.ONCHANGE_VALUE})"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<f:selectItems value="#{reclamosDevolucionesController.cboFiltroReclamoCab}"/>
						</h:selectOneMenu>
	    			</rich:column>
		    		<rich:column width="85" style="text-align: center;">
		         		<h:outputText value="Fecha Registro: "/>
		         	</rich:column>
		         	<rich:column>
	           				<rich:calendar value="#{reclamosDevolucionesController.reclamoBusq.tsRedeFechaRegistro}" 
	    							datePattern="dd/MM/yyyy" converter="calendarTimestampConverter" style="width:70px"></rich:calendar>
	           				</rich:column>
	    			<rich:column>
	    				<h:outputText value="Estado:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{reclamosDevolucionesController.reclamoBusq.intParaEstadoCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{reclamosDevolucionesController.listActivoCab}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value="Pago:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{reclamosDevolucionesController.reclamoBusq.intParaEstadoPago}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{reclamosDevolucionesController.listPago}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton  action="#{reclamosDevolucionesController.buscarReclamo}" value="Buscar" 
	    								reRender="divTblTributacion" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	<%--inicio busqueda --%>
	    	<h:panelGroup id="divTblTributacion" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblTributacion"
	    					value="#{reclamosDevolucionesController.listBuscar}" 
	    					rendered="#{not empty reclamosDevolucionesController.listBuscar}"
	                        var="item" 
	                        rowKeyVar="rowKey" 
	                        rows="5" 
	                        width="840px" 
	                        height="179px">
	                        <rich:column width="21">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column width="50">
	                        <f:facet name="header">
	                            <h:outputText value="Código"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.id.intContItemReclamoDevolucion}" title="#{item.id.intContItemReclamoDevolucion}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tsRedeFechaRegistro}" title="#{item.tsRedeFechaRegistro}">
	                        	<f:convertDateTime type="tsImpuFechaRegistro" pattern="dd/MM/yyyy"/>
	                        </h:outputText>
						</rich:column>  
			            <rich:column width="90">
			            	<f:facet name="header">
	                            <h:outputText value="Documento"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDocumentoGeneral}" title="#{item.strDocumentoGeneral}"></h:outputText>
			            </rich:column>
			            <rich:column width="200">
	                        <f:facet name="header">
	                            <h:outputText value="Tipo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strReclamoDevolucion}" title="#{item.strReclamoDevolucion}"></h:outputText>
			            </rich:column>
			            <rich:column width="250">
	                        <f:facet name="header">
	                            <h:outputText value="Personal Afectado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strNombreCompleto}" title="#{item.strNombreCompleto}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEstado}" title="#{item.strEstado}"></h:outputText>
			            </rich:column>
			            <rich:column width="100">
	                        <f:facet name="header">
	                            <h:outputText value="Cobro/Pago"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescripcionEstado}" title="#{item.strDescripcionEstado}"></h:outputText>
			            </rich:column>
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblTributacion" maxPages="10"/>
				   		 </h:panelGroup>    
			       </f:facet>
			       <a4j:support event="onRowClick"
						actionListener="#{reclamosDevolucionesController.seleccionarReclamo}"
						oncomplete="Richfaces.showModalPanel('mpModificarReclamo')"
						reRender="formBotonesReclamo,pgBotones">
						<f:attribute name="itemReclamoBusq" value="#{item}" />
					</a4j:support> 
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	            <%--fin busqueda--%>
	       	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
	           
	           <h:panelGrid columns="3" id="pgBotones">
	           		<rich:column>
	           			<a4j:commandButton value="Nuevo" action="#{reclamosDevolucionesController.motrarPanel}"
	           							disabled="#{reclamosDevolucionesController.blnVisible}"
	           							reRender="opReclamo,pgBotones" styleClass="btnEstilos" style=" width : 122px;"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Grabar" action="#{reclamosDevolucionesController.guardarReclamos}" 
	           							disabled="#{reclamosDevolucionesController.blnVisible}"
	           							reRender="opReclamo,pgBotones" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Cancelar" action="#{reclamosDevolucionesController.limpiarFomularioReclamo}" 
	           							reRender="opReclamo,pgBotones,frmTributacion" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           </h:panelGrid>
	   	</h:panelGroup>
	   	<!-- inicio -->
	   	<a4j:outputPanel id="opReclamo" >
	   		<!-- Formulario Tributación Nuevo -->
	           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px" rendered="#{reclamosDevolucionesController.blnShowPanelInferior}">
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4" >
	           			<rich:columnGroup>
	           				<rich:column width="165">
		           				<h:outputText value="Código"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           			<h:inputText value="#{reclamosDevolucionesController.reclamoDev.id.intContItemReclamoDevolucion}" disabled="true"></h:inputText>
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column>
		           				<h:outputText value="Fecha :"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           				<h:inputText value="#{reclamosDevolucionesController.daFechaReg}" disabled="true"></h:inputText>
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column >
		           				<h:outputText value="Estado :"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           				<h:inputText value="#{reclamosDevolucionesController.strEstado}" disabled="true"></h:inputText>
		           			</rich:column>
	           			</rich:columnGroup>
	           			</h:panelGrid>
	           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
		           			<rich:columnGroup>
		           				<rich:column width="165">
		           					<h:outputText value="Tipo de Documento"></h:outputText>
		           				</rich:column>
		           				<rich:column>
		           					<h:selectOneMenu value="#{reclamosDevolucionesController.reclamoDev.intParaDocumentoGeneral}" 
		           									 disabled="#{reclamosDevolucionesController.blnVisible}"
		           									 onchange="selecTipoReclamo(#{applicationScope.Constante.ONCHANGE_VALUE})">
	    								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    								<tumih:selectItems var="sel" value="#{reclamosDevolucionesController.listParametro}" 
								  						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    							</h:selectOneMenu>
		           				</rich:column>
		           			</rich:columnGroup>
	           			</h:panelGrid>
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
		           			<rich:columnGroup>
		           				<rich:column width="165">
		           					<h:outputText value="Tipo Reclamo (Devolución)"></h:outputText>
		           				</rich:column>
		           				<rich:column>
				    				<h:selectOneMenu id="cboFiltroReclamo" value="#{reclamosDevolucionesController.reclamoDev.intReclamoDevolucion}"
				    									disabled="#{reclamosDevolucionesController.blnVisible}" 
				    									onchange="selecFiltroReclamo(#{applicationScope.Constante.ONCHANGE_VALUE})">
				    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
				    					<f:selectItems value="#{reclamosDevolucionesController.cboFiltroReclamo}"/>
				    				</h:selectOneMenu>
				    			</rich:column>
		           			</rich:columnGroup>
	           			</h:panelGrid>
						           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           			<rich:columnGroup>
	           				<rich:column width="165">
	           					<h:outputText value="Personal Afectado"></h:outputText>
	           				</rich:column>
	           				<rich:column colspan="5">
	           					<h:inputText id="fullNameRect" value="#{reclamosDevolucionesController.strConcatenado}" size="82" disabled="true"></h:inputText>
	           				</rich:column>
	           				<rich:column width="50">
	           			<a4j:commandButton value="Buscar Empleado" action="#{reclamosDevolucionesController.limpiar}" styleClass="btnEstilos" disabled="#{reclamosDevolucionesController.blnVisible}"
	           								reRender="fullNameRect,mpSeleccionRect" oncomplete="Richfaces.showModalPanel('mpSeleccionRect')" style=" width : 144px;"></a4j:commandButton>
	           		</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           				<rich:column  width="165">
	           					<h:outputText value="Monto"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputText value="#{reclamosDevolucionesController.reclamoDev.bdRedeMonto}"
	           						onblur="extractNumber(this,2,false);"
				   					onkeyup="extractNumber(this,2,false);"
				   					onkeypress="return soloNumerosDecimales(this)"
	           						style="width: 100px;"
	           						disabled="#{reclamosDevolucionesController.blnVisible}">
	           					</h:inputText>
	           				</rich:column>
	           				<rich:column>
	           				<h:selectOneMenu style="width:120px" value="#{reclamosDevolucionesController.reclamoDev.intTipoMoneda}"
	           								disabled="#{reclamosDevolucionesController.blnVisible}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{reclamosDevolucionesController.listMoneda}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	           				</rich:column>
	           				<rich:column width="100">
				         	</rich:column>
	           				<rich:column>
				         		<h:outputText value="Planilla Afectada: "/>
				         	</rich:column>
				         	<rich:column>
				               	<h:selectOneMenu
									value="#{reclamosDevolucionesController.intMeses}"
									disabled="#{reclamosDevolucionesController.blnVisible}">
									<f:selectItem itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden"/>
								</h:selectOneMenu>
				            </rich:column>
				            <rich:column>
				           		<h:selectOneMenu
									style="width: 100px;" value="#{reclamosDevolucionesController.intPeriodo}"
									disabled="#{reclamosDevolucionesController.blnVisible}">
									<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel"
										value="#{reclamosDevolucionesController.listaAnios}" 
										itemValue="#{sel.intIdDetalle}"
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
		           			</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<!--inicio descargar archivo-->
	           		<h:panelGrid columns="8" id="panelAdjuntoRequisito">
	           			<rich:column width="3">
	           			</rich:column>
	           			<rich:column  width="174">
	           				<h:outputText value="Sustento"></h:outputText>
	           			</rich:column>
					<rich:column width="118">
						<h:inputText rendered="#{empty reclamosDevolucionesController.archivoAdjuntoReclamoDev}" 
							size="75"
							readonly="true" 
							style="background-color: #BFBFBF; width : 522px;"/>
						<h:inputText rendered="#{not empty reclamosDevolucionesController.archivoAdjuntoReclamoDev}"
							value="#{reclamosDevolucionesController.archivoAdjuntoReclamoDev.strNombrearchivo}"
							size="77"
							readonly="true" 
							style="background-color: #BFBFBF; width : 517px;"/>
					</rich:column>
					<rich:column width="120">
						<a4j:commandButton
							rendered="#{empty reclamosDevolucionesController.archivoAdjuntoReclamoDev}"
	                		styleClass="btnEstilos1"
	                		value="Adjunto Reclamo"
	                		reRender="pAdjuntarReclamo,panelAdjuntoRequisito"
	                		oncomplete="Richfaces.showModalPanel('pAdjuntarReclamo')"
	                		style="width:130px"
	                		disabled="#{reclamosDevolucionesController.blnVisible}"/>                 		
	                	<a4j:commandButton
							rendered="#{not empty reclamosDevolucionesController.archivoAdjuntoReclamoDev}"
							disabled="#{reclamosDevolucionesController.deshabilitarNuevo}"
	                		styleClass="btnEstilos"
	                		value="Quitar Adjunto"
	                		reRender="pAdjuntarReclamo,panelAdjuntoRequisito"
	                		action="#{reclamosDevolucionesController.quitarAdjuntoReclamo}"
	                		style="width:130px"
	                		disabled="#{reclamosDevolucionesController.blnVisible}"/>
					</rich:column>
					<rich:column width="130" 
						rendered="#{(not empty reclamosDevolucionesController.archivoAdjuntoReclamoDev)&&(reclamosDevolucionesController.deshabilitarNuevo)}">
						<h:commandLink  value=" Descargar"
							actionListener="#{fileUploadControllerContabilidad.descargarArchivo}">
							<f:attribute name="archivo" value="#{reclamosDevolucionesController.deshabilitarNuevo}"/>
						</h:commandLink>
					</rich:column>
	           </h:panelGrid>
	           <!--inicio descargar archivo-->
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           			<rich:column width="165">
	           					<h:outputText value="Glosa"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputTextarea value="#{reclamosDevolucionesController.reclamoDev.strRedeGlosa}" style=" width : 654px; height : 40px;"
	           								 disabled="#{reclamosDevolucionesController.blnVisible}"></h:inputTextarea>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           </h:panelGroup>
	   	</a4j:outputPanel>
	</rich:panel>
</h:form>