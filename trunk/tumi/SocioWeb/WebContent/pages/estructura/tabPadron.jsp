<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi   -->
	<!-- Autor     : Arturo Julca    	-->
	<!-- Modulo    :                  	-->
	<!-- Prototipo :  					-->			
	<!-- Fecha     :                	-->

<h:form id="frmEstrucOrg">
	<rich:panel style="border:1px solid #17356f;">
		<rich:panel styleClass="rich-tabcell-noborder">
        	
        	 <h:panelGrid columns="2">
				<rich:column width="370">
				</rich:column>
				<rich:column>
	         		<h:outputText style="font-weight:bold" value="Administración de Padrones"/>
	        	</rich:column>
			</h:panelGrid>
			
        	<h:panelGrid columns="9">
         		<rich:column style="width: 110px">
         			<h:outputText value="Tipo de Registro : "/>
         		</rich:column>
            	<rich:column>
					<h:selectOneMenu id="cboTipoRegistro" 
						style="width: 120px;"
						value="#{padronController.intTipoBusqueda}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_REGISTRO_PADRON}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange"  action="#{padronController.cambioTipoRegistro}" 
							reRender="colEstadoDocumentoLabel,colEstadoDocumentoCombo"  />
					</h:selectOneMenu>
             	</rich:column>
                <rich:column style="width: 60px">
                	<h:outputText value="Periodo : "/>
               	</rich:column>
                <rich:column>
					<h:selectOneMenu
						style="width: 90px;"
						value="#{padronController.padronFiltro.id.intMes}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"/>
					</h:selectOneMenu>
				</rich:column>
                <rich:column>
					<h:selectOneMenu
						style="width: 90px;"
						value="#{padronController.padronFiltro.id.intPeriodo}">						
						<tumih:selectItems var="sel" 
							value="#{padronController.listaAnios}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                </rich:column>
                <rich:column width="100">
                 	<h:outputText value="Tipo de Planilla : "/>
				</rich:column>
              	<rich:column>
					<h:selectOneMenu
						style="width: 100px;"
						value="#{padronController.padronFiltro.id.intParaModalidadCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="70" style="text-align: center;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="cboEstadoRegistro" 
						style="width: 100px;"
						value="#{padronController.padronFiltro.intParaEstadoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
                </rich:column>           
         	</h:panelGrid>
         		
         	<h:panelGrid columns="6">
            	<rich:column width="110">
                	<h:outputText value="Nivel de Entidad : "/>
              	</rich:column>
                <rich:column>
					<h:selectOneMenu id="cboNivelEntidad" 
						style="width: 120px;"
			        	value="#{padronController.padronFiltro.id.intNivel}">
			          	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
               	</rich:column>
                <rich:column>
                	<h:inputText size="46" value="#{padronController.padronFiltro.estructura.juridica.strSiglas}"/>
            	</rich:column>
            	<rich:column id="colEstadoDocumentoLabel" width="100">
                	<h:outputText value="Estado Solicitud : " rendered="#{padronController.mostrarComboDocumento}"/>
                </rich:column>
                <rich:column id="colEstadoDocumentoCombo" width="100">
		        	<h:selectOneMenu 
		        		style="width: 100px;"
						rendered="#{padronController.mostrarComboDocumento}"
						value="#{padronController.padronFiltro.solicitudPagoDetalle.solicitudPago.intParaEstadoPagoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>								
					</h:selectOneMenu>	
               	</rich:column>
             	<rich:column>
                	<a4j:commandButton styleClass="btnEstilos" value="Buscar"
                    	actionListener="#{padronController.buscar}"	 
                    	reRender="panelTablaPadrones"/>
             	</rich:column>
                
           	</h:panelGrid>
			
			
                
                <h:panelGrid id="panelTablaPadrones">
	          		<rich:extendedDataTable id="tblPadrones" 
	          			enableContextMenu="false" 
	          			sortMode="single" 
                    	var="item" 
                    	value="#{padronController.listaRegistros}"  
					  	rowKeyVar="rowKey" 
					  	rows="5" 
					  	width="925px" 
					  	rendered="#{padronController.mostrarGrillaPadrones}"
					  	height="165px" 
					  	align="center">
                                
					  	<rich:column width="27px" style="text-align: center">
                        	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                      	</rich:column>
                        <rich:column width="100px" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText  value="Tipo Archivo"/>
                          	</f:facet>
                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOPADRON}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaTipoArchivoPadronCod}"/>
                      	</rich:column>
                        <rich:column width="100" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Entidad"></h:outputText>
                          	</f:facet>
                            <h:outputText value="#{item.estructura.juridica.strSiglas}"/>
                       	</rich:column>
                        <rich:column width="70" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Periodo"></h:outputText>
                            </f:facet>
                        	<h:outputText value="#{item.id.intMes}"></h:outputText>-<h:outputText value="#{item.id.intPeriodo}"></h:outputText>                                    
                        </rich:column>
                        <rich:column width="100" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Tipo Plantilla"></h:outputText>
                            </f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaModalidadCod}"/>                          
                        </rich:column>
                        <rich:column width="100" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Tipo de Socio"></h:outputText>
                            </f:facet>               
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaTipoSocioCod}"/>                     
                       	</rich:column>
                        <rich:column width="70" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Estado"></h:outputText>
                          	</f:facet>                            
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstadoCod}"/>
                      	</rich:column>                                
                        <rich:column width="110" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Fecha Proceso"></h:outputText>                            	
                        	</f:facet>
                        	<h:outputText value="#{item.adPaFechaRegistro}">
                            	<f:convertDateTime pattern="dd-MM-yyyy" />
                            </h:outputText>
                       	</rich:column>
                        <rich:column width="140" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Usuario"></h:outputText>
                           	</f:facet>                           	
                            <h:outputText rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}"/>
                            <h:outputText rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" value="#{item.persona.juridica.strRazonSocial}"/>
                     	</rich:column>
						<rich:column width="100" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Solicitud Pago"></h:outputText>
                          	</f:facet>
                          	<h:outputText value="#{item.solicitudPagoDetalle.id.intNumero}"/>
                   		</rich:column>
						<f:facet name="footer">   
							<rich:datascroller for="tblPadrones" maxPages="10"/>   
						</f:facet>
						<a4j:support event="onRowClick"  
							actionListener="#{padronController.preEliminarRegistro}"
							reRender="panelEliminarRegistroPadron" 
							oncomplete="Richfaces.showModalPanel('pEliminarRegistro')">
                        		<f:attribute name="item" value="#{item}"/>
                        </a4j:support>
              		</rich:extendedDataTable>
              		
              		<rich:extendedDataTable id="tblPadrones22" 
	          			rendered="#{!padronController.mostrarGrillaPadrones}"
	          			enableContextMenu="false" 
	          			sortMode="single" 
                    	var="item" 
                    	value="#{padronController.listaRegistros}"  
					  	rowKeyVar="rowKey" 
					  	rows="5" 
					  	width="920px" 
					  	height="165px" 
					  	align="center">
                                
					  	<rich:column width="30px" style="text-align: center">
                        	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                      	</rich:column>
                        <rich:column width="100px" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText  value="Tipo Archivo"/>
                          	</f:facet>
                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOPADRON}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaTipoArchivoPadronCod}"/>							
                      	</rich:column>
                        <rich:column width="100" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Entidad"></h:outputText>
                          	</f:facet>
                            <h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
                       	</rich:column>
                        <rich:column width="70" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Periodo"></h:outputText>
                            </f:facet>
                        	<h:outputText value="#{item.id.intMes}-#{item.id.intPeriodo}"></h:outputText>                                    
                        </rich:column>
                        <rich:column width="100" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Tipo Plantilla"></h:outputText>
                            </f:facet>
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaModalidadCod}"/>
                        </rich:column>
                        <rich:column width="100" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Tipo Socio"></h:outputText>
                            </f:facet>
                           	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.id.intParaModalidadCod}"/>
                       	</rich:column>
                        <rich:column width="70" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Estado"></h:outputText>
                          	</f:facet>
                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intParaEstadoCod}"/>
                      	</rich:column>                                
                        <rich:column width="100" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Fecha Proceso"></h:outputText>                            	
                        	</f:facet>
                        	<h:outputText value="#{item.adPaFechaRegistro}">
                            	<f:convertDateTime pattern="dd-MM-yyyy" />
                            </h:outputText>
                       	</rich:column>
                        <rich:column width="140" style="text-align: center">
                         	<f:facet name="header">
                            	<h:outputText value="Usuario"></h:outputText>
                           	</f:facet>                           	
                            	<h:outputText value="#{item.intPersUsuarioRegistroPk}"/>
                     	</rich:column>
						<rich:column width="100" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Solicitud Pago"></h:outputText>
                          	</f:facet>
                          	<h:outputText value="#{item.solicitudPagoDetalle.id.intNumero}"/>
                   		</rich:column>
                   		<rich:column width="70" style="text-align: center">
                        	<f:facet name="header">
                            	<h:outputText value="Estado Solicitud"></h:outputText>
                          	</f:facet>                          	
							<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODOCUMENTO}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.solicitudPagoDetalle.solicitudPago.intParaEstadoPagoCod}"/>
                   		</rich:column>
						<f:facet name="footer">   
							<rich:datascroller for="tblPadrones" maxPages="10"/>   
						</f:facet>
						<a4j:support event="onRowClick"  
							actionListener="#{padronController.preEliminarRegistro}"
							reRender="panelEliminarRegistroPadron"
							oncomplete="Richfaces.showModalPanel('pEliminarRegistro')">
                        		<f:attribute name="item" value="#{item}"/>
                        </a4j:support>
              		</rich:extendedDataTable>	
	          	</h:panelGrid>
	          			
				<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
					<a4j:commandLink action="#">
						<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
					</a4j:commandLink>
					<h:outputText value="Para Eliminar hacer click en el registro" style="color:#8ca0bd"/>
				</h:panelGrid>
				
			</rich:panel>
		
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{padronController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{padronController.mostrarMensajeExito}"/>
			<h:outputText value="#{padronController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{padronController.mostrarMensajeError}"/>	
		</h:panelGroup>
		
		
		<rich:panel style="border:none"	id="contPanelInferior">
		
			<h:panelGrid columns="4">
				<a4j:commandButton value="Ingresar Padrón" actionListener="#{padronController.habilitarIngresarPadron}" 
			    	styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje" style="width:110px"/>                     
			    <a4j:commandButton value="Nueva Solicitud de Pago" actionListener="#{padronController.habilitarNuevaSolicitudPago}" 
			        styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje" style="width:150px"/>												                 
			    <a4j:commandButton value="Procesar" actionListener="#{padronController.procesar}" 
			        styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje,panelTablaPadrones" style="width:110px"
			        disabled="#{!padronController.habilitarProcesar}"/>
			    <a4j:commandButton value="Cancelar" actionListener="#{padronController.cancelar}" 
			        styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje"style="width:110px"/>
			</h:panelGrid>
		<%-- </rich:panel>--%>		        		 
		          	
		<%--<rich:panel id="contPanelInferior" style="border:0px;">--%>
			<rich:panel id="panelInferior" rendered="#{padronController.panelInferiorRendered}" 
				style="border:1px solid #17356f;background-color:#DEEBF5;">		
				          	 	
		    	<h:panelGroup id="panelIngresarPadron" rendered="#{padronController.ingresarPadronRendered}">
		        	<h:panelGrid columns="3" border="0">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Periodo : "/>
                     	</rich:column>
                        <rich:column>
                        	<h:selectOneMenu id="cboPeriodoMesIngresoPadron" 
								style="width: 160px;"
								disabled="#{padronController.deshabilitarPadron}"
								value="#{padronController.adminPadronNuevo.id.intMes}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									propertySort="intOrden"/>
						</h:selectOneMenu>
						</rich:column>
                       	<rich:column>
							<h:selectOneMenu
								style="width: 100px;"
								value="#{padronController.adminPadronNuevo.id.intPeriodo}"
								disabled="#{padronController.deshabilitarPadron}">
	                        	<tumih:selectItems var="sel" 
									value="#{padronController.listaAnios}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>                        	
                        </rich:column>                        		                    	
                    </h:panelGrid>
                    
                    <h:panelGrid columns="4" border="0">
                    	<rich:column style="width:110px">
                        	<h:outputText value="Nivel de Entidad : "/>
                        </rich:column>
                  		<rich:column>
                  			<h:selectOneMenu id="cboNivelEntidadIngresoPadron" 
                  				style="width: 160px;"
			        			value="#{padronController.adminPadronNuevo.id.intNivel}"
			        			disabled="#{padronController.deshabilitarPadron}">
			      				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange"  action="#{padronController.cambioNivelEntidadPanelInferior}" 
									reRender="cajaNombreEntidad"/>
							</h:selectOneMenu>
						</rich:column>
	                    <rich:column id="colInfNombreEntidad">
							<h:inputText value="#{padronController.adminPadronNuevo.estructura.juridica.strRazonSocial}" 
								size="55" id="cajaNombreEntidad" 
								readonly="true" disabled="#{padronController.deshabilitarPadron}"/>									
	                    </rich:column>
	                  	<rich:column>
							<a4j:commandButton id="btnBuscarEntidad" 
								actionListener="#{padronController.buscarEntidades}"
								value="Buscar" 
					    		oncomplete="Richfaces.showModalPanel('pBuscarEntidadPadron')"
					    		reRender="pgListaEstructurasPadron"
					    		disabled="#{padronController.deshabilitarPadron}" 
					    		styleClass="btnEstilos">
				          	 </a4j:commandButton>
	                  	</rich:column>                        		                    	
                   	</h:panelGrid>
                   	
                    <h:panelGrid columns="6" border="0">
                  		<rich:column style="width:110px">
                        	<h:outputText value="Tipo de Archivo : "/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu id="cboTipoArchivo" 
                        		style="width: 160px;"
			                	value="#{padronController.adminPadronNuevo.id.intParaTipoArchivoPadronCod}"
			                	disabled="#{padronController.deshabilitarPadron  || !padronController.habilitarComboTipoArchivo}">
			                    <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOPADRON}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
                        <rich:column style="width:80px">
                        	<h:outputText value="Modalidad : "/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu id="cboModalidadIngresoPadron" style="width: 100px;"
					        	disabled="#{padronController.deshabilitarPadron}"
					        	value="#{padronController.adminPadronNuevo.id.intParaModalidadCod}">
					      		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange"  action="#{padronController.cambioTipoModalidadPanelInf}" reRender="colTipoMod"  />								
							</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="text-align: center;width:100px">
                        	<h:outputText value="Tipo Socio : "/>
                        </rich:column>
                        <rich:column id="colTipoMod" style="text-align: left">
                        	<h:selectOneMenu id="cboTipoSocioIngresoPadron" style="width: 100px;"
					        	value="#{padronController.adminPadronNuevo.id.intParaTipoSocioCod}"
					        	disabled="#{padronController.deshabilitarPadronSocio}">
					      		<tumih:selectItems var="sel" 
					      			cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
                        </rich:column>                        		                       		                    	
                    </h:panelGrid>
                    
                    <h:panelGroup id="panelInferiorInterno">
	                	<h:panelGrid columns="3" border="0" rendered="#{padronController.ingresarContraseña}">
	                    	<rich:column style="width:110px">
	                        	<h:outputText value="Contraseña : "/>
	                        </rich:column>
	                        <rich:column>
								<h:inputSecret value="#{padronController.passwordIngresado.strContrasena}" size="27"/>
	                        </rich:column>
	                        <rich:column>
								<a4j:commandButton value="Validar" actionListener="#{padronController.validarContraseña}" 
		                 			styleClass="btnEstilos" reRender="contPanelInferior" style="width:110px;"/>
	                        </rich:column>                        		                    	
	                    </h:panelGrid>
	                  	<h:panelGrid columns="3" border="0" rendered="#{padronController.seleccionarArchivoRendered}">
	                    	<rich:column>
		                    	<rich:fileUpload id="uploadDBF" 
	            					addControlLabel="Adjuntar Archivo"
								    clearControlLabel="Limpiar" 
								    cancelEntryControlLabel="Cancelar"
								    uploadControlLabel="Subir Archivo" 
								    listHeight="65" 
								    listWidth="320"
								    fileUploadListener="#{padronController.manejarSubirArchivo}"
									maxFilesQuantity="1"
									doneLabel="Archivo cargado correctamente"
									immediateUpload="false"
									disabled="#{padronController.deshabilitarPadron}"
									acceptedTypes="dbf,DBF">
									<f:facet name="label">
										<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
									</f:facet>
									<a4j:support event="onuploadcomplete" reRender="cboTipoArchivo"/>								
								</rich:fileUpload>
	                        </rich:column>
	                        <rich:column style="width:110px">
	                        	<h:outputText value="Tipo de Padron : "/>
	                        </rich:column>
	                        <rich:column>
								<h:selectOneMenu id="cboTipoPadron"  
									style="width: 120px;"
									value="#{padronController.adminPadronNuevo.intParaTipoPadronCod}"
									disabled="#{padronController.deshabilitarPadron}">
                        			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_PADRON}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        		</h:selectOneMenu>
	                        </rich:column>	                        		                        		                    	
	                    </h:panelGrid>
                    </h:panelGroup>                    		
		     	</h:panelGroup>
		          	 	
		        <h:panelGroup id="panelNuevaSolPago" rendered="#{padronController.nuevaSolicitudPagoRendered}">
		        	
		        	<h:panelGrid columns="3" border="0">
                    	<rich:column style="width:120px">
                        	<h:outputText value="Periodo : "/>
                        </rich:column>
                        <rich:column>
							<h:selectOneMenu id="cboMesSolPago" 
								value="#{padronController.adminPadronFiltroSolicitud.id.intMes}"
								style="width: 160px;"
								disabled="#{padronController.deshabilitarSolicitud}">
                        		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									propertySort="intOrden"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
							<h:selectOneMenu id="cboAñoSolPago" 
								style="width: 100px;"
								value="#{padronController.adminPadronFiltroSolicitud.id.intPeriodo}"
								disabled="#{padronController.deshabilitarSolicitud}">
                        		<tumih:selectItems var="sel" 
									value="#{padronController.listaAnios}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
                        </rich:column>                        		                    	
                    </h:panelGrid>
                    
                    <h:panelGrid columns="2" border="0">
                   		<rich:column style="width:120px">
                        	<h:outputText value="Padron Pendiente : "/>
                        </rich:column>
                        <rich:column>
							<a4j:commandButton id="btnBuscarAdminPadron398" value="Agregar"
								actionListener="#{padronController.buscarPadronesSinSolicitud}"	
					    		oncomplete="Richfaces.showModalPanel('pBuscarAdminPadron')"
					    		styleClass="btnEstilos" reRender="pgListaAdminPadrones,panelNuevaSolPago"
					    		disabled="#{padronController.deshabilitarSolicitud}">
				          	</a4j:commandButton>
                        </rich:column>                        		                    	
                    </h:panelGrid>
                    
                    <h:panelGrid columns="3" border="0">
                    	<rich:column style="width:120px">
                        	<h:outputText value="Monto de Solicitud : "/>
                        </rich:column>
                        <rich:column>
							<h:selectOneMenu id="cboMoneda" 
								style="width: 160px;"
		   					    disabled="#{padronController.deshabilitarSolicitud}"
								value="#{padronController.solicitudPagoNuevo.intParaMonedaCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                        </rich:column>                        
                        <rich:column>
							<script language=Javascript>
						       function isNumberKey(evt)
						       {
						          var charCode = (evt.which) ? evt.which : event.keyCode
						          if (charCode != 46 && charCode > 31 
						            && (charCode < 48 || charCode > 57))
						             return false;
						
						          return true;
						       }
						    </script>
					    	<h:inputText size="15" value="#{padronController.solicitudPagoNuevo.bdMonto}"  
								disabled="#{padronController.deshabilitarSolicitud}"
								onkeypress="return isNumberKey(event)"/>
                        </rich:column>  		                    	
                    </h:panelGrid>
                    
		       		<h:panelGrid id="panelPadronesSinSolicitud">
		       			<rich:extendedDataTable id="tblPadronesSinSolicitud" enableContextMenu="false" 
		       				sortMode="single"  var="item" 
		       				value="#{padronController.listaAdminPadronesParaSolicitudSelec}"  
					  		rowKeyVar="rowKey" rows="5" width="600px" 
					  		height="120px">                                
					  		<rich:column width="29px">
                        		<h:outputText value="#{rowKey + 1}"></h:outputText>
	                      	</rich:column>					  		 
	                        <rich:column width="140">
	                        	<f:facet name="header">
	                            	<h:outputText  value="Tipo de Archivo"></h:outputText>
	                          	</f:facet>
	                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOPADRON}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.id.intParaTipoArchivoPadronCod}"/>
	                      	</rich:column>
	                        <rich:column width="140">
	                        	<f:facet name="header">
	                            	<h:outputText value="Entidad"></h:outputText>
	                          	</f:facet>
	                            <h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
	                       	</rich:column>
	                        <rich:column width="90">
	                        	<f:facet name="header">
	                            	<h:outputText value="Periodo"></h:outputText>
	                            </f:facet>
	                        	<h:outputText value="#{item.id.intMes}-#{item.id.intPeriodo}"></h:outputText>                                      
	                        </rich:column>
	                        <rich:column width="110">
	                         	<f:facet name="header">
	                            	<h:outputText value="Tipo de Plantilla"></h:outputText>
	                            </f:facet>
	                            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.id.intParaModalidadCod}"/>                                    
	                        </rich:column>
	                        <rich:column width="90">
                         		<f:facet name="header">
                            		<h:outputText value="Opción"></h:outputText>
                            	</f:facet>
                            	<a4j:commandButton value="Eliminar" 
                            		styleClass="btnEstilos"
                            		reRender="panelPadronesSinSolicitud"
                            		disabled="#{padronController.deshabilitarSolicitud}"
                            		actionListener="#{padronController.eliminarFilaPadron}">
                            		<f:attribute name="item" value="#{item}"/>
                            	</a4j:commandButton>
                       		</rich:column>
                        </rich:extendedDataTable>
		       		</h:panelGrid>
		       	</h:panelGroup>		          	 	
		          	 	
		  	</rich:panel>
		</rich:panel>		
		 	          	  
</rich:panel>
</h:form> 