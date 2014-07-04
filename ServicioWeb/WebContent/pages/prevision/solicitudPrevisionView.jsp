<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         -->
<!-- Prototipo : Solicitud Prevision      -->
<!-- Fecha     : 06/12/2012               -->

<h:panelGroup id="pgSolicitudPrevisionView">
	<h:panelGrid columns="3">
		<rich:column width="776">
			<h:outputText value="Datos Personales" styleClass="estiloLetra1"></h:outputText>
		</rich:column>
		<rich:column>
		</rich:column>
		<rich:column>
		</rich:column>
	</h:panelGrid>

	<rich:spacer height="10"></rich:spacer>
	<h:panelGrid columns="4" styleClass="tableCellBorder4">
		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Nombre de Socio: "></h:outputText>
			</rich:column>
			<rich:column>
				<h:inputText readonly="true" size="100"
					value="#{solicitudPrevisionController.beanSocioComp.persona.intIdPersona} - #{solicitudPrevisionController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudPrevisionController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudPrevisionController.beanSocioComp.persona.natural.strNombres}- #{solicitudPrevisionController.beanSocioComp.persona.documento.strNumeroIdentidad}" />
			</rich:column>

			<rich:column>
				<h:outputText value="Condición: "></h:outputText>
			</rich:column>
			<rich:column>
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudPrevisionController.beanSocioComp.cuenta.intParaCondicionCuentaCod}" /> -
		            <tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudPrevisionController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}" />
			</rich:column>


		</rich:columnGroup>

		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Unidad Ejecutora: " />
			</rich:column>
			<rich:column>
				<h:inputText
					value="#{solicitudPrevisionController.strUnidadesEjecutorasConcatenadas}" 
					readonly="true" size="100" />
			</rich:column>

			<rich:column>
				<h:outputText value="Estado: " />
			</rich:column>
			<rich:column> 
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudPrevisionController.beanExpedientePrevision.estadoPrevisionUltimo.intParaEstado}" />
			</rich:column>
		</rich:columnGroup>

		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Sucursal del Socio: " />
			</rich:column>
			<rich:column>
				<tumih:inputText
					value="#{solicitudPrevisionController.listaTablaSucursal}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{solicitudPrevisionController.beanSocioComp.socio.socioEstructura.intIdSucursalAdministra}"
					readonly="true" size="100" />
			</rich:column>
			<rich:column>

				<h:outputText value="Sub Sucursal: " />
			</rich:column>
			<rich:column>
				<h:inputText
					value="#{solicitudPrevisionController.strSubsucursalSocio}"
					readonly="true" />
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>

	
	<h:panelGrid id="pgTipoSol" columns="3" styleClass="tableCellBorder4">
		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Tipo de Solicitud : " />
			</rich:column>
			<rich:column width="150px" id="clnOperacion">
				<h:inputText readonly="true"  value="#{solicitudPrevisionController.strDescripcionPrevision}"
						rendered="#{solicitudPrevisionController.blnMostrarDescripcionTipoPrevision}"/>
			</rich:column>
			<rich:column width="150px" id="clnSubOperacion">
				<h:inputText readonly="true"  value="#{solicitudPrevisionController.strDescripcionTipoPrevision}"
							rendered="#{solicitudPrevisionController.blnMostrarDescripcionTipoPrevision}"
							size="35"/>
			</rich:column>

		</rich:columnGroup>
	</h:panelGrid>

	<rich:spacer height="10"></rich:spacer>


	<!------ SOLO APLICA PARA SEPELEIO ------>
	<h:panelGroup id="pgSoloAplicaSepelio"  rendered="#{solicitudPrevisionController.blnIsSepelio}">
	
	<h:panelGrid columns="5" styleClass="tableCellBorder4">
		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Solo aplica para Fondo de Sepelio:  " styleClass="estiloLetra1" />
			</rich:column>

			<rich:column width="120px">
				<h:outputText value="Fecha de Fallecimiento:" />
			</rich:column>
			
			<rich:column  width="120px" id="clnFechaFallecimiento">
				<rich:calendar datePattern="dd/MM/yyyy" inputStyle="width:76px" readonly="true" disabled="true"
					value="#{solicitudPrevisionController.beanExpedientePrevision.dtFechaFallecimiento}">
				</rich:calendar>
			</rich:column>

			<rich:column id= "clnBenefRel" rendered="#{solicitudPrevisionController.blnIsSepelioTitular}">
				<h:selectBooleanCheckbox value="#{solicitudPrevisionController.blnBenefTieneRelFam}" disabled="true">
				</h:selectBooleanCheckbox>
			</rich:column>
			
			<rich:column width="220px" rendered="#{solicitudPrevisionController.blnIsSepelioTitular}">
				<h:outputText value="Beneficiario NO tiene relación familiar" />
			</rich:column>
			
		</rich:columnGroup>
	</h:panelGrid>

	<rich:spacer height="20"></rich:spacer>
	
		<!-- FALLECIDOS DE SEPELIO -->
	<!-- rendered="#{!solicitudPrevisionController.blnBeneficiarioSepelio}" -->
	<h:panelGrid columns="3" id="pgFallecidosSepelio" rendered="#{solicitudPrevisionController.blnBeneficiarioSepelio}">
		<rich:column width="120px">
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px">
		</rich:column>
	</h:panelGrid>
	
	
	<h:panelGrid id="pgLitstaFallecidosSepelio" columns="3" rendered="#{solicitudPrevisionController.blnBeneficiarioSepelio}">
		<rich:column width="120px">
			<h:outputText value="Fallecido(s) :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px">
			<h:panelGrid id="tblFallecidoSepelio" columns="2">
				<rich:column>
					<rich:dataTable
						value="#{solicitudPrevisionController.beanExpedientePrevision.listaFallecidoPrevision}"
						rowKeyVar="rowKey" sortMode="single" width="800px"
						var="itemFallecidoSep">
					
					<f:facet name="header">
							<rich:columnGroup>
								<rich:column width="20px" />
								<rich:column width="160px" rowspan="2">
									<h:outputText value="Nombre Completo"/>
								</rich:column>
								<rich:column width="50px" rowspan="2">
									<h:outputText value="Tipo Doc."/>
								</rich:column>
								<rich:column width="100px" rowspan="2">
									<h:outputText value="Número de Doc."/>
								</rich:column>
								<rich:column width="100px" rowspan="2">
									<h:outputText value="Relación"/>
								</rich:column>
							</rich:columnGroup>
						</f:facet>
					
					<rich:column width="20px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>
						<rich:column width="250px">
							<h:outputText
								value="#{itemFallecidoSep.persona.natural.strApellidoPaterno} #{itemFallecidoSep.persona.natural.strApellidoMaterno},#{itemFallecidoSep.persona.natural.strNombres}" />
						</rich:column>
						<rich:column width="160px">
							<tumih:outputText
								cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemFallecidoSep.persona.documento.intTipoIdentidadCod}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText style="text-align: center"
								value="#{itemFallecidoSep.persona.documento.strNumeroIdentidad}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<tumih:outputText style="text-align: center"
								cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemFallecidoSep.intTipoViculo}" />
						</rich:column>
					</rich:dataTable>
				</rich:column>
			</h:panelGrid>

		</rich:column>
	</h:panelGrid>
	
	<rich:spacer height="30"></rich:spacer>
	
	<!-- BENEFICIARIOS DE SEPELIO -->
	<!-- rendered="#{!solicitudPrevisionController.blnBeneficiarioSepelio}" -->
	<h:panelGrid columns="3" id="pgBeneficiarioSepelio" rendered="#{solicitudPrevisionController.blnBeneficiarioSepelio}">
		<rich:column width="120px">
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px"
			rendered="#{solicitudPrevisionController.blnBeneficiarioSepelio}">
		</rich:column>
	</h:panelGrid>
	
	<h:panelGrid id="pgLitstaBeneficiariosSepelio" columns="3" rendered="#{solicitudPrevisionController.blnBeneficiarioSepelio}">
		<rich:column width="120px">
			<h:outputText value="Beneficiario(s) :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px">
			<h:panelGrid id="tblBenenficiarioSepelio" columns="2">
				<rich:column>
					<rich:dataTable
						value="#{solicitudPrevisionController.beanExpedientePrevision.listaBeneficiarioPrevision}"
						rowKeyVar="rowKey" sortMode="single" width="800px"
						var="itemBeneficiariosSep">
					
					<f:facet name="header">
							<rich:columnGroup>
								<rich:column rowspan="2"/>
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
								<rich:column width="80px" rowspan="2">
									<h:outputText value="% Beneficio"/>
								</rich:column>
								<rich:column colspan="5">
									<h:outputText value="Monto"/>
								</rich:column>
								<rich:column breakBefore="true">
									<h:outputText value="Total"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Gastos Administrativos"/>
								</rich:column>
								<rich:column>
									<h:outputText value="Monto Neto"/>
								</rich:column>
							</rich:columnGroup>
						</f:facet>
					
					<rich:column width="15px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>
						<rich:column width="250px">
							<h:outputText
								value="#{itemBeneficiariosSep.persona.natural.strApellidoPaterno} #{itemBeneficiariosSep.persona.natural.strApellidoMaterno},#{itemBeneficiariosSep.persona.natural.strNombres}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<tumih:outputText style="text-align: center"
								cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemBeneficiariosSep.persona.documento.intTipoIdentidadCod}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText style="text-align: center"
								value="#{itemBeneficiariosSep.persona.documento.strNumeroIdentidad}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<tumih:outputText style="text-align: center"
								cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemBeneficiariosSep.intTipoViculo}" />
						</rich:column>
						<rich:column width="100px" style="text-align: right">
							<h:inputText value="#{itemBeneficiariosSep.bdPorcentajeBeneficio}" style="text-align: right">
							</h:inputText>
						</rich:column style="text-align: right">			
						<rich:column width="100px">
							<h:outputText value="#{itemBeneficiariosSep.bdMontoTotal}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column >
						<rich:column width="100px">
							<h:outputText value="#{itemBeneficiariosSep.bdGastosAdministrativos}" style="text-align: right">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						<rich:column width="100px" >
							<h:outputText value="#{itemBeneficiariosSep.bdMontoNeto}" style="text-align: right">
								<f:converter converterId="ConvertidorMontos"  />
							</h:outputText>
						</rich:column>
					</rich:dataTable>
				</rich:column>
			</h:panelGrid>

		</rich:column>

		</h:panelGrid>
		
		<h:panelGrid columns="3">
			<rich:column  width="600px">
			</rich:column>
			<rich:column  width="250px">
			</rich:column>
			<rich:column>
			</rich:column>
		</h:panelGrid>
	</h:panelGroup>
	
	<rich:spacer height="10"></rich:spacer>


	<h:panelGrid id="pgMsgErrorSolPrev">
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtTipoSolicitud}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtSubOperacion}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtFechaSepelio}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtTieneBeneficiarios}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtTieneFallecidos}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtSumaPorcentaje}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtMonto}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtObservacion}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtEvaluacion}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtGrabar}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtCaptacionCondicion}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtCaptacionConcepto}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtCaptacionSepelio}" styleClass="msgError" />
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtAESFechaSustentacion}" styleClass="msgError"/>
		<h:outputText value="#{solicitudPrevisionController.strMsgTxtSepelioTitularNoDebeDeudaExistente}" styleClass="msgError"/>
			
			

	</h:panelGrid>

	<rich:spacer height="10"></rich:spacer>

	<h:panelGrid columns="3">
		<rich:column width="120px">
			<h:outputText value="REQUISITOS :" styleClass="estiloLetra1"></h:outputText>
		</rich:column>



		<rich:column width="150px">
			<a4j:commandButton value="Evaluación" styleClass="btnEstilos1" disabled="true"
				style="width: 90%"/>
		</rich:column>
		<rich:column width="150px">
			<h:commandButton value="Imprimir" styleClass="btnEstilos1" style="width: 90%" disabled="true"/>
		</rich:column>
	</h:panelGrid>

	
	<h:panelGroup id="pgPostEvaluacion" rendered="#{solicitudPrevisionController.blnPostEvaluacion}">
		<h:panelGroup id="pgListCondiciones">
		
			<h:panelGrid columns="3" id="mpCondicionCuotasMinimas" rendered="#{!solicitudPrevisionController.blnIsRetiro}" >
				<rich:column width="120px">
					<h:outputText value="Condiciones :" />
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>

				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
					<h:panelGrid>
						<rich:panel>
							<h:panelGrid columns="3">
								<rich:column width="20px">
									<h:selectBooleanCheckbox
										value="#{solicitudPrevisionController.chkCondicionCuotaSepelio}"
										disabled="true" />
								</rich:column>
								<rich:column width="250px">
									<h:outputText value="Cuotas mínimas de sepelio =  #{solicitudPrevisionController.intNroCuotasDefinidasSepelio}" />
								</rich:column>
								<rich:column width="420px">
									<h:outputText
										value="#{solicitudPrevisionController.strMsgTxtAESCuotasSepelio}"
										styleClass="msgError2" />
								</rich:column>
							</h:panelGrid>
						</rich:panel>
					</h:panelGrid>
				</rich:column>
			</h:panelGrid>

			<h:panelGrid columns="3" id="mpCondicionCuotasMinimasRetiro" rendered="#{solicitudPrevisionController.blnIsRetiro}"> 
				<rich:column width="120px">
					<h:outputText value="Condiciones :" />
				</rich:column>
				<rich:column>
					<h:outputText value="" />
				</rich:column>

				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
					<h:panelGrid>
						<rich:panel>
							<h:panelGrid columns="3">
								<rich:column width="20px">
									<h:selectBooleanCheckbox
										value="#{solicitudPrevisionController.chkCondicionCuotaSepelio}"
										disabled="true" />
								</rich:column>
								<rich:column width="250px">
									<h:outputText value="Cuotas mínimas de retiro =  #{solicitudPrevisionController.intNroCuotasDefinidasRetiro}" />
								</rich:column>
								<rich:column width="420px">
									<h:outputText
										value="#{solicitudPrevisionController.strMsgTxtAESCuotasSepelio}"
										styleClass="msgError2" />
								</rich:column>
							</h:panelGrid>
						</rich:panel>
					</h:panelGrid>
				</rich:column>
			</h:panelGrid>
	<h:panelGrid id="mpCondicionPeriocidadAES" rendered="#{solicitudPrevisionController.blnIsAES}" columns="3">
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>

					<rich:panel>
						<h:panelGrid columns="3">
							<rich:column width="20px">
								<h:selectBooleanCheckbox
									value="#{solicitudPrevisionController.chkCondicionPeridoMeses}"
									disabled="true" />
							</rich:column>
							<rich:column width="250px">
								<h:outputText
									value="Solicitud AES: #{solicitudPrevisionController.intSolicitudRegularidad} cada #{solicitudPrevisionController.intSolicitudPeriodicidad} meses" />
							</rich:column>
							<rich:column width="420px">
								<h:outputText
									value="#{solicitudPrevisionController.strMsgTxtAESPeriocidad}"
									styleClass="msgError2" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</h:panelGrid>
			</rich:column>
	</h:panelGrid>	
			
	<h:panelGrid id="mpCondicionTenerDeuda" rendered="#{solicitudPrevisionController.blnIsAES}" columns="3">
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>
					<rich:panel>

						<h:panelGrid columns="3">
							<rich:column width="20px">
								<h:selectBooleanCheckbox
									value="#{solicitudPrevisionController.chkCondicionDebeTenerDeuda}"
									disabled="true" />
							</rich:column>
							<rich:column width="250px">
								<h:outputText value="Debe tener alguna deuda existente" />
							</rich:column>
							<rich:column width="420px">
								<h:outputText
									value="#{solicitudPrevisionController.strMsgTxtAESDeudaExistente}"
									styleClass="msgError2" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</h:panelGrid>
			</rich:column>
	</h:panelGrid>

	<h:panelGrid id="mpCondicionPresentacionMaximo" rendered="#{solicitudPrevisionController.blnIsSepelio}" columns="3">	
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>
					<rich:panel>
						<h:panelGrid columns="3">
							<rich:column width="20px">
								<h:selectBooleanCheckbox value="#{solicitudPrevisionController.chkCondicionPresentacionSol}"
									disabled="true"/>
							</rich:column>
							<rich:column width="250px">
								<h:outputText value="Presentación de solicitud #{solicitudPrevisionController.intTiempoSustentoTabla} #{solicitudPrevisionController.strFrecuenciaPresentacionSolicitud}(s)  #{solicitudPrevisionController.strMinimoMaximo}" />
							</rich:column>
							<rich:column width="420px">
								<h:outputText value="#{solicitudPrevisionController.strMsgTxtSepelioFechaPresentacion}" 
								styleClass="msgError2" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</h:panelGrid>
			</rich:column>
	</h:panelGrid>	
	
		
		
	<h:panelGrid id="mpCondicionTiempoAportacionConsiderado" rendered="#{solicitudPrevisionController.blnIsRetiro}" columns="3">
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>
					<rich:panel>

						<h:panelGrid columns="3">
							<rich:column width="20px">
								<h:selectBooleanCheckbox
									value="true"
									disabled="true" />
							</rich:column>
							<rich:column width="250px">
								<h:outputText value="Tiempo de Aportación considerados = #{solicitudPrevisionController.bdAnnosAportacionCalculado} años." />
							</rich:column>
							<rich:column width="420px">
								<h:outputText
									value="Se considerarán años completos para considerar en base a escala."
									styleClass="msgError2" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</h:panelGrid>
			</rich:column>
		</h:panelGrid>
		
		
	
	
		<h:panelGrid id="mpCondicionValorCuotaMensual" rendered="#{solicitudPrevisionController.blnIsRetiro}" columns="3">
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>
					<rich:panel>

						<h:panelGrid columns="3">
							<rich:column width="20px">
								<h:selectBooleanCheckbox
									value="true"
									disabled="true" />
							</rich:column>
							<rich:column width="250px">
								<h:outputText value="Valor de cuota Mensual"/>
							</rich:column>
							<rich:column width="420px">
								<h:outputText 
									value="#{solicitudPrevisionController.strMsgTxtRetiroValorCuotaMensual}"
									styleClass="msgError2" />
							</rich:column>
						</h:panelGrid>
					</rich:panel>
				</h:panelGrid>
			</rich:column>
		</h:panelGrid>
		</h:panelGroup>

<rich:spacer height="10"></rich:spacer>
		
					<h:panelGrid border="0" columns="2" rendered="#{not empty solicitudPrevisionController.listaAutorizaPrevisionComp}">
				<rich:column width="120px">
					<h:outputText style="font-weight:bold;text-decoration:underline;" value="OBSERVACIONES"/>
				</rich:column>
				<rich:column>	
					<rich:dataGrid value="#{solicitudPrevisionController.listaAutorizaPrevisionComp}"
						rendered="#{not empty solicitudPrevisionController.listaAutorizaPrevisionComp}"
						var="item" columns="1" width="700px" border="1"
						style="background-color:#DEEBF5">
						<rich:panel style="border:1px">
							<h:panelGrid columns="2">
								<rich:column width="250px">
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
											property="#{item.autorizaPrevision.intParaEstadoAutorizar}" />-
							            <tumih:outputText
											cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{item.autorizaPrevision.intParaTipoAureobCod}" />.
									</h:panelGroup>
								</rich:column>
								<rich:column width="200px">
									<h:outputText value="Observación : "
									style="font-weight:bold" styleClass="label" />
								</rich:column>
								<rich:column>
									<h:outputText
									value="#{item.autorizaPrevision.strObservacion}" />.
								</rich:column>
								<rich:column width="200px">
									<h:outputText value="Fecha : "
									style="font-weight:bold" styleClass="label" />
								</rich:column>
								<rich:column>
									<h:outputText
									value="#{item.autorizaPrevision.tsFechaAutorizacion}" />
								</rich:column>
							</h:panelGrid>
						</rich:panel>
					</rich:dataGrid>
				</rich:column>
				
			</h:panelGrid>
		<rich:spacer height="10"></rich:spacer>	
			
	<h:panelGroup>
		<h:panelGrid id="pgListDocAdjuntosPrev" columns="3">
			<rich:column width="120px">
				<h:outputText value="Documentos :" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column
				style="border:1px solid #17356f; background-color:#DEEBF5">
				<h:panelGrid>
						<rich:dataGrid value="#{solicitudPrevisionController.listaRequisitoPrevisionComp}" 
							var="itemRequisitos" columns="2" width="752px" border="0">
			                <rich:panel>
			                    <h:panelGrid columns="2">
			                    	<rich:column width="180px">
										<tumih:outputText
											value="#{solicitudPrevisionController.listaTablaDescripcionAdjuntos}"
											itemValue="intIdDetalle" itemLabel="strDescripcion"
											property="#{itemRequisitos.detalle.intParaTipoDescripcion}" /> -
										<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
                                   			itemValue="intIdDetalle" itemLabel="strDescripcion" 
		                                    property="#{itemRequisitos.detalle.intParaTipoPersonaOperacionCod}"/>
									</rich:column>
								
			                        <rich:column>
									</rich:column>
			                        
			                        <rich:column id="colImgDocAdjunto" style="border: solid 1px silver; height:122px; padding:0px" rendered="#{itemRequisitos.detalle.intOpcionAdjunta==1}">
										<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty itemRequisitos.fileDocAdjunto}"
											createContent="#{solicitudPrestamoController.paintImage}" value="#{itemRequisitos.fileDocAdjunto}"
											style="width:140px; height:120px;" cacheable="false">  
										</a4j:mediaOutput>
									</rich:column>
									
									<rich:column>
										<h:commandLink value=" Descargar"
											action="#{solicitudPrevisionController.descargaArchivoUltimo}"
											style="color:black;padding-left: 5px;"
											rendered="#{not empty itemRequisitos.archivoAdjunto.strNombrearchivo}">
											<f:param name="strNombreArchivo"
												value="#{itemRequisitos.archivoAdjunto.strNombrearchivo}" />
											<f:param name="intParaTipoCod"
												value="#{itemRequisitos.archivoAdjunto.id.intParaTipoCod}" />
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
	</h:panelGroup>

	<h:panelGrid>
			<rich:column width="776">
				<h:outputText value="SOLICITUD:" styleClass="estiloLetra1"></h:outputText>
			</rich:column>
			<rich:column>
			</rich:column>
			<rich:column>
			</rich:column>
	</h:panelGrid>

		<!-------------------------- INICIA DATOS DE SOLICITUD - AES+SEPELIO+RETIRO ---------------- -->
		
		<h:panelGroup id="pgDatosSolicitudCompleta">
			
			
				<h:panelGrid>
						<rich:column width="776">
							<h:outputText value="#{solicitudPrevisionController.strMsgTxtAESFechaSustentacion}" styleClass="msgError" />
						</rich:column>
				</h:panelGrid>
	
			<!-- Fecha de solicitud + Fecha Sustento Medico + x   -->
			<!--<rich:calendar readonly="true"
						value="#{solicitudPrevisionController.dtFechaRegistro}"
						datePattern="dd/MM/yyyy" inputStyle="width:80px"></rich:calendar> -->
			<h:panelGrid columns="6">
				<rich:column width="150px">
					<h:outputText value="Fecha de Solicitud:"></h:outputText>
				</rich:column>
				<rich:column width="120px">
						<h:inputText value="#{solicitudPrevisionController.strFechaRegistro}" readonly="true"/>
				</rich:column>

				<rich:column width="170px" >
					<h:outputText value="Fecha de Sustento Médico:" rendered="#{solicitudPrevisionController.blnIsAES}"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<rich:calendar rendered="#{solicitudPrevisionController.blnIsAES}" reRender="true" readonly="true"
						value="#{solicitudPrevisionController.beanExpedientePrevision.dtFechaSustentoMedico}"
						datePattern="dd/MM/yyyy" inputStyle="width:80px">
					</rich:calendar>

					
				</rich:column>
				<rich:column width="150px">
					<h:outputText></h:outputText>
				</rich:column>
				<rich:column width="150px">
				</rich:column>
			</h:panelGrid>
			
			<!--------------- Cuota Mensual + x + x  ---------------------->
			<h:panelGrid columns="6" rendered="#{solicitudPrevisionController.blnIsRetiro}">
				<rich:column width="150px">
					<h:outputText value="Cuota Mensual:"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:inputText value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoCuotasFondo}" readonly="true">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
				<rich:column width="150px">
					<h:outputText></h:outputText>
				</rich:column>
				<rich:column width="150px">
				</rich:column>
				<rich:column width="150px">
					<h:outputText></h:outputText>
				</rich:column>
				<rich:column width="150px">
				</rich:column>
			</h:panelGrid>
			
			<!--------------- Numero de Cuotas + Años de Aportacion + x ---------------------->
			<h:panelGrid columns="6" rendered="#{solicitudPrevisionController.blnIsRetiro}">
				<rich:column width="150px">
					<h:outputText value="Número de Cuotas:"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:inputText value="#{solicitudPrevisionController.bdValorNumeroCuotas }" readonly="true">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="Años de Aportación calculado:"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:inputText value="#{solicitudPrevisionController.bdAnnosAportacionCalculado}" readonly="true">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
				<rich:column width="150px">
					<h:outputText></h:outputText>
				</rich:column>
				<rich:column width="150px">
				</rich:column>
			</h:panelGrid>
			
			<!--------------- Monte aporte Total + Interes Ganado + Monto Neto Retiro ---------------------->
			<h:panelGrid columns="6" rendered="#{solicitudPrevisionController.blnIsRetiro}">
				<rich:column width="150px">
					<h:outputText value="Monto aporte total:"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:inputText value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoBrutoBeneficio}" readonly="true"> 
							<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
				<rich:column width="150px">
					<h:outputText value="Interés ganado (En base a escala):"></h:outputText>
				</rich:column>
				<rich:column width="150px">
					<h:inputText value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoInteresBeneficio}" readonly="true">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
				<rich:column id="lbNeto" width="150px">
					<h:outputText value="Monto Neto:" />
				</rich:column>
				<rich:column width="150px">
						<h:inputText id="idMontNeto" readonly="true"
									value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoNetoBeneficio}">	
							<f:converter converterId="ConvertidorMontos"/>
						</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<!--------------- Monto Total + Gastos aaadministrativos + Monto Neto Aes ---------------------->
			<h:panelGrid columns="6">
				<rich:column width="150px">
					<h:outputText value="Monto Total:" rendered="#{!solicitudPrevisionController.blnIsRetiro}" />
				</rich:column>
				<rich:column width="150px">
					<h:inputText id="idMontoSolicitado"
						readonly="true"
						rendered="#{!solicitudPrevisionController.blnIsRetiro}"
						value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoBrutoBeneficio}"
						onblur="extractNumber(this,2,false);"
						onkeyup="extractNumber(this,2,false);"/>
				</rich:column>
				
				<rich:column width="150px">
					<h:outputText value="Gastos Administrativos (#{solicitudPrevisionController.bdPorcGastosAdministrativo}%):" 
								rendered="#{solicitudPrevisionController.blnIsSepelio}"/>
				</rich:column>
				<rich:column id="lbMontoGastos" width="150px">
					<h:inputText id="idMontoGastosAdminitrativos" readonly="true"
						rendered="#{solicitudPrevisionController.blnIsSepelio}"
						value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoGastosADM}"
						onblur="extractNumber(this,2,false);"
						onkeyup="extractNumber(this,2,false);" />
				</rich:column>

				<rich:column width="150px">
					<h:outputText value="Monto Neto:" rendered="#{solicitudPrevisionController.blnIsSepelio}"/>
				</rich:column>
				<rich:column width="150px">
				<h:inputText id="idMontNetoRetiro" readonly="true"
							rendered="#{solicitudPrevisionController.blnIsSepelio}"
							value="#{solicitudPrevisionController.beanExpedientePrevision.bdMontoNetoBeneficio}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
			</h:panelGrid>
		</h:panelGroup>

<h:panelGroup rendered="#{solicitudPrevisionController.blnIsAES}">
		<!-- rendered="#{solicitudPrevisionController.blnBeneficiarioNormal}" -->
		<h:panelGrid columns="3" id="pgBeneficiario" >
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column width="800px">
			</rich:column>
		</h:panelGrid>

	<!---------------------- Lista de Beneficiarios. Opera como fallecidos en el caso de AES -------------------------------------->
		<h:panelGrid id="pgLitstaBeneficiarios" columns="3"  rendered="#{solicitudPrevisionController.blnBeneficiarioNormal}">
			<rich:column width="120px">
				<h:outputText value="Beneficiario(a)" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" />
			</rich:column>
			<rich:column width="700px">

				<h:panelGrid id="tblBenenficiario" columns="2">
					<rich:column>
						<rich:dataTable
							value="#{solicitudPrevisionController.beanExpedientePrevision.listaBeneficiarioPrevision}"
							rowKeyVar="rowKey" sortMode="single" width="700px"
							var="itemBeneficiarios"
							rendered="#{not empty solicitudPrevisionController.beanExpedientePrevision.listaBeneficiarioPrevision}">
							<rich:column width="15px">
								<div align="center">
									<h:outputText value="#{rowKey+1}" />
								</div>
							</rich:column>
							
							<rich:column width="250px">
								<f:facet name="header">
									<h:outputText value="Nombre Completo" />
								</f:facet>
								<h:outputText
									value="#{itemBeneficiarios.persona.natural.strApellidoPaterno} #{itemBeneficiarios.persona.natural.strApellidoMaterno},#{itemBeneficiarios.persona.natural.strNombres}" />
							</rich:column>
							<rich:column width="100px">
								<f:facet name="header">
									<h:outputText value="Tipo Documento" />
								</f:facet>
								<tumih:outputText style="text-align: right"
									cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemBeneficiarios.persona.documento.intTipoIdentidadCod}" />
							</rich:column>
							<rich:column width="100px">

								<f:facet name="header">

									<h:outputText value="Nro. Documento" />
								</f:facet>
								<h:outputText style="text-align: right"
									value="#{itemBeneficiarios.persona.documento.strNumeroIdentidad}" />
							</rich:column>
							<rich:column width="100px">
								<f:facet name="header">
									<h:outputText value="Relación" />
								</f:facet>
								<tumih:outputText style="text-align: right"
									cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}"
									itemValue="intIdDetalle" itemLabel="strDescripcion"
									property="#{itemBeneficiarios.intTipoViculo}" />	
							</rich:column>

						</rich:dataTable>
					</rich:column>
				</h:panelGrid>

			</rich:column>
		</h:panelGrid>
</h:panelGroup>		
		<!-- BENEFICIARIOS DE RETIRO -->
	<h:panelGroup rendered="#{solicitudPrevisionController.blnIsRetiro}">
		<h:panelGrid columns="3" id="pgBeneficiarioRetiro" rendered="true">
		<rich:column width="120px">
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px"
			rendered="true">
		</rich:column>
	</h:panelGrid>
	
	
	<h:panelGrid id="pgLitstaBeneficiariosRetiro" columns="3" rendered="true">
		<rich:column width="120px">
			<h:outputText value="Beneficiario(s) :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column width="800px">
			<h:panelGrid id="tblBenenficiarioRetiro" columns="2">
				<rich:column>
					<rich:dataTable
						value="#{solicitudPrevisionController.beanExpedientePrevision.listaBeneficiarioPrevision}"
						rowKeyVar="rowKey" sortMode="single" width="800px"
						var="itemBeneficiariosRetiro">
					
					<f:facet name="header">
							<rich:columnGroup>
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
								<rich:column width="80px" rowspan="2">
									<h:outputText value="% Beneficio"/>
								</rich:column >
								<rich:column width="80px" rowspan="2">
									<h:outputText value="Monto"/>
								</rich:column>
							</rich:columnGroup>
						</f:facet>
					
						<rich:column width="15px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>
						<rich:column width="250px">
							<h:outputText
								value="#{itemBeneficiariosRetiro.persona.natural.strApellidoPaterno} #{itemBeneficiariosRetiro.persona.natural.strApellidoMaterno},#{itemBeneficiariosRetiro.persona.natural.strNombres}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<tumih:outputText
								cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemBeneficiariosRetiro.persona.documento.intTipoIdentidadCod}" />
						</rich:column>
						<rich:column width="100px">
							<h:outputText style="text-align: center"
								value="#{itemBeneficiariosRetiro.persona.documento.strNumeroIdentidad}" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<tumih:outputText 
								cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}"
								itemValue="intIdDetalle" itemLabel="strDescripcion"
								property="#{itemBeneficiariosRetiro.intTipoViculo}" />
						</rich:column>
						<rich:column width="100px">
							<h:outputText value="#{itemBeneficiariosRetiro.bdPorcentajeBeneficio}" style="text-align: center"/>
						</rich:column>			
						<rich:column width="100px" style="text-align: right" >
							<h:outputText value="#{itemBeneficiariosRetiro.bdMontoNeto}" style="text-align: right">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
					</rich:dataTable>
				</rich:column>
			</h:panelGrid>

		</rich:column>
	</h:panelGrid>

		<h:panelGrid columns="3">
			<rich:column  width="600px">
			</rich:column>
			<rich:column  width="250px">
			</rich:column>
			<rich:column>
			</rich:column>
		</h:panelGrid>
	
	</h:panelGroup>	


		<h:panelGrid columns="3">
			<rich:column width="120px">
				<h:outputText value="Observación:" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column width="800px">
				<h:inputTextarea rows="5" cols="140" readonly="true"
					value="#{solicitudPrevisionController.beanExpedientePrevision.strObservacion}" />
			</rich:column>
		</h:panelGrid>

	</h:panelGroup>
</h:panelGroup>
