<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->




<h:form>
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:10px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="REGISTRO DE CUADRO COMPARATIVO DE PROVEEDORES" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
       		<h:panelGrid columns="11">
           		<rich:column width="35">
					<h:outputText value="Tipo :"/>
				</rich:column>
				<rich:column width="150">
					<h:selectOneMenu
						value="#{cuadroController.cuadroComparativoFiltro.intParaTipoPropuesta}"
						style="width: 150px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_PROPUESTACOMPRA}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="60">
					<h:outputText value="Sucursal :"/>
				</rich:column>
				<rich:column width="210">
					<h:selectOneMenu
						value="#{cuadroController.cuadroComparativoFiltro.intSucuIdSucursalFiltro}"
						style="width: 200px;">
						<f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel"
							value="#{cuadroController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}"
							itemLabel="#{sel.juridica.strRazonSocial}"/>																		
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="50">
					<h:outputText value="Estado :"/>
				</rich:column>
				<rich:column width="95">
					<h:selectOneMenu
						value="#{cuadroController.cuadroComparativoFiltro.intParaEstado}"
						style="width: 90px;">
						<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</rich:column>
				<rich:column width="40" style="text-align: left;">
                	<h:outputText value="Fecha: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{cuadroController.cuadroComparativoFiltro.dtFiltroDesde}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{cuadroController.cuadroComparativoFiltro.dtFiltroHasta}"  
						jointPoint="top-right" 
						direction="right" 
						inputSize="10" 
						showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="80" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="panelTablaResultadosCC,panelMensajeCC"
                    	action="#{cuadroController.buscar}" 
                    	style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
            <!-- Agregado por cdelosrios, 08/10/2013 -->
            <h:panelGrid columns="2">
            	<rich:column width="140">
					<h:outputText value="Nro. Cuadro comparativo :"/>
				</rich:column>
				<rich:column>
					<h:inputText value="#{cuadroController.cuadroComparativoFiltro.id.intItemCuadroComparativo}" 
						size="10" onkeydown="return validarEnteros(event)"/>
				</rich:column>
            </h:panelGrid>
            <!-- Fin agregado por cdelosrios, 08/10/2013 -->
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaResultadosCC">
	        	<rich:extendedDataTable id="tblResultadoCC" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{cuadroController.listaCuadroComparativo}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1030px" 
					height="160px" 
					align="center">
                    
					<rich:column width="40" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Item"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemCuadroComparativo}"/>
                    </rich:column>
					<rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Propuesta"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_PROPUESTACOMPRA}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaTipoPropuesta}"/>
                    </rich:column>
                    <rich:column width="110" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.requisicion.sucursalSolicitante.juridica.strRazonSocial}"/>
                  	</rich:column>                  	
                  	<rich:column width="90" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Requisición"/>
                      	</f:facet>
                      	<h:outputText value="#{item.requisicion.id.intItemRequisicion}"/>
                  	</rich:column>
                  	<rich:column width="170" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Empresa de Servicio"/>                      		
                      	</f:facet>
                      	<!-- Modificado por cdelosrios, 14/10/2013 -->
                      	<%--<h:outputText rendered="#{item.intParaEstadoAprobacion==applicationScope.Constante.PARAM_T_ESTADOAPROBACIONCUADRO_APROBADO}" 
                      		value="#{item.proveedorAprobado.persona.juridica.strRazonSocial}"/>--%>
                      	<!-- Fin modificado por cdelosrios, 14/10/2013 -->
                      	<!-- Agregado por cdelosrios, 14/10/2013 -->
                      	<h:outputText value="#{item.proveedorAprobado.persona.juridica.strRazonSocial}"/>
                      	<!-- Fin agregado por cdelosrios, 14/10/2013 -->
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Estado"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstado}"/>
                    </rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Aprobación"/>
                      	</f:facet>
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODEPOSITADO}" 
							itemValue="intIdDetalle" 
							itemLabel="strDescripcion" 
							property="#{item.intParaEstadoAprobacion}"/>
                    </rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Monto"/>
                      	</f:facet>
                      	<h:outputText rendered="#{item.intParaEstadoAprobacion==applicationScope.Constante.PARAM_T_ESTADOAPROBACIONCUADRO_APROBADO}" 
                      		value="#{item.proveedorAprobado.bdPrecioTotal}">
                      		<f:converter converterId="ConvertidorMontos"/>
                      	</h:outputText>
                  	</rich:column>
                  	<rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Orden Compra"/>
                      	</f:facet>                      	
                      	<h:outputText rendered="#{not empty item.ordenCompra}" 
                      		value="#{item.ordenCompra.id.intItemOrdenCompra}"/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha Registro"/>
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy hh:mm:ss" />
	                 	</h:outputText>
                  	</rich:column>
                  	
                  	<a4j:support event="onRowClick"
                   		actionListener="#{cuadroController.seleccionarRegistro}"
                   		oncomplete="Richfaces.showModalPanel('pAlertaRegistroCC')"
						reRender="pAlertaRegistroCC">
                    	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblResultadoCC" maxPages="10"/>   
					</f:facet>
					
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Aprobar o Eliminar un Cuadro Comparativo hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensajeCC" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{cuadroController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{cuadroController.mostrarMensajeExito}"/>
			<h:outputText value="#{cuadroController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{cuadroController.mostrarMensajeError}"/>
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotonesCC">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					action="#{cuadroController.habilitarPanelInferior}" 
					reRender="contPanelInferiorCC,panelMensajeCC,panelBotonesCC" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{cuadroController.grabar}" 
			    	reRender="contPanelInferiorCC,panelMensajeCC,panelBotonesCC,panelTablaResultadosCC"
			    	disabled="#{!cuadroController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{cuadroController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferiorCC,panelMensajeCC,panelBotonesCC"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferiorCC">
	<rich:panel  rendered="#{cuadroController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="8" rendered="#{not empty cuadroController.cuadroComparativoNuevo.id.intItemCuadroComparativo}">
			<rich:column width=	"145">
				<h:outputText value="Número de Cuadro Comp : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="18" 
					value="#{cuadroController.cuadroComparativoNuevo.id.intItemCuadroComparativo}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
		</h:panelGrid>
		
		
		<h:panelGrid columns="8">
			<rich:column width=	"145">
				<h:outputText value="Fecha Registro : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="18" 
					value="#{cuadroController.cuadroComparativoNuevo.tsFechaRegistro}"
					readonly="true" 
					style="background-color: #BFBFBF;font-weight:bold;">
					<f:convertDateTime pattern="dd/MM/yyyy"/>
				</h:inputText>
			</rich:column>
			<rich:column width="120">
				<h:outputText value="Tipo de Propuesta : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{cuadroController.intTipoAccion!=1}"
					style="width: 200px;"
					value="#{cuadroController.cuadroComparativoNuevo.intParaTipoPropuesta}">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_PROPUESTACOMPRA}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" id="panelRequisicionCC">
			<rich:column width=	"145">
				<h:outputText value="Requisición : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText 
					rendered="#{not empty cuadroController.cuadroComparativoNuevo.requisicion}"
					value="#{cuadroController.cuadroComparativoNuevo.requisicion.strEtiqueta}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty cuadroController.cuadroComparativoNuevo.requisicion}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Requisicion"
	                disabled="#{cuadroController.intTipoAccion!=1}"
	                reRender="pBuscarRequisicionCuadro"
	                oncomplete="Richfaces.showModalPanel('pBuscarRequisicionCuadro')"
	                action="#{cuadroController.abrirPopUpBuscarRequisicion}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8" rendered="#{empty cuadroController.cuadroComparativoNuevo.id.intItemCuadroComparativo}">
			<rich:column width=	"145">
				<h:outputText value="Propuesta de Proveedores : "/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Agregar Propuesta"
	                disabled="#{cuadroController.intTipoAccion!=1}"
	                reRender="pAgregarProveedorCuadro"
	                oncomplete="Richfaces.showModalPanel('pAgregarProveedorCuadro')"
	                action="#{cuadroController.abrirPopUpAgregarProveedor}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		
		<h:panelGroup id="panelProveedoresCC" rendered="#{empty cuadroController.cuadroComparativoNuevo.id.intItemCuadroComparativo}">
		
		<a4j:repeat value="#{cuadroController.cuadroComparativoNuevo.listaCuadroComparativoProveedor}" var="proveedor">
			
			<rich:spacer height="30px"/>		
			
			<h:panelGrid columns="8">
				<rich:column width=	"110">
					<h:outputText value="Proveedor : "/>
				</rich:column>
				<rich:column width="482">
					<h:outputText
						rendered="#{not empty proveedor.persona}"
						value="#{proveedor.persona.intIdPersona} - #{proveedor.persona.juridica.strRazonSocial} - RUC : #{proveedor.persona.strRuc}"
						style="background-color: #BFBFBF;font-weight:bold;"/>
				</rich:column>
				<rich:column width=	"100">
					<h:outputText value="Estado Aprobación :"/>
				</rich:column>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODEPOSITADO}" 
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion" 
					property="#{cuadroController.cuadroComparativoNuevo.intParaEstadoAprobacion}"/>
			</h:panelGrid>
			
			<h:panelGrid columns="6">
				<rich:column width="110">
					<h:outputText value="Plazo de Entrega : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{proveedor.tsPlazoEntrega}">
						<f:convertDateTime pattern="dd/MM/yyyy"/>
					</h:outputText>					
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Lugar de Entrega : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{proveedor.strLugarEntrega}"/>
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Garantía : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{proveedor.strGarantia}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="6">				
				<rich:column width="110">
					<h:outputText value="Condición de Pago : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{proveedor.strCondicionPago}"/>
				</rich:column>
				<rich:column width=	"100">
					<h:outputText value="Propuesta : "/>
				</rich:column>
				<rich:column width=	"200">
					<h:outputText value="#{proveedor.archivoDocumento.strNombrearchivo}"/>
				</rich:column>				
				<rich:column width="100">
					<h:outputText value="Observación : "/>
				</rich:column>
				<rich:column width="100">
					<h:outputText value="#{proveedor.strObservacion}"/>
				</rich:column>
			</h:panelGrid>
			
			
			<h:panelGrid columns="2">
				<rich:column width=	"850">
					<rich:dataTable
						sortMode="single"
						var="item"
						value="#{proveedor.listaCuadroComparativoProveedorDetalle}"
						rowKeyVar="rowKey"
						width="830px"
						rows="#{fn:length(proveedor.listaCuadroComparativoProveedorDetalle)}">
						
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
				  	</rich:dataTable>
		  		</rich:column>		        
			</h:panelGrid>
			
			<h:panelGrid columns="6">				
				<rich:column width="110">
					<h:outputText value="Precio Total : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{proveedor.bdPrecioTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="100">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion" 
						property="#{proveedor.intParaTipoMoneda}"/>
				</rich:column>
			</h:panelGrid>
			
		</a4j:repeat>
		
		</h:panelGroup>
		
		
		<h:panelGroup rendered="#{cuadroController.intTipoAccion==2 || cuadroController.intTipoAccion==3}">
		
		<rich:spacer height="10px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"200">
				<h:outputText value="PROPUESTA DE PROVEEDORES"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="10px"/>
		
		<h:panelGrid columns="8" id="panelAprobacionCC">
			<rich:column width=	"145">
				<h:outputText value="Usuario Aprobación : "/>
			</rich:column>
			<rich:column width="497">
				<h:inputText
					rendered="#{not empty cuadroController.cuadroComparativoNuevo.personaAprueba}"
					value="DNI : #{cuadroController.cuadroComparativoNuevo.personaAprueba.documento.strNumeroIdentidad} - #{cuadroController.cuadroComparativoNuevo.personaAprueba.natural.strNombreCompleto}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
				<h:inputText 
					rendered="#{empty cuadroController.cuadroComparativoNuevo.personaAprueba}"
					size="73"
					readonly="true"
					style="background-color: #BFBFBF;font-weight:bold;"/>
			</rich:column>
			<rich:column width=	"170">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar Persona"
	                disabled="#{cuadroController.intTipoAccion ==3}"
	                reRender="pBuscarPersonaCuadro"
	                oncomplete="Richfaces.showModalPanel('pBuscarPersonaCuadro')"
	                action="#{cuadroController.abrirPopUpBuscarPersonaNatural}" 
	                style="width:150px"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"145">
				<h:outputText value="Propuesta Seleccionada : "/>
			</rich:column>
			<rich:column width="497">
				<h:selectOneMenu
					disabled="#{cuadroController.intTipoAccion ==3}"
					style="width: 490px;"
					value="#{cuadroController.intItemProveedorSeleccionado}">
					<tumih:selectItems var="sel"
						value="#{cuadroController.cuadroComparativoNuevo.listaCuadroComparativoProveedor}"
						itemValue="#{sel.id.intItemCuadroComparativoProveedor}"
						itemLabel="#{sel.strEtiqueta}"/>
					<a4j:support event="onchange" 
						action="#{cuadroController.seleccionarProveedor}" 
						reRender="contPanelInferiorCC"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="8">
			<rich:column width=	"145" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="490">
				<h:inputTextarea cols="89" rows="3" 
					disabled="#{cuadroController.intTipoAccion ==3}"
					value="#{cuadroController.cuadroComparativoNuevo.strObservacionAutoriza}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="30px"/>		
		
		<h:panelGroup id="panelProveedorAprobado">
			
			<h:panelGrid columns="8">
				<rich:column width=	"110">
					<h:outputText value="Proveedor : "/>
				</rich:column>
				<rich:column width="520">
					<h:outputText
						rendered="#{not empty cuadroController.cuadroComparativoNuevo.proveedorAprobado.persona}"
						value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.persona.intIdPersona} - #{cuadroController.cuadroComparativoNuevo.proveedorAprobado.persona.juridica.strRazonSocial} - RUC : #{cuadroController.cuadroComparativoNuevo.proveedorAprobado.persona.strRuc}"
						style="background-color: #BFBFBF;font-weight:bold;"/>
				</rich:column>
				<rich:column width=	"120">
					<h:outputText value="Estado Aprobación :"/>
				</rich:column>
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADODEPOSITADO}" 
					itemValue="intIdDetalle" 
					itemLabel="strDescripcion" 
					property="#{cuadroController.cuadroComparativoNuevo.intParaEstadoAprobacion}"/>
			</h:panelGrid>
			
			<h:panelGrid columns="6">
				<rich:column width="110">
					<h:outputText value="Plazo de Entrega : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.tsPlazoEntrega}">
						<f:convertDateTime pattern="dd/MM/yyyy"/>
					</h:outputText>					
				</rich:column>
				<rich:column width="100">
					<h:outputText value="Lugar de Entrega : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.strLugarEntrega}"/>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Garantía : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.strGarantia}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGrid columns="6">				
				<rich:column width="110">
					<h:outputText value="Condición de Pago : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.strCondicionPago}"/>
				</rich:column>
				<rich:column width=	"100">
					<h:outputText value="Propuesta : "/>
				</rich:column>
				<rich:column width=	"200">
					<h:commandLink  value="Descargar"		
						actionListener="#{fileUploadController.descargarArchivo}">
						<f:attribute name="archivo" value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.archivoDocumento}"/>		
					</h:commandLink>
				</rich:column>
				<rich:column width="120">
					<h:outputText value="Observación : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.strObservacion}"/>
				</rich:column>
			</h:panelGrid>
			
			
			<h:panelGrid columns="2">
				<rich:column width=	"850">
					<rich:dataTable
						sortMode="single"
						var="item"
						value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.listaCuadroComparativoProveedorDetalle}"
						rowKeyVar="rowKey"
						width="830px"
						rows="#{fn:length(cuadroController.cuadroComparativoNuevo.proveedorAprobado.listaCuadroComparativoProveedorDetalle)}">
						
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
				  	</rich:dataTable>
		  		</rich:column>		        
			</h:panelGrid>
			
			<h:panelGrid columns="6">				
				<rich:column width="110">
					<h:outputText value="Precio Total : "/>
				</rich:column>
				<rich:column width="200">
					<h:outputText value="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.bdPrecioTotal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
					<h:outputText value=" "/>
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
						itemValue="intIdDetalle" 
						itemLabel="strDescripcion" 
						property="#{cuadroController.cuadroComparativoNuevo.proveedorAprobado.intParaTipoMoneda}"/>
				</rich:column>
			</h:panelGrid>
		</h:panelGroup>
		
		</h:panelGroup>
		
	</rich:panel>
	
	</h:panelGroup>	

</rich:panel>
</h:form>