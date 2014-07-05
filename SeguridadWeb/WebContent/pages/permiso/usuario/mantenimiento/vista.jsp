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
<tr>
  	<td colspan="2"><h:outputText value="Tipo Pers.:" ></h:outputText></td>
  	<td colspan="2">
  		<h:selectOneMenu value="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod}" disabled="true" style="width:100px;">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			<a4j:support event="onchange" actionListener="#{usuarioPerfilController.onchangeDeTipoPersona}" reRender="pgUsuario"/>
		</h:selectOneMenu>
	</td>
	<td colspan="2"><h:outputText value="Tipo Usuario"/></td>
	<td colspan="2">
		<h:selectOneMenu value="#{usuarioPerfilController.usuario.intTipoUsuario}" style="width:100px;" disabled="true">
			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOUSUARIO}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
	<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
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
	</h:panelGroup>
	<td colspan="2"><h:outputText value="Estado" ></h:outputText></td>
	<td colspan="2">
    	<h:selectOneMenu value="#{usuarioPerfilController.usuario.intIdEstado}" style="width:100px;" disabled="true">
			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
	</td>
</tr>

<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
<tr>
	<td colspan="2" align="left"><h:outputText value="Ap. Paterno:" ></h:outputText></td>
	<td colspan="4" align="left">
		  	<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strApellidoPaterno}" label="Apellido Paterno" style="width:200px;"  disabled="true"/>
	</td>
	<td colspan="2" align="left"><h:outputText value="Ap. Materno:" ></h:outputText></td>
  	<td colspan="4" align="left">
	  	<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strApellidoMaterno}" label="Apellido Materno" style="width:200px;"  disabled="true"/>
  	</td>
  	<td colspan="2" align="left"><h:outputText value="Nombres:" ></h:outputText></td>
  	<td colspan="4" align="left">
  		<h:inputText value="#{usuarioPerfilController.usuario.persona.natural.strNombres}" style="width:200px;"  disabled="true"/>
  	</td>
</tr>
</h:panelGroup>

<h:panelGroup rendered="#{usuarioPerfilController.usuario.persona.intTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}">
<tr>
  	<td colspan="2"><h:outputText value="Razon Soc.:"/></td>
  	<td colspan="6">
		<h:inputText maxlength="11" value="#{usuarioPerfilController.usuario.persona.juridica.strRazonSocial}" style="width:300px;" disabled="true"/>
	</td>
   <td colspan="2"><h:outputText value="Nom. Comerc.:" /></td>
   <td colspan="4">
   		<h:inputText value="#{usuarioPerfilController.usuario.persona.juridica.strNombreComercial}" label="Nombre Comercial" style="width:200px;"  disabled="true"/>
   </td>
</tr>

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
	   	<h:selectBooleanCheckbox value="#{usuarioPerfilController.chkDireccion}"  disabled="true">
		<a4j:support event="onclick" reRender="pgUsuario" 
		actionListener="#{usuarioPerfilController.checkAgregarDomicilio}" />
		</h:selectBooleanCheckbox>
   	</td>
   	<td colspan="2"><h:outputText value="Dirección"></h:outputText></td>
   	<td colspan="2">
    	<a4j:commandButton id="btnAgregarUsuarioDomicilio" value="Agregar" styleClass="btnEstilos" 
    		actionListener="#{usuarioPerfilController.irAgregarDomicilio}" reRender="frmDomicilioAgregar"
    		disabled="true">
		<rich:componentControl for="pAgregarDomicilio" attachTo="btnAgregarUsuarioDomicilio" operation="show" event="onclick"/>
  		</a4j:commandButton>
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
      			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarDomicilio}" reRender="pgUsuario" styleClass="btnEstilos"  disabled="true">
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
	   	<h:selectBooleanCheckbox value="#{usuarioPerfilController.chkComunicacion}"  disabled="true">
		</h:selectBooleanCheckbox>
   	</td>
   	<td colspan="2"><h:outputText value="Comunicación"></h:outputText></td>
   	<td colspan="2">
    	<a4j:commandButton id="btnAgregarComunicacion" value="Agregar" styleClass="btnEstilos"
    		actionListener="#{usuarioPerfilController.irAgregarComunicacion}"  reRender="frmComunicacionAgregar"
    		disabled="true">
       		<rich:componentControl for="pAgregarComunicacion" attachTo="btnAgregarComunicacion" operation="show" event="onclick"/>
       	</a4j:commandButton>
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
      			<a4j:commandButton value="Eliminar" styleClass="btnEstilos" actionListener="#{usuarioPerfilController.eliminarComunicacion}" reRender="pgUsuario" disabled="true">
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
	 <rich:fileUpload id="uploadFoto" fileUploadListener="#{usuarioPerfilController.listener}" disabled="true"
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
 	<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregarEmpresaUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true"/>
  	</td>
</tr>
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
        		<h:selectOneMenu value="#{itemEmpresa.id.intPersEmpresaPk}" disabled="#{itemEmpresa.persiste}" disabled="true">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{usuarioPerfilController.listaJuridicaEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
				</h:selectOneMenu>
           	</td>
           	<td colspan="9"></td>
			<td colspan="2">
				<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarEmpresaUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
				</a4j:commandButton>									
			</td>
		</tr>
		<tr><td colspan="15">&nbsp;</td></tr>
		<tr>
			<td></td>
		    <td colspan="3">Perfil de Usuario</td>
			<td colspan="2">
				<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregarPerfilUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
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
           		<h:selectOneMenu value="#{itemPerfil.id.intIdPerfil}" disabled="#{itemPerfil.persiste}" disabled="true">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{itemEmpresa.listaPerfil}"
						itemValue="#{sel.id.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
		    </td>
			<td>
				<h:selectBooleanCheckbox value="#{itemPerfil.blnIndeterminado}" disabled="#{itemPerfil.persiste}" disabled="true">
				</h:selectBooleanCheckbox>
			</td>
			<td>
				Indeterminado
			</td>
			<td>
				<rich:calendar popup="true" inputStyle="width:70px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" disabled="true"  
		          value="#{itemPerfil.id.dtDesde}" disabled="#{itemPerfil.persiste}" cellHeight="20px"/>
			</td>    
			<td>
				<rich:calendar popup="true" inputStyle="width:70px;" datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" disabled="true"
		          value="#{itemPerfil.dtHasta}" disabled="#{itemPerfil.persiste || itemPerfil.blnIndeterminado}" cellHeight="20px"/>
			</td>
			<td>
				<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarPerfilUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
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
			<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregaSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
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
       		<h:selectOneMenu value="#{itemSucursal.id.intIdSucursal}" disabled="true">
				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{itemEmpresa.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
			</h:selectOneMenu>
        </td>
		<td>
			<h:selectBooleanCheckbox value="#{itemSucursal.blnIndeterminado}" disabled="#{itemSucursal.persiste}" disabled="true">
			</h:selectBooleanCheckbox>
		</td>
		<td>
			Indeterminado
		</td>
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" disabled="true" 
               	value="#{itemSucursal.dtDesde}" disabled="#{itemSucursal.persiste}"
                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
        </td>    
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" disabled="true"
               	value="#{itemSucursal.dtHasta}" disabled="#{itemSucursal.persiste || itemSucursal.blnIndeterminado}"
                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>
		<td>
			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
			</a4j:commandButton>
		</td>
   </tr>
   <tr><td colspan="8">&nbsp;</td></tr>
   <tr>
		<td colspan="2">Sub-Sucursal</td>
		<td colspan="6" align="left">
			<a4j:commandButton value="Agregar" actionListener="#{usuarioPerfilController.agregaSubSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
			</a4j:commandButton>
		</td>
	</tr>
			<a4j:repeat value="#{itemEmpresa.listaUsuarioSubSucursal}"
				rendered="#{not empty itemEmpresa.listaUsuarioSubSucursal}"  
         			var="itemSubSucursal" rowKeyVar="rowKeySubSucursal">
        			<h:panelGroup rendered="#{itemSubSucursal.id.intIdSucursal == itemSucursal.id.intIdSucursal}">
    <tr>
       	<td colspan="3">
	        <h:selectOneMenu value="#{itemSubSucursal.id.intIdSubSucursal}" disabled="true">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			<tumih:selectItems var="sel" value="#{itemSucursal.listaSubSucursal}"
					itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
			</h:selectOneMenu>
        </td>
		<td>
			<h:selectBooleanCheckbox value="#{itemSubSucursal.blnIndeterminado}" disabled="true">
			</h:selectBooleanCheckbox>
		</td>
		<td>
			Indeterminado
		</td>
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" disabled="true" 
		               	value="#{itemSubSucursal.dtDesde}" disabled="#{itemSubSucursal.persiste}"
		                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>    
		<td>
			<rich:calendar popup="true" inputStyle="width:70px;" disabled="true"
		               	value="#{itemSubSucursal.dtHasta}" disabled="#{itemSubSucursal.persiste || itemSubSucursal.blnIndeterminado}"
		                datePattern="dd/MM/yyyy HH:mm" cellWidth="10px" cellHeight="20px"/>
		</td>
		<td>
			<a4j:commandButton value="Eliminar" actionListener="#{usuarioPerfilController.eliminarSubSucursalUsuario}" styleClass="btnEstilos" reRender="pgUsuario" disabled="true">
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
   			<h:selectBooleanCheckbox value="#{itemEmpresa.blnControlHoraIngreso}" disabled="true">
			</h:selectBooleanCheckbox>
   		</td>
   		<td colspan="6" align="left">
	   		El Sistema controla hora de ingreso
   		</td>
   		<td colspan="8"></td>
   	</tr>
   	<tr>
   		<td><h:selectBooleanCheckbox value="#{itemEmpresa.blnCambioClave}" disabled="true">
			</h:selectBooleanCheckbox>
		</td>
   		<td colspan="6" align="left">
	   		El Sistema permite al usuario cambiar su clave
   		</td>
   		<td colspan="8"></td>
   	</tr>
   	<tr>
   		<td align="left">
   			<h:selectBooleanCheckbox value="#{itemEmpresa.blnControlVigenciaClave}" disabled="true">
			</h:selectBooleanCheckbox>
   		</td>
   		<td colspan="6" align="left">
   			Vigencia de Claves
   		</td>
   		<td colspan="5">
   			<h:selectOneRadio value="#{itemEmpresa.intRadioControlVigenciaClave}"
   				disabled="true">
   				<f:selectItem itemValue="0" itemLabel="Indeterminado" />
   				<f:selectItem itemValue="1" itemLabel="Días" />
			</h:selectOneRadio>   				
   		</td>
   		<td colspan="3" align="left">
   			<h:inputText value="#{itemEmpresa.intDiasVigencia}" size="10" 
   				disabled="true"/>		
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
	  	<h:inputText label="Nombre de Usuario" value="#{usuarioPerfilController.usuario.strUsuario}" label="Nombre de Usuario" disabled="true"></h:inputText>
  	</td>
</tr>

<tr>
  	<td colspan="3">
  		<h:outputText value="Contraseña" ></h:outputText>
  	</td>
  	<td colspan="2">
  		<h:inputSecret id="idClave" label="Contraseña" value="#{usuarioPerfilController.usuario.strContrasena}" redisplay="true" disabled="true"/>
  	</td>
</tr>

<tr>
  	<td colspan="3">
  		<h:outputText value="Validar Contraseña" ></h:outputText>
  	</td>
  	<td colspan="2">
  		<h:inputSecret id="idClave2" value="#{usuarioPerfilController.usuario.strContrasenaValidacion}" redisplay="true" disabled="true"/>
  	</td>
</tr>
</table>


</h:column>	
</h:panelGrid>
