<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca        			-->
	<!-- Modulo    : Servicio                 		-->
	<!-- Prototipo : PROTOTIPO perfil				-->
	<!-- Fecha     : 26/06/2012               		-->

    <h:form id="frmCancelado">
     	<rich:panel style="background-color:#DEEBF5"> 
		    <script language=Javascript>
		       function isNumberKey(evt)
		       {
		          var charCode = (evt.which) ? evt.which : event.keyCode
		          if (charCode != 46 && charCode > 31 
		            && (charCode < 48 || charCode > 57))
		             return false;
		
		          return true;
		       }
		    </script>
			<h:panelGrid columns="2">
				<rich:column style="border:none" width="120">
					<h:outputText value="% Cancelado : "></h:outputText>
				</rich:column>
				<rich:column>
					<h:inputText size="25" value="#{autorizacionController.confServCancelado.bdPorcentajeCancelado}" onkeypress="return isNumberKey(event)"/>
					<h:outputText value=" %"/>
	            </rich:column>
             </h:panelGrid>
             
             <h:panelGrid columns="3">
				<rich:column style="border:none" width="120">
					<h:outputText value="Forma de Pago : "/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu
						style="width: 150px;"
						value="#{autorizacionController.confServCancelado.intParaModalidadPagoCod}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_FORMADEPAGO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Agregar" action="#{autorizacionController.agregarListaConfServCancelado}"
	             		reRender="colTablaCancelados"
	             		onclick="Richfaces.hideModalPanel('pBuscarCancelado')" 
	             		styleClass="btnEstilos"/>
				</rich:column>
             </h:panelGrid>
             
	
         </rich:panel>
     </h:form>