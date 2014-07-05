<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Administración de Formulario y Documentos -->			
	<!-- Fecha     : 14/11/2011               -->

<script type="text/javascript"> 
      function jsSeleccionAmdFormDoc(itemIdEmpresa,itemIdPerfil,itemIdTransaccion,itemIdDoc,itemIdDemo,itemStrDoc,itemStrDemo){      	
      	document.getElementById("frmRelFormDocModalPanel:hiddenIdEmpresa").value	 =itemIdEmpresa;
      	document.getElementById("frmRelFormDocModalPanel:hiddenIdPerfil").value 	 =itemIdPerfil;
      	document.getElementById("frmRelFormDocModalPanel:hiddenIdTransaccion").value =itemIdTransaccion;
      	document.getElementById("frmRelFormDocModalPanel:hiddenIdVersionDoc").value  =itemIdDoc;
      	document.getElementById("frmRelFormDocModalPanel:hiddenIdVersionDemo").value  =itemIdDemo;
      	document.getElementById("frmRelFormDocModalPanel:hiddenStrArchDoc").value	 =itemStrDoc;
      	document.getElementById("frmRelFormDocModalPanel:hiddenStrArchDemo").value	 =itemStrDemo;
      }
      
      function enable_cboPerfil(status){
      	if(status == false){
      		document.getElementById("frmPrincipal:idCboPerfilEmpresa").style.visibility = 'visible';
      	}else document.getElementById("frmPrincipal:idCboPerfilEmpresa").style.visibility = 'hidden';
	    
	  }
	  
	  function enable_PerfTodos(status){
	  	status == status
      	document.getElementById("frmPrincipal:txtPerfilTodos").disabled = status;
      	if(status==true){
      		document.getElementById("frmPrincipal:txtPerfilTodos").value = '';
      	}
	  }
	  
	  function enable_EstMenu(status){
	  	status == status
      	document.getElementById("frmPrincipal:cboMenu1").disabled = status;
      	document.getElementById("frmPrincipal:cboMenu2").disabled = status;
      	document.getElementById("frmPrincipal:cboMenu3").disabled = status;
      	document.getElementById("frmPrincipal:cboMenu4").disabled = status;
	  }
	  
	  function enable_txtProceso(status){
	  	status == status
      	document.getElementById("frmPrincipal:txtProceso").disabled = status;
      	if(status==true){
      		document.getElementById("frmPrincipal:txtProceso").value='';
      	}
	  }
	  
	  function enable_txtDemo(status){
	  	status == status
      	document.getElementById("frmPrincipal:txtDemo").disabled = status;
      	if(status==true){
      		document.getElementById("frmPrincipal:txtDemo").value='';
      	}
	  }
	  
	  function getJSSelectionEmpresa(){
	  	var idEmpresa = document.frmPrincipal["frmPrincipal:idCboEmpresa"].value;
	  	document.frmPrincipal["frmPrincipal:hiddenIdEmpresaFormDoc"].value=idEmpresa;
	  }
	  
	  function getJSSelectionPerfil(){
	  	var idPerfil = document.frmPrincipal["frmPrincipal:idCboPerfilEmpresa"].value;
	  	document.frmPrincipal["frmPrincipal:hiddenIdPerfilFormDoc"].value=idPerfil;
	  	
	  	if(idPerfil==null){
	  		document.frmPrincipal["frmPrincipal:hiddenIdPerfilFormDoc"].value='';
	  	}
	  }
	  
	  function verificaUpload(){
	  	//Comprobar archivos tipo Pdf.
	  	if(document.getElementById('frmPrincipal:uploadPdf').component.idCounter == 0){
	  		document.getElementById('frmPrincipal:hiddenStrFileDocName').value='';
	  	}else{
	  		if(document.getElementById('frmPrincipal:uploadPdf').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
	      		document.getElementById('frmPrincipal:hiddenStrFileDocName').value;
	      	} else{
	      		document.getElementById('frmPrincipal:hiddenStrFileDocName').value='';
	      	}
	  	}
      	//Comprobar archivos tipo Demo.
      	if(document.getElementById('frmPrincipal:uploadDemo').component.idCounter == 0){
      		document.getElementById('frmPrincipal:hiddenStrFileDemoName').value='';
      	}else{
      		if(document.getElementById('frmPrincipal:uploadDemo').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
	      		document.getElementById('frmPrincipal:hiddenStrFileDemoName').value;
	      	} else{
	      		document.getElementById('frmPrincipal:hiddenStrFileDemoName').value='';
	      	}
      	}
      	
      	if(document.getElementById('frmPrincipal:uploadPdf').component.idCounter == 0 &&
      	   document.getElementById('frmPrincipal:uploadDemo').component.idCounter == 0){
      		alert('Debe elegir por lo menos un Tipo de Documento.');
      		return false;
      	}
      	
	  }
	  
	  function soloNumeros(evt){
		//asignamos el valor de la tecla a keynum
		if(window.event){// IE
			keynum = evt.keyCode;
		}else{
			keynum = evt.which;
		}
		//comprobamos si se encuentra en el rango
		if(keynum>47 && keynum<58){
			return true;
		}else{
			return false;
		}
	  }
	  
</script>
	<rich:modalPanel id="panelUpdateDelete" width="420" height="120"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Registro" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelink"/>
               <rich:componentControl for="panelUpdateDelete" attachTo="hidelink" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmRelFormDocModalPanel">
        	 <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar el Registro seleccionado?"  style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{admFormDocController.modificarAdmFormDoc}" styleClass="btnEstilos"  ></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{admFormDocController.eliminarFormDoc}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:inputHidden id="hiddenIdEmpresa"/>
                <h:inputHidden id="hiddenIdPerfil"/>
                <h:inputHidden id="hiddenIdTransaccion"/>
                <h:inputHidden id="hiddenIdTipoDoc"/>
                <h:inputHidden id="hiddenIdTipoDemo"/>
                <h:inputHidden id="hiddenIdVersionDoc"/>
                <h:inputHidden id="hiddenIdVersionDemo"/>
                <h:inputHidden id="hiddenStrArchDoc"/>
                <h:inputHidden id="hiddenStrArchDemo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <rich:tabPanel>
        <rich:tab label="Relación Formulario y Documentación"  >
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width: 780px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<h:panelGrid id="pgbusqEmpresa" columns="4" style="width: 550px" border = "0">
                        <h:outputText id="lblbusqEmpresa" value="Empresa : "  style="padding-left: 15px;"></h:outputText>
                        <h:selectOneMenu id="idCboEmpresaAdmDoc" valueChangeListener="#{controllerFiller.reloadCboPerfiles}"
							style="width: 270px;" value="#{admFormDocController.intCboEmpresa}">
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							<a4j:support event="onchange" reRender="idCboPerfil" ajaxSingle="true"/>
						</h:selectOneMenu>
                        <%--<h:inputText value="#{admFormDocController.txtEmpresa}" size="60"></h:inputText>--%>
                        
                        <h:outputText id="lblbusqPerfil" value="Perfil:"  style="padding-left:10px;"></h:outputText>
                        <rich:column style="border:none;">
			         		<h:selectOneMenu id="idCboPerfil"
								style="width: 250px;" value="#{admFormDocController.intCboPerfilFormDoc}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listPerfil}"
									itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			         	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="5" style="width: 550px" border = "0">
                    	<rich:column style="width:70px;border:none;">
                    		<h:outputText id="lblbusqTipo" value="Tipo : "  style="padding-left: 15px;"></h:outputText>
                    	</rich:column>
						<rich:column style="border:none;">
							<h:selectOneMenu value="#{admFormDocController.cboTipoUsuario}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONFORMACIONEMPRESA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<%--<f:selectItems value="#{parametersController.cboConformacionEmpresa}"/>--%>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="border: none">
			         		<h:outputText value="Estado:" > </h:outputText>
			         	</rich:column>
			         	<rich:column style="border: none">
			         		<h:selectOneMenu value="#{admFormDocController.cboEstado}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			         	</rich:column>
                        <rich:column style="border:none;">
                        	<a4j:commandButton value="Buscar" actionListener= "#{admFormDocController.listarAdmFormDoc}" styleClass="btnEstilos" reRender="pgAdmFormDoc"/>
                        </rich:column>
                    </h:panelGrid>
         			 
         			 <div style="padding-left: 10px">
                           <h:panelGrid id="pgAdmFormDoc" columns="1"  border="0">
                            <rich:scrollableDataTable id="tbAdminMenu" value="#{admFormDocController.beanListFormDoc}" 
                           	var="item" rowKeyVar="rowKey" sortMode="single"  rendered="#{not empty admFormDocController.beanListFormDoc}"
                           	onRowClick="jsSeleccionAmdFormDoc('#{item.intIdEmpresa}', '#{item.intIdPerfil}', '#{item.strIdTransaccion}', '#{item.intVersionDoc}', '#{item.intVersionDemo}', '#{item.strArchDoc}', '#{item.strArchDemo}');" 
                           	width="700px" height="195px"> 
                                    <rich:column width="15">
                                        <div align="center"><h:outputText value="#{rowKey+1}"></h:outputText></div>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Perfil"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strPerfil}"></h:outputText>
                                    </rich:column>
                                    <rich:column width="40px">
                                        <f:facet name="header">
                                            <h:outputText value="Orden"></h:outputText>
                                        </f:facet>
                                        <div align="center"><h:outputText value="#{item.intNivel}"></h:outputText></div>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Menú1"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strNombreM1}"></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Menú2"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strNombreM2}"></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Menú3"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strNombreM3}"></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Menú4"></h:outputText>
                                        </f:facet>
                                        <h:outputText value="#{item.strNombreM4}"></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Proceso"></h:outputText>
                                        </f:facet>
                                        <h:commandLink value="#{item.strArchDoc}" actionListener="#{admFormDocController.downloadArc}" style="color:blue">
		                               		<f:param id="paramDoc" name="paramDoc" value="#{item.strArchDoc}" />
		                                </h:commandLink>
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Demo"></h:outputText>
                                        </f:facet>
                                        <h:commandLink value="#{item.strArchDemo}" actionListener="#{admFormDocController.downloadArc}" style="color:blue">
		                               		<f:param id="paramDemo" name="paramDemo" value="#{item.strArchDemo}" />
		                                </h:commandLink>
                                    </rich:column>
                          </rich:scrollableDataTable>
                          </h:panelGrid>
                          <rich:spacer height="4px"/><rich:spacer/>
                          <rich:spacer height="4px"/><rich:spacer/>
                          <h:panelGrid columns="2">
								<h:outputLink value="#" id="linkAdmFormDoc">
							        <h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
							        <rich:componentControl for="panelUpdateDelete" attachTo="linkAdmFormDoc" operation="show" event="onclick"/>
							    </h:outputLink>
							<h:outputText value="Para Eliminar o Modificar Hacer click en un Registro" style="color:#8ca0bd" ></h:outputText>                                     
						  </h:panelGrid>
                     </div>
				 </rich:panel>
				 
				 <rich:spacer height="10px"/><rich:spacer height="10px"/>
	   	    	 <h:panelGrid columns="3">
	                 <h:commandButton value="Nuevo" actionListener="#{admFormDocController.habilitarGrabarAdmFormDoc}" styleClass="btnEstilos"/>                     
	                 <h:commandButton id="btnGuardarFormDoc" value="Guardar" onclick="return validaForm();" actionListener="#{admFormDocController.grabarFormDoc}" onclick="verificaUpload();" styleClass="btnEstilos"/>												                 
	                 <h:commandButton value="Cancelar" actionListener="#{admFormDocController.cancelarGrabarAdmFormDoc}" styleClass="btnEstilos"/>
	          	 </h:panelGrid>
		         
	          	 <rich:spacer height="4px"/><rich:spacer height="4px"/>
	          	 <rich:panel style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{admFormDocController.admFormDocRendered}">
	          	 	<h:panelGrid columns="3" border="0" >
		               <rich:column style="width: 150px; border: none">
		                  <h:outputText value="Empresa" style="padding-left:10px" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectOneMenu id="idCboEmpresa"
			                  	style="width: 300px;" disabled="#{admFormDocController.formAdmDocEnabled}"
			                  	valueChangeListener="#{controlsFiller.reloadCboPerfiles}" onchange="getJSSelectionEmpresa();submit();"
			                  	value="#{admFormDocController.intCboEmpresa}">
							    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{admFormDocController.msgTxtEmpresa}" styleClass="msgError"></h:outputText>
		               </rich:column>
		            </h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="3" border="0" >
		               <rich:column style="width: 150px; border: none">
		                  <h:outputText value="Estado" style="padding-left:10px" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectOneMenu id="cboEstado" value="#{admFormDocController.beanFormDoc.intIdEstado}" disabled="#{admFormDocController.formAdmDocEnabled}">
		                  		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{admFormDocController.msgTxtEstado}" styleClass="msgError"/>
		               </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid columns="5" border="0" >
		               <rich:column style="width: 225px; border: none">
		                  <h:outputText value="Tipo de Perfil" style="padding-left:10px"/>
		               </rich:column>
		               <rich:column style="border: none">
		                  <h:selectBooleanCheckbox id="chkPerfil" value="#{admFormDocController.chkPerfil}" disabled="#{admFormDocController.chkTodosEnabled}" onclick="enable_cboPerfil(this.checked)"/>
		               </rich:column>
		               <rich:column style="width:100px; border:none;">
		                    <h:outputText value="Todos" ></h:outputText>
		               </rich:column>
		               <rich:column style="border: none">
		                  	<h:selectOneMenu id="idCboPerfilEmpresa" disabled="#{admFormDocController.formAdmDocEnabled}" rendered="#{admFormDocController.cboPerfilRendered}"
		                  		valueChangeListener="#{admFormDocController.reloadCboMenuPerfil}" onchange="getJSSelectionPerfil();submit();" 
		                      	style="width: 300px;" value="#{admFormDocController.intCboPerfilEmpresa}">
					        	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listPerfil}"
									itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
					        	<!--<a4j:support event="onchange" oncomplete="getJSSelectionPerfil();" reRender="pMenu01" ajaxSingle="true" />!-->
					       	</h:selectOneMenu>
		               </rich:column>
		               <rich:column style="width: 200px; border: none">
		                    <h:outputText value="#{admFormDocController.msgTxtPerfil}" styleClass="msgError"/>
		               </rich:column>
		            </h:panelGrid>
		            
		            <h:inputHidden id="hiddenIdEmpresaFormDoc" value="#{controlsFiller.hiddenIdEmpresaFormDoc}"></h:inputHidden>
		            <h:inputHidden id="hiddenIdPerfilFormDoc" value="#{controlsFiller.hiddenIdPerfilFormDoc}"></h:inputHidden>
		            
		            <h:panelGrid columns="3" border="0">
		            	<rich:column style="width:150px; border: none;">
		                    <h:outputText value="Documentación de Proceso"  />
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <rich:fileUpload  id="uploadPdf" addControlLabel="Agregar Archivo"
					             disabled="#{admFormDocController.formAdmDocEnabled}"
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
		                    <h:outputText value="#{admFormDocController.msgTxtDocum}" styleClass="msgError"/>
		               	</rich:column>
		            </h:panelGrid>
		            <h:inputHidden id="hiddenStrFileDocName"></h:inputHidden>
		            
		            <h:panelGrid columns="3" border="0">
		            	<rich:column style="width:150px;border: none;">
		                    <h:outputText value="Demo"  />
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <rich:fileUpload id="uploadDemo" addControlLabel="Agregar Archivo"
					             disabled="#{admFormDocController.formAdmDocEnabled}"
					             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="320"
					             fileUploadListener="#{fileUploadController.listener}"
								 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
								 onupload="document.getElementById('frmPrincipal:hiddenStrFileDemoName').value=event.memo.entry.fileName;"
								 immediateUpload="#{fileUploadController.autoUpload}"
								 acceptedTypes="exe">
								 <f:facet name="label">
								 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
								 </f:facet>
							</rich:fileUpload>
		                </rich:column>
		                <rich:column style="width: 120px; border: none">
		                    <h:outputText value="#{admFormDocController.msgTxtDemo}" styleClass="msgError"/>
		               	</rich:column>
		            </h:panelGrid>
		            <h:inputHidden id="hiddenStrFileDemoName"></h:inputHidden>
		            
		            <h:panelGrid columns="2" border="0" rendered="#{admFormDocController.strAdjuntoDoc}">
		            	<rich:column style="width:150px;border:none;">
			            	<h:outputText value="Proceso:" />
			            </rich:column>
			            <rich:column style="border:none;">
			            	<h:inputText value="#{admFormDocController.beanFormDoc.strArchDoc}" size="80"  readonly="true"/>
			            </rich:column>
		            </h:panelGrid>
		            <h:panelGrid columns="2" border="0" rendered="#{admFormDocController.strAdjuntoDemo}">
		            	<rich:column style="width:150px;border:none;">
			            	<h:outputText value="Demo:" />
			            </rich:column>
			            <rich:column style="border:none;">
			            	<h:inputText value="#{admFormDocController.beanFormDoc.strArchDemo}" size="80"  readonly="true"/>
			            </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid columns="1" border="0" >
		            	<rich:column style="border: none">
		                    <h:outputText value="Estructura del Menú" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid border="0" >
			            <rich:column style="width: 120px; border: none">
		                    <h:outputText value="#{admFormDocController.msgTxtTransaccion}" styleClass="msgError"/>
		           		</rich:column>
	           		</h:panelGrid>
		            
		            <h:panelGrid id="pMenu01" columns="4" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText id="lblMenu1" value="Menú 01" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenuPerfil1" disabled="#{admFormDocController.formAdmDocEnabled}"
		                       	valueChangeListener="#{admFormDocController.reloadCboMenuPerfil}" value="#{admFormDocController.strCboMenu01}"
		                       	style="width: 250px;">
						        <f:selectItems value="#{admFormDocController.cboMenuPerfil1}"></f:selectItems>
						        <a4j:support event="onchange" reRender="cboMenuPerfil2,cboMenuPerfil3,cboMenuPerfil4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <%--<h:commandButton id="btnAgregaMenu1" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}"/>--%>
		                	<a4j:commandButton id="btnAgregaMenu1" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}" reRender="pIdTransacciones" />
		                </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid id="pMenu02" columns="4" border="0">
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText id="lblMenu2" value="Menú 02" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenuPerfil2" disabled="#{admFormDocController.formAdmDocEnabled}"
		                       	valueChangeListener="#{admFormDocController.reloadCboMenuPerfil}" value="#{admFormDocController.strCboMenu02}"
		                       	style="width: 250px;">      
						        <f:selectItems value="#{admFormDocController.cboMenu2}"></f:selectItems>
						        <a4j:support event="onchange" reRender="cboMenuPerfil3,cboMenuPerfil4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <%--<h:commandButton id="btnAgregaMenu2" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}"></h:commandButton>--%>
		                    <a4j:commandButton id="btnAgregaMenu2" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}" reRender="pIdTransacciones" />
		                </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid id="pMenu03" columns="4" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText id="lblMenu3" value="Menú 03" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenuPerfil3" disabled="#{admFormDocController.formAdmDocEnabled}"
		                       	valueChangeListener="#{admFormDocController.reloadCboMenuPerfil}" value="#{admFormDocController.strCboMenu03}"
		                       	style="width: 250px;">
						        <f:selectItems value="#{admFormDocController.cboMenu3}"></f:selectItems>
						        <a4j:support event="onchange" reRender="cboMenuPerfil4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <%--<h:commandButton id="btnAgregaMenu3" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}"></h:commandButton>--%>
		                	<a4j:commandButton id="btnAgregaMenu3" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}" reRender="pIdTransacciones" />
		                </rich:column>
		            </h:panelGrid>
		            
		            <h:panelGrid id="pMenu04" columns="4" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText id="lblMenu4" value="Menú 04" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenuPerfil4" disabled="#{admFormDocController.formAdmDocEnabled}"
		                       	style="width: 250px;" value="#{admFormDocController.strCboMenu04}">
						        <f:selectItems value="#{admFormDocController.cboMenu4}"></f:selectItems>
						  	</h:selectOneMenu>
		                </rich:column>
		                <rich:column style="padding-left:20px;border: none;">
		                    <%--<h:commandButton id="btnAgregaMenu4" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}"></h:commandButton>--%>
		                	<a4j:commandButton id="btnAgregaMenu4" value="Seleccionar" styleClass="btnEstilos" style="width:100px;" disabled="#{admFormDocController.formAdmDocEnabled}" actionListener="#{admFormDocController.selectNode}" reRender="pIdTransacciones" />
		                </rich:column>
		            </h:panelGrid>
	          	 </rich:panel>
	          	 
	          	 <rich:panel style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{admFormDocController.admFormDocRendered}">
	          	 	 <h:panelGrid id="pIdTransacciones" columns="1">
                           <rich:dataTable value="#{admFormDocController.beanListMenues}"
                           	style="background-color:#DEEBF5;border-color:#DEEBF5;cellspacing:0px;border:0px;"
                           	var="item" rowKeyVar="rowKey" width="625px"
                           	rendered="#{not empty admFormDocController.beanListMenues}">
                                    <rich:column style="border:0px;padding-left:0px;padding-top:0px;padding-bottom:0px;">
                                        <h:outputText value="#{item.strNombreM1}"></h:outputText>
                                    </rich:column>
                                    <rich:column breakBefore="true" style="border:0px;padding-left:30px;padding-top:0px;padding-bottom:0px;">
                                        <h:outputText value="#{item.strNombreM2}"></h:outputText>
                                    </rich:column>
                                    <rich:column breakBefore="true" style="border:0px;padding-left:60px;cellpadding:0px;cellspacing:0px;">
                                        <h:outputText value="#{item.strNombreM3}"></h:outputText>
                                    </rich:column>
                                    <rich:column breakBefore="true" style="padding-left:90px;cellpadding:0px;cellspacing:0px;border-right:0px;">
                                        <h:outputText value="#{item.strNombreM4}"></h:outputText>
                                    </rich:column>
                                    <rich:column style="border:0px;cellpadding:0px;cellspacing:0px;padding-top:0px;padding-bottom:0px;border-bottom:1px;">
                                        <h:selectBooleanCheckbox id="chkPerfil" disabled="true" value="#{item.chkPerfil}"/>
                                    </rich:column>
                           </rich:dataTable>
                     </h:panelGrid>
            	 </rich:panel>
	          	 
			</rich:panel>
    	</rich:tab>
    	
    	<!-- Administración de Documentación -->
     	<rich:tab label="Administración Documentación"  >
    		<rich:panel style="border:1px solid #17356f ;width: 780px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			 <h:panelGrid columns="2" style="width: 680px" border = "0">
                        <rich:column style="width:120px;border:none;">
                        	<h:outputText value="Empresa"  style="padding-left: 15px;"></h:outputText>
                        </rich:column>
                        <rich:column style="border:none;">
                        	<h:selectOneMenu value="#{admFormDocController.cboEmpresaAdmDoc}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							</h:selectOneMenu>
                        </rich:column>
                     </h:panelGrid>
                     <h:panelGrid columns="4" style="width: 680px" border = "0">
                        <rich:column style="width:120px;border:none;">
                        	<h:outputText value="Tipo de Perfil"  style="padding-left: 15px;"/>
                        </rich:column>
                        <rich:column style="border:none;">
                        	<h:selectOneMenu value="#{admFormDocController.cboPerfilAdmDoc}">
								<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.listPerfil}"
									itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="border:none;">
                        	<h:outputText value="Estado" />
                        </rich:column>
                        <rich:column style="border:none;">
	                        <h:selectOneMenu value="#{admFormDocController.cboEstadoAdmDoc}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
                     </h:panelGrid>
                     
                     <h:panelGrid columns="2">
                        <rich:column style="border:none;">
                        	<h:selectBooleanCheckbox style="padding-left: 15px;" onclick="enable_EstMenu(this.checked)"/>
                        </rich:column>
                        <rich:column style="border:none;">
                        	<h:outputText value="Estructura del Menú" />
                        </rich:column>
                     </h:panelGrid>
                     
                     <h:panelGrid columns="3" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText value="Menú 01" style="padding-left:10px" />
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenu1"
		                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{admFormDocController.strCboMenuAdmDoc01}"
		                       	style="width: 250px;">
						        <f:selectItems value="#{controlsFiller.cboMenu1}"/>
						        <a4j:support event="onchange" reRender="cboMenu2,cboMenu3,cboMenu4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		             </h:panelGrid>
		             
		             <h:panelGrid columns="3" border="0">
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText value="Menú 02" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenu2"
		                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{admFormDocController.strCboMenuAdmDoc02}"
		                       	style="width: 250px;">      
						        <f:selectItems value="#{controlsFiller.cboMenu2}"></f:selectItems>
						        <a4j:support event="onchange" reRender="cboMenu3,cboMenu4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		             </h:panelGrid>
		             
		             <h:panelGrid columns="3" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText value="Menú 03" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenu3"
		                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{admFormDocController.strCboMenuAdmDoc03}"
		                       	style="width: 250px;">
						        <f:selectItems value="#{controlsFiller.cboMenu3}"></f:selectItems>
						        <a4j:support event="onchange" reRender="pMenuAdmDoc4" ajaxSingle="true" />
						  	</h:selectOneMenu>
		                </rich:column>
		             </h:panelGrid>
		            
		             <h:panelGrid id="pMenuAdmDoc4" columns="3" border="0" >
		            	<rich:column style="width: 150px; border: none">
		                    <h:outputText value="Menú 04" style="padding-left:10px" ></h:outputText>
		                </rich:column>
		                <rich:column style="border:none;">
		                    <h:outputText value=":" ></h:outputText>
		                </rich:column>
		                <rich:column style="border: none">
		                    <h:selectOneMenu id="cboMenu4"
		                       	style="width: 250px;" value="#{admFormDocController.strCboMenuAdmDoc04}">
						        <f:selectItems value="#{controlsFiller.cboMenu4}"></f:selectItems>
						  	</h:selectOneMenu>
		                </rich:column>
		             </h:panelGrid>
		             
		             <h:panelGrid columns="5">
		             	<rich:column style="border:none;padding-left:15px;">
		             		<h:selectBooleanCheckbox onclick="enable_txtProceso(this.checked)"/>
		             	</rich:column>
		             	<rich:column style="border:none;width:80px;">
		             		<h:outputText value="Proceso" />
		             	</rich:column>
		             	<rich:column style="border:none;">
		             		<h:inputText id="txtProceso" size="50" value="#{admFormDocController.strArcDoc}"/>
		             	</rich:column>
		             	<rich:column style="border:none;width:140px;">
		             		<h:outputText value="Número de Versiones" />
		             	</rich:column>
		             	<rich:column style="border:none;">
		             		<h:inputText size="5" value="#{admFormDocController.intVerDoc}" onkeypress="return soloNumeros(event);"/>
		             	</rich:column>
		             </h:panelGrid>
		             
		             <h:panelGrid columns="6">
		             	<rich:column style="border:none;padding-left:15px;">
		             		<h:selectBooleanCheckbox onclick="enable_txtDemo(this.checked)"/>
		             	</rich:column>
		             	<rich:column style="border:none;width:80px;">
		             		<h:outputText value="Demos" />
		             	</rich:column>
		             	<rich:column style="border:none;">
		             		<h:inputText id="txtDemo" size="50" value="#{admFormDocController.strArcDemo}"/>
		             	</rich:column>
		             	<rich:column style="border:none;width:140px;">
		             		<h:outputText value="Número de Versiones" />
		             	</rich:column>
		             	<rich:column style="border:none;">
		             		<h:inputText size="5" value="#{admFormDocController.intVerDemo}" onkeypress="return soloNumeros(event);"/>
		             	</rich:column>
		             	<rich:column style="border:none;padding-left:30px;">
		                	<h:commandButton value="Buscar" actionListener= "#{admFormDocController.listarAdmDoc}" styleClass="btnEstilos"/>
		                </rich:column>
		             </h:panelGrid>
		             
		             <rich:dataTable width="740px;" value="#{admFormDocController.beanListAdmDoc}" 
		             	 rows="10" sortMode="true" var="item" rowKeyVar="key">
				        <f:facet name="header">
				            <rich:columnGroup>
				                <rich:column width="15px" rowspan="2">
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Perfil"/>
				                </rich:column>
				                <rich:column width="40px;" rowspan="2">
				                	<h:outputText value="Orden"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Menú 1"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Menú 2"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Menú 3"/>
				                </rich:column>
				                <rich:column rowspan="2">
				                	<h:outputText value="Menú 4"/>
				                </rich:column>
				                <rich:column colspan="3">
				                    <h:outputText value="Proceso" />
				                </rich:column>
				                <rich:column colspan="3">
				                    <h:outputText value="Demo" />
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
				                <rich:column>
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
				        <rich:column colspan="12">
				            <h:outputText value="#{item.strPerfil}" />
				        </rich:column>
				        <rich:column colspan="2" breakBefore="true">
				        	<h:outputText value=""/>
				        </rich:column>
				        <rich:column width="40px;">
				            <div align="center"><h:outputText value="#{item.intNivel}"/></div>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.strNombreM1}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.strNombreM2}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.strNombreM3}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.strNombreM4}"/>
				        </rich:column>
				        <rich:column>
					        <h:commandLink value="#{item.strArchDoc}" actionListener="#{admFormDocController.downloadArc}" style="color:blue">
	                       		<f:param id="paramDoc" name="paramDoc" value="#{item.strArchDoc}" />
	                        </h:commandLink>
                        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.daFecRegDoc}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="---"/>
				        </rich:column>
				        <rich:column>
					        <h:commandLink value="#{item.strArchDemo}" actionListener="#{admFormDocController.downloadArc}" style="color:blue">
	                       		<f:param id="paramDemo" name="paramDemo" value="#{item.strArchDoc}" />
	                        </h:commandLink>
                        </rich:column>
				        <rich:column>
				        	<h:outputText value="#{item.daFecRegDemo}"/>
				        </rich:column>
				        <rich:column>
				        	<h:outputText value="---"/>
				        </rich:column>
				     </rich:dataTable>
         		</rich:panel>
         	</rich:panel>
   		</rich:tab>
      </rich:tabPanel>
   </h:form>