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
	    				<h:outputText value="REGISTRO CONTRATO" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="11">
            	<rich:column width="35">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="150">
					<h:selectOneMenu
						value="#{contratoController.contratoFiltro.intParaTipoContrato}"
						style="width: 150px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_COMPRACONTRATO}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="55">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="215">
					<h:selectOneMenu
						value="#{contratoController.contratoFiltro.intSucuIdSucursal}"
						style="width: 210px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{contratoController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strRazonSocial}"/>																		
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="50">
					<h:outputText value="Estado :"/>
				</rich:column>
				<rich:column width="95">
					<h:selectOneMenu
						value="#{contratoController.contratoFiltro.intParaEstado}"
						style="width: 90px;">
						<f:selectItem itemValue="1" itemLabel="Activo"/>
						<f:selectItem itemValue="3" itemLabel="Anulado"/>
						<f:selectItem itemValue="-1" itemLabel="Todos"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="40" style="text-align: left;">
                	<h:outputText value="Fecha: "/>
            	</rich:column>
            	<rich:column width="60">
					<h:selectOneMenu
						value="#{contratoController.intTipoFiltroFecha}"
						style="width: 60px;">
						<f:selectItem itemValue="1" itemLabel="Inicio"/>
						<f:selectItem itemValue="2" itemLabel="Fin"/>	
					</h:selectOneMenu>
				</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{contratoController.dtFiltroDesde}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{contratoController.dtFiltroHasta}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>	
            	<rich:column width="60" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosC,panelMensajeC"
                    	action="#{contratoController.buscar}" 
                    	style="width:60px"/>
            	</rich:column>
            </h:panelGrid>
            
            <!-- Agregado por cdelosrios, 29/09/2013 -->
            <h:panelGrid columns="2">
            	<rich:column width="110">
					<h:outputText value="Nro. Contrato :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{contratoController.contratoFiltro.id.intItemContrato}" 
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
            </h:panelGrid>
            <!-- Fin agregado por cdelosrios, 29/09/2013 -->
            
            <rich:spacer height="12px"/>           
            <h:panelGrid id="panelTablaResultadosC">
	        	<rich:extendedDataTable id="tblResultadoC" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{contratoController.listaContrato}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                    
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemContrato}"/>
                    </rich:column>
					<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Tipo"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_COMPRACONTRATO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoContrato}"/>
                    </rich:column>
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strRazonSocial}"/>
                  	</rich:column>
                  	<rich:column width="220" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Empresa Servicio"/>
                      	</f:facet>
                      	<h:outputText value="#{item.empresaServicio.strEtiqueta}" title="#{item.empresaServicio.strEtiqueta}"/>
                  	</rich:column>                  		
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>   
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fechas"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.dtFechaInicio}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
	                 	<h:outputText value=" - "/>
	                 	<h:outputText value="#{item.dtFechaFin}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoContrato}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Orden"/>
                      	</f:facet>
                      	<h:outputText rendered="#{not empty item.ordenCompra}" 
                      		value="#{item.ordenCompra.id.intItemOrdenCompra}"/>
                  	</rich:column>
                  	<rich:column width="130" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaContrato}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy hh:mm:ss" />
	                 	</h:outputText>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{contratoController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistroC')"
						reRender="pAlertaRegistroC">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoC" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver o Eliminar un Contrato hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeC" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{contratoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{contratoController.mostrarMensajeExito}"/>
			<h:outputText value="#{contratoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{contratoController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesC">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{contratoController.habilitarPanelInferior}" 
					reRender="contPanelInferiorC,panelMensajeC,panelBotonesC" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{contratoController.grabar}" 
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC,panelTablaResultadosC"
			    	disabled="#{!contratoController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{contratoController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorC,panelMensajeC,panelBotonesC"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorC">
	<rich:panel  rendered="#{contratoController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Fecha Registro : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="18" 
					value="#{contratoController.contratoNuevo.tsFechaContrato}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="120">
				<h:outputText value="Tipo de Contrato : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					style="width: 200px;"
					value="#{contratoController.contratoNuevo.intParaTipoContrato}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_COMPRACONTRATO}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelRequisicionC">
			<rich:column width=	"120">
				<h:outputText value="Requisición : "/>
			</rich:column>
			<rich:column width="660">
				<h:inputText 
					rendered="#{not empty contratoController.contratoNuevo.requisicion}"
					value="#{contratoController.contratoNuevo.requisicion.strEtiqueta}"
					size="98"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty contratoController.contratoNuevo.requisicion}"
					size="98"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Requisicion"
	                disabled="#{contratoController.deshabilitarNuevo}"
	                reRender="pBuscarRequisicionContrato"
	                oncomplete="Richfaces.showModalPanel('pBuscarRequisicionContrato')"
	                action="#{contratoController.abrirPopUpBuscarRequisicion}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>		
		
		<h:panelGrid columns="8" id="panelPersonaC">
			<rich:column width=	"120">
				<h:outputText value="Empresa Servicio : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					style="width: 140px;"
					value="#{contratoController.contratoNuevo.intParatTipoEmpresaServicio}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_EMPRESASERVICIONCONTRATO}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
			<rich:column width="500">
				<h:inputText readonly="true"
					rendered="#{(not empty contratoController.contratoNuevo.empresaServicio)
					&&(contratoController.contratoNuevo.empresaServicio.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL)}"
					value="DNI : #{contratoController.contratoNuevo.empresaServicio.documento.strNumeroIdentidad} - #{contratoController.contratoNuevo.empresaServicio.natural.strNombreCompleto}"
					style="background-color: #BFBFBF;"
					size="88"/>				
				<h:inputText readonly="true"
					rendered="#{(not empty contratoController.contratoNuevo.empresaServicio)
					&&(contratoController.contratoNuevo.empresaServicio.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA)}"
					value="RUC : #{contratoController.contratoNuevo.empresaServicio.strRuc} - #{contratoController.contratoNuevo.empresaServicio.juridica.strRazonSocial}"
					style="background-color: #BFBFBF;"
					size="88"/>
				<h:inputText size="73" 
					rendered="#{empty contratoController.contratoNuevo.empresaServicio}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona"
	                disabled="#{contratoController.deshabilitarNuevo}"
	                reRender="pBuscarPersonaContrato"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaContrato')"
	                action="#{contratoController.abrirPopUpBuscarEmpresaServicio}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelDomicilioC">
			<rich:column width=	"120">
				<h:outputText value="Dirección (Alquiler) : "/>
			</rich:column>
			<rich:column width="660">
				<h:inputText readonly="true"
					rendered="#{not empty contratoController.contratoNuevo.domicilioEmpresaServicio}"
					value="#{contratoController.contratoNuevo.domicilioEmpresaServicio.strEtiqueta}"
					style="background-color: #BFBFBF;"
					size="118"/>
				<h:inputText size="98" 
					rendered="#{empty contratoController.contratoNuevo.domicilioEmpresaServicio}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Dirección"
	                disabled="#{empty contratoController.contratoNuevo.empresaServicio || contratoController.deshabilitarNuevo}"
	                reRender="pBuscarDomicilioContrato"
	                action="#{contratoController.abrirPopUpBuscarDomicilio}"
	                oncomplete="Richfaces.showModalPanel('pBuscarDomicilioContrato')"
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Empresa Solicita : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					style="width: 140px;"
					value="#{contratoController.contratoNuevo.intParaTipoEmpresaSolicita}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_EMPRESASERVICIONCONTRATO}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
			<rich:column width="497">
				<h:inputText readonly="true"
					rendered="#{not empty contratoController.contratoNuevo.empresaSolicita}"
					value="RUC : #{contratoController.contratoNuevo.empresaSolicita.strRuc} - #{contratoController.contratoNuevo.empresaSolicita.juridica.strRazonSocial}"
					style="background-color: #BFBFBF;"
					size="88"/>
				<h:inputText size="73" 
					rendered="#{empty contratoController.contratoNuevo.empresaSolicita}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
		</h:panelGrid>

		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelSucursalC">
			<rich:column width="120">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="310">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.intSucuIdSucursal}"
					style="width: 300px;">
					<tumih:selectItems var="sel"
						value="#{contratoController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" 
						action="#{contratoController.seleccionarSucursal}" 
						reRender="panelSucursalC"  />
				</h:selectOneMenu>
			</rich:column>
			<rich:column width=	"85">
				<h:outputText value="Área : "/>
			</rich:column>
			<rich:column width="235">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.intIdAreaSolicitante}"
					style="width: 235px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{contratoController.contratoNuevo.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width="120">
				<h:outputText value="Fecha de Inicio :"/>
			</rich:column>
			<rich:column width="150">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.dtFechaInicio}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="19" 
					showApplyButton="true"/>
			</rich:column>
			<rich:column width="155">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.dtFechaFin}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="19" 
					showApplyButton="true"/>
			</rich:column>
			<rich:column width="85">
				<h:outputText value="Plazo Duración :"/>
			</rich:column>
			<rich:column width="235">
				<h:inputText size="39" 
					value="#{contratoController.contratoNuevo.strCantidadDias}"
					readonly="true"
					style="background-color: #BFBFBF;"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Periodicidad de Pago : "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.intParaTipoFrecuencia}"
					style="width: 140px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="165">
				<h:selectBooleanCheckbox value="#{contratoController.contratoNuevo.seleccionaPagoUnico}"
					disabled="#{contratoController.deshabilitarNuevo}"/>
				<h:outputText value="Pago único"/>
			</rich:column>
			<rich:column width=	"85">
				<h:outputText value="Monto : "/>
			</rich:column>
			<rich:column width="130">
				<h:inputText size="20"
					rendered="#{empty contratoController.contratoNuevo.id.intItemContrato}"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.bdMontoContrato}"
					onkeypress="return soloNumerosDecimales(this)"/>
				<h:inputText size="20"
					rendered="#{not empty contratoController.contratoNuevo.id.intItemContrato}"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.bdMontoContrato}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
			<rich:column width=	"100">
				<h:selectOneMenu
					style="width: 90px;"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="120">
				<h:selectBooleanCheckbox value="#{contratoController.contratoNuevo.seleccionaRenovacion}"
					disabled="#{contratoController.deshabilitarNuevo}"/>
				<h:outputText value="Renovación"/>
			</rich:column>			
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Monto Garantía : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="22"
					rendered="#{empty contratoController.contratoNuevo.id.intItemContrato}"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.bdMontoGarantia}"
					onkeypress="return soloNumerosDecimales(this)"/>
				<h:inputText size="22"
					rendered="#{not empty contratoController.contratoNuevo.id.intItemContrato}"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.bdMontoGarantia}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>					
			</rich:column>
			<rich:column width=	"140">
				<h:selectOneMenu
					style="width: 140px;"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.intParaTipoMonedaGarantia}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelDocumentoC">
			<rich:column width=	"120">
				<h:outputText value="Documento : "/>
			</rich:column>
			<rich:column width="660">
				<h:inputText rendered="#{empty contratoController.contratoNuevo.archivoDocumento}" 
					size="118"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
				<h:inputText rendered="#{not empty contratoController.contratoNuevo.archivoDocumento}"
					value="#{contratoController.contratoNuevo.archivoDocumento.strNombrearchivo}"
					size="118"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column>
			<rich:column width=	"170"
				rendered="#{empty contratoController.contratoNuevo.id.intItemContrato}">
				<a4j:commandButton styleClass="btnEstilos"
					value="Adjuntar Documento"
	                disabled="#{contratoController.deshabilitarNuevo}"
	                reRender="pAdjuntarDocumentoContrato"
	                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentoContrato')"
	                style="width:150px"/>				 	
			</rich:column>
			<rich:column width="130" 
				rendered="#{not empty contratoController.contratoNuevo.id.intItemContrato}">
				<h:commandLink  value=" Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{contratoController.contratoNuevo.archivoDocumento}"/>		
				</h:commandLink>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="660">
				<h:inputTextarea cols="118" rows="3"
					disabled="#{contratoController.deshabilitarNuevo}"
					value="#{contratoController.contratoNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelContratoAnteriorC">
			<rich:column width=	"120">
				<h:outputText value="Contrato Anterior : "/>
			</rich:column>
			<rich:column width="660">
				<h:inputText rendered="#{empty contratoController.contratoNuevo.contratoAnterior}" 
					size="118"
					readonly="true"
					style="background-color: #BFBFBF;"/>
				<h:inputText rendered="#{not empty contratoController.contratoNuevo.contratoAnterior}"
					value="#{contratoController.contratoNuevo.contratoAnterior.strEtiqueta}"
					size="118"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Contrato"
	                disabled="#{empty contratoController.contratoNuevo.empresaServicio || contratoController.deshabilitarNuevo}"
	                action="#{contratoController.abrirPopUpBuscarAnteriorContrato}"
	                reRender="pBuscarAnteriorContrato"
	                oncomplete="Richfaces.showModalPanel('pBuscarAnteriorContrato')"
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
	</rich:panel>
	
	</h:panelGroup>	

</rich:panel>
</h:form>