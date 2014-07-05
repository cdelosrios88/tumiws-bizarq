<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pAgregarDetalleSunat" width="960" height="470"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Detalle Sunat"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarDetalleSunat" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	
   	<h:form id="fAgregarDetalleSunat">
   	
	<h:panelGrid columns="6">
		<rich:column width="120" style="text-align: left;">
			 <a4j:commandButton styleClass="btnEstilos"
                value="Aceptar"
                oncomplete="if(#{sunatController.mostrarMensajeDetalle}){rendPopUp4();}else{Richfaces.hideModalPanel('pAgregarDetalleSunat');rendTabla4();}"
                action="#{sunatController.agregarDetalleSunat2}" 
                style="width:120px"/>
	     	</rich:column>
		<rich:column width="120" style="text-align: left;">
	    	<a4j:commandButton styleClass="btnEstilos"
	        	value="Cancelar"
	            onclick="Richfaces.hideModalPanel('pAgregarDetalleSunat')"
	            style="width:120px"/>
		</rich:column>
    </h:panelGrid>
    
    <a4j:jsFunction name="rendTabla4" reRender="panelDocumentoD" ajaxSingle="true"/>
	<a4j:jsFunction name="rendPopUp4" reRender="fAgregarDetalleSunat" ajaxSingle="true"/>		
	
    <rich:spacer height="5px"/>
	<h:outputText value="#{sunatController.strMensajeDetalle}" 
		styleClass="msgError"
		style="font-weight:bold"
		rendered="#{not empty sunatController.strMensajeDetalle}"/>
	<rich:spacer height="10px"
		rendered="#{empty sunatController.strMensajeDetalle}"/>
    
    
    
    
    
    
    <h:panelGrid columns="3">
    	<rich:column width="100" style="text-align: left;font-weight:bold">
    		<h:outputText value="Tipo de Cambio : "/>
    	</rich:column>
    	<rich:column width="150" style="text-align: center">
         	<h:outputText value="De "/>
         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
				itemValue="intIdDetalle"
				itemLabel="strDescripcion"
				property="#{sunatController.tipoCambio.id.intParaMoneda}"/>
			<h:outputText value=" a Soles "/>	
        </rich:column>
		<rich:column width="140" style="text-align: left">
			<h:inputText size="15"
				readonly="true"
				style="background-color: #BFBFBF;font-weight:bold;"
				value="#{sunatController.tipoCambio.bdPromedio}">
				<f:converter converterId="ConvertidorMontos"/>
			</h:inputText>
		</rich:column>
    </h:panelGrid>
    
    <rich:spacer height="10px"/>
    
    <h:panelGrid>
    	<rich:column width="120" style="text-align: left;"><h:outputText value="Adelantos : "/></rich:column>
    </h:panelGrid>
	
			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaOrdenCompraDocumento"
	    			var="item"
	                value="#{sunatController.ordenCompra.listaOrdenCompraDocumento}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="930px">
			        
					<rich:column width="60" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Item"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.id.intItemOrdenCompraDocumento}"/>
			      	</rich:column>
					<rich:column width="130" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Documento"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.intParaDocumentoGeneral}"/>
                    </rich:column>
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Fecha"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaDocumento}">
                      		<f:convertDateTime pattern="dd/MM/yyyy" />
                      	</h:outputText>
                    </rich:column>
	                <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Monto"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoDocumento}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoMoneda}"/>   
                  	</rich:column>
	                <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Centro Costo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strRazonSocial}-#{item.area.strDescripcion}"/>
                    </rich:column>
	                <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Pagado"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoPagadoTemp}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoPagadoTemp - item.bdMontoIngresadoTemp}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Aplicado"/>
                      	</f:facet>
                      	<h:inputText size="10"
                      		disabled="#{item.bdMontoPagadoTemp==item.bdMontoIngresadoTemp}"
                      		onkeypress="return soloNumerosDecimalesPositivos(this)" 
                      		value="#{item.bdMontoUsar}"/>
                    </rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
			<rich:spacer height="15px"/>
			
    <h:panelGrid>
    	<rich:column width="120" style="text-align: left;"><h:outputText value="Detalle : "/></rich:column>
    </h:panelGrid>
    
			<h:panelGroup>
	    		<rich:dataTable
	    			id="tablaOrdenCompraDetalle"
	    			var="item"
	                value="#{sunatController.ordenCompra.listaOrdenCompraDetalle}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="930px">
			        
					<rich:column width="100" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Item"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.id.intItemOrdenCompraDetalle}"/>
			      	</rich:column>
			      	<rich:column width="50" style="text-align: right;">
						<f:facet name="header">
	                    	<h:outputText value="Cant"/>
	                 	</f:facet>
			        	<h:outputText value="#{item.bdCantidad}">
			        		<f:converter converterId="ConvertidorMontos"/>
			        	</h:outputText>
			      	</rich:column>
					<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="U.M."/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_UNIDADMEDIDA}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.intParaUnidadMedida}"/>
                    </rich:column>
					<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Descripción"/>
                      	</f:facet>
                      	<h:outputText value="#{item.strDescripcion}" title="#{item.strDescripcion}"/>
                    </rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Centro Costo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.sucursal.juridica.strRazonSocial}-#{item.area.strDescripcion}"/>
                    </rich:column>
	                <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="P.U."/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdPrecioUnitario}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="70" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Moneda"/>                      		
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoMoneda}"/>
                  	</rich:column>
	                <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="P.T."/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdPrecioTotal}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="80" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Saldo"/>
                      	</f:facet>
                      	<h:outputText value="#{item.bdMontoSaldoTemp}">
	                    	<f:converter converterId="ConvertidorMontos"/>
	                 	</h:outputText>
                    </rich:column>
                    <rich:column width="120" style="text-align: right">
                    	<f:facet name="header">
                        	<h:outputText value="Aplicado"/>
                      	</f:facet>
                      	<h:inputText size="12"
                      		disabled="#{item.bdMontoSaldoTemp==0}"
                      		onkeypress="return soloNumerosDecimalesPositivos(this)"
                      		value="#{item.bdMontoUsar}"/>
                    </rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
	    	
	   	
	</h:form>
</rich:modalPanel>	
