<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Solicitud Crédito Edit   -->
	<!-- Prototipo : Solicitud de Crédito     -->			
	<!-- Fecha     : 20/07/2012               -->

<a4j:region>
	<a4j:jsFunction name="getMontoSolicitado" reRender="idMontoSolicitado"
		actionListener="#{solicitudPrestamoController.setMontoSolicitado}">
		<f:param name="bdMontoSolicitado"/>
	</a4j:jsFunction>
</a4j:region>
	

<rich:panel rendered="#{solicitudPrestamoController.formSolicitudPrestamoRendered}" style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
	<h:panelGroup id="pgSolicitudCreditoView">
	<h:panelGroup id="pgValidDatos" rendered="#{solicitudPrestamoController.pgValidDatos}" layout="block">
		<h:panelGrid columns="7">
			<rich:column>
				<h:outputText value="Relación :"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudPrestamoController.intTipoRelacion}">
					<tumih:selectItems var="sel" value="#{solicitudPrestamoController.listaRelacion}" 
	                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
	                       propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Tipo de Crédito :"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudPrestamoController.intTipoCredito}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSCREDITOPANTALLA}" 
		                itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Tipo de Documento :"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudPrestamoController.personaBusqueda.documento.intTipoIdentidadCod}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
		                propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.personaBusqueda.documento.strNumeroIdentidad}" onkeydown="return validarEnteros(event);" size="20" maxlength="11"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid style="width: 100%">
			<a4j:commandButton value="Validar Datos" actionListener="#{solicitudPrestamoController.validarDatos}" style="width: 100%" styleClass="btnEstilos1" reRender="pgSolicCredito,pgListSaldoCuentas,pgMsgErrorValidarDatos"/>
		</h:panelGrid>
		<rich:panel style="width:949px;border-bottom:1px solid #17356f;border-top:none;border-right:none;border-left:none;background-color:#DEEBF5;"/>
		
		<h:panelGrid id="pgMsgErrorValidarDatos">
					<h:outputText value="#{solicitudPrestamoController.strMsgErrorValidarDatos}" styleClass="msgError"/>
		</h:panelGrid>
	
	</h:panelGroup>
	
	<h:panelGroup id="pgDatosSocio" rendered="#{solicitudPrestamoController.blnDatosSocio}" layout="block">
		<h:panelGrid columns="5">
			<rich:column width="120px">
				<h:outputText value="Nombre Completo"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.persona.intIdPersona} - #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudPrestamoController.beanSocioComp.persona.natural.strNombres} - #{solicitudPrestamoController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="90" disabled="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Condición :"/>
			</rich:column>
			<rich:column>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
	                              itemValue="intIdDetalle" itemLabel="strDescripcion"
	                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaCondicionCuentaCod}"/> -
	            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
	                              itemValue="intIdDetalle" itemLabel="strDescripcion"
	                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="7">
			<rich:column width="120px">
				<h:outputText value="Número de Cuenta"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.cuenta.strNumeroCuenta}" size="30" disabled="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado de Solicitud :"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.strUltimoEstadoSolicitud}"size="30" disabled="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud :"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.beanExpedienteCredito.id.intItemExpediente}-#{solicitudPrestamoController.beanExpedienteCredito.id.intItemDetExpediente}" disabled="true"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="U. Ejecutora"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
			</rich:column>
		</h:panelGrid>
           
            
		<h:panelGrid id="pgListCapacidadCredito">
			<rich:dataTable value="#{solicitudPrestamoController.listaCapacidadCreditoComp}" 
				rowKeyVar="rowKey" sortMode="single" width="950px" var="item"
				rendered="#{not empty solicitudPrestamoController.listaCapacidadCreditoComp}">
				<rich:column>
					<h:outputText value="#{rowKey+1}"/>
				</rich:column>
				<rich:column width="200px">
					<f:facet name="header">
						<h:outputText value="U. Ejecutora"/>
					</f:facet>
					<tumih:outputText value="#{solicitudPrestamoController.listEstructura}" 
						itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
						property="#{item.socioEstructura.intCodigo}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Sucursal"/>
					</f:facet>
					<tumih:outputText value="#{solicitudPrestamoController.listSucursal}" 
						itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
						property="#{item.socioEstructura.intIdSucursalUsuario}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Tipo de planilla"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{item.socioEstructura.intModalidad}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Tipo de Socio"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{item.socioEstructura.intTipoSocio}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Destacado"/>
					</f:facet>
					<h:outputText />
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Proceso"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA}"
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{item.socioEstructura.intTipoEstructura}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Carta 100%"/>
					</f:facet>
					<h:outputText value="#{item.capacidadCredito.intCartaAutorizacion==1?'Si':'No'}"/>
				</rich:column>
				<rich:column width="90px">
					<f:facet name="header">
						<h:outputText value="Capacidad"/>
					</f:facet>
					<div align="right">
						<h:outputText value="#{item.capacidadCredito.bdCapacidadPago}">
										<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</div>
				</rich:column>
				<rich:column width="90px">
					<f:facet name="header">
						<h:outputText value="Cuota Fija"/>
					</f:facet>
					<div align="right">
						<h:outputText value="#{item.capacidadCredito.bdCuotaFija}">
							<f:converter converterId="ConvertidorMontos4"  />
						</h:outputText>
					
					</div>
				</rich:column>
				<rich:column>
	            	<a4j:commandLink id="lnkViewDependencia" styleClass="no-decor" 
	            					reRender="frmCapacidadPago" 
	            					actionListener="#{capacidadPagoController.irModificarCapacidadCredito}"
	                    			oncomplete="Richfaces.showModalPanel('mpCapacidadCredito'),disableFormCapacidadPago()">
	                    <h:graphicImage value="/images/icons/page_detail_20.png" alt="edit"/>
	                    <f:param value="#{item.socioEstructura.intModalidad}" name="pIntModalidad"/>
	                    <f:param value="#{rowKey}" name="rowKeyCapacidadCredito"/>
	                    <rich:toolTip for="lnkViewDependencia" value="Ver" followMouse="true"/>
	                </a4j:commandLink>
	            </rich:column>
			</rich:dataTable>
		</h:panelGrid>
		
		<h:panelGrid id="pgMsgErrorEstructura">
			<h:outputText value="#{solicitudPrestamoController.msgTxtEstructuraRepetida}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtEstrucActivoRepetido}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtEstrucCesanteRepetido}" styleClass="msgError"/>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>

			<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="Saldo de Cuentas"/>	
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:panelGrid>
				
              <rich:dataTable id="pgListSaldoCuentas" value="#{solicitudPrestamoController.beanSocioComp.cuentaComp}" var="itemCuentaComp"
                styleClass="expandWidth">
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
								<rich:column colspan="5">
									<h:outputText value="Préstamo"/>
								</rich:column>
								<rich:column breakBefore="true">
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
								<rich:column width="20px">
									<h:outputText />
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
	                              property="#{itemCuentaComp.cuenta.intParaTipoCuentaCod}"/>
                    </rich:column>
                    <rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
							<h:outputText value="#{itemCuentaComp.cuenta.strNumeroCuenta}"/>
						</rich:column>
						<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
							<h:outputText  value="#{itemCuentaComp.bdTotalAporte}">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>	
						</rich:column>
						<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
							<h:outputText value="#{itemCuentaComp.bdTotalSepelio}">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
						<rich:column rowspan="#{itemCuentaComp.intTamannoListaExp}">
							<h:outputText value="#{itemCuentaComp.bdTotalRetiro}">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
                   
                    <rich:subTable value="#{itemCuentaComp.listExpedienteMovimientoComp}" var="itemExpedientes" rowKeyVar="appPiVerKeyVar">
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
                    </rich:subTable> 
            </rich:dataTable>
		</h:panelGrid>
	</rich:column>
	</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid columns="7" id="pgOperacion">
			<rich:column width="120px">
				<h:outputText value="Fecha de solicitud"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.strFechaRegistro}" size="20" disabled="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Tipo de Operación :"/>
			</rich:column>
			<rich:column>
			
				<h:inputText value="#{solicitudPrestamoController.strDescripcionSubTipoOperacion }" disabled="true" rendered="#{solicitudPrestamoController.blnMostrarDescripciones}"/>
				<h:selectOneMenu value="#{solicitudPrestamoController.beanExpedienteCredito.intParaSubTipoOperacionCod}"
									disabled="#{solicitudPrestamoController.blnCmbTipoOperacion}"
									rendered="#{!solicitudPrestamoController.blnMostrarDescripciones}">
		            <tumih:selectItems var="sel" value="#{solicitudPrestamoController.listaSubOpePrestamos}" 
			            itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
			            propertySort="intOrden" />
	            </h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Monto solicitado :"/>
			</rich:column>
			<rich:column>
				<h:inputText id="idMontoSolicitado" value="#{solicitudPrestamoController.beanExpedienteCredito.bdMontoSolicitado}" size="20" 
						onblur="extractNumber(this,2,false);getMontoSolicitado(this.value);" 
						onkeyup="extractNumber(this,2,false);"
						disabled="#{solicitudPrestamoController.blnTextMontoPrestamo}">
				</h:inputText>
			</rich:column>
			<%--
			<rich:column>
				<h:outputText value="Nro. Cuotas :"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudPrestamoController.intNroCuotas}" size="10" onkeydown="return validarEnteros(event);"/>
			</rich:column>--%>
		</h:panelGrid>
		
		<h:panelGrid id="pgMsgErrorEvaluacion">
			<h:outputText value="#{solicitudPrestamoController.msgTxtTipoOperacion}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtMontoSolicitado}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtListaCapacidadPago}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtNroCuotas}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtCuotaMensual}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtErrores}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtCondicionHabil}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtCondicionLaboral}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtCreditoConvenio}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtConvenioActivo}" styleClass="msgError"/>
			<h:outputText value="#{solicitudPrestamoController.msgTxtEvaluacionFinal}" styleClass="msgError"/>
		</h:panelGrid>
		
		<h:panelGrid id="pgRequisitos" columns="10">
			<rich:column width="120px">
				<h:outputText style="font-weight:bold;text-decoration:underline;" value="REQUISITOS"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Cronograma" oncomplete="#{rich:component('mpCronogramaCredito')}.show()"
					styleClass="btnEstilos" reRender="frmCronogramaCredito">
    	 		</a4j:commandButton>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Imprimir" styleClass="btnEstilos"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid id="pgEvaluacion" rendered="#{solicitudPrestamoController.blnEvaluacionCredito}">
			<h:panelGrid id="pgPrestamo1" columns="11">
				<rich:column width="120px">
					<h:outputText value="Tipo de Préstamo"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.beanCredito.strDescripcion}" size="20" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="% Interés :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.strPorcInteres}" size="10" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="% Gravamen :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.beanCredito.bdTasaSeguroDesgravamen}" size="10" disabled="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
				<rich:column>
					<h:outputText value="% Aporte :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.strPorcAportes}" size="10" disabled="true"/>
				</rich:column>
				
				<rich:column>
					<h:outputText value="Nro. Cuotas :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.intNroCuotas}" size="10"
						disabled="true"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgPrestamo2" columns="11">
				<rich:column width="120px">
					<h:outputText value="Monto del Préstamo"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.bdMontoPrestamo}" size="10" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="Gravamen(-) :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.bdPorcSeguroDesgravamen}" size="10" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="Aporte(-) :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.bdTotalDstos}" size="10" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="Saldo anterior(-) :"/>
					<h:inputText size="10" disabled="true" readonly="true" value="#{solicitudPrestamoController.bdMontoSaldoAnterior}" />
				</rich:column>

				<rich:column>
					<h:outputText value="Monto Solicitado :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.beanExpedienteCredito.bdMontoSolicitado}" size="10" disabled="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="3">
				<rich:column width="120px">
					<h:outputText value="Cuota Mensual"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudPrestamoController.bdTotalCuotaMensual}" disabled="true"/>
				</rich:column>
			</h:panelGrid>

			<h:panelGrid border="0" columns="2" rendered="#{not empty solicitudPrestamoController.listaAutorizaCreditoComp}">
				<rich:column width="120px">
					<h:outputText style="font-weight:bold;text-decoration:underline;" value="OBSERVACIONES"/>
				</rich:column>
				<rich:column>	
					<rich:dataGrid value="#{solicitudPrestamoController.listaAutorizaCreditoComp}"
						rendered="#{not empty solicitudPrestamoController.listaAutorizaCreditoComp}"
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
			
			
			
			
			
			
			
			
			<h:panelGrid columns="3">
				<rich:column width="120px">
					<h:outputText value="Garantes Solidarios"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgListGarantesSolidarios" columns="2" rendered="#{not empty solicitudPrestamoController.listaGarantiaCreditoComp}">
				<rich:column width="130px"/>
				<rich:column>
					<rich:dataTable value="#{solicitudPrestamoController.listaGarantiaCreditoComp}" id="dTblGarantias"
						rowKeyVar="rowKey" sortMode="single" width="800px" var="itemListaGarantia"
						rendered="#{not empty solicitudPrestamoController.listaGarantiaCreditoComp}">
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Nombres Completos"/>
							</f:facet>
							<h:outputText value="#{itemListaGarantia.socioComp.persona.natural.strApellidoPaterno} #{item.socioComp.persona.natural.strApellidoMaterno} #{itemListaGarantia.socioComp.persona.natural.strNombres} - #{itemListaGarantia.socioComp.persona.documento.strNumeroIdentidad}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="U. Ejecutora"/>
							</f:facet>
							<h:outputText value="#{itemListaGarantia.estructura.juridica.strRazonSocial}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Sucursal"/>
							</f:facet>
							<tumih:outputText value="#{solicitudPrestamoController.listSucursal}" 
											itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
											property="#{itemListaGarantia.socioComp.socio.socioEstructura.intIdSucursalAdministra}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Tipo Trabajador"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{itemListaGarantia.socioComp.persona.natural.perLaboral.intCondicionLaboral}"/>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Nro. Garantizados"/>
							</f:facet>
							<div align="center"><h:outputText value="#{itemListaGarantia.intNroGarantizados}"/></div>
						</rich:column>
						
						<rich:column>
	            		</rich:column>
	            
					</rich:dataTable>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgCondiciones" columns="3">
				<rich:column width="120px">
					<h:outputText value="Condiciones"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<rich:panel style="border:1px solid #17356f;width:800px; background-color:#DEEBF5">
						<h:panelGrid columns="2">
							<rich:column width="400px">
								<h:outputText value="Plazo máximo del tipo de préstamo = #{solicitudPrestamoController.intCuotasMaximas} meses"/><br/>
							</rich:column>
							<rich:column style="border-left:1px solid #17356f;" width="400px">
								<h:outputText value="#{solicitudPrestamoController.msgTxtConvenioActivo}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtCreditoConvenio}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtCreditoPk}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtRolPersona}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtEstructuraSocio}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtCondicionLaboral}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtInteresCredito}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtExesoCuotasCronograma}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtTipoCreditoEmpresa}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtCondicionCredito}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtCondicionHabil}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtSubCondicionCuenta}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtMontoExesivo}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtEnvioPlla}" styleClass="msgError"/>
								<h:outputText value="#{solicitudPrestamoController.msgTxtErrores}" styleClass="msgError"/>
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="120px">
					<h:outputText value="SOLICITUD" style="font-weight:bold;text-decoration:underline;"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
			</h:panelGrid>
			<h:panelGrid columns="3">
				<rich:column width="120px">
					<h:outputText value="Motivo de Préstamo"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu value="#{solicitudPrestamoController.beanExpedienteCredito.intParaFinalidadCod}"
										disabled="true">
                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FINALIDAD_CREDITO}" 
                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
                       propertySort="intOrden"/>
              		</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="4">
				<rich:column width="120px">
					<h:outputText value="Observación"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputTextarea value="#{solicitudPrestamoController.beanExpedienteCredito.strObservacion}" 
							cols="150" rows="3" disabled="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="#{solicitudPrestamoController.msgTxtObservacion}" styleClass="msgError"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgListDocAdjuntos" columns="3">
				<rich:column width="120px">
					<h:outputText value="Documentos"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
					<h:panelGrid>
						<rich:dataGrid id="gridDescargas" value="#{solicitudPrestamoController.listaRequisitoCreditoComp}" 
							var="itemRequisitos" columns="2" width="790px" border="0">
			                <rich:panel>
			                    <h:panelGrid columns="2">
			                    	<rich:column width="250px">
			                    		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO}" 
		                                    itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                    property="#{itemRequisitos.detalle.intParaTipoDescripcion}"/> - 
                                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
                                   			itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                    property="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
			                    	</rich:column>
			                        <rich:column>
			                        </rich:column>
			                        
			                        <rich:column id="colImgDocAdjunto" width="142" style="border: solid 1px silver; height:122px; padding:0px" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}">
										<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty itemRequisitos.fileDocAdjunto}"
											createContent="#{solicitudPrestamoController.paintImage}" value="#{itemRequisitos.fileDocAdjunto}"
											style="width:140px; height:120px;" cacheable="false">  
										</a4j:mediaOutput>
									</rich:column>
									
									<rich:column>
										<h:commandLink  value=" Descargar" action="#{solicitudPrestamoController.descargaArchivoUltimo}" target="_blank"
														style="color:black;padding-left: 5px;" rendered="#{not empty itemRequisitos.archivoAdjunto.strNombrearchivo}">
												<f:param name="strRutaActual" value="#{itemRequisitos.archivoAdjunto.rutaActual}"/>
												<f:param name="strNombreArchivo" value="#{itemRequisitos.archivoAdjunto.strNombrearchivo}"/>
												<f:param name="intParaTipoCod" value="#{itemRequisitos.archivoAdjunto.id.intParaTipoCod}"/>
										</h:commandLink>
									</rich:column>
									
									<rich:column>
			                    		<h:outputText value="#{itemRequisitos.archivoAdjunto.strNombrearchivo}"
			                    						rendered="#{not empty itemRequisitos.archivoAdjunto.strNombrearchivo}" />			                    	
			                    	</rich:column>	

			                     </h:panelGrid>
			                </rich:panel>			                
			            </rich:dataGrid>
					</h:panelGrid>
				</rich:column>
			</h:panelGrid>		
		</h:panelGrid>
	</h:panelGroup>
	</h:panelGroup>
</rich:panel>
