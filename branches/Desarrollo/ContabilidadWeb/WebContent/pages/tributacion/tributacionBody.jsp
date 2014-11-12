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
<!-- Modificación     	: Junior Chávez 		-->
<!-- Fecha Modificación : 08/08/2014            -->

<a4j:include viewId="/pages/tributacion/popup/admistracionPopupTributacion.jsp"/>
<a4j:include viewId="/pages/tributacion/popup/popSeleccionarPersona.jsp"/>
<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>
<a4j:include viewId="/pages/tributacion/popup/popImpuesto.jsp"/>
<a4j:include viewId="/pages/tributacion/popup/popPersonaRecti.jsp"/>
<a4j:include viewId="/pages/tributacion/popup/popBotonesTributacion.jsp"/>
<a4j:region>
	<a4j:jsFunction name="seleccionarPersona" reRender="txtFullNamePersona"
		action="#{tributacionController.seleccionarPersona}"
		oncomplete="Richfaces.hideModalPanel('mpTributacion')">
		<f:param name="pRowPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoPersona" reRender="cboFiltroPersona"
		actionListener="#{tributacionController.reloadCboFiltroPersona}">
		<f:param name="pTipoPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoPersona1" reRender="cboFiltroPersona1"
		actionListener="#{tributacionController.reloadCboFiltroPersona}">
		<f:param name="pTipoPersona"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersona1" reRender="txtPersonaBusq"
		actionListener="#{tributacionController.disableTxtPersonaBusq}">
		<f:param name="pFiltroPersonaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecFiltroPersona" reRender="txtPersonaBusq"
		actionListener="#{tributacionController.disableTxtPersonaBusq}">
		<f:param name="pFiltroPersonaBusq"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarPersonaRol" reRender="txtNumeroCuenta"
		action="#{tributacionController.seleccionarPersonaRol}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionPersonaRol')">
		<f:param name="pRowCuentaContable"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarImpuesto" reRender="txtImpuesto"
		action="#{tributacionController.seleccionarImpuesto}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionImpuesto')">
		<f:param name="pRowImpuesto"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="seleccionarPersRect" reRender="fullNameRect"
		action="#{tributacionController.seleccionarPersRect}"
		oncomplete="Richfaces.hideModalPanel('mpSeleccionRect')">
		<f:param name="pRowRect"></f:param>
	</a4j:jsFunction>
	
</a4j:region>

<h:form id="frmTributacion">
	<h:outputLabel value="#{tributacionController.inicioPage}"/>
	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
	   	<h:panelGroup id="divTributacion" layout="block" style="padding:15px">
	   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="TRIBUTOS IMPUESTOS" style="font-weight:bold; font-size:14px"></h:outputText>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="Tipo:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{tributacionController.impBusq.intTipoImpuestoCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{tributacionController.listaTipoTarifa}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
		    		<rich:column width="70" style="text-align: center;">
		         		<h:outputText value="Periodo: "/>
		         	</rich:column>
		         	<rich:column width="90">
		               	<h:selectOneMenu
							style="width: 90px;"
							value="#{tributacionController.impBusq.intMeses}">
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO_TODOS}" itemLabel="Todos"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
						</h:selectOneMenu>
		            </rich:column>
		    			<rich:column>
	           		<h:selectOneMenu
						style="width: 100px;" value="#{tributacionController.impBusq.intImpuPeriodo}">
						<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
						<tumih:selectItems var="sel"
							value="#{tributacionController.listaAnios}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	           			</rich:column>
	    			<rich:column>
	    				<h:outputText value="Estado:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{tributacionController.impBusq.intParaEstadoCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{tributacionController.listActivoCab}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value="Pago:"></h:outputText>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu style="width:120px" value="#{tributacionController.impBusq.intParaEstadoPagoCod}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{tributacionController.listPago}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton  action="#{tributacionController.busquedaImpuesto}" value="Buscar" 
	    								reRender="divTblTributacion" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	<%--inicio busqueda --%>
	    	<h:panelGroup id="divTblTributacion" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblTributacion"
	    					value="#{tributacionController.listaBusqImpuesto}" rendered="#{not empty tributacionController.listaBusqImpuesto}"
	                        var="item" rowKeyVar="rowKey" rows="5" width="1190px" height="179px">
	                        <rich:column width="21">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column width="50">
	                        <f:facet name="header">
	                            <h:outputText value="Código"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.id.intItemImpuesto}" title="#{item.id.intItemImpuesto}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.tsImpuFechaPresentacionD}">
	                        	<f:convertDateTime type="tsImpuFechaRegistro" pattern="dd/MM/yyyy"/>
	                        </h:outputText>
						</rich:column>  
			            <rich:column width="75">
			            	<f:facet name="header">
	                            <h:outputText value="Registro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescTipoRegistro}"></h:outputText>
			            </rich:column>
			            <rich:column width="35">
	                        <f:facet name="header">
	                            <h:outputText value="Tipo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescTipoImpuesto}"></h:outputText>
			            </rich:column>
			            <rich:column width="400">
	                        <f:facet name="header">
	                            <h:outputText value="Entidad"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strJuriRazonsocial}" title="#{item.strJuriRazonsocial}"></h:outputText>
			            </rich:column>
			            <rich:column width="65">
	                        <f:facet name="header">
	                            <h:outputText value="Periodo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.intImpuPeriodo}" title="#{item.intImpuPeriodo}"></h:outputText>
			            </rich:column>
			            <rich:column width="250">
	                        <f:facet name="header">
	                            <h:outputText value="Personal Encargado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strNombreCompleto}" title="#{item.strNombreCompleto}"></h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Monto"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescTipoMoneda}"/>
	                        <h:outputText value="#{item.bdImpuMonto}">
	                        	<f:converter converterId="ConvertidorMontos"/>
	                        </h:outputText>
			            </rich:column>
			            <rich:column width="70">
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescEstado}" title="#{item.strDescEstado}"></h:outputText>
			            </rich:column>
			            <rich:column width="85">
	                        <f:facet name="header">
	                            <h:outputText value="Estado Pago"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strDescEstadoPago}" title="#{item.strDescEstadoPago}"></h:outputText>
			            </rich:column>
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblTributacion" maxPages="10"/>
				   		 </h:panelGroup>    
			       </f:facet>
			       <a4j:support event="onRowClick"
						actionListener="#{tributacionController.seleccionarTributo}"
						oncomplete="Richfaces.showModalPanel('mpModificarTribu')"
						reRender = "frmBotonesUpdDelDspTributacion">
						<f:attribute name="itemTribuBusq" value="#{item}" />
					</a4j:support> 
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	            <%--fin busqueda--%>
	       	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
	           
	           <h:panelGrid columns="4" id="pgBotones">
	           		<rich:column>
	           			<a4j:commandButton value="Nuevo Impuesto" action="#{tributacionController.showTributacion}"
	           							reRender="opTributacion, pgBotones" styleClass="btnEstilos" style=" width : 122px;"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Nueva Rectificatoria" action="#{tributacionController.showTributacionRectificacion}"
	           							reRender="opTributacion, pgBotones" styleClass="btnEstilos" style=" width : 172px;"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Grabar" action="#{tributacionController.grabarTributacion}" 
	           							disabled="#{tributacionController.blnVisible}"
	           							reRender="opTributacion, pgBotones" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           		<rich:column>
	           			<a4j:commandButton value="Cancelar" action="#{tributacionController.limpiarFomularioTributacion}" 
	           							reRender="opTributacion, pgBotones, divTblTributacion" styleClass="btnEstilos"></a4j:commandButton>
	           		</rich:column>
	           </h:panelGrid>
	   	</h:panelGroup>
	   	<!-- inicio -->
	   	<a4j:outputPanel id="opTributacion" >
	   		<!-- Formulario Tributación Nuevo -->
	           <h:panelGroup layout="block" style="border:1px solid #17356f; padding:15px" rendered="#{tributacionController.blnShowPanelInferior}">
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4" >
	           			<rich:columnGroup>
	           				<rich:column width="105">
		           				<h:outputText value="Código"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           			<h:inputText value="#{tributacionController.impuesto.id.intItemImpuesto}" disabled="true"></h:inputText>
		           			</rich:column>
		           			<rich:column width="50">
		           			</rich:column>
		           			<rich:column>
		           				<h:outputText value="Fecha :"></h:outputText>
		           			</rich:column>
		           			<rich:column>
		           				<h:inputText value="#{tributacionController.daFechaReg}" disabled="true"></h:inputText>
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
		           				<h:inputText value="#{tributacionController.strEstado}" disabled="true"></h:inputText>
		           			</rich:column>
	           			</rich:columnGroup>
	           			</h:panelGrid>
	           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4" rendered="#{tributacionController.blnShowTributacion}">
		           			<rich:columnGroup>
		           				<rich:column width="105">
		           					<h:outputText value="Entidad"></h:outputText>
		           				</rich:column>
		           				<rich:column colspan="5">
		           					<h:inputText id="txtFullNamePersona" value="#{tributacionController.impuesto.strJuriRazonsocial}" size="82" disabled="true"></h:inputText>
		           				</rich:column>
		           				<rich:column width="50">
		           					<a4j:commandButton value="Buscar P. Jurídica" action="#{tributacionController.limpiar}" styleClass="btnEstilos1" disabled="#{tributacionController.blnVisible || tributacionController.blnModificar}"
		           							reRender="txtFullNamePersona,frmTributacionPopup" oncomplete="Richfaces.showModalPanel('mpTributacion')"></a4j:commandButton>
		           				</rich:column>
		           			</rich:columnGroup>
		           			<rich:columnGroup>
		           				<rich:column>
		           					<h:outputText value="Tipo"></h:outputText>
		           				</rich:column>
		           				<rich:column colspan="5">
			           				<h:selectOneMenu style="width:120px" value="#{tributacionController.impuesto.intTipoImpuestoCod}"
			           				disabled="#{tributacionController.blnVisible}"> 
										<f:selectItem itemLabel="Seleccione..."/>
										<tumih:selectItems var="sel" value="#{tributacionController.listaTipoTarifa}"
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									</h:selectOneMenu>
		           				</rich:column>
		           			</rich:columnGroup>
	           			</h:panelGrid>
						<h:panelGrid columns="6" styleClass="tableCellBorder4" rendered="#{tributacionController.blnShowTributacionRectificacion}">
							<rich:columnGroup>
								<rich:column width="105">
									<h:outputText value="Impuesto"></h:outputText>
								</rich:column>
								<rich:column colspan="5">
									<h:inputText id="txtImpuesto" value="#{tributacionController.impuesto.strConcatenado}" size="82" disabled="true"></h:inputText>
								</rich:column>
								<rich:column width="50">
									<a4j:commandButton value="Buscar Impuesto" action="#{tributacionController.limpiarImpuesto}" styleClass="btnEstilos1" disabled="#{tributacionController.blnVisible || tributacionController.blnModificar}"
											reRender="txtImpuesto, frmSeleccionImpuesto" oncomplete="Richfaces.showModalPanel('mpSeleccionImpuesto')"></a4j:commandButton>
								</rich:column>
							</rich:columnGroup>
							<rich:columnGroup>
								<rich:column>
									<h:outputText value="Tipo"></h:outputText>
								</rich:column>
								<rich:column colspan="5">
									<h:selectOneMenu style=" width : 146px;" value="#{tributacionController.impuesto.intTipoRectificatoriaCod}"
									disabled="#{tributacionController.blnVisible}"> 
									<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
										<tumih:selectItems var="sel" value="#{tributacionController.listTipoRect}"
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									</h:selectOneMenu>
								</rich:column>
							</rich:columnGroup>
	           			</h:panelGrid>           			
	           			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           			<rich:columnGroup>
	           				<rich:column>
	           					<h:outputText value="Personal Encargado"></h:outputText>
	           				</rich:column>
	           				<rich:column colspan="5">
	           					<h:inputText id="txtNumeroCuenta" value="#{tributacionController.strNombreCompleto}" size="82" disabled="true"></h:inputText>
	           				</rich:column>
	           				<rich:column width="50">
	           			<a4j:commandButton value="Buscar Empleado" action="#{tributacionController.limpiar}" styleClass="btnEstilos" disabled="#{tributacionController.blnVisible}"
	           								reRender="txtNumeroCuenta, frmSeleccionPersonal" oncomplete="Richfaces.showModalPanel('mpSeleccionPersonaRol')" style=" width : 144px;"></a4j:commandButton>
	           		</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           				<rich:column>
	           					<h:outputText value="Fecha presentación"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           				<rich:calendar value="#{tributacionController.impuesto.tsImpuFechaPresentacionD}" 
	    							datePattern="dd/MM/yyyy" converter="calendarTimestampConverter" style="width:76px" disabled="#{tributacionController.blnVisible}"></rich:calendar>
	           				</rich:column>
	           				<rich:column rendered="#{tributacionController.blnShowTributacion}">
				         		<h:outputText value="Periodo Declaración: "/>
				         	</rich:column>	
				         	<rich:column rendered="#{tributacionController.blnShowTributacion}">
				               	<h:selectOneMenu
									value="#{tributacionController.impuesto.intMeses}"
									disabled="#{tributacionController.blnVisible}">
									<f:selectItem itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden"/>
								</h:selectOneMenu>
				            </rich:column>
				            <rich:column rendered="#{tributacionController.blnShowTributacion}">
				           		<h:selectOneMenu
									style="width: 100px;" value="#{tributacionController.impuesto.intImpuPeriodo}"
									disabled="#{tributacionController.blnVisible}">
									<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel"
										value="#{tributacionController.listaAnios}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
		           			</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           			<rich:column width="105">
	           					<h:outputText value="Importe"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputText value="#{tributacionController.impuesto.bdImpuMonto}"
	           						onblur="extractNumber(this,2,false);"
				   					onkeyup="extractNumber(this,2,false);"
				   					onkeypress="return soloNumerosDecimales(this)"
	           						style="width: 100px;"
	           						disabled="#{tributacionController.blnVisible}">
	           					</h:inputText>
	           				</rich:column>
	           				<rich:column>
		           				<h:selectOneMenu style="width:120px" value="#{tributacionController.impuesto.intParaTipoMonedaCod}"
		           				disabled="#{tributacionController.blnVisible}"> 
									<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel" value="#{tributacionController.listTipoMoneda}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           		<h:panelGrid columns="6" styleClass="tableCellBorder4">
	           		<rich:columnGroup>
	           			<rich:column width="105">
	           					<h:outputText value="Glosa"></h:outputText>
	           				</rich:column>
	           				<rich:column>
	           					<h:inputText value="#{tributacionController.impuesto.strImpuGlosa}" style=" width : 700px;" disabled="#{tributacionController.blnVisible}"></h:inputText>
	           				</rich:column>
	           			</rich:columnGroup>
	           		</h:panelGrid>
	           </h:panelGroup>
	   	</a4j:outputPanel>
	</rich:panel>
</h:form>
