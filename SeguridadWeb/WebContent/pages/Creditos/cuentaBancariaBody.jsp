<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
    <h:form id="frmCtaBancaria">
    		<h:panelGrid columns="6" styleClass="tableCellBorder1" style="margin-left:-10px">
    			<rich:column>
    				<h:outputText value="Nombre Completo: "></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{ctaBancariaController.strNombCompleto}" size="50" disabled="true"></h:inputText>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Condición: "></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:selectOneMenu value="#{ctaBancariaController.intCondicion}" style="width: 100px" disabled="true">
    					<f:selectItem itemValue="1" itemLabel="Activo"/>
    				</h:selectOneMenu>
    			</rich:column>
    			<rich:column>
    				<h:outputText value="Rol: "></h:outputText>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{ctaBancariaController.strRol}" size="30" disabled="true"></h:inputText>
    			</rich:column>
    		</h:panelGrid>
    		
         	<h:panelGrid columns="3"> 
		        <a4j:commandButton value="Grabar" actionListener="#{ctaBancariaController.addCtaBancaria}" styleClass="btnEstilos"  
		        				   reRender="rpCuentaBancaria,pgCtaBancaria" oncomplete="Richfaces.hideModalPanel('mpCuentaBancaria')"/>												                 
		    	<a4j:commandButton value="Cancelar" styleClass="btnEstilos">
		    		<rich:componentControl for="mpCuentaBancaria" operation="hide" event="onclick"/>
		    	</a4j:commandButton>
		    </h:panelGrid>
		    <rich:spacer height="4px"></rich:spacer>
		    
            <rich:panel style="border:1px solid #17356f ;width: 835px; background-color:#DEEBF5">                       
	            <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
	             	
	             	<h:panelGrid columns="7" styleClass="tableCellBorder3" style="margin-left:-10px">
	             		<rich:columnGroup>
	             			<rich:column colspan="2">
		             			<h:outputText value="Nombre de Banco: "></h:outputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intIdBanco}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
		             				<f:selectItem itemValue="1" itemLabel="Banco de Crédito"/>
		             				<f:selectItem itemValue="2" itemLabel="Scotiabank"/>
		             				<f:selectItem itemValue="3" itemLabel="Banco HSBC"/>
		             				<f:selectItem itemValue="4" itemLabel="Banco Continental"/>
		             				<f:selectItem itemValue="5" itemLabel="Banco Interbak"/>
		             				<f:selectItem itemValue="6" itemLabel="Banco Financiero"/>
		             				<f:selectItem itemValue="7" itemLabel="Banco de la Nación"/>
		             			</h:selectOneMenu>
		             		</rich:column>
		             		<rich:column colspan="4"></rich:column>
	             		</rich:columnGroup>
	             		
	             		<rich:columnGroup>
	             			<rich:column colspan="2" style="vertical-align: top">
			             		<h:outputText value="Tipo de Cuenta"></h:outputText>
			             	</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intIdTipoCuenta}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTABANCARIA}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:outputText value="Tipo de Moneda: "></h:outputText>
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intIdMoneda}">
		             				<f:selectItems value="#{parametersController.cboTipoMoneda}"/>
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:outputText value="Tipo de fin: "></h:outputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectManyCheckbox layout="pageDirection" value="#{ctaBancariaController.beanCtaBancaria.strRazonCuenta}">
		             				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORAZONCUENTA}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectManyCheckbox>
		             		</rich:column>
	             		</rich:columnGroup>
	             		
	             		<rich:columnGroup>
	             			<rich:column colspan="2">
		             			<h:outputText value="Número de Cuenta: "></h:outputText>
		             		</rich:column>
		             		<rich:column colspan="3">
		             			<h:inputText value="#{ctaBancariaController.beanCtaBancaria.strNroCtaBancaria}" size="71"></h:inputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:outputText value="Estado de Cuenta: "></h:outputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intIdEstado}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>
		             		</rich:column>
	             		</rich:columnGroup>
	             		
	             		<rich:columnGroup>
	             			<rich:column style="vertical-align: top; padding-top: 6px">
		             			<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:outputText value="Observación: "></h:outputText>
		             		</rich:column>
		             		<rich:column colspan="5">
		             			<h:inputTextarea value="#{ctaBancariaController.beanCtaBancaria.strObservacion}" rows="3" cols="67"></h:inputTextarea>
		             		</rich:column>
	             		</rich:columnGroup>
	             	</h:panelGrid>
	             	
	            </rich:panel>
         	</rich:panel>           
   </h:form>