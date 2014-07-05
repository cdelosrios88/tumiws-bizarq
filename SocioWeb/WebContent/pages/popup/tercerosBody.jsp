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

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	
	<rich:modalPanel id="mpTerceros" width="550" height="260"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Terceros "></h:outputText>
                <h:outputText value="(Cooperativas, Bancos, Cajas, Mutuales, Financieras)" style="font-style:italic"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpTerceros" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <h:form id="frmTerceros">
	         <h:panelGroup id="pgTerceros" layout="block">
	             <h:panelGroup layout="block" style="width:500px; overflow-x:auto !important; margin:0 auto">
	                
	                <rich:dataTable id="tblTerceros" 
	                             var="item" value="#{tercerosController.listTerceros}" styleClass="dataTable1"
						  		 rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto">
	                                
				  		 	<rich:column width="29px">
				  		 		<f:facet name="header">
	                                 <h:outputText value="Nro."></h:outputText>
	                             </f:facet>
	                        	<h:outputText value="#{rowKey + 1}"></h:outputText>
	                         </rich:column>
	                         
	                         <rich:column>
	                             <f:facet name="header">
	                                 <h:outputText value="Periodo"></h:outputText>
	                             </f:facet>
								<h:outputText value="#{item.id.intPeriodo}"></h:outputText>
	                         </rich:column>
	                         <rich:column>
	                             <f:facet name="header">
	                                 <h:outputText value="Mes"></h:outputText>
	                             </f:facet>
								<h:outputText value="#{item.strMes}"></h:outputText>
	                         </rich:column>
	                         
	                         <rich:columns value="#{tercerosController.listColumnCpto}" var="columns" index="ind">
	                             <f:facet name="header">
	                                 <h:outputText value="#{columns.strCpto}">
	                                 </h:outputText>
	                             </f:facet>
								 <h:outputText value="#{item.lsMontos[ind]}">
								 	<f:convertNumber groupingUsed="true" minFractionDigits="2" />
								 </h:outputText>
	                         </rich:columns>
	                         <rich:column>
	                             <f:facet name="header">
	                                 <h:outputText value="Total" style="font-weight:bold"/>
	                             </f:facet>
								 <h:outputText value="#{item.intMonto}">
								 	<f:convertNumber groupingUsed="true" minFractionDigits="2" />
								 </h:outputText>
	                         </rich:column>
	                         
							<f:facet name="footer">   
					        	<rich:datascroller for="tblTerceros" maxPages="10"/>   
					        </f:facet>
	                </rich:dataTable>
	                
	             </h:panelGroup>
	             <!-- 
	             <h:panelGrid columns="2" styleClass="tableCellBorder3" style="margin:0 auto">
	             	<rich:column>
	             		<a4j:commandButton value="Cerrar" styleClass="btnEstilos"/>
	             	</rich:column>
	             </h:panelGrid>-->
	         </h:panelGroup>
	     </h:form>
	</rich:modalPanel>