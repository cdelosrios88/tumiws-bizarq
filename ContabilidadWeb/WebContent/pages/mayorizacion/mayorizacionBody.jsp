<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Cod. Req  : REQ14-004				-->
	<!-- Empresa   : Cooperativa Tumi		-->
	<!-- Autor     : Christian De los Ríos  -->
	<!-- Modulo    : Contabilidad         	-->
	<!-- Prototipo : Mayorizacion	 		-->
	<!-- Fecha     : 14/09/2014             -->


<rich:modalPanel id="pAlertaRegistro" width="400" height="170"
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
	        		<h:outputText value="¿Está seguro que desea eliminar el siguiente registro? Si elimina el registro, 
	        		también se eliminaran todos los registros correspondientes a los meses consecutivos del registro
	        		a eliminar."/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="2">
	    		<rich:column width="150">
	    		</rich:column>
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{mayorizacionController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelTablaContabilidad,panelMensaje"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>


<h:form id="frmMayorizacion">
   	<h:panelGroup id="divPlanCuentas" layout="block" style="padding:15px;border:1px solid #B3B3B3;">
   		
   		<h:panelGrid style="margin:0 auto; margin-bottom:15px">
    		<rich:columnGroup>
    			<rich:column>
    				<h:outputText value="MAYORIZACIÓN" style="font-weight:bold; font-size:14px"></h:outputText>
    			</rich:column>
    		</rich:columnGroup>
    	</h:panelGrid>
    	
    	<h:panelGrid columns="7">
        	<rich:column width="70" style="text-align: center;">
         		<h:outputText value="Periodo : "/>
         	</rich:column>
         	<rich:column width="150">
               	<h:selectOneMenu
					style="width: 150px;"
					value="#{mayorizacionController.libroMayorFiltro.id.intContMesMayor}">
					<f:selectItem itemValue="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO_TODOS}" itemLabel="Todos los meses"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="70">
               	<h:selectOneMenu
					style="width: 70px;"
					value="#{mayorizacionController.libroMayorFiltro.id.intContPeriodoMayor}">
					<tumih:selectItems var="sel"
						value="#{mayorizacionController.listYears}" 
						itemValue="#{sel.value}" 
						itemLabel="#{sel.label}"/>
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="80" style="text-align: center;">
         		<h:outputText value="Estado : "/>
         	</rich:column>
         	<rich:column width="100">
               	<h:selectOneMenu
					style="width: 100px;"
					value="#{mayorizacionController.libroMayorFiltro.intParaEstadoCierreCod}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOMAYORIZACION}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>	
				</h:selectOneMenu>
            </rich:column>
            <rich:column width="50">
            </rich:column>
         	<rich:column width="50" style="text-align: right;">
               	<a4j:commandButton styleClass="btnEstilos"
               		value="Buscar" reRender="panelTablaContabilidad,panelMensaje"
                   	action="#{mayorizacionController.buscar}" style="width:70px"/>
            </rich:column>
		</h:panelGrid>
		
		<rich:spacer height="12px"/>           
                
        <h:panelGrid id="panelTablaContabilidad">
	       	<rich:extendedDataTable id="tblContabilidad" 
	         		enableContextMenu="false" 
	          		sortMode="single" 
                    var="item" 
                    value="#{mayorizacionController.listaLibroMayor}"  
					rowKeyVar="rowKey" 
					rows="5" 
					width="940px" 
					height="225px" 
					align="center">
                                
					<rich:column width="30px" style="text-align: center">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                  	<rich:column width="200px" style="text-align: center">
                    	<f:facet name="header">
                        	<h:outputText value="Código"/>
                      	</f:facet>
						<h:outputText value="#{item.id.intContMesMayor}"/>
                	</rich:column>
                    <rich:column width="250" style="text-align: center">
                    	<f:facet name="header">
                      		<h:outputText value="Periodo"/>                      		
                      	</f:facet>
						<h:outputText value="#{item.id.intContPeriodoMayor}"/>
                  	</rich:column>
                  	<rich:column width="170" style="text-align: center">
                   		<f:facet name="header">
                        	<h:outputText value="Estado Cierre"/>
                        </f:facet>
						<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.intParaEstadoCierreCod}"/>
                  	</rich:column>
                   	<rich:column width="200" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value="Estado Mayorizacion"/>
                     	</f:facet>
                     	<h:panelGroup rendered="#{item.esProcesado}">
                     		<h:outputText value="Procesado"/>
                     	</h:panelGroup>
                     	<h:panelGroup rendered="#{!item.esProcesado}">
                     		<h:outputText value="Por Procesar"/>
                     	</h:panelGroup>
                  	</rich:column>
                  	<rich:column width="180" style="text-align: center">
                  		<f:facet name="header">
                        	<h:outputText value=""/>
                     	</f:facet>
                     	<h:panelGroup rendered="#{item.esProcesado}">
                     		<a4j:commandButton styleClass="btnEstilos"
	               				value="Procesado" style="width:90px"
	               				disabled="true"/>
                     	</h:panelGroup>
                     	<h:panelGroup rendered="#{!item.esProcesado}">
                     		<a4j:commandButton styleClass="btnEstilos"
	               				value="Procesar" style="width:90px"
	               				disabled="#{item.intParaEstadoCierreCod==applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO}"
	               				reRender="panelTablaContabilidad,panelBotones,panelMensaje"
	               				actionListener="#{mayorizacionController.seleccionarProcesar}">
	               					<f:attribute name="item" value="#{item}"/>
	               			</a4j:commandButton>
                     	</h:panelGroup>                     	
                  	</rich:column>
                    <f:facet name="footer">
						<rich:datascroller for="tblContabilidad" maxPages="10"/>   
					</f:facet>
					<a4j:support event="onRowClick"
						actionListener="#{mayorizacionController.seleccionarRegistro}"
						oncomplete="if(#{item.esProcesado}&&#{mayorizacionController.seleccionaRegistro}){Richfaces.showModalPanel('pAlertaRegistro');onCompleteFunction()}">
                        	<f:attribute name="item" value="#{item}"/>
                   	</a4j:support>
                   	<a4j:jsFunction name="onCompleteFunction" reRender="frmAlertaRegistro" ajaxSingle="true"/>
                   	
            </rich:extendedDataTable>
            	
         </h:panelGrid>
			
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;width:100%"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{mayorizacionController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{mayorizacionController.mostrarMensajeExito}"/>
			<h:outputText value="#{mayorizacionController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{mayorizacionController.mostrarMensajeError}"/>	
		</h:panelGroup>	
		
		
    </h:panelGroup>
</h:form>