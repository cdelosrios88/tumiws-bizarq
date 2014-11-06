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
	    				<h:outputText value="SALDOS DE CAJA" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	
       		<h:panelGrid columns="11" id="panelBuscar">            	
            	<rich:column width="70" style="text-align: left;">
                	<h:outputText value="Fecha Cierre: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{saldoController.saldoFiltro.dtFechaDesde}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{saldoController.saldoFiltro.dtFechaHasta}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="60">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="140">
					<h:selectOneMenu
						value="#{saldoController.saldoFiltro.id.intSucuIdSucursal}"
						style="width: 140px;">
						<tumih:selectItems var="sel"
							value="#{saldoController.listaTablaSucursal}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>												
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="50">
					<h:outputText value="Bancos :"/>
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						disabled="#{saldoController.intIdFondoFiltro!=0}"
						value="#{saldoController.intIdBancoFiltro}"
						style="width: 130px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{saldoController.listaBanco}"
							itemValue="#{sel.id.intItembancofondo}"
							itemLabel="#{sel.strEtiqueta}"/>
						<a4j:support event="onchange" reRender="panelBuscar"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="70">
					<h:outputText value="Fondo Fijo :"/>
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						disabled="#{saldoController.intIdBancoFiltro!=0}"
						value="#{saldoController.intIdFondoFiltro}"
						style="width: 130px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{saldoController.listaFondo}"
							itemValue="#{sel.id.intItembancofondo}"
							itemLabel="#{sel.strEtiqueta}"/>												
						<a4j:support event="onchange" reRender="panelBuscar"/>
					</h:selectOneMenu>
				</rich:column>
            	<rich:column width="90" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosS,panelMensajeS"
                    	action="#{saldoController.buscar}" 
                    	style="width:80px"/>
            	</rich:column>
            </h:panelGrid>
            
            <rich:spacer height="12px"/>           
            
            <h:panelGrid id="panelTablaResultadosS">
	        	<rich:extendedDataTable id="tblResultadoS" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{saldoController.listaSaldo}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1020px" 
					height="160px" 
					align="center">
                    
					 <rich:column width="30" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.id.dtFechaSaldo}">
                      		<f:convertDateTime pattern="dd/MM/yyyy"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Banco / Fondo Fijo"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strEtiqueta}"/>
                  	</rich:column>
                    <rich:column width="200px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strEtiquetaSucursal}"/>
                	</rich:column>
                    <rich:column width="180" style="text-align: rigth">
                    	<f:facet name="header">
                      		<h:outputText value="Saldo Inicial"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.bdSaldoInicial}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="180" style="text-align: rigth">
                   		<f:facet name="header">
                        	<h:outputText value="Movimientos"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMovimientos}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>                  	
                  	<rich:column width="180" style="text-align: rigth">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo Final"/>
                        </f:facet>
                        <h:outputText value="#{item.bdSaldoInicial + item.bdMovimientos}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoS" maxPages="10"/>   
					</f:facet>
					
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
			
						
		<h:panelGroup id="panelMensajeS" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{saldoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{saldoController.mostrarMensajeExito}"/>
			<h:outputText value="#{saldoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{saldoController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesS">
			<h:panelGrid columns="4">
				<!-- Inicio: REQ14-005 - bizarq - 19/10/2014 -->
				<%--
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px"
					action="#{saldoController.habilitarPanelInferiorNuevo}"
					reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/>
				<a4j:commandButton value="Anular" styleClass="btnEstilos" style="width:90px"
					action="#{saldoController.habilitarPanelInferiorAnular}"
					reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/>	
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{saldoController.grabar}"
			    	reRender="contPanelInferiorS,panelMensajeS,panelBotonesS,panelTablaResultadosS"
			    	disabled="#{!saldoController.habilitarGrabar}"/>
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{saldoController.deshabilitarPanelInferior}"
			    	reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/> --%>
			    <a4j:commandButton value="Generar Saldos" styleClass="btnEstilos" style="width:150px"
					action="#{saldoController.habilitarPanelInferiorNuevo}"
					reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/>
				<a4j:commandButton value="Anular Saldos" styleClass="btnEstilos" style="width:150px"
					action="#{saldoController.habilitarPanelInferiorAnular}"
					reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/>
				<a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{saldoController.deshabilitarPanelInferior}"
			    	reRender="contPanelInferiorS,panelMensajeS,panelBotonesS"/>
			    <!-- Fin: REQ14-005 - bizarq - 19/10/2014 -->
			</h:panelGrid>
		</h:panelGroup>
		
	<h:panelGroup id="contPanelInferiorS">
			
		<rich:panel rendered="#{saldoController.mostrarPanelInferiorNuevo}" style="border:1px solid #17356f;background-color:#DEEBF5;">

			<rich:spacer height="8px"/>
			
			<h:panelGrid columns="6">
				<rich:column width="200">
					<h:outputText value="Ultima Fecha Generada :"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">
	               	<h:inputText readonly="true"
						style="background-color: #BFBFBF;"
	               		value="#{saldoController.dtUltimaFechaGenerada}">
	                	<f:convertDateTime pattern="dd/MM/yyyy"/>
	               	</h:inputText>
	            </rich:column>
	            <rich:column width="200">
					<h:outputText value="Fecha de Último Cierre General :"/>
				</rich:column>
				<rich:column width="200">
					<h:inputText readonly="true"
						style="background-color: #BFBFBF;"
	               		value="#{saldoController.dtUltimaFechaCierreGeneral}">
	                	<f:convertDateTime pattern="dd/MM/yyyy"/>
	               	</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="5">
				<rich:column width="160">
					<h:outputText value="Rango de Fechas a Generar"/>
				</rich:column>
				<rich:column width="35">
					<h:outputText value="Inicio :"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">	               	
	               	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{saldoController.dtFechaInicioSaldo}"  
						disabled="#{saldoController.deshabilitarNuevo}"
						jointPoint="top-right" 
						direction="right"
						inputSize="16"
						showApplyButton="true"/>
	            </rich:column>
	            <rich:column width="40">
					<h:outputText value="Fin :"/>
				</rich:column>
				<rich:column width="210">
					<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{saldoController.dtFechaFinSaldo}"  
						disabled="#{saldoController.deshabilitarNuevo}"
						jointPoint="top-right"
						direction="right"
						inputSize="15"
						showApplyButton="true"/>
				</rich:column>
			</h:panelGrid>
			<!-- Inicio: REQ14-005 - bizarq - 19/10/2014 -->
			<h:panelGrid columns="3">
				<rich:column width="200">
					<h:outputText value="Contraseña :"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">	               	
	               	<h:inputSecret value="#{saldoController.strPassword}" size="20"
	               		disabled="#{saldoController.deshabilitarNuevo}"/>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Procesar" styleClass="btnEstilos"
	            		action="#{saldoController.processDailyAmount}"
	            		reRender="contPanelInferiorS,panelMensajeS,panelBotonesS,panelTablaResultadosS"
	            		disabled="#{saldoController.deshabilitarNuevo}"/>
	            </rich:column>
			</h:panelGrid>
			<!-- Fin: REQ14-005 - bizarq - 19/10/2014 -->
		</rich:panel>
		
		<rich:panel rendered="#{saldoController.mostrarPanelInferiorAnular}" style="border:1px solid #17356f;background-color:#DEEBF5;">

			<rich:spacer height="8px"/>
			<h:panelGrid columns="6">
				<rich:column width="160">
					<h:outputText value="Ultima Fecha Generada :"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">
	               	<h:inputText readonly="true"
						style="background-color: #BFBFBF;"
	               		value="#{saldoController.dtUltimaFechaGenerada}">
	                	<f:convertDateTime pattern="dd/MM/yyyy"/>
	               	</h:inputText>
	            </rich:column>
	            <rich:column width="200">
					<h:outputText value="Fecha de Último Cierre General :"/>
				</rich:column>
				<rich:column width="200">
					<h:inputText readonly="true"
						style="background-color: #BFBFBF;"
	               		value="#{saldoController.dtUltimaFechaCierreGeneral}">
	                	<f:convertDateTime pattern="dd/MM/yyyy"/>
	               	</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="6">
				<rich:column width="160">
					<h:outputText value="Anular desde la fecha"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">	               	
	               	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{saldoController.dtFechaInicioSaldo}"  
						disabled="#{saldoController.deshabilitarNuevo}"
						jointPoint="top-right"
						direction="right"
						inputSize="16"
						showApplyButton="true"/>
	            </rich:column>	            
			</h:panelGrid>
			
			<!-- Inicio: REQ14-005 - bizarq - 19/10/2014 -->
			<h:panelGrid columns="3">
				<rich:column width="160">
					<h:outputText value="Motivo :"/>
				</rich:column>
	            <rich:column>
	            	<h:inputTextarea value="#{saldoController.strAnulReason}" cols="80" rows="3"
	            					 disabled="#{saldoController.deshabilitarNuevo}"/>
	            </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="3">
				<rich:column width="160">
					<h:outputText value="Contraseña :"/>
				</rich:column>
				<rich:column width="150" style="text-align: left;">	               	
	               	<h:inputSecret value="#{saldoController.strPasswordAnula}" size="20"/>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Procesar" styleClass="btnEstilos"
	            		action="#{saldoController.anulateDailyAmount}"
	            		reRender="contPanelInferiorS,panelMensajeS,panelBotonesS,panelTablaResultadosS"
	            		disabled="#{saldoController.deshabilitarNuevo}"/>
	            </rich:column>
			</h:panelGrid>
			<!-- Fin: REQ14-005 - bizarq - 19/10/2014 -->
		</rich:panel>
	</h:panelGroup>	
	</rich:panel>
</h:form>
