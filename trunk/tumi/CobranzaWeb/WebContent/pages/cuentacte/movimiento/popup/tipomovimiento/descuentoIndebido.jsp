<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	 <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton disabled="#{!cuentaCteController.habilitarFormDescuentoIndebido}"
		                    value="Grabar" 
		                    styleClass="btnEstilos" 
		                    actionListener="#{cuentaCteController.grabarDescuentoIndebido}" 
		                    oncomplete="if(#{cuentaCteController.esGrabadoOk}){Richfaces.hideModalPanel('mpDescuentoIndebido')}"
		                    reRender="dataListSolCtaTipo,btnGrabar,formDescuentoIndebido">
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" 
				    		styleClass="btnEstilos"
				    		oncomplete="Richfaces.hideModalPanel('mpDescuentoIndebido')"/>
	</h:panelGrid>
	
   <h:panelGroup id="divFormDescuentoIndebido" layout="block" > 
   <rich:panel style=";border:0px">
            <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
    </rich:panel>
	     <rich:panel style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;">
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
		         	  		               disabled="#{!cuentaCteController.habilitarFormDescuentoIndebido}">
			         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
								<tumih:selectItems var="sel" value="#{cuentaCteController.listaMotivoDescuentoIndebido}" 
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
		  <rich:panel style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;">
		          <h:panelGrid  columns="3">
				            <rich:column style="width:120px; border: none">
				         		<h:outputText  value="Estado de Pago :"> </h:outputText>
				         	 </rich:column>
				         	 <rich:column style="width:160px; border: none">
				         	      <h:selectOneMenu value="#{cuentaCteController.intEstadoPago}" style="width:200px">
				         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADO_PAGO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								   </h:selectOneMenu>
				             </rich:column>
				             
				             <rich:column>
				              <a4j:commandButton value="Buscar" actionListener="#{cuentaCteController.buscarDesctoIndebido}"	
				               styleClass="btnEstilos"
				               reRender="pgFilDesctoIndebidoOrigen,pgFilDescuentoIndebidoPrestamo"/>
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
			    <h:panelGroup id="pgFilDesctoIndebidoOrigen" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilDescuentoIndebidoOrigen" value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaDesctoIndebidoOrigen}" 
                                 rendered="#{not empty cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaDesctoIndebidoOrigen}" 
                            	 var="item" rowKeyVar="rowkey" sortMode="single"  width="750px" height="240px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.id.intCcobItemsolctacte}">
                                    <f:facet name="header" >
                                        <h:outputText  id="lblPeriodo" value="Periodo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValPeriodo" value="#{item.periodoPlanilla}"></h:outputText>
                               </rich:column>
                                <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblTipo" value="Tipo" ></h:outputText>
                                    </f:facet>
                                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.socioEstructura.intTipoSocio}"/>
				         
                                </rich:column>
                                <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblModalidad" value="Modalidad" ></h:outputText>
                                    </f:facet>
                                     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.socioEstructura.intModalidad}"/>
             	                </rich:column>
                               
                                
                                <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblMonto" value="Monto" ></h:outputText>
                                    </f:facet>
                                      <h:outputText id="lblValMonto" value="#{item.bdDeinMonto}" />
                                 </rich:column>
                                 <rich:column width="160px">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstadoSol" value="Estado de Pago" ></h:outputText>
                                    </f:facet>
                                    
                                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADO_PAGO}" 
										                  itemValue="intIdDetalle" 
										                  itemLabel="strDescripcion" 
										                  property="#{item.intParaEstadoPagadocod}"/>
							     </rich:column>
                                 <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblUsuario" value="Opción" ></h:outputText>
                                    </f:facet>
                                     <a4j:commandButton rendered="#{empty item.intParaEstadoPagadocod}" 
                                                        value="Generar" styleClass="btnEstilos" 
                                                        disabled="#{!cuentaCteController.habilitarFormDescuentoIndebido}"
                                                        actionListener="#{cuentaCteController.generarDescuentoIndebido}"
                                                        reRender="pgFilDesctoIndebidoOrigen">
                                      <f:attribute  name="item" value="#{item}"/>
                                     </a4j:commandButton>
                                 </rich:column>
                                 
                                 <f:facet name="footer" >
							   		 <h:panelGroup layout="block" id="txtSeleccionarsss">
								   		 <rich:datascroller for="tblFilDescuentoIndebidoOrigen" maxPages="10"/>
								   		 <h:panelGroup layout="block" style="margin:0 auto; width:510px">
								   		
								   		 	<h:outputText style="color:#8ca0bd" >
											  Para generar el descuento indebido, Presionar el Boton "Generar".
											</h:outputText>
							   		 </h:panelGroup>
							   		 </h:panelGroup>  
						       </f:facet>   
                               
                            </rich:extendedDataTable>      
              </h:panelGroup>
			</rich:panel>   
			<rich:panel id="entidadPrestamo" style="width: 800px;border:1px solid #880f;background-color:#DEEBF5;"> 
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
			   
			    <h:panelGrid  columns="2" style="width:800px; border: none">
			      <rich:column  style="width:400px; border: 1">
			         		<h:outputText style = "color:RED;"   value="#{cuentaCteController.messagePlanillaPrestamo}"> </h:outputText>
			      </rich:column>
			      <rich:column>
			       		    <h:outputText  style = "color:RED;"  value=""> </h:outputText>
			      </rich:column>
			   </h:panelGrid>     
			      <h:panelGroup id="pgFilDescuentoIndebidoPrestamo" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilDescuentoIndebidoPrestamo" value="#{cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaDesctoIndebidoPrestamo}" 
                                 rendered="#{not empty cuentaCteController.beanSolCtaCte.solCtaCteTipo.listaDesctoIndebidoPrestamo}" 
                            	 var="item" rowKeyVar="rowkey" sortMode="single"  width="750px" height="240px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="70px" sortBy="#{item.id.intCcobItemsolctacte}">
                                    <f:facet name="header" >
                                        <h:outputText  id="lblPeriodo" value="Periodo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValPeriodo" value="#{item.periodoPlanilla}"></h:outputText>
                               </rich:column>
                                <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblTipo" value="Tipo" ></h:outputText>
                                    </f:facet>
                                       <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.socioEstructura.intTipoSocio}"/>-
				         
                                </rich:column>
                                <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblModalidad" value="Modalidad" ></h:outputText>
                                    </f:facet>
                                     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
										itemValue="intIdDetalle" itemLabel="strDescripcion" 
										property="#{item.socioEstructura.intModalidad}"/>
             	                </rich:column>
                               
                                
                                <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblMonto" value="Monto" ></h:outputText>
                                    </f:facet>
                                      <h:outputText id="lblValMonto" value="#{item.bdDeinMonto}" />
                                 </rich:column>
                                 <rich:column width="160px">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstadoSol" value="Estado de Pago" ></h:outputText>
                                    </f:facet>
                                    
                                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADO_PAGO}" 
										                  itemValue="intIdDetalle" 
										                  itemLabel="strDescripcion" 
										                  property="#{item.intParaEstadoPagadocod}"/>
							     </rich:column>
                                 <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblUsuario2" value="Opción" ></h:outputText>
                                    </f:facet>
                                    <a4j:commandButton rendered="#{empty item.intParaEstadoPagadocod}" 
                                                        value="Generar" styleClass="btnEstilos" 
                                                        disabled="#{!cuentaCteController.habilitarFormDescuentoIndebido}"
                                                        actionListener="#{cuentaCteController.generarDescuentoIndebido}"
                                                        reRender="pgFilDesctoIndebidoOrigen">
                                      <f:attribute  name="item" value="#{item}"/>
                                     </a4j:commandButton>
                                 </rich:column>
                               
                            
                                <f:facet name="footer" >
							   		 <h:panelGroup layout="block" id="txtSeleccionar2">
								   		 <rich:datascroller for="tblFilDescuentoIndebidoPrestamo" maxPages="10"/>
								   		 <h:panelGroup layout="block" style="margin:0 auto; width:510px">
								   			<h:outputText style="color:#8ca0bd" >
											 Para generar el descuento indebido, Presionar el Boton "Generar".
											</h:outputText>
							   		      </h:panelGroup>
							   		 </h:panelGroup>  
						       </f:facet>       
                            </rich:extendedDataTable>      
               </h:panelGroup>
			</rich:panel>  
			
	 </h:panelGroup>
	 
	 