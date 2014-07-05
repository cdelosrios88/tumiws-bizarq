<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<rich:panel style="width: 780px">
			<f:facet name="header">
				<h:outputText value = "Mantenimiento de Rol de Usuario" style="text-align:left"> </h:outputText>
				
			</f:facet>

		<h:form id="frmRoles">
			<h:panelGrid columns="2" id="formulario">
				<h:outputText value="Id: " styleClass="spacio"/>
				<h:inputText value="#{rolController.bean.id}" readonly="true" size="6" style="background-color: #F2F2F2;"/>
				
				<h:outputText value="Código: " styleClass="spacio"/>
				<h:inputText value="#{rolController.bean.nombre}" size="40" maxlength="45" id="nombre"/>

				<h:outputText value="Descripcion: " styleClass="spacio"/>
				<h:inputTextarea value="#{rolController.bean.descripcion}" rows="3" 
					cols="40" id="Descripcion">
					<f:validateLength minimum="0" maximum="120" />
				</h:inputTextarea>
						
				<h:outputText value="Sistema: " styleClass="spacio"/>
				<h:selectOneMenu value="#{rolController.sistemaElegido}" id="somSistema" >
					<f:selectItems value="#{rolController.listaSistema}" />
				</h:selectOneMenu>

				<h:outputText value="Estado: " styleClass="spacio"/>
				<h:selectOneMenu value="#{rolController.bean.estado}" id="somEstado">
					<f:selectItem itemValue="O" itemLabel="--SELECCIONAR--" />
					<f:selectItem itemValue="A" itemLabel="Activo" />
					<f:selectItem itemValue="I" itemLabel="Inactivo" />
				</h:selectOneMenu>
			</h:panelGrid>
			<h:outputText value="." style="color:white;font-size: 5px;"/>
			
				<h:panelGrid columns="2">
					<a4j:commandButton value=" Guardar " action="rol"
						actionListener="#{rolController.save}" reRender="datable" id="btnGuardar"/>
					<a4j:commandButton value=" Limpiar " action="rol"
						actionListener="#{rolController.clean}" reRender="formulario" id="btnLimpiar"/>
				</h:panelGrid>
			
			<br/>				
			<h:panelGrid columns="1" id="datable">
				<rich:datascroller for="mdr1" maxPages="10" rendered="#{not empty rolController.beanList}" />
				<rich:dataTable id="mdr1" value="#{rolController.beanList}"
					var="item" rows="#{rolController.rows}" width="100%" dir="LTR" frame="hsides"
					rules="all" rowKeyVar="rowKey" rendered="#{not empty rolController.beanList}">

					<f:facet name="header">
						<h:outputText value="Listado de Roles" />
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
						<h:outputText value="#{item.sistemaDescripcion}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Nombre" />
						</f:facet>
						<h:outputText value="#{item.nombre}"></h:outputText>
					</rich:column>

					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Descripcion" />
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
						<h:commandLink action="rol"
							actionListener="#{rolController.load}" immediate="true">
							<h:graphicImage value="/images/icons/edit2_20.png"
								style="border:0px;" title="Cargar datos del Rol"/>
							<f:param name="sid" id="sid1" value="#{item.id}" />
						</h:commandLink>

						<h:commandLink action="rol" onclick="return confirmDelete();"
							actionListener="#{rolController.delete}" immediate="true">
							<h:graphicImage value="/images/icons/remove_20.png"
								style="border:0px;" title="Eliminar Rol"/>
							<f:param name="sid" id="sid2" value="#{item.id}" />
						</h:commandLink>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Menús" />
						</f:facet>
						<h:commandLink action="rol_opcion" 
							actionListener="#{rolController.loadOpcionesMenu}" immediate="true">
							<h:graphicImage value="/images/icons/add_20.png"
								style="border:0px;" title="Asignar Menús al Rol"/>
							<f:param name="sid" id="sid3" value="#{item.id}" />
						</h:commandLink>
					</rich:column>

					<f:facet name="footer">
						<h:outputText value="Se listan #{rolController.rows} filas por página" />
					</f:facet>
				</rich:dataTable>
			</h:panelGrid>

		</h:form>
</rich:panel>