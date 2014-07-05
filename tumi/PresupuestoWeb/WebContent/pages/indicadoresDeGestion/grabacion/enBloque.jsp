<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : INDICADORES DE GESTION - PANEL EN BLOQUE -->
<!-- FECHA     : 10.10.2013               -->

	<h:panelGrid>
		<rich:columnGroup>
			<rich:column width="1200px" style="text-align: left">
				<table>
					<tr>
						<td width="70px" align="left">
							<h:outputText value="Año Base:"/>
						</td>
						<td width="110px" align="left">
							<h:selectOneMenu style="width: 100px;"
								onchange="getAnioProyectado(#{applicationScope.Constante.ONCHANGE_VALUE})"
								value="#{indicadoresDeGestionController.intAnioBase}"> 
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<f:selectItems id="lstYears" value="#{indicadoresDeGestionController.listYears}"/>
							</h:selectOneMenu>
						</td>
						<td width="110px" align="left">
							<h:outputText value="Año Proyectado:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText style="text-align: center; width:50px" id="txtAnioProyectado"
								value="#{indicadoresDeGestionController.intAnioProyectado}"
								disabled="#{indicadoresDeGestionController.blnDisabledAnioProy}"/>						
						</td>
						<td width="60px" align="left">
							<h:outputText value="Meses:"/>
						</td>
						<td width="50px" align="left">
							<h:outputText value="Desde"/>
						</td>
						<td width="100px" align="left">
							<h:selectOneMenu id="idMesDesde" value="#{indicadoresDeGestionController.intMesDesde}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
						</td>
						<td width="45px" align="left">
							<h:outputText value="Hasta"/>
						</td>
						<td width="100px" align="left">
							<h:selectOneMenu id="idMesHasta" value="#{indicadoresDeGestionController.intMesHasta}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
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
						<td width="100px" align="left">
							<h:outputText value="% Crecimiento:"/>
						</td>
						<td width="80px" align="left">
							<h:inputText id="bdPorcentajeCrecimiento" value="#{indicadoresDeGestionController.bdPorcentajeCrecimiento}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)"/>
						</td>
						<td width="800px" align="left">
							<h:outputText value="%"/>
							<rich:spacer width="15px"/>
							<h:outputText value="Este porcentaje es aplicado al monto de los indicadores existentes del año base seleccionado"/>
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
						<td width="110px" align="left">
							<h:outputText value="Tipo de Indicador:"/>
						</td>
						<td width="90px" align="left">
							<h:selectOneRadio value="#{indicadoresDeGestionController.rbTipoIndicador}" onclick="selectRbTipoInicador(this.value)">
	          	 				<f:selectItem id="tipIndItem1" itemLabel="Todos" itemValue="1"/>
	          	 				<f:selectItem id="tipIndItem2" itemValue="2"/>
	          	 			</h:selectOneRadio>
	          	 		</td>
	          	 		<td width="120px" align="left">
	          	 			<h:selectOneMenu id="cboTipIndGrb" value="#{indicadoresDeGestionController.intTipoIndicadorGrabacion}" disabled="#{indicadoresDeGestionController.blnDisabledTipoIndicador}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOINDICADOR}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
									tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
							</h:selectOneMenu>
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
						<td width="50px" align="left">
							<h:outputText value="Sucursal:"/>
						</td>
						<td width="90px" align="left">
							<h:selectOneRadio value="#{indicadoresDeGestionController.rbSucursal}" onclick="selectRbSucursal(this.value)">
	          	 				<f:selectItem id="sucuItem1" itemLabel="Todos" itemValue="1"/>
	          	 				<f:selectItem id="sucuItem2" itemValue="2"/>
	          	 			</h:selectOneRadio>
	          	 		</td>
						
						<td width="150px" align="left">
		          	 		<h:selectOneMenu id="cboSucPanelEnBloque" style="width: 140px;"
								onchange="getSubSucursalPanelEnBloque(#{applicationScope.Constante.ONCHANGE_VALUE})"
								value="#{indicadoresDeGestionController.intIdSucursalGrabacion}"
								disabled="#{indicadoresDeGestionController.blnDisabledSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSucursal}"
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
							</h:selectOneMenu>
						</td>
						
						<td width="120px" align="left">
							<h:selectOneMenu id="cboSubSucPanelEnBloque" style="width: 110px;"
					            value="#{indicadoresDeGestionController.intIdSubSucursalGrabacion}"
					            disabled="#{indicadoresDeGestionController.blnDisabledSubSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{indicadoresDeGestionController.listJuridicaSubsucursalGrb}"
									itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
							</h:selectOneMenu>
						</td>
					</tr>
				</table>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>