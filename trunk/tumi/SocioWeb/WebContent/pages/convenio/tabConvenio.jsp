<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         	-->
	<!-- Autor     : Christian De los Ríos    	-->
	<!-- Modulo    : Créditos                 	-->
	<!-- Prototipo : CONVENIOS 					-->
	<!-- Fecha     : 04/06/2012               	-->

	<script type="text/javascript">
		
	</script>
	
	<rich:panel style="border:1px solid #17356f ;width: 995px; background-color:#DEEBF5">
		<div align="center"><h:outputText value="REGISTRO DE CONVENIOS" style="font-weight:bold;"/></div>
			<h:panelGrid columns="6" style="width: 980px" border="0">
                <!-- Agregado por cdelosrios, 08/12/2013 -->
				<rich:column width="120"><h:outputText value="Código" /></rich:column>
				<rich:column>
					<h:inputText value="#{convenioController.intIdConvenio}" size="15"/>
				</rich:column>
				
				<rich:column><h:outputText value="Sucursal" /></rich:column>
				<rich:column>
					<h:selectOneMenu value="#{convenioController.intSucursalConv}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel" value="#{convenioController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
					</h:selectOneMenu>
				</rich:column>
				<!-- Agregado por cdelosrios, 08/12/2013 -->
            </h:panelGrid>
            
            <h:panelGrid id="pgFiltrosBusq2" columns="4" style="width: 800px" border="0">
				<rich:column width="120"><h:outputText value="Nombre de Entidad" /></rich:column>
				<rich:column>
					<h:inputText value="#{convenioController.strEntidad}" size="60"/>
				</rich:column>
				
				<rich:column>
                	<h:selectBooleanCheckbox value="#{convenioController.chkVigencia}">
                		<a4j:support event="onclick" actionListener="#{convenioController.enableDisableControls}" reRender="pgFiltrosBusq2"/>
                	</h:selectBooleanCheckbox>Vigencia :
                </rich:column>
                <rich:column>
                	<h:selectOneRadio value="#{convenioController.intVigenciaConvenio}" disabled="#{convenioController.enabRbVigencia}">
                		<f:selectItem itemLabel="Conv. Vigentes" itemValue="1"/>
                		<f:selectItem itemLabel="Conv. Vencidos" itemValue="2"/>
                	</h:selectOneRadio>
                </rich:column>
			</h:panelGrid>
            
			<h:panelGrid columns="1">
				<rich:column>
					<a4j:commandButton value="Buscar" actionListener="#{convenioController.listarConvenios}" styleClass="btnEstilos" reRender="pgListConvenio"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid id="pgListConvenio">
				<rich:extendedDataTable id="tbConvenio" value="#{convenioController.listaConvenioComp}"
					var="item" rowKeyVar="rowKey" width="860px" height="165px" enableContextMenu="false" 
	                onRowClick="jsSeleccionConvenio(#{item.adenda.id.intConvenio}, #{item.adenda.id.intItemConvenio});" 
	                rendered="#{not empty convenioController.listaConvenioComp}" rows="#{convenioController.rows}">
	                <rich:column width="60px">
	                    <f:facet name="header">
	                        <h:outputText value="Código"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.id.intConvenio}"/>
	                </rich:column>
	                <rich:column width="165px">
	                    <f:facet name="header">
	                        <h:outputText value="Nombre de la Entidad"/>
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
	                <rich:column>
	                	<f:facet name="header">
	                        <h:outputText value="Plazo"/>
	                    </f:facet>
	                    <div align="center"><h:outputText value="#{item.intDiferenciaFecha} días"/></div>
	                </rich:column>
	                <rich:column width="95px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Inicio"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strDtInicio}"/>
	                </rich:column>
	                <rich:column width="95px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Fin"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strDtCese}"/>
	                </rich:column>
	                <rich:column width="130px">
	                    <f:facet name="header">
	                        <h:outputText value="Tiempo por Vencer"/>
	                    </f:facet>
	                    <h:outputText value="---"/>
	                </rich:column>
	                <rich:column width="120px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Registro"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strFechaRegistro}"/>
	                </rich:column>
	                
	                <f:facet name="footer">
	               		<rich:datascroller for="tbConvenio" maxPages="5"/>   
	                </f:facet>
	                
	                <a4j:support event="onRowClick" actionListener="#{convenioController.listarConvenioDet}" reRender="pgListConvenioDet">
	                	<f:param name="intIdConvenio" 	value="#{item.adenda.id.intConvenio}"/>
	                	<f:param name="intItemConvenio" value="#{item.adenda.id.intItemConvenio}"/>
	                </a4j:support>
	            </rich:extendedDataTable>
          	 </h:panelGrid>
          	 <h:panelGrid columns="2">
				<h:outputLink value="#" id="linkConvenio">
			        <h:graphicImage value="/images/icons/mensaje1.jpg"
						style="border:0px"/>
			        <rich:componentControl for="panelUpdateDeleteConvenio" attachTo="linkConvenio" operation="show" event="onclick"/>
				</h:outputLink>
				<h:outputText value="Para Consultar los datos de la ADENDA hacer click en el Registro" style="color:#8ca0bd" />                                     
		     </h:panelGrid>
          	 <br/>
          	 
          	 <h:panelGrid id="pgListConvenioDet">
				<rich:extendedDataTable id="tbConvenioDet" value="#{convenioController.listaConvenioDetComp}"
					var="item" rowKeyVar="rowKey" width="720px" height="165px" enableContextMenu="false" 
	                onRowClick="jsSeleccionHojaPlan(#{item.adenda.id.intConvenio}, #{item.adenda.id.intItemConvenio});" 
	                rendered="#{not empty convenioController.listaConvenioDetComp}" rows="#{convenioController.rows}">
	                <rich:column width="15">
	                    <div align="center"><h:outputText value="#{rowKey+1}"/></div>
	                </rich:column>
	                <rich:column width="60px">
	                    <f:facet name="header">
	                        <h:outputText value="Código"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.id.intItemConvenio}"/>
	                </rich:column>
	                <rich:column>
	                    <f:facet name="header">
	                        <h:outputText value="Estado"/>
	                    </f:facet>
	                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.adenda.intParaEstadoCod}"/>
	                </rich:column>
	                <rich:column>
	                    <f:facet name="header">
	                        <h:outputText value="Plazo"/>
	                    </f:facet>
						<h:outputText value="#{item.intDiferenciaFecha}"/>
	                </rich:column>
	                <rich:column width="95px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Inicio"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strDtInicio}"/>
	                </rich:column>
	                <rich:column width="95px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Fin"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strDtCese}"/>
	                </rich:column>
	                <rich:column width="130px">
	                    <f:facet name="header">
	                        <h:outputText value="Tiempo por Vencer"/>
	                    </f:facet>
	                    <h:outputText value="---"/>
	                </rich:column>
	                <rich:column width="120px">
	                    <f:facet name="header">
	                        <h:outputText value="F. Registro"/>
	                    </f:facet>
	                    <h:outputText value="#{item.adenda.strFechaRegistro}"/>
	                </rich:column>
	                
	                <f:facet name="footer">
	               		<rich:datascroller for="tbConvenioDet" maxPages="5"/>   
	                </f:facet>
	            </rich:extendedDataTable>
          	</h:panelGrid>
          	 
		    <h:panelGrid id="pgControlsConvenios" columns="3">
	  			<a4j:commandButton value="Nuevo" actionListener="#{convenioController.habilitarGrabarFormConvenio}" styleClass="btnEstilos" reRender="pConvenio,pgControls,pgControlsConvenios"/>
		        <h:panelGroup rendered="#{convenioController.strConvenio == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		        	<a4j:commandButton value="Grabar" actionListener="#{convenioController.grabarConvenio}" styleClass="btnEstilos" reRender="pConvenio,pgConvenio,pgControlsConvenios"/>
		       	</h:panelGroup>
		        <h:panelGroup rendered="#{convenioController.strConvenio == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		       		<a4j:commandButton value="Grabar" actionListener="#{convenioController.modificarConvenio}" styleClass="btnEstilos" reRender="pConvenio,pgConvenio,pgControlsConvenios"/>
		        </h:panelGroup>
		        <a4j:commandButton value="Cancelar" actionListener="#{convenioController.cancelarGrabarConvenio}" styleClass="btnEstilos" reRender="pConvenio,pgControls,pgControlsConvenios"/>
		   	</h:panelGrid>
		   	
		   	<h:panelGrid id="pConvenio">
          	 	<h:panelGroup rendered="#{convenioController.strConvenio == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
          	 							  convenioController.strConvenio == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
          	 		<a4j:include viewId="/pages/convenio/convenio/tabConvenioEdit.jsp"/>
          	 	</h:panelGroup>
          	 	<h:panelGroup rendered="#{convenioController.strConvenio == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
          	 		<a4j:include viewId="/pages/convenio/convenio/tabConvenioView.jsp"/>
          	 	</h:panelGroup>
	        </h:panelGrid>
		</rich:panel>