<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	<a4j:region>
		<a4j:jsFunction name="seleccionarEntidad" 
			actionListener="#{socioEstrucController.setSelectedEntidad}">
			<f:param name="rowEntidad"></f:param>
		</a4j:jsFunction>
	</a4j:region>
	
    <h:form id="frmSocioEstructura">
     	<h:panelGroup layout="block" style="background-color:#DEEBF5">
             <h:panelGrid columns="3" styleClass="tableCellBorder3">
	             <rich:column style="border:none">
	             	<h:outputText value="Unidad Ejecutora: "></h:outputText>
	             </rich:column>
	             <rich:column>
	             	<h:inputText value="#{socioEstrucController.estrucBusq.juridica.strRazonSocial}" size="28"></h:inputText>
	             </rich:column>
	             <rich:column>
	             	<a4j:commandButton value="Buscar" actionListener="#{socioEstrucController.buscarSocioEstructura}"
	             					   reRender="pgSocioEstructura" styleClass="btnEstilos"/>
	             </rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgSocioEstructura" styleClass="tableCellBorder3">
                <rich:extendedDataTable id="tblEntidad" enableContextMenu="false" onRowClick="seleccionarEntidad('#{rowKey}')"
                             var="item" value="#{socioEstrucController.listaEstructuraDetalle}" style="margin:0 auto" 
					  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="661px" height="165px">
                                
			  		 	<rich:column width="29px">
                        	<h:outputText value="#{rowKey + 1}"></h:outputText>
                         </rich:column>
                         
                         <rich:column width="155">
                         		<f:facet name="header">
                                    <h:outputText value="Nombre Entidad"></h:outputText>
                                </f:facet>
							 	<tumih:outputText value="#{socioEstrucController.listEstructura}" 
									itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
									property="#{item.id.intCodigo}"/>
                         </rich:column>
					  		 
                         <rich:column width="110">
	                         	<f:facet name="header">
	                            	<h:outputText  value="Tipo de Socio"></h:outputText>
	                            </f:facet>
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaTipoSocioCod}"/>
                         </rich:column>

                         <rich:column width="110">
                         		<f:facet name="header">
                                    <h:outputText value="Modalidad"></h:outputText>
                                </f:facet>
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaModalidadCod}"/>
                         </rich:column>
                         
                         <rich:column width="130">
                             <f:facet name="header">
                                 <h:outputText value="Sucursal"></h:outputText>
                             </f:facet>
							 <tumih:outputText value="#{estrucOrgController.listJuridicaSucursal}" 
								itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
								property="#{item.intSeguSucursalPk}"/>
                         </rich:column>
                         
                         <rich:column width="130">
                             <f:facet name="header">
                                 <h:outputText value="Sub Sucursal"></h:outputText>
                             </f:facet>
							<tumih:outputText value="#{item.listaSubsucursal}" 
								itemValue="id.intIdSubSucursal" itemLabel="strDescripcion" 
								property="#{item.intSeguSubSucursalPk}"/>
                         </rich:column>
                         
						<f:facet name="footer">   
				        	<rich:datascroller for="tblEntidad" maxPages="10"/>   
				        </f:facet>
                     </rich:extendedDataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="2" styleClass="tableCellBorder3">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos" onclick="seleccionarUnidadEjecutora()"
             						oncomplete="if(#{socioEstrucController.entidadSeleccionada==null}){Richfaces.showModalPanel('mpMessage')}
             						else{Richfaces.hideModalPanel('mpSocioEstructura')}"/>
             	</rich:column>
             	<rich:column>
             		<a4j:commandButton value="Limpiar" reRender="pgListaActEconomica,tbActEconomica" styleClass="btnEstilos"
             						actionListener="#{socioEstrucController.limpiarArrayEntidad}"/>
             	</rich:column>
             </h:panelGrid>
         </h:panelGroup>
     </h:form>