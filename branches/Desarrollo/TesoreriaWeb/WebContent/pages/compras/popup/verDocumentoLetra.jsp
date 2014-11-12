<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pVerDocumentoSunat" width="910" height="350"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Documento Sunat"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pVerDocumentoSunat" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fVerDocumentoSunat">
	   
	    <rich:spacer height="3px"/>
		<!-- Autor: jchavez / Tarea: Creacion / Fecha: 23.10.2014 -->
		<h:panelGrid columns="2">			
			<rich:column width=	"120">
				<h:outputText value="Nro. Documento : "/>
			</rich:column>
			<rich:column width="210">
				<h:inputText size="23"
					style="background-color: #BFBFBF;"
					readonly="true"
					value="#{sunatController.itemDocumentoSunat.id.intItemDocumentoSunat}"/>
			</rich:column>
		</h:panelGrid>
		<!-- Fin jchavez - 23.10.2014 -->
		
		<h:panelGrid columns="6">			
			<rich:column width=	"120">
				<h:outputText value="Documento : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="true"
					style="width: 210px;"
					value="#{sunatController.itemDocumentoSunat.intParaTipoComprobante}">
					<tumih:selectItems var="sel"
						value="#{sunatController.listaTipoComprobante}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width=	"120">
				<h:outputText value="Serie : "/>
			</rich:column>
			<rich:column width="170">
				<h:inputText size="23"
					style="background-color: #BFBFBF;"
					readonly="true"
					value="#{sunatController.itemDocumentoSunat.strSerieDocumento}"/>
			</rich:column>
			<rich:column width=	"120">
				<h:outputText value="Número : "/>
			</rich:column>
			<rich:column width="210">
				<h:inputText size="23"
					style="background-color:#BFBFBF;"
					readonly="true"
					value="#{sunatController.itemDocumentoSunat.strNumeroDocumento}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width=	"120">
				<h:outputText value="Fecha Emisión : "/>
			</rich:column>
			<rich:column width="150">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="true"
					value="#{sunatController.itemDocumentoSunat.dtFechaEmision}"  
					inputSize="20">
				</rich:calendar>
			</rich:column>
			<rich:column width=	"120">
				<h:outputText value="Fecha Vencimiento : "/>
			</rich:column>
			<rich:column width="170">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="true"
					value="#{sunatController.itemDocumentoSunat.dtFechaVencimiento}"  
					inputSize="20"/>
			</rich:column>
			<rich:column width=	"120">
				<h:outputText value="Programación Pago : "/>
			</rich:column>
			<rich:column width="210">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="true"
					value="#{sunatController.itemDocumentoSunat.dtFechaPago}"  
					inputSize="20"/>
			</rich:column>			
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelModificadores">
			<rich:column width=	"120">
				<h:outputText value="Fecha de Provisión : "/>
			</rich:column>
			<rich:column width="210">
				<h:inputText size="36"
					readonly="true"
					style="background-color: #BFBFBF;"
					value="#{sunatController.itemDocumentoSunat.tsFechaProvision}">
					<f:convertDateTime pattern="dd/MM/yyyy" />
	   			</h:inputText>
			</rich:column>
			<rich:column width=	"120">
				<h:selectBooleanCheckbox value="#{sunatController.itemDocumentoSunat.seleccionaInafecto}"
					disabled="#{not empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat}">
				</h:selectBooleanCheckbox>
				<h:outputText value="Inafecto"/>
			</rich:column>
			<rich:column width=	"120">
				<h:selectBooleanCheckbox value="#{sunatController.itemDocumentoSunat.seleccionaIGV}"
					disabled="#{not empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat}">
				</h:selectBooleanCheckbox>
				<h:outputText value="IGV"/>
			</rich:column>
			<rich:column width=	"120">
				<h:selectBooleanCheckbox value="#{sunatController.itemDocumentoSunat.seleccionaPercepcion}"
					disabled="#{not empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat
					|| (sunatController.itemDocumentoSunat.intParaTipoComprobante!=applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)}">
				</h:selectBooleanCheckbox>
				<h:outputText value="Percepción"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="16">
			<rich:column width=	"120" style="vertical-align: top">
				<h:outputText value="Glosa : "/>
			</rich:column>
			<rich:column width="470">
				<h:inputTextarea cols="89" rows="2"
					style="background-color: #BFBFBF;" readonly="true"
					value="#{sunatController.itemDocumentoSunat.strGlosa}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="3">
			<rich:column width="120">
				<h:outputText value="Documento :"/>
			</rich:column>
			<rich:column width="130" 
				rendered="#{sunatController.itemDocumentoSunat.id.intItemDocumentoSunat!=null
							&& not empty sunatController.itemDocumentoSunat.archivoDocumento}">
				<h:commandLink  value=" Descargar"
					actionListener="#{fileUploadController.descargarArchivo}">
					<f:attribute name="archivo" value="#{sunatController.itemDocumentoSunat.archivoDocumento}"/>		
				</h:commandLink>
			</rich:column>
			<rich:column width="200" 
				rendered="#{sunatController.itemDocumentoSunat.id.intItemDocumentoSunat!=null
							&& empty sunatController.itemDocumentoSunat.archivoDocumento}">
				<h:outputText value="No se adjuntó ningún documento"/>
			</rich:column>
		</h:panelGrid>
    	
    	<h:panelGroup>
			<h:panelGrid columns="10">
				<rich:column width="100" style="text-align: right" rendered="#{sunatController.tipoCambio.bdPromedio!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="T.C :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.tipoCambio.bdPromedio!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						value="#{sunatController.tipoCambio.bdPromedio}"
						style="background-color: #BFBFBF;font-weight:bold;">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="80" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleSubTotal.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Subtotal :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleSubTotal.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"
						value="#{sunatController.itemDocumentoSunat.detalleSubTotal.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="90" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleDescuento.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Descuento :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleDescuento.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						rendered="#{!sunatController.poseePermisoDescuento || (not empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat)}"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"/>
					<h:inputText size="19"
						rendered="#{sunatController.poseePermisoDescuento && (empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat)}"
						value="#{sunatController.itemDocumentoSunat.detalleDescuento.bdMontoTotal}"
						onkeypress="return soloNumerosDecimalesPositivos(this)">
					</h:inputText>
			    </rich:column>
			    <rich:column width="90" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleValorVenta.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Valor Venta :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleValorVenta.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"
						value="#{sunatController.itemDocumentoSunat.detalleValorVenta.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="10">
				<rich:column width="100" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleIGV.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="IGV :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleIGV.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleIGV.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="80" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleOtros.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Otros :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleOtros.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="19"
						rendered="#{empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat}"
						value="#{sunatController.itemDocumentoSunat.detalleOtros.bdMontoTotal}"
						onkeypress="return soloNumerosDecimales(this)">
					</h:inputText>
					<h:inputText size="15"
						rendered="#{not empty sunatController.itemDocumentoSunat.id.intItemDocumentoSunat}"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleOtros.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="90" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleTotal.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Total :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleTotal.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleTotal.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="90" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detallePercepcion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText value="Percepción 2% :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detallePercepcion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detallePercepcion.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="10">
				<rich:column width="100" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleDetraccion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Detracción #{sunatController.ordenCompra.detraccion.bdPorcentaje}% :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleDetraccion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleDetraccion.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="80" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleRetencion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Retención :"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleRetencion.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleRetencion.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			    <rich:column width="90" style="text-align: right" rendered="#{sunatController.itemDocumentoSunat.detalleTotalGeneral.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:outputText style="padding-right:10px;" value="Total General:"/>
				</rich:column>
				<rich:column width="140" style="text-align: left" rendered="#{sunatController.itemDocumentoSunat.detalleTotalGeneral.bdMontoTotal!=null && sunatController.registrarNuevo}">
					<h:inputText size="15"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"					
						value="#{sunatController.itemDocumentoSunat.detalleTotalGeneral.bdMontoTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
			    </rich:column>
			</h:panelGrid>
		</h:panelGroup>
		
		<h:panelGrid columns="2" style="text-align:center">
			<rich:column width="350">
			</rich:column>
			<rich:column width="120">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pVerDocumentoSunat')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	<%--
    	<a4j:jsFunction name="rendTabla2" reRender="panelDetalleO,panelDocumentoO" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp2" reRender="fVerDocumentoSunat" ajaxSingle="true"/> --%>					
	</h:form>
</rich:modalPanel>	