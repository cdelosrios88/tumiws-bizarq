<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:panel rendered="#{cierreOperacionalController.blnFormulario}" style="width:970px;border:1px solid #17356f;background-color:#DEEBF5;">
	<table id="tblTipoOpercion" width="100%" border="0">
		<tr>
			<td align="left" width="10%">
            	&nbsp;
            	<h:outputText value="Período"/>
            </td>
            <td>
            	<h:outputText value=":"/>
            </td>
            <td align="left">
            	&nbsp;&nbsp;
            	<h:selectOneMenu id="idMes" value="#{cierreOperacionalController.intMes}" 
            					 disabled="#{cierreOperacionalController.blnValidPass}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
                    	itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"/>
				</h:selectOneMenu>
				&nbsp;
				<h:selectOneMenu id="idAnio" value="#{cierreOperacionalController.intAnio}" 
								 disabled="#{cierreOperacionalController.blnValidPass}">
					<f:selectItems id="lstYears" value="#{cierreOperacionalController.listYears}"/>
				</h:selectOneMenu>
            </td>
            <td align="left" width="130">
            	<h:outputText value="Tipo de Registro : "/>
            </td>
            <td align="left">
            	<h:selectOneMenu id="idTipoRegistro" value="#{cierreOperacionalController.cierreCobranza.intParaTipoRegistro}"
            					 disabled="#{cierreOperacionalController.blnValidPass}">
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CIERRE_COBRANZA}" 
                        itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
                        propertySort="intOrden"/>
				</h:selectOneMenu>
            </td>
		</tr>
	</table>
	
	<h:panelGrid id="pgValidarPass" rendered="#{cierreOperacionalController.blnValidPassRendered}" columns="4">
		<rich:column width="85px">
			<h:outputText value="Contraseña" />
		</rich:column>
		<rich:column>
			<h:outputText value=":"/>
		</rich:column>
		<rich:column>
			<h:inputSecret id="txtValidPass" value="#{cierreOperacionalController.strValidPass}" 
          				 size="30" />
		</rich:column>
		<rich:column>
			<a4j:commandButton id="btnValidar" value="Validar" 
          		action="#{cierreOperacionalController.validarPassPerfil}" 
          		styleClass="btnEstilos" ajaxSingle="true"
          		reRender="inCobranzaEdit,pgControls" 
          		process="idMes,idAnio,idTipoRegistro,pgOperacionesCtaCte,txtValidPass,dtLstCierreOperacion,dtLstCierrePlanillaSalud,dtLstCierrePlanillaTercero"/>
		</rich:column>
	</h:panelGrid>
	
	<br/>
	
	<h:panelGrid id="pgOperacionesCtaCte" rendered="#{cierreOperacionalController.blnOperCtaCte}">
		<table width="100%" border="0">
			<tr>
				<td align="center">
					<rich:dataTable id="dtLstCierreOperacion" var="item" rowKeyVar="rowKey" width="500px" 
									value="#{cierreOperacionalController.listaCierreOperacionComp}"
									rendered="#{not empty cierreOperacionalController.listaCierreOperacionComp}">
						<f:facet name="header">
			                <rich:columnGroup>
			                    <rich:column width="250px">
			                        <rich:spacer/>
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Estado" />
			                    </rich:column>
			                    <rich:column width="200px">
			                        <h:outputText value="Fecha y Hora" />
			                    </rich:column>
			                </rich:columnGroup>
			            </f:facet>
			            <rich:column>
		                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_SOLICITUD_CTACTE}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.paramCobranzaOperacion.intIdDetalle}"/>
		                </rich:column>
		                <rich:column style="text-align:center;">
		                	<h:selectOneMenu value="#{item.cierreCobranzaOperacion.intParaEstadoCierre}">
								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
			                        itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
		                </rich:column>
		                <rich:column>
		                	<h:outputText value="#{item.cierreCobranzaOperacion.tsFechaRegistro}"/>
		                </rich:column>
					</rich:dataTable>
				</td>
			</tr>
		</table>
	</h:panelGrid>
		
	<h:panelGrid id="pgOperacionesPlla" rendered="#{cierreOperacionalController.blnOperPllaEnviada}">
		<table width="100%" border="0">
			<tr>
				<td>
					<rich:dataTable id="dtLstCierrePlanillaSalud" var="item" rowKeyVar="rowKey" width="600px" 
									value="#{cierreOperacionalController.listaCierrePlanillaCompSalud}"
									rendered="#{not empty cierreOperacionalController.listaCierrePlanillaCompSalud}">
						<f:facet name="header">
			                <rich:columnGroup>
			                    <rich:column width="150px">
			                        <h:outputText value="Tipo de Entidad" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Modalidad" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Tipo de Socio" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Estado" />
			                    </rich:column>
			                    <rich:column width="200px">
			                        <h:outputText value="Fecha y Hora" />
			                    </rich:column>
			                </rich:columnGroup>
			            </f:facet>
			            <rich:column style="text-align:center;">
			            	<h:outputText value="Salud"/>
			            </rich:column>
			            <rich:column>
		                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.paramModalidad.intIdDetalle}"/>
		                </rich:column>
		                <rich:column>
		                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.paramTipoSocio.intIdDetalle}"/>
		                </rich:column>
		                <rich:column style="text-align:center;">
		                	<h:selectOneMenu value="#{item.cierreCobranzaPlanilla.intParaEstadoCierre}">
								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
			                        itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
		                </rich:column>
		                <rich:column>
		                	<h:outputText value="#{item.cierreCobranzaPlanilla.tsFechaRegistro}"/>
		                </rich:column>
					</rich:dataTable>
				</td>
			</tr>
		</table>
		<br/>
	</h:panelGrid>
	
	<h:panelGrid id="pgOperacionesPllaEfect" rendered="#{cierreOperacionalController.blnOperPllaEfectuada}">	
		<table width="100%" border="0">
			<tr>
				<td>
					<rich:dataTable id="dtLstCierrePlanillaTercero" var="item" rowKeyVar="rowKey" width="600px" 
									value="#{cierreOperacionalController.listaCierrePlanillaCompTercero}"
									rendered="#{not empty cierreOperacionalController.listaCierrePlanillaCompTercero}">
						<f:facet name="header">
			                <rich:columnGroup>
			                    <rich:column width="150px">
			                        <h:outputText value="Tipo de Entidad" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Modalidad" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Tipo de Socio" />
			                    </rich:column>
			                    <rich:column width="100px">
			                        <h:outputText value="Estado" />
			                    </rich:column>
			                    <rich:column width="200px">
			                        <h:outputText value="Fecha y Hora" />
			                    </rich:column>
			                </rich:columnGroup>
			            </f:facet>
			            <rich:column style="text-align:center;">
			            	<h:outputText value="Terceros"/>
			            </rich:column>
			            <rich:column>
		                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.paramModalidad.intIdDetalle}"/>
		                </rich:column>
		                <rich:column>
		                	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.paramTipoSocio.intIdDetalle}"/>
		                </rich:column>
		                <rich:column style="text-align:center;">
		                	<h:selectOneMenu value="#{item.cierreCobranzaPlanilla.intParaEstadoCierre}">
								<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOESTADOCIERRE}" 
			                        itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
		                </rich:column>
		                <rich:column>
		                	<h:outputText value="#{item.cierreCobranzaPlanilla.tsFechaRegistro}"/>
		                </rich:column>
					</rich:dataTable>
				</td>
			</tr>
		</table>
	</h:panelGrid>
</rich:panel>