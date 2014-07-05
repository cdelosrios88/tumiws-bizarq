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
	
	<h:form id="frmFirmante">
		<rich:dataTable value="#{convenioController.listaRepresentanteLegal}" 
			width="800px" rowKeyVar="rowKey" var="item">
				<rich:column width="15px">
					<h:selectBooleanCheckbox value="#{item.chkFirmante}"/>
				</rich:column>
				<rich:column width="15px">
					<div align="center"><h:outputText value="#{rowKey+1}"/></div>
	            </rich:column>
                <rich:column width="300px">
                   <f:facet name="header">
                       <h:outputText value="Nombre Completo"/>
                   </f:facet>
                   <h:outputText value="#{item.firmante.persona.natural.strApellidoPaterno} #{item.firmante.persona.natural.strApellidoMaterno} #{item.firmante.persona.natural.strNombres}"/>
                </rich:column>
                <rich:column width="80px">
                   <f:facet name="header">
                       <h:outputText value="DNI"/>
                   </f:facet>
                   <h:outputText value="#{item.firmante.persona.documento.strNumeroIdentidad}"/>
                </rich:column>
                <rich:column width="150px">
                   <f:facet name="header">
                       <h:outputText value="Cargo"/>
                   </f:facet>
				<h:outputText value="#{item.firmante.persona.personaEmpresa.vinculo.intCargoVinculoCod}"/>
                </rich:column>
                <rich:column width="160px">
                   <f:facet name="header">
                       <h:outputText value="Empresa"/>
                   </f:facet>
                   <h:outputText value="#{item.firmante.persona.juridica.strRazonSocial}"/>
               </rich:column>
		</rich:dataTable>
		<br/>
		
		<a4j:commandButton value="Seleccionar" styleClass="btnEstilos" actionListener="#{convenioController.getFirmantes}" reRender="pgListFirmantes"
		oncomplete="Richfaces.hideModalPanel('panelFirmantes')"/>
	</h:form>