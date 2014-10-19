<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form>
	   	<h:panelGroup layout="block">
			<h:panelGrid>
				<rich:columnGroup>
					<rich:column width="100" style="text-align: center">
						<h:outputText value="Monto a Ingresar: " />
					</rich:column>
					<rich:column width="100" style="text-align: center">
						<h:inputText value="#{cajaController.bdMontoIngresadoTotal}"
							size="15"
							onkeypress="return soloNumerosDecimales(this)">
						</h:inputText>
					</rich:column>
					<rich:column width="100" style="text-align: center">		
						<a4j:commandButton styleClass="btnEstilos" id="btnSimulacionIngresoSocioC"
	                		value="Aplicar"
	                		reRender="opTablaIngresoSocioC,tablaIngresoSocioC,msgErrorMontoIngresadoC"
	                    	action="#{cajaController.simulacionIngresoMonto}"
	                    	style="width:80x"/>
                    </rich:column>
                    <rich:column width="500" style="text-align: left">		
						<a4j:commandButton styleClass="btnEstilos" id="btnAceptarIngresoSocioC"
	                		value="Aceptar"
	                		reRender="opTablaIngresoSocioC,msgErrorMontoIngresadoC,pgMontoIngresadoC,pgModalidadC,panelDetalleIngresoSocioC"
	                    	action="#{cajaController.agregarIngresoSocio}"
	                    	oncomplete="if(#{cajaController.strMsgErrorMontoIngresado == ''}){Richfaces.hideModalPanel('pBuscarMovimientoIngresoSocio')}"
	                    	style="width:80x"/>
                    </rich:column>
                    <rich:column width="200" style="text-align: center; color:blue; font-weight: bold;">
						<h:outputText value="#{cajaController.strDescPeriodo}" />
					</rich:column>
				</rich:columnGroup>		
			</h:panelGrid>
			<h:outputText id="msgErrorMontoIngresadoC" value="#{cajaController.strMsgErrorMontoIngresado}" 
				styleClass="msgError"
				style="font-weight:bold; text-align: left"
				rendered="#{!cajaController.strMsgErrorMontoIngresado}"/>		
			<h:panelGroup>
				<a4j:outputPanel id="opTablaIngresoSocioC">
					<rich:dataTable
		    			id="tablaIngresoSocioC"
		    			var="item" 
		                value="#{cajaController.listaMovimientoSocio}" 
				  		rowKeyVar="rowKey" 
				  		width="1080px">
				        <f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="150px" rowspan="2" style="text-align: center">
										<h:outputText value="Tipo Concepto" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Tipo" />
									</rich:column>
									<rich:column width="100px" rowspan="2" style="text-align: center">
										<h:outputText value="Nro. de Solicitud" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Monto Total" />
									</rich:column>
									<rich:column width="60px" rowspan="2" style="text-align: center">
										<h:outputText value="CP / Cta." />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Saldo" />
									</rich:column>
									<rich:column width="270px" colspan="3" style="text-align: center">
										<h:outputText value="Monto por Regularizar" />
									</rich:column>
									<rich:column width="80px" rowspan="2" style="text-align: center">
										<h:outputText value="Monto a Pagar" />
									</rich:column>
									<rich:column width="60px" rowspan="2" style="text-align: center">
										<h:outputText value="Prioridad" />
									</rich:column>
									<rich:column width="90px" breakBefore="true" style="text-align: center">
										<h:outputText value="Amortización" />
									</rich:column>
									<rich:column width="90px" style="text-align: center">
										<h:outputText value="Interés" />
									</rich:column>
									<rich:column width="90px" style="text-align: center">
										<h:outputText value="Total" />
									</rich:column>								
								</rich:columnGroup>
						</f:facet> 

							<rich:column width="150px" style="text-align: center">
								<h:outputText value="#{item.strIngCajaDescTipo}" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="#{item.strIngCajaDescTipoCredito}" />
							</rich:column>
							<rich:column width="100px" style="text-align: center">
								<h:outputText value="#{item.strIngCajaNroSolicitud}" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="#{item.bdIngCajaMontoTotal}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="60px" style="text-align: center">
								<h:outputText value="#{item.strIngCajaCuotas}" />
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<h:outputText value="#{item.bdIngCajaSaldoCredito}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="90px" style="text-align: center">
								<h:outputText value="#{item.bdIngCajaAmortizacion}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="90px" style="text-align: center">
								<h:outputText value="#{item.bdIngCajaInteres}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="90px" style="text-align: right; font-weight: bold;">
								<h:outputText value="#{item.bdIngCajaSumCapitalInteres}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>	
							<rich:column width="80px" style="text-align: right; color:red; font-weight: bold;">
								<h:outputText value="#{item.bdIngCajaMontoPagado}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="60px" style="text-align: center">
								<h:outputText value="#{item.intIngCajaOrdenAmortizacion}" />
							</rich:column>

	
				      	<f:facet name="footer">
							<rich:columnGroup>
								<rich:column width="680" colspan="8" style="text-align: center">
									<b><h:outputText value="TOTAL" /></b>
								</rich:column>
								<rich:column width="100" style="text-align: right; font-weight: bold;">
									<h:outputText value="#{cajaController.bdSumatoriaMontosTotalesAPagar}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText>
								</rich:column>
								<rich:column width="100" style="text-align: right; color:red; font-weight: bold;">
									<h:outputText value="#{cajaController.bdMontoIngresadoTotalSimulacion}">
										<f:converter converterId="ConvertidorMontos"/>
									</h:outputText>
								</rich:column>
							</rich:columnGroup>   
						</f:facet>
						
		            </rich:dataTable>
				</a4j:outputPanel>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
