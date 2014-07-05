<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->		

<a4j:include viewId="/pages/solicitudPersonal/popup/adjuntarDocumento.jsp"/>
<a4j:include viewId="/pages/solicitudPersonal/popup/buscarPersona.jsp"/>
<a4j:include viewId="/pages/solicitudPersonal/popup/agregarSolicitudPersonalDetalle.jsp"/>
<a4j:include viewId="/pages/solicitudPersonal/popup/buscarPlanCuenta.jsp"/>
<a4j:include viewId="/pages/solicitudPersonal/popup/alertaRegistro.jsp"/>

<h:form>
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="SOLICITUD DE PAGO PERSONAL" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="11">
				<rich:column width="110">
					<h:outputText value="Tipo de Documento :"/>
				</rich:column>
				<rich:column width="210">
					<h:selectOneMenu
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intParaDocumentoGeneral}"
						style="width: 208px;">
						<f:selectItem itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{solicitudPersonalController.listaTablaDocumentoGeneral}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120">
					<h:selectOneMenu
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intTipoBusqueda}"
						style="width: 120px;">
						<f:selectItem itemValue="#{applicationScope.Constante.BUSCAR_SOLICITUDPP}" itemLabel="De Pago"/>
						<f:selectItem itemValue="#{applicationScope.Constante.BUSCAR_DETALLESOLICITUDPP}" itemLabel="De Detalle"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{solicitudPersonalController.personaFiltroBusqueda.intEstadoCod}"
						style="width: 100px;">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_RUC}" itemLabel="RUC"/>
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE}" itemLabel="Nombre"/>
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_DNI}" itemLabel="DNI"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120">
					<h:inputText value="#{solicitudPersonalController.personaFiltroBusqueda.strEtiqueta}"/>
				</rich:column>
				<rich:column width="35">
					<h:outputText value="Fecha :"/>
				</rich:column>
				<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{solicitudPersonalController.solicitudPersonalFiltro.tsFechaRegistro}"  
						inputSize="10" 
						converter="calendarTimestampConverter"
						showApplyButton="true"/>
            	</rich:column>            	
				<rich:column width="45">
					<h:outputText value="Estado :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intParaEstado}"
						style="width: 100px;">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_TODOS}" itemLabel="Seleccionar"/>
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}" itemLabel="Activo"/>
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}" itemLabel="Anulado"/>
					</h:selectOneMenu>
				</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="11" id="panelBusqueda">
				<rich:column width="110">
					<h:outputText value="Periodo :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intAño}"
						style="width: 100px;">
						<f:selectItem itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{solicitudPersonalController.listaAnios}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="panelBusqueda"/>	
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="102">
					<h:selectOneMenu
						rendered="#{solicitudPersonalController.solicitudPersonalFiltro.intAño!=applicationScope.Constante.OPCION_SELECCIONAR}"
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intMes}"
						style="width: 100px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
							propertySort="intOrden"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
					<h:selectOneMenu
						rendered="#{solicitudPersonalController.solicitudPersonalFiltro.intAño==applicationScope.Constante.OPCION_SELECCIONAR}"
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intMes}"
						disabled="true"
						style="width: 100px;">
						<f:selectItem itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}" itemLabel="Seleccionar"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Estado de Pago :"/>
				</rich:column>
				<rich:column width="120">
					<h:selectOneMenu
						value="#{solicitudPersonalController.solicitudPersonalFiltro.intParaEstadoPago}"
						style="width: 120px;">
						<f:selectItem itemValue="#{applicationScope.Constante.OPCION_SELECCIONAR}" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOPAGO}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="130" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultados,panelMensaje"
                    	action="#{solicitudPersonalController.buscar}" 
                    	style="width:130px"/>
            	</rich:column>
			</h:panelGrid>
            
            <rich:spacer height="12px"/>
                
            <h:panelGrid id="panelTablaResultados">
	        	<rich:extendedDataTable id="tblResultado" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{solicitudPersonalController.listaSolicitudPersonal}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                                
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemSolicitudPersonal}"/>
                    </rich:column>
                    <rich:column width="130" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Documento"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaDocumentoGeneral}"/>
                    </rich:column>
					<rich:column width="200" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Entidad de Pago"/>
                      	</f:facet>
                      	<h:outputText
                      		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
                      		value="#{item.persona.strRuc} - #{item.persona.juridica.strRazonSocial}" 
                      		title="#{item.persona.strRuc} - #{item.persona.juridica.strRazonSocial}"/>
                      	<h:outputText
                      		rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
                      		value="#{item.persona.documento.strNumeroIdentidad} - #{item.persona.natural.strNombreCompleto}" 
                      		title="#{item.persona.documento.strNumeroIdentidad} - #{item.persona.natural.strNombreCompleto}"/>	
                    </rich:column>
                  	<rich:column width="140" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strRazonSocial} - #{item.subsucursal.strDescripcion}"
                      		title="#{item.sucursal.juridica.strRazonSocial} - #{item.subsucursal.strDescripcion}"/>
                    </rich:column>
                    <rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
                      		<f:convertDateTime pattern="dd/MM/yyyy" />
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="80" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Operación"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_OPERACIONPAGOPLANILLA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaAgrupacionPago}"/>
                    </rich:column>
                    <rich:column width="60" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Periodo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.intPeriodoPago}"/>
                  	</rich:column>
                    <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotalSolicitud}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                  	</rich:column>
                  	 <rich:column width="60" style="text-align: left">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoMoneda}"/>
                  	</rich:column>
                    <rich:column width="90" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Pago"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOPAGO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstadoPago}"/>
                    </rich:column>
                    <rich:column width="90" style="text-align: left">
                    	<f:facet name="header">
                        	<h:outputText value="Estado"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                    </rich:column>
                    
                  	<a4j:support event="onRowClick"
                   		actionListener="#{solicitudPersonalController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistro')"
						reRender="pAlertaRegistro">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultado" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para modificar una Solicitud Personal hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{solicitudPersonalController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{solicitudPersonalController.mostrarMensajeExito}"/>
			<h:outputText value="#{solicitudPersonalController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{solicitudPersonalController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="4">
				<a4j:commandButton value="Nuevo" 
					styleClass="btnEstilos" 
					style="width:90px" 
					action="#{solicitudPersonalController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" 
			    	styleClass="btnEstilos" 
			    	style="width:90px"
			    	action="#{solicitudPersonalController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
			    	disabled="#{!solicitudPersonalController.habilitarGrabar}"/>
			    <a4j:commandButton value="Importar Datos"
			    	styleClass="btnEstilos"
			    	style="width:90px"
					action="#{solicitudPersonalController.habilitarImportarDatos}"
					reRender="contPanelInferior,panelMensaje,panelBotones"/>
			    <a4j:commandButton value="Cancelar" 
			    	styleClass="btnEstilos"
			    	style="width:90px"
			    	action="#{solicitudPersonalController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
	
	<rich:panel  rendered="#{solicitudPersonalController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
	<rich:spacer height="12px"/>
		
		<h:panelGrid columns="16" id="panelMonto">
			<rich:column width=	"140">
				<h:outputText value="Fecha Registro : "/>
			</rich:column>
			<rich:column width="160">
				<h:inputText size="20"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.tsFechaRegistro}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="60">
				<h:outputText value="Sucursal : "/>
			</rich:column>
			<rich:column width="260">
				<h:inputText size="36"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.sucursal.juridica.strRazonSocial} - #{
					solicitudPersonalController.solicitudPersonalNuevo.subsucursal.strDescripcion}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;">					
				</h:inputText>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Monto Total : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="15"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.bdMontoTotalSolicitud}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="16" id="panelTipoDocumento">
			<rich:column width=	"140">
				<h:outputText value="Tipo de Documento : "/>
			</rich:column>
			<rich:column width="160">
				<h:selectOneMenu
					style="width: 150px;"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.intParaDocumentoGeneral}">
					<tumih:selectItems var="sel"
						value="#{solicitudPersonalController.listaTablaDocumentoGeneral}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					<a4j:support event="onchange" reRender="panelTipoDocumento"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="320">
				<h:selectOneMenu
					rendered="#{solicitudPersonalController.solicitudPersonalNuevo.intParaDocumentoGeneral
						==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_REMUNERACION}"
					style="width: 320px;"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.intParaSubTipoDocumentoPlanilla}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_CONCEPTODESCUENTOSPLANILLASUELDO}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8" id="panelEntidadPago">
			<rich:column width=	"140">
				<h:outputText value="Entidad de Pago : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText 
					rendered="#{empty solicitudPersonalController.solicitudPersonalNuevo.persona}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText
					rendered="#{solicitudPersonalController.solicitudPersonalNuevo.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
					value="DNI : #{solicitudPersonalController.solicitudPersonalNuevo.persona.documento.strNumeroIdentidad} - #{solicitudPersonalController.solicitudPersonalNuevo.persona.natural.strNombreCompleto}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText
					rendered="#{solicitudPersonalController.solicitudPersonalNuevo.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
					value="RUC : #{solicitudPersonalController.solicitudPersonalNuevo.persona.strRuc} - #{solicitudPersonalController.solicitudPersonalNuevo.persona.juridica.strRazonSocial}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>	
			</rich:column>
			<rich:column width="225">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona"
	                disabled="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}"					
	                reRender="pBuscarPersona"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersona')"
	                action="#{solicitudPersonalController.abrirPopUpBuscarPersona}" 
	                style="width:225px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"140" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputTextarea cols="89" 
					disabled="#{solicitudPersonalController.deshabilitarNuevo}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.strObservacion}"/>
			</rich:column>			
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8" id="panelSustentoAutorizacion">
			<rich:column width=	"140">
				<h:outputText value="Sustento de Autorización : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText 
					rendered="#{not empty solicitudPersonalController.solicitudPersonalNuevo.archivo}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.archivo.strNombrearchivo}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty solicitudPersonalController.solicitudPersonalNuevo.archivo}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>	
			</rich:column>
			<rich:column width=	"225"
				rendered="#{empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}">
				<a4j:commandButton styleClass="btnEstilos"
					value="Adjuntar Documento"
	                disabled="#{solicitudPersonalController.deshabilitarNuevo}"
	                reRender="pAdjuntarDocumento"
	                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumento')"
	                style="width:225px"/>				 	
			</rich:column>
			<rich:column width="130" 
				rendered="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}">
				<h:commandLink  value=" Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{solicitudPersonalController.solicitudPersonalNuevo.archivo}"/>		
				</h:commandLink>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"140">
				<h:outputText value="Tipo de Operación : "/>
			</rich:column>
			<rich:column width="170">
				<h:selectOneMenu
					style="width: 150px;"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.intParaAgrupacionPago}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_OPERACIONPAGOPLANILLA}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					<a4j:support event="onchange" reRender="panelDetalle"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width=	"60">
				<h:outputText value="Moneda : "/>
			</rich:column>
			<rich:column width="255">
				<h:selectOneMenu
					style="width: 245px;"
					disabled="#{not empty solicitudPersonalController.solicitudPersonalNuevo.id.intItemSolicitudPersonal}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width=	"100">
				<h:outputText value="Periodo (aaaamm): "/>
			</rich:column>
			<rich:column width=	"150">
				<h:inputText size="17"
					onkeydown="return validarNumDocIdentidad(this,event,6)"
					disabled="#{solicitudPersonalController.deshabilitarNuevo}"
					value="#{solicitudPersonalController.solicitudPersonalNuevo.intPeriodoPago}">
				</h:inputText>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="20px"/>
		
		<h:panelGroup id="panelDetalle">
		
		<h:panelGrid columns="8">
			<rich:column width=	"140">
				<h:outputText value="Detalle Solicitud :"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Agregar"
	                disabled="#{solicitudPersonalController.deshabilitarNuevo
	                || ((solicitudPersonalController.solicitudPersonalNuevo.intParaAgrupacionPago==applicationScope.Constante.PARAM_T_OPERACIONPAGOPLANILLA_INDIVIDUAL) 
	                 && (not empty solicitudPersonalController.solicitudPersonalNuevo.listaSolicitudPersonalDetalle))}"
	                reRender="pAgregarDetalle"
	                oncomplete="Richfaces.showModalPanel('pAgregarDetalle')"
	                action="#{solicitudPersonalController.abrirPopUpAgregarDetalle}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="2">
			<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{solicitudPersonalController.solicitudPersonalNuevo.listaSolicitudPersonalDetalle}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(solicitudPersonalController.solicitudPersonalNuevo.listaSolicitudPersonalDetalle)}">
				
				<rich:column width="30" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Nro"/>
		         	</f:facet>
		         	<h:outputText value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="300" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Persona"/>
		         	</f:facet>
		         	<h:outputText value="#{item.persona.natural.strNombreCompleto}"/>
		   		</rich:column>
		        <rich:column width="250" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Sucursal"/>
		          	</f:facet>
		          	<h:outputText value="#{item.sucursal.juridica.strRazonSocial} - #{item.subsucursal.strDescripcion} - #{item.area.strDescripcion}"/>
		    	</rich:column>
		    	<rich:column width="70" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Monto"/>
		          	</f:facet>
		          	<h:outputText value="#{item.bdMonto}">
		          		<f:converter converterId="ConvertidorMontos"/>
		          	</h:outputText>
		    	</rich:column>
		    	<rich:column width="150" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Cuenta Contable"/>
		          	</f:facet>
		          	<h:outputText value="#{item.planCuenta.id.strNumeroCuenta}"/>
		    	</rich:column>
		    	<rich:column width="50" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Ver"/>
		         	</f:facet>
					<a4j:commandLink value="Ver"
						disabled="#{solicitudPersonalController.deshabilitarNuevo}"
						reRender="pAgregarDetalle"
						oncomplete="Richfaces.showModalPanel('pAgregarDetalle')"
						actionListener="#{solicitudPersonalController.verSolicitudPersonalDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>
				<rich:column width="50" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Eliminar"/>
		         	</f:facet>
					<a4j:commandLink value="Eliminar"
						disabled="#{solicitudPersonalController.deshabilitarNuevo}"
						reRender="panelDetalle,panelMonto"
						actionListener="#{solicitudPersonalController.eliminarSolicitudPersonalDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>
		  	</rich:dataTable>
		  	</rich:column>		        
		</h:panelGrid>
		
		</h:panelGroup>
		
	</rich:panel>
	
	<rich:panel  rendered="#{solicitudPersonalController.mostrarImportarDatos}"	style="border:1px solid #17356f;background-color:#DEEBF5;"
		id="panelImportarDatos">
	<rich:spacer height="12px"/>
		<h:panelGrid columns="16">
			<rich:fileUpload id="upload"
				addControlLabel="Adjuntar Excel"
				clearControlLabel="Limpiar"
				cancelEntryControlLabel="Cancelar"
				uploadControlLabel="Subir Excel"
				listHeight="65" 
				listWidth="320"
				fileUploadListener="#{fileUploadController.adjuntarArchivoSolicitudPersonal}"
				maxFilesQuantity="1"
				doneLabel="Archivo cargado correctamente"
				immediateUpload="false"
				acceptedTypes="xls">
				<f:facet name="label">
					<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
				</f:facet>
				<a4j:support event="onuploadcomplete" reRender="panelImportarDatos" />
			</rich:fileUpload>
		</h:panelGrid>
		
		<h:panelGroup rendered="#{not empty solicitudPersonalController.listaSolicitudPersonalCarga}">
		
		<h:panelGrid>
		<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{solicitudPersonalController.listaSolicitudPersonalCarga}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(solicitudPersonalController.listaSolicitudPersonalCarga)}">
				
				<rich:column width="50" style="text-align: right">
					<f:facet name="header">
		           		<h:outputText value="Item SP"/>
		         	</f:facet>
		         	<h:outputText value="#{item.id.intItemSolicitudPersonal}"/>
				</rich:column>
				<rich:column width="100" style="text-align: center">
					<f:facet name="header">
						<h:outputText value="Fecha"/>
                    </f:facet>
                    <h:outputText value="#{item.tsFechaRegistro}">
                    	<f:convertDateTime pattern="dd/MM/yyyy" />
					</h:outputText>
				</rich:column>
				<rich:column width="150" style="text-align: left">
					<f:facet name="header">
						<h:outputText value="Documento General"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						property="#{item.intParaDocumentoGeneral}"/>
                </rich:column>
                <rich:column width="100" style="text-align: left">
					<f:facet name="header">
						<h:outputText value="Operación"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_OPERACIONPAGOPLANILLA}" 
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						property="#{item.intParaAgrupacionPago}"/>
                </rich:column>
				<rich:column width="300" style="text-align: left">
		        	<f:facet name="header">
		           		<h:outputText value="Persona"/>
		         	</f:facet>
		         	<h:outputText
                    	rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
                      	value="#{item.persona.juridica.strRazonSocial}" 
                      	title="#{item.persona.juridica.strRazonSocial}"/>
					<h:outputText
                    	rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
                      	value="#{item.persona.natural.strNombreCompleto}" 
                      	title="#{item.persona.natural.strNombreCompleto}"/>
		   		</rich:column>		        
		    	<rich:column width="100" style="text-align: right">
		        	<f:facet name="header">
		            	<h:outputText value="Monto"/>
		          	</f:facet>
		          	<h:outputText value="#{item.bdMontoTotalSolicitud}">
		          		<f:converter converterId="ConvertidorMontos"/>
		          	</h:outputText>
		    	</rich:column>
		    	<rich:column width="100" style="text-align: left">
					<f:facet name="header">
						<h:outputText value="Moneda"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						property="#{item.intParaTipoMoneda}"/>
                </rich:column>
		  	</rich:dataTable>
		</rich:column>	
		</h:panelGrid>
		
		
		<h:panelGrid>
		<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{solicitudPersonalController.listaSolicitudPersonalDetalleCarga}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(solicitudPersonalController.listaSolicitudPersonalDetalleCarga)}">
				
				<rich:column width="50" style="text-align: right">
					<f:facet name="header">
		           		<h:outputText value="Item SP"/>
		         	</f:facet>
		         	<h:outputText value="#{item.id.intItemSolicitudPersonal}"/>
				</rich:column>
				<rich:column width="50" style="text-align: right">
					<f:facet name="header">
		           		<h:outputText value="Item SPD"/>
		         	</f:facet>
		         	<h:outputText value="#{item.id.intItemSolicitudPersonalDetalle}"/>
				</rich:column>
				<rich:column width="150" style="text-align: left">
					<f:facet name="header">
						<h:outputText value="Documento General"/>
					</f:facet>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						property="#{item.intParaDocumentoGeneral}"/>
                </rich:column>
				<rich:column width="300" style="text-align: left">
		        	<f:facet name="header">
		           		<h:outputText value="Persona"/>
		         	</f:facet>
		         	<h:outputText
                    	rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
                      	value="#{item.persona.juridica.strRazonSocial}" 
                      	title="#{item.persona.juridica.strRazonSocial}"/>
					<h:outputText
                    	rendered="#{item.persona.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
                      	value="#{item.persona.natural.strNombreCompleto}" 
                      	title="#{item.persona.natural.strNombreCompleto}"/>
		   		</rich:column>
                <rich:column width="250" style="text-align: left">
		        	<f:facet name="header">
		            	<h:outputText value="Sucursal"/>
		          	</f:facet>
		          	<h:outputText value="#{item.sucursal.juridica.strRazonSocial} - #{item.subsucursal.strDescripcion} - #{item.area.strDescripcion}"/>
		    	</rich:column>
		    	<rich:column width="100" style="text-align: right">
		        	<f:facet name="header">
		            	<h:outputText value="Monto"/>
		          	</f:facet>
		          	<h:outputText value="#{item.bdMonto}">
		          		<f:converter converterId="ConvertidorMontos"/>
		          	</h:outputText>
		    	</rich:column>
		  	</rich:dataTable>
		</rich:column>	
		</h:panelGrid>
		
		</h:panelGroup>
		
	</rich:panel>
	
	</h:panelGroup>	

</h:panelGroup>
</h:form>