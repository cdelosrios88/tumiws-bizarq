<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarNotaDocSunat" width="910" height="450"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Notas de Crédito y Débito"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarNotaDocSunat" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarNotaSunat">
		<h:panelGroup>	
			<h:panelGrid columns="1" id="opMsgErrorAgregarNotaDocSunat">
				<rich:column width=	"600">
					<h:outputText value="#{sunatController.mensajeOperacionDocSunatNota}" 
						styleClass="msgInfo"
						style="font-weight:bold"
						rendered="#{sunatController.mostrarMensajeExitoDocSunatNota}"/>
					<h:outputText value="#{sunatController.mensajeOperacionDocSunatNota}" 
						styleClass="msgError"
						style="font-weight:bold"
						rendered="#{sunatController.mostrarMensajeErrorDocSunatNota}"/>	
				</rich:column>
			</h:panelGrid>
			
			<rich:spacer height="3px"/>	    
		    
			<h:panelGrid columns="4">
				<rich:column width=	"110">
					<h:outputText value="Fecha de Provisón :"/>
				</rich:column>
				<rich:column width="200">
					<h:inputText size="32" 
						readonly="true"
						style="background-color: #BFBFBF;"					 
						value="#{sunatController.documentoSunatNota.tsFechaProvision}">
						<f:convertDateTime pattern="dd/MM/yyyy" />
		   			</h:inputText>
				</rich:column>
				<rich:column width=	"120">
					<h:outputText value="Tipo de Documento :"/>
				</rich:column>
				<rich:column width="150">
					<h:selectOneMenu
						style="width: 180px;"
						value="#{sunatController.documentoSunatNota.intParaTipoComprobante}"
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
						value="#{sunatController.documentoSunatNota.strSerieDocumento}"/>
				</rich:column>
				<rich:column width=	"70">
					<h:outputText value="Número :"/>
				</rich:column>
				<rich:column width="150">
					<h:inputText size="25" 
						value="#{sunatController.documentoSunatNota.strNumeroDocumento}"/>
				</rich:column>	
			</h:panelGrid>
			
		    <h:panelGrid columns="8">
				<rich:column width=	"110">
					<h:outputText value="Fecha Emisión :"/>
				</rich:column>
				<rich:column width="170">
					<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{sunatController.documentoSunatNota.dtFechaEmision}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="20" 
						showApplyButton="true">
						<a4j:support event="onchanged" 
							action="#{sunatController.cargarTipoCambioNota}" 
							reRender="itTipoCambioNota"/>
					</rich:calendar>
				</rich:column>
				<rich:column width=	"120">
					<h:outputText value="Fecha Vencimiento :"/>
				</rich:column>
				<rich:column width="170">
					<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{sunatController.documentoSunatNota.dtFechaVencimiento}"  
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
						property="#{sunatController.tipoCambioNota.id.intParaMoneda}"/>
				</rich:column>
				<rich:column width="40" style="text-align: left;font-weight:bold">
		    		<h:outputText value="T.C. : "/>
		    	</rich:column>
		    	<rich:column width="60" style="text-align: left">
		    		<h:inputText id="itTipoCambioNota"
		    			size="6"
						readonly="true"
						style="background-color: #BFBFBF;font-weight:bold;"
						value="#{sunatController.tipoCambioNota.bdPromedio}">
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
						value="#{sunatController.documentoSunatNota.strGlosa}"/>			
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="4">
				<rich:column width=	"110">
					<h:outputText value="Monto :"/>
				</rich:column>
				<rich:column width="200">
					<h:inputText id="txtMontoIngresadoTotalNotaCredito"
						size="32" readonly="true" rendered="#{sunatController.documentoSunatNota.intParaTipoComprobante==applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO }"
						value="#{sunatController.documentoSunatNota.detalleNotaCredito.bdMontoSinTipoCambio}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
					<h:inputText id="txtMontoIngresadoTotalNotaDebito"
						size="32" readonly="true" rendered="#{sunatController.documentoSunatNota.intParaTipoComprobante==applicationScope.Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO }"
						value="#{sunatController.documentoSunatNota.detalleNotaDebito.bdMontoSinTipoCambio}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:inputText>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="4">
				<rich:column width=	"110">
					<h:outputText value="Documento :"/>
				</rich:column>
				<rich:column width="497">
					<h:inputText rendered="#{empty sunatController.documentoSunatNota.archivoDocumento}" 
						size="88"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
					<h:inputText rendered="#{not empty sunatController.documentoSunatNota.archivoDocumento}"
						value="#{sunatController.documentoSunatNota.archivoDocumento.strNombrearchivo}"
						size="88"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column width=	"170"
					rendered="#{empty sunatController.documentoSunatNota.id.intItemDocumentoSunat}">
					<a4j:commandButton styleClass="btnEstilos"
						value="Adjuntar Documento"
		                reRender="pAdjuntarDocumentoSunatNota"
		                oncomplete="Richfaces.showModalPanel('pAdjuntarDocumentoSunatNota')"
		                style="width:150px"/>
		        </rich:column>
			</h:panelGrid>
			
			<rich:spacer height="3px"/>	    

			<h:panelGrid columns="1" id="pgMsgErrorMontoDetalleNota">
		    	<rich:column width="840">
		    		<h:outputText value="#{sunatController.strMsgErrorMontoDocSunatNota}"
		    			styleClass="msgError"
						style="font-weight:bold;"/>
		    	</rich:column>
		    </h:panelGrid>
		    
		    <rich:spacer height="3px"/>	
		    
	    	<h:panelGrid columns="1">
				<rich:column width=	"840">
					<rich:dataTable
						id="dtDocSunatValidosNotas"
						sortMode="single"
						var="item"
						value="#{sunatController.listaDocSunatValidosParaNota}"
						rowKeyVar="rowKey"
						width="840"
						rows="#{fn:length(sunatController.listaDocSunatValidosParaNota)}">
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
		                			<rich:spacer/>
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
					          	<h:outputText value="#{item.bdMontoSaldoTemp}">
					         		<f:converter converterId="ConvertidorMontos"/>
					         	</h:outputText>
					    	</rich:column>
							<rich:column width="10px" style="text-align: center;">
								<h:selectOneRadio value ="#{item.rbDocSunatSelected}" id="rbOpcDoc"
									style="height:10px" onclick="selecRegistroDocumento(this.value)"
									disabled="#{item.bdMontoSaldoTemp == 0}">
									<f:selectItem itemValue="#{item.id.intItemDocumentoSunat}"/>							
								</h:selectOneRadio>
					      	</rich:column>  
						</rich:columnGroup>
				  	</rich:dataTable>
			  	</rich:column>
			</h:panelGrid>
	
		    <h:panelGrid columns="1">
				<rich:column width=	"840">
					<rich:dataTable
						id="dtDetalleDocSunatRelacionado"
						sortMode="single"
						var="item"
						value="#{sunatController.listaDocSunatDetDeDocSunatRel}"
						rowKeyVar="rowKey"
						width="840"
						rows="#{fn:length(sunatController.listaDocSunatDetDeDocSunatRel)}">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column>
		                			<h:outputText value="Nro"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Cant."/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="U.M."/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Descripción"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Centro de Costo"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="A/I"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="P.U."/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Moneda"/>   
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="T.C."/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="P.T."/>
		                		</rich:column>		
		                		<rich:column>
		                			<h:outputText value="Saldo"/>
		                		</rich:column>
		                		<rich:column>
		                			<h:outputText value="Monto"/>
		                		</rich:column>
		                	</rich:columnGroup>							
						</f:facet>
						<rich:columnGroup>
							<rich:column width="30" style="text-align: center">
					         	<h:outputText value="#{item.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"/>
							</rich:column>
							<rich:column width="40" style="text-align: center">
					         	<h:outputText value="#{item.ordenCompraDetalle.bdCantidad}"/>
					   		</rich:column>
							<rich:column width="40" style="text-align: center">
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_UNIDADMEDIDA}"
									itemValue="intIdDetalle" 
									itemLabel="strDescripcion"
									property="#{item.ordenCompraDetalle.intParaUnidadMedida}"/>
					   		</rich:column>
					   		<rich:column width="120" style="text-align: center">
								<h:outputText value="#{item.ordenCompraDetalle.strDescripcion}"/>
					    	</rich:column>
							<rich:column width="120" style="text-align: center">
					          	<h:outputText value="#{item.ordenCompraDetalle.sucursal.juridica.strRazonSocial} - #{item.ordenCompraDetalle.area.strDescripcion}"/>
					    	</rich:column>
							<rich:column width="40" style="text-align: center">
		                      	<h:outputText value="A" rendered="#{item.ordenCompraDetalle.intAfectoIGV==applicationScope.Constante.INT_AFECTOIGV}"/>
		                      	<h:outputText value="I" rendered="#{item.ordenCompraDetalle.intAfectoIGV==applicationScope.Constante.INT_INAFECTOIGV}"/>
					    	</rich:column>
							<rich:column width="40" style="text-align: right">
								<h:outputText value="#{item.ordenCompraDetalle.bdPrecioUnitario}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>
							<rich:column width="70" style="text-align: center">
								<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
									itemValue="intIdDetalle" 
									itemLabel="strDescripcion" 
									property="#{item.ordenCompraDetalle.intParaTipoMoneda}"/>
							</rich:column>
							<rich:column width="40" style="text-align: center">
								<h:outputText value="#{sunatController.tipoCambioNota.bdPromedio}">
									<f:converter converterId="ConvertidorMontos"/>
								</h:outputText>
							</rich:column>					
							<rich:column width="100" style="text-align: center">
								<h:outputText value="#{item.ordenCompraDetalle.bdPrecioTotal}">
									<f:converter converterId="ConvertidorMontos" />
								</h:outputText>
							</rich:column>
							<rich:column width="100" style="text-align: right">
		                      	<h:outputText value="#{item.bdMontoSaldoTemp}">
			                    	<f:converter converterId="ConvertidorMontos"/>
			                 	</h:outputText>
		                    </rich:column>
							<rich:column width="100" style="text-align: right">
								<h:inputText 
									onblur="extractNumber(this,2,false);" 
									onkeyup="extractNumber(this,2,false);" 			   						
									onkeypress="return soloNumerosDecimales(this)"
									size="12"	
									style="text-align: right;"							
									id="txtMontoAplicado3"
									disabled="#{item.bdMontoSaldoTemp==0}"
									value="#{item.bdMontoAplicar}">
									<a4j:support event="onchange"
										actionListener="#{sunatController.getBaseCalculoDocSunatNota}"
										reRender="pgMsgErrorMontoDetalleNota, txtMontoIngresadoTotalNotaCredito, txtMontoIngresadoTotalNotaDebito, pgBtnAgregarNota" />
								</h:inputText>						
							</rich:column>
						</rich:columnGroup>
				  	</rich:dataTable>
			  	</rich:column>		
			</h:panelGrid>		
	
		   	<h:panelGrid columns="2" id="pgBtnAgregarNota">
				<rich:column width="420" style="text-align: right;">
	                <a4j:commandButton styleClass="btnEstilos"
	                	value="Aceptar"
	                	oncomplete="if(#{!sunatController.mostrarMensajeExitoDocSunatNota}){rendPopUp6();}
	                				else{Richfaces.hideModalPanel('pAgregarNotaDocSunat');rendTabla6();}"
	                   	action="#{sunatController.agregarDocumentoSunatNota}" 
	                   	disabled="#{not empty sunatController.strMsgErrorMontoDocSunatNota}"
	                   	style="width:120px"/>
	            </rich:column>
				<rich:column width="420" style="text-align: left;">
	                <a4j:commandButton styleClass="btnEstilos"
	                	value="Cancelar"
	                	onclick="Richfaces.hideModalPanel('pAgregarNotaDocSunat')"
	                   	style="width:120px"/>
	            </rich:column>
	    	</h:panelGrid>
			
	    	<a4j:jsFunction name="rendTabla6" reRender="opMsgErrorAgregarNotaDocSunat, panelLetrasDocumento" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp6" reRender="fAgregarNotaSunat" ajaxSingle="true"/>					
		</h:panelGroup>
	</h:form>
</rich:modalPanel>	