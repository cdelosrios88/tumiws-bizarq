<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

		<!-- Empresa   : Cooperativa Tumi         -->
		<!-- Autor     : Franko Yalico    		  -->
<h:form>
	<rich:panel styleClass="rich-tabcell-noborder" style="border:1px solid #17356f;">
		<h:panelGrid columns="8" id="panelprimerafila">
			<rich:column width="70" style="text-align: left;">
					<h:outputText value="Sucursal: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.estructuraDetalle.intSeguSucursalPk}"
								 style="width:137px;">
						<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
						<tumih:selectItems var="sel" value="#{envioController.listaTablaDeSucursal}"
						 itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
					<h:outputText value="Unid. Ejecutora: "/>
			</rich:column>
			<rich:column width="210">
				<h:inputText value="#{envioController.dtoFiltroDeEnvio.estructura.juridica.strRazonSocial}"
							 style="width:200px;"/>
			</rich:column>
			<rich:column width="70">
					<h:outputText value="Tipo Socio: "/>
			</rich:column>
			<rich:column width="100">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.estructuraDetalle.intParaTipoSocioCod}" 
								style="width:130px;">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		<h:panelGrid columns="10" id="panelBusqueda">
			<rich:column width="70">
					<h:outputText value="Tipo Planilla: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.intParaModalidadPlanilla}" style="width:130px;">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				  </h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
					<h:outputText value="Periodo Planilla: "/>
			</rich:column>
			<rich:column width="100">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.intPeriodoMes}" style="width:100px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"/>
			 	</h:selectOneMenu>			 	
			</rich:column>
			<rich:column width="102">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.intPeriodoAnio}" style="width:100px;">
					 <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<f:selectItem itemLabel="2012" itemValue="2012"/>
					<f:selectItem itemValue="2013" itemLabel="2013"/>
					<f:selectItem itemValue="2014" itemLabel="2014"/>
					<f:selectItem itemValue="2015" itemLabel="2015"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
					<h:outputText value="Estado: "/>
			</rich:column>
			<rich:column width="140">
				<h:selectOneMenu value="#{envioController.dtoFiltroDeEnvio.envioConcepto.intParaEstadoCod}" style="width:100px;">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>	
			</rich:column>
			<rich:column width="70" style="text-align:right;">
				<a4j:commandButton styleClass="btnEstilos"
					value="Buscar"
					reRender="panelTablaResultados, panelMensaje,pglink"
					action="#{envioController.buscarDeEnvio}" style="width:70px"/>
			</rich:column>
			<rich:column width="70" style="text-align:right;">
				<a4j:commandButton styleClass="btnEstilos"
					value="Limpiar"
					reRender="panelTablaResultados, panelMensaje,pglink,panelBusqueda,panelprimerafila"
					action="#{envioController.limpiarDeEnvio}" style="width:70px"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="12px"/>
		
		<h:panelGrid id="panelTablaResultados">
			<rich:extendedDataTable id="tblResultado"
			 enableContextMenu="false"
			 sortMode="single"
			 var="item"
			 rendered="#{not empty envioController.listaBusquedaDeEnvioresumen}"
			 value="#{envioController.listaBusquedaDeEnvioresumen}"
			 rowKeyVar="rowKey"
			 rows="5"
			 width="1030px"
			 height="160px"
			 align="center">
			<rich:column width="30px">
      			<h:outputText value="#{rowKey+1}"/>
      		</rich:column>
      		
		      <rich:column width="170px">
		          <f:facet name="header">
		          	<h:outputText value="Unidad Ejecutora"/>
		          </f:facet>
		          <h:outputText value="#{item.juridicaUnidadEjecutora.strRazonSocial}"/>
		      </rich:column>
		      
		      <rich:column width="130px">
		          <f:facet name="header">
		          	<h:outputText value="Sucursal"/>
		          </f:facet>
		          <h:outputText value="#{item.juridicaSucursal.strRazonSocial}"/>
		      </rich:column>
		      
		      <rich:column width="90px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Tipo Socio"/>
		          </f:facet>
		           <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intTiposocioCod}"/>
     		 </rich:column>
     		 
		      <rich:column width="90px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Tipo Planilla"/>
		          </f:facet>
		          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intModalidadCod}"/>
		      </rich:column>
		      
		      <rich:column width="55px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Periodo"/>
		          </f:facet>
		          <h:outputText value="#{item.intPeriodoplanilla}"/>
		      </rich:column>
      
		      <rich:column width="75px" style="text-align: right">
		          <f:facet name="header">
		          	<h:outputText value="Monto"/>
		          </f:facet>
		          <h:outputText value="#{item.bdMontototal}">
		          	<f:converter converterId="ConvertidorMontos" />
		          </h:outputText>
		      </rich:column>
		      
		      <rich:column width="70px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Estado"/>
		          </f:facet>
		          <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="intIdDetalle" itemLabel="strDescripcion" 
									property="#{item.intEstadoCod}"/>
		      </rich:column>
		      
		      <rich:column width="190px" style="text-align: center">
		          <f:facet name="header">
		          	<h:outputText value="Fecha Registro"/>
		          </f:facet>
		          <h:outputText value="#{item.tsFecharegistro}">
		          		<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="America/Bogota"/>
		          </h:outputText>
		      </rich:column>
		      		      
		      	<a4j:support event="onRowClick" actionListener="#{envioController.seleccionarRegistro}"> 					
           			<f:attribute name="item" value="#{item}"/>
      			</a4j:support>
      			
		      <f:facet name="footer">
		      	<rich:datascroller for="tblResultado" maxPages="10"/>
		      </f:facet>
		      
			</rich:extendedDataTable>
		</h:panelGrid>
		
		<h:panelGrid columns="2" style="margin-left: auto; margin-right: auto"
					rendered="#{ not empty envioController.listaBusquedaDeEnvioresumen}">
				<a4j:commandLink action="#">
					<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
				</a4j:commandLink>
				<h:outputText value="Para ver un enviado hacer click en el registro" style="color:#8ca0bd"/>
		</h:panelGrid>
		
		<h:panelGroup id="pglink">
			<h:panelGrid id="pglinkExportar">
				<rich:column width="150" style="text-align:center">
					<a4j:commandLink value ="Exportar"
								action="#{envioController.ValidarOpciones}"
								oncomplete ="Richfaces.showModalPanel('pOpciones')"
								reRender = "pOpciones, fmOpciones"
								rendered="#{not empty envioController.listaBusquedaDeEnvioresumen}"/>					
				</rich:column>		
			</h:panelGrid>
		</h:panelGroup>
		
		<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeExito}"/>
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeError}"/>
		</h:panelGroup>
		
		<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;" styleClass="rich-tabcell-noborder"
			id="panelBotones">
			<h:panelGrid columns="3">
				<a4j:commandButton value="Nuevo" 
					styleClass="btnEstilos" 
					style="width:90px" 
					action="#{envioController.habilitarPanelInferior}" 
					reRender="contPanelInferior,panelMensaje,panelBotones" />
				 <a4j:commandButton value="Grabar" 
			    	styleClass="btnEstilos" 
			    	style="width:90px"
			    	action="#{envioController.grabarDeEnvio}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones,panelTablaResultados"
			    	disabled="#{!envioController.habilitarGrabar}"/>
			    <a4j:commandButton value="Cancelar" 
			    	styleClass="btnEstilos"
			    	style="width:90px"
			    	action="#{envioController.deshabilitarPanelInferior}" 
			    	reRender="contPanelInferior,panelMensaje,panelBotones"/>  
			</h:panelGrid>
		</h:panelGroup>
		
		<h:panelGroup id="contPanelInferior">
			<rich:panel  rendered="#{envioController.mostrarPanelInferior}"	style="border:1px solid #17356f;background-color:#DEEBF5;">
				<h:panelGroup rendered="#{!envioController.datosValidados}">
					<h:panelGrid columns="2" id="panelValidarC" >
						<rich:column style="width:90px">
							<h:outputText rendered="#{envioController.dtoDeEnvio.juridicaSucursal == null}"/>
							<h:outputText value="Sucursal:" rendered="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial != null}"/>
						</rich:column>
						<rich:column>
							<h:outputText rendered="#{envioController.dtoDeEnvio.juridicaSucursal == null}"/>
							<h:inputText value="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial}" 
								disabled="true" style="background-color: #BFBFBF;font-weight:bold;" size="50"
								 rendered="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial != null}"/>
						</rich:column>
					</h:panelGrid>
					<h:panelGrid columns="3" id="panelValidarC2">
						<rich:column style="width:90px">
							<h:outputText value="Unid. Ejecutora: "/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}" 
								  	  disabled="true" style="background-color: #BFBFBF;font-weight:bold;" size="50"/>
						</rich:column>
						<rich:column>
							<a4j:commandButton  action="#{envioController.abrirPopupBuscarUnidadEjecutora}" 
											    styleClass="btnEstilos" value="Buscar"  
											    reRender="contPanelInferior, panelValidarC2,
											              pBuscarUnidadEjecutora,panelMensajeValidar"  
						   						oncomplete="Richfaces.showModalPanel('pBuscarUnidadEjecutora')"/>
						</rich:column>
					</h:panelGrid>
					<h:panelGrid id="pGridTipoSocio" columns="2">
						<rich:column style="width:90px">
							<h:outputText value="Tipo Socio: "/>
						</rich:column>
						<rich:column>
							<h:selectOneRadio value="#{envioController.strTipoSociOcas}" 
								disabled="#{empty envioController.dtoDeEnvio.juridicaSucursal}" >
								<f:selectItem itemValue="Activo" itemLabel="Activo"/>
								<f:selectItem itemValue="Cesante" itemLabel="Cesante"/>
								<f:selectItem itemValue="Cas" itemLabel="CAS"/>
							</h:selectOneRadio>
						</rich:column>
					</h:panelGrid>
					
					<rich:spacer height="12px"/>
					
					<h:panelGrid columns="1" rendered="#{!envioController.datosValidados}">
						<rich:column width="950">
							<a4j:commandButton styleClass="btnEstilos"
		                	value="Validar Datos"
		                	disabled="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial == null}"
		                	reRender="contPanelInferior,panelBotones,
		                			  panelMensajeValidar,
		                			  panelListaValidarConArchivo"
		                    action="#{envioController.irValidarDeEnvio}" 
		                    style="width:950px"/>
						</rich:column>
					</h:panelGrid>
					<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
								  id="panelMensajeValidar"  styleClass="rich-tabcell-noborder">
						<h:outputText value="#{envioController.mensajeOperacion}" 
							styleClass="msgInfo" style="font-weight:bold"
							rendered="#{envioController.mostrarMensajeExito}"/>
						<h:outputText value="#{envioController.mensajeOperacion}" 
							styleClass="msgError" style="font-weight:bold"
							rendered="#{envioController.mostrarMensajeError}"/>
					</h:panelGroup>
<!--					flyalico-->
					<h:panelGroup style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
									styleClass="rich-tabcell-noborder"  id="panelListaValidarConArchivo">
						<a4j:repeat value="#{envioController.listaMensajes}" var="item">
							<h:panelGrid rendered="#{not empty item.strMsg}" columns="1">
								<rich:column style="text-align: left">
									<table>
										<tr>
											<td>
												<h:outputText value="#{item.strMsg}" styleClass="msgError"
															  style="font-weight:bold"/>
											</td>
										</tr>
									</table>
								</rich:column>
							</h:panelGrid>
						</a4j:repeat>
					</h:panelGroup>	
<!--					flyalico-->
										
				</h:panelGroup>
				<h:panelGroup rendered="#{envioController.datosValidados}">
					<h:panelGrid columns="8">
						<rich:column>							
							<h:outputText value="Sucursal: " rendered="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial != null}"/>
						</rich:column>
						<rich:column>							
							<h:inputText value="#{envioController.dtoDeEnvio.juridicaSucursal.strRazonSocial}"
										disabled="true" style="width:200px;"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Unid. Ejecut.: "/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{envioController.dtoDeEnvio.estructura.juridica.strRazonSocial}"
										disabled="true" style="width:200px;"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Tipo Soc.: "/>
						</rich:column>
						<rich:column rendered="#{envioController.dtoDeEnvio.blnActivo}">
							<tumih:radioButton name="filtroPerfilVigencia" overrideName="true"
											   value="#{envioController.dtoDeEnvio.blnActivo}"
											  itemValue="true" itemLabel="Activo" disabled="true">
							</tumih:radioButton>
						</rich:column>
						<rich:column rendered="#{not envioController.dtoDeEnvio.blnActivo}">
							<tumih:radioButton name="filtroPerfilVigencia" overrideName="true"
											  value="#{envioController.dtoDeEnvio.blnActivo}"
											  itemValue="false" itemLabel="Cesante" disabled="true">
							</tumih:radioButton>
						</rich:column>
							
					</h:panelGrid>
					<h:panelGrid columns="8">
						<rich:column>
							<h:outputText value="Mes: "/>
						</rich:column>
						<rich:column>
							<tumih:inputText cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
									itemValue="intIdDetalle" itemLabel="strDescripcion" disabled="true"						
									property="#{envioController.dtoDeEnvio.envioConcepto.intTempMesPlanilla}" />
						</rich:column>
						<rich:column>
							<h:outputText value="Año: "/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{envioController.dtoDeEnvio.envioConcepto.intTempAnioPlanilla}"
										 style="width:50px;" disabled="true"/>
						</rich:column>
						<rich:column>
							<h:outputText value="Tipo Planilla: "/>
						</rich:column>
						<rich:column rendered="#{envioController.intNroHaberesOf > 0}">
							<h:selectBooleanCheckbox value="#{envioController.blnCheckHaber}"
											   disabled="#{envioController.blnEnviadaHaber}">							
							</h:selectBooleanCheckbox>
							<h:outputText value="Haber" />	
						</rich:column>
						<rich:column rendered="#{envioController.intNroIncentivoOf > 0}">
							<h:selectBooleanCheckbox
								value="#{envioController.blnCheckIncentivo}"
								disabled="#{envioController.blnEnviadaIncentivo}">							
							</h:selectBooleanCheckbox>
							<h:outputText value="Incentivo" />
						</rich:column>
						<rich:column rendered="#{envioController.intNroCasOf > 0}">
							<h:selectBooleanCheckbox value="#{envioController.blnCheckCAS}" disabled="#{false}">							
								<a4j:support event="onclick" reRender="pgMantenimiento"
										actionListener="#{envioController.onclickCheckCasDeEnvio}" />
							</h:selectBooleanCheckbox>
							<h:outputText value="Cas" />
						</rich:column>
					</h:panelGrid>
					<rich:spacer height="4px" />
					<rich:dataTable bgcolor="#639BC8"
						style="color: white;" id="idGrillaDePlanillaEnvio" var="item"
						sortMode="single" rows="15" value="#{envioController.planilla}"						
						rendered="#{envioController.dtoDeEnvio.blnActivo && not empty envioController.planilla}"
						width="970px" rowKeyVar="rowKey">
						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column colspan="6">
									<h:outputText
										value="#{fn:length(envioController.planilla)} Socio(s) en total ( #{envioController.nroHaberesEnviado} haberes - #{envioController.nroIncentivosEnviado} incentivos)" />
								</rich:column>
								
								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalHaberes}"
										rendered="#{envioController.nroHaberes > 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalHaberI}"
										rendered="#{envioController.totalHaberI != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalIncentivos}"
										rendered="#{envioController.nroIncentivos > 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>

								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalIncentivoI}"
										rendered="#{envioController.totalIncentivoI != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="1" style="text-align: right">

								</rich:column>

								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalTotal}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								<rich:column rowspan="3" width="200px">
									<h:outputText value="Opciones (Modificar)" />
								</rich:column>

								<rich:column breakBefore="true" rowspan="2" width="10px">
									<h:outputText value="N" />
								</rich:column>
								<rich:column rowspan="2" width="15px">
									<h:outputText value="Codigo" />
								</rich:column>
								<rich:column rowspan="2" width="250px">
									<h:outputText value="Nombre Completo" />
								</rich:column>
								<rich:column colspan="2" width="90px">
									<h:outputText value="Efectuado" />
								</rich:column>
								<rich:column rowspan="2" width="60px">
									<h:outputText value="DNI" />
								</rich:column>

								<rich:column rowspan="2" width="45px">
									<h:outputText value="Haberes" />
								</rich:column>
								<rich:column rowspan="2" width="45px">
									<h:outputText value="HInf" />
								</rich:column>
								<rich:column rowspan="2" width="45px">
									<h:outputText value="Incentivos" />
								</rich:column>
								<rich:column rowspan="2" width="45px">
									<h:outputText value="IInf" />
								</rich:column>
								<rich:column rowspan="2" width="25px">
									<h:outputText value="Otras Planillas" />
								</rich:column>
								<rich:column rowspan="2" width="45px">
									<h:outputText value="Envio Total" />
								</rich:column>
								<rich:column breakBefore="true" width="45px">
									<h:outputText value="Haberes" />
								</rich:column>
								<rich:column width="45px">
									<h:outputText value="Incentivos" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						
						<rich:column width="10px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>

						<rich:column width="15px">
							<h:outputText value="#{item.strCodigoPersona}" />
						</rich:column>

						<rich:column width="250px"
							rendered="#{!item.blnLIC && !item.blnDJUD && !item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
							</div>
						</rich:column>

						<rich:column width="250px"
							rendered="#{item.blnLIC || item.blnDJUD || item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnLIC}" value=" (LIC)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnDJUD}" value=" (DJUD)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnCartaAutorizacion}" value=" (100%)" />
							</div>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberesEfectuado}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdIncentivosEfectuado}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="60px">
							<h:outputText value="#{item.documento.strNumeroIdentidad}" />
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberes}"
								rendered="#{item.tieneHaber}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberesI}"
								rendered="#{item.bdHaberesI != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdIncentivos}"
								rendered="#{item.tieneIncentivo}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdIncentivosI}"
								rendered="#{item.bdIncentivosI != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="25px" style="text-align: right">
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdEnvioTotal}" >
<!--								rendered="#{item.bdEnvioTotal != 0}"-->
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="200px">
							<div align="center">
								<a4j:commandButton value="InflarMonto"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.editEnvioInflada}"
									disabled="#{envioController.habilitarInflada || (item.bdHaberes == 0 && item.bdIncentivos == 0)}"
									rendered="#{item.bdHaberesI > 0 || item.bdIncentivosI > 0}"
									oncomplete="Richfaces.showModalPanel('pInflada')"
									reRender="pInflada,fInfladaPlanilla,grillaMontoInflada,panelAgregar"
									style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>

								<a4j:commandButton value="InflarMonto"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.verInflada}"
									disabled="#{envioController.habilitarInflada ||
 									             (item.bdHaberes == 0 && item.bdIncentivos == 0)}"
									rendered="#{item.bdHaberesI == 0 && item.bdIncentivosI == 0}"
									oncomplete="Richfaces.showModalPanel('pInflada')"
									reRender="pInflada,fInfladaPlanilla,grillaMontoInflada"
									style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>
								<a4j:commandButton value="Tipo Planilla"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.verTipoPlanilla}"
									disabled="#{(item.bdHaberes == 0 && item.bdIncentivos == 0)
 												|| envioController.blnEnviadaHaber
									 			|| envioController.blnEnviadaIncentivo || !envioController.blnEstructuraDetalle}"
									oncomplete="Richfaces.showModalPanel('pTipoPlanilla')"
									rendered="#{item.intCantModalidades == 1
									&& envioController.dtoDeEnvio.blnActivo
									&& (!envioController.blnPlanillaEnviadaHaber
									 || !envioController.blnPlanillaEnviadaIncentivo)}"
									reRender="pTipoPlanilla,fTipoPlanilla" style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>

								<a4j:commandButton value="Monto Envio"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.verEnvioMonto}"
									oncomplete="Richfaces.showModalPanel('pMontoEnvio')"
									reRender="pMontoEnvio,fEnvioMonto"
									disabled="#{(item.bdHaberes == 0 && item.bdIncentivos == 0)
									 			 || envioController.blnEnviadaHaber
									 			 || envioController.blnEnviadaIncentivo}"
									rendered="#{envioController.dtoDeEnvio.blnActivo								      
								    && (!envioController.blnPlanillaEnviadaHaber
								    || !envioController.blnPlanillaEnviadaIncentivo)
								    && item.intCantModalidades==2}"
									style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>
							</div>
						</rich:column>
						
						<f:facet name="footer">
							<rich:datascroller for="idGrillaDePlanillaEnvio" maxPages="15" />
						</f:facet>
						
					</rich:dataTable>
					
					 <rich:dataTable bgcolor="#639BC8" style="color: white;"
						id="idGrillaDePlanillaEnvioCESANTE" var="item" sortMode="single"
						rows="15" value="#{envioController.planilla}"						
						rendered="#{!envioController.dtoDeEnvio.blnActivo && not empty envioController.planilla}"
						width="970px" rowKeyVar="rowKey">

						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column colspan="5">
									<h:outputText
										value="#{fn:length(envioController.planilla)} Socio(s) en total ( #{envioController.nroHaberesEnviado} haberes)" />
								</rich:column>
								
								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalHaberes}"
										rendered="#{envioController.totalHaberes != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalHaberI}"
										rendered="#{envioController.totalHaberI != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>

								<rich:column rowspan="1" style="text-align: right">
									<h:outputText value="#{envioController.totalTotal}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="3" width="200px">
									<h:outputText value="Opciones (Modificar)" />
								</rich:column>

								<rich:column breakBefore="true" rowspan="2" width="10px">
									<h:outputText value="N" />
								</rich:column>
								
								<rich:column rowspan="2" width="15px">
									<h:outputText value="Codigo" />
								</rich:column>
								
								<rich:column rowspan="2" width="250px">
									<h:outputText value="Nombre Completo" />
								</rich:column>
								
								<rich:column rowspan="2" width="90px">
									<h:outputText value="Efectuado Haber del Mes Anterior" />
								</rich:column>
								
								<rich:column rowspan="2" width="60px">
									<h:outputText value="DNI" />
								</rich:column>

								<rich:column rowspan="2" width="45px">
									<h:outputText value="Haberes" />
								</rich:column>
								
								<rich:column rowspan="2" width="45px">
									<h:outputText value="HInf" />
								</rich:column>

								<rich:column rowspan="2" width="45px">
									<h:outputText value="Envio Total" />
								</rich:column>

							</rich:columnGroup>
						</f:facet>
						
						<rich:column width="10px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>

						<rich:column width="15px">
							<h:outputText value="#{item.strCodigoPersona}" />
						</rich:column>

						<rich:column width="250px"
							rendered="#{!item.blnLIC && !item.blnDJUD && !item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
							</div>
						</rich:column>

						<rich:column width="250px"
							rendered="#{item.blnLIC || item.blnDJUD || item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnLIC}" value=" (LIC)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnDJUD}" value=" (DJUD)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnCartaAutorizacion}" value=" (100%)" />
							</div>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberesEfectuado}"
								rendered="#{item.bdHaberesEfectuado != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>


						<rich:column width="60px">
							<h:outputText value="#{item.documento.strNumeroIdentidad}" />
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberes}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						
						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdHaberesI}"
								rendered="#{item.bdHaberesI != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="45px" style="text-align: right">
							<h:outputText value="#{item.bdEnvioTotal}" >
<!--								rendered="#{item.bdEnvioTotal != 0}"-->
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="200px">
							<div align="center">
								<a4j:commandButton value="InflarMonto"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.editEnvioInflada}"
									disabled="#{envioController.habilitarInflada || (item.bdHaberes == 0 && item.bdIncentivos == 0)}"
									rendered="#{item.bdHaberesI > 0 || item.bdIncentivosI > 0}"
									oncomplete="Richfaces.showModalPanel('pInflada')"
									reRender="pInflada,fInfladaPlanilla,grillaMontoInflada,panelAgregar"
									style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>

								<a4j:commandButton value="InflarMonto"
									styleClass="btnEstilosPlanilla"
									actionListener="#{envioController.verInflada}"
									disabled="#{envioController.habilitarInflada ||
 									             (item.bdHaberes == 0 && item.bdIncentivos == 0)}"
									rendered="#{item.bdHaberesI == 0 && item.bdIncentivosI == 0}"
									oncomplete="Richfaces.showModalPanel('pInflada')"
									reRender="pInflada,fInfladaPlanilla,grillaMontoInflada"
									style="width:75px">
									<f:attribute name="item" value="#{item}" />
								</a4j:commandButton>

							</div>
						</rich:column>
						<f:facet name="footer">
							<rich:datascroller for="idGrillaDePlanillaEnvioCESANTE"
								maxPages="15" />
						</f:facet>
						
					</rich:dataTable>
					
					 <rich:dataTable id="idGrillaDePlanillaEnvioCAS" var="item"
						sortMode="single" rows="15" width="970px"
						value="#{envioController.planillaCAS}" rowKeyVar="rowKey"						
						rendered="#{not empty envioController.planillaCAS}">

						<f:facet name="header">
							<rich:columnGroup columnClasses="rich-sdt-header-cell">
								<rich:column colspan="5">
									<h:outputText
										value="#{fn:length(envioController.planillaCAS)} Socio(s) cas en total" />
								</rich:column>

								<rich:column rowspan="1" width="70px" style="text-align: right">
									<h:outputText value="#{envioController.totalCas}"
										rendered="#{envioController.totalCas != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" width="10px" style="text-align: right">
									<h:outputText style="font-weight:bold; font-size:12px"
										value="#{envioController.totalCasI}"
										rendered="#{envioController.totalCasI != 0}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="1" width="70px" style="text-align: right">
									<h:outputText
										value="#{envioController.totalTotalCasTotalCasI}">
										<f:converter converterId="ConvertidorMontos" />
									</h:outputText>
								</rich:column>
								
								<rich:column rowspan="3" width="250px">
									<h:outputText value="Opciones (Modificar)" />
								</rich:column>

								<rich:column breakBefore="true" rowspan="2" width="100px">
									<h:outputText value="N" />
								</rich:column>
								
								<rich:column rowspan="2" width="100px">
									<h:outputText value="Codigo" />
								</rich:column>
								
								<rich:column rowspan="2" width="500px">
									<h:outputText value="Nombre Completo" />
								</rich:column>
								
								<rich:column rowspan="2">
									<h:outputText value="Efectuado CAS" />
								</rich:column>
								
								<rich:column rowspan="2" width="60px">
									<h:outputText value="DNI" />
								</rich:column>
								
								<rich:column rowspan="2" width="70px">
									<h:outputText value="CAS" />
								</rich:column>
								
								<rich:column rowspan="2" width="10px">
									<h:outputText value="CAS INFLADA" />
								</rich:column>
								
								<rich:column rowspan="2" width="70px">
									<h:outputText value="Envio Total" />
								</rich:column>

							</rich:columnGroup>
						</f:facet>
						<rich:column width="30px">
							<div align="center">
								<h:outputText value="#{rowKey+1}" />
							</div>
						</rich:column>
						
						<rich:column width="60px">
							<h:outputText value="#{item.strCodigoPlanilla}" />
						</rich:column>
						
						<rich:column width="250px"
							rendered="#{!item.blnLIC && !item.blnDJUD && !item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
							</div>
						</rich:column>

						<rich:column width="250px"
							rendered="#{item.blnLIC || item.blnDJUD || item.blnCartaAutorizacion}">
							<div align="left">
								<h:outputText
									value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc} " />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnLIC}" value=" (LIC)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnDJUD}" value=" (DJUD)" />
								<h:outputText styleClass="msgInfo" style="font-weight:bold"
									rendered="#{item.blnCartaAutorizacion}" value=" (100%)" />
							</div>
						</rich:column>
						
						<rich:column width="60px" style="text-align: right">
							<h:outputText value="#{item.bdCasEfectuado}"
								rendered="#{item.bdCasEfectuado != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						
						<rich:column width="70px">
							<h:outputText value="#{item.documento.strNumeroIdentidad}" />
						</rich:column>

						<rich:column width="70px" style="text-align: right">
							<h:outputText value="#{item.bdCas}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>

						<rich:column width="10px" style="text-align: right">
							<h:outputText value="#{item.bdCasI}"
								rendered="#{item.bdCasI != 0}">
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						
						<rich:column width="70px" style="text-align: right">
							<h:outputText value="#{item.bdTotalCasCasI}" >
<!--								rendered="#{item.bdTotalCasCasI != 0}"-->
								<f:converter converterId="ConvertidorMontos" />
							</h:outputText>
						</rich:column>
						
						<rich:column width="70px" style="text-align: center">
							<a4j:commandButton value="InflarMonto"
								styleClass="btnEstilosPlanilla"
								actionListener="#{envioController.editEnvioInflada}"
								disabled="#{envioController.habilitarInflada || (item.bdCas == 0)}"
								rendered="#{item.bdCasI > 0}"
								oncomplete="Richfaces.showModalPanel('pInflada')"
								reRender="pInflada,fInfladaPlanilla,grillaMontoInflada,panelAgregar,panelGrabarCancelar"
								style="width:75px">
								<f:attribute name="item" value="#{item}" />
							</a4j:commandButton>

							<a4j:commandButton value="InflarMonto"
								styleClass="btnEstilosPlanilla"
								actionListener="#{envioController.verInfladaCAS}"
								disabled="#{envioController.habilitarInflada ||(item.bdCas == 0)}"
								rendered="#{item.bdCasI == 0}"
								oncomplete="Richfaces.showModalPanel('pInflada')"
								reRender="pInflada,fInfladaPlanilla,grillaMontoInflada,panelGrabarCancelar"
								style="width:75px">
								<f:attribute name="item" value="#{item}" />
							</a4j:commandButton>

						</rich:column>
						<f:facet name="footer">
							<rich:datascroller for="idGrillaDePlanillaEnvioCAS" maxPages="10" />
						</f:facet>
					</rich:dataTable>
					
					<h:outputLink value="#">
						<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"
									   onclick="Richfaces.showModalPanel('idPopupAbreviatura')" />  
					</h:outputLink>
					
				</h:panelGroup>
			</rich:panel>	 
		</h:panelGroup>
		
	</rich:panel>
</h:form>