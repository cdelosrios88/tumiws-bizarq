<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico    		  -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO   			  -->
	<!-- Fecha     : 11/10/2013               -->
	
	<rich:modalPanel id="pAgregarSocioEnEfectuado" width="775" height="275"
	resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Agregar Socio" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pAgregarSocioEnEfectuado" operation="hide"
					event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	
	<rich:spacer height="3px" />
	<h:form id="fAgregarSocioEnEfectuado">
			
	<h:panelGroup>
			<h:panelGrid columns="12" id="panelBusqueda">
				<rich:column width="610" colspan="2">
					<h:selectBooleanCheckbox 
						value="#{efectuadoController.blnFuera}">
						<a4j:support event="onclick" reRender="panelBusqueda,tablaAgregarSocio"
							actionListener="#{efectuadoController.onclickCheckFueraDelEnviado}" />
					</h:selectBooleanCheckbox>
					<h:outputText value="Fuera del Enviado" />
				</rich:column>
				<rich:column width="10">
					<h:outputText value="Socio:"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu value="#{efectuadoController.intNombreoDNI}" style="width: 100px;">
						<f:selectItem itemLabel="Nombre" itemValue="1"/>
						<f:selectItem itemLabel="DNI" itemValue="2"/>
						<a4j:support event="onchange" reRender="panelBusqueda"/>
					</h:selectOneMenu>
				</rich:column>
				
				<rich:column width="100">
					<h:inputText size="60" rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorNombre}"
								value="#{efectuadoController.strNombre}"/>
					<h:inputText size="20" rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorDNI}" 
								onkeydown="return validarNumDocIdentidad(this,event,8)" value="#{efectuadoController.strDNI}"/>
				</rich:column>
				
				<rich:column>
					<a4j:commandButton styleClass="btnEstilos" value="Buscar" reRender="tablaAgregarSocio"
							 rendered="#{efectuadoController.intNombreoDNI ==applicationScope.Constante.intPorDNI
							 		     && efectuadoController.blnFuera}" 
							 action="#{efectuadoController.buscarEnTodasDNI}" style="width:100px" />
					
					<a4j:commandButton styleClass="btnEstilos" value="Buscar" reRender="tablaAgregarSocio"
							  rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorNombre 
							  			 &&  efectuadoController.blnFuera}" 
							 action="#{efectuadoController.buscarEnTodasNombre}" style="width:100px"/>
							 		 
				   <a4j:commandButton styleClass="btnEstilos" value="Buscar" reRender="tablaAgregarSocio"
							 style="width:100px"  action="#{efectuadoController.buscarEnEnviadoxDNI}"
							 rendered="#{efectuadoController.intNombreoDNI ==applicationScope.Constante.intPorDNI 
							 			&& (!efectuadoController.blnFuera)}"/>
							 
					<a4j:commandButton styleClass="btnEstilos" value="Buscar" reRender="tablaAgregarSocio"
							 style="width:100px"  action="#{efectuadoController.buscarEnEnviadoxNombre}"
							 rendered="#{efectuadoController.intNombreoDNI ==applicationScope.Constante.intPorNombre 
							 			&& (!efectuadoController.blnFuera)}"/>		 
				</rich:column>
			</h:panelGrid >	
				
			<h:panelGrid id="tablaAgregarSocio" columns="2">	
				<rich:dataTable var="item" value="#{efectuadoController.listaEfectuadoConceptoCompTemp}"
					rowKeyVar="rowKey" rows="5" sortMode="single" width="750px">
					
						<rich:column width="50" style="text-align: center;">
							<f:facet name="header">
	                    		<h:outputText value="Documento"/>
	                 		</f:facet>
	                 		<h:outputText value="#{item.documento.strNumeroIdentidad}"/>
						</rich:column>
						
						<rich:column width="300" style="text-align: center;">
							<f:facet name="header">
	                    		<h:outputText value="Nombre"/>
	                 		</f:facet>
	                 		<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
						</rich:column>
						
						<rich:column width="50" style="text-align: center;">
							<f:facet name="header">
	                    		<h:outputText value="Pertenece al enviado"/>
	                 		</f:facet>
	                 		<h:outputText value="Si"  rendered="#{!efectuadoController.blnFuera}"/>
	                 		<h:outputText value="No" rendered="#{efectuadoController.blnFuera}"/>
						</rich:column>
						
						<rich:column width="100" style="text-align: center;">
							<f:facet name="header">
	                    		<h:outputText value="enviado"/>
	                 		</f:facet>
	                 		<h:outputText value="#{item.efectuado.bdMontoEnviadoDeEnviomonto + item.yaEfectuado.bdMontoEnviadoDeEnviomonto}"
	                 					  rendered="#{!efectuadoController.blnFuera}"/>
	                 		<h:outputText value="------" rendered="#{efectuadoController.blnFuera}" />
						</rich:column>
						
						
						<rich:column width="250" style="text-align: center;">
							<f:facet name="header">
	                    		<h:outputText value="Opciones"/>
	                 		</f:facet>
	                 		<a4j:commandButton value="Agregar Efectuado" actionListener="#{efectuadoController.verAgregarEfectuado}"
	                 					     oncomplete="Richfaces.showModalPanel('pAgregarEfectuado')" style="width:100%" 
	                 					     reRender="pAgregarEfectuado,fAgregarEfectuado,panelSocio,panelEnviado1,
	                 					               panelEfectuado1,panelEnviado2,panelEfectuado2"	                 					                        					   	   
	                 					       rendered="#{!efectuadoController.blnFuera &&
	                 					                  (efectuadoController.blnTieneEnEDAE ||
	                 					                   !efectuadoController.blnMontoMayorAE)
	                 					                   && !efectuadoController.blnNoTienePlanillaAE}">                					                        					     
	                 					     <f:attribute name="item" value="#{item}"/>
	                 		</a4j:commandButton>
	                 		
	                 		<a4j:commandButton value="Agregar Efectuado" 
	                 					     oncomplete="Richfaces.showModalPanel('idPopupAlertaAgregarEfectuado')"                 					      
	                 					       reRender="idFrmPopupAlerta,idPopupAlertaAgregarEfectuado"	                 					   	   
	                 					       style="width:100%" rendered="#{!efectuadoController.blnFuera &&
	                 					        efectuadoController.blnModalidadAE && efectuadoController.blnMontoMayorAE}">     					     
	                 		</a4j:commandButton>
	                 		<a4j:commandButton value="Agregar Efectuado" 
	                 					     oncomplete="Richfaces.showModalPanel('idPopupAlertaAgregarEfectuadoPL')"                 					      
	                 					       reRender="idFrmPopupAlertaPL,idPopupAlertaAgregarEfectuadoPL" style="width:100%"	                 					   	   
	                 					        rendered="#{!efectuadoController.blnFuera 
	                 					        		    && efectuadoController.blnNoTienePlanillaAE 
	                 					        			&& !efectuadoController.blnModalidadAE}">                   					     
	                 		</a4j:commandButton>
								                 		
	                 		<a4j:commandButton value="Agregar Enviado"  reRender="idPopupAgregarEnviado,idFrmPopupAgregarEnviado"                 							 
	                 						  oncomplete="Richfaces.showModalPanel('idPopupAgregarEnviado'),
	                 						              Richfaces.hideModalPanel('idPopupAgregarSocio')"
	                 						  actionListener="#{efectuadoController.irAgregarEnviado}"                 						  			  
	                 				          rendered="#{efectuadoController.blnFuera &&
	                 				                      efectuadoController.blnEnOtrUE &&
	                 				                      !efectuadoController.blnPopupModalidadDistinta}"
	                 				          style="width:100%">	
	                 				             <f:attribute name="item" value="#{item}"/>                 				           
	                 		 </a4j:commandButton>
	                 		 
	                 		 <a4j:commandButton value="Agregar Enviado"  reRender="idPopupAgregarEnviado,idFrmPopupAgregarEnviado"                 							 
	                 						  oncomplete="Richfaces.showModalPanel('idPopupAlertaAgregarEnviado')"	                 						  			                  						  			  
	                 				          rendered="#{efectuadoController.blnFuera
	                 				          			 && efectuadoController.blnEnOtrUE 
	                 				             		 && !efectuadoController.blnPopupModalidadDistinta}"
	                 				          style="width:100%">                 				                            				           
	                 		 </a4j:commandButton>
	                 		 
	                 		<a4j:commandButton value="Agregar Enviado" style="width:100%" 
	                 						   actionListener="#{efectuadoController.irAgregarEnviado}"	                 						   	                 							 
	                 						   oncomplete="Richfaces.showModalPanel('idPopupAgregarEnviado'),
	                 						  			 Richfaces.hideModalPanel('idPopupAgregarSocio')"
	                 				           reRender="idPopupAgregarEnviado,idFrmPopupAgregarEnviado" 
	                 				           rendered="#{efectuadoController.blnFuera && 
	                 				            			!efectuadoController.blnEnOtrUE && 
	                 				            			!efectuadoController.blnPopupModalidadDistinta}">
	                 				           <f:attribute name="item" value="#{item}"/>
	                 		 </a4j:commandButton>
	                 		 <a4j:commandButton value="Agregar Enviado" 
	                 					       oncomplete="Richfaces.showModalPanel('idPopupAlertaModalidadDistinta')"                 					      
	                 					       reRender="idFrmPopupAlertaMD,idIncludePopupAlertaMD" style="width:100%"	                 					   	   
	                 					       rendered="#{efectuadoController.blnFuera 
	                 					        && efectuadoController.blnPopupModalidadDistinta}">                   					     
	                 		</a4j:commandButton>			     
						</rich:column>
						
				</rich:dataTable>
			</h:panelGrid>	
		</h:panelGroup>	
			</h:form>
</rich:modalPanel>