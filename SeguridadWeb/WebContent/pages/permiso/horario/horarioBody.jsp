<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca   -->
	<!-- Modulo    :                -->
	<!-- Prototipo :  Horario		-->			
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
            			action="#{horarioController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{horarioController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaHorarios,panelMensaje,contPanelInferior"
             			rendered="#{horarioController.mostrarBtnEliminar}"
        			/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>


<h:panelGroup style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5;">
	<rich:panel style="margin-left: auto; margin-right: auto;width:700px;">
	
	<h:form id="frmEstrucOrg">
		<rich:panel style="border: 0px solid #17356f;" styleClass="rich-tabcell-noborder">
        	
        	<h:panelGrid columns="4">
         		<rich:column style="width: 90px;" >
         			<h:outputText value="Empresa : "/>
         		</rich:column>
         		<rich:column style="width: 210px;">
                	<h:selectOneMenu id="cboEmpresa" 
						style="width: 150px;"
						value="#{horarioController.diasAccesosFiltro.id.intPersEmpresa}">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_COMBO_SELECCIONAR}" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{horarioController.listaEmpresas}" 
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>						
					</h:selectOneMenu>
              	</rich:column>
                <rich:column style="width: 120px">
         			<h:outputText value="Tipo de Sucursal : "/>
         		</rich:column>
         		<rich:column>
					<h:selectOneMenu id="cboTipoSucursal" 
						style="width: 110px;"
						value="#{horarioController.diasAccesosFiltro.id.intIdTipoSucursal}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
             	</rich:column>                               	       
         	</h:panelGrid>
         	
         	<h:panelGrid columns="3" border="0">
            	<rich:column style="width: 90px">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column style="width: 210px">
					<h:selectOneMenu id="cboEstadoRegistro" 
						style="width: 150px;"
						value="#{horarioController.diasAccesosFiltro.intIdEstado}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>								
					</h:selectOneMenu>
                 </rich:column>  
                 <rich:column id="col21">
                	<a4j:commandButton styleClass="btnEstilos" value="Buscar"
                    	actionListener="#{horarioController.buscar}" reRender="panelTablaHorarios"/>
            	</rich:column>                    
           	</h:panelGrid>
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaHorarios" style="text-align: center;">
	        	<rich:extendedDataTable id="tblHorarios" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{horarioController.listaDiasAccesos}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="630px" 
					height="170px" 
					align="center"
					style="text-align: center">
                                
					<rich:column width="30px" style="text-align:center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="150px" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText  value="Empresa"/>
                      	</f:facet>
                      	 <h:outputText value="#{item.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="130" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText value="Tipo de Sucursal"></h:outputText>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.id.intIdTipoSucursal}"/>
                  	</rich:column>
                    <rich:column width="120" style="text-align:center">
                   		<f:facet name="header">
                        	<h:outputText value="Fecha de Inicio"></h:outputText>
                        </f:facet>
                        <h:outputText value="#{item.tsFechaInicio}">
                            	<f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>
                  	</rich:column>
                    <rich:column width="120" style="text-align:center">
                    	<f:facet name="header">
                        	<h:outputText value="Fecha de Fin"></h:outputText>
                    	</f:facet>
                    	<h:outputText value="#{item.tsFechaFin}">
                            <f:convertDateTime pattern="dd-MM-yyyy" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align:center">
                  		<f:facet name="header">
                        	<h:outputText value="Estado"></h:outputText>
                     	</f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_INT}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intIdEstado}"/>
                  	</rich:column>
                    <f:facet name="footer">   
							<rich:datascroller for="tblHorarios" maxPages="20"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
							actionListener="#{horarioController.seleccionarRegistro}"
							reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
							oncomplete="if(#{horarioController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        		<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
         	
         	</h:panelGrid>
                 
			<h:panelGrid columns="1" style="margin-left: auto; margin-right: auto">
				<rich:spacer height="8px"/>
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
		</rich:panel>
		
		<rich:panel id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%" 
			styleClass="rich-tabcell-noborder">
			<rich:panel id="subPanelMensajeExito"
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{horarioController.mostrarMensajeExito}">
				<h:outputText value="#{horarioController.mensajeOperacion}" styleClass="msgInfo"/>			
			</rich:panel>
			<rich:panel id="subPanelMensajeError"
				style="border: 0px solid #17356f;background-color:#DEEBF5;" 
				styleClass="rich-tabcell-noborder"
				rendered="#{horarioController.mostrarMensajeError}">
				<h:outputText value="#{horarioController.mensajeOperacion}" styleClass="msgError"/>			
			</rich:panel>
		</rich:panel>
		
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{horarioController.habilitarPanelInferior}" reRender="contPanelInferior,panelMensaje" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{horarioController.grabar}" reRender="contPanelInferior,panelMensaje,panelBotones"
			    	disabled="#{!computadoraController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{horarioController.deshabilitarPanelInferior}" reRender="contPanelInferior,panelMensaje"/>      
			</h:panelGrid>
		</rich:panel>
		          	
		<rich:panel id="contPanelInferior" style="border: 0px solid #17356f;background-color:#DEEBF5">
			<rich:spacer height="3px"/>  	 
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{horarioController.mostrarPanelInferior}">		          	 	
		    	<h:panelGroup id="panelIngresarHorario">
		        	<h:panelGrid columns="2" border="0">
                    	<rich:column style="width:120px">
                   			<h:outputText value="Empresa : "/>
                     	</rich:column>
                        <rich:column >
                   			<h:selectOneMenu id="cboEmpresaInf" 
										style="width:110px;"
										disabled="#{horarioController.deshabilitarNuevo}"
										value="#{horarioController.nuevoDiasAccesos.id.intPersEmpresa}">
								<tumih:selectItems var="sel" value="#{horarioController.listaEmpresas}" 
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
							</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     <h:panelGrid columns="2" border="0">
                    	<rich:column style="width:120px">
                   			<h:outputText value="Tipo de Sucursal : "/>
                     	</rich:column>
                        <rich:column>
                        	<h:selectOneMenu id="cboTipoSucursalInf" 
										style="width:110px;"
										disabled="#{horarioController.deshabilitarNuevo || !horarioController.registrarNuevo}"
										value="#{horarioController.nuevoDiasAccesos.id.intIdTipoSucursal}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
                    </h:panelGrid>
                    <h:panelGrid columns="2">
                    	<rich:column style="width: 120px">
         					<h:outputText value="Estado : "/>
         				</rich:column>
         				<rich:column>
							<h:selectOneMenu id="cboTipoEstadoInf" 
								style="width: 110px;"
								disabled="#{horarioController.deshabilitarNuevo || horarioController.registrarNuevo}"
								value="#{horarioController.nuevoDiasAccesos.intIdEstado}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
                 		</rich:column>
                    </h:panelGrid>
                    <rich:spacer height="8px"/>
                     <h:panelGrid columns="1">
                    	<rich:column>
							<h:selectBooleanCheckbox value="#{horarioController.seleccionaFeriados}"
								disabled="#{horarioController.deshabilitarNuevo}"/>
         					<h:outputText value="  Considerar feriados en el calendario"/>
                 		</rich:column>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    <h:panelGrid columns="5">
                    	<rich:column style="width: 120px">
         					<h:outputText value="Fecha de Inicio : "/>
         				</rich:column>
         				<rich:column style="width: 110px">
							<rich:calendar datePattern="dd/MM/yyyy"  value="#{horarioController.fechaInicio}"  
								disabled="#{horarioController.deshabilitarNuevo}"
								jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/> 
                 		</rich:column>
                 		<rich:column style="width: 100px">
         					<h:outputText value="Fecha de Fin : "/>
         				</rich:column>
         				<rich:column style="width: 110px" id="colFechaFin">
							<rich:calendar datePattern="dd/MM/yyyy"  value="#{horarioController.fechaFin}"
								disabled="#{horarioController.deshabilitarNuevo || !horarioController.habilitarFechaFin}"
								jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/> 
                 		</rich:column>
                 		<rich:column style="width: 120px">
         					 <h:selectBooleanCheckbox value="#{horarioController.seleccionaIndeterminado}"
         					 	disabled="#{horarioController.deshabilitarNuevo}">
         					 	<a4j:support event="onclick" action="#{horarioController.manejarIndeterminado}" reRender="panelIngresarHorario"/>
         					 </h:selectBooleanCheckbox>
         					 <h:outputText value="   Indeterminado"/>
         				</rich:column>
                    </h:panelGrid>
                    
                    <rich:spacer height="4px"/>
                    
                     <h:panelGrid id="panelTablaDias">
			        	<rich:extendedDataTable id="tblDias" 
			          		enableContextMenu="false"
			          		sortMode="single" 
		                    var="item" 
		                    value="#{horarioController.listaDiasAccesosDetalle}"  
							rowKeyVar="rowKey" 
							rows="7" 
							width="600px" 
							height="242px" 
							align="center">
		                                
		                	<rich:column width="100px">
		                		<f:facet name="header">
                        			<h:outputText value="Día"/>
                     			</f:facet>
							 	<h:selectBooleanCheckbox value="#{item.checked}" disabled="#{horarioController.deshabilitarNuevo}"/>
		                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DIASSEMANA}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.id.intIdDiaSemana}"/>
		                	</rich:column>
		                    <rich:column width="250" style="text-align: center">
		                    	<f:facet name="header">
                        			<h:outputText value="Hora Inicio"/>
                     			</f:facet>
							 	<h:panelGrid columns="5" style="text-align: center">
			                    	<rich:inputNumberSpinner styleClass="spinnerHora" value="#{item.intHoraInicio}" 
			                    		minValue="00" maxValue="23" enableManualInput="true" onchange="updateSpinnerValue(this);"
			                    		disabled="#{horarioController.deshabilitarNuevo}">
			                    		 <f:convertNumber pattern="00" />
			                    	</rich:inputNumberSpinner>	
	                   				<h:outputText value="hh "/>
	                   				<rich:inputNumberSpinner value="#{item.intMinutoInicio}" 
	                   					minValue="00" maxValue="59" enableManualInput="true" onchange="updateSpinnerValue(this);"
	                   					disabled="#{horarioController.deshabilitarNuevo}">
	                   					 <f:convertNumber pattern="00" />
	                   				</rich:inputNumberSpinner>
			                    	<h:outputText value="mm "/>
		                    	</h:panelGrid>
		                    	<script>
    								function updateSpinnerValue(element) {
								        var input = element.spinner.content.firstChild;
								        if (input.value.length == 1) {
								            input.value = "0"+input.value;
								        }
								    }
								</script>
		                  	</rich:column>
		                    <rich:column width="250" style="text-align: center">
		                    	<f:facet name="header">
                        			<h:outputText value="Hora Fin"/>
                     			</f:facet>
							 	<h:panelGrid columns="5">
			                    	<rich:inputNumberSpinner styleClass="spinnerHora" value="#{item.intHoraFin}" 
			                    		minValue="00" maxValue="23" enableManualInput="true" onchange="updateSpinnerValue(this);"
			                    		disabled="#{horarioController.deshabilitarNuevo}">
			                    		 <f:convertNumber pattern="00" />
			                    	</rich:inputNumberSpinner>	
	                   				<h:outputText value="hh "/>
	                   				<rich:inputNumberSpinner value="#{item.intMinutoFin}" 
	                   					minValue="00" maxValue="59" enableManualInput="true" onchange="updateSpinnerValue(this);"
	                   					disabled="#{horarioController.deshabilitarNuevo}">
	                   					 <f:convertNumber pattern="00" />
	                   				</rich:inputNumberSpinner>
			                    	<h:outputText value="mm "/>
		                    	</h:panelGrid>
		                  	</rich:column>
		            	</rich:extendedDataTable>         			
         			</h:panelGrid>
                    
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid columns="2" border="0">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Motivo : " />
                     	</rich:column>
                        <rich:column >
                   			<h:inputText value="#{horarioController.nuevoDiasAccesos.strMotivo}" size="60" id="cajaMotivo" disabled="#{horarioController.deshabilitarNuevo}"/>					
                     	</rich:column>
                    </h:panelGrid>
                    <h:panelGrid columns="2" border="0" rendered="#{!horarioController.registrarNuevo}">
                    	<rich:column style="width:110px">
                   			<h:outputText value="Archivo asociado" />
                     	</rich:column>
                        <rich:column >
                   			<h:inputText value="#{horarioController.nuevoDiasAccesos.archivo.strNombrearchivo}" size="60" id="cajaArchivo" disabled="true"/>					
                     	</rich:column>
                    </h:panelGrid>
                    <rich:spacer height="10px"/>
                    
                    <h:panelGrid columns="1" border="0">                    	
                     	<rich:column>
							<rich:fileUpload id="uploadDBF" 
	            					addControlLabel="Adjuntar Archivo"
								    clearControlLabel="Limpiar" 
								    cancelEntryControlLabel="Cancelar"
								    uploadControlLabel="Subir Archivo" 
								    listHeight="70" 
								    listWidth="320"
								    fileUploadListener="#{horarioController.manejarSubirArchivo}"
								    disabled="#{horarioController.deshabilitarNuevo}"
									maxFilesQuantity="1"
									doneLabel="Archivo cargado correctamente"
									immediateUpload="false">
							</rich:fileUpload>
	                  	</rich:column>
                     </h:panelGrid>                     
                   
		       	</h:panelGroup>		          	 	
		  	</rich:panel>
		</rich:panel>
		
</h:form>
</rich:panel>
</h:panelGroup>     	  