<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->

<script type="text/javascript">	  
	  
</script>
<rich:panel id="pMarcoZonal" style="border:1px solid #17356f ;width: 780px; background-color:#DEEBF5">
<h:panelGrid id="pGrid1" columns="6" style="width: 700px" border="0" >
   	<rich:column style="width: 80px; border: none">
   		<h:outputText id="lblEmpresaZonal" value="Empresa :"  style="padding-left: 15px;" > </h:outputText>
   	</rich:column>
    <rich:column >
    	<h:selectOneMenu value="#{empresaController.cboEmpresaZonal}">
		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
		itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
		</h:selectOneMenu>
    </rich:column>
    <rich:column>
        <h:outputText id="lblTipoZonal" value="Tipo :" > </h:outputText>
    </rich:column>
    <rich:column >
		<h:selectOneMenu id="cboTipoZona" value="#{empresaController.cboTipoZonal}">
	        <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	        <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOZONAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>   
    </rich:column>
    <rich:column >
        <h:outputText id="lblEstadoZona" value="Estado :" > </h:outputText>
    </rich:column>
    <rich:column>
  		<h:selectOneMenu id="cboEstadoZona" value="#{empresaController.cboEstadoZonal}">
           <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
           <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
			tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
  		</h:selectOneMenu>
 	</rich:column>
</h:panelGrid>
            
<rich:spacer height="8px"/><rich:spacer height="8px"/>
<rich:spacer height="8px"/><rich:spacer height="8px"/>
<h:panelGrid id="pgBusqueda" columns="3" style="width: 710px;">
  	<rich:column style="width: 80px; border: none">
  		<h:outputText  id="lblBusqZonal" value="Zonal :" style="padding-left:15px" ></h:outputText>
  	</rich:column>
  	<rich:column style="width: 280px;">
  		<h:inputText id="txtBusqZonal" size="50" value="#{empresaController.txtZonal}"></h:inputText>
  	</rich:column>
  	<rich:column>
  		<a4j:commandButton id="btnBusqZonal" value="Buscar" styleClass="btnEstilos" actionListener="#{empresaController.listarZonal}" reRender="pgBusqZonal"/>
  	</rich:column>
</h:panelGrid>
<br/>
<div style="width:100%; overflow-x:auto; overflow-y:hidden; padding-left: 30px">
<h:panelGrid id="pgBusqZonal" columns="1">
<rich:extendedDataTable id="tblRegistros1" value="#{empresaController.listaZonalComp}" 
	var="item" rowKeyVar="rowKey" sortMode="single"  width="660px" height="168px" rows="5"
	onRowClick="jsSeleccionZonal(#{item.zonal.id.intIdzonal});" rendered="#{not empty empresaController.listaZonalComp}">
	<rich:column width="29px">
		<h:outputText  value="#{rowKey+1}"></h:outputText>
	</rich:column>
    <rich:column width="190" sortBy="#{item.zonal.juridica.strSiglas}">
        <f:facet name="header"><h:outputText   value="Nombre de Zonal" ></h:outputText></f:facet>
        <h:outputText id="colNombreZonal" value="#{item.zonal.juridica.strRazonSocial}"></h:outputText>
        <rich:toolTip for="colNombreZonal" value="#{item.zonal.juridica.strRazonSocial}"></rich:toolTip>
    </rich:column>
    <rich:column sortBy="#{item.zonal.intIdTipoZonal}">
        <f:facet name="header"><h:outputText   value="Tipo" ></h:outputText></f:facet>
        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOZONAL}" 
    		 itemValue="intIdDetalle" itemLabel="strDescripcion" property="#{item.zonal.intIdTipoZonal}"/>
    	<%--	 
        <h:selectOneMenu value="#{item.zonal.intIdTipoZonal}" disabled="true">
			  <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOZONAL}" 
				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		</h:selectOneMenu>
		 --%>
    </rich:column>
    <rich:column sortBy="#{item.intCantidadSucursal}" width="110x">
        <f:facet name="header"><h:outputText  value="# Sucur" ></h:outputText></f:facet>
        <h:outputText value="#{item.intCantidadSucursal}"></h:outputText>
    </rich:column>
    <rich:column sortBy="#{item.zonal.juridica.strSiglas}" width="110px">
        <f:facet name="header"><h:outputText  value="Abrev" ></h:outputText></f:facet>
        <h:outputText  value="#{item.zonal.juridica.strSiglas}"></h:outputText>
    </rich:column>
    <rich:column sortBy="#{item.empresa.strRazonSocial}" width="132px">
        <f:facet name="header"><h:outputText  value="Empresa" ></h:outputText></f:facet>
        <h:outputText id="colEmpresaZonal" value="#{item.empresa.strRazonSocial}"></h:outputText>
        <rich:toolTip for="colEmpresaZonal" value="#{item.empresa.strRazonSocial}"></rich:toolTip>
    </rich:column>

	<f:facet name="footer">
	<rich:datascroller for="tblRegistros1" maxPages="10"></rich:datascroller>
	</f:facet>
</rich:extendedDataTable>                                                       

<h:panelGrid columns="2" border="">
<h:outputLink value="#" id="linkPanel">
    <h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
    <rich:componentControl for="panelUpdateDeleteZonal" attachTo="linkPanel" operation="show" event="onclick"/>
</h:outputLink>
<h:outputText value="Para Eliminar o Modificar un Sucursal hacer click en el Registro" style="color: #4449a5"></h:outputText>
</h:panelGrid>
</h:panelGrid>
</div>
		
<rich:spacer height="14px"/><rich:spacer height="14px"/> 
<h:panelGrid columns="3">
     <h:commandButton value="Nuevo" actionListener="#{empresaController.irGrabarZonal}" styleClass="btnEstilos">                     
     </h:commandButton>
 	<h:panelGroup rendered="#{empresaController.strTipoMantZonal == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
<h:commandButton value="Guardar" actionListener="#{empresaController.grabarZonal}" styleClass="btnEstilos"/>
     </h:panelGroup>
     <h:panelGroup rendered="#{empresaController.strTipoMantZonal == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
     <h:commandButton value="Guardar" actionListener="#{empresaController.modificarZonal}" styleClass="btnEstilos"/>
     </h:panelGroup>
     <h:commandButton value="Cancelar" actionListener="#{empresaController.cancelarGrabarZonal}" styleClass="btnEstilos">
     </h:commandButton>
</h:panelGrid>
<rich:spacer height="4px"/>
 
<!--Plantilla Inicial-->  
<rich:panel id="pMarcoZonal1" rendered="#{empresaController.zonalRendered}" style="width: 650px;border:1px solid #17356f;background-color:#DEEBF5;" >
    <h:panelGrid border="0" columns="4">
    	<rich:column  width="170">
    		<h:outputText value="Nombre de Empresa :" style="padding-left: 15px" ></h:outputText>
    	</rich:column>
    	<rich:column  colspan="3">
    		<h:selectOneMenu disabled="#{empresaController.formZonalDisabled}"
             	required="true" style="width: 300px;" value="#{empresaController.beanZonal.id.intPersEmpresaPk}">      
   				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
				itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
				<a4j:support event="onchange" actionListener="#{empresaController.getListaResponsableySucursalDeZonal}" reRender="pMarcoZonal1"/>
			</h:selectOneMenu>
    	</rich:column>
    </h:panelGrid>
    <h:panelGrid>
    <h:outputText value="#{empresaController.msgEmpresaZonalError}" styleClass="msgError"></h:outputText>
     </h:panelGrid>
    <h:panelGrid border="0" columns="5">
    	<rich:column  width="170">
    		<h:outputText id="lblTipoZonal1" value="Tipo de Zonal :" style="padding-left: 15px" ></h:outputText>
    	</rich:column>
    	<rich:column >
    		<h:selectOneMenu id="cboTipoZonal" value="#{empresaController.beanZonal.intIdTipoZonal}" disabled="#{empresaController.formZonalDisabled}">
     	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
     	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOZONAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          </h:selectOneMenu>
    	</rich:column>
    	<rich:column >
    		<h:outputText id="lblEstadoZonal" value="Estado :" ></h:outputText>
    	</rich:column>
    	<rich:column >
    		<h:panelGroup rendered="#{empresaController.strTipoMantZonal == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	    		<h:selectOneMenu value="#{empresaController.beanZonal.intIdEstado}" disabled="true">
	     		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	            </h:selectOneMenu>
            </h:panelGroup>
            <h:panelGroup rendered="#{empresaController.strTipoMantZonal == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
            	<h:selectOneMenu value="#{empresaController.beanZonal.intIdEstado}">
	           	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	     		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	            </h:selectOneMenu>
            </h:panelGroup>
    	</rich:column>
    	<rich:column >
    		<h:outputLabel  value="*" style="color:#f20526; font-size: 22px;"></h:outputLabel>
    	</rich:column>
     </h:panelGrid>
     <h:panelGrid>
     	 <h:outputText value="#{empresaController.msgTipoZonalError}" styleClass="msgError"></h:outputText>
    	 <h:outputText value="#{empresaController.msgEstadoZonalError}" styleClass="msgError"></h:outputText>
     </h:panelGrid>
    <h:panelGrid  border="0" columns="4">
    	<rich:column  width="170">
    		<h:outputText id="lblNomZonal" value="Nombre de Zonal" style="padding-left: 15px" ></h:outputText>
    	</rich:column>
    	<rich:column >
    		<h:inputText id="txtNomZonal" size="60" value="#{empresaController.beanZonal.juridica.strRazonSocial}" disabled="#{empresaController.formZonalDisabled}"/>
    	</rich:column>
    	<rich:column >
    		<h:outputLabel value="*" style="color:#f20526; font-size: 22px;"></h:outputLabel>
    	</rich:column>
    	<rich:column >
    		<h:inputHidden value="#{empresaController.beanZonal.id.intIdzonal}"></h:inputHidden>
    	</rich:column>
	</h:panelGrid>
	<h:panelGrid>
		<h:outputText value="#{empresaController.msgNombreZonalError}" styleClass="msgError"></h:outputText>
	</h:panelGrid>
	<h:panelGrid border="0" columns="4">
		<rich:column  width="170">
			<h:outputText id="lblAbrevZonal" value="Abreviatura de Zonal :" style="padding-left: 15px" ></h:outputText>
		</rich:column>
		<rich:column >
			<h:inputText id="txtAbrevZonal" size="10" value="#{empresaController.beanZonal.juridica.strSiglas}" disabled="#{empresaController.formZonalDisabled}"></h:inputText>
		</rich:column>
		<rich:column >
			<h:outputLabel value="*" style="color:#f20526; font-size: 22px;"></h:outputLabel>
		</rich:column>
		<rich:column >
			<h:outputText value=""></h:outputText>
		</rich:column>
	    </h:panelGrid>
	    <h:panelGrid>
		<h:outputText value="#{empresaController.msgAbrevZonalError}" styleClass="msgError"/>
	</h:panelGrid>
    <h:panelGrid border="0" columns="4">
    	<rich:column  width="170">
    		<h:outputText id="lblRespZonal" value="Responsable de Zonal :" style="padding-left: 15px" ></h:outputText>
    	</rich:column>
    	<rich:column >
    		<h:selectOneMenu value="#{empresaController.beanZonal.intPersPersonaResponsablePk}" style="width: 300px;">      
	  		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	     	<tumih:selectItems var="sel" value="#{empresaController.listaNaturalUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombres} #{sel.strApellidoPaterno} #{sel.strApellidoMaterno}"/>
	  		</h:selectOneMenu>
	    	</rich:column>
	    	<rich:column >
	    		<h:outputLabel value="*" style="color:#f20526; font-size: 22px;"/>
	    	</rich:column>
	    	<rich:column >
	    		<h:outputText value=""></h:outputText>
	    	</rich:column>
	</h:panelGrid>
	<h:panelGrid>
		<h:outputText value="#{empresaController.msgRespZonalError}" styleClass="msgError"></h:outputText>
	</h:panelGrid>
	<h:panelGrid border="0" columns="4">
	<rich:column  width="170">
	<h:outputText id="lblSucuSondeo" value="Sucursal de Sondeo :" style="padding-left: 15px" ></h:outputText>
	</rich:column>
	<rich:column >

	<h:selectOneMenu style="width: 300px;" value="#{empresaController.beanZonal.sucursal.id.intIdSucursal}">
			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>      
	      	<tumih:selectItems var="sel" value="#{empresaController.beanZonal.listaSucursal}" 
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
	</h:selectOneMenu>

	</rich:column>
	<rich:column >
	<h:outputLabel value="*" style="color:#f20526; font-size: 22px;"></h:outputLabel>
	</rich:column>
	<rich:column >
	<h:outputText value=""></h:outputText>
	</rich:column>                  
    </h:panelGrid>
    <h:panelGrid>
		<h:outputText value="#{empresaController.msgSucurSondeoError}" styleClass="msgError"></h:outputText>
	</h:panelGrid>
	<rich:spacer height="4px"/><rich:spacer height="4px"/>
	
<h:panelGrid id="pGrid6" columns="4">
   	<rich:column  width="170">
   		<h:outputText value="Listar Sucursales :" style="padding-left: 16px;"></h:outputText>
   	</rich:column>
   	<rich:column >
   		<h:selectOneMenu id="cboFiltTipoSucur" value="#{empresaController.intTipoSucursal}" style="width: 100px;">
   			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
   			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
           	<a4j:support event="onchange" actionListener="#{empresaController.getListaSucursalPorTipo}" reRender="pMarcoZonal1"/>
        </h:selectOneMenu>
   	</rich:column>
   	<rich:column >
   		<h:selectOneMenu id="cboFiltSucurAnexas" value="#{empresaController.intSucursalAnexo}" style="width: 100px;">
   			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
   			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_SUCURSALZONAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
            	<a4j:support event="onchange" actionListener="#{empresaController.getListaSucursalPorTipoYAnexo}" reRender="pMarcoZonal1"/>
        </h:selectOneMenu>
   	</rich:column>
</h:panelGrid>

<div align="center">
<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5; " >
<h:panelGrid id="pgSucurZonal" columns="1">
     <rich:extendedDataTable id="tblSucurZonal" value="#{empresaController.listaSucursalDeZonal}" 
     	var="item" rowKeyVar="rowKey" rows="5" sortMode="single"  width="479px" height="166px"
     	rendered="#{not empty empresaController.listaSucursalDeZonal}">
     	<rich:column width="29px">
             <f:facet name="header"><h:outputText   value="" ></h:outputText></f:facet>
             <h:outputText  value="#{rowKey+1}"></h:outputText>
         </rich:column>
         
         <rich:column sortBy="#{item.juridica.strRazonSocial}" width="150px">
             <f:facet name="header"><h:outputText   value="Sucursal" ></h:outputText></f:facet>
             <h:outputText id="lblNombSucur" value="#{item.juridica.strSiglas}"></h:outputText>
             <rich:toolTip for="lblNombSucur" value="#{item.juridica.strSiglas}"></rich:toolTip>
         </rich:column>
          <rich:column>
             <f:facet name="header"><h:outputText  value="Activación" ></h:outputText></f:facet>
             <h:selectBooleanCheckbox id="chkActivacion" value="#{item.checked}"></h:selectBooleanCheckbox>
         </rich:column>
         <rich:column sortBy="#{item.zonal.juridica.strRazonSocial}">
             <f:facet name="header"><h:outputText  value="Zonal" ></h:outputText></f:facet>
             <h:outputText id="lblNombZonal" value="#{item.zonal.juridica.strRazonSocial}"></h:outputText>
             <rich:toolTip for="lblNombZonal" value="#{item.zonal.juridica.strRazonSocial}"></rich:toolTip>
         </rich:column>
          <rich:column sortBy="#{item.intIdEstado}">
             <f:facet name="header"><h:outputText  value="Estado" ></h:outputText></f:facet>
             <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
    		 itemValue="intIdDetalle" itemLabel="strDescripcion" property="#{item.intIdEstado}"/>
             <%--
             <h:selectOneMenu disabled="true" required="true" style="width: 300px;" value="#{item.intIdEstado}">      
				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>					
			 </h:selectOneMenu>
  			 --%>
         </rich:column>
         
         <f:facet name="footer">
         	<rich:datascroller for="tblSucurZonal" maxPages="10"></rich:datascroller>
         </f:facet>

     </rich:extendedDataTable>                         
</h:panelGrid>
</rich:panel >
</div>
  
<h:panelGrid id="pgSASL" columns="5">
	<rich:column>
		<h:outputText id="lblSucAnexas1" value="Sucursales Anexas" style="color: #4449a5;padding-left: 16px;" ></h:outputText>
	</rich:column>
	<rich:column>
		<h:inputText id="txtSucAnexas1" value="#{empresaController.intContSucurAnexas}" size="10" ></h:inputText>
	</rich:column>
	<rich:column>
		<h:outputText id="lblSucLibres1" value="Sucursales Libres" style="color: #4449a5" ></h:outputText>
	</rich:column>
	<rich:column>
		<h:inputText id="txtSucLibres1" value="#{empresaController.intContSucurLibres}" size="10" ></h:inputText>
	</rich:column>
</h:panelGrid>

</rich:panel>

</rich:panel>