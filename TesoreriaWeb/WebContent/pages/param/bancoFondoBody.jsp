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
	<!-- Prototipo :  BancoFondo-->			
	<!-- Fecha     :                	-->

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
            			action="#{bancoFondoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje,panelBotones"/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{bancoFondoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaBancoFondo,panelMensaje,contPanelInferior"
             			rendered="#{bancoFondoController.mostrarBtnEliminar}"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionCuentaBancaria" width="535" height="325"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Cuenta Bancaria"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionCuentaBancaria" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/param/popup/seleccionCuentaBancaria.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionPlanCuenta" width="740" height="300"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Cuenta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionPlanCuenta" operation="hide" event="onclick"/>
           	</h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionPlanCuenta.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionCuentaCheques" width="570" height="480"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Cheques de Cuenta Bancaria"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionCuentaCheques" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/param/popup/seleccionCuentaCheques.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionCuentaContable" width="470" height="200"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Cuentas Contables"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionCuentaContable" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionFondoCuenta.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionDocumentoCancelar" width="470" height="260"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Documento a Cancelar"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionDocumentoCancelar" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionFondoDocumento.jsp"/>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionSobregiro" width="470" height="230"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Sobregiro"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionSobregiro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popup/seleccionFondoSobregiro.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="mpAgregarPerJuri" width="935" height="550"
	 resizeable="false" style="background-color:#DEEBF5">
       <f:facet name="header">
           <h:panelGrid>
             <rich:column style="border: none;">
               <h:outputText value="Persona Jurídica"></h:outputText>
             </rich:column>
           </h:panelGrid>
       </f:facet>
       <f:facet name="controls">
           <h:panelGroup>
              <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidePanelPerJuri"/>
              <rich:componentControl for="mpAgregarPerJuri" attachTo="hidePanelPerJuri" operation="hide" event="onclick"/>
          </h:panelGroup>
       </f:facet> 
       
       <a4j:include id="inclPerJuri" viewId="/pages/popup/juridicaBody.jsp"/>
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<a4j:region>
	<a4j:jsFunction name="setIdCallingFormBancos" 
		actionListener="#{perJuridicaController.setIdCallingFormBancos}"
		oncomplete="Richfaces.showModalPanel('mpAgregarPerJuri')">
		<f:param name="pIdFormBancos"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form id="frmBancoFondo" style="text-align: left;">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="BANCOS Y FONDOS FIJOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
    		
    		<h:panelGrid columns="11" id="panelBusqueda">
         		<rich:column width="40" style="text-align: center;">
         			<h:outputText value="Tipo : "/>
         		</rich:column>
         		<rich:column width="150">
                	<h:selectOneMenu
						style="width: 150px;"
						value="#{bancoFondoController.bancoFondoFiltro.intTipoBancoFondoFiltro}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOFONDOFIJO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>	
						<a4j:support event="onchange" reRender="panelBusqueda"  />
					</h:selectOneMenu>
              	</rich:column>     		
              	<rich:column width="60" style="text-align: center;">         			
         			<h:panelGroup rendered="#{bancoFondoController.bancoFondoFiltro.intTipoBancoFondoFiltro == applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO}">
         				<h:outputText value="Moneda"/>
         			</h:panelGroup>
         		</rich:column>
         		<rich:column width="150">
                	<h:panelGroup rendered="#{bancoFondoController.bancoFondoFiltro.intTipoBancoFondoFiltro == applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO}">
         				<h:selectOneMenu
							style="width: 150px;"
							value="#{bancoFondoController.bancoFondoFiltro.intMonedaCod}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>	
						</h:selectOneMenu>
         			</h:panelGroup>         			
              	</rich:column>       		
              	<rich:column width="60" style="text-align: center;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column width="150">
                	<h:selectOneMenu
						style="width: 150px;"
						value="#{bancoFondoController.bancoFondoFiltro.intEstadoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		action="#{bancoFondoController.buscar}"
                		value="Buscar" reRender="panelTablaBancoFondo,panelMensaje"
                    	style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaBancoFondo">
	        	<rich:extendedDataTable id="tblBancoFondo" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{bancoFondoController.listaBancoFondo}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="900px" 
					height="165px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="70px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItembancofondo}"/>
                	</rich:column>
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOFONDOFIJO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intTipoBancoFondoFiltro}"/>
                  	</rich:column>                    
                    <rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Nombre"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{item.intTipoBancoFondoFiltro==applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_BANCO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intBancoCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intTipoBancoFondoFiltro==applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intTipoFondoFijo}"/>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{item.intTipoBancoFondoFiltro==applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intMonedaCod}"/>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="140" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Cuenta Bancaria"/>
                        </f:facet>
                        <h:panelGroup rendered="#{item.intTipoBancoFondoFiltro==applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_BANCO}">
                      		<h:outputText value="#{item.intCantidadBancoCuenta}"/>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Sobregiro"/>
                        </f:facet>
                        <h:panelGroup rendered="#{item.intTipoBancoFondoFiltro==applicationScope.Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO}">
                      		<h:panelGroup rendered="#{item.poseeSobregiro}">
                      			<h:outputText value="Si"/>
                      		</h:panelGroup>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intEstadoCod}"/>
                    </rich:column>
                    <rich:column width="130" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                        </f:facet>
                        <h:outputText value="#{item.tsFecharegistro}">
                        	 <f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" />
                        </h:outputText>
                    </rich:column>
                   	<f:facet name="footer">
						<rich:datascroller for="tblBancoFondo" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{bancoFondoController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{bancoFondoController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
    		<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
	<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center;"
		styleClass="rich-tabcell-noborder">
		<h:outputText value="#{bancoFondoController.mensajeOperacion}" 
			styleClass="msgInfo"
			style="font-weight:bold"
			rendered="#{bancoFondoController.mostrarMensajeExito}"/>
		<h:outputText value="#{bancoFondoController.mensajeOperacion}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{bancoFondoController.mostrarMensajeError}"/>	
	</h:panelGroup>
					
	<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder" id="panelBotones">
		<h:panelGrid columns="4">
			<a4j:commandButton value="Banco" styleClass="btnEstilos" style="width:90px" 
				actionListener="#{bancoFondoController.mostrarPanelBanco}" 
				reRender="contPanelInferior,panelMensaje,panelBotones"
				disabled="#{!bancoFondoController.habilitarBotonMostrarBanco}"/>                 
		    <a4j:commandButton value="Fondo Fijo" styleClass="btnEstilos" style="width:90px" 
				actionListener="#{bancoFondoController.mostrarPanelFondoFijo}" 
				reRender="contPanelInferior,panelMensaje,panelBotones"
				disabled="#{!bancoFondoController.habilitarBotonMostrarFondo}"/>                     
		    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
		    	action="#{bancoFondoController.grabar}" 
		    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaBancoFondo"
		    	disabled="#{!bancoFondoController.habilitarGrabar}"/>       												                 
		    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
		    	actionListener="#{bancoFondoController.cancelar}" 
		    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
		</h:panelGrid>
	</h:panelGroup>
	
	
	<h:panelGroup id="contPanelInferior">
		
		<rich:panel  rendered="#{bancoFondoController.habilitarBanco}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
	 		
	 		<h:panelGrid columns="8">
				<rich:column width="130">
					<h:outputText value="Banco : "/>
		        </rich:column>
		        <rich:column width="130">
					<h:selectOneMenu
						style="width: 150px;"
						value="#{bancoFondoController.bancoNuevo.intBancoCod}"
						disabled="#{bancoFondoController.deshabilitarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>						
		        </rich:column>
		        <rich:column width="20">
				</rich:column>
				<rich:column width="60" style="text-align: left;">
					<h:outputText value="Estado : "/>
		        </rich:column>
		        <rich:column width="120">
					<h:selectOneMenu
						style="width: 120px;"
						value="#{bancoFondoController.bancoNuevo.intEstadoCod}"
						disabled="#{bancoFondoController.deshabilitarNuevo  || bancoFondoController.registrarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
		        </rich:column>
		        <rich:column width="20">
				</rich:column>
		        <rich:column width="60" style="text-align: left;">
					<h:outputText value="Código : "/>
		        </rich:column>
		        <rich:column width="150">
					<h:inputText size="30" value="#{bancoFondoController.bancoNuevo.id.intItembancofondo}"
						readonly="true" style="background-color: #BFBFBF;"/>
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="5">
				<rich:column width="130">
					<h:outputText value="Persona Jurídica : "/>
		        </rich:column>
		        <rich:column width="400">
					<h:inputText size="70" value="#{bancoFondoController.bancoNuevo.personaEmpresa.persona.juridica.strRazonSocial}"
						readonly="true" style="background-color: #BFBFBF;"/>											
		        </rich:column>
		        <rich:column width="140">
		        	<a4j:commandButton styleClass="btnEstilos" value="Agregar" style="width:70px"
		        					onclick="setIdCallingFormBancos(document.getElementById('frmBancoFondo').id)"/>
                    <a4j:commandButton styleClass="btnEstilos"
                		value="Editar" 
                    	style="width:70px"/>
				</rich:column>		        
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Abreviatura : "/>
		        </rich:column>
		        <rich:column width="400">
					<h:inputText size="70" value="#{bancoFondoController.bancoNuevo.strAbreviatura}"/>
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Cuenta Bancaria : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{bancoFondoController.abrirPopUpCuentaBancaria}"
				        disabled="#{bancoFondoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionCuentaBancaria"
				        oncomplete="Richfaces.showModalPanel('pSeleccionCuentaBancaria')"
				        style="width:150px">				    		                    	
			   		</a4j:commandButton>										
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelListaBancoCuenta">
				<rich:column width="130">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{bancoFondoController.listaBancoCuenta}"  
						rowKeyVar="rowKey" 
						width="700px" 
						rows="#{fn:length(bancoFondoController.listaBancoCuenta)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItembancocuenta}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nombre de Cuenta"/>
							</f:facet>
							<h:outputText value="#{item.strNombrecuenta}"/>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Tipo de Cuenta"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CUENTABANCARIA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.cuentaBancaria.intTipoCuentaCod}"/>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Tipo de Moneda"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.cuentaBancaria.intMonedaCod}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Número de Cuenta"/>
							</f:facet>
							<h:outputText value="#{item.cuentaBancaria.strNroCuentaBancaria}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Cuenta Contable"/>
							</f:facet>
							<h:outputText value="#{item.strNumerocuenta}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{bancoFondoController.editarPopUpCuentaBancaria}"
								reRender="pSeleccionCuentaBancaria"
								oncomplete="Richfaces.showModalPanel('pSeleccionCuentaBancaria')"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{bancoFondoController.eliminarCuentaBancaria}"
								reRender="panelListaBancoCuenta,panelListaBancoCuentaCheques"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Control Chequera : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{bancoFondoController.abrirPopUpCuentaCheques}"
				        disabled="#{bancoFondoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionCuentaCheques"
				        oncomplete="Richfaces.showModalPanel('pSeleccionCuentaCheques')"
				        style="width:150px">				    		                    	
			   		</a4j:commandButton>											
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelListaBancoCuentaCheques">
				<rich:column width="130">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{bancoFondoController.listaBancoCuentaCheque}"  
						rowKeyVar="rowKey" 
						width="700px" 
						rows="#{fn:length(bancoFondoController.listaBancoCuentaCheque)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItembancuencheque}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Cuenta Bancaria"/>
							</f:facet>
							<h:outputText value="#{item.bancoCuenta.strNombrecuenta}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Serie"/>
							</f:facet>
							<h:outputText value="#{item.strSerie}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nro de Control"/>
							</f:facet>
							<h:outputText value="#{item.strControl}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nro Desde"/>
							</f:facet>
							<h:outputText value="#{item.intNumeroinicio}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nro Hasta"/>
							</f:facet>
							<h:outputText value="#{item.intNumerofin}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{bancoFondoController.editarPopUpCuentaBancariaCheques}"
								reRender="pSeleccionCuentaCheques"
								oncomplete="Richfaces.showModalPanel('pSeleccionCuentaCheques')"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{bancoFondoController.eliminarCuentaBancariaCheque}"
								reRender="panelListaBancoCuentaCheques"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
		
		</rich:panel>
		
		
		<rich:panel  rendered="#{bancoFondoController.habilitarFondoFijo}" style="border:1px solid #17356f;background-color:#DEEBF5;">
	 		

			
			<h:panelGrid columns="5">
			   <rich:column width="130" style="text-align: left;">
					<h:outputText value="Código : "/>
		        </rich:column>
		        <rich:column width="200">
					<h:inputText size="29" value="#{bancoFondoController.fondoNuevo.id.intItembancofondo}"
						readonly="true" style="background-color: #BFBFBF;"/>
		        </rich:column>
		        <rich:column width="50">
				</rich:column>
				<rich:column width="100" style="text-align: left;">
					<h:outputText value="Estado : "/>
		        </rich:column>
		        <rich:column width="120">
					<h:selectOneMenu
						style="width: 120px;"
						value="#{bancoFondoController.fondoNuevo.intEstadoCod}"
						disabled="#{bancoFondoController.deshabilitarNuevo  || bancoFondoController.registrarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
		        </rich:column>	        	
			</h:panelGrid>
			
			<h:panelGrid columns="5">
			    <rich:column width="130" style="text-align: left;">
					<h:outputText value="Nombre de Fondo : "/>
		        </rich:column>
		        <rich:column width="200">
					<h:selectOneMenu
						style="width: 170px;"
						value="#{bancoFondoController.fondoNuevo.intTipoFondoFijo}"
						disabled="#{bancoFondoController.deshabilitarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
		        </rich:column>
		        <rich:column width="50">
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Tipo de Moneda : "/>
		        </rich:column>
		       	<rich:column width="120">
					<h:selectOneMenu
						style="width: 120px;"
						value="#{bancoFondoController.fondoNuevo.intMonedaCod}"
						disabled="#{bancoFondoController.deshabilitarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
		        </rich:column>
			</h:panelGrid>
						
			<h:panelGrid columns="5">
			    <rich:column width="130" style="text-align: left;">
					<h:outputText value="Observación : "/>
		        </rich:column>
		        <rich:column width="350">
					<h:inputTextarea cols="94" disabled="#{bancoFondoController.deshabilitarNuevo}" 
								rows="3" value="#{bancoFondoController.fondoNuevo.strObservacion}"/>
		        </rich:column>		        		        	
			</h:panelGrid>
			
			<rich:spacer height="15px"/>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Cuenta Contable : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{bancoFondoController.abrirPopUpCuentaContable}"
				        disabled="#{bancoFondoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionCuentaContable"
				        oncomplete="Richfaces.showModalPanel('pSeleccionCuentaContable')"
				        style="width:170px">				    		                    	
			   		</a4j:commandButton>											
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelListaCuentaContable">
				<rich:column width="130">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{bancoFondoController.listaFondoDetalleCuenta}"  
						rowKeyVar="rowKey" 
						width="700px" 
						rows="#{fn:length(bancoFondoController.listaFondoDetalleCuenta)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItemfondodetalle}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Sucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSucursal}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="SubSucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSubsucursal}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código de Cuenta"/>
							</f:facet>
							<h:outputText value="#{item.strNumerocuenta}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nombre de Cuenta"/>							
							</f:facet>
							<h:outputText value="#{item.planCuenta.strDescripcion}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{bancoFondoController.editarPopUpCuentaContable}"
								reRender="pSeleccionCuentaContable"
								oncomplete="Richfaces.showModalPanel('pSeleccionCuentaContable')"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{bancoFondoController.eliminarCuentaContable}"
								reRender="panelListaCuentaContable"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			<rich:spacer height="15px"/>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Documentos a Cancelar : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{bancoFondoController.abrirPopUpDocumentoCancelar}"
				        disabled="#{bancoFondoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionDocumentoCancelar"
				        oncomplete="Richfaces.showModalPanel('pSeleccionDocumentoCancelar')"
				        style="width:170px">
			   		</a4j:commandButton>										
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelListaDocumentoCancelar">
				<rich:column width="130">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable
						sortMode="single" 
					    var="item"
					    value="#{bancoFondoController.listaFondoDetalleDocumento}"  
						rowKeyVar="rowKey"
						width="700px"
						rows="#{fn:length(bancoFondoController.listaFondoDetalleDocumento)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItemfondodetalle}"/>
						</rich:column>
						<rich:column width="120px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Sucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSucursal}"/>
						</rich:column>
						<rich:column width="90px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="SubSucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSubsucursal}"/>
						</rich:column>
						<rich:column width="120px" style="text-align: left">
							<f:facet name="header">
								<h:outputText value="Tipo de Documento"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intDocumentogeneralCod}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: left">
							<f:facet name="header">
								<h:outputText value="Tipo de Comprobante"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intTipocomprobanteCod}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Monto Max"/>
							</f:facet>
							<h:outputText value="#{item.bdMontomaxdocumento}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{bancoFondoController.editarPopUpDocumentoCancelar}"
								reRender="pSeleccionDocumentoCancelar"
								oncomplete="Richfaces.showModalPanel('pSeleccionDocumentoCancelar')"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{bancoFondoController.eliminarDocumentoCancelar}"
								reRender="panelListaDocumentoCancelar,panelListaSobregiro"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			<rich:spacer height="15px"/>
			
			<h:panelGrid columns="2">
				<rich:column width="130">
					<h:outputText value="Sobregiro : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{bancoFondoController.abrirPopUpSobregiro}"
				        disabled="#{bancoFondoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionSobregiro"
				        oncomplete="Richfaces.showModalPanel('pSeleccionSobregiro')"
				        style="width:170px">				    		                    	
			   		</a4j:commandButton>											
		        </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="panelListaSobregiro">
				<rich:column width="130">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{bancoFondoController.listaFondoDetalleSobregiro}"  
						rowKeyVar="rowKey" 
						width="700px" 
						rows="#{fn:length(bancoFondoController.listaFondoDetalleSobregiro)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItemfondodetalle}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Sucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSucursal}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="SubSucursal"/>
							</f:facet>
							<h:outputText value="#{item.strEtiquetaSubsucursal}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Monto Seguro"/>
							</f:facet>
							<h:outputText value="#{item.bdMontosobregiro}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Cuenta Contable"/>
							</f:facet>
							<h:outputText value="#{item.strNumerocuenta}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{bancoFondoController.editarPopUpSobregiro}"
								reRender="pSeleccionSobregiro"
								oncomplete="Richfaces.showModalPanel('pSeleccionSobregiro')"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{bancoFondoController.eliminarSobregiro}"
								reRender="panelListaSobregiro"
								disabled="#{bancoFondoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			
			
		</rich:panel>
		
	</h:panelGroup>
</h:panelGroup>				
</h:form>