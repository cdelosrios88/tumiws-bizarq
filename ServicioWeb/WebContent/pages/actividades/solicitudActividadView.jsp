<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<h:panelGroup id="pgSolicitudActividadView">
	<h:panelGrid columns="3">
		<rich:column width="900px">
			<h:outputText value="Datos Personales" styleClass="estiloLetra1"></h:outputText>
		</rich:column>
		<rich:column>
		</rich:column>
		<rich:column>
		</rich:column>
	</h:panelGrid>

	<rich:spacer height="10"></rich:spacer>
	
	<h:panelGrid columns="4" styleClass="tableCellBorder4">
			<rich:column width="120px">
				<h:outputText value="Nombre de Socio: "></h:outputText>
			</rich:column>
			<rich:column>
				<h:inputText readonly="true" size="80"
					value="#{solicitudActividadController.beanSocioComp.persona.intIdPersona} - #{solicitudActividadController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudActividadController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudActividadController.beanSocioComp.persona.natural.strNombres} - DNI: #{solicitudActividadController.beanSocioComp.persona.documento.strNumeroIdentidad}" />
			</rich:column>

			<rich:column>
				<h:outputText value="Condición: "></h:outputText>
			</rich:column>
			<rich:column>
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudActividadController.beanSocioComp.cuenta.intParaCondicionCuentaCod}" /> - <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}"
																													itemValue="intIdDetalle" itemLabel="strDescripcion"
																										property="#{solicitudActividadController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}" />
			</rich:column>
	</h:panelGrid>

	<h:panelGrid columns="4" styleClass="tableCellBorder4">
			<rich:column width="120px">
				<h:outputText value="Cuenta: " />
			</rich:column>
			<rich:column>
				<h:inputText
					value="#{solicitudActividadController.beanSocioComp.cuenta.strNumeroCuenta}" 
					readonly="true" size="30" />
			</rich:column>
			<rich:column>
				<h:outputText value="Dependencia: " />
			</rich:column>
			<rich:column>
				<rich:dataList value="#{solicitudActividadController.listaDependenciasSocio}" 
					var="item" style="border-top: 1px; border-left: 1px;background-color:#FFFFFF;">
					<h:panelGrid columns="3">
						<h:outputText value="#{item.juridica.strRazonSocial}"></h:outputText>							      
					</h:panelGrid>
				</rich:dataList>
			</rich:column>
	</h:panelGrid>
	
	<h:panelGrid columns="4" styleClass="tableCellBorder4" id="pgTipoActividad">
			<rich:column width="120px">
				<h:outputText value="Tipo de Actividad: " />
			</rich:column>
			<rich:column width="110px">
				<h:inputText value="#{solicitudActividadController.strDescripcionTipoActividad }" readonly="true" rendered="true"/>
			</rich:column>
				
				
				
				<h:inputText value="#{solicitudActividadController.strDescripcionTipoSubActividad }" readonly="true" rendered="true"/>
			<rich:column/>
	</h:panelGrid>
	
	<h:panelGrid id="pgMontoACancelar" columns="4" styleClass="tableCellBorder4" rendered="#{solicitudActividadController.blnTipoDeSolicitud}">
			<rich:column width="120px">
				<h:outputText value="Monto a Cancelar: " />
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudActividadController.strMontoACancelar}"  readonly="true"
							onkeyup="extractNumber(this,2,false);">
				</h:inputText>
			</rich:column>
			<rich:column>
			</rich:column>
			<rich:column/>
	</h:panelGrid>
	
	

	<rich:spacer height="20"></rich:spacer>

	<h:panelGrid id="pgMsgErrorSolActividad">
		<h:outputText value="#{solicitudActividadController.strMsgErrorPreEvaluacion}" styleClass="msgError" />
		<h:outputText value="#{solicitudActividadController.strMsgErrorPreEvaluacionCondicionLaboral}" styleClass="msgError" />
		<h:outputText value="#{solicitudActividadController.strMsgErrorPreEvaluacionCondicion}" styleClass="msgError" />
		<h:outputText value="#{solicitudActividadController.strMsgErrorPreEvaluacionCondicionHabil}" styleClass="msgError" />
		<h:outputText value="#{solicitudActividadController.strMsgErrorPreEvaluacionTipoSocio}" styleClass="msgError" />
	</h:panelGrid>


	<rich:spacer height="10"></rich:spacer>
	<h:panelGrid columns="3">
		<rich:column width="130px">
			<h:outputText value="REQUISITOS :" styleClass="estiloLetra1"></h:outputText>
		</rich:column>
		
		<rich:column width="120px">
			<a4j:commandButton value="Evaluación" styleClass="btnEstilos1"
				style="width: 90%"
				actionListener="#{solicitudActividadController.evaluarSolicitud}"
				reRender="pgSolicitudActividad,pgPostEvaluacionActividad,pgMsgErrorSolActividad,pgListCondicionesActividad ,
							pgListDocAdjuntosActividad, btnCronograma, pgTipoActividad,pgMontoACancelar"
							disabled="true" />
		</rich:column>
			
		<rich:column width="150px">
			<a4j:commandButton style="width: 90%" value="Imprimir" styleClass="btnEstilos1"
				style="width: 90%" />
		</rich:column>
	</h:panelGrid>

	<rich:spacer height="10"></rich:spacer>
	
	<h:panelGroup id="pgPostEvaluacionActividad" rendered="#{solicitudActividadController.blnPostEvaluacion}">

	<h:panelGroup>		
		<h:panelGrid id="pgListCondicionesActividad" columns="3">
		<rich:column width="130px">
			<h:outputText value="Condiciones :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="700px">
			<h:panelGrid>
				<rich:panel>
					<h:panelGrid columns="2">
						<rich:column width="250px">
							<h:outputText
								value="Socio con Capacidad de Pago" />
						</rich:column>
						<rich:column width="680px">
							<h:outputText
								value="#{solicitudActividadController.strMsgCondicionCapacidadPagoSocio}"
								styleClass="msgError2" />
						</rich:column>
					</h:panelGrid>
				</rich:panel>
			</h:panelGrid>
		</rich:column>
		</h:panelGrid>	
	</h:panelGroup>


		
	<h:panelGroup>
		<h:panelGrid id="pgListDocAdjuntosActividad" columns="3">
			<rich:column width="130px">
				<h:outputText value="Documentos :" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5" width="700px">
				<h:panelGrid>
					<rich:dataGrid value="#{solicitudActividadController.listaRequisitoCreditoComp}"
						rendered="#{not empty solicitudActividadController.listaRequisitoCreditoComp}"
						var="itemRequisitos" columns="2" width="700px" border="0">
						<rich:panel>
							<h:panelGrid columns="2">
									<rich:column width="250px">
										<tumih:outputText
											value="#{solicitudActividadController.listaTablaDescripcionAdjuntos}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{itemRequisitos.detalle.intParaTipoDescripcion}" /> - 
	                                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
	                                   			itemValue="intIdDetalle" itemLabel="strDescripcion" 
			                                    property="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
									</rich:column>
								<rich:column>
									<a4j:commandButton value="Adjuntar Documento"
										disabled="true"
										rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}"
										actionListener="#{solicitudActividadController.adjuntarDocumento}"
										oncomplete="Richfaces.showModalPanel('mpFileUpload')"
										styleClass="btnEstilos1"
										reRender="mpFileUpload, pgListDocAdjuntosActividad">
										<f:param name="intParaTipoDescripcion"
											value="#{itemRequisitos.detalle.intParaTipoDescripcion}" />
										<f:param name="intParaTipoOperacionPersona"
											value="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}" />
									</a4j:commandButton>
								</rich:column>

								<rich:column>
									<h:commandLink value=" Descargar" target="_blank"
										action="#{solicitudActividadController.descargaArchivoUltimo}"
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
		</h:panelGrid>
	</h:panelGroup>
	
	
	<rich:spacer height="20"></rich:spacer>
	
	<h:panelGrid>
			<rich:column width="776">
				<h:outputText value="SOLICITUD:" styleClass="estiloLetra1"></h:outputText>
			</rich:column>
			<rich:column>
			</rich:column>
			<rich:column>
			</rich:column>
	</h:panelGrid>

		<!--------------------------  ---------------- -->
		
		<h:panelGroup id="pgDatosSolicitudCompleta">
			<h:panelGrid columns="6">
				<rich:column width="135px">
					<h:outputText value="Fecha de Solicitud: "></h:outputText>
				</rich:column>
				<rich:column width="120px">
					<rich:calendar readonly="true"
							value="#{solicitudActividadController.dtFechaRegistro}"
							datePattern="dd/MM/yyyy" inputStyle="width:70px">
						</rich:calendar>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="Número de Cuotas: "></h:outputText>
				</rich:column>
				<rich:column width="120px">
					<h:inputText value="#{solicitudActividadController.intNroCuotas}" readonly="true"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="6">
				<rich:column width="135px">
					<h:outputText value="Monto Total: "></h:outputText>
				</rich:column>
				<rich:column width="120px">
					<h:inputText value="#{solicitudActividadController.beanExpedienteCredito.bdMontoSolicitado}" readonly="true">
						<f:convertNumber pattern="##0.00" groupingUsed="false" />
					</h:inputText>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="Monto de Cuotas: " ></h:outputText>
				</rich:column>
				<rich:column width="120px">
					<h:inputText value="#{solicitudActividadController.bdMontoDeCuotas}"  readonly="true">
						<f:convertNumber pattern="##0.00" groupingUsed="false" />
					</h:inputText>
				</rich:column>
				
				<rich:column>
					<a4j:commandButton id="btnCronograma" value="Cronograma" oncomplete="#{rich:component('mpCronogramaActividad')}.show()"
										styleClass="btnEstilos" reRender="frmCronogramaActividad" disabled="false" >
    	 			</a4j:commandButton>
				</rich:column>
			
			</h:panelGrid>
			
			<h:panelGrid columns="4">
				<rich:column width="120px">
					<h:outputText value="Motivo de Solicitud: " />
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu value="#{solicitudActividadController.beanExpedienteCredito.intParaFinalidadCod}" disabled="true">
                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MOTIVOS_SOLICITUD_ACTIVIDAD}" 
                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
                       propertySort="intOrden"/>
              		</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			
			
			<h:panelGrid columns="4">
				<rich:column width="120px">
					<h:outputText value="Observación:" />
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>
				<rich:column width="800px">
					<h:inputTextarea rows="5" cols="140" readonly="true"
						value="#{solicitudActividadController.beanExpedienteCredito.strObservacion}" />
				</rich:column>
			</h:panelGrid>

		</h:panelGroup>


	</h:panelGroup>
	
</h:panelGroup>


