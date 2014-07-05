<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         					-->
	<!-- Autor     : Christian De los Ríos    					-->
	<!-- Modulo    : Créditos                 					-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - FONDO DE RETIRO	-->
	<!-- Fecha     : 23/03/2012               					-->
	
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:998px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<div align="center">
						<b>CONFIGURACIÓN DE FONDO DE RETIRO</b>
					</div> 
         		
         			<h:panelGrid id="pgBusq1" columns="6" style="width: 880px" border="0">
                        <rich:column width="110">
                        	<h:outputText id="lblbusqTipoConv" value="Estado de Fondo"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{fondoRetiroController.intIdEstadoAportacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
              				<h:outputText value="Nombre del Fondo de Retiro : "/>
              			</rich:column>
                        <rich:column>
                        	<h:inputText value="#{fondoRetiroController.strNombreAporte}" size="70" disabled="#{fondoRetiroController.enabDisabNombAporte}"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{fondoRetiroController.chkNombFondoRetiro}">
                        		<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgBusq1"/>
                        	</h:selectBooleanCheckbox>
                        </rich:column>
                        <rich:column><h:outputText value="Todos"/></rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgBusq2" columns="9" border="0">
                         <rich:column>
                        	<h:outputText value="Condición : "/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{fondoRetiroController.intIdCondicionAportacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                        	<h:outputText value="Tipo Configuración"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{fondoRetiroController.intIdTipoConfig}">
                        		<f:selectItem itemLabel="Todos los Tipos" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{fondoRetiroController.chkFechas}">
                        		<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgBusq2"/>
                        	</h:selectBooleanCheckbox> 
                        </rich:column>
                        <rich:column><h:outputText value="Fecha" /></rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{fondoRetiroController.intTipoFecha}" disabled="#{fondoRetiroController.enabDisabFechasAport}">
                        		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                        		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>
                        	</h:selectOneMenu>
                        </rich:column>
                       	<rich:column>
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{fondoRetiroController.enabDisabFechasAport}"
		                   	value="#{fondoRetiroController.daFecIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                       	<rich:column>
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{fondoRetiroController.enabDisabFechasAport}"
		                   	value="#{fondoRetiroController.daFecFin}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgFiltroVigente" columns="11">
                    	<rich:column>
                    		<h:outputText value="Tipo de Persona :"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{fondoRetiroController.intIdTipoPersona}">
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" propertySort="intOrden"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{fondoRetiroController.chkTasaInt}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Tasa de Interés"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{fondoRetiroController.chkLimEdad}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Limite de Edad"/>
                    	</rich:column>
                    	<rich:column>
                    		Vigencia: 
                    		<h:selectBooleanCheckbox value="#{fondoRetiroController.chkAportVigentes}">
                    			<a4j:support event="onclick" actionListener="#{fondoRetiroController.enableDisableControls}" reRender="pgFiltroVigente"/>
                    		</h:selectBooleanCheckbox>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneRadio value="#{fondoRetiroController.rbVigente}" disabled="#{fondoRetiroController.blnVigencia}">
                    			<f:selectItem itemLabel="Vigentes" itemValue="1"/>
                    			<f:selectItem itemLabel="Caducos"  itemValue="0"/>
                    		</h:selectOneRadio>
                    	</rich:column>
                    	<rich:column>
                    		<a4j:commandButton value="Buscar" actionListener="#{fondoRetiroController.listarFondoRetiro}" styleClass="btnEstilos" reRender="pgListFondoRetiro"/>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgListFondoRetiro" border="0">
                    	<rich:extendedDataTable id="edtAportaciones" value="#{fondoRetiroController.listaCaptacionComp}"
                    		onRowClick="jsSeleccionFondoRetiro(#{item.captacion.id.intPersEmpresaPk}, #{item.captacion.id.intParaTipoCaptacionCod}, #{item.captacion.id.intItem});"
                    		rendered="#{not empty fondoRetiroController.listaCaptacionComp}"  rows="#{fondoRetiroController.rows}"
	                       	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="900px" height="198px">
                              <rich:column width="15">
                                  <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                              </rich:column>
                              <rich:column width="125">
                                  <f:facet name="header">
                                      <h:outputText value="Nombre de Retiro"/>
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
						<h:outputLink value="#" id="linkMantCta">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteFondoRetiro" attachTo="linkMantCta" operation="show" event="onclick"/>
						</h:outputLink>
						<h:outputText value="Para Anular o Modificar los MANTENIMIENTOS DE CUENTA hacer click en el Registro" style="color:#8ca0bd"/>                                     
				    </h:panelGrid>
				 </rich:panel>
				 
	   	    	 <h:panelGrid id="pgControls" columns="3">
	                 <a4j:commandButton value="Nuevo" actionListener="#{fondoRetiroController.habilitarGrabarAportaciones}" styleClass="btnEstilos" reRender="pFondoRetiro,pgControls"/>                     
	                 <h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	                 	<a4j:commandButton value="Grabar" actionListener="#{fondoRetiroController.grabarFondoRetiro}" styleClass="btnEstilos" reRender="pFondoRetiro"/>
		             </h:panelGroup>
		             <h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		             	<a4j:commandButton value="Grabar" actionListener="#{fondoRetiroController.modificarFondoRetiro}" styleClass="btnEstilos" reRender="pFondoRetiro"/>
		             </h:panelGroup>
	                 <a4j:commandButton value="Cancelar" actionListener="#{fondoRetiroController.cancelarGrabarFondoRetiro}" styleClass="btnEstilos" reRender="pFondoRetiro"/>
	          	 </h:panelGrid>
		         
	          	 <h:panelGrid id="pFondoRetiro">
	          	 	<h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
	          	 							  fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	          	 		<a4j:include viewId="/pages/captacion/credito/fondoRetiro/FondoRetiroEdit.jsp"/>
	          	 	</h:panelGroup>
	          	 	
	          	 	<h:panelGroup rendered="#{fondoRetiroController.strAportacion == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
	          	 		<a4j:include viewId="/pages/captacion/credito/fondoRetiro/FondoRetiroView.jsp"/>
	          	 	</h:panelGroup>
	          	 </h:panelGrid>
			</rich:panel>