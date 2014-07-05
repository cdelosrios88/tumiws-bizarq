<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

  <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
    	<h:panelGrid id="pgDetalleRefinanLink">
			<rich:dataTable value="#{solicitudRefinanController.listaDetalleRefinanciamientoLink}"
							styleClass="dataTable1" id="tblDetalleRefinanLink"
							rowKeyVar="rowKey" sortMode="single" style="margin:1 auto text-align: center"
							var="itemDetRef" >
			<f:facet name="header">
				
					<rich:columnGroup columnClasses="rich-sdt-header-cell">
									<rich:column width="15px" rowspan="2" style="text-align: center">
										<h:outputText value="" />
									</rich:column>
									<rich:column width="490px" colspan="7" style="text-align: center">
										<h:outputText value="Credito Anterior" />
									</rich:column>
									<rich:column width="350px" colspan="5" style="text-align: center">
										<h:outputText value="Credito Refinanciado" />
									</rich:column>
									<rich:column width="210px" colspan="3" style="text-align: center">
										<h:outputText value="Fechas" />
									</rich:column>
									<rich:column width="70px" rowspan="2" style="text-align: center">
										<h:outputText value="Sucursal Proceso" />
									</rich:column>
									<rich:column width="150px" breakBefore="true" style="text-align: center">
										<h:outputText value="Tipo Crédito" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Nro. Solicitud" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Monto" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="CP/Cta" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Saldo" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Interés Atrasado" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Mora Atrasada" />
									</rich:column>
									
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Nro. Solicitud" />
									</rich:column>
									<rich:column width="150px" style="text-align: center">
										<h:outputText value="Tipo Crédito" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Monto a Refinanciar" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Nro. Cuota" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Cuota Fija" />
									</rich:column>
									
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Requisito" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Solicitud" />
									</rich:column>
									<rich:column width="70px" style="text-align: center">
										<h:outputText value="Autorizacion" />
									</rich:column>
				</rich:columnGroup>
			</f:facet>
			
					<rich:column width="15px" style="text-align: center">
						<div align="center">
							<h:outputText value="#{rowKey+1}" />
						</div>
					</rich:column>
					<rich:column width="150px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoAnteriorComp.strDescripcionExpedienteCredito}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoAnteriorComp.expedienteCredito.id.intItemExpediente}-#{itemDetRef.expedienteCreditoAnteriorComp.expedienteCredito.id.intItemDetExpediente}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoAnteriorComp.expedienteCredito.bdMontoTotal}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.intNumeroCuota}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.bdMontoSolicitado}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.bdMontoInteresAtrasado}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.bdMontoMoraAtrasada}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expediente.id.intItemExpediente}-#{itemDetRef.expediente.id.intItemExpedienteDetalle}"/>
					</rich:column>
					<rich:column width="150px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.strDescripcionExpedienteCredito}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.bdMontoTotal}"><f:converter converterId="ConvertidorMontos" /></h:outputText>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.expedienteCredito.intNumeroCuota}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.bdCuotaFija}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.strFechaRequisito}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.strFechaSolicitud}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.strFechaAutorizacion}"/>
					</rich:column>
					<rich:column width="70px" style="text-align: center">
						<h:outputText value="#{itemDetRef.expedienteCreditoComp.strDescripcionSubActividad}"/>
					</rich:column>
				</rich:dataTable>
			</h:panelGrid>
</rich:panel>

       