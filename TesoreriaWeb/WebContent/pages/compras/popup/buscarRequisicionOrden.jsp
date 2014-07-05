<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pBuscarRequisicionOrden" width="700" height="270"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Requisicion"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarRequisicionOrden" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<h:form id="fBuscarRequisicionOrden">
	   	<h:panelGroup>
	   	
	   		<h:panelGrid columns="5">
           		<rich:column width="120">
					<h:outputText value="Tipo Documento : "/>
				</rich:column>
				<rich:column width="250">
					<h:selectOneMenu id="idTipoAprobacion"
						value="#{ordenController.intTipoAprobacion}"
						style="width: 250px;">
						<tumih:selectItems var="sel"
							value="#{ordenController.listaTablaAprobacion}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
						<%--Modificado por cdelosrios, 29/09/2013, se agrego botón buscar y ya no es necesario
						<a4j:support event="onchange" 
							action="#{ordenController.cargarListaRequisicion}" 
							reRender="fBuscarRequisicionOrden"/>--%>
					</h:selectOneMenu>
				</rich:column>
				<!-- Agregado por cdelosrios, 29/09/2013 -->
				<rich:column width="120">
					<h:outputText value="Nro. Requisición : "/>
				</rich:column>
				<rich:column>
					<h:inputText id="idItemRequisicion" 
						value="#{ordenController.intItemRequisicion}"
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
				<rich:column>
					<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="pgDocumentoRequisicion"
                		process="idTipoAprobacion,idItemRequisicion"
                    	action="#{ordenController.cargarListaRequisicion}" 
                    	style="width:90px"/>
				</rich:column>
				<!-- Fin agregado por cdelosrios, 29/09/2013 -->
			</h:panelGrid>
	   		
	   		<rich:spacer height="5px"/>
	   		
	   		<h:panelGrid id="pgDocumentoRequisicion">
	   			<rich:column width="680">
	    		<rich:dataTable
	    			var="item"
	                value="#{ordenController.listaDocumentoRequisicion}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="670px">
			        
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Requisición"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.requisicion.id.intItemRequisicion}"/>
			      	</rich:column>
					<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item Documento"/>
                      	</f:facet>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES}" 
                      		value="#{item.cuadroComparativo.id.intItemCuadroComparativo}"/>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CONTRATO}"
                      		value="#{item.contrato.id.intItemContrato}"/>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_INFORME}"
                      		value="#{item.informeGerencia.id.intItemInformeGerencia}"/>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CAJACHICA
                      		|| item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_ORDENCOMPRA}"
                      		value="#{item.requisicion.id.intItemRequisicion}"/>
                    </rich:column>
                    <rich:column width="180" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Aprobación"/>                		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_APROBACION}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoAprobacion}"/>
                  	</rich:column>
               		<rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>                      		
                      	</f:facet>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES}" 
                      		value="#{item.cuadroComparativo.proveedorAprobado.bdPrecioTotal}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CONTRATO}"
                      		value="#{item.contrato.bdMontoContrato}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_INFORME}"
                      		value="#{item.informeGerencia.bdMontoAutorizado}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                      	<h:outputText rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CAJACHICA
                      		|| item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_ORDENCOMPRA}"
                      		value="#{item.requisicion.bdMontoLogistica}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                		
                      	</f:facet>
                      	<h:panelGroup rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"                      		 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.cuadroComparativo.proveedorAprobado.intParaTipoMoneda}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CONTRATO}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"                      		 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.contrato.intParaTipoMoneda}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_INFORME}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"                      		 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.informeGerencia.intParaTipoMoneda}"/>
                      	</h:panelGroup>
                      	<h:panelGroup rendered="#{item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_CAJACHICA
                      		|| item.intParaTipoAprobacion == applicationScope.Constante.PARAM_T_APROBACION_ORDENCOMPRA}">
                      		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"                      		 
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion" 
								property="#{item.requisicion.intParaTipoMoneda}"/>
                      	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.requisicion.tsFechaRequisicion}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
					<rich:column width="80" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink 
			    			value="Seleccionar"
							actionListener="#{ordenController.seleccionarRequisicion}"
							oncomplete="Richfaces.hideModalPanel('pBuscarRequisicionOrden')"
							reRender="panelRequisicionO,panelCuentasOC,panelDetalleO,panelDocumentoO">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	            </rich:column>
	    	</h:panelGrid>
			
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>	
