<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     :  			-->
	<!-- Modulo    :                  		-->
	<!-- Prototipo :      -->
	<!-- Fecha     : 13/04/2013              		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	
	<h:panelGrid id="pgSocioActividad">
		<h:panelGrid columns="2">
			<rich:column width="120" style="font-weight: bold;">
				<h:outputText  value="Socio     : "  />
			</rich:column>
			<rich:column>
				<h:outputText
					value="#{solicitudActividadController.beanSocioComp.persona.intIdPersona} - #{solicitudActividadController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudActividadController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudActividadController.beanSocioComp.persona.natural.strNombres} - DNI: #{solicitudActividadController.beanSocioComp.persona.documento.strNumeroIdentidad}" />
			</rich:column>
			
			<rich:column width="120" style="font-weight: bold;">
				<h:outputText value="Sucursal  : " />
			</rich:column>
			<rich:column>
				<h:outputText value="#{solicitudActividadController.strUnidadesEjecutorasConcatenadas}"/>
			</rich:column>
			
			<rich:column width="120" style="font-weight: bold;">
				<h:outputText value="Actividad : " />
			</rich:column>
			<rich:column>
				<tumih:outputText value="#{solicitudActividadController.listaTiposActividad}" 
						itemValue="intIdDetalle" itemLabel="strDescripcion"
						property="#{solicitudActividadController.intTipoActividad}"/>
				<tumih:outputText value="#{solicitudActividadController.listaTiposSubActividad}" 
						itemValue="intIdDetalle" itemLabel="strDescripcion"
						property="#{solicitudActividadController.intTipoSubActividad}"/>
			</rich:column>
			
			<rich:column width="120" style="font-weight: bold;">
				<h:outputText value="Solicitud  : " />
			</rich:column>
			<rich:column>
				<h:outputText value="#{solicitudActividadController.beanExpedienteCredito.id.intItemExpediente} - #{solicitudActividadController.beanExpedienteCredito.id.intItemDetExpediente}" />
			</rich:column>
		</h:panelGrid>
	
		<rich:separator height="2px" width="99%" style="margin:5px 0px"></rich:separator>

		<rich:extendedDataTable id="edtCronogramaActividad" var="item" rowKeyVar="rowKey" sortMode="single" width="570px" 
			value="#{solicitudActividadController.listaCronogramaCreditoComp}" rows="10" height="320px"
			enableContextMenu="false">
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Cuota"/>
				</f:facet>
				<h:outputText value="#{rowKey+1}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Forma de pago"/>
				</f:facet>
				<tumih:outputText
					cache="#{applicationScope.Constante.PARAM_T_FORMADEPAGO}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{item.intParaTipoFormapago}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Fecha de Pago"/>
				</f:facet>
				<h:outputText value="#{item.strFechaVencimiento}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Amortización"/>
				</f:facet>
				<div align="right">
					<h:outputText value="#{item.bdAmortizacion}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</div>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Saldo Capital"/>
				</f:facet>
				<div align="right">
					<h:outputText value="#{item.bdSaldoCapital}">
						<f:converter converterId="ConvertidorMontos"  />
					</h:outputText>
				</div>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Estado"/>
				</f:facet>
				<h:outputText value="#{item.strEstadoDescripcion}"/>
			</rich:column>
			
			<f:facet name="footer">
				<rich:datascroller for="edtCronogramaActividad" maxPages="20" />
			</f:facet>
		</rich:extendedDataTable>
	</h:panelGrid>