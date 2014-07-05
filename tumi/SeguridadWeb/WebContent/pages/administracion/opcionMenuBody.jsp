<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<rich:panel style="width: 780px">
			<f:facet name="header">
				<h:outputText value = "Mantenimiento de Opciones de Menú" style="text-align:left"> </h:outputText>
				
			</f:facet>

		<h:form id="frmOpcionesMenu">
			
			<h:panelGrid columns="2" id="formulario">
				<h:outputText value="Id: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.id}" readonly="true" size="6" 
				style="background-color: #F2F2F2;" id="id"/>
				
				<h:outputText value="Codigo: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.codigo}" maxlength="30" size="30"
					id="codigo"/>

				<h:outputText value="Nombre: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.nombre}" size="30" maxlength="30"
					id="nombre"/>

				<h:outputText value="Descripcion: " styleClass="spacio"/>
				<h:inputTextarea value="#{opcionController.bean.descripcion}" rows="3" 
						cols="40" id="Descripcion">
					<f:validateLength minimum="0" maximum="120" />
				</h:inputTextarea>
				
				<h:outputText value="Id. Superior: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.superior}" size="6" maxlength="10"
					id="idSuperior"/>

				<h:outputText value="Controller: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.controller}" size="30" maxlength="30"
					id="idController">
				</h:inputText>
				
				<h:outputText value="Regla de Navegabilidad: " styleClass="spacio"/>
				<h:inputText value="#{opcionController.bean.navigationRule}" size="30" maxlength="30"
					id="idNavRule">
				</h:inputText>
				
				<h:outputText value="Estado: " styleClass="spacio"/>
				<h:selectOneMenu value="#{opcionController.bean.estado}">
					<f:selectItem itemValue="O" itemLabel="--Seleccionar--" />
					<f:selectItem itemValue="A" itemLabel="Activo" />
					<f:selectItem itemValue="I" itemLabel="Inactivo" />
				</h:selectOneMenu>
				<h:outputText value="." style="color:white;font-size: 5px;"/>
			</h:panelGrid>

			<h:panelGrid columns="2">
				<a4j:commandButton value=" Guardar " action="opcionMenu"
					actionListener="#{opcionController.save}" reRender="datatable" />
				<a4j:commandButton value=" Limpiar " action="opcionMenu"
					actionListener="#{opcionController.clean}" reRender="formulario" />
			</h:panelGrid>
			
			<br/>
			<h:panelGrid columns="1" id="datatable">
				<rich:datascroller for="rdt1" maxPages="10" rendered="#{not empty opcionController.beanList}"/>
				<rich:dataTable id="rdt1" value="#{opcionController.beanList}" rowKeyVar="rowKey"
					var="item" rows="#{opcionController.rows}" width="100%" dir="LTR" frame="hsides" styleClass="datatable"
					rules="all" rendered="#{not empty opcionController.beanList}">

					<f:facet name="header">
						<h:outputText value="Listado de Opciones de Menu" />
					</f:facet>

					<rich:column>
						<f:facet name="header">
							<h:outputText value="#" />
						</f:facet>
						<h:outputText value="#{rowKey+1}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Codigo" />
						</f:facet>
						<h:outputText value="#{item.codigo}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Nombre" />
						</f:facet>
						<h:outputText value="#{item.nombre}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Descripción" />
						</f:facet>
						<h:outputText value="#{item.descripcion}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Id. Superior" />
						</f:facet>
						<h:outputText value="#{item.superior}"></h:outputText>
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
						<h:commandLink action="opcionMenu"
							actionListener="#{opcionController.load}" immediate="true">
							<h:graphicImage value="/images/icons/edit2_20.png"
								style="border:0px" title="Cargar datos del Menú"/>
							<f:param name="sid" id="sid1" value="#{item.id}" />
						</h:commandLink>
						<h:commandLink action="opcionMenu" onclick="return confirmDelete();"
							actionListener="#{opcionController.delete}" immediate="true">
							<h:graphicImage value="/images/icons/remove_20.png"
								style="border:0px;" title="Eliminar Menú"/>
							<f:param name="sid" id="sid2" value="#{item.id}" />
						</h:commandLink>
					</rich:column>
					
					<f:facet name="footer">
						<h:outputText value="Se listan #{opcionController.rows} filas por página" />
					</f:facet>
				</rich:dataTable>
			</h:panelGrid>
		</h:form>
</rich:panel>