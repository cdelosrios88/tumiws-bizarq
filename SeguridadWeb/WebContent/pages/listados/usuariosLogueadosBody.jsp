<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<h:form id="frmUsrLog">
		<br/>
		<h:panelGrid columns="2">
			<h:outputText value="Sistema: " styleClass="spacio"/>
			<h:selectOneMenu value="#{usuarioLogListadoController.sistemaElegido}" id="somListaSistema2" style="width:148px">
				<f:selectItems value="#{usuarioLogListadoController.listaSistema}" />
			</h:selectOneMenu>
			<h:outputText id="otDel" value="Del:" styleClass="spacio"/>
			<a4j:outputPanel id="calendarFI" layout="block">
					<rich:calendar
					value="#{usuarioLogListadoController.fechaInicio}"
					locale="es/ES" popup="true" datePattern="dd/MM/yyyy"
					showApplyButton="false" cellWidth="24px" cellHeight="22px"
					style="width:200px" id="idFechaInicio" reRender="datatable" />
			</a4j:outputPanel>
			<h:outputText id="otAl" value="Al:" styleClass="spacio"/>
			<a4j:outputPanel id="calendarFF" layout="block">
				<rich:calendar value="#{usuarioLogListadoController.fechaFin}"
				locale="es/ES" popup="true" datePattern="dd/MM/yyyy"
				showApplyButton="false" cellWidth="24px" cellHeight="22px"
				style="width:200px" id="idFechaFin" reRender="datatable" />
			</a4j:outputPanel>
			<a4j:commandButton action="#" value=" Listar "
				actionListener="#{usuarioLogListadoController.listarUsuariosLogueados}" reRender="pgDatatable2"/>
			<h:outputText value="_" style="color:white;"/>	
		</h:panelGrid>

		<h:panelGrid columns="1" id="pgDatatable2">
				<h:outputText value="_" style="color:white;"/>	
				<rich:datascroller for="rdt1" maxPages="15" rendered="#{not empty usuarioLogListadoController.listaUsuarioLog}"/>
				<rich:dataTable id="rdt1" value="#{usuarioLogListadoController.listaUsuarioLog}" var="item" rows="#{usuarioLogListadoController.rows}" width="100%" 
				dir="LTR" frame="hsides" rules="all" rowKeyVar="rowKey" rendered="#{not empty usuarioLogListadoController.listaUsuarioLog}">

					<f:facet name="header">
						<h:outputText value="Listado de Usuarios Logueados" />
					</f:facet>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="#" />
						</f:facet>
						<h:outputText value="#{rowKey+1}"></h:outputText>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Sistema" />
						</f:facet>
							<h:outputText value="#{item.SISTEMA}"></h:outputText>
					</rich:column>					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Codigo" />
						</f:facet>
							<h:outputText value="#{item.CODG}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Nombres y Apellidos" />
						</f:facet>
						<h:outputText value="#{item.NOMBR} #{item.APEPA} #{item.APEMA}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Fecha-Hora de Ingreso" />
						</f:facet>
						<h:outputText value="#{item.FECHA}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Estacion" />
						</f:facet>
						<h:outputText value="#{item.HOSTNAME} / #{item.IP}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Tipo de Ingreso" />
						</f:facet>
						<h:outputText value="#{item.TIPO}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Estado" />
						</f:facet>
						<h:outputText value="#{item.ESTD}"></h:outputText>
					</rich:column>					
					<f:facet name="footer">
						<h:outputText value="Se listan #{usuarioLogListadoController.rows} filas por página" />
					</f:facet>
				</rich:dataTable>
		</h:panelGrid>
	</h:form>
