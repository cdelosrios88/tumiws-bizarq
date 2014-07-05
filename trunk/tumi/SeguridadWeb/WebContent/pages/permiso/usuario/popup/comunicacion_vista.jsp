<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO COMUNICACION   -->
	<!-- Fecha     : 25/01/2012               -->

<script language="JavaScript" src="/js/main.js"  type="text/javascript"></script>

<rich:panel style="border: 2px solid #17356f;background-color:#DEEBF5;width:750px;">
<h:message id="LABEL_ID_MSG" styleClass="msgError" for="Numero" />
<h:panelGrid border="0" columns="2">
    <rich:column style="border:none">
     	<a4j:commandButton value="Grabar" actionListener="#{usuarioPerfilController.agregarComunicacion}" 
     		disabled="true" styleClass="btnEstilos" reRender="pgUsuario"/>
    </rich:column>
    <rich:column style="border:none">
     	<a4j:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarComunicacion}"
     		disabled="true" styleClass="btnEstilos" reRender="pgUsuario"/>
    </rich:column>
</h:panelGrid>

<rich:spacer height="4px"/><rich:spacer height="4px"/>
<rich:separator height="5px"/>

<h:panelGrid id="pgFormComunicacion" columns="2" border="0">
<rich:panel id="pMarco1" style="border:1px; solid #17356f ;width:660px; background-color:#DEEBF5">
<h:panelGrid columns="2" style="border:0px">
<rich:column style="border:none"><h:outputText value="Tipo de Comunicación:"/></rich:column>
<rich:column style="border:none;padding-left:10px;">
	<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod}" styleClass="SelectOption2" disabled="true">
		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMUNICACION}" 
		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	</h:selectOneMenu>
</rich:column>
</h:panelGrid>

<h:panelGrid columns="4">
	<rich:column style="width:123px;"> <h:outputText value="Nombre de Usuario:"/> </rich:column>
    <rich:column>
    <h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
		<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strNombres}
							#{usuarioPerfilController.usuario.persona.natural.strApellidoPaterno}
							#{usuarioPerfilController.usuario.persona.natural.strApellidoMaterno}" 
		size="40" disabled="true"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}">
		<h:inputText value="#{usuarioPerfilController.usuario.persona.juridica.strRazonSocial}
							#{usuarioPerfilController.usuario.persona.juridica.strNombreComercial}" 
		size="40" disabled="true"/>
	</h:panelGroup>	
	</rich:column>
	<rich:column style="width:123px;"> <h:outputText value="Estado:"/> </rich:column>
	<rich:column>
		<h:selectOneMenu value="#{usuarioPerfilController.usuario.intIdEstado}" disabled="true">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
</h:panelGrid>

<h:panelGroup rendered="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO}">
<h:panelGrid columns="7">
	<rich:column style="border:none">
	  	<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}" disabled="true" styleClass="SelectOption2" style="width:140px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOTELEFONO}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column width="150px" style="border:none;"><h:outputText value="Tipo de Línea:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intTipoLineaCod}" disabled="true" styleClass="SelectOption2">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOLINEATELEF}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;"><h:outputText value="Número:"/></rich:column>
	<rich:column style="border:none;">
		<h:inputText id="Numero" label="Número de Teléfono" value="#{usuarioPerfilController.comunicacion.intNumero}" disabled="true"/>
	</rich:column>
	<rich:column style="border:none;padding-left:10px;"><h:outputText value="Anexo:"/></rich:column>
	<rich:column style="border:none;padding-left:10px;">
		<h:inputText value="#{usuarioPerfilController.comunicacion.intAnexo}" disabled="true" size="15" maxlength="5"/>
	</rich:column>
</h:panelGrid>

<h:panelGrid id="pgDesc" columns="5" style="border:0px">
	<rich:column style="border:none"></rich:column>
 	<rich:column><h:outputText value="Descripción"/></rich:column>
 	<rich:column>
		<h:inputText value="#{usuarioPerfilController.comunicacion.strDescripcion}" disabled="true" size="63"/>
	</rich:column>
	<rich:column>
	<h:selectBooleanCheckbox value="#{usuarioPerfilController.comunicacion.chkCom}" disabled="true"/>
	</rich:column>
 	<rich:column style="border:none;"><h:outputText value="Caso de Emergencia"/></rich:column>
</h:panelGrid>
</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_CORREO}">
<h:panelGrid id="pgEmail" columns="3">
	<rich:column width="80px" style="border:none;"><h:outputText value="E-mail:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu style="width:140px;" value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;">
		<h:inputText id="idemail" size="80" label="E-mail" value="#{usuarioPerfilController.comunicacion.strDescripcion}" disabled="true" />
	</rich:column>
</h:panelGrid>
</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_WEB}">	
<h:panelGrid id="pgPagWeb" columns="3">
	<rich:column width="80px" style="border:none;"> <h:outputText value="Pág. Web:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu style="width:140px;" value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;">
		<h:inputText size="80" value="#{usuarioPerfilController.comunicacion.strDescripcion}" disabled="true"/>
	</rich:column>
</h:panelGrid>
</h:panelGroup>
</rich:panel>
</h:panelGrid>
</rich:panel>
