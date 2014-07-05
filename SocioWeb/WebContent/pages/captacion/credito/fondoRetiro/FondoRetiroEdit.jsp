<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : FondoRetiroView          -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 29/03/2012               -->

    <rich:panel rendered="#{fondoRetiroController.formFondoRetiroRendered}" style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
          	 	<h:panelGrid columns="5">
          	 		<rich:column width="140"><h:outputText value="Nombre de Fondo de Retiro" /></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
	          	 		<h:inputText value="#{fondoRetiroController.beanCaptacion.strDescripcion}" size="80"/>
                    </rich:column>
                    <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
                    <rich:column>
	          	 		<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaEstadoSolicitudCod}" disabled="true">
          	 				<f:selectItem itemLabel="Pendiente" itemValue="0"/>
          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                    </rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{fondoRetiroController.msgTxtDescripcion}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid id="pgRanFec" columns="5">
          	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<rich:calendar enableManualInput="true"
	                   	value="#{fondoRetiroController.daFechaIni}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 		<rich:column>
	          	 		<h:selectOneRadio value="#{fondoRetiroController.rbFecIndeterm}">
	          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
	          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
	          	 			<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgRanFec"/>
	          	 		</h:selectOneRadio>
                    </rich:column>
                    <rich:column>
          	 			<rich:calendar enableManualInput="true"
          	 			rendered="#{fondoRetiroController.fecFinAportacionRendered}"
	                   	value="#{fondoRetiroController.daFechaFin}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{fondoRetiroController.msgTxtFechaIni}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Estado del Fondo de Retiro"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaEstadoCod}" disabled="true">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
						<h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
							<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaEstadoCod}" disabled="false">
	         	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtEstadoAporte}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="6">
          	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaTipopersonaCod}">
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column width="100"><h:outputText value="Tipo de Relación"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaRolPk}">
                        	<tumih:selectItems var="sel" value="#{fondoRetiroController.listaTipoRelacion}" 
		                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
		                       propertySort="intOrden"/>
                        	<%-- <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>--%>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtTipoPersona}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{fondoRetiroController.rbCondicion}">
          	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
          	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.listarCondicion}" reRender="dtCondSocio"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtCondicion}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:panelGrid id="dtCondSocio">
         	 			<rich:dataTable value="#{fondoRetiroController.listaCondicionComp}"
	         			rendered="#{not empty fondoRetiroController.listaCondicionComp}"
	                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
	                	<rich:column width="15px;">
			                <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
			            </rich:column>
			            <rich:column width="500">
			            	<h:outputText value="#{item.tabla.strDescripcion}"/>
			            </rich:column>
	                </rich:dataTable>
         	 		</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de descuento"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaTipoDescuentoCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtTipoDscto}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de Configuración"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaTipoConfiguracionCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtTipoConfig}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgValAportacion" columns="8">
          	 		<rich:column width="140"><h:outputText value="Valor de Cuota"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaMonedaCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{fondoRetiroController.strValAporte}">
          	 				<f:selectItem itemLabel="Importe" itemValue="1"/>
          	 				<f:selectItem itemLabel="%" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgValAportacion"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdValorConfiguracion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" disabled="#{fondoRetiroController.enabDisabValImporte}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoSepelioController.beanCaptacion.bdPorcConfiguracion}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" disabled="#{fondoRetiroController.enabDisabValPorcentaje}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaAplicacionCod}">
          	 				<f:selectItem itemLabel="RMV" itemValue="1"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtMoneda}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgNumCuotas" columns="4">
          	 		<rich:column width="140">
          	 			<h:outputText value="Número de cuotas"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.intNumeroCuota}" onkeypress="return soloNumeros(event);" size="5" maxlength="2"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="cuotas"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="5">
          	 		<rich:column width="140">
          	 			<h:outputText value="Tiempo de permanencia"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intTiempoPermanencia}">
          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TPERMANENCIAFONRET}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="años"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgTasaInteres" columns="10">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkTasaInteres}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgTasaInteres"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115">
          	 			<h:outputText value="Tasa de Interés"/>
          	 		</rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column width="130px">
          	 			<h:selectOneRadio value="#{fondoRetiroController.rbTasaInteres}" disabled="#{fondoRetiroController.enabDisabTasaInt}">
          	 				<f:selectItem itemLabel="% Interés" itemValue="1"/>
          	 				<f:selectItem itemLabel="TEA" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgTasaInteres"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdTem}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="5" disabled="#{fondoRetiroController.enabDisabPorcInt}"/>
          	 		</rich:column>
          	 		<rich:column width="80px">
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intTasaNaturaleza}" disabled="#{fondoRetiroController.enabDisabPorcInt}">
          	 				%<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINTERES}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intTasaFormula}" disabled="#{fondoRetiroController.enabDisabPorcInt}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CALCINTERES}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu disabled="#{fondoRetiroController.enabDisabPorcInt}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdTea}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{fondoRetiroController.enabDisabTea}"/>
          	 		</rich:column>
          	 		<rich:column width="110px;">
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdTna}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{fondoRetiroController.enabDisabTna}"/> TNA
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{fondoRetiroController.msgTxtTNA}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid id="pgPenalidadRetiro" columns="7">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkPenalidadAnticip}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgPenalidadRetiro"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115">
          	 			<h:outputText value="Penalidad de Retiro anticipado"/>
          	 		</rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{fondoRetiroController.rbPenalidad}" disabled="#{fondoRetiroController.enabDisabPenalidadAnticip}">
          	 				<f:selectItem itemLabel="Porcentaje de Interés" itemValue="1"/>
          	 				<f:selectItem itemLabel="Tasa de Mora" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgPenalidadRetiro"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdPenalidadPorcentaje}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{fondoRetiroController.enabDisabPenalPorc}"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.bdPenalidadTasaMora}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{fondoRetiroController.enabDisabPenalTasaMora}"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaPenalidadAplicacion}" disabled="#{fondoRetiroController.enabDisabPenalidadAnticip}">
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_AFECTAPENALIDADFONRET}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgLimiteEdad" columns="5">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkLimiteEdad}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgLimiteEdad"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115px">
          	 			<h:outputText value="Limite de Edad"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.intEdadLimite}" onkeypress="return soloNumeros(event);" size="5" maxlength="2" disabled="#{fondoRetiroController.enabDisabLimiteEdad}"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="años"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgRequisitosBenef" columns="3">
          	 		<rich:column width="140">
          	 			<h:outputText value="Requisitos de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:panelGrid id="idTMinAport" columns="3">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoRetiroController.chkTiempoMinAport}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="idTMinAport"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Tiempo mínimo de aportación"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intTiempoAportacion}" disabled="#{fondoRetiroController.enabDisabTiempoMinAport}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TMINIMOFONRET}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu> meses
          	 				</rich:column>
          	 			</h:panelGrid>
          	 			
          	 			<h:panelGrid id="idCuotasMin" columns="3">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoRetiroController.chkCuotasMinimas}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="idCuotasMin"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Cuotas mínimas depositadas"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intCuota}" disabled="#{fondoRetiroController.enabDisabCuotasMinDeposit}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TCUOTASMINFONRET}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu> cuotas
          	 				</rich:column> 
          	 			</h:panelGrid>
          	 			
          	 			<h:panelGrid id="idTiempoMinDev" columns="4">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoRetiroController.chkTiempoDevol}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="idTiempoMinDev"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Tiempo mínimo de devolución"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.intTiempoDevolucion}" size="5" maxlength="3" disabled="#{fondoRetiroController.enabDisabTiempoMinDevol}" onkeypress="return soloNumeros(event);"/> días
          	 				</rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{fondoRetiroController.beanCaptacion.intParaTipoDia}" disabled="#{fondoRetiroController.enabDisabTiempoMinDevol}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TDIASFECHA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>
          	 				</rich:column>
          	 			</h:panelGrid>
          	 			
          	 			<h:panelGrid columns="2">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoRetiroController.chkEscalaBenef}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgTipoBeneficiario,pgCuotasCanc,pgCuotasCancDet"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column>
		       	 				<h:outputText value="Escala de Beneficio"/>
		       	 			</rich:column>
          	 			</h:panelGrid>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgTipoBeneficiario" columns="6">
          	 		<rich:column width="140"/>
          	 		<rich:column>
          	 			<h:outputText value="Tipo de Beneficiario"/>
          	 		</rich:column>
          	 		<!--  
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.intTipoBeneficiario}" disabled="#{fondoRetiroController.enabDisabEscBenef}">
          	 				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_BENEFICIARIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		-->
          	 		<rich:column>
						<h:selectOneMenu style="width:120px" value="#{fondoRetiroController.intTipoBeneficiario}" disabled="#{fondoRetiroController.enabDisabEscBenef}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{fondoRetiroController.listaTipoBeneficiario}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
			
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoRetiroController.intTipoMotivo}" disabled="#{fondoRetiroController.enabDisabEscBenef}">
                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_MOTFONRET}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtTipoBeneficiario}" styleClass="msgError"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoRetiroController.msgTxtTipoMotivo}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCuotasCanc" columns="2">
          	 		<rich:column width="140"/>
          	 		<rich:panel style="border:2px solid #17356f;background-color:#DEEBF5;">
	          	 		<h:panelGrid id="pgCuotasCancDet" columns="11">
	          	 			<rich:column>
	          	 				<h:outputText value="Cuotas canceladas"/>
	          	 			</rich:column>
		          	 		<rich:column>
		                        <h:selectOneMenu value="#{fondoRetiroController.intTipoCuota}" disabled="#{fondoRetiroController.enabDisabEscBenef}">
		                            <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		                            <tumih:selectItems var="condi" cache="#{applicationScope.Constante.PARAM_T_TIPO_RANGOCUOTAS}"
		                                               itemValue="#{condi.intIdDetalle}" itemLabel="#{condi.strDescripcion}"/>
		                        </h:selectOneMenu>
	                        </rich:column>
	                        <rich:column>
	                            <h:inputText value="#{fondoRetiroController.intCuotaCancelada}" disabled="#{fondoRetiroController.enabDisabEscBenef}" onkeypress="return soloNumeros(event);" size="5"/>
	                        </rich:column>
	                        <rich:column>
	                            <h:outputText value="Beneficio" />
	                        </rich:column>
	                        <rich:column>
	                            <h:selectBooleanCheckbox value="#{fondoRetiroController.bAportacion}" disabled="#{fondoRetiroController.enabDisabEscBenef}"/>
	                        </rich:column>
	                        <rich:column>
	                            <h:outputText value="Aportación"/>
	                        </rich:column>
	                        <rich:column>
	                            <h:selectBooleanCheckbox value="#{fondoRetiroController.bInteres}" disabled="#{fondoRetiroController.enabDisabEscBenef}"/>
	                        </rich:column>
		                    <rich:column>
		                        <h:outputText value="Interés"/>
		                    </rich:column>
		                    <rich:column>
		                        <h:selectBooleanCheckbox value="#{fondoRetiroController.bPenalidad}" disabled="#{fondoRetiroController.enabDisabEscBenef}"/>
		                    </rich:column>
		                    <rich:column>
		                        <h:outputText value="Penalidad"/>
		                    </rich:column>
		                    <rich:column>
		                        <a4j:commandButton value="Agregar" disabled="#{fondoRetiroController.enabDisabEscBenef}" actionListener="#{fondoRetiroController.addReqVinculo}" styleClass="btnEstilos" reRender="pgTipoBeneficiario,pgCuotasCanc,pgReqVinculo"/>
		                    </rich:column>
	                     </h:panelGrid>
                     </rich:panel>
          	 	</h:panelGrid>
          	 	<br/>
          	 	
          	 	<h:panelGrid id="pgReqVinculo" columns="2">
          	 		<rich:column width="140"/>
          	 		<rich:dataTable value="#{fondoRetiroController.listaRequisitosVinculo}"
	         			 rendered="#{not empty fondoRetiroController.listaRequisitosVinculo}"
	                     var="item" rowKeyVar="rowKey" sortMode="single" width="700px">
	                	 <f:facet name="header">
	                	 	<rich:columnGroup>
	                	 		<rich:column><rich:spacer/></rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Tipo de Beneficio"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Motivo"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Tipo de cuota"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Cuota(s) cancelada(s)"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Aportación"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Interés"/>
	                	 		</rich:column>
	                	 		<rich:column>
	                	 			<h:outputText value="Penalidad"/>
	                	 		</rich:column>
	                	 		<rich:column><rich:spacer/></rich:column>
	                	 	</rich:columnGroup>
	                	 </f:facet>
	                	 <rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		                	 <rich:column width="15px;">
				                 <h:outputText value="#{rowKey+1}"/>
				             </rich:column>
				             <rich:column width="120px">
				            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_BENEFICIARIO}" 
	                                 itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                 property="#{item.id.paraTipoVinculoCod}"/>
				             </rich:column>
				             <rich:column width="110px">
				             	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_MOTFONRET}" 
	                                 itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                 property="#{item.intParaMotivo}"/>
				             </rich:column>
				             <rich:column width="140px">
				             	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_RANGOCUOTAS}" 
	                                 itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                 property="#{item.intParaTipoCuotaCod}"/>
				             </rich:column>
				             <rich:column width="140px">
				            	 <div align="center"><h:outputText value="#{item.intCuotaCancelada}"/></div>
				             </rich:column>
				             <rich:column width="120px;">
				            	 <div align="center"><h:selectBooleanCheckbox value="#{item.intAportacion==1}" disabled="true"/></div>
				             </rich:column>
				             <rich:column width="120px;">
				            	 <div align="center"><h:selectBooleanCheckbox value="#{item.intInteres==1}" disabled="true"/></div>
				             </rich:column>
				             <rich:column width="120px;">
				           	 	<div align="center"><h:selectBooleanCheckbox value="#{item.intPenalidad==1}" disabled="true"/></div>
				             </rich:column>
				             <rich:column>
	                       		<a4j:commandLink id="lnkQuitarVinculo" actionListener="#{fondoRetiroController.removeReqVinculo}" reRender="pgReqVinculo">
	         						<f:param name="rowKeyVinculo" value="#{rowKey}"></f:param>
	         						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
	         						<rich:toolTip for="lnkQuitarVinculo" value="Quitar" followMouse="true"/>
	         					</a4j:commandLink>
		                  	</rich:column>
	                  	</rich:columnGroup>
	                </rich:dataTable>
          	 	</h:panelGrid>
          	 	<br/>
          	 	
          	 	<h:panelGrid id="pgCeseBenef" columns="5">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkCeseBeneficioMeses}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgCeseBenef"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115px">
          	 			<h:outputText value="Cese del Beneficio"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="Meses no descontados consecutivos"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoRetiroController.beanCaptacion.intCeseBeneficio}" disabled="#{fondoRetiroController.enabDisabCeseBenefMeses}" onkeypress="return soloNumeros(event);" size="5" maxlength="2"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgModelosCont" columns="3">
          	 		<rich:column width="140">
          	 			<h:outputText value="Modelos Contables"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:commandButton value="Solicitar" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgProvision" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkProvision}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Provisión"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgExtProvision" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkExtProvision}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgExtProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Extorno de Provisión"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabExtProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgRegularicCuotas" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkRegularicCuota}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgRegularicCuotas"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Regularización de cuotas"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabRegularicCuota}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCeseBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkCeseBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgCeseBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Cese del Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabCeseBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgSolicBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkSolicitudBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgSolicBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Solicitud de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabSolicitudBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgAprobBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkAprobacBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgAprobBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Aprobación de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabAprobacBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgAnulacRecBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkAnulRechazoBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgAnulacRecBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Anulación o Rechazo de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabAnulRechazoBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgGiroBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkGiroBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgGiroBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Giro de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabGiroBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCancelacion" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoRetiroController.chkCancelacion}">
          	 				<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgCancelacion"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Cancelación"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoRetiroController.enabDisabCancelacion}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 </rich:panel>