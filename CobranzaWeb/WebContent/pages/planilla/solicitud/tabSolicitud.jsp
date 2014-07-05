<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi               -->
<!-- Modulo    : Cobranza 						-->
<!-- Prototipo : Solicitud Cuenta Corriente     -->			
<!-- Fecha     : 03/12/2012                     -->
<!-- Modificado: Gutenberg Torres Brousset P.	-->
<!-- Fecha	   : 12.abr.2014					-->
	
<script type="text/javascript">	  
      function soloNumeros(tecla)
      { 
        // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57   
         var keys = nav4 ? tecla.which : tecla.keyCode; 
        
         return ((keys <= 13 || (keys >= 50 && keys <= 57));
      }
</script>

<rich:panel id="pMarcoSolicitudCtaCte"  style="border:1px solid #17356f;">
   
          <!-- INI - Filtro de Busqueda -->
	           <h:panelGrid columns="9" border="0" >
	         	    <rich:column style="width:80px; border: none">
	         		  <h:outputText  value="Sucursal:"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:100px; border: none">
		         	 <h:selectOneMenu  style="width: 150px;"
							   value="#{solicitudCtaCteController.intCboSucursal}" >
							   <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							   <tumih:selectItems var="sel" value="#{solicitudCtaCteController.listJuridicaSucursal}"
							   itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				      </h:selectOneMenu>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         		  <h:outputText  value="Tipo de Solicitud:"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:160px; border: none">
	         	  		<h:selectOneMenu value="#{solicitudCtaCteController.intCboTipoSolicitud}" style="width:200px">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOLCITUD}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
		            </rich:column>
		            <rich:column style="width:80px; border: none">
	         		  <h:outputText   value="Estado:"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         	  		<h:selectOneMenu value="#{solicitudCtaCteController.intCboEstadoSolicitud}" style="width:100px">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
		            </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         		  <h:outputText    value="DNI Socio:"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:120px; border: none">
	         		  <h:inputText onkeypress="return soloNumeros(event);" value="#{solicitudCtaCteController.strInNroDniSocio}"> </h:inputText>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
						<a4j:commandButton  value="Buscar" styleClass="btnEstilos" actionListener="#{solicitudCtaCteController.buscar}" 
							reRender="pnlFilSolicitudCtaCte,idBotones"/>
	         	    </rich:column>
	         </h:panelGrid>
            <!-- FIN - Filtro de Busqueda -->

			<rich:spacer height="12px" />

			<h:panelGrid id="pnlFilSolicitudCtaCte" 
				style="margin-left: auto; margin-right: auto">
				<rich:extendedDataTable id="tblFilSolicitudCtaCte" sortMode="single" 
					enableContextMenu="false" var="item" 
					rendered="#{not empty solicitudCtaCteController.beanListSolCtaCte}" 
					value="#{solicitudCtaCteController.beanListSolCtaCte}" rowKeyVar="rowkey" 
					rows="5" width="950px" height="240px" align="center">
					<rich:column width="29px" style="text-align: right">
						<h:outputText value="#{rowkey+1}" />
					</rich:column>
					<rich:column width="50px" sortBy="#{item.id.intCcobItemsolctacte}">
						<f:facet name="header">
							<h:outputText  id="lblNroSolicitud" value="N° Sol." />
						</f:facet>
						<h:outputText id="lblValNroSolicitud" value="#{item.id.intCcobItemsolctacte}" />
						<rich:toolTip for="lblValNroSolicitud" value="#{item.id.intCcobItemsolctacte}" ></rich:toolTip>
					</rich:column>
					<rich:column width="76px" sortBy="#{item.estSolCtaCte.dtEsccFechaEstado}">
						<f:facet name="header">
							<h:outputText  id="lblFecha" value="Fecha" />
						</f:facet>
						<h:outputText id="lblValFecha" value="#{item.estSolCtaCte.dtEsccFechaEstado}" >
							<f:convertDateTime pattern="dd/MM/yyyy" />
						</h:outputText>
						<rich:toolTip for="lblValFecha" value="#{item.estSolCtaCte.dtEsccFechaEstado}"></rich:toolTip>
					</rich:column>
					<rich:column width="180px" >
						<f:facet name="header">
							<h:outputText id="lblTipoSol" value="Tipo de Solicitud" ></h:outputText>
						</f:facet>
						<rich:dataTable value="#{item.listaSolCtaCteTipo}" 
							var="tipoSol" style="border-top: 0px; border-left: 0px;">
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
					<rich:column width="100px" >
						<f:facet name="header">
							<h:outputText  id="lblPeriodMod" value="Periodo-Modalidad" />
						</f:facet>
						<h:outputText value="#{item.intPeriodo}-" />
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMODALIDAD}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaTipomodalidad}"/>
		            </rich:column>
                      <rich:column width="200px" sortBy="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}">
                          <f:facet name="header">
                              <h:outputText  id="lblSocio" value="Socio" ></h:outputText>
                          </f:facet>
                          <h:outputText id="lblValSocio" value="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}" />
                          <rich:toolTip for="lblValSocio" value="#{item.socio.strNombres} #{item.socio.strApellidoPaterno} #{item.socio.strApellidoMaterno}"></rich:toolTip>
                      </rich:column>
                      <rich:column width="100px">
                          <f:facet name="header">
                              <h:outputText id="lblSucursal" value="Sucursal" ></h:outputText>
                          </f:facet>
                            <h:outputText id="lblValSucursal" value="#{item.sucursal.strRazonSocial}" />
                       </rich:column>
                       <rich:column width="80px">
                          <f:facet name="header">
                              <h:outputText id="lblEstadoSol" value="Estado Sol." ></h:outputText>
                          </f:facet>
                       		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPESTADOSOLCITUD}" 
				                  itemValue="intIdDetalle" 
				                  itemLabel="strDescripcion" 
				                  property="#{item.estSolCtaCte.intParaEstadoSolCtaCte}"/>
                       </rich:column>
                       <rich:column width="120px">
                          <f:facet name="header">
                              <h:outputText id="lblUsuario" value="Usuario" ></h:outputText>
                          </f:facet>
                              <h:outputText id="lblValUsuario" value="#{item.usuario.strNombres} #{item.usuario.strApellidoPaterno} #{item.usuario.strApellidoMaterno}" ></h:outputText>
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller for="tblFilSolicitudCtaCte" maxPages="10" />
					</f:facet>
					<a4j:support event="onRowClick"
						actionListener="#{solicitudCtaCteController.seleccionar}"
						reRender="txtSeleccionar,mdpOpcionSolicitudCtaCteForm,idBotones"
						oncomplete="Richfaces.showModalPanel('mdpOpcionSolicitudCtaCte')">
						<f:attribute name="item" value="#{item}" />
					</a4j:support>   
				</rich:extendedDataTable>      
			</h:panelGrid>

			<h:panelGrid columns="1"
				style="margin-left: auto; margin-right: auto">
				<h:outputText
					value="Para Modificar o Anular una Solicitud de Cuenta Corriente hacer clic en el registro"
					style="color:#8ca0bd" />
			</h:panelGrid>
              
      <!-- Ini - Formulario-->   
		<h:panelGrid id="idBotones"  columns="3">
			<rich:column visible="#{!solicitudCtaCteController.btnVisible}">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" actionListener="#{solicitudCtaCteController.nuevo}" 
					reRender="divFormSolicitudCtaCte,idBotones" >
				</a4j:commandButton>
			</rich:column>
			<rich:column visible="#{solicitudCtaCteController.btnVisible}">
				<a4j:commandButton id="btnGrabar" disabled="#{solicitudCtaCteController.btnGrabarDisabled}" value="Grabar" 
					actionListener="#{solicitudCtaCteController.grabar}" styleClass="btnEstilos" 
					reRender="divFormSolicitudCtaCte,pnlFilSolicitudCtaCte,btnGrabar,idBotones" >
				</a4j:commandButton>
			</rich:column>
			<rich:column visible="#{solicitudCtaCteController.btnVisible}">
				<a4j:commandButton value="Cancelar" actionListener="#{solicitudCtaCteController.cancelar}" styleClass="btnEstilos"
					reRender="divFormSolicitudCtaCte,btnGrabar,idBotones" >
				</a4j:commandButton>
			</rich:column>
		</h:panelGrid>

		<rich:spacer height="8px" />
	   
	  <!-- Form Enlace -->      
	 <h:panelGroup id="divFormSolicitudCtaCte" layout="block" > 
	     <rich:panel style="width:1080px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{solicitudCtaCteController.solicitudCtaCteFormRendered}">
	       <h:panelGrid  columns="4" id = "pGridFilaUno" >
		         <rich:column style="width:80px; border: none">
	         		<h:outputText  value="Tipo Rol:"> </h:outputText>
	         	 </rich:column>
	         	 <rich:column style="width:160px; border: none">
		         		<h:selectOneMenu rendered="#{!solicitudCtaCteController.solicitudCtaCteRendered}" value="#{solicitudCtaCteController.intCboParaTipo}" style="width:160px"  disabled="#{solicitudCtaCteController.intTipoRolDisabled}">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" value="#{solicitudCtaCteController.listaTipoRol}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
						
						<h:outputText rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}" value="#{solicitudCtaCteController.strListaPersonaRol}"></h:outputText>
		         </rich:column>
		         <rich:column style="width:800px; border: none" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	         		  <h:outputText value="#{solicitudCtaCteController.socioCom.socio.id.intIdPersona} - #{solicitudCtaCteController.socioCom.socio.strNombreSoc} #{solicitudCtaCteController.socioCom.socio.strApePatSoc} #{solicitudCtaCteController.socioCom.socio.strApeMatSoc} "/>
	        		  <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{solicitudCtaCteController.socioCom.cuenta.intParaCondicionCuentaCod}"/>
	         			          - 
	         	      <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{solicitudCtaCteController.socioCom.cuenta.intParaSubCondicionCuentaCod}"/>
						          
	         	     DNI:<h:outputText value="#{solicitudCtaCteController.socioCom.persona.documento.strNumeroIdentidad}"></h:outputText>
				     Cta:<h:outputText value="#{solicitudCtaCteController.socioCom.cuenta.strNumeroCuenta}"></h:outputText>
	         	 </rich:column>
	         	 <rich:column style="width:80px; border: none" rendered="#{!solicitudCtaCteController.solicitudCtaCteRendered}">
	         		<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{solicitudCtaCteController.buscarSocio}"
	         		oncomplete="Richfaces.showModalPanel('mpBusqSocio')" reRender="mpBusqSocioForm" />
			 	 </rich:column>
          </h:panelGrid>
          <h:panelGrid  columns="2" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered && not empty solicitudCtaCteController.strDescEntidad}">
	      	 <rich:column style="width:80px; border: none">
            	<h:outputText  value="Entidad Origen:"  > </h:outputText>
         	</rich:column> 
         	<rich:column style="width:600px; border: none">
         		<h:outputText  value="#{solicitudCtaCteController.strDescEntidad}" > </h:outputText>
         	</rich:column>
	     </h:panelGrid>
	     
	     <h:panelGrid  columns="2" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered && not empty solicitudCtaCteController.strDescEntidadPres}">
	      	 <rich:column style="width:80px; border: none">
            	<h:outputText  value="Entidad Préstamo:"  > </h:outputText>
         	</rich:column> 
         	<rich:column style="width:600px; border: none">
         		<h:outputText  value="#{solicitudCtaCteController.strDescEntidadPres}" > </h:outputText>
         	</rich:column>
         </h:panelGrid>
	     
	      <h:panelGrid  columns="2" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	      	 <rich:column style="width:80px; border: none">
            	<h:outputText  value="Tipo de Solicitud:"  ></h:outputText>
         	</rich:column> 
         	 <rich:dataGrid value="#{solicitudCtaCteController.listaTipoSolicitud}" var="item" columns="4" elements="#{solicitudCtaCteController.tamanioListaTipoSol}" width="1000px">
	            <rich:panel>
	                <h:panelGrid columns="2">
	                    <h:selectBooleanCheckbox value="#{item.checked}" disabled="#{solicitudCtaCteController.chkTipoSolicitudDisabled}">
							<a4j:support event="onclick"
								action="#{solicitudCtaCteController.manejarCheched}"
								reRender="pnlPeriodo" />
						</h:selectBooleanCheckbox>
	                    <h:outputText value="#{item.strDescripcion}" />
	                </h:panelGrid>
	                <h:panelGrid columns="2">
	                       Obs:
	                       <h:inputTextarea  value="#{item.strAbreviatura}" rows="3" style="width:180px; border: none">
	                           <f:validateLength maximum="180" />
	                       </h:inputTextarea>
	                </h:panelGrid>
	            </rich:panel>
	        </rich:dataGrid>
	     </h:panelGrid>
	     
	     <h:panelGrid id="pnlPeriodo" columns="7" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	        <rich:column style="width:80px; border: none">
         		<h:outputText  value="Periodo:"  > </h:outputText>
         	</rich:column>
            <rich:column style="width:30px; border: none">
         		<h:outputText  value="Año:"> </h:outputText>
         	</rich:column>
         	 <rich:column style="width:60px; border: none">
         		<h:selectOneMenu value="#{solicitudCtaCteController.strCboAnio}" style="width:60px"
         		                 disabled="#{solicitudCtaCteController.cboAnioDisabled}">
	         			<tumih:selectItems var="sel" value="#{solicitudCtaCteController.listaAnio}" 
							itemValue="#{sel}" itemLabel="#{sel}"/>
				</h:selectOneMenu>
         	</rich:column>
         	<rich:column style="width:30px; border: none">
         		<h:outputText  value="Mes:"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:100px; border: none">
         	   <h:selectOneMenu value="#{solicitudCtaCteController.intCboMes}" style="width:100px"
         	                    disabled="#{solicitudCtaCteController.cboMesDisabled}">
         	         <tumih:selectItems var="sel" value="#{solicitudCtaCteController.listaMeses}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>          	   						   
			   </h:selectOneMenu>
         	</rich:column>
         	<rich:column style="width:80px; border: none">
         		<h:outputText  value="Modalidad:"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:80px; border: none">
         	   <h:selectOneMenu value="#{solicitudCtaCteController.beanSolCtaCte.intParaTipomodalidad}" style="width:80px"
         	                    disabled="#{solicitudCtaCteController.cboTipoModalidadDisabled}">
				 <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									   tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
									   />
			   </h:selectOneMenu>
         	</rich:column>
       </h:panelGrid>
       <h:panelGrid  columns="2" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	      	<rich:column style="width:80px; border: none">
            	<h:outputText  value="Observación:"  />
         	</rich:column> 
         	<rich:column style="width:300px; border: none">
            	<h:inputText  size="80"  value="#{solicitudCtaCteController.strObservacion}" disabled="#{solicitudCtaCteController.txtObservacionDisabled}"></h:inputText>
         	</rich:column> 
       </h:panelGrid>
       <h:panelGrid  columns="4" id="panelDocCobranzaG" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	       	<rich:column style="width:80px; border: none">
            	<h:outputText  value="Sustento Solicitud:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento6" width="150">
				<h:inputText rendered="#{empty solicitudCtaCteController.archivoDocSolicitud}" 
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;" />
				<h:inputText rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud}"
					value="#{solicitudCtaCteController.archivoDocSolicitud.strNombrearchivo}"
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column id="IDColDocumento7" width="150">
				<a4j:commandButton
					rendered="#{empty solicitudCtaCteController.archivoDocSolicitud}"
					styleClass="btnEstilos"
               		value="Adjuntar Documento Cobranza"
               	    reRender="panelDocCobranzaG,idFormFileUpload"
               		oncomplete="Richfaces.showModalPanel('pAdjuntarDocumento')"
           		    style="width:180px"
           		    actionListener="#{solicitudCtaCteController.seleccionarDoc}">
       		     	<f:attribute  name="item" value="1"/>
       		   	</a4j:commandButton>
               	<a4j:commandButton
					rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud}"
					styleClass="btnEstilos"
               		value="Quitar Documento"
               		reRender="panelDocCobranzaG"
               		action="#{solicitudCtaCteController.quitarDocumento}"
               		style="width:180px"
               		disabled="#{solicitudCtaCteController.btnQuitarArchivoDisabled}"/>
			</rich:column>
			<rich:column width="140px"  rendered="#{solicitudCtaCteController.solicitudCtaCteRendered && not empty solicitudCtaCteController.beanSolCtaCte.id.intCcobItemsolctacte && not empty solicitudCtaCteController.archivoDocSolicitud}">
				<h:commandLink  value="   Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{solicitudCtaCteController.archivoDocSolicitud}"/>		
				</h:commandLink>
	         </rich:column>
       </h:panelGrid> 	
      	<h:panelGrid  columns="4" id="panelDocCobranzaGG" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	       	<rich:column style="width:80px; border: none">
            	<h:outputText  value="Doc. Opcional:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento8" width="150">
				<h:inputText rendered="#{empty solicitudCtaCteController.archivoDocSolicitud1}" 
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;" />
				<h:inputText rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud1}"
					value="#{solicitudCtaCteController.archivoDocSolicitud1.strNombrearchivo}"
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column id="IDColDocumento9" width="150">
				<a4j:commandButton
					rendered="#{empty solicitudCtaCteController.archivoDocSolicitud1}"
					styleClass="btnEstilos"
               		value="Adjuntar Documento Cobranza"
               	    reRender="panelDocCobranzaGG,idFormFileUpload"
               		oncomplete="Richfaces.showModalPanel('pAdjuntarDocumento')"
               		actionListener="#{solicitudCtaCteController.seleccionarDoc}"
           		    style="width:180px"
           		    >
           		    <f:attribute  name="item" value="2"/>
       		 	</a4j:commandButton>   
               	<a4j:commandButton
					rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud1}"
					styleClass="btnEstilos"
               		value="Quitar Documento"
               		reRender="panelDocCobranzaGG"
               		action="#{solicitudCtaCteController.quitarDocumento1}"
               		style="width:180px"
               		disabled="#{solicitudCtaCteController.btnQuitarArchivoDisabled}"/>
			</rich:column>
			<rich:column width="140px"  rendered="#{solicitudCtaCteController.solicitudCtaCteRendered && not empty solicitudCtaCteController.beanSolCtaCte.id.intCcobItemsolctacte && not empty solicitudCtaCteController.archivoDocSolicitud1}">
				<h:commandLink  value="   Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{solicitudCtaCteController.archivoDocSolicitud1}"/>		
				</h:commandLink>
	         </rich:column>
       </h:panelGrid>
       <h:panelGrid  columns="4" id="panelDocCobranzaGGG" rendered="#{solicitudCtaCteController.solicitudCtaCteRendered}">
	       	<rich:column style="width:80px; border: none">
            	<h:outputText  value="Doc. Opcional:"  > </h:outputText>
         	</rich:column> 
         	<rich:column id="IDColDocumento10" width="150">
				<h:inputText rendered="#{empty solicitudCtaCteController.archivoDocSolicitud2}" 
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;" />
				<h:inputText rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud2}"
					value="#{solicitudCtaCteController.archivoDocSolicitud2.strNombrearchivo}"
					size="77"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column> 
			<rich:column id="IDColDocumento11" width="150">
				<a4j:commandButton
					rendered="#{empty solicitudCtaCteController.archivoDocSolicitud2}"
					styleClass="btnEstilos"
               		value="Adjuntar Documento Cobranza"
               	    reRender="panelDocCobranzaGGG,idFormFileUpload"
               		oncomplete="Richfaces.showModalPanel('pAdjuntarDocumento')"
               		actionListener="#{solicitudCtaCteController.seleccionarDoc}"
           		    style="width:180px"
           		    >
           		    <f:attribute  name="item" value="3"/>
       		 	</a4j:commandButton>
               	<a4j:commandButton
					rendered="#{not empty solicitudCtaCteController.archivoDocSolicitud2}"
					styleClass="btnEstilos"
               		value="Quitar Documento"
               		reRender="panelDocCobranzaGGG"
               		action="#{solicitudCtaCteController.quitarDocumento2}"
               		style="width:180px"
               		disabled="#{solicitudCtaCteController.btnQuitarArchivoDisabled}"/>
			</rich:column>
			<rich:column width="140px"  rendered="#{solicitudCtaCteController.solicitudCtaCteRendered && not empty solicitudCtaCteController.beanSolCtaCte.id.intCcobItemsolctacte && not empty solicitudCtaCteController.archivoDocSolicitud2}">
				<h:commandLink  value="   Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{solicitudCtaCteController.archivoDocSolicitud2}"/>		
				</h:commandLink>
	         </rich:column>
       </h:panelGrid> 	

	</rich:panel>

</h:panelGroup>      	   
	        
</rich:panel>	