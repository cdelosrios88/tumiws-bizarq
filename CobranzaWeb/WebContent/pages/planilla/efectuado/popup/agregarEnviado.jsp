<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico    		  -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO   			  -->
	<!-- Fecha     : 22/10/2013               -->
	
	<h:panelGrid columns="2" id="panelSocio">
			<rich:column width="100">
				<h:outputText value="Socio : " />
			</rich:column>
			<rich:column width="500">
			<h:inputText  size="65" readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align:center" 
						value="#{efectuadoController.registroSeleccionadoEnAE.socio.id.intIdPersona} 
							   #{efectuadoController.registroSeleccionadoEnAE.socio.strApePatSoc}
							   #{efectuadoController.registroSeleccionadoEnAE.socio.strApeMatSoc}, 						        
						       #{efectuadoController.registroSeleccionadoEnAE.socio.strNombreSoc}- 
						       #{efectuadoController.registroSeleccionadoEnAE.documento.strNumeroIdentidad}"/>
			</rich:column>	
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelDependenciaOrig" rendered="#{efectuadoController.blnDependenciaOrigen}">
		<rich:column width="100">
				<h:outputText value="Dependencia: " />
		</rich:column>
		<rich:column width="500">
				<h:inputText size="65" value="#{efectuadoController.juridicaSuc.strRazonSocial}-   
						    #{efectuadoController.juridicaUE.strRazonSocial}-
						    #{efectuadoController.dtoDeEfectuado.strModalidad}"
							readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align:center" />
		</rich:column>		
	</h:panelGrid>
	
	
	<h:panelGrid columns="2" id="panelDependenciaFinal" rendered="#{!efectuadoController.blnDependenciaOrigen}">
		<rich:column width="100">
				<h:outputText value="Dependencia: " />
		</rich:column>
		<rich:column width="500">
				<h:inputText size="65" value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial}   
						    #{efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial}-
						    #{efectuadoController.dtoDeEfectuado.strModalidad}"
							readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align:center" />
		</rich:column>		
	</h:panelGrid>
	
	<h:panelGrid  id="panelMesPeriodo" columns="2">		
		<rich:columnGroup>
	    	<rich:column width="600">
	    		<h:inputText value="Periodo" disabled="true" style="width:70px;"/>
	    		
				<tumih:inputText  cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" style="width:65px;"
				property="#{efectuadoController.dtoDeEfectuado.intPeriodoMes}" disabled="true"/>	
	
				<h:inputText value=" #{efectuadoController.dtoDeEfectuado.intPeriodoAnio}"
							 disabled="true" style="width:40px;"/>
		</rich:column>
	   </rich:columnGroup>
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelEnviado1">
		<rich:column width="75">
				<h:outputText value="Enviado: " />
		</rich:column>
		<rich:column width="500">
				<h:inputText value="#{efectuadoController.registroSeleccionadoEnAE.efectuado.envioMonto.bdMontoenvio}" size="8"
							 readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align: right" />
		</rich:column>
	</h:panelGrid>
	<h:panelGrid columns="2" id="panelEfectuado">
		<rich:column width="75">
				<h:outputText value="Efectuado: " />
		</rich:column>
		<rich:column width="500">				
				<h:inputText  onblur="extractNumber(this,2,false);" onkeyup="return extractNumber(this,2,false)"
							 onkeypress="return soloNumerosDecimalesPositivos(this)" size="8" style="text-align: right"
							 value="#{efectuadoController.registroSeleccionadoEnAE.efectuado.bdMontoEfectuado}"
							 disabled="#{efectuadoController.blnAgregarPlanilla}"/>		
		</rich:column>
	</h:panelGrid>
		<h:panelGroup rendered="#{efectuadoController.blnAgregarPlanilla}" id="pgOculto" >
		<h:panelGrid  id="panelConsideraciones" width="100%">
			<h:panelGrid style="margin:0 auto; margin-bottom:10px" rendered="#{efectuadoController.listaExpediente != null}">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CONSIDERACIONES" style="font-weight:bold; font-size:13px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
			
			<rich:dataTable  value="#{efectuadoController.listaExpediente}" styleClass="dataTable1" width="100%" id="dtPrestamos"
			  rows="5" sortMode="single" style="margin:0 auto text-align: left" var="item" rowKeyVar="rowKey" rendered="#{efectuadoController.listaExpediente != null}">						
				<rich:column style="text-align: left" width="15%" rendered="#{item.bdprimeraCuota != 0}">				
					<h:outputText value="PRESTAMO:" rendered="#{item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_PRESTAMO}"/>
					<h:outputText value="ORDEN CREDITO:" rendered="#{item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO}"/>
					<h:outputText value="ACTIVIDAD:" rendered="#{item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD}"/>
					<h:outputText value="REFINANCIADO:" rendered="#{item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO}"/>
				</rich:column>	
				<rich:column style="text-align: left" width="60%" rendered="#{item.bdprimeraCuota != 0}">
				<h:outputText value="Solicitud: #{item.id.intItemExpediente}-#{item.id.intItemExpedienteDetalle}
									/Monto: #{item.bdSaldoCredito}/Primera Cuota:#{item.bdprimeraCuota}"/>
				</rich:column>
				<rich:column width="25%" style="text-align: left" rendered="#{item.bdprimeraCuota != 0}">					
					<h:outputText value="ENERO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_ENERO}"/>
					<h:outputText value="FEBRERO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_FEBRERO}"/>
					<h:outputText value="MARZO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_MARZO}"/>
					<h:outputText value="ABRIL " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_ABRIL}"/>
					<h:outputText value="MAYO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_MAYO}"/>
					<h:outputText value="JUNIO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_JUNIO}"/>
					<h:outputText value="JULIO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_JULIO}"/>
					<h:outputText value="AGOSTO " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_AGOSTO}"/>
					<h:outputText value="SETIEMBRE " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_SETIEMBRE}"/>
					<h:outputText value="OCTUBRE " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_OCTUBRE}"/>
					<h:outputText value="NOVIEMBRE " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_NOVIEMBRE}"/>
					<h:outputText value="DICIEMBRE " rendered="#{item.intMes==applicationScope.Constante.PARAM_T_MES_DICIEMBRE}"/>
					
					<h:outputText value="#{item.intAnio}"/>
				</rich:column>				
			</rich:dataTable>
		</h:panelGrid>		  			  		
		
		<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="RUBROS A CONSIDERAR POR EL MONTO INGRESADO" style="font-weight:bold; font-size:13px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    	</h:panelGrid>
		
	<h:panelGrid  id="panelAportaciones">		
		<rich:column width="100%">					  			
			<h:outputText value="Aporte:#{efectuadoController.bdAporte} " rendered="#{efectuadoController.bdAporte != null && blnFiltrado==TRUE}"/>			  
			<h:outputText value=" Sepelio:#{efectuadoController.bdSepelio} " rendered="#{efectuadoController.bdSepelio != null && blnFiltrado==TRUE}"/>			
			<h:outputText value=" Mantenimiento:#{efectuadoController.bdMant} " rendered="#{efectuadoController.bdMant != null && blnFiltrado==TRUE}"/>			
			<h:outputText value=" Fondo de Retiro:#{efectuadoController.bdFdoRetiro}" rendered="#{efectuadoController.bdFdoRetiro != null && blnFiltrado==TRUE}"/>					
		</rich:column>
	</h:panelGrid>
	
	<rich:spacer height="4px"/>
	
	<rich:spacer height="8px"/>
	<h:panelGrid id="panelConceptos">
		<rich:dataTable  value="#{efectuadoController.listaTemporal}" styleClass="dataTable1" width="100%" id="dtConceptos"
			  rows="10" sortMode="single" style="margin:0 auto text-align: left" var="item" rowKeyVar="rowKey">
			<rich:column style="text-align: left" width="85%" rendered="#{item.intItemexpediente != null}">
				<h:outputText value="Prestamo (Adelanto-Amortizacion) #{item.intItemexpediente}-#{item.intItemdetexpediente}: #{item.bdMontoconcepto}" rendered="#{item.intTipoconceptogeneralCod==applicationScope.Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION
																		&& item.intItemexpediente != null && item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_PRESTAMO}"/>
				<h:outputText value="Actividad (Adelanto-Amortizacion) #{item.intItemexpediente}-#{item.intItemdetexpediente}: #{item.bdMontoconcepto}" rendered="#{item.intTipoconceptogeneralCod==applicationScope.Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION  
																		&& item.intItemexpediente != null && item.intParaTipoCreditoCod==applicationScope.Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD}"/>
				<h:outputText value="INTERES #{item.intItemexpediente}-#{item.intItemdetexpediente}: #{item.bdMontoconcepto}" rendered="#{item.intTipoconceptogeneralCod==applicationScope.Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES}"/>
			</rich:column>
			
		</rich:dataTable>	  
	</h:panelGrid>
	<rich:spacer height="6px"/>
	<h:panelGrid  id="panelCuentasPorPagar">		
		<rich:column width="100%">	
			<h:outputText value="Cuentas Por Pagar: #{efectuadoController.bdCuentaPorPagar}" rendered="#{efectuadoController.bdCuentaPorPagar > 0}"/>
		</rich:column>
	</h:panelGrid>
	<rich:spacer height="6px"/>
	<h:panelGrid width="100%">
			<a4j:commandButton  value="AGREGAR A LA PLANILLA" action="#{efectuadoController.agregarALaPlanilla}"
			 				    style="width:100%" styleClass="btnEstilos"
			 				    rendered="#{!efectuadoController.blnPerteneceAlaPlanilla}"
			 					oncomplete="Richfaces.hideModalPanel('idPopupAgregarEnviado'),
			 			 				    Richfaces.hideModalPanel('pAgregarSocioEnEfectuado')"
			 				   reRender="idGrillaTieneEfectuada, idGrillaNoTieneEfectuada, idGrillaDePlanillaEfectuado"/>
			<a4j:commandButton value="AGREGAR A LA PLANILLA" style="width:100%" styleClass = "btnEstilos"
								rendered="#{efectuadoController.blnPerteneceAlaPlanilla}"
								oncomplete="Richfaces.showModalPanel('idPopupAlertaSocioPerteneceAlaPlanilla'),
											Richfaces.hideModalPanel('idPopupAgregarEnviado'),
											Richfaces.hideModalPanel('pAgregarSocioEnEfectuado')"/>			
	</h:panelGrid>
	</h:panelGroup>
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: left"
				styleClass="rich-tabcell-noborder">
				<h:outputText value="#{efectuadoController.mensajeOperacion}" 
					styleClass="msgInfo"
					style="font-weight:bold"
					rendered="#{efectuadoController.mostrarMensajeExito}"/>
				<h:outputText value="#{efectuadoController.mensajeOperacion}" 
					styleClass="msgError"
					style="font-weight:bold"
					rendered="#{efectuadoController.mostrarMensajeError}"/>
		</h:panelGroup>
	
		<h:panelGrid  columns="2" id="panelCalcular" width="100%">
			<rich:column colspan="2">
				<a4j:commandButton value="CALCULAR" styleClass="btnEstilos" style="width:100%"
				 action="#{efectuadoController.calcularAgregarEnviado}"
				 rendered="#{!efectuadoController.blnAgregarPlanilla && !efectuadoController.blnDependenciaOrigen}" 
				 reRender="idFrmPopupAgregarEnviado,pgOculto,panelCuentasPorPagar,panelMensaje,panelCalcular"/>				
			</rich:column>
		</h:panelGrid>
		<h:panelGrid  columns="2" id="panelCalcularAdvertencia" width="100%">
			<rich:column colspan="2">
				<a4j:commandButton value="CALCULAR" styleClass="btnEstilos" style="width:100%"
				 oncomplete="Richfaces.showModalPanel('idPopupAlertaAgregarEnviado'), Richfaces.hideModalPanel('idPopupAgregarEnviado')"
				 rendered="#{!efectuadoController.blnAgregarPlanilla && efectuadoController.blnDependenciaOrigen}" 
				reRender="idFrmPopupAlertaAE,idPopupAlertaAgregarEnviado"/>				
			</rich:column>
		</h:panelGrid>
	
		
		
	