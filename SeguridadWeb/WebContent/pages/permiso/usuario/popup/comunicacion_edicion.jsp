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

<script language="JavaScript" type="text/javascript">
function grabarComunicacion(pIdBoton){
	lControl = document.getElementById(pIdBoton);
	if(lControl){
	 	lControl.click();
	}
}
</script>

<rich:panel style="border: 2px solid #17356f;background-color:#DEEBF5;width:750px;">
<h:message id="LABEL_ID_MSG" styleClass="msgError" for="Numero" />
    <h:panelGrid border="0" columns="2">
    <rich:column style="border:none">
     	<a4j:commandButton id="asancho" value="Grabar" actionListener="#{usuarioPerfilController.validarComunicacion}"
     		styleClass="btnEstilos" reRender="frmComunicacionAgregar"/>
     	<h:panelGroup rendered="#{not empty usuarioPerfilController.esPopPupValido && usuarioPerfilController.esPopPupValido}"> 	
     	<a4j:commandButton id="btnGrabarComunicacion" actionListener="#{usuarioPerfilController.agregarComunicacion}"
     		oncomplete="Richfaces.hideModalPanel('pAgregarComunicacion')" style="display:none;" reRender="pgUsuario"/>
		<script>
			grabarComunicacion('frmComunicacionAgregar:incComunicacionAgregar:btnGrabarComunicacion');
		</script>     		
     	</h:panelGroup>		
    </rich:column>
    <rich:column style="border:none">
     	<a4j:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarComunicacion}"
     		oncomplete="Richfaces.hideModalPanel('pAgregarComunicacion')" 
     		styleClass="btnEstilos" reRender="pgUsuario"/>
    </rich:column>
</h:panelGrid>

<rich:spacer height="4px"/><rich:spacer height="4px"/>
<rich:separator height="5px"/>

<h:panelGrid id="pgFormComunicacion" columns="2" border="0">
<rich:panel id="pMarco1" style="border:1px; solid #17356f ;width:660px; background-color:#DEEBF5">
<h:panelGrid columns="2" style="border:0px">
<rich:column style="border:none"><h:outputText value="Tipo de Comunicación:"/></rich:column>
<rich:column style="border:none;padding-left:10px;">
	<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod}" styleClass="SelectOption2">
	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMUNICACION}" 
	itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeTipoComunicacion}" reRender="pgFormComunicacion"/>
	</h:selectOneMenu>
</rich:column>
</h:panelGrid>

<h:panelGrid columns="2" style="border:0px" rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.tipoComunicacion}">
<rich:column style="border:none"></rich:column>
<rich:column style="border:none;padding-left:10px;">
	<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.tipoComunicacion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.tipoComunicacion}"/>	
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
	  	<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}" styleClass="SelectOption2" style="width:140px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOTELEFONO}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column width="150px" style="border:none;"><h:outputText value="Tipo de Línea:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu value="#{usuarioPerfilController.comunicacion.intTipoLineaCod}" styleClass="SelectOption2">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOLINEATELEF}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;"><h:outputText value="Número:"/></rich:column>
	<rich:column style="border:none;">
		<h:inputText id="Numero" label="Número de Teléfono" value="#{usuarioPerfilController.comunicacion.intNumero}" onkeypress="return soloNumeros(event);" size="15">
			<f:validateLength minimum="7"/>
		</h:inputText>
	</rich:column>
	<rich:column style="border:none;padding-left:10px;"><h:outputText value="Anexo:"/></rich:column>
	<rich:column style="border:none;padding-left:10px;">
		<h:inputText value="#{usuarioPerfilController.comunicacion.intAnexo}" onkeypress="return soloNumeros(event);" size="15" maxlength="5"/>
	</rich:column>
</h:panelGrid>

<h:panelGrid columns="7" rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion ||
								  	 not empty usuarioPerfilController.msgUsuario.msgComunicacion.tipoLinea ||
								  	 not empty usuarioPerfilController.msgUsuario.msgComunicacion.numero}">
	<rich:column style="border:none">
	  	<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}"/>
	</rich:column>
	<rich:column width="150px" style="border:none;"></rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.tipoLinea}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.tipoLinea}"/>
	</rich:column>
	<rich:column style="border:none;"></rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.numero}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.numero}"/>
	</rich:column>
	<rich:column style="border:none;padding-left:10px;"></rich:column>
	<rich:column style="border:none;padding-left:10px;"></rich:column>
</h:panelGrid>

<h:panelGrid id="pgDesc" columns="5" style="border:0px">
	<rich:column style="border:none"></rich:column>
 	<rich:column><h:outputText value="Descripción"/></rich:column>
 	<rich:column>
		<h:inputText value="#{usuarioPerfilController.comunicacion.strDescripcion}" size="63"/>
	</rich:column>
	<rich:column>
	<h:selectBooleanCheckbox value="#{usuarioPerfilController.comunicacion.chkCom}">
	<a4j:support event="onclick" reRender="pgFormComunicacion" 
		actionListener="#{usuarioPerfilController.checkCasoEmergenciaDeComunicacion}" />
	</h:selectBooleanCheckbox>
	</rich:column>
 	<rich:column style="border:none;"><h:outputText value="Caso de Emergencia"/></rich:column>
</h:panelGrid>

<h:panelGrid columns="5" style="border:0px" rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}">
	<rich:column style="border:none"></rich:column>
 	<rich:column></rich:column>
 	<rich:column>
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}"/>
	</rich:column>
	<rich:column></rich:column>
 	<rich:column style="border:none;"></rich:column>
</h:panelGrid>

</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_CORREO}">
<h:panelGrid id="pgEmail" columns="3">
	<rich:column width="80px" style="border:none;"><h:outputText value="E-mail:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu style="width:140px;" value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;">
		<h:inputText id="idemail" size="80" label="E-mail" value="#{usuarioPerfilController.comunicacion.strDescripcion}"
			validatorMessage="El e-mail ingresado no es válido">
			<f:validator validatorId="checkvalidemail" />
		</h:inputText>
	</rich:column>
</h:panelGrid>

<h:panelGrid columns="3" rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion ||
									 not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}">
	<rich:column width="80px" style="border:none;"></rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}"/>
	</rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}"/>
	</rich:column>
</h:panelGrid>

</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.comunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_WEB}">	
<h:panelGrid id="pgPagWeb" columns="3">
	<rich:column width="80px" style="border:none;"> <h:outputText value="Pág. Web:"/></rich:column>
	<rich:column style="border:none;">
		<h:selectOneMenu style="width:140px;" value="#{usuarioPerfilController.comunicacion.intSubTipoComunicacionCod}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;">
		<h:inputText size="80" value="#{usuarioPerfilController.comunicacion.strDescripcion}"/>
	</rich:column>
</h:panelGrid>
<h:panelGrid columns="3" rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion ||
									 not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}">
	<rich:column width="80px" style="border:none;"></rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.subTipoComunicacion}"/>
	</rich:column>
	<rich:column style="border:none;">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.msgComunicacion.descripcion}"/>
	</rich:column>
</h:panelGrid>

</h:panelGroup>
</rich:panel>
</h:panelGrid>
</rich:panel>
