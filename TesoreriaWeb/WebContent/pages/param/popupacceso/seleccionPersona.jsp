<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

	
	<h:form id="frmSeleccionPersona">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="100">
	    			<h:selectOneMenu
	    				style="width: 100px;"
	    				value="#{accesoController.intTipoFiltroPersona}">
	    				<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE}" itemLabel="Nombres"/>
	    				<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_OPCIONPERSONABUSQ_DNI}" 	itemLabel="DNI"/>
	    			</h:selectOneMenu>
	    		</rich:column>
	    		<rich:column width="250">
	    			<h:inputText value="#{accesoController.strTextoFiltroPersona}" size="48"/>
	    		</rich:column>
	    		<rich:column width="100">
	    			<a4j:commandButton styleClass="btnEstilos"
				    	action="#{accesoController.buscarPersona}"	                		
				        value="Buscar"
				        reRender="divTblPersona"
				        style="width:90px">				    		                    	
			   		</a4j:commandButton>
	    		</rich:column>
	    		<rich:column width="60" style="text-align: right">
	    			<h:outputText value="Orden : "/>
	    		</rich:column>
	    		<rich:column width="20">
	    			<h:inputText value="#{accesoController.intOrden}" size="5" onkeypress="return soloNumerosNaturales(event)"/>
	    		</rich:column>
	    		
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblPersona">
	    		<rich:extendedDataTable id="tblPersona" 
	    			enableContextMenu="false" style="margin:0 auto"
	                var="item" 
	                value="#{accesoController.listaPersona}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="600px" 
			  		height="165px">
			                    
			        <rich:column width="30px">
			        	<h:outputText value="#{rowKey + 1}"></h:outputText>
			    	</rich:column>
			        <rich:column width="350">
	                	<f:facet name="header">
	                    	<h:outputText value="Nombre Completo"></h:outputText>
	               		</f:facet>
						<h:outputText value="#{item.natural.strNombres} "/>
						<h:outputText value="#{item.natural.strApellidoPaterno} "/>
						<h:outputText value="#{item.natural.strApellidoMaterno}"/>
			        </rich:column> 
			       	<rich:column width="150">
	                	<f:facet name="header">
	                    	<h:outputText value="Documento"></h:outputText>
	                 	</f:facet>
						<h:outputText value="#{item.documento.strNumeroIdentidad}"/>
			   		</rich:column>
			        <rich:column width="70">
	                	<f:facet name="header">
	                    	<h:outputText value="Opción"></h:outputText>
	                   	</f:facet>
						<a4j:commandLink
							value="Seleccionar"
				           	actionListener="#{accesoController.seleccionarPersona}"
							reRender="panelListaAccesoDetalleResCuenta,panelListaAccesoDetalleResFondo"
							oncomplete="Richfaces.hideModalPanel('pSeleccionPersona')">
							<f:attribute name="item" value="#{item}"/>							
						</a4j:commandLink>
			     	</rich:column>
			        
				   	<f:facet name="footer">
				   		<h:panelGroup layout="block">
					   		<rich:datascroller for="tblPersona" maxPages="10"/>
				   		</h:panelGroup>  
			       </f:facet>
	  			</rich:extendedDataTable>
	   		</h:panelGroup>
	  	</h:panelGroup>
	</h:form>