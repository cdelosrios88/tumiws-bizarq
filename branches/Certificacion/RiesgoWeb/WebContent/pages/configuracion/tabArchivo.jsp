<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Prototipo :  Archivos de Envio -->			
	<!-- Fecha     :                	-->


<h:form id="frmConfigArchivo">

<rich:panel style="border:1px solid #17356f;">
	


	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder">
        	
        	<h:panelGrid columns="9">
         		<rich:column style="width: 140px">
         			<h:outputText value="Código de Configuración : "/>
         		</rich:column>
         		<rich:column>
                	<h:inputText size="15" value="#{confArchivoController.configuracionFiltro.id.intItemConfiguracion}"/>
              	</rich:column>
                <rich:column style="text-align: center;width: 50px;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu id="cboEstado" 
						style="width: 100px;"
						value="#{confArchivoController.configuracionFiltro.intParaEstadoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="width: 130px">
         			<h:outputText value="Fecha Registro Desde : "/>
         		</rich:column>
              	<rich:column>
         			<rich:calendar datePattern="dd/MM/yyyy"  value="#{confArchivoController.fechaFiltroInicio}"
         				inputSize="10" showApplyButton="true"/>
         		</rich:column>
         		<rich:column style="text-align: center;width: 40px;">
         			<h:outputText value="Hasta : "/>
         		</rich:column>
         		<rich:column>
         			<rich:calendar datePattern="dd/MM/yyyy"  value="#{confArchivoController.fechaFiltroFin}"
         				inputSize="10" showApplyButton="true"/>
         		</rich:column>
         		<rich:column style="text-align: right; width:60px">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaConfiguracion"
                    	action="#{confArchivoController.buscar}"/>
            	</rich:column>
            </h:panelGrid>
            <h:panelGrid columns="5">
              	
         	</h:panelGrid>
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaConfiguracion">
	        	<rich:extendedDataTable id="tblConfiguraciones" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{confArchivoController.listaConfiguracion}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="860px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="200px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código de configuración" style="text-align: center"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemConfiguracion}"/>
                	</rich:column>
                    <rich:column width="160" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Formato de Archivo"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FORMATOARCHIVOS}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaFormatoArchivoCod}"/>
                  	</rich:column>
                    <rich:column width="110" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>                        
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstadoCod}"/>
                  	</rich:column>
                    <rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                    	</f:facet>
                    	<h:outputText value="#{item.tsFechaRegistro}">
                            	<f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="190" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Usuario"/>
                     	</f:facet>
                     	<h:outputText value="#{item.intPersPersonaUsuarioPk} - #{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
                  	</rich:column>
                    <f:facet name="footer">   
						<rich:datascroller for="tblConfiguraciones" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
							actionListener="#{confArchivoController.seleccionarRegistro}"
							reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
							oncomplete="if(#{confArchivoController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        		<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="1" style="margin-left: auto; margin-right: auto">
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
		</rich:panel>
				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;margin-left: auto; margin-right: auto;width:500px;"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{confArchivoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{confArchivoController.mostrarMensajeExito}"/>
			<h:outputText value="#{confArchivoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{confArchivoController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{confArchivoController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{confArchivoController.grabar}" reRender="contPanelInferior,panelMensaje,panelBotones"
			    	disabled="#{!confArchivoController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{confArchivoController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje"/>      
			</h:panelGrid>
		</rich:panel>
		        	
		
		          	
		<rich:panel id="contPanelInferior" style="border:0px;">
			<rich:spacer height="3px"/>  	 
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{confArchivoController.mostrarPanelInferior}">		          	 	
		    	<h:panelGroup id="panelIngresarConfiguracion">                    
	        	
					<h:panelGrid columns="2">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Formato de Archivo : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu 
								style="width: 100px;"
								disabled="#{confArchivoController.deshabilitarNuevo}"
								value="#{confArchivoController.configuracionNuevo.intParaFormatoArchivoCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FORMATOARCHIVOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								<a4j:support event="onchange" action="#{confArchivoController.seleccionarFormato}" 
									reRender="colCampos,contPanelInferior,panelEjemplo"  />			
							</h:selectOneMenu>
              			</rich:column>
                    </h:panelGrid>  
                    
                    <h:panelGrid columns="2">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Estado : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu
								style="width: 100px;"
								disabled="#{confArchivoController.deshabilitarNuevo || confArchivoController.registrarNuevo}"
								value="#{confArchivoController.configuracionNuevo.intParaEstadoCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>			
							</h:selectOneMenu>
              			</rich:column>
                    </h:panelGrid>
                    
  
				    
                    <h:panelGrid columns="2">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Nombre de Archivo : "/>
                     	</rich:column>
                     	<rich:column>
                     	<rich:panel style="width:350px" id="panelNombre">
                     		<h:panelGrid columns="3">
			                	<rich:column>
			                		<h:dataTable
			                			id="tblValoresA"
			                			var="item" 
						                value="#{confArchivoController.listaNombreValor}"
						                rows="2"
						                width="120px">
										<h:column>
                        					<h:selectBooleanCheckbox 
                        						value="#{item.checked}"
                        						disabled="#{confArchivoController.deshabilitarNuevo}">
                        						 <a4j:support event="onclick" actionListener="#{confArchivoController.seleccionarNombre}"
                        						 	reRender="panelNombre">
                        						 	<f:attribute name="item" value="#{item}"/>
                        						 </a4j:support>
                        					</h:selectBooleanCheckbox>
						                </h:column>
										<h:column>
						                	<h:outputText value="#{item.strDescripcion}"></h:outputText>                        	
						                </h:column>
			                		</h:dataTable>				                	
			                	</rich:column>
			                	<rich:column>
			                		<h:panelGrid columns="2">
					                	<rich:column style="width: 120px;">
							            	<h:inputText size="18" value="#{confArchivoController.nombre.strValorFijo}"
						            		disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarNombreFijo}"/>
					              		</rich:column> 
					              	</h:panelGrid>
					                <h:panelGrid columns="2">
					                	<rich:column style="width: 120px;">
							            	<h:selectOneMenu													
												value="#{confArchivoController.nombre.intParaTipoVariableCod}"
												disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarNombreVariable}">
												<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
													tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
											</h:selectOneMenu>
					              		</rich:column>
					             	</h:panelGrid>
			                	</rich:column>
			                	<rich:column>
			                	 	<a4j:commandButton styleClass="btnEstilos" style="height:22px" value="Agregar"
	                    				action="#{confArchivoController.agregarNombre}" reRender="panelNombreResultado,panelMensaje"
	                    				disabled="#{confArchivoController.deshabilitarNuevo}"/>
			                	</rich:column>
		                	</h:panelGrid>
		                	</rich:panel>
		                	
		                	<h:panelGrid columns="3" id="panelNombreResultado">
			                	<rich:column>
				                	<rich:dataTable id="tblValores"
							          	sortMode="single" 
						                var="item" 
						                value="#{confArchivoController.listaNombre}"  
										rowKeyVar="rowKey" 
										width="300px"
										rows="#{fn:length(confArchivoController.listaNombre)}"
										align="center">
										<rich:column width="20px">
						                	<h:outputText value="#{rowKey + 1}"></h:outputText>
						                </rich:column>
						                <rich:column width="120px">
						                	<f:facet name="header">
						                    	<h:outputText value="Valor"/>						                        	
						                    </f:facet>
						                    <h:panelGroup rendered="#{item.intParaTipoValorCod==applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO_FIJO}">
						                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO}" 
													itemValue="intIdDetalle" itemLabel="strDescripcion" 
													property="#{item.intParaTipoValorCod}"/>
						                    </h:panelGroup>
						                    <h:panelGroup rendered="#{item.intParaTipoValorCod==applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE}">
						                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO}" 
													itemValue="intIdDetalle" itemLabel="strDescripcion" 
													property="#{item.intParaTipoVariableCod}"/>
						                    </h:panelGroup>
						                </rich:column>
						                <rich:column width="80" style="text-align: center;">
						                	<f:facet name="header">
						                    	<h:outputText value="Dato"/>
						                    </f:facet>
						                    <h:outputText value="#{item.strValorFijo}"/>
						                </rich:column>
						                <rich:column style="text-align: center;"width="80">
						                	<f:facet name="header">
						                    	<h:outputText value="Opciones"/>
						                    </f:facet>
						                    <a4j:commandLink value="Eliminar"
								            	actionListener="#{confArchivoController.eliminarNombre}"
								            	reRender="panelNombreResultado"
								            	disabled="#{confArchivoController.deshabilitarNuevo}">
								            	<f:attribute name="item" value="#{item}"/>
						                   	</a4j:commandLink>
						                </rich:column>
									</rich:dataTable>
								</rich:column>
								<rich:column width="50">
								</rich:column>
								<rich:column>
									<rich:dataTable id="tblPreview" 
								        sortMode="single" 
							            var="item" 
							            value="#{confArchivoController.listaMuestraPrevia}"  
										rowKeyVar="rowKey" 
										width="300px"
										rows="1" 
										align="center">
										<rich:column style="text-align: center;" width="100px">
							            	<f:facet name="header">
							            		<h:outputText value="Fecha"/>
							            	</f:facet>
							            	<h:outputText value="#{confArchivoController.fechaActual}">
							            		<f:convertDateTime pattern="MMMM-yyyy" />
							            	</h:outputText>
							        	</rich:column>
							        	<rich:column style="text-align: center;" width="200px">
							            	<f:facet name="header">
							            		<h:outputText value="Muestra Previa"/>							            		
							            	</f:facet>
							            	<h:outputText value="#{item}"/>
							        	</rich:column>
							        </rich:dataTable>
								</rich:column>
							</h:panelGrid>
              			</rich:column> 
                    </h:panelGrid>
                    
                    <h:panelGrid columns="5">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Contenido de Archivo : "/>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:170px">
                   			<h:outputText value="Tipo de Dato : "/>
                   			<h:selectOneMenu
								value="#{confArchivoController.confDetalle.intParaTipoDatoCod}"
								disabled="#{confArchivoController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODATOARCHIVO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
							</h:selectOneMenu>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:210px">
                   			<h:outputText value="Completar : "/>
                   			<h:selectOneMenu
								style="width: 140px;"
								value="#{confArchivoController.confDetalle.intParaTipoCompletaCod}"
								disabled="#{confArchivoController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_COMPLETARVALORES}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
							</h:selectOneMenu>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:110px">
                   			<h:outputText value="Tamaño : "/>
                   			<h:inputText size="5" value="#{confArchivoController.confDetalle.intTamano}"
                   				disabled="#{confArchivoController.deshabilitarNuevo}"
                   				onkeypress="return onlyNumbers(event)">                   				
                   			</h:inputText>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:160px">
                   			<h:outputText value="Alineación : "/>
                   			<h:selectOneMenu
								style="width: 80px;"
								value="#{confArchivoController.confDetalle.intParaTipoAlineacionCod}"
								disabled="#{confArchivoController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ALINEACIONDECAMPO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
							</h:selectOneMenu>
                     	</rich:column>
                    </h:panelGrid>
                    
                    <script type="text/javascript">
						function onlyNumbers(evt)
						{
						    var e = (window.event)?event:evt; // for cross browser compatibility
						    var charCode = e.which || e.keyCode;
						    if (charCode > 31 && (charCode < 48 || charCode > 57)){
						        return false;
						    }
						    return true;
						}
					</script>
					
                    <rich:spacer height="8px"/>
                    
                    <h:panelGrid columns="3">
			        	<rich:column style="width:130px">
			        	</rich:column>
			        	
			        	<rich:column>
			        	<rich:panel style="width:520px" id="panelConfDetalle">
                     		<h:panelGrid columns="2">
			                	<rich:column>
			                		<h:dataTable
			                			id="tblValoresB"
			                			var="item" 
						                value="#{confArchivoController.listaConfDetalleValor}"
						                rows="5"
						                width="120px">
										<h:column>
                        					<h:selectBooleanCheckbox 
                        						value="#{item.checked}"
                        						disabled="#{confArchivoController.deshabilitarNuevo}">
                        						 <a4j:support event="onclick" actionListener="#{confArchivoController.seleccionarConfDetalle}"
                        						 	reRender="panelConfDetalle">
                        						 	<f:attribute name="item" value="#{item}"/>
                        						 </a4j:support>
                        					</h:selectBooleanCheckbox>
						                </h:column>
										<h:column>
						                	<h:outputText value="#{item.strDescripcion}"></h:outputText>                        	
						                </h:column>
			                		</h:dataTable>				                	
			                	</rich:column>
			                	<rich:column id="colCampos">
			                		<h:panelGrid columns="2">
					                	<rich:column style="text-align: right;width:160px">
						                	<h:outputText value="Campo : "/>
								        	<h:inputText size="13" value="#{confArchivoController.confDetalle.strValorCampo}"
								        		rendered="#{confArchivoController.habilitarCampo}"
								        		disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarCampo || !confArchivoController.habilitarConfDetalleFijo}"/>
								        	<h:inputText size="13" value="#{confArchivoController.confDetalle.strValorCampo}"
								        		rendered="#{!confArchivoController.habilitarCampo}"
								        		style="background-color: #BFBFBF;"
								        		disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarCampo || !confArchivoController.habilitarConfDetalleFijo}"/>
						              	</rich:column>
						              	<rich:column style="text-align: right;width:200px">
						                	<h:outputText value="Dato : "/>
								        	<h:inputText size="25"  value="#{confArchivoController.confDetalle.strDatoFijo}"
								        		disabled="#{confArchivoController.deshabilitarNuevo  || !confArchivoController.habilitarConfDetalleFijo}"/>
						              	</rich:column> 
					              	</h:panelGrid>
					                <h:panelGrid columns="2">
					                	<rich:column style="text-align: right;width:160px">
						                	<h:outputText value="Campo : "/>
								        	<h:inputText size="13" value="#{confArchivoController.confDetalleAux.strValorCampo}"
								        		rendered="#{confArchivoController.habilitarCampo}"
								        		disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarCampo || !confArchivoController.habilitarConfDetalleVariable}"/>
								        	<h:inputText size="13" value="#{confArchivoController.confDetalleAux.strValorCampo}"
								        		rendered="#{!confArchivoController.habilitarCampo}"
								        		style="background-color: #BFBFBF;"
								        		disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarCampo || !confArchivoController.habilitarConfDetalleVariable}"/>
						              	</rich:column>
						              	<rich:column style="text-align: right;width:200px">
						                	<h:outputText value="Dato : "/>
								        	<h:selectOneMenu
												style="width: 150px;"
												value="#{confArchivoController.confDetalle.intParaTipoDatoVariableCod}"
												disabled="#{confArchivoController.deshabilitarNuevo || !confArchivoController.habilitarConfDetalleVariable}">
												<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODATOVARIABLE}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
													tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
											</h:selectOneMenu>
						              	</rich:column>
					             	</h:panelGrid>
			                	</rich:column>
		                	</h:panelGrid>
		                </rich:panel>
			        	</rich:column>			        	
			        	
			            <rich:column>
			            	<a4j:commandButton styleClass="btnEstilos" value="Agregar"
	                    		action="#{confArchivoController.agregarConfDetalle}" reRender="panelTipoDatos,panelMensaje,panelEjemplo"
	                    		disabled="#{confArchivoController.deshabilitarNuevo}"/>
			           	</rich:column>
		          	</h:panelGrid>
                    
                    <rich:spacer height="4px"/>
                    
                    <h:panelGroup id="panelTipoDatos">
                   			<rich:dataTable id="tblTipoDatos" 
							    sortMode="single" 
						        var="item"
						        value="#{confArchivoController.listaConfDetalle}"  
								rowKeyVar="rowKey" 
								width="800px" 
								rows="#{fn:length(confArchivoController.listaConfDetalle)}" 
								align="center">
								<rich:column width="30px" style="text-align: center">
                    				<h:outputText value="#{rowKey + 1}"/>                        	
                    			</rich:column>
								<rich:column width="90px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Tipo de Dato"/>
						            </f:facet>
						            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODATOARCHIVO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaTipoDatoCod}"/>
						        </rich:column>
						        <rich:column width="80px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Valor"/>
						            </f:facet>
						             <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaTipoValorCod}"/>
						        </rich:column>
						        <rich:column width="100px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Campo"/>						            						            	
						            </f:facet>
						            <h:outputText value="#{item.strValorCampo}"/>	
						        </rich:column>
						        <rich:column width="130px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Dato"/>
						            </f:facet>
						            <h:panelGroup rendered="#{item.intParaTipoValorCod==applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO_FIJO}">
						            	<h:outputText value="#{item.strDatoFijo}"/>
						            </h:panelGroup>
						            <h:panelGroup rendered="#{item.intParaTipoValorCod==applicationScope.Constante.PARAM_T_TIPOVALORARCHIVO_VARIABLE}">
						            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODATOVARIABLE}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intParaTipoDatoVariableCod}"/>
						            </h:panelGroup>
						        </rich:column>
						        <rich:column width="80px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Tamaño"/>
						            </f:facet>
						            <h:outputText value="#{item.intTamano}"/>
						        </rich:column>
						        <rich:column width="80px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Alineación"/>
						            </f:facet>
						             <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ALINEACIONDECAMPO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaTipoAlineacionCod}"/>
						        </rich:column>
						        <rich:column width="130px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Completar"/>
						            </f:facet>
						             <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_COMPLETARVALORES}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.intParaTipoCompletaCod}"/>
						        </rich:column>
						        <rich:column width="80px" style="text-align: center">
						        	<f:facet name="header">
						            	<h:outputText value="Opción"/>
						            </f:facet>
						            <a4j:commandLink value="Eliminar"
						            	actionListener="#{confArchivoController.eliminarConfDetalle}"
						            	reRender="panelTipoDatos,panelEjemplo"
						            	disabled="#{confArchivoController.deshabilitarNuevo}">
						            	<f:attribute name="item" value="#{item}"/>
				                   	</a4j:commandLink>
						        </rich:column>
						</rich:dataTable>
                    </h:panelGroup>
                    
                    <rich:spacer height="4px"/>
                    
                    <rich:panel id="panelEjemplo" style="text-align: center;border:0px;width=100%;overflow: scroll;" >
	                    <rich:dataTable 
							value="#{confArchivoController.listaEjemploAux}" 
							var="item"
							rows="1" 
							align="center"
							style="border:1px solid #000; padding:5px">
				          	<f:facet name="header">
								<h:outputText value="Muestra Previa" />
							</f:facet>
							<rich:columns value="#{confArchivoController.listaEjemplo}" 
								var="columns"  index="ind"
								style="border:1px solid #000; padding:5px;text-align: center">				          		
				          		<h:outputText value="#{item[ind]}"/>
				          	</rich:columns>
						</rich:dataTable>						
					</rich:panel>
		       	</h:panelGroup>		          	 	
		  	</rich:panel>
		</rich:panel>

</rich:panel>
</h:form>