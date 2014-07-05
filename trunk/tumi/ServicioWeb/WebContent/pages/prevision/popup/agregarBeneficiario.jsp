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
	<h:form id="frmBusqBeneficiario">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
		       <h:panelGrid columns="3">
		           <rich:column style="border:none">
		           		<h:outputText value="Beneficiario: "/>
		           </rich:column>
		           <rich:column>
		           	<h:inputText  value="#{solicitudPrevisionController.campoBuscarBeneficiario}"/>
		           </rich:column>
		           <rich:column>
		            	<a4j:commandButton value="Buscar" action="#{solicitudPrevisionController.buscarBeneficiarioFiltro}"
		            		reRender="pgBeneficiarios" styleClass="btnEstilos"/>
		           </rich:column>
	           </h:panelGrid>

           		<h:panelGrid id="pgBeneficiarios">
	              <rich:extendedDataTable id="tblBenenficiario" enableContextMenu="false"
                         			var="itemBeneficiariosPopup" value="#{solicitudPrevisionController.listaBeneficiarioPrevisionBusq}" 
                         			style="margin:0 auto"  rowKeyVar="rowKey" rows="5" sortMode="single" width="650px" 
                         			height="165px">
		  		 	<rich:column width="29px">
                   		<h:outputText value="#{rowKey + 1}"></h:outputText>
                    </rich:column>

                    <rich:column width="260">
                 		<f:facet name="header">
                            <h:outputText value="Nombre"></h:outputText>
                        </f:facet>
					 	<h:outputText value="#{itemBeneficiariosPopup.persona.natural.strApellidoPaterno} #{itemBeneficiariosPopup.persona.natural.strApellidoMaterno},#{itemBeneficiariosPopup.persona.natural.strNombres}"/>
                    </rich:column>
						
                    <rich:column width="110">
                     	<f:facet name="header">
                        	<h:outputText  value="Tipo Documento"></h:outputText>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemBeneficiariosPopup.persona.documento.intTipoIdentidadCod}"/>
                    </rich:column>
					
					<rich:column width="110">
                     	<f:facet name="header">
                        	<h:outputText  value="Documento"></h:outputText>
                        </f:facet>
						<h:outputText value="#{itemBeneficiariosPopup.persona.documento.strNumeroIdentidad}"/>
                    </rich:column>
                    
                    <rich:column width="110">
                 		<f:facet name="header">
                            <h:outputText value="Relación"></h:outputText>
                        </f:facet>                        
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{itemBeneficiariosPopup.intTipoViculo}"/>		
                    </rich:column>
                    <a4j:support event="onRowClick"  
								 actionListener="#{solicitudPrevisionController.seleccionarBeneficiario}"
								 reRender="pgBeneficiario, pgBeneficiarioSepelio, pgLitstaBeneficiarios,pgLitstaBeneficiariosSepelio, 
								 			pgSolicitudPrevision,pgMsgErrorSolPrev, pgBeneficiario">
					       <f:attribute name="itemBeneficiariosPopup" value="#{itemBeneficiariosPopup}"/>
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
						actionListener="#{solicitudPrevisionController.agregarBeneficiarioSocio}"
						oncomplete="Richfaces.hideModalPanel('mpBeneficiario')"
						styleClass="btnEstilos"
						reRender="pgSolicitudPrevision, pgLitstaBeneficiarios, tblBenenficiario,
									pgLitstaBeneficiariosSepelio,tblBenenficiarioSepelio">
					</a4j:commandButton>
					<a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpBeneficiario')"/>

	         	</rich:column>
	        </h:panelGrid>
       </rich:panel>
       
   </h:form>    