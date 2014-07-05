<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmProveedorDetalle">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid>
             	<rich:dataTable
             		id="tablatipoprov"
             		var="item" 
             		rowKeyVar="rowKey" 
             		sortMode="single" 
             		width="340px" 
    				value="#{proveedorController.listaTablaTipoProveedor}" 
    				rows="8"
    				styleClass="dataTable1">
	                	<rich:column width="40px">
	                        <f:facet name="header">
	                            <h:outputText value="Seleccione"></h:outputText>
	                        </f:facet>
	                        <h:selectBooleanCheckbox value="#{item.checked}"/>
	                    </rich:column>
	                    <rich:column width="300">
	                    	<f:facet name="header">
	                            <h:outputText value="Tipo de Proveedor"></h:outputText>
	                        </f:facet>
	                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOPROVEEDOR}" 
								itemValue="intIdDetalle" itemLabel="strDescripcion" 
								property="#{item.intIdDetalle}"/>
	                    </rich:column>
                    <f:facet name="footer">
	                    <rich:datascroller for="tablatipoprov" maxPages="5"/>   
	                </f:facet> 
                </rich:dataTable>
             </h:panelGrid>
             
             <h:panelGrid columns="2">
				<rich:column width="70">
			    </rich:column>
			    <rich:column style="border:none">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{proveedorController.aceptarAgregarProveedorDetalle}"
			    		reRender="panelTipoProveedor"
		        		oncomplete="Richfaces.hideModalPanel('pAgregarProveedorDetalle')"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pAgregarProveedorDetalle')"/>
			 	</rich:column>
			</h:panelGrid>
             
	   	</h:panelGroup>
	</h:form>