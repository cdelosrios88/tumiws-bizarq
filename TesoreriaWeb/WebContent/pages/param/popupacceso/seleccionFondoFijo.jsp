<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmSeleccionFondoFijo">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Fondo Fijo : "/>
         		</rich:column>
         		<rich:column width="180">
					<h:selectOneMenu
						style="width: 180px;"
						value="#{accesoController.accesoDetalleAgregar.fondoFijo.intTipoFondoFijo}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOFONDOFIJO}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
                <rich:column width="70" style="text-align: right;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column width="180">
                	<h:selectOneMenu
						style="width: 180px;"
						disabled="#{accesoController.intAccionRegistro==1}"
						value="#{accesoController.accesoDetalleAgregar.intParaEstado}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>	
					</h:selectOneMenu>
              	</rich:column>
	    	</h:panelGrid>

			<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Moneda : "/>
         		</rich:column>
         		<rich:column width="180">
					<h:selectOneMenu
						style="width: 180px;"
						value="#{accesoController.accesoDetalleAgregar.fondoFijo.intMonedaCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>                
	    	</h:panelGrid>
	    	
			<h:panelGrid columns="2">
				<rich:column width="150">
					<h:outputText value="Responsable : "/>
		        </rich:column>
		        <rich:column width="180">
					<a4j:commandButton styleClass="btnEstilos"
				    	action="#{accesoController.abrirPopUpPersona}"
				        value="Agregar"
				        reRender="pSeleccionPersona"
				        oncomplete="Richfaces.showModalPanel('pSeleccionPersona')"
				        style="width:180px">				    		                    	
			   		</a4j:commandButton>
		        </rich:column>
			</h:panelGrid>
	    	
			<h:panelGrid columns="1" id="panelListaAccesoDetalleResFondo">
				
		        <rich:column width="580">
					<rich:panel style="text-align: center;border:0px;width=565px;overflow: scroll;height:100px;" >
						<rich:dataTable
							sortMode="single" 
						    var="item" 
						    value="#{accesoController.accesoDetalleAgregar.listaAccesoDetalleRes}"  
							rowKeyVar="rowKey" 
							width="540px" 
							rows="#{fn:length(accesoController.accesoDetalleAgregar.listaAccesoDetalleRes)}">					
							
							<rich:column width="50px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Código"/>
								</f:facet>
								<h:outputText value="#{item.id.intItemAccesoDetalleRes}"/>
							</rich:column>
							<rich:column width="250px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Nombre Completo"/>
								</f:facet>
								<h:outputText value="#{item.persona.natural.strNombres} "/>
								<h:outputText value="#{item.persona.natural.strApellidoPaterno} "/>
								<h:outputText value="#{item.persona.natural.strApellidoMaterno}"/>
							</rich:column>
							<rich:column width="80px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="DNI"/>
								</f:facet>
								<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"/>
							</rich:column>
							<rich:column width="60px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Orden"/>
								</f:facet>
								<h:outputText value="#{item.intOrden}"/>
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Editar"/>
								</f:facet>
								<a4j:commandLink
									value="Editar"
									reRender="pSeleccionPersona"
									oncomplete="Richfaces.showModalPanel('pSeleccionPersona')"
									actionListener="#{accesoController.editarPopUpPersona}">
					            	<f:attribute name="item" value="#{item}"/>							
								</a4j:commandLink>
							</rich:column>
							<rich:column width="50px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Eliminar"/>
								</f:facet>
								<a4j:commandLink
									value="Eliminar"
									reRender="panelListaAccesoDetalleResFondo"
									actionListener="#{accesoController.eliminarAccesoDetalleResCuenta}">
					            	<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
							</rich:column>
						</rich:dataTable>
					</rich:panel>
		        </rich:column>
			</h:panelGrid>	    	

	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Monto del Fondo : "/>
         		</rich:column>
         		<rich:column width="180">
                	<h:inputText size="31" value="#{accesoController.accesoDetalleAgregar.bdMontoFondo}" 
                		onkeypress="return soloNumerosDecimales(this)"/>
              	</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Monto Máximo del Fondo : "/>
         		</rich:column>
         		<rich:column width="180">
                	<h:inputText size="31" value="#{accesoController.accesoDetalleAgregar.bdMontoMaximo}"
                		onkeypress="return soloNumerosDecimales(this)"/>
              	</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Alerta de reintegro : "/>
         		</rich:column>
         		<rich:column width="180">
					<h:selectOneMenu 
						style="width: 180px;"
						value="#{accesoController.accesoDetalleAgregar.intParaTipoValorAlerta}">						
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ALERTADEREINTEGRO}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
         		<rich:column width="257">
                	<h:inputText size="46" value="#{accesoController.accesoDetalleAgregar.bdMontoAlerta}"
                		onkeypress="return soloNumerosDecimales(this)"/>
              	</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Tiempo de Vencimiento : "/>
         		</rich:column>
         		<rich:column width="180">
					<h:inputText size="31" value="#{accesoController.accesoDetalleAgregar.intCantidadTiempo}"
						onkeypress="return soloNumerosNaturales(event)"/>
                </rich:column>
         		<rich:column width="257">                	
                	<h:selectOneMenu 
						style="width: 257px;"
						value="#{accesoController.accesoDetalleAgregar.intParaFrecuenciaTiempo}">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FRECUENCPAGOINT}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
	    	</h:panelGrid>

	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="150">
         			<h:outputText value="Observación : "/>
         		</rich:column>
         		<rich:column width="440">
					<h:inputTextarea cols="85"	rows="2" value="#{accesoController.accesoDetalleAgregar.strObservacion}"/>
		        </rich:column>		        		        	
	    	</h:panelGrid>
	    	
	    	<h:panelGroup rendered="#{accesoController.mostrarMensajePopUp}">
	    		<h:outputText value="#{accesoController.mensajePopUp}" 
					styleClass="msgError"
					style="font-weight:bold"/>
	    	</h:panelGroup>
	    	<h:panelGroup rendered="#{!accesoController.mostrarMensajePopUp}">
	    		<rich:spacer height="20px"/>
	    	</h:panelGroup>	

			
			<h:panelGrid columns="2">
				<rich:column width="220">
			    </rich:column>
			    <rich:column style="border:none">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{accesoController.aceptarFondoFijo}"
		        		oncomplete="if(#{accesoController.mostrarMensajePopUp}){rendPopUp2();}else{Richfaces.hideModalPanel('pSeleccionFondoFijo');rendTabla2();}"
		        		reRender="panelListaFondoFijo"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pSeleccionFondoFijo')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla2" reRender="panelListaFondoFijo" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp2" reRender="frmSeleccionFondoFijo" ajaxSingle="true"/>
			
	   	</h:panelGroup>
	</h:form>