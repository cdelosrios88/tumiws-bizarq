<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->

<script type="text/javascript">	  
	  
</script>
	     <rich:panel style="border:1px solid #17356f ;width: 765px; background-color:#DEEBF5" id="pnPrincipal">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
                    <h:panelGrid id="pgbusqEmpresa" columns="6">
                    	<rich:column>
                    		<h:outputText value="Empresa : "/>
                    	</rich:column>
                    	<rich:column>
                    		<h:selectOneMenu style="width: 200px;" value="#{empresaController.cboEmpresa}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
							</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column >
                    		<h:outputText id="lblbusqRuc" value="RUC : "></h:outputText>
                    	</rich:column>
                    	<rich:column >
                    		<h:inputText value="#{empresaController.txtRuc}" size="15"></h:inputText>
                    	</rich:column>
                    	<rich:column >
                    		<h:outputText value="Tipo : "></h:outputText>
                    	</rich:column>
                    	<rich:column >
                    		<h:selectOneMenu value="#{empresaController.cboTipoEmpresa}">
                    			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONFORMACIONEMPRESA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					    	</h:selectOneMenu>
                    	</rich:column>
                     </h:panelGrid>
                     <rich:spacer height="4px"/><rich:spacer height="4px"/>
                     <h:panelGrid columns="3">
                     	<rich:column >
                     		<h:outputText value="Estado : "></h:outputText>
                     	</rich:column>
                     	<rich:column >
							<h:selectOneMenu value="#{empresaController.cboEstadoEmpresa}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					    	</h:selectOneMenu>
                     	</rich:column>
                     	<rich:column >
                     		<a4j:commandButton value="Buscar" actionListener= "#{empresaController.listarEmpresas}" styleClass="btnEstilos" reRender="pgbusqEmpresa1"/>
                     	</rich:column>
                     </h:panelGrid>
                 <br/>
                 <div style="width:100%; overflow-x:auto; overflow-y:hidden; padding-left: 30px">
                     <h:panelGrid id="pgbusqEmpresa1">
                     		    
                            <rich:extendedDataTable id="tblbusqEmpresa" 
                             value="#{empresaController.beanListEmpresas}" var="item" sortMode="single" rows="5" width="640px" height="168px"
					  		 rowKeyVar="rowKey" onRowClick="jsSeleccionEmpresa(#{item.intIdEmpresa});" rendered="#{not empty empresaController.beanListEmpresas}">
                                
					  		 	<rich:column width="29">
                                    <f:facet name="header">
                                        <h:outputText  value="" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{rowKey+1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column id="colNombreEmpresa" width="200" headerClass="extdt-Headerclass" sortBy="#{item.juridica.strRazonSocial}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblNomEmpresa" value="Nombre de Empresa"></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValNomEmpresa" value="#{item.juridica.strRazonSocial}"></h:outputText>
                                    <rich:toolTip for="lblValNomEmpresa" value="#{item.juridica.strRazonSocial}" />
                                </rich:column>

                                <rich:column id="colTipoEmpresa" width="105" headerClass="extdt-Headerclass" sortBy="#{item.intTipoConformacionCod}">
                                    <f:facet name="header">
                                        <h:outputText id="lblTipo" value="Tipo"></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.intTipoConformacionCod}">
		                    			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONFORMACIONEMPRESA}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>

                                <rich:column id="colEstadoEmpresa" width="105" headerClass="extdt-Headerclass" sortBy="#{item.intEstadoCod}">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstado" value="Estado"></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.intEstadoCod}">
										<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>
                                <rich:column id="colAbreviaturaEmp" sortBy="#{item.juridica.strSiglas}">
                                    <f:facet name="header">
                                        <h:outputText id="lblAbreviatura" value="Abreviatura"></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValAbreviatura" value="#{item.juridica.strSiglas}"></h:outputText>
                                </rich:column>

                                <rich:column id="colEmpresaSuc" sortBy="#{item.intCantidadSucursal}">
                                    <f:facet name="header">
                                        <h:outputText id="lblSucursales" value="Sucursales"></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValSucursales" value="#{item.intCantidadSucursal}"></h:outputText>
                                </rich:column>
                                
                                <f:facet name="footer">   
					            	<rich:datascroller for="tblbusqEmpresa" maxPages="10"/>   
					            </f:facet>

                            </rich:extendedDataTable>
                            
                        </h:panelGrid>
                        <h:panelGrid columns="2">
						   	<h:outputLink value="#" id="linkPanelEmpr">
						        <h:graphicImage value="/images/icons/mensaje1.jpg"
									style="border:0px"/>
						        <rich:componentControl for="panelUpdateDelete" attachTo="linkPanelEmpr" operation="show" event="onclick"/>
						    </h:outputLink>
							<h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
						</h:panelGrid>
						
						<h:panelGrid>
							<rich:column >
	                        	<h:outputText id="lblValSucursales" value="#{empresaController.msgSinEmpresas}" styleClass="msgInfo"></h:outputText>
	                        </rich:column>
						</h:panelGrid>
						
					</div>
                       
                    	
                </rich:panel>                          
				<rich:spacer height="14px"/><rich:spacer height="14px"/> 
     	    <h:panelGrid columns="3">
                <a4j:commandButton id="btnNuevo" value="Nuevo" actionListener="#{empresaController.habilitarGrabarEmpresa}" styleClass="btnEstilos" reRender="pnRegistro"/>
				<a4j:commandButton value="Guardar" actionListener="#{empresaController.grabarEmpresa}" styleClass="btnEstilos" reRender="pnRegistro,pnPrincipal"/>												                 
                <a4j:commandButton id="btnEliminar" value="Cancelar" actionListener="#{empresaController.cancelarGrabarEmpresa}" styleClass="btnEstilos" reRender="pnRegistro,pnPrincipal"/>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <a4j:outputPanel id="pnRegistro">
           <rich:panel rendered="#{empresaController.empresaRendered}" style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;" >
  			    <h:panelGrid columns="4">
  			        <rich:column style="width: 95px; border: none">
  			        	<h:outputText value="Empresa : " style="padding-left: 10px"></h:outputText>
  			        </rich:column>
  			        <rich:column style="width: 250px; border: none">
  			        	<h:inputText value="#{empresaController.beanEmpresa.juridica.strRazonSocial}" size="60"></h:inputText>
  			        </rich:column>
  			        <rich:column >
  			        	<h:outputText value="#{empresaController.msgTxtEmpresa}" styleClass="msgError"></h:outputText>
  			        </rich:column>
  			        <rich:column >
  			        	<h:inputHidden value="#{empresaController.beanEmpresa.intIdEmpresa}"></h:inputHidden>
  			        </rich:column>
  			    </h:panelGrid>
  			    
  			    <h:panelGrid columns="3">
  			    	<rich:column style="width: 95px; border: none">
  			    		<h:outputText value="Abreviatura : " style="padding-left: 10px"></h:outputText>
  			    	</rich:column>
  			    	<rich:column >
  			    		<h:inputText  size="15" value="#{empresaController.beanEmpresa.juridica.strSiglas}" maxlength="100"></h:inputText>
  			    	</rich:column>
  			    	<rich:column >
  			        	<h:outputText value="#{empresaController.msgTxtAbreviatura}" styleClass="msgError"></h:outputText>
  			        </rich:column>
  			    </h:panelGrid> 
  			    
  			    <h:panelGrid columns="3">
  			    	<rich:column style="width: 95px; border: none">
  			    		<h:outputText id="lblRuc" value="Ruc : " style="padding-left: 10px"></h:outputText>
  			    	</rich:column>
  			    	<rich:column >
  			    		<h:inputText id= "txtRuc" value="#{empresaController.beanEmpresa.juridica.persona.strRuc}" maxlength="11" size="15"></h:inputText>
  			    	</rich:column>
  			    	<rich:column >
  			        	<h:outputText value="#{empresaController.msgTxtRuc}" styleClass="msgError"></h:outputText>
  			        </rich:column>
  			    </h:panelGrid>        
           
                <h:panelGrid columns="4">
                	<rich:column style="width: 95px; border: none">
                		<h:outputText id="lblTipoEmpresa" value="Tipo : " style="padding-left: 10px"></h:outputText>
                	</rich:column>
                	<rich:column style="width: 100px; border: none">
                		<h:selectOneMenu id="cboTipoEmpresa" style="width: 125px;"
			                value="#{empresaController.beanEmpresa.intTipoConformacionCod}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                   			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONFORMACIONEMPRESA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				    	</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="width: 85px; border: none">
                		<h:outputText id="lblEstEmpresa" value="Estado : "></h:outputText>
                	</rich:column>
                	<rich:column >
	                    <h:selectOneMenu id="cboEstEmpresa" value="#{empresaController.beanEmpresa.intEstadoCod}">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                   			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				         </h:selectOneMenu>
                	</rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="2" rendered="#{empresaController.renderSelectError}">
                	<rich:column style="width: 250px; border: none">
  			        	<h:outputText value="#{empresaController.msgTipoEmpresa}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
  			        </rich:column>
  			        <rich:column >
  			        	<h:outputText value="#{empresaController.msgEstadoEmp}" styleClass="msgError"></h:outputText>
  			        </rich:column>
                </h:panelGrid>
                
				<rich:spacer height="8px"/><rich:spacer height="8px"/>
				
               <rich:panel id="pMarco3" style="border: 1px solid #17356f;background-color:#DEEBF5;" >
                    <f:facet name="header">
                        <h:outputText id="lblSesEmpresa" value="Sesiones de Usuario"></h:outputText>
                    </f:facet>
                    <h:panelGrid columns="10" border="0" >
                      <rich:column style="border: none; width: 130px">
                        <h:outputText id="lblTimSesion" value="Tiempos de Sesión :"></h:outputText>
                      </rich:column>
                      <rich:column style="border: none; width: 125px">
                      	<h:selectOneMenu id="cboHoraSesion"    
                         	label="Hora"    
                         	required="true"    
                         	value="#{empresaController.strHoraSesion}">       
				            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>   
				        </h:selectOneMenu>
                      	<h:selectOneMenu id="cboMinutosSesion"    
                         	label="Minutos"    
                         	required="true"    
                         	value="#{empresaController.strMinutosSesion}">       
				            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>   
				        </h:selectOneMenu>
                      	<h:selectOneMenu id="cboSegundosSesion"    
                         	label="Segundos"    
                         	required="true"    
                         	value="#{empresaController.strSegundosSesion}">       
				            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>   
				        </h:selectOneMenu>
                      </rich:column>
                      <rich:column >
                        <h:outputLabel  value="*" style="color:red;font-size:20"></h:outputLabel>
                      </rich:column>
                      <rich:column >
                        <h:outputText value="hh:mm:ss" ></h:outputText>
                      </rich:column>
                      <rich:column >
                        <h:outputText id="lblAleSesion" value="Alerta de Sesión :"></h:outputText>
                      </rich:column>
                      <rich:column style="border: none; width: 125px">
                      	<h:selectOneMenu id="cboHoraAlerta"    
                         	label="Hora"    
                         	required="true"    
                         	value="#{empresaController.strHoraAlerta}">       
				            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>   
				        </h:selectOneMenu>
                      	<h:selectOneMenu id="cboMinutosAlerta"    
                         	label="Minutos"    
                         	required="true"    
                         	value="#{empresaController.strMinutosAlerta}">       
				            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>   
				        </h:selectOneMenu>
                      	<h:selectOneMenu id="cboSegundosAlerta"    
                         	label="Segundos"    
                         	required="true"    
                         	value="#{empresaController.strSegundosAlerta}">       
				            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>   
				        </h:selectOneMenu>
                      </rich:column>
                      <rich:column >
                        <h:outputLabel  value="*" style="color:red;font-size:20"></h:outputLabel> 
                      </rich:column> 
                      <rich:column >
                        <h:outputText value="hh:mm:ss"></h:outputText>      
                      </rich:column>      
                    </h:panelGrid>
                    
                    <h:panelGrid columns="2" rendered="#{empresaController.renderTiempoSesionError}">
	                	<rich:column style="width:340px; border: none">
	  			        	<h:outputText value="#{empresaController.msgTiempoSesion}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	  			        <rich:column >
	  			        	<h:outputText value="#{empresaController.msgAlertaSesion}" styleClass="msgError"></h:outputText>
	  			        </rich:column>
	                </h:panelGrid>
	                
	                <h:panelGrid columns="1" rendered="#{empresaController.renderTiempoSesionInvalido}">
	  			        <rich:column >
	  			        	<h:outputText value="#{empresaController.msgTiempoSesionError}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	                </h:panelGrid>
                
                    <rich:spacer height="8px"/><rich:spacer height="8px"/>
                    <h:panelGrid columns="4" border="0" style="width:680px;">
                    	<rich:column width="145" >
                        	<h:outputText id="lblVigClaves" value="Vigencia de Claves :"  style="padding-right: 10px;"></h:outputText>
                    	</rich:column>
                    	<rich:column rowspan="2" >
                    		<h:selectOneRadio id="radioVigenciaClaves" value="#{empresaController.strRadioVigenciaClaves}" onclick="jsClickRadioVigencia();"
                    		   layout="pageDirection" style="height: 50px">
                               <f:selectItem itemValue="indeterminado"/>
                               <f:selectItem itemValue="determinado"/>
                        	</h:selectOneRadio>
                    	</rich:column>
                    	<rich:column >
                        	<h:outputText id="lblIndeterminado" value="Indeterminado"   style="padding-right: 32px;"></h:outputText>
                    	</rich:column>
                    	<rich:column ></rich:column>
                                              
                    	<rich:column ></rich:column>
                    	<rich:column breakBefore="true" colspan="2" >
                    		<h:inputText id="txtVigenciaDias" size="10" value="#{empresaController.beanEmpresa.intVigenciaClaves}"></h:inputText>
                        	<h:outputText id="lblDias" value="Días"  style="padding-right: 20px;" ></h:outputText>
                    	</rich:column>
                    	<rich:column >
                    		<h:inputText id="txtCaducidad" size="10" value="#{empresaController.beanEmpresa.intAlertaCaducidad}"></h:inputText>
                        	<h:outputText id="lblIndeterminado1" value="Alerta de Caducidad (días)"  ></h:outputText>
                    	</rich:column>                                                                                                         
                    </h:panelGrid>
                    
                    <h:panelGrid columns="2" rendered="#{empresaController.renderVigenciaError}">
	  			        <rich:column style="width: 400px; border: none">
	  			        	<h:outputText value="#{empresaController.msgVigenciaClaves}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	  			        <rich:column >
	  			        	<h:outputText value="#{empresaController.msgAlertaCaducidad}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	                </h:panelGrid>
	                
	                <h:panelGrid columns="1" rendered="#{empresaController.renderVigenciaInvalido}">
	  			        <rich:column >
	  			        	<h:outputText value="#{empresaController.msgVigenciaError}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	                </h:panelGrid>
	                
                    <rich:spacer height="8px"/><rich:spacer height="8px"/>
                    <h:panelGrid id="panelSesiones" columns="3">
                    	<rich:column >
                    		<h:outputText value="Nro. Intentos de Ingreso :" ></h:outputText>
                    	</rich:column>
                    	<rich:column rowspan="2" >
                    		<h:selectOneRadio id="radioIntentosIngreso" value="#{empresaController.strRadioIntentosIngreso}" onclick="jsClickRadioIntentos();" 
                    			layout="pageDirection" style="height: 50px">
	                            <f:selectItem itemValue="determinado" />
	                            <f:selectItem itemValue="indeterminado" />
	                            <a4j:support event="onclick" actionListener="#{empresaController.selectRadioIntentosIngreso}" reRender="txtIntentos"/>
                        </h:selectOneRadio>
                    	</rich:column>
                    	<rich:column >
                    		<h:inputText id="txtIntentos" size="10" value="#{empresaController.beanEmpresa.intIntentosIngreso}"></h:inputText>
                    		<h:outputText value="Intentos" ></h:outputText>
                    	</rich:column>
                        
                    	<rich:column ></rich:column>
						<rich:column breakBefore="true" colspan="2" style="padding-bottom: 5px; border: none">
                    		<h:outputText value="Indeterminado" ></h:outputText>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="1" rendered="#{empresaController.renderIntentosError}">
	  			        <rich:column style="width: 400px; border: none">
	  			        	<h:outputText value="#{empresaController.msgIntentosError}" styleClass="msgError" style="padding-left: 10px"></h:outputText>
	  			        </rich:column>
	                </h:panelGrid>

                </rich:panel >
               <rich:spacer height="16px"/><rich:spacer height="16px"/>
                <rich:panel id="pMarco4" style="border: 1px solid #17356f;background-color:#DEEBF5; " >
                    <f:facet name="header">
                        <h:outputText id="lblParamGenerales" value="Parametros Generales " ></h:outputText>
                    </f:facet>
                    <h:panelGrid columns="2" style="height:68px;">
                    	<rich:column >
                    		<h:selectBooleanCheckbox id="chkSistContHorIngreso" value="#{empresaController.beanEmpresa.blnControlHoraIngreso}">                           
                        	</h:selectBooleanCheckbox>
                    	</rich:column>
                    	<rich:column >
                    		<h:outputText id="lblSistContHorIngreso" value="El Sistema Controla la Hora de Ingreso" ></h:outputText>
                    	</rich:column>
                    	
                    	<rich:column >
                    		<h:selectBooleanCheckbox id="chkSistVerPCRegistrada" value="#{empresaController.beanEmpresa.blnControlRegistro}">                           
                        	</h:selectBooleanCheckbox>
                    	</rich:column>
                    	<rich:column >
                    		<h:outputText id="lblSistVerPCRegistrada" value="El Sistema Verifica PCs Registradas" ></h:outputText>
                    	</rich:column>
                    	
                    	<rich:column >
                    		<h:selectBooleanCheckbox id="chkUsuCambClave" value="#{empresaController.beanEmpresa.blnControlCambioClave}">                           
                        	</h:selectBooleanCheckbox>
                    	</rich:column> 
                    	<rich:column >
                    		<h:outputText id="lblUsuCambClave" value="El Sistema Permitirá al Usuario Cambiar su Clave" ></h:outputText>
                    	</rich:column>
                    </h:panelGrid>
                </rich:panel>
     </rich:panel>
     </a4j:outputPanel>
     <br></br>
     </rich:panel>