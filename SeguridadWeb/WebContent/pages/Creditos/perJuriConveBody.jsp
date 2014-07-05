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
	resizeable="false" style="background-color:#DEEBF5;">
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
     <a4j:include viewId="/pages/popups/domicilioBody.jsp"/>
</rich:modalPanel>
    
<rich:modalPanel id="pAgregarComunicacion" width="780" height="310"
resizeable="false" style="background-color:#DEEBF5;">
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
    <a4j:include viewId="/pages/popups/comunicacionBody.jsp"/>
</rich:modalPanel>
	
<rich:modalPanel id="mpCuentaBancaria" width="865" height="415"
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
        
        <a4j:include id="inclCtaBancaria" viewId="/pages/Creditos/cuentaBancariaBody.jsp"/>
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
        
        <a4j:include id="inclRepLegal" viewId="/pages/Creditos/representanteLegalBody.jsp"/>
</rich:modalPanel>
      
<rich:jQuery name="disableInputs" selector=":input" query="attr('disabled','disabled');" timing="onJScall" />
<rich:jQuery name="enableInputs" selector=":input" query="removeAttr('disabled','disabled');" timing="onJScall" />

    <h:form id="frmPerJuriConve">
         	<h:panelGrid columns="3">                  
		        <a4j:commandButton value="Grabar" actionListener="#{perJuridicaController.savePerJuridicaEstructura}" styleClass="btnEstilos"
		        				reRender="pAgregarPerJuri,pgRazonSocN1,pgRazonSocN2,pgRazonSocN3"/>												                 
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
							 	<f:selectItems value="#{parametersController.cboTiposDocumento}"/>
							 	<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText value="#{perJuridicaController.intTxtRuc}" size="15"></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                    	
                </rich:panel>                          
				<rich:spacer height="8px"/><rich:spacer/> 
				<h:panelGrid style="width: 100%">											                 
			    	<a4j:commandButton value="Validar Datos" actionListener="#{perJuridicaController.validarPerJuridica}" 
			    					   styleClass="btnEstilos" style="width: 100%" reRender="frmPerJuriConve"/>
			    </h:panelGrid>
			    <rich:spacer height="8px"/><rich:spacer/>
			    
			    <h:panelGrid columns="2" style="width: 100%">
			    	<rich:column>
			    		<h:outputText value="Datos Principales" styleClass="estiloLetra1"></h:outputText>
			    	</rich:column>
			    	<rich:column style="width: 150px">
			    		<h:outputLink value="http://www.google.com" styleClass="buttonLink" style="color:#000000" target="_blank">
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
				    		<rich:calendar value="#{perJuridicaController.perJuridica.juridica.dtFechaInicioActi}" 
			               				   datePattern="dd/MM/yyyy" inputStyle="width: 76px"/>
				    	</rich:column>
				    	<rich:column style="padding-left:20px; width:132px">
				    		<h:outputText value="Estado Contribuyente"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intEstadoContribuyente}">
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
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.intRuc}" size="15"></h:inputText>
				    	</rich:column>
				    	<rich:column style="border:none; padding-left:20px; width:148px">
				    		<h:outputText value="Número de Trabajadores"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.intNumTrabajadores}" size="15"></h:inputText>
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
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intTipoContribuyente}" style="width:168px">
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
				    
				    <h:panelGrid columns="2">
				    	<rich:panel style="border:none; width:293">
				    		<h:panelGrid columns="3" style="margin-left: -10px">
				    			<rich:column style="width:135px">
						    		<h:outputText value="Actividad Económica"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:outputText value=" : "></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:inputText value="#{perJuridicaController.actEconomica.strActEconomica}"></h:inputText>
						    	</rich:column>
				    		</h:panelGrid>
				    		
				    		<rich:panel style="border:none; margin:0 auto">
				    			<rich:scrollableDataTable id="tbAccesosFueraHora" value="#{accesoEspecialController.beanListAccesosFueraHora}" 
				                            			var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty accesoEspecialController.beanListAccesosFueraHora}"
				                            			width="277px" height="124px"> 
				                	<rich:column width="29px">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column width="176px">
				                        <f:facet name="header">
				                            <h:outputText value="Actividad Económica"></h:outputText>
				                        </f:facet>
				                        <h:outputText id="lblAFHUsuario" value="#{item.strFullNameUsu}"></h:outputText>
				                        <rich:toolTip for="lblAFHUsuario" value="#{item.strFullNameUsu}"></rich:toolTip>
				                    </rich:column>
				                    <rich:column width="30px" style="margin-left:15px">
				                    	<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
				                    </rich:column>
				                </rich:scrollableDataTable>
				    		</rich:panel>
				    	</rich:panel>
				    	
				    	<h:panelGrid>
				    		<h:panelGrid columns="7">
				    			<rich:column width="47"></rich:column>
				    			<rich:column style="width:78px">
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
						    	
						    	<rich:column style="padding-left:20px; width:125px">
						    		<h:outputText value="Tipo de Comprobantes"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:outputText value=" : "></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:selectManyCheckbox layout="pageDirection">
						    			  <f:selectItem itemValue="0" itemLabel="Factura"/>
										  <f:selectItem itemValue="1" itemLabel="Boleta de Venta"/>
										  <f:selectItem itemValue="2" itemLabel="Guía de Remisión"/>
						    		</h:selectManyCheckbox>
						    	</rich:column>
				    		</h:panelGrid>
				    		
				    		<h:panelGrid columns="3">
						    	<rich:column>
						    		<h:outputText value="Ficha de Registro Público"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:outputText value=" : "></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:inputText value="#{empresaController.txtRuc}" size="15"></h:inputText>
						    	</rich:column>
						    </h:panelGrid>
						    
						    <h:panelGrid columns="6">
						    	<rich:column style="width:135px">
						    		<h:outputText value="Tipo de Persona"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:outputText value=" : "></h:outputText>
						    	</rich:column>
						    	<rich:column style="width:163px">
						    		<h:selectOneMenu value="#{perJuridicaController.intCboTiposPersona}" style="width: 100px" disabled="true">
										<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									</h:selectOneMenu>
						    	</rich:column>
						    	<rich:column style="width:80px">
						    		<h:outputText value="Roles Actuales"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:outputText value=" : "></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<h:inputText value="Socio, Proveedor" disabled="true" size="15"></h:inputText>
						    	</rich:column>
						    </h:panelGrid>
				    	</h:panelGrid>
				    </h:panelGrid>
			    </rich:panel>
			    
			    <h:panelGroup layout="block">
			    	<rich:panel id="rpRepLegal" style="float:left; width: 435px; border:none">
			    		<h:panelGrid columns="3">
				    			<rich:column style="width:135px">
				    				<h:outputText value="Representante Legal" />
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : " />
				    			</rich:column>
				    			<rich:column>
				    				<a4j:commandButton value="Agregar" actionListener="#{naturalController.cleanBeanRepLegal}"
				    								   styleClass="btnEstilos1" oncomplete="Richfaces.showModalPanel('mpRepLegal')"
				    								   reRender="frmRepLegal">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgRepLegal">
				    			<rich:dataTable value="#{naturalController.beanListRepLegal}" 
				    					id="tbRepreLegal" rows="5" 
		                            	var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strNombreCompleto}"></h:outputText>
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{naturalController.verRepLegal}" styleClass="btnEstilos1" 
					                    				   reRender="frmRepLegal" oncomplete="Richfaces.showModalPanel('mpRepLegal'),disableInputs()">
					                    	<f:param id="idTipoVinculo" name="idTipoVinculo" value="#{item.intTipoVinculo}"></f:param>
					                    	<f:param id="idTipoPersona" name="idTipoPersona" value="#{item.intTipoPersona}"></f:param>
					                    	<f:param id="idTipoDoc" name="idTipoDoc" value="#{item.intTipoDocIdentidad}"></f:param>
					                    	<f:param id="nroDocIdentidad" name="nroDocIdentidad" value="#{item.strNroDocIdentidad}"></f:param>
					                    </a4j:commandButton>
					                </rich:column>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbRepreLegal" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<rich:panel style="float:left; border:none">
			    		<h:panelGrid id="pAgregarComunicacion" columns="3">
				    			<rich:column style="width:135px">
				    				<h:outputText value="Comunicaciones" />
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : " />
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton id="btnPerJuriComunicacion" value="Agregar" actionListener="#{comunicacionController.agregarComunicacion}"  
				    								reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" styleClass="btnEstilos1">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgListComunicacion">
				    			<rich:dataTable id="tbComunicaciones" value="#{comunicacionController.beanListComuniPerJuri}" 
				    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strDescCom}" />
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{comunicacionController.viewComunicacion}"
					                    	reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" styleClass="btnEstilos1">
					                    	<f:param name="pIntIdPersona" 		value="#{item.intIdPersona}" />
			                       			<f:param name="pIntIdComunicacion" 	value="#{item.intIdComunicacion}" />
					                    </a4j:commandButton>
					                </rich:column>
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
			    		<h:panelGrid columns="3">
				    			<rich:column style="width:135px">
				    				<h:outputText value="Direcciones" />
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : " />
				    			</rich:column>
				    			<rich:column>
				    				<a4j:commandButton id="btnPerJuriDireccion" value="Agregar" actionListener="#{domicilioController.agregarDireccion}"
				    					styleClass="btnEstilos1" reRender="pgFormDomicilio,frmDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgListDomicilio">
				    			<rich:dataTable id="tbDirecciones" value="#{domicilioController.beanListDirecPerJuri}"
				    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strDireccion}" />
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{domicilioController.viewDomicilio}" styleClass="btnEstilos1" 
					                    	reRender="pgFormDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
					                    	<f:param id="idPersona" 	name="pIntIdPersona" 	value="#{item.intIdPersona}" />
			                       			<f:param id="idDomicilio" 	name="pIntIdDomicilio" 	value="#{item.intIdDireccion}" />
					                    </a4j:commandButton>
					                </rich:column>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbDirecciones" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
			    	</rich:panel>
			    	
			    	<rich:panel id="rpCuentaBancaria" style="float:left; border:none">
			    		<h:panelGrid columns="3">
				    			<rich:column style="width:135px">
				    				<h:outputText value="Cuenta Bancaria" />
				    			</rich:column>
				    			<rich:column>
				    				<h:outputText value=" : " />
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton value="Agregar" actionListener="#{ctaBancariaController.cleanBeanCtaBancaria}"
				    					 			   reRender="frmCtaBancaria" styleClass="btnEstilos1">
			          	 				<rich:componentControl for="mpCuentaBancaria" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid id="pgCtaBancaria">
				    			<rich:dataTable value="#{ctaBancariaController.beanListCtaBancaria}" 
				    					id="tbCuentaBancaria" rows="5" 
		                            	var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strNroCtaBancaria}" />
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{ctaBancariaController.verCtaBancaria}" styleClass="btnEstilos1" 
					                    				   reRender="frmCtaBancaria" oncomplete="Richfaces.showModalPanel('mpCuentaBancaria'),disableInputs()">
					                    	<f:param id="idPersonaCtaBan" name="idPersonaCtaBan" value="#{item.intIdPersona}"></f:param>
					                    	<f:param id="idCtaBancaria" name="idCtaBancaria" value="#{item.intIdCtaBancaria}"></f:param>
					                    </a4j:commandButton>
					                </rich:column>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbCuentaBancaria" maxPages="5"/>   
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