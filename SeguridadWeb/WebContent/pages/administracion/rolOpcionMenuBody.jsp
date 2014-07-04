<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<h:form id="frmOpcionesxRol">
			<h:outputLabel value="." style="color:white;"/>
			<rich:panel style="width: 250px;">
				<f:facet name="header">
					<h:outputText id="otHeader" value="Rol - Opcion Menu"/>
				</f:facet>
				<h:panelGrid columns="3" style="color: gray; text-align: left;">
					<h:outputText value="Nombre" style="font-weight:bold"/>
					<h:outputText value="_" style="color: white;"/>
					<h:outputText value="#{rolController.bean.nombre}"/>
					<h:outputText value="Descripcion" style="font-weight:bold"/>
					<h:outputText value="_" style="color: white;"/>
					<h:outputText value="#{rolController.bean.descripcion}"></h:outputText>
					<h:outputText value="Estado" style="font-weight:bold"/>
					<h:outputText value="_" style="color: white;"/>
					<c:if test="${sessionScope.rolController.bean.estado eq 'A' }">
						<h:outputText value="Activo"/>
					</c:if>
					<c:if test="${sessionScope.rolController.bean.estado eq 'I' }">
						<h:outputText value="Inactivo"/>
					</c:if>
				</h:panelGrid>				
			</rich:panel>
			<h:outputLabel value="_" style="color:white;"/>
			<h:panelGrid columns="2">
				<a4j:commandButton value=" Guardar " action="rol_opcion" actionListener="#{rolController.update}"
					reRender="opcionMenuPanel">
					<f:param name="sid1" id="sid1" value="#{rolController.bean.id}" />
				</a4j:commandButton>
				<a4j:commandButton value=" Cancelar " action="rol"  actionListener="#{rolController.clean}"   />
			</h:panelGrid>
			<h:outputText value="_" style="color:white;"/>
			<h:panelGrid columns="5">
				<h:outputText value="Opciones de Menú Disponibles" style="font-weight:bold;text-align: center; width: 180px;"/>
				<h:outputText value="_" style="color:white;"/>
				<h:outputText value="_" style="color:white;"/>
				<h:outputText value="_" style="color:white;"/>
				<h:outputText value="Opciones de Menú Seleccionadas" style="font-weight:bold;text-align: center; width: 180px;"/>
				<h:selectManyListbox id="smListaMenuDisponible" value="#{rolController.menuAgregar}" style="height: 150px; width: 180px;">
						<f:selectItems value="#{rolController.listaOpcionDisponible}" />
				</h:selectManyListbox>
				<h:outputText value="_" style="color:white;"/>
				<h:panelGrid columns="1" style="align: center;">
					<h:commandButton value=' <- Remove '  action="#" actionListener="#{rolController.eliminar}" styleClass="boton" style="width:65px;"/>
					<h:commandButton value=" -> Copy " action="#" actionListener="#{rolController.agregar}" styleClass="boton" style="width:65px;"/>
				</h:panelGrid>
				<h:outputText value="_" style="color:white;"/>
				<h:selectManyListbox  id="smListaRolMenu" value="#{rolController.menuEliminar}" style="height: 150px; width: 180px;">
						<f:selectItems value="#{rolController.listaOpcionRol}" />
				</h:selectManyListbox>
			</h:panelGrid>

		</h:form>