<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modificado: Junior Chavez 10.12.2013 -->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->





<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;width: 1100px">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CIERRE DIARIO Y ARQUEO" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	
       		<h:panelGrid columns="12" id="panelCFFFiltroF">
            	<rich:column width="60">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="150">
					<h:selectOneMenu value="#{cierreDiarioController.intIdSucursal}" disabled="#{cierreDiarioController.blnDisabledSucursal}" style="width: 160px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{cierreDiarioController.listaSucursalBus}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>					
				</rich:column>
            	<rich:column width="50" style="text-align: left;">
                	<h:outputText value="Arqueo :"/>
            	</rich:column>
            	<rich:column width="140">
            		<h:selectOneMenu
						style="width: 137px;"
						value="#{cierreDiarioController.cierreDiarioArqueoFiltro.id.intParaTipoArqueo}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOARQUEO}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{cierreDiarioController.dtFechaFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>            	
            	<rich:column width="80" style="text-align: left;">
                	<h:outputText value="Responsable :"/>
            	</rich:column>
            	<rich:column width="100">
            		<h:selectOneMenu
						value="#{cierreDiarioController.intTipoFiltroPersona}"
						style="width: 90px;">
						<f:selectItem itemValue="1" itemLabel="Nombre"/>
						<f:selectItem itemValue="2" itemLabel="DNI"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="90" style="text-align: left;">
                	<h:inputText value="#{cierreDiarioController.strTextoFiltroPersona}" size="32"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosCD,panelMensajeCD"
                    	action="#{cierreDiarioController.buscar}" 
                    	style="width:90px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosCD">
	        	<rich:extendedDataTable id="tblResultadoCD" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{cierreDiarioController.listaCierreDiarioArqueo}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1020px" 
					height="160px" 
					align="center">
                                
					 <rich:column width="20" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>
                    </rich:column>
                    <rich:column width="140" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaCierreArqueo}">
                      		<f:convertDateTime pattern="dd/MM/yyyy HH:mm"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="130" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOARQUEO}"
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.id.intParaTipoArqueo}"/>
                  	</rich:column>
                    <rich:column width="100px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strSiglas}"/>
                	</rich:column>
                    <rich:column width="250" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Responsable"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.persona.natural.strNombreCompleto}"
                      		title="#{item.persona.natural.strNombreCompleto}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.intParaEstadoCierre}"/>
                  	</rich:column>                  	
                  	<rich:column width="140" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Ingreso Pendiente"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoIngresoPendiente}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                    <rich:column width="140" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Diferencia Resumen"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoDiferencia}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>                  	
                  
                   	
                   	<a4j:support event="onRowClick"  
						actionListener="#{cierreDiarioController.seleccionarRegistro}"
						reRender="pAlertaRegistroCD"
						oncomplete="if(#{item.intParaEstadoCierre==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}){Richfaces.showModalPanel('pAlertaRegistroCD')}">
                        <f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoCD" maxPages="10"/>   
					</f:facet>
					
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>

			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Ver o Anular hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>
			
						
		<h:panelGroup id="panelMensajeCD" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{cierreDiarioController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{cierreDiarioController.mostrarMensajeExito}"/>
			<h:outputText value="#{cierreDiarioController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{cierreDiarioController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesCD">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{cierreDiarioController.habilitarPanelInferior}"
					reRender="contPanelInferiorCD,panelMensajeCD,panelBotonesCD"/>
				<a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{cierreDiarioController.grabar}" 
			    	reRender="contPanelInferiorCD,panelMensajeCD,panelBotonesCD,panelTablaResultadosCD"
			    	disabled="#{!cierreDiarioController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{cierreDiarioController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorCD,panelMensajeCD,panelBotonesCD"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorCD">
			
		<rich:panel  rendered="#{cierreDiarioController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		

			<rich:spacer height="8px"/>
			
			<h:panelGrid columns="7" id="panelSucursalCierre">
				<rich:column width="120">
					<h:outputText value="Fecha de Arqueo :"/>
				</rich:column>
				<rich:column width="110" style="text-align: right;">
	               	<h:inputText readonly="true" id="txtFechaArqueo"
						style="background-color: #BFBFBF;"
	               		value="#{cierreDiarioController.cierreDiarioArqueo.tsFechaCierreArqueo}">
	                	<f:convertDateTime pattern="dd/MM/yyyy"/>
	               	</h:inputText>
	            </rich:column>
	            <rich:column width="110">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="200">
					<h:selectOneMenu
						disabled="#{cierreDiarioController.deshabilitarNuevo || !cierreDiarioController.sucursalSesionEsSede}"
						value="#{cierreDiarioController.cierreDiarioArqueo.id.intSucuIdSucursal}"
						style="width: 190px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cierreDiarioController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strSiglas}"/>
						<a4j:support event="onchange"
							action="#{cierreDiarioController.seleccionarSucursal}" 
							reRender="contPanelInferiorCD"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Sub-Sucursal :" />
				</rich:column>
				<rich:column width="130">
					<h:selectOneMenu
						disabled="#{cierreDiarioController.deshabilitarNuevo || !cierreDiarioController.sucursalSesionEsSede}"
						value="#{cierreDiarioController.cierreDiarioArqueo.id.intSudeIdSubsucursal}"
						style="width: 120px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel"
							value="#{cierreDiarioController.listaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}"
							itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange"
							action="#{cierreDiarioController.seleccionarSubsucursal}" 
							reRender="contPanelInferiorCD"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="120" style="vertical-align: top;">
					<a4j:commandButton value="Cambiar Sucursal" styleClass="btnEstilos"style="width:150px"
			    	action="#{cierreDiarioController.actualizarSucursal}" 
			    	reRender="pCambioSucursal"
			    	rendered="#{cierreDiarioController.SESION_IDSUCURSAL == 59}"
					oncomplete="Richfaces.showModalPanel('pCambioSucursal')"/>   
				</rich:column>
			</h:panelGrid>			
			
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="6">
				<rich:column width="100" style="vertical-align: top;">
					<h:outputText value="Tipo de Arqueo :"/>
				</rich:column>
				<rich:column width="127" style="vertical-align: top;">
	               <h:selectOneMenu
	               		disabled="#{cierreDiarioController.deshabilitarNuevo}"
						value="#{cierreDiarioController.cierreDiarioArqueo.id.intParaTipoArqueo}"
						style="width: 125px;">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_TIPOARQUEO}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange"
								action="#{cierreDiarioController.seleccionarTipoArqueo}" 
								reRender="contPanelInferiorCD"/>
					</h:selectOneMenu>
	            </rich:column>
	           	<rich:column width="110">
					<h:outputText value="Responsable :" />
				</rich:column>
				<rich:column width="440" style="text-align: left;">
	               	<h:inputText readonly="true"
						size="78"
						style="background-color: #BFBFBF;"
	               		value="#{cierreDiarioController.usuarioSesion.persona.intIdPersona} - #{cierreDiarioController.usuarioSesion.persona.natural.strNombres} - DNI: #{cierreDiarioController.usuarioSesion.persona.documento.strNumeroIdentidad}">
	               	</h:inputText>
	            </rich:column>
			</h:panelGrid>

			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="6">
				<rich:column width="100" style="vertical-align: top">
					<h:outputText value="Observación :"/>
				</rich:column>
				<rich:column>
					<h:inputTextarea rows="2" cols="126"
						value="#{cierreDiarioController.cierreDiarioArqueo.strObservacion}"
						disabled="#{cierreDiarioController.deshabilitarNuevo}"/>
				</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="6" rendered="#{cierreDiarioController.deshabilitarNuevo}">
				<rich:column width="100" style="vertical-align: top;">
					<h:outputText value="RESUMEN :"/>
				</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="6" id="panelResumenCD" rendered="#{cierreDiarioController.deshabilitarNuevo}">
				<rich:column width="100" style="vertical-align: top;">
					<h:outputText value="Efectivo Arqueado:"/>
				</rich:column>
				<rich:column width="100" style="vertical-align: top;">
	               <h:inputText readonly="true" 
						size="20"
						style="background-color: #BFBFBF;" 
	               		value="#{cierreDiarioController.cierreDiarioArqueo.bdMontoTotalEfectivo}">
	               		<f:converter converterId="ConvertidorMontos"/>
	               	</h:inputText>
	            </rich:column>
	           	<rich:column width="110">
					<h:outputText value="Fondos Fijos y Caja :" />
				</rich:column>
				<rich:column width="200" style="text-align: left;">
	               	<h:inputText readonly="true" 
						size="33"
						style="background-color: #BFBFBF;" 
	               		value="#{cierreDiarioController.cierreDiarioArqueo.bdMontoTotalFondo}">
	               		<f:converter converterId="ConvertidorMontos"/>
	               	</h:inputText>
	            </rich:column>
	            <rich:column width="100">
					<h:outputText value="Diferencia :" />
				</rich:column>
				<rich:column width="100" style="text-align: left;">
	               	<h:inputText readonly="true" 
						size="18"
						style="background-color: #BFBFBF;" 
	               		value="#{cierreDiarioController.cierreDiarioArqueo.bdMontoDiferencia}">
	               		<f:converter converterId="ConvertidorMontos"/>
	               	</h:inputText>
	            </rich:column>
			</h:panelGrid>
			
			
			<rich:spacer height="3px"/>
			
			<h:panelGroup id="panelTablasFondosCD">
			
			<a4j:repeat value="#{cierreDiarioController.cierreDiarioArqueo.listaCierreDiarioArqueoDetalle}" var="item">
				 	
			<h:panelGrid columns="1" rendered="#{not empty item.strNumeroApertura}">
				<rich:column style="text-align: left">
					<table>
						<thead>
							<tr>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:200px;text-align: center;height: 27px" >Fondo Fijo</th>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:435px;text-align: center;height: 27px" >
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"
										itemValue="intIdDetalle" 
										itemLabel="strDescripcion"
										property="#{item.intParaTipoFondoFijo}"/></th>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:150px;text-align: center" >Monto</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Apertura"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"><h:outputText value="#{item.strNumeroApertura}"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoApertura}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Monto Rendición"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px">
									<h:inputText size="70" value="#{item.strDetalleRendido}" disabled="#{cierreDiarioController.deshabilitarNuevo}"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoRendido}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Sub Total"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoApertura - item.bdMontoRendido}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Recibos Sunat"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px">
									<h:inputText size="70" value="#{item.strDetalleReciboSunat}" disabled="#{cierreDiarioController.deshabilitarNuevo}"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoReciboSunat}" size="25" rendered="#{!cierreDiarioController.deshabilitarNuevo}" 
										disabled="#{cierreDiarioController.deshabilitarNuevo}" onkeypress="return soloNumerosDecimales(this)">
									<a4j:support event="onblur"
                   						actionListener="#{cierreDiarioController.calcularSaldoEfectivo}"
										reRender="panelTablasFondosCD,panelResumenCD">
                    					<f:attribute name="item" value="#{item}"/>
                   					</a4j:support>
									</h:inputText>
									<h:inputText value="#{item.bdMontoReciboSunat}" size="25" rendered="#{cierreDiarioController.deshabilitarNuevo}" 
										disabled="#{cierreDiarioController.deshabilitarNuevo}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Vales a Rendir"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px">
									<h:inputText size="70" value="#{item.strDetalleValeRendir}" disabled="#{cierreDiarioController.deshabilitarNuevo}"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoValesRendir}" size="25" rendered="#{!cierreDiarioController.deshabilitarNuevo}"
										disabled="#{cierreDiarioController.deshabilitarNuevo}" onkeypress="return soloNumerosDecimales(this)">
									<a4j:support event="onblur"
                   						actionListener="#{cierreDiarioController.calcularSaldoEfectivo}"
										reRender="panelTablasFondosCD,panelResumenCD">
                    					<f:attribute name="item" value="#{item}"/>
                   					</a4j:support>
									</h:inputText>
									<h:inputText value="#{item.bdMontoValesRendir}" size="25" rendered="#{cierreDiarioController.deshabilitarNuevo}"
										disabled="#{cierreDiarioController.deshabilitarNuevo}">
									<f:converter converterId="ConvertidorMontos"/>									
									</h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="SALDO EFECTIVO"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{item.bdMontoSaldoEfectivo}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
						</tbody>
					</table>
				</rich:column>
			</h:panelGrid>
			
			</a4j:repeat>
			
			</h:panelGroup>
			
			
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="1">
				<rich:column style="text-align: left">
					<table>
						<thead>
							<tr>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:200px;text-align: center;height: 27px" >Fondo Fijo</th>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:435px;text-align: center;height: 27px" >
									<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}"
										itemValue="intIdDetalle" 
										itemLabel="strDescripcion"
										property="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO_CAJA}"/></th>
								<th style="background-color:#CCCC99;border: solid 1px #6699CC;width:150px;text-align: center" >Monto</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Ingresos a Caja"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{cierreDiarioController.cierreDiarioArqueoDetalleIngresos.bdMontoIngreso}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Depósitos al Banco"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"/>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{cierreDiarioController.cierreDiarioArqueoDetalleIngresos.bdMontoDeposito}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Ajuste de Redondeo"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{cierreDiarioController.cierreDiarioArqueoDetalleIngresos.bdMontoAjuste}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
										<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr>
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Ingresos Pendiente de Depósito"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"/>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{cierreDiarioController.cierreDiarioArqueoDetalleIngresos.bdMontoIngPendiente}" readonly="true" style="background-color: #BFBFBF;text-align: right;" size="25">
									<f:converter converterId="ConvertidorMontos"/></h:inputText></td>							
							</tr>
							<!-- Autor: jchavez / Tarea: Modificación / Fecha: 14.08.2014 /
								 Funcionalidad: Se quita esta parte, segun REUNION MOD TESORERIA 13.08.2014  
							<tr>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:200px;height: 27px"><h:outputText value="Saldo Efectivo"/></td>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:435px;height: 27px"/>
								<td  style="background-color:white;border: solid 1px #6699CC;text-align: center;width:150px;height: 27px">
									<h:inputText value="#{cierreDiarioController.cierreDiarioArqueoDetalleIngresos.bdMontoSaldoEfectivo}" readonly="true" style="background-color: #BFBFBF;" size="25">
									<f:converter converterId="ConvertidorMontos"/></h:inputText></td>
							</tr> -->
						</tbody>
					</table>
				</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="3px"/>
			
			<h:panelGrid columns="1">
            	<rich:column>
					<h:outputText value="DETALLE DE BILLETES Y MONEDAS EN EFECTIVO"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="11">
            	<rich:column width="90">
					<h:outputText value="Tipo : "/>
				</rich:column>
				<rich:column width="200" style="text-align: left;">
					<h:selectOneMenu
						disabled="#{cierreDiarioController.deshabilitarNuevo}"
						value="#{cierreDiarioController.intTipoDenominacionAgregar}"
						style="width: 190px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" 
							value="#{cierreDiarioController.listaTablaDenominacion}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="100">
					<h:inputText size="10" 
						disabled="#{cierreDiarioController.deshabilitarNuevo}"
						value="#{cierreDiarioController.intCantidadDenominacionAgregar}"/>
				</rich:column>
				<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Agregar"
                		reRender="paneldetallebilletes"
                    	action="#{cierreDiarioController.agregarDenominacionBilleteMoneda}" 
                    	style="width:90px"/>
            	</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="1" id="paneldetallebilletes">
				<rich:column>
					<rich:dataTable
				    	var="item"
				    	styleClass="datatable"
				        value="#{cierreDiarioController.cierreDiarioArqueo.listaCierreDiarioArqueoBillete}"
					 	sortMode="single"
						width="550px"
			            rows="#{fn:length(cierreDiarioController.cierreDiarioArqueo.listaCierreDiarioArqueoBillete)}">
			                    
						<rich:column width="200" style="text-align: center">
			            	<f:facet name="header">
			                	<h:outputText value="Tipo"/>
			                </f:facet>
			         		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DENOMINACIONBILLETEMONEDA}"
								itemValue="intIdDetalle"
								itemLabel="strDescripcion"
								property="#{item.id.intParaTipoMonedaBillete}"/>
			            </rich:column>
			            <rich:column width="150" style="text-align: right">
			                <f:facet name="header">
			                	<h:outputText value="Cantidad"/>
			             	</f:facet>
			                <h:outputText value="#{item.intCantidad}"/>
			         	</rich:column>
			            <rich:column width="200" style="text-align: right">
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
				
			
		</rich:panel>
		
	</h:panelGroup>	

	</rich:panel>

</h:form>
