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
    				<!--<h:inputText value="#{perJuridicaController.perJuridica.intIdPersona} - #{perJuridicaController.perJuridica.juridica.strRazonSocial}" size="50" disabled="true"/>-->
    				<h:inputText value="#{socioController.beanSocioComp.persona.natural.strApellidoPaterno} 
    									#{socioController.beanSocioComp.persona.natural.strApellidoMaterno} 
    									#{socioController.beanSocioComp.persona.natural.strNombres}" 
    					size="100" disabled="true"/>
    			</rich:column>
    		</h:panelGrid>
    		
         	<h:panelGrid columns="3"> 
		        <a4j:commandButton value="Grabar" actionListener="#{ctaBancariaController.addCtaBancaria}" styleClass="btnEstilos"  
		        				   reRender="#{ctaBancariaController.pgListCtaBancaria}" 
		        				   oncomplete="Richfaces.hideModalPanel('#{ctaBancariaController.strIdModalPanel}')"/>												                 
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
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intBancoCod}">
		             				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
		             		<rich:column colspan="4"></rich:column>
	             		</rich:columnGroup>
	             		
	             		<rich:columnGroup>
	             			<rich:column colspan="2" style="vertical-align: top">
			             		<h:outputText value="Tipo de Cuenta"></h:outputText>
			             	</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intTipoCuentaCod}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTABANCARIA}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:outputText value="Tipo de Moneda: "></h:outputText>
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intMonedaCod}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
		             		<rich:column style="vertical-align: top">
		             			<h:outputText value="Tipo de fin: "></h:outputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectManyCheckbox layout="pageDirection" value="#{ctaBancariaController.strRazonCuenta}">
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
		             			<h:inputText value="#{ctaBancariaController.beanCtaBancaria.strNroCuentaBancaria}" size="71"></h:inputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:outputText value="Estado de Cuenta: "></h:outputText>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu value="#{ctaBancariaController.beanCtaBancaria.intEstadoCod}">
		             				<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							  			itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
	             		</rich:columnGroup>
	             		
	             		<rich:columnGroup>
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