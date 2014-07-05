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
	<!-- Prototipo :  Ratios y Anexo-->			
	<!-- Fecha     :                	-->




<h:form id="frmRatio">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="RATIOS Y ANEXOS" style="font-weight:bold; font-size:14px"/>
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
						value="#{ratioController.ratioFiltro.id.intContPeriodoRatio}">
						<tumih:selectItems var="sel"
							value="#{ratioController.listaAnios}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
         		<rich:column width="50" style="text-align: left;">
         			<h:outputText value="Indicador : "/>
         		</rich:column>
         		<rich:column width="150">
                	<h:selectOneMenu
						style="width: 150px;"
						value="#{ratioController.intIndicadorFiltro}">
						<tumih:selectItems var="sel"
								cache="#{applicationScope.Constante.PARAM_T_GRUPORATIOANEXO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						<a4j:support event="onchange" reRender="frmRatio"/>
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="10" style="text-align: left;">         			
         		</rich:column>
         		<rich:column width="100" style="text-align: left;" id="colNombreLabel">
         			<h:panelGroup rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_RATIO}">
         				<h:outputText value="Tipo de Ratio : "/>
         			</h:panelGroup>
         			<h:panelGroup rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_ANEXO}">
         				<h:outputText value="Tipo de Anexo : "/>
         			</h:panelGroup>
         		</rich:column>
         		<rich:column width="260" id="colComboTipo">
                	<h:selectOneMenu
						style="width: 260px;"
						rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_RATIO}"
						value="#{ratioController.ratioFiltro.intParaTipoRatio}">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_TIPORATIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						style="width: 260px;"
						rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_ANEXO}"
						value="#{ratioController.anexoFiltro.id.intParaTipoAnexo}">
						<tumih:selectItems var="sel"
							value="#{ratioController.listaTipoAnexo}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
              	
              	<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaRatio,panelMensaje"
                    	action="#{ratioController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGroup id="panelTablaRatio">
            	<h:panelGroup rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_RATIO}">
		        	<rich:extendedDataTable id="tblRatio" 
		          		enableContextMenu="false" 
		          		sortMode="single" 
	                    var="item"
	                    value="#{ratioController.listaRatio}"  
						rowKeyVar="rowKey" 
						rows="5"
						width="850px"
						height="170px"
						align="center">
	                                
						<rich:column width="30px" style="text-align: center">
	                    	<h:outputText value="#{rowKey + 1}"/>                       	
	                    </rich:column>
	                  	<rich:column width="150px" style="text-align: center">
	                    	<f:facet name="header">
	                        	<h:outputText value="Periodo"/>
	                      	</f:facet>
	                      	<h:outputText value="#{item.id.intContPeriodoRatio}" />
	                	</rich:column>
	                    <rich:column width="220" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Inidicador"/>                		
	                      	</f:facet>
	                      	<h:outputText value="Ratio Contable"/>
	                  	</rich:column>
	                    <rich:column width="250" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Tipo de Indicador"/>                      		                      		
	                      	</f:facet>
	                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORATIO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaTipoRatio}"/>
	                  	</rich:column>
	                  	<rich:column width="200" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Fecha y Hora"/>                      		
	                      	</f:facet>
	                      	<h:outputText value="#{item.tsFechaRegistro}">
	                           	<f:convertDateTime pattern="dd-MM-yyyy hh:mm" />
	                        </h:outputText>
	                  	</rich:column>
	                  	<f:facet name="footer">
							<rich:datascroller for="tblRatio" maxPages="10"/>   
						</f:facet>
						<a4j:support event="onRowClick"  
							actionListener="#{ratioController.seleccionarRegistro}"
							reRender="frmAlertaRegistroRatio,contPanelInferiorRatio,panelBotonesRatio"
							oncomplete="if(#{ratioController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistroRatio')}">
	                        	<f:attribute name="item" value="#{item}"/>
	                   	</a4j:support>
	            	</rich:extendedDataTable>
            	</h:panelGroup>
            	<h:panelGroup rendered="#{ratioController.intIndicadorFiltro==Constante.PARAM_T_GRUPORATIOANEXO_ANEXO}">
	            	<rich:extendedDataTable id="tblAnexo" 
		          		enableContextMenu="false" 
		          		sortMode="single" 
	                    var="item"
	                    value="#{ratioController.listaAnexo}"  
						rowKeyVar="rowKey" 
						rows="5"
						width="850px"
						height="170px"
						align="center">
	                                
						<rich:column width="30px" style="text-align: center">
	                    	<h:outputText value="#{rowKey + 1}"/>                       	
	                    </rich:column>
	                  	<rich:column width="150px" style="text-align: center">
	                    	<f:facet name="header">
	                        	<h:outputText value="Periodo"/>
	                      	</f:facet>
	                      	<h:outputText value="#{item.id.intContPeriodoAnexo}" />
	                	</rich:column>
	                    <rich:column width="220" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Inidicador"/>                		
	                      	</f:facet>
	                      	<h:outputText value="Anexo Contable"/>
	                  	</rich:column>
	                    <rich:column width="250" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Tipo de Indicador"/>                      		                      		
	                      	</f:facet>
	                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSFINANCIEROS}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaTipoAnexo}"/>
	                  	</rich:column>
	                  	<rich:column width="200" style="text-align: center">
	                    	<f:facet name="header">
	                      		<h:outputText value="Fecha y Hora"/>                      		
	                      	</f:facet>
	                      	<h:outputText value="#{item.tsFechaRegistro}">
	                           	<f:convertDateTime pattern="dd-MM-yyyy hh:mm" />
	                        </h:outputText>
	                  	</rich:column>
	                  	<f:facet name="footer">
							<rich:datascroller for="tblAnexo" maxPages="10"/>   
						</f:facet>
						<a4j:support event="onRowClick"  
							actionListener="#{ratioController.seleccionarRegistroAnexo}"
							reRender="frmAlertaRegistroRatio,contPanelInferiorRatio,panelBotonesRatio"
							oncomplete="Richfaces.showModalPanel('pAlertaRegistroRatio')">
	                        	<f:attribute name="item" value="#{item}"/>
	                   	</a4j:support>
	            	</rich:extendedDataTable>
            	</h:panelGroup>
         	</h:panelGroup>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeRatio" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{ratioController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{ratioController.mostrarMensajeExito}"/>
			<h:outputText value="#{ratioController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{ratioController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesRatio">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{ratioController.habilitarPanelInferior}" 
					reRender="contPanelInferiorRatio,panelMensajeRatio,panelBotonesRatio" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{ratioController.grabar}" 
			    	reRender="contPanelInferiorRatio,panelMensajeRatio,panelBotonesRatio,panelTablaRatio"
			    	disabled="#{!ratioController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{ratioController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorRatio,panelMensajeRatio,panelBotonesRatio"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		          	
		<rich:panel id="contPanelInferiorRatio" style="border:0px;">
			<rich:panel id="panelInferiorRatio" style="border:1px solid #17356f;" rendered="#{ratioController.mostrarPanelInferior}">		          	 	
		    
		        
		        <h:panelGrid columns="7">
			    	<rich:column width="110">
						<h:outputText value="Período : "/>
		        	</rich:column>
		        	<rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							value="#{ratioController.ratioNuevo.id.intContPeriodoRatio}"
							disabled="#{ratioController.deshabilitarNuevo || !ratioController.registrarNuevo || !ratioController.habilitarCabeceraNuevo}">
							<tumih:selectItems var="sel"
								value="#{ratioController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		        	</rich:column>		        	
		        	<rich:column width="50">						
		        	</rich:column>
		        	<rich:column width="110" style="text-align: left">
						<h:outputText value="Indicador : "/>
		        	</rich:column>
		        	<rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							value="#{ratioController.intIndicador}"
							disabled="#{ratioController.deshabilitarNuevo || !ratioController.habilitarCabeceraNuevo}">
							<tumih:selectItems var="sel" 
								cache="#{applicationScope.Constante.PARAM_T_GRUPORATIOANEXO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		        	</rich:column>		        	
		        	<rich:column width="50">
		        	</rich:column>
		        	<rich:column width="150">
						<a4j:commandButton styleClass="btnEstilos"
                			value="Analizar" reRender="contPanelInferiorRatio,panelBotonesRatio"
                			disabled="#{!ratioController.habilitarCabeceraNuevo}"
                    		action="#{ratioController.analizar}" style="width:70px"/>
		        	</rich:column>
		        </h:panelGrid>
		        
		        
		        
		        <h:panelGroup rendered="#{ratioController.intIndicador==applicationScope.Constante.PARAM_T_GRUPORATIOANEXO_RATIO}">
		        	
		        	<h:panelGrid columns="5">
				    	<rich:column width="110">
							<h:outputText value="Tipo de Ratio : "/>
			        	</rich:column>
			        	<rich:column width="180">
							<h:selectOneMenu
								style="width: 180px;"
								value="#{ratioController.ratioNuevo.intParaTipoRatio}"
								disabled="#{ratioController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPORATIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="50">
		        		</rich:column>
		        		<rich:column width="110" style="text-align: left">
							<h:outputText value="Estado : "/>
			        	</rich:column>
			        	<rich:column width="150">
							<h:selectOneMenu
								style="width: 150px;"
								value="#{ratioController.ratioNuevo.intParaEstado}"
								disabled="#{ratioController.deshabilitarNuevo || ratioController.registrarNuevo}">
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>					
							</h:selectOneMenu>
			        	</rich:column>
			        </h:panelGrid>
		        
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Nombre de Ratio : "/>
			        	</rich:column>
			        	<rich:column width="450">
							<h:inputText size="68" value="#{ratioController.ratioNuevo.strNombreRatio}" 
								disabled="#{ratioController.deshabilitarNuevo}"/>
			        	</rich:column>
		        	</h:panelGrid>
		        	
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Descripción : "/>
			        	</rich:column>
			        	<rich:column width="450">
							<h:inputTextarea cols="68" disabled="#{ratioController.deshabilitarNuevo}" 
								rows="3" value="#{ratioController.ratioNuevo.strDescripcionRatio}"/>
			        	</rich:column>
		        	</h:panelGrid>
		        	
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Periodicidad : "/>
			        	</rich:column>
			        	<rich:column width="180">
							<h:selectOneMenu
								style="width: 180px;"
								value="#{ratioController.ratioNuevo.intParaPeriodicidad}"
								disabled="#{ratioController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" 
									value="#{ratioController.listaPeriodicidad}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			        	</rich:column>
		        	</h:panelGrid>
		        			        	
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Unidad de Medida : "/>
			        	</rich:column>
			        	<rich:column width="180">
							<h:selectOneMenu
								style="width: 180px;"
								value="#{ratioController.ratioNuevo.intParaUnidadMedida}"
								disabled="#{ratioController.deshabilitarNuevo}">
								<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_UNIDADMEDIDAINDICADOR}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								<a4j:support event="onchange"
									reRender="panelMonedaRatio"  />
							</h:selectOneMenu>
			        	</rich:column>
			        	<rich:column width="180" id="panelMonedaRatio">
							<h:panelGroup rendered="#{ratioController.ratioNuevo.intParaUnidadMedida==applicationScope.Constante.PARAM_T_FRECUENCPAGOINT_MONEDA}">
								<h:selectOneMenu
									style="width: 180px;"
									value="#{ratioController.ratioNuevo.intParaMoneda}"
									disabled="#{ratioController.deshabilitarNuevo}">
									<tumih:selectItems var="sel" 
										value="#{ratioController.listaMoneda}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>							
			        	</rich:column>
		        	</h:panelGrid>
		        	
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Fórmula : "/>
			        	</rich:column>			        	
			        </h:panelGrid>
			        
			        <h:panelGroup id="panelFormula">
				        <h:panelGrid columns="5">
			        		<rich:column width="80">
				        		<h:outputText value="Superior : "/>
				        	</rich:column>
				        	<rich:column width="810" id="tablaSuperior">
				        		<rich:dataTable value="#{ratioController.listaRatioDetalleSuperior}" 
		                     		var="item"
		                     		width="810px" 
		                     		style="border-top: 0px; border-left: 0px;">
									
									<rich:column width="10">
			                     		<h:outputText value="#{item.intParteGrupo}"/>
									</rich:column>
									<rich:column width="300">
										<f:facet name="header">
				                        	<h:outputText value="Operando 1"/>
				                      	</f:facet>
				                      	<a4j:commandLink
				                      		actionListener="#{ratioController.abrirPopUpElemento}"
											reRender="pSeleccionElemento"
											oncomplete="Richfaces.showModalPanel('pSeleccionElemento')"
											disabled="#{ratioController.deshabilitarNuevo}">
												<h:graphicImage value="/images/icons/add.JPG" style="border:0px" title="Agregar Referencia"/>
												<f:attribute name="item" value="#{item}"/>
												<f:attribute name="operando" value="1"/>
										</a4j:commandLink>
					                    <h:inputText size="40" value="#{item.operando1.strTexto}" readonly="true"/>                    
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	<h:outputText value="Operador"/>
				                      	</f:facet>
				                      	<h:selectOneMenu
											style="width: 70px;"
											value="#{item.intParaOperacionInterno}"
											disabled="#{ratioController.deshabilitarNuevo}">
											<tumih:selectItems var="sel" 
												cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="300">
										<f:facet name="header">
				                        	<h:outputText value="Operando 2"/>
				                      	</f:facet>
			                     		<a4j:commandLink
											actionListener="#{ratioController.abrirPopUpElemento}"
											reRender="pSeleccionElemento"
											oncomplete="Richfaces.showModalPanel('pSeleccionElemento')"
											disabled="#{ratioController.deshabilitarNuevo}">
												<h:graphicImage value="/images/icons/add.JPG" style="border:0px" title="Agregar Referencia"/>
												<f:attribute name="item" value="#{item}"/>
												<f:attribute name="operando" value="2"/>
										</a4j:commandLink>
					                    <h:inputText size="40" value="#{item.operando2.strTexto}"/>
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	<h:outputText value="Operador"/>
				                      	</f:facet>
				                      	<h:selectOneMenu
											style="width: 70px;"
											value="#{item.intParaOperacionExterno}"
											disabled="#{ratioController.deshabilitarNuevo}">
											<tumih:selectItems var="sel" 
												cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	
				                      	</f:facet>
				                      	<a4j:commandLink
					                			value="Eliminar" 
					                			reRender="tablaSuperior"
					                			disabled="#{ratioController.deshabilitarNuevo}"
					                    		actionListener="#{ratioController.desagregarListaRatioDetalleSuperior}" 
					                    		>
					                    		<f:attribute name="item" value="#{item}"/>
					                    </a4j:commandLink>
									</rich:column>
								</rich:dataTable>
								<a4j:commandLink
						        	value="Agregar Superior" 
						            reRender="tablaSuperior"
						            disabled="#{ratioController.deshabilitarNuevo}"
						            action="#{ratioController.agregarListaRatioDetalleSuperior}"/>
				        	</rich:column>
				        </h:panelGrid>
				        
				        <h:panelGrid columns="5">
			        		<rich:column width="80">
				        		<h:outputText value="Operación : "/>
				        	</rich:column>
				        	<rich:column width="100">
				        		<h:selectOneMenu
									style="width: 100px;"
									value="#{ratioController.ratioNuevo.intParaTipoOperacion}"
									disabled="#{ratioController.deshabilitarNuevo}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
									</h:selectOneMenu>
				        	</rich:column>
				        </h:panelGrid>
				        
				        
				        <h:panelGrid columns="5">
			        		<rich:column width="80">
				        		<h:outputText value="Inferior : "/>
				        	</rich:column>
				        	<rich:column width="810" id="tablaInferior">
				        		<rich:dataTable value="#{ratioController.listaRatioDetalleInferior}" 
		                     		var="item"
		                     		width="810px" 
		                     		style="border-top: 0px; border-left: 0px;">
									
									<rich:column width="10">
			                     		<h:outputText value="#{item.intParteGrupo}"/>
									</rich:column>
									<rich:column width="300">
										<f:facet name="header">
				                        	<h:outputText value="Operando 1"/>
				                      	</f:facet>
				                      	<a4j:commandLink
				                      		actionListener="#{ratioController.abrirPopUpElemento}"
											reRender="pSeleccionElemento"
											oncomplete="Richfaces.showModalPanel('pSeleccionElemento')"
											disabled="#{ratioController.deshabilitarNuevo}">
												<h:graphicImage value="/images/icons/add.JPG" style="border:0px" title="Agregar Referencia"/>
												<f:attribute name="item" value="#{item}"/>
												<f:attribute name="operando" value="1"/>
										</a4j:commandLink>
					                    <h:inputText size="40" value="#{item.operando1.strTexto}" readonly="true"/>                    
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	<h:outputText value="Operador"/>
				                      	</f:facet>
				                      	<h:selectOneMenu
											style="width: 70px;"
											value="#{item.intParaOperacionInterno}"
											disabled="#{ratioController.deshabilitarNuevo}">
											<tumih:selectItems var="sel" 
												cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="300">
										<f:facet name="header">
				                        	<h:outputText value="Operando 2"/>
				                      	</f:facet>
			                     		<a4j:commandLink
											actionListener="#{ratioController.abrirPopUpElemento}"
											reRender="pSeleccionElemento"
											oncomplete="Richfaces.showModalPanel('pSeleccionElemento')"
											disabled="#{ratioController.deshabilitarNuevo}">
												<h:graphicImage value="/images/icons/add.JPG" style="border:0px" title="Agregar Referencia"/>
												<f:attribute name="item" value="#{item}"/>
												<f:attribute name="operando" value="2"/>
										</a4j:commandLink>
					                    <h:inputText size="40" value="#{item.operando2.strTexto}"/>
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	<h:outputText value="Operador"/>
				                      	</f:facet>
				                      	<h:selectOneMenu
											style="width: 70px;"
											value="#{item.intParaOperacionExterno}"
											disabled="#{ratioController.deshabilitarNuevo}">
											<tumih:selectItems var="sel" 
												cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
										</h:selectOneMenu>
									</rich:column>
									<rich:column width="70">
			                     		<f:facet name="header">
				                        	
				                      	</f:facet>
				                      	<a4j:commandLink
					                			value="Eliminar" 
					                			reRender="tablaInferior"
					                			disabled="#{ratioController.deshabilitarNuevo}"
					                    		actionListener="#{ratioController.desagregarListaRatioDetalleInferior}" 
					                    		>
					                    		<f:attribute name="item" value="#{item}"/>
					                    </a4j:commandLink>
									</rich:column>
								</rich:dataTable>
								<a4j:commandLink 
					            	value="Agregar Inferior" 
					                reRender="tablaInferior"
					                disabled="#{ratioController.deshabilitarNuevo}"
					                action="#{ratioController.agregarListaRatioDetalleInferior}"/>
				        	</rich:column>
				        </h:panelGrid>
			        </h:panelGroup>
			        
		        </h:panelGroup>
		        
		        
		        
		        <h:panelGroup rendered="#{ratioController.intIndicador==applicationScope.Constante.PARAM_T_GRUPORATIOANEXO_ANEXO}">
		        	
		        	<h:panelGrid columns="5">
				    	<rich:column width="110">
							<h:outputText value="Tipo de Anexo : "/>
			        	</rich:column>
			        	<rich:column width="360">
							<h:selectOneMenu
								style="width: 360px;"
								value="#{ratioController.anexoNuevo.id.intParaTipoAnexo}"
								disabled="#{ratioController.deshabilitarNuevo}">
								<tumih:selectItems var="sel"
									value="#{ratioController.listaTipoAnexo}"
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			        	</rich:column>
			        </h:panelGrid>
		        
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Descripción : "/>
			        	</rich:column>
			        	<rich:column width="450">
							<h:inputTextarea cols="68" disabled="#{ratioController.deshabilitarNuevo}" 
								rows="4" value="#{ratioController.anexoNuevo.strDescripcion}"/>
			        	</rich:column>
		        	</h:panelGrid>
		        	
		        	<h:panelGrid columns="5">
		        		<rich:column width="110">
							<h:outputText value="Periodicidad : "/>
			        	</rich:column>
			        	<rich:column width="180">
							<h:selectOneMenu
								style="width: 180px;"
								value="#{ratioController.anexoNuevo.intParaPeriodicidad}"
								disabled="#{ratioController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" 
									value="#{ratioController.listaPeriodicidad}" 
									itemValue="#{sel.intIdDetalle}"
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			        	</rich:column>
		        	</h:panelGrid>
		        	
		        	
		       		<h:panelGrid id="colListaTextoRatio">
			        	<rich:dataTable 
							sortMode="single" 
						    var="item" 
						    value="#{ratioController.listaAnexoDetalle}"  
							rowKeyVar="rowKey" 
							width="900px" 
							rows="#{fn:length(ratioController.listaAnexoDetalle)}">					
							
							<rich:column width="400px" style="text-align: left">
								<f:facet name="header">
									<h:outputText value="Descripción"/>
								</f:facet>
								<h:outputText value="#{item.strNumeracion}.- " escape="false"/>
								<h:inputText value="#{item.strTexto}" size="#{65-2*item.intNivel}"/>
							</rich:column>
							<rich:column width="70px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Configurar"/>
								</f:facet>
								<a4j:commandButton styleClass="btnEstilos"
				                	actionListener="#{ratioController.abrirPopUpConfiguracion}"
				                	disabled="#{ratioController.deshabilitarNuevo || !item.habilitarConfigurar}"		                		
				                	value="Configurar"
				                	reRender="pConfigurarRatio,panelMensajeRatio"
				                    oncomplete="Richfaces.showModalPanel('pConfigurarRatio')"
				                    style="width:70px">
				                    <f:attribute name="item" value="#{item}"/>		                    	
			                   	</a4j:commandButton>							
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Agregar"/>
								</f:facet>
								<a4j:commandButton styleClass="btnEstilos"
									value="Cuenta"
				                	actionListener="#{ratioController.abrirPopUpCuenta}"
									reRender="pSeleccionCuenta"
									oncomplete="Richfaces.showModalPanel('pSeleccionCuenta')"
									disabled="#{ratioController.deshabilitarNuevo || !item.habilitarVerCuentas}"
									style="width:70px">
									<f:attribute name="item" value="#{item}"/>
									<f:attribute name="operando" value="0"/>
								</a4j:commandButton>
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Agregar"/>
								</f:facet>
								<a4j:commandButton styleClass="btnEstilos"
									value="Elemento"
				                	actionListener="#{ratioController.abrirPopUpElemento}"
									reRender="pSeleccionElemento"
									oncomplete="Richfaces.showModalPanel('pSeleccionElemento')"
									disabled="#{ratioController.deshabilitarNuevo || !item.habilitarVerReferencia}"
									style="width:70px">
									<f:attribute name="item" value="#{item}"/>
									<f:attribute name="operando" value="0"/>
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
										disabled="#{ratioController.deshabilitarNuevo}">
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
									actionListener="#{ratioController.addAnexoDetalleHermano}"
									reRender="colListaTextoRatio,panelMensajeRatio"	
									disabled="#{ratioController.deshabilitarNuevo}">
										<h:graphicImage value="/images/icons/add_rigth.JPG"style="border:0px"title="Añadir elemento al mismo nivel"/>
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="AddHijo"/>
								</f:facet>
								<a4j:commandLink
									actionListener="#{ratioController.addAnexoDetalleHijo}"
									reRender="colListaTextoRatio,panelMensaje"	
									disabled="#{ratioController.deshabilitarNuevo}">
										<h:graphicImage value="/images/icons/add_down.JPG"style="border:0px"title="Añadir subelemento"/>
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>						
							<rich:column width="30px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Subir"/>
								</f:facet>
								<a4j:commandLink
									actionListener="#{ratioController.subirOrden}"
									reRender="colListaTextoRatio"	
									disabled="#{ratioController.deshabilitarNuevo}">
										<h:graphicImage value="/images/icons/arrow_up.JPG" style="border:0px" title="Subir Elemento"/>
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>
							<rich:column width="30px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Bajar"/>
								</f:facet>
								<a4j:commandLink 
									actionListener="#{ratioController.bajarOrden}"
									reRender="colListaTextoRatio"	
									disabled="#{ratioController.deshabilitarNuevo}">
										<h:graphicImage value="/images/icons/arrow_down.JPG" style="border:0px" title="Bajar Elemento"/>
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>
							<rich:column width="40px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Eliminar"/>
								</f:facet>
								<a4j:commandLink 
									actionListener="#{ratioController.iniciarEliminarAnexoDetalle}"
									reRender="colListaTextoRatio"	
									disabled="#{ratioController.deshabilitarNuevo}">
										<h:graphicImage value="/images/icons/cross.JPG" style="border:0px" title="Eliminar"/>
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>
						</rich:dataTable>
		        	</h:panelGrid>
		        
		        </h:panelGroup>
		        
		    </rich:panel>	 
		</rich:panel>
</h:panelGroup>				
</h:form>