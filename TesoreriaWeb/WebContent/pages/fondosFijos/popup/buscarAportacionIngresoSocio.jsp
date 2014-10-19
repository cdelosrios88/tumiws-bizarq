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
                    <rich:column width="500" style="text-align: left">		
						<a4j:commandButton styleClass="btnEstilos" id="btnAceptarIngresoSocioC"
	                		value="Aceptar"
	                		reRender="opTablaIngresoSocioAportacionC,msgErrorMontoIngresadoAportacionC,pgMontoIngresadoC,pgModalidadC,panelDetalleIngresoSocioC"
	                    	action="#{cajaController.agregarIngresoSocio}"
	                    	oncomplete="if(#{cajaController.strMsgErrorMontoIngresado == ''}){Richfaces.hideModalPanel('pBuscarAportacionIngresoSocio')}"
	                    	style="width:80x"/>
                    </rich:column>
				</rich:columnGroup>		
			</h:panelGrid>
			<h:outputText id="msgErrorMontoIngresadoAportacionC" value="#{cajaController.strMsgErrorMontoIngresado}" 
				styleClass="msgError"
				style="font-weight:bold; text-align: left"
				rendered="#{!cajaController.strMsgErrorMontoIngresado}"/>		
			<h:panelGroup>
				<a4j:outputPanel id="opTablaIngresoSocioAportacionC">
					<rich:dataTable
		    			id="tablaIngresoSocioAportacionC"
		    			var="item" 
		                value="#{cajaController.listaMovimientoSocio}" 
				  		rowKeyVar="rowKey" 
				  		width="520px">
				        <f:facet name="header">
								<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="150px" rowspan="2" style="text-align: center">
										<h:outputText value="Tipo Concepto" />
									</rich:column>
									<rich:column width="90px" rowspan="2" style="text-align: center">
										<h:outputText value="Saldo" />
									</rich:column>
									<rich:column width="90px" rowspan="2" style="text-align: center">
										<h:outputText value="Interés" />
									</rich:column>
									<rich:column width="90px" rowspan="2" style="text-align: center">
										<h:outputText value="Saldo Total" />
									</rich:column>
									<rich:column width="90px" rowspan="2" style="text-align: center">
										<h:outputText value="Total" />
									</rich:column>		
								</rich:columnGroup>
						</f:facet> 

							<rich:column width="150px" style="text-align: center">
								<h:outputText value="#{item.strIngCajaDescTipo}" />
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
							<rich:column width="90px" style="text-align: center">
								<h:outputText value="#{item.bdIngCajaSumCapitalInteres}" >
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="90px" style="text-align: right; font-weight: bold;">
								<h:inputText 
									onblur="extractNumber(this,2,false);" 
			   						onkeyup="extractNumber(this,2,false);" 			   						
			   						onkeypress="return soloNumerosDecimales(this)"
			   						size="15"	
			   						style="text-align: right;"							
									id="txtMontoAportado"
			                    	value="#{item.bdIngCajaMontoPagado}">
			                    	<a4j:support event="onchange"
										actionListener="#{cajaController.getBaseCalculo}"
										reRender="txtMontoIngresadoTotalC" />
			                    </h:inputText>
							</rich:column>	
							
				      	<f:facet name="footer">
							<rich:columnGroup>
								<rich:column width="420px" colspan="4" style="text-align: center">
									<b><h:outputText value="TOTAL APORTACIONES" /></b>
								</rich:column>
								<rich:column width="90PX" style="text-align: right; color:red; font-weight: bold;">
									<h:outputText id="txtMontoIngresadoTotalC" value="#{cajaController.bdMontoIngresadoTotal}">
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
