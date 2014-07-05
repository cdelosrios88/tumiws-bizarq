<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
	<rich:jQuery name="disableInputs" selector=":input" query="attr('disabled','disabled');" timing="onJScall" />
	<rich:jQuery name="enableInputs" selector=":input" query="removeAttr('disabled','disabled');" timing="onJScall" />
	
    <h:form id="frmContactoNatu">
    
    		<h:panelGrid columns="3">
    			<rich:column width="165">
    				<h:outputText value="Nombre de Persona Jurídica: "></h:outputText>
    			</rich:column>
    			<rich:column width="285">
    				<h:inputText value="#{perJuridicaController.perJuridica.intIdPersona} - #{perJuridicaController.perJuridica.juridica.strRazonSocial}" size="50" disabled="true"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="RUC - #{perJuridicaController.perJuridica.strRuc}" size="20" disabled="true"></h:inputText>
    			</rich:column>
    		</h:panelGrid>
    		<rich:spacer height="15"></rich:spacer>
         	<h:panelGrid columns="2"> 
		        <a4j:commandButton value="Grabar" actionListener="#{naturalController.addContactoNatu}" styleClass="btnEstilos"  
		        				   reRender="rpContactoNatu,pgContactoNatu" oncomplete="Richfaces.hideModalPanel('mpContactoNatu')"/>
		    	<a4j:commandButton value="Cancelar" styleClass="btnEstilos">
		    		<rich:componentControl for="mpContactoNatu" operation="hide" event="onclick"/>
		    	</a4j:commandButton>
		    </h:panelGrid>
		    <rich:spacer height="4px"></rich:spacer>
		          	 
            <rich:panel style="border:1px solid #17356f; width: 925px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">
             		
                    <h:panelGrid columns="7">
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Vínculo: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="150">
                     		<h:selectOneMenu style="width:140px" value="#{applicationScope.Constante.PARAM_T_TIPOVINCULO_CONTACTO}" disabled="true">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Persona: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                     		<h:selectOneMenu value="#{naturalController.intCboTipoPersona}" disabled="true">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	<rich:column width="144">
                    		<h:outputText value="Número de Documento: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                     		<h:selectOneMenu style="width:110px" value="#{naturalController.intCboTipoDoc}" disabled="true">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText size="15" value="#{naturalController.strNroDocIdentidad}" tabindex="80"></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
                     <h:panelGrid style="width: 100%">
                     	<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" tabindex="90"
                     					   actionListener="#{naturalController.validarContacto}" reRender="frmContactoNatu"></a4j:commandButton>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
                     <h:panelGrid>
                     	<h:outputText value="Datos Personales" styleClass="estiloLetra1"></h:outputText>
                     </h:panelGrid>
                     <rich:spacer height="8"></rich:spacer>
                     <rich:spacer height="8"></rich:spacer>
                     
                     <h:panelGroup layout="block" id="pgContactoNatu">
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Ap. Paterno: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText value="#{naturalController.beanPerNatural.natural.strApellidoPaterno}" size="15" tabindex="100"></h:inputText>
		                     	</rich:column>
								
                     			<rich:column>
		                     		<h:outputText value="Fecha de Nacimiento: " style="padding-top:0px"></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<rich:calendar datePattern="dd/MM/yyyy" value="#{naturalController.beanPerNatural.natural.dtFechaNacimiento}"
		                     		inputStyle="width:70px;" cellWidth="10px" cellHeight="20px" tabindex="130"/>
		                     	</rich:column>
								
                     			<rich:column>
                     				<h:outputText value="Sexo: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneRadio value="#{naturalController.beanPerNatural.natural.intSexoCod}" style="width:155px" tabindex="140">
                     					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSEXO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneRadio>  
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Estado Civil: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.natural.intEstadoCivilCod}" tabindex="150">
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>  
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Persona: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.intTipoPersonaCod}" disabled="true">
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>  
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Vínculo: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.personaEmpresa.vinculo.intTipoVinculoCod}" disabled="true">
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu> 
                     			</rich:column>
                     			<%-- 
                     			<rich:column>
                     				<h:outputText value="Cargo: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:selectOneMenu value="#{naturalController.beanPerNatural.natural.perLaboral.intCargo}">
		     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CARGOPERSONAL}" 
									  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		     						</h:selectOneMenu>
                     			</rich:column>
                     			--%>
                     			<rich:column>
                     				<h:outputText value="Roles Actuales: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText value="#{naturalController.strRoles}" size="30" disabled="true"></h:inputText>
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Número Documento: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText value="#{naturalController.beanPerNatural.documento.strNumeroIdentidad}" tabindex="160"
                     							onkeydown="return validarNumDocIdentidad(this,event,8)"></h:inputText>
                     			</rich:column>
                     		</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Ap. Materno: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText tabindex="110" size="15" value="#{naturalController.beanPerNatural.natural.strApellidoMaterno}"></h:inputText>
		                     	</rich:column>
		                     	
	                     		<rich:column>
		                     		<h:outputText value="Firma: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:graphicImage width="200" height="80"></h:graphicImage>
		                     	</rich:column>
	                     	</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:right">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Nombres: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText tabindex="120" size="25" value="#{naturalController.beanPerNatural.natural.strNombres}"></h:inputText>
		                     	</rich:column>
		                     	
                     			<rich:column>
		                     		<h:outputText value="Foto: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:graphicImage width="80" height="80"></h:graphicImage>
		                     	</rich:column>
                     		</h:panelGrid>
                     	</h:panelGroup>
                     </h:panelGroup>
                     
                     <h:panelGroup layout="block" style="clear: both">
                     	<h:panelGrid columns="4" styleClass="tableCellBorder2" style="margin-left:-10px">
	                     	<rich:column width="117">
	                     		<h:outputText value="Otros Documentos: "></h:outputText>
	                     	</rich:column>
	                     	<rich:column>
 	                     		<h:selectOneMenu value="#{naturalController.intIdOtroDocumento}" tabindex="170">
 	                     			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     		</h:selectOneMenu>  
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="20" value="#{naturalController.strDocIdentidad}" tabindex="180"></h:inputText>
	                     	</rich:column>
	                     	<rich:column>
	                     		<a4j:commandButton value="Agregar" actionListener="#{naturalController.addOtroDocumento}" 
	                     						reRender="tbOtrosDocumentos" styleClass="btnEstilos" tabindex="190"></a4j:commandButton>
	                     	</rich:column>
	                     	
	                     	<rich:column>
	                     	</rich:column>
	                     	<rich:column colspan="6">
	                     		<rich:dataTable id="tbOtrosDocumentos" var="item" rowKeyVar="rowKey" sortMode="single" width="400px" 
				    							value="#{naturalController.beanPerNatural.listaDocumento}" rows="5"
				    							rendered="#{not empty naturalController.beanPerNatural.listaDocumento}">
				    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">	
					                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
					                        <h:outputText value="#{rowKey + 1}"></h:outputText>
					                    </rich:column>
					                    <rich:column style="border: 1px solid #C0C0C0" width="420">
					                    	<f:facet name="header">
					                            <h:outputText value="Documento de Identidad"></h:outputText>
					                        </f:facet>
					                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
					                    </rich:column>
					                    <rich:column style="border: 1px solid #C0C0C0" width="420">
					                    	<f:facet name="header">
					                            <h:outputText value="Número"></h:outputText>
					                        </f:facet>
					                        <h:outputText value="#{item.strNumeroIdentidad}"></h:outputText>
					                    </rich:column>
					                    <rich:column style="border: 1px solid #C0C0C0" width="420">
					                    	<f:facet name="header">
					                            <h:outputText value="Quitar"></h:outputText>
					                        </f:facet>
					                        <a4j:commandButton value="Quitar" actionListener="#{naturalController.quitarOtrosDocumentos}" 
	                     						reRender="tbOtrosDocumentos" styleClass="btnEstilos">
	                     						<f:param name="rowKeyOtrosDocumentos" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
					                    </rich:column>
					                </rich:columnGroup>
				                    <f:facet name="footer">   
					                    <rich:datascroller for="tbOtrosDocumentos" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
	                     	</rich:column>
	                    </h:panelGrid>
	                    
	                    <h:panelGrid columns="4" styleClass="tableCellBorder2" style="margin-left:-10px">
	                    	
	                    </h:panelGrid>
	                    
	                    <h:panelGrid columns="4" style="margin-left:-10px">
	                    	<rich:columnGroup>
		                    	<rich:column>
	 	                    		<h:selectBooleanCheckbox tabindex="200"></h:selectBooleanCheckbox>  
		                    	</rich:column>
		                    	<rich:column>
		                    		<h:outputText value="Comunicaciones"></h:outputText>
		                    	</rich:column>
		                    	<rich:column>
		                    		<a4j:commandButton value="Agregar" actionListener="#{comunicacionController.showComuniContactoNatu}" styleClass="btnEstilos1" 
					    								reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" tabindex="210"></a4j:commandButton>
		                    	</rich:column>
	                    	</rich:columnGroup>
				        
				        	<rich:columnGroup>
				        		<rich:column colspan="4">
				        			<h:panelGroup id="divComuniContactoNatu" layout="block">
				        				<rich:dataTable id="tbComuniContactoNatu" value="#{comunicacionController.listComuniContactoNatu}" 
						    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
						    					rendered="#{not empty comunicacionController.listComuniContactoNatu}"> 
						    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
							                	<rich:column width="180">
								                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
								                </rich:column>
								                <rich:column>
								                    <a4j:commandButton value="Ver" actionListener="#{comunicacionController.viewComunicacionNatural}"
								                    	reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion'),enableInputs()" styleClass="btnEstilos1">
								                    	<f:param name="rowKeyComuniContactoNatu" value="#{rowKey}" />
								                    </a4j:commandButton>
								                </rich:column>
								                <rich:column>
								                    <a4j:commandButton value="Quitar" actionListener="#{comunicacionController.quitarComuniContactoNatu}" 
					                   						reRender="tbComuniContactoNatu" styleClass="btnEstilos1">
					                   						<f:param name="rowKeyComuniContactoNatu" value="#{rowKey}"></f:param>
					                   					</a4j:commandButton>
								                </rich:column>
							                </rich:columnGroup>
							                <f:facet name="footer">   
							                    <rich:datascroller for="tbComuniContactoNatu" maxPages="5"/>   
							                </f:facet> 
						                </rich:dataTable>
				        			</h:panelGroup>
				        		</rich:column>
				        	</rich:columnGroup>
		                </h:panelGrid>
                     </h:panelGroup>
                    	
                </rich:panel>
               
         </rich:panel>           
   </h:form>