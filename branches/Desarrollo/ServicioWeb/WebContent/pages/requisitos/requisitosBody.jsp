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
	<!-- Prototipo :  Archivos de Envio -->
	<!-- Fecha     :                	-->

<a4j:include viewId="/pages/requisitos/popup/estructuraBody.jsp"/>
<a4j:include viewId="/pages/requisitos/popup/productoBody.jsp"/>
<a4j:include viewId="/pages/requisitos/popup/captacionBody.jsp"/>
<a4j:include viewId="/pages/requisitos/popup/alerta.jsp"/>

<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;text-align: left;">
<h:form id="frmRequisitos">
	<h:outputLabel value="#{requisitosController.inicioPage}"/>
		<rich:panel style="border: 0px solid #17356f;" styleClass="rich-tabcell-noborder">
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="REQUISITOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="8">
         		<rich:column style="width: 120px">
         			<h:outputText value="Tipo de Operación : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu
						style="width: 150px;"
						value="#{requisitosController.confServSolicitudFiltro.intParaTipoOperacionCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							propertySort="intOrden"/>			
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="width: 10px;">
         		</rich:column>
                <rich:column style="width: 100px;">
         			<h:outputText value="Tipo de Cuenta : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu
						style="width: 120px;"
						value="#{requisitosController.tipoCuentaFiltro}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTAREQUISITOS}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>		
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="width: 60px;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu
						style="width: 120px;"
						value="#{requisitosController.confServSolicitudFiltro.intParaEstadoCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>		
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="text-align: right; width:90px">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaConfiguracion"
                    	action="#{requisitosController.buscar}"/>
            	</rich:column>
            </h:panelGrid>            

            <rich:spacer height="12px"/>

            <h:panelGrid id="panelTablaConfiguracion">
	        	<rich:extendedDataTable id="tblConfiguraciones" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{requisitosController.listaConfServSolicitud}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="970px" 
					height="170px" 
					align="center">
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                    <rich:column width="130px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="ReqAut"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemSolicitud}"/>
                	</rich:column>
                  	<rich:column width="130px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Tipo de Operación"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoOperacionCod}"/>
                	</rich:column>
                    <rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sub Operación"/>
                      	</f:facet>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUBOPERACION}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONPRESTAMO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONORDENDECREDITO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONACTIVIDADES}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONFONDOSEPELIO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONFONDORETIRO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_AES}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONFONDOAES}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      
                  	</rich:column>
                    <rich:column width="110" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Tipo de Cuenta"/>
                        </f:facet>
						<rich:dataTable value="#{item.listaGrupoCta}" 
                     		var="cuenta" 
                     		style="border-top: 0px; border-left: 0px;">
							<rich:column width="100">
	                     		<h:panelGroup rendered="#{cuenta.intValor==requisitosController.selecciona}">
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTAREQUISITOS}" 
										itemValue="intIdDetalle" 
										itemLabel="strDescripcion" 
										property="#{cuenta.id.intParaTipoCuentaCod}"/>
									<h:outputText value=" "/>
							</h:panelGroup>
	                  		</rich:column>
						</rich:dataTable>
                  	</rich:column>
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Estado"/>
                    	</f:facet>
                    	 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstadoCod}"/>
                  	</rich:column>
                  	<rich:column width="140" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Rango de Fecha"/>
                     	</f:facet>
                     	 <h:outputText value="#{item.dtDesde}">
                     	 	 <f:convertDateTime pattern="dd/MM/yyyy" />                           
                        </h:outputText>
                        <h:outputText value="-"/>
                        <h:panelGroup rendered="#{empty item.dtHasta}">
                        	<h:outputText value="Indeterminado"/>
                        </h:panelGroup>
                        <h:panelGroup rendered="#{!empty item.dtHasta}">
                        	<h:outputText value="#{item.dtHasta}">
                        		 <f:convertDateTime pattern="dd/MM/yyyy" />
                        	</h:outputText>
                        </h:panelGroup>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Requisitos"/>
                     	</f:facet>
                     	<h:outputText value="#{fn:length(item.listaDetalle)}"/>
                  	</rich:column>
                  	<rich:column width="130" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                     	</f:facet>
                     	<h:outputText value="#{item.tsFechaRegistro}">
                             <f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>    
                  	</rich:column>
                    <f:facet name="footer">
						<rich:datascroller for="tblConfiguraciones" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{requisitosController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{requisitosController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
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
			<h:outputText value="#{requisitosController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{requisitosController.mostrarMensajeExito}"/>
			<h:outputText value="#{requisitosController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{requisitosController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{requisitosController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{requisitosController.grabar}" reRender="contPanelInferior,panelMensaje,panelBotones"
			    	disabled="#{!requisitosController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{requisitosController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</rich:panel>	        	
		
		<h:panelGroup id="contPanelInferior">
		
			<rich:panel rendered="#{requisitosController.mostrarPanelInferior}" style="border:1px solid #17356f;background-color:#DEEBF5;">		          	 	
		    
		    	<h:panelGrid columns="6">
			    	<rich:column width="150">
		        		<rich:dataTable value="#{requisitosController.listaTipoRelacion}" var="item">
							<rich:column width="150">
		                  		<f:facet name="header">
		                        	<h:outputText value="Tipo de Relación"/>
		                     	</f:facet>
	                     		<h:selectBooleanCheckbox value="#{item.checked}" 
	                     			disabled="#{requisitosController.deshabilitarNuevo}"/>
	                     		<h:outputText value="#{item.strDescripcion}"/>
	                  		</rich:column>
						</rich:dataTable>
		        	</rich:column>
			        <rich:column width="120">
			        	<h:outputText value="Tipo de Operación : " />
			        </rich:column>
			        <rich:column width="150">
			        	<h:selectOneMenu
			        		style="width: 150px;"
							disabled="#{requisitosController.deshabilitarNuevo || !requisitosController.habilitarTipoOperacion}"
							value="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>			
						</h:selectOneMenu>
			        </rich:column>
			        <rich:column width="115">
		        		<a4j:commandButton 
		        			value="Registrar Tipo" 
		        			styleClass="btnEstilos"
			    			style="width:110px"
			    			disabled="#{requisitosController.deshabilitarNuevo}"
			    			action="#{requisitosController.seleccionarRegistrarTipo}"
			    			reRender="contPanelInferior,panelBotones"/> 
		        	</rich:column>
		        	<rich:column width="60" style="text-align: right;">
		        		<h:outputText value="Estado : " rendered="#{requisitosController.habilitarComboEstado}"/>		        			        			        		
		        	</rich:column>
		        	<rich:column width="80">
		        		<h:selectOneMenu
			        			rendered="#{requisitosController.habilitarComboEstado}"
			        			style="width: 80px;"
			        			disabled="#{requisitosController.deshabilitarNuevo || requisitosController.registrarNuevo}"
								value="#{requisitosController.confServSolicitudNuevo.intParaEstadoCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>			
							</h:selectOneMenu>
		        	</rich:column>
				</h:panelGrid>

			    <h:panelGroup id="panelOperaciones">

			    	<h:panelGroup id="panelLiquidacion" rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA}">
			        	
			        	<h:panelGrid columns="2">
			        		<rich:column width="150">
			        			<rich:dataTable value="#{requisitosController.listaTipoCuenta}" var="item">
									<rich:column width="150">
			                  			<f:facet name="header">
			                        		<h:outputText value="Tipo de Cuenta"/>
			                     		</f:facet>
		                     			<h:selectBooleanCheckbox value="#{item.checked}" 
		                     				disabled="#{requisitosController.deshabilitarNuevo}"/>
		                     			<h:outputText value="#{item.strDescripcion}"/>
		                  			</rich:column>
								</rich:dataTable>
		        			</rich:column>
		        			<rich:column>
			        			<h:panelGrid columns="5" id="panelFechasLiqui">
					        		<rich:column width="90">
					        			<h:outputText value="Sub operación : "/>
				        			</rich:column>
				        			<rich:column width="140">
					        			<h:selectOneMenu
											style="width: 140px;"
											disabled="#{requisitosController.deshabilitarNuevo}"
											value="#{requisitosController.confServSolicitudNuevo.intParaSubtipoOperacionCod}">
											<tumih:selectItems var="sel" 
												value="#{requisitosController.listaSuboperacion}" 
												itemValue="#{sel.intIdDetalle}" 
												itemLabel="#{sel.strDescripcion}"/>
										</h:selectOneMenu>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Rango de Fecha :  Inicio : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtDesde}"
		         							disabled="#{requisitosController.deshabilitarNuevo}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Fin : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"
				        					id="inputFechaFinLiqui"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtHasta}"
				        					disabled="#{requisitosController.deshabilitarNuevo || !requisitosController.habilitarFechaFin}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				 <h:selectBooleanCheckbox value="#{requisitosController.seleccionaIndeterminado}"
		         					 		disabled="#{requisitosController.deshabilitarNuevo}">
		         					 		<a4j:support event="onclick" 
		         					 			action="#{requisitosController.manejarIndeterminado}" 
		         					 			reRender="inputFechaFinLiqui"/>
		         					 	</h:selectBooleanCheckbox>
				        				<h:outputText value="Indeterminado"/>		        				
				        			</rich:column>
			        			</h:panelGrid>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		
		        		
		        		<h:panelGrid columns="5" id="panelEstructuraLiqui">
			        		<rich:column width="120">
			        			<h:outputText value="Estructura Orgánica : "/>
		        			</rich:column>
		        			<rich:column width="130">
			        			<h:selectOneRadio value="#{requisitosController.radioEstructura}"
			        				disabled="#{requisitosController.deshabilitarNuevo}">
						   			<f:selectItem itemLabel="Todos" itemValue="1"/>
						   			<f:selectItem itemLabel=" Entidad" itemValue="2"/>
						   			<a4j:support event="onclick" 
						   				action="#{requisitosController.seleccionarRadioEstructura}" 
										reRender="panelOperaciones"/>
						   		</h:selectOneRadio>
		        			</rich:column>
		        			<rich:column width="90">
		        				<a4j:commandButton
		        					oncomplete="Richfaces.showModalPanel('pBuscarEntidad')"
									reRender="panelSeleccionaEstructura" 
		        					value="Agregar" 
		        					styleClass="btnEstilos"
			    					style="width:80px"
			    					disabled="#{!requisitosController.habilitarAgregarEstructura  || requisitosController.deshabilitarNuevo}"
			    					actionListener="#{requisitosController.abrirConfServEstructuraDetalle}">			    					
			    					<f:attribute name="nombrePanelEstructura" value="panelEstructuraLiqui"/>
			    				</a4j:commandButton>
			    			</rich:column>
			    			<rich:column>
			    				<rich:dataTable id="tblEstructurasLiqui" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServEstructuraDetalle}"  
									rowKeyVar="rowKey" 
									width="550px" 
									rows="10">
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="100px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Nivel"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intNivelPk}"/>
				                	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Entidad"/>
				                      	</f:facet>
				                      	<h:outputText value="#{item.strRazonSocial}" />
				                  	</rich:column>				                   
				                  	<rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Modalidad"/>
				                        </f:facet>
				                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoModalidad}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Socio"/>
				                        </f:facet>
				                         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoSocio}"/>
				                  	</rich:column>
				                    <rich:column width="60" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarEstructuraDetalle}"
								            reRender="panelEstructuraLiqui"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>
				                  	<f:facet name="footer">
										<rich:datascroller for="tblEstructurasLiqui" maxPages="20"/>   
									</f:facet>                  	
				            	</rich:dataTable>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid columns="7">
			        		<rich:column width="120">
			        			<h:outputText value="Requisitos : "/>
		        			</rich:column>
		        			<rich:column width="100">
			        			<h:outputText value="Descripción : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoDescripcion}">
									<tumih:selectItems var="sel" 
										value="#{requisitosController.listaRequisito}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>			
								</h:selectOneMenu>
							</rich:column>
		        			<rich:column width="50">
			        			<h:outputText value="Tipo : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoPersonaOperacionCod}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
								</h:selectOneMenu>
		        			</rich:column>
		        			<rich:column width="140">
		        				<h:selectBooleanCheckbox value="#{requisitosController.habilitarArchivoAdjunto}" 
		        					id="checkHabilitarArchivoAdjuntoLiqui"
		        					disabled="#{requisitosController.deshabilitarNuevo}"/>
			        			<h:outputText value="Archivo adjunto"/>
		        			</rich:column>
		        			<rich:column width="140">
			        			<a4j:commandButton styleClass="btnEstilos" 
                					value="Agregar" 
                					reRender="panelTablaRequisitosLiqui,checkHabilitarArchivoAdjuntoLiqui"
                					disabled="#{requisitosController.deshabilitarNuevo}"
                    				action="#{requisitosController.agregarRequisito}"/>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid id="panelTablaRequisitosLiqui" columns="2">
		        			<rich:column width="110">
				        	</rich:column>
				        	<rich:column width="300">
					        	<rich:dataTable id="tblRequisitosLiqui" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServDetalle}"  
									rowKeyVar="rowKey" 
									width="510px" 
									rows="#{fn:length(requisitosController.listaConfServDetalle)}"
									align="center">
				                                
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="140px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Descripción"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoDescripcion}"/>
				                	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Tipo"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoPersonaOperacionCod}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Archivo Adjunto"/>
				                        </f:facet>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.selecciona}">
				                        	<h:outputText value="Si"/>
				                        </h:panelGroup>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.noSelecciona}">
				                        	<h:outputText value="No"/>
				                        </h:panelGroup>
				                  	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarRequisito}"
								            reRender="panelTablaRequisitosLiqui"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>		                  	
				            	</rich:dataTable>
			         		</rich:column>
			         		
		        		</h:panelGrid>
		       		</h:panelGroup>
		       		
		       		
		       		
		       		<h:panelGroup id="panelPrestamos" 
		       			rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO
		       				 || requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO
		       				 || requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD
		       				 || requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO}">
			        	
			        	<h:panelGrid columns="3">
			        		<rich:column width="150">
			        			<rich:dataTable value="#{requisitosController.listaTipoCuenta}" var="item">
									<rich:column width="150">
			                  			<f:facet name="header">
			                        		<h:outputText value="Tipo de Cuenta"/>
			                     		</f:facet>
		                     			<h:selectBooleanCheckbox value="#{item.checked}" 
		                     				disabled="#{requisitosController.deshabilitarNuevo}"/>
		                     			<h:outputText value="#{item.strDescripcion}"/>
		                  			</rich:column>
								</rich:dataTable>
		        			</rich:column>
		        			<rich:column>
		        				<h:panelGrid columns="5" id="panelFechasPrest">
					        		<rich:column width="90">
					        			<h:outputText value="Sub operación : "/>
				        			</rich:column>
				        			<rich:column width="140">
					        			<h:selectOneMenu
											style="width: 140px;"
											disabled="#{requisitosController.deshabilitarNuevo}"
											value="#{requisitosController.confServSolicitudNuevo.intParaSubtipoOperacionCod}">
											<tumih:selectItems var="sel"
												value="#{requisitosController.listaSuboperacion}" 
												itemValue="#{sel.intIdDetalle}"
												itemLabel="#{sel.strDescripcion}"/>
										</h:selectOneMenu>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Rango de Fecha :  Inicio : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtDesde}"
		         							disabled="#{requisitosController.deshabilitarNuevo}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Fin : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"
				        					id="inputFechaFinPrest"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtHasta}"
				        					disabled="#{requisitosController.deshabilitarNuevo || !requisitosController.habilitarFechaFin}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				 <h:selectBooleanCheckbox value="#{requisitosController.seleccionaIndeterminado}"
		         					 		disabled="#{requisitosController.deshabilitarNuevo}">
		         					 		<a4j:support event="onclick" 
		         					 			action="#{requisitosController.manejarIndeterminado}" 
		         					 			reRender="inputFechaFinPrest"/>
		         					 	</h:selectBooleanCheckbox>
				        				<h:outputText value="Indeterminado"/>		        				
				        			</rich:column>
				        		</h:panelGrid>
		        			</rich:column>
		        		</h:panelGrid>	  
		        		
		        		<h:panelGrid columns="5" id="panelProductosPrest">
			        		<rich:column width="120">
			        			<h:outputText value="Productos : "/>
		        			</rich:column>
		        			<rich:column width="90">
		        				<a4j:commandButton
		        					oncomplete="Richfaces.showModalPanel('pBuscarProducto')"
									reRender="panelSeleccionarProducto" 
		        					value="Buscar" 
		        					styleClass="btnEstilos"
			    					style="width:80px"
			    					disabled="#{requisitosController.deshabilitarNuevo}"
			    					actionListener="#{requisitosController.abrirConfServCredito}">	
			    					<f:attribute name="nombrePanelProducto" value="panelProductosPrest"/>
			    				</a4j:commandButton>
			    			</rich:column>
			    			<rich:column>
			    				<rich:dataTable id="tblProductosPrest" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServCredito}"  
									rowKeyVar="rowKey" 
									width="400px" 
									rows="#{fn:length(requisitosController.listaConfServCredito)}"
									>
				                    
									<rich:column width="80px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Código"/>
				                      	</f:facet>
				                      	<h:outputText value="#{item.intSocioItemCredito}"/>   				                      	
				                	</rich:column>
				                    <rich:column width="130" style="text-align: center"
				                    	rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO}">
				                    	<f:facet name="header">
				                    		<h:outputText value="Tipo de Crédito"  />	  		
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoCreditoEmpresa}"/>
				                  	</rich:column>
				                  	<rich:column width="130" style="text-align: center"
				                  	 	rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD}">
				                    	<f:facet name="header">
				                    		<h:outputText value="Tipo de Actividad"/>              		
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoCreditoEmpresa}"/>
				                  	</rich:column>
				                  	<rich:column width="130" style="text-align: center"
				                  	 	rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO}">
				                    	<f:facet name="header">
				                    		<h:outputText value="Tipo Orden de Crédito"/>              		
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoCreditoEmpresa}"/>
				                  	</rich:column>
				                  	<rich:column width="130" style="text-align: center"
				                  	 	rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO}">
				                    	<f:facet name="header">
				                    		<h:outputText value="Tipo de Refinanciamiento"/>              		
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoCreditoEmpresa}"/>
				                  	</rich:column>
				                    <rich:column width="190" style="text-align: center">
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
								        	actionListener="#{requisitosController.eliminarConfServEstructura}"
								            reRender="panelProductosPrest"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>		                  	
				            	</rich:dataTable>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid columns="5" id="panelEstructuraPrest">
			        		<rich:column width="120">
			        			<h:outputText value="Estructura Orgánica : "/>
		        			</rich:column>
		        			<rich:column width="130">
			        			<h:selectOneRadio value="#{requisitosController.radioEstructura}"
			        				disabled="#{requisitosController.deshabilitarNuevo}">
						   			<f:selectItem itemLabel="Todos" itemValue="1"/>
						   			<f:selectItem itemLabel="Entidad" itemValue="2"/>
						   			<a4j:support event="onclick" 
						   				action="#{requisitosController.seleccionarRadioEstructura}" 
										reRender="panelOperaciones"/>
						   		</h:selectOneRadio>
		        			</rich:column>
		        			<rich:column width="90">
		        				<a4j:commandButton
		        					oncomplete="Richfaces.showModalPanel('pBuscarEntidad')"
									reRender="panelSeleccionaEstructura" 
		        					value="Agregar" 
		        					styleClass="btnEstilos"
			    					style="width:80px"
			    					disabled="#{!requisitosController.habilitarAgregarEstructura}"
			    					actionListener="#{requisitosController.abrirConfServEstructuraDetalle}">			    					
			    					<f:attribute name="nombrePanelEstructura" value="panelEstructuraPrest"/>
			    				</a4j:commandButton>
			    			</rich:column>
			    			<rich:column>
			    				<rich:dataTable id="tblEstructurasPrest" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServEstructuraDetalle}"  
									rowKeyVar="rowKey" 
									width="550px" 
									rows="10">
				                                
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="100px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Nivel"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intNivelPk}"/>
				                	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Entidad"/>
				                      	</f:facet>
				                      	<h:outputText value="#{item.strRazonSocial}" />
				                  	</rich:column>				                   
				                  	<rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Modalidad"/>
				                        </f:facet>
				                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoModalidad}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Socio"/>
				                        </f:facet>
				                         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoSocio}"/>
				                  	</rich:column>
				                    <rich:column width="60" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarEstructuraDetalle}"
								            reRender="panelEstructuraPrest"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>
				                  	<f:facet name="footer">
										<rich:datascroller for="tblEstructurasPrest" maxPages="20"/>   
									</f:facet> 
				            	</rich:dataTable>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid columns="7">
			        		<rich:column width="120">
			        			<h:outputText value="Requisitos : "/>
		        			</rich:column>
		        			<rich:column width="100">
			        			<h:outputText value="Descripción : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoDescripcion}">
									<tumih:selectItems var="sel" 
										value="#{requisitosController.listaRequisito}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>			
								</h:selectOneMenu>
							</rich:column>
		        			<rich:column width="50">
			        			<h:outputText value="Tipo : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoPersonaOperacionCod}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
								</h:selectOneMenu>
		        			</rich:column>
		        			<rich:column width="140">
		        				<h:selectBooleanCheckbox value="#{requisitosController.habilitarArchivoAdjunto}" 
		        					id="checkHabilitarArchivoAdjuntoPrest"
		        					disabled="#{requisitosController.deshabilitarNuevo}"/>
			        			<h:outputText value="Archivo adjunto"/>
		        			</rich:column>
		        			<rich:column width="140">
			        			<a4j:commandButton styleClass="btnEstilos" 
                					value="Agregar" 
                					reRender="panelTablaRequisitosPrest,checkHabilitarArchivoAdjuntoPrest"
                					disabled="#{requisitosController.deshabilitarNuevo}"
                    				action="#{requisitosController.agregarRequisito}"/>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid id="panelTablaRequisitosPrest" columns="2">
		        			<rich:column width="110">
				        	</rich:column>
				        	<rich:column width="300">
					        	<rich:dataTable id="tblRequisitosPrest" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServDetalle}"  
									rowKeyVar="rowKey" 
									width="510px" 
									rows="#{fn:length(requisitosController.listaConfServDetalle)}"
									align="center">
				                                
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="140px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Descripción"/>
				                      	</f:facet>				                      	
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ORDENCREDITO}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_ORDENCREDITO}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDADES}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	
				                	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Tipo"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoPersonaOperacionCod}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Archivo Adjunto"/>
				                        </f:facet>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.selecciona}">
				                        	<h:outputText value="Si"/>
				                        </h:panelGroup>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.noSelecciona}">
				                        	<h:outputText value="No"/>
				                        </h:panelGroup>
				                  	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarRequisito}"
								            reRender="panelTablaRequisitosPrest"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>		                  	
				            	</rich:dataTable>
			         		</rich:column>
			         		
		        		</h:panelGrid>
		       		</h:panelGroup>
		       		
		       		
		       		<h:panelGroup id="panelCaptacion" 
		       			rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO
		       				 || requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO
		       				 || requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_AES}">
			        	
			        	<h:panelGrid columns="2">
			        		<rich:column width="150">
			        			<rich:dataTable value="#{requisitosController.listaTipoCuenta}" var="item">
									<rich:column width="150">
			                  			<f:facet name="header">
			                        		<h:outputText value="Tipo de Cuenta"/>
			                     		</f:facet>
		                     			<h:selectBooleanCheckbox value="#{item.checked}" 
		                     				disabled="#{requisitosController.deshabilitarNuevo}"/>
		                     			<h:outputText value="#{item.strDescripcion}"/>
		                  			</rich:column>
								</rich:dataTable>
		        			</rich:column>
		        			<rich:column>
		        				<h:panelGrid columns="5" id="panelFechasCapta">
					        		<rich:column width="90">
					        			<h:outputText value="Sub operación : "/>
				        			</rich:column>
				        			<rich:column width="140">
					        			<h:selectOneMenu
											style="width: 140px;"
											disabled="#{requisitosController.deshabilitarNuevo}"
											value="#{requisitosController.confServSolicitudNuevo.intParaSubtipoOperacionCod}">
											<tumih:selectItems var="sel" 
												value="#{requisitosController.listaSuboperacion}" 
												itemValue="#{sel.intIdDetalle}" 
												itemLabel="#{sel.strDescripcion}"/>
										</h:selectOneMenu>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Rango de Fecha :  Inicio : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtDesde}"
		         							disabled="#{requisitosController.deshabilitarNuevo}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				<h:outputText value="Fin : "/>
				        				<rich:calendar datePattern="dd/MM/yyyy"
				        					id="inputFechaFinCapta"  
				        					value="#{requisitosController.confServSolicitudNuevo.dtHasta}"
				        					disabled="#{requisitosController.deshabilitarNuevo || !requisitosController.habilitarFechaFin}"
		         							inputSize="10" 
		         							showApplyButton="true"/>
				        			</rich:column>
				        			<rich:column>
				        				 <h:selectBooleanCheckbox value="#{requisitosController.seleccionaIndeterminado}"
		         					 		disabled="#{requisitosController.deshabilitarNuevo}">
		         					 		<a4j:support event="onclick" action="#{requisitosController.manejarIndeterminado}" 
		         					 			reRender="inputFechaFinCapta"/>
		         					 	</h:selectBooleanCheckbox>
				        				<h:outputText value="Indeterminado"/>		        				
				        			</rich:column>
				        		</h:panelGrid>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		
		        		
		        		<h:panelGrid columns="5" id="panelProductosCapta">
			        		<rich:column width="120">
			        			<h:outputText value="Productos : "/>
		        			</rich:column>
		        			<rich:column width="90">
		        				<a4j:commandButton
		        					oncomplete="Richfaces.showModalPanel('pBuscarCaptacion')"
									reRender="panelSeleccionarCaptacion" 
		        					value="Buscar" 
		        					styleClass="btnEstilos"
			    					style="width:80px"
			    					disabled="#{requisitosController.deshabilitarNuevo}"
			    					actionListener="#{requisitosController.abrirConfServCaptacion}">	
			    					<f:attribute name="nombrePanelProducto" value="panelProductosCapta"/>
			    				</a4j:commandButton>
			    			</rich:column>
			    			<rich:column>
			    				<rich:dataTable id="tblProductosCapta" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServCaptacion}"  
									rowKeyVar="rowKey" 
									width="400px" 
									rows="#{fn:length(requisitosController.listaConfServCaptacion)}"
									>
				                    
									<rich:column width="80px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Código"/>
				                      	</f:facet>
				                      	<h:outputText value="#{item.intSocioItem}"/>   				                      	
				                	</rich:column>				                    
				                    <rich:column width="190" style="text-align: center">
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
								        	actionListener="#{requisitosController.eliminarConfServEstructura}"
								            reRender="panelProductosPrest"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>		                  	
				            	</rich:dataTable>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid columns="5" id="panelEstructuraCapta">
			        		<rich:column width="120">
			        			<h:outputText value="Estructura Orgánica : "/>
		        			</rich:column>
		        			<rich:column width="130">
			        			<h:selectOneRadio value="#{requisitosController.radioEstructura}"
			        				disabled="#{requisitosController.deshabilitarNuevo}">
						   			<f:selectItem itemLabel="Todos" itemValue="1"/>
						   			<f:selectItem itemLabel=" Entidad" itemValue="2"/>
						   			<a4j:support event="onclick" action="#{requisitosController.seleccionarRadioEstructura}" 
										reRender="panelOperaciones"/>
						   		</h:selectOneRadio>
		        			</rich:column>
		        			<rich:column width="90">
		        				<a4j:commandButton
		        					oncomplete="Richfaces.showModalPanel('pBuscarEntidad')"
									reRender="panelSeleccionaEstructura" 
		        					value="Agregar" 
		        					styleClass="btnEstilos"
			    					style="width:80px"
			    					disabled="#{!requisitosController.habilitarAgregarEstructura}"
			    					actionListener="#{requisitosController.abrirConfServEstructuraDetalle}">			    					
			    					<f:attribute name="nombrePanelEstructura" value="panelEstructuraCapta"/>
			    				</a4j:commandButton>
			    			</rich:column>
			    			<rich:column>
			    				<rich:dataTable id="tblEstructurasCapta" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServEstructuraDetalle}"  
									rowKeyVar="rowKey" 
									width="550px" 
									rows="10">
				                                
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="100px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Nivel"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NIVELENTIDAD}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intNivelPk}"/>
				                	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Entidad"/>
				                      	</f:facet>
				                      	<h:outputText value="#{item.strRazonSocial}" />
				                  	</rich:column>				                   
				                  	<rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Modalidad"/>
				                        </f:facet>
				                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoModalidad}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Tipo Socio"/>
				                        </f:facet>
				                         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intTipoSocio}"/>
				                  	</rich:column>
				                    <rich:column width="60" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarEstructuraDetalle}"
								            reRender="panelEstructuraCapta"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>
				                  	<f:facet name="footer">
										<rich:datascroller for="tblEstructurasCapta" maxPages="20"/>   
									</f:facet> 
				            	</rich:dataTable>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid columns="7">
			        		<rich:column width="120">
			        			<h:outputText value="Requisitos : "/>
		        			</rich:column>
		        			<rich:column width="100">
			        			<h:outputText value="Descripción : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoDescripcion}">
									<tumih:selectItems var="sel" 
										value="#{requisitosController.listaRequisito}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>			
								</h:selectOneMenu>
							</rich:column>
		        			<rich:column width="50">
			        			<h:outputText value="Tipo : "/>
		        			</rich:column>
		        			<rich:column width="170">
			        			<h:selectOneMenu
									style="width: 170px;"
									disabled="#{requisitosController.deshabilitarNuevo}"
									value="#{requisitosController.confServDetalle.intParaTipoPersonaOperacionCod}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>			
								</h:selectOneMenu>
		        			</rich:column>
		        			<rich:column width="140">
		        				<h:selectBooleanCheckbox value="#{requisitosController.habilitarArchivoAdjunto}" 
		        					id="checkHabilitarArchivoAdjuntoCapta"
		        					disabled="#{requisitosController.deshabilitarNuevo}"/>
			        			<h:outputText value="Archivo adjunto"/>
		        			</rich:column>
		        			<rich:column width="140">
			        			<a4j:commandButton styleClass="btnEstilos" 
                					value="Agregar" 
                					reRender="panelTablaRequisitosCapta,checkHabilitarArchivoAdjuntoCapta"
                					disabled="#{requisitosController.deshabilitarNuevo}"
                    				action="#{requisitosController.agregarRequisito}"/>
		        			</rich:column>
		        		</h:panelGrid>
		        		
		        		<h:panelGrid id="panelTablaRequisitosCapta" columns="2">
		        			<rich:column width="110">
				        	</rich:column>
				        	<rich:column width="300">
					        	<rich:dataTable id="tblRequisitosCapta" 
					          		sortMode="single" 
				                    var="item" 
				                    value="#{requisitosController.listaConfServDetalle}"  
									rowKeyVar="rowKey" 
									width="510px" 
									rows="#{fn:length(requisitosController.listaConfServDetalle)}"
									align="center">
				                                
									<rich:column width="30px" style="text-align: center">
				                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
				                    </rich:column>
				                  	<rich:column width="140px" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Descripción"/>
				                      	</f:facet>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_FONDORETIRO}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                      	<h:panelGroup rendered="#{requisitosController.confServSolicitudNuevo.intParaTipoOperacionCod == applicationScope.Constante.PARAM_T_TIPOOPERACION_AES}">
				                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISITOSDESCRIPCION_AES}" 
												itemValue="intIdDetalle" 
												itemLabel="strDescripcion" 
												property="#{item.intParaTipoDescripcion}"/>
				                      	</h:panelGroup>
				                	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                    	<f:facet name="header">
				                      		<h:outputText value="Tipo"/>
				                      	</f:facet>
				                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONAREQUISITO}" 
											itemValue="intIdDetalle" 
											itemLabel="strDescripcion" 
											property="#{item.intParaTipoPersonaOperacionCod}"/>
				                  	</rich:column>
				                    <rich:column width="120" style="text-align: center">
				                   		<f:facet name="header">
				                        	<h:outputText value="Archivo Adjunto"/>
				                        </f:facet>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.selecciona}">
				                        	<h:outputText value="Si"/>
				                        </h:panelGroup>
				                        <h:panelGroup rendered="#{item.intOpcionAdjunta == requisitosController.noSelecciona}">
				                        	<h:outputText value="No"/>
				                        </h:panelGroup>
				                  	</rich:column>
				                    <rich:column width="100" style="text-align: center">
				                    	<f:facet name="header">
				                        	<h:outputText value="Opciones"/>
				                    	</f:facet>
				                    	<a4j:commandLink value="Eliminar"
								        	actionListener="#{requisitosController.eliminarRequisito}"
								            reRender="panelTablaRequisitosCapta"
								            disabled="#{requisitosController.deshabilitarNuevo}">
								            <f:attribute name="item" value="#{item}"/>
						            	</a4j:commandLink>
				                  	</rich:column>		                  	
				            	</rich:dataTable>
			         		</rich:column>
			         		
		        		</h:panelGrid>
		       		</h:panelGroup>
		       		
		       	</h:panelGroup>
		       	
			</rich:panel>
		
		</h:panelGroup>		
</h:form>
</rich:panel>