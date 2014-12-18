<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Cod. Req. : REQ14-000       			-->
<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Autor     : Bizarq			   			-->
<!-- Modulo    : Seguridad	     			-->
<!-- Prototipo : Reporte Fondos Fijos		-->
<!-- Fecha     : 14-12-2014     			-->

<h:panelGroup style="width:1000px; background-color:#DEEBF5;">
	<rich:panel style="margin-left: auto; margin-right: auto;width:900px;">
		<h:form id="frmPrincipal">
			<div style="text-align: center; font-size: 16px; font-weight: bold;">Movimientos
				de Fondos Fijos</div>
			<br />
			<h:inputHidden id="strIniPage" value="#{reporteFondosFijosController.strIniciar}" />
			<h:panelGrid columns="7">
				<rich:column style="width: 70px">
					<h:outputText value="Sucursal:" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="cboSucursal" style="width: 140px;"
						value="#{reporteFondosFijosController.intIdSucursal}"
						>
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel"
							value="#{reporteFondosFijosController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strRazonSocial}"
							propertySort="juridica.strRazonSocial" />
							<a4j:support event="onchange" reRender="cboAnio,cboTipoFondoFijo,cboIdFondoFijoReporte"
							actionListener="#{reporteFondosFijosController.seleccionarSucursasl}"
							ajaxSingle="true" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column>
					<h:outputText value="Año:" />
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="cboAnio" style="width: 70px;"
						value="#{reporteFondosFijosController.intYear}">
						<tumih:selectItems var="sel"
							value="#{reporteFondosFijosController.listYears}"
							itemValue="#{sel.label}" itemLabel="#{sel.label}" />
							<a4j:support event="onchange" reRender="cboTipoFondoFijo,cboIdFondoFijoReporte"
							actionListener="#{reporteFondosFijosController.seleccionarAnio}"
							ajaxSingle="true" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="width: 110px">
					<h:selectOneMenu id="cboTipoFondoFijo" style="width: 140px;"
						value="#{reporteFondosFijosController.intIdTipoFondoFijo}">
						<f:selectItem itemLabel="Tipo Fondo Fijo" itemValue="0" />
						<tumih:selectItems var="sel"
							value="#{reporteFondosFijosController.lstTipoFondoFijo}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden" />
						<a4j:support event="onchange" reRender="cboIdFondoFijoReporte"
							actionListener="#{reporteFondosFijosController.obtenerFondoFijo}"
							/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="width: 110px">
					<h:selectOneMenu id="cboIdFondoFijoReporte" style="width: 150px;"
					value="#{reporteFondosFijosController.intIdFondoFijo}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="-1" />
						<tumih:selectItems var="sel"
							value="#{reporteFondosFijosController.lstFondoFijo}"
							itemValue="#{sel.intRow}" itemLabel="#{sel.strDescripcion}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column style="width: 110px">
					<a4j:commandButton styleClass="btnEstilos" value="Detalle"
						reRender="" />
				</rich:column>
			</h:panelGrid>
			<br />
			<h:panelGrid id="pgEgresos" columns="1" border="0"
				style="text-align:center;">
				<rich:dataTable id="dtEgresos"
					value="#{reporteFondosFijosController.listaEgreso}" rows="5"
					var="item" rowKeyVar="rowKey" sortMode="single">
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Item" />
						</f:facet>
						<h:outputText value="#{rowKey + 1}"></h:outputText>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Nro Movimiento" />
						</f:facet>
						<h:outputText value="#{item.strNroMovimiento}"/>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Fecha" />
						</f:facet>
						<h:outputText value="#{item.dtFechaEgreso}"/>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Concepto" />
						</f:facet>
						<h:outputText value="#{item.strConcepto}"/>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Monto" />
						</f:facet>
						<h:outputText value="#{item.dblMontoReporte}"/>
					</rich:column>
				</rich:dataTable>
			</h:panelGrid>
			<br />
			<h:panelGrid columns="3" style="text-align:center;margin:auto;">
				<rich:column style="width: 110px">
					<a4j:commandButton styleClass="btnEstilos" value="Consultar"
						action="#{reporteFondosFijosController.consultarEgreso}"
						reRender="pgEgresos" />
				</rich:column>
				<rich:column style="width: 110px">
					<a4j:commandButton styleClass="btnEstilos" value="Reporte"
						reRender="" />
				</rich:column>
				<rich:column style="width: 110px">
					<a4j:commandButton styleClass="btnEstilos" value="Cancelar"
						reRender="" />
				</rich:column>
			</h:panelGrid>
		</h:form>
	</rich:panel>
</h:panelGroup>