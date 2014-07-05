<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<h:panelGroup id="pgSolicitudRefinan">
	
	<h:panelGrid columns="3">
		<rich:column width="200px">
			<h:outputText value="DATOS PERSONALES" style="font-weight:bold;text-decoration:underline;"></h:outputText>
		</rich:column>
		<rich:column>
		</rich:column>
		<rich:column>
		</rich:column>
	</h:panelGrid>
	
	<rich:spacer height="10"></rich:spacer>

	<h:panelGrid columns="4" styleClass="tableCellBorder4">
		<rich:column width="120px">
			<h:outputText value="Nombre Completo: " styleClass="estiloLetra1"></h:outputText>
		</rich:column>
		<rich:column>
			<h:inputText readonly="true" value="#{solicitudRefinanController.beanSocioComp.persona.intIdPersona} - #{solicitudRefinanController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudRefinanController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudRefinanController.beanSocioComp.persona.natural.strNombres} - #{solicitudRefinanController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="90"/>
		</rich:column>
		<rich:column>
			<h:outputText value="Condición: " styleClass="estiloLetra1"></h:outputText>
		</rich:column>
		<rich:column>
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion"
				property="#{solicitudRefinanController.beanSocioComp.cuenta.intParaCondicionCuentaCod}" /> 
			- 
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion"
				property="#{solicitudRefinanController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}" />
		</rich:column>
	</h:panelGrid>
	
	<h:panelGrid columns="8" styleClass="tableCellBorder4">
		<rich:column width="120px">
			<h:outputText value="Número de Cuenta: " styleClass="estiloLetra1"/>
		</rich:column>
		<rich:column>
			<h:inputText value="#{solicitudRefinanController.beanSocioComp.cuenta.strNumeroCuenta}" 
						readonly="true" size="20" />
		</rich:column>
		<rich:column>
			<h:outputText value="Estado de Solicitud: " styleClass="estiloLetra1"/>
		</rich:column>
		<rich:column>
			<h:inputText value="#{solicitudRefinanController.strUltimoEstado}" readonly="true" />
		</rich:column>
		<rich:column>
			<h:outputText value="N° Solicitud: " styleClass="estiloLetra1"/>
		</rich:column>
		<rich:column>
			<h:inputText value="#{solicitudRefinanController.beanExpedienteCredito.id.intItemExpediente}-#{solicitudRefinanController.beanExpedienteCredito.id.intItemDetExpediente}"  
						styleClass="estiloLetra1" readonly="true" size="5"/>
		</rich:column>
		<rich:column>
			<h:outputText value="Tipo de Operación: " styleClass="estiloLetra1"/>
		</rich:column>
		<rich:column>
			<h:inputText value="#{solicitudRefinanController.strSubTipoOperacion}" readonly="true" />
		</rich:column>
	</h:panelGrid>
	
	<rich:spacer height="20"></rich:spacer>
	
	<h:panelGrid columns="2" styleClass="tableCellBorder4">
		<rich:columnGroup>
			<rich:column width="120px">
				<h:outputText value="Dependencia: " styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Agregar" styleClass="btnEstilos" 		
								actionListener="#{socioEstrucController.addEntidadSocioRef}"
								oncomplete="#{rich:component('mpSocioEstructuraRef')}.show()"
								reRender="mpSocioEstructuraRef,frmSocioEstructuraRef"/>
			</rich:column>
		</rich:columnGroup>

		<rich:columnGroup>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			
			<rich:column>
				<h:panelGrid id="pgListCapacidadRefinan">
					<rich:dataTable value="#{solicitudRefinanController.listaCapacidadCreditoComp}" 
									rowKeyVar="rowKey" sortMode="single" width="800px" var="item">
						<rich:column>
							<h:outputText value="#{rowKey+1}"/>
						</rich:column>
						<rich:column width="200px">
							<f:facet name="header" >
								<h:outputText value="Dependencia" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText value="#{solicitudPrestamoController.listEstructura}" 
											itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
											property="#{item.socioEstructura.intCodigo}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Sucursal" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText value="#{solicitudPrestamoController.listSucursal}" 
											itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
											property="#{item.socioEstructura.intIdSucursalUsuario}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Planilla" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.socioEstructura.intModalidad}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Destacado" styleClass="estiloLetra1"/>
							</f:facet>
							<h:outputText/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Proceso" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA}"
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.socioEstructura.intTipoEstructura}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Carta 100%" styleClass="estiloLetra1"/>
							</f:facet>
							<h:outputText value="#{item.capacidadCredito.intCartaAutorizacion==1?'Si':'No'}"/>
						</rich:column>
						<rich:column width="90px">
							<f:facet name="header">
								<h:outputText value="Capacidad" styleClass="estiloLetra1"/>
							</f:facet>
							<div align="right"><h:outputText value="#{item.capacidadCredito.bdCapacidadPago}"/></div>
						</rich:column>
						<rich:column width="90px">
							<f:facet name="header">
								<h:outputText value="Cuota Fija" styleClass="estiloLetra1"/>
							</f:facet>
							<div align="right"><h:outputText value="#{item.capacidadCredito.bdCuotaFija}"/></div>
						</rich:column>
						<rich:column width="90px">
							<f:facet name="header">
								<h:outputText value="Operaciones" styleClass="estiloLetra1"/>
							</f:facet>
							<div align="center">
								<a4j:commandLink id="lnkViewDependencia" styleClass="no-decor"  reRender="frmCapacidadPago" 
									actionListener="#{capacidadPagoController.irModificarCapacidadCreditoRef}"
									oncomplete="Richfaces.showModalPanel('mpCapacidadCreditoRef'),disableFormCapacidadPago()">
										<h:graphicImage value="/images/icons/page_detail_20.png" alt="edit"/>
										<f:param value="#{item.socioEstructura.intModalidad}" name="pIntModalidad"/>
										<f:param value="#{rowKey}" name="rowKeyCapacidadCreditoRef"/>
										<rich:toolTip for="lnkViewDependencia" value="Ver" followMouse="true"/>
								</a4j:commandLink>
								&nbsp;
								<a4j:commandLink id="lnkEditDependencia" styleClass="no-decor" reRender="frmCapacidadPago" 
								rendered="#{!solicitudRefinanController.blnPostEvaluacion}"
								actionListener="#{capacidadPagoController.irModificarCapacidadCreditoRef}"
								oncomplete="Richfaces.showModalPanel('mpCapacidadCreditoRef')">
									<h:graphicImage value="/images/icons/edit.png" alt="edit"/>
									<f:param value="#{item.socioEstructura.intModalidad}" name="pIntModalidad"/>
									<f:param value="#{rowKey}" name="rowKeyCapacidadCreditoRef"/>
									<rich:toolTip for="lnkEditDependencia" value="Editar" followMouse="true"/>
								</a4j:commandLink>
							</div>
						</rich:column>
					</rich:dataTable>
				</h:panelGrid>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>
	
	<rich:spacer height="20"></rich:spacer>
	
	<h:panelGrid columns="2"  styleClass="tableCellBorder4">
		<rich:columnGroup>
			<rich:column width="120px">
				<h:outputText value="Saldo de Cuentas : " styleClass="estiloLetra1"/>	
			</rich:column>

			<rich:column>
				<rich:dataTable id="pgListSaldoCuentasMix" value="#{solicitudRefinanController.beanSocioComp.cuentaComp}" var="itemCuentaComp"
							width="800px">
					<f:facet name="header">
						<rich:columnGroup>
							<rich:column rowspan="2"/>
							<rich:column width="80px" rowspan="2">
								<h:outputText value="Tipo de Cuenta"/>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<h:outputText value="Número de Cuenta"/>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<h:outputText value="Aporte"/>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<h:outputText value="Fondo de Retiro"/>
							</rich:column>
							<rich:column width="80px" rowspan="2">
								<h:outputText value="Fondo de Sepelio"/>
							</rich:column>
							<rich:column colspan="6">
								<h:outputText value="Préstamo"/>
							</rich:column>
							<rich:column breakBefore="true">
								<h:outputText value="Sol."/>
							</rich:column>
							<rich:column >
								<h:outputText value="Tipo"/>
							</rich:column>
							<rich:column>
								<h:outputText value="Monto"/>
							</rich:column>
							<rich:column>
								<h:outputText value="CP/Cta"/>
							</rich:column>
							<rich:column>
								<h:outputText value="Saldo"/>
							</rich:column>
							<rich:column>
								<h:outputText value="Sel." />
							</rich:column> 
						</rich:columnGroup>	
					</f:facet>
					
					<rich:column width="15px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<div align="center">
							<h:outputText value="#{rowKey+1}" />
						</div>
					</rich:column>
					<rich:column rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTAREQUISITOS}"  
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{solicitudRefinanController.beanSocioComp.cuenta.intParaTipoCuentaCod}"/>
					</rich:column>
					<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText value="#{solicitudRefinanController.beanSocioComp.cuenta.strNumeroCuenta}"/>
					</rich:column>
					<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText  value="#{itemCuentaComp.bdTotalAporte}">
							<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>	
					</rich:column>
					<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText value="#{itemCuentaComp.bdTotalRetiro}">
							<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</rich:column>
					<rich:column rowspan="#{itemCuentaComp.intTamannoListaExp}">
					
					<h:outputText value="#{itemCuentaComp.bdTotalSepelio}">
							<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</rich:column>
					
					<rich:subTable value="#{itemCuentaComp.listExpedienteMovimientoComp}" var="itemExpedientes" rowKeyVar="appPiVerKeyVar">
						<rich:column>
							<h:outputText value="#{itemExpedientes.expediente.id.intItemExpediente}-#{itemExpedientes.expediente.id.intItemExpedienteDetalle}"/>
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.strDescripcionTipoCreditoEmpresa}"/>
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.expediente.bdMontoTotal}" >
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.intNroCuotasPagadas}/#{itemExpedientes.intNroCuotasDefinidas}" />
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.bdSaldo}">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
						<rich:column >
							<h:selectBooleanCheckbox value="#{itemExpedientes.checked}" disabled="#{solicitudRefinanController.blnBloquearcheck}"/>
						</rich:column>
					</rich:subTable> 
				</rich:dataTable>
			
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>
	
	<rich:spacer height="20"></rich:spacer>
	
	<h:panelGrid columns="2"  styleClass="tableCellBorder4" rendered="#{solicitudRefinanController.blnPostEvaluacion}">
		<rich:columnGroup id="cgGarantes" >
			<rich:column width="120px">
				<h:outputText value="Garantes Solidarios:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Agregar" oncomplete="#{rich:component('mpGarantesSolidariosRef')}.show()"
				disabled="#{solicitudRefinanController.blnBtnAddGarante}"
				styleClass="btnEstilos"/>
			</rich:column>
		</rich:columnGroup>
			
		<rich:columnGroup id="pgListGarantesSolidarios" rendered="#{not empty solicitudRefinanController.listaGarantiaCreditoComp}">
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:panelGrid>
					<rich:dataTable value="#{solicitudRefinanController.listaGarantiaCreditoComp}"
					rowKeyVar="rowKey" sortMode="single" width="800px" var="itemListaGarantia"
					rendered="#{not empty solicitudRefinanController.listaGarantiaCreditoComp}">
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Nombres Completos" styleClass="estiloLetra1"/>
							</f:facet>
							<h:outputText value="#{itemListaGarantia.socioComp.persona.natural.strApellidoPaterno} #{item.socioComp.persona.natural.strApellidoMaterno} #{itemListaGarantia.socioComp.persona.natural.strNombres} - #{itemListaGarantia.socioComp.persona.documento.strNumeroIdentidad}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="U. Ejecutora" styleClass="estiloLetra1"/>
							</f:facet>
							<h:outputText value="#{itemListaGarantia.estructura.juridica.strRazonSocial}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Sucursal" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText value="#{solicitudRefinanController.listSucursal}" 
							itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
							property="#{itemListaGarantia.socioComp.socio.socioEstructura.intIdSucursalAdministra}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Tipo Trabajador" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
	                           itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                           property="#{itemListaGarantia.socioComp.persona.natural.perLaboral.intCondicionLaboral}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Condición" styleClass="estiloLetra1"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemListaGarantia.socioComp.cuenta.intParaCondicionCuentaCod}" />
							-
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemListaGarantia.socioComp.cuenta.intParaSubCondicionCuentaCod}" />
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Nro. Garantizados" styleClass="estiloLetra1"/>
							</f:facet>
							<div align="center"><h:outputText value="#{itemListaGarantia.intNroGarantizados}"/></div>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Observaciones" styleClass="estiloLetra1"/>
							</f:facet>
							<div align="center"><h:outputText value="#{itemListaGarantia.socioComp.strDescValidaRefinan}" styleClass="msgError"/></div>
						</rich:column>

						<rich:column>
							<a4j:commandLink id="lnkQuitarGaranteSolidario" disabled="false"
							onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;" 
							actionListener="#{solicitudRefinanController.removeGaranteSolidario}" reRender="pgListGarantesSolidarios,pgSolicitudRefinan">
								<f:param name="rowKeyGaranteSolidario" value="#{rowKey}" />
								<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
								<rich:toolTip for="lnkQuitarGaranteSolidario" value="Quitar" followMouse="true"/>
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
				</h:panelGrid>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>	
	
	<h:panelGrid id="pgMsgErrorGarantesObs"  styleClass="tableCellBorder4" columns="2">
		<rich:column width="120px">
				<h:outputText value="" styleClass="estiloLetra1"/>
		</rich:column>
		<rich:column>
			<h:outputText value="#{solicitudRefinanController.strMensajeGarantesObservacion}" styleClass="msgError"/>
		</rich:column>	
	</h:panelGrid>
	

			
	<rich:spacer height="10"></rich:spacer>
	

	
	<h:panelGrid  styleClass="tableCellBorder4">	
		<rich:columnGroup>
			<rich:column  width="120px">
				<h:outputText value="Fecha de Solicitud: " styleClass="estiloLetra1"></h:outputText>
			</rich:column>
			<rich:column>
				<rich:calendar readonly="true" value="#{solicitudRefinanController.dtFechaRegistro}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px">
				</rich:calendar>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>
	
	<h:panelGrid id="pgMsgErrorSolRefinan"  styleClass="tableCellBorder4">
		<h:outputText value="#{solicitudRefinanController.strMsgErrorConfiguracionRefinan}" styleClass="msgError" />
		<h:outputText value="#{solicitudRefinanController.strMsgErrorPreEvaluacion}" styleClass="msgError" />
		<h:outputText value="#{solicitudRefinanController.strMsgErrorExisteRefinanciamiento}" styleClass="msgError" />
		<h:outputText value="#{solicitudRefinanController.msgTxtCuotaMensual}" styleClass="msgError"/>
		<h:outputText value="#{solicitudRefinanController.msgTxtListaCapacidadPago}" styleClass="msgError"/>
		<h:outputText value="#{solicitudRefinanController.strMsgMasDeUnoSeleecionado}" styleClass="msgError"/>
		<h:outputText value="#{solicitudRefinanController.strMensajeEdadSocio}" styleClass="msgError"/>
	</h:panelGrid>
	
	<h:panelGrid columns="4"  styleClass="tableCellBorder4">
		<rich:column width="120px">
			<h:outputText value="REQUISITOS :" style="font-weight:bold;text-decoration:underline;"></h:outputText>
		</rich:column>
		
		<rich:column width="150px">
			<a4j:commandButton value="EvaluaMMción" styleClass="btnEstilos1" style="width: 90%"
			actionListener="#{solicitudRefinanController.evaluarRefinanciamiento}"
			reRender="pgSolicitudRefinan,pgPostEvaluacionRef,pgMsgErrorSolRefinan,pgListCondicionesRef ,
					  pgListDocAdjuntosRef,pgListCapacidadRefinan" />
		</rich:column>
		
		<rich:column width="150px">
			<a4j:commandButton style="width: 90%" value="Cronograma" oncomplete="#{rich:component('mpCronogramaRefinanciamiento')}.show()"
				styleClass="btnEstilos" reRender="frmCronogramaRefinan">
			</a4j:commandButton>
		</rich:column>
		
		<rich:column width="150px">
			<a4j:commandButton style="width: 90%" value="Imprimir" styleClass="btnEstilos1"
				style="width: 90%" />
		</rich:column>
	</h:panelGrid>
	
	<rich:spacer height="20"></rich:spacer>
	
				<h:panelGrid border="0" columns="2" rendered="#{not empty solicitudRefinanController.listaAutorizaCreditoComp}">
				<rich:column width="120px">
					<h:outputText style="font-weight:bold;text-decoration:underline;" value="OBSERVACIONES"/>
				</rich:column>
				<rich:column>	
					<rich:dataGrid value="#{solicitudRefinanController.listaAutorizaCreditoComp}"
						rendered="#{not empty solicitudRefinanController.listaAutorizaCreditoComp}"
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
											property="#{item.autorizaCredito.intParaEstadoAutorizar}" />-
							            <tumih:outputText
											cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{item.autorizaCredito.intParaTipoAureobCod}" />.
									</h:panelGroup>
								</rich:column>
								<rich:column width="200px">
									<h:outputText value="Observación : "
									style="font-weight:bold" styleClass="label" />
								</rich:column>
								<rich:column>
									<h:outputText
									value="#{item.autorizaCredito.strObservacion}" />.
								</rich:column>
								<rich:column width="200px">
									<h:outputText value="Fecha : "
									style="font-weight:bold" styleClass="label" />
								</rich:column>
								<rich:column>
									<h:outputText
									value="#{item.autorizaCredito.tsFechaAutorizacion}" />
								</rich:column>
							</h:panelGrid>
						</rich:panel>
					</rich:dataGrid>
				</rich:column>
				
			</h:panelGrid>
			
	<rich:spacer height="20"></rich:spacer>

	<h:panelGroup id="pgPostEvaluacionRef" rendered="#{solicitudRefinanController.blnPostEvaluacion}"  styleClass="tableCellBorder4">
		<h:panelGrid columns="8" styleClass="tableCellBorder4">
			<rich:columnGroup>
				<rich:column width="120px">
					<h:outputText value="Tipo de Operación:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column width="100px">
					<h:inputText size="15"  value="#{solicitudRefinanController.strSubTipoOperacion}" readonly="true"></h:inputText>
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="% Interés:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column width="100px">
					<h:inputText size="10" value="#{solicitudRefinanController.strPorcentajeInteres}" readonly="true">
					</h:inputText>
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="Nro. Cuotas:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column width="100px">
					<h:inputText size="10" value="#{solicitudRefinanController.intNroCuotas}" readonly="false"
						onblur="extractNumber(this,2,false);" 
						onkeyup="extractNumber(this,2,false);"/>
				</rich:column>
				<rich:column width="100px">
					<h:outputText value=""></h:outputText>
				</rich:column>
				<rich:column width="100px">
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>
			
		<h:panelGroup styleClass="tableCellBorder4">
			<h:panelGrid columns="8" styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column width="120px">
					<h:outputText value="Monto del Préstamo:" styleClass="estiloLetra1"></h:outputText>
					</rich:column>
					<rich:column width="100px">
						<h:inputText size="10" value="#{solicitudRefinanController.strMontoTotalDelPrestamoRefinanciado}" readonly="true"
							onblur="extractNumber(this,2,false);" 
							onkeyup="extractNumber(this,2,false);" />
					</rich:column>
					<rich:column width="100px">
						<h:outputText value="Interés Atrasado:" styleClass="estiloLetra1"></h:outputText>
					</rich:column>
					<rich:column width="100px">
						<h:inputText size="10" value="#{solicitudRefinanController.strInteresAtrasadoDelPrestamoRefinanciado}" readonly="true">
						</h:inputText>(+)
					</rich:column>
					<rich:column width="100px">
						<h:outputText value="Mora atrasada:" styleClass="estiloLetra1"></h:outputText>
					</rich:column>
					<rich:column width="100px">
						<h:inputText size="10" value="#{solicitudRefinanController.strMoraAtrasadoDelPrestamoRefinanciado}" readonly="true">
						</h:inputText>(+)
					</rich:column>
					<rich:column width="100px">
						<h:outputText value="Monto Solicitado:" styleClass="estiloLetra1"></h:outputText>
					</rich:column>
					<rich:column width="100px">
						<h:inputText size="15"  value="#{solicitudRefinanController.strMontoDelPrestamoRefinanciado}" readonly="true"
							onblur="extractNumber(this,2,false);" 
							onkeyup="extractNumber(this,2,false);" />
					</rich:column>
				</rich:columnGroup>
			</h:panelGrid>
		</h:panelGroup>
			
		<h:panelGrid columns="8" styleClass="tableCellBorder4">
			<rich:columnGroup>
				<rich:column width="120px">
				<h:outputText value="Cuota Fija:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column width="120px">
					<h:inputText  size="15" value="#{solicitudRefinanController.bdTotalCuotaMensual}" readonly="true"></h:inputText>
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>

		<h:panelGroup id="pgListCondicionesRef">
			
			<!--
			<rich:column>
			<tumih:outputText
				value="#{solicitudRefinanController.listaEstadoPrestamo}"
			itemValue="intIdDetalle" itemLabel="strDescripcion"
			property="#{solicitudRefinanController.beanExpedienteCredito.estadoCreditoUltimo.intParaEstadoCreditoCod}" />
			</rich:column>
			
			
			<h:panelGrid columns="3" id="mpCondicionCancelarPorPlanilla">
			<rich:column width="120px">
			<h:outputText value="Condiciones :" />
			</rich:column>
			<rich:column>
			<h:outputText value="" />
			</rich:column>
			
			<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
			<h:panelGrid>
			<rich:panel>
			<h:panelGrid columns="2">
			<rich:column width="250px">
			<h:outputText value="Cancelar por Planilla más del 40% del crédito vigente." />
			</rich:column>
			<rich:column width="420px">
			<h:outputText
				value="#{solicitudRefinanController.strMsgCondicionCancelarPorPlanilla}"
			styleClass="msgError2" />
			</rich:column>
			</h:panelGrid>
			</rich:panel>
			</h:panelGrid>
			</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="3" id="mpCondicionPlazoMaximoPrestamo"> 
			<rich:column width="120px">
			<h:outputText value="" />
			</rich:column>
			<rich:column>
			<h:outputText value="" />
			</rich:column>
			
			<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
			<h:panelGrid>
			<rich:panel>
			<h:panelGrid columns="2">
			<rich:column width="250px">
			<h:outputText value="Plazo máximo del tipo de préstamo = 12 meses." />
			</rich:column>
			<rich:column width="420px">
			<h:outputText
				value="#{solicitudRefinanController.strMsgCondicionPlazoMaximoPrestamo}"
			styleClass="msgError2" />
			</rich:column>
			</h:panelGrid>
			</rich:panel>
			</h:panelGrid>
			</rich:column>
			</h:panelGrid>
			-->
			
			<h:panelGrid id="mpCondicionTiempoCeseTrabajador" columns="3" styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column width="120px">
						<h:outputText value="" />
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
					<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
						<h:panelGrid>
							<rich:panel>
								<h:panelGrid columns="2">
									<rich:column width="250px">
										<h:outputText value="Plazo máximo del tipo de préstamo es de 65 años." />
									</rich:column>
									<rich:column width="420px">
										<h:outputText value="#{solicitudRefinanController.strMsgCondicionTiempoCeseTrabajador}"
											styleClass="msgError2" />
									</rich:column>
								</h:panelGrid>
							</rich:panel>
						</h:panelGrid>
					</rich:column>
				</rich:columnGroup>
				
			</h:panelGrid>	
		</h:panelGroup>
			
		<h:panelGroup>
			<h:panelGrid id="pgListDocAdjuntosRef" columns="3" styleClass="tableCellBorder4">
				<rich:columnGroup>
					<rich:column width="120px">
						<h:outputText value="Documentos :" styleClass="estiloLetra1"/>
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
					<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
						<h:panelGrid>
							<rich:dataGrid value="#{solicitudRefinanController.listaRequisitoCreditoComp}"
							var="itemRequisitos" columns="2" width="710px" border="0">
								<rich:panel>
									<h:panelGrid columns="2">
										<rich:column width="250px">
											<tumih:outputText value="#{solicitudRefinanController.listaTablaDescripcionAdjuntos}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{itemRequisitos.detalle.intParaTipoDescripcion}" /> - 
											  	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
											 			itemValue="intIdDetalle" itemLabel="strDescripcion" 
											    property="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
										</rich:column>
										<!--<rich:column id="colImgDocAdjunto" width="142" style="border: solid 1px silver; height:122px; padding:0px" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}">
													<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty itemRequisitos.fileDocAdjunto}"
														createContent="#{solicitudRefinanController.paintImage}" value="#{itemRequisitos.fileDocAdjunto}"
													style="width:140px; height:120px;" cacheable="false">  
												</a4j:mediaOutput>
										</rich:column>-->
										<rich:column>
										<a4j:commandButton value="Adjuntar Documento"
											rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}"
										actionListener="#{solicitudRefinanController.adjuntarDocumento}"
											oncomplete="Richfaces.showModalPanel('mpFileUpload')"
										styleClass="btnEstilos1"
											reRender="mpFileUpload, pgListDocAdjuntosRef">
										<f:param name="intParaTipoDescripcion"
											value="#{itemRequisitos.detalle.intParaTipoDescripcion}" />
										<f:param name="intParaTipoOperacionPersona"
											value="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}" />
										</a4j:commandButton>
										</rich:column>
										
										<rich:column>
										<h:commandLink value=" Descargar" target="_blank"
											action="#{solicitudRefinanController.descargaArchivoUltimo}"
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
						</h:panelGrid>
					</rich:column>
				</rich:columnGroup>
				
			</h:panelGrid>
		</h:panelGroup>
			
		<rich:spacer height="10"></rich:spacer>
			
		<h:panelGrid styleClass="tableCellBorder4">
			<rich:column width="776">
				<h:outputText value="SOLICITUD:" style="font-weight:bold;text-decoration:underline;"></h:outputText>
			</rich:column>
			<rich:column>
			</rich:column>
			<rich:column>
			</rich:column>
		</h:panelGrid>
						
		<h:panelGrid columns="4" styleClass="tableCellBorder4">
			<rich:columnGroup>
				<rich:column width="120px">
					<h:outputText value="Motivo de Préstamo:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>
				<rich:column width="800px">
					<h:selectOneMenu style="width:190px" value="#{solicitudRefinanController.intMotivoRefinanciamiento}">
						<tumih:selectItems var="sel" value="#{solicitudRefinanController.listaMotivoPrestamo}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:outputText value=""/>
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>
			
		<h:panelGrid columns="4" styleClass="tableCellBorder4">
			<rich:columnGroup>
				<rich:column width="120px">
					<h:outputText value="Observación:" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>
				<rich:column width="800px">
					<h:inputTextarea rows="5" cols="140"
						value="#{solicitudRefinanController.beanExpedienteCredito.strObservacion}" />
				</rich:column>
				<rich:column>
					<h:outputText value="#{solicitudRefinanController.msgTxtObservacion}" styleClass="msgError"/>
				</rich:column>
			</rich:columnGroup>
		</h:panelGrid>
	</h:panelGroup>
	
</h:panelGroup>


