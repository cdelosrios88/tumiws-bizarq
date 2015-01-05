<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Christian De los Ríos 			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 09/08/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	
	<h:panelGrid>
		<rich:extendedDataTable id="edtCronogramaCredito" var="item" rowKeyVar="rowKey" sortMode="single" width="1000px" 
			value="#{solicitudPrestamoController.listaCronogramaCreditoComp}" rows="10" height="250px"
			enableContextMenu="false">
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Cuota"/>
				</f:facet>
				<h:outputText value="#{rowKey+1}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Fecha de Pago"/>
				</f:facet>
				<h:outputText value="#{item.strFechaVencimiento}"/>
			</rich:column>
			<rich:column width="80px">
				<f:facet name="header">
					<h:outputText value="Días Transc."/>
				</f:facet>
				<h:outputText value="#{item.intDiasTranscurridos}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Monto Capital"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdSaldoCapital}"/></div>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Amortización"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdAmortizacion}"/></div>
			</rich:column>
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Interés"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdInteres}"/></div>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Cuota Mensual"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdCuotaMensual}"/></div>
			</rich:column>
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Aportes"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdAportes}"/></div>
			</rich:column>
			<rich:column rendered="#{solicitudPrestamoController.intCantExpedientesVigentes == 1}" width="85px">
				<f:facet name="header">
					<h:outputText value="#{solicitudPrestamoController.strDescCuotaFijaExpCred1}"/>
				</f:facet>
				<h:outputText value="#{item.bdCuotaFijaExpediente1}">
					<f:converter converterId="ConvertidorMontos"  />
				</h:outputText>
			</rich:column>
			<rich:column rendered="#{solicitudPrestamoController.intCantExpedientesVigentes == 2}" width="85px">
				<f:facet name="header">
					<h:outputText value="#{solicitudPrestamoController.strDescCuotaFijaExpCred2}"/>
				</f:facet>
				<h:outputText value="#{item.bdCuotaFijaExpediente2}">
					<f:converter converterId="ConvertidorMontos"  />
				</h:outputText>
			</rich:column>
			<rich:column rendered="#{solicitudPrestamoController.intCantExpedientesVigentes == 3}" width="85px">
				<f:facet name="header">
					<h:outputText value="#{solicitudPrestamoController.strDescCuotaFijaExpCred3}"/>
				</f:facet>
				<h:outputText value="#{item.bdCuotaFijaExpediente3}">
					<f:converter converterId="ConvertidorMontos"  />
				</h:outputText>
			</rich:column>
			<rich:column rendered="#{solicitudPrestamoController.intCantExpedientesVigentes == 4}" width="85px">
				<f:facet name="header">
					<h:outputText value="#{solicitudPrestamoController.strDescCuotaFijaExpCred4}"/>
				</f:facet>
				<h:outputText value="#{item.bdCuotaFijaExpediente4}">
					<f:converter converterId="ConvertidorMontos"  />
				</h:outputText>
			</rich:column>
			<rich:column width="80px">
				<f:facet name="header">
					<h:outputText value="Total Cuota Mensual"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdTotalCuotaMensual}"/></div>
			</rich:column>
			
			<f:facet name="footer">
				<rich:datascroller for="edtCronogramaCredito" maxPages="20" />
			</f:facet>
		</rich:extendedDataTable>
	</h:panelGrid>