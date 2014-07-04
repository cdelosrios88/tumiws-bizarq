<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO DOMICILIOS     -->
	<!-- Fecha     : 25/01/2012               -->

<script language="JavaScript" src="/SeguridadWeb/js/main.js"  type="text/javascript"></script>
<script type="text/javascript">
function grabarDomicilio(pIdBoton){
	lControl = document.getElementById(pIdBoton);
	if(lControl){
	 	lControl.click();
	}
}
</script>
<h:panelGrid columns="1" style="border:0px;width:100%;">
<h:column>
<table>
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
	</tr>
	<tr>
		<td colspan="2">
			<a4j:commandButton value="Grabar" actionListener="#{usuarioPerfilController.validarDomicilio}"
	   		styleClass="btnEstilos" reRender="frmDomicilioAgregar"/>
	   		<h:panelGroup rendered="#{not empty usuarioPerfilController.esPopPupValido && usuarioPerfilController.esPopPupValido}">
		   		<a4j:commandButton id="btnGrabarDomicilio" actionListener="#{usuarioPerfilController.agregarDomicilio}"
		     		oncomplete="Richfaces.hideModalPanel('pAgregarDomicilio')" style="display:none;" reRender="pgUsuario"/>
				<script>
					grabarDomicilio('frmDomicilioAgregar:incDomicilioAgregar:btnGrabarDomicilio');
				</script>
			</h:panelGroup>
		</td>
		<td colspan="2">
			<a4j:commandButton value="Cancelar" actionListener="#{usuarioPerfilController.cancelarDomicilio}"
	   		oncomplete="Richfaces.hideModalPanel('pAgregarDomicilio')"
	   		styleClass="btnEstilos"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left"><h:outputText value="Nombre Usuario:"/></td>
		<td colspan="6" align="left">
			<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
				<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strNombres}
									#{usuarioPerfilController.usuario.persona.natural.strApellidoPaterno}
									#{usuarioPerfilController.usuario.persona.natural.strApellidoMaterno}" 
				style="width:300px;" disabled="true"/>
			</h:panelGroup>
			<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}">
				<h:inputText value="#{usuarioPerfilController.usuario.persona.juridica.strRazonSocial}
									#{usuarioPerfilController.usuario.persona.juridica.strNombreComercial}" 
				style="width:300px;" disabled="true"/>
			</h:panelGroup>	
		</td>
		<td colspan="2" align="left"><h:outputText value="Estado:"/></td>
		<td colspan="2" align="left">
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{usuarioPerfilController.usuario.intIdEstado}"/>
		</td>
	</tr>
	<tr>
	<td colspan="12">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2" align="left"><h:outputText value="Tipo Domicilio:"/></td>
		<td colspan="2" align="left">
			<h:selectOneMenu id="cboTipoDomicilio" value="#{usuarioPerfilController.domicilio.intTipoDomicilioCod}" style="width:100px;" disabled="true">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOMICILIO}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
		</td>
		<td colspan="2" align="left"><h:outputText value="Tipo dirección:"/></td>
		<td colspan="2" align="left">
			<h:selectOneMenu id="cboTipoDireccion" value="#{usuarioPerfilController.domicilio.intTipoDireccionCod}" style="width:100px;" disabled="true">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECCION}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
		</td>
		<td colspan="2" align="left"><h:outputText value="Tipo vivienda:"/></td>
		<td colspan="2" align="left">
			<h:selectOneMenu id="cboTipoVivienda" value="#{usuarioPerfilController.domicilio.intTipoViviendaCod}" style="width:100px;" disabled="true">
			  	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVIVIENDA}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
		</td>
	</tr>
	
<tr>
	<td colspan="2" align="left"><h:outputText value="Tipo vía:"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboTipoVia" value="#{usuarioPerfilController.domicilio.intTipoViaCod}" style="width:100px;" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVIA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" align="left"><h:outputText value="Nombre vía:"/></td>
	<td colspan="6" align="left">
		<h:inputText id="nombreVia" value="#{usuarioPerfilController.domicilio.strNombreVia}" label="nombreVia" style="width:300px;" disabled="true"/>	
	</td>
</tr>

<tr>
	<td colspan="2" align="left"><h:outputText value="Número vía:"/></td>
	<td colspan="2" align="left">
		<h:inputText value="#{usuarioPerfilController.domicilio.intNumeroVia}" style="width:100px" onkeypress="return soloNumeros(event);" maxlength="5" disabled="true"/>
	</td>
	<td colspan="2" align="left"><h:outputText value="Interior:"/></td>
	<td colspan="2" align="left"><h:inputText value="#{usuarioPerfilController.domicilio.intInterior}" style="width:100px;"  disabled="true"/></td>
</tr>

<tr>
	<td colspan="2" align="left"><h:outputText value="Tipo Zona:"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboTipoZona" value="#{usuarioPerfilController.domicilio.intTipoZonaCod}" styleClass="SelectOption2" style="width:100px;"  disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOZONA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" align="left"><h:outputText value="Nombre Zona:"/></td>
	<td colspan="2" align="left">
		<h:inputText value="#{usuarioPerfilController.domicilio.strNombreZona}" style="width:100px;"  disabled="true"/>
	</td>
</tr>
<tr>
	<td colspan="2" align="left"><h:outputText value="Referencia:"/></td>
	<td colspan="10" align="left">
		<h:inputText value="#{usuarioPerfilController.domicilio.strReferencia}" style="width:300px;"  disabled="true"/>
	</td>
</tr>
<tr>
	<td colspan="2" align="left"><h:outputText value="Departamento:"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboDepartamento" value="#{usuarioPerfilController.domicilio.intParaUbigeoPkDpto}" style="width:100px;"  disabled="true">
			<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
			<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaUbigeoDepartamento}"
				itemValue="#{sel.intIdUbigeo}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeUbigeoDepartamento}" reRender="pgFormDomicilio"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" align="left"><h:outputText value="Provincia:"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboProvincia" value="#{usuarioPerfilController.domicilio.intParaUbigeoPkProvincia}" style="width:100px;"  disabled="true">
			<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
			<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaUbigeoProvincia}"
				itemValue="#{sel.intIdUbigeo}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeUbigeoProvincia}" reRender="pgFormDomicilio"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2" align="left"><h:outputText value="Distrito:"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboDistrito" value="#{usuarioPerfilController.domicilio.intParaUbigeoPkDistrito}" style="width:100px;"  disabled="true">
			<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
			<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaUbigeoDistrito}"
				itemValue="#{sel.intIdUbigeo}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>	
</tr>
<tr>
	<td><h:selectBooleanCheckbox value="#{usuarioPerfilController.domicilio.fgCroquis}">
		</h:selectBooleanCheckbox>
	</td>
	<td align="left"><h:outputText value="Croquis"/></td>
	<td colspan="2" align="left">
		<h:selectOneMenu id="cboDireccion" value="#{usuarioPerfilController.domicilio.intIdDirUrl}" styleClass="SelectOption" onchange="changeOption()" style="width:100px;"  disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOREFERENCIA}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<td colspan="4" align="left">
		<h:inputText  id="txtDirUrl" value="#{usuarioPerfilController.domicilio.strDirUrl}" style="width:200px;"  disabled="true"/>
	</td>
	<td colspan="2"></td>
	<td colspan="2"></td>	
</tr>
<tr>
	<td colspan="8" align="left">
		<rich:fileUpload id="upload" addControlLabel="Adjuntar Imagen" clearControlLabel="Limpiar"  disabled="true"
	      cancelEntryControlLabel="Cancelar" uploadControlLabel="Subir Imagen" listHeight="65" listWidth="320"
	      fileUploadListener="#{fileUploadController.listener}"	maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
		  immediateUpload="#{fileUploadController.autoUpload}" acceptedTypes="jpg, gif, png, bmp">
			<f:facet name="label"> <h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" /> </f:facet>
		</rich:fileUpload>
	</td>
	<td colspan="2"></td>
	<td colspan="2"></td>	
</tr>
<tr>
	<td><h:selectBooleanCheckbox value="#{usuarioPerfilController.domicilio.fgCorrespondencia}"  disabled="true"/></td>
	<td colspan="2">Envíar Correspondencia</td>
	<td colspan="2"></td>
	<td colspan="2"></td>
	<td colspan="2"></td>
	<td colspan="2"></td>	
</tr>
<tr>
	<td colspan="2" align="left"><h:outputText value="Observación"/></td>
	<td colspan="8" align="left">
		<h:inputTextarea rows="3" cols="80" value="#{usuarioPerfilController.domicilio.strObservacion}" disabled="true"/>
	</td>
	<td colspan="2"></td>	
</tr>
</table>
</h:column>
</h:panelGrid>