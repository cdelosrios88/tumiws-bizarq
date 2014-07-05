<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - TERCEROS -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGroup layout="block" style="padding:15px">
	<h:panelGrid id="formDsctoTrosHaberes" rendered="#{estadoCuentaController.blnShowPanelTabTercerosHaberes}">
		<rich:columnGroup>
			<rich:column width="1150px" style="text-align: left">
				<b><h:outputText value="PLANILLA DE HABERES"/></b>
			</rich:column>
		</rich:columnGroup>
		<rich:dataTable value="#{estadoCuentaController.listaMontoDsctoTrosHaberes}" styleClass="dataTable1" id="dtMontoDsctoTrosHaberes"
						rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
						var="itemGrilla">
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Periodo"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strDstePeriodo}"/>
			</rich:column>
			<rich:columns value="#{estadoCuentaController.listaNvoColumnasHaberes == null ? '':estadoCuentaController.listaNvoColumnasHaberes}" var="columns" index="ind" style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="#{columns.strNomCpto}"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
			</rich:columns>
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Total"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
			</rich:column>
			<f:facet name="footer">
				<h:panelGroup layout="block">
                      	<rich:datascroller for="dtMontoDsctoTrosHaberes" maxPages="10"/>
                      </h:panelGroup>
                     </f:facet>						
		</rich:dataTable>
	</h:panelGrid>
	<!-- ################################################################################################################# -->
	<rich:spacer width="15"></rich:spacer>
	<h:panelGrid id="formDsctoTrosIncentivos" rendered="#{estadoCuentaController.blnShowPanelTabTercerosIncentivos}">
		<rich:columnGroup>
			<rich:column width="1150px" style="text-align: left">
				<b><h:outputText value="PLANILLA DE INCENTIVOS"/></b>
			</rich:column>
		</rich:columnGroup>
		<rich:dataTable value="#{estadoCuentaController.listaMontoDsctoTrosIncentivos}" styleClass="dataTable1" id="dtMontoDsctoTrosIncentivos"
						rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
						var="itemGrilla">
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Periodo"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strDstePeriodo}"/>
			</rich:column>
			<rich:columns value="#{estadoCuentaController.listaNvoColumnasIncentivos == null ? '':estadoCuentaController.listaNvoColumnasIncentivos}" var="columns" index="ind" style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="#{columns.strNomCpto}"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
			</rich:columns>
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Total"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
			</rich:column>
			<f:facet name="footer">
				<h:panelGroup layout="block">
                      	<rich:datascroller for="dtMontoDsctoTrosIncentivos" maxPages="10"/>
                      </h:panelGroup>
                     </f:facet>	
		</rich:dataTable>
	</h:panelGrid>
	<!-- ################################################################################################################# -->
	<rich:spacer width="15"></rich:spacer>
	<h:panelGrid id="formDsctoTrosCas" rendered="#{estadoCuentaController.blnShowPanelTabTercerosCas}">
		<rich:columnGroup>
			<rich:column width="1150px" style="text-align: left">
				<b><h:outputText value="PLANILLA DE CAS"/></b>
			</rich:column>
		</rich:columnGroup>
		<rich:dataTable value="#{estadoCuentaController.listaMontoDsctoTrosCas}" styleClass="dataTable1" id="dtMontoDsctoTrosCas"
						rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
						var="itemGrilla">
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Periodo"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strDstePeriodo}"/>
			</rich:column>
			<rich:columns value="#{estadoCuentaController.listaNvoColumnasCas == null ? '':estadoCuentaController.listaNvoColumnasCas}" var="columns" index="ind" style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="#{columns.strNomCpto}"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.strMontoDscto[ind]}"/>
			</rich:columns>
			<rich:column style="text-align: center">
				<f:facet name="header">
					<b><h:outputText value="Total"/></b>
				</f:facet>
				<h:outputText value="#{itemGrilla.bdSumMontoDscto}"/>
			</rich:column>
			<f:facet name="footer">
				<h:panelGroup layout="block">
                      	<rich:datascroller for="dtMontoDsctoTrosCas" maxPages="10"/>
                      </h:panelGroup>
                     </f:facet>	
		</rich:dataTable>
	</h:panelGrid>	
</h:panelGroup>