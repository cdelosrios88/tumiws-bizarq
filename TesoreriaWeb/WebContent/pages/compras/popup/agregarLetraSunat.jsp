<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarLetraSunat" width="870" height="350"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Letra"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarLetraSunat" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarLetraSunat">
	   
	    <rich:spacer height="3px"/>	    

				
		<h:panelGrid columns="3" id="panelCambioLetra">
	    	<rich:column width="110" style="text-align: left;font-weight:bold">
	    		<h:outputText value="Tipo de Cambio : "/>
	    	</rich:column>
	    	<rich:column width="110" style="text-align: left">
	         	<h:outputText value="De "/>
	         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
					itemValue="intIdDetalle"
					itemLabel="strDescripcion"
					property="#{sunatController.tipoCambioLetra.id.intParaMoneda}"/>
				<h:outputText value=" a Soles "/>	
	        </rich:column>
			<rich:column width="140" style="text-align: left">
				<h:inputText size="8"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{sunatController.tipoCambioLetra.bdPromedio}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
	    </h:panelGrid>
	    
		<h:panelGrid columns="5">
			<rich:column width=	"110">
				<h:outputText value="Fecha de Provisón :"/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" 
					readonly="true"
					style="background-color: #BFBFBF;"					 
					value="#{sunatController.documentoSunatLetra.tsFechaProvision}">
					<f:convertDateTime pattern="dd/MM/yyyy" />
	   			</h:inputText>
			</rich:column>
			<rich:column width=	"110">
				<h:outputText value="Tipo Documento :"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					style="width: 210px;"
					value="#{sunatController.documentoSunatLetra.intParaTipoComprobante}">
					<tumih:selectItems var="sel"
						value="#{sunatController.listaTablaTipoComprobanteLetra}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>					
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
	    <h:panelGrid columns="8">
			<rich:column width=	"110">
				<h:outputText value="Fecha Emisión :"/>
			</rich:column>
			<rich:column width="200">
				<rich:calendar datePattern="dd/MM/yyyy"  
					value="#{sunatController.documentoSunatLetra.dtFechaEmision}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="29" 
					showApplyButton="true">
					<a4j:support event="ondateselected" 
						action="#{sunatController.cargarTipoCambioLetra}" 
						reRender="panelCambioLetra"/>
				</rich:calendar>
			</rich:column>
			<rich:column width=	"110">
				<h:outputText value="Fecha Vencimiento :"/>
			</rich:column>
			<rich:column width="170">
				<rich:calendar datePattern="dd/MM/yyyy"  
					value="#{sunatController.documentoSunatLetra.dtFechaVencimiento}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="20" 
					showApplyButton="true"/>
			</rich:column>
		</h:panelGrid>
				
		<h:panelGrid columns="5">		
			<rich:column width=	"110">
				<h:outputText value="Serie :"/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" 
					value="#{sunatController.documentoSunatLetra.strSerieDocumento}"/>
			</rich:column>
			<rich:column width=	"110">
				<h:outputText value="Número :"/>
			</rich:column>
			<rich:column width="210">
				<h:inputText size="23" 
					value="#{sunatController.documentoSunatLetra.strNumeroDocumento}"/>
			</rich:column>
			
		</h:panelGrid>

		<h:panelGrid columns="4">
			<rich:column width=	"110">
				<h:outputText value="Monto :"/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" 
					onkeypress="return soloNumerosDecimalesPositivos(this)"
					value="#{sunatController.documentoSunatLetra.detalleLetra.bdMontoSinTipoCambio}"/>
			</rich:column>
			<rich:column width="260">
				<h:selectOneMenu
					value="#{sunatController.documentoSunatLetra.detalleLetra.intParaTipoMoneda}"
					style="width: 267px;">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
					<a4j:support event="onchange" 
						action="#{sunatController.cargarTipoCambioLetra}" 
						reRender="panelCambioLetra"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="8" id="panelSucursalLetra">
			<rich:column width="110">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="200">
				<h:selectOneMenu
					value="#{sunatController.documentoSunatLetra.detalleLetra.intSucuIdSucursal}"
					style="width: 190px;">
					<tumih:selectItems var="sel"
						value="#{sunatController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" 
						action="#{sunatController.seleccionarSucursalLetra}" 
						reRender="panelSucursalLetra"  />	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="110">
				<h:outputText value="Subsucursal :"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					value="#{sunatController.documentoSunatLetra.detalleLetra.intSudeIdSubsucursal}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{sunatController.documentoSunatLetra.detalleLetra.ordenCompraDetalle.sucursal.listaSubSucursal}"
						itemValue="#{sel.id.intIdSubSucursal}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="50">
				<h:outputText value="Área :"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					value="#{sunatController.documentoSunatLetra.detalleLetra.intIdArea}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{sunatController.documentoSunatLetra.detalleLetra.ordenCompraDetalle.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
    	
    	<rich:spacer height="80px"/>
		
		<h:outputText value="#{sunatController.strMensajeLetra}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{sunatController.mostrarMensajeLetra}"/>
		<rich:spacer height="10px"
			rendered="#{!sunatController.mostrarMensajeLetra}"/>
			
		<h:panelGrid columns="6">
			<rich:column width="250" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Aceptar"
                	oncomplete="if(#{sunatController.mostrarMensajeLetra}){rendPopUp5();}else{Richfaces.hideModalPanel('pAgregarLetraSunat');rendTabla5();}"
                   	action="#{sunatController.agregarDocumentoSunatLetra}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarLetraSunat')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>

    	<a4j:jsFunction name="rendTabla5" reRender="panelLetrasDocumento" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp5" reRender="fAgregarLetraSunat" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	