<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
	
	<rich:modalPanel id="mpTercerosPrestamo" width="550" height="260"
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
               		<rich:componentControl for="mpTercerosPrestamo" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet> 
        
        <h:form id="frmTercerosPrestamo">
	         <h:panelGroup id="pgTercerosPrestamo" layout="block">
	             <h:panelGroup layout="block" style="width:500px; overflow-x:auto !important; margin:0 auto">
	                
	                <rich:dataTable id="tblTercerosPrestamo" 
	                             var="item" value="#{solicitudPrestamoController.listTerceros}" styleClass="dataTable1"
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
	                         
	                         <rich:columns  value="#{solicitudPrestamoController.listColumnCpto == null ? '':solicitudPrestamoController.listColumnCpto}" var="columns" index="ind">
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
								 <h:outputText value="#{item.bdMontoTotal}">
								 	<f:convertNumber groupingUsed="true" minFractionDigits="2" />
								 </h:outputText>
	                         </rich:column>
	                         
							<f:facet name="footer">   
					        	<rich:datascroller for="tblTercerosPrestamo" maxPages="10"/>   
					        </f:facet>
	                </rich:dataTable>
	             </h:panelGroup>
	         </h:panelGroup>
	     </h:form>
	</rich:modalPanel>