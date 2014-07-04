<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		 -->
	<!-- Autor     : Fredy Ramirez C.      			 -->
	<!-- Modulo    : Cobranza                 		 -->
	<!-- Prototipo : PROTOTIPO ALERTA ENVIO PLANILLA -->
	<!-- Fecha     : 14/01/2012               		 -->
    <h:form id="frmAlertaEnvioPlanilla">
     	<h:panelGroup layout="block" style="background-color:#DEEBF5;width:421px; border: 1">
             
             <h:panelGrid  columns="1">
		         <rich:column style="width:300px; border: none">
	         		<h:outputText  value="#{cuentaCteController.strMessageAlertEnvioPlanilla}"  > </h:outputText>
	         	</rich:column>
	       </h:panelGrid>
	       
         	 <h:panelGrid  columns="1">
		     	<rich:column style="width:321px; border: none">
		         	<h:inputText  value="#{cuentaCteController.strPreguntaAlertaEnvioPlanilla}" size="80" disabled="true"> </h:inputText>        
		     	</rich:column>
	        </h:panelGrid>
         	
             <h:panelGrid columns="4" style="width:321px; border: none">
                 <rich:column style="width:100px; border: none">
                 </rich:column>
             	 <rich:column style="width:50px; border: none">
             		<a4j:commandButton value="SI" styleClass="btnEstilos" 
             						oncomplete="Richfaces.hideModalPanel('mpAlertaEnvioPlanilla')"
             						actionListener="#{cuentaCteController.cambiarEnvioPlanilla}"
             						reRender="dataListEntidadPrestamo,entidadOrigen,divFormCambioEntidad"
             						rendered="#{cuentaCteController.habilitarBotonAlertEnvioPlanilla}">
             		   <f:attribute name="item" value="S"/>				
             		</a4j:commandButton>
             	 </rich:column>
             	 <rich:column>
             	    <a4j:commandButton value="NO" styleClass="btnEstilos" 
             						oncomplete="Richfaces.hideModalPanel('mpAlertaEnvioPlanilla')"
             						actionListener="#{cuentaCteController.cambiarEnvioPlanilla}"
             						reRender="dataListEntidadPrestamo,entidadOrigen,divFormCambioEntidad"
             						rendered="#{cuentaCteController.habilitarBotonAlertEnvioPlanilla}">
             		   <f:attribute name="item" value="N"/>				  
             		</a4j:commandButton>			
             	 </rich:column>
             	 <rich:column>
             	 <a4j:commandButton value="Aceptar" styleClass="btnEstilos" 
             						oncomplete="Richfaces.hideModalPanel('mpAlertaEnvioPlanilla')"
             						actionListener="#{cuentaCteController.cambiarEnvioPlanilla}"
             						reRender="dataListEntidadPrestamo,entidadOrigen,divFormCambioEntidad"
             						rendered="#{!cuentaCteController.habilitarBotonAlertEnvioPlanilla}">
             		   <f:attribute name="item" value="N"/>				  
             	 </a4j:commandButton>			
             	</rich:column>
             </h:panelGrid>
         </h:panelGroup>
     </h:form>