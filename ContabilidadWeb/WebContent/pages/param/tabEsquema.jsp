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
	<!-- Prototipo :  Esquema de EstadosFinancieros-->			
	<!-- Fecha     :                	-->




<h:form id="frmAnexo">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="ESQUEMA DE ESTADOS FINANCIEROS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">
              	<rich:column width="50" style="text-align: left;">
         			<h:outputText value="Periodo : "/>
         		</rich:column>
         		<rich:column width="70">
                	<h:selectOneMenu
						style="width: 70px;"
						value="#{anexoController.anexoFiltro.id.intContPeriodoAnexo}">
						<tumih:selectItems var="sel"
							value="#{anexoController.listaAnios}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="110" style="text-align: right;">
					<h:outputText value="Estado Financiero : "/>
		        </rich:column>
		        <rich:column width="252">
					<h:selectOneMenu
						style="width: 252px;"
						value="#{anexoController.anexoFiltro.id.intParaTipoAnexo}">
						<tumih:selectItems var="sel"
							value="#{anexoController.listaTipoAnexo}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
		        </rich:column>
		        <rich:column width="300" style="text-align: right;">
					<h:inputText value="#{anexoController.anexoFiltro.strAnexoDetalleBusqueda}" size="55"/>
		        </rich:column>
              	<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaAnexo,panelMensaje"
                    	action="#{anexoController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaAnexo">
	        	<rich:extendedDataTable id="tblAnexo" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{anexoController.listaAnexo}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="900px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>                       	
                    </rich:column>
                  	<rich:column width="60px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Periodo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intContPeriodoAnexo}" />
                	</rich:column>
                    <rich:column width="130" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado Financiero"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSFINANCIEROS}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intParaTipoAnexo}"/>
                  	</rich:column>
                    <rich:column width="680" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Arbol"/>                      		                      		
                      	</f:facet>
                      	<rich:dataTable value="#{item.listaDescripciones}" 
                     		var="desc" 
                     		style="border-top: 0px; border-left: 0px;">
							<rich:column width="540">
	                     		<h:outputText value="#{desc}"/>
							</rich:column>
						</rich:dataTable>
                  	</rich:column>
                  	<f:facet name="footer">
						<rich:datascroller for="tblAnexo" maxPages="10"/>   
					</f:facet>
					
					<a4j:support event="onRowClick"  
						actionListener="#{anexoController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="Richfaces.showModalPanel('pAlertaRegistro')">
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

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{anexoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{anexoController.mostrarMensajeExito}"/>
			<h:outputText value="#{anexoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{anexoController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{anexoController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{anexoController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaAnexo"
			    	disabled="#{!anexoController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{anexoController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>	        	
		
		          	
		<rich:panel id="contPanelInferior" style="border:0px;">
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{anexoController.mostrarPanelInferior}">		          	 	
		    
		    	 <h:panelGrid columns="2">
			    	<rich:column width="130">
						<h:outputText value="Período : "/>
		        	</rich:column>
		        	<rich:column width="252">
						<h:selectOneMenu
							style="width: 252px;"
							value="#{anexoController.anexoNuevo.id.intContPeriodoAnexo}"
							disabled="#{anexoController.deshabilitarNuevo || !anexoController.registrarNuevo}">
							<tumih:selectItems var="sel"
								value="#{anexoController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		        	</rich:column>		        	
		        </h:panelGrid>
		    	
		    	<rich:spacer height="4px"/>
		    	
		    	<h:panelGrid columns="2">
			    	<rich:column width="130">
						<h:outputText value="Estado Financiero : "/>
		        	</rich:column>
		        	<rich:column width="252">
						<h:selectOneMenu
							style="width: 252px;"
							value="#{anexoController.anexoNuevo.id.intParaTipoAnexo}"
							disabled="#{anexoController.deshabilitarNuevo || !anexoController.registrarNuevo}">
							<tumih:selectItems var="sel"
								value="#{anexoController.listaTipoAnexo}"
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		        	</rich:column>		        	
		        </h:panelGrid>
		        
		        <rich:spacer height="4px"/>
		    	
		    	<h:panelGrid columns="2">
			    	<rich:column width="130" style="vertical-align:text-top;">
						<h:outputText value="Descripción : " />
		        	</rich:column>
		        	<rich:column width="450">
						<h:inputTextarea cols="89" disabled="#{anexoController.deshabilitarNuevo}" 
							rows="4" value="#{anexoController.anexoNuevo.strDescripcion}"/>
		        	</rich:column>		        	
		        </h:panelGrid>				
		        
		        <rich:spacer height="15px"/>

				
		        <h:panelGrid id="colListaTexto">
		        	<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{anexoController.listaAnexoDetalle}"  
						rowKeyVar="rowKey" 
						width="900px" 
						rows="#{fn:length(anexoController.listaAnexoDetalle)}">					
						
						<rich:column width="500px" style="text-align: left">
							<f:facet name="header">
								<h:outputText value="Descripción"/>
							</f:facet>
							<h:outputText value="#{item.strNumeracion}.- " escape="false"/>
							<h:inputText value="#{item.strTexto}" size="#{70-2*item.intNivel}"/>
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Configurar"/>
							</f:facet>
							<a4j:commandButton styleClass="btnEstilos"
			                	actionListener="#{anexoController.abrirPopUpConfiguracion}"
			                	disabled="#{anexoController.deshabilitarNuevo || !item.habilitarConfigurar}"		                		
			                	value="Configurar"
			                	reRender="pConfigurar,panelMensaje"
			                    oncomplete="Richfaces.showModalPanel('pConfigurar')"
			                    style="width:70px">
			                    <f:attribute name="item" value="#{item}"/>		                    	
		                   	</a4j:commandButton>							
						</rich:column>
						<rich:column width="80px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Ver Cuentas"/>
							</f:facet>
							<a4j:commandButton styleClass="btnEstilos"
			                	actionListener="#{anexoController.abrirPopUpVerCuentas}"
			                	disabled="#{anexoController.deshabilitarNuevo || !item.habilitarVerCuentas || anexoController.registrarNuevo}"
			                	value="Ver Cuentas"
			                	reRender="pVerCuentas,panelMensaje"
			                    oncomplete="Richfaces.showModalPanel('pVerCuentas')"
			                    style="width:80px">
			                    	<f:attribute name="item" value="#{item}"/>		                    	
		                   	</a4j:commandButton>
						</rich:column>
						<rich:column width="60px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Operacion"/>
							</f:facet>
							<h:panelGroup rendered="#{item.intNivel!=1}">
								<h:selectOneMenu
									style="width: 60px;"
									value="#{item.intTipoOperacion}"
									disabled="#{anexoController.deshabilitarNuevo}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="AddHerm"/>
							</f:facet>
							<a4j:commandLink
								actionListener="#{anexoController.addAnexoDetalleHermano}"
								reRender="colListaTexto,panelMensaje"	
								disabled="#{anexoController.deshabilitarNuevo}">
									<h:graphicImage value="/images/icons/add_rigth.JPG"style="border:0px"title="Añadir elemento al mismo nivel"/>
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>
						<rich:column width="50px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="AddHijo"/>
							</f:facet>
							<a4j:commandLink
								actionListener="#{anexoController.addAnexoDetalleHijo}"
								reRender="colListaTexto,panelMensaje"	
								disabled="#{anexoController.deshabilitarNuevo}">
									<h:graphicImage value="/images/icons/add_down.JPG"style="border:0px"title="Añadir subelemento"/>
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>						
						<rich:column width="30px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Subir"/>
							</f:facet>
							<a4j:commandLink
								actionListener="#{anexoController.subirOrden}"
								reRender="colListaTexto"	
								disabled="#{anexoController.deshabilitarNuevo}">
									<h:graphicImage value="/images/icons/arrow_up.JPG" style="border:0px" title="Subir Elemento"/>
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>
						<rich:column width="30px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Bajar"/>
							</f:facet>
							<a4j:commandLink 
								actionListener="#{anexoController.bajarOrden}"
								reRender="colListaTexto"	
								disabled="#{anexoController.deshabilitarNuevo}">
									<h:graphicImage value="/images/icons/arrow_down.JPG" style="border:0px" title="Bajar Elemento"/>
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>
						<rich:column width="40px" style="text-align: center">
							<f:facet name="header">
								<h:outputText value="Eliminar"/>
							</f:facet>
							<a4j:commandLink
								actionListener="#{anexoController.iniciarEliminarAnexoDetalle}"
								reRender="colListaTexto"	
								disabled="#{anexoController.deshabilitarNuevo}">
									<h:graphicImage value="/images/icons/cross.JPG" style="border:0px" title="Eliminar"/>
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
						</rich:column>
					</rich:dataTable>
		        </h:panelGrid>
		        
			</rich:panel>
		</rich:panel>
</h:panelGroup>				
</h:form>