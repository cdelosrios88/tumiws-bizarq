<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos	  -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Relación Perfil / MOF    -->
	<!-- Fecha     : 14/11/2011               -->

<script type="text/javascript">
      function jsSeleccionPerfilMof(itemIdEmpresa,itemIdPerfil,itemIdVersion){
      	document.getElementById("frmRelPerfMofModalPanel:hiddenIdEmpresa").value =itemIdEmpresa;
      	document.getElementById("frmRelPerfMofModalPanel:hiddenIdPerfil").value  =itemIdPerfil;
      	document.getElementById("frmRelPerfMofModalPanel:hiddenIdVersion").value =itemIdVersion;
      }
      
      function enable_cboPerfil(status){
      	if(status == false){
      		document.getElementById("frmPrincipal:idCboPerfilEmpresa").style.visibility = 'visible';
      	}else document.getElementById("frmPrincipal:idCboPerfilEmpresa").style.visibility = 'hidden';
	    
	  }
      
      function verificaUpload(){
	  	//Comprobar archivos tipo Pdf.
	  	if(document.getElementById('frmPrincipal:uploadPdf').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
      		document.getElementById('frmPrincipal:hiddenStrFileDocName').value;
      	} else{
      		document.getElementById('frmPrincipal:hiddenStrFileDocName').value='';
      	}
      	alert(document.getElementById('frmPrincipal:hiddenStrFileDocName').value);
	  }
	  
	  function verificaUploadOrg(){
	  	if(document.getElementById('frmPanelOrg:uploadOrg').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
      		document.getElementById('frmPanelOrg:hiddenStrFileNameOrg').value;
      	} else{
      		document.getElementById('frmPanelOrg:hiddenStrFileNameOrg').value='';
      	}
	  }
</script>
	<rich:modalPanel id="panelUpdateDelete" width="420" height="120"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Registro" />
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelink"/>
               <rich:componentControl for="panelUpdateDelete" attachTo="hidelink" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmRelPerfMofModalPanel">
        	 <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar el Registro seleccionado?"  style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{perfilMofController.modificarPerfilMof}" styleClass="btnEstilos" />
                    <h:commandButton value="Eliminar" actionListener="#{perfilMofController.eliminarPerfilMof}" styleClass="btnEstilos" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" />
                </h:panelGrid>
                <h:inputHidden id="hiddenIdEmpresa"/>
                <h:inputHidden id="hiddenIdPerfil"/>
                <h:inputHidden id="hiddenIdVersion"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
    <rich:modalPanel id="panelOrganigrama" width="640" height="250"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Organigrama"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkOrg"/>
               <rich:componentControl for="panelOrganigrama" attachTo="hidelinkOrg" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmPanelOrg">
        	 <div align="left">
	        	 <rich:panel style="border:0px solid #17356f;background-color:#DEEBF5;width:600px;">                 
	                <h:panelGrid columns="4">
	                	<rich:column style="width:150px;">
	                    	<h:outputText value="Empresa"/>
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<h:outputText value=":"/>
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<h:selectOneMenu style="width: 300px;" value="#{perfilMofController.intCboEmpresaOrg}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							</h:selectOneMenu>
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<h:outputText value="#{perfilMofController.msgTxtEmpresaOrg}" styleClass="msgError"/>
	                    </rich:column>
	                </h:panelGrid>
	                <h:panelGrid columns="4">
	                    <rich:column style="border:none;width:150px;">
	                    	<h:outputText value="Organigrama General" />
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<h:outputText value=":" />
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<rich:fileUpload  id="uploadOrg" addControlLabel="Agregar Archivo"
					             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
					             fileUploadListener="#{fileUploadController.listener}"
								 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
								 onupload="document.getElementById('frmPanelOrg:hiddenStrFileNameOrg').value=event.memo.entry.fileName;"
								 immediateUpload="#{fileUploadController.autoUpload}"
								 acceptedTypes="pdf">
								 <f:facet name="label">
								 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
								 </f:facet>
							</rich:fileUpload>
	                    </rich:column>
	                    <rich:column style="border:none;">
	                    	<h:outputText value="#{perfilMofController.msgTxtArchivoOrg}" styleClass="msgError"/>
	                    </rich:column>
	                </h:panelGrid>
	                <h:inputHidden id="hiddenStrFileNameOrg"></h:inputHidden>
	                
	                <rich:spacer height="16px"></rich:spacer>
                    <div align="center" style="padding-right:10px;">
                    	<h:commandButton value="Grabar"  styleClass="btnEstilos" actionListener="#{perfilMofController.grabarOrganigrama}" onclick="verificaUploadOrg();"></h:commandButton>
                    </div>
	             </rich:panel>
             </div>
        </h:form>    
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <rich:tabPanel>
        <rich:tab label="Relación Perfil / MOF"  >
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width: 800px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<h:panelGrid columns="7" style="width: 550px" border = "0">
                        <h:outputText value="Empresa : " ></h:outputText>
                        <h:selectOneMenu valueChangeListener="#{controlsFiller.reloadCboPerfiles}"
							style="width: 190px;" value="#{perfilMofController.cboEmpresa}">
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							<a4j:support event="onchange" reRender="idCboPerfil" ajaxSingle="true"/>
						</h:selectOneMenu>
                        <h:outputText value="Perfil :  "  style="padding-left: 5px;"></h:outputText>
                        <h:selectOneMenu id="idCboPerfil" style="width: 190px;" value="#{perfilMofController.cboPerfil}">
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listPerfil}"
								itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                        <h:outputText value="Estado : "  style="padding-left: 5px;"></h:outputText>
						<h:selectOneMenu value="#{perfilMofController.cboEstado}">
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                        <rich:column style="padding-left:10px;border:none;">
                        	<a4j:commandButton value="Buscar" actionListener= "#{perfilMofController.listarPerfilMof}" styleClass="btnEstilos" reRender="pgPerfilMof"/>
                        </rich:column>
                     </h:panelGrid>
         			 
         			 <rich:spacer height="10px"></rich:spacer>
         			 
         			 <div align="center" style="padding-left: 10px">
                           <h:panelGrid id="pgPerfilMof" columns="1"  border="0">
                            <rich:extendedDataTable id="tbAdminMenu" value="#{perfilMofController.beanListPerfilMof}" 
                           	var="item" rowKeyVar="rowKey" sortMode="single"  rendered="#{not empty perfilMofController.beanListPerfilMof}"
                           	onRowClick="jsSeleccionPerfilMof('#{item.intIdEmpresa}', '#{item.intIdPerfil}', '#{item.intVersion}');"
                           	width="600px" height="170px" rows="#{perfilMofController.rows}">
                                    <rich:column width="22">
                                        <div align="center"><h:outputText value="#{rowKey+1}"></h:outputText></div>
                                    </rich:column>
                                    <rich:column width="150px">
                                        <f:facet name="header">
                                            <h:outputText value="Empresa"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strEmpresa}"></h:outputText>
                                    </rich:column>
                                    <rich:column width="90px">
                                        <f:facet name="header">
                                            <h:outputText value="Estado"></h:outputText>
                                        </f:facet>
                                        <div align="center"><h:outputText value="#{item.strEstado}"></h:outputText></div>
                                    </rich:column>
                                    <rich:column width="160px">
                                        <f:facet name="header">
                                            <h:outputText value="Perfil"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strPerfil}"></h:outputText>
                                    </rich:column>
                                    <rich:column width="180px">
                                        <f:facet name="header">
                                            <h:outputText value="Documento MOF"></h:outputText>
                                        </f:facet>
                                        <h:commandLink value="#{item.strArchivo}" actionListener="#{perfilMofController.downloadArc}" style="color:blue">
				                       		<f:param id="paramArc" name="paramArc" value="#{item.strArchivo}" />
				                        </h:commandLink>
                                    </rich:column>
                                    <f:facet name="footer">
                                    	<rich:datascroller for="tbAdminMenu" maxPages="10"/>
                                    </f:facet>
                          </rich:extendedDataTable>
                          </h:panelGrid>
                          <rich:spacer height="4px"/><rich:spacer/>
                          <rich:spacer height="4px"/><rich:spacer/>
                          
                          <h:panelGrid columns="2">
								<h:outputLink value="#" id="linkPerfMof">
							        <h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
							        <rich:componentControl for="panelUpdateDelete" attachTo="linkPerfMof" operation="show" event="onclick"/>
							    </h:outputLink>
							<h:outputText value="Para Eliminar o Actualizar Hacer click en un Registro" style="color:#8ca0bd" />                                     
						  </h:panelGrid>
                     </div>
				 </rich:panel>
				 
				 <rich:spacer height="10px"/><rich:spacer height="10px"/>
	   	    	 <h:panelGrid columns="4">
	                 <h:commandButton value="Nuevo" actionListener="#{perfilMofController.habilitarGrabarPerfilMof}" styleClass="btnEstilos"/>                     
	                 <h:commandButton id="btnGuardarPerfMof" value="Grabar" onclick="return validaForm();" actionListener="#{perfilMofController.grabarPerfilMof}" onclick="verificaUpload();" styleClass="btnEstilos"/>												                 
	                 <h:commandButton value="Cancelar" actionListener="#{perfilMofController.cancelarGrabarPerfilMof}" styleClass="btnEstilos"/>
	                 <h:outputLink value="#" id="linkOrganigrama">
				         <h:graphicImage value="/images/icons/organigrama.jpg" style="border:0px;"/>
				         <rich:componentControl for="panelOrganigrama" attachTo="linkOrganigrama" operation="show" event="onclick"/>
				     </h:outputLink>
	          	 </h:panelGrid>
		         
	          	 <rich:spacer height="4px"/><rich:spacer height="4px"/>
	          	 <rich:panel style="width: 760px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{perfilMofController.perfilMofRendered}">
	          	 	<h:panelGrid columns="3" border="0" >
		               <rich:column style="width: 150px; border: none">
		                  <h:outputText value="Empresa" style="padding-left:10px" ></h:outputText>
		               </rich:column>
		               <rich:column>
		                  	<h:selectOneMenu id="idCboEmpresa"
			                  	style="width: 300px;" disabled="#{perfilMofController.formPerfilMofEnabled}"
			                  	valueChangeListener="#{perfilMofController.reloadCboPerfiles}"
			                   	value="#{perfilMofController.intCboEmpresa}">
							    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							    <a4j:support event="onchange" reRender="idCboPerfilEmpresa" ajaxSingle="true"/>
							</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{perfilMofController.msgTxtEmpresa}" styleClass="msgError"></h:outputText>
		               </rich:column>
		            </h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="3" border="0" >
		               <rich:column style="width: 150px; border: none">
		                  <h:outputText value="Estado" style="padding-left:10px" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectOneMenu value="#{perfilMofController.beanPerfilMof.intIdEstado}">
		                  		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{perfilMofController.msgTxtEstado}" styleClass="msgError"/>
		               </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid columns="5" border="0" >
		               <rich:column style="width: 225px; border: none">
		                  <h:outputText value="Tipo de Perfil" style="padding-left:10px" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectBooleanCheckbox id="chkPerfil" value="#{perfilMofController.chkPerfil}" disabled="#{perfilMofController.chkTodosEnabled}" onclick="enable_cboPerfil(this.checked)"/>
		               </rich:column>
		               <rich:column style="width:100px; border:none;">
		                    <h:outputText value="Todos" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectOneMenu id="idCboPerfilEmpresa" disabled="#{perfilMofController.formPerfilMofEnabled}" rendered="#{perfilMofController.cboPerfilRendered}"
		                  		style="width: 300px;" value="#{perfilMofController.intCboPerfilEmpresa}">
					        	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{perfilMofController.listPerfil}"
									itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
					       	</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{perfilMofController.msgTxtPerfil}" styleClass="msgError"/>
		               </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid columns="3" border="0">
		            	<rich:column style="width:155px; border: none;">
		                    <h:outputText value="Documentación de MOF:"  />
		                </rich:column>
		                <rich:column style="padding-left:10px;border: none;">
		                    <rich:fileUpload  id="uploadPdf" addControlLabel="Agregar Archivo"
					             disabled="#{perfilMofController.formPerfilMofEnabled}"
					             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
					             fileUploadListener="#{fileUploadController.listener}"
								 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
								 onupload="document.getElementById('frmPrincipal:hiddenStrFileDocName').value=event.memo.entry.fileName;"
								 immediateUpload="#{fileUploadController.autoUpload}"
								 acceptedTypes="pdf">
								 <f:facet name="label">
								 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
								 </f:facet>
							</rich:fileUpload>
		                </rich:column>
		                <rich:column style="width: 120px; border: none">
		                    <h:outputText value="#{perfilMofController.msgTxtDocum}" styleClass="msgError"/>
		               	</rich:column>
		            </h:panelGrid>
		            <h:inputHidden id="hiddenStrFileDocName"></h:inputHidden>
		            
		            <h:panelGrid id="pArchivo" columns="2" border="0" rendered="#{perfilMofController.strAdjuntoRendered}">
		            	<rich:column style="width:80px;border:none;">
			            	<h:outputText value="Archivo:" />
			            </rich:column>
			            <rich:column style="border:none;">
			            	<h:inputText value="#{perfilMofController.beanPerfilMof.strArchivo}" size="80"  readonly="true"/>
			            </rich:column>
		            </h:panelGrid>
		            
		    	</rich:panel>
			</rich:panel>
    	</rich:tab>
    	
    	<!-- Administración de Documentación -->
     	<rich:tab label="Administración Documentación"  >
    		<rich:panel style="border:1px solid #17356f ;width: 800px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			 <h:panelGrid columns="7" style="width: 550px" border = "0">
                        <h:outputText value="Empresa : " ></h:outputText>
                        <h:selectOneMenu valueChangeListener="#{controlsFiller.reloadCboPerfiles}"
							style="width: 190px;" value="#{perfilMofController.strCboEmpresaAdm}">
							<f:selectItems value="#{controlsFiller.cboEmpresas}"/>
							<a4j:support event="onchange" reRender="idCboPerfilAdm" ajaxSingle="true"/>
						</h:selectOneMenu>
                        <h:outputText value="Perfil :"  style="padding-left: 5px;"/>
                        <h:selectOneMenu id="idCboPerfilAdm" style="width: 190px;" value="#{perfilMofController.strCboPerfilEmpAdm}">
							<f:selectItems value="#{controlsFiller.cboPerfil}" />
						</h:selectOneMenu>
                        <h:outputText value="Estado : "  style="padding-left: 5px;"></h:outputText>
						<h:selectOneMenu value="#{perfilMofController.cboEstadoAdm}">
							<f:selectItems value="#{parametersController.cboEstados}"/>
						</h:selectOneMenu>
                        <rich:column style="padding-left:10px;border:none;">
                        	<h:commandButton value="Buscar" actionListener= "#{perfilMofController.listarAdmDoc}" styleClass="btnEstilos"/>
                        </rich:column>
                     </h:panelGrid>
                     
                     <rich:dataTable width="740px;" value="#{perfilMofController.beanListAdmDoc}" 
                     	 rows="10" sortMode="true" var="item" rowKeyVar="key"
		             	 onRowMouseOver="this.style.backgroundColor='#dfecab'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.tableBackgroundColor}'">
				        <f:facet name="header">
				            <rich:columnGroup>
				                <rich:column width="22px" rowspan="2">
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Empresa"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Estado"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Perfil"/>
				                </rich:column>
				                <rich:column colspan="3">
				                    <h:outputText value="Documento MOF" />
				                </rich:column>
				                <rich:column breakBefore="true">
				                    <h:outputText value="Archivo" />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Fecha y Hora" />
				                </rich:column>
				                <rich:column>
				                    <h:outputText value="Usuario" />
				                </rich:column>
				            </rich:columnGroup>
				        </f:facet>
				        
				        <rich:column width="15px" style="text-align:center;" >
				            <h:outputText value="#{key+1}" />
				        </rich:column>
				        <rich:column>
				            <h:outputText value="#{item.strEmpresa}" />
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.strEstado}"/>
				        </rich:column>
				        <rich:column  colspan="4">
				        	<h:outputText value="#{item.strPerfil}"/>
				        </rich:column>
				        <rich:column colspan="4" breakBefore="true">
				        	<h:outputText value=""/>
				        </rich:column>
				        <rich:column>
					        <h:commandLink value="#{item.strArchivo}" actionListener="#{perfilMofController.downloadArc}" style="color:blue">
	                       		<f:param id="paramArc" name="paramArc" value="#{item.strArchivo}" />
	                        </h:commandLink>
                        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.daFecRegistro}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="---"/>
				        </rich:column>
				        <a4j:support event="onRowClick" actionListener="#{perfilMofController.editAdmDoc}" reRender="pEmpresa,pOrganigrama,pEstado,pTipoPerfil,pDocumentacion">
				        	<f:param id="intIdEmpresa" name="intIdEmpresa" 	value="#{item.intIdEmpresa}"></f:param>
				        	<f:param id="intIdPerfil"  name="intIdPerfil" 	value="#{item.intIdPerfil}"></f:param>
				        	<f:param id="intVersion"   name="intVersion" 	value="#{item.intVersion}"></f:param>
				        </a4j:support>
				     </rich:dataTable>
                     
                     <rich:spacer height="10px;"></rich:spacer>
                     
	                 <rich:panel style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;" >    
	                     <h:panelGrid id="pEmpresa" columns="2" style="width: 680px" border="0">
	                        <rich:column style="width:150px;border:none;">
	                        	<h:outputText value="Empresa"  style="padding-left:10px;"/>
	                        </rich:column>
	                        <rich:column style="border:none;">
	                        	<h:inputText id="txtEmpresaAdm" value="#{perfilMofController.strEmpresaAdm}" size="70"/>
	                        </rich:column>
	                     </h:panelGrid>
	                     
	                     <h:panelGrid id="pOrganigrama" columns="2">
			             	<rich:column style="width:150px;border:none;">
			             		<h:outputText value="Organigrama General"  style="padding-left:10px;"/>
			             	</rich:column>
			             	<rich:column style="border:none;">
			             		<h:inputText value="#{perfilMofController.strOrganigrama}" size="70"/>
			             	</rich:column>
			             </h:panelGrid>
			             
			             <h:panelGrid id="pEstado" columns="2">
			             	<rich:column style="width:150px;border:none;">
			             		<h:outputText value="Estado"  style="padding-left:10px;"/>
			             	</rich:column>
			             	<rich:column style="border:none;">
			             		<h:selectOneMenu value="#{perfilMofController.beanPerfilMof.intIdEstado}">
									<f:selectItems value="#{parametersController.cboEstados}"/>
								</h:selectOneMenu>
			             	</rich:column>
			             </h:panelGrid>
			             
			             <h:panelGrid id="pTipoPerfil" columns="2" border="0" >
			               <rich:column style="width: 150px; border: none">
			                  <h:outputText value="Tipo de Perfil" style="padding-left:10px" ></h:outputText>
			               </rich:column>
			               <rich:column style="border: none">
			                  	<h:selectOneMenu id="idCboPerfilEmpresaAdmDoc"
			                  		style="width: 300px;" value="#{perfilMofController.strCboPerfilAdm}">
						        	<f:selectItems value="#{controlsFiller.cboPerfiles}"></f:selectItems>
						       	</h:selectOneMenu>
			               </rich:column>
			             </h:panelGrid>
			             
			             <h:panelGrid id="pDocumentacion" columns="4">
			             	<rich:column style="width: 150px; border: none">
			             		<h:outputText value="Documentación MOF"  style="padding-left:10px;"></h:outputText>
			             	</rich:column>
			             	<rich:column style="width: 150px; border: none">
			             		<h:inputText value="#{perfilMofController.beanPerfilMof.strArchivo}" size="60"/>
			             	</rich:column>
			             	<rich:column style="border:none">
			             		<h:outputText value="Número de Versiones:" ></h:outputText>
			             	</rich:column>
			             	<rich:column style="border:none">
			             		<h:inputText value="#{perfilMofController.beanPerfilMof.intVersion}" size="5"/>
			             	</rich:column>
			             </h:panelGrid>
			   		</rich:panel>
         		</rich:panel>
         	</rich:panel>
   		</rich:tab>
   		
      </rich:tabPanel>
   </h:form>