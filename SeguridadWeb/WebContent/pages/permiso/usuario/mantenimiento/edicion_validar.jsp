<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<h:panelGrid columns="1" style="width:100%;border:0px">
<h:column>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
  	<td style="width:50px;"></td>
  	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
  	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
</tr>
<h:panelGroup rendered="#{usuarioPerfilController.strValidarUsuario == applicationScope.Constante.MANTENIMIENTO_VALIDAR}">
<tr>
  	<td colspan="2" align="left"> 
  		<h:outputText value="Tipo Persona:" ></h:outputText>
  	</td>
  	<td colspan="2" align="left">
		 <h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod}" disabled="#{usuarioPerfilController.usuario.persiste}" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeTipoPersona}" reRender="pgUsuario"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" align="left"> 
  		<h:outputText value="Tipo Dcto.:" style="width:100px;"/>
  	</td>
	<td colspan="2" align="left">
        <h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.listaDocumento[0].intTipoIdentidadCod}" style="width:100px;" disabled="true">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>	
	</td>
	<td colspan="2" align="left"> 
  		<h:outputText value="Nro. Dcto.:" style="width:100px;"/>
  	</td>
	<td colspan="2" align="left">
        <h:inputText maxlength="11" value="#{usuarioPerfilController.usuario.persona.listaDocumento[0].strNumeroIdentidad}" onkeypress="return soloNumeros(event);" style="width:100px;"/>
	</td>
</tr>

<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.tipoPersonaCod ||
						  not empty usuarioPerfilController.msgUsuario.tipoIdentidad ||
						  not empty usuarioPerfilController.msgUsuario.nroIdentidad}">
<tr>
  	<td colspan="5" align="left">
    	<h:outputText value="#{usuarioPerfilController.msgUsuario.tipoPersonaCod}" styleClass="msgError" 
        	rendered="#{not empty usuarioPerfilController.msgUsuario.tipoPersonaCod}"/>
    </td>
    <td></td>
    <td colspan="5" align="left">
    	<h:outputText value="#{usuarioPerfilController.msgUsuario.tipoIdentidad}" styleClass="msgError" 
       		rendered="#{not empty usuarioPerfilController.msgUsuario.tipoIdentidad}"/>
    </td>
    <td colspan="6" align="left">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.nroIdentidad}" styleClass="msgError" 
       		rendered="#{not empty usuarioPerfilController.msgUsuario.nroIdentidad}"/>
    </td>
</tr>
</h:panelGroup>

<tr>
  	<td colspan="16"> 
  		<h:commandButton value="Validar" actionListener="#{usuarioPerfilController.irValidarUsuarioUnico}" styleClass="btnEstilos" style="width:100%"/>
  	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{usuarioPerfilController.strValidarUsuario != applicationScope.Constante.MANTENIMIENTO_VALIDAR}">
<tr>
	<td colspan="16">
		<a4j:include viewId="/pages/permiso/usuario/mantenimiento/edicion.jsp"/>
	</td>		
</tr>	
</h:panelGroup>

</table>

</h:column>	
</h:panelGrid>
