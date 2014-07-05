<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         					-->
	<!-- Autor     : Christian De los Ríos    					-->
	<!-- Modulo    : Créditos                 					-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - FONDO DE SEPELIO-->
	<!-- Fecha     : 03/04/2012               					-->
	
	<script type="text/javascript">
		function jsSeleccionCredito(itemIdEmpresa, itemIdTipoCredito, itemIdCodigo){
			document.getElementById("frmCreditoModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmCreditoModalPanel:hiddenIdTipoCredito").value=itemIdTipoCredito;
			document.getElementById("frmCreditoModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
	</script>
	
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:995px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<h:panelGrid id="pgBusq1" columns="8" border="0">
                        <rich:column>
                        	<h:outputText value="Estado de Solicitud de Crédito"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{creditoController.intIdEstadoSolicCredito}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
              				<h:outputText value="Estado del Crédito"/>
              			</rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{creditoController.intIdEstadoCredito}">
                        		<f:selectItem itemLabel="Todos" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
              				<h:outputText value="Tipo de Crédito"/>
              			</rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{creditoController.intTipoCredito}">
                        		<f:selectItem itemLabel="Todos" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                    		<h:outputText value="Tipo de Persona :"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{creditoController.intIdTipoPersona}">
                    			<f:selectItem itemLabel="Todos los Tipos" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="4" border="0">
                        <rich:column>
                        	<h:outputText value="Nombre del Crédito: "/>
                        </rich:column>
                        <rich:column>
                        	<h:inputText value="#{creditoController.strNombreCredito}" size="60"/>
                        </rich:column>
                        <rich:column>
                        	<h:outputText value="Tipo Condición Laboral: "/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{creditoController.intTipoCondicionLaboral}">
                        		<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="6">
                    	<rich:column>
                    		<h:outputText value="Condición de Socio :"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{creditoController.intCondicionSocio}">
                    			<f:selectItem itemLabel="Todas las condiciones" itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkFecha}"/>Fecha
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{creditoController.intTipoFecha}" disabled="#{creditoController.enabDisabFechasCredito}">
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FECHAVIGENCAPORT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{creditoController.enabDisabFechasCredito}"
		                   	value="#{creditoController.daFecIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
                    	</rich:column>
                    	<rich:column>
                    		<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{creditoController.enabDisabFechasCredito}"
		                   	value="#{creditoController.daFecFin}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="11">
		            	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkGarantia}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Garantías"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkDescuento}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Descuentos"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkExcepcion}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Excepciones"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkActivo}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Créditos Activos"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectBooleanCheckbox value="#{creditoController.chkCaduco}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Créditos Caducos"/>
                    	</rich:column>
                    	<rich:column>
                    		<a4j:commandButton value="Buscar" actionListener="#{creditoController.listarCredito}" styleClass="btnEstilos" reRender="pgListCreditos"/>
                    	</rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid id="pgListCreditos" border="0">
                    	<rich:extendedDataTable id="edtCreditos" value="#{creditoController.listaCreditoComp}"
                    		rendered="#{not empty creditoController.listaCreditoComp}" rows="#{creditoController.rows}"
                    		onRowClick="jsSeleccionCredito(#{item.credito.id.intPersEmpresaPk}, #{item.credito.id.intParaTipoCreditoCod}, #{item.credito.id.intItemCredito});"
	                       	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="900px" height="195px">
                              <rich:column width="15">
                                  <div align="center"><h:outputText value="#{rowKey+1}"/></div>
                              </rich:column>
                              <rich:column width="100">
                                  <f:facet name="header">
                                      <h:outputText value="Nom. Crédito"/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.strDescripcion}"/>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Fec. Ini."/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.strDtFechaIni}"/>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Fec. Fin"/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.strDtFechaFin}"/>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Estado"/>
                                  </f:facet>
                                  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.credito.intParaEstadoSolicitudCod}"/>
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
                              <rich:column width="95px">
                                  <f:facet name="header">
                                      <h:outputText value="Tasa Interés"/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.bdTasaAnual}"/>%
                              </rich:column>
                              <rich:column width="130px">
                                  <f:facet name="header">
                                      <h:outputText value="Tasa Desgravamen"/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.bdTasaSeguroDesgravamen}"/>%
                              </rich:column>
                              <rich:column width="110px">
                                  <f:facet name="header">
                                      <h:outputText value="Tasa Moratoria"/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.bdTasaMoratoriaMensual}"/>%
                              </rich:column>
                              <rich:column width="110px">
                                  <f:facet name="header">
                                      <h:outputText value="Fecha de Reg."/>
                                  </f:facet>
                                  <h:outputText value="#{item.credito.strDtFechaRegistro}"/>
                              </rich:column>
                              
                              <f:facet name="footer">
                                <rich:datascroller for="edtCreditos" maxPages="10"/>
                              </f:facet>
		                </rich:extendedDataTable>
		            </h:panelGrid>
		            
                    <h:panelGrid columns="2">
						<h:outputLink value="#" id="linkCredito">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteCredito" attachTo="linkCredito" operation="show" event="onclick"/>
						</h:outputLink>
						<h:outputText value="Para Anular o Modificar el CREDITO hacer click en el Registro" style="color:#8ca0bd"/>                                     
				    </h:panelGrid>
				 </rich:panel>
				 
	   	    	 <h:panelGrid id="pgControls" columns="3">
	                 <a4j:commandButton value="Nuevo" actionListener="#{creditoController.habilitarGrabarCredito}" styleClass="btnEstilos" reRender="pCredito,pgControls"/>                     
	                 <h:panelGroup rendered="#{creditoController.strCredito == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	                 	<a4j:commandButton value="Grabar" actionListener="#{creditoController.grabarCredito}" styleClass="btnEstilos" reRender="pCredito"/>
		             </h:panelGroup>
		             <h:panelGroup rendered="#{creditoController.strCredito == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		             	<a4j:commandButton value="Grabar" actionListener="#{creditoController.modificarCredito}" styleClass="btnEstilos" reRender="pCredito"/>
		             </h:panelGroup>
	                 <a4j:commandButton value="Cancelar" actionListener="#{creditoController.cancelarGrabarCredito}" styleClass="btnEstilos" reRender="pCredito"/>
	          	 </h:panelGrid>
	          	 
	          	 <h:panelGrid id="pCredito">
				 	<h:panelGroup rendered="#{creditoController.strCredito == applicationScope.Constante.MANTENIMIENTO_GRABAR ||
				 							  creditoController.strCredito == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
				 		<a4j:include viewId="/pages/credito/creditoEdit/creditoEdit.jsp"/>
				 	</h:panelGroup>
				 	
				 	<h:panelGroup rendered="#{creditoController.strCredito == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
				 		<a4j:include viewId="/pages/credito/creditoEdit/creditoView.jsp"/>
				 	</h:panelGroup>
				 </h:panelGrid>
			</rich:panel>