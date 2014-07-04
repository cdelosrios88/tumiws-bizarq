<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SELECCIONAR CUENTA MODELO     -->
	<!-- Fecha     : 17/05/2012               		-->
   
<script type="text/javascript">	  
	  
      function soloNumeros(tecla)
      {   
         // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57   
         var keys = nav4 ? tecla.which : tecla.keyCode;   
         return ((keys <= 13 || (keys >= 48 && keys <= 57));
      }
      
         
</script>
<rich:modalPanel id="mpModeloDetalleNivel" width="700" height="520"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Configurar Cuenta"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideModeloDetalleNivel" />
			<rich:componentControl for="mpModeloDetalleNivel" attachTo="hideModeloDetalleNivel"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmModeloDetalleNivel">
	   	<h:panelGroup id="divModeloDetalleNivel" layout="block" style="padding-left:5px; padding-right:5px">
	   		<h:panelGrid styleClass="tableCellBorder4" rendered="#{modeloController.isEditableModeloDetNivel}">
	   			<rich:columnGroup>
	   				<rich:column style="padding-left:-5px" rendered="#{!modeloController.isUpdatingModeloDetalle}" >
	   					<a4j:commandButton value="Agregar Cuenta" actionListener="#{modeloController.addModeloDetalle}" 
	   					                reRender="divModeloDetalle,divTblModeloDetalle,divModeloDetalleNivel,divFormModeloDetalleNivel" styleClass="btnEstilos1"
	   									oncomplete="if(#{modeloController.isValidModeloDetalle == true}){Richfaces.hideModalPanel('mpModeloDetalleNivel')}	   									            "></a4j:commandButton>
	   				</rich:column>
	   				<rich:column rendered="#{!modeloController.isUpdatingModeloDetalle}">
	   					<a4j:commandButton value="Buscar otra cuenta" 
	   									oncomplete="Richfaces.hideModalPanel('mpModeloDetalleNivel'),Richfaces.showModalPanel('mpModeloDetalle')"
	   									reRender="divModeloDetalle" styleClass="btnEstilos1"></a4j:commandButton>
	   				</rich:column>
	   				<rich:column rendered="#{modeloController.isUpdatingModeloDetalle}">
	   					<a4j:commandButton value="Guardar" styleClass="btnEstilos" actionListener="#{modeloController.updateModeloDetalle}"
	   									oncomplete="Richfaces.hideModalPanel('mpModeloDetalleNivel'),Richfaces.showModalPanel('mpModeloDetalle')"
	   									reRender="divModeloDetalle"></a4j:commandButton>
	   				</rich:column>
	   			</rich:columnGroup>
	   		</h:panelGrid>
	    	
	    	<rich:panel style="border:1px solid #17356f">
	    		<f:facet name="header">
                	<h:outputText value="Cuenta: #{modeloController.modeloDetalleSelected.id.strContNumeroCuenta} - #{modeloController.modeloDetalleSelected.planCuenta.strDescripcion}" style="font-size:12px"></h:outputText>
                </f:facet>
                <h:panelGrid columns="6" styleClass="tableCellBorder4">
                        <rich:column>
	    					<h:outputText value="#{modeloController.msgNroCuentaError}" styleClass="msgError"></h:outputText>
	    				</rich:column>
                </h:panelGrid>
                <h:panelGrid columns="6" styleClass="tableCellBorder4">
	    			<rich:columnGroup>
	    			   <rich:column>
	    					<h:outputText value="Tipo de Movimientos"></h:outputText>
	    				</rich:column>
	    				<rich:column>
	    					<h:selectOneMenu value="#{modeloController.modeloDetalleSelected.intParaOpcionDebeHaber}" 
	    						onchange="selecTipoMovimiento(#{applicationScope.Constante.ONCHANGE_VALUE})"
	    						disabled="#{!modeloController.isEditableModeloDetNivel}">
		    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_OPCIONDEBEHABER}" 
									  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		    				</h:selectOneMenu>
	    				</rich:column>
	    				<rich:column>
	    				   <h:outputText value="#{modeloController.msgTipoMovimientoDetError}" styleClass="msgError"></h:outputText>
	    				</rich:column>
	    					
	    			</rich:columnGroup>
	    		</h:panelGrid>
	    		
	    		
	    		<rich:separator height="2px" width="99%" style="margin:5px 0px"></rich:separator>
	    		<h:panelGrid id = "divMsgDetalleNivelDetError" columns="6" styleClass="tableCellBorder4">
	    		    <rich:columnGroup>
	    		       <rich:column>
	    			      <h:outputText value="#{modeloController.msgDetalleNivelDetError}" styleClass="msgError"></h:outputText>
	    			   </rich:column> 
	    			</rich:columnGroup>	
	    		</h:panelGrid>
	    		
	    		<h:panelGrid id="divFormModeloDetalleNivel" columns="6" styleClass="tableCellBorder4"
	    					rendered="#{modeloController.isEditableModeloDetNivel}">
	    				
	    			<rich:columnGroup>
	    				<rich:column>
	    					<h:outputText value="Descripción"></h:outputText>
	    				</rich:column>
	    				<rich:column id="txtDescripcionNivel" colspan="5">
	    					<h:inputText id="idDescripcion" value="#{modeloController.beanModeloDetalleNivel.strDescripcion}" size="30" required="false">
	    					</h:inputText>
	    					<h:outputText value="#{modeloController.msgDescripcionDetalleaError}" styleClass="msgError"></h:outputText>
	    					<h:outputText value="#{modeloController.msgParaTipoRegistroError}" styleClass="msgError"></h:outputText>
	    					<h:outputText value="#{modeloController.msgDatoTablasError}" styleClass="msgError"></h:outputText>
	    					<h:outputText value="#{modeloController.msgDatoArgumento}" styleClass="msgError"></h:outputText>
	    					<h:outputText value="#{modeloController.msgIntValor}" styleClass="msgError"></h:outputText>
	       			</rich:column>
	    			</rich:columnGroup>
	    			<rich:columnGroup>
	    				<rich:column>
	    					<h:outputText value="Tipo de Registro"></h:outputText>
	    				</rich:column>
	    				<rich:column id="cboTipoRegistroNivel" rowspan="2">
	    					<h:selectOneRadio value="#{modeloController.beanModeloDetalleNivel.intParaTipoRegistro}"
	    									layout="pageDirection" style="height:47px" onclick="selecTipoRegistroNivel(this.value)">
	    						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOREGISTROMODELO}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    					</h:selectOneRadio>
	    				</rich:column>
	    				<rich:column>
	    					<h:outputText value="Codigo de la tabla"></h:outputText>
	    				</rich:column>
	    				<rich:column>
	    					<h:inputText  id="txtDatoTablas" value="#{modeloController.beanModeloDetalleNivel.intDatoTablas}"
	    								disabled="#{modeloController.isDisabledTipoRegistro==null || modeloController.isDisabledTipoRegistro}" 
	    								onkeypress="return soloNumeros(event)"
	    								maxlength="3">
	    					 </h:inputText>
	    				</rich:column>
	    				<rich:column>
	    					<h:outputText value="Argumento"></h:outputText>
	    				</rich:column>
	    				<rich:column>
	    					<h:inputText id="txtDatoArgumento" value="#{modeloController.beanModeloDetalleNivel.intDatoArgumento}"
	    								disabled="#{modeloController.isDisabledTipoRegistro==null || modeloController.isDisabledTipoRegistro}"
	    								onkeypress="return soloNumeros(event)"
	    								maxlength="6"></h:inputText>
	    				</rich:column>
	    				
	    				<rich:column breakBefore="true"></rich:column>
	    				<rich:column>
	    					<h:outputText value="Valor"></h:outputText>
	    				</rich:column>
	    				<rich:column>
	    					<h:inputText id="txtValor" value="#{modeloController.beanModeloDetalleNivel.intValor}"
	    								disabled="#{modeloController.isDisabledTipoRegistro==null || !modeloController.isDisabledTipoRegistro}"
	    								onkeypress="return soloNumeros(event)"
	    								maxlength="5"></h:inputText>
	    				</rich:column>
	    				<rich:column>
	    					<a4j:commandButton value="Agregar"  actionListener="#{modeloController.addModeloDetalleNivel}" 
	    									reRender="divFormModeloDetalleNivel,divTblDetalleNivel" styleClass="btnEstilos"></a4j:commandButton>
	    				</rich:column>
	    			</rich:columnGroup>
	    		</h:panelGrid>
	    		
	    		<h:panelGroup id="divTblDetalleNivel" layout="block" style="margin-top:13px">
		    		<rich:extendedDataTable id="tblDetalleNivel" enableContextMenu="false" style="margin:0 auto"
		                        var="item" value="#{modeloController.modeloDetalleSelected.listModeloDetalleNivel}"
		                        rendered="#{not empty modeloController.modeloDetalleSelected.listModeloDetalleNivel}" 
		                        onRowClick="selecModeloDetNivel('#{rowKey}')"
				  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="631px" height="205px">
				                           
						 	<rich:column width="31" filterExpression="#{item.isDeleted==null || !item.isDeleted}">
				            	<h:outputText value="#{rowKey + 1}"></h:outputText>
				            </rich:column>
				                    
				            <rich:column width="150">
		                        <f:facet name="header">
		                            <h:outputText value="Descripción"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.strDescripcion}"></h:outputText>
				            </rich:column> 
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Tipo Registro"></h:outputText>
		                        </f:facet>
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOREGISTROMODELO}" 
												itemValue="intIdDetalle" itemLabel="strDescripcion" 
												property="#{item.intParaTipoRegistro}"/>
				            </rich:column>
				            <rich:column width="150">
		                        <f:facet name="header">
		                            <h:outputText value="Tabla" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.intDatoTablas}"></h:outputText>
				            </rich:column>
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Argumento" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.intDatoArgumento}"></h:outputText>
				            </rich:column>
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Valor" style="font-weight:bold"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.intValor}"></h:outputText>
				            </rich:column>
				                    
					   <f:facet name="footer">
					   		 <h:panelGroup layout="block">
						   		 <rich:datascroller for="tblDetalleNivel" maxPages="10"/>
						   		 <h:panelGroup layout="block" style="margin:0 auto; width:315px">
						   		 	<h:outputText value="Para " style="color:#38749C"></h:outputText>
									<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpConfirmMessage')"
													actionListener="#{modeloController.onDeleteModeloDetNivel}" 
													reRender="mpConfirmMessage" disabled="#{!modeloController.isEditableModeloDetNivel}">
										<h:graphicImage value="/images/icons/icon_trash.gif" style="border:0px"/>
										<h:outputText value="Eliminar"></h:outputText>
									</a4j:commandLink>
									<h:outputText value=" un Detalle hacer click en el Registro" style="color:#38749C"/>
						   		 </h:panelGroup>
					   		 </h:panelGroup>   
				       </f:facet>
		            </rich:extendedDataTable>
		    	</h:panelGroup>
	    	</rich:panel>
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>