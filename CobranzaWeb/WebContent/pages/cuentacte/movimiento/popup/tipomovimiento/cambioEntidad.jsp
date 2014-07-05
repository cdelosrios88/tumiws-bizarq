<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	 <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton disabled="#{!cuentaCteController.habilitarFormCambioEntidad}"
		                    value="Grabar" 
		                    styleClass="btnEstilos" 
		                    actionListener="#{cuentaCteController.grabarCambioEntidad}" 
		                   oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpCambioEntidad')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,formCambioEntidad">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpCambioEntidad')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormCambioEntidad" layout="block" > 
   <rich:panel style=";border:0px">
            <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
    </rich:panel>
	     <rich:panel style="width: 950px;border:1px solid #880f;background-color:#DEEBF5;">
		          <h:panelGrid  columns="4">
		             <rich:column style="width:80px; border: none">
		         		<h:outputText  value="Fecha :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:160px; border: none">
		         	      <h:inputText value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.dtFechaDocumento}">
		         	          <f:convertDateTime pattern="dd/MM/yyyy"/>
		         	      </h:inputText>
		             </rich:column>
		             <rich:column style="width:180px; border: none">
		         		<h:outputText  value="Motivo de Cambio de Entidad :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:200px; border: none">
		         	  		<h:selectOneMenu  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" style="width:200px"
		         	  		               disabled="#{!cuentaCteController.habilitarFormCambioEntidad}">
			         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoCambioEntidad}" 
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
		  <rich:panel id="entidadOrigen" style="width: 950px;border:1px solid #880f;background-color:#DEEBF5;" > 
			   <h:panelGrid  columns="2" style="width:800px; border: none">
			      <rich:column style="width:400px; border: 1">
			         		<h:outputText  value="Entidad Origen:"> </h:outputText>
			      </rich:column>
			      <rich:column>
			         		<h:outputText  value="Nueva Entidad Origen:"> </h:outputText>
			      </rich:column>
			      
			   </h:panelGrid>     
			   
			    <h:panelGrid  columns="2" style="width:800px; border: none">
			      <rich:column  style="width:400px; border: 1">
			         		<h:outputText style = "color:RED;"   value="#{cuentaCteController.messagePlanillaOrigen}"> </h:outputText>
			      </rich:column>
			      <rich:column>
			         		<h:outputText  style = "color:RED;"  value="#{cuentaCteController.messagePlanillaOrigenNueva}"> </h:outputText>
			      </rich:column>
			   </h:panelGrid>     
			   <h:panelGrid   columns="3" style="width:860px; border: none">
				       <rich:column style="width:400px; border: 1">
				         <h:outputText  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigen.strEntidad} "/>
				         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigen.intTipoSocio}"/>-
				         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigen.intModalidad}"/>
             
				       </rich:column>
				      <rich:column rendered="#{not empty cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigenNueva}">
		           		 <h:outputText  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigenNueva.strEntidad} "> </h:outputText>
		           		 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigenNueva.intTipoSocio}"/>-
				         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigenNueva.intModalidad}"/>
									    Cód.Planilla: <h:inputText value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraOrigenNueva.strCodigoPlanilla}"></h:inputText>
				      </rich:column>
				      <rich:column>
				      	 <a4j:commandButton value="Cambiar" 
						    		        styleClass="btnEstilos"
						    		        actionListener="#{cuentaCteController.seleccionarTipoEstructura}"
						    		        oncomplete="#{rich:component('mpSocioEstructura')}.show()"
						    		        rendered="#{cuentaCteController.habilitarFormCambioEntidad}">
						    		        
						   <f:attribute  name="tipoEstructura" value="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN}"/>
						 </a4j:commandButton>    		        
				      </rich:column>
				</h:panelGrid> 
			</rich:panel>   
			<rich:panel id="entidadPrestamo" style="width: 950px;border:1px solid #880f;background-color:#DEEBF5;"> 
			   <h:panelGrid  columns="2" style="width:800px; border: none">
			      <rich:column style="width:400px; border: 1">
			         		<h:outputText  value="Entidad Prestamo:"> </h:outputText>
			      </rich:column>
			      <rich:column>
			         		<h:outputText  value="Nueva Entidad Prestamo:"> </h:outputText>
			      </rich:column>
			      
			   </h:panelGrid>     
			   
			    <h:panelGrid  columns="2" style="width:800px; border: none">
			      <rich:column  style="width:400px; border: 1">
			         		<h:outputText style = "color:RED;"   value="#{cuentaCteController.messagePlanillaPrestamo}"> </h:outputText>
			      </rich:column>
			      <rich:column>
			       		    <h:outputText  style = "color:RED;"  value="#{cuentaCteController.messagePlanillaPrestamoNueva}"> </h:outputText>
			      </rich:column>
			   </h:panelGrid>     
			     <h:panelGrid   columns="3" style="width:860px; border: none" rendered="#{not empty cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamo}">
				      <rich:column style="width:400px; border: 1">
				         <h:outputText  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamo.strEntidad} "> </h:outputText>
				           <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamo.intTipoSocio}"/>-
				         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamo.intModalidad}"/>
             
				      </rich:column>
				      <rich:column rendered="#{not empty cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamoNueva}">
		           		 <h:outputText  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamoNueva.strEntidad} "> </h:outputText>
		           		   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamoNueva.intTipoSocio}"/>-
				         <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamoNueva.intModalidad} "/>
                                        Cód.Planilla: <h:inputText value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.socioEstructuraPrestamoNueva.strCodigoPlanilla}"></h:inputText>
				      </rich:column>
				      <rich:column>
				      	 <a4j:commandButton value="Cambiar" 
						    		        styleClass="btnEstilos"
						    		        actionListener="#{cuentaCteController.seleccionarTipoEstructura}"
						    		        oncomplete="#{rich:component('mpSocioEstructura')}.show()"
						    		        rendered="#{cuentaCteController.habilitarFormCambioEntidad}">
						    		        
						   <f:attribute  name="tipoEstructura" value="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO}"/>
						 </a4j:commandButton>    		        
				      </rich:column>
				 </h:panelGrid> 
			</rich:panel>  
			
			<h:panelGroup layout="block" style="margin-top:20px">
	     		<h:panelGrid>
	     			<h:outputText value="DATOS LABORALES" style="font-weight: bold; text-decoration: underline"></h:outputText>
	     		</h:panelGrid>
	     		
	     		<h:panelGroup layout="block" style="float:left">
	     			<h:panelGrid columns="4" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Centro de Trabajo"></h:outputText>
		     				</rich:column>
		     				<rich:column colspan="3">
		     					<h:inputText size="48" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.strCentroTrabajo}"></h:inputText>
		     				</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Fecha Inicio Servicio"></h:outputText>
		     				</rich:column>
		     				<rich:column>
		     					<rich:calendar value="#{cuentaCteController.socioCom.persona.natural.perLaboral.dtInicioServicio}" datePattern="dd/MM/yyyy" inputStyle="width:70px"></rich:calendar>
		     				</rich:column>
		     				<rich:column>
		     					<h:outputText value="Remuneración"></h:outputText>
		     				</rich:column>
		     				<rich:column>
		     					<h:inputText size="9" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.bdRemuneracion}"></h:inputText>
		     				</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Condición Laboral"></h:outputText>
		     				</rich:column>
		     				<rich:column>
		     					<h:selectOneMenu value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboral}"
		     						valueChangeListener="#{cuentaCteController.loadListDetCondicionLaboral}">
		     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		     						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								  	<a4j:support event="onchange" reRender="cboDetCondicionLaboral,divContrato" ajaxSingle="true"></a4j:support>
		     					</h:selectOneMenu>
		     				</rich:column>
		     				<rich:column colspan="2">
		     					<h:selectOneMenu id="cboDetCondicionLaboral" style="width:150px"
		     						value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCondicionLaboralDet}">
		     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		     						<tumih:selectItems var="sel" value="#{cuentaCteController.listDetCondicionLaboral}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		     					</h:selectOneMenu>
		     				</rich:column>
	     				</rich:columnGroup>
	     				
	     			</h:panelGrid>
	     		</h:panelGroup>
	     		
	     		<h:panelGroup id="divContrato" layout="block" style="float:left">
	     			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
	     						<h:outputText value="Cargo"></h:outputText>
	     					</rich:column>
	     					<rich:column>
	     						<h:selectOneMenu value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intCargo}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CARGOPERSONAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column colspan="2">
	     						<h:outputText value="Régimen Laboral"></h:outputText>
	     						<h:selectOneMenu style="width:100px; margin-left:10px" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.intRegimenLaboral}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_REGIMENLABORAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     					<rich:column colspan="4">
	     						<h:outputText value="Solicitud ONP"></h:outputText>
	     						<h:inputText style="margin-left:10px" size="10" value="#{cuentaCteController.socioCom.persona.natural.perLaboral.strSolicitudONP}"></h:inputText>
	     					</rich:column>
	     				</rich:columnGroup>
	     			</h:panelGrid>
	     			
	     			
	     		</h:panelGroup>
	     		
	         </h:panelGroup>
	          		     
	 </h:panelGroup>
	 
	 