<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - DIFERENCIA PLANILLA -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formTabPlanillaDiferencia" border="0" rendered="#{estadoCuentaController.blnShowPanelTabPlanillaDiferencia}">
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="DIFERENCIA PLANILLA" /></b>
		</rich:column>
	</rich:columnGroup>
	<rich:dataTable value="#{estadoCuentaController.listaMontoPorPeriodoYPrioridad}" 
					styleClass="dataTable1" id="dtDiferenciaPlanilla"
					rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
					var="itemGrilla">
		<rich:column style="text-align: center">
			<f:facet name="header">
				<h:outputText value="Periodo"/>
			</f:facet>
			<h:outputText value="#{itemGrilla.strPeriodoPlanilla}"/>
		</rich:column>
		<rich:columns width="90px" value="#{estadoCuentaController.lstColumnasPrioridadDscto == null ? '':estadoCuentaController.lstColumnasPrioridadDscto}" var="columns" index="ind" style="text-align: center">
                          <f:facet name="header">
                              <h:outputText value="#{columns.intPrdeOrdenprioridad} - #{columns.strDescPrioridad}"/>
                          </f:facet>
				<h:outputText value="#{itemGrilla.lstBdMontoPorPeriodoYPrioridad[ind]}">
				<f:converter converterId="ConvertidorMontos"  /></h:outputText>
                 </rich:columns>
		<rich:column style="text-align: center">
			<f:facet name="header">
				<h:outputText value="Total de Diferencia"></h:outputText>
			</f:facet>
			<h:outputText value="#{itemGrilla.bdSumaDifMontoConcepto}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<f:facet name="footer">     
        	<h:panelGroup layout="block">
		   		 <rich:datascroller for="dtDiferenciaPlanilla" maxPages="10"/>
		   	</h:panelGroup>
	   	</f:facet>
	</rich:dataTable>
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<a4j:commandButton id="btnRetornarPlanilla" value="Volver" styleClass="btnEstilos1" action="#{estadoCuentaController.retornarTabPlanilla}" 
				 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabPlanilla,formTabPlanillaResumen,formTabPlanillaDiferencia"/>
		</rich:column>
	</rich:columnGroup>	 
</h:panelGrid>
