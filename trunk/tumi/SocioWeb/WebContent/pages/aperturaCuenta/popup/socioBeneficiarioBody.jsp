<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Christian De los Ríos 			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 02/07/2012               		-->

	<h:form id="frmBeneficiario" onkeypress="ifEnterClick(event, document.getElementById('frmBeneficiario:btnValidar'));">
    	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 860px;">
    		<h:panelGrid columns="3">
    			<rich:column  width="120px"><h:outputText value="Nombre de Socio : "/></rich:column>
    			<rich:column style="padding-left:10px;">
    				<h:inputText value="#{aperturaCuentaController.beanSocioComp.persona.intIdPersona} - #{aperturaCuentaController.beanSocioComp.persona.natural.strApellidoPaterno} #{aperturaCuentaController.beanSocioComp.persona.natural.strApellidoMaterno} #{aperturaCuentaController.beanSocioComp.persona.natural.strNombres}" size="60" disabled="true"/>
				</rich:column>
				<rich:column style="padding-left:10px;">
			    	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{aperturaCuentaController.beanSocioComp.persona.documento.intTipoIdentidadCod}"/>: 
    				<h:inputText value="#{aperturaCuentaController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="15" disabled="true"/>
				</rich:column>
    		</h:panelGrid>
    		
    		<h:panelGrid columns="3">
    			<rich:column>
    				<h:panelGroup rendered="#{beneficiarioController.strBeneficiario == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
    					<a4j:commandButton value="Grabar" actionListener="#{beneficiarioController.grabarBeneficiario}" reRender="pgListBeneficiario,pgErrors" 
    						oncomplete="if(#{beneficiarioController.validBeneficiario}){Richfaces.hideModalPanel('mpBeneficiario')}" styleClass="btnEstilos"/>
    				</h:panelGroup>
    				<h:panelGroup rendered="#{beneficiarioController.strBeneficiario == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
    					<a4j:commandButton value="Grabar" actionListener="#{beneficiarioController.modificarBeneficiario}" reRender="pgListBeneficiario,pgErrors" 
    						oncomplete="if(#{beneficiarioController.validBeneficiario}){Richfaces.hideModalPanel('mpBeneficiario')}" styleClass="btnEstilos"/>
    				</h:panelGroup>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton value="Cancelar" actionListener="#{beneficiarioController.cancelarBeneficiario}" reRender="pBeneficiario" 
    					disabled="#{beneficiarioController.blnCancelar}" styleClass="btnEstilos"/>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton value="Cerrar" reRender="pgListBeneficiario,pgErrors" 
    						oncomplete="Richfaces.hideModalPanel('mpBeneficiario')" styleClass="btnEstilos"/>
    			</rich:column>
    		</h:panelGrid>
    		
    		<h:panelGrid id="pBeneficiario">
	        	<rich:panel style="width: 880px;border:1px solid #17356f;background-color:#DEEBF5;">
	        	<h:panelGroup layout="block" rendered="#{beneficiarioController.blnShowFiltroPersonaNatu}">
	        		<h:panelGrid columns="5">
	        			<%--
	        			<rich:column  width="100">
	        				<h:outputText value="Tipo de Vínculo: "/>
	        			</rich:column>
	        			<rich:column>
	        				<h:selectOneMenu id="cboTipoVinculo" value="#{beneficiarioController.intTipoVinculo}">
	        					<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
	        					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
			                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
			                       propertySort="intOrden"/>
	        				</h:selectOneMenu>
	        			</rich:column> --%>
	        			<rich:column width="110" style="padding-left:10px;">
	        				<h:outputText value="Tipo de Persona: "/>
	        			</rich:column>
	        			<rich:column>
	        				<h:selectOneMenu id="cboTipoPersona" value="#{beneficiarioController.personaBusqueda.intTipoPersonaCod}">
	        					<f:selectItem itemLabel="Natural" itemValue="1"/>
	        				</h:selectOneMenu>
	        			</rich:column>
	        			
	        			<rich:column width="150" style="padding-left:10px;">
	        				<h:outputText value="Número de Documento: "/>
	        			</rich:column>
	        			<rich:column>
	        				<h:selectOneMenu id="cboTipoDoc" value="#{beneficiarioController.personaBusqueda.documento.intTipoIdentidadCod}">
	        					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
			                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
			                       propertySort="intOrden"/>
	        				</h:selectOneMenu>
	        			</rich:column>
	        			<rich:column style=" padding-left:10px;">
	        				<h:inputText size="11" value="#{beneficiarioController.personaBusqueda.documento.strNumeroIdentidad}" onkeydown="return validarEnteros(event);" maxlength="11"/>
	        			</rich:column>
	        		</h:panelGrid>
	        		
	        		<h:panelGrid style="width: 100%">
	        			<a4j:commandButton id="btnValidar" value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
	        				actionListener="#{beneficiarioController.validarBeneficiario}" reRender="pBeneficiario,mpConfirmMessage,pgNroDocumento"
	        				oncomplete="if(#{beneficiarioController.beanSocioComp.persona==null}){Richfaces.showModalPanel('mpConfirmMessage')}
                     		else if(#{beneficiarioController.beanSocioComp.persona.intIdPersona==null}){Richfaces.showModalPanel('mpMessage')}"/>
	        		</h:panelGrid>
	        		
	        		<h:outputText value="#{beneficiarioController.msgTipoVinculo}" styleClass="msgError"/>
	        		<h:outputText value="#{beneficiarioController.msgTipoDoc}" styleClass="msgError"/>
	        		<h:outputText value="#{beneficiarioController.msgNroDoc}" styleClass="msgError"/>
	        		<rich:spacer height="4px"/><rich:spacer height="4px"/>
	        		<rich:separator height="5px"/>
	        		<rich:spacer height="8px"/>
	         	</h:panelGroup>
	         	
	         	<h:panelGrid id="pgErrors">
	         		<h:outputText value="#{beneficiarioController.msgTxtPorcentajeBeneficio}" styleClass="msgError"/>
	         		<h:outputText value="#{beneficiarioController.msgTxtBeneficiarioInactivo}" styleClass="msgError"/>
	         		<h:outputText value="#{beneficiarioController.msgTxtVinculoPersona}" styleClass="msgError"/>
	         	</h:panelGrid>
	         		
         		<rich:panel rendered="#{beneficiarioController.blnShowDivFormNatural}" style="width: 860px;border:1px solid #17356f;background-color:#DEEBF5;">
		         	<h:panelGrid>
		         		<h:outputText value="DATOS PERSONALES" style="font-weight:bold;text-decoration:underline;"/>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid columns="6">
		         		<rich:column >
		         			<h:outputText value="Ap. Paterno:"/>
		         		</rich:column>
		         		<rich:column>
		         			<h:inputText value="#{beneficiarioController.beanSocioComp.persona.natural.strApellidoPaterno}" size="30"/>
		         		</rich:column>
		         		<rich:column>
		         			<h:outputText value="Ap. Materno: "/>
		         		</rich:column>
		         		<rich:column>
		         			<h:inputText value="#{beneficiarioController.beanSocioComp.persona.natural.strApellidoMaterno}" size="30"/>
		         		</rich:column>
		         		<rich:column>
		         			<h:outputText value="Nombres: "/>
		         		</rich:column>
		         		<rich:column>
		         			<h:inputText value="#{beneficiarioController.beanSocioComp.persona.natural.strNombres}" size="40"/>
		         		</rich:column>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid id="pgImages" columns="2">
		         		<rich:column>
		         			<h:panelGrid columns="3">
				          		<rich:column  width="110px;">
				          			<h:outputText value="Fec. de Nacimiento"/>
				          		</rich:column>
				          		<rich:column >
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style=" padding-left:10px;">
				          			<rich:calendar popup="true" enableManualInput="true"
				          			value="#{beneficiarioController.beanSocioComp.persona.natural.dtFechaNacimiento}"
				                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
				                    cellWidth="10px" cellHeight="20px"/>
				          		</rich:column>
			          		</h:panelGrid>
		          		
		          			<h:panelGrid columns="3">
				          		<rich:column width="70px;">
				          			<h:outputText value="Sexo"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:selectOneRadio value="#{beneficiarioController.beanSocioComp.persona.natural.intSexoCod}">
				          				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSEXO}" 
					                     itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
					                     propertySort="intOrden"/>
				          			</h:selectOneRadio>
				          		</rich:column>
			          		</h:panelGrid>
			          		
			          		<h:panelGrid columns="3">
				          		<rich:column width="110px;">
				          			<h:outputText value="Estado Civil"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:selectOneMenu value="#{beneficiarioController.beanSocioComp.persona.natural.intEstadoCivilCod}">
				          				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
				          				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				          			</h:selectOneMenu>
				          		</rich:column>
			          		</h:panelGrid>
			          	
				          	<h:panelGrid columns="3">
				          		<rich:column width="130px;">
				          			<h:outputText value="Tipo de Persona"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:selectOneMenu value="#{beneficiarioController.beanSocioComp.persona.intTipoPersonaCod}">
				          				<f:selectItem itemValue="1" itemLabel="Natural"/>
				          			</h:selectOneMenu>
				          			<%--
				          			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
                                            itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                            property="#{beneficiarioController.beanSocioComp.persona.intTipoPersonaCod}"/>
                                            --%>
				          		</rich:column>
				          	</h:panelGrid>
			          	
				          	<h:panelGrid columns="3">
				          		<rich:column width="130px;">
				          			<h:outputText value="Tipo de Vínculo"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:selectOneMenu id="cboTipoVinculo" value="#{beneficiarioController.beanSocioComp.persona.personaEmpresa.vinculo.intTipoVinculoCod}">
	 	                     			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								  		<tumih:selectItems var="sel" value="#{beneficiarioController.listTipoVinculo}" 
						  					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						  					propertySort="intOrden"/>
						  			</h:selectOneMenu>
				          		</rich:column>
				          	</h:panelGrid>
			          		
				          	<h:panelGrid columns="3">
				          		<rich:column width="130px;">
				          			<h:outputText value="Roles Actuales"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:inputText value="#{beneficiarioController.beanSocioComp.strRoles}" disabled="true" size="20"/>
				          		</rich:column>
				          	</h:panelGrid>
			          	
				          	<h:panelGrid id="pgNroDocumento" columns="3">
				          		<rich:column width="130px;">
				          			<h:outputText value="Número Documento"/>
				          		</rich:column>
				          		<rich:column>
				          			<h:outputText value=":"/>
				          		</rich:column>
				          		<rich:column style="padding-left:10px;">
				          			<h:inputText value="#{beneficiarioController.beanSocioComp.persona.documento.strNumeroIdentidad}" onkeydown="return validarNumDocIdentidad(this,event,8)" 
				          				disabled="#{beneficiarioController.blnNroDocumento}" size="20"/>
				          		</rich:column>
				          	</h:panelGrid>
		         		</rich:column>
		         		
		         		<rich:column>
		         			<h:panelGroup id="divFirmaBeneficiario" layout="block" style="float:left; width:300px; margin-left:88px">
	                     		<h:panelGrid columns="1" styleClass="tableCellBorder4">
			                     	<rich:column width="202" style="border: solid 1px silver; height:102px; padding:0px">
			                     		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty beneficiarioController.fileFirmaSocio}"
		                                    createContent="#{beneficiarioController.paintImage}" value="#{beneficiarioController.fileFirmaSocio}"
		                                    style="width:200px; height:100px;" cacheable="false">  
		                                </a4j:mediaOutput>
			                     	</rich:column>
			                     	<%--<rich:column style="padding:0px">
			                     		<a4j:commandButton value="Firma" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
	     										actionListener="#{beneficiarioController.adjuntarFirma}" reRender="mpFileUpload"/>
			                     	</rich:column>--%>
		                     	</h:panelGrid>
	                     	</h:panelGroup>
                     		
	                     	<h:panelGroup id="divFotoBeneficiario" layout="block" style="float:left">
	                     		<h:panelGrid columns="1" styleClass="tableCellBorder4">
			                     	<rich:column width="102" style="border: solid 1px silver; height:102px; padding:0px">
			                     		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty beneficiarioController.fileFotoSocio}"
		                                    createContent="#{beneficiarioController.paintImage}" value="#{beneficiarioController.fileFotoSocio}"
		                                    style="width:100px; height:100px;" cacheable="false">  
		                                </a4j:mediaOutput>
			                     	</rich:column>
			                     	<%--<rich:column style="padding:0px">
			                     		<a4j:commandButton value="Foto" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
	     										actionListener="#{beneficiarioController.adjuntarFoto}" reRender="mpFileUpload"></a4j:commandButton>
			                     	</rich:column>--%>
	                     		</h:panelGrid>
	                     	</h:panelGroup>
		         		</rich:column>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid columns="5">
		         		<rich:column   width="130px;">
		         			<h:outputText value="Otros Documentos"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value=":"/>
		         		</rich:column>
		         		<rich:column style="padding-left:10px;">
		         			<h:selectOneMenu value="#{beneficiarioController.beanSocioComp.intTipoDocIdentidad}"
	                     			onchange="selecTipoDocIdentidad(#{applicationScope.Constante.ONCHANGE_VALUE})">
	                     			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						  		<tumih:selectItems var="sel" value="#{beneficiarioController.listDocumento}" 
				  					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                     		</h:selectOneMenu>  
		         		</rich:column>
		         		<rich:column style="padding-left:10px;">
		         			<h:inputText id="txtOtroDocumento" size="20" value="#{beneficiarioController.beanSocioComp.strDocIdentidad}"
			                     			onkeydown="return validarNumDocIdentidad(this,event,#{beneficiarioController.intDocIdentidadMaxLength})"/>
		         		</rich:column>
		         		<rich:column >
		         			<a4j:commandButton value="Agregar" actionListener="#{beneficiarioController.addOtroDocumento}"
          						reRender="pgOtrosDocumentos,mpMessage" styleClass="btnEstilos"
          						oncomplete="if(#{messageController.blnShowMessage}){Richfaces.showModalPanel('mpMessage')}"/>
		         		</rich:column>
		         	</h:panelGrid>
		         	
		         	<h:panelGrid id="pgOtrosDocumentos">
			         	<rich:dataTable var="item" rowKeyVar="rowKey" sortMode="single" width="400px" 
										value="#{beneficiarioController.beanSocioComp.persona.listaDocumento}" rows="5"
										rendered="#{not empty beneficiarioController.beanSocioComp.persona.listaDocumento}">
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
	         		</h:panelGrid>
		         	<h:panelGrid id="pgAportes" columns="6">
		         		<rich:column  width="130px;">
		         			<h:outputText value="Tipo de Beneficio:"/>
		         		</rich:column>
		         		<rich:column  width="250">
		         			<h:outputText value="Aportes"/>
		         		</rich:column>
		         		<rich:column   width="150">
		         			<h:outputText value="Porcentaje de Beneficio"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value=":"/>
		         		</rich:column>
		         		<rich:column style="padding-left:10px;">
		         			<h:inputText value="#{beneficiarioController.bdPorcAportes}" disabled="#{aperturaCuentaController.intValorAportacion==null}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value="%"/>
		         		</rich:column>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid id="pgFondoSepelio" columns="6">
		         		<rich:column  width="130px;">
		         			<h:outputText />
		         		</rich:column>
		         		<rich:column  width="250">
		         			<h:outputText value="Fondo de Sepelio"/>
		         		</rich:column>
		         		<rich:column  width="150">
		         			<h:outputText value="Porcentaje de Beneficio"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value=":"/>
		         		</rich:column>
		         		<rich:column style="padding-left:10px;">
		         			<h:inputText value="#{beneficiarioController.bdPorcFondoSepelio}" disabled="#{aperturaCuentaController.intValorFondoSepelio==null}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value="%"/>
		         		</rich:column>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid id="pgFondoRetiro" columns="6">
		         		<rich:column width="130px;">
		         			<h:outputText />
		         		</rich:column>
		         		<rich:column  width="250">
		         			<h:outputText value="Fondo de Retiro"/>
		         		</rich:column>
		         		<rich:column  width="150">
		         			<h:outputText value="Porcentaje de Beneficio"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value=":"/>
		         		</rich:column>
		         		<rich:column style="padding-left:10px;">
		         			<h:inputText value="#{beneficiarioController.bdPorcFondoRetiro}" disabled="#{aperturaCuentaController.intValorFondoRetiro==null}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
		         		</rich:column>
		         		<rich:column >
		         			<h:outputText value="%"/>
		         		</rich:column>
		         	</h:panelGrid>
	         		
		         	<h:panelGroup layout="block" id="pgCuentaBancaria" style="width:430px">
                    		<h:panelGrid columns="3">
			    			<rich:column width="120px">
			    				<h:outputText value="Cuenta Bancaria"></h:outputText>
			    			</rich:column>
			    			<rich:column>
			    				<a4j:commandButton id="btnSocioNatuCtaBancaria" value="Agregar" 
			    								   actionListener="#{ctaBancariaController.showCtaBancariaSocioNatu}"
			    					 			   reRender="frmCtaBancaria" styleClass="btnEstilos">
		          	 				<rich:componentControl for="mpCuentaBancaria" operation="show" event="onclick"/>
		          	 			</a4j:commandButton>
			    			</rich:column>
			    		</h:panelGrid>
			    		
			    		<rich:dataTable value="#{ctaBancariaController.listCtaBancariaSocioNatu}" 
			    					id="tbCuentaBancaria" rows="5"  var="item" rowKeyVar="rowKey" sortMode="single"
			    					rendered="#{not empty ctaBancariaController.listCtaBancariaSocioNatu}"> 
                           	<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
			                	<rich:column width="350">
				                    <h:outputText value="#{item.strNroCuentaBancaria}"></h:outputText>
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
	         		
		         	<h:panelGrid id="pDomicilio" columns="3">
		         		<rich:column width="120px">
		         			<h:outputText value="Direcciones"/>
		         		</rich:column>
		         		<rich:column >
		                  	<a4j:commandButton id="btnBeneficiarioDireccion" value="Agregar" actionListener="#{domicilioController.showDomicilioBeneficiario}"
			    					styleClass="btnEstilos" reRender="pgFormDomicilio,frmDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio')">
		          	 		</a4j:commandButton>
		         		</rich:column>
		         	</h:panelGrid>
		         	<h:inputHidden id="tbDomicilioRepLegal"/>
		         	
		         	<h:panelGrid id="pgListDomicilio">
		         		<rich:dataTable id="tbDomicilioBeneficiario" value="#{domicilioController.listDirecBeneficiario}"
		    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" 
		    					rendered="#{not empty domicilioController.listDirecBeneficiario}"> 
		    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
			                	<rich:column width="350">
				                    <h:outputText value="#{item.strDireccion}"/>
				                </rich:column>
				                <rich:column>
				                    <a4j:commandButton value="Ver" actionListener="#{domicilioController.viewDomicilioBeneficiario}" styleClass="btnEstilos1" 
				                    	reRender="pgFormDomicilio" oncomplete="Richfaces.showModalPanel('pAgregarDomicilio'),disableDomicilio()">
				                    	<f:param name="rowKeyDomicilioBeneficiario" value="#{rowKey}" />
				                    </a4j:commandButton>
				                </rich:column>
				            </rich:columnGroup>
			                <f:facet name="footer">   
			                    <rich:datascroller for="tbDomicilioBeneficiario" maxPages="5"/>   
			                </f:facet> 
		                </rich:dataTable>
		         	</h:panelGrid>
	         		
		         	<h:panelGrid id="pComunicacion" columns="2">
		         		<rich:column width="120px">
		         			<h:outputText value="Comunicaciones"/>
		         		</rich:column>
		         		<rich:column>
		                  	<a4j:commandButton id="btnSocioNatuComunicacion" value="Agregar" actionListener="#{comunicacionController.showComuniBeneficiario}" styleClass="btnEstilos" 
				    						reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion')"/>
		         		</rich:column>
		         	</h:panelGrid>
		         	
		         	<h:panelGrid id="pgListComunicacion">
		         		<rich:dataTable id="tbComuniSocioNatu" value="#{comunicacionController.listComuniBeneficiario}" 
		    					rows="5" var="item" rowKeyVar="rowKey" sortMode="single" 
		    					rendered="#{not empty comunicacionController.listComuniBeneficiario}"> 
		    				<rich:columnGroup rendered="#{item.intEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
			                	<rich:column width="350">
				                    <h:outputText value="#{item.strComunicacion}"></h:outputText>
				                </rich:column>
				                <rich:column>
				                    <a4j:commandButton value="Ver" actionListener="#{comunicacionController.viewComuniBeneficiario}"
				                    	reRender="frmComunicacion" oncomplete="Richfaces.showModalPanel('pAgregarComunicacion'),disableComunicacion()" styleClass="btnEstilos1">
				                    	<f:param name="rowKeyComuniBeneficiario" value="#{rowKey}" />
				                    </a4j:commandButton>
				                </rich:column>
			                </rich:columnGroup>
			                <f:facet name="footer">   
			                    <rich:datascroller for="tbComuniSocioNatu" maxPages="5"/>   
			                </f:facet> 
		                </rich:dataTable>
		         	</h:panelGrid>
		        </rich:panel>
	         </rich:panel>
       	 </h:panelGrid>
         </rich:panel>
    </h:form>