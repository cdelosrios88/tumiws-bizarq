<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PREVISION SOCIAL - BENEFICIOS OTORGADOS -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formTabPrevisionSocial" rendered="#{not empty estadoCuentaController.listaPrevSocialFdoSepelio}">
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="BENEFICIOS OTORGADOS" /></b>
		</rich:column>
	</rich:columnGroup>
	<rich:columnGroup>
		<rich:column width="1100px" style="text-align: left">
			<rich:extendedDataTable id="edtBeneficiosOtorgados" enableContextMenu="false" sortMode="single" 
				var="itemGrilla" value="#{estadoCuentaController.listaExpedientePrevisionComp}" 
                rendered="#{not empty estadoCuentaController.listaExpedientePrevisionComp}" 
                rowKeyVar="rowKey" width="1050px" height="210px">
				<f:facet name="header">
					<rich:columnGroup>
						<rich:column width="80px" rowspan="2" style="text-align: center">
							<h:outputText value="Fecha" />
						</rich:column>
						<rich:column width="60px" rowspan="2" style="text-align: center">
							<h:outputText value="Nro. Solicitud" />
						</rich:column>
						<rich:column width="160px" rowspan="2" style="text-align: center">
							<h:outputText value="Tipo de Beneficio" />
						</rich:column>
						<rich:column width="70px" rowspan="2" style="text-align: center">
							<h:outputText value="Monto" />
						</rich:column>
						<rich:column width="350px" colspan="2" style="text-align: center">
							<h:outputText value="Datos del Fallecido" />
						</rich:column>
						<rich:column width="350px" colspan="2" style="text-align: center">
							<h:outputText value="Datos del Beneficiario" />
						</rich:column>
						<rich:column width="70px" breakBefore="true" style="text-align: center">
							<h:outputText value="Tipo" />
						</rich:column>
						<rich:column width="280px" style="text-align: center">
							<h:outputText value="Nombre y Apellido" />
						</rich:column>
						<rich:column width="70px" style="text-align: center">
							<h:outputText value="Tipo" />
						</rich:column>
						<rich:column width="280px" style="text-align: center">
							<h:outputText value="Nombre y Apellido" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column width="80px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strFechaSolicitud}"/>
				</rich:column>
				<rich:column width="60px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNroSolicitud}"/>
				</rich:column>
				<rich:column width="160px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strDescripcionTipoBeneficio}"/>
				</rich:column>
				<rich:column width="70px" style="text-align: center">
					<h:outputText value="#{itemGrilla.bdMontoNeto}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
				</rich:column>
				<rich:column width="70px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intTipoVinculoFallecido}"/>
				</rich:column>
				<rich:column width="280px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNomApeFallecido}"/>
				</rich:column>
				<rich:column width="70px" style="text-align: center">
					<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
		   		     	itemValue="intIdDetalle" itemLabel="strDescripcion" 
					    property="#{itemGrilla.intTipoVinculoBeneficiario}"/>
				</rich:column>
				<rich:column width="260px" style="text-align: center">
					<h:outputText value="#{itemGrilla.strNomApeBeneficiario}"/>
				</rich:column>
			</rich:extendedDataTable>
		</rich:column>
	</rich:columnGroup>        
	<rich:spacer height="15"></rich:spacer>
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="PAGOS DE CUOTAS DEL BENEFICIO" /></b>
		</rich:column>
	</rich:columnGroup>	
</h:panelGrid>
