<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi         				-->
	<!-- Autor     : Christian De los Ríos    					-->
	<!-- Modulo    : Créditos - Garantías     					-->
	<!-- Prototipo : PROTOTIPO POPUP CAPACIDAD DE PAGOS - CAS	-->
	<!-- Fecha     : 16/07/2012               					-->
	<script>
	</script>
	
	<a4j:region>
		<a4j:jsFunction name="getTotalIngreso" reRender="pagoHabIdBaseCalculo"
			actionListener="#{capacidadPagoController.getBaseCalculoPagoHaberes}">
			<f:param name="bdTotalIngreso"/>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="getBaseTotalDscto" reRender="pagoHabIdBaseTotDscto"
			actionListener="#{capacidadPagoController.getBaseTotalDscto}">
			<f:param name="bdBaseTotalDscto"/>
		</a4j:jsFunction>
	</a4j:region>
	
    	<rich:panel style="border:0px solid #17356f;background-color:#DEEBF5;width:940px;">
    		<h:panelGrid columns="6">
    			<rich:column>
    				<h:outputText value="Socio :"/>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.persona.intIdPersona} - #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoPaterno} #{solicitudPrestamoController.beanSocioComp.persona.natural.strApellidoMaterno}, #{solicitudPrestamoController.beanSocioComp.persona.natural.strNombres} - DNI : #{solicitudPrestamoController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="70" disabled="true"/>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Nro. Cuenta :"/>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{solicitudPrestamoController.beanSocioComp.cuenta.strNumeroCuenta}" size="30" disabled="true"/>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Condición :"/>
    			</rich:column>
    			<rich:column>
    				<rich:column>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
			                              itemValue="intIdDetalle" itemLabel="strDescripcion"
			                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaCondicionCuentaCod}"/> -
			            <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
			                              itemValue="intIdDetalle" itemLabel="strDescripcion"
			                              property="#{solicitudPrestamoController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}"/>
					</rich:column>
    			</rich:column>
    		</h:panelGrid>
    		
    		<h:panelGrid columns="3">
    			<rich:column>
    				<a4j:commandButton id="btnCapacidadPagoHaberes" value="Grabar" actionListener="#{capacidadPagoController.grabarCapacidadCredito}" 
    					oncomplete="if(#{capacidadPagoController.validCapacidadCredito}){Richfaces.hideModalPanel('mpCapacidadCredito')}"
    					styleClass="btnEstilos" reRender="frmCapacidadPago,pgSolicCredito,pgListCapacidadCredito,pgDatosSocio,pgSolicCredito,pgRequisitos,pgEvaluacion,pgPrestamo1,pgPrestamo2,pgCondiciones,pgMsgErrorEvaluacion,pgListDocAdjuntos"
    					disabled="#{capacidadPagoController.blnMostrarBoton}"
    					/>
    			</rich:column>
    			<rich:column>
    				<a4j:commandButton value="Cancelar" styleClass="btnEstilos" 
    					oncomplete="{Richfaces.hideModalPanel('mpCapacidadCredito')}" />
    			</rich:column>
    			<rich:column id="pgMsgErrorCapacidad">
					<h:outputText value="#{capacidadPagoController.strMsgErrorCapacidad}" styleClass="msgError"/>
				</rich:column>
    		</h:panelGrid>
    		
    		<rich:panel style="border: 2px solid #17356f;background-color:#DEEBF5;width:920px;">
    			<h:panelGrid columns="2">
    				<rich:column>
    					<b><u>CONCEPTOS</u></b>
    				</rich:column>
    				<rich:column style="padding-left:600px">
    					<a4j:commandButton value="Verificar Terceros" styleClass="btnEstilos1" reRender="mpTercerosPrestamo"
							actionListener="#{solicitudPrestamoController.listarTercerosSolicitudPrestamo}"
							oncomplete="Richfaces.showModalPanel('mpTercerosPrestamo')"></a4j:commandButton>
    				</rich:column>
    			</h:panelGrid>
    			
    			<!-- Autor: jchavez / Tarea: Modificación / Fecha: 28.08.2014 -->
    			<h:panelGrid columns="2">
    				<h:selectBooleanCheckbox value="#{capacidadPagoController.blnChkCartaAutorizacion}"
    					disabled="#{solicitudPrestamoController.blnEsMINSA}">
    					<a4j:support event="onclick" actionListener="#{capacidadPagoController.enableDisableControls}" reRender="pgIndiceDscto"/>
    				</h:selectBooleanCheckbox>Tiene Carta de Autorización (Descuento 100%)
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgTotalIngresos" columns="4">
    				<rich:column width="220px">
    					<h:outputText value="A.- Total de Ingresos" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText id="idTotalIngresos" value="#{capacidadPagoController.beanCapacidadCredito.bdTotalIngresos}" 
    						onblur="extractNumber(this,2,false);getTotalIngreso(this.value);" 
    						onkeyup="extractNumber(this,2,false);" size="15"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Dato de boleta (Ingreso Bruto), no considerar escolaridad, gratificación y reintegros"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgIndiceDscto" columns="4">
    				<rich:column width="220px">
    					<h:outputText value="B.- Índice de Descuentos" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText id="idIndiceDscto" value="#{capacidadPagoController.beanCapacidadCredito.bdIndiceDescuento}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="% según dependencia y tipo de planilla, excepto quienes cuenten con carta de autorización"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid columns="4">
    				<rich:column width="220px">
    					<h:outputText value="C.- Base del Calculo" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText id="pagoHabIdBaseCalculo" value="#{capacidadPagoController.beanCapacidadCredito.bdBaseCalculo}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Producto A x B"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid columns="4">
    				<rich:column width="220px">
    					<h:outputText value="D.- Total de descuentos en Recibo" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdTotalDescuento}" 
    						onblur="extractNumber(this,2,false); getBaseTotalDscto(this.value);"
    						onkeyup="extractNumber(this,2,false);" size="15"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Total de egresos mostrados en el recibo"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid columns="4">
    				<rich:column width="220px">
    					<h:outputText value="E.- Base Total de descuentos" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText id="pagoHabIdBaseTotDscto" value="#{capacidadPagoController.beanCapacidadCredito.bdBaseTotal}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Diferencia C - D"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgDsctosVarios" columns="5">
    				<rich:column width="220px">
    					<h:outputText value="F.- Descuentos Varios" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText id="pagoHabIdDsctoVarios" value="#{capacidadPagoController.bdDsctosVarios}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<a4j:commandButton value="Agregar" actionListener="#{capacidadPagoController.addDescuentoVarios}" reRender="pgListCapacidadDscto,pgTotalEntidades" styleClass="btnEstilos"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Cooperativas, Tumi, Bancos, Cafae, Mutuales, otros"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgListCapacidadDscto" columns="2">
    				<rich:column width="230px"/>
    				<rich:column>
	    				<rich:dataTable var="item" rowKeyVar="rowKey" sortMode="single" width="300px"
	    					value="#{capacidadPagoController.beanCapacidadCredito.listaCapacidadDscto}"
	    					rendered="#{not empty capacidadPagoController.beanCapacidadCredito.listaCapacidadDscto}">
	    					<rich:column>
	    						<f:facet name="header">
	    							<h:outputText value="Entidad"/>
	    						</f:facet>
	    						<h:inputText value="#{item.strEntidad}" size="20"/>
	    					</rich:column>
	    					<rich:column>
	    						<f:facet name="header">
	    							<h:outputText value="Monto"/>
	    						</f:facet>
	    						<h:inputText value="#{item.bdMonto}" 
	    							onblur="extractNumber(this,2,false);" 
	    							onkeyup="extractNumber(this,2,false);" size="20"/>
	    					</rich:column>
	    					<rich:column>
	    						<a4j:commandLink id="lnkDeleteCapacDscto" styleClass="no-decor" reRender="pgListCapacidadDscto,pgDsctosVarios,pgTotalEntidades,pagoHabIdDsctoVarios,pgBaseTotYDsctos,pgCapacPagoCas"
				            		actionListener="#{capacidadPagoController.removeCapacidadDscto2}"
				            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
				                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
				                    <f:param value="#{rowKey}"  		name="rowKeyCapacidadDscto"/>
				            		<rich:toolTip for="lnkDeleteCapacDscto" value="Eliminar" followMouse="true"/>
				            	</a4j:commandLink>
	    					</rich:column>
	    				</rich:dataTable>
	    				<%--<a4j:commandButton value="Calcular Dsctos" actionListener="#{capacidadPagoController.calcularDsctos}" styleClass="btnEstilos1" rendered="#{not empty capacidadPagoController.beanCapacidadCredito.listaCapacidadDscto}" reRender="pgDsctosVarios"/>--%>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgBaseTotYDsctos" columns="4">
    				<rich:column width="220px">
    					<h:outputText value="G.- E + Descuentos" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText value="#{capacidadPagoController.bdBaseTotYDsctos}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Suma de E + F"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgTotalEntidades" columns="4">
    				<rich:column width="220px">
    					<h:outputText value="H.- Nro. Total de entidades" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.intNroEntidades}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Número de entidades de descuentos varias, en caso de ser Promocional se suma una entidad más"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid columns="1">
    				<rich:column>
    					<a4j:commandButton value="Calcular" actionListener="#{capacidadPagoController.calcularCapacidadPago}" styleClass="btnEstilos" 
    					reRender="pgBaseTotYDsctos,pgTotalEntidades,pgCapacPagoCas,pgDsctosVarios,pgMsgErrorCapacidad,btnCapacidadPagoHaberes"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid id="pgCapacPagoCas" columns="4">
    				<rich:column width="220px">
    					<h:outputText value="Capacidad de Pago - Haberes" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdCapacidadPago}" size="15" disabled="true"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Producto (G / H)"/>
    				</rich:column>
    			</h:panelGrid>
    			
    			<h:panelGrid columns="5">
    				<rich:column width="220px">
    					<h:outputText value="Cuota Fija" style="font-weight:bold;"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value=":"/>
    				</rich:column>
    				<rich:column>
    					<h:inputText value="#{capacidadPagoController.beanCapacidadCredito.bdCuotaFija}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" size="15"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="Monto a descontar por este tipo de planilla"/>
    				</rich:column>
    				<rich:column>
    					<h:outputText value="#{capacidadPagoController.msgTxtCuotaFija}" styleClass="msgError"/>
    				</rich:column>
    			</h:panelGrid>
    		</rich:panel>
    	</rich:panel>