<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Paul Rivera		    				-->
	<!-- Modulo    : Créditos                 				-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - APORTACIONES-->
	<!-- Fecha     : 06/02/2012               				-->
	<script language="JavaScript" src="/tumi/js/main.js"  type="text/javascript"></script>
	
	<script type="text/javascript">
		function jsSeleccionAportacion(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo){
			document.getElementById("frmAportacionModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
			document.getElementById("frmAportacionModalPanel:hiddenIdTipoCaptacion").value=itemIdTipoCaptacion;
			document.getElementById("frmAportacionModalPanel:hiddenIdCodigo").value=itemIdCodigo;
		}
	</script>
	
	<rich:modalPanel id="panelUpdateDeleteAportacion" width="400" height="155"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Aportación"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAportacion"/>
               <rich:componentControl for="panelUpdateDeleteAportacion" attachTo="hidelinkAportacion" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmAportacionModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar una Aportación?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{aportacionesController.modificarAportaciones}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{aportacionesController.eliminarAportacion}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"/>
				<h:inputHidden id="hiddenIdTipoCaptacion"/>
				<h:inputHidden id="hiddenIdCodigo"/>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
      <rich:tabPanel>
        <rich:tab label="Aportaciones">
         	<rich:panel id="pMarco1" style="border:1px solid #17356f ;width:930px; background-color:#DEEBF5">
         		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
         			<h:panelGrid id="pgBusq1" columns="7" style="width: 800px" border="0">
                        <rich:column width="150">
                        	<h:outputText id="lblbusqTipoConv" value="Estado de Aportación" styleClass="tamanioLetra"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{aportacionesController.intIdEstadoAportacion}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column>
              				<h:selectBooleanCheckbox value="#{aportacionesController.chkNombAporte}">
              					<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgBusq1"/>
              				</h:selectBooleanCheckbox>
              			</rich:column>
                        <rich:column>
              				<h:outputText value="Nombre del Aporte"/>
              			</rich:column>
                        <rich:column>
                        	<h:inputText value="#{aportacionesController.strNombreAporte}" size="60" disabled="#{aportacionesController.enabDisabNombAporte}"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectBooleanCheckbox />
                        </rich:column>
                        <rich:column><h:outputText value="Todos"/></rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgBusq2" columns="10" border="0">
                        <rich:column>
                        	<h:selectBooleanCheckbox value="#{aportacionesController.chkCondAporte}">
                        		<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgBusq2"/>
                        	</h:selectBooleanCheckbox>
                        </rich:column>
                        <rich:column>
                        	<h:outputText value="Condición"/>
                        </rich:column>
                        <rich:column>
                        	<h:selectOneMenu value="#{aportacionesController.intIdCondicionAportacion}" disabled="#{aportacionesController.enabDisabCondAporte}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:15px;">
                        	<h:outputText value="Tipo Configuración"/>
                        </rich:column>
                        <rich:column style="padding-left:15px;">
                        	<h:selectOneMenu value="#{aportacionesController.intIdTipoConfig}">
                        		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                        	</h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:15px;">
                        	<h:selectBooleanCheckbox value="#{aportacionesController.chkFechas}">
                        		<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgBusq2"/>
                        	</h:selectBooleanCheckbox> 
                        </rich:column>
                        <rich:column><h:outputText value="Fecha" /></rich:column>
                        <rich:column style="padding-left:15px;">
                        	<h:selectOneMenu disabled="#{aportacionesController.enabDisabFechasAport}">
                        		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                        		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>
                        	</h:selectOneMenu>
                        </rich:column>
                       	<rich:column style="border:none; padding-left:10px;">
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{aportacionesController.enabDisabFechasAport}"
		                   	value="#{aportacionesController.daFecIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                       	<rich:column style="border:none; padding-left:10px;">
		          			<rich:calendar popup="true" enableManualInput="true"
		          			disabled="#{aportacionesController.enabDisabFechasAport}"
		                   	value="#{aportacionesController.daFecIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
		          		</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="11">
                    	<rich:column>
                    		<h:outputText value="Tipo de Persona :"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu value="#{aportacionesController.intIdTipoPersona}">
                    			<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column style="padding-left:15px;">
                    		<h:selectBooleanCheckbox value="#{aportacionesController.chkTasaInt}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Tasa de Interés"/>
                    	</rich:column>
                    	<rich:column style="padding-left:15px;">
                    		<h:selectBooleanCheckbox value="#{aportacionesController.chkLimEdad}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Limite de Edad"/>
                    	</rich:column>
                    	<rich:column style="padding-left:15px;">
                    		<h:selectBooleanCheckbox value="#{aportacionesController.chkAportVigentes}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Aportación Vigentes"/>
                    	</rich:column>
                    	<rich:column style="padding-left:15px;">
                    		<h:selectBooleanCheckbox value="#{aportacionesController.chkAportCaduco}"/>
                    	</rich:column>
                    	<rich:column>
                    		<h:outputText value="Aportación Caduco"/>
                    	</rich:column>
                    	<rich:column style="padding-left:15px;">
                    		<a4j:commandButton value="Buscar" actionListener="#{aportacionesController.listarAportaciones}" styleClass="btnEstilos" reRender="pgListAportaciones"/>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid id="pgListAportaciones" border="0">
                    	<rich:extendedDataTable id="edtAportaciones" value="#{aportacionesController.beanListAportaciones}"
                    		onRowClick="jsSeleccionAportacion(#{item.intIdEmpresa}, #{item.intIdTipoCaptacion}, #{item.intIdCodigo});"
                    		rendered="#{not empty aportacionesController.beanListAportaciones}"  rows="#{aportacionesController.rows}"
	                       	enableContextMenu="false" var="item" rowKeyVar="rowKey" width="900px" height="170px">
                              <rich:column width="15">
                                  <div align="center"><h:outputText value="#{rowKey+1}"></h:outputText></div>
                              </rich:column>
                              <rich:column width="125">
                                  <f:facet name="header">
                                      <h:outputText value="Nombre de Aporte"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.strDescripcion}"></h:outputText>
                              </rich:column>
                              <rich:column>
                                  <f:facet name="header">
                                      <h:outputText value="Fec. de Inicio"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.daFecIni}"></h:outputText>
                              </rich:column>
                              <rich:column width="80px">
                                  <f:facet name="header">
                                      <h:outputText value="Fec. de Fin"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.daFecFin}"></h:outputText>
                              </rich:column>
                              <rich:column width="70px">
                                  <f:facet name="header">
                                      <h:outputText value="Estado"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.strEstSolicitud}"></h:outputText>
                              </rich:column>
                              <rich:column width="135px">
                                  <f:facet name="header">
                                      <h:outputText value="Condición de Socio"></h:outputText>
                                  </f:facet>
                                  <h:outputText id="strCondSoc" value="#{item.strCondicionSocio}">
                                  	<rich:toolTip for="strCondSoc" value="#{item.strCondicionSocio}" followMouse="true" 
		                                  		styleClass="tooltip" showDelay="500"/>
                                  </h:outputText>
                              </rich:column>
                              <rich:column width="150px">
                                  <f:facet name="header">
                                      <h:outputText value="Tipo de Configuración"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.strTipoConfig}"></h:outputText>
                              </rich:column>
                              <rich:column width="115px">
                                  <f:facet name="header">
                                      <h:outputText value="Modelo Contable"></h:outputText>
                                  </f:facet>
                                  <h:outputText value="xxxx"></h:outputText>
                              </rich:column>
                              <rich:column>
                                  <f:facet name="header">
                                      <h:outputText value="Fecha de Reg."></h:outputText>
                                  </f:facet>
                                  <h:outputText value="#{item.strFecRegistro}"></h:outputText>
                              </rich:column>
                              
                              <f:facet name="footer">
                                <rich:datascroller for="edtAportaciones" maxPages="10"/>
                              </f:facet>
		                </rich:extendedDataTable>
		            </h:panelGrid>
		            
                    <h:panelGrid columns="2">
						<h:outputLink value="#" id="linkAportacion">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteAportacion" attachTo="linkAportacion" operation="show" event="onclick"/>
						</h:outputLink>
						<h:outputText value="Para Eliminar, Modificar o Imprimir las APORTACIONES hacer click en el Registro" style="color:#8ca0bd"/>                                     
				    </h:panelGrid>
				 </rich:panel>
				 
	   	    	 <h:panelGrid columns="3">
	                 <a4j:commandButton value="Nuevo" actionListener="#{aportacionesController.habilitarGrabarAportaciones}" styleClass="btnEstilos" reRender="pAportaciones"/>                     
	                 <a4j:commandButton id="btnGuardarAportacion" value="Grabar" actionListener="#{aportacionesController.grabarAportaciones}" styleClass="btnEstilos" reRender="pAportaciones"/>												                 
	                 <a4j:commandButton value="Cancelar" actionListener="#{aportacionesController.cancelarGrabarAportaciones}" styleClass="btnEstilos" reRender="pAportaciones"/>
	          	 </h:panelGrid>
		         
	          	 <h:panelGrid id="pAportaciones">
	          	 
	          	 
	          	 
	          	 
	          	 <rich:panel rendered="#{aportacionesController.formAportacionesRendered}" style="width: 900px;border:1px solid #17356f;background-color:#DEEBF5;">
	          	 	
	          	 	
	          	 	<h:panelGrid columns="5">
	          	 		<rich:column width="140"><h:outputText value="Nombre del Aporte" /></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
		          	 		<h:inputText value="#{aportacionesController.beanAportacion.strDescripcion}" size="80"/>
	                    </rich:column>

	          	 	</h:panelGrid>
	          	 	<h:outputText value="#{aportacionesController.msgTxtDescripcion}" styleClass="msgError"/>
	          	 	
	          	 	
	          	 	
	          	 	
	          	 	
	          	 	
	          	 	
	          	 	<h:panelGrid id="pgRanFec" columns="5">
	          	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<rich:calendar enableManualInput="true"
		                   	value="#{aportacionesController.daFechaIni}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
	          	 		</rich:column>
	          	 		<rich:column>
		          	 		<h:selectOneRadio value="#{aportacionesController.rbFecIndeterm}">
		          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
		          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
		          	 			<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgRanFec"/>
		          	 		</h:selectOneRadio>
	                    </rich:column>
	                    <rich:column>
	          	 			<rich:calendar enableManualInput="true"
	          	 			rendered="#{aportacionesController.fecFinAportacionRendered}"
		                   	value="#{aportacionesController.daFechaFin}"
		                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
		                    cellWidth="10px" cellHeight="20px"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	<h:outputText value="#{aportacionesController.msgTxtFechaIni}" styleClass="msgError"/>
	          	 	
	          	 	<h:panelGrid columns="4">
	          	 		<rich:column width="140"><h:outputText value="Estado del Aporte"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdEstado}">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
	          	 			<%--
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdEstado}">
	          	 				<f:selectItems value="#{parametersController.cboEstados}"/>
	          	 			</h:selectOneMenu>
	          	 			--%>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtEstadoAporte}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="4">
	          	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdTipoPersona}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtTipoPersona}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="4">
	          	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneRadio value="#{aportacionesController.rbCondSocio}">
	          	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
	          	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.listarCondSocio}" reRender="dtCondSocio"/>
	          	 			</h:selectOneRadio>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtCondSocio}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	<h:panelGrid id="dtCondSocio">
          	 			<rich:dataTable value="#{aportacionesController.beanListCondSocio}" rows="10"
		         			rendered="#{not empty aportacionesController.beanListCondSocio}"
		                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
		                	<rich:column width="15px;">
				                <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
				            </rich:column>
				            <rich:column width="500">
				            	<h:outputText value="#{item.strDescripcion}"/>
				            </rich:column>
		                </rich:dataTable>
          	 		</h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="4">
	          	 		<rich:column width="140"><h:outputText value="Tipo de descuento"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdTipoDcto}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtTipoDscto}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="4">
	          	 		<rich:column width="140"><h:outputText value="Tipo de Configuración"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdTipoConfig}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtTipoConfig}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid columns="7">
	          	 		<rich:column width="140"><h:outputText value="Valor de Aportación"/></rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdMoneda}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneRadio value="#{aportacionesController.strValAporte}">
	          	 				<f:selectItem itemLabel="Importe" itemValue="1"/>
	          	 				<f:selectItem itemLabel="%" itemValue="2"/>
	          	 			</h:selectOneRadio>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:inputText value="#{aportacionesController.beanAportacion.flValorConfig}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdAplicacion}">
	          	 				<f:selectItem itemLabel="RMV" itemValue="1"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="#{aportacionesController.msgTxtMoneda}" styleClass="msgError"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid id="pgTasaInteres" columns="10">
	          	 		<rich:column>
	          	 			<h:selectBooleanCheckbox value="#{aportacionesController.chkTasaInteres}">
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgTasaInteres"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="130">
	          	 			<h:outputText value="Tasa de Interés"/>
	          	 		</rich:column>
	          	 		<rich:column><h:outputText value=":"/></rich:column>
	          	 		<rich:column width="130px">
	          	 			<h:selectOneRadio value="#{aportacionesController.rbTasaInteres}" disabled="#{aportacionesController.enabDisabTasaInt}">
	          	 				<f:selectItem itemLabel="% Interés" itemValue="1"/>
	          	 				<f:selectItem itemLabel="TEA" itemValue="2"/>
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgTasaInteres"/>
	          	 			</h:selectOneRadio>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:inputText value="#{aportacionesController.beanAportacion.flTem}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="5" disabled="#{aportacionesController.enabDisabPorcInt}"/>
	          	 		</rich:column>
	          	 		<rich:column width="80px">
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdTasaNaturaleza}" disabled="#{aportacionesController.enabDisabPorcInt}">
	          	 				%<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINTERES}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu value="#{aportacionesController.beanAportacion.intIdTasaFormula}" disabled="#{aportacionesController.enabDisabPorcInt}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CALCINTERES}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:selectOneMenu disabled="#{aportacionesController.enabDisabPorcInt}">
	          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	          	 			</h:selectOneMenu>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:inputText value="#{aportacionesController.beanAportacion.flTea}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{aportacionesController.enabDisabTea}"/>
	          	 		</rich:column>
	          	 		<rich:column width="110px;">
	          	 			<h:inputText value="#{aportacionesController.beanAportacion.flTna}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="8" disabled="#{aportacionesController.enabDisabTna}"/> TNA
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	<h:outputText value="#{aportacionesController.msgTxtTNA}" styleClass="msgError"/>
	          	 	
	          	 	<h:panelGrid id="pgLimiteEdad" columns="5">
	          	 		<rich:column>
	          	 			<h:selectBooleanCheckbox value="#{aportacionesController.chkLimiteEdad}">
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgLimiteEdad"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="115px">
	          	 			<h:outputText value="Limite de Edad"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value=":"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:inputText value="#{aportacionesController.beanAportacion.intEdadLimite}" onkeypress="return soloNumeros(event);" size="5" maxlength="2" disabled="#{aportacionesController.enabDisabLimiteEdad}"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value="años"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid id="pgModelosCont" columns="3">
	          	 		<rich:column width="140">
	          	 			<h:outputText value="Modelos Contables"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:outputText value=":"/>
	          	 		</rich:column>
	          	 		<rich:column>
	          	 			<h:commandButton value="Solicitar" styleClass="btnEstilos"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid id="pgProvision" columns="4">
	          	 		<rich:column width="150"></rich:column>
	          	 		<rich:column>
	          	 			<h:selectBooleanCheckbox value="#{aportacionesController.chkProvision}">
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgProvision"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="150">
	          	 			<h:outputText value="Provisión"/>
	          	 		</rich:column>
	          	 		<rich:column width="120">
	          	 			<h:commandButton value="Detalle" disabled="#{aportacionesController.enabDisabProvision}" styleClass="btnEstilos"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid id="pgExtProvision" columns="4">
	          	 		<rich:column width="150"></rich:column>
	          	 		<rich:column>
	          	 			<h:selectBooleanCheckbox value="#{aportacionesController.chkExtProvision}">
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgExtProvision"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="150">
	          	 			<h:outputText value="Extorno de Provisión"/>
	          	 		</rich:column>
	          	 		<rich:column width="120">
	          	 			<h:commandButton value="Detalle" disabled="#{aportacionesController.enabDisabExtProvision}" styleClass="btnEstilos"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 	
	          	 	<h:panelGrid id="pgCancelacion" columns="4">
	          	 		<rich:column width="150"></rich:column>
	          	 		<rich:column>
	          	 			<h:selectBooleanCheckbox value="#{aportacionesController.chkCancelacion}">
	          	 				<a4j:support event="onclick" actionListener="#{aportacionesController.enableDisableControls}" reRender="pgCancelacion"/>
	          	 			</h:selectBooleanCheckbox>
	          	 		</rich:column>
	          	 		<rich:column width="150">
	          	 			<h:outputText value="Cancelación"/>
	          	 		</rich:column>
	          	 		<rich:column width="120">
	          	 			<h:commandButton value="Detalle" disabled="#{aportacionesController.enabDisabCancelacion}" styleClass="btnEstilos"/>
	          	 		</rich:column>
	          	 	</h:panelGrid>
	          	 </rich:panel>
	          	 </h:panelGrid>
			</rich:panel>
    	</rich:tab>
    	
    	<rich:tab label="Fondo de Sepelio">
    		<a4j:include viewId="/pages/Creditos/fondoSepelioBody.jsp"/>
    	</rich:tab>
    	
      </rich:tabPanel>
   </h:form>