<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pVerDocumentoAdelanto" width="400" height="260"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Documento Adelanto"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pVerDocumentoAdelanto" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fVerDocumentoAdelanto">
	   
	    <rich:spacer height="3px"/>

		<h:panelGrid columns="2">			
			<rich:column width=	"120">
				<h:outputText value="Documento : "/>
			</rich:column>
			<rich:column width="210">
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL}" 
					itemValue="intIdDetalle"
					itemLabel="strDescripcion"
					property="#{sunatController.itemOrdenCompraDocumento.intParaDocumentoGeneral}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<rich:column width=	"120">
				<h:outputText value="Fecha : "/>
			</rich:column>
			<rich:column width="170">
				<h:outputText value="#{sunatController.itemOrdenCompraDocumento.tsFechaDocumento}">
               		<f:convertDateTime pattern="dd/MM/yyyy"/>
               	</h:outputText>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<rich:column width=	"120">
				<h:outputText value="Monto : "/>
			</rich:column>
			<rich:column width="150">
				<h:outputText value="#{sunatController.itemOrdenCompraDocumento.bdMontoDocumento}">
                  	<f:converter converterId="ConvertidorMontos"/>
               	</h:outputText>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<rich:column width=	"120">
				<h:outputText value="Moneda : "/>
			</rich:column>
			<rich:column width="170">
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion" 
					property="#{sunatController.itemOrdenCompraDocumento.intParaTipoMoneda}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<rich:column width=	"120">
				<h:outputText value="Centro Costo : "/>
			</rich:column>
			<rich:column width="150">
				<h:outputText value="#{sunatController.itemOrdenCompraDocumento.sucursal.juridica.strRazonSocial}-#{sunatController.itemOrdenCompraDocumento.area.strDescripcion}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="4">
			<rich:column width=	"120">
				<h:outputText value="Pagado : "/>
			</rich:column>
			<rich:column width="150">
				<h:outputText value="#{sunatController.itemOrdenCompraDocumento.bdMontoPagado}">
                  	<f:converter converterId="ConvertidorMontos"/>
               	</h:outputText>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<rich:column width=	"120">
				<h:outputText value="Saldo : "/>
			</rich:column>
			<rich:column width="150">
				<h:outputText value="#{sunatController.itemOrdenCompraDocumento.bdMontoPagado - sunatController.itemOrdenCompraDocumento.bdMontoIngresado}">
                  	<f:converter converterId="ConvertidorMontos"/>
               	</h:outputText>
			</rich:column>
		</h:panelGrid>
	</h:form>
</rich:modalPanel>	