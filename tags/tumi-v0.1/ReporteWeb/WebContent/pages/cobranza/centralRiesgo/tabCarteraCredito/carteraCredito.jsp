<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 14/04/2014               	-->

<h:form id="frmCarteraCredito">
   	<rich:panel id="rpTabCarteraCredito" style="border:1px solid #17356f;width:2500px;background-color:#DEEBF5">
	   	<div align="center">
			<b>OPERACIONES DE CARTERA DE CREDITOS</b>
		</div>
		<table width="100%" border="0" align="center">
			<tr>
	            <td align="left" width="80px">
	            	<h:outputText value="Sucursal *: "/>
	            </td>
	            <td width="150px" align="left">
					<h:selectOneMenu id="cboSucursal" style="width: 140px;"
						onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})"
						value="#{carteraCreditoController.intIdSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{carteraCreditoController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
					</h:selectOneMenu>
				</td>
				<td width="95px" align="left">
					<h:outputText value="Sub Sucursal *: "/>
				</td>
				<td width="120px" align="left">
					<h:selectOneMenu id="cboSubSucursal" style="width: 110px;"
						value="#{carteraCreditoController.intIdSubSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{carteraCreditoController.listJuridicaSubsucursal}"
							itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</td>
				<td width="100px">
					<h:outputText value="Tipo de Socio :"/>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="idTipoSocio" value="#{carteraCreditoController.intParaTipoSocio}">
                   		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</td>
				<td width="100px">
					<h:outputText value="Modalidad :"/>
				</td>
				<td width="120px">
					<h:selectOneMenu id="idModalidad" value="#{carteraCreditoController.intParaModalidad}">
                   		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</td>
				<td width="120px">
					<h:outputText value="Tipo de Crédito :"/>
				</td>
				<td width="120px">
					<h:selectOneMenu id="idTipoCredito" value="#{carteraCreditoController.intParaTipoCredito}"
						onchange="getSubTipoCredito(#{applicationScope.Constante.ONCHANGE_VALUE})">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{carteraCreditoController.lstTipoCredito}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</td>
				<td width="160px">
					<h:selectOneMenu id="idSubTipoCredito" value="#{carteraCreditoController.intParaSubTipoCredito}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{carteraCreditoController.lstSubTipoCredito}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</td>
				
				<td width="140px">
					<h:outputText value="Clasificación Deudor :"/>
				</td>
				<td width="80px" align="left">
					<h:selectOneMenu id="idClasifDeudor" value="#{carteraCreditoController.intParaClasificacionDeudor}">
                   		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCATEGORIADERIESGO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intIdDetalle"/>
					</h:selectOneMenu>
				</td>
				<td width="80px">
					<h:outputText value="Periodo :"/>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboMes" value="#{carteraCreditoController.intParaMes}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboAnioBusq" value="#{carteraCreditoController.intParaAnio}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<f:selectItems id="lstYears" value="#{carteraCreditoController.listYears}"/>
					</h:selectOneMenu>
				</td>
				<td width="80px">
					<a4j:commandButton id="btnConsultar" value="Consultar" styleClass="btnEstilos1" 
						action="#{carteraCreditoController.consultarCarteraCredito}"
   	 					reRender="dtCarteraCredito,pgTotalesResumen,mpMessage"
   	 					oncomplete="if(#{carteraCreditoController.intErrorFiltros == 1}){Richfaces.showModalPanel('mpMessage')}"/>
				</td>
				<td width="80px">
					<h:commandButton id="btnReporte" value="Reporte" styleClass="btnEstilos1" 
						action="#{carteraCreditoController.imprimirCarteraCredito}"/>
				</td>
				<td width="80px">
					<a4j:commandButton id="btnCancelar" value="Cancelar" styleClass="btnEstilos1" 
						action="#{carteraCreditoController.limpiarTabCarteraCredito}"
						reRender="frmCarteraCredito"/>
				</td>
	        </tr>
        </table>
        <br/>
        
        <div align="center">
		<table>
			<tr>
				<td align="center">
					<rich:dataTable id="dtCarteraCredito"
						value="#{carteraCreditoController.listaCarteraCredito}" 
                   		rows="5" 
                   		var="item" rowKeyVar="rowKey" width="2400px">
                   		
                   		<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Código del Deudor" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Número de Crédito" />
								</rich:column>
								<rich:column width="120px" rowspan="2" style="text-align: center">
									<h:outputText value="Código de Agencia" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo Moneda" />
								</rich:column>
								<rich:column width="150px" rowspan="2" style="text-align: center">
									<h:outputText value="Nombre del deudor" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo de Crédito" />
								</rich:column>
								<rich:column width="100px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo de Producto" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Fecha Desembolso" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Monto Otorgado" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Saldo Capital Deuda" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Tarea interés Nominal Mensual" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Nro. de Cuotas Pactadas" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Días de atraso" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Fecha de Vencimiento general de la deuda" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Fecha último movimiento" />
								</rich:column>
								<rich:column width="160px" colspan="2" style="text-align: center">
									<h:outputText value="Provisión" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Superávit o Déficit" />
								</rich:column>
								<rich:column width="160px" colspan="2" style="text-align: center">
									<h:outputText value="Interés" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo de Operación 1" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Clasificación del Deudor 2" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Tipo de Garantía" />
								</rich:column>
								<rich:column width="120px" rowspan="2" style="text-align: center">
									<h:outputText value="Nombre de Administrador" />
								</rich:column>
								<rich:column width="80px" rowspan="2" style="text-align: center">
									<h:outputText value="Monto de Cuota Pactada" />
								</rich:column>
								<rich:column width="500px" colspan="9" style="text-align: center">
									<h:outputText value="Datos del socio" />
								</rich:column>
								<rich:column width="80px" breakBefore="true" style="text-align: center">
									<h:outputText value="Requerida" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Constituida" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Rend. Devengado" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="En suspenso" />
								</rich:column>
								<rich:column width="60px" style="text-align: center">
									<h:outputText value="Genero 3" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="DNI" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Relación Laboral con la cooperativa  4" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Fecha de Nacimiento" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Lugar de residencia" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Distrito" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Provincia" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Departamento" />
								</rich:column>
								<rich:column width="80px" style="text-align: center">
									<h:outputText value="Cargo" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
                   		<rich:column style="text-align:center">
							<h:outputText value="#{item.intCodigoDeudor}"/>
						</rich:column>
                   		<rich:column style="text-align:center">
							<h:outputText value="#{item.strNumeroCredito}"/>
						</rich:column>
                   		<rich:column style="text-align:center">
							<h:outputText value="#{item.strCodigoAgencia}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strTipoMoneda}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strNombreDeudor}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strTipoCredito}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strTipoProducto}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strFechaDesembolso}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdMontoOtorgado}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdSaldoCapitalDeuda}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdTasaInteresNominalMensual}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.intNroCuotasPactadas}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.intDiasAtraso}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strFechaVencimientoGral}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strFechaUltimoMovimiento}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdRequerida}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdConstituida}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdSuperavit}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdPorCobrar}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdSuspenso}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.intTipoOperacion}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.intClasificacionDeudor}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strTipoGarantia}"/>
						</rich:column>
						<rich:column style="text-align:left">
							<h:outputText value="#{item.strNombreAdmin}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.bdMontoCuotaPactada}">
								<f:converter converterId="ConvertidorMontos"/>
							</h:outputText>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strGenero}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strDNI}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.intRelacLaboral}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strFecNacimiento}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strLugarResidencia}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strDistrito}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strProvincia}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strDepartamento}"/>
						</rich:column>
						<rich:column style="text-align:center">
							<h:outputText value="#{item.strCargo}"/>
						</rich:column>
						
						<f:facet name="footer">
							<rich:datascroller for="dtCarteraCredito" maxPages="10"/>
						</f:facet>
                   	</rich:dataTable>
	            </td>
	        </tr>
	    </table>
	    </div>
	    
	    <br/>
	    
	    <table>
	    	<tr>
	    		<td align="left"><b>Nota 1 :</b> 0, vigente / 1, refinanciado / 2, Reestructurada / 3, Vencida / 4, Castigado</td>
	    	</tr>
	    	<tr>
	    		<td align="left"><b>Nota 2 :</b> 1, Normal / 2, CPP / 3, Deficiente / 4, Dudoso / 5, Perdida</td>
	    	</tr>
	    	<tr>
	    		<td align="left"><b>Nota 3 :</b> F, Femenino / M, Masculino </td>
	    	</tr>
	    	<tr>
	    		<td align="left"><b>Nota 4 :</b> 0, Ninguna / 2, Socio / 10, Trabajador</td>
	    	</tr>
	    </table>
	    
	    <br/><br/>
	    
	    <h:panelGrid id="pgTotalesResumen" border="1">
	    	<rich:columnGroup columnClasses="rich-sdt-header-cell" style="border:1px;border-style:solid;">
				<rich:column width="150px" rowspan="4" style="text-align:center;font-weight:bold;">
					<h:outputText value="Nro. Solicitudes" />
				</rich:column>
				<rich:column width="150px" rowspan="4" style="text-align:center;font-weight:bold;border-color:gray;">
					<h:outputText value="Monto Otorgado" />
				</rich:column>
				<rich:column width="150px" rowspan="4" style="text-align:center;font-weight:bold;border-color:gray;">
					<h:outputText value="Saldo Capital Deuda" />
				</rich:column>
				<rich:column width="150px" rowspan="4" style="text-align:center;font-weight:bold;border-color:gray;">
					<h:outputText value="Provisión Total" />
				</rich:column>
				<rich:column width="1500px" colspan="5" style="text-align:center;font-weight:bold;border-style:outset;">
					<h:outputText value="Clasificación del deudor (% provisión - máximo días de atraso - nombre de categoría)" />
				</rich:column>
				<rich:column width="300px" breakBefore="true" style="text-align:center;font-weight:bold;">
					<h:outputText value="1%" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="5%" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="25%" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="60%" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="100%" />
				</rich:column>
				<rich:column width="300px" breakBefore="true" style="text-align:center;font-weight:bold;">
					<h:outputText value="8" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="30" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="60" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="90" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="120" />
				</rich:column>
				<rich:column width="300px" breakBefore="true" style="text-align:center;font-weight:bold;">
					<h:outputText value="Normal" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="CPP" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="Deficiente" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="Dudoso" />
				</rich:column>
				<rich:column width="300px" style="text-align:center;font-weight:bold;">
					<h:outputText value="Pérdida" />
				</rich:column>
			</rich:columnGroup>
			<rich:columnGroup style="border:1px;border-style:solid">
				<rich:column width="150px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.intTotNroSolicitud}" />
				</rich:column>
				<rich:column width="150px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotMontoOtorgado}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="150px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotSaldoCapitalDeuda}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="150px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotProvision}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="300px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotClasifNormal}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="300px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotClasifCPP}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="300px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotClasifDeficiente}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="300px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotClasifDudoso}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
				<rich:column width="300px" style="text-align:center;background-color:#FFFFFF;">
					<h:outputText value="#{carteraCreditoController.bdTotClasifPerdida}">
						<f:converter converterId="ConvertidorMontos"/>
					</h:outputText>
				</rich:column>
			</rich:columnGroup>
	    </h:panelGrid>
	</rich:panel>
</h:form>