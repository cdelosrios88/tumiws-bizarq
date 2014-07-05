<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca   -->
	<!-- Modulo    :                -->
	<!-- Prototipo : Permiso de acceso de Computadoras		-->			
	<!-- Fecha     :                -->

<rich:modalPanel id="pAlertaRegistro" width="400" height="120"
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
            			action="#{computadoraController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{computadoraController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaComputadoras,panelMensaje,contPanelInferior"
             			rendered="#{computadoraController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

<h:panelGroup style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5;">
	<rich:panel style="margin-left: auto; margin-right: auto;width:700px;">
	
	<h:form id="frmComputadora">	

		<rich:panel style="border: 0px solid #17356f"	styleClass="rich-tabcell-noborder">
        	
        	<h:panelGrid columns="5">
         		<rich:column style="width: 100px">
         			<h:outputText value="Empresa : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu id="cboEmpresa" 
						style="width: 150px;"
						value="#{computadoraController.computadoraFiltro.id.intPersEmpresaPk}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{computadoraController.listaEmpresas}" 
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
						<a4j:support event="onchange" action="#{computadoraController.seleccionarEmpresa}" reRender="colSucursal,colArea"  />
					</h:selectOneMenu>
              	</rich:column>
                <rich:column style="width: 50px">
         		</rich:column>
         		<rich:column style="width: 80px">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column>
                	<h:selectOneMenu id="cboEstado" 
						style="width: 120px;"
						value="#{computadoraController.computadoraFiltro.intIdEstado}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>					
					</h:selectOneMenu>
              	</rich:column>
         	</h:panelGrid>
         	<h:panelGrid columns="6">
         		<rich:column  style="width: 100px">
         			<h:outputText value="Sucursal : "/>
         		</rich:column>
         		<rich:column id="colSucursal">
					<h:selectOneMenu id="cboSucursal" 
						style="width: 150px;"
						value="#{computadoraController.computadoraFiltro.id.intIdSucursal}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{computadoraController.listaSucursales}" 
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
						<a4j:support event="onchange" action="#{computadoraController.seleccionarSucursal}" reRender="colArea"  />
					</h:selectOneMenu>
                </rich:column>
            	<rich:column  style="width: 50px">
         		</rich:column>
         		<rich:column  style="width: 80px">
         			<h:outputText value="Área : "/>
         		</rich:column>
         		<rich:column id="colArea">
					<h:selectOneMenu id="cboArea" 
						style="width: 120px;"
						value="#{computadoraController.computadoraFiltro.id.intIdArea}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{computadoraController.listaAreas}" 
							itemValue="#{sel.area.id.intIdArea}" itemLabel="#{sel.area.strAbreviatura}"/>
					</h:selectOneMenu>
                </rich:column>
                <rich:column  style="width: 90px;text-align:right">
         			<a4j:commandButton styleClass="btnEstilos" value="Buscar"
                    	action="#{computadoraController.buscar}" reRender="panelTablaComputadoras"/>
         		</rich:column>         		
         	</h:panelGrid>
         	 
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaComputadoras">
	        	<rich:extendedDataTable id="tblComputadoras" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{computadoraController.listaComputadoras}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="650px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align:center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="100px" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText  value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="100" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText value="Área"/>
                      	</f:facet>
						<h:outputText value="#{item.area.strAbreviatura}"/>
                  	</rich:column>
                    <rich:column width="150" style="text-align:center">
                   		<f:facet name="header">
                        	<h:outputText value="Identificador de PC's"/>
                        </f:facet>
                        <h:outputText value="#{item.strIdentificador}"/>
                  	</rich:column>
                    <rich:column width="90" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText value="Estado"/>
                    	</f:facet>
                    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_INT}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intIdEstado}"/>
                  	</rich:column>
                  	<rich:column width="180" style="text-align:center">
                  		<f:facet name="header">
                        	<h:outputText value="Fecha-Hora de Activación"/>
                     	</f:facet>
                     	<h:outputText value="#{item.dtFechaActivacion}">
                           	<f:convertDateTime pattern="dd-MM-yyyy hh:mm" />
                        </h:outputText>
                  	</rich:column>
                    <f:facet name="footer">   
							<rich:datascroller for="tblComputadoras" maxPages="20"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
							actionListener="#{computadoraController.seleccionarRegistro}"
							reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
							oncomplete="if(#{computadoraController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        		<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
         	
         	</h:panelGrid>
         	      
			<h:panelGrid columns="1" style="margin-left: auto; margin-right: auto">
				 <h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
		</rich:panel>		
		
		<rich:panel id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%">
			<rich:panel id="subPanelMensajeExito"
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{computadoraController.mostrarMensajeExito}">
				<h:outputText value="#{computadoraController.mensajeOperacion}" styleClass="msgInfo"/>			
			</rich:panel>
			<rich:panel id="subPanelMensajeError"
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{computadoraController.mostrarMensajeError}">
				<h:outputText value="#{computadoraController.mensajeOperacion}" styleClass="msgError"/>			
			</rich:panel>
		</rich:panel>
		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="4">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{computadoraController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{computadoraController.grabar}" reRender="contPanelInferior,panelMensaje,panelBotones"
			    	disabled="#{!computadoraController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{computadoraController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje"/>      
			</h:panelGrid>
		</rich:panel>
		          	
		<rich:panel id="contPanelInferior" style="border: 0px solid #17356f;">
			<rich:spacer height="3px"/>
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{computadoraController.mostrarPanelInferior}">		          	 	
		    	<h:panelGroup id="panelIngresarComputadora">
		        	<h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Empresa : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:selectOneMenu id="cboEmpresaInf" 
								style="width:210px;"
								value="#{computadoraController.computadoraNuevo.id.intPersEmpresaPk}"
								disabled="#{computadoraController.deshabilitarNuevo||!computadoraController.registrarNuevo}">
								<f:selectItem itemValue="-1" itemLabel="Seleccionar..."/>
								<tumih:selectItems var="sel" value="#{computadoraController.listaEmpresas}" 
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
								<a4j:support event="onchange" action="#{computadoraController.seleccionarEmpresaNuevo}" reRender="colSucursalInf,colAreaInf"/>
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Estado : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:selectOneMenu id="cboEstadoInf" 
								style="width:210px;"
								value="#{computadoraController.computadoraNuevo.intIdEstado}"
								disabled="#{computadoraController.deshabilitarNuevo||computadoraController.registrarNuevo}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>	
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Nombre de Sucursal : "/>
                     	</rich:column>
                        <rich:column id="colSucursalInf">
                   			<h:selectOneMenu id="cboSucursalInf" 
								style="width:210px;"
								value="#{computadoraController.computadoraNuevo.id.intIdSucursal}"
								disabled="#{computadoraController.deshabilitarNuevo||!computadoraController.registrarNuevo}">
								<tumih:selectItems var="sel" value="#{computadoraController.listaSucursalesNuevo}" 
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
								<a4j:support event="onchange" action="#{computadoraController.seleccionarSucursalNuevo}" reRender="colAreaInf"/>
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Nombre de Área : "/>
                     	</rich:column>
                        <rich:column id="colAreaInf">
                   			<h:selectOneMenu id="cboAreaInf" 
								style="width:210px;"
								value="#{computadoraController.computadoraNuevo.id.intIdArea}"
								disabled="#{computadoraController.deshabilitarNuevo||!computadoraController.registrarNuevo}">
								<tumih:selectItems var="sel" value="#{computadoraController.listaAreasNuevo}" 
									itemValue="#{sel.area.id.intIdArea}" itemLabel="#{sel.area.strAbreviatura}"/>
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Número de Identificación : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:inputText size="60" id="cajaNumId" value="#{computadoraController.computadoraNuevo.strIdentificador}" disabled="#{computadoraController.deshabilitarNuevo}"/>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid border="0" columns="2">
                    	<rich:column style="width:160px">
                   			<h:outputText value="Código de Terceros : "/>
                     	</rich:column>
                        <rich:column>
                        	<rich:spacer height="3px"/>    
                        	<h:panelGrid border="0" columns="1">
                        		<rich:column id="colTerceros">
                   			 		<h:panelGrid border="0" columns="4">
                   			 			<rich:column style="width:63px">
                   							<h:outputText value="Nombre : "/>
		                     			</rich:column>
		                        		<rich:column id="colSelAcceso">
		                   			 		<h:selectOneMenu 
												style="width:100px;"
												value="#{computadoraController.accesoASelec.intIdDetalle}"
												disabled="#{computadoraController.deshabilitarNuevo}">
												<tumih:selectItems var="sel" value="#{computadoraController.listaAccesoNoSelec}" 
													itemValue="#{sel.acceso.intIdDetalle}" itemLabel="#{sel.acceso.strDescripcion}"/>	
											</h:selectOneMenu>
										</rich:column>
										<rich:column>
											<a4j:commandButton styleClass="btnEstilos" value="Agregar"
		                    					actionListener="#{computadoraController.agregarAcceso}" reRender="panelInferior"
		                    					disabled="#{computadoraController.deshabilitarNuevo}"/>
		                    			</rich:column>
										<rich:column>
		                    				<a4j:commandButton styleClass="btnEstilos" value="Eliminar"
		                    					actionListener="#{computadoraController.eliminarAcceso}" reRender="panelInferior"
		                    					disabled="#{computadoraController.deshabilitarNuevo}"/>
		                     			</rich:column>
                     				</h:panelGrid>
                     			</rich:column>
                     		</h:panelGrid>
                     		<rich:spacer height="3px"/>
                     		<h:panelGrid border="0" columns="1">
                     			<rich:column id="colTblAccesos">
	                     			<rich:extendedDataTable id="tblAccesos" 
						          		enableContextMenu="false"
						          		sortMode="single" 
					                    var="item" 
					                    value="#{computadoraController.listaAccesoSelec}"  
										rowKeyVar="rowKey" 
										rows="4" 
										width="260px" 
										height="150px" 
										align="center">
					                                
										<rich:column width="20px" style="text-align:center">
										</rich:column>
					                	<rich:column width="120px">
					                		<f:facet name="header">
			                        			<h:outputText value="Nombre"/>
			                     			</f:facet>
										 	<h:selectBooleanCheckbox value="#{item.checked}" 
										 		disabled="#{computadoraController.deshabilitarNuevo}"/>
					                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSWTERCERO}" 
												itemValue="intIdDetalle" itemLabel="strDescripcion" 
												property="#{item.intIdTipoAcceso}"/>
					                	</rich:column>
					                	<rich:column width="120px" style="text-align:center">
					                		<f:facet name="header">
			                        			<h:outputText value="Estado"/>
			                     			</f:facet>
										 	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
												itemValue="intIdDetalle" itemLabel="strDescripcion" 
												property="#{item.intIdEstado}"/>
					                	</rich:column>
					                	<f:facet name="footer">   
											<rich:datascroller for="tblAccesos" maxPages="10"/>   
										</f:facet>
			                		</rich:extendedDataTable>
		                		</rich:column>
                   			 </h:panelGrid>                   			 
                     	</rich:column>
                     </h:panelGrid>
		       	</h:panelGroup>		          	 	
		  	</rich:panel>
		</rich:panel> 	
	</h:form> 	          	  
</rich:panel>
</h:panelGroup>