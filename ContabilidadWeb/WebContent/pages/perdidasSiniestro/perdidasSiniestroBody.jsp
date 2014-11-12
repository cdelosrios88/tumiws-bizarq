<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Autor     : Rodolfo Villarreal    			-->
<!-- Modulo    : Perdidas               		-->
<!-- Prototipo : PROTOTIPO PERDIDAS			    -->
<!-- Fecha     : 25/07/2014               		-->
<!-- Creación     	: Rodolfo Villarreal 		-->
<!-- Fecha Modificación : 14/08/2014            -->

<a4j:include viewId="/pages/perdidasSiniestro/popup/popPersonaJuridica.jsp"/>
<a4j:include viewId="/pages/perdidasSiniestro/popup/popPersonaPerdida.jsp"/>
<a4j:include viewId="/pages/perdidasSiniestro/popup/popBotonesPerdidas.jsp"/>

<a4j:region>
	<a4j:jsFunction name="selecPersonaJuridica" reRender="cboPersonaJuridica"
		actionListener="#{perdidasSiniestroController.reloadCboPersonaJuridica}">
		<f:param name="pPersonaJuridica"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersonaJuridica" reRender="txtJuridicaBusq"
		actionListener="#{perdidasSiniestroController.disableTxtJuridicaBusq}">
		<f:param name="pFiltroPersonaBusq"></f:param>
	</a4j:jsFunction>
	
	<!--Inicio combo persona-->
	<a4j:jsFunction name="selecTipoPersona" reRender="cboFiltroPersona"
		actionListener="#{perdidasSiniestroController.reloadCboFiltroPersona}">
		<f:param name="pTipoPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersona" reRender="txtPersonaBusq"
		actionListener="#{perdidasSiniestroController.disableTxtPersonaBusq}">
		<f:param name="pFiltroPersonaPerdi"></f:param>
	</a4j:jsFunction>
	<!--Fin combo persona-->
	<!--Seleccionar persona Juridica-->
	<a4j:jsFunction name="seleccionarJuridica" reRender="fullNameJuridica"
		action="#{perdidasSiniestroController.seleccionarJuridica}"
		oncomplete="Richfaces.hideModalPanel('mpSelecPersJuridica')">
		<f:param name="pRowRect"></f:param>
	</a4j:jsFunction>
	<!--Seleccionar persona con rol 3-->
	<a4j:jsFunction name="seleccionarPersPerdido" reRender="fullNamePerdido"
		action="#{perdidasSiniestroController.seleccionarPersPerdido}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionPerdi')">
		<f:param name="pRowPerdida"></f:param>
	</a4j:jsFunction>
	
	<!--Inicio combos cabecera-->
	<a4j:jsFunction name="selecTipoPerdidoCab" reRender="cboFiltroPerdidaCab"
		action="#{perdidasSiniestroController.recargarCboFiltroPerdidoCab}">
		<f:param name="pTipoPerdidoCab"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPerdidoCab" reRender="txtPerdidoBusq"
		action="#{perdidasSiniestroController.disableTxtPerdidoBusqCab}">
		<f:param name="pFiltroPerdidoBusq"></f:param>
	</a4j:jsFunction>
	<!--Fin combos cabecera-->
	
</a4j:region>

<h:form id="frmPerdidas">
	<h:outputLabel value="#{perdidasSiniestroController.inicioPage}"/>
	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
	   	<h:panelGroup id="divPerdidas" layout="block" style="padding:15px">
	   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="PERDIDAS POR SINIESTRO" style="font-weight:bold; font-size:14px"></h:outputText>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
						<h:selectOneMenu value="#{perdidasSiniestroController.intCboTipoPerdidoBusq}"
	    								 onchange="selecTipoPerdidoCab(#{applicationScope.Constante.ONCHANGE_VALUE})"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listTipo}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>						
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu id="cboFiltroPerdidaCab" value="#{perdidasSiniestroController.intCboFiltroPerdidoBusq}"
	    									onchange="selecFiltroPerdidoCab(#{applicationScope.Constante.ONCHANGE_VALUE})"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<f:selectItems value="#{perdidasSiniestroController.cboFiltroPerdidaCab}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column colspan="5">
	           					<h:inputText id="txtPerdidoBusq" value="#{perdidasSiniestroController.strTxtPerdidoBusq}" 
	           							     disabled="#{perdidasSiniestroController.isDisabledTxtPerdidoBusq}" size="40" ></h:inputText>
	           				</rich:column>
		    		<rich:column width="85" style="text-align: center;">
		         		<h:outputText value="Fecha Siniestro: "/>
		         	</rich:column>
		         	<rich:column>
	           				<rich:calendar value="#{perdidasSiniestroController.perdidasSiniBusq.dtSiniFechaSiniestro}" 
	    							datePattern="dd/MM/yyyy" style="width:35px"></rich:calendar>
	           				</rich:column>
	    			<rich:column>
	    				<h:outputText value="Estado:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{perdidasSiniestroController.perdidasSiniBusq.intParaEstadoCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listActivoCab}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value="Pago:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{perdidasSiniestroController.perdidasSiniBusq.intParaEstadoCobroCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listPago}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton  action="#{perdidasSiniestroController.buscarPerdido}" value="Buscar" 
	    								reRender="divTblPerdido" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	<%--inicio busqueda --%>
	    	<h:panelGroup id="divTblPerdido" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblPerdido"
	    					value="#{perdidasSiniestroController.listaBuscarPrincipal}" rendered="#{not empty perdidasSiniestroController.listaBuscarPrincipal}"
	                        var="item" rowKeyVar="rowKey" rows="5" width="950px" height="179px">
	                        <rich:column width="21">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column width="50">
	                        <f:facet name="header">
	                            <h:outputText value="Código"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.id.intContItemSiniestro}" title="#{item.id.intContItemSiniestro}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Siniestro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.dtSiniFechaSiniestro}" title="#{item.dtSiniFechaSiniestro}">
	                        	<f:convertDateTime type="tsImpuFechaRegistro" pattern="dd/MM/yyyy"/>
	                        </h:outputText>
						</rich:column>  
			            <rich:column width="180">
			            	<f:facet name="header">
	                            <h:outputText value="Tipo Siniestro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescripcionTipo}" title="#{item.strDescripcionTipo}"></h:outputText>
			            </rich:column>
			            <rich:column width="200">
	                        <f:facet name="header">
	                            <h:outputText value="Aseguradora"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strNombreJuridica}" title="#{item.strNombreJuridica}"></h:outputText>
			            </rich:column>
			            <rich:column width="250">
	                        <f:facet name="header">
	                            <h:outputText value="Personal Afectado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strNombreCompletoPerdido}" title="#{item.strNombreCompletoPerdido}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescripcionEstado}" title="#{item.strDescripcionEstado}"></h:outputText>
			            </rich:column>
			            <rich:column width="110">
	                        <f:facet name="header">
	                            <h:outputText value="Estado Cobro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescripcionPago}" title="#{item.strDescripcionPago}"></h:outputText>
			            </rich:column>
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblPerdido" maxPages="10"/>
				   		 </h:panelGroup>    
			       </f:facet>
			       <a4j:support event="onRowClick"
						actionListener="#{perdidasSiniestroController.seleccionarPerdido}"
						oncomplete="Richfaces.showModalPanel('mpModificarPerdido')"
						reRender="formBotonesPerdido,pgBotones">
						<f:attribute name="itemPerdidoBusq" value="#{item}" />
					</a4j:support> 
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	            <%--fin busqueda--%>
	       	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
	           
	           <h:panelGrid columns="3" id="pgBotones">
	           		<rich:column>
	           			<a4j:commandButton value="Nuevo" action="#{perdidasSiniestroController.motrarPanel}"
	           							reRender="opPerdidas" styleClass="btnEstilos" style=" width : 122px;"></a4j:commandButton>
	           		</rich:column>
	           		<!--<rich:column>
	           			<a4j:commandButton value="Nueva Rectificatoria" action="#{tributacionController.showTributacionRectificacion}"
	           							reRender="opReclamo" styleClass="btnEstilos" style=" width : 172px;"></a4j:commandButton>
	           		</rich:column>-->
	           		<rich:column>
	           			<a4j:commandButton value="Grabar" action="#{perdidasSiniestroController.guardarPerdidas}"
	           							   disabled="#{perdidasSiniestroController.blnVisible}"
	           							   reRender="opPerdidas" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Cancelar" action="#{perdidasSiniestroController.limpiarFomularioPerdidas}" 
	           							reRender="opPerdidas,frmPerdidas,pgBotones" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           </h:panelGrid>
	   	</h:panelGroup>
	   	<!-- inicio -->
	   	<a4j:outputPanel id="opPerdidas" >
	   		<!-- Formulario Tributación Nuevo -->
	           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px" rendered="#{perdidasSiniestroController.blnShowPanelInferior}">
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4" >
	           			<rich:columnGroup>
	           				<rich:column width="165">
		           				<h:outputText value="Código"></h:outputText>
		           			</rich:column>
		           			<rich:column>
			           			<h:inputText value="#{perdidasSiniestroController.perdidasSini.id.intContItemSiniestro}" disabled="true">
			           			</h:inputText>
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column>
		           				<h:outputText value="Fecha :"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           				<h:inputText value="#{perdidasSiniestroController.daFechaRegistro}" disabled="true"></h:inputText>
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
		           				<h:inputText value="#{perdidasSiniestroController.strEstado}" disabled="true"></h:inputText>
		           			</rich:column>
	           			</rich:columnGroup>
	           			</h:panelGrid>
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           			<rich:columnGroup>
	           				<rich:column width="165">
	           					<h:outputText value="Aseguradora"></h:outputText>
	           				</rich:column>
	           				<rich:column colspan="5">
	           					<h:inputText id="fullNameJuridica" value="#{perdidasSiniestroController.seleccionPerdido.strNombreJuridica}" size="82" disabled="true"></h:inputText>
	           				</rich:column>
	           				<rich:column width="50">
	           			<a4j:commandButton value="Buscar P. Jurídica" action="#{perdidasSiniestroController.limpiarAsegurador}" styleClass="btnEstilos" disabled="#{perdidasSiniestroController.blnVisible}"
	           								reRender="mpSelecPersJuridica,fullNameJuridica" oncomplete="Richfaces.showModalPanel('mpSelecPersJuridica')" style=" width : 144px;"></a4j:commandButton>
	           		</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
		           			<rich:columnGroup>
		           				<rich:column width="165">
		           					<h:outputText value="Tipo de siniestro"></h:outputText>
		           				</rich:column>
		           				<rich:column>
				    				<h:selectOneMenu style="width:120px" value="#{perdidasSiniestroController.perdidasSini.intParaTipoSiniestro}"
				    								 disabled="#{perdidasSiniestroController.blnVisible}">
										<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
										<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listTipoSiniestro}"
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									</h:selectOneMenu>
				    			</rich:column>
				    			<rich:column  width="165">
				    			</rich:column>
				    			<rich:column>
		           					<h:outputText value="Fecha de Siniestro"></h:outputText>
		           				</rich:column>
		           				<rich:column>
	           					<rich:calendar value="#{perdidasSiniestroController.perdidasSini.dtSiniFechaSiniestro}" disabled="#{perdidasSiniestroController.blnVisible}"
	    							datePattern="dd/MM/yyyy" style="width:70px"></rich:calendar>
	           					</rich:column>
		           			</rich:columnGroup>
	           			</h:panelGrid>
						           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           			<rich:columnGroup>
	           				<rich:column width="165">
	           					<h:outputText value="Personal Afectado"></h:outputText>
	           				</rich:column>
	           				<rich:column colspan="5">
	           					<h:inputText id="fullNamePerdido" value="#{perdidasSiniestroController.strConcatenado}" size="82" disabled="true"></h:inputText>
	           				</rich:column>
	           				<rich:column width="50">
	           			<a4j:commandButton value="Buscar Empleado" action="#{perdidasSiniestroController.limpiar}" styleClass="btnEstilos" disabled="#{perdidasSiniestroController.blnVisible}"
	           								reRender="mpSeleccionPerdi,fullNamePerdido" oncomplete="Richfaces.showModalPanel('mpSeleccionPerdi')" style=" width : 144px;"></a4j:commandButton>
	           		</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="3" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           				<rich:column  width="165">
	           					<h:outputText value="Importe de Daños"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputText value="#{perdidasSiniestroController.perdidasSini.bdSiniMontoDado}"
	           						disabled="#{perdidasSiniestroController.blnVisible}"
	           						onblur="extractNumber(this,2,false);"
				   					onkeyup="extractNumber(this,2,false);"
				   					onkeypress="return soloNumerosDecimales(this)"
	           						style="width: 100px;"
	           						>
	           					</h:inputText>
	           				</rich:column>
	           				<rich:column>
	           			<h:selectOneMenu style="width:120px" value="#{perdidasSiniestroController.perdidasSini.intParaTipoMonedaCod}"
	           								 disabled="#{perdidasSiniestroController.blnVisible}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listMoneda}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	           				</rich:column>
	           				<rich:column  width="165">
	           					<h:outputText value="(Evidencia gastos realizados)"></h:outputText>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="3" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           				<rich:column  width="165">
	           					<h:outputText value="Monto Asegurado"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputText value="#{perdidasSiniestroController.perdidasSini.bdSiniMontoAsegurado}"
	           						disabled="#{perdidasSiniestroController.blnVisible}"
	           						onblur="extractNumber(this,2,false);"
				   					onkeyup="extractNumber(this,2,false);"
				   					onkeypress="return soloNumerosDecimales(this)"
	           						style="width: 100px;"
	           						>
	           					</h:inputText>
	           				</rich:column>
	           				<rich:column>
	           				<h:selectOneMenu style="width:120px" value="#{perdidasSiniestroController.perdidasSini.intParaTipoMonedaAsegCod}"
	           								 disabled="#{perdidasSiniestroController.blnVisible}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{perdidasSiniestroController.listMoneda}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	           				</rich:column>
	           				<rich:column  width="165">
	           					<h:outputText value="(Evidencia ingresos realizados)"></h:outputText>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           			<rich:column width="165">
	           					<h:outputText value="Glosa"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputTextarea value="#{perdidasSiniestroController.perdidasSini.strSiniGlosa}" style=" width : 654px; height : 40px;"
	           								     disabled="#{perdidasSiniestroController.blnVisible}"></h:inputTextarea>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           </h:panelGroup>
	   	</a4j:outputPanel>
	</rich:panel>
</h:form>