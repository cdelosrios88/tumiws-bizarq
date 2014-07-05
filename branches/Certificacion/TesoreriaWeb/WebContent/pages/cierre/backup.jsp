<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modificado: Junior Chavez 06.12.2013 -->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->


<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{cierreFondosController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGrid columns="2">
				<rich:column width="120">
					<h:outputText value="Tipo de Anulación : "/>
				</rich:column>
				<rich:column width="200">
                	<h:selectOneMenu value="#{cierreFondosController.intParaTipoAnulaFondo}"
						disabled="#{!cierreFondosController.datosValidados}" style="width: 200px;">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOANULACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>	
					</h:selectOneMenu>
            	</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="12px"/>
			
            <h:panelGrid>
	        	<rich:dataTable
	          		sortMode="single"
                    var="item"
                    value="#{cierreFondosController.listaControlFondosFijosAnular}"  
					rowKeyVar="rowKey"
					rows="1"
					width="820px"
					rows="#{fn:length(cierreFondosController.listaControlFondosFijosAnular)}">
                                
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Sucursal"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strDescSucursal}"/>
                	</rich:column>
                    <rich:column width="120" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Tipo de Fondo"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.id.intParaTipoFondoFijo}"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Apertura"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strNumeroApertura}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strFechaRegistro}"/>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto Apertura"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoApertura}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="120" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto Rendido"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoUtilizado}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoSaldo}">
                        	<f:converter converterId="ConvertidorMontos" />
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
                        <h:outputText value="#{item.strDescEstadoFondo}"/>
                  	</rich:column>
					
            	</rich:dataTable>
            	
         	</h:panelGrid>
			
			<rich:spacer height="12px"/>
			
			
			<h:panelGroup id="panelValidar">

			
			
			<h:panelGroup rendered="#{cierreFondosController.datosValidados}">
			
				<rich:spacer height="10px"/>
				
				<h:panelGrid columns="2">
					<rich:column width="120">
						<h:outputText value="Responsable : "/>
					</rich:column>
					<rich:column>
						<h:inputText size="100" 
							value="#{cierreFondosController.controlFondosFijosAnular.strDescripcionPersona}" 
							readonly="true" 
							style="background-color: #BFBFBF;"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="3px"/>
				
				<h:panelGrid columns="2">
					<rich:column width="120">
						<h:outputText value="Fecha y Hora Cierre : "/>
					</rich:column>
					<rich:column>
						<h:inputText size="100" value="#{cierreFondosController.tsFechaCierreAnular}" 
							readonly="true" style="background-color: #BFBFBF;">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss"/>
						</h:inputText>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="6">
					<rich:column width="120" style="vertical-align: top">
						<h:outputText value="Observación :"/>
					</rich:column>
					<rich:column>
						<h:inputTextarea rows="2" cols="124"
							value="#{cierreFondosController.strObservacion}"/>
					</rich:column>
				</h:panelGrid>
			</h:panelGroup>
		</h:panelGroup>
	</rich:panel>
</h:panelGroup>
</rich:panel>
</h:form>