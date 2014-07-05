<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : AES 		  Edit        -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 02/04/2012               -->

		<rich:panel rendered="#{aesController.formAesRendered}" style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
         	 	<h:panelGrid columns="5">
         	 		<rich:column width="140"><h:outputText value="Nombre del AES" /></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
          	 		<h:inputText value="#{aesController.beanCaptacion.strDescripcion}" size="80"/>
                   </rich:column>
                   <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
                   <rich:column>
          	 		<h:selectOneMenu value="#{aesController.beanCaptacion.intParaEstadoSolicitudCod}" disabled="true">
         	 				<f:selectItem itemLabel="Pendiente" itemValue="0"/>
         	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                   </rich:column>
         	 	</h:panelGrid>
         	 	<h:outputText value="#{aesController.msgTxtDescripcion}" styleClass="msgError"/>
         	 	
         	 	<h:panelGrid id="pgRanFec" columns="5">
         	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<rich:calendar enableManualInput="true"
                   	value="#{aesController.daFechaIni}"
                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                    cellWidth="10px" cellHeight="20px"/>
         	 		</rich:column>
         	 		<rich:column>
          	 		<h:selectOneRadio value="#{aesController.rbFecIndeterm}">
          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
          	 			<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgRanFec"/>
          	 		</h:selectOneRadio>
                   </rich:column>
                   <rich:column>
         	 			<rich:calendar enableManualInput="true"
         	 			rendered="#{aesController.fecFinAportacionRendered}"
                   	value="#{aesController.daFechaFin}"
                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                    cellWidth="10px" cellHeight="20px"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<h:outputText value="#{aesController.msgTxtFechaIni}" styleClass="msgError"/>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Estado del AES"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intParaEstadoCod}" disabled="#{aesController.enabDisabEstCaptacion}">
         	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aesController.msgTxtEstadoAporte}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="6">
         	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intParaTipopersonaCod}">
                       		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column><h:outputText value="Tipo de relación: "/></rich:column>
         	 		<rich:column>
       	 				<h:selectOneMenu value="#{aesController.beanCaptacion.intParaRolPk}">
       	 					<tumih:selectItems var="sel" value="#{aesController.listaTipoRelacion}" 
		                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
		                       propertySort="intOrden"/>
	                      	<%--<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>--%>
        	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aesController.msgTxtTipoPersona}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneRadio value="#{aesController.rbCondicion}">
         	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
         	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
         	 				<a4j:support event="onclick" actionListener="#{aesController.listarCondicion}" reRender="dtCondSocio"/>
         	 			</h:selectOneRadio>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aesController.msgTxtCondicion}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<h:panelGrid id="dtCondSocio">
       	 			<rich:dataTable value="#{aesController.listaCondicionComp}"
	         			rendered="#{not empty aesController.listaCondicionComp}"
	                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
	                	<rich:column width="15px;">
			                <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
			            </rich:column>
			            <rich:column width="500">
			            	<h:outputText value="#{item.tabla.strDescripcion}"/>
			            </rich:column>
	                </rich:dataTable>
        	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="6">
         	 		<rich:column width="140"><h:outputText value="Regularidad de beneficio"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column><h:outputText value="Nro. de veces"/></rich:column>
         	 		<rich:column><h:inputText value="#{aesController.beanCaptacion.intRegularidad}" size="5" onkeydown="return validarEnteros(event);"/></rich:column>
         	 		<rich:column><h:outputText value="Periodicidad"/></rich:column>
         	 		<h:selectOneMenu
						value="#{aesController.beanCaptacion.intPeriodicidad}">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{aesController.listaPeriocidadAes}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>  meses
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgLimiteEdad" columns="5">
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aesController.chkLimiteEdad}">
         	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgLimiteEdad"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="115px">
         	 			<h:outputText value="Limite de Edad"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value=":"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aesController.beanCaptacion.intEdadLimite}" onkeydown="return validarEnteros(event);" size="5" maxlength="2" disabled="#{aesController.enabDisabLimiteEdad}"/>
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
         	 			<rich:panel style="border:1px solid #17356f;background-color:#DEEBF5;width:700px;">
         	 			<h:panelGrid id="pgCuotasFondoSep" columns="5">
         	 				<rich:column>
         	 					<h:selectBooleanCheckbox value="#{aesController.chkCuotaFondoSepelio}">
	          	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgCuotasFondoSep"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="220">
	          	 			<h:outputText value="Cuotas de Fondo de Sepelio"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intCuota}" disabled="#{aesController.enabDisabCuotaFondoSepelio}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TCUOTASMINFONRET}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 				<rich:column width="40"><h:outputText value="cuotas"/> </rich:column>
         	 				<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intParaTipoMaxMinCuotaCod}" disabled="#{aesController.enabDisabCuotaFondoSepelio}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESCALATIEMPO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 			</h:panelGrid>
         	 			
         	 			<h:panelGrid id="pgTiempoAportacion" columns="5">
         	 				<rich:column>
         	 					<h:selectBooleanCheckbox value="#{aesController.chkTiempoAportacion}">
	          	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgTiempoAportacion"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="220">
	          	 			<h:outputText value="Tiempo de Aportación"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intTiempoAportacion}" disabled="#{aesController.enabDisabTiempoAportacion}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TCUOTASMINFONRET}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 				<rich:column width="40"><h:outputText value="meses"/> </rich:column>
         	 				<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intParaTipoMaxMinAportCod}" disabled="#{aesController.enabDisabTiempoAportacion}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESCALATIEMPO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 			</h:panelGrid>
         	 			
         	 			<h:panelGrid id="pgTiempoPresent" columns="5">
         	 				<rich:column>
         	 					<h:selectBooleanCheckbox value="#{aesController.chkTiempoPresentSustento}">
	          	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgTiempoPresent"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="220">
	          	 			<h:outputText value="Tiempo de presentación de sustento"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intTiempoSustento}" disabled="#{aesController.enabDisabTiempoPresentSust}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TCUOTASMINFONRET}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 				<rich:column width="40"><h:outputText value="días"/> </rich:column>
         	 				<rich:column>
	          	 			<h:selectOneMenu value="#{aesController.beanCaptacion.intParaTipoMaxMinSustCod}" disabled="#{aesController.enabDisabTiempoPresentSust}">
	                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESCALATIEMPO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
         	 				</rich:column>
         	 			</h:panelGrid>
         	 			</rich:panel>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="3">
         	 		<rich:column width="140"><h:outputText value="Tipo de concepto"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<rich:panel style="border:1px solid #17356f;background-color:#DEEBF5;width:700px;">
         	 			<h:panelGrid border="0">
         	 				<rich:dataTable value="#{aesController.listaConceptos}"
			         			rendered="#{not empty aesController.listaConceptos}"
			         			style="background-color:#DEEBF5;border:none;"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="650px">
			                	<%--
			                	<rich:column width="15px;">
					                <h:selectBooleanCheckbox value="#{item.chkConcepto}"/>
					            </rich:column>
					             --%>
					            <rich:column width="100">
					            	<h:outputText value="#{item.tabla.strDescripcion}"/>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value=":"/>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value="#{item.tabla.intIdDetalle==2?'Monto máximo x día':'Monto máximo'}"/>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value=":"/>
					            </rich:column>
					            <rich:column>
					            	<h:inputText value="#{item.intMonto}" size="5" onkeydown="return validarEnteros(event);"/>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value="soles"/>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value="#{item.tabla.intIdDetalle==2?'Nro. días':''}"/>
					            </rich:column>
					            <rich:column>
					            	<h:inputText value="#{item.intDia}" onkeydown="return validarEnteros(event);" rendered="#{item.tabla.intIdDetalle==2}" maxlength="3" size="5"/>
					            </rich:column>
			                </rich:dataTable>
         	 			</h:panelGrid>
         	 			</rich:panel>
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
         	 	
         	 	<h:panelGrid id="pgSolicBeneficio" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aesController.chkSolicitudBeneficio}">
         	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgSolicBeneficio"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="200">
         	 			<h:outputText value="Solicitud de Beneficio"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="#{aesController.enabDisabSolicitudBenef}" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgAprobBeneficio" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aesController.chkAprobacBeneficio}">
         	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgAprobBeneficio"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="200">
         	 			<h:outputText value="Aprobación de Beneficio"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="#{aesController.enabDisabAprobacBenef}" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgAnulacRecBeneficio" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aesController.chkAnulRechazoBeneficio}">
         	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgAnulacRecBeneficio"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="200">
         	 			<h:outputText value="Rechazo o Anulación de Beneficio"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="#{aesController.enabDisabAnulRechazoBenef}" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgGiroBeneficio" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aesController.chkGiroBeneficio}">
         	 				<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgGiroBeneficio"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="200">
         	 			<h:outputText value="Giro de Beneficio"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="#{aesController.enabDisabGiroBenef}" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         </rich:panel>
