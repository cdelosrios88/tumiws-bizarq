<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<h:form id="frmUsrPrfl">
		<br/>
		<h:panelGrid columns="2">
			<h:outputText value="Sistema: " styleClass="spacio"/>
			<h:panelGrid id="pgListar" columns="3">
				<h:selectOneMenu value="#{usuarioLogListadoController.sistemaElegido}" id="somListaSistema1">
					<f:selectItems value="#{usuarioLogListadoController.listaSistema}" />
				</h:selectOneMenu>
				<h:outputText value="_" style="color:white;"/>
				<a4j:commandButton action="#" value=" Listar "
					actionListener="#{usuarioLogListadoController.listarUsuariosPerfil}" reRender="pgDatatable3" />
			</h:panelGrid>
		</h:panelGrid>
		<h:panelGrid columns="1" id="pgDatatable3">
				<h:outputText value="_" style="color:white;"/>
				<rich:datascroller for="rdt1" maxPages="15" rendered="#{not empty usuarioLogListadoController.listaUsuarioPerfil}"/>
				<rich:dataTable id="rdt1" value="#{usuarioLogListadoController.listaUsuarioPerfil}" var="item" rows="#{usuarioLogListadoController.rows}" width="100%" 
				dir="LTR" frame="hsides" rules="all" rowKeyVar="rowKey" rendered="#{not empty usuarioLogListadoController.listaUsuarioPerfil}">

					<f:facet name="header">
						<h:outputText value="Listado de Usuarios por Perfil" />
					</f:facet>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="#" />
						</f:facet>
						<h:outputText value="#{rowKey+1}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Sistema" />
						</f:facet>
							<h:outputText value="#{item.SISTEMA}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Rol" />
						</f:facet>
							<h:outputText value="#{item.ROL}"></h:outputText>						
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