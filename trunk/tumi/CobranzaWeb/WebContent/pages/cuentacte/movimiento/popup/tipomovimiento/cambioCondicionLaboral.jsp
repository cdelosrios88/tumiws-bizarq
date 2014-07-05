<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	 <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"
		                    value="Grabar" 
		                    styleClass="btnEstilos" 
		                    actionListener="#{cuentaCteController.grabarCambioCondicionLaboral}" 
		                   oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpCambioCondicionLaboral')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,formCambioCondicionLaboral">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpCambioCondicionLaboral')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormCambioCondicionLaboral" layout="block" > 
   <rich:panel style=";border:0px">
            <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
    </rich:panel>
	     <rich:panel style="width: 780px;border:1px solid #800f;background-color:#DEEBF5;">
		          <h:panelGrid  columns="4">
		             <rich:column style="width:80px; border: none">
		         		<h:outputText  value="Fecha :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:160px; border: none">
		         	      <h:inputText value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.dtFechaDocumento}">
		         	          <f:convertDateTime pattern="dd/MM/yyyy"/>
		         	      </h:inputText>
		             </rich:column>
		             <rich:column style="width:220px; border: none">
		         		<h:outputText  value="Motivo de Cambio de Condiciòn Laboral:"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:200px; border: none">
		         	  		<h:selectOneMenu  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" style="width:200px"
		         	  		               disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}">
			         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoCambioCondicionLaboral}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
			         </rich:column>
		         </h:panelGrid>  
		         <h:panelGrid  columns="3" >
		             <rich:column style="width:80px; border: none">
		         		<h:outputText  value="Tipo Rol :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:100px; border: none">
			    			<h:outputText value="#{cuentaCteController.strListaPersonaRol}"></h:outputText>
			         </rich:column>
			         <rich:column style="width:600px; border: none">
		         		  <h:outputText value="#{cuentaCteController.socioCom.socio.id.intIdPersona} - #{cuentaCteController.socioCom.socio.strNombreSoc} #{cuentaCteController.socioCom.socio.strApePatSoc} #{cuentaCteController.socioCom.socio.strApeMatSoc} "/>
		        		  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioCom.cuenta.intParaCondicionCuentaCod}"/>
		         			          - 
		         	      <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioCom.cuenta.intParaSubCondicionCuentaCod}"/>
							          
		         	     DNI:<h:outputText value="#{cuentaCteController.socioCom.persona.documento.strNumeroIdentidad}"></h:outputText>
					     Cta:<h:outputText value="#{cuentaCteController.socioCom.cuenta.strNumeroCuenta}"></h:outputText>
		         	 </rich:column>
		        </h:panelGrid>
		  </rich:panel>
		  <rich:panel id="cambioCondicionLaboral" style="width: 780px;border:1px solid #800f;background-color:#DEEBF5;"> 
			   <h:panelGrid  columns="2" style="width:600px; border: none">
			      <rich:column style="width:300px; border: 1" >
			         		<h:outputText  value="Condición Laboral:"> </h:outputText>
			      </rich:column>
			      <rich:column colspan="2" rendered="#{cuentaCteController.habilitarFormCambioCondicionLaboral}">
			         		<h:outputText  value="Nueva Condición Laboral:"> </h:outputText>
			      </rich:column>
			   </h:panelGrid>     
			   
			    <h:panelGrid  columns="2" style="width:600px; border: none">
			      <rich:column  style="width:300px; border: 1">
			          <h:panelGrid columns="2">
			            <rich:column>
			                 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
								   		      itemValue="intIdDetalle" itemLabel="strDescripcion" 
											  property="#{cuentaCteController.intCondicionLaboral}"/>
					    </rich:column>
			            <rich:column style="width:400px; border: 1">
			                <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DETALLECONDICIONLABORAL}" 
								   		      itemValue="intIdDetalle" itemLabel="strDescripcion" 
											  property="#{cuentaCteController.intCondicionLaboralDet}"/>
				         </rich:column>
			          </h:panelGrid>
			      </rich:column>
				  <rich:column style="width:400px; border: 1" rendered="#{cuentaCteController.habilitarFormCambioCondicionLaboral}">
				     <h:panelGrid columns="3">
				        <rich:column>
				           <h:selectOneMenu value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboral}"
			     						valueChangeListener="#{cuentaCteController.loadListDetCondicionLaboral}"
			     						disabled="#{cuentaCteController.intCondicionLaboralDisabled}">
			     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
			     						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
									  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									  	<a4j:support event="onchange" reRender="cboDetCondicionLaboral,divContrato,panelDatosLaborales" ajaxSingle="true"></a4j:support>
			     				</h:selectOneMenu>
				        </rich:column>
				        <rich:column>
				                <h:selectOneMenu       id    = "cboDetCondicionLaboral" style="width:150px"
						     						value    = "#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboralDet}"
						     						disabled = "#{!cuentaCteController.habilitarFormCambioCondicionLaboral}">
			     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
			     						<tumih:selectItems var="sel" value="#{cuentaCteController.listDetCondicionLaboral}" 
									  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			     			  </h:selectOneMenu>
			     		</rich:column>	
			     		<rich:column>
			     		  <a4j:commandButton value = "Cambiar" 
				    						 styleClass = "btnEstilos"
				    						 disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"
				    						 actionListener = "#{cuentaCteController.cambiarCondicionLaboral}"
				    						 reRender="divFormCambioCondicionLaboral"
				    						 >
				    	      <f:attribute name="pIntCondicionLaboral" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboral}"/>
				    	      <f:attribute name="pIntCondicionLaboralDet" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboralDet}"/> 	 	
				    	   	 	
				    	  </a4j:commandButton>  
			     		</rich:column>  
				     </h:panelGrid>
				  </rich:column>
	         </h:panelGrid>  
	      </rich:panel>  
		 
			<h:panelGroup id="panelDatosLaborales" style="width: 780px;border:1px solid #800f;background-color:#DEEBF5;" layout="block" style="margin-top:20px" >
	     		<h:panelGrid rendered="#{cuentaCteController.habilitarDatosLaborales}">
	     			<h:outputText value="DATOS LABORALES" style="font-weight: bold; text-decoration: underline"></h:outputText>
	     		</h:panelGrid>
	     		
	     		<h:panelGroup layout="block" style="float:left" rendered="#{cuentaCteController.habilitarDatosLaborales}">
	     			<h:panelGrid columns="4" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Centro de Trabajo"></h:outputText>
		     				</rich:column>
		     				<rich:column colspan="3">
		     					<h:inputText size="48" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.strCentroTrabajo}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"></h:inputText>
		     				</rich:column>
	     				</rich:columnGroup>
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Fecha Inicio Servicio"></h:outputText>
		     				</rich:column>
		     				<rich:column>
		     					<rich:calendar value="#{cuentaCteController.socioCom.persona.natural.perLaboral.dtInicioServicio}" datePattern="dd/MM/yyyy" inputStyle="width:70px" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"></rich:calendar>
		     				</rich:column>
		     				<rich:column>
		     					<h:outputText value="Remuneración"></h:outputText>
		     				</rich:column>
		     				<rich:column>
		     					<h:inputText size="9" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.bdRemuneracion}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"></h:inputText>
		     				</rich:column>
	     				</rich:columnGroup>
	     			</h:panelGrid>
	     		</h:panelGroup>
	     		<h:panelGroup id="divContrato" layout="block" style="float:left" rendered="#{cuentaCteController.habilitarDatosLaborales}">
	     			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
	     						<h:outputText value="Cargo"></h:outputText>
	     					</rich:column>
	     					<rich:column>
	     						<h:selectOneMenu value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCargo}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CARGOPERSONAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column colspan="2">
	     						<h:outputText value="Régimen Laboral"></h:outputText>
	     						<h:selectOneMenu style="width:100px; margin-left:10px" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intRegimenLaboral}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_REGIMENLABORAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     					<rich:column colspan="4">
	     						<h:outputText value="Solicitud ONP"></h:outputText>
	     						<h:inputText style="margin-left:10px" size="10" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.strSolicitudONP}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"></h:inputText>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup rendered="#{cuentaCteController.isContratado}">
	     					<rich:column>
	     						<h:outputText value="Tipo Contrato"></h:outputText>
	     					</rich:column>
	     					<rich:column>
	     						<h:selectOneMenu style="width:100px" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intTipoContrato}" disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"
	     										onchange="selecTipoContrato(#{applicationScope.Constante.ONCHANGE_VALUE})">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONTRATO}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     					<rich:column colspan="4">
	     						<h:outputText value="Inicio"></h:outputText>
	     						<rich:calendar id="calIniContrato" datePattern="dd/MM/yyyy" inputStyle="width:70px; margin-left:10px" 
	     									value="#{cuentaCteController.socioCom.persona.natural.perLaboral.dtInicioContrato}"
	     									disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"/>
	     						<h:outputText value="Fin" style="margin-left:20px"></h:outputText>
	     						<rich:calendar id="calFinContrato" datePattern="dd/MM/yyyy" inputStyle="width:70px; margin-left:10px"
	     									value="#{cuentaCteController.socioCom.persona.natural.perLaboral.dtFinContrato}"
	     									disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}" />
	     					</rich:column>	     					
	     				</rich:columnGroup>
	     				<rich:columnGroup id="colContrato" rendered="#{cuentaCteController.isContratado}">
	     					<rich:column colspan="6">
	     						<a4j:commandButton value="Adjuntar Contrato" 
	     						                   disabled="#{!cuentaCteController.habilitarFormCambioCondicionLaboral}"
	     						                   styleClass="btnEstilos1" 
	     						                   reRender="panelDocCobranzaG,idFormFileUpload"
	     						                   actionListener="#{cuentaCteController.adjuntarContrato}"
                		                           oncomplete="Richfaces.showModalPanel('pAdjuntarDocumento')"/>
	     						<h:inputText id="txtContrato" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.contrato == null?
	     										cuentaCteController.fileContrato.strNombrearchivo:
	     										cuentaCteController.socioCom.persona.natural.perLaboral.contrato.strNombrearchivo}"
	     										size="50" style="margin-left:10px" readonly="true"/>
	     						<h:commandLink value="Ver" styleClass="buttonLink" style="margin-left:10px" target="_blank"
	     										actionListener="#{cuentaCteController.verContratoPerNatu}"
	     										rendered="#{cuentaCteController.fileContrato!=null}"/>
	     						<a4j:commandButton styleClass="btnEstilos" value="Quitar Documento" reRender="divFormCambioCondicionLaboral"
			                		action="#{cuentaCteController.removeContrato}" style="width:180px;padding-left:20px;"
			                		rendered="#{cuentaCteController.fileContrato!=null}"/>
	     					</rich:column>
	     				</rich:columnGroup>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     			<h:panelGrid columns="1" styleClass="tableCellBorder4">
	     			    <rich:column>
	     						<h:outputText value="."></h:outputText>
	     				</rich:column>
	     			</h:panelGrid>
	     		</h:panelGroup>
	         </h:panelGroup>
	         
	 </h:panelGroup>
	 
	 