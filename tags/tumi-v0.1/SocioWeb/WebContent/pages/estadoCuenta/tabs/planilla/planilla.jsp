<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - RESUMEN PLANILLA -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formTabPlanillaResumen" border="0" rendered="#{estadoCuentaController.blnShowPanelTabPlanillaResumen}">
	<rich:dataTable value="#{estadoCuentaController.listaEnvEfecPlanilla}" 
					styleClass="dataTable1" id="dtPlanillaResumen"
					rowKeyVar="rowKey" sortMode="single" width="1250px"
					var="itemGrilla">	
        <f:facet name="header">
            <rich:columnGroup  columnClasses="rich-sdt-header-cell">
                <rich:column rowspan="2">
					<b><h:outputText value="Periodo" /></b>
                </rich:column>
                <rich:column colspan="3">
                    <h:outputText value="Total Enviado" />
                </rich:column>
                <rich:column rowspan="2">
                    <h:outputText value="TOTAL" />
                </rich:column>
				<rich:column colspan="3">
                    <h:outputText value="Total Efectuado" />
                </rich:column>
                <rich:column rowspan="2">
                    <h:outputText value="TOTAL" />
                </rich:column>
				<rich:column rowspan="2">
					<b><h:outputText value="Diferencia Planilla" /></b>
                </rich:column>
                <rich:column colspan="3">
					<b><h:outputText value="Estado de Pago Dependencia" /></b>
                </rich:column>
                <rich:column rowspan="2">
					<b><h:outputText value="Cargos" /></b>
                </rich:column>
                <rich:column rowspan="2">
					<b><h:outputText value="Abonos" /></b>
                </rich:column>
                <rich:column rowspan="2">
					<b><h:outputText value="Diferencia" /></b>
                </rich:column>
                <rich:column breakBefore="true">
                    <h:outputText value="Hab." />
                </rich:column>
                <rich:column>
                    <h:outputText value="Incent." />
                </rich:column>
                <rich:column>
                    <h:outputText value="CAS" />
                </rich:column>
                <rich:column>
                    <h:outputText value="Hab." />
                </rich:column>
                <rich:column>
                    <h:outputText value="Incent." />
                </rich:column>
                <rich:column>
                    <h:outputText value="CAS" />
                </rich:column>
                <rich:column>
                    <h:outputText value="Hab." />
                </rich:column>
                <rich:column>
                    <h:outputText value="Incent." />
                </rich:column>
                <rich:column>
                    <h:outputText value="CAS" />
                </rich:column>
            </rich:columnGroup>
        </f:facet>
        <rich:column width="110px" style="text-align: center">
			<h:outputText value="#{itemGrilla.strPeriodoConFormato}"/>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.bdMontoenvioHaberes}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.bdMontoenvioIncentivos}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.bdMontoenvioCas}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.bdSumEnviomonto}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.efectuado.bdMontoEfectuadoHaberes}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.efectuado.bdMontoEfectuadoIncentivos}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.efectuado.bdMontoEfectuadoCas}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.bdSumEfectuado}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="80px" style="text-align: center">
			<h:outputText value="#{itemGrilla.bdSumaDifMontoConcepto}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="60px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.strCobroPlanillaHaberes}"/>
		</rich:column>
		<rich:column width="60px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.strCobroPlanillaIncentivos}"/>
		</rich:column>
		<rich:column width="60px" style="text-align: center">
			<h:outputText value="#{itemGrilla.enviomonto.strCobroPlanillaCas}"/>
		</rich:column>						
		<rich:column width="80px" style="text-align: center">
		</rich:column>
		<rich:column width="80px" style="text-align: center">
		</rich:column>
		<rich:column width="80px" style="text-align: center">
		</rich:column>
		<f:facet name="footer">     
        	<h:panelGroup layout="block">
		   		 <rich:datascroller for="dtPlanillaResumen" maxPages="10"/>
		   	</h:panelGroup>
	   	</f:facet>
	</rich:dataTable>
	<rich:spacer height="15"></rich:spacer>
	<a4j:commandButton id="btnDiferenciaPlanilla" value="Diferencia Planilla" styleClass="btnEstilos1" action="#{estadoCuentaController.obtenerDatosTabPlanillaDiferenciaPlanila}" 
					 reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda,formTabPlanilla,formTabPlanillaResumen,formTabPlanillaDiferencia"/> 
</h:panelGrid>
