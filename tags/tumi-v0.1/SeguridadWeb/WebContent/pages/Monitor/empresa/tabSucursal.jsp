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
	     <rich:panel style="border:1px solid #17356f; width:765px; background-color:#DEEBF5">                               
	         <h:panelGrid id="pgbusqSucursal" columns="6" style="width: 550px" border="0">
	           <h:outputText value="Empresa:" style="padding-left:10px;"/>
	           <h:selectOneMenu style="width: 300px;"
					valueChangeListener="#{empresaController.reloadCboSucursales}"
					value="#{empresaController.intCboEmpresaSuc}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
					<a4j:support event="onchange" reRender="idCboSucursalEmp" ajaxSingle="true"/>
				</h:selectOneMenu>
	           <h:outputText value="Tipo:" ></h:outputText>
			   <h:selectOneMenu id="idCboTipoSucursal" value="#{empresaController.cboTipoSucursal}">
			   		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
			   		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			   </h:selectOneMenu>
			    
			    <h:outputText value="Estado:"/>
		        <h:selectOneMenu value="#{empresaController.cboEstadoSucursal}">
		        		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
                       tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
                </h:selectOneMenu>
			    
			    <h:outputText id="lblbusqSucursal" value="Sucursal:"  style="padding-left: 15px;"/>
	            <h:selectOneMenu id="idCboSucursalEmp" style="width: 300px;" value="#{empresaController.intCboSucursalEmp}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaSucursal}"
								itemValue="#{sel.intPersPersonaPk}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				</h:selectOneMenu>
				
				<h:outputText value="Terceros:"/>
	 			<h:selectBooleanCheckbox value="#{empresaController.chkTerceroFiltro}"/>
				<h:outputText value="Sub Sucursales:"/>
	 			<h:selectBooleanCheckbox value="#{empresaController.chkSubsucursalFiltro}"/>
	           	<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener= "#{empresaController.listarSucursales}" reRender="pgbusqSucursal1"/>
	         </h:panelGrid>
	         <br/>
	         <div style="width:100%; overflow-x:auto; overflow-y:hidden" align="center">
			        <h:panelGrid id="pgbusqSucursal1" columns="1" border="0">
			           <rich:extendedDataTable id="tblbusqSucursal" value="#{empresaController.listaSucursalComp}" var="item" sortMode="single"
						  		 onRowClick="jsSeleccionSucursal(#{item.sucursal.juridica.intIdPersona});"
						  		 rowKeyVar="rowKey" rendered="#{not empty empresaController.listaSucursalComp}"
						  		 width="668px" height="195px" rows="5">
			              <rich:column width="29">
			              	 <h:outputText id="lblValRowKey" value="#{rowKey+1}"/>
			              </rich:column>
			              <rich:column width="200">
			                 <f:facet name="header">
			                    <h:outputText id="lblNomSucursal" value="Nombre de Sucursal"/>
			                 </f:facet>
			              	 <h:outputText id="lblValNomSucursal" value="#{item.sucursal.juridica.strRazonSocial}"/>
			              	 <rich:toolTip for="lblValNomSucursal" value="#{item.sucursal.juridica.strRazonSocial}"/>		
			              </rich:column>
			              <rich:column>
			                 <f:facet name="header">
			                    <h:outputText id="lblTipo" value="Tipo"/>
			                 </f:facet>
			                 <h:selectOneMenu id="idCboTipoSucursal" value="#{item.sucursal.intIdTipoSucursal}" disabled="true">
			                 	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						   	 	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							 		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						   	 </h:selectOneMenu>
			                 <%--<h:outputText id="lblValTipo" value="#{item.sucursal.intIdTipoSucursal}"/>--%>
			              </rich:column>
			              <rich:column width="120">
			                 <f:facet name="header">
			                    <h:outputText value="Empresa"/>
			                 </f:facet>
			                 <h:outputText value="#{item.empresa.juridica.strSiglas}"/>
			              </rich:column>
			              <rich:column>
			                 <f:facet name="header">
			                    <h:outputText id="lblAbreviatura" value="Abreviatura"/>
			                 </f:facet>
			              	 <h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
			              </rich:column>               
			              <rich:column width="120">
			                 <f:facet name="header">
			                    <h:outputText value="Sub Sucursales"/>
			                 </f:facet>
			              	 <h:outputText value="#{item.intCantidadSubSucursal}"/>
			              </rich:column>
			              
			              <f:facet name="footer">
			              	<rich:datascroller for="tblbusqSucursal" maxPages="10" />
			              </f:facet>
					   </rich:extendedDataTable>
					   
					   <%--<rich:datascroller id="tblbusqDireccion" for="tblbusqDireccion" maxPages="15"></rich:datascroller>--%>                             
			           <h:panelGrid columns="2">
			           		<h:outputLink value="#" id="linkPanelSuc">
							     <h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
							     <rich:componentControl for="panelUpdateDeleteSuc" attachTo="linkPanelSuc" operation="show" event="onclick"/>
							</h:outputLink>
							<h:outputText value=" Para Eliminar o Modificar una Sucursal Hacer click en el Registro" style="color:#8ca0bd" ></h:outputText>                                     
					   </h:panelGrid>
					   
			      </h:panelGrid>
		     </div>
		     
	         <div>
	            <h:panelGrid columns="3">
	               <h:commandButton id="btnNuevo2" value="Nuevo" actionListener="#{empresaController.habilitarGrabarSucursal}" styleClass="btnEstilos"/>
	               <h:panelGroup rendered="#{empresaController.strTipoMantSucursal == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	               		<h:commandButton id="btnGrabar2" value="Grabar" actionListener="#{empresaController.grabarSucursal}" styleClass="btnEstilos"/>
	               </h:panelGroup>
	               <h:panelGroup rendered="#{empresaController.strTipoMantSucursal == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<h:commandButton value="Guardar" actionListener="#{empresaController.modificarSucursal}" styleClass="btnEstilos"/>
		           </h:panelGroup>
	               <h:commandButton value="Cancelar" actionListener="#{empresaController.cancelarGrabarSucursal}" styleClass="btnEstilos"/>
	            </h:panelGrid>
	         </div>
	         <rich:spacer height="4px"/><rich:spacer height="4px"/>
	         <rich:panel id="pSucursal" style="width: 740px;background-color:#DEEBF5;border: 1px solid #17356f;"
	         			rendered="#{empresaController.sucursalRendered}">
	            <f:facet name="header">
	              <h:outputText id="lblEncabezado" value="Sucursales" ></h:outputText>
	            </f:facet>
	            <h:panelGrid columns="3" border="0" >
	               <rich:column style="width: 150px; padding-left: 15px" >
	                  <h:outputText id="lblSucursal1" value="Empresa"  > </h:outputText>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":"/>
	               </rich:column>
	               <h:selectOneMenu id="intIdempresa" value="#{empresaController.beanSucursal.id.intPersEmpresaPk}" style="width:300px;">
	               		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
	               </h:selectOneMenu>
	            </h:panelGrid>
	            <h:panelGrid columns="5" style="width: 650px;" border="0" >
	               <rich:column style="width: 150px; padding-left:15px">
	                  <h:outputText id="lblTipoSucursal" value="Tipo de Sucursal"/>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":" />
	               </rich:column>
	               <rich:column>
	               	  <h:selectOneMenu id="cboTipoSucursal" value="#{empresaController.beanSucursal.intIdTipoSucursal}">
					  	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					   	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					  </h:selectOneMenu>
	               </rich:column>
	               <rich:column>
	                  <h:outputText id="lblEstSucursal" value="Estado : "/>
	               </rich:column>
	               <rich:column>
	               		<h:panelGroup rendered="#{empresaController.beanSucursal.id.intIdSucursal != null}">
						    <h:selectOneMenu value="#{empresaController.beanSucursal.intIdEstado}">
			                       <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
			                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
			                       tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
			                </h:selectOneMenu>
					    </h:panelGroup>
					    <h:panelGroup rendered="#{empresaController.beanSucursal.id.intIdSucursal == null}">
						    <h:selectOneMenu value="#{empresaController.beanSucursal.intIdEstado}">
						   		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>   
	               </rich:column>
	            </h:panelGrid>
	            <h:panelGrid columns="3" border="0" >
	               <rich:column style="width: 150px;">
	                  <h:outputText id="lblCodSucursal" value="Código" style="padding-left:10px"/>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":"/>
	               </rich:column>
	               <rich:column>
	                  <h:inputText id="txtCodSucursal" size="10" value="#{empresaController.beanSucursal.id.intIdSucursal}" disabled="true"/>
	               </rich:column>
	            </h:panelGrid>
	            <h:panelGrid columns="4">
	               <rich:column style="width: 150px;">
	                  <h:outputText value="Nombre" style="padding-left: 10px"/>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":"/>
	               </rich:column>
	               <rich:column style="width: 90px;">
	                  <h:inputText id="txtNomSucursal" size="50" value="#{empresaController.beanSucursal.juridica.strRazonSocial}"/>
	               </rich:column>
	               <rich:column>
			          <h:outputText value="#{empresaController.msgTxtSucursal}" styleClass="msgError"/>
			       </rich:column>
	            </h:panelGrid>
	            <h:panelGrid columns="4" border="0" >
	               <rich:column style="width: 150px;">
	                  <h:outputText id="lblAbvSucursal" value="Abreviatura" style="padding-left: 10px"/>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":"/>
	               </rich:column>
	               <rich:column style="width: 70px;">
	                  <h:inputText id="txtAbvSucursal" size="30" value="#{empresaController.beanSucursal.juridica.strSiglas}"></h:inputText>
	               </rich:column>
	               <rich:column>
			          <h:outputText value="#{empresaController.msgTxtAbreviatura}" styleClass="msgError"/>
			       </rich:column>
	            </h:panelGrid>
	            <h:panelGrid columns="3" border="0">
	               <rich:column style="width: 150px;">
	                  <h:outputText id="lblDireccion" value="Dirección" style="padding-left: 10px"/>
	               </rich:column>
	               <rich:column>
	                  <h:outputText value=":"/>
	               </rich:column>
	               <rich:column style="border:0px;">
	               		<a4j:commandButton value="Agregar" oncomplete="#{rich:component('pAgregarDomicilio')}.show()"
          	 				actionListener="#{domicilioController.agregarDireccion}" reRender="pgListDomicilio,pgFormDomicilio,ubicacionPanel"
          	 				styleClass="btnEstilos"/>
	               		<%--
	               		<a4j:commandButton id="btnAgregarDomicilio" value="Agregar" styleClass="btnEstilos1"
	               			actionListener="#{domicilioController.agregarDireccion}" reRender="pgListDomicilio,ubicacionPanel">
	               			<rich:componentControl for="pAgregarDomicilio" attachTo="btnAgregarDomicilio" operation="show" event="onclick"/>
	               	  	</a4j:commandButton>--%>
				   </rich:column>
	            </h:panelGrid>
	            
	            <h:panelGrid id="pgListDomicilio" columns="1" border="0" style="text-align:center;">
		            <rich:dataTable id="edtDomicilio" value="#{domicilioController.listaDomicilio}" rows="10"
	         			rendered="#{not empty domicilioController.listaDomicilio}"  
	                    var="item" rowKeyVar="rowKey" sortMode="single">
	                	<rich:column width="15px;">
			                <h:selectBooleanCheckbox value="#{item.chkDir}"/>
			            </rich:column>
			            <rich:column width="500">
			            	<f:facet name="header">
	                            <h:outputText value="Dirección"/>
	                        </f:facet>
			            	<h:outputText id="lblValDireccion" value="#{item.strDireccion}"/>
			            </rich:column>
			            <rich:column>
			            	<a4j:commandLink id="lnkEditDireccion" styleClass="no-decor" reRender="pViewDomicilio,pgViewFormDomicilio"
			            		rendered="#{empresaController.beanSucursal.id.intIdSucursal!=null && item.id.intIdDomicilio != null}"
			            		actionListener="#{domicilioController.irViewDomicilio}"
			                    oncomplete="#{rich:component('pViewDomicilio')}.show()">
			                    <h:graphicImage value="/images/icons/buscar7.jpg" alt="edit"/>
			                    <f:param value="#{item.id.intIdPersona}" name="pIntIdPersona"/>
			                    <f:param value="#{item.id.intIdDomicilio}" name="pIntIdDomicilio"/>
			                    <rich:toolTip for="lnkEditDireccion" value="Ver Dirección" followMouse="true"/>
			                </a4j:commandLink>
			            </rich:column>
			            <rich:column>
			            	<a4j:commandLink id="lnkDeleteDireccion" styleClass="no-decor" reRender="pgListDomicilio"
			            		rendered="#{empresaController.beanSucursal.id.intIdSucursal!=null && item.id.intIdDomicilio != null}"
			            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;"
			            		actionListener="#{domicilioController.eliminarDomicilio}">
			                    <h:graphicImage value="/images/icons/delete.png" alt="edit"/>
			                    <f:param value="#{item.id.intIdPersona}" name="pIntIdPersona"/>
			                    <f:param value="#{item.id.intIdDomicilio}" name="pIntIdDomicilio"/>
			                    <rich:toolTip for="lnkDeleteDireccion" value="Eliminar Dirección" followMouse="true"/>
			                </a4j:commandLink>
			            </rich:column>
	               	</rich:dataTable>
			    </h:panelGrid>
			    
	            <rich:panel id="pTercero" style="border: 0px solid #17356f;background-color:#DEEBF5; " >
				    <h:panelGrid columns="5" border="0" >
				       <rich:column style="width: 5px; border: none">
				          <h:outputText id="lblcdt" value=""  ></h:outputText>
				       </rich:column>
				       <rich:column style="width: 10px; border: none">
				          <h:selectBooleanCheckbox id="chkCodTerceros" value="#{empresaController.chkTercero}">
				          	<a4j:support event="onclick" reRender="pTercero" actionListener="#{empresaController.enableDisable}" />
				          </h:selectBooleanCheckbox>
				       </rich:column>
				       <rich:column style="width: 220px; border: none">
				          <h:outputText id="lblCodTerceros" value="Código de Terceros :"/>
				       </rich:column>
				       <rich:column>
				          	<a4j:commandButton value="Agregar" styleClass="btnEstilos" actionListener="#{empresaController.addSucursalCodigo}" 
				          		disabled="#{empresaController.formSucursalEnabled}" reRender="pgSucursalCodigo"/>
				       </rich:column>
				       <rich:column>
				       		<a4j:commandButton value="Eliminar" styleClass="btnEstilos" actionListener="#{empresaController.removeSucursalCodigo}" 
				          		disabled="#{empresaController.formSucursalEnabled}" reRender="pgSucursalCodigo"/>
				       </rich:column>
				    </h:panelGrid>
			    </rich:panel>
				
	            <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">                  
	               <h:panelGrid id="pgSucursalCodigo">
	               	  <rich:dataTable value="#{empresaController.listSucursalCodigo}" var="item"
									  rendered="#{not empty empresaController.listSucursalCodigo}">
						 <rich:column>
						 	<h:selectBooleanCheckbox value="#{item.chkCodigoTercero}"/>
						 </rich:column>
						 <rich:column style="width:150px">
						 	<f:facet name="header">
	                           <h:outputText value="Tipo de Código"/>
	                        </f:facet>
						    <h:selectOneMenu value="#{item.intIdTipoCodigo}">
						    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCODTERCERO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						    </h:selectOneMenu>
						 </rich:column>
						 <rich:column style="width:80px">
						 	<f:facet name="header">
	                           <h:outputText value="Código"/>
	                        </f:facet>
	                        <h:inputText value="#{item.strIdCodigo}"/>
						 </rich:column>
						 <rich:column>
						 	<f:facet name="header">
	                           <h:outputText  id="lblCodigoTer" value="Estado"/>
	                        </f:facet>
						    <h:selectOneMenu value="#{item.intEstadoCod}">
						    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						    </h:selectOneMenu>
						 </rich:column>
	                  </rich:dataTable>
	               </h:panelGrid>
	            </rich:panel>
				
				<rich:panel id="pSubsucursal" style="border: 0px solid #17356f;background-color:#DEEBF5; " >
		            <h:panelGrid columns="5" border="0">
		               <rich:column style="width: 5px;">
				          <h:outputText id="lblsbs" value="" />
				       </rich:column>
		               <rich:column style="width: 10px; border: none">
		               	  <h:selectBooleanCheckbox value="#{empresaController.chkSubsucursal}">
				          	<a4j:support event="onclick" reRender="pSubsucursal" actionListener="#{empresaController.enableDisable1}" />
				          </h:selectBooleanCheckbox>
		               </rich:column>
		               <rich:column style="width: 220px; border: none">
		                  <h:outputText id="lblTieneSubSucursales" value="Tiene Sub Sucursales :"  ></h:outputText>
		               </rich:column>
		               <rich:column>
		                  <a4j:commandButton value="Agregar" styleClass="btnEstilos" actionListener="#{empresaController.addSubSucursal}" 
		                  	disabled="#{empresaController.formSucursalEnabled2}" reRender="pgSubSucursales"/>
		               </rich:column>
		               <rich:column>
		                  <a4j:commandButton value="Eliminar" styleClass="btnEstilos" actionListener="#{empresaController.removeSubSucursal}" 
		                  	disabled="#{empresaController.formSucursalEnabled2}" reRender="pgSubSucursales"/>
		               </rich:column>
		            </h:panelGrid>
		        </rich:panel>
	            
               <h:panelGrid id="pgSubSucursales">
                  <rich:dataTable id="listSubsucursal" var="item" 
                  	value="#{empresaController.listSubsucursal}"
                  	rendered="#{not empty empresaController.listSubsucursal}">
					 <rich:column>
					 	<h:selectBooleanCheckbox value="#{item.chkSubSucursal}"/>
					 </rich:column>
					 <rich:column style="width:150px">
					 	<f:facet name="header">
                           <h:outputText value="Nombre"/>
                        </f:facet>
					    <h:inputText value="#{item.strDescripcion}" size="20"/>
					 </rich:column>
					 <rich:column style="width:80px">
					 	<f:facet name="header">
                           <h:outputText value="Activo"/>
                        </f:facet>
					    <h:selectOneMenu value="#{item.intIdEstado}">
					    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					    </h:selectOneMenu>
					 </rich:column>
                  </rich:dataTable>
               </h:panelGrid>
	         </rich:panel>
		</rich:panel>