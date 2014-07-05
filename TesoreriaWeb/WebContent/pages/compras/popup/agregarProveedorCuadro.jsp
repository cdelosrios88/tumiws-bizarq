<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarProveedorCuadro" width="930" height="400"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Proveedor"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarProveedorCuadro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarProveedorCuadro">
	   
	    <rich:spacer height="3px"/>
	    
	    <h:panelGrid columns="8" id="panelPersonaCC">
			<rich:column width=	"100">
				<h:outputText value="Proveedor : "/>
			</rich:column>
			<rich:column width="579">
				<h:inputText readonly="true"
					rendered="#{not empty cuadroController.cuadroComparativoProveedorNuevo.persona}"
					value="RUC : #{cuadroController.cuadroComparativoProveedorNuevo.persona.strRuc} - #{cuadroController.cuadroComparativoProveedorNuevo.persona.juridica.strRazonSocial}"
					style="background-color: #BFBFBF;font-weight:bold;"
					size="87"/>
				<h:inputText size="87" 
					rendered="#{empty cuadroController.cuadroComparativoProveedorNuevo.persona}"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona"
	                reRender="pBuscarPersonaCuadro"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaCuadro')"
	                action="#{cuadroController.abrirPopUpBuscarPersonaJuridica}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
			
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Plazo de Entrega : "/>
			</rich:column>
			<rich:column width="330">
				<rich:calendar datePattern="dd/MM/yyyy"  
						converter="calendarTimestampConverter"
						value="#{cuadroController.cuadroComparativoProveedorNuevo.tsPlazoEntrega}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="46" 
						showApplyButton="true"/>
			</rich:column>
			<rich:column width="110">
				<h:outputText value="Lugar de Entrega : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="50" 
					value="#{cuadroController.cuadroComparativoProveedorNuevo.strLugarEntrega}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Garantía : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="50" 
					value="#{cuadroController.cuadroComparativoProveedorNuevo.strGarantia}"/>
			</rich:column>
			<rich:column width="110">
				<h:outputText value="Condición de Pago : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="50" 
					value="#{cuadroController.cuadroComparativoProveedorNuevo.strCondicionPago}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Descuento : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputText size="50" 
					value="#{cuadroController.cuadroComparativoProveedorNuevo.strDescuento}"/>
			</rich:column>
		</h:panelGrid>		
		
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Producto/Servicio : "/>
			</rich:column>
			<rich:column width=	"330">
				<a4j:commandButton styleClass="btnEstilos"
					value="Agregar"
	                disabled="#{cuadroController.deshabilitarNuevo}"
	                reRender="pAgregarProveedorDetalleCuadro"
	                oncomplete="Richfaces.showModalPanel('pAgregarProveedorDetalleCuadro')"
	                action="#{cuadroController.abrirPopUpAgregarProveedorDetalle}" 
	                style="width:287px"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2" id="panelDetalleCC">
			<rich:column width=	"910">
			<rich:dataTable
				sortMode="single"
				var="item"
				value="#{cuadroController.cuadroComparativoProveedorNuevo.listaCuadroComparativoProveedorDetalle}"
				rowKeyVar="rowKey"
				width="900px"
				rows="#{fn:length(cuadroController.cuadroComparativoProveedorNuevo.listaCuadroComparativoProveedorDetalle)}">
				
				<rich:column width="40" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Nro"/>
		         	</f:facet>
					<h:outputText value="#{rowKey + 1}"/>				
				</rich:column>
				<rich:column width="300" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Descripción"/>
		         	</f:facet>
		            <h:outputText value="#{item.strDetalle}"/>
		   		</rich:column>
		        <rich:column width="160" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Marca"/>
		          	</f:facet>
		          	<h:outputText value="#{item.strMarca}"/>
		    	</rich:column>
		 		<rich:column width="100px" style="text-align: center">
		        	<f:facet name="header">
		            	<h:outputText value="Cantidad"/>
		        	</f:facet>
		            <h:outputText value="#{item.bdCantidad}"/>
		     	</rich:column>
		     	<rich:column width="100" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Moneda"/>
		         	</f:facet>
		           <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion" 
						property="#{item.intParaTipoMoneda}"/>
		   		</rich:column>
				<rich:column width="130" style="text-align: center">
		        	<f:facet name="header">
		           		<h:outputText value="Precio Unitario"/>
		         	</f:facet>
		            <h:outputText value="#{item.bdPrecioUnitario}">
                      		<f:converter converterId="ConvertidorMontos"/>
                    </h:outputText>
		   		</rich:column>
		   		<rich:column width="70" style="text-align: center">
					<f:facet name="header">
		           		<h:outputText value="Eliminar"/>
		         	</f:facet>
					<a4j:commandLink value="Eliminar"
						reRender="panelDetalleCC"
						actionListener="#{cuadroController.quitarProveedorDetalle}">
						<f:attribute name="item" value="#{item}"/>
					</a4j:commandLink>				
				</rich:column>
		  	</rich:dataTable>
		  	</rich:column>		        
		</h:panelGrid>
    	
    	<h:panelGrid columns="8" id="panelDocumentoCC">
			<rich:column width=	"100">
				<h:outputText value="Propuesta : "/>
			</rich:column>
			<rich:column width="579">
				<h:inputText rendered="#{empty cuadroController.cuadroComparativoProveedorNuevo.archivoDocumento}" 
					size="87"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText rendered="#{not empty cuadroController.cuadroComparativoProveedorNuevo.archivoDocumento}"
					value="#{cuadroController.cuadroComparativoProveedorNuevo.archivoDocumento.strNombrearchivo}"
					size="87"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Adjuntar Documento"
	                disabled="#{cuadroController.deshabilitarNuevo}"
	                reRender="pAdjuntarDocumentoCuadro"
	                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentoCuadro')"
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
    	<h:panelGrid columns="8" id="panelObservacionCC">
			<rich:column width=	"100" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="579">
				<h:inputTextarea cols="106" rows="2" 
					value="#{cuadroController.cuadroComparativoProveedorNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>		
		
		<rich:spacer height="5px"/>
		<h:outputText value="#{cuadroController.strMensajeProveedor}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{not empty cuadroController.strMensajeProveedor}"/>
		<rich:spacer height="10px"
			rendered="#{empty cuadroController.strMensajeProveedor}"/>
		
		<h:panelGrid columns="6">
			<rich:column width="250" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Aceptar"
                	oncomplete="if(#{cuadroController.mostrarMensajeProveedor}){rendPopUp1();}else{Richfaces.hideModalPanel('pAgregarProveedorCuadro');rendTabla1();}"
                   	action="#{cuadroController.agregarProveedor}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarProveedorCuadro')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	

    	<a4j:jsFunction name="rendTabla1" reRender="panelProveedoresCC" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp1" reRender="fAgregarProveedorCuadro" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	