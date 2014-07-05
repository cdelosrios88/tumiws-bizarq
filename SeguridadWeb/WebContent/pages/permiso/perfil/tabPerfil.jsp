<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fecha     : 19/08/2011               -->
<h:panelGrid columns="1" id="idTabPerfil" style="width:100%;border-width:1px;border-style:solid;border-color:#17356f;">
<h:column>
<table border="0">
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
<tr>
	<td colspan="2" class="rich-table-cell"><h:outputText value="Empresa :" /></td>
	<td colspan="3">
		<h:selectOneMenu value="#{usuarioPerfilController.filtroPerfil.id.intPersEmpresaPk}" style="width:150px;">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaJuridicaEmpresa}"
							   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
			<a4j:support event="onchange" reRender="frmPrincipal" actionListener="#{usuarioPerfilController.onchangeEmpresaDeFiltroPerfil}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" class="rich-table-cell"><h:outputText value="Tipo Perfil :"  /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.filtroPerfil.intTipoPerfil}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERFIL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" class="rich-table-cell"><h:outputText value="Estado :"  /></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.filtroPerfil.intIdEstado}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
			tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="3"></td>	
</tr>

<tr>
	<td colspan="2" class="rich-table-cell"><h:outputText value="Perfil :"  /></td>
	<td colspan="3">
	   <h:selectOneMenu value="#{usuarioPerfilController.filtroPerfil.id.intIdPerfil}" style="width: 150px;"> 
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaFiltroPerfil}" 
				itemValue="#{sel.id.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" class="rich-table-cell"><h:outputText value="Vigencia:" />
		<a4j:commandLink actionListener="#{usuarioPerfilController.onClickIndeterminadoDeFiltroPerfil}" ajaxSingle="true" style="color: #dbe5f1;" reRender="idTabPerfil">
			<h:panelGroup rendered="#{usuarioPerfilController.filtroPerfil.blnIndeterminado}">	
				<input type="checkbox"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{not usuarioPerfilController.filtroPerfil.blnIndeterminado}">	
				<input type="checkbox" checked="checked" />
			</h:panelGroup>
		</a4j:commandLink>
		
	</td>
	<td colspan="2" class="rich-table-cell">
		<tumih:radioButton name="filtroPerfilVigencia" overrideName="true" 
			value="#{usuarioPerfilController.filtroPerfil.blnVigencia}" itemValue="true" itemLabel="Vigentes" 
			disabled="#{usuarioPerfilController.filtroPerfil.blnIndeterminado}">
		</tumih:radioButton>	
	</td>
	<td colspan="2" class="rich-table-cell">
		<tumih:radioButton name="filtroPerfilVigencia" overrideName="true" 
			value="#{usuarioPerfilController.filtroPerfil.blnVigencia}" itemValue="false" itemLabel="No Vigentes"
			disabled="#{usuarioPerfilController.filtroPerfil.blnIndeterminado}">
		</tumih:radioButton>	
	</td>
	<td colspan="5"></td>
</tr>
<tr>
	<td colspan="14"></td>
	<td colspan="2">
		<h:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{usuarioPerfilController.buscarPerfil}"/>	
	</td>
</tr>
	
</table>
</h:column>
<h:panelGrid id="pgFilUsuario" columns="3">
 <rich:column style="border:none;width:40px;"/>
 <rich:column style="border:none;width:920px;">
<%--<h:panelGrid id="pgBusquedaPerfil" columns="1"  border="0"> --%>
<rich:extendedDataTable id="grillaBusquedaPerfil" var="item" sortMode="single" rows="5" enableContextMenu="false"
	value="#{usuarioPerfilController.listaBusquedaPerfil}" rowKeyVar="rowKey" 
	rendered="#{not empty usuarioPerfilController.listaBusquedaPerfil}" width="920px" height="170px"  
	onRowClick="jsSeleccionPerfil('#{rowKey}');"> 
      <rich:column width="30px"><h:outputText value="#{rowKey+1}"/></rich:column>
      <rich:column width="50px">
          <f:facet name="header"><h:outputText value="Código"/></f:facet>
          <h:outputText value="#{item.id.intIdPerfil}"/>
      </rich:column>
      <rich:column width="200px">
          <f:facet name="header"><h:outputText value="Nombre"/></f:facet>
          	<h:outputText value="#{item.strDescripcion}"/>
      </rich:column>
      <rich:column width="100px">
          <f:facet name="header"><h:outputText value="Estado"/></f:facet>
          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" property="#{item.intIdEstado}"/>
      </rich:column>
      <rich:column width="200px">
          <f:facet name="header"><h:outputText value="Empresa"/></f:facet>
          	<h:outputText value="#{item.juridica.strSiglas}"/>
      </rich:column>
      <rich:column width="150px">
          <f:facet name="header"><h:outputText value="Rango de Fecha"/></f:facet>
          	<h:outputText value="#{item.dtHasta}">
          		<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
          	</h:outputText>
          	<h:outputText value=" - "/>
          	<h:outputText value="#{item.dtHasta}">
          		<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
          	</h:outputText>
      </rich:column>
      <rich:column width="150px">
          <f:facet name="header"><h:outputText value="Fecha de Registro"/></f:facet>
          <h:outputText value="#{item.tsFechaRegistro}">
          	<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="America/Bogota"/>
          </h:outputText>
      </rich:column>
      <f:facet name="footer"><rich:datascroller for="grillaBusquedaPerfil" maxPages="10"/></f:facet>
</rich:extendedDataTable>
</rich:column>
<rich:column style="border:none;width:40px;"/>
</h:panelGrid>

<h:panelGrid columns="2" id="idIrMatenimiento">
	<h:outputLink value="#">
	<h:panelGroup rendered="#{not empty usuarioPerfilController.strEstadoPerfil && usuarioPerfilController.strEstadoPerfil == applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		<h:graphicImage value="/images/icons/mensaje1.jpg" onclick="Richfaces.showModalPanel('pConsultarPerfil')" style="border:0px"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{not empty usuarioPerfilController.strEstadoPerfil && usuarioPerfilController.strEstadoPerfil != applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		<h:graphicImage value="/images/icons/mensaje1.jpg" onclick="Richfaces.showModalPanel('pUpdateDeletePerfil')" style="border:0px"/>
	</h:panelGroup>
	</h:outputLink>
	<h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
</h:panelGrid>
<h:inputHidden id="hiddenIndexPerfil" value="#{usuarioPerfilController.strIndexPerfil}"/>
<h:inputHidden id="hiddenEstadoPerfil" value="#{usuarioPerfilController.strEstadoPerfil}"/>
<a4j:commandButton id="idBtnIrMantenimiento" actionListener="#{usuarioPerfilController.seleccionarPerfilDeGrilla}" style="display:none;" reRender="idIrMatenimiento"/>

<h:panelGroup rendered="#{not empty usuarioPerfilController.strMsgPerfil}">
<table>
<tr>
	<td align="left">
		<h:outputText value="#{usuarioPerfilController.strMsgPerfil}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.strMsgPerfil}"/>
	</td>
</tr>
</table>
</h:panelGroup>

<h:panelGrid columns="4">
	<h:commandButton value="Nuevo" actionListener="#{usuarioPerfilController.irGrabarPerfil}" styleClass="btnEstilos"/>
	<h:commandButton value="Guardar" actionListener="#{usuarioPerfilController.grabarPerfil}" styleClass="btnEstilos"
  		rendered="#{usuarioPerfilController.strTipoMantPerfil == applicationScope.Constante.MANTENIMIENTO_GRABAR}"/>
  	<h:commandButton value="Guardar" actionListener="#{usuarioPerfilController.modificarPerfil}" styleClass="btnEstilos"
  		rendered="#{usuarioPerfilController.strTipoMantPerfil == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}"/>
  	<h:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarPerfil}" styleClass="btnEstilos"/>
</h:panelGrid>

<h:panelGrid columns="1" id="pgMantenimiento" style="width:100%;border-width:1px;border-style:solid;border-color:#17356f;">
<rich:panel rendered="#{usuarioPerfilController.strTipoMantPerfil != applicationScope.Constante.MANTENIMIENTO_NINGUNO}">
	<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantPerfil != applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="idMantenimientoEdicion" viewId="/pages/permiso/perfil/mantenimiento/edicion.jsp"/>
	</h:panelGroup>
	<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantPerfil == applicationScope.Constante.MANTENIMIENTO_CONSULTAR}">
		<a4j:include id="idMantenimientoVista" viewId="/pages/permiso/perfil/mantenimiento/vista.jsp"/>
	</h:panelGroup>
</rich:panel>
</h:panelGrid>

</h:panelGrid>  
