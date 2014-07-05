<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO productos			-->
	<!-- Fecha     : 08/05/2012               		-->

<rich:modalPanel id="pBuscarCaptacion" width="800" height="420"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Producto"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCaptacion" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    
    <h:form id="frmCaptacion">
     	<rich:panel style="background-color:#DEEBF5" id="panelSeleccionarCaptacion">             
             
			<h:panelGrid columns="2">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Captación : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
                  		disabled="true"
			        	value="#{requisitosController.intFiltroTipoCaptacion}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO}">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Beneficio : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.intFiltroTipoBeneficio}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_BENEFICIARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{requisitosController.buscarCaptacion}"
	             		reRender="pgListaCaptaciones" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="4" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO}">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Benificio : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.intFiltroTipoBeneficio}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_BENEFICIARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </rich:column>	            
	            <rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.intFiltroTipoMotivo}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_MOTFONRET}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{requisitosController.buscarCaptacion}"
	             		reRender="pgListaCaptaciones" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_AES}">
				<rich:column style="border:none" width="170">
					<h:outputText value="Tipo de Concepto : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
                  		style="width: 150px;"
			        	value="#{requisitosController.intFiltroTipoConcepto}">
			      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONCEPTO_AES}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Buscar" action="#{requisitosController.buscarCaptacion}"
	             		reRender="pgListaCaptaciones" styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
             <h:panelGrid id="pgListaCaptaciones">
             	<rich:dataTable id="tbCaptaciones" 
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="750px" 
    				value="#{requisitosController.listaCaptacion}" 
    				rows="5">
	                <rich:column style="text-align:center" width="60">
	                	<f:facet name="header">
	             			<h:outputText value="Seleccione"/>
	                	</f:facet>
	                	<h:selectBooleanCheckbox value="#{item.checked}"/>
	             	</rich:column>	                
	         		<rich:column width="150px" style="text-align: center">
						<f:facet name="header">
				       		<h:outputText value="Código"/>
						</f:facet>
						<h:outputText value="#{item.id.intItem}"/>	
				 	</rich:column>
				    <rich:column width="220" style="text-align: center" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO
				    	 ||	requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO}">
				    	<f:facet name="header">
				        	<h:outputText value="Tipo de Beneficio"/>
				      	</f:facet>
				      	<h:outputText value="#{item.strRequisitosConcatenados}"/>
					</rich:column>
					 <rich:column width="220" style="text-align: center" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO}">
				    	<f:facet name="header">
				        	<h:outputText value="Tipo de Motivo"/>
				      	</f:facet>
				      	<h:outputText value="#{item.strVinculosConcatenados}"/>
					</rich:column>
					 <rich:column width="220" style="text-align: center" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_AES}">
				    	<f:facet name="header">
				        	<h:outputText value="Tipo de Concepto"/>
				      	</f:facet>
				      	<h:outputText value="#{item.strConceptosConcatenados}"/>
					</rich:column>
				    <rich:column width="320" style="text-align: center">
				    	<f:facet name="header">
				        	<h:outputText value="Descripción"/>
				      	</f:facet>
				      	<h:outputText value="#{item.strDescripcion}"/>	
				 	</rich:column>
                    <f:facet name="footer">
	                	<rich:datascroller for="tbCaptaciones" maxPages="20"/>   
	               	</f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="1">
             	<rich:column>
             		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos"
		             	action="#{requisitosController.agregarConfServCredito}"
		             	onclick="Richfaces.hideModalPanel('pBuscarCaptacion')"
		             	reRender="#{requisitosController.nombrePanelProducto}">
		             </a4j:commandButton>
             	</rich:column>
             </h:panelGrid>			
         </rich:panel>
     </h:form>
     
</rich:modalPanel>     