<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi				-->
	<!-- Autor     :    							-->
	<!-- Modulo    :   Solicitud de Liquidacion     -->
	<!-- Fecha     :    28/01/2012            		-->



	<h:form>
		<h:outputText value="#{solicitudLiquidacionController.limpiarLiquidacion}"/>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        <h:panelGrid id="pgTitulo" style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="INGRESO DE SOLICITUD DE LIQUIDACIÓN" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    	</h:panelGrid>        	

		<h:panelGrid id="pgBusq0" columns="1" border="0">
			<rich:column>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq1" columns="7" border="0">
			<rich:column width="120px">
				<h:outputText value="Datos de Socio" styleClass="estiloLetra1" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudLiquidacionController.intBusquedaTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{solicitudLiquidacionController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Nro. Solicitud	:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:inputText value="#{solicitudLiquidacionController.strBusqNroSol}" />
			</rich:column>
		</h:panelGrid>

		<h:panelGrid id="pgBusq2" columns="9" border="0">
			<rich:column width="120px">
				<h:outputText value="Sucursal Admin." styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu style="width:150px" value="#{solicitudLiquidacionController.intBusqSucursal}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel" value="#{solicitudLiquidacionController.listSucursal}"
						itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strSiglas}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="70px">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						value="#{solicitudLiquidacionController.intBusqTipoLiquidacion}"
						style="width: 170px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{solicitudLiquidacionController.listaTablaTipoDocumento}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			<rich:column>
				<h:outputText value="Estado :" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{solicitudLiquidacionController.intBusqEstado}">
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden" />
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudLiquidacionController.dtBusqFechaEstadoDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{solicitudLiquidacionController.dtBusqFechaEstadoHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid id="pgBusq3" columns="11" border="0">
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="100px">
			</rich:column>
			<rich:column width="150px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column width="120px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{solicitudLiquidacionController.listarSolicitudLiquidacion}" styleClass="btnEstilos" reRender="panelSolicitudTablaResultados"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{solicitudLiquidacionController.limpiarFiltros}" styleClass="btnEstilos" reRender="pgBusq1,pgBusq2,panelSolicitudTablaResultados,tblResultado"/>
			</rich:column> 
		</h:panelGrid>


            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelSolicitudTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{solicitudLiquidacionController.listaExpedienteLiquidacionComp}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="970px" 
					height="180px" 
					align="center">
					
					
					<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column rowspan="2" width="15px" />
						<rich:column rowspan="2" width="90px">
							<h:outputText value="Nro. de Cuenta" />
						</rich:column>
						<rich:column rowspan="2" width="180px">
							<h:outputText value="Nombre Completo" />
						</rich:column>
						<rich:column rowspan="2" width="60px">
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="110px">
							<h:outputText value="Sucursal" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Tipo de Solicitud" />
						</rich:column>
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Monto Liquidación" />
						</rich:column>
						
						<rich:column rowspan="2" width="80px">
							<h:outputText value="Estado de Solicitud" />
						</rich:column>
						<rich:column colspan="3">
							<h:outputText value="Fechas" />
						</rich:column>
						<rich:column breakBefore="true">
							<h:outputText value="Requisito" />
						</rich:column>
						<rich:column>
							<h:outputText value="Solicitud" />
						</rich:column>
						<rich:column>
							<h:outputText value="Autorización" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				
				
				<rich:column width="15px">
				<div align="center">
					<h:outputText value="#{rowKey+1}" />
				</div>
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.expedienteLiquidacion.strNroCuentaExpediente}"/>
				</rich:column>
				<rich:column width="180px">
					<h:outputText value="#{item.expedienteLiquidacion.strNombreCompletoPersona}" title="#{item.expedienteLiquidacion.strNombreCompletoPersona}"/> 
				</rich:column>
				<rich:column width="60px">
						<h:outputText value="#{item.expedienteLiquidacion.id.intItemExpediente}" />
				</rich:column>
				<rich:column width="110px">
					<tumih:outputText value="#{solicitudLiquidacionController.listSucursal}" 
                                      itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
                                      property="#{item.expedienteLiquidacion.intSucursalEstadoInicial}"/>
				</rich:column>
				
				<rich:column width="80px">
									     <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUBOPERACION}"
										itemValue="intIdDetalle"
										itemLabel="strDescripcion"
										property="#{item.expedienteLiquidacion.intParaSubTipoOperacion}"/>
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="#{item.expedienteLiquidacion.bdMontoBrutoLiquidacion}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
				</rich:column>
				
				
				
				<rich:column width="80px">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOSOLICPRESTAMO}" 
                                      itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                      property="#{item.expedienteLiquidacion.intEstadoCreditoUltimo}"/>
				</rich:column>
				<rich:column width="95px">
					<h:outputText value="#{item.strFechaRequisito}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaSolicitud}" />
				</rich:column>
				<rich:column width="90px">
					<h:outputText value="#{item.strFechaAutorizacion}" />
			</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>
					</f:facet>
					
					<a4j:support event="onRowClick" reRender="mpUpDelSolLiquidacion,frmSolicitudLiquidacionModalPanel"
					actionListener="#{solicitudLiquidacionController.seleccionarRegistro}">
                       	<f:attribute name="item" value="#{item}"/>
                	 </a4j:support>
                	 
            	</rich:extendedDataTable>
         	</h:panelGrid>
         	
         	<h:panelGroup layout="block" style="margin:0 auto; width:580px">
				<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelSolLiquidacion')">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver, Editar, Anular o Imprimir Formatos una SOLICITUD DE LIQUIDACION hacer click en el Registro" style="color:#8ca0bd"/>
			</h:panelGroup>
				
				
				
				
				<h:panelGrid id="pgMsgErrorInicial">
					<h:outputText value="#{solicitudLiquidacionController.strMsgTxtProcedeEvaluacion}" styleClass="msgError"/>
					<h:outputText value="#{solicitudLiquidacionController.strMsgTxtProcedeEvaluacion1}" styleClass="msgError"/>
				</h:panelGrid>

        <h:panelGrid id="divPanelControlLiquidacion" columns="3"> 
			<a4j:commandButton value="Nuevo" actionListener="#{solicitudLiquidacionController.nuevaSolicitudLiquidacion}" styleClass="btnEstilos"  
			  					reRender="divFormLiquidacion, pgSolicitudLiquidacion,pgMsgErrorInicial"/>
			<a4j:commandButton value="Guardar" actionListener="#{solicitudLiquidacionController.grabarSolicitud}" styleClass="btnEstilos"
								reRender="divFormLiquidacion, pgSolicitudLiquidacion,pgMsgErrorInicial"/>
			<a4j:commandButton value="Cancelar" actionListener="#{solicitudLiquidacionController.cancelarGrabarSolicitud}" styleClass="btnEstilos"
								reRender="divFormLiquidacion, pgSolicitudLiquidacion,pgMsgErrorInicial,divFormSolicitudLiquidacion, pgViewSolicitudLiquidacion">
			</a4j:commandButton>
		</h:panelGrid>
		    
		<br/>
		  
		<h:panelGroup layout="block" id="divFormLiquidacion"> 
			<rich:panel style="border:1px solid #17356f; width: 966px; background-color:#DEEBF5">
			<h:panelGroup layout="block" rendered="#{solicitudLiquidacionController.blnShowValidarDatos}">
				<h:panelGrid columns="7">
					<rich:column style="border: none">
						<h:outputText value="Tipo de Relación : "></h:outputText>
					</rich:column>
					
					<rich:column>
						<h:selectOneMenu value="#{solicitudLiquidacionController.intTipoRelacion}">
							<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
							<tumih:selectItems var="sel" value="#{solicitudLiquidacionController.listaTipoRelacion}" 
							            		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							            		propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
	
					<rich:column width="100">
						<h:outputText value="Tipo de Persona: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu value="#{solicitudLiquidacionController.personaValida.intTipoPersonaCod}"
										onchange="selecTipoPersonaLiquidacion(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="144">
						<h:outputText value="Número de Documento: "></h:outputText>
					</rich:column>
					<rich:column width="120">
						<h:selectOneMenu id="cboTipoIdentidadLiquidacion" style="width:110px" value="#{solicitudLiquidacionController.personaValida.documento.intTipoIdentidadCod}">
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{solicitudLiquidacionController.listDocumentoBusq}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												propertySort="intOrden"/>
						</h:selectOneMenu>  
					</rich:column>
					<rich:column>
						<h:inputText size="15" value="#{solicitudLiquidacionController.personaValida.documento.strNumeroIdentidad}"></h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid style="width: 100%">
					<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
									   actionListener="#{solicitudLiquidacionController.validarDatos}" reRender="divFormLiquidacion"/>
				</h:panelGrid>
				
				<rich:spacer height="10"></rich:spacer>
				
				<h:panelGrid id="pgMsgErrorValidarDatos">
					<h:outputText value="#{solicitudLiquidacionController.strMsgErrorValidarDatos}" styleClass="msgError"/>
				</h:panelGrid>
			
			</h:panelGroup>
			
			<h:panelGroup rendered="#{solicitudLiquidacionController.strSolicitudLiquidacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
				<a4j:include id="divFormSolicitudLiquidacion" viewId="/pages/liquidacion/solicitudLiquidacionEdit.jsp" layout="block"
			 			rendered="#{solicitudLiquidacionController.blnShowDivFormSolicitudLiquidacion}"/> 
			
			</h:panelGroup>
			<h:panelGroup  id="pgViewSolicitudLiquidacion" rendered="#{solicitudLiquidacionController.strSolicitudLiquidacion == applicationScope.Constante.MANTENIMIENTO_ELIMINAR}">
				<a4j:outputPanel id="opViewSolicitudLiquidacion">
					<a4j:include viewId="/pages/liquidacion/solicitudLiquidacionView.jsp"/>
				</a4j:outputPanel>	 			
	 		</h:panelGroup>

			</rich:panel>
		</h:panelGroup>
         	
        </rich:panel>
   </h:form>
         	