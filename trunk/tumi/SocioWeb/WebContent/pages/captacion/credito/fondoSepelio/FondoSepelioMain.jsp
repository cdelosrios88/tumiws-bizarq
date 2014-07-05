<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : AES 		  Edit        -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 02/04/2012               -->
	
	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
   		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
   			<div align="center">
				<b>CONFIGURACIÓN DE FONDO DE SEPELIO</b>
			</div>
   			<h:panelGrid id="pgBusq1" columns="6" border="0">
   				<rich:column>
   					<h:outputText id="lblbusqTipoConv" value="Estado de Sepelio"/>
                </rich:column>
                <rich:column>
                	<h:selectOneMenu value="#{fondoSepelioController.intIdEstadoAportacion}">
                		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                 		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                	</h:selectOneMenu>
                </rich:column>
                <rich:column>
      				<h:outputText value="Nombre del Sepelio"/>
      			</rich:column>
                <rich:column>
                	<h:inputText value="#{fondoSepelioController.strNombreFondoSepelio}" size="60" disabled="#{fondoSepelioController.enabDisabNombFondoSepelio}"/>
                </rich:column>
                <rich:column>
                	<h:selectBooleanCheckbox value="#{fondoSepelioController.chkNombFondoSepelio}">
                		<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgBusq1"/>
                	</h:selectBooleanCheckbox>
                </rich:column>
                <rich:column><h:outputText value="Todos"/></rich:column>
            </h:panelGrid>
              
            <h:panelGrid id="pgBusq2" columns="9" border="0">
                <rich:column>
                	<h:outputText value="Condición"/>
                </rich:column>
                <rich:column>
                	<h:selectOneMenu value="#{fondoSepelioController.intIdCondicionAportacion}">
                		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                	</h:selectOneMenu>
                </rich:column>
                <rich:column>
                	<h:outputText value="Tipo Configuración"/>
                </rich:column>
                <rich:column>
	             	<h:selectOneMenu value="#{fondoSepelioController.intIdTipoConfig}">
	             		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	              		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	             	</h:selectOneMenu>
                </rich:column>
                <rich:column>
                	<h:selectBooleanCheckbox value="#{fondoSepelioController.chkFechas}">
                  		<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgBusq2"/>
                  	</h:selectBooleanCheckbox> 
                </rich:column>
                <rich:column><h:outputText value="Fecha" /></rich:column>
                <rich:column style="padding-left:15px;">
                	<h:selectOneMenu value="#{fondoSepelioController.intTipoFecha}" disabled="#{fondoSepelioController.enabDisabFechasAport}">
                		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>
                	</h:selectOneMenu>
                </rich:column>
                <rich:column style="border:none; padding-left:10px;">
	      			<rich:calendar popup="true" enableManualInput="true"
	      			disabled="#{fondoSepelioController.enabDisabFechasAport}"
	               	value="#{fondoSepelioController.daFecIni}"
	                datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                cellWidth="10px" cellHeight="20px"/>
      			</rich:column>
                <rich:column style="border:none; padding-left:10px;">
	      			<rich:calendar popup="true" enableManualInput="true"
	      			disabled="#{fondoSepelioController.enabDisabFechasAport}"
	               	value="#{fondoSepelioController.daFecFin}"
	                datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                cellWidth="10px" cellHeight="20px"/>
      			</rich:column>
            </h:panelGrid>
              
              <h:panelGrid id="pgFiltroVigente" columns="9">
              	<rich:column>
              		<h:outputText value="Tipo de Persona :"/>
              	</rich:column>
              	<rich:column>
              		<h:selectOneMenu value="#{fondoSepelioController.intIdTipoPersona}">
              			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                   	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
              		</h:selectOneMenu>
              	</rich:column>
              	<rich:column>
              		<h:selectBooleanCheckbox value="#{fondoSepelioController.chkLimEdad}"/>
              	</rich:column>
              	<rich:column>
              		<h:outputText value="Limite de Edad"/>
              	</rich:column>
              	<rich:column>
              		Vigencia: 
              		<h:selectBooleanCheckbox value="#{fondoSepelioController.chkAportVigentes}">
              			<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgFiltroVigente"/>
              		</h:selectBooleanCheckbox>
              	</rich:column>
              	<rich:column>
              		<h:selectOneRadio value="#{fondoSepelioController.rbVigente}" disabled="#{fondoSepelioController.blnVigencia}">
              			<f:selectItem itemLabel="Vigentes" itemValue="1"/>
              			<f:selectItem itemLabel="Caducos"  itemValue="0"/>
              		</h:selectOneRadio>
              	</rich:column>
              	<rich:column>
              		<a4j:commandButton value="Buscar" actionListener="#{fondoSepelioController.listarFondoSepelio}" styleClass="btnEstilos" reRender="pgListAportaciones"/>
              	</rich:column>
              </h:panelGrid>
              
              <h:panelGrid id="pgListAportaciones" border="0">
              	<rich:extendedDataTable id="edtAportaciones" value="#{fondoSepelioController.listaCaptacionComp}"
              		onRowClick="jsSeleccionFondoSepelio(#{item.captacion.id.intPersEmpresaPk}, #{item.captacion.id.intParaTipoCaptacionCod}, #{item.captacion.id.intItem});"
              		rendered="#{not empty fondoSepelioController.listaCaptacionComp}"  rows="#{fondoSepelioController.rows}"
                  	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="900px" height="170px">
                        <rich:column width="15">
                            <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                        </rich:column>
                        <rich:column width="125">
                            <f:facet name="header">
                                <h:outputText value="Nombre de Sepelio"/>
                            </f:facet>
                            <h:outputText value="#{item.captacion.strDescripcion}"/>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fec. de Inicio"/>
                            </f:facet>
                            <h:outputText value="#{item.captacion.strDtFechaIni}"/>
                        </rich:column>
                        <rich:column width="80px">
                            <f:facet name="header">
                                <h:outputText value="Fec. de Fin"/>
                            </f:facet>
                            <h:outputText value="#{item.captacion.strDtFechaFin}"/>
                        </rich:column>
                        <rich:column width="70px">
                            <f:facet name="header">
                                <h:outputText value="Estado"/>
                            </f:facet>
                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.captacion.intParaEstadoSolicitudCod}"/>
                        </rich:column>
                        <rich:column width="135px">
                            <f:facet name="header">
                                <h:outputText value="Condición de Socio"/>
                            </f:facet>
                            <div align="center">
                             <h:outputText id="strCondSoc" value="#{item.strCondicionSocio}">
                             	<rich:toolTip for="strCondSoc" value="#{item.strCondicionSocio}" followMouse="true" 
                               		styleClass="tooltip" showDelay="500"/>
                             </h:outputText>
                            </div>
                        </rich:column>
                        <rich:column width="150px">
                            <f:facet name="header">
                                <h:outputText value="Tipo de Configuración"/>
                            </f:facet>
                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.captacion.intParaTipoConfiguracionCod}"/>
                        </rich:column>
                        <rich:column width="115px">
                            <f:facet name="header">
                                <h:outputText value="Modelo Contable"/>
                            </f:facet>
                            <h:outputText value="xxxx"/>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha de Reg."/>
                            </f:facet>
                            <h:outputText value="#{item.captacion.strDtFechaRegistro}"/>
                        </rich:column>
                        
                        <f:facet name="footer">
                          <rich:datascroller for="edtAportaciones" maxPages="10"/>
                        </f:facet>
            	</rich:extendedDataTable>
        	  </h:panelGrid>
        
              <h:panelGrid columns="2">
				<h:outputLink value="#" id="linkAportacion">
				       <h:graphicImage value="/images/icons/mensaje1.jpg"
						style="border:0px"/>
				       <rich:componentControl for="panelUpdateDeleteFondoSepelio" attachTo="linkAportacion" operation="show" event="onclick"/>
				</h:outputLink>
				<h:outputText value="Para Anular o Modificar el FONDO DE SEPELIO hacer click en el Registro" style="color:#8ca0bd"/>                                     
			  </h:panelGrid>
		</rich:panel>

	   	 <h:panelGrid id="pgControls" columns="3">
	   	 	<a4j:commandButton value="Nuevo" actionListener="#{fondoSepelioController.habilitarGrabarFondoSepelio}" styleClass="btnEstilos" reRender="pFondoSepelio,pgControls"/>                     
	        <h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	         	<a4j:commandButton value="Grabar" actionListener="#{fondoSepelioController.grabarFondoSepelio}" styleClass="btnEstilos" reRender="pFondoSepelio"/>
	      	</h:panelGroup>
	      	<h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	      		<a4j:commandButton value="Grabar" actionListener="#{fondoSepelioController.modificarFondoSepelio}" styleClass="btnEstilos" reRender="pFondoSepelio"/>
	      	</h:panelGroup>
	        <a4j:commandButton value="Cancelar" actionListener="#{fondoSepelioController.cancelarGrabarFondoSepelio}" styleClass="btnEstilos" reRender="pFondoSepelio"/>
	  	 </h:panelGrid>
	     
		 <h:panelGrid id="pFondoSepelio">
		 	<h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
		 							  fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		 		<a4j:include viewId="/pages/captacion/credito/fondoSepelio/FondoSepelioEdit.jsp"/>
		 	</h:panelGroup>
		 	
		 	<h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
		 		<a4j:include viewId="/pages/captacion/credito/fondoSepelio/FondoSepelioView.jsp"/>
		 	</h:panelGroup>
		 </h:panelGrid>
	</rich:panel>