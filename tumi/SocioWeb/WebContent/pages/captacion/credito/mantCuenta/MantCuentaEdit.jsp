<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : MantCuentaEdit           -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 02/04/2012               -->

		<rich:panel rendered="#{mantCuentaController.formMantCuentaRendered}" style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
          	 	<h:panelGrid columns="5">
          	 		<rich:column width="140"><h:outputText value="Nombre del Mantenimiento"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
	          	 		<h:inputText value="#{mantCuentaController.beanCaptacion.strDescripcion}" size="80"/>
                    </rich:column>
                    <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
                    <rich:column>
	          	 		<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaEstadoSolicitudCod}" disabled="true">
	          	 			<f:selectItem itemLabel="Pendiente" itemValue="0"/>
          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                    </rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{mantCuentaController.msgTxtDescripcion}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Estado del Mantenimiento"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaEstadoCod}" disabled="true">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
						<h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
							<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaEstadoCod}" disabled="false">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtEstadoAporte}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgRanFec" columns="5">
          	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<rich:calendar enableManualInput="true"
	                   	value="#{mantCuentaController.daFechaIni}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 		<rich:column>
	          	 		<h:selectOneRadio value="#{mantCuentaController.rbFecIndeterm}">
	          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
	          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
	          	 			<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgRanFec"/>
	          	 		</h:selectOneRadio>
                    </rich:column>
                    <rich:column>
          	 			<rich:calendar enableManualInput="true"
          	 			rendered="#{mantCuentaController.fecFinAportacionRendered}"
	                   	value="#{mantCuentaController.daFechaFin}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{mantCuentaController.msgTxtFechaIni}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid columns="6">
          	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaTipopersonaCod}">
          	 				<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column width="100"><h:outputText value="Tipo de Relación"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaRolPk}">
          	 				<f:selectItem itemLabel="Todos" itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtTipoPersona}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{mantCuentaController.rbCondicion}">
          	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
          	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.listarCondicion}" reRender="dtCondSocio"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtCondicion}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="dtCondSocio">
         	 			<rich:dataTable value="#{mantCuentaController.listaCondicionComp}"
	         			rendered="#{not empty mantCuentaController.listaCondicionComp}"
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
          	 		<rich:column width="140"><h:outputText value="Tipo de Condición Laboral"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaCondicionLaboralCod}">
          	 				<f:selectItem itemLabel="Todos" itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtTipoCondLaboral}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de Descuento"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaTipoDescuentoCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtTipoDscto}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de Moneda"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaMonedaCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtMoneda}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgValAportacion" columns="8">
          	 		<rich:column width="140"><h:outputText value="Tipo de Configuración"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaTipoConfiguracionCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{mantCuentaController.strValAporte}">
          	 				<f:selectItem itemLabel="Importe" itemValue="1"/>
          	 				<f:selectItem itemLabel="%" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgValAportacion"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{mantCuentaController.beanCaptacion.bdValorConfiguracion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" disabled="#{mantCuentaController.enabDisabValImporte}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{mantCuentaController.beanCaptacion.bdPorcConfiguracion}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" disabled="#{mantCuentaController.enabDisabValPorcentaje}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{mantCuentaController.beanCaptacion.intParaAplicacionCod}">
          	 				<f:selectItem itemLabel="RMV" itemValue="1"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{mantCuentaController.msgTxtTipoConfig}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCuentasConsideradas" columns="3">
          	 		<rich:column width="140"><h:outputText value="Cuentas Consideradas"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
         	 			<rich:column>
          	 			<h:selectManyCheckbox value="#{mantCuentaController.lstCtasConsideradas}" layout="pageDirection">
          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectManyCheckbox>
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
          	 			<h:selectBooleanCheckbox value="#{mantCuentaController.chkProvision}">
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="250">
          	 			<h:outputText value="Provisión de Mantenimiento"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{mantCuentaController.enabDisabProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgExtProvision" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{mantCuentaController.chkExtProvision}">
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgExtProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="250">
          	 			<h:outputText value="Extorno de Mantenimiento"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{mantCuentaController.enabDisabExtProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCancelacion" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{mantCuentaController.chkCancelacion}">
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgCancelacion"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="250">
          	 			<h:outputText value="Cancelación de Transferencia"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{mantCuentaController.enabDisabCancelacion}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCancelacionxPlaSueldos" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{mantCuentaController.chkCancelacion}">
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgCancelacion"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="250">
          	 			<h:outputText value="Cancelación de por Planilla de Sueldos"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{mantCuentaController.enabDisabCancelacion}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCancelacionxCaja" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{mantCuentaController.chkCancelacion}">
          	 				<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgCancelacion"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="250">
          	 			<h:outputText value="Cancelación por Caja"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{mantCuentaController.enabDisabCancelacion}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
        </rich:panel>
