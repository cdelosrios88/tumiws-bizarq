<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         			-->
	<!-- Autor     : Christian De los Ríos    			-->
	<!-- Modulo    : Créditos                 			-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - AES		-->
	<!-- Fecha     : 29/03/2012               			-->
	
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         		<div align="center">
						<b>CONFIGURACIÓN DE AES</b>
					</div> 
         		
         		
         			<h:panelGrid id="pgBusq1" columns="6" style="width: 800px" border="0">
                        <rich:column>
                        	<h:outputText id="lblbusqTipoConv" value="Estado de AES"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{aesController.intIdEstadoAportacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
              				<h:outputText value="Nombre del AES"/>
              			</rich:column>
                        <rich:column>
                        	<h:inputText value="#{aesController.strNombreFondoSepelio}" size="60" disabled="#{aesController.enabDisabNombFondoSepelio}"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{aesController.chkNombFondoSepelio}">
                        		<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgBusq1"/>
                        	</h:selectBooleanCheckbox>
                        </rich:column>
                        <rich:column><h:outputText value="Todos"/></rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgBusq2" columns="9" border="0">
                        <rich:column width="95">
                        	<h:outputText value="Condición"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{aesController.intIdCondicionAportacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                        	<h:outputText value="Tipo relación"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{aesController.intIdTipoRelacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{aesController.chkFechas}">
                        		<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgBusq2"/>
                        	</h:selectBooleanCheckbox> 
                        </rich:column>
                        <rich:column><h:outputText value="Fecha" /></rich:column>
                        <rich:column style="padding-left:15px;">
                        	<h:selectOneMenu value="#{aesController.intTipoFecha}" disabled="#{aesController.enabDisabFechasAport}">
                        		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                        		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>
                        	</h:selectOneMenu>
                        </rich:column>
                       	<rich:column style="border:none; padding-left:10px;">
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{aesController.enabDisabFechasAport}"
		                   	value="#{aesController.daFecIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                       	<rich:column style="border:none; padding-left:10px;">
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{aesController.enabDisabFechasAport}"
		                   	value="#{aesController.daFecFin}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgFiltroVigente" columns="9">
                    	<rich:column>
                    		<h:outputText value="Tipo de Persona :"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{aesController.intIdTipoPersona}">
                    			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		Vigencia: 
                    		<h:selectBooleanCheckbox value="#{aesController.chkAportVigentes}">
                    			<a4j:support event="onclick" actionListener="#{aesController.enableDisableControls}" reRender="pgFiltroVigente"/>
                    		</h:selectBooleanCheckbox>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneRadio value="#{aesController.rbVigente}" disabled="#{aesController.blnVigencia}">
                    			<f:selectItem itemLabel="Vigentes" itemValue="1"/>
                    			<f:selectItem itemLabel="Caducos"  itemValue="0"/>
                    		</h:selectOneRadio>
                    	</rich:column>
                    	<rich:column>
                    		<a4j:commandButton value="Buscar" actionListener="#{aesController.listarAes}" styleClass="btnEstilos" reRender="pgListAportaciones"/>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <div align="center">
                    <h:panelGrid id="pgListAportaciones" border="0">
                    	<rich:extendedDataTable id="edtAportaciones" value="#{aesController.listaCaptacionComp}"
                    		onRowClick="jsSeleccionAes(#{item.captacion.id.intPersEmpresaPk}, #{item.captacion.id.intParaTipoCaptacionCod}, #{item.captacion.id.intItem});"
                    		rendered="#{not empty aesController.listaCaptacionComp}"  rows="#{aesController.rows}"
	                       	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="780px" height="170px">
                              <rich:column width="15">
                                  <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                              </rich:column>
                              <rich:column width="125">
                                  <f:facet name="header">
                                      <h:outputText value="Nombre de AES"/>
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
                              <rich:column width="115px">
                                  <f:facet name="header">
                                      <h:outputText value="Modelo Contable"/>
                                  </f:facet>
                                  <h:outputText value="xxxx"/>
                              </rich:column>
                              <rich:column width="140px">
                                  <f:facet name="header">
                                      <h:outputText value="Fecha de Registro"/>
                                  </f:facet>
                                  <h:outputText value="#{item.captacion.strDtFechaRegistro}"/>
                              </rich:column>
                              
                              <f:facet name="footer">
                                <rich:datascroller for="edtAportaciones" maxPages="10"/>
                              </f:facet>
		                </rich:extendedDataTable>
		            </h:panelGrid>
		            </div>
		            
                    <h:panelGrid columns="2">
						<h:outputLink value="#" id="linkAportacion">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteAES" attachTo="linkAportacion" operation="show" event="onclick"/>
						</h:outputLink>
						<h:outputText value="Para Anular o Modificar el AES hacer click en el Registro" style="color:#8ca0bd"/>                                     
				    </h:panelGrid>
				 </rich:panel>
				 
	   	    	 <h:panelGrid id="pgControls" columns="3">
	                 <a4j:commandButton value="Nuevo" actionListener="#{aesController.habilitarGrabarAes}" styleClass="btnEstilos" reRender="pgAES,pgControls"/>                     
	                 <h:panelGroup rendered="#{aesController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	                 	<a4j:commandButton value="Grabar" actionListener="#{aesController.grabarAes}" styleClass="btnEstilos" reRender="pgAES"/>
		             </h:panelGroup>
		             <h:panelGroup rendered="#{aesController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		             	<a4j:commandButton value="Grabar" actionListener="#{aesController.modificarAes}" styleClass="btnEstilos" reRender="pgAES"/>
		             </h:panelGroup>
	                 <a4j:commandButton value="Cancelar" actionListener="#{aesController.cancelarGrabarAes}" styleClass="btnEstilos" reRender="pgAES"/>
	          	 </h:panelGrid>
		         
	          	 <h:panelGrid id="pgAES">
	          	 	<h:panelGroup rendered="#{aesController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
	          	 							  aesController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	          	 		<a4j:include viewId="/pages/captacion/credito/aes/AesEdit.jsp"/>
	          	 	</h:panelGroup>
	          	 	
	          	 	<h:panelGroup rendered="#{aesController.strAportacion == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
	          	 		<a4j:include viewId="/pages/captacion/credito/aes/AesView.jsp"/>
	          	 	</h:panelGroup>
	          	 </h:panelGrid>
			</rich:panel>