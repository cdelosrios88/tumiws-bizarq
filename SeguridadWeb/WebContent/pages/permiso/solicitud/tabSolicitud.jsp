<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
<div id="dvBarras" style="background-color: #DEEBF5">	
               <h:panelGrid id = "panelTable" columns="6" border="0" style="width: 750px;" >
                  <rich:column style="width: 95px; border: none">
                  	<h:outputText value="Empresa :"  />
                  </rich:column>
                  <rich:column style="width: 240px; border: none">
                  	<h:selectOneMenu id="cboEmpSoliBusq" style="width: 220px;" value="#{adminMenuController.intCboEmpresaSoli}">      
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{adminMenuController.listaJuridicaEmpresa}"
							   itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
						<a4j:support event="onchange" reRender="cboUsuDesarrolloBusq,cboUsuSolicitanteBusq" ajaxSingle="true"/>
					</h:selectOneMenu>
                  </rich:column>
                  <rich:column style="width: 105px; border: none">
                      <h:outputText value="Tipo de Cambio :"  />
                  </rich:column>
                  <rich:column style="width: 80px; border: none">
                  	<h:selectOneMenu value="#{adminMenuController.intTipoCambioSoli}">
	                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOLICCAMBIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                	<%--<f:selectItems value="#{parametersController.cboTiposSolicitudCambio}"/>--%>
	                </h:selectOneMenu>
                  </rich:column>
	              <rich:column style="width: 50px; border: none">
	              	<h:outputText value="Estado :" />
	              </rich:column>
	              <rich:column style="width: 80px; border: none">
	              	<h:selectOneMenu value="#{adminMenuController.intEstadoSoli}">
		            	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCAMBIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		            </h:selectOneMenu>
	              </rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="4" border="0" style="width: 750px;" >
                  <rich:column style="width: 106px; border: none">
                  	<h:outputText value="Desarrollado : "  style="padding-left: 6px;"/>
                  </rich:column>
                  <rich:column style="width: 249px; border: none">
                  	<h:selectOneMenu id="cboUsuDesarrolloBusq" value="#{adminMenuController.intCboDesarrolladorSoli}" style="width:120px;">
			        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<%--tumih:selectItems var="sel" value="#{controllerFiller.listUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/--%>
			        	<%--<f:selectItems value="#{controllerFiller.cboUsuarioSoli}"/> --%>
			        </h:selectOneMenu>
                  </rich:column>
                  <rich:column style="width: 109px; border: none">
                  	<h:outputText value="Solicitante : "  />
                  </rich:column>
                  <rich:column style="border: none">
                  	<h:selectOneMenu id="cboUsuSolicitanteBusq" value="#{adminMenuController.intCboSolicitanteSoli}" style="width:120px;">
		               	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<%--tumih:selectItems var="sel" value="#{controllerFiller.listUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/--%>
			        </h:selectOneMenu>
                  </rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="9" border="0" style="width: 750px;" >
                	<rich:column style="border: none">
                      <h:outputText value="Clase :" style="padding-left: 7px;"  />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu value="#{adminMenuController.intClaseSoli}">
                  			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CLASECAMBIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                		<%--<f:selectItems value="#{parametersController.cboTiposClaseCambio}"></f:selectItems>--%>
	                	</h:selectOneMenu>
                  	</rich:column>
                	<rich:column style="border: none">
                      <h:outputText value="Rango de fechas :"  />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu value="#{adminMenuController.intRangoFechSoli}">
	                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORANGOFECHASOLICCAMBIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                	</h:selectOneMenu>
                  	</rich:column>
                	<rich:column style="border: none">
                		<rich:calendar value="#{adminMenuController.dtFechRango1Soli}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"/>
                	</rich:column>
                	<rich:column style="border: none">
                		<rich:calendar value="#{adminMenuController.dtFechRango2Soli}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"/>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectBooleanCheckbox value="#{adminMenuController.blnAnexosSoli}"/>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Anexos"  />
                	</rich:column>
                	<rich:column style="border: none">
	                  	<a4j:commandButton value="Buscar" actionListener= "#{adminMenuController.buscarSolicitudes}" styleClass="btnEstilos" reRender="pgAdminMenu"/>
	                </rich:column>
                </h:panelGrid>
                
              <h:panelGrid id="pgAdminMenu" columns = "2" style = "background-color: #DEEBF5">
                 <rich:column style="width: 40px; border: none">
                 	<h:outputText value=""/>
                 </rich:column>
                 <rich:column style="border: none">
                 	<rich:scrollableDataTable id="tbSolicitud" value="#{adminMenuController.beanListSolicitudes}" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single"  rendered="#{not empty adminMenuController.beanListSolicitudes}"
	                            	onRowClick="jsSelectSolicitud('#{item.intIdSolicitud}');" width="655px" height="195px">
	                    <rich:column width="29">
	                       <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="250">
	                       <f:facet name="header">
	                          <h:outputText value="Ruta" ></h:outputText>
	                       </f:facet>
	                       <h:outputText id="solRuta" value="#{item.strRuta}"></h:outputText>
	                       <rich:toolTip for="solRuta" value="#{item.strRuta}"></rich:toolTip>
	                    </rich:column>
	                    <rich:column width="120">
	                       <f:facet name="header">
	                          <h:outputText value="Fecha de Cambio" > </h:outputText>
	                       </f:facet>
	                       <h:outputText value="#{item.strFechSolicitud}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="120">
	                       <f:facet name="header">
	                          <h:outputText value="Tipo de Cambio" ></h:outputText>
	                       </f:facet>
	                       <h:outputText value="#{item.strTipoCambio}"></h:outputText>
	                    </rich:column>
	                    <rich:column>
	                       <f:facet name="header">
	                          <h:outputText value="Estado" ></h:outputText>
	                       </f:facet>
	                       <h:outputText value="#{item.strEstado}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="120">
	                       <f:facet name="header">
	                          <h:outputText value="Observaciones" ></h:outputText>
	                       </f:facet>
	                       <h:outputText value="#{item.strObserv}"></h:outputText>
	                    </rich:column>
	                 </rich:scrollableDataTable>
                 </rich:column>										
              </h:panelGrid>
              
              <rich:spacer height="8px"/>
              <rich:spacer height="8px"/>
              
              	<h:panelGrid columns="3">
              		<rich:column style="width: 40px; border: none">
	                 	<h:outputText value=""/>
	                 </rich:column>
					<h:outputLink value="#" id="linkSolCam">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelSolicitud" attachTo="linkSolCam" operation="show" event="onclick"/>
					</h:outputLink>
					<h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
				</h:panelGrid>
              
              	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
              <h:panelGrid columns="3">
              	<h:commandButton value="Nuevo" actionListener="#{adminMenuController.habilitarGrabarSolicitud}" styleClass="btnEstilos"/>
                <h:commandButton value="Grabar" actionListener="#{adminMenuController.guardarSolicitudes}" styleClass="btnEstilos"/>  
              	<h:commandButton value="Cancelar" actionListener="#{adminMenuController.cancelarGrabarSolicitud}" styleClass="btnEstilos"/>
              </h:panelGrid>
              	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               
			   <rich:panel style="width: 750px; background-color: #DEEBF5" rendered="#{adminMenuController.blnSolicitudRendered}">
			   
			    <h:panelGrid columns="2" border="0" style="width: 750px;" >
                  <rich:column style="width: 95px; border: none">
                  	<h:outputText value="Empresa :"  />
                  </rich:column>
                  <rich:column style="border: none">
                  	<h:selectOneMenu id="cboEmpresasSoli" style="width: 300px;" required="true"
                  					valueChangeListener="#{adminMenuController.reloadCboUsuarioMenu}"
				                   	value="#{adminMenuController.beanSolicitud.intIdEmpresa}">      
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						<a4j:support event="onchange" reRender="cboUsuDesarrollo,cboUsuSolicitante,cboUsuCIO,cboSolMenu1,cboSolMenu2,cboSolMenu3,cboSolMenu4" ajaxSingle="true" />
					</h:selectOneMenu>
                  </rich:column>
                </h:panelGrid>
                
                <rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
               	<rich:spacer height="4px"/><rich:spacer height="4px"/>
			   	
			   	<rich:panel style="background-color: #DEEBF5">
                  <f:facet name="header">
                     <h:outputText value="Nombre de la Aplicación"/>
                  </f:facet>
                  <h:panelGrid columns="8" border="0" cellspacing="50%" style="width:610px;">  
                  	<rich:column style="border: none">
                  		<h:outputText value="Menú1" />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu id="cboSolMenu1" value="#{adminMenuController.strCboSolMenu1}"
					       	valueChangeListener="#{adminMenuController.reloadCboMenu2}" style="width: 120px;">      
						    <f:selectItems value="#{adminMenuController.cboMenu1}"/>
						    <a4j:support event="onchange" reRender="cboSolMenu2,cboSolMenu3,cboSolMenu4" ajaxSingle="true" />
						</h:selectOneMenu>
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:outputText value="Menú2" />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu id="cboSolMenu2" value="#{adminMenuController.strCboSolMenu2}"
					     	valueChangeListener="#{adminMenuController.reloadCboMenu3}" style="width: 120px;">      
							<f:selectItems value="#{adminMenuController.cboMenu2}"></f:selectItems>
							<a4j:support event="onchange" reRender="cboSolMenu3,cboSolMenu4" ajaxSingle="true" />
						</h:selectOneMenu>
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:outputText value="Menú3" />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu id="cboSolMenu3" value="#{adminMenuController.strCboSolMenu3}"
					       	valueChangeListener="#{adminMenuController.reloadCboMenu4}" style="width: 120px;">      
						    <f:selectItems value="#{adminMenuController.cboMenu3}"></f:selectItems>
						    <a4j:support event="onchange" reRender="cboSolMenu4" ajaxSingle="true" />
						</h:selectOneMenu>
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:outputText value="Menú4" />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu id="cboSolMenu4" value="#{adminMenuController.strCboSolMenu4}" style="width: 120px;">      
						    <f:selectItems value="#{adminMenuController.cboMenu4}"></f:selectItems>
						</h:selectOneMenu>
                  	</rich:column>
                  </h:panelGrid>
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                  
                  <rich:separator height="5px"></rich:separator>
                  
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                   
                   
                  <h:panelGrid columns="3" border="0"   style="width:700px;height: 80px;" >
                     <h:outputText value="Descripción del Cambio :" />
                   	 <h:inputTextarea value="#{adminMenuController.beanSolicitud.strDescripcion}" cols="85"></h:inputTextarea>                 
                     <h:outputText value=""/>                    	
                     <h:outputText value="Justificación del Cambio :" />
                     <h:inputTextarea value="#{adminMenuController.beanSolicitud.strJustificacion}" cols="85"></h:inputTextarea>
                     <h:outputText value=""/>                    	
                     <h:outputText value="Finalidad del Cambio :" />
                     <h:inputTextarea id="txtaFinaCambio" value="#{adminMenuController.beanSolicitud.strFinalidad}" cols="85"></h:inputTextarea>
                     <h:outputText value=""/>
                     <h:outputText value="Aplicaciones Afectadas :"/>
                     <h:inputText id="txtAplicacionesSoli" value="#{adminMenuController.strAplicacionesMenu}" size="86"></h:inputText>
                     <a4j:commandLink actionListener="#{adminMenuController.listarAplicaciones}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="pgTablas,tbTablasMenu" immediate="true">
                       	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                     </a4j:commandLink>
                     <h:outputText value="Tablas Afectadas :" />
                     <h:inputText id="txtTablasSoli" value="#{adminMenuController.strTablasMenu}" size="86" ></h:inputText>
                     <a4j:commandLink actionListener="#{adminMenuController.listarTablas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="pgTablas,tbTablasMenu" immediate="true">
                       	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                     </a4j:commandLink>
                     <h:outputText value="Vistas Afectadas :" />
                     <h:inputText id="txtVistasSoli" value="#{adminMenuController.strVistasMenu}" size="86" ></h:inputText>
                     <a4j:commandLink actionListener="#{adminMenuController.listarVistas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="tbTablasMenu" immediate="true">
                       	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                     </a4j:commandLink>
                     <h:outputText value="Triggers Afectadas :" />
                     <h:inputText id="txtTriggersSoli" value="#{adminMenuController.strTriggersMenu}" size="86" ></h:inputText>
                     <a4j:commandLink actionListener="#{adminMenuController.listarTriggers}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" reRender="tbTablasMenu" immediate="true">
                    	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                     </a4j:commandLink>
                     <h:outputText value="Anexos :" />
                     <h:inputText size="86"></h:inputText>
                     <a4j:commandLink>
                    	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                     </a4j:commandLink>                                                     
                  </h:panelGrid>
                   <rich:spacer height="4px"/><rich:spacer height="4px"/>
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                     
                </rich:panel>  
                
                <rich:panel style="background-color: #DEEBF5">    
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                  <rich:spacer height="4px"/><rich:spacer height="4px"/>
                  
                  <rich:spacer height="10px"/><rich:spacer height="10px"/>
                  <h:panelGrid>
                  	<h:commandButton value="Agregar" actionListener="#{adminMenuController.agregarSolicitudes}" styleClass="btnEstilos"></h:commandButton>
                  </h:panelGrid>
                  <h:panelGrid columns="6" border="0" width="750px"   bgcolor="#DEEBF5">
                  	<rich:column style="width: 105px; border: none">
                  		<h:outputText value="Tipo de Cambio :" />
                  	</rich:column>
                  	<rich:column style="width: 120px; border: none">
                  		<h:selectOneMenu value="#{adminMenuController.beanSolicitud.intTipoCambio}">
                  			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOLICCAMBIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     </h:selectOneMenu>
                  	</rich:column>
                  	<rich:column style="width: 100px; border: none">
                  		<h:outputText value="Estado :" />
                  	</rich:column>
                  	<rich:column style="width: 120px; border: none">
                  		<h:selectOneMenu value="#{adminMenuController.beanSolicitud.intIdEstado}">
	                        <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCAMBIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     </h:selectOneMenu>
                  	</rich:column>
                  	<rich:column style="width: 100px; border: none">
                  		<h:outputText value="Clase :" />
                  	</rich:column>
                  	<rich:column style="border: none">
                  		<h:selectOneMenu value="#{adminMenuController.beanSolicitud.intClase}">
	                        <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
	                        <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CLASECAMBIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     </h:selectOneMenu>
                  	</rich:column>
	            </h:panelGrid>  
	            <h:panelGrid columns="6" border="0" width="750px"   bgcolor="#DEEBF5">
	            	<rich:column style="width: 105px; border: none">
	            		<h:outputText value="Fecha Solicitud :" />
	            	</rich:column>
	            	<rich:column style="width: 120px; border: none">
	            		<rich:calendar value="#{adminMenuController.dtFechSolicitud}" datePattern="dd/MM/yyyy" inputStyle="width: 75px" style="width: 170px"/>
	            	</rich:column>
	            	<rich:column style="width: 100px; border: none">
	            		<h:outputText value="Fecha Prueba :" />
	            	</rich:column>
	            	<rich:column style="width: 120px; border: none">
	            		<rich:calendar value="#{adminMenuController.dtFechPrueba}" datePattern="dd/MM/yyyy" inputStyle="width: 75px" style="width: 170px"/>
	            	</rich:column>
	            	<rich:column style="width: 100px; border: none">
	            		<h:outputText value="Fecha Entrega :" />
	            	</rich:column>
	            	<rich:column style="border: none">
	            		<rich:calendar value="#{adminMenuController.dtFechEntrega}" datePattern="dd/MM/yyyy" inputStyle="width: 75px" style="width: 170px"/>
	            	</rich:column>
                </h:panelGrid>
                <rich:spacer height="8px"/><rich:spacer height="8px"/>
                <rich:spacer height="8px"/><rich:spacer height="8px"/>  
                   <h:panelGrid columns="2"> 
                   	<rich:column style="border:none; width:130px">
                   		<h:outputText value="Desarrollado por : " />
                   	</rich:column>
                   	<rich:column style="border:none;">
                   		<h:selectOneMenu id="cboUsuDesarrollo" value="#{adminMenuController.beanSolicitud.intIdDesarrollador}" style="width:120px;">
			               	<f:selectItems value="#{adminMenuController.cboUsuario}"></f:selectItems>
			            </h:selectOneMenu>
                   	</rich:column>
                   	<rich:column style="border:none; width:130px">
                   		<h:outputText value="Solicitantes : " />
                   	</rich:column>
                   	<rich:column style="border:none;">
                   		<h:selectOneMenu id="cboUsuSolicitante" value="#{adminMenuController.beanSolicitud.intIdSolicitante}" 
	               			style="width:120px;">
			               	<f:selectItems value="#{adminMenuController.cboUsuario}"></f:selectItems>
			             </h:selectOneMenu>
                   	</rich:column>
                   	<rich:column style="border:none; width:130px">
                   		<h:outputText value="Obs. del CIO : " />
                   	</rich:column>
                   	<rich:column style="border:none;">
                   		<h:selectOneMenu id="cboUsuCIO" value="#{adminMenuController.beanSolicitud.intIdCIO}" 
	               			style="width:120px;">
			               	<f:selectItems value="#{adminMenuController.cboUsuario}"></f:selectItems>
			             </h:selectOneMenu>
                   	</rich:column>                                                                                                    
                  </h:panelGrid>
                </rich:panel>
                   
                   <rich:spacer height="8px"/><rich:spacer height="8px"/>
                   <rich:spacer height="8px"/><rich:spacer height="8px"/> 
                  
                  <h:panelGrid columns="1" border="0">
                     <rich:dataTable id="dtSolCambio" value="#{adminMenuController.beanListSolAgregadas}" rows="5" sortMode="single"
	                            	width="600" var="item" rowKeyVar="rowKey" rendered="#{not empty adminMenuController.beanListSolAgregadas}">
                        <rich:column>
                           <h:outputText value="#{rowKey + 1}"></h:outputText>
                        </rich:column>
                        <rich:column>
                           <f:facet name="header">
                              <h:outputText value="Tipo de Cambio" ></h:outputText>
                           </f:facet>
                           <h:outputText value="#{item.strTipoCambio}"></h:outputText>
                        </rich:column>
                        <rich:column>
                           <f:facet name="header">
                              <h:outputText value="Estado" ></h:outputText>
                           </f:facet>
                           <h:outputText value="#{item.strEstado}"></h:outputText>
                        </rich:column>
                        <rich:column>
                           <f:facet name="header">
                              <h:outputText value="Clase" ></h:outputText>
                           </f:facet>
                           <h:outputText value="#{item.strClase}"></h:outputText>
                        </rich:column>
                        <rich:column>
                           <f:facet name="header">
                              <h:outputText value="Fecha de Solicitud" ></h:outputText>
                           </f:facet>
                           <h:outputText value="#{item.strFechSolicitud}"></h:outputText>
                        </rich:column>
                        <rich:column>
                           <f:facet name="header">
                              <h:outputText value="Desarrollado por" ></h:outputText>
                           </f:facet>
                           <h:outputText value="#{item.strDesarrollador}"></h:outputText>
                        </rich:column>
                     </rich:dataTable>
                                         
                     <h:outputText value="" style="padding-right:2px;"></h:outputText>
                     <rich:datascroller for="dtSolCambio" maxPages="20"/>
                     
                  </h:panelGrid>
			   </rich:panel>
</div>			   