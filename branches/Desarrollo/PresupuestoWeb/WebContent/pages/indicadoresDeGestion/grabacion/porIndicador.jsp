<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : INDICADORES DE GESTION - PANEL POR INDICADOR -->			
<!-- FECHA     : 10.10.2013               -->

	<h:panelGrid>
		<rich:columnGroup>
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="60px" align="left">
							<h:outputText value="Año:"/>
						</td>
						<td width="80px" align="left">
							<h:selectOneMenu id="idAnioGrab" value="#{indicadoresDeGestionController.intAnioGrabacion}" disabled="#{indicadoresDeGestionController.blnDisabledAnio}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<f:selectItems id="lstYears" value="#{indicadoresDeGestionController.listYears}"/>
							</h:selectOneMenu>	
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
		<rich:spacer height="8px"/>	
		<rich:columnGroup>
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="100px" align="left">
							<h:outputText value="Tipo Indicador:"/>
						</td>
						<td width="150px" align="left">
							<h:selectOneMenu id="idTipoIndGrabacion" value="#{indicadoresDeGestionController.intTipoIndicadorGrabacion}" disabled="#{indicadoresDeGestionController.blnDisabledTipoIndicador}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINDICADOR}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
						</td>
						<td width="70px" align="left">
							<h:outputText value="Tipo Valor:"/>
						</td>
						<td width="100px" align="left">
							<h:selectOneMenu id="idTipoValorGrabacion" value="#{indicadoresDeGestionController.intTipoValorGrabacion}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVALOR}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
		<rich:spacer height="8px"/>	
		<rich:columnGroup>
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="60px" align="left">
							<h:outputText value="Sucursal:"/>
						</td>
						<td width="150px" align="left">
		          	 		<h:selectOneMenu id="cboSucPanelPorIndicador" style="width: 140px;"
								onchange="getSubSucursalPanelPorIndicador(#{applicationScope.Constante.ONCHANGE_VALUE})"
								value="#{indicadoresDeGestionController.intIdSucursalGrabacion}" disabled="#{indicadoresDeGestionController.blnDisabledSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSucursal}"
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
							</h:selectOneMenu>
						</td>
						<td width="100px" align="left">
							<h:outputText value="Sub-sucursal"></h:outputText>
						</td>
						<td width="150px" align="left">
							<h:selectOneMenu id="cboSubSucPanelPorIndicador" style="width: 110px;"
					            value="#{indicadoresDeGestionController.intIdSubSucursalGrabacion}" disabled="#{indicadoresDeGestionController.blnDisabledSubSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSubsucursalGrb}"
									itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
							</h:selectOneMenu>
						</td>					
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
		<rich:spacer height="8px"/>
		<rich:columnGroup>
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="80px" align="left">
							<h:outputText value="Enero:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyEnero" value="#{indicadoresDeGestionController.bdMontoProyEnero}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyEnero}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Febrero:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyFebrero" value="#{indicadoresDeGestionController.bdMontoProyFebrero}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyFebrero}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Marzo:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyMarzo" value="#{indicadoresDeGestionController.bdMontoProyMarzo}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyMarzo}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Abril:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyAbril" value="#{indicadoresDeGestionController.bdMontoProyAbril}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyAbril}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Mayo:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyMayo" value="#{indicadoresDeGestionController.bdMontoProyMayo}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyMayo}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Junio:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyJunio" value="#{indicadoresDeGestionController.bdMontoProyJunio}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyJunio}"/>
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
		<rich:spacer height="8px"/>	
		<rich:columnGroup>
			<rich:column width="1200px" style="text-align: left">
				<table>
					<tr>
						<td width="80px" align="left">
							<h:outputText value="Julio:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyJulio" value="#{indicadoresDeGestionController.bdMontoProyJulio}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyJulio}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Agosto:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyAgosto" value="#{indicadoresDeGestionController.bdMontoProyAgosto}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyAgosto}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Septiembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProySeptiembre" value="#{indicadoresDeGestionController.bdMontoProySeptiembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProySeptiembre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Octubre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyOctubre" value="#{indicadoresDeGestionController.bdMontoProyOctubre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyOctubre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Noviembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyNoviembre" value="#{indicadoresDeGestionController.bdMontoProyNoviembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyNoviembre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Diciembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyDiciembre" value="#{indicadoresDeGestionController.bdMontoProyDiciembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{indicadoresDeGestionController.blnDisabledBdMontoProyDiciembre}"/>
						</td>
					</tr>
				</table>
			</rich:column>		
		</rich:columnGroup>
	</h:panelGrid>


