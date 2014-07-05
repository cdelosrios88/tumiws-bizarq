<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmBuscarEgreso">
	   	
			<h:panelGrid columns="12" id="panelPopUpBuscarPersona">
	    		<rich:column width="50">
					<h:outputText value="Monto :"/>
				</rich:column>
				<rich:column width="100">
					<h:inputText size="18" 
						value="#{transferenciaController.egresoTelecreditoFiltro.bdMontoTotal}"
						onkeypress="return soloNumerosDecimales(this)" />
				</rich:column>
				<rich:column width="50">
           			<h:outputText  value="Fecha :"/>
           		</rich:column>
           		<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{transferenciaController.egresoTelecreditoFiltro.dtFechaEgreso}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="90">
					<h:outputText value="Nro Egreso :"/>
				</rich:column>
				<rich:column width="100">
					<h:inputText size="18"  
						value="#{transferenciaController.egresoTelecreditoFiltro.strNumeroEgreso}"/>
				</rich:column>
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaEgresoPop"
                    	action="#{transferenciaController.buscarEgreso}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	
	    	
			<h:panelGroup id="panelTablaEgresoPop">
	    		<rich:dataTable
	    			sortMode="single" 
                   	id="tablaEgreso"
	    			var="item" 
	                value="#{transferenciaController.listaEgresoPorAgregar}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="700px">
			                           
					<rich:column width="130px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Egreso"/>
	                 	</f:facet>	                 	
			        	<h:outputText value="#{item.strNumeroEgreso}"/>
			      	</rich:column>
					<rich:column width="90px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Fecha"/>
	                 	</f:facet>
	                 	<h:outputText value="#{item.dtFechaEgreso}">
	                 		<f:convertDateTime pattern="dd/MM/yyyy"/>
	                 	</h:outputText>
			      	</rich:column>
					<rich:column width="70px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Persona"/>
	                 	</f:facet>
	                 	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.personaApoderado.intTipoPersonaCod}"/>
			      	</rich:column>
			      	<rich:column width="240px" style="text-align: left;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
	                 	<h:outputText
			        		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}" 
			        		value="#{item.personaApoderado.natural.strNombreCompleto}"/>
			        	<h:outputText
			        		rendered="#{item.personaApoderado.intTipoPersonaCod==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
			        		value="#{item.personaApoderado.juridica.strRazonSocial}"/>
			      	</rich:column>
			      	<rich:column width="100px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Monto"/>
	                 	</f:facet>
	                 	<h:outputText value="#{item.bdMontoTotal}">
	                 		<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
			      	</rich:column>
			      	<rich:column width="70px" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
	                 	<h:selectBooleanCheckbox value="#{item.checked}"/>
			      	</rich:column>
			      	<f:facet name="footer">
						<rich:datascroller for="tablaEgreso" maxPages="10"/>   
					</f:facet>
					
	            </rich:dataTable>
	    	</h:panelGroup>
			<a4j:commandButton
			    value="Aceptar"
			    styleClass="btnEstilos"
				action="#{transferenciaController.aceptarSeleccionEgreso}"
				oncomplete="Richfaces.hideModalPanel('pBuscarEgreso')"
				reRender="panelDocumentosAgregadosTT,panelMontoTT">
			</a4j:commandButton>
	</h:form>