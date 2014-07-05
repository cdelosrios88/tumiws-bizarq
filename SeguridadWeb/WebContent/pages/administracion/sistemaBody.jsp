<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

		<h:form id="frmSistemas">
			<h:panelGrid columns="2" id="formulario">
				<h:outputText value="Id: " styleClass="spacio"/>
				<h:inputText value="#{sistemaController.bean.id}" readonly="true" size="6" style="background-color: #F2F2F2;"/>
				
				<h:outputText value="Codigo: " styleClass="spacio"/>
				<h:inputText value="#{sistemaController.bean.codigo}" size="30" maxlength="30" id="codigo"/>

				<h:outputText value="Descripción: " styleClass="spacio"/>
				<h:inputTextarea value="#{sistemaController.bean.descripcion}" rows="3" 
					cols="40" id="Descripcion" />
						
				<h:outputText value="Estado: " styleClass="spacio"/>
				<h:selectOneMenu value="#{sistemaController.bean.estado}" id="somEstado">
					<f:selectItem itemValue="O" itemLabel="--SELECCIONAR--" />
					<f:selectItem itemValue="A" itemLabel="Activo" />
					<f:selectItem itemValue="I" itemLabel="Inactivo" />
				</h:selectOneMenu>
				<h:outputText value="." style="color:white;font-size: 5px;"/>						

			</h:panelGrid>
				<h:panelGrid columns="2">
					<a4j:commandButton value=" Guardar " action="sistema" 
						actionListener="#{sistemaController.save}" reRender="datable" id="btnGuardar"/>
					<a4j:commandButton value=" Limpiar " action="sistema" 
						actionListener="#{sistemaController.clean}" reRender="frmSistemas,formulario" id="btnLimpiar"/>
				</h:panelGrid>

			<br/>
			<h:panelGrid columns="1" id="datable">
				<rich:datascroller for="mdr1" maxPages="10" rendered="#{not empty sistemaController.beanList}" />
				<rich:dataTable id="mdr1" value="#{sistemaController.beanList}"
					var="item" rows="#{sistemaController.rows}" width="100%" dir="LTR" frame="hsides"
					rules="all" rowKeyVar="rowKey" rendered="#{not empty sistemaController.beanList}">

					<f:facet name="header">
						<h:outputText value="Listado de Sistemas" />
					</f:facet>

					<rich:column>
						<f:facet name="header">
							<h:outputText value="#" />
						</f:facet>
						<h:outputText value="#{rowKey+1}"></h:outputText>
					</rich:column>

					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Código" />
						</f:facet>
						<h:outputText value="#{item.codigo}"></h:outputText>
					</rich:column>

					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Descripción" />
						</f:facet>
						<h:outputText value="#{item.descripcion}"></h:outputText>
					</rich:column>

					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Estado" />
						</f:facet>
						<h:outputText value="Activo" rendered="#{item.estado eq 'A'}"></h:outputText>
						<h:outputText value="Inactivo" rendered="#{item.estado eq 'I'}"></h:outputText>
					</rich:column>

					<rich:column>
						<f:facet name="header">
							<h:outputText value="Acciones" />
						</f:facet>
						<h:commandLink action="sistema"
							actionListener="#{sistemaController.load}" immediate="true">
							<h:graphicImage value="/images/icons/edit2_20.png"
								style="border:0px" title="Cargar datos del Sistema"/>
							<f:param name="sid" id="sid1" value="#{item.id}" />
						</h:commandLink>

						<h:commandLink action="sistema" onclick="return confirmDelete();"
							actionListener="#{sistemaController.delete}" immediate="true">
							<h:graphicImage value="/images/icons/remove_20.png"
								style="border:0px;" title="Eliminar Sistema"/>
							<f:param name="sid" id="sid2" value="#{item.id}" />
						</h:commandLink>
					</rich:column>
										
					<f:facet name="footer">
						<h:outputText value="Se listan #{sistemaController.rows} filas por página" />
					</f:facet>
				</rich:dataTable>
			</h:panelGrid>
		</h:form>