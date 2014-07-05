<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     :      							-->
	<!-- Modulo    : Solicitud Prevision                 		-->
	<!-- Prototipo :      							-->
	<!-- Fecha     :                				-->      	
	<h:form id="frmBusqFallecido">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		       <h:panelGrid columns="3">
		           <rich:column style="border:none">
		           		<h:outputText value="Fallecido: "/>
		           </rich:column>
		           <rich:column>
		           	<h:inputText  value="#{solicitudPrevisionController.campoBuscarFallecido}"/>
		           </rich:column>
		           <rich:column>
		            	<a4j:commandButton value="Buscar" action="#{solicitudPrevisionController.buscarFallecidoFiltro}"
		            		reRender="pgFallecidos" styleClass="btnEstilos"/>
		           </rich:column>
	           </h:panelGrid>

           		<h:panelGrid id="pgFallecidos">
	              <rich:extendedDataTable id="tblFallecido" enableContextMenu="false"
                         			var="itemFallecidoPopup" value="#{solicitudPrevisionController.listaFallecidosPrevisionBusq}" 
                         			style="margin:0 auto"  rowKeyVar="rowKey" rows="5" sortMode="single" width="650px" 
                         			height="165px">
		  		 	<rich:column width="29px">
                   		<h:outputText value="#{rowKey + 1}"></h:outputText>
                    </rich:column>

                    <rich:column width="260">
                 		<f:facet name="header">
                            <h:outputText value="Nombre"></h:outputText>
                        </f:facet>
					 	<h:outputText value="#{itemFallecidoPopup.persona.natural.strApellidoPaterno} #{itemFallecidoPopup.persona.natural.strApellidoMaterno},#{itemFallecidoPopup.persona.natural.strNombres}"/>
                    </rich:column>
						
                    <rich:column width="110">
                     	<f:facet name="header">
                        	<h:outputText  value="Tipo Documento"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemFallecidoPopup.persona.documento.intTipoIdentidadCod}"/>
                    </rich:column>
					
					<rich:column width="110">
                     	<f:facet name="header">
                        	<h:outputText  value="Documento"></h:outputText>
                        </f:facet>
						<h:outputText value="#{itemFallecidoPopup.persona.documento.strNumeroIdentidad}"/>
                    </rich:column>
                    
                    <rich:column width="110">
                 		<f:facet name="header">
                            <h:outputText value="Relación"></h:outputText>
                        </f:facet>                        
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemFallecidoPopup.intTipoViculo}"/>		
                    </rich:column>
                    <a4j:support event="onRowClick"  
								 actionListener="#{solicitudPrevisionController.seleccionarFallecido}"
								 reRender="pgFallecido, pgFallecidos,pgSolicitudPrevision,pgMsgErrorSolPrev">
					       <f:attribute name="itemFallecidoPopup" value="#{itemFallecidoPopup}"/>
  					</a4j:support>
	         	  </rich:extendedDataTable>
      	 	</h:panelGrid>
      	 	
      	 	<!--                  	<f:facet name="footer">
	               		<rich:datascroller for="tblBenenficiario" maxPages="5"/>   
	                </f:facet> -->
           
	        <h:panelGrid columns="2">
	         	<rich:column>
	        		<a4j:commandButton
		    			value="Seleccionar"
						actionListener="#{solicitudPrevisionController.agregarFallecidoSocio}"
						oncomplete="Richfaces.hideModalPanel('mpFallecido')"
						styleClass="btnEstilos"
						reRender="pgSolicitudPrevision, pgListaFallecidos, tblBenenficiario,
									tblBenenficiarioSepelio">
					</a4j:commandButton>
					<a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpFallecido')"/>

	         	</rich:column>
	        </h:panelGrid>
       </rich:panel>
       
   </h:form>    