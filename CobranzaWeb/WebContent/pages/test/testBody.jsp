<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<script language="JavaScript" src="/tumi/js/main.js"  type="text/javascript"></script>
	
    <h:form id="frmPrincipal">
				 
  	 	<h:outputText value="#{testController.dto.intEstado}" styleClass="msgError"/>
  	 	<br/>
	    <h:outputText value="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" styleClass="msgError"/>
   </h:form>
   