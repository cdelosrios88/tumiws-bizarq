<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<rich:panel style="width: 780px">
			<f:facet name="header">
				<h:outputText value = "Mantenimiento de Usuarios del Sistema" style="text-align:left"> </h:outputText>
				
			</f:facet>
		<h:form id="frmUsuario">
			<h:panelGrid columns="2" id="usuarioPanel">
				<h:outputText value="Id: " styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.id}" readonly="true" size="6" style="background-color: #F2F2F2;" id="idUsr"/>
				
				<h:outputText value="Código de Usuario "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.codigo}" maxlength="30" size="30" id="codigo"/>
				
				<h:outputText value="Nombre: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.nombre}" size="40" maxlength="60" id="nombre"/>
				
				<h:outputText value="Apellido Paterno: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.apePa}" size="40" maxlength="60" id="apePa"/>

				<h:outputText value="Apellido Materno: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.apeMa}" size="40" maxlength="60" id="apeMa"/>

				<h:outputText value="Correo: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.correoElectronico}" size="40" maxlength="40" id="correo"/>
				
				<%---Modificado Jessica Tadeo 24/06/2011 --%>
				<h:outputText value="Area Gestora: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.areaGestora}" size="40" maxlength="60" id="areaGestora"/>
				
				<h:outputText value="Empresa: "  styleClass="spacio" />
				<h:inputText value="#{usuarioController.bean.empresa}" size="40" maxlength="60" id="empresa"/>
				
				<h:outputText value="Rol: "  styleClass="spacio" />
					<h:selectOneMenu value="#{usuarioController.rolElegido}" id="somRol" >
						<f:selectItems value="#{usuarioController.listaRol}" />
					</h:selectOneMenu>
				<%---Fin Modificado Jessica Tadeo 24/06/2011 --%>
								
				<h:outputText value="Estado: "  styleClass="spacio" />
				<h:selectOneMenu value="#{usuarioController.bean.estado}" id="somEstado">
					<f:selectItem itemValue="O" itemLabel="--SELECCIONAR--" />
					<f:selectItem itemValue="H" itemLabel="Habilitado" />
					<f:selectItem itemValue="B" itemLabel="Bloqueado" />
				</h:selectOneMenu>				
				<h:outputText value="." style="color:white;font-size: 5px;"/>						
			</h:panelGrid>
			<h:panelGrid columns="3">
				<a4j:commandButton value=" Guardar " action="usuario"
					actionListener="#{usuarioController.save}" reRender="datatable" id="btnGuardar"/>
				<a4j:commandButton value=" Limpiar " action="usuario"
					actionListener="#{usuarioController.clean}" reRender="frmUsuario,usuarioPanel" id="btnLimpiar"/>
			</h:panelGrid>
			<br/>

			<h:panelGrid columns="1" id="datatable">
				<rich:datascroller for="rdt1" maxPages="10" rendered="#{not empty usuarioController.beanList}"/>
				<rich:dataTable id="rdt1" value="#{usuarioController.beanList}" var="item" rows="#{usuarioController.rows}" width="100%" 
				dir="LTR" frame="hsides" rules="all" rowKeyVar="rowKey" rendered="#{not empty usuarioController.beanList}">

					<f:facet name="header" >
						<h:outputText value="Listado de Usuarios" />
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
							<h:outputText value="Nombres y Apellidos" />
						</f:facet>
						<h:outputText value="#{item.nombre} #{item.apePa} #{item.apeMa}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Correo" />
						</f:facet>
						<h:outputText value="#{item.correoElectronico}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Area Gestora" />
						</f:facet>
						<h:outputText value="#{item.areaGestora}"></h:outputText>
					</rich:column>
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Empresa" />
						</f:facet>
						<h:outputText value="#{item.empresa}"></h:outputText>
					</rich:column>
					
					<rich:column sortable="true">
						<f:facet name="header">
							<h:outputText value="Estado" />
						</f:facet>
						<h:outputText value="Habilitado" rendered="#{item.estado eq 'H'}"></h:outputText>
						<h:outputText value="Bloqueado" rendered="#{item.estado eq 'B'}"></h:outputText>
						<h:outputText value="Nuevo" rendered="#{item.estado eq 'N'}"></h:outputText>
						<h:outputText value="Inactivo" rendered="#{item.estado eq 'I'}"></h:outputText>
					</rich:column>

					<rich:column>
						<f:facet name="header">
							<h:outputText value="Acciones" />
						</f:facet>
						<h:commandLink action="usuario"
							actionListener="#{usuarioController.load}" immediate="true">
							<h:graphicImage value="/images/icons/edit2_20.png"
								style="border:0px" title="Cargar datos del Usuario"/>
							<f:param name="sid" id="sid1" value="#{item.id}" />
						</h:commandLink>
						<h:commandLink action="usuario" onclick="return confirmReiniciar();"
							actionListener="#{usuarioController.reiniciarContrasenha}" immediate="true">
							<h:graphicImage value="/images/icons/actualizar.png"
								style="border:0px;" title="Reiniciar Password"/>
							<f:param name="sid" id="sid2" value="#{item.id}" />
						</h:commandLink>
						<%--<h:commandLink action="usuario" onclick="return confirmDelete();"
							actionListener="#{usuarioController.delete}" immediate="true">
							<h:graphicImage value="/images/icons/remove_20.png"
								style="border:0px;" title="Eliminar Usuario"/>
							<f:param name="sid" id="sid3" value="#{item.id}" />
						</h:commandLink>--%>
					</rich:column>

					<%-- 
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Roles" />
						</f:facet>
						<h:commandLink action="usuario_rol"
							actionListener="#{usuarioController.loadRol}" immediate="true">
							<h:graphicImage value="/images/icons/add_20.png" style="border:0px;" title="Asignar Roles"/>
							<f:param name="sid" id="sid4" value="#{item.id}" />
						</h:commandLink>

					</rich:column>
					--%>

					<f:facet name="footer">
						<h:outputText value="Se listan #{usuarioController.rows} filas por página" />
					</f:facet>

				</rich:dataTable>


			</h:panelGrid>

		</h:form>
</rich:panel>