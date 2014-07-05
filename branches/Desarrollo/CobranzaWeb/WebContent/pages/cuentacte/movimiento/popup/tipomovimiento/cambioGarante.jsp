<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	
	
   
    <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton disabled="#{!cuentaCteController.habilitarFormCambioGarante}"
		                    value="Grabar" 
		                    styleClass="btnEstilos" 
		                    actionListener="#{cuentaCteController.grabarCambioGarante}" 
		                   oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpCambioGarante')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,formCambioGarante">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpCambioGarante')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormCambioGarante" layout="block" > 
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
		         		<h:outputText  value="Motivo de Cambio de Garante :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:200px; border: none">
		         	  		<h:selectOneMenu  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" style="width:200px"
		         	  		               disabled="#{!cuentaCteController.habilitarFormCambioGarante}">
			         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoCambioGarante}" 
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
	          <h:panelGrid  columns="2">
		      	 <rich:column style="width:80px; border: none">
	            	<h:outputText  value="Entidad :"  > </h:outputText>
	         	</rich:column> 
	         	<rich:column style="width:480px; border: none">
	         		<h:outputText  value="#{cuentaCteController.strDescEntidad}" > </h:outputText>
	         	</rich:column>
		     </h:panelGrid>
		     
		      <h:panelGroup id="divListCambioGarante" layout="block" style="padding:15px">
		        <rich:extendedDataTable     value="#{cuentaCteController.listarGarantiaCreditoComp}" 
					                  		var="item" 
					                  		rendered="#{not empty cuentaCteController.listarGarantiaCreditoComp}" 
					                   		width="920px" height="240px"
					                   		rowKeyVar="rowKey">
					                   		
					<rich:column width="80px">
	                   <f:facet name="header">
	                                        <h:outputText  id="lblTipo" value="Tipo" ></h:outputText>
	                    </f:facet>
	                     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{item.expediente.intParaTipoCreditoCod}"/>
				    </rich:column>   
	                <rich:column width="90px">
	                   <f:facet name="header">
	                                        <h:outputText  id="lblNroDoc" value="Nro. Doc." ></h:outputText>
	                    </f:facet>
	                   <h:outputText id="lblValNroDoc" value="#{item.garantiaCredito.id.intItemExpediente}-#{item.garantiaCredito.id.intItemDetExpediente}"></h:outputText>
	                </rich:column>   
	                <rich:column width="80px">
	                   <f:facet name="header">
	                              <h:outputText  id="lblSaldo" value="Saldo" ></h:outputText>
	                    </f:facet>
	                              <h:outputText id="lblValSaldo" value="#{item.expediente.bdMontoTotal}"></h:outputText>
	                </rich:column>   
	                <rich:column width="290px">
	                   <f:facet name="header">
	                              <h:outputText  id="lblGarante" value="Garante" ></h:outputText>
	                    </f:facet>
	                   <h:outputText rendered="#{not empty item.socioComp}" id="lblValGarante" value="#{item.socioComp.socio.id.intIdPersona} #{item.socioComp.socio.strNombreSoc} #{item.socioComp.socio.strApePatSoc} #{item.socioComp.socio.strApeMatSoc}"></h:outputText>-
	                   <h:outputText rendered="#{not empty item.socioComp}" value ="#{item.socioComp.cuenta.strNumeroCuenta}"></h:outputText>
	                   
	                   <tumih:outputText   cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{item.socioComp.cuenta.intParaSubCondicionCuentaCod}"/>
					</rich:column>   
	                <rich:column width="210px">
	                   <f:facet name="header">
	                              <h:outputText  id="lblGaranteNuevo" value="Nuevo Garante" ></h:outputText>
	                    </f:facet>
	                      <h:outputText id="lblValNuevoGarante" rendered="#{not empty item.garanteComp}" value="#{item.garanteComp.socio.id.intIdPersona} #{item.garanteComp.socio.strNombreSoc} #{item.garanteComp.socio.strApePatSoc} #{item.garanteComp.socio.strApeMatSoc}"></h:outputText>-
	                </rich:column>     
	                <rich:column width="160px">
	                   <f:facet name="header">
	                                        <h:outputText  id="lblOpciones" value="Opciones" ></h:outputText>
	                    </f:facet>
	                     <a4j:commandButton value="Eliminar" 
	                                        disabled="#{!cuentaCteController.habilitarFormCambioGarante}"
	                                         actionListener="#{cuentaCteController.eliminarGarante}"
	                                        styleClass="btnEstilos"
	                                        reRender="divListCambioGarante">
	                                         <f:attribute  name="nroItem" value="#{rowKey}"
	                                         />
	                     </a4j:commandButton>
	                     <a4j:commandButton value="Cambiar" 
	                                        disabled="#{!cuentaCteController.habilitarFormCambioGarante}"
	                                        styleClass="btnEstilos"
	                                        actionListener="#{cuentaCteController.irCambiarGarante}" 
	                                        oncomplete="Richfaces.showModalPanel('mpBusqGarante')">
	                        <f:attribute  name="nroItem" value="#{rowKey}"/>
	                     </a4j:commandButton>
	                </rich:column>                		
				</rich:extendedDataTable>
		  </h:panelGroup>
	    </rich:panel>    
	     
	 </h:panelGroup>
	 
	 