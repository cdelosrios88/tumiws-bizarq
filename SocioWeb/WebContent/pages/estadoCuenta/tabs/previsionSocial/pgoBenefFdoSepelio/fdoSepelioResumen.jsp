<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PAGOS BENEFICIOS FDO.SEPELIO - RESUMEN) -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formTabPrevisionSocial" rendered="#{not empty estadoCuentaController.listaPrevSocialFdoSepelio}">
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="FONDO DE SEPELIO" /></b>
		</rich:column>
	</rich:columnGroup>
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<rich:dataTable value="#{estadoCuentaController.listaPrevSocialFdoSepelio}" 
							styleClass="dataTable1" id="dtPagoCtasResumenFdoSepelio"
							rowKeyVar="rowKey" rows="5" sortMode="single" style="margin:0 auto text-align: left"
							var="itemGrilla">
				<f:facet name="header">
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
						<rich:column width="70px" rowspan="2" style="text-align: center">
							<h:outputText value="Periodo" />
						</rich:column>
						<rich:column width="200px" colspan="2" style="text-align: center">
							<h:outputText value="Cuota" />
						</rich:column>
						<rich:column width="300px" colspan="3" style="text-align: center">
							<h:outputText value="Monto" />
						</rich:column>
						<rich:column width="150px" rowspan="2" style="text-align: center">
							<h:outputText value="Opciones" />
						</rich:column>
						<rich:column width="100px" breakBefore="true" style="text-align: center">
							<h:outputText value="Número" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Monto" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Provisionado" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Cancelado" />
						</rich:column>
						<rich:column width="100px" style="text-align: center">
							<h:outputText value="Pendiente" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column width="70px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strPeriodo}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.intNumeroCuotas}"/>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdMontoCuotas}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdProvisionado}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdCancelado}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				</rich:column>
				<rich:column width="100px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdPendiente}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				</rich:column>
				<rich:column width="150px" style="text-align: center">
					<a4j:commandLink id="lnkDetalle" value="Detalle" 
					actionListener="#{estadoCuentaController.obtenerPagoCuotasBenefFdoSepelioDetalle}" 
					oncomplete="Richfaces.showModalPanel('mpPgoCtasDetalleFdoSepelio')"
					reRender="mpPgoCtasDetalleFdoSepelio">
					<f:attribute name="itemGrilla" value="#{itemGrilla}"/>
					</a4j:commandLink>
				</rich:column>
				<f:facet name="footer">     
		        	<h:panelGroup layout="block">
				   		 <rich:datascroller for="dtPagoCtasResumenFdoSepelio" maxPages="10"/>
				   	</h:panelGroup>
			   	</f:facet>									
			</rich:dataTable>
		</rich:column>
	</rich:columnGroup>
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="Años Aportados = #{estadoCuentaController.intPgoCtaBeneficioFdoSepelio}" /></b>
		</rich:column>
	</rich:columnGroup>
</h:panelGrid>