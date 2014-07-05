<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fecha     : 201108191122             -->
		
<script type="text/javascript">
	function jsSelectRegPc(itemId){
		//alert("id Registro Pc: "+itemId);
        document.getElementById("formRegPc:hiddenIdRegPc").value=itemId;
    }
    function jsSelectCboExternos(){
    	var idTipoAccesoExt = document.formRegistroPc["formRegistroPc:cboTerceros"].selectedIndex;
    	//alert(idTipoAccesoExt);
	  	var strTipoAccesoExt = document.formRegistroPc["formRegistroPc:cboTerceros"][idTipoAccesoExt].text;
	  	//alert(strTipoAccesoExt);
	  	
	  	document.formRegistroPc["formRegistroPc:hiddenTerceros"].value=strTipoAccesoExt;
	  	//alert(document.frmPrincipal["formRegistroPc:hiddenTerceros"].value);
	  }
</script>

<rich:modalPanel id="mpUpDelRegPc" width="380" height="120"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Registro de Pc" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkRegPc"/>
               <rich:componentControl for="mpUpDelRegPc" attachTo="hidelinkRegPc" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formRegPc">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar un Registro de Pc?" styleClass="tamanioLetra"   style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{registroPcController.modificarRegistroPc}" styleClass="btnEstilos"></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{registroPcController.eliminarRegistroPc}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdRegPc"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>

 <h:form id="formRegistroPc">
 	<rich:tabPanel id="tpRegistroPc" switchType="ajax">
 	<rich:tab  id="rtRegPc" label="Registro de Pc"  styleClass="tamanioLetra">
 	
        <rich:panel style="width: 760px ;background-color: #DEEBF5">
            
            <h:panelGrid columns="5">
            	<rich:column style="width: 70px; border: none">
            		<h:outputText  value="Empresa :" styleClass="tamanioLetra"/>
            	</rich:column>
            	<rich:column style="width: 230px; border:none">
            		<h:selectOneMenu id="cboEmpresasRegPc" 
                       	valueChangeListener="#{controllerFiller.reloadCboSucursales}"
                       	style="width: 220px;"
                       	value="#{registroPcController.intCboEmpresasBusq}">
                       	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				        <tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
				        <a4j:support event="onchange" reRender="cboSucursalesRegPc,cboAreasRegPc" ajaxSingle="true" />
				  	</h:selectOneMenu>
            	</rich:column>
                <rich:column style="width: 70px; border:none">
                  	<h:outputText value="Estado :" styleClass="tamanioLetra" />
                </rich:column>
                <rich:column style="border:none">
	                <h:selectOneMenu style="width: 100px;"
	                	value="#{registroPcController.intCboEstadoBusq}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		            </h:selectOneMenu>    
                </rich:column>
            </h:panelGrid>
            <h:panelGrid columns="6">
            	<rich:column style="width: 70px; border: none">
            		<h:outputText  value="Sucursal :" styleClass="tamanioLetra"/>
            	</rich:column>
            	<rich:column style="width: 230px; border:none">
			      <h:selectOneMenu id="cboSucursalesRegPc" 
                       	valueChangeListener="#{controllerFiller.reloadCboAreas}"
                       	style="width: 220px;"
                       	value="#{registroPcController.intCboSucursalesBusq}">
				        <f:selectItems value="#{controllerFiller.cboSucursales}"></f:selectItems>
				        <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				        <tumih:selectItems var="sel" value="#{controllerFiller.listSucursal}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
				        <a4j:support event="onchange" reRender="cboAreasRegPc" ajaxSingle="true" />
				  	</h:selectOneMenu>
            	</rich:column>
                <rich:column style="width: 70px; border:none">
                  	<h:outputText value="Area :" styleClass="tamanioLetra" />
                </rich:column>
                <rich:column style="width: 210px; border:none">
	                <h:selectOneMenu id="cboAreasRegPc"
                      	style="width: 220px;" 
                      	value="#{registroPcController.intCboAreasBusq}">  
			        	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				        <tumih:selectItems var="sel" value="#{controllerFiller.listAreas}"
							itemValue="#{sel.intIdArea}" itemLabel="#{sel.strDescripcion}"/> 
			      </h:selectOneMenu> 
                </rich:column>
                <rich:column style="border:none">
                	<a4j:commandButton value="Buscar" actionListener="#{registroPcController.listarRegistroPc}" reRender="pgBusqRegPc,tbRegistroPc" styleClass="btnEstilos"/>
                </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid id="pgBusqRegPc" columns="2">
            	<rich:column width="40" style="border:none">
            	</rich:column>
            	<rich:column style="border: none">
            		<rich:extendedDataTable id="tbRegistroPc" value="#{registroPcController.beanListRegistroPc}" rows="5" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty registroPcController.beanListRegistroPc}"
	                            	onRowClick="jsSelectRegPc('#{item.intIdPc}');" width="645px" height="166px"> 
		            	<rich:column width="29">
			            	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
		            	<rich:column width="160" sortBy="#{item.strSucursal}">
			            	<f:facet name="header">
			                	<h:outputText value="Nombre de Sucursal" styleClass="tamanioLetra"></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strSucursal}"></h:outputText>
			            </rich:column>
			            <rich:column width="120" sortBy="#{item.strArea}">
			            	<f:facet name="header">
			                	<h:outputText value="Area" styleClass="tamanioLetra"></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strArea}"></h:outputText>
			            </rich:column>
			            <rich:column width="140" sortBy="#{item.strCodPc}">
			            	<f:facet name="header">
			                	<h:outputText value="Identificador Pc's" styleClass="tamanioLetra"></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strCodPc}"></h:outputText>
			            </rich:column>
			            <rich:column width="200">
			            	<f:facet name="header">
			                	<h:outputText value="Fecha y Hora de Activación" styleClass="tamanioLetra"></h:outputText>
			                </f:facet>
			                <h:outputText value="#{item.strFechaAcceso}"></h:outputText>
			            </rich:column>
			            <f:facet name="footer">   
			            	<rich:datascroller for="tbRegistroPc" maxPages="5"/>   
			            </f:facet>
		            </rich:extendedDataTable>
            	</rich:column>
            	
            	<rich:column width="40" style="border:none">
            	</rich:column>
            	<rich:column style="border:none">
            		<h:outputLink value="#" id="linkRegPc">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelRegPc" attachTo="linkRegPc" operation="show" event="onclick"/>
					</h:outputLink>
              		<h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>  
            </h:panelGrid>
           <h:panelGrid columns="3">
	           	<h:commandButton value="Nuevo" actionListener="#{registroPcController.habilitarGrabarRegistroPc}" styleClass="btnEstilos"/>
               	<h:commandButton value="Guardar" actionListener="#{registroPcController.grabarRegistroPc}" styleClass="btnEstilos"/>  
              	<h:commandButton value="Cancelar" actionListener="#{registroPcController.cancelarRegistroPc}" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel rendered="#{registroPcController.blnRegistroPcRendered}" style="width: 740px ;background-color: #DEEBF5;border:2px solid #17356f;">
                
                <h:panelGrid columns="4" border="0">
                        <h:outputText value="Empresa :" styleClass="tamanioLetra"></h:outputText>
                        <h:selectOneMenu id="cboEmpRegPc" 
	                       	valueChangeListener="#{controllerFiller.reloadCboSucursales}"
	                       	style="width: 220px;"
	                       	value="#{registroPcController.beanRegistroPc.intIdEmpresa}">
					        <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
					        <a4j:support event="onchange" reRender="cboSucRegPc,cboAreaRegPc" ajaxSingle="true" />
					  	</h:selectOneMenu>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value=""></h:outputText>
                           
	                  	<h:outputText value="Estado :" styleClass="tamanioLetra" />
		                <h:selectOneMenu value="#{registroPcController.beanRegistroPc.intIdEstado}" style="width: 90px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			            </h:selectOneMenu>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value=""></h:outputText>
                        
                        <h:outputText value="Nombre de Sucursal :" styleClass="tamanioLetra"></h:outputText>
                        <h:selectOneMenu id="cboSucRegPc" 
	                       	valueChangeListener="#{controllerFiller.reloadCboAreas}"
	                       	style="width: 220px;"
	                       	value="#{registroPcController.beanRegistroPc.intIdSucursal}">
					        <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listSucursal}"
								itemValue="#{sel.intIdsucursal}" itemLabel="#{sel.strNombreComercial}"/>
					        <a4j:support event="onchange" reRender="cboAreaRegPc" ajaxSingle="true" />
					  	</h:selectOneMenu>
                        <h:outputLabel  value="*" style="color:red;font-size:20"></h:outputLabel>
                        <h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                        
                        <h:outputText value="Nombre de Área :" styleClass="tamanioLetra"></h:outputText>
	                	<h:selectOneMenu id="cboAreaRegPc"
	                      	style="width: 220px;" 
	                      	value="#{registroPcController.beanRegistroPc.intIdArea}">
				        <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listAreas}"
							itemValue="#{sel.intIdArea}" itemLabel="#{sel.strDescripcion}"/>   
				      	</h:selectOneMenu> 
                        <h:outputLabel  value="*" style="color:red;font-size:20"></h:outputLabel>
                        <h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                        
                        <h:outputText value="Número de Identificación :" styleClass="tamanioLetra"></h:outputText>
                        <h:inputText value="#{registroPcController.beanRegistroPc.strCodPc}" size="60"></h:inputText>
                        <h:outputLabel  value="*" style="color:red;font-size:20"></h:outputLabel>
                        <h:outputText value="" styleClass="tamanioLetra"></h:outputText>
                  </h:panelGrid>
                  <h:panelGrid columns="3">
                  		<rich:column width="155" style="border: none">
                  			<h:outputText value="Accesos Externos : " styleClass="tamanioLetra"></h:outputText>
                  		</rich:column>
                  		<rich:column style="border: none">
                  			<h:selectOneMenu id="cboTerceros" value="#{registroPcController.intCboExternos}" 
                  							 style="width: 220px;" onchange="jsSelectCboExternos();">
								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSWTERCERO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				            </h:selectOneMenu>
				            <h:inputHidden id="hiddenTerceros"></h:inputHidden>
                  		</rich:column>
                  		<rich:column style="width:155px; border: none">
           					<a4j:commandButton value="Agregar" actionListener="#{registroPcController.agregarExternos}" reRender="tbTerceros" styleClass="btnEstilos"></a4j:commandButton>
                  		</rich:column>
                  		
                  		<rich:column style="border: none"/>
                  		<rich:column style="border: none">
                  			<rich:dataTable id="tbTerceros" value="#{registroPcController.beanListAccesosExtPc}"
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rows="5" width="320" columns="3" columnsWidth="21,199,100"> 
			                	<rich:column>
				                    <h:outputText value="#{rowKey + 1}"></h:outputText>
				                </rich:column>
			                	<rich:column>
				                	<f:facet name="header">
				                    	<h:outputText value="Nombre"></h:outputText>
				                    </f:facet>
				                    <h:outputText value="#{item.strTipoAcceso}"></h:outputText>
				                </rich:column>
				                <rich:column>
				                	<f:facet name="header">
				                    	<h:outputText value="Activo"></h:outputText>
				                    </f:facet>
				                    <h:outputText value="#{item.intIdEstado}"></h:outputText>
				                </rich:column>
				                <f:facet name="footer">   
				                    <rich:datascroller for="tbTerceros" maxPages="10"/>   
				                </f:facet> 
			                </rich:dataTable>
                  		</rich:column>
                  		<rich:column style="border: none"/>
                  </h:panelGrid>
            </rich:panel>           
  		</rich:panel>
  
  	</rich:tab>
  </rich:tabPanel>
</h:form>
	