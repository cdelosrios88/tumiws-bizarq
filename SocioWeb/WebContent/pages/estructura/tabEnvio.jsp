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

<h:form id="frmConfiguracion">
<rich:panel style="border:1px solid #17356f;">		
		
		<rich:panel styleClass="rich-tabcell-noborder">
		
	        <h:panelGrid columns="2">
				<rich:column width="370">
				</rich:column>
				<rich:column>
	         		<h:outputText style="font-weight:bold" value="Archivos de Envio"/>
	        	</rich:column>
			</h:panelGrid>
			
        	<h:panelGrid columns="7">
         		<rich:column style="width: 120px">
         			<h:outputText value="Tipo de Entidad : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu 
						style="width: 125px;"
						value="#{configuracionController.configuracionFiltro.confEstructura.intSociNivelPk}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column>
                	<h:inputText size="29" value="#{configuracionController.strSiglasFiltro}"/>
              	</rich:column>
                <rich:column style="text-align: center;width: 80px;">
         			<h:outputText value="Modalidad : "/>
         		</rich:column>
         		<rich:column>
         			<h:selectOneMenu
						style="width: 90px;"
						value="#{configuracionController.configuracionFiltro.confEstructura.intParaModalidadCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						<a4j:support event="onchange"  action="#{configuracionController.cambioTipoModalidadBus}" reRender="colBusTipoSocio"  />			
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="text-align: center;width: 100px;">
         			<h:outputText value="Tipo de Socio : "/>
         		</rich:column>
         		<rich:column id="colBusTipoSocio">
		        	<h:selectOneMenu
						style="width: 90px;"
						disabled="#{!configuracionController.habilitarTipoSocioBus}"
						value="#{configuracionController.configuracionFiltro.confEstructura.intParaTipoSocioCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
					</h:selectOneMenu>
              	</rich:column>              	
            </h:panelGrid>
            
            <h:panelGrid columns="5">
              	<rich:column style="width: 120px">
         			<h:outputText value="Formato de Archivo : "/>
         		</rich:column>
              	<rich:column>
		        	<h:selectOneMenu 
						style="width: 125px;"
						value="#{configuracionController.configuracionFiltro.intParaFormatoArchivoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FORMATOARCHIVOS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
              	</rich:column>
         		<rich:column style="text-align: center;width: 50px;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column>
         			<h:selectOneMenu
						style="width: 110px;"
						value="#{configuracionController.configuracionFiltro.intParaEstadoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
         		</rich:column>
         		<rich:column style="text-align: right; width:87px">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaConfiguracion"
                    	action="#{configuracionController.buscar}"/>
            	</rich:column>
         	</h:panelGrid>         	
         	         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaConfiguracion">
	        	<rich:extendedDataTable id="tblConfiguraciones" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{configuracionController.listaConfiguracion}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="925px" 
					height="170px" 
					align="center">
                                
					<rich:column width="26px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="55px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código" style="text-align: center"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemConfiguracion}"/>
                	</rich:column>
                	<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Entidad"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.confEstructura.intSociNivelPk}"/>
                  	</rich:column>
                  	<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Entidad"/>
                      	</f:facet>
                      	<h:outputText value="#{item.confEstructura.strSiglas}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Socio"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.confEstructura.intParaTipoSocioCod}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Modalidad"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.confEstructura.intParaModalidadCod}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Formato"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FORMATOARCHIVOS}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaFormatoArchivoCod}"/>
                  	</rich:column>
                    <rich:column width="70" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>                        
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstadoCod}"/>
                  	</rich:column>
                    <rich:column width="130" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                    	</f:facet>
                    	<h:outputText value="#{item.tsFechaRegistro}">
                            	<f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="160" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Usuario"/>
                     	</f:facet>
                     	<h:outputText value="#{item.intPersPersonaUsuarioPk} - #{item.natural.strNombres} #{item.natural.strApellidoPaterno} #{item.natural.strApellidoMaterno}"/>
                  	</rich:column>
                    <f:facet name="footer">
						<rich:datascroller for="tblConfiguraciones" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
							actionListener="#{configuracionController.seleccionarRegistro}"
							reRender="frmAlertaRegistro,contPanelInferior"
							oncomplete="if(#{configuracionController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
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
			
		</rich:panel>
				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{configuracionController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{configuracionController.mostrarMensajeExito}"/>
			<h:outputText value="#{configuracionController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{configuracionController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
				 		
		<rich:panel style="border:none"	id="contPanelInferior">
		
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{configuracionController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{configuracionController.grabar}" reRender="contPanelInferior,panelMensaje"
			    	disabled="#{!configuracionController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{configuracionController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje"/>      
			</h:panelGrid>
		<%--</rich:panel>--%>	        	
		
		          	
		<%--<rich:panel id="contPanelInferior" style="border:0px;">--%>
			<rich:panel id="panelInferior" rendered="#{configuracionController.mostrarPanelInferior}" style="border:1px solid #17356f;background-color:#DEEBF5;">		          	 	
		    	<h:panelGroup id="panelIngresarConfiguracion">
		        	
		        	<h:panelGrid columns="4">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Tipo de Entidad : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu
								style="width: 120px;"
								disabled="#{configuracionController.deshabilitarNuevo}"
								value="#{configuracionController.confEstructura.intSociNivelPk}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
              			</rich:column>
              			<rich:column>
                   			<h:inputText id="cajaNombreEstructura" size="25" readonly="true"
                   				value="#{configuracionController.estructuraSeleccionada.juridica.strRazonSocial }"/>
                     	</rich:column>
                     	<rich:column>
		                	<a4j:commandButton id="btnBuscarEntidad" 
								action="#{configuracionController.buscarEntidades}"
								value="Buscar"
								oncomplete="Richfaces.showModalPanel('pBuscarEntidadEnvio')"
								reRender="pgListaEstructuras"
								disabled="#{configuracionController.deshabilitarNuevo}" 
					    		styleClass="btnEstilos">
				          	 </a4j:commandButton>
              			</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="4">                    	
              			<rich:column style="width:130px">
                   			<h:outputText value="Modalidad : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu
								style="width: 120px;"
								disabled="#{configuracionController.deshabilitarNuevo}"
								value="#{configuracionController.confEstructura.intParaModalidadCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange"  action="#{configuracionController.cambioTipoModalidad}" reRender="colTipoSocio"  />			
							</h:selectOneMenu>
              			</rich:column>
              			<rich:column style="width:100px">
                   			<h:outputText value="Tipo de Socio : "/>
                     	</rich:column>
                     	<rich:column id="colTipoSocio">
		                	<h:selectOneMenu
								style="width: 130px;"
								disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarTipoSocio}"
								value="#{configuracionController.confEstructura.intParaTipoSocioCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>			
							</h:selectOneMenu>
              			</rich:column>
                    </h:panelGrid>
		        	
		        	<h:panelGrid columns="2">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Estado : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu
								style="width: 120px;"
								disabled="#{configuracionController.deshabilitarNuevo || configuracionController.registrarNuevo}"
								value="#{configuracionController.configuracionNuevo.intParaEstadoCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>			
							</h:selectOneMenu>
              			</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="2">
                    	<rich:column style="width:130px">
                   			<h:outputText value="Formato de Archivo : "/>
                     	</rich:column>
                     	<rich:column>
		                	<h:selectOneMenu 
								style="width: 120px;"
								disabled="#{configuracionController.deshabilitarNuevo}"
								value="#{configuracionController.configuracionNuevo.intParaFormatoArchivoCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FORMATOARCHIVOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								<a4j:support event="onchange" action="#{configuracionController.seleccionarFormato}" 
									reRender="colCampos,contPanelInferior,panelEjemplo"  />			
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
						                value="#{configuracionController.listaNombreValor}"
						                rows="2"
						                width="120px">
										<h:column>
                        					<h:selectBooleanCheckbox 
                        						value="#{item.checked}"
                        						disabled="#{configuracionController.deshabilitarNuevo}">
                        						 <a4j:support event="onclick" actionListener="#{configuracionController.seleccionarNombre}"
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
							            	<h:inputText size="18" value="#{configuracionController.nombre.strValorFijo}"
						            		disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarNombreFijo}"/>
					              		</rich:column> 
					              	</h:panelGrid>
					                <h:panelGrid columns="2">
					                	<rich:column style="width: 120px;">
							            	<h:selectOneMenu													
												value="#{configuracionController.nombre.intParaTipoVariableCod}"
												disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarNombreVariable}">
												<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVALORVARIABLEARCHIVO}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
													tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
											</h:selectOneMenu>
					              		</rich:column>
					             	</h:panelGrid>
			                	</rich:column>
			                	<rich:column>
			                	 	<a4j:commandButton styleClass="btnEstilos" value="Agregar"
	                    				action="#{configuracionController.agregarNombre}" reRender="panelNombreResultado,panelMensaje"
	                    				disabled="#{configuracionController.deshabilitarNuevo}"/>
			                	</rich:column>
		                	</h:panelGrid>
		                	</rich:panel>
		                	
		                	<h:panelGrid columns="3" id="panelNombreResultado">
			                	<rich:column>
				                	<rich:dataTable id="tblValores"
							          	sortMode="single" 
						                var="item" 
						                value="#{configuracionController.listaNombre}"  
										rowKeyVar="rowKey" 
										width="320px" 
										rows="#{fn:length(configuracionController.listaNombre)}"
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
						                <rich:column style="text-align: center;" width="80">
						                	<f:facet name="header">
						                    	<h:outputText value="Opciones"/>
						                    </f:facet>
						                    <a4j:commandLink value="Eliminar"
								            	actionListener="#{configuracionController.eliminarNombre}"
								            	reRender="panelNombreResultado"
								            	disabled="#{configuracionController.deshabilitarNuevo}">
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
							            value="#{configuracionController.listaMuestraPrevia}"  
										rowKeyVar="rowKey" 
										width="300px"
										rows="1" 
										align="center">
										<rich:column style="text-align: center;" width="100px">
							            	<f:facet name="header">
							            		<h:outputText value="Fecha"/>
							            	</f:facet>
							            	<h:outputText value="#{configuracionController.fechaActual}">
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
								value="#{configuracionController.confDetalle.intParaTipoDatoCod}"
								disabled="#{configuracionController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODATOARCHIVO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
							</h:selectOneMenu>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:210px">
                   			<h:outputText value="Completar : "/>
                   			<h:selectOneMenu
								style="width: 140px;"
								value="#{configuracionController.confDetalle.intParaTipoCompletaCod}"
								disabled="#{configuracionController.deshabilitarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_COMPLETARVALORES}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
							</h:selectOneMenu>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:110px">
                   			<h:outputText value="Tamaño : "/>
                   			<h:inputText size="5" value="#{configuracionController.confDetalle.intTamano}"
                   				disabled="#{configuracionController.deshabilitarNuevo}"
                   				onkeypress="return onlyNumbers(event)">                   				
                   			</h:inputText>
                     	</rich:column>
                     	<rich:column style="text-align: right;width:160px">
                   			<h:outputText value="Alineación : "/>
                   			<h:selectOneMenu
								style="width: 80px;"
								value="#{configuracionController.confDetalle.intParaTipoAlineacionCod}"
								disabled="#{configuracionController.deshabilitarNuevo}">
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
						                value="#{configuracionController.listaConfDetalleValor}"
						                rows="5"
						                width="120px">
										<h:column>
                        					<h:selectBooleanCheckbox 
                        						value="#{item.checked}"
                        						disabled="#{configuracionController.deshabilitarNuevo}">
                        						 <a4j:support event="onclick" actionListener="#{configuracionController.seleccionarConfDetalle}"
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
								        	<h:inputText size="13" value="#{configuracionController.confDetalle.strValorCampo}"
								        		rendered="#{configuracionController.habilitarCampo}"
								        		disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarCampo || !configuracionController.habilitarConfDetalleFijo}"/>
								        	<h:inputText size="13" value="#{configuracionController.confDetalle.strValorCampo}"
								        		rendered="#{!configuracionController.habilitarCampo}"
								        		style="background-color: #BFBFBF;"
								        		disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarCampo || !configuracionController.habilitarConfDetalleFijo}"/>
						              	</rich:column>
						              	<rich:column style="text-align: right;width:200px">
						                	<h:outputText value="Dato : "/>
								        	<h:inputText size="25"  value="#{configuracionController.confDetalle.strDatoFijo}"
								        		disabled="#{configuracionController.deshabilitarNuevo  || !configuracionController.habilitarConfDetalleFijo}"/>
						              	</rich:column> 
					              	</h:panelGrid>
					                <h:panelGrid columns="2">
					                	<rich:column style="text-align: right;width:160px">
						                	<h:outputText value="Campo : "/>
								        	<h:inputText size="13" value="#{configuracionController.confDetalleAux.strValorCampo}"
								        		rendered="#{configuracionController.habilitarCampo}"
								        		disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarCampo || !configuracionController.habilitarConfDetalleVariable}"/>
								        	<h:inputText size="13" value="#{configuracionController.confDetalleAux.strValorCampo}"
								        		rendered="#{!configuracionController.habilitarCampo}"
								        		style="background-color: #BFBFBF;"
								        		disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarCampo || !configuracionController.habilitarConfDetalleVariable}"/>
						              	</rich:column>
						              	<rich:column style="text-align: right;width:200px">
						                	<h:outputText value="Dato : "/>
								        	<h:selectOneMenu
												style="width: 150px;"
												value="#{configuracionController.confDetalle.intParaTipoDatoVariableCod}"
												disabled="#{configuracionController.deshabilitarNuevo || !configuracionController.habilitarConfDetalleVariable}">
												<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODATOVARIABLEARCHENVIO}" 
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
	                    		action="#{configuracionController.agregarConfDetalle}" reRender="panelTipoDatos,panelMensaje,panelEjemplo"
	                    		disabled="#{configuracionController.deshabilitarNuevo}"/>
			           	</rich:column>
		          	</h:panelGrid>
                    
                    <rich:spacer height="4px"/>
                    
                    <h:panelGroup id="panelTipoDatos">
                   			<rich:dataTable id="tblTipoDatos"
							    sortMode="single" 
						        var="item"
						        value="#{configuracionController.listaConfDetalle}"  
								rowKeyVar="rowKey" 
								width="800px" 
								rows="#{fn:length(configuracionController.listaConfDetalle)}" 
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
						            	actionListener="#{configuracionController.eliminarConfDetalle}"
						            	reRender="panelTipoDatos,panelEjemplo"
						            	disabled="#{configuracionController.deshabilitarNuevo}">
						            	<f:attribute name="item" value="#{item}"/>
				                   	</a4j:commandLink>
						        </rich:column>
						</rich:dataTable>
                    </h:panelGroup>
                    
                    <rich:spacer height="4px"/>
                    
                    <rich:panel id="panelEjemplo" style="text-align: center;border:0px;width=100%;overflow: scroll;">
	                    <rich:dataTable 
							value="#{configuracionController.listaEjemploAux}" 
							var="item"
							rows="1" 
							align="center"
							style="border:1px solid #000; padding:5px">
				          	<f:facet name="header">
								<h:outputText value="Muestra Previa" />
							</f:facet>
							<rich:columns value="#{configuracionController.listaEjemplo}" 
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