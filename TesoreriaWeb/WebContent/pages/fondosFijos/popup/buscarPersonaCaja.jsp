<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form>
	   	<h:panelGroup>
	   		<h:outputText id="msgErrorMontoPllaEfect" value="#{cajaController.strMsgErrorSocioSeleccionado}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{!cajaController.strMsgErrorSocioSeleccionado}"/>		    	
	    	<h:panelGrid columns="12" id="panelPopUpBuscarPersonaC">
	    		<rich:column width="30">
	    			<h:selectOneMenu id="cboTipoPersonaC"
	    				onchange="getFiltroBusqC(#{applicationScope.Constante.ONCHANGE_VALUE})"
						style="width: 130px;"
						value="#{cajaController.intOpcionBusquedaC}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel"
							value="#{cajaController.listaTablaOpcionBusqueda}"
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
           		</rich:column>

	           		<rich:column width="150">
	           			<h:inputText size="20"
	           				rendered="#{cajaController.intTipoPersonaC==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL && cajaController.intOpcionBusquedaC==applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_DNI}"
	           				onkeydown="return validarNumDocIdentidad(this,event,8)"
	           				value="#{cajaController.strFiltroTextoPersona}"/>
	           			<h:inputText size="20"
	           				rendered="#{cajaController.intTipoPersonaC==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA && cajaController.intOpcionBusquedaC==applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_RUC}"
	           				onkeydown="return validarNumDocIdentidad(this,event,12)"
	           				value="#{cajaController.strFiltroTextoPersona}"/>
	           			<h:inputText size="20"
	           				rendered="#{cajaController.intOpcionBusquedaC==applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE || cajaController.intOpcionBusquedaC==applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_RAZONSOCIAL}"
	           				value="#{cajaController.strFiltroTextoPersona}"/>
	           		</rich:column>

           		
           		<rich:column>
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" 
                		reRender="tablaPersonaC"
                    	action="#{cajaController.buscarPersona}" 
                    	style="width:100px"/>
           		</rich:column>
	    	</h:panelGrid>	    	

			<h:panelGroup id="tablaPersonaC">
	    		<rich:dataTable
	    			var="item"
	                value="#{cajaController.listaPersonaIngresoCaja}"
			  		rowKeyVar="rowKey"
			  		rows="5"
			  		sortMode="single" 
			  		width="520px">
			        
					<rich:column width="400" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Nombre"/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.strIngCajaDescripcion}"/>
			      	</rich:column>
			      	<rich:column width="150" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText
				        		rendered="#{cajaController.intTipoPersonaC==applicationScope.Constante.PARAM_T_TIPOPERSONA_NATURAL}"
				        		value="DNI"/>
				        	<h:outputText
				        		rendered="#{cajaController.intTipoPersonaC==applicationScope.Constante.PARAM_T_TIPOPERSONA_JURIDICA}" 
				        		value="RUC"/>
	                 	</f:facet>
			        	<h:outputText
			        		value="#{item.strIngCajaNroDocumento}"/>
			      	</rich:column>
					<rich:column width="120" style="text-align: center;">
						<f:facet name="header">
	                    	<h:outputText value="Seleccionar"/>
	                 	</f:facet>
			        	<a4j:commandLink
			    			value="Seleccionar"
							actionListener="#{cajaController.seleccionarSocio}"							
							reRender="panelPersonaC,panelDocumentoC,panelGestorC,panelMensajeC,panelBotonesC,opCuentaSocioC,cboTipoDocumentoC,opDatosPersonaSelect,cboCuentaSocioC,msgErrorMontoPllaEfect"
							oncomplete="if(#{cajaController.blnSocioSeleccionadoOk}){Richfaces.hideModalPanel('pBuscarPersonaCaja')}">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			      	</rich:column>
			      	
	            </rich:dataTable>
	    	</h:panelGroup>
			
	   	</h:panelGroup>
	</h:form>