<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<h:panelGrid columns="1" style="border:0px">
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
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
	<td style="width:50px;"></td>
</tr>
<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
<tr><td colspan="16">&nbsp;</td></tr>
</h:panelGroup>
<tr>
  	<td colspan="2"><h:outputText value="Tipo Pers.:" ></h:outputText></td>
  	<td colspan="2">
  		<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
  		<h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod}" disabled="true" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeTipoPersona}" reRender="pgUsuario"/>
		</h:selectOneMenu>
		</h:panelGroup>
		<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		<h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod}" disabled="#{usuarioPerfilController.usuario.persiste}" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeTipoPersona}" reRender="pgUsuario"/>
		</h:selectOneMenu>
		</h:panelGroup>		
	</td>
	<td colspan="2"><h:outputText value="Tipo Usuario"/></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.usuario.intTipoUsuario}" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOUSUARIO}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<%-- <h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">--%>
	<td colspan="2"><h:outputText value="Tipo Dcto.:" ></h:outputText></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.listaDocumento[0].intTipoIdentidadCod}" disabled="true" style="width:100px;">
		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>	
	</td>
	<td colspan="2"><h:outputText value="Nro. Dcto.:" ></h:outputText></td>
	<td colspan="2">
		<h:inputText maxlength="11" value="#{usuarioPerfilController.usuario.persona.listaDocumento[0].strNumeroIdentidad}" disabled="true"/>	
	</td>
	<%--</h:panelGroup>--%>
	<td colspan="2"><h:outputText value="Estado" ></h:outputText></td>
	<td colspan="2">
		<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
    	<h:selectOneMenu value="#{usuarioPerfilController.usuario.intIdEstado}" style="width:100px;" disabled="true">
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
		</h:panelGroup>
		<h:panelGroup rendered="#{usuarioPerfilController.strTipoMantUsuario == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
    	<h:selectOneMenu value="#{usuarioPerfilController.usuario.intIdEstado}" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
		</h:panelGroup>
	</td>
</tr>

<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.tipoPersonaCod ||
					      not empty usuarioPerfilController.msgUsuario.tipoUsuario}">
<tr>
  	<td colspan="5" align="left">
    	<h:outputText value="#{usuarioPerfilController.msgUsuario.tipoPersonaCod}" styleClass="msgError" 
        	rendered="#{not empty usuarioPerfilController.msgUsuario.tipoPersonaCod}"/>
    </td>
    <td></td>
    <td colspan="5" align="left">
    	<h:outputText value="#{usuarioPerfilController.msgUsuario.tipoUsuario}" styleClass="msgError" 
        	rendered="#{not empty usuarioPerfilController.msgUsuario.tipoUsuario}"/>
    </td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
<tr>
	<td colspan="2" align="left"><h:outputText value="Ap. Paterno:" ></h:outputText></td>
	<td colspan="4" align="left">
		  	<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strApellidoPaterno}" label="Apellido Paterno" style="width:200px;"/>
	</td>
	<td colspan="2" align="left"><h:outputText value="Ap. Materno:" ></h:outputText></td>
  	<td colspan="4" align="left">
	  	<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strApellidoMaterno}" label="Apellido Materno" style="width:200px;"/>
  	</td>
  	<td colspan="2" align="left"><h:outputText value="Nombres:" ></h:outputText></td>
  	<td colspan="4" align="left">
  		<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strNombres}" style="width:200px;"/>
  	</td>
</tr>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.apellidoPaterno ||
					      not empty usuarioPerfilController.msgUsuario.apellidoMaterno ||
					      not empty usuarioPerfilController.msgUsuario.nombres}">
<tr>
  	<td colspan="6">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.apellidoPaterno}" styleClass="msgError" 
		      rendered="#{not empty usuarioPerfilController.msgUsuario.apellidoPaterno}"/>
	</td>
	<td colspan="6">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.apellidoMaterno}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.apellidoMaterno}"/>
  	</td>
  	<td colspan="6">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.nombres}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.nombres}"/>
  	</td>
</tr>
</h:panelGroup>
</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}">
<tr>
  	<td colspan="2"><h:outputText value="Razon Soc.:"/></td>
  	<td colspan="6">
		<h:inputText maxlength="11" value="#{usuarioPerfilController.usuario.persona.juridica.strRazonSocial}"  style="width:300px;"/>
	</td>
   <td colspan="2"><h:outputText value="Nom. Comerc.:" /></td>
   <td colspan="4">
   		<h:inputText value="#{usuarioPerfilController.usuario.persona.juridica.strNombreComercial}" label="Nombre Comercial" style="width:200px;"/>
   </td>
</tr>

<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.nombreComercial ||
					      not empty usuarioPerfilController.msgUsuario.razonSocial}">
<tr>
  	<td colspan="6">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.razonSocial}" styleClass="msgError" 
		      rendered="#{not empty usuarioPerfilController.msgUsuario.razonSocial}"/>
	</td>
	<td colspan="6">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.nombreComercial}" styleClass="msgError" 
		        	rendered="#{not empty usuarioPerfilController.msgUsuario.nombreComercial}"/>
  	</td>
</tr>
</h:panelGroup>

</h:panelGroup>
<tr><td colspan="16">&nbsp;</td></tr>
<tr>
<td colspan="12">
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
  	<td>
	   	<h:selectBooleanCheckbox value="#{usuarioPerfilController.chkDireccion}" >
		<a4j:support event="onclick" reRender="pgUsuario" 
		actionListener="#{usuarioPerfilController.checkAgregarDomicilio}" />
		</h:selectBooleanCheckbox>
   	</td>
   	<td colspan="2"><h:outputText value="Dirección"></h:outputText></td>
   	<td colspan="2">
    	<a4j:commandButton id="btnAgregarUsuarioDomicilio" value="Agregar" styleClass="btnEstilos"
    		actionListener="#{usuarioPerfilController.irAgregarDomicilio}" reRender="frmDomicilioAgregar"
    		disabled="#{not empty usuarioPerfilController.chkDireccion && !usuarioPerfilController.chkDireccion}">
		<rich:componentControl for="pAgregarDomicilio" attachTo="btnAgregarUsuarioDomicilio" operation="show" event="onclick"/>
  		</a4j:commandButton>
   	</td>
   	<td colspan="2">
   		<h:outputText value="#{usuarioPerfilController.msgUsuario.listaDomicilio}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaDomicilio}"/>
   	</td>
 </tr>
 <tr><td colspan="12">&nbsp;</td></tr>	
 <tr>
    <td></td>
    <td colspan="11" align="left">
	 <rich:dataTable id="tblDomicilio" var="itemDomicilio" sortMode="single" rows="3" cellpadding="0" cellspacing="0"
	 	value="#{usuarioPerfilController.usuario.persona.listaDomicilio}" rowKeyVar="rowKeyDomicilio" width="500px"
	 	rendered="#{not empty usuarioPerfilController.usuario.persona.listaDomicilio}">
 		<rich:column width="20px">
         	<h:outputText value="#{rowKeyDomicilio + 1}"></h:outputText>                        	
       	</rich:column>
       	<rich:column width="300px">
       			<h:outputText value="#{itemDomicilio.strNombreVia}-#{itemDomicilio.intNumeroVia}"/>
      			<h:outputText value="  "/>
      			<tumih:outputText value="#{usuarioPerfilController.listaUbigeoDepartamento}"
					itemValue="intIdUbigeo" itemLabel="strDescripcion"
					property="#{itemDomicilio.intParaUbigeoPkDpto}"/>
       	</rich:column>
       	<rich:column width="180px">
      			<a4j:commandButton value="Consultar" actionListener="#{usuarioPerfilController.consultarDomicilio}" reRender="frmDomicilioConsultar" styleClass="btnEstilos">
					<f:param name="pIndexDomicilio"  value="#{rowKeyDomicilio}" />
					<rich:componentControl for="pConsultarDomicilio" operation="show" event="onclick"/>      			
      			</a4j:commandButton>
      			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarDomicilio}" reRender="pgUsuario" styleClass="btnEstilos">
      				<f:param name="pIndexDomicilio"  value="#{rowKeyDomicilio}" />
      			</a4j:commandButton>
      	</rich:column>
		<f:facet name="footer"><rich:datascroller for="tblDomicilio" maxPages="10"/></f:facet>      	
    </rich:dataTable>
 	</td>
</tr>
<tr><td colspan="12">&nbsp;</td></tr>
<tr>
  	<td>
	   	<h:selectBooleanCheckbox value="#{usuarioPerfilController.chkComunicacion}" >
		<a4j:support event="onclick" reRender="pgUsuario" 
		actionListener="#{usuarioPerfilController.checkAgregarComunicacion}" />
		</h:selectBooleanCheckbox>
   	</td>
   	<td colspan="2"><h:outputText value="Comunicación"></h:outputText></td>
   	<td colspan="2">
    	<a4j:commandButton id="btnAgregarComunicacion" value="Agregar" styleClass="btnEstilos"
    		actionListener="#{usuarioPerfilController.irAgregarComunicacion}"  reRender="frmComunicacionAgregar"
    		disabled="#{not empty usuarioPerfilController.chkComunicacion && !usuarioPerfilController.chkComunicacion}">
       		<rich:componentControl for="pAgregarComunicacion" attachTo="btnAgregarComunicacion" operation="show" event="onclick"/>
       	</a4j:commandButton>
   	</td>
   	<td colspan="2">
   		<h:outputText value="#{usuarioPerfilController.msgUsuario.listaComunicacion}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaComunicacion}"/>
   	</td>
 </tr>
 <tr><td colspan="12">&nbsp;</td></tr>	
 <tr>
    <td></td>
    <td colspan="11" align="left">
    	<rich:dataTable id="tblComunicacion" var="itemComunicacion" sortMode="single" rows="3" cellpadding="0" cellspacing="0"
	 	value="#{usuarioPerfilController.usuario.persona.listaComunicacion}" rowKeyVar="rowKeyComunicacion" width="500px"
	 	rendered="#{not empty usuarioPerfilController.usuario.persona.listaComunicacion}">
 		<rich:column width="20px">
         	<h:outputText value="#{rowKeyComunicacion + 1}"></h:outputText>                        	
       	</rich:column>
       	<rich:column width="300px">
       			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCOMUNICACION}"
					itemValue="intIdDetalle" itemLabel="strDescripcion"
					property="#{itemComunicacion.intTipoComunicacionCod}"/>
				<h:outputText value="  "/>
      			<h:panelGroup rendered="#{itemComunicacion.intTipoComunicacionCod == applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO}">
					<h:outputText value="#{itemComunicacion.intNumero} #{itemComunicacion.intAnexo}"/>      
      			</h:panelGroup>
      			<h:panelGroup rendered="#{itemComunicacion.intTipoComunicacionCod != applicationScope.Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO}">
      				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}"
						itemValue="intIdDetalle" itemLabel="strDescripcion"
						property="#{itemComunicacion.intSubTipoComunicacionCod}"/>
					<h:outputText value="  "/>
      				<h:outputText value="#{itemComunicacion.strDescripcion}"/>
      			</h:panelGroup>
       	</rich:column>
       	<rich:column width="180px">
      			<a4j:commandButton value="Consultar" actionListener="#{usuarioPerfilController.consultarComunicacion}" reRender="frmComunicacionConsultar" styleClass="btnEstilos">
					<f:param name="pIndexComunicacion"  value="#{rowKeyComunicacion}" />
					<rich:componentControl for="pConsultarComunicacion" operation="show" event="onclick"/>      			
      			</a4j:commandButton>
      			<a4j:commandButton value="Eliminar" styleClass="btnEstilos" actionListener="#{usuarioPerfilController.eliminarComunicacion}" reRender="pgUsuario">
      				<f:param name="pIndexComunicacion"  value="#{rowKeyComunicacion}" />
      			</a4j:commandButton>
      	</rich:column>
		<f:facet name="footer"><rich:datascroller for="tblComunicacion" maxPages="10"/></f:facet>      	
    	</rich:dataTable>
 	</td>
</tr>
</table>
</td>
<td colspan="8">
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
	</tr>
	<tr>
	<td colspan="8">
	<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">	
	 <rich:fileUpload id="uploadFoto" fileUploadListener="#{usuarioPerfilController.listener}"
  	   addControlLabel="Adjuntar Imagen" clearControlLabel="Limpiar" 
  	   cancelEntryControlLabel="Cancelar" uploadControlLabel="Subir Archivo"
       maxFilesQuantity="1" listWidth="250" listHeight="40" doneLabel="Imagen cargada correctamente"
       immediateUpload="#{fileUploadController.autoUpload}"
       acceptedTypes="jpg" allowFlash="#{fileUploadController.useFlash}">
       <f:facet name="label">
		<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
	   </f:facet>
   	   </rich:fileUpload>
   	   <div align="center">
	   <h:graphicImage value="#{usuarioPerfilController.usuario.strImagen}"
	   		width="80px" height="60px" rendered="#{usuarioPerfilController.imgUsuario}"/>
	   </div>
	</h:panelGroup>	   
	</td>
	</tr>	
	</table>
</td>
<tr>
<tr><td colspan="16">&nbsp;</td></tr>
<tr>
	<td colspan="2"><h:outputText value="Empresa"></h:outputText></td>
	<td colspan="2">
 	<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregarEmpresaUsuario}" styleClass="btnEstilos" reRender="pgUsuario"/>
  	</td>
  	<td colspan="2">
		<h:outputText value="#{usuarioPerfilController.msgUsuario.listaEmpresaUsuario}" styleClass="msgError" 
        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaEmpresaUsuario}"/>
  	</td>
  	<td colspan="10"></td>
</tr>
<tr><td colspan="16">&nbsp;</td></tr>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioPerfil}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.listaUsuarioPerfil}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioPerfil}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.idPerfil}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.idPerfil}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.idPerfil}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.desdePerfil}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.desdePerfil}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.desdePerfil}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.hastaPerfil}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.hastaPerfil}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.hastaPerfil}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.listaUsuarioSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.idSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.idSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.idSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.desdeSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.desdeSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.desdeSucursal}"/>
	</td>		
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.hastaSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.hastaSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.hastaSucursal}"/>
	</td>		
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioSubSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.listaUsuarioSubSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.listaUsuarioSubSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.idSubSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.idSubSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.idSubSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.desdeSubSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.desdeSubSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.desdeSubSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<h:panelGroup rendered="#{not empty usuarioPerfilController.msgUsuario.hastaSubSucursal}">
<tr>
	<td colspan="16">
			<h:outputText value="#{usuarioPerfilController.msgUsuario.hastaSubSucursal}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.hastaSubSucursal}"/>
	</td>
</tr>
</h:panelGroup>
<tr><td colspan="16">&nbsp;</td></tr>
<tr>
  	<td></td>
    <td colspan="15" align="left">
		<a4j:repeat value="#{usuarioPerfilController.usuario.listaEmpresaUsuario}" var="itemEmpresa" 
			rendered="#{not empty usuarioPerfilController.usuario.listaEmpresaUsuario}" rowKeyVar="rowKeyEmpresa">
        <table style="border:1px solid #17356f;">
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
		</tr>
        <tr>
        	<td colspan="2">Empresa</td>
        	<td colspan="2">
        		<h:selectOneMenu value="#{itemEmpresa.id.intPersEmpresaPk}" disabled="#{itemEmpresa.persiste}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaJuridicaEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
				<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeEmpresa}" reRender="pgUsuario">
					<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
					<f:param name="pIntPersEmpresaPk"  value="#{itemEmpresa.id.intPersEmpresaPk}" />
					<f:param name="pIntPersPersonaPk"  value="#{itemEmpresa.id.intPersPersonaPk}" />
				</a4j:support>		
				</h:selectOneMenu>
           	</td>
           	<td colspan="9"></td>
			<td colspan="2">
				<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarEmpresaUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				</a4j:commandButton>									
			</td>
		</tr>
		<tr><td colspan="15">&nbsp;</td></tr>
		<tr>
			<td></td>
		    <td colspan="3">Perfil de Usuario</td>
			<td colspan="2">
				<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregarPerfilUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				</a4j:commandButton>
			</td>
			<td colspan="9"></td>
		</tr>
		<tr>
		<td></td>
		<td colspan="14" align="left">
		<table style="border:1px solid #17356f;">
		<tr>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
			<td style="width:20px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
		</tr>	
		<a4j:repeat value="#{itemEmpresa.listaUsuarioPerfil}" rendered="#{not empty itemEmpresa.listaUsuarioPerfil}"  
		            var="itemPerfil" rowKeyVar="rowKeyPerfil">
         <tr>
           	<td colspan="3">
           		<h:selectOneMenu value="#{itemPerfil.id.intIdPerfil}" disabled="#{itemPerfil.persiste}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{itemEmpresa.listaPerfil}"
						itemValue="#{sel.id.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
				<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDePerfil}" reRender="pgUsuario">
					<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
					<f:param name="pIndexPerfil"  value="#{rowKeyPerfil}" />
					<f:param name="pIntPersEmpresaPk"  value="#{itemPerfil.id.intPersEmpresaPk}" />
					<f:param name="pIntPersPersonaPk"  value="#{itemPerfil.id.intPersPersonaPk}" />
					<f:param name="pIntIdPerfil"  value="#{itemPerfil.id.intIdPerfil}" />
					<f:param name="pDtDesde"  value="#{itemPerfil.id.dtDesde}" />
				</a4j:support>
				</h:selectOneMenu>
		    </td>
			<td>
				<h:selectBooleanCheckbox value="#{itemPerfil.blnIndeterminado}" disabled="#{itemPerfil.persiste}">
				<a4j:support event="onclick" actionListener="#{usuarioPerfilController.seleccionarFechaDePerfilUsuario}" rendered="true" reRender="pgUsuario">
					<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
					<f:param name="pIndexPerfil"  value="#{rowKeyPerfil}" />
				</a4j:support>
				</h:selectBooleanCheckbox>
			</td>
			<td>
				Indeterminado
			</td>
			<td>
				<rich:calendar popup="true" inputStyle="width:70px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px"  
		          value="#{itemPerfil.id.dtDesde}" disabled="#{itemPerfil.persiste}" cellHeight="20px"/>
			</td>    
			<td>
				<rich:calendar popup="true" inputStyle="width:70px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px"   
		          value="#{itemPerfil.dtHasta}" disabled="#{itemPerfil.persiste || itemPerfil.blnIndeterminado}" cellHeight="20px"/>
			</td>
			<td>
				<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarPerfilUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexPerfil"  value="#{rowKeyPerfil}" />
				</a4j:commandButton>
			</td>
	</tr>
	</a4j:repeat>
	</table>
	</td>
	</tr>
	<tr><td colspan="15">&nbsp;</td></tr>
	<tr>
		<td></td>
		<td colspan="3">Sucursal</td>
		<td colspan="2">
			<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregaSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
			<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
			</a4j:commandButton>
		</td>
		<td colspan="9"></td>
	</tr>
	<tr>
		<td colspan="15">&nbsp;</td>
	</tr>
		<a4j:repeat value="#{itemEmpresa.listaUsuarioSucursal}" 
		rendered="#{not empty itemEmpresa.listaUsuarioSucursal}" var="itemSucursal" rowKeyVar="rowKeySucursal">
    <tr>
    <td></td>
	<td colspan="14" align="left">	
   	<table style="border:1px solid #17356f;">
   	<tr>
   			<td style="width:50px;"></td>
   			<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
			<td style="width:20px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
			<td style="width:100px;"></td>
	</tr>		
 	<tr>
   		<td colspan="3">
       		<h:selectOneMenu value="#{itemSucursal.id.intIdSucursal}" disabled="#{itemSucursal.persiste}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{itemEmpresa.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeSucursal}" reRender="pgUsuario">
					<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
					<f:param name="pIndexSucursal"  value="#{rowKeySucursal}" />
					<f:param name="pIntPersEmpresaPk"  value="#{itemSucursal.id.intPersEmpresaPk}" />
					<f:param name="pIntIdSucursal"  value="#{itemSucursal.id.intIdSucursal}" />
					<f:param name="pIntPersPersonaPk"  value="#{itemSucursal.id.intPersPersonaPk}" />
					<f:param name="pDtFechaRegistro"  value="#{itemSucursal.id.dtFechaRegistro}" />
				</a4j:support>		
			</h:selectOneMenu>
        </td>
		<td>
			<h:selectBooleanCheckbox value="#{itemSucursal.blnIndeterminado}" disabled="#{itemSucursal.persiste}">
			<a4j:support event="onclick" actionListener="#{usuarioPerfilController.seleccionarFechaDeSucursalUsuario}" rendered="true" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSucursal"  value="#{rowKeySucursal}" />
			</a4j:support>	
			</h:selectBooleanCheckbox>
		</td>
		<td>
			Indeterminado
		</td>
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" 
               	value="#{itemSucursal.dtDesde}" disabled="#{itemSucursal.persiste}"
                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
        </td>    
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" 
               	value="#{itemSucursal.dtHasta}" disabled="#{itemSucursal.persiste || itemSucursal.blnIndeterminado}"
                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>
		<td>
			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSucursal"  value="#{rowKeySucursal}" />
			</a4j:commandButton>
		</td>
   </tr>
   <tr><td colspan="8">&nbsp;</td></tr>
   <tr>
		<td colspan="2">Sub-Sucursal</td>
		<td colspan="6" align="left">
			<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregaSubSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSucursal"  value="#{rowKeySucursal}" />
			</a4j:commandButton>
		</td>
	</tr>
			<a4j:repeat value="#{itemEmpresa.listaUsuarioSubSucursal}"
				rendered="#{not empty itemEmpresa.listaUsuarioSubSucursal}"  
         			var="itemSubSucursal" rowKeyVar="rowKeySubSucursal">
        			<h:panelGroup rendered="#{itemSubSucursal.id.intIdSucursal == itemSucursal.id.intIdSucursal}">
    <tr>
       	<td colspan="3">
	        <h:selectOneMenu value="#{itemSubSucursal.id.intIdSubSucursal}" disabled="#{itemSubSucursal.persiste}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{itemSucursal.listaSubSucursal}"
					itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeSubSucursal}" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSucursal"  value="#{rowKeySucursal}" />
				<f:param name="pIndexSubSucursal"  value="#{rowKeySubSucursal}" />
				<f:param name="pIntPersEmpresaPk"  value="#{itemSubSucursal.id.intPersEmpresaPk}" />
				<f:param name="pIntIdSucursal"  value="#{itemSubSucursal.id.intIdSucursal}" />
				<f:param name="pIntIdSubSucursal"  value="#{itemSubSucursal.id.intIdSubSucursal}" />
				<f:param name="pDtFechaRegistro"  value="#{itemSubSucursal.id.dtFechaRegistro}" />
				<f:param name="pIntPersPersonaPk"  value="#{itemSubSucursal.id.intPersPersonaPk}" />
			</a4j:support>
			</h:selectOneMenu>
        </td>
		<td>
			<h:selectBooleanCheckbox value="#{itemSubSucursal.blnIndeterminado}" disabled="#{itemSubSucursal.persiste}">
			<a4j:support event="onclick" actionListener="#{usuarioPerfilController.seleccionarFechaDeSubSucursalUsuario}" rendered="true" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSubSucursal"  value="#{rowKeySubSucursal}" />
			</a4j:support>
			</h:selectBooleanCheckbox>
		</td>
		<td>
			Indeterminado
		</td>
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" 
		               	value="#{itemSubSucursal.dtDesde}" disabled="#{itemSubSucursal.persiste}"
		                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>    
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" 
		               	value="#{itemSubSucursal.dtHasta}" disabled="#{itemSubSucursal.persiste || itemSubSucursal.blnIndeterminado}"
		                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>
		<td>
			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarSubSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				<f:param name="pIndexSubSucursal"  value="#{rowKeySubSucursal}" />
			</a4j:commandButton>
		</td>
    </tr>
   	</h:panelGroup>
    </a4j:repeat>
   	<tr><td colspan="8">&nbsp;</td></tr>
   	</table>
   	</td>
   	</tr>
   	<tr><td colspan="15">&nbsp;</td></tr>
   	</a4j:repeat>
   	<tr><td colspan="15">&nbsp;</td></tr>
   	<tr>
   		<td colspan="4" align="left">Validaciones Especiales</td>
   		<td colspan="11"></td>
   	</tr>
   	<tr>
   		<td>
   			<h:selectBooleanCheckbox value="#{itemEmpresa.blnControlHoraIngreso}">
			<a4j:support event="onclick" actionListener="#{usuarioPerfilController.checkControlHoraIngreso}" rendered="true" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
			</a4j:support>
			</h:selectBooleanCheckbox>
   		</td>
   		<td colspan="6" align="left">
	   		El Sistema controla hora de ingreso
   		</td>
   		<td colspan="8"></td>
   	</tr>
   	<tr>
   		<td><h:selectBooleanCheckbox value="#{itemEmpresa.blnCambioClave}">
			<a4j:support event="onclick" actionListener="#{usuarioPerfilController.checkCambioClave}" rendered="true" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
			</a4j:support>
			</h:selectBooleanCheckbox>
		</td>
   		<td colspan="6" align="left">
	   		El Sistema permite al usuario cambiar su clave
   		</td>
   		<td colspan="8"></td>
   	</tr>
   	<tr>
   		<td align="left">
   			<h:selectBooleanCheckbox value="#{itemEmpresa.blnControlVigenciaClave}">
			<a4j:support event="onclick" actionListener="#{usuarioPerfilController.checkControlVigenciaClave}" rendered="true" reRender="pgUsuario">
				<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
			</a4j:support>
			</h:selectBooleanCheckbox>
   		</td>
   		<td colspan="6" align="left">
   			Vigencia de Claves
   		</td>
   		<td colspan="5">
   			<h:selectOneRadio value="#{itemEmpresa.intRadioControlVigenciaClave}"
   				disabled="#{empty itemEmpresa.blnControlVigenciaClave ||
   							!itemEmpresa.blnControlVigenciaClave}">
   				<f:selectItem itemValue="0" itemLabel="Indeterminado" />
   				<f:selectItem itemValue="1" itemLabel="Días" />
   				<a4j:support event="onclick" actionListener="#{usuarioPerfilController.radioDiasVigencia}" rendered="true" reRender="pgUsuario">
					<f:param name="pIndexEmpresa"  value="#{rowKeyEmpresa}" />
				</a4j:support>
			</h:selectOneRadio>   				
   		</td>
   		<td colspan="3" align="left">
   			<h:inputText value="#{itemEmpresa.intDiasVigencia}" size="10" 
   				disabled="#{empty itemEmpresa.blnControlVigenciaClave ||
   						    !itemEmpresa.blnControlVigenciaClave ||
   							empty itemEmpresa.intRadioControlVigenciaClave || 
   							itemEmpresa.intRadioControlVigenciaClave == 0}"/>		
   		</td>
   	</tr>
	</table>
	<br/>
    </a4j:repeat>
    </td>
  </tr>

<tr>
  	<td colspan="3">
  		<h:outputText value="Nombre de Usuario" ></h:outputText>
  	</td>
  	<td colspan="2">
	  	<h:inputText label="Nombre de Usuario" value="#{usuarioPerfilController.usuario.strUsuario}" label="Nombre de Usuario"></h:inputText>
  	</td>
  	<td colspan="2">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.usuario}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.usuario}"/>
  	</td>
  	<td colspan="9"></td>
</tr>

<tr>
  	<td colspan="3">
  		<h:outputText value="Contraseña" ></h:outputText>
  	</td>
  	<td colspan="2">
  		<h:inputSecret id="idClave" label="Contraseña" value="#{usuarioPerfilController.usuario.strContrasena}" redisplay="true"/>
  	</td>
  	<td colspan="2">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.contrasena}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.contrasena}"/>
  	</td>
	<td colspan="9"></td>
</tr>

<tr>
  	<td colspan="3">
  		<h:outputText value="Validar Contraseña" ></h:outputText>
  	</td>
  	<td colspan="2">
  		<h:inputSecret id="idClave2" value="#{usuarioPerfilController.usuario.strContrasenaValidacion}" redisplay="true" />
  	</td>
  	<td colspan="2">
  		<h:outputText value="#{usuarioPerfilController.msgUsuario.contrasenaValida}" styleClass="msgError" 
			        	rendered="#{not empty usuarioPerfilController.msgUsuario.contrasenaValida}"/>
  	</td>
	<td colspan="9"></td>
</tr>
</table>


</h:column>	
</h:panelGrid>
