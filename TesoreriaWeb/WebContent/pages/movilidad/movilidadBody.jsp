<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi	-->
	<!-- Autor     : Arturo Julca   	-->
	<!-- Fecha Modificación: 16.12.2013 -->
	<!-- Modificado: Junior Chavez      -->
	<!-- Modulo    :                	-->
	<!-- Fecha     :                	-->


<rich:modalPanel id="pAlertaRegistro" width="360" height="135"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistro">
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="80">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{movilidadController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje,panelBotones"/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{movilidadController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaMovilidad,panelMensaje,contPanelInferior"
             			rendered="#{movilidadController.mostrarBtnEliminar}"/>	        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAgregarMovilidadDetalle" width="500" height="300"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Detalle de Movilidad"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarMovilidadDetalle" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   <a4j:include viewId="/pages/movilidad/popup/agregarMovilidadDetalle.jsp"/>
</rich:modalPanel>

<h:outputText value="#{movilidadController.inicioPage}" />
<h:form id="frmMovilidad">
   	<h:panelGroup layout="block" style="padding:15px;border:1px solid #B3B3B3;text-align: left;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="PLANILLA DE MOVILIDAD" style="font-weight:bold; font-size:14px"/>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="11">  
        		<rich:column width="80" style="text-align: left;">
                	<h:outputText value="Usuario: "/>
            	</rich:column>          
            	  	
              	<rich:column width="158" style="text-align: right;">
                	<h:selectOneMenu 
						style="width: 158px;"
						value="#{movilidadController.movilidadFiltro.intTipoBusqueda}">
						<f:selectItem itemValue="#{applicationScope.Constante.FILTROMOVIMIENTO_DNI}" itemLabel="DNI"/>
						<f:selectItem itemValue="#{applicationScope.Constante.FILTROMOVIMIENTO_APELLIDOS_Y_NOMBRES}" itemLabel="Apellidos y nombres"/>						
					</h:selectOneMenu>
            	</rich:column>
            	
            	<rich:column width="405" style="text-align: left;">
                	<h:inputText size="76" value="#{movilidadController.movilidadFiltro.strTextoFiltro}"/>
            	</rich:column>
            	
            	<rich:column width="80" style="text-align: left;">
                	<h:outputText value="Sucursal: "/>
            	</rich:column>     
            	<rich:column width="140" style="text-align: left;">
            		<h:selectOneMenu id="cboSucursal" style="width: 140px;"
						value="#{movilidadController.movilidadFiltro.intSucuIdSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{movilidadController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
					</h:selectOneMenu>
				</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<h:selectOneMenu
						style="width: 100px;"
						value="#{movilidadController.movilidadFiltro.intParaEstado}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							propertySort="intOrden"/>	
					</h:selectOneMenu>
            	</rich:column>				
            </h:panelGrid>
            
            <h:panelGrid columns="11">      
         		<rich:column width="150" style="text-align: left;">
                	<h:outputText value="Fecha de Registro: "/>
            	</rich:column>
            	<rich:column width="110" style="text-align: right;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{movilidadController.dtDesdeFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="110" style="text-align: left;">
                	<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{movilidadController.dtHastaFiltro}"  
						jointPoint="top-right" direction="right" inputSize="10" showApplyButton="true"/>
            	</rich:column>
            	<rich:column width="150" style="text-align: left;">
                	<h:outputText value="Periodo de Planilla: "/>
            	</rich:column>
            	<rich:column width="70" style="text-align: right;">
                	<h:selectOneMenu
						style="width: 70px;"
						value="#{movilidadController.movilidadFiltro.intAño}">
						<tumih:selectItems var="sel"
							value="#{movilidadController.listaAnios}"
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<h:selectOneMenu
						style="width: 100px;"
						value="#{movilidadController.movilidadFiltro.intMes}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							propertySort="intOrden"/>	
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="180" style="text-align: left;">
                	<h:outputText value="Estado de Cancelación: "/>
            	</rich:column>
            	<rich:column width="100" style="text-align: left;">
                	<h:selectOneMenu
						style="width: 100px;"
						value="#{movilidadController.movilidadFiltro.intParaEstadoPago}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_ESTADOPAGO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"
							propertySort="intOrden"/>	
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="100" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaMovilidad,panelMensaje"
                    	action="#{movilidadController.buscar}" style="width:100px"/>
            	</rich:column>
            </h:panelGrid>
            
            
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaMovilidad">
	        	<rich:extendedDataTable id="tblMovilidad" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{movilidadController.listaMovilidad}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="1000px" 
					height="160px" 
					align="center">
                                
					<rich:column width="20px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"/>                        	
                    </rich:column>
                  	<rich:column width="90px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Nro. Planilla"/>
                      	</f:facet>
                      	<h:outputText value="#{item.id.intItemMovilidad}"/>
                	</rich:column>
                    <rich:column width="250" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Usuario"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strPersonaUsuario}"/>
                  	</rich:column>
                    <rich:column width="200" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Sucursal"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.strDescSucuSubcucu}"/>
                  	</rich:column>                    
                    <rich:column width="100" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Fecha"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.tsFechaRegistro}">
	                    	<f:convertDateTime pattern="dd/MM/yyyy" />
	                 	</h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Periodo"/>                      		
                      	</f:facet>
                      	<h:outputText value="#{item.intPeriodo}"/>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Monto"/>
                        </f:facet>
                        <h:outputText value="#{item.bdMontoAcumulado}">
                        	<f:converter converterId="ConvertidorMontos"/>
                        </h:outputText>
                  	</rich:column>
                  	<rich:column width="80" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Moneda"/>
                        </f:facet>
                        <h:outputText value="Soles"/>
                  	</rich:column>
                    <rich:column width="100" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Egreso"/>
                        </f:facet>
                        <h:outputText rendered="#{not empty item.egreso}" 
                        	value="#{item.egreso.strNumeroEgreso}"/>
                  	</rich:column>
                  	
                   	<f:facet name="footer">
						<rich:datascroller for="tblMovilidad" maxPages="10"/>   
					</f:facet>
					
					<a4j:support event="onRowClick"  
						actionListener="#{movilidadController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{movilidadController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar o Eliminar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{movilidadController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{movilidadController.mostrarMensajeExito}"/>
			<h:outputText value="#{movilidadController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{movilidadController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{movilidadController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{movilidadController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaMovilidad"
			    	disabled="#{!movilidadController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	action="#{movilidadController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>
		
		
		
	<h:panelGroup id="contPanelInferior">
			
		<rich:panel  rendered="#{movilidadController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">	 		
			
			<h:panelGroup rendered="#{!movilidadController.datosValidados}">
			
				<h:panelGrid columns="8">
					<rich:column width="120">
						<h:outputText value="Tipo de Persona : "/>
				  	</rich:column>
				  	<rich:column width="150">
						<h:inputText size="20" value="Persona Natural" readonly="true" style="background-color: #BFBFBF;"/>						
				 	</rich:column>
					<rich:column width="130" style="text-align: left;">
						<h:outputText value="Tipo de Documento : "/>
				  	</rich:column>
				    <rich:column width="100">
						<h:selectOneMenu
							style="width: 80px;">
							<f:selectItem itemValue="1" itemLabel="DNI"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column width="165" style="text-align: left;">
						<h:outputText value="Número de Documento : "/>
				  	</rich:column>
				    <rich:column width="200">
						<h:inputText size="27" value="#{movilidadController.strFiltroDNI}"/>
					</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="15px"/>
				
				<h:panelGrid columns="1">
					<rich:column width="865">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Validar Datos" reRender="contPanelInferior,panelBotones"
	                    	action="#{movilidadController.validarDatos}" style="width:865px"/>
				  	</rich:column>
				</h:panelGrid>
						
			</h:panelGroup>
			
			
			<h:panelGroup rendered="#{movilidadController.datosValidados}">
				
				<h:panelGrid columns="8">
					<rich:column width="80">
						<h:outputText value="Empresa : "/>
				  	</rich:column>
				  	<rich:column width="515">
						<h:inputText size="90" readonly="true" style="background-color: #BFBFBF;" value="#{movilidadController.strEtiquetaEmpresa}"/>						
				 	</rich:column>
				 	<rich:column width="60" style="text-align: left;">
						<h:outputText value="Fecha : "/>
				  	</rich:column>
				  	<rich:column width="180">
						<h:inputText size="31" readonly="true" style="background-color: #BFBFBF;" value="#{movilidadController.movilidadNuevo.tsFechaRegistro}">
							<f:convertDateTime pattern="dd-MM-yyyy" />
						</h:inputText>						
				 	</rich:column>				 	
				</h:panelGrid>
				
				<h:panelGrid columns="8">
					<rich:column width="80">
						<h:outputText value="Usuario : "/>
				  	</rich:column>
				  	<rich:column width="515">
						<h:inputText size="90" readonly="true" style="background-color: #BFBFBF;" value="#{movilidadController.movilidadNuevo.strEtiquetaUsuario}"/>
				 	</rich:column>
				 	<rich:column width="60">
						<h:outputText value="Periodo : "/>
				  	</rich:column>
				  	<rich:column width="180">
						<h:inputText size="31" readonly="true" style="background-color: #BFBFBF;" value="#{movilidadController.movilidadNuevo.intPeriodo}"/>
				 	</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="8" id="panelSucursal">
	         		<rich:column  width="80">
	         			<h:outputText value="Sucursal : "/>
	         		</rich:column>
	         		<rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							disabled="#{movilidadController.deshabilitarNuevo}"
							value="#{movilidadController.movilidadNuevo.intSucuIdSucursal}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel" 
								value="#{movilidadController.listaSucursal}" 
								itemValue="#{sel.id.intIdSucursal}" 
								itemLabel="#{sel.juridica.strSiglas}"/>
							<a4j:support event="onchange" action="#{movilidadController.seleccionarSucursal}" reRender="panelSucursal"/>
						</h:selectOneMenu>
	                </rich:column>
	                <rich:column  width="100" style="text-align: right;">
	         			<h:outputText value="Subsucursal : "/>
	         		</rich:column>
	                <rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							disabled="#{movilidadController.deshabilitarNuevo}"
							value="#{movilidadController.movilidadNuevo.intSudeIdSubsucursal}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{movilidadController.listaSubsucursal}" 
								itemValue="#{sel.id.intIdSubSucursal}" 
								itemLabel="#{sel.strDescripcion}"/>				
						</h:selectOneMenu>
	                </rich:column>
	                <rich:column  width="30" style="text-align: right;">
	         			
	         		</rich:column>
	                <rich:column  width="60" style="text-align: left;">
	         			<h:outputText value="Área : "/>
	         		</rich:column>
	                <rich:column width="180">
						<h:selectOneMenu
							style="width: 180px;"
							disabled="#{movilidadController.deshabilitarNuevo}"
							value="#{movilidadController.movilidadNuevo.intIdArea}">
							<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
							<tumih:selectItems var="sel"
								value="#{movilidadController.listaArea}" 
								itemValue="#{sel.id.intIdArea}" 
								itemLabel="#{sel.strDescripcion}"/>					
						</h:selectOneMenu>
	                </rich:column>
	                
		    	</h:panelGrid>				
				
				<h:panelGrid columns="8">
					<rich:column width="80">
						<h:outputText value="Movilidad : "/>
				  	</rich:column>
				  	<rich:column width="150">
						<a4j:commandButton styleClass="btnEstilos"
	                		value="Agregar"
	                		disabled="#{movilidadController.deshabilitarNuevo}"
	                		reRender="pAgregarMovilidadDetalle"
	                    	oncomplete="Richfaces.showModalPanel('pAgregarMovilidadDetalle')"
	                    	action="#{movilidadController.abrirPopUpMovilidadDetalle}" style="width:150px"/>
				 	</rich:column>
				</h:panelGrid>
				
				<h:panelGrid columns="8" id="panelListaMovilidadDetalle">
					<rich:column width="80">
				  	</rich:column>
				  	<rich:column width="840">
						<rich:dataTable
							sortMode="single"
						    var="item"
						    value="#{movilidadController.listaMovilidadDetalle}"
							rowKeyVar="rowKey"
							width="830px"
							rows="#{fn:length(movilidadController.listaMovilidadDetalle)}">
							
							<rich:column width="30px" style="text-align: center">
								<h:outputText value="#{rowKey + 1}"/>
							</rich:column>
							<rich:column width="100px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Fecha"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.dtFechaMovilidad}">
	                            	<f:convertDateTime pattern="dd-MM-yyyy" />
	                        	</h:outputText>
		                	</rich:column>
		                	<rich:column width="150px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Tipo de Motivo"/>
		                      	</f:facet>
		                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_PLANILLAMOVILIDAD}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intParaTipoMovilidad}"/>
		                	</rich:column>
		                	<rich:column width="150px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Motivo"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.strMotivo}" title="#{item.strMotivo}"/>
		                	</rich:column>
		                	<rich:column width="200px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Destino"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.strDestino}" title="#{item.strDestino}"/>
		                	</rich:column>
		                	<rich:column width="80px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Monto"/>
		                      	</f:facet>
		                      	<h:outputText value="#{item.bdMonto}">
		                      		<f:converter converterId="ConvertidorMontos"/>
		                      	</h:outputText>
		                	</rich:column>
		                	<rich:column width="60px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Moneda"/>
		                      	</f:facet>
		                      	<h:outputText value="Soles"/>
		                	</rich:column>
		                	<rich:column width="60px" style="text-align: center">
		                    	<f:facet name="header">
		                        	<h:outputText value="Eliminar"/>
		                      	</f:facet>
		                      	<a4j:commandLink
									value="Eliminar"
					            	actionListener="#{movilidadController.eliminarMovilidadDetalle}"
									reRender="panelListaMovilidadDetalle"
									disabled="#{movilidadController.deshabilitarNuevo}">
									<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
		                	</rich:column>
						</rich:dataTable>
				 	</rich:column>
				</h:panelGrid>
			</h:panelGroup>
			

			
			
		</rich:panel>
				
	</h:panelGroup>	

</h:panelGroup>
</h:form>