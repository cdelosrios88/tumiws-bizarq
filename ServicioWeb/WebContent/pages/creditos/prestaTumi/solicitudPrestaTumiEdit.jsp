<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         			-->
<!-- Modulo    : Servicio    						-->
<!-- Prototipo : Solicitud Presta Tumi - Edicion	-->
<!-- Fecha     : 30/11/2013               			-->

		
<rich:panel rendered="#{solicitudEspecialController.formSolicitudPrestamoRendered}" style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">

<!-- 1. FORMULARIO DE VALIDACION DE DATOS -->

			<h:panelGroup id="pgValidDatos" rendered="#{solicitudEspecialController.blnValidDatos}" layout="block">
				<h:panelGrid columns="7">
					<rich:column>
						<h:outputText value="Relación :"/>
					</rich:column>
					<rich:column>
						<h:selectOneMenu value="#{solicitudEspecialController.intTipoRelacion}">
							<tumih:selectItems var="sel" value="#{solicitudEspecialController.listaRelacion}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:outputText value="Tipo de Crédito :"/>
					</rich:column>
					<rich:column>
						<h:selectOneMenu value="#{solicitudEspecialController.intTipoCredito}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSCREDITOPANTALLA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:outputText value="Tipo de Documento :"/>
					</rich:column>
					<rich:column>
						<h:selectOneMenu value="#{solicitudEspecialController.personaBusqueda.documento.intTipoIdentidadCod}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
												propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column>
						<h:inputText value="#{solicitudEspecialController.personaBusqueda.documento.strNumeroIdentidad}" onkeydown="return validarEnteros(event);" size="20" maxlength="11"/>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid style="width: 100%">
					<a4j:commandButton value="Validar Datos" actionListener="#{solicitudEspecialController.validarDatos}" style="width: 100%" styleClass="btnEstilos1" reRender="pgSolicCreditoEspecial,pgListSaldoCuentas,pgMsgErrorValidarDatos,pgDatosSocio"/>
				</h:panelGrid>
				<rich:panel style="width:949px;border-bottom:1px solid #17356f;border-top:none;border-right:none;border-left:none;background-color:#DEEBF5;"/>
				<h:panelGrid id="pgMsgErrorValidarDatos">
				<h:outputText value="#{solicitudEspecialController.strMsgErrorValidarDatos}" styleClass="msgError"/>
				<h:outputText value="#{solicitudEspecialController.strMsgErrorValidarMovimientos}" styleClass="msgError"/>
				</h:panelGrid>
			</h:panelGroup>
	
	
<!-- 2. FORMULARIO DE REGISTROD E SOLICITUD -->

		<h:panelGroup id="pgDatosSocio" rendered="#{solicitudEspecialController.blnDatosSocio}">
		<rich:spacer height="20"></rich:spacer>
		
		<h:panelGrid columns="5">
			<rich:column width="120px">
				<h:outputText value="Nombre Completo" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.beanSocioComp.persona.intIdPersona} - #{solicitudEspecialController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudEspecialController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudEspecialController.beanSocioComp.persona.natural.strNombres} - #{solicitudEspecialController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="90" readonly="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Condición :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
	                              itemValue="intIdDetalle" itemLabel="strDescripcion"
	                              property="#{solicitudEspecialController.beanSocioComp.cuenta.intParaCondicionCuentaCod}"/> -
	            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
	                              itemValue="intIdDetalle" itemLabel="strDescripcion"
	                              property="#{solicitudEspecialController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="7">
			<rich:column width="120px">
				<h:outputText value="Número de Cuenta" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.beanSocioComp.cuenta.strNumeroCuenta}" size="30" readonly="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Estado de Solicitud :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.strUltimoEstadoSolicitud}"size="30" readonly="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.beanExpedienteCredito.id.intItemExpediente}-#{solicitudEspecialController.beanExpedienteCredito.id.intItemDetExpediente}" 
					readonly="true"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="U. Ejecutora" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Agregar" styleClass="btnEstilos" 
					disabled="#{solicitudEspecialController.blnBtnAddCapacidad}"			
					actionListener="#{socioEstrucController.addEntidadSocio}"
					oncomplete="#{rich:component('mpSocioEstructuraEsp')}.show()"
					reRender="mpSocioEstructura,frmSocioEstructuraEsp"/>
			</rich:column>
		</h:panelGrid>
           
		<h:panelGrid id="pgListCapacidadCreditoEspecial">
			<rich:dataTable value="#{solicitudEspecialController.listaCapacidadCreditoComp}" 
				rowKeyVar="rowKey" sortMode="single" width="950px" var="item">
				<rich:column>
					<h:outputText value="#{rowKey+1}"/>
				</rich:column>
				<rich:column width="200px">
					<f:facet name="header">
						<h:outputText value="U. Ejecutora" styleClass="estiloLetra1"/>
					</f:facet>
					<tumih:outputText value="#{solicitudEspecialController.listEstructura}" 
						itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
						property="#{item.socioEstructura.intCodigo}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Sucursal" styleClass="estiloLetra1"/>
					</f:facet>
					<tumih:outputText value="#{solicitudEspecialController.listSucursal}" 
						itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
						property="#{item.socioEstructura.intIdSucursalUsuario}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Tipo de planilla" styleClass="estiloLetra1"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{item.socioEstructura.intModalidad}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Tipo de Socio" styleClass="estiloLetra1"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
						itemValue="intIdDetalle" itemLabel="strDescripcion" 
						property="#{item.socioEstructura.intTipoSocio}"/>
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Destacado" styleClass="estiloLetra1"/>
					</f:facet>
					<h:outputText />
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
					<div align="right">
						<h:outputText value="#{item.capacidadCredito.bdCapacidadPago}">
										<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</div>
				</rich:column>
				<rich:column width="90px">
					<f:facet name="header">
						<h:outputText value="Cuota Fija" styleClass="estiloLetra1"/>
					</f:facet>
					<div align="right">
						<h:outputText value="#{item.capacidadCredito.bdCuotaFija}">
							<f:converter converterId="ConvertidorMontos4"  />
						</h:outputText>
					
					</div>
				</rich:column>
				<rich:column>
	            	<a4j:commandLink id="lnkViewDependencia" styleClass="no-decor" 
	            					reRender="mpCapacidadCreditoEsp,frmCapacidadPagoEspecial" 
	            					actionListener="#{capacidadPagoController.irModificarCapacidadCreditoEspecial}"
	                    			oncomplete="Richfaces.showModalPanel('mpCapacidadCreditoEsp'),frmCapacidadPagoEspecial()">
	                    <h:graphicImage value="/images/icons/page_detail_20.png" alt="edit"/>
	                    <f:param value="#{item.socioEstructura.intModalidad}" name="pIntModalidad"/>
	                    <f:param value="#{rowKey}" name="rowKeyCapacidadCredito"/>
	                    <rich:toolTip for="lnkViewDependencia" value="Ver" followMouse="true"/>
	                </a4j:commandLink>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandLink id="lnkEditDependencia" styleClass="no-decor" reRender="mpCapacidadCreditoEsp,frmCapacidadPagoEspecial" 
						disabled="#{solicitudEspecialController.blnBtnEditCapacidad}"
	            		actionListener="#{capacidadPagoController.irModificarCapacidadCreditoEspecial}"
	                    oncomplete="Richfaces.showModalPanel('mpCapacidadCreditoEsp')">
	                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
	                    <f:param value="#{item.socioEstructura.intModalidad}" name="pIntModalidad"/>
	                    <f:param value="#{rowKey}" name="rowKeyCapacidadCredito"/>
	                    <rich:toolTip for="lnkEditDependencia" value="Editar" followMouse="true"/>
	                </a4j:commandLink>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandLink id="lnkQuitarSocioEstructura" 
						disabled="#{solicitudEspecialController.blnBtnDelCapacidad}"
               			onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;" 
               			actionListener="#{solicitudEspecialController.removeSocioEstructura}" reRender="pgListCapacidadCreditoEspecial,pgErrors,pgMsgErrorEstructura">
   						<f:param name="rowKeySocioEstructura" value="#{rowKey}" />
   						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
   						<rich:toolTip for="lnkQuitarSocioEstructura" value="Quitar" followMouse="true"/>
   					</a4j:commandLink>
	            </rich:column>
			</rich:dataTable>
		</h:panelGrid>
		
		<h:panelGrid id="pgMsgErrorEstructura">
			<h:outputText value="#{solicitudEspecialController.msgTxtEstructuraRepetida}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtEstrucActivoRepetido}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtEstrucCesanteRepetido}" styleClass="msgError"/>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>

			<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="Saldo de Cuentas" styleClass="estiloLetra1"/>	
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<h:panelGrid>
				
              <rich:dataTable id="pgListSaldoCuentas" value="#{solicitudEspecialController.beanSocioComp.cuentaComp}" var="itemCuentaComp"
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
                    	 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTAREQUISITOS}" style="align: center"
	                              itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                              property="#{itemCuentaComp.cuenta.intParaTipoCuentaCod}"/>
                    </rich:column>
                    <rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText value="#{itemCuentaComp.cuenta.strNumeroCuenta}" style="lign: center"/>
					</rich:column>
					<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText  value="#{itemCuentaComp.bdTotalAporte}" style="align: right">
							<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>	
					</rich:column>
					<rich:column width="80px" rowspan="#{itemCuentaComp.intTamannoListaExp}">
					<h:outputText value="#{itemCuentaComp.bdTotalRetiro}" style="align: right">
							<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</rich:column>
					<rich:column rowspan="#{itemCuentaComp.intTamannoListaExp}">
						<h:outputText value="#{itemCuentaComp.bdTotalSepelio}" style="align: right">
						<f:converter converterId="ConvertidorMontos"  />
						</h:outputText>
					</rich:column>
                   
                    <rich:subTable value="#{itemCuentaComp.listExpedienteMovimientoComp}" var="itemExpedientes" rowKeyVar="appPiVerKeyVar">
                   		<rich:column>
                        	<h:outputText value="#{itemExpedientes.strDescripcionTipoCreditoEmpresa}"/>
                    	</rich:column>
                    	<rich:column>
							<h:outputText value="#{itemExpedientes.expediente.bdMontoTotal}" style="text-align: right" >
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.intNroCuotasPagadas}/#{itemExpedientes.intNroCuotasDefinidas}" style="align: center"/>
						</rich:column>
						<rich:column>
							<h:outputText value="#{itemExpedientes.bdSaldo}" style="align: right">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
						<rich:column width="20px">
						</rich:column>
                    </rich:subTable> 
            </rich:dataTable>
		</h:panelGrid>
	</rich:column>
	</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid columns="7" id="pgOperacion">
			<rich:column width="120px">
				<h:outputText value="Fecha de solicitud" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.strFechaRegistro}" size="20" readonly="true"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Tipo de Producto :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudEspecialController.strDescripcionPrestamoEspecial}" readonly="true" 
				rendered="#{solicitudEspecialController.blnMostrarDescripciones}" />
				
				<h:selectOneMenu value="#{solicitudEspecialController.beanExpedienteCredito.intItemCredito}"
									disabled="false" rendered="#{!solicitudEspecialController.blnMostrarDescripciones}" style="width:150px">
		            <tumih:selectItems var="sel" value="#{solicitudEspecialController.lstCreditosOfrecidos}" 
			            itemValue="#{sel.id.intItemCredito}" itemLabel="#{sel.strDescripcion}"/>
	            </h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:outputText value="Monto solicitado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText id="idMontoSolicitado" value="#{solicitudEspecialController.beanExpedienteCredito.bdMontoSolicitado}" size="20" 
						onblur="extractNumber(this,2,false);getMontoSolicitado(this.value);" 
						onkeyup="extractNumber(this,2,false);"
						readonly="#{solicitudEspecialController.blnTextMontoPrestamo}">
				</h:inputText>
			</rich:column>

		</h:panelGrid>
		
		
		<h:panelGrid id="pgMsgErrorEvaluacion">
			<h:outputText value="#{solicitudEspecialController.msgTxtTipoOperacion}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtMontoSolicitado}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtListaCapacidadPago}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtNroCuotas}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtCuotaMensual}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtErrores}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtCondicionHabil}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtCondicionLaboral}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtCreditoConvenio}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtConvenioActivo}" styleClass="msgError"/>
			<h:outputText value="#{solicitudEspecialController.msgTxtEvaluacionFinal}" styleClass="msgError"/>
		</h:panelGrid>

		
		<h:panelGrid id="pgRequisitos" columns="5">
			<rich:column width="120px">
				<h:outputText style="font-weight:bold;text-decoration:underline;" value="REQUISITOS"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Evaluación" actionListener="#{solicitudEspecialController.evaluarPrestamo}" styleClass="btnEstilos1" 
					reRender="pgSolicCreditoEspecial,pgRequisitos,pgEvaluacion,pgPrestamo1,pgPrestamo2,pgCondiciones,pgMsgErrorEvaluacion,pgListDocAdjuntos,pgOperacion,idMontoSolicitado"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Cronograma" oncomplete="#{rich:component('mpCronogramaCreditoEsp')}.show()"
					styleClass="btnEstilos" reRender="frmCronogramaCreditoEsp">
    	 		</a4j:commandButton>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Imprimir" styleClass="btnEstilos"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10"></rich:spacer>
		
		<h:panelGrid id="pgEvaluacion" rendered="#{solicitudEspecialController.blnEvaluacionCreditoEspecial}">
			<h:panelGrid id="pgPrestamo1" columns="9">
				<rich:column width="120px">
					<h:outputText value="% Interés" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.strPorcInteres}" size="10" readonly="true"/>
				</rich:column>
				<rich:column>
					<h:outputText value="% Gravamen:" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.beanCredito.bdTasaSeguroDesgravamen}" size="6" readonly="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
				<rich:column>
					<h:outputText value="% Aporte:" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.strPorcAportes}" size="6" readonly="true"/>
				</rich:column>
				
				<rich:column>
					<h:outputText value="Nro. Cuotas:" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.intNroCuotas}" size="10"
						disabled="#{solicitudEspecialController.blnTextNroCuotas}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgPrestamo2" columns="9">
				<rich:column width="120px">
					<h:outputText value="Monto del Préstamo" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.bdMontoTotalSolicitado}" size="10" readonly="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
				<rich:column>
					<h:outputText value="Gravamen :" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.bdMontoSeguroDesgravamen}" size="10" readonly="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
				<rich:column>
					<h:outputText value="Aporte :" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.bdTotalDstosAporte}" size="10" readonly="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
				<rich:column>
					<h:outputText value="Monto Solicitado :" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.beanExpedienteCredito.bdMontoSolicitado}" size="10" readonly="true">
						<f:converter converterId="ConvertidorMontos"  />
					</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="3">
				<rich:column width="120px">
					<h:outputText value="Cuota Mensual" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{solicitudEspecialController.bdTotalCuotaMensual}" readonly="true"/>
				</rich:column>
			</h:panelGrid>
			
			
			<h:panelGrid border="0" columns="2" rendered="#{not empty solicitudEspecialController.listaAutorizaCreditoComp}">
				<rich:column width="120px">
					<h:outputText style="font-weight:bold;text-decoration:underline;" value="OBSERVACIONES"/>
				</rich:column>
				<rich:column>	
					<rich:dataGrid value="#{solicitudEspecialController.listaAutorizaCreditoComp}"
						rendered="#{not empty solicitudEspecialController.listaAutorizaCreditoComp}"
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
					<h:outputText value="Motivo de Préstamo" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu value="#{solicitudEspecialController.beanExpedienteCredito.intParaFinalidadCod}"
										disabled="#{solicitudEspecialController.blnTxtMotivoPrestamo}">
                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FINALIDAD_CREDITO}" 
                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
                       propertySort="intOrden"/>
              		</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="4">
				<rich:column width="120px">
					<h:outputText value="Observación" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column>
					<h:inputTextarea value="#{solicitudEspecialController.beanExpedienteCredito.strObservacion}" 
							cols="150" rows="3" disabled="#{solicitudEspecialController.blnTxtObservacionesPrestamo}" />
				</rich:column>
				<rich:column>
					<h:outputText value="#{solicitudEspecialController.msgTxtObservacion}" styleClass="msgError"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgListDocAdjuntos" columns="3">
				<rich:column width="120px">
					<h:outputText value="Documentos" styleClass="estiloLetra1"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
					<h:panelGrid>
						<rich:dataGrid value="#{solicitudEspecialController.listaRequisitoCreditoComp}" 
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
			                        	<a4j:commandButton value="Adjuntar Documento" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}"
			                        		actionListener="#{solicitudEspecialController.adjuntarDocumento}" reRender="mpFileUploadEspecial"
			                        		oncomplete="Richfaces.showModalPanel('mpFileUploadEspecial')" styleClass="btnEstilos1"
			                        		disabled="#{solicitudEspecialController.blnBtnAddRequisito}"
			                        		reRender="pgSolicCreditoEspecial,mpFileUploadEspecial, pgListDocAdjuntos,pgEvaluacion,colImgDocAdjuntoEspecial">
			                        		<f:param name="intParaTipoDescripcion" value="#{itemRequisitos.detalle.intParaTipoDescripcion}"/>
			                        		<f:param name="intParaTipoOperacionPersona" value="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
			                        	</a4j:commandButton>
			                        </rich:column>
			                        
			                        <rich:column id="colImgDocAdjuntoEspecial" width="142" style="border: solid 1px silver; height:122px; padding:0px" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}">
										<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty itemRequisitos.fileDocAdjunto}"
											createContent="#{solicitudEspecialController.paintImage}" value="#{itemRequisitos.fileDocAdjunto}"
											style="width:140px; height:120px;" cacheable="false">  
										</a4j:mediaOutput>
									</rich:column>
									
									<rich:column>
										<h:commandLink  value=" Descargar" action="#{solicitudEspecialController.descargaArchivoUltimo}" target="_blank"
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
	
</rich:panel>