<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->
	<!-- Fecha     : 03/04/2012               -->

	<script type="text/javascript">
		
	</script>
	
	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:995px; background-color:#DEEBF5">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
			<!-- Modificado por cdelosrios, 04/12/2013 -->
			<%--<h:panelGrid columns="10" style="width: 900px" border="0">
	             <rich:column width="130" style="padding-left:5px;"><h:outputText id="lblbusqTipoConv" value="Tipo de Convenio" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.intTipoConvenio}">
						<f:selectItem itemValue="" itemLabel="Todos.."/>
						<f:selectItem itemValue="0" itemLabel="Nuevo Convenio"/>
						<f:selectItem itemValue="1" itemLabel="Adenda"/>
					</h:selectOneMenu>
	             </rich:column>
	             <rich:column width="120" style="padding-left:10px;"><h:outputText value="Nivel de Entidad" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.intNivelEntidad}">
	             		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	              	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	             	</h:selectOneMenu>
	             </rich:column>
	             <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Estados" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu value="#{hojaPlaneamientoController.intEstadoConv}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		              	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	             </rich:column>
	             <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Sucursal" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu style="width:90px;" value="#{hojaPlaneamientoController.intSucursalConv}">
					    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	             </rich:column>
	             <rich:column style="padding-left:10px;padding-right:10px;"><h:outputText value="Modalidad" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu value="#{hojaPlaneamientoController.intModalidad}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	             </rich:column>
	         </h:panelGrid>--%>
	         <h:panelGrid columns="6" style="width: 900px" border="0">
	         	<rich:column width="130" style="padding-left:5px;">
	         		<h:outputText value="Nro. Hoja"/>
	         	</rich:column>
	         	<rich:column>
	         		<h:inputText value="#{hojaPlaneamientoController.intNroHoja}"/>
	         	</rich:column>
	         	<rich:column>
	         		<h:outputText value="Sucursal gestiona"/>
	         	</rich:column>
	         	<rich:column>
	             	<h:selectOneMenu id="cboSucursal" value="#{hojaPlaneamientoController.intSucursalConv}"
	            		style="width:140px">
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel" value="#{hojaPlaneamientoController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" />
					</h:selectOneMenu>
	         	</rich:column>
	         	<rich:column>
	         		<h:outputText value="Estado"/>
	         	</rich:column>
	         	<rich:column>
	             	<h:selectOneMenu value="#{hojaPlaneamientoController.intEstadoConv}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		              	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	         	</rich:column>
	         </h:panelGrid>
	         
	         <h:panelGrid columns="2">
	         	<rich:column width="130px">
	         		<h:outputText value="Nombre de Entidad"/>
	         	</rich:column>
	         	<rich:column>
	         		<h:inputText size="60" value="#{hojaPlaneamientoController.strNombreEntidad}"/>
	         	</rich:column>
	         </h:panelGrid>
	         <!-- Modificado por cdelosrios, 04/12/2013 -->
	         <%--
	         <h:panelGrid columns="6" border="0">
	             <rich:column width="120"><h:outputText value="Nombre de Entidad" /></rich:column>
	             <rich:column>
	             	<h:inputText size="60" value="#{hojaPlaneamientoController.strNombreEntidad}"/>
	             </rich:column>
	             <rich:column width="110"><h:outputText value="Tipo de Socio" style="padding-left:30px;"/></rich:column>
	             <rich:column>
	             	<h:selectOneMenu value="#{hojaPlaneamientoController.intTipoSocio}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	             </rich:column>
	             <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkDonacion}" style="padding-left:30px;"/></rich:column>
	             <rich:column><h:outputText value="Donación" /></rich:column>
	         </h:panelGrid>
	         
	         <!-- Modificado por cdelosrios, 04/12/2013 -->
	         <h:panelGrid id="pRangoFec" columns="11" style="width: 850px" border="0">
	             <rich:column>
	             	<h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkRangoFec}">
	             		<a4j:support event="onclick" actionListener="#{hojaPlaneamientoController.enableDisableControls}" reRender="pRangoFec"/>
	             	</h:selectBooleanCheckbox>
	             </rich:column>
	             <rich:column><h:outputText value="Rango de Fecha" /></rich:column>
	             <rich:column>
	             	<h:selectOneMenu value="#{hojaPlaneamientoController.intRanFecha}" disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_RANGOFECHA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	             </rich:column>
	             <rich:column style="border:0px;width:100px;">
	          <rich:calendar id="idFecIniPerfUsu" popup="true"
	          	value="#{hojaPlaneamientoController.daFecIni}"
	          	disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}"
	           datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	           cellWidth="10px" cellHeight="20px"/>
	       </rich:column>
	       <rich:column style="border:0px;width:100px;">
	          <rich:calendar id="idFecFinPerfUsu" popup="true"
	          	value="#{hojaPlaneamientoController.daFecFin}"
	          	disabled="#{hojaPlaneamientoController.formRangoFechaEnabled}"
	           datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	           cellWidth="10px" cellHeight="20px"/>
	       </rich:column>
	       <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkIndeterminado}"/></rich:column>
	             <rich:column><h:outputText value="Indeterminado"/></rich:column>
	             <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkDocAdjuntos}"/></rich:column>
	             <rich:column><h:outputText value="Documentos Adjuntos" /></rich:column>
	             <rich:column><h:selectBooleanCheckbox value="#{hojaPlaneamientoController.chkCartaAutorizacion}"/></rich:column>
	             <rich:column><h:outputText value="Carta de Autorización"/></rich:column>
	         </h:panelGrid>
	         <h:panelGrid>
	         	<h:outputText value="#{hojaPlaneamientoController.msgTxtRangoFec}" styleClass="msgError"/>
	         </h:panelGrid>--%>
	         <!-- Modificado por cdelosrios, 04/12/2013 -->
	         <h:panelGrid>
	         	<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{hojaPlaneamientoController.listarHojaPlaneamiento}" reRender="pgConvenio"/>
	         </h:panelGrid>
	         
	         <br/>
	         <h:panelGrid id="pgConvenio" columns="1"  border="0">
	         	<rich:extendedDataTable id="tbAdminMenu" value="#{hojaPlaneamientoController.listaEstructHojaPlaneamientoComp}"
	             	var="item" rowKeyVar="rowKey" width="925px" height="165px" enableContextMenu="false"
	             	onRowClick="jsSeleccionHojaPlan(#{item.adenda.id.intConvenio}, #{item.adenda.id.intItemConvenio});" 
	             	rows="#{hojaPlaneamientoController.rows}"
	             	rendered="#{not empty hojaPlaneamientoController.listaEstructHojaPlaneamientoComp}">
	                   <rich:column width="25">
	                       <div align="center"><h:outputText value="#{rowKey+1}"/></div>
	                   </rich:column>
	                   <rich:column width="60px">
	                       <f:facet name="header">
	                           <h:outputText value="Código"/>
	                       </f:facet>
	                       <h:outputText value="#{item.adenda.id.intConvenio}"/>
	                   </rich:column>
	                   <rich:column width="135px">
	                       <f:facet name="header">
	                           <h:outputText value="Nombre de Entidad"/>
	                       </f:facet>
	                       <h:outputText id="strEntidad" value="#{item.estructuraDetalle.estructura.juridica.strRazonSocial}">
	                       	<rich:toolTip for="strEntidad" value="#{item.estructuraDetalle.estructura.juridica.strRazonSocial}" followMouse="true"/>
	                       </h:outputText>
	                   </rich:column>
	                   <rich:column>
	                       <f:facet name="header">
	                           <h:outputText value="Nivel"/>
	                       </f:facet>
						<h:outputText id="idStrNivel" value="#{item.strNivel}">
						<rich:toolTip for="idStrNivel" value="#{item.strNivel}" followMouse="true" />
						</h:outputText>
	                   </rich:column>
	                   <rich:column width="95px">
	                       <f:facet name="header">
	                           <h:outputText value="T. Convenio"/>
	                       </f:facet>
	                       <h:outputText value="#{item.adenda.id.intItemConvenio==0?'Nuevo Convenio':'Adenda'}"/>
	                   </rich:column>
	                   <rich:column>
	                       <f:facet name="header">
	                           <h:outputText value="Estado"/>
	                       </f:facet>
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
	                                  itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                  property="#{item.adenda.intParaEstadoHojaPlan}"/>
	                   </rich:column>
	                   <rich:column width="120px">
	                       <f:facet name="header">
	                           <h:outputText value="Sucursal"/>
	                       </f:facet>
	                       <tumih:outputText value="#{hojaPlaneamientoController.listJuridicaSucursal}" 
	                                  itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
	                                  property="#{item.adenda.intSeguSucursalPk}"/>
	                   </rich:column>
	                   <rich:column width="90px">
	                       <f:facet name="header">
	                           <h:outputText value="Modalidad"/>
	                       </f:facet>
	                       <h:outputText value="#{item.strModalidad}"/>
	                       <%--<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
	                                  itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                  property="#{item.estructuraDetalle.intParaModalidadCod}"/>--%>
	                   </rich:column>
	                   <rich:column width="80px">
	                       <f:facet name="header">
	                           <h:outputText value="T. Socio"/>
	                       </f:facet>
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
	                                  itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                  property="#{item.estructuraDetalle.intParaTipoSocioCod}"/>
	                   </rich:column>
	                   <rich:column width="120px">
	                       <f:facet name="header">
	                           <h:outputText value="F. Registro"/>
	                       </f:facet>
	                       <h:outputText value="#{item.adenda.strFechaRegistro}"/>
	                   </rich:column>
	                   
	                   <f:facet name="footer">
	          	<rich:datascroller for="tbAdminMenu" maxPages="5"/>   
	          </f:facet>
	        	<a4j:support event="onRowClick"  
					actionListener="#{hojaPlaneamientoController.seleccionarRegistro}" >
                       	<f:attribute name="itemRegistro" value="#{item}"/>
                 </a4j:support>
                 
	       </rich:extendedDataTable>
	   	</h:panelGrid>
	   
		<h:panelGrid columns="2">
			<h:outputLink value="#" id="linkHojaPlan">
			  <h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
			  <rich:componentControl for="panelUpdateDeleteHojaPlan" attachTo="linkHojaPlan" operation="show" event="onclick"/>
			</h:outputLink>
			<h:outputText value="Para Eliminar, Modificar o Imprimir la HOJA DE PLANEAMIENTO hacer click en el Registro XXX" style="color:#8ca0bd" />                                     
		</h:panelGrid>
		</rich:panel>
		
		<h:panelGrid id="pgControlsHojaPlaneamiento" columns="3">
          	<a4j:commandButton value="Nuevo" actionListener="#{hojaPlaneamientoController.habilitarGrabarHojaPlan}" styleClass="btnEstilos" reRender="pHojaPlan,pgControlsHojaPlaneamiento"/>
          	<h:panelGroup rendered="#{hojaPlaneamientoController.strHojaPlaneamiento == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
          		<a4j:commandButton value="GrabarN" actionListener="#{hojaPlaneamientoController.grabarHojaPlaneamiento}" styleClass="btnEstilos" reRender="pHojaPlan,pgConvenio"/>
       		</h:panelGroup>
	       	<h:panelGroup rendered="#{hojaPlaneamientoController.strHojaPlaneamiento == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	       		<a4j:commandButton value="GrabarM" actionListener="#{hojaPlaneamientoController.modificarHojaPlaneamiento}" styleClass="btnEstilos" reRender="pHojaPlan,pgConvenio"/>
	       	</h:panelGroup>
          	<a4j:commandButton value="Cancelar" actionListener="#{hojaPlaneamientoController.cancelarGrabarHojaPlaneamiento}" styleClass="btnEstilos" reRender="idTabHojaPlaneamiento,pgControlsHojaPlaneamiento"/>
   	 	</h:panelGrid>
    	 
    	<h:panelGrid id="pHojaPlan">
    	 	<h:panelGroup rendered="#{hojaPlaneamientoController.strHojaPlaneamiento == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
    	 							  hojaPlaneamientoController.strHojaPlaneamiento == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
    	 		<a4j:include viewId="/pages/convenio/hojaPlaneamiento/tabHojaPlaneamientoEdit.jsp"/>
    	 	</h:panelGroup>
    	 	<h:panelGroup rendered="#{hojaPlaneamientoController.strHojaPlaneamiento == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
    	 		<a4j:include viewId="/pages/convenio/hojaPlaneamiento/tabHojaPlaneamientoView.jsp"/>
    	 	</h:panelGroup>
        </h:panelGrid>
	</rich:panel>