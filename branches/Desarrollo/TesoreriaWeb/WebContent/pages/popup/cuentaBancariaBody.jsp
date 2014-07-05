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
    				<h:outputText value="Nombre Completo: "/>
    			</rich:column>
    			<rich:column>
    				<h:inputText value="#{perJuridicaController.perJuridica.intIdPersona} - #{perJuridicaController.perJuridica.juridica.strRazonSocial}" 
    					size="50" disabled="true"/>
    			</rich:column>
    		</h:panelGrid>
    		
         	<h:panelGrid columns="3"> 
		        <a4j:commandButton value="Grabar" 
		        	actionListener="#{ctaBancariaController.addCtaBancaria}" 
		        	disabled="#{!ctaBancariaController.habilitarEditar}"
		        	styleClass="btnEstilos"
		        	reRender="#{ctaBancariaController.pgListCtaBancaria}" 
		        	oncomplete="Richfaces.hideModalPanel('#{ctaBancariaController.strIdModalPanel}')"/>
		    	<a4j:commandButton value="Cancelar" styleClass="btnEstilos">
		    		<rich:componentControl for="mpCuentaBancaria" operation="hide" event="onclick"/>
		    	</a4j:commandButton>
		    </h:panelGrid>
		    
		    
            <rich:panel style="border:1px solid #17356f ;width: 635px; background-color:#DEEBF5">                       
	            <h:panelGroup style="border: 0px solid #17356f;">
	             	
	             <h:panelGrid columns="2">
	             	<rich:column>
	             		<h:panelGrid columns="2">
	             			<rich:column width="130">
		             			<h:outputText value="Nombre de Banco : "/>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu style="width: 150px;" 
		             				disabled="#{!ctaBancariaController.habilitarEditar}"
		             				value="#{ctaBancariaController.beanCtaBancaria.intBancoCod}">
		             				<tumih:selectItems var="sel" 
		             					cache="#{applicationScope.Constante.PARAM_T_BANCOS}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>
		             		</rich:column>
	             		</h:panelGrid>
	             		
	             		<h:panelGrid columns="2">
	             			<rich:column width="130">
			             		<h:outputText value="Tipo de Cuenta : "/>
			             	</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu style="width: 150px;"  
		             				disabled="#{!ctaBancariaController.habilitarEditar}"
		             				value="#{ctaBancariaController.beanCtaBancaria.intTipoCuentaCod}">
		             				<tumih:selectItems var="sel" 
							  			cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTABANCARIA}" 
							  			itemValue="#{sel.intIdDetalle}" 
							  			itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
	             		</h:panelGrid>
	             		
	             		<h:panelGrid columns="2">
	             			<rich:column width="130">
		             			<h:outputText value="Tipo de Moneda : "/>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu style="width: 150px;"  
		             				disabled="#{!ctaBancariaController.habilitarEditar}"
		             				value="#{ctaBancariaController.beanCtaBancaria.intMonedaCod}">
		             				<tumih:selectItems var="sel" 
							  			cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							  			itemValue="#{sel.intIdDetalle}" 
							  			itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
	             		</h:panelGrid>
	             		
	             		<h:panelGrid columns="2">
	             			<rich:column width="130">
		             			<h:outputText value="Estado de Cuenta :"/>
		             		</rich:column>
		             		<rich:column>
		             			<h:selectOneMenu style="width: 150px;"  
		             				disabled="#{!ctaBancariaController.habilitarEditar}"
		             				value="#{ctaBancariaController.beanCtaBancaria.intEstadoCod}">
		             				<tumih:selectItems var="sel" 
							  			cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							  			itemValue="#{sel.intIdDetalle}" 
							  			itemLabel="#{sel.strDescripcion}"/>
		             			</h:selectOneMenu>  
		             		</rich:column>
	             		</h:panelGrid>
	             		
            		
	             		
	             	</rich:column>
	             	
	             	<rich:column>
	             		<h:panelGrid columns="2">
		             		<rich:column width="100" style="text-align: center;">
		             			<h:outputText value="Tipo de Fin: "/>
		             		</rich:column>
		             		<rich:column>
		             			<rich:dataTable
		             				sortMode="single"
				                    var="item"
				                    value="#{ctaBancariaController.listaTablaFin}"  
									rowKeyVar="rowKey"
									rows="4"
									width="170px"
									align="center">
			             			<rich:column>
			             				<h:selectBooleanCheckbox value="#{item.checked}" disabled="#{!ctaBancariaController.habilitarEditar}"/>
			             			</rich:column>
			             			<rich:column>			             				
			             				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORAZONCUENTA}" 
											itemValue="intIdDetalle" itemLabel="strDescripcion" 
											property="#{item.intIdDetalle}"/>
			             			</rich:column>
		             			</rich:dataTable>
		             		</rich:column>
		             	</h:panelGrid>
	             	</rich:column>
	             	
	             </h:panelGrid>
             		
	         			<h:panelGrid columns="2">
		             		<rich:column width="130">
		             			<h:outputText value="Nro de Cuenta :"/>
		             		</rich:column>
		             		<rich:column>
		             			<h:inputText value="#{ctaBancariaController.beanCtaBancaria.strNroCuentaBancaria}" 
		             				size="85" disabled="#{!ctaBancariaController.habilitarEditar}"/>
		             		</rich:column>
		           		</h:panelGrid>
			            
			            <h:panelGrid columns="2">
		             		<rich:column width="130">
		             			<h:outputText value="Cod Interbancario :"/>
		             		</rich:column>
		             		<rich:column>
		             			<h:inputText value="#{ctaBancariaController.beanCtaBancaria.strCodigoInterbancario}"
		             				onkeydown="return validarNumDocIdentidad(this,event,20)"
		             				size="85" disabled="#{!ctaBancariaController.habilitarEditar}"/>
		             		</rich:column>
		           		</h:panelGrid>
		           		 		
		          		<h:panelGrid columns="2">
		           			<rich:column width="130">
		             			<h:outputText value="Observación :"/>
		             		</rich:column>
		             		<rich:column>
		             			<h:inputTextarea value="#{ctaBancariaController.beanCtaBancaria.strObservacion}" 
		             				rows="3" cols="85" disabled="#{!ctaBancariaController.habilitarEditar}"/>
		             		</rich:column>
		           		</h:panelGrid>	 
	            </h:panelGroup>
         	</rich:panel>           
   </h:form>