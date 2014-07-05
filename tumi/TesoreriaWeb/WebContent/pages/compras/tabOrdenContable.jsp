<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->

<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="REGISTRO DE ORDEN DE BIENES/SERVICIOS" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11" id="panelPersonaFiltroO">
        		<rich:column width="60">
					<h:outputText value="Persona : "/>
				</rich:column>
				<rich:column width="100" style="text-align: left;">
					<h:selectOneMenu
						value="#{ordenController.intTipoBusquedaPersona}"
						style="width: 100px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
						<f:selectItem itemValue="3" itemLabel="RUC"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="150" style="text-align: left;">
					<h:inputText size="22"
						value="#{ordenController.strTextoPersonaFiltro}"/>
				</rich:column>
				<rich:column width="50" style="text-align: left;">
					<h:outputText value="Gestión : "/>
				</rich:column>
				<rich:column width="80">
					<h:selectOneMenu
						value="#{ordenController.ordenCompraFiltro.intParaEstadoOrden}"
						style="width: 80px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOORDEN}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}" />
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="50" style="text-align: left;">
					<h:outputText value="Estado : "/>
				</rich:column>
				<rich:column width="80">
					<h:selectOneMenu
						value="#{ordenController.ordenCompraFiltro.intParaEstado}"
						style="width: 80px;">
						<f:selectItem itemValue="-1" itemLabel="Todos"/>
						<f:selectItem itemValue="1" itemLabel="Activo"/>
						<f:selectItem itemValue="3" itemLabel="Anulado"/>						
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="40" style="text-align: left;">
                	<h:outputText value="Fecha : "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{ordenController.ordenCompraFiltro.dtFiltroDesde}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{ordenController.ordenCompraFiltro.dtFiltroHasta}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="120" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosO,panelMensaje0"
                    	action="#{ordenController.buscar}" 
                    	style="width:120px"/>
            	</rich:column>
            </h:panelGrid>
            
            <!-- Agregado por cdelosrios, 29/09/2013 -->
            <h:panelGrid columns="2">
            	<rich:column width="180">
					<h:outputText value="Nro. Orden de Bienes/Servicio :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{ordenController.ordenCompraFiltro.id.intItemOrdenCompra}" 
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
            </h:panelGrid>
            <!-- Fin agregado por cdelosrios, 29/09/2013 -->
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosO">
	        	<rich:extendedDataTable id="tblResultadoO" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{ordenController.listaOrdenCompra}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                    
					<rich:column width="50" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemOrdenCompra}"/>
                    </rich:column>
                  	<rich:column width="350" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Empresa Servicio"/>                      		
                      	</f:facet>
                      	<h:outputText 
							rendered="#{item.personaProveedor.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
							value="DNI : #{item.personaProveedor.documento.strNumeroIdentidad} - #{item.personaProveedor.natural.strNombreCompleto}"
							title="DNI : #{item.personaProveedor.documento.strNumeroIdentidad} - #{item.personaProveedor.natural.strNombreCompleto}"
						/>
						<h:outputText
							rendered="#{item.personaProveedor.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}"
							value="RUC : #{item.personaProveedor.strRuc} - #{item.personaProveedor.juridica.strRazonSocial}"
							title="RUC : #{item.personaProveedor.strRuc} - #{item.personaProveedor.juridica.strRazonSocial}"
						/>
                  	</rich:column>               		
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaEstado}"/>
                  	</rich:column>                  	
                  	<rich:column width="90" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Entrega"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsPlazoEntrega}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: right">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoTotalDetalle}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaTipoMoneda}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Gestión"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOORDEN}"
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion"
							property="#{item.intParaEstadoOrden}"/>
                  	</rich:column>
                  	<rich:column width="90" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Registro"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
                  	
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{ordenController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistroO')"
						reRender="pAlertaRegistroO">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoO" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Eliminar una Orden hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeO" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{ordenController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{ordenController.mostrarMensajeExito}"/>
			<h:outputText value="#{ordenController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{ordenController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesO">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{ordenController.habilitarPanelInferior}" 
					reRender="contPanelInferiorO,panelMensajeO,panelBotonesO" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{ordenController.grabar}" 
			    	reRender="contPanelInferiorO,panelMensajeO,panelBotonesO,panelTablaResultadosO"
			    	disabled="#{!ordenController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{ordenController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorO,panelMensajeO,panelBotonesO"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorO">
	<rich:panel  rendered="#{ordenController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
		<rich:spacer height="5px"/>
		
		<!-- Agregado por cdelosrios, 11/11/2013 -->
		<h:panelGrid columns="4" rendered="#{not empty ordenController.ordenCompraNuevo.id.intItemOrdenCompra}">
			<rich:column width=	"120">
				<h:outputText value="Nº Orden : "/>
			</rich:column>
			<rich:column>
				<h:outputText style="font-weight:bold;" value="#{ordenController.ordenCompraNuevo.id.intItemOrdenCompra}"/>
			</rich:column>
			
			<rich:column width="150" style="text-align: center">
            	<h:commandLink id="btnReporte" value="Ver Reporte" action="#{ordenController.imprimirOrdenCompra}"/>
          	</rich:column>
		</h:panelGrid>
		<!-- Fin agregado por cdelosrios, 11/11/2013 -->
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Fecha Registro : "/>
			</rich:column>
			<rich:column width="120">
				<h:inputText size="14" 
					value="#{ordenController.ordenCompraNuevo.tsFechaRegistro}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Estado Gestión : "/>
			</rich:column>
			<rich:column width="115">
				<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_ESTADOORDEN}" 
					size="16"
					readonly="true" 
					style="background-color: #BFBFBF;"
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion" 
					property="#{ordenController.ordenCompraNuevo.intParaEstadoOrden}"/>
			</rich:column>
			<rich:column width="150">
				<h:outputText value="Fecha Probable de atención :"/>
			</rich:column>
			<rich:column width="120">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{ordenController.deshabilitarNuevo}"
					value="#{ordenController.ordenCompraNuevo.tsFechaAtencionLog}"  
					converter="calendarTimestampConverter"
					jointPoint="top-right" 
					direction="right" 
					inputSize="12" 
					showApplyButton="true"/>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Fecha Atención :"/>
			</rich:column>
			<rich:column width="120">
			<!-- Modificado por cdelosrios, 15/10/2013 -->
			<%--disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOORDEN_CERRADO" --%>
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
					|| !ordenController.habilitarGrabar}"
					value="#{ordenController.ordenCompraNuevo.tsFechaAtencionReal}"  
					converter="calendarTimestampConverter"
					jointPoint="top-right" 
					direction="right" 
					inputSize="12"
					showApplyButton="true"/>
			<!-- Fin modificado por cdelosrios, 15/10/2013 -->
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGroup id="panelRequisicionO">
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Documento Requisito : "/>
			</rich:column>
			<rich:column width="600">
				<h:inputText 
					rendered="#{not empty ordenController.ordenCompraNuevo.documentoRequisicion}"
					value="#{ordenController.ordenCompraNuevo.documentoRequisicion.requisicion.strEtiqueta}"
					size="95"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty ordenController.ordenCompraNuevo.documentoRequisicion}"
					size="95"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Documento"
	                disabled="#{ordenController.deshabilitarNuevo}"
	                reRender="pBuscarRequisicionOrden"
	                oncomplete="Richfaces.showModalPanel('pBuscarRequisicionOrden')"
	                action="#{ordenController.abrirPopUpBuscarRequisicion}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>		
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Solicitante : "/>
			</rich:column>
			<rich:column width="600">
				<h:inputText size="95" 
					rendered="#{not empty ordenController.ordenCompraNuevo.documentoRequisicion}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{ordenController.ordenCompraNuevo.documentoRequisicion.requisicion.personaSolicitante.strEtiqueta}"/>
				<h:inputText size="95" 
					rendered="#{empty ordenController.ordenCompraNuevo.documentoRequisicion}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>	
			</rich:column>	
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelProveedorO">
			<rich:column width=	"120">
				<h:outputText value="Proveedor : "/>
			</rich:column>
			<rich:column width="600">
				<h:inputText size="95" 
					rendered="#{empty ordenController.ordenCompraNuevo.personaProveedor}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText size="95"
					rendered="#{not empty ordenController.ordenCompraNuevo.personaProveedor}"
					value="#{ordenController.ordenCompraNuevo.personaProveedor.strEtiqueta}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>			
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona"
	                rendered="#{ordenController.ordenCompraNuevo.documentoRequisicion.intParaTipoAprobacion==applicationScope.Constante.PARAM_T_APROBACION_ORDENCOMPRA
	                || ordenController.ordenCompraNuevo.documentoRequisicion.intParaTipoAprobacion==applicationScope.Constante.PARAM_T_APROBACION_CAJACHICA}"
	                reRender="pBuscarPersonaOrden"
	                disabled="#{!ordenController.habilitarGrabar}"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaOrden')"
	                action="#{ordenController.abrirPopUpBuscarPersona}" 
	                style="width:150px"/>
			</rich:column>			
		</h:panelGrid>
		
		<rich:spacer height="15px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="120">
				<h:outputText value="Plazo de Entrega : "/>
			</rich:column>
			<rich:column width="239">
				<!-- Modificado por cdelosrios, 15/10/2013 -->
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
					|| !ordenController.habilitarGrabar}"	
					converter="calendarTimestampConverter"
					value="#{ordenController.ordenCompraNuevo.tsPlazoEntrega}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="35" 
					showApplyButton="true"/>
				<!-- Fin Modificado por cdelosrios, 15/10/2013 -->
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Lugar de Entrega : "/>
			</rich:column>
			<rich:column width="330">
				<!-- Modificado por cdelosrios, 15/10/2013 -->
				<h:inputText size="53" 
					disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
					|| !ordenController.habilitarGrabar}"
					value="#{ordenController.ordenCompraNuevo.strLugarEntrega}"/>
				<!-- Modificado por cdelosrios, 15/10/2013 -->
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6" id="panelGP">
			<rich:column width="120">
				<h:outputText value="Garantía Producto : "/>
			</rich:column>
			<rich:column width="110">
				<h:inputText size="19" 
					rendered="#{empty ordenController.ordenCompraNuevo.id.intItemOrdenCompra}"
					onkeypress="return soloNumerosDecimales(this)"
					disabled="#{ordenController.deshabilitarNuevo}"
					value="#{ordenController.ordenCompraNuevo.bdGarantiaProductoServicio}"/>
				<h:inputText size="19" 
					rendered="#{not empty ordenController.ordenCompraNuevo.id.intItemOrdenCompra}"
					disabled="#{ordenController.deshabilitarNuevo}"
					value="#{ordenController.ordenCompraNuevo.bdGarantiaProductoServicio}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>	
			</rich:column>
			<rich:column width="110">
				<h:selectOneMenu
					disabled="#{ordenController.deshabilitarNuevo}"
					value="#{ordenController.ordenCompraNuevo.intParaTipoFrecuenca}"
					style="width: 100px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Tipo Servicio :"/>
			</rich:column>
			<rich:column width="330">
				<h:selectOneMenu
					disabled="#{ordenController.deshabilitarNuevo || ordenController.blnHabilitarAdmin}"
					value="#{ordenController.ordenCompraNuevo.intParaTipoDetraccion}"
					style="width: 300px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{ordenController.listaDetraccion}"
						itemValue="#{sel.intParaTipoDetraccion}"
						itemLabel="#{sel.strDescripcion}"/>
					<a4j:support event="onchange" reRender="panelCuentasOC,panelGP" action="#{ordenController.seleccionarTipoServicio}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
		
		<rich:spacer height="3px"/>
		
		<h:panelGroup id="panelCuentasOC">
		
		<h:panelGrid columns="6">
			<rich:column width="120">
				<h:outputText value="Forma Pago : "/>
			</rich:column>
			<rich:column width="240">
				<!-- Modificado por cdelosrios, 15/10/2013 -->
				<h:selectOneMenu
					disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
					|| !ordenController.habilitarGrabar
					|| ordenController.blnHabilitarAdmin}"
					value="#{ordenController.ordenCompraNuevo.intParaFormaPago}"
					style="width: 230px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_FORMAPAGOEGRESO}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					<a4j:support event="onchange" reRender="panelCuentasOC" action="#{ordenController.seleccionarTipoFormaPago}"/>
				</h:selectOneMenu>
				<!-- Fin Modificado por cdelosrios, 15/10/2013 -->
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Cuenta Bancaria : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="53" 
					readonly="true"
					style="background-color: #BFBFBF"
					rendered="#{empty ordenController.ordenCompraNuevo.cuentaBancariaPago}"/>
				<h:inputText size="53" 
					readonly="true"
					style="background-color: #BFBFBF"
					rendered="#{not empty ordenController.ordenCompraNuevo.cuentaBancariaPago}"
					value="#{ordenController.ordenCompraNuevo.cuentaBancariaPago.strEtiqueta}"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Cuenta"
	                disabled="#{ordenController.deshabilitarNuevo || !ordenController.habilitarCuentaFormaPago}"
	                reRender="pBuscarCuentaBancariaOrden"
	                oncomplete="Richfaces.showModalPanel('pBuscarCuentaBancariaOrden')"
	                actionListener="#{ordenController.abrirPopUpBuscarCuentaBancaria}" 
	                style="width:150px">
	                <f:attribute name="item" value="1"/>
	        	</a4j:commandButton>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="120">
				<h:outputText value="Impuestos : "/>
			</rich:column>
			<rich:column width="240">
				<h:selectOneMenu
					disabled="#{ordenController.deshabilitarNuevo}"
					style="width: 230px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_IMPUESTOORDEN}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="100">
				<h:outputText value="Cuenta Bancaria : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="53" 
					readonly="true"
					style="background-color: #BFBFBF"
					rendered="#{empty ordenController.ordenCompraNuevo.cuentaBancariaImpuesto}"/>
				<h:inputText size="53" 
					rendered="#{not empty ordenController.ordenCompraNuevo.cuentaBancariaImpuesto}"
					readonly="true"
					style="background-color: #BFBFBF"
					value="#{ordenController.ordenCompraNuevo.cuentaBancariaImpuesto.strEtiqueta}"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Cuenta"
	                disabled="#{ordenController.deshabilitarNuevo || !ordenController.habilitarCuentaImpuesto}"
	                reRender="pBuscarCuentaBancariaOrden"
	                oncomplete="Richfaces.showModalPanel('pBuscarCuentaBancariaOrden')"
	                actionListener="#{ordenController.abrirPopUpBuscarCuentaBancaria}" 
	                style="width:150px">
	                <f:attribute name="item" value="2"/>
	      		</a4j:commandButton>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputTextarea cols="121"					
					rows="2"
					disabled="#{ordenController.deshabilitarNuevo}"
					value="#{ordenController.ordenCompraNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="15px"/>
		
		<h:panelGroup id="panelDetalleO">
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Detalle de Orden : "/>
			</rich:column>
			<rich:column width="150">
				<!-- Modificado por cdelosrios, 15/10/2013 -->
				<a4j:commandButton styleClass="btnEstilos"
                	disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
                	|| empty ordenController.ordenCompraNuevo.personaProveedor
                	|| !ordenController.habilitarGrabar}"
                	value="Agregar" 
                	reRender="pAgregarDetalleOrden"
	                oncomplete="Richfaces.showModalPanel('pAgregarDetalleOrden')"
                    action="#{ordenController.abrirPopUpAgregarOrdenCompraDetalle}" 
                    style="width:120px"/>
                <!-- Fin Modificado por cdelosrios, 15/10/2013 -->
			</rich:column>
		</h:panelGrid>
		
		
		<h:panelGrid columns="2">
			<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{ordenController.ordenCompraNuevo.listaOrdenCompraDetalle}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(ordenController.ordenCompraNuevo.listaOrdenCompraDetalle)}">
				
				<rich:column width="20" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Nro"/>
		         	</f:facet>
		         	<h:outputText value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="70" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Cant"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdCantidad}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		        <rich:column width="70" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="U.M."/>
		          	</f:facet>
		          	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_UNIDADMEDIDA}"
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion"
						property="#{item.intParaUnidadMedida}"/>
		    	</rich:column>
		 		<rich:column width="120" style="text-align: center">
		        	<f:facet name="header">
	            		<h:outputText value="Descripcion"/>
		        	</f:facet>
		        	<h:outputText value="#{item.strDescripcion}"/>
		     	</rich:column>
		     	<rich:column width="150" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Centro Costo"/>
		         	</f:facet>
		         	<h:outputText value="#{item.sucursal.juridica.strRazonSocial}-#{item.area.strDescripcion}"/>
		   		</rich:column>
				<rich:column width="80" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="P.U."/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdPrecioUnitario}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		   		<rich:column width="70" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Moneda"/>
		         	</f:facet>
		         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion"
						property="#{item.intParaTipoMoneda}"/>
		   		</rich:column>
		   		<rich:column width="90" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="P.T."/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdPrecioTotal}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		   		<rich:column width="90" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Saldo"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdMontoSaldo}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
				<rich:column width="60" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Ver"/>
		         	</f:facet>
					<a4j:commandLink value="Ver"
						reRender="pAgregarDetalleOrden"
						oncomplete="Richfaces.showModalPanel('pAgregarDetalleOrden')"
						actionListener="#{ordenController.verOrdenCompraDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>				
		   		<rich:column width="80" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Eliminar"/>
		         	</f:facet>
					<a4j:commandLink value="Eliminar"
						disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
						|| !ordenController.habilitarGrabar}"
						reRender="panelDetalleO,panelMensajeO"
						actionListener="#{ordenController.quitarOrdenCompraDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>				
				</rich:column>
		  	</rich:dataTable>
		  	</rich:column>		        
		</h:panelGrid>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Precio Total : "/>
			</rich:column>
			<rich:column width="100" style="text-align: right">
				<h:outputText value="#{ordenController.ordenCompraNuevo.bdMontoTotalDetalle}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:outputText>
			</rich:column>
			<rich:column width="100" style="text-align: left">
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion"
					property="#{ordenController.ordenCompraNuevo.intParaTipoMoneda}"/>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
		
		<rich:spacer height="15px"/>
		
		<h:panelGroup id="panelDocumentoO">
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Detalle Adicional : "/>
			</rich:column>
			<rich:column width="150">
				<a4j:commandButton styleClass="btnEstilos"
                	disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
                	|| empty ordenController.ordenCompraNuevo.personaProveedor
                	|| !ordenController.habilitarGrabar
                	|| ordenController.blnHabilitarAdmin}"
                	value="Agregar" 
                	reRender="pAgregarDocumentoOrden"
	                oncomplete="Richfaces.showModalPanel('pAgregarDocumentoOrden')"
                    action="#{ordenController.abrirPopUpAgregarOrdenCompraDocumento}" 
                    style="width:120px"/>
			</rich:column>
		</h:panelGrid>
		
		
		<h:panelGrid columns="2">
			<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{ordenController.ordenCompraNuevo.listaOrdenCompraDocumento}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(ordenController.ordenCompraNuevo.listaOrdenCompraDocumento)}">
				
				<rich:column width="40" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Nro"/>
		         	</f:facet>
		         	<h:outputText value="#{rowKey + 1}"/>
				</rich:column>
				<rich:column width="150" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Documento"/>
		         	</f:facet>
		         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}"
						itemValue="intIdDetalle"
						itemLabel="strDescripcion"
						property="#{item.intParaDocumentoGeneral}"/>
		   		</rich:column>
		        <rich:column width="100" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Fecha"/>
		          	</f:facet>
		          	<h:outputText value="#{item.tsFechaDocumento}">
	                	<f:convertDateTime pattern="dd/MM/yyyy" />
	                </h:outputText>
		    	</rich:column>
		 		<rich:column width="90" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Monto"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdMontoDocumento}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		   		<rich:column width="80" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Moneda"/>
		         	</f:facet>
		         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion"
						property="#{item.intParaTipoMoneda}"/>
		   		</rich:column>		   		
		 		<rich:column width="210" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Centro Costo"/>
		         	</f:facet>
		         	<h:outputText value="#{item.sucursal.juridica.strRazonSocial}-#{item.area.strDescripcion}"/>
		   		</rich:column>
		   		
		   		<!-- Agregado por cdelosrios, 08/10/2013 -->
				<rich:column style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Monto Pagado"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdMontoPagado}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		   		
		   		<rich:column style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Monto Ingresado"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdMontoIngresado}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
		   		<rich:column width="90" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Saldo"/>
		         	</f:facet>
		         	<h:outputText value="#{item.bdMontoPagado - item.bdMontoIngresado}">
		         		<f:converter converterId="ConvertidorMontos" />
		         	</h:outputText>
		   		</rich:column>
				<!-- Fin agregado por cdelosrios, 08/10/2013 -->
		   		
				<rich:column width="50" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Ver"/>
		         	</f:facet>
					<a4j:commandLink value="Ver"
						reRender="pAgregarDocumentoOrden"
						oncomplete="Richfaces.showModalPanel('pAgregarDocumentoOrden')"
						actionListener="#{ordenController.verOrdenCompraDocumento}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>
		   		<rich:column width="100" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Eliminar"/>
		         	</f:facet>
					<a4j:commandLink value="Eliminar"
						disabled="#{ordenController.ordenCompraNuevo.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO
						|| !ordenController.habilitarGrabar}"
						reRender="panelDocumentoO,panelMensajeO"
						actionListener="#{ordenController.quitarOrdenCompraDocumento}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>
		  	</rich:dataTable>
		  	</rich:column>		        
		</h:panelGrid>		
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Documento Total : "/>
			</rich:column>
			<rich:column width="100" style="text-align: right">
				<h:outputText value="#{ordenController.ordenCompraNuevo.bdMontoTotalDocumento}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:outputText>
			</rich:column>
			<rich:column width="100" style="text-align: left">
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion"
					property="#{ordenController.ordenCompraNuevo.intParaTipoMoneda}"/>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
				
	</rich:panel>
	
	</h:panelGroup>	

</rich:panel>
</h:form>
