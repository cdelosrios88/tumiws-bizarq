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
	
	<rich:modalPanel id="pRelacionarNoSocio" width="635" height="340"
						resizeable="false" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Relacionar No Socio" />
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png"
					styleClass="hidelink">
					<rich:componentControl for="pRelacionarNoSocio" operation="hide"
						event="onclick" />
				</h:graphicImage>
			</h:panelGroup>
		</f:facet>
		<rich:spacer height="3px" />
		<h:form id="fRelacionarNoSocio">
			<h:panelGroup>
					<h:panelGrid id="panelBusqueda" columns="12">
						<rich:column width="10">
							<h:outputText value="Trabajador"/>
						</rich:column>
						<rich:column width="90">
							<h:selectOneMenu value="#{efectuadoController.intNombreoDNI}" style="width:100px;">
								<f:selectItem itemLabel="Nombre" itemValue="1"/>
								<f:selectItem itemLabel="DNI" itemValue="2"/>
								<a4j:support event="onchange" reRender="panelBusqueda"/>
							</h:selectOneMenu>
						</rich:column>
						
						<rich:column width="60">
							<h:inputText  rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorNombre}" 
											value="#{efectuadoController.strNombre}" size="35"/>
							<h:inputText  onkeydown="return validarNumDocIdentidad(this,event,8)"
									    value="#{efectuadoController.strDNI}" size="10"
										rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorDNI}"/>
						</rich:column>
						
						<rich:column>
							<div align="left">
							<a4j:commandButton styleClass="btnEstilos" value="Buscar" 
												action="#{efectuadoController.buscarRelacionarNoSocioxN}"
												rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorNombre}"							
												 style="width:90px"
												reRender="tablaRelacionarSocio"/>
							<a4j:commandButton styleClass="btnEstilos" value="Buscar"
												action="#{efectuadoController.buscarRelacionarNoSocioxDNI}"  
												rendered="#{efectuadoController.intNombreoDNI == applicationScope.Constante.intPorDNI}"							
												style="width:100px"
												reRender="tablaRelacionarSocio"/>
							</div> 
						</rich:column>
					</h:panelGrid>
				</h:panelGroup>
				<h:panelGroup>
					<h:panelGrid id="tablaRelacionarSocio" columns="2">
						<rich:extendedDataTable id="tblSocios" enableContextMenu="false" style="margin:0 auto"						
							var="item" value="#{efectuadoController.listandoPersona}"
							rowKeyVar="rowKey" rows="4" sortMode="single" width="580px" height="165px">
						
							<rich:column width="30">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            	</rich:column>
			                    
				            <rich:column width="80">
		                        <f:facet name="header">
		                            <h:outputText value="Dni"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.documento.strNumeroIdentidad}"></h:outputText>
				            </rich:column> 
				            
				            <rich:column width="300">
		                        <f:facet name="header">
		                            <h:outputText value="Nombre"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"></h:outputText>
				            </rich:column>			           
			                 
			                 <rich:column width="150">
		                        <f:facet name="header">
		                            <h:outputText value="Opcion"/>
		                        </f:facet>
								<a4j:commandButton value="AgregarPersona" oncomplete="Richfaces.showModalPanel('pAgregarPersona')"
												  style="width:100%" actionListener="#{efectuadoController.verAgregarPersona}"
												  rendered="#{!efectuadoController.blnGrabarNuevaPersona &&
												  			   efectuadoController.blnEstaEnSocio &&
												  			   efectuadoController.blnEstaEnSocioEstructura}"
												  reRender="panelMonto,panelAgregar,pAgregarPersona,formAgregarPersona">
									<f:attribute name="item" value="#{item}"/>
								</a4j:commandButton>
								<a4j:commandLink value="Seleccionar" actionListener="#{efectuadoController.selecionarNuevaPersona}"
												rendered="#{efectuadoController.blnGrabarNuevaPersona}"
												reRender="panelMonto,panelAgregar,panelMensaje">								
									<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
								<a4j:commandButton value="AgregarPersona" oncomplete="Richfaces.showModalPanel('pNewPersona')"
												  style="width:100%" rendered="#{efectuadoController.blnNoEstaEnSocio}"
												  actionListener ="#{efectuadoController.newPersona}"												  
												  reRender="panelMonto,panelAgregar,pNewPersona,fmNewPersona">
									<f:attribute name="item" value="#{item}"/>
								</a4j:commandButton>
								<a4j:commandButton value="AgregarPersona" oncomplete="Richfaces.showModalPanel('idPopupAlertaAgregarNoSocio')"
												  style="width:100%" rendered="#{efectuadoController.blnEstaEnSocio &&
												  					    efectuadoController.blnNoEstaEnSocioEstructura}"												  												  
												  reRender="idPopupAlertaAgregarNoSocio,idFrmPopupAlertaans">									
								</a4j:commandButton>				  
				            </rich:column>
			                    
						   <f:facet name="footer">
						   		 <h:panelGroup layout="block">
							   		 <rich:datascroller for="tblSocios" maxPages="10"/>
						   		 </h:panelGroup>  
					       </f:facet>
						</rich:extendedDataTable>
					</h:panelGrid>
								
					<rich:spacer height="8px"/>
					
					<h:panelGrid id="panelMonto" columns="4" width="100%" >						
							<rich:column  colspan="2">
								<div align="left">
									<h:outputText value="MONTO DE EFECTUADO PARA AGREGAR"/>
								</div>	
							</rich:column>
							<rich:column>								
									<h:inputText   value="#{efectuadoController.bdMontoNoSocio}" style="text-align:right"
								             	   onkeypress="return soloNumerosDecimalesPositivos(this)"
								             	   onkeyup="return extractNumber(this,2,false)" onblur="extractNumber(this,2,false);"
								             	   disabled="#{!efectuadoController.blnMontoNoSocio}" maxlength="9">	
								        <a4j:support event="onchange" reRender="panelMensaje, panelMonto, panelAgregar"/>       							            
								    </h:inputText>								
							</rich:column>						
					</h:panelGrid>
					
					<h:panelGroup  style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
						styleClass="rich-tabcell-noborder" id="panelMensaje">
						<h:outputText value="#{efectuadoController.mensajeOperacion}" 
									styleClass="msgInfo" style="font-weight:bold"				
									rendered="#{efectuadoController.mostrarMensajeExito}"/>
						<h:outputText value="#{efectuadoController.mensajeOperacion}" 
									styleClass="msgError" style="font-weight:bold"				
									rendered="#{efectuadoController.mostrarMensajeError}"/>
					</h:panelGroup>
					
					<h:panelGrid id="panelAgregar" columns="2">							
								<a4j:commandButton value= "Validar" action="#{efectuadoController.validarRelacionarNoSocio}"
												   reRender="panelAgregar,panelMensaje"
												   disabled="#{!efectuadoController.blnMontoNoSocio}"/>							
								<a4j:commandButton value="Agregar No Socio"
												   action="#{efectuadoController.agregarNoSocio}"											   
												   reRender="idGrillaTieneEfectuada,idGrillaNoTieneEfectuada,idGrillaDePlanillaEfectuado"
											 	   oncomplete="Richfaces.hideModalPanel('pRelacionarNoSocio')"
											 	   disabled="#{!efectuadoController.blnAgregarNosocio}"/>												
					</h:panelGrid>										
				</h:panelGroup>				
		</h:form>
	</rich:modalPanel>	
		

	