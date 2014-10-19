<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->

<rich:modalPanel id="pAgregarProveedorDetalle" width="360" height="320"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Tipo de Proveedor"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarProveedorDetalle" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/proveedor/popup/agregarProveedorDetalle.jsp"/>
</rich:modalPanel>

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
     <a4j:include viewId="/pages/popup/domicilioBody.jsp"/>
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
    <a4j:include id="inclComunicacion" viewId="/pages/popup/comunicacionBody.jsp"/>
</rich:modalPanel>
	
<rich:modalPanel id="mpCuentaBancaria" width="670" height="390"
	 resizeable="false" style="background-color:#DEEBF5">
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

<rich:modalPanel id="mpRepLegal" width="970" height="500"
	 resizeable="false" style="background-color:#DEEBF5;">
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
	 resizeable="false" style="background-color:#DEEBF5;">
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

<rich:modalPanel id="pAgregarActividadEconomica" width="620" height="430"
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

<rich:modalPanel id="pAgregarTipoComprobante" width="560" height="430"
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


<rich:modalPanel id="pAlertaRegistro" width="360" height="135"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistro">
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="80">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{proveedorController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje,panelBotones"/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{proveedorController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelResultado,panelMensaje,contPanelInferior"
             			rendered="#{proveedorController.mostrarBtnEliminar}"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>



<h:form id="frmProveedor">
	<h:outputLabel value="#{proveedorController.inicioPage}"/>
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
        	
        	<h:inputHidden value="#{messageController.strTypeMessage}"/>
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:5px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="REGISTRO DE PROVEEDOR" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
            
            <h:panelGrid columns="11" id="panelBusqueda">      
            	<rich:column width="60" style="text-align: left;">
                	<h:outputText value="Persona : "/>
            	</rich:column>
            	
            	<rich:column width="120" style="text-align: left;">
                	<h:selectOneMenu
						value="#{proveedorController.intTipoBusquedaFiltro}"
						style="width: 120px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQUEDA}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>	
					</h:selectOneMenu>					
            	</rich:column>            	
            	
            	<rich:column width="200" style="text-align: left;">
                	<h:inputText size="32"
                		value="#{proveedorController.strTextoFiltro}"/>                	
            	</rich:column>
            	
           		<rich:column width="30" style="text-align: right;">
                	<h:outputText value="Tipo : "/>
            	</rich:column>
            	
             	<rich:column width="250" style="text-align: left;">
                	<h:selectOneMenu
						style="width: 250px;"
						value="#{proveedorController.intTipoProveedorFiltro}">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_TIPOPROVEEDOR_TODOS}" itemLabel="Todos"/>
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOPROVEEDOR}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
            	</rich:column>
            	

            	
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelResultado,panelMensaje"
                    	action="#{proveedorController.buscar}" style="width:100px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>
                
            <h:panelGrid id="panelResultado">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{proveedorController.listaProveedor}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="930px" 
					height="165px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>                        	
                    </rich:column>
                  	<rich:column width="60px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intPersPersona}"/>
                	</rich:column>
                    <rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nombre"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{not empty item.persona.natural}">
                      		<h:outputText value="#{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno} #{item.persona.natural.strNombres}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{not empty item.persona.juridica}">
                      		<h:outputText value="#{item.persona.juridica.strNombreComercial}"/>
                      	</h:panelGroup>
                  	</rich:column>
                    <rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Razón Social"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.persona.juridica.strRazonSocial}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="RUC"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.persona.strRuc}"/>
                  	</rich:column>
                  	<rich:column width="190" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Dirección"/>
                      	</f:facet>
                      	<h:outputText value="#{item.persona.listaDomicilio[0].strNombreVia} #{item.persona.listaDomicilio[0].intNumeroVia}"/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Fecha de Inscripción"/>
                        </f:facet>
                        <h:outputText value="#{item.persona.juridica.dtFechaInscripcion}">
                        	<f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>
					
					<a4j:support event="onRowClick"  
						actionListener="#{proveedorController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{proveedorController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
				<h:inputHidden value="#{perJuridicaController.strTxtRuc}"/>
				<h:inputHidden value="#{fileUploadController.strTitle}"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{proveedorController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{proveedorController.mostrarMensajeExito}"/>
			<h:outputText value="#{proveedorController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{proveedorController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{proveedorController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{proveedorController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelResultado"
			    	disabled="#{!proveedorController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{proveedorController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{proveedorController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGroup rendered="#{!proveedorController.datosValidados}">
			
				<h:panelGrid columns="12" id="panelValidar">
					<rich:column width="80">
						<h:outputText value="Tipo de Rol : "/>
				  	</rich:column>
				  	<rich:column width="120">
						<h:inputText size="20" value="Proveedor" readonly="true" style="background-color: #BFBFBF;"/>						
				 	</rich:column>
					<rich:column width="110" style="text-align: right;">
						<h:outputText value="Tipo de Persona : "/>
				  	</rich:column>
				    <rich:column width="100">
						<h:selectOneMenu
							style="width: 100px;"
							value="#{proveedorController.intTipoPersona}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" action="#{proveedorController.seleccionarPersona}" reRender="panelValidar"/>	
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="150" style="text-align: right;">
						<h:outputText value="Número de Documento : "/>
				  	</rich:column>
				    <rich:column width="80">
						<h:selectOneMenu
							style="width: 80px;"
							disabled="true"
							value="#{proveedorController.intTipoDocumento}">
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO_DNI}" itemLabel="DNI"/>
							<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO_RUC}" itemLabel="RUC"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="250">
						<h:inputText size="30" value="#{proveedorController.strTextoValidar}" 
							onkeydown="if(#{proveedorController.intTipoDocumento==applicationScope.Constante.PARAM_T_TIPODOCUMENTO_DNI}){return validarNumDocIdentidad(this,event,8)}else{return validarNumDocIdentidad(this,event,11)}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="15px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="880">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Validar Datos" reRender="contPanelInferior,panelBotones,panelMensaje"
	                    	action="#{proveedorController.validarDatos}" style="width:865px"/>
				  	</rich:column>
				</h:panelGrid>
						
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{proveedorController.datosValidados}">


			    <rich:spacer height="8px"/><rich:spacer/>

				<h:panelGroup id="panelNatural" rendered="#{perJuridicaController.perJuridica.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
					 <h:panelGrid columns="12">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Apellido Paterno"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="38" value="#{perJuridicaController.perJuridica.natural.strApellidoPaterno}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:65px;text-align: right">
				    		<h:outputText value="Materno : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="26" value="#{perJuridicaController.perJuridica.natural.strApellidoMaterno}"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:50px;text-align: right">
				    		<h:outputText value="Nombre : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="30" value="#{perJuridicaController.perJuridica.natural.strNombres}"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    </h:panelGrid>
				     
				     <h:panelGrid columns="12">
				     	<rich:column style="width:125px">
				    		<h:outputText value="Fecha de Nacimiento"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<rich:calendar datePattern="dd/MM/yyyy" inputStyle="width: 207px" 
				    			value="#{perJuridicaController.perJuridica.natural.dtFechaNacimiento}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>				    		
				    	</rich:column>
				    	<rich:column style="width:46px;text-align: right">
				    		<h:outputText value="Sexo : "></h:outputText>
				    	</rich:column>
				    	<rich:column width="155">
				    		<h:selectOneMenu 
				    			value="#{perJuridicaController.perJuridica.natural.intSexoCod}"
								disabled="#{proveedorController.deshabilitarNuevo}"
								style="width: 155px;">
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOSEXO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>	
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="width:50px;text-align: right">
				    		<h:outputText value="DNI : "/>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="30" disabled="true" value="#{proveedorController.strTextoValidar}"
				    				disabled="true"/>
				    	</rich:column>
				     </h:panelGrid>
				</h:panelGroup>
							    
			    <h:panelGrid columns="2" style="width: 100%">
			    	<rich:column>
			    		<h:outputText value="Datos Principales" styleClass="estiloLetra1"></h:outputText>
			    	</rich:column>
			    	<rich:column style="width: 150px">
			    		<h:outputLink value="http://www.sunat.gob.pe/cl-ti-itmrconsruc/jcrS00Alias" 
			    				styleClass="buttonLink" style="color:#000000" target="_blank">
			    			<h:outputText value="Verificar Datos Sunat"></h:outputText>
			    		</h:outputLink>
			    	</rich:column>
			    </h:panelGrid>
			    
			    <h:panelGroup id="pFormPerJuridica" style="border: 0px solid #17356f;background-color:#DEEBF5;" >
				    <h:panelGrid columns="3">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Razón Social"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strRazonSocial}" size="38"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="11">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Nombre Comercial"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strNombreComercial}" size="38"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:62px;text-align: right">
				    		<h:outputText value="Siglas"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.strSiglas}" size="24"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:150px;text-align: right" rendered="#{perJuridicaController.perJuridica.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
				    		<h:outputText value="Suspensión renta 4ta :"></h:outputText>
				    	</rich:column>
				    	<rich:column width="150" rendered="#{perJuridicaController.perJuridica.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}">
				    		<h:selectOneMenu value="#{proveedorController.proveedorNuevo.intRetencionCuarta}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			style="width:150px">
								<tumih:selectItems var="sel"
									cache="#{applicationScope.Constante.PARAM_T_RETENCION}"
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="9">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Fecha de Inscripción"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<rich:calendar value="#{perJuridicaController.perJuridica.juridica.dtFechaInscripcion}"  
			               				   datePattern="dd/MM/yyyy" inputStyle="width:93px"
			               				   disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:170px;text-align: right">
				    		<h:outputText value="Fecha Inicio de Actividades : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<rich:calendar datePattern="dd/MM/yyyy" value="#{perJuridicaController.perJuridica.juridica.dtFechaInicioAct}" 
				    			inputStyle="width: 132px" disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:130px;text-align: right">
				    		<h:outputText value="Estado Contribuyente :"></h:outputText>
				    	</rich:column>
				    	<rich:column width="150">
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intEstadoContribuyenteCod}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			style="width:150px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCONTRIB}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
	  							
	  				<h:panelGrid columns="9">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Número Documento"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.strRuc}" 
				    			size="15"
				    			onkeydown="return validarNumDocIdentidad(this,event,11)"
				    			disabled="#{(not empty perJuridicaController.perJuridica.strRuc) || proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    	<rich:column style="width:189px;text-align: right">
				    		<h:outputText value="Número de Trabajadores : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.perJuridica.juridica.intNroTrabajadores}" 
				    			size="24"
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			onkeydown="return validarEnteros(event)"></h:inputText>
				    	</rich:column>
				    	<rich:column style="width:150px;text-align: right">
				    		<h:outputText value="Condición Contribuyente :"></h:outputText>
				    	</rich:column>
				    	<rich:column width="150">
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intCondContribuyente}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			style="width:150px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" 
							      	cache="#{applicationScope.Constante.PARAM_T_TIPOCONDCONTRIBUYENTE}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
				    
				    <h:panelGrid columns="9">
				    	<rich:column style="width:125px">
				    		<h:outputText value="Tipo de Contribuyente"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intTipoContribuyenteCod}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			style="width:222px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" 
							      	cache="#{applicationScope.Constante.PARAM_T_TIPOCONTRIBUYENTE}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="width:70px">
				    		<h:outputText value="Contabilidad :"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intSistemaContable}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			style="width:150px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" 
							      	cache="#{applicationScope.Constante.PARAM_T_TIPOCONTABILIDAD}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="width:150px;text-align: right">
				    		<h:outputText value="Emisión Compobantes : "></h:outputText>
				    	</rich:column>
				    	<rich:column width="150">
				    		<h:selectOneMenu  style="width:150px"
				    			disabled="#{proveedorController.deshabilitarNuevo}"
				    			value="#{perJuridicaController.perJuridica.juridica.intEmisionComprobante}">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							      <tumih:selectItems var="sel" 
							      	cache="#{applicationScope.Constante.PARAM_T_TIPOEMISIONCOMPROBANTE}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    </h:panelGrid>
				    
			    	<h:panelGrid columns="15">		    		
		    			<rich:column width="125px">
				    		<h:outputText value="Ficha de Reg Público"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText size="38" value="#{perJuridicaController.perJuridica.juridica.strFichaRegPublico}"
				    			disabled="#{proveedorController.deshabilitarNuevo}"/>
				    	</rich:column>
				    					    	
				    	<rich:column>
				    		<h:outputText value="Comercio Ext:"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.juridica.intComercioExterior}" 
				    			disabled="#{proveedorController.deshabilitarNuevo}" 
				    			style="width:150px">
								  <f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					      		  <tumih:selectItems var="sel" 
					      		  	cache="#{applicationScope.Constante.PARAM_T_TIPOCOMERCIOEXTERIOR}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
				    	<rich:column style="width:148px;text-align: right">
				    		<h:outputText value="Tipo de Persona : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:selectOneMenu value="#{perJuridicaController.perJuridica.intTipoPersonaCod}"
				    			style="width: 150px" 
				    			disabled="#{not empty perJuridicaController.perJuridica.intTipoPersonaCod}">
					      		<tumih:selectItems var="sel"
					      			cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
				    	</rich:column>
		    		</h:panelGrid>
		    		
				    <h:panelGrid columns="6">				    	
				    	<rich:column width="125">
				    		<h:outputText value="Roles Actuales"></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:outputText value=" : "></h:outputText>
				    	</rich:column>
				    	<rich:column>
				    		<h:inputText value="#{perJuridicaController.strRoles}" disabled="true" size="38"/>
				    	</rich:column>
		    	</h:panelGrid>
		    					    
				    <rich:spacer height="15px"/>
				    
				     <h:panelGroup>
				     	<h:panelGrid columns="6">
							<rich:column style="width:137px">
						    	<h:outputText value="Tipo de Proveedor"/>
						    </rich:column>
						    <rich:column style="width:215px">
						    	<a4j:commandButton
						    		value="Seleccione"
						    		action="#{proveedorController.abrirPopUpTipoProveedor}"
				    				oncomplete="Richfaces.showModalPanel('pAgregarProveedorDetalle');"
				    				reRender="pAgregarProveedorDetalle"
				    				styleClass="btnEstilos"
				    				disabled="#{proveedorController.deshabilitarNuevo}"
				    				style="width:213px">
			          	 		</a4j:commandButton>
						    </rich:column>				     	
				     	</h:panelGrid>
				     	
				     	<h:panelGrid id="panelTipoProveedor" style="vertical-align: top">
				     		<rich:column width="443" style="vertical-align: top">
					    		<rich:dataTable
					    			id="tablaTipoProveedor"
					    			var="item"
					    			rowKeyVar="rowKey" 
					    			sortMode="single" 
					    			width="443px"
				    				value="#{proveedorController.listaProveedorDetalle}"
				    				rows="5">
					                <rich:column width="30px" style="text-align: center">
					                	<h:outputText value="#{rowKey + 1}"/>
					                </rich:column>
					               	<rich:column width="326">					                	
					                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPROVEEDOR}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intParaTipoProveedor}"/>
					           		</rich:column>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tablaTipoProveedor" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>				    		
				    		</rich:column>
				    	</h:panelGrid>
				     </h:panelGroup>
			    </h:panelGroup>			    


 				<rich:spacer height="15px"/>
 
				<h:panelGrid columns="2" style="vertical-align: top">
					<rich:column style="vertical-align: top">
							<h:panelGrid columns="6" style="vertical-align: top">
				    			<rich:column style="width:137px">
						    		<h:outputText value="Actividad Económica"></h:outputText>
						    	</rich:column>
						    	<rich:column style="width:215px">
						    		<a4j:commandButton id="btnActividadEconomica" 
						    			value="Seleccione" 
						    			actionListener="#{actEconomicaController.showActiEcoJuridicaProveedor}"
				    					oncomplete="Richfaces.showModalPanel('pAgregarActividadEconomica');"
				    					reRender="pAgregarActividadEconomica"
				    					styleClass="btnEstilos"
				    					disabled="#{proveedorController.deshabilitarNuevo}"
				    					style="width:213px">
			          	 			</a4j:commandButton>
						    	</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid style="vertical-align: top">
								<rich:dataTable id="tbActividadEconomica" 
					    			var="item" 
					    			rowKeyVar="rowKey" 
					    			sortMode="single" 
					    			width="443px" 
				    				value="#{actEconomicaController.listActEconomicaProveedor}" 
				    				rows="5">
				    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					                <rich:column width="30px" style="text-align: center">
					                	<h:outputText value="#{rowKey + 1}"></h:outputText>
					                </rich:column>
					               	<rich:column width="326">
					                	<f:facet name="header">
					                    	<h:outputText value="Actividad Económica"></h:outputText>
					                  	</f:facet>
					                    <h:outputText value="#{item.tabla.strDescripcion}"/>
					           		</rich:column>
					           		</rich:columnGroup>
					                <f:facet name="footer">   
					                    <rich:datascroller for="tbActividadEconomica" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
					</rich:column>
					
					<rich:column style="vertical-align: top">
							<h:panelGrid columns="6" style="vertical-align: top">
				    			<rich:column style="width:133px;text-align: left">
						    		<h:outputText value="Tipo de Comprobantes"></h:outputText>
						    	</rich:column>
						    	<rich:column>
						    		<a4j:commandButton id="btnTipoComprobante" 
						    			value="Seleccione" 
						    			actionListener="#{tipoComprobanteController.showTipoComprobJuridicaProveedor}"
				    					oncomplete="Richfaces.showModalPanel('pAgregarTipoComprobante')" 
				    					reRender="pAgregarTipoComprobante" 
				    					styleClass="btnEstilos"
				    					disabled="#{proveedorController.deshabilitarNuevo}"
				    					style="width:165px">
			          	 			</a4j:commandButton>
						    	</rich:column>
				    		</h:panelGrid>
				    		<h:panelGrid style="vertical-align: top">
								<rich:dataTable id="tbTipoComprobante" 
					    			var="item" 
					    			rowKeyVar="rowKey"
					    			sortMode="single"
					    			width="460px"
				    				value="#{tipoComprobanteController.listComprobanteProveedor}" 
				    				rows="5">
				    				<rich:columnGroup rendered="#{(item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)}">
				    				<rich:column style="text-align: center" width="30px">
					                        <h:outputText value="#{rowKey + 1}"/>
					            	</rich:column>
					                <rich:column width="430">
					                	 <f:facet name="header">
					                     	<h:outputText value="Tipo Comprobante"/>
										</f:facet>
					                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intTipoComprobanteCod}"/>
					               	</rich:column>
					               	</rich:columnGroup>
				                    <f:facet name="footer">
					                    <rich:datascroller for="tbTipoComprobante" maxPages="5"/>   
					                </f:facet> 
				                </rich:dataTable>
				    		</h:panelGrid>
					</rich:column>
				</h:panelGrid>

				<rich:spacer height="15px"/>
			    
		    	<a4j:outputPanel id="opRepLegal">
				    <h:panelGrid columns="2" style="vertical-align: top">
				    	<rich:column id="rpRepLegal" style="vertical-align: top">
								<h:panelGrid columns="4" style="vertical-align: top">
					    			<rich:column style="width:137px">
					    				<h:outputText value="Representante Legal"/>
					    			</rich:column>
					    			<rich:column style="width:215px">
					    				<a4j:commandButton value="Agregar" 
					    					actionListener="#{naturalController.nuevoRepLegal}"
					    					disabled="#{proveedorController.deshabilitarNuevo}"
					    					styleClass="btnEstilos1"
					    					style="width:213px"
					    					oncomplete="Richfaces.showModalPanel('mpRepLegal')"
					    					reRender="frmRepLegal">
				          	 			</a4j:commandButton>
					    			</rich:column>
					    		</h:panelGrid>
					    		<h:panelGrid id="pgRepLegal" style="vertical-align: top">
					    			<rich:dataTable value="#{naturalController.beanListRepLegal}" 
					    				id="tbRepreLegal" 
					    				rows="5" 
					    				styleClass="dataTable1"
			                            width="443px" 
			                            var="item" 
			                            rowKeyVar="rowKey" 
			                            sortMode="single">
			                            <rich:column width="343">
							            	<h:outputText value="#{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno} - DNI:#{item.documento.strNumeroIdentidad}"/>
							            </rich:column>
							            <rich:column width="50">
							            	<a4j:commandLink value="Ver"
							                	actionListener="#{naturalController.verRepLegal}"
							                    reRender="frmRepLegal"
							                    disabled="#{proveedorController.deshabilitarNuevo}"
							                    oncomplete="Richfaces.showModalPanel('mpRepLegal')">
							                    <f:param name="rowKeyRepLegal" value="#{rowKey}"></f:param>
							                </a4j:commandLink>
							        	</rich:column>
							            <rich:column width="50">
							            	<a4j:commandLink value="Quitar" 
							                	actionListener="#{naturalController.quitarRepLegal}" 
		                     					disabled="#{proveedorController.deshabilitarNuevo}"
		                     					reRender="tbRepreLegal">
		                     					<f:param name="rowKeyRepLegal" value="#{rowKey}"></f:param>
		                     				</a4j:commandLink>
							          	</rich:column>
						                <f:facet name="footer">   
						                    <rich:datascroller for="tbRepreLegal" maxPages="5"/>   
						                </f:facet> 
					                </rich:dataTable>
					    		</h:panelGrid>			    		
				    	</rich:column>
				    	<rich:column style="vertical-align: top">
				    			<h:panelGrid id="pAgregarComunicacion" columns="4" style="vertical-align: top">
					    			<rich:column style="width:133px;text-align: left">
					    				<h:outputText value="Comunicaciones"/>
					    			</rich:column>
					    			<rich:column>
					    				<a4j:commandButton id="btnPerJuriComunicacion" 
					    					value="Agregar"
					    					actionListener="#{comunicacionController.showComuniPerProveedor}"  
					    					reRender="frmComunicacion" 
					    					oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')" 
					    					disabled="#{proveedorController.deshabilitarNuevo}"
					    					style="width:165px"
					    					styleClass="btnEstilos1">				    					
				          	 			</a4j:commandButton>
					    			</rich:column>
					    		</h:panelGrid>
					    		<h:panelGrid id="pgListComunicacion" style="vertical-align: top">
					    			<rich:dataTable id="tbComunicaciones" 
					    				value="#{comunicacionController.beanListComuniProveedor}" 
					    				rows="5" 
					    				var="item"
					    				width="410px"
					    				rowKeyVar="rowKey" 
					    				sortMode="single" 
					    				styleClass="dataTable1"> 
					    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					    					<rich:column width="310">
							                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
							                </rich:column>
							                <rich:column width="50">
							                    <a4j:commandLink value="Ver"
							                    	actionListener="#{comunicacionController.viewComunicacionProveedor}"
							                    	reRender="frmComunicacion"
							                    	disabled="#{proveedorController.deshabilitarNuevo}"
							                    	oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')">						                    	
							                    	<f:param name="rowKeyComunicacionProveedor" value="#{rowKey}" />
							                    </a4j:commandLink>
							                </rich:column>
							                <rich:column width="50">
							                    <a4j:commandLink value="Quitar" 
							                    	actionListener="#{comunicacionController.quitarComunicacionProveedor}" 
		                     						disabled="#{proveedorController.deshabilitarNuevo}"
		                     						reRender="tbComunicaciones">
		                     						<f:param name="rowKeyComunicacionProveedor" value="#{rowKey}" />
		                     					</a4j:commandLink>
							                </rich:column>
							           	</rich:columnGroup>
						                <f:facet name="footer">   
						                    <rich:datascroller for="tbComunicaciones" maxPages="5"/>   
						                </f:facet> 
					                </rich:dataTable>
					    		</h:panelGrid>
				    	</rich:column>
				    </h:panelGrid>
				</a4j:outputPanel>
			    	
			    <rich:spacer height="15px"/>
			    <a4j:outputPanel id="opCuentaBancaria">
				    <h:panelGrid columns="2" style="vertical-align: top">
				    	<rich:column>
				    			<h:panelGrid columns="2" style="vertical-align: top">
					    			<rich:column style="width:137px">
					    				<h:outputText value="Direcciones"></h:outputText>
					    			</rich:column>
					    			<rich:column style="width:215px">
					    				<a4j:commandButton id="btnPerJuriDireccion" value="Agregar" 
					    					actionListener="#{domicilioController.showDomicilioJuriProveedor}"
					    					styleClass="btnEstilos1" 
					    					reRender="pgFormDomicilio,frmDomicilio" 
					    					disabled="#{proveedorController.deshabilitarNuevo}"
					    					style="width:213px"
					    					oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
				          	 			</a4j:commandButton>
					    			</rich:column>
					    		</h:panelGrid>
					    		<h:panelGrid id="pgListDomicilio" style="vertical-align: top">
					    			<rich:dataTable id="tbDirecciones" 
					    				value="#{domicilioController.beanListDirecProveedor}"
					    				rows="5" 
					    				var="item"
					    				width="443px" 
					    				rowKeyVar="rowKey" 
					    				sortMode="single" 
					    				styleClass="dataTable1">
					    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
					    					<rich:column width="343">
							                    <h:outputText value="#{item.strDireccion}"/>
							                </rich:column>
							                <rich:column width="50">
							                    <a4j:commandLink value="Ver" 
							                    	actionListener="#{domicilioController.viewDomicilioProveedor}"
							                    	reRender="pAgregarDomicilio" 
							                    	disabled="#{proveedorController.deshabilitarNuevo}"
							                    	oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
							                    	<f:param name="rowKey" value="#{rowKey}" />
							                    </a4j:commandLink>
							                </rich:column>
							                <rich:column width="50">
							                    <a4j:commandLink value="Quitar"
							                    	disabled="#{proveedorController.deshabilitarNuevo}"
							                    	actionListener="#{domicilioController.quitarDomicilioProveedor}" 
		                     						reRender="tbDirecciones">
		                     						<f:param name="rowKey" value="#{rowKey}"></f:param>
		                     					</a4j:commandLink>
							                </rich:column>
							            </rich:columnGroup>
						                <f:facet name="footer">   
						                    <rich:datascroller for="tbDirecciones" maxPages="5"/>   
						                </f:facet> 
					                </rich:dataTable>
					    		</h:panelGrid>
				    	</rich:column>
			    	
				    	<rich:column style="vertical-align: top">
				    		<h:panelGrid columns="4" style="vertical-align: top">
					    			<rich:column style="width:133px;text-align: left">
					    				<h:outputText value="Cuenta Bancaria"></h:outputText>
					    			</rich:column>
					    			<rich:column style="border:none">
					    				<a4j:commandButton value="Agregar" 
					    					actionListener="#{ctaBancariaController.showCtaBancariaPerProveedor}"
					    					reRender="mpCuentaBancaria"
					    					oncomplete="Richfaces.showModalPanel('mpCuentaBancaria')"
					    					disabled="#{proveedorController.deshabilitarNuevo}"
					    					style="width:165px"
					    					styleClass="btnEstilos1">
				          	 			</a4j:commandButton>
					    			</rich:column>
					    		</h:panelGrid>
					    		<h:panelGrid style="vertical-align: top">
					    		<rich:dataTable value="#{ctaBancariaController.listCtaBancariaProveedor}" 
					    			id="tbCuentaBancaria" 
					    			rows="5" 
					    			styleClass="dataTable1"
			                        var="item"
			                        width="410px"
			                        rowKeyVar="rowKey" 
			                        sortMode="single">
			                        <rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
			                        <rich:column width="310">
			                        	<!-- Autor: jchavez / Tarea: Modificacion / Fecha: 01.10.2014 -->
							        	<h:outputText value="#{item.strEtiqueta}"></h:outputText>
							        	<!-- Fin jchavez - 01.10.2014 -->
							        </rich:column>
							        <rich:column width="50">
							         	<a4j:commandLink value="Ver" 
							         		actionListener="#{ctaBancariaController.verCtaBancariaProveedor}"
							            	reRender="frmCtaBancaria"
							            	disabled="#{proveedorController.deshabilitarNuevo}"
							            	oncomplete="Richfaces.showModalPanel('mpCuentaBancaria')">
							                <f:param name="rowKey" value="#{rowKey}"/>
							           	</a4j:commandLink>
							   		</rich:column>
							        <rich:column width="50">
							  			<a4j:commandLink value="Quitar"
							  				actionListener="#{ctaBancariaController.quitarCtaBancariaProveedor}"
							  				disabled="#{proveedorController.deshabilitarNuevo}" 
		                     				reRender="tbCuentaBancaria">
		                     				<f:param name="rowKey" value="#{rowKey}"/>
		                     			</a4j:commandLink>
							      	</rich:column>
							      	</rich:columnGroup>
						            <f:facet name="footer">   
						            	<rich:datascroller for="tbCuentaBancaria" maxPages="5"/>   
						            </f:facet> 
					       		</rich:dataTable>
					       	</h:panelGrid>
				    	</rich:column>
			    	</h:panelGrid>
			    </a4j:outputPanel>
			    
			    <rich:spacer height="15px"/>
				<a4j:outputPanel id="opContactoNatu">
				    <h:panelGrid columns="2" style="vertical-align: top">
				    	<rich:column id="rpContactoNatu" style="vertical-align: top">
				    			<h:panelGrid columns="4" style="vertical-align: top">
					    			<rich:column style="width:137px">
					    				<h:outputText value="Contacto"/>
					    			</rich:column>
					    			<rich:column style="width:215px">
					    				<a4j:commandButton value="Agregar" 
					    					actionListener="#{naturalController.nuevoContactoNatu}"
					    					styleClass="btnEstilos1" 
					    					oncomplete="Richfaces.showModalPanel('mpContactoNatu')"
					    					disabled="#{proveedorController.deshabilitarNuevo}"
					    					style="width:213px"
					    					reRender="frmContactoNatu">
				          	 			</a4j:commandButton>
					    			</rich:column>
					    		</h:panelGrid>
					    		<h:panelGrid id="pgContactoNatu" style="vertical-align: top">
					    			<rich:dataTable value="#{naturalController.beanListContactoNatu}" 
					    					id="tbContactoNatu" 
					    					rows="5"
					    					width="443px"
			                            	var="item" 
			                            	rowKeyVar="rowKey" 
			                            	sortMode="single">		                             		
						                	<rich:column width="343">
							                    <h:outputText value="#{item.natural.strNombreCompleto}"/>
							                </rich:column>
							                <rich:column width="50"> 
							                    <a4j:commandLink value="Ver"
							                    	actionListener="#{naturalController.verContactoNatu}"
							                    	reRender="frmContactoNatu"
							                    	disabled="#{proveedorController.deshabilitarNuevo}"
							                    	oncomplete="Richfaces.showModalPanel('mpContactoNatu')">
							                    	<f:param name="rowKeyContactoNatu" value="#{rowKey}"/>
							                    </a4j:commandLink>
							                </rich:column>
							                <rich:column width="50">
							                    <a4j:commandLink value="Quitar" 
							                    	actionListener="#{naturalController.quitarContactoNatu}"
							                    	disabled="#{proveedorController.deshabilitarNuevo}">
		                     						<f:param name="rowKeyContactoNatu" value="#{rowKey}"/>
		                     					</a4j:commandLink>
							                </rich:column>						            
						                <f:facet name="footer">   
						                    <rich:datascroller for="tbContactoNatu" maxPages="5"/>   
						                </f:facet> 
					                </rich:dataTable>
					    		</h:panelGrid>
				    	</rich:column>
				    	
				    </h:panelGrid>
			   	</a4j:outputPanel>

			    

			</h:panelGroup>			
			
		</rich:panel>
	</h:panelGroup>
</h:panelGroup>
</h:form>