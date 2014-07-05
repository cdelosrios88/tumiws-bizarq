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
			actionListener="#{cuentaCteController.setSelectedEntidad}">
			<f:param name="rowEntidad"></f:param>
		</a4j:jsFunction>
	</a4j:region>
	
    <h:form id="frmSocioEstructura">
     	<h:panelGroup layout="block" style="background-color:#DEEBF5">
             
             <h:panelGrid  columns="4">
		         <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Sucursal :"  > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:221px; border: none">
		         	<h:selectOneMenu id="cboSucursal" style="width: 220px;" 
						valueChangeListener="#{cuentaCteController.listarSubSucursalPorSuc}"
						value="#{cuentaCteController.intSucuIdSucursal}">
						<f:selectItem itemValue="" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{cuentaCteController.listJuridicaSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
						<a4j:support event="onchange" reRender="cboSubSucursal" ajaxSingle="true"></a4j:support>
		             </h:selectOneMenu>
		     	</rich:column>
	         	 <rich:column style="width:100px; border: none">
	         		<h:outputText   value="Sub Sucursal:"  > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:181px; border: none">
	         		<h:selectOneMenu id="cboSubSucursal" style="width: 180px;" value="#{cuentaCteController.intSudeIdSubsucursal}">
							<f:selectItem itemValue="" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{cuentaCteController.listJuridicaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	         	</rich:column>
         	</h:panelGrid>
         	 <h:panelGrid  columns="3">
		         <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Entidad :"  > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:221px; border: none">
		         	<h:inputText  value="#{cuentaCteController.strNombreEntidad}"  > </h:inputText>        
		     	</rich:column>
	         	 <rich:column>
	             	<a4j:commandButton value="Buscar" actionListener="#{cuentaCteController.buscarSocioEstructura}"
	             					   reRender="pgSocioEstructura" styleClass="btnEstilos"/>
	             </rich:column>
         	</h:panelGrid>
         	
             
             <h:panelGrid id="pgSocioEstructura" styleClass="tableCellBorder3">
                <rich:extendedDataTable id="tblEntidad" enableContextMenu="false" onRowClick="seleccionarEntidad('#{rowKey}')"
                             var="item" value="#{cuentaCteController.listaEstructuraDetalle}" style="margin:0 auto" 
					  		 rowKeyVar="rowKey" rows="5" sortMode="single" width="661px" height="165px">
                                
			  		 	<rich:column width="29px">
                        	<h:outputText value="#{rowKey + 1}"></h:outputText>
                         </rich:column>
                         
                         <rich:column width="155">
                         		<f:facet name="header">
                                    <h:outputText value="Nombre Entidad"></h:outputText>
                                </f:facet>
                                
                                <h:outputText  value="#{item.id.intCodigo}"/>-
							 	<tumih:outputText value="#{cuentaCteController.listEstructura}" 
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
							 <tumih:outputText value="#{cuentaCteController.listJuridicaSucursal}" 
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
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos" 
             						actionListener="#{cuentaCteController.seleccionarEntidad}"
             						oncomplete="if(#{cuentaCteController.habilitarFormAlerEnvioPlanilla == true}){Richfaces.hideModalPanel('mpSocioEstructura'),Richfaces.showModalPanel('mpAlertaEnvioPlanilla')}else{Richfaces.hideModalPanel('mpSocioEstructura')}"
             						reRender="dataListEntidadPrestamo,entidadOrigen,divFormCambioEntidad,frmAlertaEnvioPlanilla"/>
             	</rich:column>
             </h:panelGrid>
         </h:panelGroup>
     </h:form>