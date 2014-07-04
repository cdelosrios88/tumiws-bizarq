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
      
<rich:modalPanel id="pAgregarDomicilio" width="720" height="540"
	resizeable="false" style="background-color:#DEEBF5;" onhide="enableInputs()">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Domicilio"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="pAgregarDomicilio" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <a4j:include viewId="/pages/popup/domicilioBody.jsp"/>
</rich:modalPanel>
    
<rich:modalPanel id="pAgregarComunicacion" width="780" height="310"
resizeable="false" style="background-color:#DEEBF5;" onhide="enableInputs()">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Comunicación"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarComunicacion" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include id="inclComunicacion" viewId="/pages/popup/comunicacionBody.jsp"/>
</rich:modalPanel>
	
<rich:modalPanel id="mpCuentaBancaria" width="890" height="405"
	 resizeable="false" style="background-color:#DEEBF5" onhide="enableInputs()">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cuenta Bancaria"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpCuentaBancaria" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclCtaBancaria" viewId="/pages/popup/cuentaBancariaBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="mpRepLegal" width="970" height="530"
	 resizeable="false" style="background-color:#DEEBF5;" onhide="enableInputs()">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Representante Legal"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpRepLegal" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclRepLegal" viewId="/pages/popup/representanteLegalBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="mpContactoNatu" width="970" height="530"
	 resizeable="false" style="background-color:#DEEBF5;" onhide="enableInputs()">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Contacto"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpContactoNatu" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclContactoNatu" viewId="/pages/popup/contactoNaturalBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAgregarActividadEconomica" width="548" height="348"
resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Actividad Económica"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarActividadEconomica" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/popup/actividadEconomicaBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAgregarTipoComprobante" width="355" height="400"
resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Tipo de Comprobante"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarTipoComprobante" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/popup/tipoComprobanteBody.jsp"/>
</rich:modalPanel>

<rich:jQuery name="disableInputs" selector=":input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableInputs" selector=":input" query="removeAttr('disabled','disabled');" timing="onJScall" />

    <h:form id="frmPerJuriConve">
         	<h:panelGrid columns="3">
		        <a4j:commandButton value="Grabar" actionListener="#{perJuridicaController.savePerJuridicaEstructura}" styleClass="btnEstilos"
		        				reRender="contPanelInferior,mpMessage"
		        				oncomplete="if(#{perJuridicaController.isJuridicaSaved}){Richfaces.hideModalPanel('mpAgregarPerJuri')}else{Richfaces.showModalPanel('mpMessage')}"/>												                 
		    	<a4j:commandButton value="Cancelar" styleClass="btnEstilos">
		    		<rich:componentControl for="mpCuentaBancaria" operation="hide" event="onclick"/>
		    	</a4j:commandButton>
		    </h:panelGrid>
		    <rich:spacer height="4px"></rich:spacer>
		          	 
            <rich:panel style="border:1px solid #17356f ;width: 890px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
             		
                    <h:panelGrid columns="7">
                    	<rich:column style="border: none">
                    		<h:outputText value="Tipo de Relación : "></h:outputText>
                    	</rich:column>
                    	<rich:column style="width:110px">
                    		<h:selectOneMenu value="#{perJuridicaController.intCboTipoRelPerJuri}" style="width:100px" disabled="true">
							  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORELACPERJURIDICO}" 
							  	itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Tipo de Persona : "></h:outputText>
                    	</rich:column>
                    	<rich:column style="border:none; width:120px">
                    		<h:selectOneMenu value="#{perJuridicaController.intCboTiposPersona}" style="width:100px" disabled="true">
							  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  	itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Número de Documento : "></h:outputText>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{perJuridicaController.intCboTiposDocumento}" style="width:100px" disabled="true">
							 	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText value="#{perJuridicaController.strTxtRuc}" size="15"></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                    	
                </rich:panel>                          
				<rich:spacer height="8px"/><rich:spacer/> 
				<h:panelGrid style="width: 100%">											                 
			    	<a4j:commandButton value="Validar Datos" actionListener="#{perJuridicaController.validarPerJuriEstructura}" 
			    					   styleClass="btnEstilos" style="width: 100%" reRender="frmPerJuriConve"/>
			    </h:panelGrid>
			    <rich:spacer height="8px"/><rich:spacer/>
			    
			    <h:panelGrid columns="2" style="width: 100%">
			    	<rich:column>
			    		<h:outputText value="Datos Principales" styleClass="estiloLetra1"></h:outputText>
			    	</rich:column>
			    	<rich:column style="width: 150px">
			    		<h:outputLink value="http://www.sunat.gob.pe/cl-ti-itmrconsruc/jcrS00Alias" styleClass="buttonLink" style="color:#000000" target="_blank">
			    			<h:outputText value="Verificar Datos Sunat"></h:outputText>
			    		</h:outputLink>
			    	</rich:column>
			    </h:panelGrid>
			    
			    <rich:panel id="pFormPerJuridica" style="border: 0px solid #17356f;background-color:#DEEBF5;" >
				    <h:panelGrid columns="3">
				    	<rich:column style="width:135px">
				    		<h:outputText value="Razón Social"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strRazonSocial}" size="38"></h:inputText>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="6">
				    	<rich:column style="width:135px">
				    		<h:outputText value="Nombre Comercial"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strNombreComercial}" size="38"></h:inputText>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:33px">
				    		<h:outputText value="Siglas"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strSiglas}" size="15"></h:inputText>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="9">
				    	<rich:column style="width:135px">
				    		<h:outputText value="Fecha de Inscripción"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<rich:calendar value="#{perJuridicaController.perJuridica.juridica.dtFechaInscripcion}"  
			               				   datePattern="dd/MM/yyyy" inputStyle="width:76px"/>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:148px">
				    		<h:outputText value="Fecha Inicio de Actividades"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<rich:calendar datePattern="dd/MM/yyyy" value="#{perJuridicaController.perJuridica.juridica.dtFechaInicioAct}" inputStyle="width: 76px"/>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:132px">
				    		<h:outputText value="Estado Contribuyente"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intEstadoContribuyenteCod}">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
	  							
	  				<h:panelGrid columns="9">
				    	<rich:column style="width:135px">
				    		<h:outputText value="Número Documento"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.strRuc}" size="15"
				    					onkeydown="return validarNumDocIdentidad(this,event,11)"></h:inputText>
				    	</rich:column>
				    	<rich:column style="border:none; padding-left:20px; width:148px">
				    		<h:outputText value="Número de Trabajadores"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.intNroTrabajadores}" size="15"
				    					onkeydown="return validarEnteros(event)"></h:inputText>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:135px">
				    		<h:outputText value="Condición Contribuyente"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intCondContribuyente}">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONDCONTRIBUYENTE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="9">
				    	<rich:column style="border:none; width:135px">
				    		<h:outputText value="Tipo de Contribuyente"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intTipoContribuyenteCod}" style="width:168px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONTRIBUYENTE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:80px">
				    		<h:outputText value="Contabilidad"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intSistemaContable}" style="102px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONTABILIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:129px">
				    		<h:outputText value="Emisión Compobantes"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intEmisionComprobante}">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOEMISIONCOMPROBANTE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGroup id="pgDetalleJuridica">
				    	<h:panelGroup style="border:none; width:435px; float:left">
				    		<h:panelGrid columns="3">
				    			<rich:column style="width:135px">
						    		<h:outputText value="Actividad Económica"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<a4j:commandButton id="btnActividadEconomica" value="Seleccione" actionListener="#{actEconomicaController.showActiEcoJuridica}"
				    								oncomplete="Richfaces.showModalPanel('pAgregarActividadEconomica')" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
						    	</rich:column>
				    		</h:panelGrid>
				    		
				    		<rich:dataTable id="tbActividadEconomica" var="item" rowKeyVar="rowKey" sortMode="single" width="300px" 
			    							value="#{actEconomicaController.listActEconomicaJuridica}" rows="5">
			    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">	
				                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column style="border: 1px solid #C0C0C0" width="420">
				                    	<f:facet name="header">
				                            <h:outputText value="Actividad Económica"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
				                    </rich:column>
				                </rich:columnGroup>
			                    <f:facet name="footer">   
				                    <rich:datascroller for="tbActividadEconomica" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
				    	</h:panelGroup>
				    	
				    	<h:panelGroup style="border:none; float:left">
				    		<h:panelGrid columns="3">
				    			<rich:column style="width:135px">
						    		<h:outputText value="Tipo de Comprobantes"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<a4j:commandButton id="btnTipoComprobante" value="Seleccione" actionListener="#{tipoComprobanteController.showTipoComprobJuridica}"
				    								oncomplete="Richfaces.showModalPanel('pAgregarTipoComprobante')" reRender="frmTipoComprobante" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
						    	</rich:column>
				    		</h:panelGrid>
				    		
				    		<rich:dataTable id="tbTipoComprobante" var="item" rowKeyVar="rowKey" sortMode="single" width="300px" 
			    							value="#{tipoComprobanteController.listComprobanteJuridica}" rows="5">
			    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column style="border: 1px solid #C0C0C0" width="420">
				                    	<f:facet name="header">
				                            <h:outputText value="Tipo Comprobante"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
				                    </rich:column>
				                </rich:columnGroup>
			                    <f:facet name="footer">   
				                    <rich:datascroller for="tbTipoComprobante" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
				    	</h:panelGroup>
				    </h:panelGroup>
			    </rich:panel>
			    
			    <h:panelGrid columns="6">
		    		<rich:columnGroup>
		    			<rich:column>
				    		<h:outputText value="Ficha de Registro Público"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="15" value="#{perJuridicaController.perJuridica.juridica.strFichaRegPublico}"></h:inputText>
				    	</rich:column>
				    	
				    	<rich:column>
				    		<h:outputText value="Comercio Ext."></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intComercioExterior}" style="width:104px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					      		  <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMERCIOEXTERIOR}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
		    		</rich:columnGroup>
				    
				    <rich:columnGroup>
				    	<rich:column>
				    		<h:outputText value="Tipo de Persona"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.intCboTiposPersona}" style="width: 100px" disabled="true">
								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value="Roles Actuales"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.strRoles}" disabled="true" size="25"></h:inputText>
				    	</rich:column>
				    </rich:columnGroup>
		    	</h:panelGrid>
			    
			    <h:panelGroup layout="block">
			    	<rich:panel id="rpRepLegal" style="float:left; width: 435px; border:none">
			    		<h:panelGrid columns="4">
				    			<rich:column>
				    				<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value="Representante Legal"></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : "></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<a4j:commandButton value="Agregar" actionListener="#{naturalController.nuevoRepLegal}"
				    								   styleClass="btnEstilos1" oncomplete="Richfaces.showModalPanel('mpRepLegal')"
				    								   reRender="frmRepLegal">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgRepLegal">
				    			<rich:dataTable value="#{naturalController.beanListRepLegal}" 
				    					id="tbRepreLegal" rows="5" styleClass="dataTable1"
		                            	var="item" rowKeyVar="rowKey" sortMode="single">
		                            <rich:columnGroup rendered="#{item.personaEmpresa.vinculo.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}"> 
					                	<rich:column width="180">
						                    <h:outputText value="#{item.natural.strNombreCompleto}"></h:outputText>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Ver" actionListener="#{naturalController.verRepLegal}" styleClass="btnEstilos1" 
						                    				   reRender="frmRepLegal" oncomplete="Richfaces.showModalPanel('mpRepLegal'),disableInputs()">
						                    	<f:param name="rowKeyRepLegal" value="#{rowKey}"></f:param>
						                    </a4j:commandButton>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Quitar" actionListener="#{naturalController.quitarRepLegal}" 
	                     						reRender="tbRepreLegal" styleClass="btnEstilos1">
	                     						<f:param name="rowKeyRepLegal" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
						                </rich:column>
					                </rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbRepreLegal" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<rich:panel style="float:left; border:none">
			    		<h:panelGrid id="pAgregarComunicacion" columns="4">
				    			<rich:column style="border:none">
				    				<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<h:outputText value="Comunicaciones"></h:outputText>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<h:outputText value=" : "></h:outputText>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton id="btnPerJuriComunicacion" value="Agregar" actionListener="#{comunicacionController.showComuniPerJuridica}"  
				    								reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgListComunicacion">
				    			<rich:dataTable id="tbComunicaciones" value="#{comunicacionController.beanListComuniPerJuri}" 
				    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" styleClass="dataTable1"> 
				    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					                	<rich:column width="180">
						                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Ver" actionListener="#{comunicacionController.viewComunicacionJuridica}"
						                    	reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion'),disableInputs()" styleClass="btnEstilos1">
						                    	<f:param name="rowKeyComunicacionJuridica" value="#{rowKey}" />
						                    </a4j:commandButton>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Quitar" actionListener="#{comunicacionController.quitarComunicacionJuridica}" 
	                     						reRender="tbComunicaciones" styleClass="btnEstilos1">
	                     						<f:param name="rowKeyComunicacionJuridica" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
						                </rich:column>
					                </rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbComunicaciones" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<h:panelGroup layout="block" style="clear: both">
			    	</h:panelGroup>
			   </h:panelGroup>
			    
			    <h:panelGroup layout="block" style="clear: both">
			    	<rich:panel style="float:left; width:435px; border:none">
			    		<h:panelGrid columns="4">
				    			<rich:column>
				    				<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value="Direcciones"></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : "></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<a4j:commandButton id="btnPerJuriDireccion" value="Agregar" actionListener="#{domicilioController.showDomicilioJuriConve}"
				    					styleClass="btnEstilos1" reRender="pgFormDomicilio,frmDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgListDomicilio">
				    			<rich:dataTable id="tbDirecciones" value="#{domicilioController.beanListDirecPerJuri}"
				    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" styleClass="dataTable1"> 
				    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					                	<rich:column width="180">
						                    <h:outputText value="#{item.strDireccion}"></h:outputText>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Ver" actionListener="#{domicilioController.viewDomicilioJuridica}" styleClass="btnEstilos1" 
						                    	reRender="pgFormDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio'),disableInputs()">
						                    	<f:param name="rowKeyDomicilioJuridica" value="#{rowKey}" />
						                    </a4j:commandButton>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Quitar" actionListener="#{domicilioController.quitarDomicilioJuridica}" 
	                     						reRender="tbDirecciones" styleClass="btnEstilos1">
	                     						<f:param name="rowKeyDomicilioJuridica" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
						                </rich:column>
						            </rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbDirecciones" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<rich:panel id="pgCuentaBancaria" style="float:left; border:none">
			    		<h:panelGrid columns="4">
				    			<rich:column>
				    				<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value="Cuenta Bancaria"></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : "></h:outputText>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton value="Agregar" actionListener="#{ctaBancariaController.showCtaBancariaPerJuridica}"
				    					 			   reRender="frmCtaBancaria" styleClass="btnEstilos1">
			          	 				<rich:componentControl for="mpCuentaBancaria" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<rich:dataTable value="#{ctaBancariaController.listCtaBancariaJuriConve}" 
				    					id="tbCuentaBancaria" rows="5" styleClass="dataTable1"
		                            	var="item" rowKeyVar="rowKey" sortMode="single"> 
		                           	<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					                	<rich:column width="180">
						                    <h:outputText value="#{item.strNroCuentaBancaria}"></h:outputText>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Ver" actionListener="#{ctaBancariaController.verCtaBancariaJuridica}" styleClass="btnEstilos1" 
						                    				   reRender="frmCtaBancaria" oncomplete="Richfaces.showModalPanel('mpCuentaBancaria'),disableInputs()">
						                    	<f:param name="rowKeyCtaBancariaJuridica" value="#{rowKey}"></f:param>
						                    </a4j:commandButton>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Quitar" actionListener="#{ctaBancariaController.quitarCtaBancariaJuridica}" 
	                     						reRender="tbCuentaBancaria" styleClass="btnEstilos1">
	                     						<f:param name="rowKeyCtaBancariaJuridica" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
						                </rich:column>
					                </rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbCuentaBancaria" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
			    	</rich:panel>
			    	
			    	<h:panelGroup layout="block" style="clear: both">
			    	</h:panelGroup>
			    </h:panelGroup>
			    
			    <h:panelGroup layout="block" style="clear: both">
			    	<rich:panel id="rpContactoNatu" style="float:left; width:435px; border:none">
			    		<h:panelGrid columns="4">
				    			<rich:column>
				    				<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value="Contacto"></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : "></h:outputText>
				    			</rich:column>
				    			<rich:column>
				    				<a4j:commandButton value="Agregar" actionListener="#{naturalController.nuevoContactoNatu}"
				    								   styleClass="btnEstilos1" oncomplete="Richfaces.showModalPanel('mpContactoNatu')"
				    								   reRender="frmContactoNatu">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgContactoNatu">
				    			<rich:dataTable value="#{naturalController.beanListContactoNatu}" 
				    					id="tbContactoNatu" rows="5" 
		                            	var="item" rowKeyVar="rowKey" sortMode="single">
		                            <rich:columnGroup rendered="#{item.personaEmpresa.vinculo.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}"> 
					                	<rich:column width="180">
						                    <h:outputText value="#{item.natural.strNombreCompleto}"></h:outputText>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Ver" actionListener="#{naturalController.verContactoNatu}" styleClass="btnEstilos1" 
						                    				   reRender="frmContactoNatu" oncomplete="Richfaces.showModalPanel('mpContactoNatu'),disableInputs()">
						                    	<f:param name="rowKeyContactoNatu" value="#{rowKey}"></f:param>
						                    </a4j:commandButton>
						                </rich:column>
						                <rich:column>
						                    <a4j:commandButton value="Quitar" actionListener="#{naturalController.quitarContactoNatu}" 
	                     						reRender="tbContactoNatu" styleClass="btnEstilos1">
	                     						<f:param name="rowKeyContactoNatu" value="#{rowKey}"></f:param>
	                     					</a4j:commandButton>
						                </rich:column>
					                </rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbContactoNatu" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<h:panelGroup layout="block" style="clear: both">
			    	</h:panelGroup>
			    </h:panelGroup>
			    
			    <h:panelGroup layout="block" style="clear: both">
			    
			    </h:panelGroup>
               
         </rich:panel>           
   </h:form>