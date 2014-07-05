<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi               -->
<!-- Modulo    : Cobranza 						-->
<!-- Prototipo : Movimiento de Cuenta Corriente -->			
<!-- Fecha     : 11/12/2012                     -->
<!-- Modificado: Gutenberg Torres Brousset P.	-->
<!-- Fecha	   : 15.abr.2014					-->
	
<script type="text/javascript">	  
      function soloNumeros(tecla)
      { 
        // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57   
         var keys = nav4 ? tecla.which : tecla.keyCode; 
        
         return ((keys <= 13 || (keys >= 50 && keys <= 57));
      }
      
     
</script>
<rich:panel id="pMarcoCuentaCte" style="border:1px solid #17356f;">
   
          <!-- INI - Filtro de Busqueda -->
	           <h:panelGrid columns="8" border="0" >
	         	    <rich:column style="width:30px; border: none">
	         		  <h:outputText  value="Sucursal: "  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:100px; border: none">
		         	 <h:selectOneMenu  style="width: 140px;"
							   value="#{cuentaCteController.intCboSucursal}" >
							   <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							   <tumih:selectItems var="sel" value="#{cuentaCteController.listJuridicaSucursal}"
							   itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				      </h:selectOneMenu>
	         	    </rich:column>
	         	    <rich:column style="width:65px; border: none">
	         		  <h:outputText   value="DNI Socio: "  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:115px; border: none">
	         		  <h:inputText onkeypress="return soloNumeros(event);" value="#{cuentaCteController.strInNroDniSocio}"> </h:inputText>
	         	    </rich:column>
		            <rich:column style="width:30px; border: none">
	         		   <h:outputText  value="Fecha: "  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:100px; border: none">
		         	  <h:selectOneMenu  style="width: 140px;"
							   value="#{cuentaCteController.intCboParaTipoEstado}" >
							   <f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							   <f:selectItem itemValue="1" itemLabel="Registro"/>
							   <f:selectItem itemValue="2" itemLabel="Atención"/>
				      </h:selectOneMenu>
	         	    </rich:column>
	         	    <rich:column style="width:150px; border: none">
	         	      <rich:calendar value="#{cuentaCteController.dtFechaInicio}" datePattern="dd/MM/yyyy" style="width:76px">
	         	      </rich:calendar>
	         	    </rich:column>
	         	    <rich:column style="width:150px; border: none">
	         	      <rich:calendar value="#{cuentaCteController.dtFechaFin}" datePattern="dd/MM/yyyy" style="width:76px">
	         	      </rich:calendar>
	         	    </rich:column>
	         	    <rich:column style="width:40px; border: none">
	         		  <h:outputText   value="Estado:"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         	  		<h:selectOneMenu value="#{cuentaCteController.intCboEstadoSolicitud}" style="width:100px">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
		            </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         	       <h:outputText  value="Tipo de Solicitud:"  > </h:outputText>
	         	    </rich:column>
		            <rich:column style="width:180px; border: none">
	         	  		<h:selectOneMenu value="#{cuentaCteController.intCboTipoSolicitud}" style="width:200px">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOLCITUD}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
		            </rich:column>
	         	   
	         </h:panelGrid>
	          
	          <h:panelGrid columns="1" border="0" style="width:1000px; border: none">
	                <rich:column style="width:80px; border: none">
	         		  <a4j:commandButton  value="Buscar" styleClass="btnEstilos" actionListener="#{cuentaCteController.buscar}" reRender="pgFilCuentaCte">
	         		  </a4j:commandButton>
	         	    </rich:column>
	          </h:panelGrid>
           <!-- FIN - Filtro de Busqueda -->

			<rich:spacer height="12px" />

			<h:panelGrid id="pgFilCuentaCte" 
				style="margin-left: auto; margin-right: auto">
					<rich:extendedDataTable id="tblFilCuentaCte" sortMode="single" 
						enableContextMenu="false" var="item" 
						rendered="#{not empty cuentaCteController.beanListSolCtaCte}" 
						value="#{cuentaCteController.beanListSolCtaCte}" rowKeyVar="rowkey" 
						rows="5" width="1150px" height="240px" align="center">
						<rich:column width="29px" style="text-align: right">
							<h:outputText value="#{rowkey+1}" />
						</rich:column>
						<rich:column width="50px" sortBy="#{item.id.intCcobItemsolctacte}">
							<f:facet name="header" >
								<h:outputText  id="lblNroSolicitud" value="N° Sol." />
							</f:facet>
							<h:outputText id="lblValNroSolicitud" value="#{item.id.intCcobItemsolctacte}" />
						</rich:column>
						<rich:column width="80px">
							<f:facet name="header">
								<h:outputText id="lblFecha" value="Fecha" />
							</f:facet>
							<rich:dataTable value="#{item.listaEstSolCtaCte}" 
								var="itemEstadoSol" style="border-top: 0px; border-left: 0px;">
								<rich:column width="80px" rendered="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE == itemEstadoSol.intParaEstadoSolCtaCte}">
									<h:panelGroup>
										<h:outputText id="lblValFechaSol" value="#{itemEstadoSol.dtEsccFechaEstado}" >
											<f:convertDateTime pattern="dd/MM/yyyy" />
										</h:outputText>
									</h:panelGroup>
								</rich:column>
							</rich:dataTable>
						</rich:column>
						<rich:column width="70px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblTipRol" value="Tipo Rol" />
                                    </f:facet>
                                    <rich:dataTable value="#{item.listaPersonaSocioRol}" 
				                     		var="tipoRol" 
				                     		style="border-top: 0px; border-left: 0px;">
											<rich:column width="200px">
					                     		<h:panelGroup>
													<tumih:outputText value="#{cuentaCteController.listaTipoRol}" 
											        itemValue="intIdDetalle" 
											        itemLabel="strDescripcion" 
											        property="#{tipoRol.id.intParaRolPk}"/>
											   </h:panelGroup>
					                  		</rich:column>
									</rich:dataTable>
                                </rich:column>
                                <rich:column width="200px" sortBy="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblSocio" value="Nombre" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValSocio" value="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}-#{item.documentoSocio.strNumeroIdentidad}" />
                                    <rich:toolTip for="lblValSocio" value="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}-#{item.documentoSocio.strNumeroIdentidad}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText id="lblSucursal" value="Sucursal" />
                                    </f:facet>
                                      <h:outputText id="lblValSucursal" value="#{item.sucursal.strRazonSocial}" />
                                 </rich:column>
                                 <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstadoSol" value="Estado Sol." />
                                    </f:facet>
                                    
                                    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD}" 
										                  itemValue="intIdDetalle" 
										                  itemLabel="strDescripcion" 
										                  property="#{item.intEstadoSolCtaCte}"/>
					                  		
							     </rich:column>
                                 <rich:column width="140px">
                                    <f:facet name="header">
                                        <h:outputText id="lblUsuario" value="Usuario" />
                                    </f:facet>
                                  <rich:dataTable value="#{item.listaEstSolCtaCte}" 
				                     		var="itemEstadoSol" 
				                     		style="border-top: 0px; border-left: 0px;">
										<rich:column width="80px" rendered="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE == itemEstadoSol.intParaEstadoSolCtaCte}">
					                   		<h:panelGroup>
				                             <h:outputText id="lblValUsuarioSol" value="#{itemEstadoSol.usuarioSolicitud.strNombres} #{itemEstadoSol.usuarioSolicitud.strApellidoPaterno} #{itemEstadoSol.usuarioSolicitud.strApellidoMaterno}" ></h:outputText>
     						                 </h:panelGroup>
					               		</rich:column>
								 </rich:dataTable>
                                 </rich:column>
                                 <rich:column width="160px" >
                                    <f:facet name="header">
                                        <h:outputText id="lblTipoSol" value="Tipo Sol." />
                                    </f:facet>
                                    <rich:dataTable value="#{item.listaSolCtaCteTipo}" 
				                     		var="tipoSol" 
				                     		style="border-top: 0px; border-left: 0px;">
											<rich:column width="200px">
					                     		<h:panelGroup>
													<h:outputText value=" "/>
													<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOLCITUD}" 
											        itemValue="intIdDetalle" 
											        itemLabel="strDescripcion" 
											        property="#{tipoSol.id.intTipoSolicitudctacte}"/>
											        
											   </h:panelGroup>
					                  		</rich:column>
									</rich:dataTable>
                                 </rich:column>
                                 <rich:column width="80px">
                                    <f:facet name="header">
                                        <h:outputText id="lblFAtencion" value="Fec. Atenc." ></h:outputText>
                                    </f:facet>
                                    <rich:dataTable value="#{item.listaEstSolCtaCte}" 
				                     		var="itemEstadoSol" 
				                     		style="border-top: 0px; border-left: 0px;">
											<rich:column width="80px" rendered="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO == itemEstadoSol.intParaEstadoSolCtaCte}">
					                     		<h:panelGroup>
													 <h:outputText id="lblValFechaAten" value="#{itemEstadoSol.dtEsccFechaEstado}" >
				                                      <f:convertDateTime pattern="dd/MM/yyyy" />
				                                    </h:outputText>
											   </h:panelGroup>
					                  		</rich:column>
									</rich:dataTable>
                                 </rich:column>
                                  <rich:column width="140px">
                                    <f:facet name="header">
                                        <h:outputText id="lblUAtencion" value="Usuario Atenc." ></h:outputText>
                                    </f:facet>
                                    <rich:dataTable value="#{item.listaEstSolCtaCte}" 
				                     		var="itemEstadoSol" 
				                     		style="border-top: 0px; border-left: 0px;">
										<rich:column width="80px" rendered="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO == itemEstadoSol.intParaEstadoSolCtaCte}">
					                   		<h:panelGroup>
				                             <h:outputText id="lblValUsuarioAten" value="#{itemEstadoSol.usuarioAtencion.strNombres} #{itemEstadoSol.usuarioAtencion.strApellidoPaterno} #{itemEstadoSol.usuarioAtencion.strApellidoMaterno}" ></h:outputText>
     						                </h:panelGroup>
					               		</rich:column>
								     </rich:dataTable>
                                 </rich:column>
								<f:facet name="footer">
									<rich:datascroller for="tblFilCuentaCte" maxPages="10" />
								</f:facet>
								<a4j:support event="onRowClick"
									actionListener="#{cuentaCteController.seleccionar}"
									reRender="txtSeleccionar,frmOpcionCuentaCte"
									oncomplete="Richfaces.showModalPanel('mdpOpcionCuentaCte')">
									<f:attribute name="item" value="#{item}" />
								</a4j:support>     
                            </rich:extendedDataTable>      
               </h:panelGrid>

			<h:panelGrid columns="1"
				style="margin-left: auto; margin-right: auto">
				<h:outputText
					value="Para Atender o Ver un Movimiento Cuenta Corriente hacer clic en el registro"
					style="color:#8ca0bd" />
			</h:panelGrid>
              
		<!-- Ini - Formulario-->   
		<h:panelGrid id="idBotones"  columns="2">
      		<rich:column>
				<a4j:commandButton id="btnGrabar"  disabled="#{cuentaCteController.btnGrabarDisabled}" 
						value="Grabar" actionListener="#{cuentaCteController.grabar}" styleClass="btnEstilos"
		              	reRender="divFormCuentaCte,pgFilCuentaCte,btnGrabar" >
		 		</a4j:commandButton>
				<a4j:commandButton value="Cancelar" actionListener="#{cuentaCteController.cancelar}" styleClass="btnEstilos"
						reRender="divFormCuentaCte,btnGrabar" >
				</a4j:commandButton>
			</rich:column>
		</h:panelGrid>

		<rich:spacer height="8px" />
	   
	  <!-- Form  -->      
	 <h:panelGroup id="divFormCuentaCte" layout="block" > 
	     <rich:panel style="width:1120px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{cuentaCteController.cuentaCteFormRendered}">
	         <h:panelGrid  columns="2" id = "pGridFilaCero" >
	             <rich:column style="width:110px; border: none">
	         		<h:outputText  value="Solicitud Cta Cte :"> </h:outputText>
	         	 </rich:column>
	         	  <rich:column style="width:210px; border: none">
	         	   <rich:dataTable value="#{cuentaCteController.beanSolCtaCte.listaEstSolCtaCte}" 
				                     		var="itemEstadoSol" 
				                     		style="border-top: 0px; border-left: 0px;">
											<rich:column width="200px" rendered="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE == itemEstadoSol.intParaEstadoSolCtaCte}">
											         <h:outputText  value="#{cuentaCteController.beanSolCtaCte.id.intCcobItemsolctacte}"> </h:outputText>-Fecha:
					                     			 <h:outputText id="lblValFechaSol" value="#{itemEstadoSol.dtEsccFechaEstado}" >
				                                      <f:convertDateTime pattern="dd/MM/yyyy" />
				                                    </h:outputText>
											 </rich:column>
				  </rich:dataTable>
	         	 </rich:column>
	         </h:panelGrid>  	 
	       <h:panelGrid  columns="6" id = "pGridFilaUno" >
	             <rich:column style="width:110px; border: none">
	         		<h:outputText  value="Tipo Rol :"> </h:outputText>
	         	 </rich:column>
	         	 <rich:column style="width:160px; border: none">
		         		<h:selectOneMenu rendered="#{!cuentaCteController.cuentaCteRendered}" value="#{cuentaCteController.intCboParaTipo}" style="width:160px"  disabled="#{cuentaCteController.intTipoRolDisabled}">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" value="#{cuentaCteController.listaTipoRol}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
						
						<h:outputText rendered="#{cuentaCteController.cuentaCteRendered}" value="#{cuentaCteController.strListaPersonaRol}"></h:outputText>
		         </rich:column>
		         <rich:column style="width:300px; border: none" rendered="#{cuentaCteController.cuentaCteRendered}">
	         		  <h:outputText value="#{cuentaCteController.socioCom.socio.id.intIdPersona} - #{cuentaCteController.socioCom.socio.strNombreSoc} #{cuentaCteController.socioCom.socio.strApePatSoc} #{cuentaCteController.socioCom.socio.strApeMatSoc} "/>
	        		  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{cuentaCteController.socioCom.cuenta.intParaCondicionCuentaCod}"/>
	         			          - 
	         	      <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{cuentaCteController.socioCom.cuenta.intParaSubCondicionCuentaCod}"/>
						          
	         	 </rich:column>
	         	 <rich:column style="width:100px; border: none">
	         	      DNI:<h:outputText value=" #{cuentaCteController.socioCom.persona.documento.strNumeroIdentidad}"></h:outputText>
				 </rich:column>
				 <rich:column style="width:100px; border: none">
	         	      Cta:<h:outputText value=" #{cuentaCteController.socioCom.cuenta.strNumeroCuenta}"></h:outputText>
				 </rich:column>
			 	 <rich:column style="width:80px; border: none" rendered="#{!cuentaCteController.cuentaCteRendered}">
	         		<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{cuentaCteController.buscarSocio}"
	         		oncomplete="Richfaces.showModalPanel('mpBusqSocio')" reRender="mpBusqSocioForm" />
			 	 </rich:column>
          </h:panelGrid>
          <h:panelGrid  columns="4" rendered="#{cuentaCteController.cuentaCteRendered && not empty cuentaCteController.strDescEntidad}">
	      	 <rich:column style="width:110px; border: none">
            	<h:outputText  value="Entidad Origen:"  > </h:outputText>
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
	     
	     <h:panelGrid  columns="4" rendered="#{cuentaCteController.cuentaCteRendered && not empty cuentaCteController.strDescEntidadPres}">
	      	 <rich:column style="width:110px; border: none">
            	<h:outputText  value="Entidad Préstamo:"  > </h:outputText>
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
	      <h:panelGrid  columns="2" rendered="#{cuentaCteController.cuentaCteRendered}">
	      	<rich:column style="width:110px; border: none">
            	<h:outputText  value="Observación:"  > </h:outputText>
         	</rich:column> 
         	<rich:column style="width:300px; border: none">
            	<h:outputText   value="#{cuentaCteController.strObservacion}"></h:outputText>
         	</rich:column> 
        </h:panelGrid> 
	      <h:panelGrid  columns="2" rendered="#{cuentaCteController.cuentaCteRendered}">
	      	 <rich:column style="width:110px; border: none">
            	<h:outputText  value="Tipo de Solicitud:"  > </h:outputText>
         	</rich:column> 
         	 <rich:dataGrid value="#{cuentaCteController.listaTipoSolicitud}" var="item" columns="4" elements="#{cuentaCteController.tamanioListaTipoSol}" >
         	  <rich:panel>
	                <h:panelGrid columns="2">
	                    <h:selectBooleanCheckbox  value="#{item.checked}" disabled="true"/>
	                    <h:outputText value="#{item.strDescripcion}" />
	                </h:panelGrid>
	                <h:panelGrid columns="2">
	                       Obs:
	                       <h:inputTextarea  value="#{item.strAbreviatura}" rows="3" style="width:180px; border: none" disabled="true">
	                           <f:validateLength maximum="180" />
	                       </h:inputTextarea>
	                </h:panelGrid>
	          </rich:panel>      
	        </rich:dataGrid>
	     </h:panelGrid>
       <h:panelGrid  columns="3" id="panelDocCobranzaG" rendered="#{cuentaCteController.cuentaCteRendered}">
	       	<rich:column style="width:110px; border: none">
            	<h:outputText  value="Sustento Solicitud:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento6" width="650">
					<h:outputText rendered="#{not empty cuentaCteController.archivoDocSolicitud}"
						value="#{cuentaCteController.archivoDocSolicitud.strNombrearchivo}"
						style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column width="140px">
			    <h:commandButton  value="Ver"	styleClass="btnEstilos"  
						actionListener="#{cuentaCteController.descargarArchivo}">
						<f:attribute name="archivo" value="#{cuentaCteController.archivoDocSolicitud}"/>		
				</h:commandButton>
	        </rich:column>
       </h:panelGrid> 	
       
       <h:panelGrid  columns="3" id="panelDocCobranzaGG" rendered="#{cuentaCteController.cuentaCteRendered}">
	       	<rich:column style="width:110px; border: none">
            	<h:outputText  value="Doc Opc:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento66" width="650">
					<h:outputText rendered="#{not empty cuentaCteController.archivoDocSolicitud1}"
						value="#{cuentaCteController.archivoDocSolicitud1.strNombrearchivo}"
						style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column width="140px">
					<h:commandButton  value="Ver"	styleClass="btnEstilos"  
						actionListener="#{cuentaCteController.descargarArchivo}">
						<f:attribute name="archivo" value="#{cuentaCteController.archivoDocSolicitud1}"/>		
					</h:commandButton>
	         </rich:column>
       </h:panelGrid> 	
       
       <h:panelGrid  columns="3" id="panelDocCobranzaGGG" rendered="#{cuentaCteController.cuentaCteRendered}">
	       	<rich:column style="width:110px; border: none">
            	<h:outputText  value="Doc Opc:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento666" width="650">
					<h:outputText rendered="#{not empty cuentaCteController.archivoDocSolicitud2}"
						value="#{cuentaCteController.archivoDocSolicitud2.strNombrearchivo}"
						style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column width="140px">
					<h:commandButton  value="Ver"	styleClass="btnEstilos"  
						actionListener="#{cuentaCteController.descargarArchivo}">
						<f:attribute name="archivo" value="#{cuentaCteController.archivoDocSolicitud2}"/>		
					</h:commandButton>
	         </rich:column>
       </h:panelGrid> 	
          
	</rich:panel>

		<rich:spacer height="8px" />
		
	 <rich:panel id="dataListSolCtaTipo" style="width: 1120px;border:1px solid #880f;background-color:#DEEBF5;" 
	 		rendered="#{cuentaCteController.cuentaCteFormRendered}">
		 <rich:dataList value="#{cuentaCteController.beanSolCtaCte.listaSolCtaCteTipo}" var="item">
		    <h:panelGrid columns="5">
			      <rich:column style="width:200px; border: none">
		          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOLCITUD}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.id.intTipoSolicitudctacte}"/>
	         		
	             	          </rich:column>
	          <rich:column style="width:80px; border: none">
	         	<h:selectOneMenu value="#{item.intParaEstadoanalisis}" style="width:100px" 
	         	                 disabled="#{cuentaCteController.cboAprobarRechazarDisabled || empty item.id.intCcobItemsolctacte}"> 
	             		         <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOSOLCITUD}" 
								 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								 tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
								 <a4j:support event="onchange" reRender="dataListSolCtaTipo" ajaxSingle="true"/>
				</h:selectOneMenu>
		      </rich:column>
		      <rich:column style="width:200px; border: none">
	            <h:inputText value="#{item.strDescripcion}" size="20"  disabled="true"/>
	          </rich:column>
	          <rich:column>
	            <a4j:commandButton rendered="#{!cuentaCteController.cboAprobarRechazarDisabled && (item.intParaEstadoanalisis == applicationScope.Constante.PARAM_T_ESTADOANALISIS_APROBAR || empty item.intParaEstadoanalisis)}" value="Nuevo"  
	                               styleClass="btnEstilos"   
	                               actionListener="#{cuentaCteController.irSolicitudCtaCteTipo}"
	                               oncomplete="if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_LICENCIA}){#{rich:component('mpLicencia')}.show()}
	                                           else                                                                                                     
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOGARANTE}){#{rich:component('mpCambioGarante')}.show()}
	                                           else  
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOENTIDAD}){#{rich:component('mpCambioEntidad')}.show()}
	                                           else                                                                                                      
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION}){#{rich:component('mdpCambioCondicion')}.show()}
	                                           else                                                                                                      
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL}){#{rich:component('mpCambioCondicionLaboral')}.show()}
	                                           else                                                                                                      
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_DESCUENTO_INDEBIDO}){#{rich:component('mpDescuentoIndebido')}.show()}
	                                           else                                                                                                      
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_TRANSFERENCIA}){#{rich:component('mpTransferencia')}.show()}"
	                                 
	                               reRender="formLicencia,formCambioGarante,formCambioEntidad,formCambioCondicion,formCambioCondicionLaboral,formDescuentoIndebido,formTransferencia">
	                               
	              <f:attribute  name="item" value="#{item}"/>
	            </a4j:commandButton>
	             <a4j:commandButton rendered="#{cuentaCteController.cboAprobarRechazarDisabled && item.intParaEstadoanalisis == applicationScope.Constante.PARAM_T_ESTADOANALISIS_APROBAR}" value="VER"  
	                                styleClass="btnEstilos"   
	                                actionListener="#{cuentaCteController.verSolicitudCtaCteTipo}"
	                                oncomplete="if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_LICENCIA}){#{rich:component('mpLicencia')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOGARANTE}){#{rich:component('mpCambioGarante')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOENTIDAD}){#{rich:component('mpCambioEntidad')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION}){#{rich:component('mdpCambioCondicion')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL}){#{rich:component('mpCambioCondicionLaboral')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_DESCUENTO_INDEBIDO}){#{rich:component('mpDescuentoIndebido')}.show()}
	                                           else
	                                           if(#{item.id.intTipoSolicitudctacte == applicationScope.Constante.PARAM_T_TIPOSOLCITUD_TRANSFERENCIA}){#{rich:component('mpTransferencia')}.show()}"
	                            
	                                       
	                                reRender="formLicencia,formCambioGarante,formCambioEntidad,formCambioCondicion,formCambioCondicionLaboral,formDescuentoIndebido,formTransferencia">
	              <f:attribute  name="item" value="#{item}"/>
	            </a4j:commandButton>
	            <h:outputText rendered="#{not empty item.strDescripcion}" style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
	            <a4j:commandButton rendered ="#{!cuentaCteController.cboAprobarRechazarDisabled && (empty item.id.intCcobItemsolctacte)}" 
         	                       value="Quitar" 
         	                       styleClass="btnEstilos"
         	                       actionListener="#{cuentaCteController.quitarMovimiento}"
         	                       reRender="dataListSolCtaTipo">
         	                       <f:attribute  name="item" value="#{item}"/>
         	   </a4j:commandButton>
	          </rich:column>
          </h:panelGrid>
		 </rich:dataList>
		 
		 <h:panelGrid  columns="3">
	      	<rich:column style="width:120px; border: none">
            	<h:outputText  value="Agregar Movimiento: "  > </h:outputText>
         	</rich:column> 
         	<rich:column style="width:300px; border: none" >
            	<h:selectOneMenu value="#{cuentaCteController.intCboTipoSolicitudAux}" style="width:300px" disabled="#{cuentaCteController.cboAprobarRechazarDisabled}">
		         		    <tumih:selectItems var="sel" value="#{cuentaCteController.listaTipoMovimiento}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			   </h:selectOneMenu>
         	</rich:column> 
         	<rich:column>
         	   <a4j:commandButton rendered ="#{!cuentaCteController.cboAprobarRechazarDisabled}" 
         	                      value="Agregar" 
         	                      styleClass="btnEstilos"
         	                      actionListener="#{cuentaCteController.agregarMovimiento}"
         	                      disabled="#{cuentaCteController.btnAgregarDisabled}"
         	                      reRender="dataListSolCtaTipo">
         	   </a4j:commandButton>
         	</rich:column>
        </h:panelGrid> 
	</rich:panel>  	   
</h:panelGroup>      	   
	        
</rich:panel>	