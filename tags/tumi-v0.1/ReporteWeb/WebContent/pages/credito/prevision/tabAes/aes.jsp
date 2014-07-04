<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 27/11/2013               	-->

<h:form id="frmAes">
   	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
       	<div align="center">
			<b>AES</b>
		</div>
		<br/>
		<table width="100%" border="0" align="center">
			<tr>
	            <td align="left"  width="80">
	            	<h:outputText value="Sucursal : "/>
	            </td>
	            <td width="150">
	            	<h:selectOneMenu id="cboSucursal" value="#{aesController.intIdSucursal}"
	            		onchange="getSubSucursalAes(#{applicationScope.Constante.ONCHANGE_VALUE})"
	            		style="width:140px">
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel" value="#{aesController.listaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" />
					</h:selectOneMenu>
	            </td>
	            
	            <td align="center" width="80">
	            	<h:outputText value="Sub Sucursal : "/>
	            </td>
	            <td>
	            	<h:selectOneMenu id="cboSubSucursalAes" style="width: 110px;"
			            value="#{aesController.intIdSubSucursal}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{aesController.listaSubSucursal}"
							itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}" propertySort="strDescripcion"/>
					</h:selectOneMenu>
				</td>
				<td>
					<h:selectOneMenu id="idTipoSolicitud" value="#{aesController.intIdTipoFiltro}">
						<f:selectItem itemValue="0" itemLabel="Seleccione.." />
						<f:selectItem itemValue="1" itemLabel="Solicitud" />
						<f:selectItem itemValue="2" itemLabel="DNI" />
						<f:selectItem itemValue="3" itemLabel="Apellido y Nombre" />
						<f:selectItem itemValue="4" itemLabel="Cuenta" />
					</h:selectOneMenu>
				</td>
				<td>
					<h:inputText id="idDescFiltro" value="#{aesController.strTipoFiltro}" size="60"/>
	            </td>
	        </tr>
		</table>
		<br/>
		<table>
			<tr>
	        	<td align="left" width="70">
	            	<h:outputText value="Tipo : "/>
	            </td>
	            <td>
	            	<h:selectOneMenu id="idTipoAes" value="#{aesController.intTipoTitular}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0" />
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCONCEPTO_AES}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </td>
	            <td align="center">
	            	<h:outputText value="Estado Expediente :"/>
	            </td>
	            <td>
	            	<h:selectOneMenu id="idEstadoSolicitud" value="#{aesController.intEstadoSolicitud}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{aesController.listaEstadoSolicitud}"
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	            </td>
	            <td align="center">
	            	<h:outputText value="Estado Pago : "/>
	            </td>
	            <td>
	            	<h:selectOneMenu id="idEstadoPago" value="#{aesController.intEstadoPago}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<f:selectItem itemLabel="Pendiente" itemValue="1"/>
						<f:selectItem itemLabel="Girado" itemValue="6"/>
					</h:selectOneMenu>
	            </td>
	            <td align="left" width="90">
	            	<h:outputText value="Fecha Solicitud : "/>
	            </td>
	            <td>
	            	<rich:calendar id="dtSolicitudDesde" datePattern="dd/MM/yyyy"
						value="#{aesController.dtFechaSolicitudDesde}"  
						inputSize="10" 
						showApplyButton="true"/>
	            </td>
	            <td>
	            	<rich:calendar id="dtSolicitudHasta" datePattern="dd/MM/yyyy"
						value="#{aesController.dtFechaSolicitudHasta}"  
						inputSize="10" 
						showApplyButton="true"/>
	            </td>
	        </tr>
	        <tr>
	        	<td>
	            	<a4j:commandButton  value="Buscar" 
  						action="#{aesController.buscar}" 
  						styleClass="btnEstilos" ajaxSingle="true"
  						process="cboSucursal,cboSubSucursalAes,idTipoSolicitud,idDescFiltro,idTipoAes,
  								 idEstadoSolicitud,idEstadoPago,dtSolicitudDesde,dtSolicitudHasta"
  						reRender="dtAes"/>
	            </td>
	            <td>
	            	<h:commandButton  value="Reporte" 
  						action="#{aesController.imprimirAes}" 
  						styleClass="btnEstilos"/>
	            </td>
	        </tr>
		</table>
		<br/>
		
		<div align="center">
			<table>
				<tr>
					<td align="center">
		        		<a4j:outputPanel id="oppObjSqlSc" ajaxRendered="true">
							<rich:dataTable id="dtAes" styleClass="dataTable1"
								value="#{aesController.listaAes}"
								rendered="#{not empty aesController.listaAes}"
		                   		rows="5" 
		                   		var="item" rowKeyVar="rowKey" width="950px">
		                   			<f:facet name="header">
										<rich:columnGroup columnClasses="rich-sdt-header-cell">
											<rich:column width="80px" rowspan="2" style="text-align: center">
												<h:outputText value="Nro. Solicitud" />
											</rich:column>
											<rich:column width="300px" rowspan="2" style="text-align: center">
												<h:outputText value="Cuenta - Apellido y Nombre" />
											</rich:column>
											<rich:column width="150px" rowspan="2" style="text-align: center">
												<h:outputText value="Tipo" />
											</rich:column>
											<rich:column width="120px" rowspan="2" style="text-align: center">
												<h:outputText value="Monto" />
											</rich:column>
											<rich:column width="90px" rowspan="2" style="text-align: center">
												<h:outputText value="Fecha de solicitud" />
											</rich:column>
											<rich:column width="200px" colspan="2" style="text-align: center">
												<h:outputText value="Evaluación" />
											</rich:column>
											<rich:column width="80px" rowspan="2" style="text-align: center">
												<h:outputText value="Fecha de Egreso" />
											</rich:column>
											<rich:column width="70px" breakBefore="true" style="text-align: center">
												<h:outputText value="Estado" />
											</rich:column>
											<rich:column width="90px" style="text-align: center">
												<h:outputText value="Fecha" />
											</rich:column>
										</rich:columnGroup>
									</f:facet>
		                            <rich:column style="text-align: center">
										<h:outputText value="#{item.strNroSolicitud}"/>
									</rich:column>
									<rich:column>
										<h:outputText value="#{item.strCuenta}"/>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.strTipo}"/>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.bdMontoBruto}">
											<f:converter converterId="ConvertidorMontos"/>
										</h:outputText>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.strFechaSolicitud}"/>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.strEvaluacionEstado}"/>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.strEvaluacionFecha}"/>
									</rich:column>
									<rich:column style="text-align: center">
										<h:outputText value="#{item.strEgresoFecha}"/>
									</rich:column>
		                            <f:facet name="footer">
		                            	<rich:datascroller for="dtAes" maxPages="10"/>
		                            </f:facet>
			                </rich:dataTable>
						</a4j:outputPanel>
					</td>
				</tr>
			</table>
		</div>
	</rich:panel>
</h:form>