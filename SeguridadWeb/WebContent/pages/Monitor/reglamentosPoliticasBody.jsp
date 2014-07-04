<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fehca     : 201108191122             -->
		
<script type="text/javascript">
	function jsSelectRegPol(idEmpresa,idPerfil,idTransaccion,idEstado){
		//alert(idEmpresa+" "+idPerfil+" "+idTransaccion);
        document.getElementById("frmUpDelRegPol:hiddenIdEmpresa").value=idEmpresa;
        document.getElementById("frmUpDelRegPol:hiddenIdPerfil").value=idPerfil;
        document.getElementById("frmUpDelRegPol:hiddenIdTransaccion").value=idTransaccion;
        document.getElementById("frmUpDelRegPol:hiddenIdEstado").value=idEstado;
    }	
    
    function verificaUpload(){
	  	//Comprobar archivos tipo Pdf.
	  	if(document.getElementById('formRegPol:uploadReglamento').component.idCounter == 0){
	  		document.getElementById('formRegPol:hiddenStrFileRegName').value='';
	  	}else{
	  		if(document.getElementById('formRegPol:uploadReglamento').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
	      		document.getElementById('formRegPol:hiddenStrFileRegName').value;
	      	} else{
	      		document.getElementById('formRegPol:hiddenStrFileRegName').value='';
	      	}
	  	}
      	//Comprobar archivos tipo Demo.
      	if(document.getElementById('formRegPol:uploadPolitica').component.idCounter == 0){
      		document.getElementById('formRegPol:hiddenStrFilePolName').value='';
      	}else{
      		if(document.getElementById('formRegPol:uploadPolitica').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
	      		document.getElementById('formRegPol:hiddenStrFilePolName').value;
	      	} else{
	      		document.getElementById('formRegPol:hiddenStrFilePolName').value='';
	      	}
      	}
      	
      	if(document.getElementById('formRegPol:uploadReglamento').component.idCounter == 0 &&
      	   document.getElementById('formRegPol:uploadPolitica').component.idCounter == 0){
      		alert('Debe elegir por lo menos un Tipo de Documento.');
      		return false;
      	}
      	
	  }
</script>

<rich:modalPanel id="mpUpDelRegPc" width="380" height="120"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Registro de Pc" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkRegPc"/>
               <rich:componentControl for="mpUpDelRegPc" attachTo="hidelinkRegPc" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmUpDelRegPol">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar un Registro de Pc?"    style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{reglamentoPoliticaController.modificarRegPol}" styleClass="btnEstilos"></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{reglamentoPoliticaController.eliminarRegPol}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdEmpresa"></h:inputHidden>
					<h:inputHidden id="hiddenIdPerfil"></h:inputHidden>
					<h:inputHidden id="hiddenIdTransaccion"></h:inputHidden>
					<h:inputHidden id="hiddenIdEstado"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>

 <h:form id="formRegPol">
 	<rich:tabPanel id="tbAccesosEspeciales" switchType="ajax">
 	<rich:tab id="tbAccesosFH" label="Relación Formulario - Documento">
 	
        <rich:panel style="width: 760px ;background-color: #DEEBF5">
            <h:panelGrid columns="5">
            	<rich:column style="width: 70px; border: none">
            		<h:outputText  value="Empresa :" />
            	</rich:column>
            	<rich:column style="width: 230px; border:none">
            		<h:selectOneMenu id="cboEmpresasRegPol" 
                       	required="true" style="width: 220px;"
                       	value="#{reglamentoPoliticaController.intCboEmpresasBusq}">
				        <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
				  	</h:selectOneMenu>
            	</rich:column>
                <rich:column style="width: 70px; border:none">
                  	<h:outputText value="Estado :"  />
                </rich:column>
                <rich:column style="border:none">
	                <h:selectOneMenu required="true" style="width: 100px;"
	                	value="#{reglamentoPoliticaController.intCboEstadoBusq}">						
		                <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		            </h:selectOneMenu>    
                </rich:column>
                <rich:column style="border:none">
                	<a4j:commandButton value="Buscar" actionListener= "#{reglamentoPoliticaController.listarReglamentosPoliticas}" styleClass="btnEstilos" reRender="pgReglamPolitica"/>
                </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid id="pgReglamPolitica" columns="2">
            	<rich:column width="40" style="border:none">
            	</rich:column>
            	<rich:column style="border: none">
            		<rich:scrollableDataTable id="tbRegistroPc" value="#{reglamentoPoliticaController.beanListRegPolMenu}"
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty reglamentoPoliticaController.beanListRegPolMenu}"
	                            	onRowClick="jsSelectRegPol('#{item.intIdEmpresa}','#{item.intIdPerfil}','#{item.strIdTransaccion}','#{item.intIdEstado}');" width="645px" height="185px"> 
		            	<rich:column width="29">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			            <rich:column width="150">
			            	<f:facet name="header">
			                	<h:outputText value="Perfil" ></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strPerfil}"></h:outputText>
			            </rich:column>
		            	<rich:column width="150">
			            	<f:facet name="header">
			                	<h:outputText value="Menu 1" ></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strMenu1}"></h:outputText>
			            </rich:column>
			            <rich:column width="120">
			            	<f:facet name="header">
			                	<h:outputText value="Menu 2" ></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strMenu2}"></h:outputText>
			            </rich:column>
			            <rich:column width="120">
			            	<f:facet name="header">
			                	<h:outputText value="Menu 3" ></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strMenu3}"></h:outputText>
			            </rich:column>
			            <rich:column width="180">
			            	<f:facet name="header">
			                	<h:outputText value="Menu 4" ></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strMenu4}"></h:outputText>
			            </rich:column>
			            <rich:column width="120">
			            	<f:facet name="header">
			                	<h:outputText value="Reglamento" ></h:outputText>
			                </f:facet>
			                <h:commandLink id="dwReglamento" value="#{item.strReglamento}"
								actionListener="#{reglamentoPoliticaController.downloadReglamento}" immediate="true">
								<f:param name="strReglamento" id="strReglamento" value="#{item.strReglamento}" />
								<rich:toolTip for="dwReglamento" value="Descargar reglamento"/>
							</h:commandLink>
			            </rich:column>
			            <rich:column width="180">
			            	<f:facet name="header">
			                	<h:outputText value="Politica" ></h:outputText>
			                </f:facet>
			                <h:commandLink id="dwPolitica" value="#{item.strPolitica}"
								actionListener="#{reglamentoPoliticaController.downloadPolitica}" immediate="true">
								<f:param name="strPolitica" id="strPolitica" value="#{item.strPolitica}" />
								<rich:toolTip for="dwPolitica" value="Descargar politica"/>
							</h:commandLink>
			            </rich:column>
		            </rich:scrollableDataTable>
            	</rich:column>
            	
            	<rich:column width="40" style="border:none">
            	</rich:column>
            	<rich:column style="border:none">
            		<h:outputLink value="#" id="linkRegPc">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelRegPc" attachTo="linkRegPc" operation="show" event="onclick"/>
					</h:outputLink>
              		<h:outputText value="Para Eliminar o Actualizar Hacer click en Registro" style="color:#8ca0bd"/>
            	</rich:column>  
            </h:panelGrid>
           <h:panelGrid columns="3">
	           	<h:commandButton value="Nuevo" actionListener="#{reglamentoPoliticaController.habilitarGrabarRegPol}" styleClass="btnEstilos"/>
               	<h:commandButton value="Guardar" actionListener="#{reglamentoPoliticaController.grabarRegPol}" onclick="verificaUpload();" styleClass="btnEstilos"/>  
              	<h:commandButton value="Cancelar" actionListener="#{reglamentoPoliticaController.cancelarGrabarRegPol}" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel rendered="#{reglamentoPoliticaController.blnRegPolRendered}" style="width: 740px ;background-color: #DEEBF5;border:2px solid #17356f;">
                
                <h:panelGrid columns="4" border="0">
                	<rich:column width="110" style="border: none">
                		<h:outputText value="Empresa :" ></h:outputText>
                	</rich:column>
                	<rich:column width="240" style="border: none">
                		<h:selectOneMenu id="cboEmpRegPc" 
	                       	valueChangeListener="#{reglamentoPoliticaController.reloadCboPerfiles}"
	                       	style="width: 220px;"
	                       	value="#{reglamentoPoliticaController.beanRegPol.intIdEmpresa}">
					        <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
					        <a4j:support event="onchange" reRender="cboRelPerfil" ajaxSingle="true"/>
					  	</h:selectOneMenu>
                	</rich:column>
                	<rich:column width="65" style="border: none">
                		<h:outputText value="Estado :"  />
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu required="true" value="#{reglamentoPoliticaController.beanRegPol.intIdEstado}" style="width:110px;">
                			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			            </h:selectOneMenu>
                	</rich:column>
			    </h:panelGrid>
			    <rich:spacer height="8px"/>
			    <h:panelGrid columns="4">
			    	<rich:column width="110" style="border: none">
			    		<h:outputText value="Tipo Perfil :"  />
                	</rich:column>
                	<rich:column width="20" style="border: none">
                		<h:selectBooleanCheckbox value="#{reglamentoPoliticaController.beanRegPol.blnPerfilTodos}"/>
                	</rich:column>
                	<rich:column width="100" style="border: none">
                		<h:outputText value="Todos"/>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboRelPerfil" required="true" value="#{reglamentoPoliticaController.beanRegPol.intIdPerfil}"
                			valueChangeListener="#{reglamentoPoliticaController.reloadCboMenuPerfil}" style="width: 110px;">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{reglamentoPoliticaController.listPerfil}"
								itemValue="#{sel.intIdPerfil}" itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="cboRelMenu1" ajaxSingle="true"/>
			            </h:selectOneMenu>
                	</rich:column>
			     </h:panelGrid>
			     <rich:spacer height="8px"/>
			     <h:panelGrid columns="2">
			       	<h:panelGrid>
			       		<rich:column width="110" style="border: none" rowspan="4">
	                		<h:selectOneRadio id="menuRadio" value="#{reglamentoPoliticaController.strRadioMenu}"
	                		 	layout="pageDirection" style="height: 100px" >
	                			<f:selectItems value="#{reglamentoPoliticaController.itemListRadioMenu}"/>
	                		</h:selectOneRadio>
	                	</rich:column>
			       	</h:panelGrid>
			     	
                	<h:panelGrid>
                		<rich:column style="border: none; height: 25px">
	                  		<h:selectOneMenu id="cboRelMenu1" value="#{reglamentoPoliticaController.strCboRelMenu1}"
						       	valueChangeListener="#{controllerFiller.reloadCboMenu2}" style="width: 120px;">      
							    <f:selectItems value="#{reglamentoPoliticaController.cboRelMenu1}"></f:selectItems>
							    <a4j:support event="onchange" reRender="cboRelMenu2,cboRelMenu3,cboRelMenu4" ajaxSingle="true" />
							</h:selectOneMenu>
	                  	</rich:column>
	                  	<rich:column style="border: none; height: 25px">
	                  		<h:selectOneMenu id="cboRelMenu2" value="#{reglamentoPoliticaController.strCboRelMenu2}"
						     	valueChangeListener="#{controllerFiller.reloadCboMenu3}" style="width: 120px;">      
								<f:selectItems value="#{controllerFiller.cboMenu2}"></f:selectItems>
								<a4j:support event="onchange" reRender="cboRelMenu3,cboRelMenu4" ajaxSingle="true" />
							</h:selectOneMenu>
	                  	</rich:column>
	                  	<rich:column style="border: none; height: 25px">
	                  		<h:selectOneMenu id="cboRelMenu3" value="#{reglamentoPoliticaController.strCboRelMenu3}"
						       	valueChangeListener="#{controllerFiller.reloadCboMenu4}" style="width: 120px;">      
							    <f:selectItems value="#{controllerFiller.cboMenu3}"></f:selectItems>
							    <a4j:support event="onchange" reRender="cboRelMenu4" ajaxSingle="true" />
							</h:selectOneMenu>
                  		</rich:column>
                  		<rich:column style="border: none; height: 25px">
	                  		<h:selectOneMenu id="cboRelMenu4" value="#{reglamentoPoliticaController.strCboRelMenu4}" style="width: 120px;">      
							    <f:selectItems value="#{controllerFiller.cboMenu4}"></f:selectItems>
							</h:selectOneMenu>
	                  	</rich:column>
                	</h:panelGrid>
			     </h:panelGrid>
			     <rich:spacer height="8px"/>
			     <h:panelGrid columns="4">
			        <rich:column width="110" style="border: none">
			        	<h:outputText value="Documentación :"  />
                	</rich:column>
                	<rich:column style="border: none" />
                	<rich:column style="border: none" />
                	<rich:column style="border: none" />
			     </h:panelGrid>
			     <rich:spacer height="8px"/>
			     <h:panelGrid columns="4">
			        <rich:column width="110" style="border: none">
			        	<h:outputText value="Reglamentos :"  />
                	</rich:column>
                	<rich:column style="border: none">
                		<rich:fileUpload id="uploadReglamento" addControlLabel="Adjuntar"
					        clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					        uploadControlLabel="Subir Archivo" listHeight="45"
					        fileUploadListener="#{fileUploadController.listener}"
							maxFilesQuantity="1" doneLabel="Archivo cargado correctamente" 
							onupload="document.getElementById('formRegPol:hiddenStrFileRegName').value=event.memo.entry.fileName;"
							immediateUpload="#{fileUploadController.autoUpload}"
							acceptedTypes="pdf,jpg,doc,xls,docx,xlsx">
							<f:facet name="label">
								<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
						</rich:fileUpload>
                	</rich:column>    
                	<rich:column style="border: none" />
                	<rich:column style="border: none" />
			     </h:panelGrid>
			     <h:inputHidden id="hiddenStrFileRegName"></h:inputHidden>
			     	<rich:spacer height="8px"/>
			     <h:panelGrid columns="4">       
			        <rich:column width="110" style="border: none">
			        	<h:outputText value="Políticas :"  />
                	</rich:column>
                	<rich:column style="border: none">
                		<rich:fileUpload id="uploadPolitica" addControlLabel="Adjuntar" 
					        clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
					        uploadControlLabel="Subir Archivo" listHeight="45"
					        fileUploadListener="#{fileUploadController.listener}"
							maxFilesQuantity="1" doneLabel="Archivo cargado correctamente" 
							onupload="document.getElementById('formRegPol:hiddenStrFilePolName').value=event.memo.entry.fileName;" 
							immediateUpload="#{fileUploadController.autoUpload}" 
							acceptedTypes="pdf,jpg,doc,xls,docx,xlsx">
							<f:facet name="label">
								<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
						</rich:fileUpload>
                	</rich:column>
                	<rich:column style="border: none" />
                	<rich:column style="border: none" />
                  </h:panelGrid>
                  <h:inputHidden id="hiddenStrFilePolName"></h:inputHidden>
            </rich:panel>           
 		 </rich:panel>
    </rich:tab>
    
    <rich:tab label="Administración - Documentación"  >
	 	<rich:panel style="width: 760px ;background-color: #DEEBF5">
            <h:panelGrid columns="5">
            	<rich:column style="width: 70px; border: none">
            		<h:outputText  value="Empresa :" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboEmpresa"
						style="width: 300px;"
						value="#{reglamentoPoliticaController.intCboEmpresa}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
					</h:selectOneMenu>
                </rich:column>
                
                <rich:column style="width: 70px; border: none">
            		<h:outputText  value="Estado :"  style="padding-left:10px;"/>
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu value="#{reglamentoPoliticaController.intCboEstado}">
	                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		            </h:selectOneMenu>
                </rich:column>
                
                <rich:column style="border:none;padding-left:20px;">
                	<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{reglamentoPoliticaController.listarReglamAdmDoc}" reRender="pgAdmFormDoc"/>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgAdmFormDoc">
	            <rich:dataTable value="#{reglamentoPoliticaController.beanListRegPolMenuAdmDoc}"
				 rows="10" sortMode="true" rowKeyVar="key" width="675" var="item"
				 onRowMouseOver="this.style.backgroundColor='#dfecab'" 
				 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.tableBackgroundColor}'">
				<f:facet name="header">
					<rich:columnGroup>
						<rich:column rowspan="2">
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
							<h:outputText value="Reglamento" />
						</rich:column>
						<rich:column colspan="3">
							<h:outputText value="Política" />
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
				
					<rich:column width="20px" style="text-align:center;" >
						<h:outputText value="#{key+1}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strMenu1}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strMenu2}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strMenu3}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strMenu4}"/>
					</rich:column>
					<rich:column>
						<h:commandLink id="dwReglamentoAdm" value="#{item.strReglamento}" style="width:120px;color:blue;" 
							actionListener="#{reglamentoPoliticaController.downloadReglamento}" immediate="true">
							<f:param name="strReglamento" id="strReglamento" value="#{item.strReglamento}"/>
							<rich:toolTip for="dwReglamentoAdm" value="Descargar Reglamento"/>
						</h:commandLink>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strDaFecRegistro}" />
					</rich:column>
					<rich:column>
						<h:outputText value="---"/>
					</rich:column>
					<rich:column>
						<h:commandLink id="dwPoliticaAdm" value="#{item.strPolitica}" style="width:120px;color:blue;"
							actionListener="#{reglamentoPoliticaController.downloadPolitica}" immediate="true">
							<f:param name="strPolitica" id="strPolitica" value="#{item.strPolitica}" />
							<rich:toolTip for="dwPoliticaAdm" value="Descargar Política"/>
						</h:commandLink>
					</rich:column>
					<rich:column>
						<h:outputText value="#{item.strDaFecRegistro}"/>
					</rich:column>
					<rich:column>
						<h:outputText value="---"></h:outputText>
					</rich:column>
					<a4j:support event="onRowClick" actionListener="#{reglamentoPoliticaController.editRegPol}" reRender="pEmpresa,pTipoPerfil,pMenu01,pMenu02,pMenu03,pMenu04,pReglamento,pPolitica">
			        	<f:param id="intIdEmpresa" 		name="intIdEmpresa" 	value="#{item.intIdEmpresa}"></f:param>
			        	<f:param id="intIdPerfil"  		name="intIdPerfil" 		value="#{item.intIdPerfil}"></f:param>
			        	<f:param id="strIdTransaccion"  name="strIdTransaccion" value="#{item.strIdTransaccion}"></f:param>
			        </a4j:support>
			</rich:dataTable>
		    </h:panelGrid>
		    
		    <rich:spacer height="10px"></rich:spacer>
            
            <rich:panel style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;">
            	<h:panelGrid id="pEmpresa" columns="4">
	            	<rich:column style="width: 100px; border: none">
	            		<h:outputText  value="Empresa :" />
	            	</rich:column>
	                <rich:column style="border:none">
	                	<h:selectOneMenu style="width: 300px;"
							value="#{reglamentoPoliticaController.beanRegPol.intIdEmpresa}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						</h:selectOneMenu>
	                </rich:column>
	                
	                <rich:column style="width: 100px; border: none">
	            		<h:outputText value="Estado :"  style="padding-left:10px;"/>
	            	</rich:column>
	                <rich:column style="border:none">
	                	<h:selectOneMenu style="width: 100px;"
		                	value="#{reglamentoPoliticaController.beanRegPol.intIdEstado}">
		                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			                <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			            </h:selectOneMenu>
	                </rich:column>
	            </h:panelGrid>
	            	
	            <h:panelGrid id="pTipoPerfil" columns="2">
	            	<rich:column style="width: 100px; border: none">
	            		<h:outputText  value="Tipo de Perfil:" />
	            	</rich:column>
	                <rich:column style="border:none">
	                	<h:inputText value="#{reglamentoPoliticaController.beanRegPol.strPerfil}" size="60"></h:inputText>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pEstMenu" columns="1" border="0" >
	            	<rich:column style="border: none">
	                    <h:outputText value="Estructura del Menú" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pMenu01" columns="3" border="0" >
	            	<rich:column style="width: 150px; border: none">
	                    <h:outputText id="lblMenu1" value="Menú 01" style="padding-left:10px"/>
	                </rich:column>
	                <rich:column style="border:none;">
	                    <h:outputText value=":"/>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:selectOneMenu id="cboMenu1"
	                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{reglamentoPoliticaController.beanRegPol.strIdMenu1}"
	                       	style="width: 250px;">
					        <f:selectItems value="#{controlsFiller.cboMenu1}"/>
					        <a4j:support event="onchange" reRender="pMenu02,pMenu03,pMenu04" ajaxSingle="true"/>
					  	</h:selectOneMenu>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pMenu02" columns="3" border="0">
	            	<rich:column style="width: 150px; border: none">
	                    <h:outputText id="lblMenu2" value="Menú 02" style="padding-left:10px"/>
	                </rich:column>
	                <rich:column style="border:none;">
	                    <h:outputText value=":"/>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:selectOneMenu id="cboMenu2"
	                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{reglamentoPoliticaController.beanRegPol.strIdMenu2}"
	                       	style="width: 250px;">      
					        <f:selectItems value="#{controlsFiller.cboMenu2}"/>
					        <a4j:support event="onchange" reRender="pMenu03,pMenu04" ajaxSingle="true"/>
					  	</h:selectOneMenu>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pMenu03" columns="3" border="0" >
	            	<rich:column style="width: 150px; border: none">
	                    <h:outputText id="lblMenu3" value="Menú 03" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border:none;">
	                    <h:outputText value=":" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:selectOneMenu id="cboMenu3"
	                       	valueChangeListener="#{controlsFiller.reloadCboMenu}" value="#{reglamentoPoliticaController.beanRegPol.strIdMenu3}"
	                       	style="width: 250px;">
					        <f:selectItems value="#{controlsFiller.cboMenu3}"></f:selectItems>
					        <a4j:support event="onchange" reRender="pMenu04" ajaxSingle="true" />
					  	</h:selectOneMenu>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pMenu04" columns="3" border="0" >
	            	<rich:column style="width: 150px; border: none">
	                    <h:outputText id="lblMenu4" value="Menú 04" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border:none;">
	                    <h:outputText value=":" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:selectOneMenu id="cboMenu4"
	                       	style="width: 250px;" value="#{reglamentoPoliticaController.beanRegPol.strIdMenu4}">
					        <f:selectItems value="#{controlsFiller.cboMenu4}"></f:selectItems>
					  	</h:selectOneMenu>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid columns="1" border="0" >
	            	<rich:column style="border: none">
	                    <h:outputText value="Documentación" style="padding-left:10px"/>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pReglamento" columns="4" border="0" >
	            	<rich:column style="width:100px;border: none">
	                    <h:outputText value="Reglamento" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:inputText size="50" value="#{reglamentoPoliticaController.beanRegPol.strReglamento}"></h:inputText>
	                </rich:column>
	                <rich:column style="width:150px;border: none">
	                    <h:outputText value="Número de Versiones" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:inputText size="3" value="#{reglamentoPoliticaController.beanRegPol.intDocVersion}"></h:inputText>
	                </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pPolitica" columns="4" border="0" >
	            	<rich:column style="width:100px;border: none">
	                    <h:outputText value="Política" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:inputText size="50" value="#{reglamentoPoliticaController.beanRegPol.strPolitica}"></h:inputText>
	                </rich:column>
	                <rich:column style="width:150px;border: none">
	                    <h:outputText value="Número de Versiones" style="padding-left:10px" ></h:outputText>
	                </rich:column>
	                <rich:column style="border: none">
	                    <h:inputText size="3" value="#{reglamentoPoliticaController.beanRegPol.intDocVersion}"></h:inputText>
	                </rich:column>
	            </h:panelGrid>
            </rich:panel>
 		 </rich:panel>
    </rich:tab>
  </rich:tabPanel>
  
</h:form>
	