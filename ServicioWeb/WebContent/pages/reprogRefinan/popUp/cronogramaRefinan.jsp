<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Christian De los R�os 			-->
	<!-- Modulo    : Cr�ditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 09/08/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	
	<h:panelGrid>
		<rich:extendedDataTable id="edtCronogramaRefinan" var="item" rowKeyVar="rowKey" sortMode="single" width="920px" 
			value="#{solicitudRefinanController.listaCronogramaCreditoComp}" rows="10" height="280px"
			rendered="#{not empty solicitudRefinanController.listaCronogramaCreditoComp}"
			enableContextMenu="false">
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Cuota"/>
				</f:facet>
				<h:outputText value="#{rowKey+1}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Fecha Env�o"/>
				</f:facet>
				<h:outputText value="#{item.strFechaEnvio}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Fecha de Pago"/>
				</f:facet>
				<h:outputText value="#{item.strFechaVencimiento}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="D�as Transc."/>
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
					<h:outputText value="Amortizaci�n"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdAmortizacion}"/></div>
			</rich:column>
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Inter�s"/>
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
			<rich:column width="140px">
				<f:facet name="header">
					<h:outputText value="Total Cuota Mensual"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdTotalCuotaMensual}"/></div>
			</rich:column>
			<f:facet name="footer">
				<rich:datascroller for="edtCronogramaRefinan" maxPages="20" />
			</f:facet>
		</rich:extendedDataTable>
	</h:panelGrid>