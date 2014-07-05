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
            			action="#{accesoController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje,panelBotones"/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{accesoController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaAcceso,panelMensaje,contPanelInferior"
             			rendered="#{accesoController.mostrarBtnEliminar}"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>


<rich:modalPanel id="pSeleccionCuentaBancaria" width="620" height="440"
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
    <a4j:include viewId="/pages/param/popupacceso/seleccionCuentaBancaria.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pSeleccionFondoFijo" width="635" height="470"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Fondo Fijo"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionFondoFijo" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popupacceso/seleccionFondoFijo.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pSeleccionPersona" width="625" height="260"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Persona"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionPersona" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/param/popupacceso/seleccionPersona.jsp"/>
</rich:modalPanel>


<h:form id="frmAcceso">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="NIVELES DE ACCESOS Y MONTOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">
              	<rich:column width="70">
					<h:outputText value="Sucursal : "/>
			  	</rich:column>
			  	
			  	<rich:column width="200">
					<h:selectOneMenu
						style="width: 200px;"
						value="#{accesoController.accesoFiltro.intSucuIdSucursal}">
						<tumih:selectItems var="sel" 
							value="#{accesoController.listaSucursal}" 
							itemValue="#{sel.id.intIdSucursal}" 
							itemLabel="#{sel.juridica.strSiglas}"/>
					</h:selectOneMenu>					
			 	</rich:column>
			 	
			 	<rich:column width="10" style="text-align: left;">					
			  	</rich:column>
			  	
			 	<rich:column width="60" style="text-align: left;">
					<h:outputText value="Estado : "/>
			  	</rich:column>
			    
			    <rich:column width="200">
					<h:selectOneMenu
						style="width: 200px;"
						value="#{accesoController.accesoFiltro.intParaEstado}"
						disabled="#{accesoController.deshabilitarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</rich:column>
              	
              	<rich:column width="70" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaAcceso,panelMensaje"
                    	action="#{accesoController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaAcceso">
	        	<rich:extendedDataTable id="tblAcceso" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{accesoController.listaAcceso}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="930px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemAcceso}"/>
                	</rich:column>
                    <rich:column width="180" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strEtiquetaSucursal}"/>
                  	</rich:column>
                    <rich:column width="180" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sub Sucursal"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strEtiquetaSubsucursal}"/>
                  	</rich:column>                    
                    <rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Cuenta Bancaria"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.intCantidadCuentaBancaria}"/>
                  	</rich:column>
                  	<rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fondo Fijo"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.intCantidadFondoFijo}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstado}"/>
                  	</rich:column>
                    
                   	<f:facet name="footer">
						<rich:datascroller for="tblAcceso" maxPages="10"/>   
					</f:facet>
					
					<a4j:support event="onRowClick"  
						actionListener="#{accesoController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{accesoController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
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
			<h:outputText value="#{accesoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{accesoController.mostrarMensajeExito}"/>
			<h:outputText value="#{accesoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{accesoController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{accesoController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{accesoController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaAcceso"
			    	disabled="#{!accesoController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{accesoController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>	        	
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{accesoController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Código : "/>
			  	</rich:column>
			  	<rich:column width="200">
					<h:inputText size="35" value="#{accesoController.accesoNuevo.id.intItemAcceso}" 
						readonly="true" style="background-color: #BFBFBF;"/>						
			 	</rich:column>
			    <rich:column width="30">
				</rich:column>
				<rich:column width="70" style="text-align: left;">
					<h:outputText value="Estado : "/>
			  	</rich:column>
			    <rich:column width="200">
					<h:selectOneMenu
						style="width: 200px;"
						value="#{accesoController.accesoNuevo.intParaEstado}"
						disabled="#{accesoController.deshabilitarNuevo  || accesoController.registrarNuevo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8" id="panelSucursal">
				<rich:column width="120">
					<h:outputText value="Sucursal : "/>
			  	</rich:column>
			  	<rich:column width="200">
					<h:selectOneMenu
						style="width: 200px;"
						value="#{accesoController.accesoNuevo.intSucuIdSucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" 
							value="#{accesoController.listaSucursal}" 
							itemValue="#{sel.id.intIdSucursal}" 
							itemLabel="#{sel.juridica.strSiglas}"/>
						<a4j:support event="onchange" action="#{accesoController.seleccionarSucursal}" 
							reRender="panelSucursal,panelFondoFijo"  />
					</h:selectOneMenu>					
			 	</rich:column>
			    <rich:column width="30">
				</rich:column>
				<rich:column width="60" style="text-align: left;">
					<h:outputText value="Subsucursal : "/>
			  	</rich:column>
			    <rich:column width="200">
					<h:selectOneMenu 
						style="width: 200px;"
						value="#{accesoController.accesoNuevo.intSudeIdSubsucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>	
						<tumih:selectItems var="sel"
							value="#{accesoController.listaSubSucursal}" 
							itemValue="#{sel.id.intIdSubSucursal}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="8">
				<rich:column width="120">
					<h:outputText value="Observación : "/>
			  	</rich:column>
			  	<rich:column width="500">
					<h:inputTextarea cols="101" disabled="#{accesoController.deshabilitarNuevo}" 
							rows="3" value="#{accesoController.accesoNuevo.strObservacion}"/>
                </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<rich:column width="120">
					<h:outputText value="Cuenta Bancaria : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{accesoController.abrirPopUpCuentaBancaria}"
				        disabled="#{accesoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        reRender="pSeleccionCuentaBancaria"
				        oncomplete="Richfaces.showModalPanel('pSeleccionCuentaBancaria')"
				        style="width:150px">				    		                    	
			   		</a4j:commandButton>						
		        </rich:column>
			</h:panelGrid>
		
			<h:panelGrid columns="2" id="panelListaCuentaBancaria">
				<rich:column width="120">
		        </rich:column>
		        <rich:column width="720">
					<rich:dataTable
						sortMode="single" 
					    var="item" 
					    value="#{accesoController.listaAccesoDetalleCuentaBancaria}"  
						rowKeyVar="rowKey" 
						width="750px" 
						rows="#{fn:length(accesoController.listaAccesoDetalleCuentaBancaria)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItemAccesoDetalle}"/>
						</rich:column>
						<rich:column width="150px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Banco"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intBancoCod}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Tipo de Cuenta"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CUENTABANCARIA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.bancoCuenta.cuentaBancaria.intTipoCuentaCod}"/>
						</rich:column>
						<rich:column width="200px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Número de Cuenta"/>
							</f:facet>
							<h:outputText value="#{item.bancoCuenta.cuentaBancaria.strNroCuentaBancaria}"/>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Moneda"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.bancoCuenta.cuentaBancaria.intMonedaCod}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Estado"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstado}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{accesoController.editarPopUpCuentaBancaria}"
								reRender="pSeleccionCuentaBancaria"
								oncomplete="Richfaces.showModalPanel('pSeleccionCuentaBancaria')"
								disabled="#{accesoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{accesoController.eliminarCuentaBancaria}"
								reRender="panelListaCuentaBancaria"
								disabled="#{accesoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			
			<h:panelGroup id="panelFondoFijo">
			
			<h:panelGrid columns="2">
				<rich:column width="120">
					<h:outputText value="Fondo Fijo : "/>
		        </rich:column>
		        <rich:column width="230">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{accesoController.abrirPopUpFondoFijo}"
				        disabled="#{accesoController.deshabilitarNuevo}"		                		
				        value="Agregar"
				        disabled="#{accesoController.deshabilitarNuevo}"
				        reRender="pSeleccionFondoFijo"
				        oncomplete="Richfaces.showModalPanel('pSeleccionFondoFijo')"
				        style="width:150px">                  	
			   		</a4j:commandButton>						
		        </rich:column>
			</h:panelGrid>
		
			<h:panelGrid columns="2" id="panelListaFondoFijo">
				<rich:column width="120">
		        </rich:column>
		        <rich:column width="750">
					<rich:dataTable
						sortMode="single" 
					    var="item" 
					    value="#{accesoController.listaAccesoDetalleFondoFijo}"  
						rowKeyVar="rowKey" 
						width="750px" 
						rows="#{fn:length(accesoController.listaAccesoDetalleFondoFijo)}">					
						
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Código"/>
							</f:facet>
							<h:outputText value="#{item.id.intItemAccesoDetalle}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Nombre de Fondo"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.fondoFijo.intTipoFondoFijo}"/>
						</rich:column>
						<rich:column width="200px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Responsable"/>
							</f:facet>
							<rich:dataTable
								sortMode="single" 
							    var="res"
							    value="#{item.listaAccesoDetalleRes}">
							    <rich:column width="200px" style="text-align: center">
									<h:outputText value="#{res.persona.natural.strNombres} "/>
									<h:outputText value="#{res.persona.natural.strApellidoPaterno} "/>
									<h:outputText value="#{res.persona.natural.strApellidoMaterno}"/>
								</rich:column>
							</rich:dataTable>
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Monto"/>
							</f:facet>
							<h:outputText value="#{item.bdMontoFondo}"/>
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Máximo de Giro"/>
							</f:facet>
							<h:outputText value="#{item.bdMontoMaximo}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Moneda"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.fondoFijo.intMonedaCod}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Estado"/>
							</f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstado}"/>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Editar"/>
							</f:facet>
							<a4j:commandLink
								value="Editar"
				            	actionListener="#{accesoController.editarPopUpFondoFijo}"
								reRender="pSeleccionFondoFijo"
								oncomplete="Richfaces.showModalPanel('pSeleccionFondoFijo')"
								disabled="#{accesoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								value="Eliminar"
				            	actionListener="#{accesoController.eliminarFondoFijo}"
								reRender="panelListaFondoFijo"
								disabled="#{accesoController.deshabilitarNuevo}">
								<f:attribute name="item" value="#{item}"/>							
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </rich:column>
			</h:panelGrid>
			
			</h:panelGroup>
			
		</rich:panel>
				
	</h:panelGroup>	

</h:panelGroup>		
</h:form>