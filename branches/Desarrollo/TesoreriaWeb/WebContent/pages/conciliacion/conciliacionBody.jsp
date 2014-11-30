	<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
	<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
	<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
	<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->
	
	<!-- 
	-----------------------------------------------------------------------------------------------------------
	* Modificaciones
	* Motivo                      Fecha            Nombre                      Descripción
	* -----------------------------------------------------------------------------------------------------------
	* REQ14-006       			01/11/2014     Christian De los Ríos        Se modificó la pantalla inicial de Conciliación
	 -->

	<a4j:include viewId="/pages/conciliacion/popup/panelVerModificar.jsp"/>
	
	<h:outputText value="#{conciliacionController.limpiarConciliacion}"/>
	
	<h:form>
		<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
			<h:panelGrid style="margin:0 auto; margin-bottom:10px">
				<rich:columnGroup>
					<rich:column>
						<h:outputText value="CONCILIACION BANCARIA" style="font-weight:bold; font-size:14px"/>
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>

			<h:panelGrid id="pgParamsBusq" columns="7">
				<!-- Inicio: REQ14-06 Bizarq - 18/10/2014 -->
				<rich:column width="60px">
					<h:outputText value="Fecha"/>
				</rich:column>
				<rich:column width="10px">
					<h:outputText value=":" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" enableManualInput="true" 
						value="#{conciliacionController.conciliacionCompBusq.dtBusqFechaDesde}" 
						datePattern="dd/MM/yyyy" inputStyle="width:70px;" /> 
				</rich:column>
				<rich:column>
					<rich:calendar popup="true" enableManualInput="true" 
						value="#{conciliacionController.conciliacionCompBusq.dtBusqFechaHasta}"
						datePattern="dd/MM/yyyy" inputStyle="width:70px;" /> 
				</rich:column>

				<rich:column width=	"120">
					<h:outputText value="Cuenta Bancaria : "/>
				</rich:column>
			<rich:column width="120">
				<h:selectOneMenu style="width: 170px;"
					value="#{conciliacionController.intBancoSeleccionado}">
					<f:selectItem itemValue="0" itemLabel="Seleccionar" />
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
					<a4j:support event="onchange"
						action="#{conciliacionController.seleccionarBancoFiltro}"
						reRender="cboListaCuentas" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="120">
				<h:selectOneMenu
					value="#{conciliacionController.intBancoCuentaSeleccionado}"
					style="width: 260px;" id="cboListaCuentas">
					<f:selectItem itemValue="0" itemLabel="Seleccionar" />
					<tumih:selectItems var="sel"
						value="#{conciliacionController.listaBancoCuentaFiltro}"
						itemValue="#{sel.id.intItembancocuenta}"
						itemLabel="#{sel.strEtiqueta}" />
				</h:selectOneMenu>
			</rich:column>

			<rich:column width="80" style="text-align: right;">
					<a4j:commandButton styleClass="btnEstilos"
						value="Buscar" 
						reRender="panelTablaResultados,panelMensaje,tblResultado"
						action="#{conciliacionController.buscar}"
						style="width:80px"/>
				</rich:column>
				<rich:column width="80" style="text-align: right;">
					<a4j:commandButton styleClass="btnEstilos"
						value="Limpiar" 
						reRender="panelTablaResultados,panelMensaje,pgParamsBusq,tblResultado"
						action="#{conciliacionController.limpiar}"
						style="width:80px"/>
				</rich:column>
				
				<!-- Fin: REQ14-006 Bizarq - 18/10/2014 -->
			</h:panelGrid>

			<rich:spacer height="12px"/>
			<h:panelGrid id="panelTablaResultados">
				<rich:extendedDataTable id="tblResultado" 
					enableContextMenu="false" 
					sortMode="single" 
					var="item" 
					value="#{conciliacionController.listaConciliacionBusq}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">

					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Fecha Conc."/>
						</f:facet>
						<h:outputText value="#{item.tsFechaConciliacion}">
						<f:convertDateTime pattern="dd/MM/yyyy"/>
						</h:outputText>
					</rich:column>
					<rich:column width="60" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Banco"/>
						</f:facet>
						<h:outputText value="#{item.strBanco}"/>
					</rich:column>
					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Tipo Cuenta"/>
						</f:facet>
						<h:outputText value="#{item.strTipoCuenta}"/>	
					</rich:column>
					<rich:column width="70" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Moneda"/>
						</f:facet>
						<h:outputText value="#{item.strMoneda}"/>
					</rich:column>
					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Nro. Cta."/>
						</f:facet>
						<h:outputText value="#{item.strNumeroCuenta}"/>
					</rich:column>
					<rich:column width="100" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Saldo Anterior"/>
						</f:facet>
						<h:outputText value="#{item.bdMontoSaldoInicial}" style="align: right"/>
					</rich:column>
					<rich:column width="80" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Debe"/>
						</f:facet>
						<h:outputText value="#{item.bdMontoDebe}" style="align: right">
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="80" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Haber"/>
						</f:facet>
						<h:outputText value="#{item.bdMontoHaber}" style="align: right">
						<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Saldo Caja"/>
						</f:facet>
						<h:outputText value="#{item.bdSaldoCaja}" style="align: right">
						<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Saldo Conc."/>
						</f:facet>
						<h:outputText value="#{item.bdSaldoConciliacion}" style="align: right">
						<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="90" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Nro. Mov."/>
						</f:facet>
						<h:outputText value="#{item.intNroMovimientos}"/>
					</rich:column>
					<rich:column width="100" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Por Conciliar"/>
						</f:facet>
						<h:outputText value="#{item.bdPorConciliar}"/>
					</rich:column>
					
					<a4j:support event="onRowClick"
						actionListener="#{conciliacionController.seleccionarRegistro}"
						reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
						oncomplete="if(#{conciliacionController.mostrarBtnView}){Richfaces.showModalPanel('panelUpdateViewConciliacion')}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:support>
					
					<f:facet name="footer">
					<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>
				</rich:extendedDataTable>
				
			</h:panelGrid>
			
			<%--
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<h:outputLink value="#" id="linkConciliacion">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px" />
					<rich:componentControl for="panelUpdateViewConciliacion"
						attachTo="linkConciliacion" operation="show" event="onclick" />
				</h:outputLink>
				<h:outputText value="Para anular o ver una Conciliacion Bancaria hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>--%>
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar / Ver hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

			<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
				styleClass="rich-tabcell-noborder">
				<h:outputText value="#{conciliacionController.mensajeOperacion}" 
					styleClass="msgInfo"
					style="font-weight:bold"
					rendered="#{conciliacionController.mostrarMensajeExito}"/>
				<h:outputText value="#{conciliacionController.mensajeOperacion}" 
					styleClass="msgError"
					style="font-weight:bold"
					rendered="#{conciliacionController.mostrarMensajeError}"/>
			</h:panelGroup>

			<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
				id="panelBotones">
				<h:panelGrid columns="5">
					<a4j:commandButton value="Nuevo" 
						styleClass="btnEstilos" 
						style="width:90px" 
						action="#{conciliacionController.habilitarPanelInferior}"
						rendered="#{conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_ANALISTA_TESORERIA ||
									conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_JEFE_TESORERIA}" 
						reRender="contPanelInferior,panelMensaje,panelBotones,panelDatosAnular" />                     
					<a4j:commandButton value="Grabar" 
						styleClass="btnEstilos" 
						style="width:90px"
						action="#{conciliacionController.grabar}" 
						reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
						rendered="#{conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_ANALISTA_TESORERIA ||
									conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_JEFE_TESORERIA}"
						disabled="#{empty conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual}"/>
					<a4j:commandButton value="Grabar Conciliacion Diaria" 
						styleClass="btnEstilos" 
						style="width:170px"
						action="#{conciliacionController.grabarConciliacionDiaria}" 
						reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
						rendered="#{conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_JEFE_TESORERIA}"
						disabled="#{empty conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual}"/>
					<a4j:commandButton value="Anular Conciliacion" 
						styleClass="btnEstilos" 
						style="width:140px"
						action="#{conciliacionController.habilitarPanelAnulacion}" 
						reRender="contPanelInferior,panelMensaje,panelBotones,panelDatosAnular"
						rendered="#{conciliacionController.usuario.perfil.id.intIdPerfil==applicationScope.Constante.INT_PERFIL_JEFE_CONTABILIDAD}"/>
					<a4j:commandButton value="Cancelar" 
						styleClass="btnEstilos"
						style="width:90px"
						action="#{conciliacionController.deshabilitarPanelInferior}" 
						reRender="contPanelInferior,panelMensaje,panelBotones,panelDatosAnular"/>      
				</h:panelGrid>
			</h:panelGroup>

			<h:panelGroup id="contPanelInferior">
				<rich:panel  rendered="#{conciliacionController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
				<rich:spacer height="12px"/>
					<h:panelGroup id="panelDatosSinValidar">
						<h:panelGrid columns="16" id="panelMonto">
							<rich:column width=	"130">
								<h:outputText value="Fecha Conciliación : "/>
							</rich:column>
							<rich:column width="120">
								<h:inputText size="12"
									readonly="true"
									value="#{conciliacionController.conciliacionNuevo.tsFechaConciliacion}"
									style="background-color: #BFBFBF;font-weight:bold;">
									<f:convertDateTime pattern="dd/MM/yyyy"/>
								</h:inputText>
							</rich:column>
							<rich:column width=	"150">
								<h:outputText value="Tipo de Documento : "/>
							</rich:column>
							<!--<rich:column width="160">
							 
								<h:selectOneMenu
									style="width: 150px;"
									value="#{conciliacionController.conciliacionNuevo.intParaDocumentoGeneralFiltro}"
									disabled="#{conciliacionController.datosValidados}">
										<tumih:selectItems var="sel"
										cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
										itemValue="#{sel.intIdDetalle}"
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							
							</rich:column>-->
							<rich:column width=	"140">
								<h:selectOneMenu value="#{conciliacionController.conciliacionNuevo.intParaDocumentoGeneralFiltro}" 
									style="width:150px;" disabled="#{conciliacionController.blDeshabilitarVerConc}">
									<f:selectItem itemValue="0" itemLabel="Todos"/>
									<tumih:selectItems var="sel" value="#{conciliacionController.listaTablaTipoDoc}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>	
							</rich:column>
							<rich:column width=	"120">
								<h:outputText value="Cuenta Bancaria : "/>
							</rich:column>
							<rich:column width="120" rendered="#{!conciliacionController.blModoEdicion}">
								<h:selectOneMenu style="width: 170px;"
									disabled="#{conciliacionController.deshabilitarBancoNuevoConc}"
									value="#{conciliacionController.intBancoNuevoSeleccionado}">
									<f:selectItem itemValue="0" itemLabel="Seleccionar" />
									<tumih:selectItems var="sel"
										cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
										itemValue="#{sel.intIdDetalle}"
										itemLabel="#{sel.strDescripcion}" />
									<a4j:support event="onchange"
										action="#{conciliacionController.seleccionarNuevoConcBancoFiltro}"
										reRender="cboListaCuentasNuevaConc,pgUploadTelecreditoFile" />
								</h:selectOneMenu>
							</rich:column>
							<rich:column width="120" rendered="#{conciliacionController.blModoEdicion}">
								<tumih:inputText readonly="true"
								cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{conciliacionController.intBancoNuevoSeleccionado}" />
							</rich:column>							
							
							<rich:column width="120"rendered="#{!conciliacionController.blModoEdicion}">
								<h:selectOneMenu
									value="#{conciliacionController.intBancoCuentaNuevaConcSeleccionado}"
									disabled="#{conciliacionController.deshabilitarBancoCuentaNuevoConc}"
									style="width: 260px;" id="cboListaCuentasNuevaConc">
									<f:selectItem itemValue="0" itemLabel="Seleccionar" />
									<tumih:selectItems var="sel"
										value="#{conciliacionController.listaBancoCuentaFiltroNuevaConc}"
										itemValue="#{sel.id.intItembancocuenta}"
										itemLabel="#{sel.strEtiqueta}" />
										<a4j:support event="onchange"
										action="#{conciliacionController.seleccionarBancoCuentaNuevoConc}"
										reRender="contPanelInferior" />
								</h:selectOneMenu>
							</rich:column>
							<rich:column width="120" rendered="#{conciliacionController.blModoEdicion}">
								<h:inputText style="width: 260px;" value="#{conciliacionController.strDescCuentaBancariaConciliacion}"
									readonly="true" />
							</rich:column>
						</h:panelGrid>

						<rich:spacer height="10px"/>

						<h:panelGrid id="pgUploadTelecreditoFile" columns="8">
							<rich:column width=	"140">
								<h:outputText value="Estado de Registro : "/>
							</rich:column>
							<rich:column width="160">
								<h:selectOneMenu
									disabled="#{conciliacionController.blDeshabilitarVerConc}"
									style="width: 150px;"
									value="#{conciliacionController.conciliacionNuevo.intEstadoCheckFiltro}">
									<f:selectItem itemValue="0" itemLabel="Todos"/>
									<f:selectItem itemValue="1" itemLabel="Chekeado"/>
									<f:selectItem itemValue="2" itemLabel="No Checkeado"/>
									
									<a4j:support event="onchange" reRender="dtResumen" actionListener="#{conciliacionController.onclickCheck}" />
									
								</h:selectOneMenu>
							</rich:column>
							
							<h:panelGrid id="pgUplTelecredito" columns="2"
								rendered="#{conciliacionController.showFileUpload}">
								<rich:column>
									<h:outputText value="Telecrédito: "/>
								</rich:column>
								
								<rich:column>
									<rich:fileUpload id="upload"
										addControlLabel="Adjuntar Archivo"
										clearControlLabel="Limpiar"
										cancelEntryControlLabel="Cancelar"
										uploadControlLabel="Subir Archivo"
										listHeight="65"
										listWidth="320"
										fileUploadListener="#{conciliacionController.adjuntarDocTelecredito}"
										maxFilesQuantity="1"
										doneLabel="Archivo cargado correctamente"
										immediateUpload="false"
										autoclear="false"
										disabled="#{conciliacionController.blDeshabilitarVerComp}"
										acceptedTypes="xls,xlsx">
										<f:facet name="label">
											<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
										</f:facet>
									</rich:fileUpload>
								</rich:column>
							</h:panelGrid>
							
							<rich:column>
								<a4j:commandButton value="Validar Checks" styleClass="btnEstilos"
									action="#{conciliacionController.matchTelecreditoFileAgainstLstConcDet}"
									style="width:150px"
									rendered="#{not empty conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual}"
									reRender="panelDatosValidados,panelMensaje"/>
							</rich:column>
						</h:panelGrid>

						<rich:spacer height="15px"/>

						<h:panelGrid columns="1" rendered="#{conciliacionController.blDeshabilitarBuscar}">
							<rich:column width="940">
								<a4j:commandButton styleClass="btnEstilos"
									disabled="false"
									value="Buscar"
									reRender="contPanelInferior,panelBotones"
									action="#{conciliacionController.buscarRegistrosConciliacionEdicion}"
									style="width:940px"/>
							</rich:column>
						</h:panelGrid>

						<rich:spacer height="5px"/>
						
						<h:panelGrid columns="1" rendered="true">
							<rich:column width="940">
								<a4j:commandButton styleClass="btnEstilos"
									rendered="#{conciliacionController.blDeshabilitaValidarDatos}" 
									disabled="#{empty conciliacionController.conciliacionNuevo.bancoCuenta}"
									value="Mostrar Datos"
									reRender="contPanelInferior,panelBotones"
									action="#{conciliacionController.validarDatos}"
									style="width:940px"/>
							</rich:column>
						</h:panelGrid>
					</h:panelGroup>
					
					<h:panelGrid id="panelDatosValidados" 
						rendered="#{not empty conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual}">
						<rich:column width=	"910">
						<rich:dataTable
							sortMode="single"
							var="item"
							value="#{conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual}"
							rowKeyVar="rowKey"
							width="955px"
							rows="#{fn:length(conciliacionController.conciliacionNuevo.listaConciliacionDetalleVisual)}">
								<rich:column width="30" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Tipo"/>
									</f:facet>
									<h:outputText rendered="#{not empty item.egreso}" value="E"/>
									<h:outputText rendered="#{not empty item.ingreso}" value="I"/>
								</rich:column>
								<rich:column width="150" style="text-align: right">
									<f:facet name="header">
										<h:outputText value="Documento"/>
									</f:facet>
									<h:panelGroup rendered="#{not empty item.egreso}">
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
										itemValue="intIdDetalle"
										itemLabel="strDescripcion" 
										property="#{item.egreso.intParaDocumentoGeneral}"/>
									</h:panelGroup>
									<h:panelGroup rendered="#{not empty item.ingreso}">
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
										itemValue="intIdDetalle"
										itemLabel="strDescripcion" 
										property="#{item.ingreso.intParaDocumentoGeneral}"/>
									</h:panelGroup>
								</rich:column>
								<rich:column width="80" style="text-align: right">
									<f:facet name="header">
										<h:outputText value="Número"/>
									</f:facet>
									<h:outputText rendered="#{not empty item.egreso}" 
									value="#{item.egreso.intItemPeriodoEgreso} - #{item.egreso.intItemEgreso}"/>  	
									<h:outputText rendered="#{not empty item.ingreso}" 
									value="#{item.ingreso.intItemPeriodoIngreso} - #{item.ingreso.intItemIngreso}"/>
								</rich:column>
								<rich:column width="90" style="text-align:center">
									<f:facet name="header">
										<h:outputText value="Fecha"/>
									</f:facet>
									<h:outputText rendered="#{not empty item.egreso}" value="#{item.egreso.tsFechaProceso}">
									<f:convertDateTime pattern="dd/MM/yyyy"/>
									</h:outputText>  	
									<h:outputText rendered="#{not empty item.ingreso}" value="#{item.ingreso.tsFechaProceso}">
									<f:convertDateTime pattern="dd/MM/yyyy"/>
									</h:outputText>
								</rich:column>
								<rich:column width="100" style="text-align: right">
									<f:facet name="header">
										<h:outputText value="Nro. Operación"/>
									</f:facet>
									<h:outputText rendered="#{not empty item.egreso}" value="#{item.egreso.intNumeroPlanilla}#{item.egreso.intNumeroCheque}#{item.egreso.intNumeroTransferencia}"/>  	
									<h:outputText rendered="#{not empty item.ingreso}" value="#{item.ingreso.strNumeroOperacion}"/>
								</rich:column>
								<rich:column width="110" style="text-align:left">
									<f:facet name="header">
										<h:outputText value="Concepto"/>
									</f:facet>
									<h:panelGroup rendered="#{not empty item.egreso}">
										<%-- 
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
										itemValue="intIdDetalle"
										itemLabel="strDescripcion" 
										property="#{item.egreso.strObservacion}"/>--%>
										<h:outputText value="#{item.egreso.strObservacion}"/>
									</h:panelGroup>
									<h:panelGroup rendered="#{not empty item.ingreso}">
										<%--
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
										itemValue="intIdDetalle"
										itemLabel="strDescripcion" 
										property="#{item.ingreso.strObservacion}"/>--%>
										<h:outputText value="#{item.ingreso.strObservacion}"/>
									</h:panelGroup>
								</rich:column>	
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Sucursal que Gira"/>
									</f:facet>
									<h:panelGroup rendered="#{not empty item.egreso}">
										<tumih:outputText value="#{conciliacionController.listSucursal}" 
														  itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
														  property="#{item.egreso.intSucuIdSucursal}"/>				
									</h:panelGroup>

									<h:panelGroup rendered="#{not empty item.ingreso}">
										<tumih:outputText value="#{conciliacionController.listSucursal}" 
														  itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
														  property="#{item.ingreso.intSucuIdSucursal}"/>
									</h:panelGroup>                              
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Sucursal a quien Paga"/>
									</f:facet>
									<h:panelGroup>
									<h:outputText value="#{item.strDescripcionSucursalPaga}"/>  
									</h:panelGroup>                             
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Debe"/>
									</f:facet>
									<h:panelGroup rendered="#{not empty item.egreso}">
										<h:outputText value="---"  style="align: right"/>				
									</h:panelGroup>
									<h:panelGroup rendered="#{not empty item.ingreso}">
										<h:outputText value="#{item.ingreso.bdMontoTotal}" style="align: right">
										<f:converter converterId="ConvertidorMontos" />
										</h:outputText>

									</h:panelGroup>                              
								</rich:column>
								<rich:column width="120" style="text-align:right">
									<f:facet name="header">
										<h:outputText value="Haber"/>
									</f:facet>
									<h:panelGroup rendered="#{not empty item.egreso}">
										<h:outputText value="#{item.egreso.bdMontoTotal}">
										<f:converter converterId="ConvertidorMontos" />
										</h:outputText>				
									</h:panelGroup>
									<h:panelGroup rendered="#{not empty item.ingreso}">
										<h:outputText value="---"/>
									</h:panelGroup>
								</rich:column>
								<rich:column width="50" style="text-align: right">
									<f:facet name="header">
										<h:outputText value="Check"/>
									</f:facet>
									<h:selectBooleanCheckbox value="#{item.blIndicadorCheck}" 
										disabled="#{conciliacionController.blDeshabilitarVerConc}"/>
								</rich:column>
								<rich:column width="50" style="text-align: right">
									<f:facet name="header">
										<h:outputText value="Concil"/>
									</f:facet>
									<h:selectBooleanCheckbox value="#{item.blIndicadorConci}" disabled="true"/>
								</rich:column>			
							</rich:dataTable>
							
							<rich:spacer height="5px"/>
								
							<rich:dataTable
								id="dtResumen"
								sortMode="single"
								var="itemRes"
								rendered="#{not empty conciliacionController.conciliacionNuevo.listaConciliacionDetalle}"
								value="#{conciliacionController.lstResumen}"
								rowKeyVar="rowKey"
								width="955px">
								
								<rich:column width="90" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Fecha Conciliacion"/>
									</f:facet>
									<h:outputText value="#{conciliacionController.conciliacionNuevo.tsFechaConciliacion}">
									<f:convertDateTime pattern="dd/MM/yyyy"/>
									</h:outputText>
								</rich:column>
								<rich:column width="90" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Saldo Anterior"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenSaldoAnterior}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
								<rich:column width="80" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Debe"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenDebe}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
								<rich:column width="80" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Haber"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenHaber}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
								<rich:column width="90" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Saldo Caja"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenSaldoCaja}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
								<rich:column width="90" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Saldo conciliacion"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenSaldoConciliacion}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
								<rich:column width="50" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Nro. Mov."/>
									</f:facet>
									<h:outputText value="#{itemRes.intResumenNroMov}"/>
								</rich:column>
								<rich:column width="90" style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Por Conciliar"/>
									</f:facet>
									<h:outputText value="#{itemRes.bdResumenPorConciliar}" style="align: right">
									<f:converter converterId="ConvertidorMontos" />
									</h:outputText>	
								</rich:column>
							</rich:dataTable>				
						</rich:column>
			 		</h:panelGrid>
				</rich:panel>
			</h:panelGroup>	
			
			<h:panelGroup id="panelDatosAnular">
				<rich:panel rendered="#{conciliacionController.blnMostrarPanelAnulacion}">
				<h:panelGrid style="margin:0 auto; margin-bottom:10px">
					<rich:columnGroup>
						<rich:column>
							<h:outputText value="ANULAR CONCILIACION BANCARIA" style="font-weight:bold; font-size:14px"/>
						</rich:column>
					</rich:columnGroup>
				</h:panelGrid>
				<h:panelGrid columns="16" id="pgAnula">
					<rich:column width="60px">
							<h:outputText value="Fecha :"/>
					</rich:column>	
					<rich:column>
						<rich:calendar popup="true" enableManualInput="true" 
						value="#{conciliacionController.conciliacionCompAnul.dtFechaAnulDesde}" 
						datePattern="dd/MM/yyyy" inputStyle="width:70px;" /> 
					</rich:column>
					<rich:column width=	"120">
						<h:outputText value="Cuenta Bancaria : "/>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu style="width: 170px;"
							value="#{conciliacionController.intBancoAnuladoSeleccionado}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar" />
							<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_BANCOS}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}" />
							<a4j:support event="onchange"
								action="#{conciliacionController.seleccionarAnularConcBancoFiltro}"
								reRender="cboListaCuentasAnulaConc" />
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu
							value="#{conciliacionController.intBancoCuentaAnuladoConcSeleccionado}"
							style="width: 260px;" id="cboListaCuentasAnulaConc">
							<f:selectItem itemValue="0" itemLabel="Seleccionar" />
							<tumih:selectItems var="sel"
								value="#{conciliacionController.listaBancoCuentaFiltroAnulaConc}"
								itemValue="#{sel.id.intItembancocuenta}"
								itemLabel="#{sel.strEtiqueta}" />
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
				<h:panelGrid columns="5">
					<rich:column width="150">
						<h:outputText value="Observacion : *"/>
					</rich:column>
					<rich:column width="150">	
						<h:inputTextarea rows="10" cols="140"
								value="#{conciliacionController.conciliacionCompAnul.strObservacionAnula}" />
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid id="pgMsgErrorValidarAnulacion">
					<h:outputText value="#{conciliacionController.strMsgErrorAnulaFecha}" 		styleClass="msgError"/>
					<h:outputText value="#{conciliacionController.strMsgErrorAnulaCuenta}" 		styleClass="msgError"/>
					<h:outputText value="#{conciliacionController.strMsgErrorAnulaObservacion}" styleClass="msgError"/>
					<h:outputText value="#{conciliacionController.strMsgErrorAnulaPerfil}" 		styleClass="msgError"/>
				</h:panelGrid>
		
				<h:panelGrid columns="1">
					<rich:column width="300">
					<a4j:commandButton styleClass="btnEstilos"
						disabled="false"
						value="Anular Conciliacion"
						reRender="panelDatosAnular,contPanelInferior,panelMensaje,panelBotones,pgMsgErrorValidarAnulacion"
						action="#{conciliacionController.anularConciliacion}"
						style="width:300px"/>
					</rich:column>
				</h:panelGrid>
				</rich:panel>
			</h:panelGroup>
			
		</h:panelGroup>
	</h:form>