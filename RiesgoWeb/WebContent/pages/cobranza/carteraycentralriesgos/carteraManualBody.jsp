<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA EL TUMI                  -->
<!-- AUTOR     : GUTENBERG A. TORRES BROUSSET PACHECO -->
<!-- MODULO    : RIESGO                               -->
<!-- PROTOTIPO : BODY                                 -->
<!-- FECHA     : 20.03.2014                           -->

<rich:modalPanel id="mdpAsientoCartera" width="360" height="520"
	resizeable="false" style="background-color: #DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Asiento Contable"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="mdpAsientoCartera" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmAsientoCartera">
		<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="2">
				<rich:column style="width:80px;border:none;">
					<h:outputText value="Periodo: " />
				</rich:column>
				<rich:column style="width:80px;border:none;">
					<h:inputText
						value="#{carteraManualController.intMesAsiento == 1 ? 'Enero' :
							 		carteraManualController.intMesAsiento == 2 ? 'Febrero' :
							 		carteraManualController.intMesAsiento == 3 ? 'Marzo' :
							 		carteraManualController.intMesAsiento == 4 ? 'Abril' :
							 		carteraManualController.intMesAsiento == 5 ? 'Mayo' :
							 		carteraManualController.intMesAsiento == 6 ? 'Junio' :
							 		carteraManualController.intMesAsiento == 7 ? 'Julio' :
							 		carteraManualController.intMesAsiento == 8 ? 'Agosto' :
							 		carteraManualController.intMesAsiento == 9 ? 'Setiembre' :
							 		carteraManualController.intMesAsiento == 10 ? 'Octubre' :
							 		carteraManualController.intMesAsiento == 11 ? 'Noviembre' :
							  		'Diciembre'} - #{carteraManualController.intAnioAsiento}"
						disabled="true" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2">
				<rich:column style="width:80px;border:none;">
					<h:outputText value="Cód. Libro: " />
				</rich:column>
				<rich:column style="width:80px;border:none;">
					<h:inputText value="#{carteraManualController.intCodLibroAsiento}"
						disabled="true" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<rich:extendedDataTable
				value="#{carteraManualController.listaAsientoCartera}" var="lib"
				id="edtLibroCartera" style="height:200px; width:300px;" 
				selectionMode="none" noDataLabel="No existen registros">
				<rich:column style="text-align:left">
					<f:facet name="header">
						<h:outputText value="Cuenta" />
					</f:facet>
					<h:outputText value="#{lib.strContNumeroCuenta}"
						style="padding-left:10px;" />
				</rich:column>
				<rich:column style="text-align:right">
					<f:facet name="header">
						<h:outputText value="Debe" />
					</f:facet>
					<h:outputText value="#{lib.bdDebeSoles}"
						style="padding-right:12px;" />
				</rich:column>
				<rich:column style="text-align:right">
					<f:facet name="header">
						<h:outputText value="Haber" />
					</f:facet>
					<h:outputText value="#{lib.bdHaberSoles}"
						style="padding-right:12px;" />
				</rich:column>
			</rich:extendedDataTable>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="mdpConfirmaGenerar" width="400" height="120"
	resizeable="false" style="background-color: #DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Confirmación: Generar de Cartera de Crédito" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="mdpConfirmaGenerar" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmConfirmaGenerar">
		<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="1">
				<rich:column style="border:none">
					<h:outputText value="¿Desea Generar la Cartera de Crédito?" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2" style="margin: 0 auto;">
				<rich:column style="border:none" id="colBtnModificar">
					<a4j:commandButton value="Sí" styleClass="btnEstilos"
						action="#{carteraManualController.generar}"
						onclick="Richfaces.hideModalPanel('mdpConfirmaGenerar')"
						reRender="pngCarteraManual4,pngCarteraManual5" />
				</rich:column>
				<rich:column>
					<a4j:commandButton value="No" styleClass="btnEstilos"
						onclick="Richfaces.hideModalPanel('mdpConfirmaGenerar')"
						reRender="pngCarteraManual4,pngCarteraManual5" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="mdpConfirmaCerrar" width="400" height="120"
	resizeable="false" style="background-color: #DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Confirmación: Cerrar de Cartera de Crédito"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="mdpConfirmaCerrar" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<h:form id="frmConfirmaCerrar">
		<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">
			<h:panelGrid columns="1">
				<rich:column style="border:none">
					<h:outputText value="¿Desea Cerrar la Cartera de Crédito?" />
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="12px" />
			<h:panelGrid columns="2" style="margin: 0 auto;">
				<rich:column style="border:none" id="colBtnModificar">
					<a4j:commandButton value="Sí" styleClass="btnEstilos"
						action="#{carteraManualController.cerrar}"
						onclick="Richfaces.hideModalPanel('mdpConfirmaCerrar')"
						reRender="pngCarteraManual2,pngCarteraManual4,pngCarteraManual5" />
				</rich:column>
				<rich:column>
					<a4j:commandButton value="No" styleClass="btnEstilos"
						onclick="Richfaces.hideModalPanel('mdpConfirmaCerrar')"
						reRender="pngCarteraManual4,pngCarteraManual5" />
				</rich:column>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<h:form>
	<h:outputLabel value="#{carteraManualController.inicioPage}" />
	<rich:tabPanel style="margin:15px; width:970px">
		<rich:tab label="Cartera de Crédito">
			<a4j:include id="idIncludeTabCarteraManual"
				viewId="/pages/cobranza/carteraycentralriesgos/tabs/tabCarteraManual.jsp" />
		</rich:tab>
		<rich:tab label="Infocorp">
			<a4j:include id="idIncludeTabInfocorp"
				viewId="/pages/cobranza/carteraycentralriesgos/tabs/tabInfocorp.jsp" />
		</rich:tab>
		<rich:tab label="Terceros - Minsa">
			<a4j:include id="idIncludeTabTercerosMinsa"
				viewId="/pages/cobranza/carteraycentralriesgos/tabs/tabTerceroMinsa.jsp" />
		</rich:tab>
	</rich:tabPanel>
</h:form>