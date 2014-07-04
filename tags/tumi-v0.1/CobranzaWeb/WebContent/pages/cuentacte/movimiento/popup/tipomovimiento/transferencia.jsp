<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>





	 <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton disabled="#{!cuentaCteController.habilitarFormTransferencia}"
		                    value="Grabar" 
		                    styleClass="btnEstilos" 
		                    actionListener="#{cuentaCteController.grabarTransferencia}" 
		                    oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpTransferencia')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,formTransferencia">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpTransferencia')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormTransferencia" layout="block" > 
   <rich:panel id="idMensaje" style=";border:0px">
            <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
    </rich:panel>
	     <rich:panel style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;">
	     
	             <h:panelGrid  columns="1" id="idRadio">
		             <rich:column style="width:540px; border: none">
		         		<h:selectOneRadio value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.radioOpcionTransferencias}">
		         	      <f:selectItem id="si1d" itemLabel="Entre Conceptos de la Cuenta del Socio." itemValue="1" itemDisabled="#{cuentaCteController.radioEntreConceptos}"/>
					      <f:selectItem   id="si1" itemLabel="Entre 02 cuentas distintas." itemValue="0"  itemDisabled="#{cuentaCteController.radioEntreConceptos}"/>
					    </h:selectOneRadio>
		         	 </rich:column>
		         </h:panelGrid>
	             <h:panelGrid  columns="4">
		             <rich:column style="width:80px; border: none">
		         		<h:outputText  value="Fecha :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:160px; border: none">
		         	      <rich:calendar    value       = "#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.dtFechaDocumento}" 
		         	                        datePattern = "dd/MM/yyyy" 
		         	                        style       = "width:76px"
		         	                        disabled    = "#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud != applicationScope.Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA}">
		         	         <a4j:support   event="onchanged" 
							 			    action="#{cuentaCteController.validarCasoLicencia}"
								            reRender="divFormTransferencia" 
											focus="calendar" />          
		         	      </rich:calendar>
		             </rich:column>
		             <rich:column style="width:150px; border: none">
		         		<h:outputText  value="Tipo de Transferencia :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:200px; border: none">
		         	  		<h:selectOneMenu  value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud}" style="width:200px"
		         	  		               disabled="#{!cuentaCteController.habilitarFormTransferencia}"
		         	  		               valueChangeListener="#{cuentaCteController.seleccionarTipoTransferencia}">
			         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoTransferencia}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							  	<a4j:support event="onchange" reRender="divFormTransferencia,idMensaje,idRadio" ajaxSingle="true"></a4j:support>
			     			</h:selectOneMenu>
			         </rich:column>
		         </h:panelGrid> 
		         <h:panelGrid  columns="2" rendered="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud == applicationScope.Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA}">
		             <rich:column style="width:150px; border: none">
		         		<h:outputText  value="Periodo de Transferencia :"> </h:outputText>
		         	 </rich:column>
		         	 <rich:column style="width:150px; border: none">
		         		<h:outputText  value="#{cuentaCteController.strPeriodoTrans}"> </h:outputText>
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
		        		  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioCom.cuenta.intParaCondicionCuentaCod}"/>
		         			          - 
		         	      <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioCom.cuenta.intParaSubCondicionCuentaCod}"/>
							          
		         	     DNI:<h:outputText value="#{cuentaCteController.socioCom.persona.documento.strNumeroIdentidad}"></h:outputText>
					     Cta:<h:outputText value="#{cuentaCteController.socioCom.cuenta.strNumeroCuenta}"></h:outputText>
		         	 </rich:column>
		      </h:panelGrid>
		  </rich:panel>
		  <rich:panel id="entidadOrigen" style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;" > 
			   <h:panelGrid  columns="4">
			      <rich:column style="width:100px; border: 1">
			         <h:outputText  value="Entidad Origen:"> </h:outputText>
			      </rich:column>
			      <rich:column style="width:300px; border: none">
         			 <h:outputText  value="#{cuentaCteController.strDescEntidad}" > </h:outputText>
		          </rich:column>
		          <rich:column style="width:80px; border: none">
		          	 <h:outputText  value="#{cuentaCteController.strDesSucursal}" > </h:outputText>
		          </rich:column>
		          <rich:column style="width:100px; border: none">
		          	 <h:outputText  value="#{cuentaCteController.strDesModaliadTipoSocio}" ></h:outputText>
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
			</rich:panel>   
			<rich:panel id="entidadPrestamo" style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{not empty cuentaCteController.strDescEntidadPres}"> 
			   <h:panelGrid  columns="2" style="width:800px; border: none">
			     <h:panelGrid  columns="4">
				      <rich:column style="width:100px; border: 1">
				         <h:outputText  value="Entidad Prestamo:"> </h:outputText>
				      </rich:column>
				      <rich:column style="width:300px; border: none">
	         			 <h:outputText  value="#{cuentaCteController.strDescEntidadPres}" > </h:outputText>
			          </rich:column>
			          <rich:column style="width:80px; border: none">
			          	 <h:outputText  value="#{cuentaCteController.strDesSucursalPres}" > </h:outputText>
			          </rich:column>
			          <rich:column style="width:100px; border: none">
			          	 <h:outputText  value="#{cuentaCteController.strDesModaliadTipoSocioPres}" ></h:outputText>
			          </rich:column>
			   </h:panelGrid>     
			   </h:panelGrid>     
			   
			    
			 </rich:panel>  
			 
			 <!--Datos del Garante-->
			<rich:panel id="datoGarante" style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.radioOpcionTransferencias == 0}"> 
			   <h:panelGrid  columns="3">
			      <rich:column style="width:100px; border: 1">
			         <h:outputText  value="Socio/Cliente:"> </h:outputText>
			      </rich:column>
			      <rich:column style="width:600px; border: none" rendered="#{not empty cuentaCteController.socioComGarante}">
		         		  <h:outputText value="#{cuentaCteController.socioComGarante.socio.id.intIdPersona} - #{cuentaCteController.socioComGarante.socio.strNombreSoc} #{cuentaCteController.socioComGarante.socio.strApePatSoc} #{cuentaCteController.socioComGarante.socio.strApeMatSoc} "/>
		        		  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioComGarante.cuenta.intParaCondicionCuentaCod}"/>
		         			          - 
		         	      <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{cuentaCteController.socioComGarante.cuenta.intParaSubCondicionCuentaCod}"/>
							          
		         	     DNI:<h:outputText value="#{cuentaCteController.socioComGarante.persona.documento.strNumeroIdentidad}"></h:outputText>
					     Cta:<h:outputText value="#{cuentaCteController.socioComGarante.cuenta.strNumeroCuenta}"></h:outputText>
		          </rich:column>
		          <rich:column style="width:80px; border: none">
		          	<a4j:commandButton value="Buscar" 
	                                        styleClass="btnEstilos"
	                                        actionListener="#{cuentaCteController.listarSociosGarantes}" 
	                                        reRender="mpBusqGaranteForm"
	                                        oncomplete="Richfaces.showModalPanel('mpBusqGarante')">
	                  </a4j:commandButton> 
		          </rich:column>
		       </h:panelGrid>     
			    <h:panelGrid  columns="4" style="width:600px; border: none" rendered="#{not empty cuentaCteController.strDescEntidadGarante}">
			      <rich:column style="width:100px; border: 1">
			         <h:outputText  value="Entidad:"> </h:outputText>
			      </rich:column>
			      <rich:column style="width:300px; border: none">
         			 <h:outputText  value="#{cuentaCteController.strDescEntidadGarante}" > </h:outputText>
		          </rich:column>
		          <rich:column style="width:80px; border: none">
		          	 <h:outputText  value="#{cuentaCteController.strDesSucursalGarante}" > </h:outputText>
		          </rich:column>
		          <rich:column style="width:100px; border: none">
		          	 <h:outputText  value="#{cuentaCteController.strDesModaliadTipoSocioGarante}" ></h:outputText>
		          </rich:column>
			   </h:panelGrid>     
			</rich:panel>   
			 <rich:panel style=";border:0px">
                 <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
             </rich:panel>
			 <rich:panel> 
			   <h:panelGrid  columns="2" id="idMontoTransferencia">
			          <rich:column style="width:160px; border: none">
			          	 <h:outputText  value="Monto Transferencia:" > </h:outputText>
			          </rich:column>
			          <rich:column style="width:100px; border: none">
			          	 <h:inputText  value="#{cuentaCteController.bgMontoTransferencia}" disabled="true">
			          	  <f:converter converterId="ConvertidorMontos" />
			          	 </h:inputText>
			          </rich:column>
			   </h:panelGrid>
			   <h:panelGrid style="vertical-align: top">
						<rich:dataTable id="tbActividadEconomica" 
					    			var="item" 
					    			rowKeyVar="rowKey" 
					    			sortMode="single" 
					    			width="680px" 
				    				value="#{cuentaCteController.beanListaExpediente}" 
				    				rows="10">
				    				
				    				 <f:facet name="header">
                                        <rich:columnGroup>
	                                          <rich:column width="30px" style="text-align: center">
							                	 <h:outputText value="Nro"></h:outputText>
							                  </rich:column>
							                  <rich:column width="140">
							                     <h:outputText value="Tipo"></h:outputText>
							                  </rich:column>
							                  <rich:column width="60">
							                     <h:outputText value="Concepto"></h:outputText>
							                  </rich:column>
							                  <rich:column width="100">
							                    <h:outputText value="Nro. Documento"></h:outputText>
							                  </rich:column>
							                  <rich:column width="40">
							                    <h:outputText value="Saldo"></h:outputText>
							                  </rich:column>
							                  <rich:column width="40">
							                    <h:outputText value="Prioridad"></h:outputText>
							                  </rich:column>
							                  <rich:column width="100">
							                    	<h:outputText value="Monto Cargo"></h:outputText>
							                  </rich:column>
							                  <rich:column width="100">
							                    	<h:outputText value="Monto Abono"></h:outputText>
							                  </rich:column>
						                </rich:columnGroup>
                                     </f:facet>   
				    				<rich:columnGroup>
						                <rich:column width="30px" style="text-align: center">
						                	<h:outputText value="#{rowKey + 1}"></h:outputText>
						                </rich:column>
						               	<rich:column width="140">
						               	   
						                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
								   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
											      property="#{item.intParaTipoCreditoCod}"/>
							                <h:outputText rendered="#{empty item.intParaTipoCreditoCod}" value="#{item.strDescripcion}"></h:outputText>
						           		</rich:column>
						           		<rich:column width="60">
						                    <h:outputText rendered="#{not empty item.intParaTipoCreditoCod}" value="#{item.strDescripcion}"/>
						           		</rich:column>
						           		<rich:column width="100">
						                    <h:outputText value="#{item.id.intItemExpediente}"/>-<h:outputText value="#{item.id.intItemExpedienteDetalle}"/>
						           		</rich:column>
						           		<rich:column width="40" style="text-align: right" >
						                    <h:outputText rendered="#{empty item.checked}" value="#{item.bdSaldoCreditoSoles}">
						                       <f:converter converterId="ConvertidorMontos" />
						                    </h:outputText>
						                    <h:outputText rendered="#{item.checked}" value="#{cuentaCteController.bgMontoSaldoAportaciones}">
						                       <f:converter converterId="ConvertidorMontos" />
						                    </h:outputText>
						           		</rich:column>
						           		<rich:column width="40">
						                    <h:outputText value="#{item.intOrdenPrioridad}"/>
						           		</rich:column>
						           		<rich:column width="60">
						                    <h:outputText value=""/>
						                    <h:outputText rendered="#{item.checked && cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud != applicationScope.Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA}" value="#{item.bdSaldoCreditoSoles}">
						                        <f:converter converterId="ConvertidorMontos" />
						                    </h:outputText>
						                    <h:inputText  rendered="#{item.checked && cuentaCteController.beanSolCtaCte.solCtaCteTipo.intMotivoSolicitud == applicationScope.Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA}" value="#{item.bdSaldoCreditoSoles}">
						                         <a4j:support   event="onkeyup" 
																action="#{cuentaCteController.repartirMontoCargoAlMontoAbono}"
																ignoreDupResponses="true"
																requestDelay="500" 
																reRender="divFormTransferencia" 
												                focus="input" />
						                    </h:inputText>
						           		</rich:column>
						           		<rich:column width="40" style="text-align: right">
						                   <h:inputText rendered="#{empty item.checked && cuentaCteController.esEditableMontoSaldoAbono == false}" 
						                   		value="#{item.bdMontoAbono}" 
						                   		disabled="#{cuentaCteController.esEditableMontoSaldoAbono}"
						                   		onkeypress="return soloNumerosDecimalesPositivos(this);getMontoAbono(this.value);"
						                   		size="10">
						                   		 <a4j:support   event="onkeyup" 
																action="#{cuentaCteController.actualizarMontoTransferencia}"
																ignoreDupResponses="true"
																requestDelay="500" 
																reRender="divFormTransferencia,idMontoTransferencia,idMontoTotalTransf,idMontoTotalAbono" 
												                focus="input" />
										  </h:inputText>
						                  <h:outputText  rendered="#{empty item.checked && cuentaCteController.esEditableMontoSaldoAbono == true}" value="#{item.bdMontoAbono}">
						                     <f:converter converterId="ConvertidorMontos" />
						                  </h:outputText>
						           		</rich:column>
					           		</rich:columnGroup>
					                <f:facet name="footer">   
						           		<rich:columnGroup>
							                <rich:column width="30px" style="text-align: center">
							                	<h:outputText value=""></h:outputText>
							                </rich:column>
							               	<rich:column width="40" colspan="5" style="text-align: center">
							                    <h:outputText value="Total"/>
							           		</rich:column>
							           		<rich:column width="40">
							                    <h:outputText id="idMontoTotalTransf" value="#{cuentaCteController.bgMontoTransferencia}">
							                      <f:converter converterId="ConvertidorMontos" />
							                    </h:outputText>
							                    
							           		</rich:column>
							           		<rich:column width="40" style="text-align: right" >
							                	<h:outputText  id="idMontoTotalAbono"  value="#{cuentaCteController.bgMontoSaldoTotalAbono}">
							                	 <f:converter converterId="ConvertidorMontos" />
							                	</h:outputText>
							           		</rich:column>
						           		</rich:columnGroup>
					                </f:facet> 
					                
				            </rich:dataTable>
			  </h:panelGrid>
			</rich:panel>
			

			
	 </h:panelGroup>
	 
	 