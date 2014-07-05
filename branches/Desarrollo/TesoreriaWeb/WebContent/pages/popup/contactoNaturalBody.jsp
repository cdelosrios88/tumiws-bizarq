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
	
	<rich:modalPanel id="pAgregarArchivoFotoCon" width="360" height="190"
		resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Agregar Foto"></h:outputText>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <h:form id="frmAgregarArchivoFotoContacto">
	    	<h:panelGroup style="background-color:#DEEBF5">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
        				<rich:fileUpload id="upload" 
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar" 
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen" 
				             listHeight="65" 
				             listWidth="320"
				             fileUploadListener="#{fileUploadController.adjuntarFoto}"
							 maxFilesQuantity="1" 
							 doneLabel="Archivo cargado correctamente"
							 immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							 acceptedTypes="jpg, gif, png, bmp">
							 <f:facet name="label">
							 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							 </f:facet>
						</rich:fileUpload>
		        	</rich:column>
		    	</h:panelGrid>
		    	<h:panelGrid columns="2">
					<rich:column width="100">
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
				    		action="#{naturalController.aceptarFoto}"
			        		oncomplete="Richfaces.hideModalPanel('pAgregarArchivoFotoCon')"
			        		reRender="panelFotoPerCon"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>	    
	</rich:modalPanel>
	
	<rich:modalPanel id="pAgregarArchivoFirmaCon" width="360" height="190"
		resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Agregar Firma"></h:outputText>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <h:form id="frmAgregarArchivoFirmaContacto">
	    	<h:panelGroup style="background-color:#DEEBF5">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
        				<rich:fileUpload id="upload" 
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar" 
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen" 
				             listHeight="65" 
				             listWidth="320"				             
				             fileUploadListener="#{fileUploadController.adjuntarFirma}"
							 maxFilesQuantity="1" 
							 doneLabel="Archivo cargado correctamente"
							 immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							 acceptedTypes="jpg, gif, png, bmp">
							 <f:facet name="label">
							 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							 </f:facet>
						</rich:fileUpload>
		        	</rich:column>
		    	</h:panelGrid>
		    	<h:panelGrid columns="2">
					<rich:column width="100">
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
				    		action="#{naturalController.aceptarFirma}"
			        		oncomplete="Richfaces.hideModalPanel('pAgregarArchivoFirmaCon')"
			        		reRender="panelFirmaPerCon"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>	    
	</rich:modalPanel>
	
    <h:form id="frmContactoNatu">
    
			<h:panelGroup styleClass="rich-tabcell-noborder">
				<h:outputText value="#{naturalController.mensaje}" 
					styleClass="msgInfo"
					style="font-weight:bold"
					rendered="#{naturalController.mostrarMensaje}"/>					
			</h:panelGroup>
			
         	<h:panelGrid columns="2"> 
		        <a4j:commandButton value="Grabar" 
		        	actionListener="#{naturalController.addContactoNatu}" 
		        	styleClass="btnEstilos"  
		        	reRender="rpContactoNatu,pgContactoNatu,rpRepLegal,pgRepLegal"
		        	disabled="#{(!naturalController.datosValidados)  || (!naturalController.habilitarEditar)}"
		        	oncomplete="Richfaces.hideModalPanel('mpContactoNatu')"/>
		    	<a4j:commandButton value="Cancelar" 
		    		disabled="#{!naturalController.habilitarEditar}"
		    		styleClass="btnEstilos">
		    		<rich:componentControl for="mpContactoNatu" operation="hide" event="onclick"/>
		    	</a4j:commandButton>
		    </h:panelGrid>
		    <rich:spacer height="4px"></rich:spacer>
		          	 
            <rich:panel style="border:1px solid #17356f; width: 925px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">
             		
             		<h:panelGroup rendered="#{!naturalController.datosValidados}">
             		
                    <h:panelGrid columns="7">
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Vínculo: "></h:outputText>
                    	</rich:column>
                    	
                    	<rich:column width="150">
                     		<h:selectOneMenu style="width:140px" 
                     			value="#{applicationScope.Constante.PARAM_T_TIPOVINCULO_CONTACTO}" 
                     			disabled="true">
                    			<tumih:selectItems var="sel" 
							  		cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  		itemValue="#{sel.intIdDetalle}" 
							  		itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Persona: "></h:outputText>
                    	</rich:column>
                    	
                    	<rich:column width="120">
                     		<h:selectOneMenu value="#{naturalController.intCboTipoPersona}" 
                     			disabled="true">
                    			<tumih:selectItems var="sel" 
                    				cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  		itemValue="#{sel.intIdDetalle}" 
							  		itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	
                    	<rich:column width="144">
                    		<h:outputText value="Número de Documento: "></h:outputText>
                    	</rich:column>
                    	
                    	<rich:column width="120">
                     		<h:selectOneMenu style="width:110px" value="#{naturalController.intCboTipoDoc}" disabled="true">
                    			<tumih:selectItems var="sel" 
                    				cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  		itemValue="#{sel.intIdDetalle}"							  		
							  		itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText size="15" value="#{naturalController.strNroDocIdentidad}" tabindex="80"
                    			onkeydown="return validarNumDocIdentidad(this,event,8)"/>
                    	</rich:column>
                     </h:panelGrid>
                     
                     <rich:spacer height="10"></rich:spacer>
                     
                     <h:panelGrid style="width: 100%">
                     	<a4j:commandButton value="Validar Datos" 
                     		styleClass="btnEstilos1" 
                     		style="width: 100%" 
                     		tabindex="90"
                     		actionListener="#{naturalController.validarContacto}" 
                     		reRender="frmContactoNatu"/>
                     </h:panelGrid>
                     
                     </h:panelGroup>
                     
                     <h:panelGroup rendered="#{naturalController.datosValidados}">                     
 
                     
                     <h:panelGroup layout="block" id="pgContactoNatu">
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Ap. Paterno: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText value="#{naturalController.beanPerNatural.natural.strApellidoPaterno}" 
		                     			size="15" tabindex="100" disabled="#{!naturalController.habilitarEditar}"/>
		                     	</rich:column>
								
                     			<rich:column>
		                     		<h:outputText value="Fecha de Nacimiento: " style="padding-top:0px"/>
		                     	</rich:column>
		                     	<rich:column>
		                     		<rich:calendar datePattern="dd/MM/yyyy" 
		                     			disabled="#{!naturalController.habilitarEditar}"
		                     			value="#{naturalController.beanPerNatural.natural.dtFechaNacimiento}"
		                     			inputStyle="width:70px;" 
		                     			cellWidth="10px" 
		                     			cellHeight="20px" 
		                     			tabindex="130"/>
		                     	</rich:column>
								
                     			<rich:column>
                     				<h:outputText value="Sexo: "/>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneRadio value="#{naturalController.beanPerNatural.natural.intSexoCod}" 
                      					disabled="#{!naturalController.habilitarEditar}"
                      					style="width:155px" 
                      					tabindex="140">
                     					<tumih:selectItems var="sel" 
                     						cache="#{applicationScope.Constante.PARAM_T_TIPOSEXO}" 
							  				itemValue="#{sel.intIdDetalle}" 
							  				itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneRadio>  
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Estado Civil: "/>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.natural.intEstadoCivilCod}" 
                      					disabled="#{!naturalController.habilitarEditar}"
                      					tabindex="150">
                     					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							  			<tumih:selectItems var="sel" 
							  				cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
							  				itemValue="#{sel.intIdDetalle}" 
							  				itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>  
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Persona: "/>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.intTipoPersonaCod}" disabled="true">
                     					<tumih:selectItems var="sel" 
							  				cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  				itemValue="#{sel.intIdDetalle}" 
							  				itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>  
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Vínculo: "/>
                     			</rich:column>
                     			<rich:column>
                      				<h:selectOneMenu value="#{naturalController.beanPerNatural.personaEmpresa.vinculo.intTipoVinculoCod}" 
                      					disabled="true">
                     					<tumih:selectItems var="sel" 
							  				cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  				itemValue="#{sel.intIdDetalle}" 
							  				itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu> 
                     			</rich:column>
                     			<rich:column>
                     				<h:outputText value="Roles Actuales: "/>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText value="#{naturalController.strRoles}" size="30" disabled="true"/>
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="DNI: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText value="#{naturalController.beanPerNatural.documento.strNumeroIdentidad}" 
                     					tabindex="160"
                     					disabled="true"
                     					onkeydown="return validarNumDocIdentidad(this,event,8)"/>
                     			</rich:column>
                     		</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Ap. Materno: "/>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText tabindex="110" size="15" disabled="#{!naturalController.habilitarEditar}"
		                     			value="#{naturalController.beanPerNatural.natural.strApellidoMaterno}"/>
		                     	</rich:column>
		                     	
	                     		<rich:column>
		                     		<a4j:commandButton styleClass="btnEstilos"
				                		disabled="#{!naturalController.habilitarEditar}"
				                		value="Firma"
				                		reRender="pAgregarArchivoFirmaCon"
				                    	oncomplete="Richfaces.showModalPanel('pAgregarArchivoFirmaCon')"
				                    	style="width:50px"/>
		                     	</rich:column>
		                     	<rich:column style="border: solid 1px silver; height:102px; padding:0px">
		                     		<a4j:mediaOutput id="mediaFirma"
		                     			element="img" 
		                     			mimeType="image/jpeg" 
		                     			rendered="#{not empty naturalController.fileFirma}"
		                            	createContent="#{naturalController.paintImageFirma}" 
		                            	value="#{naturalController.fileFirma}"
		                                style="width:100px; height:100px;"
		                                cacheable="false">
		                            </a4j:mediaOutput>		                     		
		                     	</rich:column>
	                     	</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:right">
                     		<h:panelGrid columns="2">
                     			<rich:column>
		                     		<h:outputText value="Nombres: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText tabindex="120" size="25" disabled="#{!naturalController.habilitarEditar}" 
		                     			value="#{naturalController.beanPerNatural.natural.strNombres}"/>
		                     	</rich:column>
		                     	
                     			<rich:column>		                     		
		                     		<a4j:commandButton styleClass="btnEstilos"
				                		disabled="#{!naturalController.habilitarEditar}"
				                		value="Foto"
				                		reRender="pAgregarArchivoFotoCon"
				                    	oncomplete="Richfaces.showModalPanel('pAgregarArchivoFotoCon')"
				                    	style="width:50px"/>
		                     	</rich:column>
		                     	
		                     	<rich:column  style="border: solid 1px silver; height:102px; padding:0px">
		                     		<a4j:mediaOutput id="mediaFoto"
		                     			element="img" 
		                     			mimeType="image/jpeg" 
		                     			rendered="#{not empty naturalController.fileFoto}"
		                            	createContent="#{naturalController.paintImageFoto}" 
		                            	value="#{naturalController.fileFoto}"
		                                style="width:100px; height:100px;" 
		                                cacheable="false">  
		                            </a4j:mediaOutput>
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
 	                     		<h:selectOneMenu value="#{naturalController.intIdOtroDocumento}" 
 	                     			disabled="#{!naturalController.habilitarEditar}"
 	                     			tabindex="170">
 	                     			<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							  		<tumih:selectItems var="sel" 
							  			cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  			itemValue="#{sel.intIdDetalle}" 
							  			itemLabel="#{sel.strDescripcion}"/>
	                     		</h:selectOneMenu>  
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="20" value="#{naturalController.strDocIdentidad}" tabindex="180"/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<a4j:commandButton value="Agregar" 
	                     			disabled="#{!naturalController.habilitarEditar}"
	                     			actionListener="#{naturalController.addOtroDocumento}" 
	                     			reRender="tbOtrosDocumentos" 
	                     			styleClass="btnEstilos" 
	                     			tabindex="190"/>
	                     	</rich:column>
	                     	
	                     	<rich:column>
	                     	</rich:column>
	                     	<rich:column colspan="6">
	                     		<rich:dataTable id="tbOtrosDocumentos" 
	                     			var="item" 
	                     			rowKeyVar="rowKey" 
	                     			sortMode="single" 
	                     			width="400px" 
				    				value="#{naturalController.beanPerNatural.listaDocumento}" 
				    				rows="5"
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
					                        <a4j:commandLink 
					                        	value="Quitar"
					                        	disabled="#{!naturalController.habilitarEditar}"
					                        	actionListener="#{naturalController.quitarOtrosDocumentos}" 
	                     						reRender="tbOtrosDocumentos">
	                     						<f:param name="rowKeyOtrosDocumentos" value="#{rowKey}"></f:param>
	                     					</a4j:commandLink>
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
		                    		<h:outputText value="Comunicaciones"></h:outputText>
		                    	</rich:column>
		                    	<rich:column>
		                    		<a4j:commandButton value="Agregar" 
		                    			disabled="#{!naturalController.habilitarEditar}"
		                    			actionListener="#{comunicacionController.showComuniContactoNatu}" styleClass="btnEstilos1" 
					    				reRender="frmComunicacion"
					    				oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" 
					    				tabindex="210"/>
		                    	</rich:column>
	                    	</rich:columnGroup>
				        
				        	<rich:columnGroup>
				        		<rich:column colspan="4">
				        			<h:panelGroup id="divComuniContactoNatu" layout="block">
				        				<rich:dataTable id="tbComuniContactoNatu" 
				        						value="#{comunicacionController.listComuniContactoNatu}" 
						    					rows="5" 
						    					var="item" 
						    					rowKeyVar="rowKey" 
						    					sortMode="single"
						    					rendered="#{not empty comunicacionController.listComuniContactoNatu}"> 
						    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
							                	<rich:column width="300">
								                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
								                </rich:column>
								                <rich:column width="50">
								                    <a4j:commandLink value="Ver" 
								                    	actionListener="#{comunicacionController.viewComunicacionNatural}"
								                    	reRender="frmComunicacion"
								                    	disabled="#{!naturalController.habilitarEditar}"
								                    	oncomplete="Richfaces.showModalPanel('pAgregarComunicacion'),enableInputs()">
								                    	<f:param name="rowKeyComuniContactoNatu" value="#{rowKey}" />
								                    </a4j:commandLink>
								                </rich:column>
								                <rich:column width="50">
								                    <a4j:commandLink value="Quitar" 
								                    	actionListener="#{comunicacionController.quitarComuniContactoNatu}" 
					                   					disabled="#{!naturalController.habilitarEditar}"
					                   					reRender="tbComuniContactoNatu">
					                   					<f:param name="rowKeyComuniContactoNatu" value="#{rowKey}"></f:param>
					                   				</a4j:commandLink>
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
                    
                    </h:panelGroup>	
                    	
                </rich:panel>
               
         </rich:panel>           
   </h:form>