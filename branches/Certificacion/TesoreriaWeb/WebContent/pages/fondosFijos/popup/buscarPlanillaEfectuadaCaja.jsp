<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form>
	   	<h:panelGroup layout="block">
			<!-- 							<a4j:support event="onclick" ajaxSingle="true" reRender="divFormCambioGarante,divListCambioGarante,btnGrabar,divFormTransferencia"
										oncomplete="Richfaces.hideModalPanel('mpBusqGarante')"></a4j:support>
										 -->
			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaDocPlanillaEfectuadaC"
	    			var="item" 
	                value="#{cajaController.listaDocumentoPorAgregar}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="960px">
			          <rich:column width="10px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value=""/>
	                 	</f:facet>
						<h:selectOneRadio value ="#{item.rbPlanillaEfectSelec}" id="rbOpcDoc"
							style="height:10px" onclick="selecRegistroDocumento(this.value)">
							<f:selectItem itemValue="#{rowKey}"/>
							
						</h:selectOneRadio>
			      	</rich:column>                
					<rich:column width="200px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Entidad"/>
	                 	</f:facet>
						<h:outputText value="#{item.strDescripcion}"/>
			      	</rich:column>
					<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Tipo Socio"/>
	                 	</f:facet>
			        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.efectuadoResumen.intTiposocioCod}"/>
			      	</rich:column>
					<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Modalidad"/>
	                 	</f:facet>
			        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.efectuadoResumen.intModalidadCod}"/>
			      	</rich:column>
			      	<rich:column width="70px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Periodo"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.intPeriodoPlanilla}"/>
			      	</rich:column>
			      	<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Monto"/>
	                 	</f:facet>
	                 	<h:outputText value="#{item.bdMonto}">
			        		<f:converter converterId="ConvertidorMontos" />
			        	</h:outputText>
			      	</rich:column>
			      	<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Monto Pagado"/>
	                 	</f:facet>
	                 	<h:outputText value="#{item.bdMontoPagado}">
			        		<f:converter converterId="ConvertidorMontos" />
			        	</h:outputText>
			      	</rich:column>
			      	<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Monto a Pagar"/>
	                 	</f:facet>
						<h:inputText size="10"
							id="txtMontoAPagar"
	                    	disabled="#{item.isDisabledDocPlanillaEfect==null || item.isDisabledDocPlanillaEfect}"
	                    	value="#{item.bdMontoAIngresar}" 
	                    	onkeypress="return soloNumerosDecimales(this)">
	                    </h:inputText>
			      	</rich:column>
			      	<rich:column width="80px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{cajaController.seleccionarDocumento}"
							oncomplete="Richfaces.hideModalPanel('pBuscarDocPlanillaEfectuadaCaja')"
							reRender="panelDocumentoC">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
			      	<f:facet name="footer">
						<rich:datascroller for="tablaDocPlanillaEfectuadaC" maxPages="5"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>
