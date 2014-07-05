<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<rich:panel style="width: 780px">
			<f:facet name="header">
				<h:outputText value = "Mantenimiento de Parametros del Sistema" style="text-align:left"> </h:outputText>
				
			</f:facet>
		<h:form id="frmParametros">
			<h:panelGrid columns="2" id="pgParametros">
				<h:outputText value="Id: " styleClass="spacio"/>
				<h:inputText value="#{parametroController.bean.id}" readonly="true" size="6" style="background-color: #F2F2F2;" id="idParams"/>
				
				<h:outputText value="Codigo: " styleClass="spacio"/>
				<h:inputText value="#{parametroController.bean.codigo}" size="30" maxlength="60" id="codigo"/>
				
				<h:outputText value="Valor " styleClass="spacio"/>
				<h:inputTextarea value="#{parametroController.bean.valor}" rows="3" cols="40" id="valor">
						<f:validateLength minimum="1" maximum="255" />
				</h:inputTextarea>
				
				<h:outputText value="Descripcion " styleClass="spacio"/>
				<h:inputTextarea value="#{parametroController.bean.descripcion}" rows="3" cols="40" id="descripcion">
						<f:validateLength minimum="0" maximum="120" />
				</h:inputTextarea>
				
				<h:outputText value="Sistema: " styleClass="spacio"/>
				<h:selectOneMenu value="#{parametroController.sistemaElegido}" id="somSistema" >
					<f:selectItems value="#{parametroController.listaSistema}" />
				</h:selectOneMenu>			
				
				<h:outputText value="Estado: " styleClass="spacio"/>
				<h:selectOneMenu value="#{parametroController.bean.estado}" id="somEstado">
					<f:selectItem itemValue="O" itemLabel="--Seleccionar--" />
					<f:selectItem itemValue="A" itemLabel="Activo" />
					<f:selectItem itemValue="I" itemLabel="Inactivo" />
				</h:selectOneMenu>
				
				<h:outputText value="." style="color:white;font-size: 5px;"/>
			</h:panelGrid>

				<h:panelGrid columns="2">
					<a4j:commandButton value=" Guardar " action="parametro"
						actionListener="#{parametroController.save}" reRender="datatable" id="btnGuardar"/>
					<a4j:commandButton value=" Limpiar " action="parametro"
						actionListener="#{parametroController.clean}" reRender="pgParametros" id="btnLimpiar"/>
				</h:panelGrid>
			
			<br/>
			<h:panelGrid columns="1" id="datatable">
				<rich:datascroller for="rdt1" maxPages="10" rendered="#{not empty parametroController.beanList}"/>
				<rich:dataTable id="rdt1" value="#{parametroController.beanList}"
					var="item" rows="#{parametroController.rows}" width="100%" dir="LTR" frame="hsides" 
					rules="all" rowKeyVar="rowKey" rendered="#{not empty parametroController.beanList}">
					
					<f:facet name="header">
						<h:outputText value="Listado de Parámetros" />
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
							<h:outputText value="Valor" />
						</f:facet>
						<h:outputText value="#{item.valor}"></h:outputText>
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
						<h:commandLink action="parametro"
							actionListener="#{parametroController.load}" immediate="true">
							<h:graphicImage value="/images/icons/edit2_20.png"
								style="border:0px" title="Cargar datos del Parámetro"/>
							<f:param name="sid" id="sid1" value="#{item.id}" />
						</h:commandLink>
						<h:commandLink action="parametro" onclick="return confirmDelete();"
							actionListener="#{parametroController.delete}" immediate="true">
							<h:graphicImage value="/images/icons/remove_20.png"
								style="border:0px;" title="Eliminar Parámetro"/>
							<f:param name="sid" id="sid2" value="#{item.id}" />
						</h:commandLink>
					</rich:column>
					
					<f:facet name="footer">
						<h:outputText value="Se listan #{parametroController.rows} filas por página" />
					</f:facet>
					
				</rich:dataTable>

			</h:panelGrid>

		</h:form>
</rich:panel>