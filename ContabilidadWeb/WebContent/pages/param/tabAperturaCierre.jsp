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
	<!-- Prototipo :  Aperuta Cierre Cuentas-->			
	<!-- Fecha     :                	-->



<h:form id="frmAperturaCierre">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="APERTURA Y CIERRE" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">
         		<rich:column width="60" style="text-align: center;">
         			<h:outputText value="Periodo : "/>
         		</rich:column>
         		<rich:column width="70">
                	<h:selectOneMenu
						style="width: 70px;"
						value="#{aperturaCierreController.cuentaCierreFiltro.id.intContPeriodoCierre}">
						<tumih:selectItems var="sel"
							value="#{aperturaCierreController.listaAnios}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
              	
              	<rich:column width="60" style="text-align: center;">
         			<h:outputText value="Operación : "/>
         		</rich:column>
         		<rich:column width="120">
                	<h:selectOneMenu
						style="width: 120px;"
						value="#{aperturaCierreController.cuentaCierreFiltro.id.intParaTipoCierre}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONANUAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
              	</rich:column>
              	
              	<rich:column width="100" style="text-align: center;">
         			<h:outputText value="Cuenta Contable : "/>
         		</rich:column>
         		<rich:column width="130">
                	<h:selectOneMenu
						style="width: 130px;"
						value="#{aperturaCierreController.intFiltroPlanCuenta}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="50">
              		<h:inputText size="20" value="#{aperturaCierreController.strCuentaContableFiltro}"/>
              	</rich:column>
              	
              	<rich:column width="50" style="text-align: center;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column width="90">
                	<h:selectOneMenu
						style="width: 90px;"
						value="#{aperturaCierreController.cuentaCierreFiltro.intParaEstado}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
              	</rich:column>
              	
              	<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaCierre,panelMensajeApertura"
                    	action="#{aperturaCierreController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaCierre">
	        	<rich:extendedDataTable id="tblCierre" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{aperturaCierreController.listaCuentaCierre}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="970px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="70px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intContCodigoCierre}" />
                	</rich:column>
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Periodo"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.id.intContPeriodoCierre}" />
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Operación"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONANUAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaTipoCierre}"/>
                  	</rich:column>                    
                    <rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Descripción"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strDescripcion}" />
                  	</rich:column>
                  	<rich:column width="150" style="text-align: left">
                    	<f:facet name="header">
                      		<h:outputText value="Cuenta Contable"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strContNumeroCuenta}" />
                  	</rich:column>
                  	<rich:column width="75" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                  	</rich:column>
                    <rich:column width="265" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Usuario y Fecha de Registro"/>
                        </f:facet>
                         <h:outputText value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno} "/>
                        <h:outputText value="#{item.tsFechaRegistro}">
                        	 <f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" />
                        </h:outputText>
                  	</rich:column>                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblCierre" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{aperturaCierreController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contpanelInferiorAperturaApertura,panelBotonesApertura"
						oncomplete="if(#{aperturaCierreController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistroApertura')}">
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

				
		<h:panelGroup id="panelMensajeApertura" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{aperturaCierreController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{aperturaCierreController.mostrarMensajeExito}"/>
			<h:outputText value="#{aperturaCierreController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{aperturaCierreController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesApertura">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{aperturaCierreController.habilitarPanelInferior}" 
					reRender="contpanelInferiorAperturaApertura,panelMensajeApertura,panelBotonesApertura" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{aperturaCierreController.grabar}" 
			    	reRender="contpanelInferiorAperturaApertura,panelMensajeApertura,panelBotonesApertura,panelTablaCierre"
			    	disabled="#{!aperturaCierreController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{aperturaCierreController.deshabilitarPanelInferior}" 
			    	reRender="contpanelInferiorAperturaApertura,panelMensajeApertura,panelBotonesApertura"/>      
			</h:panelGrid>
		</h:panelGroup>	        	
		
		          	
		<rich:panel id="contpanelInferiorAperturaApertura" style="border:0px;">
			<rich:panel id="panelInferiorApertura" style="border:1px solid #17356f;" rendered="#{aperturaCierreController.mostrarPanelInferior}">		          	 	
		    
		    	<h:panelGrid columns="5">
			    	<rich:column width="130">
						<h:outputText value="Código : "/>
		        	</rich:column>
		        	<rich:column width="130">
						<h:inputText size="21" value="#{aperturaCierreController.cuentaCierreNuevo.id.intContCodigoCierre}"
							readonly="true" style="background-color: #BFBFBF;"/>
		        	</rich:column>
		        	<rich:column width="100">
					</rich:column>
		        	<rich:column width="130" style="text-align: left;">
						<h:outputText value="Estado : "/>
		        	</rich:column>
		        	<rich:column width="150">
						<h:selectOneMenu
							style="width: 150px;"
							value="#{aperturaCierreController.cuentaCierreNuevo.intParaEstado}"
							disabled="#{aperturaCierreController.deshabilitarNuevo  || aperturaCierreController.registrarNuevo}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								/>
						</h:selectOneMenu>
		        	</rich:column>
		        </h:panelGrid>
		        
		        <rich:spacer height="4px"/>
		        
		        <h:panelGrid columns="5">
			    	<rich:column width="130">
						<h:outputText value="Período : "/>
		        	</rich:column>
		        	<rich:column width="130">
						<h:selectOneMenu
							style="width: 130px;"
							value="#{aperturaCierreController.cuentaCierreNuevo.id.intContPeriodoCierre}"
							disabled="#{aperturaCierreController.deshabilitarNuevo || !aperturaCierreController.registrarNuevo}">
							<tumih:selectItems var="sel"
								value="#{aperturaCierreController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		        	</rich:column>
		        	<rich:column width="100">
					</rich:column>
		        	<rich:column width="130" style="text-align: left;">
						<h:outputText value="Tipo de Operación : "/>
		        	</rich:column>
		        	<rich:column width="150">
						<h:selectOneMenu
							style="width: 150px;"
							value="#{aperturaCierreController.cuentaCierreNuevo.id.intParaTipoCierre}"
							disabled="#{aperturaCierreController.deshabilitarNuevo || !aperturaCierreController.registrarNuevo}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONANUAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
						</h:selectOneMenu>
		        	</rich:column>
		        </h:panelGrid>
		        
		        <rich:spacer height="4px"/>
				
				<h:panelGrid columns="2">
			    	<rich:column width="130">
						<h:outputText value="Descripción : "/>
		        	</rich:column>
		        	<rich:column width="530">
						<h:inputText size="102" value="#{aperturaCierreController.cuentaCierreNuevo.strDescripcion}"
							disabled="#{aperturaCierreController.deshabilitarNuevo}"/>
		        	</rich:column>
		        </h:panelGrid>
				
				<rich:spacer height="4px"/>
				  
				<h:panelGrid columns="3">
			    	<rich:column width="130">
						<h:outputText value="Agregar Cuenta : "/>
		        	</rich:column>
		        	<rich:column width="510">
						<h:inputText size="102" readonly="true" style="background-color: #BFBFBF;" 
							value="#{aperturaCierreController.cuentaCierreNuevo.strContNumeroCuenta}"
							disabled="#{aperturaCierreController.deshabilitarNuevo}"/>
		        	</rich:column>
		        	<rich:column width="50" style="text-align: right;">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		actionListener="#{aperturaCierreController.abrirPopUpPlanCuenta}"
	                		disabled="#{aperturaCierreController.deshabilitarNuevo}"
	                		value="Buscar" 
	                		reRender="pBuscarCuenta"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarCuenta')"
	                    	style="width:70px">
	                    	<f:attribute name="#{applicationScope.Constante.TIPOAGREGARPLANCUENTA}" 
	                    		value="#{applicationScope.Constante.TIPOAGREGARPLANCUENTA_CIERRECUENTA}"/>
	                    </a4j:commandButton>
	            	</rich:column>
		        </h:panelGrid>
				
				<rich:spacer height="4px"/>
				
				<h:panelGrid columns="3">
			    	<rich:column width="130">
						<h:outputText value="Detalle de Operación : "/>
		        	</rich:column>
		        	<rich:column width="510">
						<h:inputText size="102" readonly="true"/>
		        	</rich:column>
		        	<rich:column width="50" style="text-align: right;">
	                	<a4j:commandButton styleClass="btnEstilos"
	                		actionListener="#{aperturaCierreController.abrirPopUpPlanCuenta}"
	                		disabled="#{aperturaCierreController.deshabilitarNuevo}"
	                		value="Buscar" 
	                		reRender="pBuscarCuenta"
	                    	oncomplete="Richfaces.showModalPanel('pBuscarCuenta')"
	                    	style="width:70px">
	                    	<f:attribute name="#{applicationScope.Constante.TIPOAGREGARPLANCUENTA}" 
	                    		value="#{applicationScope.Constante.TIPOAGREGARPLANCUENTA_CIERRECUENTADETALLE}"/>
	                   	</a4j:commandButton>
	            	</rich:column>
		        </h:panelGrid>
		        
		        <h:panelGrid columns="2">
			    	<rich:column width="130">						
		        	</rich:column>
		        	<rich:column width="615" id ="colTablaCuentaCierreDetalle">
									<rich:dataTable 
						          		sortMode="single" 
					                    var="item" 
					                    value="#{aperturaCierreController.listaCuentaCierreDetalle}"  
										rowKeyVar="rowKey" 
										width="612px" 
										rows="#{fn:length(aperturaCierreController.listaCuentaCierreDetalle)}">
										
										<rich:column width="30px" style="text-align: center">
                    						<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    					</rich:column>
                    					<rich:column width="72" style="text-align: center">
											<f:facet name="header">
									       		<h:outputText value="Período"/>
											</f:facet>
											<h:outputText value="#{item.intContPeriodoCuenta}"/>
									 	</rich:column>
									    <rich:column width="150" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Cuenta Contable"/>
									      	</f:facet>
									      	<h:outputText value="#{item.strContNumeroCuenta}"/>
										</rich:column>
										<rich:column width="300" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Descripción"/>
									      	</f:facet>
									      	<h:outputText value="#{item.strDescripcion}"/>
										</rich:column>
										<rich:column width="60" style="text-align: center">
					                    	<f:facet name="header">
					                        	<h:outputText value="Opciones"/>
					                    	</f:facet>
					                    	<a4j:commandLink value="Eliminar"
									        	actionListener="#{aperturaCierreController.eliminarCuentaCierreDetalle}"
									            reRender="colTablaCuentaCierreDetalle"
									            disabled="#{aperturaCierreController.deshabilitarNuevo}">
									            <f:attribute name="item" value="#{item}"/>
							            	</a4j:commandLink>
					                  	</rich:column>
									</rich:dataTable>													
	            	</rich:column>
		        </h:panelGrid>
		        
				<rich:spacer height="4px"/>
				
			</rich:panel>
		</rich:panel>
</h:panelGroup>				
</h:form>