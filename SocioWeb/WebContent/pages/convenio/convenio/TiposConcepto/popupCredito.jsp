<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         -->
<!-- Autor     : Christian De los Ríos    -->
<!-- Modulo    : Popup Firmantes          -->
<!-- Prototipo : FIRMANTES 	  			  -->
<!-- Fecha     : 11/06/2012               -->

	<script type="text/javascript">
		
	</script>
	
	<h:form id="frmCredito">
		<rich:dataTable value="#{convenioController.listaCreditoComp}" 
			width="800px" rowKeyVar="rowKey" var="item">
				<rich:column width="15px">
					<h:selectBooleanCheckbox value="#{item.chkCredito}"/>
				</rich:column>
				<rich:column width="15px">
					<div align="center"><h:outputText value="#{rowKey+1}"/></div>
	            </rich:column>
                <rich:column width="200px">
                   <f:facet name="header">
                       <h:outputText value="Tipo de Crédito"/>
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
                                     itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                     property="#{item.credito.id.intParaTipoCreditoCod}"/>
                </rich:column>
                <rich:column>
                   <f:facet name="header">
                       <h:outputText value="Según Empresa"/>
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCREDITOEMPRESA}" 
                                     itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                     property="#{item.credito.intParaTipoCreditoEmpresa}"/>
                </rich:column>
                <rich:column>
                    <f:facet name="header">
                    	<h:outputText value="Monto Min."/>
                    </f:facet>
					<h:outputText value="#{item.credito.bdMontoMinimo}"/>
                </rich:column>
                <rich:column>
                    <f:facet name="header">
                    	<h:outputText value="% Min."/>
                    </f:facet>
					<h:outputText value="#{item.credito.bdPorcMinimo}"/>
                </rich:column>
                <rich:column>
                    <f:facet name="header">
                    	<h:outputText value="Monto Max."/>
                    </f:facet>
					<h:outputText value="#{item.credito.bdMontoMaximo}"/>
                </rich:column>
                <rich:column>
                    <f:facet name="header">
                    	<h:outputText value="% Max."/>
                    </f:facet>
					<h:outputText value="#{item.credito.bdPorcMaximo}"/>
                </rich:column>
		</rich:dataTable>
		<br/>
		
		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos" actionListener="#{convenioController.getListaCreditos}" reRender="pgListCredito"
		oncomplete="Richfaces.hideModalPanel('panelCredito')"/>
	</h:form>