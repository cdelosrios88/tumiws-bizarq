<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Cod. Req  : REQ14-004				-->
	<!-- Empresa   : Cooperativa Tumi		-->
	<!-- Autor     : Christian De los Ríos  -->
	<!-- Modulo    : Contabilidad         	-->
	<!-- Prototipo : Mayorizacion	 		-->
	<!-- Fecha     : 14/09/2014             -->

<h:form id="frmMayorizacion">
   	<h:panelGroup id="divPlanCuentas" layout="block" style="padding:15px;border:1px solid #B3B3B3;">
   		
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="MAYORIZACIÓN" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid columns="7">
        	<rich:column width="70" style="text-align: center;">
         		<h:outputText value="Periodo : "/>
         	</rich:column>
         	<rich:column width="150">
               	<h:selectOneMenu
					style="width: 150px;"
					value="#{mayorizacionController.libroMayorFiltro.id.intContMesMayor}">
					<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO_TODOS}" itemLabel="Todos los meses"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="70">
               	<h:selectOneMenu
					style="width: 70px;"
					value="#{mayorizacionController.libroMayorFiltro.id.intContPeriodoMayor}">
					<tumih:selectItems var="sel"
						value="#{mayorizacionController.listYears}" 
						itemValue="#{sel.value}" 
						itemLabel="#{sel.label}"/>
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="80" style="text-align: center;">
         		<h:outputText value="Estado : "/>
         	</rich:column>
         	<rich:column width="100">
               	<h:selectOneMenu style="width: 100px;"
					value="#{mayorizacionController.libroMayorFiltro.intEstadoCod}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOMAYORIZACION}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="50">
            </rich:column>
         	<rich:column width="50" style="text-align: right;">
               	<a4j:commandButton styleClass="btnEstilos"
               		value="Buscar" reRender="panelTablaContabilidad,panelMensaje"
                   	action="#{mayorizacionController.buscarMayorizado}" style="width:70px"/>
            </rich:column>
		</h:panelGrid>
		
		<rich:spacer height="12px"/>           
        
        <div align="center">
        	<h:panelGrid id="panelTablaContabilidad">
		       	<rich:extendedDataTable id="tblContabilidad" 
		         		enableContextMenu="false" 
		          		sortMode="single" 
	                    var="item" 
	                    value="#{mayorizacionController.listaLibroMayor}"  
						rowKeyVar="rowKey" 
						rows="5"
						width="700px" 
						height="195px" 
						align="center">
	                                
						<rich:column width="30px" style="text-align: center">
	                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
	                    </rich:column>
	                    <rich:column width="200" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Periodo"/>                      		
	                      	</f:facet>
	                      	<h:outputText value="#{item.id.intContPeriodoMayor}"/> -
	                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intContMesMayor}"/>
							
	                  	</rich:column>
	                  	<rich:column width="170" style="text-align: center">
	                   		<f:facet name="header">
	                        	<h:outputText value="Estado Cierre"/>
	                        </f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstadoCierreCod}"/>
	                  	</rich:column>
	                   	<rich:column width="200" style="text-align: center">
	                  		<f:facet name="header">
	                        	<h:outputText value="Estado Mayorizacion"/>
	                     	</f:facet>
	                     	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOMAYORIZACION}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intEstadoCod}"/>
	                  	</rich:column>
	                  	<rich:column width="60" style="text-align: center">
	                  		<f:facet name="header">
	                        	<h:outputText value=""/>
	                     	</f:facet>
	                     	<a4j:commandLink id="lnkDelete" styleClass="no-decor" reRender="tblContabilidad,panelValidMsgError"
			            		actionListener="#{mayorizacionController.deleteMayorizado}" 
			            		rendered="#{item.intEstadoCod==applicationScope.Constante.PARAM_T_TIPOESTADOMAYORIZACION_PROCESADO}"
			            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
			                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
			                    <f:attribute name="item" value="#{item}"/>
			            		<rich:toolTip for="lnkDelete" value="Eliminar" followMouse="true"/>
			            	</a4j:commandLink>
	                  	</rich:column>
	                    <f:facet name="footer">
							<rich:datascroller for="tblContabilidad" maxPages="10"/>   
						</f:facet>
	                   	<a4j:jsFunction name="onCompleteFunction" reRender="frmAlertaRegistro" ajaxSingle="true"/>
	            </rich:extendedDataTable>
         	</h:panelGrid>
        </div>
			
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{mayorizacionController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{mayorizacionController.mostrarMensajeExito}"/>
			<h:outputText value="#{mayorizacionController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{mayorizacionController.mostrarMensajeError}"/>	
		</h:panelGroup>	
		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesFH">
			<h:panelGrid columns="4">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{mayorizacionController.habilitarPanelInferior}" 
					reRender="contPanelInferiorProcMay,panelValidMsgError,panelValidMsgSuccess,dlValidAccounts" />
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{mayorizacionController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorProcMay,panelValidMsgError,panelValidMsgSuccess" />
			</h:panelGrid>
		</rich:panel>
		
		<h:panelGrid id="panelValidMsgSuccess">
			<h:outputText value="#{mayorizacionController.strMsgSuccess}" styleClass="msgInfo"/>
			<h:outputText value="#{mayorizacionController.strMsgFailed}" styleClass="msgError"/>
		</h:panelGrid>
		
		<rich:panel id="contPanelInferiorProcMay" style="border:0px;">
			<rich:spacer height="3px"/>
				<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{mayorizacionController.mostrarPanelInferior}">
					<rich:spacer height="3px"/>
					<rich:column width="70" style="text-align: center;">
		         		<h:outputText value="Periodo : "/>
		         	</rich:column>
		         	<rich:column width="150">
		               	<h:selectOneMenu
							style="width: 150px;"
							value="#{mayorizacionController.libroMayorNuevo.id.intContMesMayor}">
							
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
						</h:selectOneMenu>
		            </rich:column>
		            <rich:column width="70">
		               	<h:selectOneMenu
							style="width: 70px;"
							value="#{mayorizacionController.libroMayorNuevo.id.intContPeriodoMayor}">
							<tumih:selectItems var="sel"
								value="#{mayorizacionController.listYears}" 
								itemValue="#{sel.value}" 
								itemLabel="#{sel.label}"/>
						</h:selectOneMenu>
		            </rich:column>
		            <rich:column width="90" style="text-align: right;">
		               	<a4j:commandButton styleClass="btnEstilos"
		               		value="Procesar"
		                   	action="#{mayorizacionController.procesarMayorizado}" style="width:70px"
		                   	reRender="panelValidMsgError,panelValidMsgSuccess,tblContabilidad"/>
		            </rich:column>
		            <rich:spacer height="3px"/>
				</rich:panel>
		</rich:panel>
		<h:panelGrid id="panelValidMsgError">
			<h:outputText value="#{mayorizacionController.strErrorValidateMsg}" styleClass="msgError"/>
			<rich:dataList id="dlValidAccounts" var="item" value="#{mayorizacionController.lstResultMsgValidation}" 
				rows="#{mayorizacionController.intValidResultAccounts}" type="disc"
				title="Cuentas no válidas"
				rendered="#{not empty mayorizacionController.lstResultMsgValidation}">
                <h:outputText value="#{item}" styleClass="msgError"/>
        	</rich:dataList>
		</h:panelGrid>
		
    </h:panelGroup>
</h:form>