<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:panel style="border:1px solid #17356f ;width: 760px; background-color:#DEEBF5">
	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		<h:panelGrid columns="6" style="width: 700px" border="0">
			<rich:column style="width: 60px; border: none">
				<h:outputText value="Empresa:" style="padding-left: 10px"/>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="idCboEmpresaPerm" style="width: 250px;"
					value="#{usuarioPerfilController.cboEmpresaPermiso}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
					<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="border:none;width:110px;">
				<h:outputText value="Tipo de Perfil:"/>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu value="#{usuarioPerfilController.cboTipoPerfilPermiso}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERFIL}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="border: none">
				<h:outputText value="Estado:"/>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu value="#{usuarioPerfilController.cboEstadoPerfilPermiso}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pgFiltrosPermisos" columns="5" border="0"
			style="width: 700px;">
			<rich:column style="width:70px; border:none">
				<h:outputText value="Usuario:" style="padding-left: 10px;"/>
			</rich:column>
			<rich:column style="border: none">
				<h:inputText size="40"
					value="#{usuarioPerfilController.txtUsuarioPermiso}"/>
			</rich:column>
			<rich:column style="width: 120px;border:none;">
				<h:outputText value="Rango de Fecha:"/>
			</rich:column>
			<rich:column style="border:none;">
				<rich:calendar popup="true" value="#{usuarioPerfilController.daFechaIniPermiso}"
					datePattern="dd/MM/yyyy" inputStyle="width:90px" cellWidth="10px" cellHeight="20px" />
			</rich:column>
			<rich:column style="border:none;">
				<rich:calendar popup="true" value="#{usuarioPerfilController.daFechaFinPermiso}"
					datePattern="dd/MM/yyyy" inputStyle="width:90px" cellWidth="10px" cellHeight="20px" />
			</rich:column>
		</h:panelGrid>
		<h:panelGrid>
			<a4j:commandButton value="Buscar" styleClass="btnEstilos"
				actionListener="#{usuarioPerfilController.listarPermisos}" reRender="pgFilPermiso" />
		</h:panelGrid>

		<rich:panel
			style="border: 0px solid #17356f;background-color:#DEEBF5;width:680px; ">
			<h:panelGrid id="pgFilPermiso" columns="1">
				<rich:scrollableDataTable id="tblFilPermiso"
					value="#{usuarioPerfilController.beanListPermisos}" var="item"
					sortMode="single"
					onRowClick="jsSeleccionPermiso(#{item.intIdPersona}, #{item.intIdEmpresa});"
					rendered="#{not empty usuarioPerfilController.beanListPermisos}"
					rowKeyVar="rowkey" width="700px" height="180px">
					<rich:column width="15px">
						<f:facet name="header">
							<h:outputText></h:outputText>
						</f:facet>
						<h:outputText value="#{rowkey+1}"/>
					</rich:column>
					<rich:column width="130px">
						<f:facet name="header"><h:outputText value="Empresa" /></f:facet>
						<h:outputText value="#{item.strEmpresa}" />
					</rich:column>
					<rich:column width="160px">
						<f:facet name="header"><h:outputText value="Nombre de Usuario" /></f:facet>
						<h:outputText value="#{item.strNombreCompleto}" />
					</rich:column>
					<rich:column width="80px">
						<f:facet name="header"><h:outputText value="Estado" /></f:facet>
						<h:outputText value="#{item.strEstado}"></h:outputText>
					</rich:column>

					<rich:column width="180px">
						<f:facet name="header"><h:outputText value="Perfil" /></f:facet>
						<h:outputText value="#{item.strDescPerfil}" />
					</rich:column>
					<rich:column width="110px">
						<f:facet name="header"><h:outputText value="Permisos" /></f:facet>
						<h:outputText value="-----"></h:outputText>
					</rich:column>
					<rich:column width="170px">
						<f:facet name="header"><h:outputText value="Rango de Fecha" /></f:facet>
						<h:outputText value="#{item.strRanFecha}"></h:outputText>
					</rich:column>

					<rich:column width="140px">
						<f:facet name="header"><h:outputText value="Fecha de Registro" /></f:facet>
						<h:outputText value="#{item.daFecRegistro}" />
					</rich:column>
				</rich:scrollableDataTable>

				<h:panelGrid columns="2" border="">
					<h:outputLink value="#" id="linkPanelPermiso">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
							style="border:0px" />
						<rich:componentControl for="panelUpdateDeletePermiso"
							attachTo="linkPanelPermiso" operation="show" event="onclick" />
					</h:outputLink>
					<h:outputText id="lblMsjPermiso"
						value="Para Eliminar o Actualizar un Permiso hacer click en el Registro"
						style="color: #4449a5"></h:outputText>
				</h:panelGrid>

			</h:panelGrid>
		</rich:panel>
	</rich:panel>

	<rich:spacer height="10px" />
	<rich:spacer height="10px" />

	<h:panelGrid columns="3">
		<h:commandButton value="Nuevo" actionListener="#{usuarioPerfilController.habilitarGrabarPermiso}"
			styleClass="btnEstilos" />
		<h:commandButton id="btnGuardarPermisos" value="Guardar" actionListener="#{usuarioPerfilController.grabarPermiso}"
			styleClass="btnEstilos" />
		<h:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarGrabarPermiso}"
			styleClass="btnEstilos" />
	</h:panelGrid>

	<rich:spacer height="4px" />
	<rich:spacer height="4px" />

	<rich:panel id="pMarco3"
		style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;"
		rendered="#{usuarioPerfilController.permisoRendered}">
		<h:panelGrid columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Empresa" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="cboEmpresasPermiso" disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{usuarioPerfilController.reloadCboUsuario}" style="width: 300px;"
					value="#{usuarioPerfilController.beanPermiso.intIdEmpresa}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
					<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}" />
					<a4j:support event="onchange" reRender="idCboUsuario" ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="width: 200px; border: none">
				<h:outputText value="#{usuarioPerfilController.msgTxtEmpresaPermiso}" styleClass="msgError"/>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Usuario" style="padding-left:10px"/>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="idCboUsuario"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{usuarioPerfilController.reloadCboPerfilUsuario}"
					style="width: 300px;"
					value="#{usuarioPerfilController.beanPermiso.intIdPersona}">
					<f:selectItems value="#{usuarioPerfilController.cboUsuario}"/>
					<a4j:support event="onchange" reRender="idCboPerfilUsuario" ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="border: none;">
				<h:outputText
					value="#{usuarioPerfilController.msgTxtUsuarioPermiso}"
					styleClass="msgError"></h:outputText>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Perfil:" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="idCboPerfilUsuario"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{usuarioPerfilController.listarMenues1}"
					onchange="getJSSelectionPerfilUsuario();submit();"
					style="width: 300px;"
					value="#{usuarioPerfilController.beanPermiso.intIdPerfil}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
					<tumih:selectItems var="sel"
						value="#{usuarioPerfilController.listPerfilUsuario}"
						itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescPerfil}" />
					<%--<f:selectItems value="#{usuarioPerfilController.cboPerfilUsuario}"></f:selectItems>--%>
					<a4j:support event="onchange" reRender="pMenuPermiso"
						ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText
					value="#{usuarioPerfilController.msgCboTipoPerfilPermiso}"
					styleClass="msgError"></h:outputText>
				<h:inputHidden id="hiddenIdPerfilPerm"></h:inputHidden>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Estado" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu
					value="#{usuarioPerfilController.beanPermiso.intIdEstado}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="width: 250px; border: none">
				<h:outputText value="#{usuarioPerfilController.msgCboEstadoPermiso}"
					styleClass="msgError"></h:outputText>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pFechasPermiso" columns="6" border="0">
			<rich:spacer width="10px"></rich:spacer>
			<rich:column style="width:10px; padding-right:15px;border:0px;">
				<h:selectBooleanCheckbox id="chkRangoFecPermiso"
					value="#{usuarioPerfilController.beanPermiso.chkRanFecha}"
					disabled="#{usuarioPerfilController.formPermisoEnabled}">
					<a4j:support event="onclick" reRender="pFechasPermiso"
						actionListener="#{usuarioPerfilController.enableDisable2}" />
				</h:selectBooleanCheckbox>
			</rich:column>
			<rich:column style="width:120px; border:none;">
				<h:outputText value="Rango de Fecha"></h:outputText>
			</rich:column>
			<rich:column style="border:0px;width:145px;">
				<rich:calendar popup="true"
					value="#{usuarioPerfilController.daFechaPermisoIni}"
					disabled="#{usuarioPerfilController.formPermisoEnabled2}"
					datePattern="dd/MM/yyyy" cellWidth="10px" cellHeight="20px"
					style="width:200px" />
			</rich:column>
			<rich:column style="border:0px;width:150px;">
				<rich:calendar popup="true"
					value="#{usuarioPerfilController.daFechaPermisoFin}"
					disabled="#{usuarioPerfilController.formPermisoEnabled2}"
					datePattern="dd/MM/yyyy" cellWidth="10px" cellHeight="20px"
					style="width:200px" />
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value="#{usuarioPerfilController.msgTxtRanFecPermiso}"
					styleClass="msgError"></h:outputText>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Menú 01" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="cboMenuPermiso1"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{controlsFiller.reloadCboMenu}"
					value="#{usuarioPerfilController.strCboMenuPermiso01}"
					style="width: 250px;">
					<f:selectItems value="#{controlsFiller.cboMenu1}"></f:selectItems>
					<a4j:support event="onchange"
						reRender="cboMenuPermiso2,cboMenuPermiso3,cboMenuPermiso4"
						ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="padding-left:20px;border: none;">
				<a4j:commandButton id="btnAgregaMenuPermiso1" value="Agregar Todos"
					styleClass="btnEstilos" style="width:100px;"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					actionListener="#{usuarioPerfilController.agregaMenuPermiso}"
					reRender="pMenuPermiso" />
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pMenuPermiso2" columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Menú 02" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="cboMenuPermiso2"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{controlsFiller.reloadCboMenu}"
					value="#{usuarioPerfilController.strCboMenuPermiso02}"
					style="width: 250px;">
					<f:selectItems value="#{controlsFiller.cboMenuPermiso2}"></f:selectItems>
					<a4j:support event="onchange"
						reRender="cboMenuPermiso3,cboMenuPermiso4" ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="padding-left:20px;border: none;">
				<a4j:commandButton id="btnAgregaMenuPermiso2" value="Agregar Todos"
					styleClass="btnEstilos" style="width:100px;"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					actionListener="#{usuarioPerfilController.agregaMenuPermiso}"
					reRender="pMenuPermiso" />
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pMenuPermiso3" columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Menú 03" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="cboMenuPermiso3"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					valueChangeListener="#{controlsFiller.reloadCboMenu}"
					value="#{usuarioPerfilController.strCboMenuPermiso03}"
					style="width: 250px;">
					<f:selectItems value="#{controlsFiller.cboMenuPermiso3}"></f:selectItems>
					<a4j:support event="onchange" reRender="cboMenuPermiso4"
						ajaxSingle="true" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="padding-left:20px;border: none;">
				<a4j:commandButton id="btnAgregaMenuPermiso3" value="Agregar Todos"
					styleClass="btnEstilos" style="width:100px;"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					actionListener="#{usuarioPerfilController.agregaMenuPermiso}"
					reRender="pMenuPermiso" />
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pMenuPermiso4" columns="4" border="0">
			<rich:column style="width: 150px; border: none">
				<h:outputText value="Menú 04" style="padding-left:10px"></h:outputText>
			</rich:column>
			<rich:column style="border:none;">
				<h:outputText value=":"></h:outputText>
			</rich:column>
			<rich:column style="border: none">
				<h:selectOneMenu id="cboMenuPermiso4"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					value="#{usuarioPerfilController.strCboMenuPermiso04}"
					style="width: 250px;">
					<f:selectItems value="#{controlsFiller.cboMenuPermiso4}"></f:selectItems>
				</h:selectOneMenu>
			</rich:column>
			<rich:column style="padding-left:20px;border: none;">
				<a4j:commandButton id="btnAgregaMenuPermiso4" value="Agregar"
					styleClass="btnEstilos" style="width:100px;"
					disabled="#{usuarioPerfilController.formPermisoEnabled}"
					actionListener="#{usuarioPerfilController.agregaMenuPermiso}"
					reRender="pMenuPermiso" />
			</rich:column>
		</h:panelGrid>
	</rich:panel>

<rich:panel style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;"
	rendered="#{usuarioPerfilController.permisoRendered}">
	<h:panelGrid id="pMenuPermiso" columns="1">
		<rich:dataTable value="#{usuarioPerfilController.beanListMenuesPermisos}"
			style="background-color:#DEEBF5;border-color:#DEEBF5;cellspacing:0px;border:0px;"
			var="item" rowKeyVar="rowKey" width="625px"
			rendered="#{not empty usuarioPerfilController.beanListMenuesPermisos}">
			<rich:column style="border:0px;padding-left:0px;padding-top:0px;padding-bottom:0px;">
				<h:outputText value="#{item.strNombreM1}"></h:outputText>
			</rich:column>
			<rich:column breakBefore="true" style="border:0px;padding-left:30px;padding-top:0px;padding-bottom:0px;">
				<h:outputText value="#{item.strNombreM2}"></h:outputText>
			</rich:column>
			<rich:column breakBefore="true" style="border:0px;padding-left:60px;cellpadding:0px;cellspacing:0px;">
				<h:outputText value="#{item.strNombreM3}"></h:outputText>
			</rich:column>
			<rich:column breakBefore="true" style="padding-left:90px;cellpadding:0px;cellspacing:0px;border-right:0px;">
				<h:outputText value="#{item.strNombreM4}"></h:outputText>
			</rich:column>
			<rich:column style="border:0px;cellpadding:0px;cellspacing:0px;padding-top:0px;padding-bottom:0px;border-bottom:1px;">
				<h:selectBooleanCheckbox id="chkPerfil" value="#{item.chkPerfil}" />
			</rich:column>
		</rich:dataTable>
	</h:panelGrid>
</rich:panel>

</rich:panel>