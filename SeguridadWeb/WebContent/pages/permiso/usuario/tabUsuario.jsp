<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<h:panelGrid columns="1" style="width:100%;border-width:1px;border-style:solid;border-color:#17356f;">
<h:panelGrid columns="10" style="width:100%" border="0" >
  	<rich:column  style="border:none;width:100px;text-align:left;">
  	    <h:outputText id="lblEmpresaUsuario" value="Empresa:" style="width:100px;"/>
  	</rich:column>
  	<rich:column style="border:none;width:100px;text-align:left;">
  		<h:selectOneMenu id="idCboEmpresaUsu" value="#{usuarioPerfilController.usuarioComp.empresaUsuario.id.intPersEmpresaPk}" style="width:100px;">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaJuridicaEmpresa}"
		   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
		<a4j:support event="onchange" actionListener="#{usuarioPerfilController.getListaPerfilDeEmpresa}" reRender="idCboPerfil"/>
	</h:selectOneMenu>
   	</rich:column>
   	<rich:column style="border:none;width:100px;text-align:left;">
		<h:outputText value ="Tipo Sucursal:" style="width:100px;"/>
	</rich:column>
	<rich:column style="border:none;width:100px;text-align:left;">
		<h:selectOneMenu id="idCboTipoSucursal" value="#{usuarioPerfilController.usuarioComp.intIdTipoSucursal}" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
   	<rich:column style="border:none;width:100px;text-align:left;">
  		<h:outputText id="lblTipoEmpresUsuario" value="Tipo Usuario:" style="width:100px;"/>
  	</rich:column>
  	<rich:column style="border:none;width:100px;text-align:left;">
    	<h:selectOneMenu id="idCboTipoUsuario" value="#{usuarioPerfilController.usuarioComp.usuario.intTipoUsuario}" style="width:100px;">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOUSUARIO}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
   	</rich:column>
   	<rich:column style="border:none;width:100px;text-align:left;">
		<h:outputText value ="Usuario:" style="width:100px;"/>
	</rich:column>
	<rich:column style="border:none;width:100px;text-align:left;">
		<h:inputText value="#{usuarioPerfilController.usuarioComp.usuario.strUsuario}" style="width:100px;"/>
	</rich:column>
   	<rich:column style="border:none;width:100px;text-align:left;">
  		<h:outputText id="lblEstadoUsuario" value="Estado:" style="width:100px;"/>
  	</rich:column>
  	<rich:column style="border:none;width:100px;text-align:left;">
  		<h:selectOneMenu id="idCboTipoAplicacion" value="#{usuarioPerfilController.usuarioComp.usuario.intIdEstado}" style="width:100px;">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
			tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
		</h:selectOneMenu>
   	</rich:column>
</h:panelGrid>

<h:panelGrid columns="4" border="0" style="width:100%;text-align:left;">
	<rich:column style="border:none;width:100px;text-align:left;">
		<h:outputText id="lblFilSucursal" value ="Perfil:" style="width:100px;"/>
	</rich:column>
	<rich:column style="border:none;width:100px;text-align:left;">
		<h:selectOneMenu id="idCboPerfil" value="#{usuarioPerfilController.usuarioComp.intIdPerfil}" style="width:100px;">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaPerfil}"
		itemValue="#{sel.id.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</rich:column>
	<rich:column style="border:none;width:700px;text-align:left;">
   	</rich:column>
	<rich:column style="border:none;width:100px;text-align:left;">
		<h:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{usuarioPerfilController.buscarUsuario}" style="width:100px;"/>
	</rich:column>
</h:panelGrid>

<h:panelGrid id="pgFilUsuario" columns="3">
 <rich:column style="border:none;width:40px;"/>
 <rich:column style="border:none;width:920px;">
 <rich:extendedDataTable  id="tblFilUsuario" var="item" sortMode="single" rows="5" enableContextMenu="false"
 	value="#{usuarioPerfilController.listaUsuarioComp}" rowKeyVar="rowkey" width="920px" height="170px"  
 	rendered="#{not empty usuarioPerfilController.listaUsuarioComp}" 
 	onRowClick="jsSeleccionUsuario(#{rowkey});">
   <rich:column sortable="false" width="30px">
       <f:facet name="header"><h:outputText /></f:facet>
       <h:outputText value="#{rowkey+1}"/>
   </rich:column>
   <rich:column sortable="false" width="150px">
       <f:facet name="header"><h:outputText value="Nombre Usuario" /></f:facet>
       <h:panelGroup rendered="#{item.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
	       	<h:outputText value="#{item.usuario.persona.natural.strNombres}
	       						 #{item.usuario.persona.natural.strApellidoPaterno}
	       						 #{item.usuario.persona.natural.strApellidoMaterno}"></h:outputText>
       </h:panelGroup>
       <h:panelGroup rendered="#{item.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}">
       		<h:outputText value="#{item.usuario.persona.juridica.strRazonSocial}"></h:outputText>
       </h:panelGroup>
   </rich:column>
   <rich:column sortable="true" sortBy="#{item.usuario.intIdEstado}" width="70px">
       <f:facet name="header"><h:outputText value="Estado" /></f:facet>
       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
		itemValue="intIdDetalle" itemLabel="strDescripcion" property="#{item.usuario.intIdEstado}"/>
   </rich:column>
   
   <rich:column sortable="true" sortBy="#{item.empresaUsuario.juridica.strSiglas}" width="80px">
       <f:facet name="header"><h:outputText value="Empresa" ></h:outputText></f:facet>
       <h:outputText value="#{item.empresaUsuario.juridica.strSiglas}"></h:outputText>
   </rich:column>
   <rich:column sortable="false" width="100px">
       <f:facet name="header"><h:outputText value="Tipo Usuario" ></h:outputText></f:facet>
       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOUSUARIO}"
		itemValue="intIdDetalle" itemLabel="strDescripcion" property="#{item.usuario.intTipoUsuario}"/>
   </rich:column>
   
   <rich:column sortable="true" sortBy="#{item.strPerfil}" width="100px">
       <f:facet name="header"><h:outputText value="Perfil" ></h:outputText></f:facet>
       <h:outputText value="#{item.strPerfil}"></h:outputText>
   </rich:column>
   
   <rich:column sortable="true" sortBy="#{item.strSucursal}" width="270px">
       <f:facet name="header"><h:outputText value="Sucursal / Sub Sucursal" ></h:outputText></f:facet>
       <h:outputText value="#{item.strSucursal}"></h:outputText>
   </rich:column>
   
   <rich:column sortable="false" width="120px">
       <f:facet name="header"><h:outputText value="Fecha Registro" ></h:outputText></f:facet>
       <h:outputText value="#{item.usuario.dtFechaRegistro}">
       	<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="America/Bogota"/>
       </h:outputText>
   </rich:column>
   <f:facet name="footer"><rich:datascroller for="tblFilUsuario" maxPages="10"/></f:facet>
</rich:extendedDataTable>
</rich:column>
<rich:column style="border:none;width:40px;"/>
</h:panelGrid>

<h:panelGrid id="idPanelMatenimiento" columns="2" width="100%">
	<rich:column style="border:none;width:40px;"/>
	<rich:column style="border:none;">
		  <h:outputLink value="#" id="linkPanelUsuario">
		     <h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
		     <h:panelGroup rendered="#{not empty usuarioPerfilController.strEstadoUsuario && usuarioPerfilController.strEstadoUsuario == applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		     	<rich:componentControl for="panelConsultarUsuario" attachTo="linkPanelUsuario" operation="show" event="onclick"/>
		     </h:panelGroup>
		     <h:panelGroup rendered="#{not empty usuarioPerfilController.strEstadoUsuario && usuarioPerfilController.strEstadoUsuario != applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		     	<rich:componentControl for="panelUpdateDeleteUsuario" attachTo="linkPanelUsuario" operation="show" event="onclick"/>
		     </h:panelGroup>
		  </h:outputLink>
		  <h:outputText value="Para Eliminar o Actualizar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>
		  
		  <h:inputHidden id="hiddenIndexUsuario" value="#{usuarioPerfilController.strIndexUsuario}"/>
		  <h:inputHidden id="hiddenEstadoUsuario" value="#{usuarioPerfilController.strEstadoUsuario}"/>
		  <a4j:commandButton id="hiddenBtnSeleccionUsuario" actionListener="#{usuarioPerfilController.seleccionarUsuarioDeGrilla}" style="display:none;" reRender="idPanelMatenimiento"/>
	 </rich:column> 
</h:panelGrid>

<h:panelGrid columns="2">
  <rich:column style="border:none;width:40px;"/>
  <rich:column style="border:none;">
	  <h:commandButton value="Nuevo" actionListener="#{usuarioPerfilController.irGrabarUsuario}" styleClass="btnEstilos"/>                     
	  <h:commandButton value="Guardar" actionListener="#{usuarioPerfilController.grabarUsuario}" styleClass="btnEstilos"
	  		rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_GRABAR}"/>
	  <h:commandButton value="Guardar" actionListener="#{usuarioPerfilController.modificarUsuario}" styleClass="btnEstilos"
	  		rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}"/>
	  <h:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarGrabarUsuario}" styleClass="btnEstilos"/>
  </rich:column>
</h:panelGrid>

<rich:spacer height="10px"/>

<h:panelGrid columns="1" id="pgUsuario" style="width:100%;border-width:1px;border-style:solid;border-color:#17356f;">
<rich:panel rendered="#{usuarioPerfilController.strTipoMantUsuario != applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
	<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario != applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="incUsuarioEdicion" viewId="/pages/permiso/usuario/mantenimiento/edicion_validar.jsp"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="incUsuarioVista" viewId="/pages/permiso/usuario/mantenimiento/vista.jsp"/>
	</h:panelGroup>
</rich:panel>
</h:panelGrid>

</h:panelGrid>
