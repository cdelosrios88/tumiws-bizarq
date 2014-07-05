<%@ taglib  uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

		<!-- Empresa   : Cooperativa Tumi         -->
		<!-- Autor     : Franko Yalico    		  -->
<h:form>
	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
		<h:panelGrid columns="8"
					 id="primeraBusqueda">
			<rich:column width="70" style="text-align: left;">
					<h:outputText value="Sucursal: "/>
			</rich:column>
			<rich:column width="140">
					<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.estructuraDetalle.intSeguSucursalPk}"
									 style="width:137px;">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{efectuadoController.listaTablaDeSucursal}"
			 			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
			<rich:column width="90" style="text-align: left;">
					<h:outputText value="Unid. Ejecutora: "/>
			</rich:column>
			<rich:column width="210">
				<h:inputText value="#{efectuadoController.dtoFiltroDeEfectuado2.estructura.juridica.strRazonSocial}"
							 style="width:200px;"/>
			</rich:column>
			<rich:column width="70" style="text-align: left;">
				<h:outputText value="Tipo Socio: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.estructuraDetalle.intParaTipoSocioCod}"
								 style="width:100px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>	
			</rich:column>
		</h:panelGrid>
		<h:panelGrid columns="10" id="panelBusqueda">
			<rich:column width="70" style="text-align: left;">
					<h:outputText value="Tipo Planilla: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.intParaModalidadPlanilla}" 
								 style="width:150px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
					tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
	  			</h:selectOneMenu>
			</rich:column>
			<rich:column width="90" style="text-align: left;">
					<h:outputText value="Periodo Planilla: "/>
			</rich:column> 	
			<rich:column width="100">
				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.intPeriodoMes}" 
								 style="width:100px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"/>
	  			</h:selectOneMenu>			 
			</rich:column>
			<rich:column width="102">
				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.intPeriodoAnio}" 
								 style="width:100px;">
			  		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<f:selectItem itemLabel="2012" itemValue="2012"/>
					<f:selectItem itemValue="2013" itemLabel="2013"/>
					<f:selectItem itemValue="2014" itemLabel="2014"/>
					<f:selectItem itemValue="2015" itemLabel="2015"/>
	  			</h:selectOneMenu>
			</rich:column>
			<rich:column width="70" style="text-align: left;">
				<h:outputText value="Estado: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado2.efectuadoConcepto.intEstadoCod}" 
								 style="width:100px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="70" style="text-align:right;">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar" reRender="panelTablaResultados, panelMensaje"
					action="#{efectuadoController.buscarDeEfectuado}" style="width:70px"/>
			</rich:column>
			<rich:column width="70" style="text-align:right;">
				<a4j:commandButton styleClass="btnEstilos" style="width:70px"
								   value="Limpiar" reRender="panelTablaResultados, panelMensaje, panelBusqueda, primeraBusqueda"
								   action="#{efectuadoController.limpiandoDeEfectuado}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="12px"/>
		
		<h:panelGrid id="panelTablaResultados">
			<rich:extendedDataTable id="tblResultado"
			 enableContextMenu="false"
			 sortMode="single"
			 var="item"
			 rendered="#{not empty efectuadoController.listaBusquedaEfectuadoResumen}"
			 value="#{efectuadoController.listaBusquedaEfectuadoResumen}"
			 rowKeyVar="rowKey"
			 rows="5"
			 width="1030px"
			 height="160px"
			 align="center">
			 
			 <rich:column width="30px">
      			<h:outputText value="#{rowKey+1}"/>
      		</rich:column>
      		
		      <rich:column width="170px">
		          <f:facet name="header">
		          	<h:outputText value="Unidad Ejecutora"/>
		          </f:facet>
		          <h:outputText value="#{item.juridicaUnidadEjecutora.strRazonSocial}"/>
		      </rich:column>
		      
		      <rich:column width="130px">
		          <f:facet name="header">
		          	<h:outputText value="Sucursal"/>
		          </f:facet>
		          <h:outputText value="#{item.juridicaSucursal.strRazonSocial}"/>
		      </rich:column>
		      
		      <rich:column width="90px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Tipo Socio"/>
		          </f:facet>
		           <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intTiposocioCod}"/>
     		  </rich:column>
     		  
		      <rich:column width="90px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Tipo Planilla"/>
		          </f:facet>
		          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intModalidadCod}"/>
		      </rich:column>
		      
		      <rich:column width="50px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Periodo"/>
		          </f:facet>
		          <h:outputText value="#{item.intPeriodoPlanilla}"/>
		      </rich:column>
      
		      <rich:column width="80px" style="text-align: right">
		          <f:facet name="header">
		          	<h:outputText value="Monto"/>
		          </f:facet>
		          <h:outputText value="#{item.bdMontoTotal}">
		          	<f:converter converterId="ConvertidorMontos" />
		          </h:outputText>
		      </rich:column>
		      
		      <rich:column width="70px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Estado"/>
		          </f:facet>
		          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intEstadoCod}"/>
		      </rich:column>
		      
		      <rich:column width="190px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Fecha Registro"/>
		          </f:facet>
		          <h:outputText value="#{item.tsFecharegistro}">
		          		<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="America/Bogota"/>
		          </h:outputText>
		      </rich:column>		      
		      
		      <f:facet name="footer">
		      	<rich:datascroller for="tblResultado" maxPages="10"/>
		      </f:facet>
		      
			 </rich:extendedDataTable>
		</h:panelGrid>
		
		<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto" 
					 rendered="#{not empty efectuadoController.listaBusquedaEfectuadoResumen}">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para ver un enviado hacer click en el registro" style="color:#8ca0bd"/>
		</h:panelGrid>
		
		<h:panelGroup  style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder" id="panelMensaje">
			<h:outputText value="#{efectuadoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{efectuadoController.mostrarMensajeExito}"/>
			<h:outputText value="#{efectuadoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{efectuadoController.mostrarMensajeError}"/>
		</h:panelGroup>
		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;"
					  styleClass="rich-tabcell-noborder"
					  id="panelBotones">
			<h:panelGrid columns="4">
				<a4j:commandButton value="ConArchivo" 
					styleClass="btnEstilos" 
					style="width:90px" 
					action="#{efectuadoController.habilitarPanelInferiorConArchivo}" 
					reRender="contPanelInferiorConArchivo,contPanelInferior,panelMensaje,panelBotones" />
				<a4j:commandButton value="SinArchivo" 
					styleClass="btnEstilos" 
					style="width:90px" 
					action="#{efectuadoController.habilitarPanelInferior}" 
					reRender="contPanelInferior,contPanelInferiorConArchivo,panelMensaje,panelBotones,panelValidarA" />
				 <a4j:commandButton value="Grabar" 
			    	styleClass="btnEstilos" 
			    	style="width:90px"
			    	action="#{efectuadoController.grabar}" 
			    	reRender="contPanelInferior,contPanelInferiorConArchivo,panelMensaje,panelBotones,panelTablaResultados"
			    	disabled="#{!efectuadoController.habilitarGrabar}"/>
			    <a4j:commandButton value="Cancelar" 
			    	styleClass="btnEstilos"
			    	style="width:90px"
			    	action="#{efectuadoController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,contPanelInferiorConArchivo"/>
	  
			</h:panelGrid>
		</h:panelGroup>	
		<rich:spacer height="12px"/>
		<h:panelGroup id="contPanelInferior">
			<rich:panel  rendered="#{efectuadoController.mostrarPanelInferior}"	
						 style="border:1px solid #17356f;background-color:#DEEBF5;">
						 
				<h:panelGroup rendered="#{!efectuadoController.datosValidados}">					
						<h:panelGrid columns="4" id="panelValidarC">
							<rich:column style="width:90px">
								<h:outputText value="Tipo Socio"/>
							</rich:column>							
							<rich:column>
								<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod}" 
												 id="cboTipoSocio" style="width:100px;" 
											     valueChangeListener="#{efectuadoController.reloadCboTipoPlanilla}">
									<f:selectItem itemLabel="Seleccionar" itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}"/>
									<f:selectItem itemLabel="Activo" itemValue="1"/>
									<f:selectItem itemLabel="Cesante" itemValue="2"/>
									<a4j:support event="onchange" reRender="panelValidarC,panelValidarB,panelValidarA,pgDatosValidados,panelMensaje"/>
								</h:selectOneMenu>
							</rich:column>
							
							<rich:column>
								<h:outputText value="Tipo Planilla"/>
							</rich:column>
							
							<rich:column>
								<h:selectOneMenu rendered="#{efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
				  				  						     ==applicationScope.Constante.OPCION_SELECCIONAR}" 
				  				  				 disabled="true"  style="width:150px;">
									<f:selectItem itemLabel="Seleccionar" itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}" /> 
											     
								</h:selectOneMenu>
								<h:selectOneMenu value="#{efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla}"
								 			     id="cboTipoPlanilla"  style="width:150px;"
				  				  				 rendered="#{efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
				  				  							!=applicationScope.Constante.OPCION_SELECCIONAR}">
				  				  	<f:selectItem itemLabel="Seleccionar" itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}"/>				 		 
									<tumih:selectItems var="sel" value="#{efectuadoController.listaModalidadPlanillaTemporal}"
						 						  	   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						 			<a4j:support event="onchange" reRender="panelValidarC,panelValidarB,pgDatosValidados,panelValidarA,panelMensaje"/>
				  				</h:selectOneMenu>
							</rich:column>
							
						</h:panelGrid>
						
						<h:panelGrid columns="2" id="panelValidarB">
							<rich:column style="width:90px">
								<h:outputText rendered="#{efectuadoController.dtoDeEfectuado.juridicaSucursal == null}"/>								
								<h:outputText value="Sucursal:" rendered="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial != null}"/>
							</rich:column>
							
							<rich:column>
								<h:outputText rendered="#{efectuadoController.dtoDeEfectuado.juridicaSucursal == null}"/>
								<h:inputText value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial}"
										     disabled="true" style="width:250px;"
											 rendered="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial != null}"/>
							</rich:column>							
						</h:panelGrid>
						
						<h:panelGrid columns="3" id="panelValidarA">
							<rich:column style="width:90px">
								<h:outputText value="Unid. Ejecutora: "/>
							</rich:column>
							
							<rich:column>
								<h:inputText value="#{efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial}"
											 disabled="true" style="width:250px;"/>
							</rich:column>
							
							<rich:column>								
								<a4j:commandButton value="Buscar" action="#{efectuadoController.abrirPopupBuscarUnidadEjecutora}"												
											     reRender="pBuscarUnidadEjecutoraEfectuado, contPanelInferior,panelMensaje,formBuscarUnidadEjecutora"
											     disabled="#{efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla== applicationScope.Constante.OPCION_SELECCIONAR}"	 
			  		                 				 	    					   						
						   						oncomplete="Richfaces.showModalPanel('pBuscarUnidadEjecutoraEfectuado')"
						   						styleClass="btnEstilos"/>
							</rich:column>
						</h:panelGrid>
						
						<rich:spacer height="12px"/>
						
						<h:panelGrid columns="1" rendered="#{!efectuadoController.datosValidados}" id="pgDatosValidados">
							<rich:column width="950">								
			                    <a4j:commandButton value="validar" styleClass="btnEstilos" style="width:950px" 
			                    				 rendered="#{(efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
			                    				 			== applicationScope.Constante.PARAM_T_TIPOSOCIO_ACTIVO
			                    				 			&& efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla 
			  		                 				 	    == applicationScope.Constante.OPCION_SELECCIONAR) || (efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
			                    				 			== applicationScope.Constante.OPCION_SELECCIONAR) ||
			                    				 			 (efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
			                    				 			== applicationScope.Constante.OPCION_SELECCIONAR			                    				 						  		                 				  
			  		                 				 	    && efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla 
			  		                 				 	    == applicationScope.Constante.OPCION_SELECCIONAR) || 
			  		                 				 	    ( efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
			                    				 			  == applicationScope.Constante.PARAM_T_TIPOSOCIO_CESANTE && 
			                    				 			  efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla 
			  		                 				 	     == applicationScope.Constante.OPCION_SELECCIONAR) }"
			  		                 				 disabled="true"/>
			  					<a4j:commandButton value="Validar" actionListener="#{efectuadoController.irValidarDeEfectuado}" 
			  		                 			styleClass="btnEstilos" style="width:100%" reRender="panelMensajeValidar, contPanelInferior"  
			  		                 			rendered="#{((efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod
			  		                 				 	== applicationScope.Constante.PARAM_T_TIPOSOCIO_CESANTE &&
  					 							   		efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla
  					 							    	== applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
  														|| (efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
  														== applicationScope.Constante.PARAM_T_TIPOSOCIO_ACTIVO
  					  									&& efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla 
  					  									== applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_CAS))}"/>
			  					<a4j:commandButton value="Validar" actionListener="#{efectuadoController.irValidarDeEfectuadoHaberIncentivo}" 
			  		                 			styleClass="btnEstilos" style="width:100%" reRender="panelMensajeValidar, contPanelInferior"
			  		                 			rendered="#{((efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
			  		                 							== applicationScope.Constante.PARAM_T_TIPOSOCIO_ACTIVO
								 								&& efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla
								 								 == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_HABERES) ||								 								 
								 								(efectuadoController.dtoFiltroDeEfectuado.estructuraDetalle.intParaTipoSocioCod 
								 								== applicationScope.Constante.PARAM_T_TIPOSOCIO_ACTIVO &&
								 								efectuadoController.dtoFiltroDeEfectuado.intParaModalidadPlanilla ==
								 								 applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS))}"/>
							</rich:column>
						</h:panelGrid>
						
						<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
									styleClass="rich-tabcell-noborder"  id="panelMensajeValidar" >
							<h:outputText value="#{efectuadoController.mensajeOperacion}" 
								styleClass="msgInfo"
								style="font-weight:bold"
								rendered="#{efectuadoController.mostrarMensajeExito}"/>
							<h:outputText value="#{efectuadoController.mensajeOperacion}" 
								styleClass="msgError"
								style="font-weight:bold"
								rendered="#{efectuadoController.mostrarMensajeError}"/>
						</h:panelGroup>	
											
				</h:panelGroup>
				
				<h:panelGroup rendered="#{efectuadoController.datosValidados}">
					<h:panelGrid columns="4">
						<rich:column>
							<h:outputText value="Sucursal: "/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial}"
									     disabled="true" style="width:200px;"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Unid. Ejecut.:"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial}"
									     size="#{fn:length(efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial)}"
									     disabled="true" style="width:275px;"/>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="6px"/>
					
					<h:panelGrid columns="8">
						<rich:column>
							<h:outputText value="Mes: "/>
						</rich:column>
						<rich:column>
							<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" disabled="true"
							property="#{efectuadoController.dtoDeEfectuado.intPeriodoMes}" style="width:75px;"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Año: "/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{efectuadoController.dtoDeEfectuado.intPeriodoAnio}"
										 style="width:40px;" disabled="true"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Tipo Planilla: "/>
						</rich:column>
						<rich:column>
							<h:outputText value="#{efectuadoController.dtoDeEfectuado.strModalidad}"  style="width:50px;"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Tipo Socio: "/>
						</rich:column>
						<rich:column>
							<h:outputText value="#{efectuadoController.dtoDeEfectuado.strTipoSocio}"  style="width:50px;"/> 
						</rich:column>
					</h:panelGrid>
					<rich:spacer height="12px"/>
					<h:panelGrid id="pgAgregar" columns="3">
						<a4j:commandButton 		
												value="AgregarSocio"    
												style="width:100%"  styleClass="btnEstilos"
												oncomplete="Richfaces.showModalPanel('pAgregarSocioEnEfectuado')" 												
												reRender="pAgregarSocioEnEfectuado,fAgregarSocioEnEfectuado" 
												actionListener="#{efectuadoController.irAgregarSocio}"/>
						<a4j:commandButton  styleClass="btnEstilos" 
									       value="Relacionar/No Socio" style="width:100%" 
											oncomplete="Richfaces.showModalPanel('pRelacionarNoSocio')" 
											reRender="pRelacionarNoSocio,fRelacionarNoSocio"
											actionListener="#{efectuadoController.irArelacionar}"  />
											 
						<h:commandButton value="Generar Archivo Exportable" styleClass="btnEstilos" style="width:100%"
										action="#{efectuadoController.exportarEfectuadoAExcel}"/>
					</h:panelGrid>
					<rich:spacer height="2px"/>
					<rich:dataTable id="idGrillaTieneEfectuada" var="item" sortMode="single" rows="15" 
						value="#{efectuadoController.listaEfectuadoConceptoComp}" rowKeyVar="rowKey" width="940px"  
		 			   rendered="#{ efectuadoController.intEfectuado == applicationScope.Constante.intHaberIncentivo 
		 			           && efectuadoController.intEfectuado2 == applicationScope.Constante.intHaberIncentivoTieneEfectuado}"> 		 
					   <f:facet name="header">
					   	<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column colspan="4" width="490px">
									<h:outputText value="Enviado:#{efectuadoController.nroEnviadoEnTieneEfectuada} Efectuado:#{efectuadoController.nroEfectuadoEnTieneEfectuada}" />
								</rich:column>
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalEnviadoEnAEfectuar}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalEfectuadoEnAEfectuar}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalDiferenciaEnAEfectuar}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalEnviadoEnYaEfectuada}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalEfectuadoEnYaEfectuada}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalDiferenciaEnYaEfectuada}">
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalesAEnviar}" >
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>	
								
								<rich:column rowspan="1" width="50px" style="text-align: right">
									<h:outputText value="#{efectuadoController.totalesAEfectuar}" >
											<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" width="50px" style="text-align: right">
								</rich:column>
											
								<rich:column breakBefore="true" rowspan="2" width="10px">
									<h:outputText value="Nro" />
								</rich:column>
								<rich:column rowspan="2" width="30px">
									<h:outputText value="Codigo" />
								</rich:column>
								<rich:column rowspan="2" width="400px">
									<h:outputText value="Nombre Completo" />
								</rich:column>
								<rich:column rowspan="2" width="50px">
									<h:outputText value="DNI" />
								</rich:column>
								
								<rich:column colspan="3" rowspan="1" width="75px">
									<h:outputText value="Planilla a Efectuar (#{efectuadoController.dtoDeEfectuado.strModalidad})" />
								</rich:column>
								
								<rich:column colspan="3" rowspan="1"  width="75px">
									<h:outputText value="Planilla Efectuada(#{efectuadoController.dtoDeEfectuado.strModalidad2})" />
								</rich:column>
								
								<rich:column rowspan="1" width="25px">
									<h:outputText value="Enviar" />
								</rich:column>
								
								<rich:column rowspan="1" width="25px">
									<h:outputText value="Efectuar" />
								</rich:column>
								
								<rich:column rowspan="1" width="25px">
									<h:outputText value="Obs." />
								</rich:column>
								
								<rich:column breakBefore="true" width="25px">
									<h:outputText value="Enviado" />
								</rich:column>
								<rich:column width="25px">
									<h:outputText value="Efectuado" />
								</rich:column>
								<rich:column width="25px" >
									<h:outputText value="Diferencia" />
								</rich:column>
								
								<rich:column  width="25px">
									<h:outputText value="Enviado" />
								</rich:column>
								<rich:column width="25px">
									<h:outputText value="Efectuado" />
								</rich:column>
								<rich:column width="25px">
									<h:outputText value="Diferencia" />
								</rich:column>	
												
								<rich:column width="25px">
									<h:outputText value="Total" />
								</rich:column>
								<rich:column width="25px">
									<h:outputText value="Total" />
								</rich:column>
								<rich:column width="25px">					
								</rich:column>
						</rich:columnGroup>
					   </f:facet>
	   
		   <rich:column width="10px" style="text-align: left" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<div align="center"> 
					<h:outputText value="#{rowKey+1}" />				
				</div>							
			</rich:column>
			 <rich:column width="10px" style="text-align: left" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio}" >
				<div align="center"> 				
					<h:outputText value="#{rowKey+1}"/>
				</div>							
			</rich:column>
			<rich:column width="10px" style="text-align: left" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio}" >
				<div align="center"> 				
					<h:outputText value="#{rowKey+1}"/>
				</div>							
			</rich:column>
			
			<rich:column width="30px" style="text-align: left" 
						 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.socio.id.intIdPersona}"/>																			
			</rich:column>		
			<rich:column width="30px" style="text-align: left" styleClass="columnaAgregarSocio" 
						 rendered="#{item.blnAgregarSocio}">			
				<h:outputText value="#{item.socio.id.intIdPersona}"/>																
			</rich:column>
			<rich:column width="30px" style="text-align: left" styleClass="columnaAgregarSocio" 
						 rendered="#{item.blnAgregarNoSocio}">		
				<h:outputText value="#{item.socio.id.intIdPersona}"/>		 	
			</rich:column>
											
			<rich:column width="300px" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<div align="left">
					<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
				</div>
			</rich:column>
			<rich:column width="300px" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
				<div align="left">
					<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
					<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
				</div>
			</rich:column>
			<rich:column width="300px" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
				<div align="left">
					<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																											
				</div>
			</rich:column>									
			
			<rich:column width="50px" style="text-align: left" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.documento.strNumeroIdentidad}" />			 
			</rich:column>		
			<rich:column width="50px" style="text-align: left" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">			
				<h:outputText value="#{item.documento.strNumeroIdentidad}" /> 
			</rich:column>
			<rich:column width="50px" style="text-align: left" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">			
				<h:outputText value="#{item.documento.strNumeroIdentidad}" /> 
			</rich:column>
			
			<rich:column width="25px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
							  rendered="#{item.efectuado.envioMonto != null}" >								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>			
				<h:outputText  rendered="#{item.efectuado.envioMonto == null}" >			
				</h:outputText>
			</rich:column>		
			<rich:column width="25px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio}">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
							  rendered="#{item.efectuado.envioMonto != null}" >								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText  rendered="#{item.efectuado.envioMonto == null}" >			
				</h:outputText>
			</rich:column>
			<rich:column width="25px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio}">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
							  rendered="#{item.efectuado.envioMonto != null}" >								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText  rendered="#{item.efectuado.envioMonto == null}" >			
				</h:outputText>
			</rich:column>
				
			<rich:column width="25px" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">					 
				<h:inputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right"
				 	onkeypress="return soloNumerosDecimalesPositivos(this)" rendered="#{item.efectuado.bdMontoEfectuado != null}"
				 	onkeyup="return extractNumber(this,2,false)" onblur="extractNumber(this,2,false);">
				 	<a4j:support event="onchange" reRender="idGrillaTieneEfectuada"/>												
				</h:inputText>			
				<h:outputText  rendered="#{item.efectuado.bdMontoEfectuado == null}"
							  style="text-align: right">			
				</h:outputText>
			</rich:column>			
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
				<h:outputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right"
				  rendered="#{item.efectuado.bdMontoEfectuado != null}">	
				  <f:converter converterId="ConvertidorMontos" />	 											
				</h:outputText>			
				<h:outputText  rendered="#{item.efectuado.bdMontoEfectuado == null}" style="text-align: right"/>
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
				<h:outputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right"
				  rendered="#{item.efectuado.bdMontoEfectuado != null}">	
				  <f:converter converterId="ConvertidorMontos" />	 											
				</h:outputText>			
				<h:outputText  rendered="#{item.efectuado.bdMontoEfectuado == null}" style="text-align: right"/>
			</rich:column>	
					
			<rich:column width="25px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio - item.efectuado.bdMontoEfectuado}"
							   rendered="#{item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>			
				<h:outputText rendered="#{item.efectuado.bdMontoEfectuado == null || item.efectuado.envioMonto == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio - item.efectuado.bdMontoEfectuado}"
							   rendered="#{item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>			
				<h:outputText rendered="#{item.efectuado.bdMontoEfectuado == null || item.efectuado.envioMonto == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio - item.efectuado.bdMontoEfectuado}"
							   rendered="#{item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>			
				<h:outputText rendered="#{item.efectuado.bdMontoEfectuado == null || item.efectuado.envioMonto == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
			</rich:column>			
			
			<rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}" 
							  rendered="#{item.yaEfectuado.envioMonto != null}">								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>			
				<h:outputText rendered="#{item.yaEfectuado.envioMonto == null}"/>
			</rich:column>		
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			
			<rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.yaEfectuado.bdMontoEfectuado}" rendered="#{item.yaEfectuado.bdMontoEfectuado != null}">						   								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText rendered="#{item.yaEfectuado.bdMontoEfectuado == null}"/>
			</rich:column>		
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			
			<rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.yaEfectuado.bdDiferencia}" rendered="#{item.yaEfectuado.bdDiferencia != null}">						   								
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText rendered="#{item.yaEfectuado.bdDiferencia == null}"/>
			</rich:column>		
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
							
			<rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio + item.yaEfectuado.envioMonto.bdMontoenvio}" 
								rendered="#{item.yaEfectuado.envioMonto != null && item.efectuado.envioMonto != null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
								rendered="#{item.yaEfectuado.envioMonto == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}" 
								rendered="#{item.efectuado.envioMonto == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
			</rich:column>
			
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
					
			<rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
				<h:outputText value="#{item.efectuado.bdMontoEfectuado + item.yaEfectuado.bdMontoEfectuado}" 
								rendered="#{item.yaEfectuado.bdMontoEfectuado != null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
				<h:outputText value="#{item.efectuado.bdMontoEfectuado}" 
								rendered="#{item.yaEfectuado.bdMontoEfectuado == null}">						
					<f:converter converterId="ConvertidorMontos" />
				</h:outputText>
			</rich:column>		
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
			<rich:column width="25px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			</rich:column>
						 	
		   <rich:column width="25px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
		   </rich:column>	   
		   <rich:column width="25px" style="text-align: center" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
		   		<div align="center">
		   			<h:outputText value="Enviado Agregado"/>
		   		</div>
		   </rich:column> 
		   <rich:column width="25px" style="text-align: center" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
		   		<div align="center">
		   			<h:outputText value="No socio"/>
		   		</div>
		   </rich:column> 
			<f:facet name="footer">									
				<rich:datascroller for="idGrillaTieneEfectuada" maxPages="15"/>								
			</f:facet>
		</rich:dataTable>	
	
	<rich:dataTable id="idGrillaDePlanillaEfectuado" var="item" sortMode="single" rows="15" 
		value="#{efectuadoController.listaEfectuadoConceptoComp}" rowKeyVar="rowKey"  
		 width="870px" rendered="#{efectuadoController.intEfectuado == applicationScope.Constante.intCesanteCAS}"> 		 
	   <f:facet name="header">
	   	<rich:columnGroup columnClasses="rich-sdt-header-cell">
				<rich:column colspan="4">
					<h:outputText value="Enviado:#{efectuadoController.nroEnviadoEmpezandoEfectuada} Efectuado:#{efectuadoController.nroEfectuadoEmpezandoEfectuada}" />
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalEnviadoEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalEfectuadoEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalDiferenciaEmpezandoEfectuada}">
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalesAEnviarEnEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>	
				
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalesAEfectuarEnEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
				</rich:column>			
				<rich:column breakBefore="true" rowspan="2" width="30px">
					<h:outputText value="Nro" />
				</rich:column>
				<rich:column rowspan="2" width="40px">
					<h:outputText value="Codigo" />
				</rich:column>
				<rich:column rowspan="2" width="350px">
					<h:outputText value="Nombre Completo" />
				</rich:column>
				<rich:column rowspan="2" width="50px">
					<h:outputText value="DNI" />
				</rich:column>
				<rich:column colspan="3">
					<h:outputText value="Planilla Procesada (#{efectuadoController.dtoDeEfectuado.strModalidad})" />
				</rich:column>
				<rich:column>
					<h:outputText value="Enviado" />
				</rich:column>
				<rich:column>
					<h:outputText value="Efectuado" />
				</rich:column>
				<rich:column>
					<h:outputText value="Observacion" />
				</rich:column>
				<rich:column breakBefore="true" width="50px">
					<h:outputText value="Enviado" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Efectuado" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Diferencia" />
				</rich:column>				
				<rich:column width="50px">
					<h:outputText value="Total" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Total" />
				</rich:column>
				<rich:column width="50px">
				</rich:column>
		</rich:columnGroup>
	   </f:facet>
	   <rich:column width="30px" style="text-align: left" 
	   				rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<div align="center">
				 <h:outputText value="#{rowKey+1}" />
			</div>							
		</rich:column>
		 <rich:column width="30px" style="text-align: left"
		 			  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<div align="center"> 
				<h:outputText value="#{rowKey+1}" />
			</div>							
		</rich:column>
		<rich:column width="30px" style="text-align: left" 
			rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			<div align="center"> 
				<h:outputText value="#{rowKey+1}" />
			</div>							
		</rich:column>		
		
		<rich:column width="60px" style="text-align: left" 
					rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.socio.id.intIdPersona}" />															
		</rich:column>
		<rich:column width="60px" style="text-align: left" 
					rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.socio.id.intIdPersona}" />															
		</rich:column>
		<rich:column width="60px" style="text-align: left" 
					rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.socio.id.intIdPersona}" />
		</rich:column>
						
		<rich:column width="300px" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
			</div>
		</rich:column>
		<rich:column width="300px" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
			</div>
		</rich:column>
		<rich:column width="300px" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																										
			</div>
		</rich:column>
				
		
		<rich:column width="70px" style="text-align: left" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.documento.strNumeroIdentidad}" />
		</rich:column>
		<rich:column width="70px" style="text-align: left" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.documento.strNumeroIdentidad}" />
		</rich:column>
		<rich:column width="70px" style="text-align: left" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.documento.strNumeroIdentidad}" />
		</rich:column>		
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" rendered="#{item.efectuado.envioMonto != null}" >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" rendered="#{item.efectuado.envioMonto != null}" >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" rendered="#{item.efectuado.envioMonto != null}" >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>		
		
		<rich:column width="30px" rendered="#{item.efectuado.bdMontoEfectuado != null && !item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:inputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right"
			 			onkeypress="return soloNumerosDecimalesPositivos(this)" 
			 			onkeyup="return extractNumber(this,2,false)" 
			 			onblur="extractNumber(this,2,false);">	
			 									
				<a4j:support event="onchange" reRender="idGrillaDePlanillaEfectuado"/>								
			</h:inputText>
		</rich:column>
		<rich:column style="text-align: right" width="30px" rendered="#{item.efectuado.bdMontoEfectuado != null && item.blnAgregarSocio}" 
					styleClass="columnaAgregarSocio">					 
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column style="text-align: right" width="30px" rendered="#{item.efectuado.bdMontoEfectuado != null && item.blnAgregarNoSocio}"
					 styleClass="columnaAgregarSocio">					 
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>		
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		
		<rich:column width="30px" style="text-align: right"  rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" rendered="#{item.efectuado.envioMonto != null}" >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" rendered="#{item.efectuado.envioMonto != null}" >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
					 rendered="#{item.blnAgregarNoSocio}">
		</rich:column>
		
		<rich:column width="70px" style="text-align: right" 
					 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}"    >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}"  >								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
		</rich:column>
		
		<rich:column width="70px"  rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
		</rich:column>
		<rich:column width="70px"  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
	   			<h:outputText value="Enviado Agregado"/>
	   		</div>
	   </rich:column>
	   <rich:column width="70px"  rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
	   			<h:outputText value="No Socio"/>
	   		</div>
	   </rich:column>	 	
		<f:facet name="footer">									
			<rich:datascroller for="idGrillaDePlanillaEfectuado" maxPages="15"/>								
		</f:facet>
	</rich:dataTable>
	 
	<rich:dataTable id="idGrillaNoTieneEfectuada" var="item" sortMode="single" rows="15" 
		value="#{efectuadoController.listaEfectuadoConceptoComp}" rowKeyVar="rowKey"  
		 width="940px" rendered="#{efectuadoController.intEfectuado == applicationScope.Constante.intHaberIncentivo
		 && efectuadoController.intEfectuado2 == applicationScope.Constante.intHaberIncentivoNotienEfectuado}"> 		 
	   <f:facet name="header">
	   	<rich:columnGroup columnClasses="rich-sdt-header-cell">
				<rich:column colspan="4" width="590px">
					<h:outputText value="Enviado: #{efectuadoController.nroEnviadoEmpezandoEfectuada} Efectuado: #{efectuadoController.nroEfectuadoEmpezandoEfectuada}" />
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalEnviadoEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalEfectuadoEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalDiferenciaEmpezandoEfectuada}">
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalEnviadoEnYaEfectuadaEnEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalesAEnviarEnEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>	
				
				<rich:column rowspan="1" width="50px" style="text-align: right">
					<h:outputText value="#{efectuadoController.totalesAEfectuarEnEmpezandoEfectuada}" >
							<f:converter converterId="ConvertidorMontos" />
					</h:outputText>
				</rich:column>
				
				<rich:column rowspan="1" width="50p	x" style="text-align: right">					
				</rich:column>
							
				<rich:column breakBefore="true" rowspan="2" width="10px">
					<h:outputText value="Nro" />
				</rich:column>
				<rich:column rowspan="2" width="60px">
					<h:outputText value="Codigo" />
				</rich:column>
				<rich:column rowspan="2" width="500px">
					<h:outputText value="Nombre Completo" />
				</rich:column>
				<rich:column rowspan="2" width="50px">
					<h:outputText value="DNI" />
				</rich:column>
				<rich:column colspan="3" width="150px">
					<h:outputText value="Planilla a Efectuar(#{efectuadoController.dtoDeEfectuado.strModalidad})" />
				</rich:column>
				<rich:column width="50px"> 
					<h:outputText value="Enviado (#{efectuadoController.dtoDeEfectuado.strModalidad2})" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Enviado" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Efectuado" />
				</rich:column>				
				<rich:column width="50px">
					<h:outputText  value="Obs." />
				</rich:column>				
				<rich:column breakBefore="true" width="50px">
					<h:outputText value="Enviado" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Efectuado" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Diferencia" />
				</rich:column>	
				<rich:column width="50px">
					<h:outputText value="Enviado" />
				</rich:column>			
				<rich:column width="50px">
					<h:outputText value="Total" />
				</rich:column>
				<rich:column width="50px">
					<h:outputText value="Total" />
				</rich:column>
				<rich:column width="50px">					
				</rich:column>
		</rich:columnGroup>
	   </f:facet>
	 	
	 	<rich:column width="30px" style="text-align: left"  
	 				 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<div align="center">
			  <h:outputText value="#{rowKey+1}" />
			</div>							
		</rich:column>
		<rich:column width="30px"  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
			  <h:outputText value="#{rowKey+1}" />
			</div>
	   </rich:column>
	   <rich:column width="30px"  rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
			  <h:outputText value="#{rowKey+1}" />
			</div>
	   </rich:column>
		
		<rich:column width="60px" style="text-align: left" 
			rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.socio.id.intIdPersona}" />															
		</rich:column>
		<rich:column width="60px" style="text-align: left"
					 rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.socio.id.intIdPersona}" />	
	   </rich:column>
	   <rich:column width="60px" style="text-align: left" 
	   				rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.socio.id.intIdPersona}" />	
	   </rich:column>
	 	
	 	<rich:column width="300px" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
				<h:outputText styleClass="msgInfo" style="font-weight:bold" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
			</div>
		</rich:column>						
		<rich:column width="300px" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
				<h:outputText  style="font-weight:bold" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
			</div>
		</rich:column>
		<rich:column width="300px" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
			<div align="left">
				<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"/>																
			</div>
		</rich:column>
		
		<rich:column width="70px" style="text-align: left" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.documento.strNumeroIdentidad}" />
		</rich:column>
		<rich:column width="70px"  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.documento.strNumeroIdentidad}" />
	   </rich:column>
	   <rich:column width="70px"  rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.documento.strNumeroIdentidad}" />
	   </rich:column>
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" 
				rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto != null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						   								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto != null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						   								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto != null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						   								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		
	 	<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto == null && !item.blnAgregarNoSocio}">
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto == null}">
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto == null}">
		</rich:column>
		
		<rich:column width="30px" rendered="#{!item.blnAgregarSocio &&  item.efectuado.bdMontoEfectuado != null && !item.blnAgregarNoSocio}">					 
			<h:inputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right"
			 onkeypress="return soloNumerosDecimalesPositivos(this)" 
			 onkeyup="return extractNumber(this,2,false)" onblur="extractNumber(this,2,false);">	
			 <a4j:support event="onchange" reRender="idGrillaNoTieneEfectuada"/>										
			</h:inputText>			
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarSocio &&  item.efectuado.bdMontoEfectuado != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}">
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>	
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarNoSocio &&  item.efectuado.bdMontoEfectuado != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}">
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>	
		</rich:column>
		
		<rich:column width="30px" style="text-align: right"  rendered="#{!item.blnAgregarSocio && item.efectuado.bdMontoEfectuado == null && !item.blnAgregarNoSocio}">
		</rich:column>		
		<rich:column width="30px" style="text-align: right"  rendered="#{item.blnAgregarSocio && item.efectuado.bdMontoEfectuado == null}" styleClass="columnaAgregarSocio">
		</rich:column>
		<rich:column width="30px" style="text-align: right"  rendered="#{item.blnAgregarNoSocio && item.efectuado.bdMontoEfectuado == null}" styleClass="columnaAgregarSocio">
		</rich:column>
				
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>		
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado != null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado != null && !item.blnAgregarNoSocio}">
			<h:outputText value="-#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>	
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado != null}">
			<h:outputText value="-#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado != null}">
			<h:outputText value="-#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado == null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>	
		</rich:column>	
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado == null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto != null && item.efectuado.bdMontoEfectuado == null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		
		<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado == null && !item.blnAgregarNoSocio}">
		</rich:column>	
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado == null}">
		</rich:column>
		<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto == null && item.efectuado.bdMontoEfectuado == null}">
		</rich:column>
		
		<rich:column width="70px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.yaEfectuado.envioMonto != null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}" >					  								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarSocio && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}" >					  								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}" >					  								
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>			
		</rich:column>
		
		
		<rich:column width="70px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.yaEfectuado.envioMonto == null && !item.blnAgregarNoSocio}">
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarSocio && item.yaEfectuado.envioMonto == null}" styleClass="columnaAgregarSocio">
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.yaEfectuado.envioMonto == null}" styleClass="columnaAgregarSocio">
		</rich:column>
				
		<rich:column width="30px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto != null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio + item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>		
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio + item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>		
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio + item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>		
		</rich:column>
			
		<rich:column width="30px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto == null && !item.blnAgregarNoSocio}">
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto == null}" styleClass="columnaAgregarSocio">
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto == null}" styleClass="columnaAgregarSocio">
		</rich:column>
		
		<rich:column width="30px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto != null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>		
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto == null && item.yaEfectuado.envioMonto != null}" styleClass="columnaAgregarSocio">
			<h:outputText value="#{item.yaEfectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		
		<rich:column width="30px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto == null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" styleClass="columnaAgregarSocio" style="text-align: right" rendered="#{item.blnAgregarSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto == null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="30px" styleClass="columnaAgregarSocio" style="text-align: right" rendered="#{item.blnAgregarNoSocio && item.efectuado.envioMonto != null && item.yaEfectuado.envioMonto == null}">
			<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}">						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		
		<rich:column width="70px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.bdMontoEfectuado!= null && !item.blnAgregarNoSocio}">
			<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarSocio  && item.efectuado.bdMontoEfectuado!= null}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
	   </rich:column>
	   <rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarNoSocio  && item.efectuado.bdMontoEfectuado!= null}" styleClass="columnaAgregarSocio">
	   		<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
				<f:converter converterId="ConvertidorMontos" />
			</h:outputText>
	   </rich:column>
	   
	   <rich:column width="70px" style="text-align: right" rendered="#{!item.blnAgregarSocio && item.efectuado.bdMontoEfectuado== null && !item.blnAgregarNoSocio}">
	   </rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarSocio  && item.efectuado.bdMontoEfectuado == null}" styleClass="columnaAgregarSocio">
	    </rich:column>
		<rich:column width="70px" style="text-align: right" rendered="#{item.blnAgregarNoSocio  && item.efectuado.bdMontoEfectuado == null}" styleClass="columnaAgregarSocio">
	    </rich:column>	 
	    
		<rich:column width="70px" style="text-align: right" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">		
		</rich:column>
		
		<rich:column width="70px"  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
	   			<h:outputText value="Enviado Agregado"/>
	   		</div>
	   </rich:column>
	   
	   <rich:column width="70px"  rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
	   		<div align="center">
	   			<h:outputText value="No Socio"/>
	   		</div>
	   </rich:column>
		<f:facet name="footer">									
			<rich:datascroller for="idGrillaNoTieneEfectuada" maxPages="15"/>								
		</f:facet>
	</rich:dataTable>		
	
	<h:outputLink value="#">
		<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"
						onclick="Richfaces.showModalPanel('idPopupAbreviatura')" />  
	</h:outputLink>	
					
				</h:panelGroup>
			</rich:panel>
		</h:panelGroup>
		<h:panelGroup id="contPanelInferiorConArchivo">
			<rich:panel rendered="#{efectuadoController.mostrarPanelInferiorConArchivo}"
						style="border:1px solid #17356f;background-color:#DEEBF5;">
				<h:panelGroup rendered="#{efectuadoController.examinar}">
					<h:panelGrid columns="3" id="panelValidarConArchivo">
						<rich:column style="width:90px">
							<h:outputText value="Seleccionar Archivo:"/>
						</rich:column>
						
						<rich:column id="IDColDocumento6" width="150">
							<h:inputText readonly="true" size="25" style="FONT-SIZE: medium; background-color: #BFBFBF;"
										rendered="#{empty efectuadoController.archivo.strNombrearchivo}" />							
						</rich:column>
						
						<rich:column>
							<a4j:commandButton value="Examinar" styleClass="btnEstilos" style="width:90px"
											   rendered="#{empty efectuadoController.archivo.strNombrearchivo}"										  
											   reRender="panelValidarConArchivo,fmUpload,contPanelInferiorConArchivo"
											   oncomplete="Richfaces.showModalPanel('pUploadEfectuadoConArchivo')" >										
							</a4j:commandButton>											   							
						</rich:column>

					</h:panelGrid>			
					
					
					<h:panelGrid columns="2">
						<rich:column style="width:90px">
							<h:outputText value="Tipo Socio:"/>
						</rich:column>
						<rich:column>
							<h:selectOneRadio disabled="true">
								<f:selectItem itemValue="Activo" itemLabel="Activo"/>
								<f:selectItem itemValue="Cesante" itemLabel="Cesante"/>
							</h:selectOneRadio>
						</rich:column>
					</h:panelGrid>
					<rich:spacer  height="6px"/>
					<h:panelGrid>
						<rich:column width="950">
							<a4j:commandButton disabled="true" value="Validar" styleClass="btnEstilos" style="width:950px" />
						</rich:column>						
					</h:panelGrid>					
				</h:panelGroup>

				<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
							rendered="#{efectuadoController.examinar}"
									styleClass="rich-tabcell-noborder"  id="panelListaValidarConArchivoInicial">
						<a4j:repeat value="#{efectuadoController.listaMensajes}" var="item">
							<h:panelGrid rendered="#{not empty item.strMsg}" columns="1">
								<rich:column style="text-align: left">
									<table>
										<tr>
											<td>
												<h:outputText value="#{item.strMsg}" styleClass="msgError"
															  style="font-weight:bold"/>
											</td>
										</tr>
									</table>
								</rich:column>
							</h:panelGrid>
						</a4j:repeat>
					</h:panelGroup>

				<h:panelGroup rendered="#{efectuadoController.datosValidadosConArchivo}">
					<h:panelGrid columns="4">
						<rich:column width="130">
							<h:outputText value="Sucursal: "/>
						</rich:column>
						
						<rich:column width="100">
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.juridicaSucursal.strRazonSocial}"
										 readonly="true" size="36" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						
						<rich:column width="150">
							<h:outputText value="Unidad Ejecutora: "/>
						</rich:column>
						
						<rich:column width="200">
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.estructura.juridica.strRazonSocial}"
										 readonly="true" style="background-color: #BFBFBF;font-weight:bold;"
										size="#{fn:length(efectuadoController.dtoDeEfectuadoConArchivo.estructura.juridica.strRazonSocial)}" />
						</rich:column>
					</h:panelGrid>
					<h:panelGrid columns="6">
						<rich:column width="130">
							<h:outputText value="Archivo seleccionado: "/>							
						</rich:column>
						
						<rich:column width="100">
							<h:inputText value="#{efectuadoController.archivo.strNombrearchivo}" size="36"
										 rendered="#{efectuadoController.datosValidadosConArchivo}"
										 readonly="true" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						
						<rich:column >
							<h:outputText value="Tipo socio: "/>
						</rich:column>
						
						<rich:column>
							<h:selectOneRadio value="#{efectuadoController.strTipoSocioConArchivo}">
								<f:selectItem itemValue="Activo" itemLabel="Activo"/>
								<f:selectItem itemValue="Cesante" itemLabel="Cesante"/>
							</h:selectOneRadio>
						</rich:column>
						
						<rich:column>
							<h:outputText value="Modalidad: "/>
						</rich:column>
						
						<rich:column>
							<h:outputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.strModalidad}"
											style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
					</h:panelGrid>
					<rich:spacer height="12px"/>
					<h:panelGrid>
						<rich:column width="950">
							<a4j:commandButton  action="#{efectuadoController.validarConArchivo}"
												reRender="contPanelInferiorConArchivo,
													      panelMensajeValidarConArchivo,
													      panelListaValidarConArchivo"
												value="Validar" styleClass="btnEstilos" style="width:950px" />
						</rich:column>						
					</h:panelGrid>
					<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
									styleClass="rich-tabcell-noborder"  id="panelMensajeValidarConArchivo">
							<h:outputText value="#{efectuadoController.mensajeOperacion}" 
								styleClass="msgInfo"
								style="font-weight:bold"
								rendered="#{efectuadoController.mostrarMensajeExito}"/>
							<h:outputText value="#{efectuadoController.mensajeOperacion}" 
								styleClass="msgError"
								style="font-weight:bold"
								rendered="#{efectuadoController.mostrarMensajeError}"/>
					</h:panelGroup>
					<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
									styleClass="rich-tabcell-noborder"  id="panelListaValidarConArchivo">
						<a4j:repeat value="#{efectuadoController.listaMensajes}" var="item">
							<h:panelGrid rendered="#{not empty item.strMsg}" columns="1">
								<rich:column style="text-align: left">
									<table>
										<tr>
											<td>
												<h:outputText value="#{item.strMsg}" styleClass="msgError"
															  style="font-weight:bold"/>
											</td>
										</tr>
									</table>
								</rich:column>
							</h:panelGrid>
						</a4j:repeat>
					</h:panelGroup>									
				</h:panelGroup>
				<h:panelGroup rendered="#{efectuadoController.datosValidados2ConArchivo}">
					<h:panelGrid columns="4">
						<rich:column width="60">
							<h:outputText value="Sucursal: "/>
						</rich:column>
						
						<rich:column width="100">
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.juridicaSucursal.strRazonSocial}"
										 readonly="true" size="36" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
						
						<rich:column width="150">
							<h:outputText value="Unidad Ejecutora: "/>
						</rich:column>
						
						<rich:column width="275">
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.estructura.juridica.strRazonSocial}"
											
											size="#{fn:length(efectuadoController.dtoDeEfectuadoConArchivo.estructura.juridica.strRazonSocial)}"
											readonly="true" size="45" style="background-color: #BFBFBF;font-weight:bold;"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="9">
						<rich:column  width="60">
							<h:outputText value="Mes: "/>							
						</rich:column>
						
						<rich:column width="100">
							<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" disabled="true"
							style="background-color: #BFBFBF;font-weight:bold; width:75px;"
							property="#{efectuadoController.dtoDeEfectuadoConArchivo.intPeriodoMes}"/>
						</rich:column>
						
						<rich:column>
							<h:outputText value="Año: "/>							
						</rich:column>
						
						<rich:column>						
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.intPeriodoAnio}"
							     style="background-color: #BFBFBF;font-weight:bold; width:40px;" disabled="true"/>
						</rich:column>
						
						<rich:column >						
							<h:outputText/>
						</rich:column>							
										 
						<rich:column >						
							<h:outputText value="Tipo socio: "/>
						</rich:column>
						
						<rich:column>
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.strTipoSocio}"
										 style="background-color: #BFBFBF;font-weight:bold;width:55px;" 
										 disabled="true"/>
						</rich:column>
						
						<rich:column>
							<h:outputText value="Modalidad: "/>
						</rich:column>
						
						<rich:column>
							<h:inputText value="#{efectuadoController.dtoDeEfectuadoConArchivo.strModalidad}"							
										 style="background-color: #BFBFBF;font-weight:bold;width:75px;"
										 disabled="true" />
						</rich:column>
					</h:panelGrid>
					<rich:spacer height="12px"/>
					
					<rich:dataTable id="idGrillaConArchivo" var="item" sortMode="single" rows="15" 
					value="#{efectuadoController.listaEfectuadoConceptoComp}" rowKeyVar="rowKey"
					rendered="#{not empty efectuadoController.listaEfectuadoConceptoComp}"  
		 			width="870px"> 		 
				   <f:facet name="header">
				   	<rich:columnGroup columnClasses="rich-sdt-header-cell">
							<rich:column colspan="4">
								<h:outputText value="Enviado:#{efectuadoController.nroEnviadoEmpezandoEfectuada} Efectuado:#{efectuadoController.nroEfectuadoEmpezandoEfectuada}" />
							</rich:column>
							<rich:column rowspan="1" width="50px" style="text-align: right">
								<h:outputText value="#{efectuadoController.totalEnviadoEmpezandoEfectuada}" >
										<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>
							<rich:column rowspan="1" width="50px" style="text-align: right">
								<h:outputText value="#{efectuadoController.totalEfectuadoEmpezandoEfectuada}" >
										<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>
							<rich:column rowspan="1" width="50px" style="text-align: right">
								<h:outputText value="#{efectuadoController.totalDiferenciaEmpezandoEfectuada}">
										<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>
							
							<rich:column rowspan="1" width="50px" style="text-align: right">
								<h:outputText value="#{efectuadoController.totalesAEnviarEnEmpezandoEfectuada}" >
										<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>	
							
							<rich:column rowspan="1" width="50px" style="text-align: right">
								<h:outputText value="#{efectuadoController.totalesAEfectuarEnEmpezandoEfectuada}" >
										<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>
							<rich:column rowspan="1" width="50px" style="text-align: right">
							</rich:column>			
							<rich:column breakBefore="true" rowspan="2" width="30px">
								<h:outputText value="Nro" />
							</rich:column>
							<rich:column rowspan="2" width="40px">
								<h:outputText value="Codigo" />
							</rich:column>
							<rich:column rowspan="2" width="350px">
								<h:outputText value="Nombre Completo" />
							</rich:column>
							<rich:column rowspan="2" width="50px">
								<h:outputText value="DNI" />
							</rich:column>
							<rich:column colspan="3">
								<h:outputText value="Planilla Procesada (#{efectuadoController.dtoDeEfectuado.strModalidad})" />
							</rich:column>
							<rich:column>
								<h:outputText value="Enviado" />
							</rich:column>
							<rich:column>
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column>
								<h:outputText value="Observacion" />
							</rich:column>
							<rich:column breakBefore="true" width="50px">
								<h:outputText value="Enviado" />
							</rich:column>
							<rich:column width="50px">
								<h:outputText value="Efectuado" />
							</rich:column>
							<rich:column width="50px">
								<h:outputText value="Diferencia" />
							</rich:column>				
							<rich:column width="50px">
								<h:outputText value="Total" />
							</rich:column>
							<rich:column width="50px">
								<h:outputText value="Total" />
							</rich:column>
							<rich:column width="50px">
							</rich:column>
					</rich:columnGroup>
				   </f:facet>
				   <rich:column width="30px" style="text-align: left" 
				   				rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<div align="center"> 
							<h:outputText value="#{rowKey+1}" />
						</div>							
					</rich:column>
					 <rich:column width="30px" style="text-align: left" 
					 			  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
						<div align="center"> 
							<h:outputText value="#{rowKey+1}" />
						</div>							
					</rich:column>
					<rich:column width="30px" style="text-align: left" 
								 rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
						<div align="center"> 
							<h:outputText value="#{rowKey+1}" />
						</div>							
					</rich:column>		
					
					<rich:column width="60px" style="text-align: left" 
								rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.socio.id.intIdPersona}" />															
					</rich:column>
					<rich:column width="60px" style="text-align: left" 
								 rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
						<h:outputText value="#{item.socio.id.intIdPersona}" />															
					</rich:column>
					<rich:column width="60px" style="text-align: left" 
								 rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
						<h:outputText value="#{item.socio.id.intIdPersona}" />	
					</rich:column>
									
					<rich:column width="300px" rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<div align="left">
							<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																
							<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
							<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
							<h:outputText styleClass="msgInfo" style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
						</div>
					</rich:column>
					<rich:column width="300px" rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
						<div align="left">
							<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																
							<h:outputText  style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnLIC}" value=" (LIC)" />								
							<h:outputText  style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnDJUD}" value=" (DJUD)" />
							<h:outputText  style="font-weight:bold font-size:10px" rendered="#{item.efectuado.blnCartaAutorizacion}" value=" (100%)" />																								
						</div>
					</rich:column>
					<rich:column width="300px" rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
						<div align="left">
							<h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}" style="font-weight:bold font-size:10px"/>																										
						</div>
					</rich:column>							
					
					<rich:column width="70px" style="text-align: left" 
								 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.documento.strNumeroIdentidad}" />
					</rich:column>
					<rich:column width="70px" style="text-align: left" 
								 rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
						<h:outputText value="#{item.documento.strNumeroIdentidad}" />
					</rich:column>
					<rich:column width="70px" style="text-align: left" 
								rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
						<h:outputText value="#{item.documento.strNumeroIdentidad}" />
					</rich:column>		
					
					<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" 
								 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
									  rendered="#{item.efectuado.envioMonto != null}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								 rendered="#{item.blnAgregarSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
									  rendered="#{item.efectuado.envioMonto != null}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								 rendered="#{item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
									  rendered="#{item.efectuado.envioMonto != null}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>		
					
					<rich:column width="30px" rendered="#{item.efectuado.bdMontoEfectuado != null && !item.blnAgregarSocio && !item.blnAgregarNoSocio}">					 
						<h:inputText value="#{item.efectuado.bdMontoEfectuado}" style="text-align: right" readonly="TRUE"
						 			 onkeypress="return soloNumerosDecimalesPositivos(this)" 
						 			 onkeyup="return extractNumber(this,2,false)" 
						 			 onblur="extractNumber(this,2,false);">							
							<a4j:support event="onchange" reRender="idGrillaDePlanillaEfectuado"/>								
						</h:inputText>
					</rich:column>
					<rich:column style="text-align: right" width="30px" styleClass="columnaAgregarSocio"
								 rendered="#{item.efectuado.bdMontoEfectuado != null && item.blnAgregarSocio}">								 				 
						<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column style="text-align: right" width="30px" 
								rendered="#{item.efectuado.bdMontoEfectuado != null && item.blnAgregarNoSocio}" 
								styleClass="columnaAgregarSocio">					 
						<h:outputText value="#{item.efectuado.bdMontoEfectuado}" >						
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>		
					
					<rich:column width="30px" style="text-align: right" styleClass="columnaEfectuado" 
								 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								rendered="#{item.blnAgregarSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								rendered="#{item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio-item.efectuado.bdMontoEfectuado}" >						
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					
					<rich:column width="30px" style="text-align: right"  
								 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
									  rendered="#{item.efectuado.envioMonto != null}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								rendered="#{item.blnAgregarSocio}">
						<h:outputText value="#{item.efectuado.envioMonto.bdMontoenvio}" 
									 rendered="#{item.efectuado.envioMonto != null}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="30px" style="text-align: right" styleClass="columnaAgregarSocio" 
								rendered="#{item.blnAgregarNoSocio}">
					</rich:column>
					
					<rich:column width="70px" style="text-align: right" 
								 rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
						<h:outputText value="#{item.efectuado.bdMontoEfectuado}" rendered="#{item.efectuado.bdMontoEfectuado != 0}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: right" 
								 rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
						<h:outputText value="#{item.efectuado.bdMontoEfectuado}" 
									 rendered="#{item.efectuado.bdMontoEfectuado != 0}" >								
							<f:converter converterId="ConvertidorMontos" />
						</h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: right" 
								rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
					</rich:column>
					
					<rich:column width="70px"  rendered="#{!item.blnAgregarSocio && !item.blnAgregarNoSocio}">
					</rich:column>
					<rich:column width="70px"  rendered="#{item.blnAgregarSocio}" styleClass="columnaAgregarSocio">
				   		<div align="center">
				   			<h:outputText value="Enviado Agregado"/>
				   		</div>
				   </rich:column>
				   <rich:column width="70px"  rendered="#{item.blnAgregarNoSocio}" styleClass="columnaAgregarSocio">
				   		<div align="center">
				   			<h:outputText value="No Socio"/>
				   		</div>
				   </rich:column>	 	
					<f:facet name="footer">									
						<rich:datascroller for="idGrillaConArchivo" maxPages="15"/>								
					</f:facet>
				</rich:dataTable>
				<h:outputLink value="#">
						<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"
									   onclick="Richfaces.showModalPanel('idPopupAbreviatura')"/>  
				</h:outputLink>		
										
				</h:panelGroup>
			</rich:panel>
		</h:panelGroup>
	</rich:panel>
</h:form>