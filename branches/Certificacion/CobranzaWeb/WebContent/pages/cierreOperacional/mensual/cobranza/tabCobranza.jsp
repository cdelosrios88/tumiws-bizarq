<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Modulo    : Cobranza                 		-->
	<!-- Prototipo : Cierre Operacional - Cobranza	-->			
	<!-- Fecha     : 24/06/2013               		-->
	
<script type="text/javascript">
</script>

<rich:panel style="border:1px solid #17356f;width:100%; background-color:#DEEBF5">
	<table width="100%" border="0" align="center">
		<tr>
            <td align="left" >
            	<h:outputText value="Período : "/>
            </td>
            <td>
            	<h:selectOneMenu id="idMesBusq" value="#{cierreOperacionalController.intMesBusq}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
                              itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
                              tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>
				&nbsp;
				<h:selectOneMenu id="idAnioBusq" value="#{cierreOperacionalController.intAnioBusq}">
					<f:selectItems id="lstYears" value="#{cierreOperacionalController.listYears}"/>
					<f:selectItem itemValue="2000" itemLabel="Todos los años" />
				</h:selectOneMenu>
            </td>
            
            <td align="left" width="130">
            	<h:outputText value="Tipo de Operaciones : "/>
            </td>
            <td>
            	<h:selectOneMenu id="idTipoOperacionBusq" value="#{cierreOperacionalController.intTipoOperacionBusq}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CIERRE_COBRANZA}" 
                           itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
                           tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>
            </td>
            <td align="left" >
            	<h:outputText value="Estado : "/>
            </td>
            <td>
            	<h:selectOneMenu id="idEstadoBusq" value="#{cierreOperacionalController.intEstadoBusq}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
                              itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
                              tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>
            </td>
            <td>
            	<a4j:commandButton  value="Buscar" 
            						action="#{cierreOperacionalController.buscarCierreCobranza}" 
            						styleClass="btnEstilos" ajaxSingle="true"
            						process="edtCierreMensual,idMesBusq,idAnioBusq,idTipoOperacionBusq,idEstadoBusq"
            						reRender="edtCierreMensual"/>
            </td>
        </tr>
	</table>
	<br/>
	
	<div align="center">
		<table>
			<tr>
				<td align="center">
	        		<a4j:outputPanel id="oppObjSqlSc" ajaxRendered="true">
						<rich:dataTable id="edtCierreMensual"
							value="#{cierreOperacionalController.listaCierreCobranza}" 
	                   		rows="5" rendered="#{not empty cierreOperacionalController.listaCierreCobranza}"
	                   		var="item" rowKeyVar="rowKey" width="950px">
	                             <f:facet name="header">
	                             	<rich:columnGroup>
	                             		<rich:column width="15"/>
	                           			<rich:column width="100px">
	                           				<h:outputText value="Período"/>
	                           			</rich:column>
	                           			<rich:column width="200px">
	                           				<h:outputText value="Usuario"/>
	                           			</rich:column>
	                           			<rich:column width="200px">
	                           				<h:outputText value="Tipo de Operación"/>
	                           			</rich:column>
	                           			<rich:column width="200px">
	                           				<h:outputText value="Sub Tipo de Operaciones"/>
	                           			</rich:column>
	                           			<rich:column width="70px">
	                           				<h:outputText value="Estado"/>
	                           			</rich:column>
	                           			<rich:column width="150px">
	                           				<h:outputText value="Fecha de Registro"/>
	                           			</rich:column>
	                           		</rich:columnGroup>
	                             </f:facet>
	                             <rich:column>
	                             	<h:outputText value="#{rowKey+1}"/>
	                             </rich:column>
	                             <rich:column width="100px">
	                                 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.intMes}"/> - 
									 <h:outputText value="#{item.intAnio}"/>
	                             </rich:column>
	                             <rich:column>
	                                 <h:outputText value="#{item.usuario.natural.strNombres} 
	                                 					  #{item.usuario.natural.strApellidoPaterno} 
	                                 					  #{item.usuario.natural.strApellidoMaterno}"/>
	                             </rich:column>
	                             <rich:column width="200px">
	                                 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CIERRE_COBRANZA}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.cierreCobranza.intParaTipoRegistro}"/>
	                             </rich:column>
	                             <rich:column width="200px">
	                                 <h:panelGroup rendered="#{item.cierreCobranzaOperacion.id.intParaTipoSolicitudCtaCte!=null}">
	                                 	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_SOLICITUD_CTACTE}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.cierreCobranzaOperacion.id.intParaTipoSolicitudCtaCte}"/>
	                                 </h:panelGroup>
	                                 <h:panelGroup rendered="#{item.cierreCobranzaPlanilla.id.intEstrucGrupo!=null}">
	                                 	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOENTIDAD}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.cierreCobranzaPlanilla.id.intEstrucGrupo}"/> - 
									    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.cierreCobranzaPlanilla.id.intParaModalidad}"/> - 
									    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.cierreCobranzaPlanilla.id.intParaTipoSocio}"/>
	                                 </h:panelGroup>
	                             </rich:column>
	                             <rich:column width="150px">
	                                 <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
						   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
									      property="#{item.intEstado}"/>
	                             </rich:column>
	                             <rich:column width="150px">
	                                 <h:outputText value="#{item.strFechaRegistro}"/>
	                             </rich:column>
	                             <f:facet name="footer">
	                               <rich:datascroller for="edtCierreMensual" maxPages="10"/>
	                             </f:facet>
		                </rich:dataTable>
					</a4j:outputPanel>
				</td>
			</tr>
		</table>
	</div>
	
	<br/>
	
	<table>
		<tr>
			<td>
				<h:panelGrid id="pgControls" columns="3">
					<a4j:commandButton id="btnNuevo" value="Nuevo" action="#{cierreOperacionalController.showFormCierreOperacional}" styleClass="btnEstilos" reRender="inCobranzaEdit,pgControls,btnGrabar"/>
					<h:panelGroup rendered="#{cierreOperacionalController.strCierreCobranza == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
						<a4j:commandButton id="btnGrabar" value="Grabar" action="#{cierreOperacionalController.grabarCierreCobranza}" styleClass="btnEstilos" rendered="#{cierreOperacionalController.blnGrabar}" reRender="inCobranzaEdit"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{cierreOperacionalController.strCierreCobranza == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
						<a4j:commandButton id="btnModificar" value="Grabar" action="#{cierreOperacionalController.modificarCierreCobranza}" styleClass="btnEstilos" rendered="#{cierreOperacionalController.blnGrabar}" reRender="inCobranzaEdit"/>
					</h:panelGroup>
		            <a4j:commandButton id="btnCancelar" value="Cancelar" action="#{cierreOperacionalController.cancelarCierreMensual}" styleClass="btnEstilos" reRender="inCobranzaEdit,pgControls,btnGrabar"/>
				</h:panelGrid>
			</td>
		</tr>
	</table>
	<br/>
	
	<table>
		<tr>
			<td>
				<a4j:include id="inCobranzaEdit" viewId="/pages/cierreOperacional/mensual/cobranza/tabCobranzaEdit.jsp"/>
			</td>
		</tr>
	</table>
</rich:panel>