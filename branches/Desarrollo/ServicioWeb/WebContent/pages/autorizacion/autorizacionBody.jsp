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
	<!-- Prototipo :  Autorizaciones 	-->			
	<!-- Fecha     :                	-->

<rich:modalPanel id="pBuscarPerfil" width="600" height="350"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Perfiles"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarPerfil" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/autorizacion/popup/perfilBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarUsuario" width="700" height="350"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Usuarios"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarUsuario" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/autorizacion/popup/usuarioBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarCancelado" width="435" height="180"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Porcentaje Cancelado"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarCancelado" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/autorizacion/popup/canceladoBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistro" width="400" height="150"
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
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="1">
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{autorizacionController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{autorizacionController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelMensaje,contPanelInferior,panelTablaAutorizacion"
             			rendered="#{autorizacionController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>



<h:form id="frmAutorizacion">
	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
		
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:5px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="NIVELES DE AUTORIZACIÓN" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="9" id="panelBusqueda">
         		<rich:column width="110">
         			<h:outputText value="Tipo de Operación : "/>
         		</rich:column>
         		<rich:column width="150">
                	<h:selectOneMenu
						style="width: 150px;"
						value="#{autorizacionController.confServSolicitudFiltro.intParaTipoOperacionCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							propertySort="intOrden"/>			
					</h:selectOneMenu>
              	</rich:column>              	
              	<rich:column width="140">
              		<h:selectBooleanCheckbox value="#{autorizacionController.habilitarFiltroFecha}">
              			<a4j:support event="onclick" action="#{autorizacionController.seleccionaFiltroFecha}"
		         			reRender="panelBusqueda"/>
              		</h:selectBooleanCheckbox>
         			<h:outputText value="Rango de Fecha : "/>         			
		        </rich:column>
		        <rich:column width="100">
		        	<rich:calendar datePattern="dd/MM/yyyy"  
				 		disabled="#{!autorizacionController.habilitarFiltroFecha}" 
				 		value="#{autorizacionController.confServSolicitudFiltro.dtDesde}"
		         		inputSize="10" 
		         		showApplyButton="true"/>
         		</rich:column>
		        <rich:column width="100">
		         	<rich:calendar datePattern="dd/MM/yyyy"  
				        disabled="#{!autorizacionController.habilitarFiltroFecha}"
				        value="#{autorizacionController.confServSolicitudFiltro.dtHasta}"
		         		inputSize="10" 
		         		showApplyButton="true"/>		         							
         		</rich:column>
         		<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaAutorizacion,panelMensaje"
                    	action="#{autorizacionController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>            
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaAutorizacion">
	        	<rich:extendedDataTable id="tblAutorizacion" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{autorizacionController.listaConfServSolicitud}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="920px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                    
                    <rich:column width="60px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemSolicitud}"/>
                	</rich:column>
                	
                	
                  	<rich:column width="150px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Tipo de Operación"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoOperacionCod}"/>
                	</rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sub Operación"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONPRESTAMO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBOPERACIONREFINANCIAMIENTO}" 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.intParaSubtipoOperacionCod}"/>
                      	</h:panelGroup>
                  	</rich:column>
                    <rich:column width="160" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Perfil"/>
                        </f:facet>
                        <rich:dataTable value="#{item.listaPerfil}" 
                     		var="perfil" 
                     		style="border-top: 0px; border-left: 0px;">
							<rich:column style="text-align: center">
	                     		<h:outputText value="#{perfil.strDescripcion}"/>
	                  		</rich:column>
						</rich:dataTable>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Usuario"/>
                        </f:facet>
                        <rich:dataTable value="#{item.listaUsuario}" 
                     		var="usuarioC" 
                     		style="border-top: 0px; border-left: 0px;">
							<rich:column style="text-align: center">
	                     		<h:outputText value="#{usuarioC.usuarioComp.usuario.strUsuario}"/>
	                  		</rich:column>
						</rich:dataTable>
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
                   	<rich:column width="130" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                     	</f:facet>
                     	<h:outputText value="#{item.tsFechaRegistro}">
                             <f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>    
                  	</rich:column>
                    <f:facet name="footer">
						<rich:datascroller for="tblAutorizacion" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{autorizacionController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{autorizacionController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
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
			
				
			<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
				styleClass="rich-tabcell-noborder">
				<h:outputText value="#{autorizacionController.mensajeOperacion}" 
					styleClass="msgInfo"
					style="font-weight:bold"
					rendered="#{autorizacionController.mostrarMensajeExito}"/>
				<h:outputText value="#{autorizacionController.mensajeOperacion}" 
					styleClass="msgError"
					style="font-weight:bold"
					rendered="#{autorizacionController.mostrarMensajeError}"/>	
			</h:panelGroup>
				 		
			<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
				id="panelBotones">
				<h:panelGrid columns="3">
					<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
						actionListener="#{autorizacionController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones" />                     
				    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
				    	action="#{autorizacionController.grabar}" reRender="contPanelInferior,panelMensaje,panelBotones"
				    	disabled="#{!autorizacionController.habilitarGrabar}"/>       												                 
				    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
				    	actionListener="#{autorizacionController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones"/>      
				</h:panelGrid>
			</h:panelGroup>	        	
		
		          	
		<h:panelGroup id="contPanelInferior">
		
			<rich:panel  rendered="#{autorizacionController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	          	 	
		    
		    	<h:panelGrid columns="6">
			    	<rich:column width="150">
		        		<rich:dataTable value="#{autorizacionController.listaTipoRelacion}" var="item">
							<rich:column width="150">
		                  		<f:facet name="header">
		                        	<h:outputText value="Tipo de Relación"/>
		                     	</f:facet>
	                     		<h:selectBooleanCheckbox value="#{item.checked}" 
	                     			disabled="#{autorizacionController.deshabilitarNuevo}"/>
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
							disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarTipoOperacion}"
							value="#{autorizacionController.confServSolicitudNuevo.intParaTipoOperacionCod}">
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
			    			disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarTipoOperacion}"
			    			action="#{autorizacionController.seleccionarRegistrarTipo}"
			    			reRender="contPanelInferior,panelBotones"/> 
		        	</rich:column>
		        	<rich:column width="60" style="text-align: right;">
		        		<h:outputText value="Estado : " rendered="#{autorizacionController.habilitarComboEstado}"/>		        			        			        		
		        	</rich:column>
		        	<rich:column width="80">
		        		<h:selectOneMenu
			        		rendered="#{autorizacionController.habilitarComboEstado}"
			        		style="width: 80px;"
			        		disabled="#{autorizacionController.deshabilitarNuevo || autorizacionController.registrarNuevo}"
							value="#{autorizacionController.confServSolicitudNuevo.intParaEstadoCod}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>			
						</h:selectOneMenu>
		        	</rich:column>
				</h:panelGrid>

				<h:panelGroup id="panelOperacion" 
					rendered="#{(autorizacionController.confServSolicitudNuevo.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_PRESTAMO)
					|| (autorizacionController.confServSolicitudNuevo.intParaTipoOperacionCod==applicationScope.Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)}">
				
					<h:panelGrid columns="2">
						<rich:column width="150">
							<h:outputText value="Suboperación : "/>
						</rich:column>
						<rich:column width="150">
				        	<h:selectOneMenu
								style="width: 120px;"
								disabled="#{autorizacionController.deshabilitarNuevo}"
								value="#{autorizacionController.confServSolicitudNuevo.intParaSubtipoOperacionCod}">
								<tumih:selectItems var="sel" value="#{autorizacionController.listaSuboperacion}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="panelCancelado"  />
							</h:selectOneMenu>
				        </rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column width="150">
							<h:outputText value="Rango de Fecha : "/>
						</rich:column>
						<rich:column>
							<h:outputText value=" Inicio : "/>
					        <rich:calendar datePattern="dd/MM/yyyy"  
					        	value="#{autorizacionController.confServSolicitudNuevo.dtDesde}"
			         			disabled="#{autorizacionController.deshabilitarNuevo}"
			         			inputSize="10" 
			         			showApplyButton="true"/>
					 	</rich:column>
				       	<rich:column>
				        	<h:outputText value="Fin : "/>
				        	<rich:calendar datePattern="dd/MM/yyyy"
				        		id="inputFechaFinLiqui"  
				        		value="#{autorizacionController.confServSolicitudNuevo.dtHasta}"
				        		disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarFechaFin}"
		         				inputSize="10" 
		         				showApplyButton="true"/>
				        </rich:column>
				        <rich:column>
				        	<h:selectBooleanCheckbox value="#{autorizacionController.seleccionaIndeterminado}"
		         				disabled="#{autorizacionController.deshabilitarNuevo}">
		         				<a4j:support event="onclick" action="#{autorizacionController.manejarIndeterminado}" 
		         					reRender="inputFechaFinLiqui"/>
		         			</h:selectBooleanCheckbox>
				        	<h:outputText value="Indeterminado"/>		        				
				        </rich:column>				        
					</h:panelGrid>	
					
					<h:panelGrid columns="2" id="PanelEntidadAutorizacion">
						<rich:column width="150">
							<h:outputText value="Entidad de Autorización : "/>
						</rich:column>
						<rich:column>
							<h:panelGrid columns="3" id="colTablaPerfiles">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarAgregarPerfil}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" action="#{autorizacionController.seleccionarEntidadAutorizacionPerfil}" 
		         							reRender="PanelEntidadAutorizacion"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="Perfiles "/>
								</rich:column>
								<rich:column>
									<a4j:commandButton styleClass="btnEstilos"
                						value="Agregar"
                    					disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarAgregarPerfil}"
                    					oncomplete="Richfaces.showModalPanel('pBuscarPerfil')"
                    					reRender="pgListaPerfiles"/>
								</rich:column>
								<rich:column>
									<rich:dataTable 
						          		sortMode="single" 
					                    var="item" 
					                    value="#{autorizacionController.listaConfServPerfil}"  
										rowKeyVar="rowKey" 
										width="370px" 
										rows="#{fn:length(autorizacionController.listaConfServPerfil)}">
										<rich:column width="90" style="text-align: center">
											<f:facet name="header">
									       		<h:outputText value="Código"/>
											</f:facet>
											<h:outputText value="#{item.intIdPerfilPk}"/>
									 	</rich:column>
									    <rich:column width="220" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Nombre de Perfil"/>
									      	</f:facet>
									      	<h:outputText value="#{item.strDescripcion}"/>
										</rich:column>
										<rich:column width="60" style="text-align: center">
					                    	<f:facet name="header">
					                        	<h:outputText value="Opciones"/>
					                    	</f:facet>
					                    	<a4j:commandLink value="Eliminar"
									        	actionListener="#{autorizacionController.eliminarConfServPerfil}"
									            reRender="colTablaPerfiles"
									            disabled="#{autorizacionController.deshabilitarNuevo}">
									            <f:attribute name="item" value="#{item}"/>
							            	</a4j:commandLink>
					                  	</rich:column>
									</rich:dataTable>
								</rich:column>
							</h:panelGrid>		        				
							<h:panelGrid columns="3">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarAgregarUsuario}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" action="#{autorizacionController.seleccionarEntidadAutorizacionUsuario}" 
		         							reRender="PanelEntidadAutorizacion"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="Usuarios "/>
								</rich:column>
								<rich:column>
									<a4j:commandButton styleClass="btnEstilos"
                						value="Agregar"
                    					disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarAgregarUsuario}"
                    					oncomplete="Richfaces.showModalPanel('pBuscarUsuario')"
                    					reRender="pgListaUsuarios"/>
								</rich:column>
								<rich:column id="colTablaUsuarios">
									<rich:dataTable
						          		sortMode="single" 
					                    var="item" 
					                    value="#{autorizacionController.listaConfServUsuario}"  
										rowKeyVar="rowKey" 
										width="480px" 
										rows="#{fn:length(autorizacionController.listaConfServUsuario)}">
									    <rich:column width="120" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Nombre de Usuario"/>
									      	</f:facet>
									      	<h:outputText value="#{item.usuarioComp.usuario.strUsuario}"/>
										</rich:column>
										<rich:column width="300" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Perfil"/>
									      	</f:facet>
											<h:outputText value="#{item.usuarioComp.strPerfil}"/>
										</rich:column>
										<rich:column width="60" style="text-align: center">
					                    	<f:facet name="header">
					                        	<h:outputText value="Opciones"/>
					                    	</f:facet>
					                    	<a4j:commandLink value="Eliminar"
									        	actionListener="#{autorizacionController.eliminarConfServUsuario}"
									            reRender="colTablaUsuarios"
									            disabled="#{autorizacionController.deshabilitarNuevo}">
									            <f:attribute name="item" value="#{item}"/>
							            	</a4j:commandLink>
					                  	</rich:column>
									</rich:dataTable>
								</rich:column>
							</h:panelGrid>
				        </rich:column>				        
					</h:panelGrid>
					
					<rich:spacer height="12px"/>
					
					<h:panelGrid columns="2">
						<rich:column width="150">
							<h:outputText value="Niveles de Autorización : "/>
						</rich:column>
						<rich:column>
							<h:panelGrid columns="2" id="panelTipoPrestamo">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarTipoPrestamo}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" reRender="panelTipoPrestamo"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="Tipo de Préstamo"/>
								</rich:column>
								<rich:column width="200">
									<rich:dataTable value="#{autorizacionController.listaTipoPrestamo}" var="item">
										<rich:column width="150">
					                  		<f:facet name="header">
					                        	<h:outputText value="Tipo de Préstamo"/>
					                     	</f:facet>
				                     		<h:selectBooleanCheckbox value="#{item.checked}" 
				                     			disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarTipoPrestamo}"/>
				                     		<h:outputText value="#{item.strDescripcion}"/>
				                  		</rich:column>
									</rich:dataTable>
								</rich:column>
							</h:panelGrid>
							
							<script language=Javascript>
						       function isNumberKey(evt)
						       {
						          var charCode = (evt.which) ? evt.which : event.keyCode
						          if (charCode != 46 && charCode > 31 
						            && (charCode < 48 || charCode > 57))
						             return false;
						
						          return true;
						       }
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
						    
							<h:panelGrid columns="3" id="panelRangoMontos">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarRangoMontos}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" reRender="panelRangoMontos"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="Rango de Montos "/>
								</rich:column>
								<rich:column width="120">
									<h:outputText value=" Desde : "/>
									<h:inputText size="10"
										rendered="#{!autorizacionController.habilitarRangoMontos}"
										disabled="true"
										style="background-color: #BFBFBF;"/>
									<h:inputText size="10"
										rendered="#{autorizacionController.habilitarRangoMontos}" 
										disabled="#{autorizacionController.deshabilitarNuevo}"
										value="#{autorizacionController.confServSolicitudNuevo.bdMontoDesde}"
										onkeypress="return isNumberKey(event)"/>
								</rich:column>
								<rich:column width="120">
									<h:outputText value=" Hasta : "/>
									<h:inputText size="10"
										rendered="#{!autorizacionController.habilitarRangoMontos}"
										disabled="true"
										style="background-color: #BFBFBF;"/>
									<h:inputText size="10"
										rendered="#{autorizacionController.habilitarRangoMontos}" 
										disabled="#{autorizacionController.deshabilitarNuevo}"
										value="#{autorizacionController.confServSolicitudNuevo.bdMontoHasta}"
										onkeypress="return isNumberKey(event)"/>
								</rich:column>
							</h:panelGrid>
							
							<h:panelGrid columns="3" id="panelNumeroCuotas">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarNumeroCuotas}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" reRender="panelNumeroCuotas"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="Número de cuotas "/>
								</rich:column>
								<rich:column width="120">
									<h:outputText value=" Desde : "/>
									<h:inputText size="10"
										rendered="#{!autorizacionController.habilitarNumeroCuotas}"
										disabled="true"
										style="background-color: #BFBFBF;"/>
									<h:inputText size="10"
										rendered="#{autorizacionController.habilitarNumeroCuotas}" 
										disabled="#{autorizacionController.deshabilitarNuevo}"
										value="#{autorizacionController.confServSolicitudNuevo.bdCuotaDesde}"
										onkeypress="return onlyNumbers(event)"/>
								</rich:column>
								<rich:column width="120">
									<h:outputText value=" Hasta : "/>
									<h:inputText size="10"
										rendered="#{!autorizacionController.habilitarNumeroCuotas}"
										disabled="true"
										style="background-color: #BFBFBF;"/>
									<h:inputText size="10"
										rendered="#{autorizacionController.habilitarNumeroCuotas}" 
										disabled="#{autorizacionController.deshabilitarNuevo}"
										value="#{autorizacionController.confServSolicitudNuevo.bdCuotaHasta}"
										onkeypress="return onlyNumbers(event)"/>
								</rich:column>
							</h:panelGrid>
							
							<h:panelGroup id="panelCancelado">
							<h:panelGrid columns="3" rendered="#{autorizacionController.confServSolicitudNuevo.intParaSubtipoOperacionCod == applicationScope.Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO}">
								<rich:column width="150">
									<h:selectBooleanCheckbox value="#{autorizacionController.habilitarCancelado}"
										disabled="#{autorizacionController.deshabilitarNuevo}">
										<a4j:support event="onclick" reRender="panelCancelado"/>
									</h:selectBooleanCheckbox>
									<h:outputText value="% cancelado"/>
								</rich:column>
								<rich:column>
									<a4j:commandButton styleClass="btnEstilos"
		                				value="Agregar" 
		                				oncomplete="Richfaces.showModalPanel('pBuscarCancelado')"
		                				disabled="#{autorizacionController.deshabilitarNuevo || !autorizacionController.habilitarCancelado}"
		                    		/>
						        </rich:column>
						        <rich:column id="colTablaCancelados">
									<rich:dataTable 
						          		sortMode="single" 
					                    var="item" 
					                    value="#{autorizacionController.listaConfServCancelado}"  
										rowKeyVar="rowKey" 
										width="360px" 
										rows="#{fn:length(autorizacionController.listaConfServCancelado)}">
										<rich:column width="300" style="text-align: center">
									    	<f:facet name="header">
									        	<h:outputText value="Porcentaje"/>
									      	</f:facet>
									      	<h:outputText value="#{item.bdPorcentajeCancelado}"/>
									      	<h:outputText value=" % - "/>
									      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_FORMADEPAGO}" 
												itemValue="intIdDetalle" itemLabel="strDescripcion" 
												property="#{item.intParaModalidadPagoCod}"/>											
										</rich:column>
										<rich:column width="60" style="text-align: center">
					                    	<f:facet name="header">
					                        	<h:outputText value="Opciones"/>
					                    	</f:facet>
					                    	<a4j:commandLink value="Eliminar"
									        	actionListener="#{autorizacionController.eliminarConfServCancelado}"
									            reRender="colTablaCancelados"
									            disabled="#{autorizacionController.deshabilitarNuevo}">
									            <f:attribute name="item" value="#{item}"/>
							            	</a4j:commandLink>
					                  	</rich:column>
									</rich:dataTable>
								</rich:column>
							</h:panelGrid>
							</h:panelGroup>
							
				        </rich:column>				        
					</h:panelGrid>
					
				</h:panelGroup>
		       	
		</rich:panel>
				
	</h:panelGroup>	

</h:panelGroup>
</h:form>