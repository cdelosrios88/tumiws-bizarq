<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : PRESUPUESTO - PANEL POR PARTIDA -->			
<!-- FECHA     : 10.10.2013               -->

	<h:panelGrid>
		<rich:columnGroup>
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="60px" align="left">
							<h:outputText value="Año:"/>
						</td>
						<td width="100px" align="left">
							<h:selectOneMenu id="idAnioGrab" value="#{presupuestoController.intAnioGrabacion}" disabled="#{presupuestoController.blnDisabledAnio}"
								onchange="selectYear(#{applicationScope.Constante.ONCHANGE_VALUE})">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<f:selectItems id="lstYears" value="#{presupuestoController.listYears}"/>
							</h:selectOneMenu>	
						</td>
						<td width="50px" align="left">
							<h:outputText value="Moneda"/>
						</td>
						<td width="100px" align="center">
							<h:selectOneMenu id="idTipoMoneda" value="#{presupuestoController.intTipoMoneda}" disabled="#{presupuestoController.blnDisabledTipoMoneda}"
								onchange="selectMoneda(#{applicationScope.Constante.ONCHANGE_VALUE})">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
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
			<rich:column width="1100px" style="text-align: left">
				<table>
					<tr>
						<td width="100px" align="left">
							<h:outputText value="Cuenta Contable:"/>
						</td>
						<td width="250px" align="center">
							<rich:spacer width="5px"/>
							<h:inputText id="txtCtaContableGrbPorPartida" value="#{presupuestoController.strCuentaGrb}" style="width:220px" disabled="true"></h:inputText>
				    	</td>
						<td width="120px" align="left">
							<rich:spacer width="4px"/>
	          	 			<a4j:commandButton id="btnAddCtaContablePorPartida" value="Agregar cuenta" styleClass="btnEstilos1" 
	          	 				action="#{presupuestoController.limpiarMpAddCuentaContable}"
	          	 				disabled="#{presupuestoController.blnDisabledAddCtaContable}"
	           					oncomplete="if(#{presupuestoController.intErrorPresupuestoPorAnioBase == 1}){Richfaces.showModalPanel('mpMessage')}
	           								else {Richfaces.showModalPanel('mpAddCtaContable')}"
	           					reRender="divAddCtaContable, divTblCuentas, mpMessage"/>	          	 			
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
		          	 		<h:selectOneMenu id="cboSucPanelPorPartida" style="width: 140px;"
								onchange="getSubSucursalPanelPorPartida(#{applicationScope.Constante.ONCHANGE_VALUE})"
								value="#{presupuestoController.intIdSucursalGrabacion}" disabled="#{presupuestoController.blnDisabledSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{presupuestoController.listJuridicaSucursal}"
									itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
							</h:selectOneMenu>
						</td>
						<td width="100px" align="left">
							<h:outputText value="Sub-sucursal"/>
						</td>
						<td width="150px" align="left">
							<h:selectOneMenu id="cboSubSucPanelPorPartida" style="width: 110px;"
					            value="#{presupuestoController.intIdSubSucursalGrabacion}" disabled="#{presupuestoController.blnDisabledSubSucursal}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{presupuestoController.listJuridicaSubsucursalGrb}"
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
							<h:inputText id="bdMontoProyEnero" value="#{presupuestoController.bdMontoProyEnero}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyEnero}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Febrero:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyFebrero" value="#{presupuestoController.bdMontoProyFebrero}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyFebrero}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Marzo:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyMarzo" value="#{presupuestoController.bdMontoProyMarzo}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyMarzo}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Abril:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyAbril" value="#{presupuestoController.bdMontoProyAbril}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyAbril}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Mayo:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyMayo" value="#{presupuestoController.bdMontoProyMayo}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyMayo}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Junio:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyJunio" value="#{presupuestoController.bdMontoProyJunio}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyJunio}"/>
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
							<h:inputText id="bdMontoProyJulio" value="#{presupuestoController.bdMontoProyJulio}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyJulio}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Agosto:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyAgosto" value="#{presupuestoController.bdMontoProyAgosto}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyAgosto}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Septiembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProySeptiembre" value="#{presupuestoController.bdMontoProySeptiembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProySeptiembre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Octubre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyOctubre" value="#{presupuestoController.bdMontoProyOctubre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyOctubre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Noviembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyNoviembre" value="#{presupuestoController.bdMontoProyNoviembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyNoviembre}"/>
						</td>
						<td width="80px" align="left">
							<h:outputText value="Diciembre:"/>
						</td>
						<td width="100px" align="left">
							<h:inputText id="bdMontoProyDiciembre" value="#{presupuestoController.bdMontoProyDiciembre}" style="width:85px;"
							onkeypress="return soloNumerosDecimalesPositivos(this)" disabled="#{presupuestoController.blnDisabledBdMontoProyDiciembre}"/>
						</td>
					</tr>
				</table>
			</rich:column>		
		</rich:columnGroup>
	</h:panelGrid>


