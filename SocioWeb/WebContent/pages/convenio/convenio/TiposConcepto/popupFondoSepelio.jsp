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
	
	<h:form id="frmFondoSepelio">
		<rich:extendedDataTable id="edtAportacion" value="#{convenioController.listaFondoSepelioComp}" 
			width="660px" height="175px" rowKeyVar="rowKey" var="item" enableContextMenu="false">
				<rich:column width="45px">
					<h:selectBooleanCheckbox value="#{item.chkCaptacion}"/>
				</rich:column>
				<rich:column width="15px">
					<div align="center"><h:outputText value="#{rowKey+1}"/></div>
	            </rich:column>
                <rich:column width="250px">
                   <f:facet name="header">
                       <h:outputText value="Nombre de Aportación"/>
                   </f:facet>
                   <h:outputText value="#{item.captacion.strDescripcion}"/>
                </rich:column>
                <rich:column width="80px">
                   <f:facet name="header">
                       <h:outputText value="F. Inicio"/>
                   </f:facet>
                   <h:outputText value="#{item.captacion.strDtFechaIni}"/>
                </rich:column>
                <rich:column width="80px">
                   <f:facet name="header">
                       <h:outputText value="F. Fin"/>
                   </f:facet>
                   <h:outputText value="#{item.captacion.strDtFechaFin}"/>
                </rich:column>
                <rich:column>
                   <f:facet name="header">
                       <h:outputText value="Tipo de Valor"/>
                   </f:facet>
                   <div align="center"><h:outputText value="#{item.captacion.bdValorConfiguracion==null? '%': 'S/.'}"/></div>
                </rich:column>
                <rich:column width="80px">
                   <f:facet name="header">
                       <h:outputText value="Valor"/>
                   </f:facet>
                   <div align="center"><h:outputText value="#{item.captacion.bdValorConfiguracion==null? item.captacion.bdPorcConfiguracion: item.captacion.bdValorConfiguracion}"/></div>
                </rich:column>
                <f:facet name="footer">
              		<rich:datascroller for="edtAportacion" maxPages="5"/>   
                </f:facet>
		</rich:extendedDataTable>
		<br/>
		
		<a4j:commandButton id="btnSelectFondoSepelio" value="Seleccionar" styleClass="btnEstilos" actionListener="#{convenioController.getListaFondoSepelio}" reRender="pgListFondoSepelio"
		oncomplete="Richfaces.hideModalPanel('panelFondoSepelio')"/>
	</h:form>