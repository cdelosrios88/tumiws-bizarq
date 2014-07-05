<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PAGOS BENEFICIOS FDO.RETIRO - DETALLE -->			
<!-- FECHA     : 30.09.2013               -->

<rich:modalPanel id="mpPgoCtasDetalleFdoRetiro" width="1100" height="300" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="PAGO DE CUOTAS FDO. RETIRO - DETALLE"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mpPgoCtasDetalleFdoRetiro" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:dataTable value="#{estadoCuentaController.listaPrevSocialFdoRetiroDetalle}" 
					styleClass="dataTable1" id="dtPagoCtasDetalleFdoRetiro"
					rowKeyVar="rowKey" rows="6" sortMode="single" style="margin:0 auto text-align: left"
					var="itemGrilla">
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">				
				<h:outputText value="AÑO" />
			</f:facet>
			<b><h:outputText value="#{itemGrilla.strFechaMovimiento}"/></b>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="CONCEPTO" />
			</f:facet>
			<h:outputText value="#{itemGrilla.strDescTipoConceptoGral}"/>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="ENE" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoEne}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="FEB" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoFeb}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="MAR" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoMar}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="ABR" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoAbr}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="MAY" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoMay}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="JUN" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoJun}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="JUL" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoJul}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="AGO" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoAgo}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="SET" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoSet}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="OCT" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoOct}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="NOV" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoNov}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="DIC" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoMovimientoDic}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="TOTAL" />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoTotal}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<f:facet name="header">	
				<h:outputText value="ACUM." />
			</f:facet>
			<h:outputText value="#{itemGrilla.bdMontoAcumulado}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>		
	<f:facet name="footer">     
        	<h:panelGroup layout="block">
		   		 <rich:datascroller for="dtPagoCtasDetalleFdoRetiro" maxPages="10"/>
		   	</h:panelGroup>
	   	</f:facet>
	</rich:dataTable>
</rich:modalPanel>