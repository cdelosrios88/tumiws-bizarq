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
	<script language="JavaScript" src="/SocioWeb/js/main.js"  type="text/javascript"></script>
    <h:form id="frmRepLegal">
    
    		<h:panelGrid columns="3">
    			<rich:column width="165">
    				<h:outputText value="Nombre de Persona Jurídica: "></h:outputText>
    			</rich:column>
    			<rich:column width="285">
    				<h:inputText size="50" disabled="true"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText size="20" disabled="true"></h:inputText>
    			</rich:column>
    		</h:panelGrid>
    		<rich:spacer height="15"></rich:spacer>
         	<h:panelGrid columns="2"> 
		        <a4j:commandButton value="Grabar" actionListener="#{repLegalController.addRepLegal}" styleClass="btnEstilos"  
		        				   reRender="rpRepLegal,pgRepLegal" oncomplete="Richfaces.hideModalPanel('mpRepLegal')"/>												                 
		    	<a4j:commandButton value="Cancelar" styleClass="btnEstilos" oncomplete="Richfaces.hideModelPanel('mpRepLegal')"/>
		    </h:panelGrid>
		    <rich:spacer height="4px"></rich:spacer>
		          	 
            <rich:panel style="border:1px solid #17356f; width: 925px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">
             		
                    <h:panelGrid columns="7">
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Vínculo: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="150">
                    		<h:selectOneMenu style="width:140px" value="#{repLegalController.intCboTipoVinculo}" disabled="true">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Persona: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                    		<h:selectOneMenu value="#{repLegalController.intCboTipoPersona}" disabled="true">
                    			<f:selectItems value="#{parametersController.cboTiposPersona}"/>
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column width="144">
                    		<h:outputText value="Número de Documento: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                    		<h:selectOneMenu style="width:110px" value="#{repLegalController.intCboTipoDoc}">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText size="15" value="#{repLegalController.strNroDocIdentidad}"></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
                     <h:panelGrid style="width: 100%">
                     	<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
                     					   actionListener="#{repLegalController.validarRepLegal}" reRender="frmRepLegal"></a4j:commandButton>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
                     <h:panelGrid>
                     	<h:outputText value="Datos Personales" styleClass="estiloLetra1"></h:outputText>
                     </h:panelGrid>
                     <rich:spacer height="8"></rich:spacer>
                     <rich:spacer height="8"></rich:spacer>
                     
                     <h:panelGroup layout="block" id="pgpRepLegal">
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder1" style="margin-left: -10px">
                     			<rich:column>
		                     		<h:outputText value="Ap. Paterno: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText value="#{repLegalController.beanRepLegal.strApePat}" size="15"></h:inputText>
		                     	</rich:column>
								
                     			<rich:column>
		                     		<h:outputText value="Fecha de Nacimiento: " style="padding-top:0px"></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<rich:calendar datePattern="dd/MM/yyyy" value="#{repLegalController.beanRepLegal.dtFechaNac}"
		                     		inputStyle="width:70px;" cellWidth="10px" cellHeight="20px"/>
		                     	</rich:column>
								
                     			<rich:column>
                     				<h:outputText value="Sexo: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:selectOneRadio value="#{repLegalController.beanRepLegal.intSexo}" style="width:155px">
                     					<f:selectItems value="#{parametersController.rdoSexo}"/>
                     				</h:selectOneRadio>
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Estado Civil: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:selectOneMenu value="#{repLegalController.beanRepLegal.intEstadoCivil}">
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Persona: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:selectOneMenu value="#{repLegalController.beanRepLegal.intTipoPersona}" disabled="true">
                     					<f:selectItems value="#{parametersController.cboTiposPersona}"/>
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PPARAM_T_TIPOPERSONA}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>
                     			</rich:column>

                     			<rich:column>
                     				<h:outputText value="Tipo de Vínculo: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:selectOneMenu value="#{repLegalController.beanRepLegal.intTipoVinculo}" disabled="true">
                     					<f:selectItems value="#{parametersController.cboTipoVinculo}"/>
                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     				</h:selectOneMenu>
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Roles Actuales: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText size="30" value="#{repLegalController.beanRepLegal.strRoles}" disabled="true"></h:inputText>
                     			</rich:column>
                     			
                     			<rich:column>
                     				<h:outputText value="Número Documento: "></h:outputText>
                     			</rich:column>
                     			<rich:column>
                     				<h:inputText value="#{repLegalController.beanRepLegal.strNroDocIdentidad}"
                     							onkeydown="return validarNumDocIdentidad(this,event,8)"></h:inputText>
                     			</rich:column>
                     		</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:left">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder1">
                     			<rich:column>
		                     		<h:outputText value="Ap. Materno: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText size="15" value="#{repLegalController.beanRepLegal.strApeMat}"></h:inputText>
		                     	</rich:column>
		                     	
	                     		<rich:column>
		                     		<h:outputText value="Firma: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:graphicImage value="#{repLegalController.beanRepLegal.strPathFirma}" width="200" height="80"></h:graphicImage>
		                     	</rich:column>
	                     	</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:right">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder1">
                     			<rich:column>
		                     		<h:outputText value="Nombres: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:inputText size="25" value="#{repLegalController.beanRepLegal.strNombres}"></h:inputText>
		                     	</rich:column>
		                     	
                     			<rich:column>
		                     		<h:outputText value="Foto: "></h:outputText>
		                     	</rich:column>
		                     	<rich:column>
		                     		<h:graphicImage value="#{repLegalController.beanRepLegal.strPathFoto}" width="80" height="80"></h:graphicImage>
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
	                     		<h:selectOneMenu>
	                     			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
							  						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     		</h:selectOneMenu>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="20" onkeydown="return validarNumDocIdentidad(this,event,8)"></h:inputText>
	                     	</rich:column>
	                     	<rich:column>
	                     		<a4j:commandButton value="Agregar" styleClass="btnEstilos"></a4j:commandButton>
	                     	</rich:column>
	                    </h:panelGrid>
	                    
	                    <h:panelGrid columns="3" styleClass="tableCellBorder1" style="margin-left:-10px">
	                    	<rich:column>
	                    		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
	                    	</rich:column>
	                    	<rich:column>
	                    		<h:outputText value="Comunicaciones"></h:outputText>
	                    	</rich:column>
	                    	<rich:column>
	                    		<a4j:commandButton id="btnRepLegalComunicacion" value="Agregar" actionListener="#{comunicacionController.agregarComunicacion}" styleClass="btnEstilos1" 
				    								reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')"></a4j:commandButton>
	                    	</rich:column>
	                    </h:panelGrid>
	                    
	                    <rich:dataTable id="tbComunicaciones" value="#{comunicacionController.beanListComuniRepLegal}" 
	                    				rows="5" var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strDescCom}"></h:outputText>
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
	                    
	                    <h:panelGrid columns="3" styleClass="tableCellBorder1" style="margin-left:-10px">
	                    	<rich:column>
	                    		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
	                    	</rich:column>
	                    	<rich:column>
	                    		<h:outputText value="Direcciones"></h:outputText>
	                    	</rich:column>
	                    	<rich:column>
	                    		<a4j:commandButton id="btnRepLegalDireccion" value="Agregar" actionListener="#{domicilioController.agregarDireccion}"
				    					styleClass="btnEstilos1" reRender="pgFormDomicilio,frmDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
			          	 		</a4j:commandButton>
	                    	</rich:column>
	                    </h:panelGrid>
	                    
	                    <rich:dataTable id="tbDirecciones" value="#{domicilioController.beanListDirecRepLegal}"
	                    				rows="5" var="item" rowKeyVar="rowKey" sortMode="single"> 
				                	<rich:column width="180">
					                    <h:outputText value="#{item.strDireccion}"></h:outputText>
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
                     </h:panelGroup>
                    	
                </rich:panel>
               
         </rich:panel>           
   </h:form>