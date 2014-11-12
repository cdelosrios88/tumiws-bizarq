<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarLetraSunat" width="880" height="450"
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
		<a4j:outputPanel id="opMsgErrorAgregarLetraDocSunat">
			<h:panelGrid columns="1">
				<rich:column width=	"600">
					<h:outputText value="#{sunatController.mensajeOperacionDocSunatLetra}" 
						styleClass="msgInfo"
						style="font-weight:bold"
						rendered="#{sunatController.mostrarMensajeExitoDocSunatLetra}"/>
					<h:outputText value="#{sunatController.mensajeOperacionDocSunatLetra}" 
						styleClass="msgError"
						style="font-weight:bold"
						rendered="#{sunatController.mostrarMensajeErrorDocSunatLetra}"/>	
				</rich:column>
			</h:panelGrid>
		</a4j:outputPanel>
		<rich:spacer height="3px"/>	    

		<h:panelGrid columns="4">
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
			<rich:column width=	"120">
				<h:outputText value="Tipo de Documento :"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					style="width: 180px;"
					value="#{sunatController.documentoSunatLetra.intParaTipoComprobante}"
					disabled="true">
					<tumih:selectItems var="sel"
						value="#{sunatController.listaTablaTipoComprobanteLetrasYNotas}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>					
				</h:selectOneMenu>
				
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="8">
			<rich:column width=	"110">
				<h:outputText value="Documento :"/>
			</rich:column>
			<rich:column width=	"50">
				<h:outputText value="Serie :"/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="25" 
					value="#{sunatController.documentoSunatLetra.strSerieDocumento}"/>
			</rich:column>
			<rich:column width=	"70">
				<h:outputText value="Número :"/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="25" 
					value="#{sunatController.documentoSunatLetra.strNumeroDocumento}"/>
			</rich:column>	
		</h:panelGrid>
			
		<rich:spacer height="3px" rendered="#{sunatController.intValidaCierrePorFechaEmisionLetra==0}"/>
	    <h:panelGrid columns="8">
			<rich:column width=	"110">
				<h:outputText value="Fecha Emisión :"/>
			</rich:column>
			<rich:column width="170">
				<rich:calendar datePattern="dd/MM/yyyy"  
					value="#{sunatController.documentoSunatLetra.dtFechaEmision}"  
					jointPoint="top-right" 
					direction="right" 
					inputSize="20" 
					showApplyButton="true">
					<a4j:support event="onchanged" 
						action="#{sunatController.cargarTipoCambioLetra}" 
						reRender="itTipoCambioLetra"/>
				</rich:calendar>
			</rich:column>
			<rich:column width=	"120">
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
			<!-- Autor: jchavez / Tarea: Creacion / Fecha: 29.10.2014 -->
			<rich:column width="100" style="text-align: left">
				<h:outputText value="Moneda : "/>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
					itemValue="intIdDetalle"
					itemLabel="strDescripcion"
					property="#{sunatController.tipoCambioLetra.id.intParaMoneda}"/>
			</rich:column>
			<rich:column width="40" style="text-align: left;font-weight:bold">
	    		<h:outputText value="T.C. : "/>
	    	</rich:column>
	    	<rich:column width="60" style="text-align: left">
	    		<h:inputText id="itTipoCambioLetra"
	    			size="6"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"
					value="#{sunatController.tipoCambioLetra.bdPromedio}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
	    	</rich:column>
			<!-- Fin jchavez - 29.10.2014 -->
		</h:panelGrid>
		
		<h:panelGrid columns="4">
			<rich:column width=	"110">
				<h:outputText value="Descripcion :"/>
			</rich:column>
			<rich:column width=	"110">
				<h:inputTextarea cols="89" rows="2"
					value="#{sunatController.documentoSunatLetra.strGlosa}"/>			
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="4">
			<rich:column width=	"110">
				<h:outputText value="Monto :"/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" id="txtMontoIngresadoTotalL" readonly="true"
					value="#{sunatController.documentoSunatLetra.detalleLetra.bdMontoSinTipoCambio}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="4">
			<rich:column width=	"110">
				<h:outputText value="Documento :"/>
			</rich:column>
			<rich:column width="497">
				<h:inputText rendered="#{empty sunatController.documentoSunatLetra.archivoDocumento}" 
					size="88"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
				<h:inputText rendered="#{not empty sunatController.documentoSunatLetra.archivoDocumento}"
					value="#{sunatController.documentoSunatLetra.archivoDocumento.strNombrearchivo}"
					size="88"
					readonly="true" 
					style="background-color: #BFBFBF;"/>
			</rich:column>
			<rich:column width=	"170"
				rendered="#{empty sunatController.documentoSunatLetra.id.intItemDocumentoSunat}">
				<a4j:commandButton styleClass="btnEstilos"
					value="Adjuntar Documento"
	                reRender="pAdjuntarDocumentoSunatLetra"
	                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentoSunatLetra')"
	                style="width:150px"/>
	        </rich:column>
		</h:panelGrid>
    	
		<a4j:outputPanel>
		    <h:panelGrid id="msgErrorMontoDetalleLetra">
		    	<rich:column width="600" style="text-align: left;">
		    		<h:outputText value="#{sunatController.strMsgErrorMontoDocSunatL}"
		    			styleClass="msgError"
						style="font-weight:bold; text-align: left"/>
		    	</rich:column>
		    </h:panelGrid>
	    </a4j:outputPanel>
	    
	    <h:panelGrid columns="1">
			<rich:column width=	"840">
				<rich:dataTable
					sortMode="single"
					var="item"
					value="#{sunatController.listaDocSunatValidosParaLetraDeCambio}"
					rowKeyVar="rowKey"
					width="840"
					rows="#{fn:length(sunatController.listaDocSunatValidosParaLetraDeCambio)}">
					<f:facet name="header">
	                	<rich:columnGroup>
	                		<rich:column>
	                			<h:outputText value="Nro"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Tipo"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Nro.Documento"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Moneda"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="SubTotal"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="I.G.V."/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Total"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Detracción"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Total a Cancelar"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Saldo"/>
	                		</rich:column>
	                		<rich:column>
	                			<h:outputText value="Aplicado"/>
	                		</rich:column>
	                	</rich:columnGroup>
		            </f:facet>
					<rich:columnGroup>
						<rich:column width="30" style="text-align: center">
				         	<h:outputText value="#{item.id.intItemDocumentoSunat}"/>
						</rich:column>
						<rich:column width="100" style="text-align: center">
				         	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{item.intParaTipoComprobante}"/>
				   		</rich:column>
						<rich:column width="100" style="text-align: center">
				         	<h:outputText value="#{item.strSerieDocumento} - #{item.strNumeroDocumento}"/>
				   		</rich:column>
						<rich:column width="90" style="text-align: center">
				          	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{item.intParaMoneda}"/>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
				          	<h:outputText value="#{item.detalleSubTotal.bdMontoTotal}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
				          	<h:outputText value="#{item.detalleIGV.bdMontoTotal}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
				          	<h:outputText value="#{item.detalleTotal.bdMontoTotal}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="100" style="text-align: right">
				          	<h:outputText value="#{item.docDetraccion.bdMontoDocumento}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="100" style="text-align: right">
				          	<h:outputText value="#{item.bdMontoTotalSinDetraccion}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
				    	<rich:column width="100" style="text-align: right">
				          	<h:outputText value="#{item.bdMontoSaldoTemp}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
							<h:inputText 
								onblur="extractNumber(this,2,false);" 
								onkeyup="extractNumber(this,2,false);" 			   						
								onkeypress="return soloNumerosDecimales(this)"
								size="12"	
								style="text-align: right;"							
								id="txtMontoAplicado"
								disabled="#{item.bdMontoSaldoTemp==0}"
								value="#{item.bdMontoAplicar}">
								<a4j:support event="onchange"
									actionListener="#{sunatController.getBaseCalculoDocSunatLetra}"
									reRender="txtMontoIngresadoTotalL, msgErrorMontoDetalleLetra, pgBtnAgregarLetra" />
							</h:inputText>						
	                    </rich:column>
	            	</rich:columnGroup>
	            	<rich:columnGroup rendered="#{item.docPercepcion!=null && item.docPercepcion.bdMontoDocumento!=null}">
	            		<rich:column width="30" style="text-align: center">
							<h:outputText value="#{item.docPercepcion.id.intItemDocumentoSunatDoc}"/>
						</rich:column>
						<rich:column width="100" style="text-align: center">
				        	<h:outputText value="Percepción"/>
				   		</rich:column>
						<rich:column width="100" style="text-align: center">
				        	<h:outputText value="#{item.strSerieDocumento} - #{item.strNumeroDocumento}"/>
				   		</rich:column>
						<rich:column width="90" style="text-align: center">
				        	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
								itemValue="intIdDetalle" 
								itemLabel="strDescripcion"
								property="#{item.intParaMoneda}"/>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
				        	<h:outputText value="#{item.docPercepcion.bdMontoDocumento}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
			            	<h:outputText value=""/>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
				          	<h:outputText value="#{item.docPercepcion.bdMontoDocumento}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="100" style="text-align: right">
				        	<h:outputText value=""/>
				    	</rich:column>
						<rich:column width="100" style="text-align: right">
				        	<h:outputText value="#{item.docPercepcion.bdMontoDocumento}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
				    	<rich:column width="100" style="text-align: right">
				        	<h:outputText value="#{item.docPercepcion.bdMontoSaldoTemp}">
				         		<f:converter converterId="ConvertidorMontos"/>
				         	</h:outputText>
				    	</rich:column>
						<rich:column width="80" style="text-align: right">
	                    	<h:inputText 
								onblur="extractNumber(this,2,false);" 
								onkeyup="extractNumber(this,2,false);" 			   						
								onkeypress="return soloNumerosDecimales(this)"
								size="12"	
								style="text-align: right;"							
								id="txtMontoAplicado2"
								disabled="#{item.docPercepcion.bdMontoSaldoTemp==0}"
								value="#{item.docPercepcion.bdMontoAplicar}">
								<a4j:support event="onchange"
									actionListener="#{sunatController.getBaseCalculoDocSunatLetra}"
									reRender="txtMontoIngresadoTotalL, msgErrorMontoDetalleLetra, pgBtnAgregarLetra" />
							</h:inputText>						
	                    </rich:column>
	            	</rich:columnGroup>
			  	</rich:dataTable>
		  	</rich:column>
		</h:panelGrid>
		<!-- Autor: jchavez / Tarea: Se comenta por desuso / Fecha: 04.11.2014
		<h:outputText value="#{sunatController.strMensajeLetra}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{sunatController.mostrarMensajeLetra}"/>
			 -->
	   	<h:panelGrid columns="2" id="pgBtnAgregarLetra">
			<rich:column width="420" style="text-align: right;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Aceptar"
                	oncomplete="if(#{!sunatController.mostrarMensajeExitoDocSunatLetra}){rendPopUp5();}
                				else{Richfaces.hideModalPanel('pAgregarLetraSunat');rendTabla5();}"
                   	action="#{sunatController.agregarDocumentoSunatLetra}" 
                   	disabled="#{not empty sunatController.strMsgErrorMontoDocSunatL}"
                   	style="width:120px"/>
            </rich:column>
			<rich:column width="420" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarLetraSunat')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
		
    	<a4j:jsFunction name="rendTabla5" reRender="opMsgErrorAgregarLetraDocSunat, panelLetrasDocumento" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp5" reRender="fAgregarLetraSunat" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	


<!-- 
<a4j:outputPanel id="opErrorFechaEmisionLetra" rendered="#{sunatController.intValidaCierrePorFechaEmisionLetra==0}">
	<h:panelGrid columns="1">
		<rich:column width=	"300">
			<h:outputText value="#{sunatController.strMsgErrorFechaEmisionLetra}"
				styleClass="msgError"
				style="font-weight:bold"/>
		</rich:column>
	</h:panelGrid>
</a4j:outputPanel>
 -->