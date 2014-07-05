<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
    <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton rendered="#{cuentaCteController.habilitarFormLicencia}"
		                    value="Grabar" 
		                    actionListener="#{cuentaCteController.grabarLicencia}" 
		                    styleClass="btnEstilos" 
		                    oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpLicencia')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,divFormLicencia">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpLicencia')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormLicencia" layout="block" > 
	    <rich:panel style=";border:0px">
	            <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
	    </rich:panel>
         
	     <rich:panel style="width: 840px;border:1px solid #880f;background-color:#DEEBF5;">
	         <h:panelGrid  columns="6">
	             <rich:column style="width:140px; border: none">
	         		<h:outputText  value="Motivo de Licencia :"> </h:outputText>
	         	 </rich:column>
	         	 <rich:column style="width:100px; border: none">
	         	  		<h:selectOneMenu value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" 
	         	  		                 style="width:140px"
	         	  		                 disabled="#{!cuentaCteController.habilitarFormLicencia}">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoLicencia}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
		         </rich:column>
		         <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Fecha Incio:"> </h:outputText>
	         	 </rich:column>
	         	  <rich:column style="width:200px; border: none">
	         	     <rich:calendar disabled="#{!cuentaCteController.habilitarFormLicencia}" value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.movimiento.dtFechaInicio}" datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
	         	  </rich:column>
	         	  <rich:column style="width:100px; border: none">
	         		<h:outputText  value="Fecha Fin:"> </h:outputText>
	         	 </rich:column>
	         	  <rich:column style="width:200px; border: none">
	         		 <rich:calendar disabled="#{!cuentaCteController.habilitarFormLicencia}" value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.movimiento.dtFechaFin}" datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
	         	 </rich:column>
	        </h:panelGrid>  
	        <h:panelGrid  columns="3">
	             <rich:column style="width:220px; border: none">
	         		<h:outputText  value="Concepto a considerar para el Bloqueo :"> </h:outputText>
	         	 </rich:column>
	         	  <rich:dataList value="#{cuentaCteController.beanSolCtaCte.cuenta.listaConcepto}" 
				                     		var="item" 
				                     		style="border-top: 1px; border-left: 1px;">
					   <h:panelGrid columns="2">
					      <h:selectBooleanCheckbox  value="#{item.checked}" disabled="#{!cuentaCteController.habilitarFormLicencia}"/>
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.id.intItemCuentaConcepto}"/>
										      
	                  </h:panelGrid>
				  </rich:dataList>
				  <rich:dataList value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaCtaCptoExpCredito}" 
				                     		var="item" 
				                     		style="border-top: 1px; border-left: 1px;">
					   <h:panelGrid columns="2">
					      <h:selectBooleanCheckbox  value="#{item.checked}" disabled="#{!cuentaCteController.habilitarFormLicencia}"/>
	                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.id.intItemCuentaConcepto}"/>
										      
	                  </h:panelGrid>
				  </rich:dataList>
			</h:panelGrid> 	 
	     </rich:panel>    
	 </h:panelGroup>