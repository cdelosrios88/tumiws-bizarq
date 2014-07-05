<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi         				-->
	<!-- Autor     : Christian De los Ríos    					-->
	<!-- Modulo    : Créditos - Garantías     					-->
	<!-- Prototipo : PROTOTIPO POPUP CAPACIDAD DE PAGOS - CAS	-->
	<!-- Fecha     : 16/07/2012               					-->
	
	<script type="text/javascript">
		
	</script>
  		<rich:panel id="pIncentivoProrrateo" rendered="#{capacidadPagoController.blnFormTipoIncentivoProrrateo}" style="border: 2px solid #17356f;background-color:#DEEBF5;width:900px;">
   			<h:panelGrid columns="2">
   				<rich:column>
   					<b><u>CONCEPTOS</u></b>
   				</rich:column>
   				<rich:column style="padding-left:600px">
   					<a4j:commandButton value="Verificar Terceros" styleClass="btnEstilos1" reRender="mpTercerosPrestamo"
							actionListener="#{solicitudPrestamoController.listarTercerosSolicitudPrestamo}"
							oncomplete="Richfaces.showModalPanel('mpTercerosPrestamo')"></a4j:commandButton>
   				</rich:column>
   			</h:panelGrid>
   			
   			<h:panelGrid columns="2">
   				<h:selectBooleanCheckbox value="#{capacidadPagoController.blnChkCartaAutorizacion}"/>Tiene Carta de Autorización (Descuento 100%)
   			</h:panelGrid>
   			
   			<h:panelGrid columns="4">
   				<rich:column width="220px">
   					<h:outputText value="A.- Total Ingresos" style="font-weight:bold;"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value=":"/>
   				</rich:column>
   				<rich:column>
   					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdTotalIngresos}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="15"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value="Dato de boleta (Ingreso Bruto), no considerar los conceptos aetas (O), (OT.INg.D),"/>
   				</rich:column>
   			</h:panelGrid>
   			
   			<h:panelGrid columns="4">
   				<rich:column width="220px">
   					<h:outputText value="B.- Número de Entidades" style="font-weight:bold;"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value=":"/>
   				</rich:column>
   				<rich:column>
   					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.intNroEntidades}" size="15"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value="Considerando la Cooperativa El Tumi"/>
   				</rich:column>
   			</h:panelGrid>
   			
   			<h:panelGrid columns="1">
   				<rich:column>
   					<a4j:commandButton value="Calcular" actionListener="#{capacidadPagoController.calcularCapacPagoIncentProrrateo}" styleClass="btnEstilos" reRender="pgCapacidadPago"/>
   				</rich:column>
   			</h:panelGrid>
   			
   			<h:panelGrid id="pgCapacidadPago" columns="4">
   				<rich:column width="220px">
   					<h:outputText value="Capacidad de Pago - Incentivos" style="font-weight:bold;"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value=":"/>
   				</rich:column>
   				<rich:column>
   					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdCapacidadPago}" size="15" disabled="true"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value="Producto ( A / B )"/>
   				</rich:column>
   			</h:panelGrid>
   			
   			<h:panelGrid columns="5">
   				<rich:column width="220px">
   					<h:outputText value="Cuota Fija" style="font-weight:bold;"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value=":"/>
   				</rich:column>
   				<rich:column>
   					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdCuotaFija}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" size="15"/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value="Monto a descontar por este tipo de planilla."/>
   				</rich:column>
   				<rich:column>
   					<h:outputText value="#{capacidadPagoController.msgTxtCuotaFija}" styleClass="msgError"/>
   				</rich:column>
   			</h:panelGrid>
   		</rich:panel>