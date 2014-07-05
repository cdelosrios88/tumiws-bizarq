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
	    				<h:outputText value="REGISTRO DE INFORME DE GERENCIA" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="11">        		
				<rich:column width="50">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="150">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<h:selectOneMenu
						value="#{informeController.informeGerenciaFiltro.intParaTipoInforme}"
						style="width: 150px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_COMPRAINFORMEGERENCIA}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="60">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="220">
					<h:selectOneMenu
						value="#{informeController.informeGerenciaFiltro.intSucuIdSucursal}"
						style="width: 210px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{informeController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strRazonSocial}"/>																		
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="60">
					<h:outputText value="Estado :"/>
				</rich:column>
				<rich:column width="100">
					<h:selectOneMenu
						value="#{informeController.informeGerenciaFiltro.intParaEstado}"
						style="width: 90px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="50" style="text-align: left;">
                	<h:outputText value="Fecha: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{informeController.informeGerenciaFiltro.dtFiltroDesde}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{informeController.informeGerenciaFiltro.dtFiltroHasta}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>	
            	<rich:column width="80" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosI,panelMensajeI"
                    	action="#{informeController.buscar}" 
                    	style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
            <!-- Agregado por cdelosrios, 08/10/2013 -->
            <h:panelGrid columns="2">
            	<rich:column width="150">
					<h:outputText value="Nro. Informe de Gerencia :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{informeController.informeGerenciaFiltro.id.intItemInformeGerencia}" 
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
            </h:panelGrid>
            <!-- Fin agregado por cdelosrios, 08/10/2013 -->
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosI">
	        	<rich:extendedDataTable id="tblResultadoI" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{informeController.listaInformeGerencia}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                    
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemInformeGerencia}"/>
                    </rich:column>
					<rich:column width="90" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Informe"/>
                      	</f:facet>                      	
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_COMPRAINFORMEGERENCIA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoInforme}"/>
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
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>              		
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Informe"/>                      		
                      	</f:facet>                      	
                      	<h:outputText value="#{item.dtFechaInforme}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	 <h:outputText value="#{item.bdMontoAutorizado}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Orden Compra"/>
                      	</f:facet>
                      	 <h:outputText rendered="#{not empty item.ordenCompra}" 
                      	 	value="#{item.ordenCompra.id.intItemOrdenCompra}"/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy hh:mm:ss" />
	                 	</h:outputText>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{informeController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistroI')"
						reRender="pAlertaRegistroI">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoI" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Eliminar un Informe de Gerencia hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeI" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{informeController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{informeController.mostrarMensajeExito}"/>
			<h:outputText value="#{informeController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{informeController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesI">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{informeController.habilitarPanelInferior}" 
					reRender="contPanelInferiorI,panelMensajeI,panelBotonesI" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{informeController.grabar}" 
			    	reRender="contPanelInferiorI,panelMensajeI,panelBotonesI,panelTablaResultadosI"
			    	disabled="#{!informeController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{informeController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorI,panelMensajeI,panelBotonesI"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorI">
	<rich:panel  rendered="#{informeController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Fecha Requisición : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="18" 
					value="#{informeController.informeGerenciaNuevo.tsFechaRegistro}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="120">
				<h:outputText value="Tipo de Informe : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{informeController.deshabilitarNuevo}"
					style="width: 200px;"
					value="#{informeController.informeGerenciaNuevo.intParaTipoInforme}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_COMPRAINFORMEGERENCIA}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelRequisicionI">
			<rich:column width=	"120">
				<h:outputText value="Requisición : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText 
					rendered="#{not empty informeController.informeGerenciaNuevo.requisicion}"
					value="#{informeController.informeGerenciaNuevo.requisicion.strEtiqueta}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty informeController.informeGerenciaNuevo.requisicion}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Requisicion"
	                disabled="#{informeController.deshabilitarNuevo}"
	                reRender="pBuscarRequisicionInforme"
	                oncomplete="Richfaces.showModalPanel('pBuscarRequisicionInforme')"
	                action="#{informeController.abrirPopUpBuscarRequisicion}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGroup id="panelPersonaI">
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Empresa Servicio : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText readonly="true"
					rendered="#{not empty informeController.informeGerenciaNuevo.empresaServicio}"
					value="RUC : #{informeController.informeGerenciaNuevo.empresaServicio.strRuc} - #{informeController.informeGerenciaNuevo.empresaServicio.juridica.strRazonSocial}"
					style="background-color: #BFBFBF;"
					size="88"/>
				<h:inputText size="73" 
					rendered="#{empty informeController.informeGerenciaNuevo.empresaServicio}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona Juridica"
	                disabled="#{informeController.deshabilitarNuevo}"
	                reRender="pBuscarPersonaInforme"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaInforme')"
	                action="#{informeController.abrirPopUpBuscarEmpresaServicio}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"120">
				<h:outputText value="Persona Autoriza : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText readonly="true"
					rendered="#{not empty informeController.informeGerenciaNuevo.personaAutoriza}"
					value="DNI : #{informeController.informeGerenciaNuevo.personaAutoriza.documento.strNumeroIdentidad} - #{informeController.informeGerenciaNuevo.personaAutoriza.natural.strNombreCompleto}"
					style="background-color: #BFBFBF;"
					size="88"/>
				<h:inputText size="73" 
					rendered="#{empty informeController.informeGerenciaNuevo.personaAutoriza}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona Natural"
	                disabled="#{informeController.deshabilitarNuevo}"
	                reRender="pBuscarPersonaInforme"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaInforme')"
	                action="#{informeController.abrirPopUpBuscarPersonaAutoriza}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
				
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelSucursalI">
			<rich:column width="120">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="270">
				<h:selectOneMenu
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.intSucuIdSucursal}"
					style="width: 260px;">
					<tumih:selectItems var="sel"
						value="#{informeController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" 
						action="#{informeController.seleccionarSucursal}" 
						reRender="panelSucursalI"  />	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width=	"40">
				<h:outputText value="Area : "/>
			</rich:column>
			<rich:column width="120">
				<h:selectOneMenu
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.intIdArea}"
					style="width: 160px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{informeController.informeGerenciaNuevo.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width="120">
				<h:outputText value="Fecha de Informe :"/>
			</rich:column>
			<rich:column width="210">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.dtFechaInforme}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="20" 
					showApplyButton="true"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Monto Autorizado : "/>
			</rich:column>
			<rich:column width="270">
				<h:inputText size="44"
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.bdMontoAutorizado}"
					onkeypress="return soloNumerosDecimales(this)"/>
			</rich:column>
			<rich:column width=	"200">
				<h:selectOneMenu
					style="width: 208px;"
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelDocumentoI">
			<rich:column width=	"120">
				<h:outputText value="Documento : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText rendered="#{empty informeController.informeGerenciaNuevo.archivoDocumento}" 
					size="88"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
				<h:inputText rendered="#{not empty informeController.informeGerenciaNuevo.archivoDocumento}"
					value="#{informeController.informeGerenciaNuevo.archivoDocumento.strNombrearchivo}"
					size="88"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column>
			<!-- Modificado por cdelosrios, 31/10/2013 -->
			<rich:column width=	"170"
				rendered="#{empty informeController.informeGerenciaNuevo.id.intItemInformeGerencia}">
				<a4j:commandButton styleClass="btnEstilos"
					value="Adjuntar Documento"
	                reRender="pAdjuntarDocumentoInforme"
	                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentoInforme')"
	                style="width:150px"/>
			</rich:column>
			<!-- Fin modificado por cdelosrios, 31/10/2013 -->
			<!-- Agregado por cdelosrios, 31/10/2013 -->
			<rich:column width="130" 
				rendered="#{not empty informeController.informeGerenciaNuevo.id.intItemInformeGerencia}">
				<h:commandLink  value=" Descargar"		
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{informeController.informeGerenciaNuevo.archivoDocumento}"/>		
				</h:commandLink>
			</rich:column>
			<!-- Fin agregado por cdelosrios, 31/10/2013 -->
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="470">
				<h:inputTextarea cols="88" rows="3"
					disabled="#{informeController.deshabilitarNuevo}"
					value="#{informeController.informeGerenciaNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
				
		<rich:spacer height="12px"/>
		
	</rich:panel>
	
	</h:panelGroup>	

</rich:panel>
</h:form>