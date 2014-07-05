<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         								-->
	<!-- Autor     : Christian De los Ríos    								-->
	<!-- Modulo    : Créditos                 								-->
	<!-- Prototipo : CONFIGURACIÓN DE PRODUCTOS - MANTENIMIENTO DE CUENTA	-->
	<!-- Fecha     : 21/03/2012               								-->
	
      	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
      		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
      			<h:panelGrid id="pgBusq1" columns="8" border="0">
                     <rich:column width="160">
                     	<h:outputText id="lblbusqTipoConv" value="Estado de Solicitud de Mnto." />
                     </rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdEstadoAportacion}">
                     		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                     <rich:column width="110">
           				<h:outputText value="Tipo de Cuenta"/>
           			</rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdTipoCuenta}">
                     		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                     <rich:column width="110">
           				<h:outputText value="Nombre del Mnto."/>
           			</rich:column>
                     <rich:column>
                     	<h:inputText value="#{mantCuentaController.strNombreMantenimiento}" disabled="#{mantCuentaController.enabDisabNombAporte}" size="30"/>
                     </rich:column>
                     <rich:column>
                     	<h:selectBooleanCheckbox value="#{mantCuentaController.chkNombreMant}">
                     		<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgBusq1"/>
                     	</h:selectBooleanCheckbox>
                     </rich:column>
					 <rich:column>
                     	<h:outputText value="Todos"/>
                     </rich:column>
                 </h:panelGrid>
                 
                 <h:panelGrid id="pgBusq2" columns="8" style="width: 900px" border="0">
                     <rich:column width="130">
                     	<h:outputText value="Cond. de Socio:"/>
                     </rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdCondicionMantenimiento}" style="width:130px;">
                     		<f:selectItem itemLabel="Todas las condiciones" itemValue="0"/>
                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                     <rich:column width="130">
                     	<h:outputText value="Tipo Configuración"/>
                     </rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdTipoConfig}">
                     		<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                     <rich:column width="110">
                     	<h:outputText value="Cond. Laboral:"/>
                     </rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdCondicionLaboral}">
                     		<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                     <rich:column width="130">
                     	<h:outputText value="Tipo de Persona:"/>
                     </rich:column>
                     <rich:column>
                     	<h:selectOneMenu value="#{mantCuentaController.intIdTipoPersona}">
                     		<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                      	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     	</h:selectOneMenu>
                     </rich:column>
                 </h:panelGrid>
                 
                 <h:panelGrid id="pgFechas" columns="10">
                 	<rich:column>
                     	<h:selectBooleanCheckbox value="#{mantCuentaController.chkFechas}">
                     		<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgFechas"/>
                     	</h:selectBooleanCheckbox> 
                     </rich:column>
                     <rich:column><h:outputText value="Fecha"/></rich:column>
                     <rich:column style="padding-left:15px;">
                     	<h:selectOneMenu value="#{mantCuentaController.intTipoFecha}" disabled="#{mantCuentaController.enabDisabFechasAport}">
                     		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                     		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>
                     	</h:selectOneMenu>
                     </rich:column>
                    	<rich:column style="border:none; padding-left:10px;">
         			<rich:calendar popup="true" enableManualInput="true"
         			disabled="#{mantCuentaController.enabDisabFechasAport}"
                  	value="#{mantCuentaController.daFecIni}"
                   datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                   cellWidth="10px" cellHeight="20px"/>
         		</rich:column>
                    	<rich:column style="border:none; padding-left:10px;">
         			<rich:calendar popup="true" enableManualInput="true"
         			disabled="#{mantCuentaController.enabDisabFechasAport}"
                  	value="#{mantCuentaController.daFecFin}"
                   datePattern="dd/MM/yyyy" inputStyle="width:70px;"
                   cellWidth="10px" cellHeight="20px"/>
         		</rich:column>
                 	<rich:column>
                 		Vigencia: 
                 		<h:selectBooleanCheckbox value="#{mantCuentaController.chkMantVigentes}">
                 			<a4j:support event="onclick" actionListener="#{mantCuentaController.enableDisableControls}" reRender="pgFechas"/>
                 		</h:selectBooleanCheckbox>
                 	</rich:column>
                 	<rich:column>
                 		<h:selectOneRadio value="#{mantCuentaController.rbVigente}" disabled="#{mantCuentaController.blnVigencia}">
                 			<f:selectItem itemLabel="Vigentes" itemValue="1"/>
                 			<f:selectItem itemLabel="Caducos"  itemValue="0"/>
                 		</h:selectOneRadio>
                 	</rich:column>
                 	<rich:column style="padding-left:55px;">
                 		<a4j:commandButton value="Buscar" actionListener="#{mantCuentaController.listarMantenimientoCta}" styleClass="btnEstilos" reRender="pgListMantCuenta"/>
                 	</rich:column>
                 </h:panelGrid>
                 
                 <h:panelGrid id="pgListMantCuenta" border="0">
                 	<rich:extendedDataTable id="edtAportaciones" value="#{mantCuentaController.listaCaptacionComp}"
                 		onRowClick="jsSeleccionMantCuenta(#{item.captacion.id.intPersEmpresaPk}, #{item.captacion.id.intParaTipoCaptacionCod}, #{item.captacion.id.intItem});"
                 		rendered="#{not empty mantCuentaController.listaCaptacionComp}" rows="#{mantCuentaController.rows}"
                     	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="900px" height="200px">
                           <rich:column width="15">
                               <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                           </rich:column>
                           <rich:column width="125">
                               <f:facet name="header">
                                   <h:outputText value="Nombre de Mant."/>
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
                           <rich:column width="125px">
                               <f:facet name="header">
                                   <h:outputText value="Ctas. Consideradas"/>
                               </f:facet>
                               <div align="center">
                                <h:outputText id="strCtasConsideradas" value="#{item.strCtasConsideradas}">
                                	<rich:toolTip for="strCtasConsideradas" value="#{item.strCtasConsideradas}" followMouse="true" 
                                  		styleClass="tooltip" showDelay="500"/>
                                </h:outputText>
                               </div>
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
		        <rich:componentControl for="panelUpdateDeleteMantCuenta" attachTo="linkAportacion" operation="show" event="onclick"/>
			</h:outputLink>
			<h:outputText value="Para Eliminar, Modificar o Imprimir las APORTACIONES hacer click en el Registro" style="color:#8ca0bd"/>                                     
	    </h:panelGrid>
	 </rich:panel>
	 
 	    	 <h:panelGrid id="pgControls" columns="3">
               <a4j:commandButton value="Nuevo" actionListener="#{mantCuentaController.habilitarGrabarAportaciones}" styleClass="btnEstilos" reRender="pAportaciones,pgControls"/>                     
               <h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
               	<a4j:commandButton value="Grabar" actionListener="#{mantCuentaController.grabarMantCuenta}" styleClass="btnEstilos" reRender="pAportaciones"/>
            </h:panelGroup>
            <h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
            	<a4j:commandButton value="Grabar" actionListener="#{mantCuentaController.modificarMantCuenta}" styleClass="btnEstilos" reRender="pAportaciones"/>
            </h:panelGroup>
               <a4j:commandButton value="Cancelar" actionListener="#{mantCuentaController.cancelarGrabarMantCuenta}" styleClass="btnEstilos" reRender="pAportaciones"/>
        	 </h:panelGrid>
        	 
        	 <h:panelGrid id="pAportaciones">
        	 	<h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
        	 							  mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
        	 		<a4j:include viewId="/pages/captacion/credito/mantCuenta/MantCuentaEdit.jsp"/>
        	 	</h:panelGroup>
        	 	
        	 	<h:panelGroup rendered="#{mantCuentaController.strAportacion == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
        	 		<a4j:include viewId="/pages/captacion/credito/mantCuenta/MantCuentaView.jsp"/>
        	 	</h:panelGroup>
        	 </h:panelGrid>
</rich:panel>
