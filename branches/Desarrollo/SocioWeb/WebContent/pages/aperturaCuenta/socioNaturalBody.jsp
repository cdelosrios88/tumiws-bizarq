<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 04/06/2012               -->
	
<a4j:region>
	<a4j:jsFunction name="seleccionarUnidadEjecutora" reRender="mpMessage,divSocioNatuDepen,pgSocioNatuDepen,pgTestUniEje,txtIdSucursal"
		actionListener="#{socioEstrucController.seleccionarEntidad}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="rederRoles" reRender="txtRoles">
	</a4j:jsFunction>
</a4j:region>
					<h:panelGrid>
						<h:outputText value="#{socioController.msgTxtDsctoJudicial}" styleClass="msgInfo"/>
						<h:outputText value="#{socioController.msgTxtLicencPermiso}" styleClass="msgInfo"/>
						<h:outputText value="#{socioController.msgTxtLicencSubvencPermiso}" styleClass="msgInfo"/>
					</h:panelGrid>
					
					<h:panelGrid columns="3">
                     	<rich:column width="776">
                     		<h:outputText value="Datos Personales" styleClass="estiloLetra1"></h:outputText>
                     	</rich:column>
                     	<rich:column>
                     		<a4j:commandButton value="Consultar Terceros" styleClass="btnEstilos1" reRender="mpTerceros"
                     						actionListener="#{tercerosController.consultarTerceros}"
                     						oncomplete="Richfaces.showModalPanel('mpTerceros')"></a4j:commandButton>
                     	</rich:column>
                     	<rich:column>
                     		<h:selectOneMenu value="#{tercerosController.intFrecuenciaTerceros}">
                     			<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
	                     		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FREQCONSULTATERCEROS}" 
							  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     	</h:selectOneMenu>
                     	</rich:column>
                     </h:panelGrid>
                     
                     <h:panelGrid>
                     	<h:outputText value="Feliz Cumpleaños Socio" 
                     	style="color:red;font:oblique bold 120% cursive;" rendered="#{socioController.blnMsgFecCumple}"/>
                     </h:panelGrid>
                     
                     <h:panelGrid columns="6" styleClass="tableCellBorder4">
                     	<rich:columnGroup>
                     		<rich:column>
	                     		<h:outputText value="Reniec: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Ap. Paterno: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText value="#{socioController.beanSocioComp.socio.strApePatSoc}" size="15" disabled="#{socioController.isDatosView}"/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Ap. Materno: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="15" value="#{socioController.beanSocioComp.socio.strApeMatSoc}" disabled="#{socioController.isDatosView}"/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Nombres: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="25" value="#{socioController.beanSocioComp.socio.strNombreSoc}" disabled="#{socioController.isDatosView}"/>
	                     	</rich:column>
                     	</rich:columnGroup>
                     	
                     	<rich:columnGroup>
                     		<rich:column>
	                     		<h:outputText value="Centro Trabajo: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Ap. Paterno: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText value="#{socioController.beanSocioComp.persona.natural.strApellidoPaterno}" size="15" disabled="#{socioController.blnIsDatoPadron || socioController.isDatosView}"/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Ap. Materno: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="15" value="#{socioController.beanSocioComp.persona.natural.strApellidoMaterno}" disabled="#{socioController.blnIsDatoPadron || socioController.isDatosView}"/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:outputText value="Nombres: "/>
	                     	</rich:column>
	                     	<rich:column>
	                     		<h:inputText size="25" value="#{socioController.beanSocioComp.persona.natural.strNombres}" disabled="#{socioController.blnIsDatoPadron || socioController.isDatosView}"/>
                     		</rich:column>
                     	</rich:columnGroup>
                     </h:panelGrid>
                     
                     <h:panelGroup id="pgSocio" layout="block">
                     	<h:panelGroup id="divPerNatu" layout="block" style="float:left">
                     		<h:panelGrid columns="3" styleClass="tableCellBorder4" style="margin-left: -10px">
                     			<rich:columnGroup>
                     				<rich:column>
		                   				<h:outputText value="Número Documento: "/>
		                   			</rich:column>
		                   			<rich:column>
		                   				<h:inputText value="#{socioController.beanSocioComp.persona.documento.strNumeroIdentidad}"
		                   							onkeydown="return validarNumDocIdentidad(this,event,8)" disabled="true"/>
		                   			</rich:column>
                     			</rich:columnGroup>
                     			
                     			<rich:columnGroup>
                     				<rich:column>
			                     		<h:outputText value="Fecha de Nacimiento: " style="padding-top:0px" />
			                     	</rich:column>
			                     	<rich:column>
			                     		<rich:calendar datePattern="dd/MM/yyyy" value="#{socioController.beanSocioComp.persona.natural.dtFechaNacimiento}"
			                     		inputStyle="width:70px;" cellWidth="10px" cellHeight="20px" disabled="#{socioController.blnIsDatoPadron || socioController.isDatosView}" 
			                     		enableManualInput="true"/>
			                     	</rich:column>
                     			</rich:columnGroup>
								
								<rich:columnGroup>
									<rich:column>
	                     				<h:outputText value="Sexo: "></h:outputText>
	                     			</rich:column>
	                     			<rich:column>
	                      				<h:selectOneRadio value="#{socioController.beanSocioComp.persona.natural.intSexoCod}" style="width:155px" disabled="#{socioController.isDatosView}">
	                     					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSEXO}" 
								  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     				</h:selectOneRadio>  
	                     			</rich:column>
								</rich:columnGroup>
                     			
                     			<rich:columnGroup>
                     				<rich:column>
	                     				<h:outputText value="Estado Civil: "></h:outputText>
	                     			</rich:column>
	                     			<rich:column>
	                      				<h:selectOneMenu value="#{socioController.beanSocioComp.persona.natural.intEstadoCivilCod}">
	                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
								  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     				</h:selectOneMenu>  
	                     			</rich:column>
                     			</rich:columnGroup>

								<rich:columnGroup>
									<rich:column>
	                     				<h:outputText value="Tipo de Persona: "></h:outputText>
	                     			</rich:column>
	                     			<rich:column>
	                      				<h:selectOneMenu value="#{socioController.beanSocioComp.persona.intTipoPersonaCod}" disabled="true">
	                     					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                     				</h:selectOneMenu>  
	                     			</rich:column>
								</rich:columnGroup>
                     			
                     			<rich:columnGroup>
                     				<rich:column>
	                     				<h:outputText value="Roles Actuales: "></h:outputText>
	                     			</rich:column>
	                     			<rich:column>
	                     				<h:inputText id="txtRoles" value="#{socioController.strRoldePersona}#{socioController.beanSocioComp.strRoles}" size="30" disabled="true"/>
	                     			</rich:column>
	                     			<rich:column>
		                     			<a4j:commandButton value="Seleccionar" actionListener="#{socioController.showRoles}"
						                     						reRender="mpRoles" styleClass="btnEstilos"
						                     						oncomplete="Richfaces.showModalPanel('mpRoles')"></a4j:commandButton>
	                     			</rich:column>
                     			</rich:columnGroup>
                     		</h:panelGrid>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="float:left; width:530px">
                     		<h:panelGroup layout="block" style="margin-left:10px">
                     			<h:panelGrid id="pgDocumentos" columns="4" styleClass="tableCellBorder4">
			                     	<rich:columnGroup>
			                   			<rich:column>
				                     		<h:outputText value="Otros Documentos: "/>
				                     	</rich:column>
				                     	<rich:column>
			 	                     		<h:selectOneMenu value="#{socioController.beanSocioComp.intTipoDocIdentidad}"
			 	                     						onchange="selecTipoDocIdentidad(#{applicationScope.Constante.ONCHANGE_VALUE})">
			 	                     			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
										  		<tumih:selectItems var="sel" value="#{socioController.listDocumento}" 
								  					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				                     		</h:selectOneMenu>
				                     	</rich:column>
				                     	<rich:column>
				                     		<h:inputText size="20" value="#{socioController.beanSocioComp.strDocIdentidad}" maxlength="11"/>
				                     		<%--
				                     		<h:inputText id="txtOtroDocumento" size="20" value="#{socioController.beanSocioComp.strDocIdentidad}"
				                     			maxlength="#{socioController.intDocIdentidadMaxLength}"/>--%>
				                     	</rich:column>
				                     	<rich:column>
				                     		<a4j:commandButton value="Agregar" actionListener="#{socioController.addOtroDocumento}"
				                     						reRender="pgDocumentos,tbOtrosDocumentos" styleClass="btnEstilos"/>
				                     		<!-- 
				                     		<a4j:commandButton value="Agregar" actionListener="#{socioController.addOtroDocumento}"
				                     						reRender="pgDocumentos,tbOtrosDocumentos,mpMessage" styleClass="btnEstilos"
				                     						oncomplete="if(#{messageController.blnShowMessage}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>-->
				                     	</rich:column>
			                     	</rich:columnGroup>
			                     	
			                     	<rich:columnGroup>
			                     		<rich:column></rich:column>
			                     		<rich:column colspan="3">
				                     		<rich:dataTable id="tbOtrosDocumentos" var="item" rowKeyVar="rowKey" sortMode="single" width="400px" 
							    							value="#{socioController.beanSocioComp.persona.listaDocumento}" rows="5"
							    							rendered="#{not empty socioController.beanSocioComp.persona.listaDocumento}">
							    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">	
								                	<rich:column style="border: 1px solid #C0C0C0" width="29px">
								                        <h:outputText value="#{rowKey + 1}"></h:outputText>
								                    </rich:column>
								                    <rich:column style="border: 1px solid #C0C0C0" width="420">
								                    	<f:facet name="header">
								                            <h:outputText value="Documento de Identidad"></h:outputText>
								                        </f:facet>
								                        <h:outputText value="#{item.tabla.strDescripcion}"></h:outputText>
								                    </rich:column>
								                    <rich:column style="border: 1px solid #C0C0C0" width="420">
								                    	<f:facet name="header">
								                            <h:outputText value="Número"></h:outputText>
								                        </f:facet>
								                        <h:outputText value="#{item.strNumeroIdentidad}"></h:outputText>
								                    </rich:column>
								                </rich:columnGroup>
							                    <f:facet name="footer">   
								                    <rich:datascroller for="tbOtrosDocumentos" maxPages="5"/>   
								                </f:facet> 
							                </rich:dataTable>
				                     	</rich:column>
			                     	</rich:columnGroup>
					             </h:panelGrid>
                     		</h:panelGroup>
                     	
                     		<h:panelGroup id="divFirma" layout="block" style="float:left; width:300px; margin-left:10px">
	                     		<h:panelGrid columns="1" styleClass="tableCellBorder4">
			                     	<rich:column width="202" style="border: solid 1px silver; height:102px; padding:0px">
			                     		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty socioController.fileFirmaSocio}"
		                                    createContent="#{socioController.paintImage}" value="#{socioController.fileFirmaSocio}"
		                                    style="width:200px; height:100px;" cacheable="false">  
		                                </a4j:mediaOutput>
			                     	</rich:column>
			                     	<rich:column style="padding:0px">
			                     		<a4j:commandButton value="Firma" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
	     										actionListener="#{socioController.adjuntarFirma}" reRender="mpFileUpload"/>
			                     	</rich:column>
		                     	</h:panelGrid>
	                     	</h:panelGroup>
	                     	
	                     	<h:panelGroup id="divFoto" layout="block" style="float:left">
	                     		<h:panelGrid columns="1" styleClass="tableCellBorder4">
			                     	<rich:column width="102" style="border: solid 1px silver; height:102px; padding:0px">
			                     		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty socioController.fileFotoSocio}"
		                                    createContent="#{socioController.paintImage}" value="#{socioController.fileFotoSocio}"
		                                    style="width:100px; height:100px;" cacheable="false">  
		                                </a4j:mediaOutput>
			                     	</rich:column>
			                     	<rich:column style="padding:0px">
			                     		<a4j:commandButton value="Foto" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
	     										actionListener="#{socioController.adjuntarFoto}" reRender="mpFileUpload"/>
			                     	</rich:column>
	                     		</h:panelGrid>
	                     	</h:panelGroup>
                     	</h:panelGroup>
                     </h:panelGroup>
                     
                     <h:panelGroup layout="block" style="clear: both">
                     </h:panelGroup>
                     
                     <h:panelGroup layout="block" id="pgContacto">
                     	<h:panelGroup layout="block" id="pgListDomicilio" style="float:left; width:430px">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder4" style="margin-left:-10px">
		                    	<rich:column width="100">
		                    		<h:outputText value="Direcciones"/>
		                    	</rich:column>
		                    	<rich:column>
		                    		<a4j:commandButton id="btnSocioNatuDireccion" value="Agregar" actionListener="#{domicilioController.showDomicilioSocioNatu}"
					    					styleClass="btnEstilos1" reRender="pgFormDomicilio,frmDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')"/>
		                    	</rich:column>
		                    </h:panelGrid>
					        
					        <rich:dataTable id="tbDomicilioSocioNatu" value="#{domicilioController.beanListDirecSocioNatu}"
			    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" 
			    					rendered="#{not empty domicilioController.beanListDirecSocioNatu}"> 
			    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="350" rendered="#{!isDatosView}">
					                    <h:outputText value="#{item.strDireccion}"/>
					                </rich:column>
				                	<rich:column width="350" rendered="#{isDatosView}">
					                    <h:outputText value="#{item.strDireccion}"/>
					                    <h:outputText value=" / "/>
					                    <tumih:outputText value="#{domicilioController.listaUbigeoDepartamento}" 
		                                      itemValue="intIdUbigeo" itemLabel="strDescripcion"
		                                      property="#{item.intParaUbigeoPkDpto}"/>
					                    <h:outputText value=" - "/>
					                    <tumih:outputText value="#{domicilioController.listaUbigeoProvincia}" 
		                                      itemValue="intIdUbigeo" itemLabel="strDescripcion"
		                                      property="#{item.intParaUbigeoPkProvincia}"/>
					                    <h:outputText value=" - "/>
					                    <tumih:outputText value="#{domicilioController.listaUbigeoDistrito}" 
		                                      itemValue="intIdUbigeo" itemLabel="strDescripcion"
		                                      property="#{item.intParaUbigeoPkDistrito}"/>
					                </rich:column>
					                <rich:column>
					                	<h:commandLink value="Descargar" styleClass="buttonLink" style="margin-left:10px" target="_blank"
	     										actionListener="#{domicilioController.verCroquis}"
	     										rendered="#{item.croquis!=null}">
	     										<f:attribute name="item" value="#{item}"/>
	     								</h:commandLink>
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{domicilioController.viewDomicilioSocioNatu}" styleClass="btnEstilos1" 
					                    	reRender="pgViewFormDomicilio" oncomplete="Richfaces.showModalPanel('pViewDomicilio')">
					                    	<f:param name="rowKeyDomicilioSocioNatu" value="#{rowKey}" />
					                    </a4j:commandButton>
					                </rich:column>
					            </rich:columnGroup>
				                <f:facet name="footer">
				                    <rich:datascroller for="tbDomicilioSocioNatu" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" id="pgCuentaBancaria" style="float:left; width:430px">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder4" style="margin-left:-10px">
				    			<rich:column width="100">
				    				<h:outputText value="Cuenta Bancaria"></h:outputText>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton id="btnSocioNatuCtaBancaria" value="Agregar" 
				    								   actionListener="#{ctaBancariaController.showCtaBancariaSocioNatu}"
				    					 			   reRender="frmCtaBancaria" styleClass="btnEstilos1">
			          	 				<rich:componentControl for="mpCuentaBancaria" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		
				    		<rich:dataTable value="#{ctaBancariaController.listCtaBancariaSocioNatu}" 
				    					id="tbCuentaBancaria" rows="5"  var="item" rowKeyVar="rowKey" sortMode="single"
				    					rendered="#{not empty ctaBancariaController.listCtaBancariaSocioNatu}"> 
	                           	<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="350">
				                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion"
									      property="#{item.intBancoCod}"/> - 
					                    <h:outputText value="#{item.strNroCuentaBancaria}"/>
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{ctaBancariaController.verCtaBancariaSocioNatu}" styleClass="btnEstilos1" 
					                    				   reRender="frmCtaBancaria" oncomplete="Richfaces.showModalPanel('mpCuentaBancaria'),disableCtaBancaria()">
					                    	<f:param name="rowKeyCtaBanSocioNatu" value="#{rowKey}"></f:param>
					                    </a4j:commandButton>
					                </rich:column>
				                </rich:columnGroup>
				                <f:facet name="footer">   
				                    <rich:datascroller for="tbCuentaBancaria" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="clear: both">
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" id="pgListComunicacion" style="float:left; width:430px">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder4" style="margin-left:-10px">
		                    	<rich:column width="100">
		                    		<h:outputText value="Comunicaciones"></h:outputText>
		                    	</rich:column>
		                    	<rich:column>
		                    		<a4j:commandButton id="btnSocioNatuComunicacion" value="Agregar" actionListener="#{comunicacionController.showComuniSocioNatu}" styleClass="btnEstilos1" 
					    								reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')"></a4j:commandButton>
		                    	</rich:column>
		                    </h:panelGrid>
					        
					        <rich:dataTable id="tbComuniSocioNatu" value="#{comunicacionController.listComuniSocioNatu}" 
			    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" 
			    					rendered="#{not empty comunicacionController.listComuniSocioNatu}"> 
			    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="350">
					                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{comunicacionController.viewComuniSocioNatu}"
					                    	reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion'),disableComunicacion()" styleClass="btnEstilos1">
					                    	<f:param name="rowKeyComuniSocioNatu" value="#{rowKey}" />
					                    </a4j:commandButton>
					                </rich:column>
				                </rich:columnGroup>
				                <f:facet name="footer">   
				                    <rich:datascroller for="tbComuniSocioNatu" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" id="pgSocioEmpresa" style="float:left; width:430px">
                     		<h:panelGrid columns="2" styleClass="tableCellBorder4" style="margin-left:-10px">
				    			<rich:column width="100">
				    				<h:outputText value="Tiene Empresa"></h:outputText>
				    			</rich:column>
				    			<rich:column style="border:none">
				    				<a4j:commandButton value="Agregar" actionListener="#{perJuridicaController.cleanSocioNatuEmp}"
				    					 			   reRender="frmSocioEmpresa" styleClass="btnEstilos1">
			          	 				<rich:componentControl for="mpSocioEmpresa" operation="show" event="onclick"/>
			          	 			</a4j:commandButton>
				    			</rich:column>
				    		</h:panelGrid>
				    		
				    		<rich:dataTable value="#{perJuridicaController.listPerJuridicaSocioNatu}" 
				    					id="tbEmpresaSocio" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
				    					rendered="#{not empty perJuridicaController.listPerJuridicaSocioNatu}"> 
	                           	<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="350">
					                    <h:outputText value="#{item.juridica.strRazonSocial}"></h:outputText>
					                </rich:column>
					                <rich:column>
					                    <a4j:commandButton value="Ver" actionListener="#{perJuridicaController.verPerJuridicaSocio}" styleClass="btnEstilos1" 
					                    				   reRender="frmSocioEmpresa" oncomplete="Richfaces.showModalPanel('mpSocioEmpresa'),disableSocioEmpresa()">
					                    	<f:param name="rowKeyEmpresaSocio" value="#{rowKey}"></f:param>
					                    </a4j:commandButton>
					                </rich:column>
				                </rich:columnGroup>
				                <f:facet name="footer">   
				                    <rich:datascroller for="tbEmpresaSocio" maxPages="5"/>   
				                </f:facet> 
			                </rich:dataTable>
                     	</h:panelGroup>
                     	
                     	<h:panelGroup layout="block" style="clear: both">
                     	</h:panelGroup>
                    </h:panelGroup>
               
	         <h:panelGroup layout="block" style="margin-top:20px">
	     		<h:panelGrid>
	     			<h:outputText value="DATOS LABORALES" style="font-weight: bold; text-decoration: underline"></h:outputText>
	     		</h:panelGrid>
	     		
	     		<h:panelGroup layout="block" style="float:left">
	     			<h:panelGrid columns="4" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Centro de Trabajo"/>
		     				</rich:column>
		     				<rich:column colspan="3">
		     					<h:inputText size="48" value="#{socioController.beanSocioComp.persona.natural.perLaboral.strCentroTrabajo}"></h:inputText>
		     				</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Fecha Inicio Servicio"/>
		     				</rich:column>
		     				<rich:column>
		     					<rich:calendar value="#{socioController.beanSocioComp.persona.natural.perLaboral.dtInicioServicio}" datePattern="dd/MM/yyyy" inputStyle="width:70px"></rich:calendar>
		     				</rich:column>
		     				<rich:column>
		     					<h:outputText value="Remuneración neta"/>
		     				</rich:column>
		     				<rich:column>
		     					<h:inputText size="9" value="#{socioController.beanSocioComp.persona.natural.perLaboral.bdRemuneracion}" readonly="#{socioController.blnRemunNeta}"
		     									onblur="extractNumber(this,2,false);" 
												onkeyup="extractNumber(this,2,false);"/>
		     				</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column>
		     					<h:outputText value="Condición Laboral"/>
		     				</rich:column>
		     				<rich:column>
		     					<h:selectOneMenu value="#{socioController.beanSocioComp.persona.natural.perLaboral.intCondicionLaboral}"
		     						valueChangeListener="#{socioController.loadListDetCondicionLaboral}" disabled="#{socioController.blnCondLaboral}">
		     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		     						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								  	<a4j:support event="onchange" reRender="cboDetCondicionLaboral,divContrato" ajaxSingle="true"></a4j:support>
		     					</h:selectOneMenu>
		     				</rich:column>
		     				<rich:column colspan="2">
		     					<h:selectOneMenu id="cboDetCondicionLaboral" style="width:150px" disabled="#{socioController.blnCondLaboral}"
		     						value="#{socioController.beanSocioComp.persona.natural.perLaboral.intCondicionLaboralDet}">
		     						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
		     						<tumih:selectItems var="sel" value="#{socioController.listDetCondicionLaboral}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		     					</h:selectOneMenu>
		     				</rich:column>
	     				</rich:columnGroup>
	     			</h:panelGrid>
	     		</h:panelGroup>
	     		
	     		<h:panelGroup id="divContrato" layout="block" style="float:left">
	     			<h:outputText value="#{socioController.fileContrato.strNombrearchivo}"/>
	     			<h:panelGrid columns="6" styleClass="tableCellBorder4">
	     				<rich:columnGroup>
	     					<rich:column>
	     						<h:outputText value="Cargo"></h:outputText>
	     					</rich:column>
	     					<rich:column>
	     						<h:selectOneMenu value="#{socioController.beanSocioComp.persona.natural.perLaboral.intCargo}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CARGOPERSONAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup>
	     					<rich:column colspan="2">
	     						<h:outputText value="Régimen Laboral"/>
	     						<h:selectOneMenu style="width:150px; margin-left:10px" value="#{socioController.beanSocioComp.persona.natural.perLaboral.intRegimenLaboral}"
	     							disabled="#{socioController.blnRegimenLaboral}">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_REGIMENLABORAL}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     					<rich:column colspan="4">
	     						<h:outputText value="Solicitud ONP"/>
	     						<h:inputText style="margin-left:10px" size="10" value="#{socioController.beanSocioComp.persona.natural.perLaboral.strSolicitudONP}"/>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup rendered="#{socioController.isContratado}">
	     					<rich:column>
	     						<h:outputText value="Tipo Contrato"/>
	     					</rich:column>
	     					<rich:column>
	     						<h:selectOneMenu style="width:100px" value="#{socioController.beanSocioComp.persona.natural.perLaboral.intTipoContrato}"
	     										onchange="selecTipoContrato(#{applicationScope.Constante.ONCHANGE_VALUE})">
	     							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	     							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONTRATO}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	     						</h:selectOneMenu>
	     					</rich:column>
	     					<rich:column colspan="4">
	     						<h:outputText value="Inicio"/>
	     						<rich:calendar id="calIniContrato" datePattern="dd/MM/yyyy" inputStyle="width:70px; margin-left:10px" 
	     									value="#{socioController.beanSocioComp.persona.natural.perLaboral.dtInicioContrato}"
	     									disabled="#{socioController.blnFechaContratoDisabled}"/>
	     						<h:outputText value="Fin" style="margin-left:20px"/>
	     						<rich:calendar id="calFinContrato" datePattern="dd/MM/yyyy" inputStyle="width:70px; margin-left:10px"
	     									value="#{socioController.beanSocioComp.persona.natural.perLaboral.dtFinContrato}"
	     									disabled="#{socioController.blnFechaContratoDisabled}"/>
	     					</rich:column>
	     				</rich:columnGroup>
	     				
	     				<rich:columnGroup id="colContrato" rendered="#{socioController.isContratado}">
	     					<rich:column colspan="6">
	     						<a4j:commandButton value="Adjuntar Contrato" 
	     											oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
	     											actionListener="#{socioController.adjuntarContrato}" reRender="mpFileUpload"/>
	     						<h:inputText id="txtContrato" value="#{socioController.beanSocioComp.persona.natural.perLaboral.contrato.strNombrearchivo==null?
	     										socioController.fileContrato.strNombrearchivo:
	     										socioController.beanSocioComp.persona.natural.perLaboral.contrato.strNombrearchivo}"
	     										size="50" style="margin-left:10px" readonly="true"/>
	     						<%--<h:inputText value="#{socioController.beanSocioComp.persona.natural.perLaboral.contrato.strNombrearchivo}" 
	     									size="30" style="margin-left:10px" disabled="true"/>--%>
	     						<h:commandLink value="Ver" styleClass="buttonLink" style="margin-left:10px" target="_blank"
	     										actionListener="#{socioController.verContratoPerNatu}"
	     										rendered="#{socioController.fileContrato!=null}"/>
	     						<a4j:commandButton styleClass="btnEstilos" value="Quitar Documento" reRender="divFormSocioNatu"
			                		action="#{socioController.removeContrato}" style="width:180px;padding-left:20px;"
			                		rendered="#{socioController.fileContrato!=null}"/>
	     					</rich:column>
	     				</rich:columnGroup>
	     			</h:panelGrid>
	     		</h:panelGroup>
	     		
	     		<h:panelGroup layout="block" style="clear:both">
                </h:panelGroup>
	         </h:panelGroup>
	         
	         <h:panelGroup id="divSocioNatuDepen" layout="block" style="margin-top:20px">
	         	<h:panelGrid id="pgTestUniEje" styleClass="tableCellBorder4">
	     			<h:outputText value="Unidad Ejecutora" style="font-weight:bold"/>
	     		</h:panelGrid>
	     		
	         	<h:panelGrid id="pgSocioNatuDepen" columns="8" styleClass="tableCellBorder4">
	         		<rich:columnGroup>
	         			<rich:column width="95">
	         				<h:outputText value="Sucursal usuario"></h:outputText>
	         			</rich:column>
	         			<rich:column width="210">
	         				<tumih:inputText value="#{socioEstrucController.listSucursal}" style="width:200px"
									itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
									property="#{socioController.socioEstructura.intIdSucursalUsuario}"/>
	         			</rich:column>
	         			<rich:column width="110">
	         				<h:outputText value="Sucursal administra"></h:outputText>
	         			</rich:column>
	         			<rich:column colspan="3" width="235">
	         				<tumih:inputText value="#{socioEstrucController.listSucursal}" style="width:225px"
									itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
									property="#{socioController.socioEstructura.intIdSucursalAdministra}"/>
	         			</rich:column>
	         			<rich:column>
	         				<h:outputText value="Modalidad"></h:outputText>
	         			</rich:column>
	         			<rich:column>
							<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{socioController.socioEstructura.intModalidad}"/>
	         			</rich:column>
	         		</rich:columnGroup>
	         		
	         		<rich:columnGroup>
	         			<rich:column>
	         				<h:outputText value="Unidad Ejecutora"></h:outputText>
	         			</rich:column>
	         			<rich:column>
	         				<tumih:inputText value="#{socioEstrucController.listEstructura}" style="width:200px"
									itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
									property="#{socioController.socioEstructura.intCodigo}"/>
	         			</rich:column>
	         			<rich:column>
	         				<h:outputText value="Código Planilla"></h:outputText>
	         			</rich:column>
	         			<rich:column>
	         				<h:inputText size="10" value="#{socioController.socioEstructura.strCodigoPlanilla}"></h:inputText>
	         			</rich:column>
	         			<rich:column>
	         				<h:outputText value="Tipo"></h:outputText>
	         			</rich:column>
	         			<rich:column>
	         				<h:selectOneMenu style="width:100px" value="#{socioController.socioEstructura.intTipoEstructura}">
               					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA}" 
					  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
               				</h:selectOneMenu> 
	         			</rich:column>
	         			<rich:column>
	         				<a4j:commandButton value="Buscar" styleClass="btnEstilos" 
	         					actionListener="#{socioEstrucController.addEntidadSocio}"
	         					oncomplete="Richfaces.showModalPanel('mpSocioEstructura')"
	         					reRender="mpSocioEstructura,frmSocioEstructura"></a4j:commandButton>
	         			</rich:column>
	         			<rich:column>
	         				<a4j:commandButton value="Agregar" styleClass="btnEstilos"
	         					actionListener="#{socioController.addListEntidadSocio}"
	         					reRender="pgSocioNatuDepen,tblSocioEstructura,mpMessage"
	         					oncomplete="if(#{socioController.isValidSocioEstructura==false}){Richfaces.showModalPanel('mpMessage')}"></a4j:commandButton>
	         			</rich:column>
	         		</rich:columnGroup>
	         	</h:panelGrid>
	         	
	         	<h:panelGroup layout="block" style="width:920px; overflow-x:auto !important; margin:0 auto">
	         		<rich:extendedDataTable id="tblSocioEstructura" enableContextMenu="false" sortMode="single" 
			                    var="item" value="#{socioController.beanSocioComp.socio.listSocioEstructura}" style="margin:0 auto"  
			  		 			rowKeyVar="rowKey" rows="5" width="1030px" height="165px">
			                           
			  		   	<rich:column width="29px">
		                   <h:outputText value="#{rowKey + 1}"></h:outputText>
		               	</rich:column>
		
		               	<rich:column width="150">
		                   <f:facet name="header">
		                       <h:outputText  value="Sucursal Usuario"></h:outputText>
		                   </f:facet>
		                   <tumih:outputText value="#{socioEstrucController.listSucursal}" 
								itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
								property="#{item.intIdSucursalUsuario}"/>
		               	</rich:column>
		
		               	<rich:column width="150">
		                   <f:facet name="header">
		                       <h:outputText value="Sucursal Administra"></h:outputText>
		                   </f:facet>
		                   <tumih:outputText value="#{socioEstrucController.listSucursal}" 
								itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
								property="#{item.intIdSucursalAdministra}"/>
		               	</rich:column>
						
						<rich:column>
		                  	<f:facet name="header">
		                      <h:outputText value="Tipo Socio"></h:outputText>
		                  	</f:facet>
		                  	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intTipoSocio}"/>
		              	</rich:column>
		               	<rich:column>
		                  	<f:facet name="header">
		                      <h:outputText value="Modalidad"></h:outputText>
		                  	</f:facet>
		                  	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intModalidad}"/>
		              	</rich:column>
		              	<rich:column width="200">
			                  <f:facet name="header">
			                      <h:outputText value="U. Ejecutora"></h:outputText>
			                  </f:facet>
			                  <tumih:outputText value="#{socioEstrucController.listEstructura}" 
									itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
									property="#{item.intCodigo}"/>
			             </rich:column>
			             <rich:column>
			                  <f:facet name="header">
			                      <h:outputText value="Tipo"></h:outputText>
			                  </f:facet>
							  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTRUCTURA}"
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intTipoEstructura}"/>
			             </rich:column>
			             <rich:column>
			                  <f:facet name="header">
			                      <h:outputText value="Cód. Descto"></h:outputText>
			                  </f:facet>
			                  <h:outputText value="#{item.strCodigoPlanilla}"></h:outputText>
			             </rich:column>
			             <rich:column width="100">
			                  <f:facet name="header">
			                      <h:outputText value="Fecha y Hora"></h:outputText>
			                  </f:facet>
			                  <h:outputText value="#{item.dtFechaRegistro}">
						      		<f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Bogota"/>
						      </h:outputText>
			             </rich:column>
						 <f:facet name="footer">   
			        		<rich:datascroller for="tblSocioEstructura" maxPages="10"/>   
			        	 </f:facet>
			       	</rich:extendedDataTable>
	         	</h:panelGroup>
	         	
	         </h:panelGroup>