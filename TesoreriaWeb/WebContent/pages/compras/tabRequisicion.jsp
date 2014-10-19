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
	    				<h:outputText value="REGISTRO DE REQUISICIÓN" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="8">        		
            	<rich:column width="120">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="210">
					<h:selectOneMenu
						value="#{requisicionController.requisicionFiltro.intSucuIdSucursal}"
						style="width: 200px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{requisicionController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strRazonSocial}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Tipo de Aprobación :"/>
				</rich:column>
				<rich:column width="200">
					<h:selectOneMenu
						value="#{requisicionController.requisicionFiltro.intParaTipoAprobacion}"
						style="width: 180px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_APROBACION}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<!-- Agregado por cdelosrios, 28/09/2013 -->
				<rich:column width="40" style="text-align: left;">
					<h:outputText value="Fecha :"/>
				</rich:column>
				<rich:column width="100" style="text-align: right;">
					<rich:calendar datePattern="dd/MM/yyyy"
						value="#{requisicionController.requisicionFiltro.tsFechaRequisicionIni}"
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
				</rich:column>
				<rich:column width="100" style="text-align: right;">
					<rich:calendar datePattern="dd/MM/yyyy"
						value="#{requisicionController.requisicionFiltro.tsFechaRequisicionFin}"
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
				</rich:column>
				<!-- Fin agregado por cdelosrios, 28/09/2013 -->
            	<rich:column width="120" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosR,panelMensajeR"
                    	action="#{requisicionController.buscar}" 
                    	style="width:120px"/>
            	</rich:column>
            </h:panelGrid>
            
            <!-- Agregado por cdelosrios, 29/09/2013 -->
            <h:panelGrid columns="2">
            	<rich:column width="110">
					<h:outputText value="Nro. Requisición :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{requisicionController.requisicionFiltro.id.intItemRequisicion}" 
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
            </h:panelGrid>
            <!-- Fin agregado por cdelosrios, 29/09/2013 -->
            
            <rich:spacer height="12px"/>
                
            <h:panelGrid id="panelTablaResultadosR">
	        	<rich:extendedDataTable id="tblResultadoR" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{requisicionController.listaRequisicion}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                                
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemRequisicion}"/>
                    </rich:column>
					<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Requisición"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_REQUISICION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoRequisicion}"/>
                    </rich:column>
                  	<rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Área solicitante"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.sucursalSolicitante.juridica.strRazonSocial} - #{item.area.strDescripcion}"/>
                  	</rich:column>
                  	<rich:column width="90" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Aprobación"/>                		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_APROBACION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoAprobacion}"/>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Jefatura"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{not empty item.intParaEstadoAprobacionJefatura}">
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOREQUISICION}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion" 
							property="#{item.intParaEstadoAprobacionJefatura}"/>
                      	</h:panelGroup>                      		
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Logística"/>                      		
                      	</f:facet>
                      	<h:panelGroup rendered="#{not empty item.intParaEstadoAprobacionLogistica}">
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOREQUISICION}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion" 
							property="#{item.intParaEstadoAprobacionLogistica}"/>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Plazo"/>                      		
                      	</f:facet>
                      	<h:outputText rendered="#{not empty item.ordenCompra}"
                      		value="#{item.ordenCompra.tsFechaAtencionLog}">
                      		<f:convertDateTime pattern="dd/MM/yyyy" />
                      	</h:outputText>
                  	</rich:column>
                  	<!-- Modificado por cdelosrios, 11/11/2013 -->
                  	<%-- <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Atención"/>
                      	</f:facet>
                      	<h:outputText rendered="#{not empty item.ordenCompra}" 
                      		value="#{item.ordenCompra.tsFechaAtencionReal}">
                      		<f:convertDateTime pattern="dd/MM/yyyy" />
                      	</h:outputText>
                  	</rich:column> --%>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Estado"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                  	</rich:column>
                  	<!-- Fin modificado por cdelosrios, 11/11/2013 -->
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Orden"/>
                      	</f:facet>
                      	<h:outputText rendered="#{not empty item.ordenCompra}"
                      		value="#{item.ordenCompra.id.intItemOrdenCompra}"/>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRequisicion}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy hh:mm" />
	                 	</h:outputText>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{requisicionController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistroR')"
						reRender="pAlertaRegistroR">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoR" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Aprobar, Aprobar Logistica, Eliminar una Requisicion hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeR" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{requisicionController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{requisicionController.mostrarMensajeExito}"/>
			<h:outputText value="#{requisicionController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{requisicionController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesR">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{requisicionController.habilitarPanelInferior}" 
					reRender="contPanelInferiorR,panelMensajeR,panelBotonesR" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{requisicionController.grabar}" 
			    	reRender="contPanelInferiorR,panelMensajeR,panelBotonesR,panelTablaResultadosR"
			    	disabled="#{!requisicionController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{requisicionController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorR,panelMensajeR,panelBotonesR"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorR">
	<rich:panel  rendered="#{requisicionController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
		<rich:spacer height="5px"/>
		
		<!-- Agregado por cdelosrios, 11/11/2013 -->
		<h:panelGrid columns="4" rendered="#{not empty requisicionController.requisicionNuevo.id.intItemRequisicion}">
			<rich:column width=	"120">
				<h:outputText value="Nº Requisición : "/>
			</rich:column>
			<rich:column>
				<h:outputText style="font-weight:bold;" value="#{requisicionController.requisicionNuevo.id.intItemRequisicion}"/>
			</rich:column>
			
			<rich:column width="150" style="text-align: center">
            	<h:commandLink id="btnReporte" value="Ver Reporte" action="#{requisicionController.imprimirRequisicion}"/>
          	</rich:column>
		</h:panelGrid>
		<!-- Fin agregado por cdelosrios, 11/11/2013 -->
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Fecha Requisición : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="18" 
					value="#{requisicionController.requisicionNuevo.tsFechaRequisicion}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="120">
				<h:outputText value="Tipo de Requisición : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{(requisicionController.habilitarVer) || (requisicionController.deshabilitarNuevo)}"
					style="width: 200px;"
					value="#{requisicionController.requisicionNuevo.intParaTipoRequisicion}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_REQUISICION}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
				<%--<h:selectOneMenu
					disabled="#{!requisicionController.habilitarSolicitante || requisicionController.deshabilitarNuevo}"
					style="width: 200px;"
					value="#{requisicionController.requisicionNuevo.intParaTipoRequisicion}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_REQUISICION}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>--%>
			</rich:column>
			<rich:column width="130">
				<h:outputText value="Tipo de Aprobación : "/>
			</rich:column>
			<rich:column width="200">
				<!-- Modificado por cdelosrios, 13/10/2013, línea 288 el desabilitado del campo  -->
				<%-- disabled="#{(requisicionController.habilitarVer) || (requisicionController.deshabilitarNuevo)}" --%>
				<h:selectOneMenu
					disabled="#{!requisicionController.habilitarSolicitante || requisicionController.deshabilitarNuevo}"
					style="width: 200px;"
					value="#{requisicionController.requisicionNuevo.intParaTipoAprobacion}">
					<tumih:selectItems var="sel"
						value="#{requisicionController.listaTablaAprobacion}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
				<!-- Modificado por cdelosrios, 13/10/2013 -->
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Usuario Solicitante : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText size="73" 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{requisicionController.requisicionNuevo.personaSolicitante.strEtiqueta}"/>
			</rich:column>
			<rich:column width=	"130">
				<h:outputText value="Sucursal Solcitante : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="27"					 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" 
					value="#{requisicionController.requisicionNuevo.sucursalSolicitante.juridica.strRazonSocial}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Centro de Costo : "/>
			</rich:column>
			<rich:column width="480">
				<h:selectOneMenu
					style="width: 487px;"
					disabled="#{!requisicionController.habilitarSolicitante || requisicionController.deshabilitarNuevo}"
					value="#{requisicionController.requisicionNuevo.intIdArea}">
					<tumih:selectItems var="sel"
						value="#{requisicionController.requisicionNuevo.sucursalSolicitante.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Monto estimado : "/>
			</rich:column>
			<rich:column width="270">
				<h:inputText size="44"
					rendered="#{empty requisicionController.requisicionNuevo.bdMontoEstimado}"
					value="#{requisicionController.requisicionNuevo.bdMontoEstimado}"
					onkeypress="return soloNumerosDecimales(this)"/>					
				<h:inputText size="44"
					rendered="#{not empty requisicionController.requisicionNuevo.bdMontoEstimado}"
					disabled="true"
					value="#{requisicionController.requisicionNuevo.bdMontoEstimado}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
			<rich:column width=	"200">
				<h:selectOneMenu
					style="width: 208px;"
					disabled="#{!requisicionController.habilitarSolicitante || requisicionController.deshabilitarNuevo}"
					value="#{requisicionController.requisicionNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="470">
				<h:inputTextarea cols="88" 
					disabled="#{!requisicionController.habilitarSolicitante || requisicionController.deshabilitarNuevo}"
					value="#{requisicionController.requisicionNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="12px"/>
		
		
		<h:panelGroup rendered="#{(requisicionController.habilitarJefatura && !requisicionController.habilitarVer)
			|| (requisicionController.habilitarVer && not empty requisicionController.requisicionNuevo.intParaEstadoAprobacionJefatura)}">
			
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Usuario Jefatura : "/>
			</rich:column>
			<rich:column width="490">
				<h:inputText size="73" 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{requisicionController.requisicionNuevo.personaJefatura.strEtiqueta}"/>
			</rich:column>
			<rich:column width=	"130">
				<h:outputText value="Sucursal Jefatura: "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="27"					 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" 
					value="#{requisicionController.requisicionNuevo.sucursalJefatura.juridica.strRazonSocial}"/>
			</rich:column>
		</h:panelGrid>
				
		<h:panelGrid columns="3">
			<rich:column width=	"120">
				<h:outputText value="Jefatura Evaluación : "/>
			</rich:column>
			<rich:column width="480">
				<h:selectOneMenu
					style="width: 487px;"
					disabled="#{not empty requisicionController.requisicionNuevo.bdMontoJefatura}"
					value="#{requisicionController.requisicionNuevo.intParaEstadoAprobacionJefatura}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOREQUISICION}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		<h:panelGrid columns="3">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Jefatura Fundamento : "/>
			</rich:column>
			<rich:column width="470">
				<h:inputTextarea cols="88" 
					disabled="#{not empty requisicionController.requisicionNuevo.bdMontoJefatura && not empty requisicionController.requisicionNuevo.strFundamentoJefatura}"
					value="#{requisicionController.requisicionNuevo.strFundamentoJefatura}"/>
			</rich:column>
		</h:panelGrid>		
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Monto Jefatura : "/>
			</rich:column>
			<rich:column width="270">
				<h:inputText size="44"
					rendered="#{empty requisicionController.requisicionNuevo.bdMontoJefatura}"
					value="#{requisicionController.requisicionNuevo.bdMontoJefatura}"
					onkeypress="return soloNumerosDecimales(this)"/>
				<h:inputText size="44"
					rendered="#{not empty requisicionController.requisicionNuevo.bdMontoJefatura}"
					disabled="true"
					value="#{requisicionController.requisicionNuevo.bdMontoJefatura}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>	
			</rich:column>
			<rich:column width=	"200">
				<h:selectOneMenu
					style="width: 208px;"
					disabled="true"
					value="#{requisicionController.requisicionNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
		
		<rich:spacer height="12px"/>
		
		<h:panelGroup rendered="#{((requisicionController.habilitarLogistica && !requisicionController.habilitarVer)
			|| (requisicionController.habilitarVer && not empty requisicionController.requisicionNuevo.intParaEstadoAprobacionLogistica))
			&&  requisicionController.requisicionNuevo.intParaTipoAprobacion != applicationScope.Constante.PARAM_T_APROBACION_CAJACHICA}">
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Usuario Logistica : "/>
			</rich:column>
			<rich:column width="490">
				<h:inputText size="73" 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{requisicionController.requisicionNuevo.personaLogistica.strEtiqueta}"/>
			</rich:column>
			<rich:column width=	"130">
				<h:outputText value="Sucursal Logística : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="27"					 
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;" 
					value="#{requisicionController.requisicionNuevo.sucursalLogistica.juridica.strRazonSocial}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="3">
			<rich:column width=	"120">
				<h:outputText value="Logística Evaluación : "/>
			</rich:column>
			<rich:column width="480">
				<h:selectOneMenu
					style="width: 487px;"
					disabled="#{not empty requisicionController.requisicionNuevo.bdMontoLogistica}"
					value="#{requisicionController.requisicionNuevo.intParaEstadoAprobacionLogistica}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOREQUISICION}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="3">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Logística Fundamento : "/>
			</rich:column>
			<rich:column width="470">
				<h:inputTextarea cols="88" 
					disabled="#{not empty requisicionController.requisicionNuevo.bdMontoLogistica && not empty requisicionController.requisicionNuevo.strFundamentoLogistica}"
					value="#{requisicionController.requisicionNuevo.strFundamentoLogistica}"/>
			</rich:column>
		</h:panelGrid>		
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Monto Logística : "/>
			</rich:column>
			<rich:column width="270">
				<h:inputText size="44"
					rendered="#{empty requisicionController.requisicionNuevo.bdMontoLogistica}"
					value="#{requisicionController.requisicionNuevo.bdMontoLogistica}"
					onkeypress="return soloNumerosDecimales(this)"/>
				<h:inputText size="44"
					rendered="#{not empty requisicionController.requisicionNuevo.bdMontoLogistica}"
					disabled="true"
					value="#{requisicionController.requisicionNuevo.bdMontoLogistica}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>	
			</rich:column>
			<rich:column width=	"200">
				<h:selectOneMenu
					style="width: 208px;"
					disabled="true"
					value="#{requisicionController.requisicionNuevo.intParaTipoMoneda}">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		</h:panelGroup>
		
		
		<rich:spacer height="12px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120">
				<h:outputText value="Detalle de Requisición : "/>
			</rich:column>
			<rich:column width="150">
				<a4j:commandButton styleClass="btnEstilos"
	            	value="Agregar"
	                disabled="#{requisicionController.deshabilitarNuevo}"
	                reRender="pAgregarRequisicionDetalle"
	                oncomplete="Richfaces.showModalPanel('pAgregarRequisicionDetalle')"
	                action="#{requisicionController.abrirPopUpAgregarRequisicionDetalle}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2" id="panelDetalleR">
			<rich:column width=	"750">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{requisicionController.requisicionNuevo.listaRequisicionDetalle}"
				rowKeyVar="rowKey"
				width="710px"
				rows="#{fn:length(requisicionController.requisicionNuevo.listaRequisicionDetalle)}">
				
				<rich:column width="40" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Nro"/>
		         	</f:facet>
					<h:outputText value="#{rowKey + 1}"/>				
				</rich:column>
				<rich:column width="100" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Cantidad"/>
		         	</f:facet>
		            <h:outputText value="#{item.intCantidad}"/>
		   		</rich:column>
		        <rich:column width="250" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Descripción"/>
		          	</f:facet>
		          	<h:outputText value="#{item.strDetalle}" title="#{item.strDetalle}"/>
		    	</rich:column>
		 		<rich:column width="250px" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Observación"/>
		        	</f:facet>
		            <h:outputText value="#{item.strObservacion}" title="#{item.strObservacion}"/>
		     	</rich:column>
		     	<rich:column width="70" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Editar"/>
		         	</f:facet>
					<a4j:commandLink value="Editar"
						oncomplete="Richfaces.showModalPanel('pAgregarRequisicionDetalle')"
	                	actionListener="#{requisicionController.abrirPopUpEditarRequisicionDetalle}"
	                	disabled="#{requisicionController.deshabilitarNuevo}"
	                	reRender="pAgregarRequisicionDetalle">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>
				</rich:column>
				<rich:column width="70" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Eliminar"/>
		         	</f:facet>
					<a4j:commandLink value="Eliminar"
						disabled="#{requisicionController.deshabilitarNuevo}"
						reRender="panelDetalleR"
						actionListener="#{requisicionController.quitarRequisicionDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>				
				</rich:column>
		  	</rich:dataTable>
		  	</rich:column>		        
		</h:panelGrid>
	
	</rich:panel>
	
	</h:panelGroup>	

</rich:panel>
</h:form>