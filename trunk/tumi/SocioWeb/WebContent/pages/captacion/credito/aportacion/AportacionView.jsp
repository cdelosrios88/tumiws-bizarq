<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Aportaciones View        -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 29/03/2012               -->

         	 <rich:panel rendered="#{aportacionController.formAportacionesRendered}" style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
         	 	<h:panelGrid columns="5">
         	 		<rich:column width="140"><h:outputText value="Nombre del Aporte" /></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
          	 		<h:inputText value="#{aportacionController.beanCaptacion.strDescripcion}" disabled="true" size="80"/>
                   </rich:column>
                   <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
                   <rich:column>
          	 		<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaEstadoSolicitudCod}" disabled="true">
         	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                   </rich:column>
         	 	</h:panelGrid>
         	 	<h:outputText value="#{aportacionController.msgTxtDescripcion}" styleClass="msgError"/>
         	 	
         	 	<h:panelGrid id="pgRanFec" columns="5">
         	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<rich:calendar enableManualInput="true" disabled="true"
                   	value="#{aportacionController.daFechaIni}"
                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                    cellWidth="10px" cellHeight="20px"/>
         	 		</rich:column>
         	 		<rich:column>
          	 		<h:selectOneRadio value="#{aportacionController.rbFecIndeterm}" disabled="true">
          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
          	 			<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgRanFec"/>
          	 		</h:selectOneRadio>
                   </rich:column>
                   <rich:column>
         	 			<rich:calendar enableManualInput="true" disabled="true"
         	 			rendered="#{aportacionController.fecFinAportacionRendered}"
                   	value="#{aportacionController.daFechaFin}"
                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                    cellWidth="10px" cellHeight="20px"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<h:outputText value="#{aportacionController.msgTxtFechaIni}" styleClass="msgError"/>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Estado del Aporte"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaEstadoCod}" disabled="true">
         	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtEstadoAporte}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaTipopersonaCod}" disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtTipoPersona}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneRadio value="#{aportacionController.rbCondicion}" disabled="true">
         	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
         	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.listarCondicion}" reRender="dtCondSocio"/>
         	 			</h:selectOneRadio>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtCondicion}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<h:panelGrid id="dtCondSocio">
        	 			<rich:dataTable value="#{aportacionController.listaCondicionComp}"
         			rendered="#{not empty aportacionController.listaCondicionComp}"
                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
                	<rich:column width="15px;">
		                <h:selectBooleanCheckbox value="#{item.chkSocio}" disabled="true"/>
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
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaTipoDescuentoCod}" disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtTipoDscto}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid columns="4">
         	 		<rich:column width="140"><h:outputText value="Tipo de Configuración"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaTipoConfiguracionCod}" disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtTipoConfig}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgValAportacion" columns="8">
         	 		<rich:column width="140"><h:outputText value="Valor de Aportación"/></rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaMonedaCod}" disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:selectOneRadio value="#{aportacionController.strValAporte}" disabled="true">
         	 				<f:selectItem itemLabel="Importe" itemValue="1"/>
         	 				<f:selectItem itemLabel="%" itemValue="2"/>
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgValAportacion"/>
         	 			</h:selectOneRadio>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aportacionController.beanCaptacion.bdValorConfiguracion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" disabled="true" size="10"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aportacionController.beanCaptacion.bdPorcConfiguracion}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" disabled="true" size="10"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intParaAplicacionCod}" disabled="true">
         	 				<f:selectItem itemLabel="RMV" itemValue="1"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="#{aportacionController.msgTxtMoneda}" styleClass="msgError"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgTasaInteres" columns="10">
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aportacionController.chkTasaInteres}" disabled="true">
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgTasaInteres"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="130">
         	 			<h:outputText value="Tasa de Interés"/>
         	 		</rich:column>
         	 		<rich:column><h:outputText value=":"/></rich:column>
         	 		<rich:column width="130px">
         	 			<h:selectOneRadio value="#{aportacionController.rbTasaInteres}" disabled="true">
         	 				<f:selectItem itemLabel="% Interés" itemValue="1"/>
         	 				<f:selectItem itemLabel="TEA" itemValue="2"/>
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgTasaInteres"/>
         	 			</h:selectOneRadio>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aportacionController.beanCaptacion.bdTem}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="5" disabled="true"/>
         	 		</rich:column>
         	 		<rich:column width="80px">
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intTasaNaturaleza}" disabled="true">
         	 				%<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINTERES}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu value="#{aportacionController.beanCaptacion.intTasaFormula}" disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CALCINTERES}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:selectOneMenu disabled="true">
         	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                       	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
         	 			</h:selectOneMenu>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aportacionController.beanCaptacion.bdTea}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="true"/>
         	 		</rich:column>
         	 		<rich:column width="110px;">
         	 			<h:inputText value="#{aportacionController.beanCaptacion.bdTna}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="true"/> TNA
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	<h:outputText value="#{aportacionController.msgTxtTNA}" styleClass="msgError"/>
         	 	
         	 	<h:panelGrid id="pgLimiteEdad" columns="5">
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aportacionController.chkLimiteEdad}" disabled="true">
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgLimiteEdad"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="115px">
         	 			<h:outputText value="Limite de Edad"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value=":"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:inputText value="#{aportacionController.beanCaptacion.intEdadLimite}" onkeypress="return soloNumeros(event);" size="5" maxlength="2" disabled="true"/>
         	 		</rich:column>
         	 		<rich:column>
         	 			<h:outputText value="años"/>
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
         	 			<h:commandButton value="Solicitar" disabled="true" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgProvision" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aportacionController.chkProvision}" disabled="true">
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgProvision"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="150">
         	 			<h:outputText value="Provisión"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="true" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgExtProvision" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aportacionController.chkExtProvision}" disabled="true">
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgExtProvision"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="150">
         	 			<h:outputText value="Extorno de Provisión"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="true" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 	
         	 	<h:panelGrid id="pgCancelacion" columns="4">
         	 		<rich:column width="150"></rich:column>
         	 		<rich:column>
         	 			<h:selectBooleanCheckbox value="#{aportacionController.chkCancelacion}" disabled="true">
         	 				<a4j:support event="onclick" actionListener="#{aportacionController.enableDisableControls}" reRender="pgCancelacion"/>
         	 			</h:selectBooleanCheckbox>
         	 		</rich:column>
         	 		<rich:column width="150">
         	 			<h:outputText value="Cancelación"/>
         	 		</rich:column>
         	 		<rich:column width="120">
         	 			<h:commandButton value="Detalle" disabled="true" styleClass="btnEstilos"/>
         	 		</rich:column>
         	 	</h:panelGrid>
         	 </rich:panel>
