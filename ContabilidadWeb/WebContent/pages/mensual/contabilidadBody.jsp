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
	<!-- Prototipo :  Cierre Mensual -Contabilidad 	-->			
	<!-- Fecha     :                	-->


<rich:modalPanel id="pAlertaRegistro" width="400" height="150"
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
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="150">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{cierreMensualContabilidadController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>


<h:form id="frmContabilidad">
   	<h:panelGroup id="divPlanCuentas" layout="block" style="padding:15px;border:1px solid #B3B3B3;">
        	
        	<h:panelGrid style="margin:0 auto; margin-bottom:15px">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:outputText value="CIERRE MENSUAL DE CONTABILIDAD" style="font-weight:bold; font-size:14px"></h:outputText>
	    			</rich:column>
	    		</rich:columnGroup>
    		</h:panelGrid>
        	
        	<h:panelGrid columns="7">
         		<rich:column width="70" style="text-align: center;">
         			<h:outputText value="Periodo : "/>
         		</rich:column>
         		<rich:column width="90">
                	<h:selectOneMenu
						style="width: 90px;"
						value="#{cierreMensualContabilidadController.libroMayorFiltro.id.intContMesMayor}">
						<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO_TODOS}" itemLabel="Todos"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							propertySort="intOrden"/>
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="70">
                	<h:selectOneMenu
						style="width: 70px;"
						value="#{cierreMensualContabilidadController.libroMayorFiltro.id.intContPeriodoMayor}">
						<tumih:selectItems var="sel"
							value="#{cierreMensualContabilidadController.listaAnios}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="80" style="text-align: center;">
         			<h:outputText value="Estado : "/>
         		</rich:column>
         		<rich:column width="100">
                	<h:selectOneMenu
						style="width: 100px;"
						value="#{cierreMensualContabilidadController.libroMayorFiltro.intParaEstadoCierreCod}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
					</h:selectOneMenu>
              	</rich:column>
              	<rich:column width="50">
              	</rich:column>
         		<rich:column width="50" style="text-align: right;">
                	<a4j:commandButton styleClass="btnEstilos"
                		value="Buscar" reRender="panelTablaContabilidad,panelMensaje"
                    	action="#{cierreMensualContabilidadController.buscar}" style="width:70px"/>
            	</rich:column>
            </h:panelGrid>
            
         	
            <rich:spacer height="12px"/>           
                
            <h:panelGrid id="panelTablaContabilidad">
	        	<rich:extendedDataTable id="tblContabilidad" 
	          		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{cierreMensualContabilidadController.listaLibroMayor}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="880px" 
					height="170px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="150px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
						<h:outputText value="#{item.id.intContMesMayor}"/>
                	</rich:column>
                    <rich:column width="150" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Periodo"/>                      		
                      	</f:facet>
						<h:outputText value="#{item.id.intContPeriodoMayor}"/>
                  	</rich:column>
                    <rich:column width="210" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Usuario"/>
                        </f:facet>
						<h:outputText value="#{item.naturalPersonaUsuario.strNombres} #{item.naturalPersonaUsuario.strApellidoPaterno} #{item.naturalPersonaUsuario.strApellidoMaterno}"/>
                  	</rich:column>
                  	<rich:column width="150" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado"/>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstadoCierreCod}"/>
                  	</rich:column>
                   	<rich:column width="210" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Fecha de Registro"/>
                     	</f:facet>
 						<h:outputText value="#{item.tsFechaRegistro}">
                            <f:convertDateTime pattern="dd-MM-yyyy HH:mm:ss" />
                       	</h:outputText>
                  	</rich:column>
                    <f:facet name="footer">
						<rich:datascroller for="tblContabilidad" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"  
						actionListener="#{cierreMensualContabilidadController.seleccionarRegistro}"
						reRender="frmAlertaRegistro,contPanelInferior,panelBotones"
						oncomplete="if(#{cierreMensualContabilidadController.mostrarBtnEliminar}){Richfaces.showModalPanel('pAlertaRegistro')}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
            	</rich:extendedDataTable>
            	
         	</h:panelGrid>
	          	                 
			<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para Modificar hacer click en el registro" style="color:#8ca0bd"/>
			</h:panelGrid>

				
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{cierreMensualContabilidadController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{cierreMensualContabilidadController.mostrarMensajeExito}"/>
			<h:outputText value="#{cierreMensualContabilidadController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{cierreMensualContabilidadController.mostrarMensajeError}"/>	
		</h:panelGroup>
				 		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" styleClass="btnEstilos" style="width:90px" 
					actionListener="#{cierreMensualContabilidadController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />                     
			    <a4j:commandButton value="Grabar" styleClass="btnEstilos" style="width:90px"
			    	action="#{cierreMensualContabilidadController.grabar}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaContabilidad"
			    	disabled="#{!cierreMensualContabilidadController.habilitarGrabar}"/>       												                 
			    <a4j:commandButton value="Cancelar" styleClass="btnEstilos"style="width:90px"
			    	actionListener="#{cierreMensualContabilidadController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>      
			</h:panelGrid>
		</h:panelGroup>	        	
		
		          	
		<rich:panel id="contPanelInferior" style="border:0px;">
			<rich:panel id="panelInferior" style="border:1px solid #17356f;" rendered="#{cierreMensualContabilidadController.mostrarPanelInferior}">		          	 	
		    
		    	<h:panelGrid columns="6">
			    	<rich:column width="100">
						<h:outputText value="Periodo : "/>
		        	</rich:column>
		        	<rich:column width="90">
                		<h:selectOneMenu
							style="width: 90px;"
							value="#{cierreMensualContabilidadController.libroMayorNuevo.id.intContMesMayor}"
							disabled="#{!cierreMensualContabilidadController.registrarNuevo || cierreMensualContabilidadController.deshabilitarNuevo}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
						</h:selectOneMenu>
              		</rich:column>
	              	<rich:column width="70">
	                	<h:selectOneMenu
							style="width: 70px;"
							value="#{cierreMensualContabilidadController.libroMayorNuevo.id.intContPeriodoMayor}"
							disabled="#{!cierreMensualContabilidadController.registrarNuevo || cierreMensualContabilidadController.deshabilitarNuevo}">
							<tumih:selectItems var="sel" 
								value="#{cierreMensualContabilidadController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
	              	</rich:column>
				</h:panelGrid>
				
				<rich:spacer height="8px"/>
				  
				<h:panelGrid columns="6" id="panelOperacionesLibro">
					<rich:column width="100">
						<h:outputText value="Operaciones : "/>
		        	</rich:column>
					<rich:column>
						<a4j:commandButton styleClass="btnEstilos"
                			value="Cerrar Operaciones"
                			action="#{cierreMensualContabilidadController.seleccionarCerrarOperaciones}"
                			disabled="#{!cierreMensualContabilidadController.habilitarCerrarOperaciones  || cierreMensualContabilidadController.deshabilitarNuevo}"
                			reRender="panelOperacionesLibro,panelBotones,panelMensaje"
                			style="width:160px"/>
					</rich:column>
					<rich:column style="width: 10px;">
					</rich:column>
					<rich:column>
						<h:panelGroup rendered="#{!cierreMensualContabilidadController.registrarNuevo}">
							<a4j:commandButton styleClass="btnEstilos"
                				value="Aperturar Operaciones"
                				action="#{cierreMensualContabilidadController.seleccionarAperturarOperaciones}"
                				disabled="#{!cierreMensualContabilidadController.habilitarAperturarOperaciones  || cierreMensualContabilidadController.deshabilitarNuevo}" 
                				reRender="panelOperacionesLibro,panelBotones,panelMensaje"
                				style="width:160px"/>
						</h:panelGroup>
					</rich:column>
				</h:panelGrid>
			</rich:panel>
		</rich:panel>
</h:panelGroup>				
</h:form>