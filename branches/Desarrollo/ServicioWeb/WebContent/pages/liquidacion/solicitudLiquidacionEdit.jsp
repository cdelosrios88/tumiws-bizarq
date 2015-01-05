<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


	<h:panelGroup id="pgSolicitudLiquidacion">		
		<h:panelGrid id="pgMsgErrorCuenta" style="width: 100%;" 
			rendered="#{solicitudLiquidacionController.blnBloquearXCuenta}">
			<rich:column>
				<h:outputText
					value="#{solicitudLiquidacionController.strMensajeValidacionCuenta}" 
					styleClass="msgErrorValidacion"/>
			</rich:column>
			<rich:spacer height="10" />
		</h:panelGrid>
		
		<h:panelGrid columns="1">
			<rich:column width="300">
				<h:outputText value="DATOS PERSONALES" styleClass="tagSubTitulo"></h:outputText>
			</rich:column>
		</h:panelGrid>
	
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid columns="4">
			<rich:column width="120px">
				<h:outputText value="Nombre de Socio: "/>
			</rich:column>
			<rich:column>
				<h:inputText readonly="true" style="width:500px"
					value="#{solicitudLiquidacionController.beanSocioComp.persona.intIdPersona} - #{solicitudLiquidacionController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudLiquidacionController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudLiquidacionController.beanSocioComp.persona.natural.strNombres}- #{solicitudLiquidacionController.beanSocioComp.persona.documento.strNumeroIdentidad}" />
			</rich:column>	
			<rich:column>
				<h:outputText value="Condición: "></h:outputText>
			</rich:column>
			<rich:column>
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudLiquidacionController.beanSocioComp.cuenta.intParaCondicionCuentaCod}" /> -
		        <tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudLiquidacionController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="4">
			<rich:column width="120px">
				<h:outputText value="Unidad Ejecutora: " />
			</rich:column>				
			<rich:column>
				<h:inputTextarea 
					rows="3" 
					cols="70"
					value="#{solicitudLiquidacionController.strUnidadesEjecutorasConcatenadas}" readonly="true" />
			</rich:column>	
			<rich:column>
				<h:outputText value="Estado: " />
			</rich:column>
			<rich:column>
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudLiquidacionController.beanExpedienteLiquidacion.estadoLiquidacionUltimo.intParaEstado}" />
			</rich:column>
		</h:panelGrid>
		<h:panelGrid columns="4">
			<rich:column width="120px">
				<h:outputText value="Sucursal del Socio: " />
			</rich:column>
			<rich:column>
				<tumih:inputText
					value="#{solicitudLiquidacionController.listaTablaSucursal}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudLiquidacionController.beanSocioComp.socio.socioEstructura.intIdSucursalAdministra}"
					readonly="true" style="width:180px" />
			</rich:column>
			<rich:column width="100px">	
				<h:outputText value="Sub Sucursal: " />
			</rich:column>
			<rich:column>
				<h:inputText
					value="#{solicitudLiquidacionController.strSubsucursalSocio}"
					readonly="true" 
					style="width:180px"/>
			</rich:column>		
		</h:panelGrid>
		
		<h:panelGrid columns="3" styleClass="tableCellBorder4">

			<rich:column width="120px">
				<h:outputText value="Tipo de Cuenta: "></h:outputText>
			</rich:column>
			<!----------------------------- TIPO CUENTA ------------------------------------------>
			
			<rich:column>
				<rich:dataList 
					value="#{solicitudLiquidacionController.listaCuentaSocio}"
					var="item"
				 	style="border-top: 1px; border-left: 1px;background-color:#FFFFFF;">
					<h:panelGrid columns="3">
						<h:selectBooleanCheckbox value="true" disabled="false"/>
						<h:outputText value="#{item.strDescripcionTipoCuenta}"/>							      
					</h:panelGrid>
				</rich:dataList>
			</rich:column> 	
			
			<!-------------------------------- TIPO CUENTA CONCEPTOS ------------------------------------------------>
		
			<rich:column>
				<rich:dataList value="#{solicitudLiquidacionController.listaCuentaConceptoComp}" 
					var="itemCuentaConcepto" 
					rowKeyVar="rowKey"
					style="border-top: 1px; border-left: 1px;background-color:#FFFFFF;">
					<!-- Autor: jchavez / Tarea: Modificación / Fecha: 29.08.2014 -->
					<h:panelGrid columns="3" rendered="#{itemCuentaConcepto.intParaTipoConceptoCod == applicationScope.Constante.PARAM_T_TIPOCUENTA_APORTES || itemCuentaConcepto.intParaTipoConceptoCod == applicationScope.Constante.PARAM_T_TIPOCUENTA_FONDORETIRO}">
						<h:selectBooleanCheckbox  value="#{itemCuentaConcepto.cuentaConcepto.checked}" disabled="true"/>
						<h:outputText 
							value="#{rowKey+1} - #{itemCuentaConcepto.strDescripcionCuenta} - #{itemCuentaConcepto.strNumeroCuenta} - #{itemCuentaConcepto.strDescripcionConcepto}: S/.#{itemCuentaConcepto.cuentaConcepto.bdSaldo}"
							rendered="#{itemCuentaConcepto.intParaTipoConceptoCod == applicationScope.Constante.PARAM_T_TIPOCUENTA_APORTES}"/>			        
						<h:outputText 
							value="#{rowKey+1} - #{itemCuentaConcepto.strDescripcionCuenta} - #{itemCuentaConcepto.strNumeroCuenta} - #{itemCuentaConcepto.strDescripcionConcepto}: S/.#{solicitudLiquidacionController.bdMondoFondoRetiroTotal}"
							rendered="#{itemCuentaConcepto.intParaTipoConceptoCod == applicationScope.Constante.PARAM_T_TIPOCUENTA_FONDORETIRO}"/>			        
					</h:panelGrid>
					<!-- FIN jchavez 29.08.2014 -->	          
				</rich:dataList>
			</rich:column>	
		</h:panelGrid>
		<h:panelGrid id="cgTipoLiquidacion" columns="3" styleClass="tableCellBorder4">				
			<rich:column>
				<h:outputText value="Tipo de Liquidación: " />
			</rich:column>
			<rich:column rendered="#{solicitudLiquidacionController.blnMostrarDescripcionTipoLiquidacion}">
				<h:selectOneMenu
					value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.intParaSubTipoOperacion}"
					style="width: 150px;"
					onchange="selecTipoSolicitudLiquidacion(#{applicationScope.Constante.ONCHANGE_VALUE})">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{solicitudLiquidacionController.listaSubOperacion}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column rendered="#{!solicitudLiquidacionController.blnMostrarDescripcionTipoLiquidacion}">
				<h:inputText readonly="true"  value="#{solicitudLiquidacionController.strDescripcionTipoLiquidacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>		
	
		<a4j:outputPanel id="opMsgErrorSolLiquidacion">		
			<h:panelGrid id="pgMsgErrorSolLiq">			
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtProcedeEvaluacion}" styleClass="msgErrorValidacion" />	
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtProcedeEvaluacion1}" styleClass="msgErrorValidacion" />	
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtCondiciones}" styleClass="msgErrorValidacion" />	
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtSubOperacion}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtFechaRenuncia}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtFechaRecepcion}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtFechaProgramacion}" 	styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtSumaPorcentaje}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtMontoBrutoLiquidacion}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtObservacion}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtGrabar}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxttPeriodoUltimoDescuento}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtTieneBeneficiarios}" styleClass="msgErrorValidacion" />
				<h:outputText value="#{solicitudLiquidacionController.strMsgTxtParaMotivoRenuncia}" styleClass="msgErrorValidacion" />
			</h:panelGrid>
		</a4j:outputPanel>
	
		<h:panelGrid border="0" columns="2" rendered="#{not empty solicitudLiquidacionController.listaAutorizaLiquidacionComp}"  id="pgAutorizacionesPreviasLiquidacion">
			<rich:column width="120px">
				<h:outputText value="OBSERVACIONES" styleClass="tagSubTitulo"/>
			</rich:column>
			<rich:column>	
				<rich:dataGrid value="#{solicitudLiquidacionController.listaAutorizaLiquidacionComp}"
					rendered="#{not empty solicitudLiquidacionController.listaAutorizaLiquidacionComp}"
					var="item" columns="1" width="700px" border="1"
					style="background-color:#DEEBF5">
					<rich:panel style="border:1px">
						<h:panelGrid columns="2">
							<rich:column width="200px">
								<h:outputText value="Encargado de Autorización : " style="font-weight:bold"/>
							</rich:column>
							<rich:column>
								<h:outputText value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} - #{item.strPerfil}. DNI: #{item.persona.documento.strNumeroIdentidad}." />
							</rich:column>
							<rich:column width="200px">
								<h:outputText value="Resultado : " style="font-weight:bold"
									styleClass="label" />
							</rich:column>
							<rich:column>
								<h:panelGroup>
									<tumih:outputText
										cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
										itemValue="intIdDetalle" itemLabel="strDescripcion"
										property="#{item.autorizaLiquidacion.intParaEstadoAutorizar}" />-
						            <tumih:outputText
										cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO}"
										itemValue="intIdDetalle" itemLabel="strDescripcion"
										property="#{item.autorizaLiquidacion.intParaTipoAureobCod}" />
								</h:panelGroup>
							</rich:column>
							<rich:column width="200px">
								<h:outputText value="Observación : "
									style="font-weight:bold" styleClass="label" />
							</rich:column>
							<rich:column>
								<h:outputText value="#{item.autorizaLiquidacion.strObservacion}" />
							</rich:column>
							<rich:column width="200px">
								<h:outputText value="Fecha : "
									style="font-weight:bold" styleClass="label" />
							</rich:column>
							<rich:column>
								<h:outputText value="#{item.autorizaLiquidacion.tsFechaAutorizacion}" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</rich:dataGrid>
			</rich:column>				
		</h:panelGrid>					
				
		<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="REQUISITOS" styleClass="tagSubTitulo"/>
			</rich:column>
	
			<rich:column width="150px">
				<a4j:commandButton 
					id="btnEvaluacionLiquidacion"
					value="Evaluación" 
					styleClass="btnEstilos1"
					style="width: 90%"
					actionListener="#{solicitudLiquidacionController.evaluarSolicitud}"
					disabled="#{!solicitudLiquidacionController.blnCorrespondeLiquidacion}"
					reRender="pgSolicitudLiquidacion, pgPostEvaluacionLiquidacion, pgMsgErrorSolLiq, cgTipoLiquidacion" />
			</rich:column>
			<rich:column width="150px">
				<a4j:commandButton value="Imprimir" styleClass="btnEstilos1"
					style="width: 90%" />
			</rich:column>
		</h:panelGrid>
		
		<a4j:outputPanel id="pgPostEvaluacionLiquidacion" rendered="#{solicitudLiquidacionController.blnPostEvaluacion}">
			<h:panelGroup id="pgListCondiciones">
	
				<h:panelGrid columns="3" id="mpCondicionSinDeudaPendiente" >
					<rich:column width="120px"/>
	
					<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
						<h:panelGrid>
							<rich:panel>
								<h:panelGrid columns="4">
									<rich:column width="20px">
										<h:selectBooleanCheckbox
											value="#{solicitudLiquidacionController.chkCondicionSocioSinDeudaPendiente}"
											disabled="true" />
									</rich:column>
									<rich:column width="250px">
										<h:outputText value="Socio sin deuda pendiente" />
									</rich:column>
									<rich:column width="420px">
										<rich:dataList var="deuda" value="#{solicitudLiquidacionController.lstDeudaPendiente}" rows="10">
											<h:outputText value="#{deuda.strDescripcion} " styleClass="msgErrorValidacion"/>		
										</rich:dataList>
									</rich:column>
								</h:panelGrid>
							</rich:panel>
						</h:panelGrid>
					</rich:column>
				</h:panelGrid>
	
				<h:panelGrid columns="3" id="mpCondicionSinGaranteDeudores"> 
					<rich:column width="120px" />
					<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
						<h:panelGrid>
							<rich:panel>
								<h:panelGrid columns="3">
									<rich:column width="20px">
										<h:selectBooleanCheckbox 
											value="#{solicitudLiquidacionController.chkCondicionSinGarantesDeudores}"
											disabled="true" />
									</rich:column>
									<rich:column width="250px">
										<h:outputText value="Sin garantes deudores" />
									</rich:column>
									<rich:column width="420px">
					 					<rich:dataList value="#{solicitudLiquidacionController.lstMsgCondicionSinGarantesDeudores}" var="item">
											<h:panelGrid columns="1">
												<h:outputText value="#{item.strDescripcion}" styleClass="msgErrorValidacion" />
							            	</h:panelGrid>
										</rich:dataList>								
									</rich:column>
								</h:panelGrid>
							</rich:panel>
						</h:panelGrid>
					</rich:column>
				</h:panelGrid>
		
				<h:panelGrid id="mpCondicionBeneficioFondoSepelio" columns="3" rendered="#{solicitudLiquidacionController.blnIsFallecimiento}">
					<rich:column width="120px" />
					<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
						<h:panelGrid>			
							<rich:panel>
								<h:panelGrid columns="3">
									<rich:column width="20px">
										<h:selectBooleanCheckbox
											value="#{solicitudLiquidacionController.chkCondicionBeneficioFondoSepelio}"
											readonly="true" 
											disabled="true"/>
									</rich:column>
									<rich:column width="250px">
										<h:outputText
											value="Beneficio de Fondo de Sepelio(Fallecimiento)" />
									</rich:column>
									<rich:column width="420px">
										<h:outputText
											value="#{solicitudLiquidacionController.strMsgCondicionBeneficioFondoSepelio}"
											styleClass="msgErrorValidacion" />
									</rich:column>
								</h:panelGrid>
							</rich:panel>
						</h:panelGrid>
					</rich:column>
				</h:panelGrid>	
			</h:panelGroup>

			<h:panelGrid id="pgListDocAdjuntosLiquidacion" columns="3">
				<rich:column width="120px">
					<h:outputText value="Documentos :" />
				</rich:column>
				<rich:column
					style="border:1px solid #17356f; background-color:#DEEBF5">
						<rich:dataGrid value="#{solicitudLiquidacionController.listaRequisitoLiquidacionComp}"
							rendered="#{not empty solicitudLiquidacionController.listaRequisitoLiquidacionComp}"
							var="itemRequisitos" columns="2" width="790px" border="0">
							<rich:panel>
								<h:panelGrid columns="2">
									<rich:column width="250px">
										<tumih:outputText
											cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{itemRequisitos.detalle.intParaTipoDescripcion}" /> - 
	                                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
	                                   			itemValue="intIdDetalle" itemLabel="strDescripcion" 
			                                    property="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
									</rich:column>
									<rich:column>
										<a4j:commandButton value="Adjuntar Documento"
											rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}"
											actionListener="#{solicitudLiquidacionController.adjuntarDocumento}"
											oncomplete="Richfaces.showModalPanel('mpFileUpload')"
											styleClass="btnEstilos1"
											reRender="mpFileUpload">
											<f:param name="intParaTipoDescripcion"
												value="#{itemRequisitos.detalle.intParaTipoDescripcion}" />
											<f:param name="intParaTipoOperacionPersona"
												value="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}" />
										</a4j:commandButton>
									</rich:column>
									
									<rich:column id="colImgDocAdjunto" width="142" style="border: solid 1px silver; height:122px; padding:0px" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}">
											<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty itemRequisitos.fileDocAdjunto}"
												createContent="#{solicitudLiquidacionController.paintImage}" value="#{itemRequisitos.fileDocAdjunto}"
												style="width:140px; height:120px;" cacheable="false">  
											</a4j:mediaOutput>
									</rich:column>
										
									<rich:column>
										<h:commandLink value=" Descargar" target="_blank"
											action="#{solicitudLiquidacionController.descargaArchivoUltimo}"
											style="color:black;padding-left: 5px;"
											rendered="#{not empty itemRequisitos.archivoAdjunto.strNombrearchivo}">
											<f:param name="strNombreArchivo"
												value="#{itemRequisitos.archivoAdjunto.strNombrearchivo}" />
											<f:param name="intParaTipoCod"
												value="#{itemRequisitos.archivoAdjunto.id.intParaTipoCod}" />
										</h:commandLink>
									</rich:column>
	
									<rich:column>
										<h:outputText
											value="#{itemRequisitos.archivoAdjunto.strNombrearchivo}"
											rendered="#{not empty itemRequisitos.archivoAdjunto.strNombrearchivo}" />
									</rich:column>			
								</h:panelGrid>
							</rich:panel>			
					</rich:dataGrid>
				</rich:column>
			</h:panelGrid>

			<h:panelGrid>
				<rich:column width="180">
					<h:outputText value="SOLICITUD" styleClass="tagSubTitulo"/>
				</rich:column>
			</h:panelGrid>
	
			<h:panelGroup id="pgDatosSolicitudCompleta">
				
				<!-- Fecha de solicitud + Fecha Sustento Medico + x   onchanged ="selecFechaRenuncia(#{applicationScope.Constante.ONCHANGE_VALUE})"  -->
				<h:panelGrid columns="6">
					<rich:column width="150px">
						<h:outputText value="Fecha de Renuncia:"></h:outputText>
					</rich:column>
					<rich:column width="120px">
							<h:inputText value="#{solicitudLiquidacionController.strFechaRenuncia}" readonly="true" style="width:90px"/>
					</rich:column>
					
					<rich:column width="200px" >
						<h:outputText value="Fecha Recepción Renuncia:"></h:outputText>
					</rich:column>
					<rich:column width="120px">
						<rich:calendar readonly="false" id="calFecRecep"
							value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.dtFechaRecepcion}"
							datePattern="dd/MM/yyyy" inputStyle="width:70px">
							<a4j:support event="onchanged" action="#{solicitudLiquidacionController.loadFechaRenuncia}" ajaxSingle="true"
			         			reRender="pgDatosSolicitudCompleta, pgMsgErrorSolLiq "/>
						</rich:calendar>
						<h:outputText
							value="#{solicitudLiquidacionController.strMsgTxtAsterisco}"
							styleClass="msgErrorValidacion" />
					</rich:column>
											
					<rich:column width="260px" >
						<h:outputText value="Fecha de programación de Pago:"></h:outputText>
					</rich:column>
					<rich:column width="120px">
						<h:inputText value="#{solicitudLiquidacionController.strFechaProgramacionPago}" readonly="true" style="width:90px"/>
					</rich:column>
				</h:panelGrid>
				
				<!--------------- + x + x  ---------------------->
				<h:panelGrid columns="6">
					<rich:column width="150px">
						<h:outputText value="Monto Total:"></h:outputText>
					</rich:column>
					<rich:column width="150px">
						<h:inputText value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.bdMontoBrutoLiquidacion}"  readonly="true">
							<f:converter converterId="ConvertidorMontos" />	 
						</h:inputText>
					</rich:column>
									
					<rich:column width="150px">
						<h:outputText value="Periodo último descuento:"></h:outputText>
					</rich:column>
					<rich:column width="120px">
						<h:inputText size="8" value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.intPeriodoUltimoDescuento}" readonly="true" />
					</rich:column> 
					<rich:column width="80px">
						<h:outputText value="aaaa/mm"/>
					</rich:column>
					<rich:column width="150px">
						<h:outputText></h:outputText>
					</rich:column>
				</h:panelGrid>
			</h:panelGroup>
	
			<!-- BENEFIC-->
			<h:panelGrid>
				<rich:column width="1000px"
					rendered="true">
					<a4j:commandButton value="Agregar"
						styleClass="btnEstilos1" style="width: 100%"
						actionListener="#{solicitudLiquidacionController.addBeneficiario}"
						oncomplete="#{rich:component('mpBeneficiarioLiquidacion')}.show()"
						reRender="frmBusqBeneficiarioLiqui" />
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid>
				<rich:column width="120px">
					<h:outputText value="BENEFICIARIO(S)" styleClass="tagSubTitulo"/>
				</rich:column>
			</h:panelGrid>
			
			<a4j:outputPanel id="pgLitstaBeneficiariosLiquidacion">
				<h:panelGrid columns="1">
					<rich:dataTable
						value="#{solicitudLiquidacionController.listaBeneficiarioLiquidacionVista}"
						rowKeyVar="rowKey" sortMode="single" width="800px"
						var="itemBeneficiariosLiquidacion">
						
						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column width="10px" rowspan="2"/>
								<rich:column width="80px" rowspan="2">
									<h:outputText value="Nombre Completo"/>
								</rich:column>
								<rich:column width="50px" rowspan="2">
									<h:outputText value="Tipo Doc."/>
								</rich:column>
								<rich:column width="80px" rowspan="2">
									<h:outputText value="Número de Doc."/>
								</rich:column>
								<rich:column width="80px" rowspan="2">
									<h:outputText value="Relación"/>
								</rich:column>
								<rich:column width="40px" rowspan="2">
									<h:outputText value="Aporte"/>
								</rich:column >
								<rich:column width="80px" rowspan="2">
									<h:outputText value=" S/. Aporte"/>
								</rich:column >											
								<rich:column width="40px" rowspan="2">
									<h:outputText value="F. Retiro"/>
								</rich:column>
								<rich:column width="80px" rowspan="2">
									<h:outputText value="S/. F. Retiro"/>
								</rich:column>
								<rich:column width="40px" rowspan="2">
									<h:outputText value="Ahorros"/>
								</rich:column>
								<rich:column width="80px" rowspan="2">
									<h:outputText value="S/. Ahorros"/>
								</rich:column> 
								<rich:column width="80px" rowspan="2">
									<h:outputText value="Total"/>
								</rich:column>
								<rich:column rowspan="2" />
							</rich:columnGroup>
						</f:facet>
						
							<rich:column width="15px">
								<div align="center">
									<h:outputText value="#{rowKey+1}" />
								</div>
							</rich:column>
							<rich:column width="80px">
								<h:outputText
									value="#{itemBeneficiariosLiquidacion.persona.natural.strApellidoPaterno} #{itemBeneficiariosLiquidacion.persona.natural.strApellidoMaterno},#{itemBeneficiariosLiquidacion.persona.natural.strNombres}" />
							</rich:column>
							<rich:column width="50px">
								<tumih:outputText
									cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemBeneficiariosLiquidacion.persona.documento.intTipoIdentidadCod}" />
							</rich:column>
							<rich:column width="80px">
								<h:outputText
									value="#{itemBeneficiariosLiquidacion.persona.documento.strNumeroIdentidad}" />
							</rich:column>
							<rich:column width="80px">
								<tumih:outputText
									cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemBeneficiariosLiquidacion.intTipoViculo}" />
							</rich:column>
							<rich:column width="40px">
								<h:inputText value="#{itemBeneficiariosLiquidacion.bdPorcentajeBeneficioApo}" 
									readonly="#{!solicitudLiquidacionController.blnMostrarObjeto}" onkeyup="extractNumber(this,2,false);">
								</h:inputText>
							</rich:column>			
							<rich:column width="80px">
								<h:outputText  value="#{itemBeneficiariosLiquidacion.bdMontoAporte}" >
									<f:converter converterId="ConvertidorMontos"  />
								</h:outputText>
							</rich:column>
							<rich:column width="40px">
								<h:inputText  value="#{itemBeneficiariosLiquidacion.bdPorcentajeBeneficioRet}"
								readonly="#{!solicitudLiquidacionController.blnMostrarObjeto}" onkeyup="extractNumber(this,2,false);"/>
							</rich:column>
							<rich:column width="80px">
								<h:outputText value="#{itemBeneficiariosLiquidacion.bdMontoRetiro}">
									<f:converter converterId="ConvertidorMontos"  />
								</h:outputText>
							</rich:column>
							<rich:column width="40px">
								<h:inputText  value="#{itemBeneficiariosLiquidacion.bdPorcentajeBeneficioAhorro}"
								readonly="#{!solicitudLiquidacionController.blnMostrarObjeto}" onkeyup="extractNumber(this,2,false);"/>
							</rich:column>
							<rich:column width="80px">
								<h:outputText value="#{itemBeneficiariosLiquidacion.bdMontoAhorro}">
									<f:converter converterId="ConvertidorMontos"  />
								</h:outputText>
							</rich:column>
							<rich:column width="80px">
								<h:outputText value="#{itemBeneficiariosLiquidacion.bdMontoTotal}">
									<f:converter converterId="ConvertidorMontos"  />
								</h:outputText>
							</rich:column>
							<rich:column>
				            	<a4j:commandLink id="lnkQuitarBeneficiarioLiquidacion" 
				            		rendered="#{solicitudLiquidacionController.blnMostrarObjeto}"
			               			onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;" 
			               			actionListener="#{solicitudLiquidacionController.removerBeneficiarioLiquidacion}" reRender="pgLitstaBeneficiariosLiquidacion">
			   						<f:param name="rowKeyBeneficiarioLiquidacion" value="#{rowKey}" />
			   						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
			   						<rich:toolTip for="lnkQuitarBeneficiarioLiquidacion" value="Quitar" followMouse="true"/>
			   					</a4j:commandLink>
		           		 	</rich:column>
	
							<f:facet name="footer">          
								<rich:columnGroup>	
									<rich:column colspan="5" style="text-align: right" >
										<h:outputText value="Totales"/>
									</rich:column>
									<rich:column width="40px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdPorcentajeBeneficioApo}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>			
									<rich:column width="80px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdMontoAporte}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
									<rich:column width="40px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdPorcentajeBeneficioRet}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
									<rich:column width="80px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdMontoRetiro}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
									<rich:column width="40px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdPorcentajeBeneficioAhorro}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
									<rich:column width="80px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdMontoAhorro}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
									<rich:column colspan="2" width="80px">
										<h:outputText value="#{solicitudLiquidacionController.beneficiarioTotales.bdMontoTotal}">
											<f:converter converterId="ConvertidorMontos" />
										</h:outputText>
									</rich:column>
							   </rich:columnGroup>
							</f:facet>			
						</rich:dataTable>
				</h:panelGrid>
			</a4j:outputPanel>
			
			<h:panelGrid columns="3">
				<rich:column width="120px" />
				<rich:column width="500px">
					<h:outputText id="txtValidacionBeneficario"
						value="#{solicitudLiquidacionController.strMsgTxtValidacionbeneficiarios}"
						styleClass="msgErrorValidacion" />							
				</rich:column>
		
				<rich:column width="150px" rendered="#{solicitudLiquidacionController.blnMostrarObjeto}">
					<a4j:commandButton value="ReCalcular" styleClass="btnEstilos1"
						style="width: 90%"
						rendered="#{not empty solicitudLiquidacionController.listaBeneficiarioLiquidacionVista}"
						action="#{solicitudLiquidacionController.reCalcularMontosGrillaBeneficiarios}"
						reRender="txtValidacionBeneficario, pgLitstaBeneficiariosLiquidacion"
						/>
				</rich:column>
			</h:panelGrid>

			<!-- Fecha de solicitud + Fecha Sustento Medico + x   -->
			<h:panelGrid columns="6" id="pgMotivoRenuncia" rendered="#{solicitudLiquidacionController.blnIsRenuncia}">
				<rich:column width="150px">
					<h:outputText value="Motivo de Renuncia:"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:selectOneMenu value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.intParaMotivoRenuncia}"
									style="width: 200px;">
						<tumih:selectItems var="sel"
							value="#{solicitudLiquidacionController.listaMotivoRenuncia}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>

			<h:panelGrid columns="3">
				<rich:column width="120px">
					<h:outputText value="Observación:" />
				</rich:column>
				<rich:column width="800px">
					<h:inputTextarea rows="5" cols="140"
						value="#{solicitudLiquidacionController.beanExpedienteLiquidacion.strObservacion}" >
					</h:inputTextarea>
				</rich:column>
			</h:panelGrid>
		</a4j:outputPanel>
	</h:panelGroup>		
