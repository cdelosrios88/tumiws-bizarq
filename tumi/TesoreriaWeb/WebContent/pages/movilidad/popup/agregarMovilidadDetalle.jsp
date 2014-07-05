<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
	<h:form id="frmAgregarMovDet">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="105">
         			<h:outputText value="Fecha : "/>
         		</rich:column>
         		<rich:column width="350">
					<rich:calendar datePattern="dd/MM/yyyy"  
						value="#{movilidadController.movilidadDetalleAgregar.dtFechaMovilidad}"  
						jointPoint="top-right" direction="right" inputSize="60" showApplyButton="true"/> 
                </rich:column>                
	    	</h:panelGrid>
			
			<h:panelGrid columns="5">
         		<rich:column  width="105">
         			<h:outputText value="Tipo de Movilidad : "/>
         		</rich:column>
         		<rich:column width="325">
					<h:selectOneMenu 
						style="width: 325px;"
						value="#{movilidadController.movilidadDetalleAgregar.intParaTipoMovilidad}">
						<tumih:selectItems var="sel" 
							cache="#{applicationScope.Constante.PARAM_T_PLANILLAMOVILIDAD}" 
							itemValue="#{sel.intIdDetalle}"
							itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
                </rich:column>
	    	</h:panelGrid>
	    	
			<h:panelGrid columns="5">
         		<rich:column  width="105">
         			<h:outputText value="Monto : "/>
         		</rich:column>
         		<rich:column width="325">
					<h:inputText size="60" value="#{movilidadController.movilidadDetalleAgregar.bdMonto}" 
						onkeypress="return soloNumerosDecimales(this)"/>
                </rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="105"  style="vertical-align: top">
         			<h:outputText value="Destino : "/>
         		</rich:column>
         		<rich:column width="325">
					<h:inputTextarea cols="61" rows="2" value="#{movilidadController.movilidadDetalleAgregar.strDestino}"/>
                </rich:column>                
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
         		<rich:column  width="105"  style="vertical-align: top">
         			<h:outputText value="Motivo : "/>
         		</rich:column>
         		<rich:column width="325">
					<h:inputTextarea cols="61" rows="3" value="#{movilidadController.movilidadDetalleAgregar.strMotivo}"/>
                </rich:column>                
	    	</h:panelGrid>
	    	
	    	
			<h:panelGroup rendered="#{movilidadController.mostrarMensajePopUp}">
	    		<h:outputText value="#{movilidadController.mensajePopUp}" 
					styleClass="msgError"
					style="font-weight:bold"/>
	    	</h:panelGroup>
	    	<h:panelGroup rendered="#{!movilidadController.mostrarMensajePopUp}">
	    		<rich:spacer height="20px"/>
	    	</h:panelGroup>
			
			<h:panelGrid columns="2">
				<rich:column width="170">
			    </rich:column>
			    <rich:column style="border:none" id="colBtnModificar">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{movilidadController.aceptarAgregarMovilidadDetalle}"
		        		oncomplete="if(#{movilidadController.mostrarMensajePopUp}){rendPopUp();}else{Richfaces.hideModalPanel('pAgregarMovilidadDetalle');rendTabla();}"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pAgregarMovilidadDetalle')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla" reRender="panelListaMovilidadDetalle" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp" reRender="frmAgregarMovDet" ajaxSingle="true"/>
	   	</h:panelGroup>
	</h:form>