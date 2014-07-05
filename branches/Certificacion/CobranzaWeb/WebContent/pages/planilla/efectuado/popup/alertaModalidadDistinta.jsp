<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<h:panelGrid  id="panelMensaje">
	<rich:columnGroup>
	    <rich:column>
			<h:outputText value=" El socio a agregar tiene modalidad distinta.								  
								  Por favor verificar los datos del socio." 
							style="font-weight:bold; font-size:13px"/>						
		</rich:column>
	 </rich:columnGroup>
</h:panelGrid>

