<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         	-->
	<!-- Autor     : Arturo Julca   			-->
	<!-- Modulo    :                			-->
	<!-- Prototipo :  Acceso fuera de hora		-->			
	<!-- Fecha     :                			-->

<rich:panel style="border: 0px solid #17356f;" styleClass="rich-tabcell-noborder">
        	
			<h:panelGrid columns="6">
    			<rich:column style="width: 110px">
         			<h:outputText value="Empresa : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu id="cboEmpresa" 
						style="width: 150px;"
						value="#{accesoController.accesoEspecialFiltro.intPersEmpresa}">
						<tumih:selectItems var="sel" value="#{accesoController.listaEmpresas}" 
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
					</h:selectOneMenu>
              	</rich:column>
                <rich:column style="width: 50px">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu
						style="width: 90px;"
						value="#{accesoController.accesoEspecialFiltro.intIdEstado}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>					
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column style="width: 50px">
         			<h:outputText value="Motivo : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu 
						style="width: 135px;"
						value="#{accesoController.accesoEspecialFiltro.intParaTipoMotivo}">						
						<tumih:selectItems var="sel" value="#{accesoController.listaMotivos}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
											propertySort="intOrden"/>
					</h:selectOneMenu>
              	</rich:column>
         	</h:panelGrid>
         	<h:panelGrid columns="4">
         		<rich:column  style="width: 110px">
         			<h:outputText value="Tipo de Usuario : "/>
         		</rich:column>
         		<rich:column style="width: 120px;">
					<h:selectOneMenu
						style="width: 150px;"
						value="#{accesoController.intTipoUsuario}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOUSUARIOACCESOESPECIAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" action="#{accesoController.seleccionaTipoUsuario}" 
							reRender="colCajaUsuarioFH,colBotonUsuarioFH"  />						
					</h:selectOneMenu>
                </rich:column>
            	<rich:column  id="colCajaUsuarioFH">
         			<h:inputText size="36" 
         				readonly="true" 
         				id="cajaUsuarioFiltroFH"
         				disabled="#{!accesoController.habilitarCajaUsuario}" 
         				value=" #{accesoController.accesoEspecialFiltro.naturalOpera.strNombres} #{accesoController.accesoEspecialFiltro.naturalOpera.strApellidoPaterno}	#{accesoController.accesoEspecialFiltro.naturalOpera.strApellidoMaterno}"
         			/>
         		</rich:column>
         		<rich:column  id="colBotonUsuarioFH">
         			<a4j:commandButton
         				style="width: 110px" 
						actionListener="#{accesoController.buscarUsuarios}"
						value="Buscar Usuario"
						disabled="#{!accesoController.habilitarCajaUsuario}" 
					    oncomplete="Richfaces.showModalPanel('pBuscarUsuario')"
					    reRender="pgListaUsuarios"
					    styleClass="btnEstilos">
					    <f:attribute name="caja" value="cajaUsuarioFiltroFH"/>
				    </a4j:commandButton>
         		</rich:column>
         	</h:panelGrid>
         	<h:panelGrid columns="4">
         		<rich:column  style="width: 110px">
         			<h:outputText value="Rango de Fecha : "/>
         		</rich:column>
         		<rich:column>
					<rich:calendar datePattern="dd/MM/yyyy"  value="#{accesoController.fechaInicioFiltro}"						
						jointPoint="down-right" direction="right" inputSize="21" showApplyButton="true"/> 
                </rich:column>
            	<rich:column style="width: 205px">
         			<rich:calendar datePattern="dd/MM/yyyy"  value="#{accesoController.fechaFinFiltro}"
						jointPoint="down-right" direction="right" inputSize="21" showApplyButton="true"/> 
         		</rich:column>
         		<rich:column>
         			<a4j:commandButton styleClass="btnEstilos" value="Buscar"
                    	action="#{accesoController.buscar}" reRender="panelTablaAccesosFH"/>
         		</rich:column>          	         		
         	</h:panelGrid>
            
            <rich:spacer height="12px"/>           
            
            <h:panelGrid id="panelTablaAccesosFH">
	        	<rich:extendedDataTable id="tblAccesosFH" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{accesoController.listaAccesosEspeciales}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="860px" 
					height="170px"
					align="center">
                                
					<rich:column width="30px">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="140px">
                    	<f:facet name="header">
                        	<h:outputText value="Nombre de Usuario"/>
                      	</f:facet>
                      	<h:outputText value="#{item.naturalOpera.strNombres} #{item.naturalOpera.strApellidoPaterno} #{item.naturalOpera.strApellidoMaterno}"/>
                	</rich:column>
                    <rich:column width="90">
                    	<f:facet name="header">
                        	<h:outputText value="Estado"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_INT}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intIdEstado}"/>
                  	</rich:column>
                    <rich:column width="80">
                   		<f:facet name="header">
                        	<h:outputText value="Empresa"/>
                        </f:facet>
                        <h:outputText value="#{item.juridica.strSiglas}"/>
                  	</rich:column>
                  	<rich:column width="130">
                   		<f:facet name="header">
                        	<h:outputText value="Motivo"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOSACCESO}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaTipoMotivo}"/>
                  	</rich:column>
                    <rich:column width="130">
                    	<f:facet name="header">
                        	<h:outputText value="Rango de Fecha"/>
                    	</f:facet>
                    	<h:outputText value="#{item.tsFechaInicio}">
                           	<f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                        <h:outputText value=" - "/>
                        <h:outputText value="#{item.tsFechaFin}">
                           	<f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="130">
                  		<f:facet name="header">
                        	<h:outputText value="Autorizado por"/>
                     	</f:facet>
                     	<h:outputText value="#{item.naturalAutoriza.strNombres} #{item.naturalAutoriza.strApellidoPaterno} #{item.naturalAutoriza.strApellidoMaterno}"/>
                  	</rich:column>
                    <rich:column width="130">
                  		<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                     	</f:facet>
                     	<h:outputText value="#{item.tsFechaRegistro}">
                           	<f:convertDateTime pattern="dd-MM-yyyy hh:mm" />
                        </h:outputText>
                  	</rich:column>
                    
                    <f:facet name="footer">   
							<rich:datascroller for="tblAccesosFH" maxPages="20"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{accesoController.seleccionarRegistro}"
						reRender="frmAlertaRegistroFH,contPanelInferiorFH,panelBotonesFH"
						oncomplete="if(#{accesoController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistroFH')}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>  
            	       	
         	</h:panelGrid>
	          	
                 
			<h:panelGrid columns="1" style="margin-left: auto; margin-right: auto">
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
		</rich:panel>
		
				
		<rich:panel id="panelMensajeFH" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%">
			<rich:panel
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{accesoController.mostrarMensajeExito}">
				<h:outputText value="#{accesoController.mensajeOperacion}" styleClass="msgInfo"/>			
			</rich:panel>
			<rich:panel
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{accesoController.mostrarMensajeError}">
				<h:outputText value="#{accesoController.mensajeOperacion}" styleClass="msgError"/>			
			</rich:panel>
		</rich:panel>				 
		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesFH">
			<h:panelGrid columns="4">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{accesoController.habilitarPanelInferior}" reRender="contPanelInferiorFH,panelMensajeFH,panelBotonesFH" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{accesoController.grabar}" reRender="contPanelInferiorFH,panelMensajeFH,panelBotonesFH"
			    	disabled="#{!accesoController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{accesoController.deshabilitarPanelInferior}" reRender="contPanelInferiorFH,panelMensajeFH"/>      
			</h:panelGrid>
		</rich:panel>		        	
		
		          	
		<rich:panel id="contPanelInferiorFH" style="border:0px;">
			<rich:spacer height="3px"/>  	 
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{accesoController.mostrarPanelInferior}">		          	 	
		    	<h:panelGroup id="panelIngresarAcceso">
		        	<h:panelGrid border="0" columns="2">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Empresa : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:selectOneMenu id="cboEmpresaInf" 
								style="width:210px;"
								value="#{accesoController.accesoEspecialNuevo.intPersEmpresa}"
								disabled="#{accesoController.deshabilitarNuevo||!accesoController.registrarNuevo}">
								<tumih:selectItems var="sel" value="#{accesoController.listaEmpresas}" 
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     
                     <rich:spacer height="4px"/>
                     
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Estado : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:selectOneMenu id="cboEstadoInf" 
								style="width:210px;"
								value="#{accesoController.accesoEspecialNuevo.intIdEstado}"
								disabled="#{accesoController.deshabilitarNuevo||accesoController.registrarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>	
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>                     
                     
                     <rich:spacer height="4px"/>
                     
                     <h:panelGrid border="0" columns="3">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Usuario : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:inputText size="37" id="cajaUsuarioOperaNuevoFH" readonly="true" 
                   				disabled="#{accesoController.deshabilitarNuevo}"
                   				value=" #{accesoController.accesoEspecialNuevo.naturalOpera.strNombres} 
         								#{accesoController.accesoEspecialNuevo.naturalOpera.strApellidoPaterno} 
         								#{accesoController.accesoEspecialNuevo.naturalOpera.strApellidoMaterno}"
         					/>
                     	</rich:column>
                     	<rich:column>
                    		<a4j:commandButton 
								actionListener="#{accesoController.buscarUsuarios}"
								value="Buscar" 
					    		oncomplete="Richfaces.showModalPanel('pBuscarUsuario')"
					    		reRender="pgListaUsuarios"
					    		styleClass="btnEstilos">
					    		<f:attribute name="caja" value="cajaUsuarioOperaNuevoFH"/>
				    		</a4j:commandButton>
                     	</rich:column>
                     </h:panelGrid>
                     
                     <rich:spacer height="4px"/>
                     
                     <h:panelGrid columns="3">
                     	<rich:column style="width:110px">
                     		<h:outputText value="Fecha Hora Inicio : "/>
                     	</rich:column>
                     	<rich:column>
							<rich:calendar datePattern="dd/MM/yyyy HH:mm"  value="#{accesoController.fechaInicioNuevo}"  
								showApplyButton="true" defaultTime="00:00" disabled="#{accesoController.deshabilitarNuevo}"/> 
		                </rich:column>
                     </h:panelGrid>
                     
                     <rich:spacer height="4px"/>
                     
                     <h:panelGrid columns="3">
                     	<rich:column style="width:110px">
                     		<h:outputText value="Fecha Hora Fin : "/>
                     	</rich:column>
                     	<rich:column>
							<rich:calendar datePattern="dd/MM/yyyy HH:mm"  value="#{accesoController.fechaFinNuevo}"  
								showApplyButton="true" defaultTime="00:00" disabled="#{accesoController.deshabilitarNuevo}"/> 
		                </rich:column>
                     </h:panelGrid>
                     
                     <rich:spacer height="10px"/>
                     
                     <h:panelGrid columns="1">
                     	<rich:column>
                     		<h:selectBooleanCheckbox value="#{accesoController.habilitarDias}" 
                     			disabled="#{accesoController.deshabilitarNuevo}">
                     			<a4j:support event="onclick" reRender="panelDias"/>
                     		</h:selectBooleanCheckbox>
                     		<h:outputText value="Dia de la semana"/>
                     	</rich:column>                    	
		         	</h:panelGrid>
		         	
		         	<rich:spacer height="6px"/>
		         	
		         	<h:panelGrid columns="7" id="panelDias">
			         	<rich:dataTable value="#{accesoController.listaAccesosEspecialesDetalle}" var="item">
							<rich:column width="50">
	                  			<f:facet name="header">
	                        		<h:outputText value="Seleccionar"/>
	                     		</f:facet>
	                     		<h:selectBooleanCheckbox value="#{item.checked}" 
	                     			disabled="#{!accesoController.habilitarDias || accesoController.deshabilitarNuevo}"/>
	                  		</rich:column>
	                  		<rich:column width="100">
	                  			<f:facet name="header">
	                        		<h:outputText value="Dia"/>
	                     		</f:facet>
	                     		<h:outputText value="#{item.dia.strDescripcion}"/>
	                  		</rich:column>
						</rich:dataTable>
					</h:panelGrid>
					
					<rich:spacer height="6px"/>
		         	
		         	<h:panelGrid columns="1">
						<rich:column>
							<h:selectBooleanCheckbox value="#{accesoController.seleccionaFeriados}"
								disabled="#{accesoController.deshabilitarNuevo}"/>
							<h:outputText value="Considerar Feriados de Calendario"/>
		                </rich:column>                     
		         	</h:panelGrid>
		         	
		         	<rich:spacer height="10px"/>
		         	
		         	<h:panelGrid columns="2" styleClass="rich-tabcell-noborder">
						<rich:column style="width:110px">
							<h:outputText value="Observación : "/>
		                </rich:column>		                
						<rich:column>
							<h:panelGrid columns="2">
								<rich:column style="width:90px">
									<h:outputText value="Motivo : "/>
				                </rich:column>		                
								<rich:column>
									<h:panelGrid columns="1">
										<rich:column>
											<h:selectOneMenu
												style="width:262px;"
												value="#{accesoController.accesoEspecialNuevo.intParaTipoMotivo}"
												disabled="#{accesoController.deshabilitarNuevo}">
												<tumih:selectItems var="sel" value="#{accesoController.listaMotivosNuevo}" 
													itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
													propertySort="intOrden"/>
											</h:selectOneMenu>
										</rich:column>
									</h:panelGrid>
				                </rich:column>
				         	</h:panelGrid>
				         	<h:panelGrid columns="2">
								<rich:column style="width:90px">
									<h:outputText value="Autorizado por :"/>
				                </rich:column>
								<rich:column>
									<h:panelGrid columns="2">
										<rich:column >
						         			<h:inputText size="30" id="cajaUsuarioAutorizaNuevoFH" readonly="true" 
				                   				disabled="#{accesoController.deshabilitarNuevo}"
				                   				value=" #{accesoController.accesoEspecialNuevo.naturalAutoriza.strNombres} 
				         								#{accesoController.accesoEspecialNuevo.naturalAutoriza.strApellidoPaterno} 
				         								#{accesoController.accesoEspecialNuevo.naturalAutoriza.strApellidoMaterno}"
				         					/>
						         		</rich:column>
						         		 <rich:column  style="width: 60px">
						         			<a4j:commandButton 
												actionListener="#{accesoController.buscarUsuarios}"
												value="Buscar"
									    		oncomplete="Richfaces.showModalPanel('pBuscarUsuario')"
									    		reRender="pgListaUsuarios"
									    		styleClass="btnEstilos">
									    		<f:attribute name="caja" value="cajaUsuarioAutorizaNuevoFH"/>
								    		</a4j:commandButton>
						         		</rich:column>
					         		</h:panelGrid>
				                </rich:column>
				         	</h:panelGrid>
		                </rich:column>
		         	</h:panelGrid>
                    <h:panelGrid>
				    	<h:inputTextarea cols="95" rows="4" value="#{accesoController.accesoEspecialNuevo.strObservacion}"
				    		disabled="#{accesoController.deshabilitarNuevo}"/>
				    </h:panelGrid>
		       	</h:panelGroup> 	
		  	</rich:panel>
		</rich:panel>