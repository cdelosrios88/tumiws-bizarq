<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Modif.    : Gutenberg Torres Brousset              -->
<!-- Fecha Mod.: 2014.04.16                             -->
<!-- Campos bloqueados para la opción Ver.				-->
<!-- La Fecha Final siempre se muestra.					-->

    <h:panelGrid id="idBotones"  columns="4">
		<rich:column visible="#{!cuentaCteController.btnAtenderDisabled}" >
			<a4j:commandButton value="Grabar" 
					actionListener="#{cuentaCteController.grabarCambioCondicion}"  
					styleClass="btnEstilos" 
					oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mdpCambioCondicion')}" 
					reRender="dataListSolCtaTipo,btnGrabar,divFormCambioCondicion">
		 </a4j:commandButton>
    	</rich:column>
    	<rich:column>
			<a4j:commandButton value="Cancelar" 
		 			styleClass="btnEstilos" 
		 			oncomplete="Richfaces.hideModalPanel('mdpCambioCondicion')"/>
		</rich:column>
	</h:panelGrid>
	
   <h:panelGroup id="divFormCambioCondicion" layout="block" > 
	    <rich:panel style=";border:0px" id ="idColMensaje">
	            <h:outputText  style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
	    </rich:panel>
         
	     <rich:panel style="width: 840px;border:1px solid #880f;background-color:#DEEBF5;">
	         <h:panelGrid  columns="6">
	             <rich:column style="width:140px; border: none">
	         		<h:outputText  value="Motivo de Cambio Condición:"> </h:outputText>
	         	 </rich:column>
	         	 <rich:column style="width:100px; border: none">
	         	  		<h:selectOneMenu value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" 
								style="width:140px" disabled="#{cuentaCteController.btnAtenderDisabled}" >
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoCambioCondicion}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		         </rich:column>
		         <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Fecha Inicio: "> </h:outputText>
	         	 </rich:column>
	         	  <rich:column style="width:200px; border: none">
	         	     <rich:calendar  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.movimiento.dtFechaInicio}" 
	         	     		datePattern="dd/MM/yyyy" disabled="#{cuentaCteController.btnAtenderDisabled}"  
	         	     		style="width:76px" />
	         	  </rich:column>
	         	  <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Fecha Fin: "> </h:outputText>
	         	 </rich:column>
	         	  <rich:column id="idColFechaFinal" style="width:200px; border: none">
	         		 <rich:calendar  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.movimiento.dtFechaFin}" 
	         		 		datePattern="dd/MM/yyyy" disabled="#{cuentaCteController.btnAtenderDisabled}"  
	         		 		style="width:76px" />
	         	 </rich:column>
	        </h:panelGrid>  
	        
	        <h:panelGrid  columns="5" >
	           <rich:column style="width:140px; border: none">
	         		<h:outputText  value="Condicion Inicial: "> </h:outputText>
	           </rich:column>
	           <rich:column style="width:100px; border: none">
	         		 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{cuentaCteController.socioCom.cuenta.intParaCondicionCuentaCod}"/>
	           </rich:column>
	           <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Condicion Final: "> </h:outputText>
	           </rich:column>
	           	<rich:column  style="width:100px; border: none">
	         	  		<h:selectOneMenu value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intParaCondicionCuentaFinal}" 
	         	  		                 style="width:140px" disabled="#{cuentaCteController.btnAtenderDisabled}" 
	         	  		                 valueChangeListener="#{cuentaCteController.limpiarConceptosCta}"
	         	  		                 >
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" value="#{cuentaCteController.listaCondicionFinal}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>	
						</h:selectOneMenu>
		        </rich:column>
				<a4j:commandButton value="Cambiar" 
						disabled="#{cuentaCteController.btnAtenderDisabled}" 
						actionListener="#{cuentaCteController.mostrarConceptosAbloquear}" 
						styleClass="btnEstilos" 
						reRender="idConceptos,idColMensaje,divFormCambioCondicion"/>
	        </h:panelGrid>
	        
	        <h:panelGrid columns="2">
	        	<rich:column style="width:140px; border: none">
	        		<h:outputText value="Observaciones: " />
	        	</rich:column>
	        	<rich:column>
	        		<h:inputTextarea value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.strScctObservacion}" cols="40" rows="8" />
	        	</rich:column>
	        </h:panelGrid>
	        
	        <h:panelGrid id = "idConceptos"  columns="3">
	             <rich:column style="width:220px; border: none">
	         		<h:outputText  value="Concepto a considerar para el Bloqueo:"> </h:outputText>
	         	 </rich:column>
	         	  <rich:dataList value="#{cuentaCteController.beanSolCtaCte.cuenta.listaConcepto}" 
				                     		var="item" 
				                     		style="border-top: 1px; border-left: 1px;">
					   <h:panelGrid columns="2">
					      <h:selectBooleanCheckbox value="#{true}" disabled="true"/>
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.id.intItemCuentaConcepto}"/>
	                   </h:panelGrid>
				  </rich:dataList>
				  <rich:dataList value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaExpediente}" 
				                     		var="item" 
				                     		style="border-top: 1px; border-left: 1px;">
					   <h:panelGrid columns="3">
					      <h:selectBooleanCheckbox  value="#{true}" disabled="true"/>
					       
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.intParaTipoCreditoCod}"/>
			               <h:outputText value="#{item.strDescripcion}"></h:outputText>							      
										      
	                  </h:panelGrid>
				  </rich:dataList>
			</h:panelGrid> 	 
	     </rich:panel>    
	 </h:panelGroup>