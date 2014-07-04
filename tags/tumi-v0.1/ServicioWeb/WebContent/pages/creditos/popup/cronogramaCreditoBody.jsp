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
		<rich:extendedDataTable id="edtCronogramaCredito" var="item" rowKeyVar="rowKey" sortMode="single" width="920px" 
			value="#{solicitudPrestamoController.listaCronogramaCreditoComp}" rows="10" height="280px"
			enableContextMenu="false">
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Cuota"/>
				</f:facet>
				<h:outputText value="#{rowKey+1}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Fecha Envío"/>
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
			<rich:column width="140px">
				<f:facet name="header">
					<h:outputText value="Total Cuota Mensual"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdTotalCuotaMensual}"/></div>
			</rich:column>
			<f:facet name="footer">
				<rich:datascroller for="edtCronogramaCredito" maxPages="20" />
			</f:facet>
		</rich:extendedDataTable>
		<%--
		<rich:extendedDataTable id="edtCronogramaCredito" var="item" rowKeyVar="rowKey" sortMode="single" width="920px" 
			value="#{solicitudPrestamoController.beanExpedienteCredito.listaCronogramaCredito}" rows="10" height="280px"
			rendered="#{not empty solicitudPrestamoController.beanExpedienteCredito.listaCronogramaCredito}"
			enableContextMenu="false">
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Cuota"/>
				</f:facet>
				<h:outputText value="#{item.intNroCuota}"/>
			</rich:column>
			<rich:column width="80px">
				<f:facet name="header">
					<h:outputText value="Tipo Cuota"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUOTACRONOGRAMA}" 
                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                property="#{item.intParaTipoCuotaCod}"/>
			</rich:column>
			<rich:column width="110px">
				<f:facet name="header">
					<h:outputText value="Forma de Pago"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FORMADEPAGO}" 
                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                property="#{item.intParaFormaPagoCod}"/>
			</rich:column>
			<rich:column width="120px">
				<f:facet name="header">
					<h:outputText value="Tipo de Concepto"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCONCEPTOPRESTAMO}" 
                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                property="#{item.intParaTipoConceptoCod}"/>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Monto Concepto"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdMontoConcepto}"/></div>
			</rich:column>
			<rich:column>
				<f:facet name="header">
					<h:outputText value="Monto Capital"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.bdMontoCapital}"/></div>
			</rich:column>
			<rich:column width="100px">
				<f:facet name="header">
					<h:outputText value="Fecha Venc."/>
				</f:facet>
				<div align="right">
					<h:outputText value="#{item.tsFechaVencimiento}">
						<f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
					</h:outputText>
				</div>
			</rich:column>
			<rich:column width="110px">
				<f:facet name="header">
					<h:outputText value="Período Planilla"/>
				</f:facet>
				<div align="right"><h:outputText value="#{item.intPeriodoPlanilla}"/></div>
			</rich:column>
			<rich:column width="60px">
				<f:facet name="header">
					<h:outputText value="Aportes"/>
				</f:facet>
				<h:outputText value="#{solicitudPrestamoController.bdAportes}"/>
			</rich:column>
			<rich:column width="80px">
				<f:facet name="header">
					<h:outputText value="Estado"/>
				</f:facet>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                property="#{item.intParaEstadoCod}"/>
			</rich:column>
			<f:facet name="footer">
				<rich:datascroller for="edtCronogramaCredito" maxPages="20" />
			</f:facet>
		</rich:extendedDataTable>
		--%>
	</h:panelGrid>