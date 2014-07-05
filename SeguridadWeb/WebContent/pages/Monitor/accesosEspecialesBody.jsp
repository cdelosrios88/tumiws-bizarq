<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fehca     : 201108191122             -->
	
<script type="text/javascript">
	function jsSelectAccesosEspeciales(idempresa,idusuario,fechainicio,nombreTab){
		//alert("idempresa: "+idempresa+" idusuario: "+idusuario+" fechainicio: "+fechainicio);
        document.getElementById("formUpDelAccesos:hiddenIdEmpAcceso").value=idempresa;
        document.getElementById("formUpDelAccesos:hiddenIdUsuAcceso").value=idusuario;
        document.getElementById("formUpDelAccesos:hiddenFechaIniAcceso").value=fechainicio;
        document.getElementById("formUpDelAccesos:hiddenNombreTab").value=nombreTab;
    }
</script>

	<rich:modalPanel id="mpUpDelAccesos" width="380" height="120"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Accesos Especiales" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAccesosFH"/>
               <rich:componentControl for="mpUpDelAccesos" attachTo="hidelinkAccesosFH" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formUpDelAccesos">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. Actualizar o Eliminar este registro?" styleClass="tamanioLetra" style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{accesoEspecialController.modificarRegistroAcceso}" styleClass="btnEstilos"></h:commandButton>
                    <h:commandButton value="Eliminar" actionListener="#{accesoEspecialController.eliminarAccesoEspecial}" styleClass="btnEstilos"></h:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdEmpAcceso"></h:inputHidden>
					<h:inputHidden id="hiddenIdUsuAcceso"></h:inputHidden>
					<h:inputHidden id="hiddenFechaIniAcceso"></h:inputHidden>
					<h:inputHidden id="hiddenNombreTab"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
	</rich:modalPanel>
		
<h:form id="formAccesosEspeciales">
  <rich:tabPanel id="tbAccesosEspeciales" switchType="ajax">
 	<rich:tab  id="tbAccesosFH" label="Accesos Fuera de Hora"  styleClass="tamanioLetra">
 		
        <rich:panel style="width: 760px ;background-color: #DEEBF5">
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Empresa :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="230" style="border: none">
            		<h:selectOneMenu id="cboEmpAccesoFHBusq" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadCboUsuario}"
				                   	value="#{accesoEspecialController.intCboEmpAccesoFH}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						<a4j:support event="onchange" reRender="cboUsuAccesoFHBusq, cboResponsableFHBusq" ajaxSingle="true" />
					</h:selectOneMenu>
            	</rich:column >
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Estado :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="100" style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboEstadoFH}">
            			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>            		
            	</rich:column>
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Motivo :" style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboMotivoFH}" style="width: 120px">
            			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOSACCESO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                </h:selectOneMenu>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Usuario :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="240" style="border: none">
            		<h:selectOneMenu id="cboUsuAccesoFHBusq" value="#{accesoEspecialController.intCboUsuAccesoFH}"
	               			style="width:220px;">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Autorización :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:selectOneMenu id="cboResponsableFHBusq" style="width:220px;"
	                	value="#{accesoEspecialController.intCboResponsableFH}">
	                	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
	                	<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:commandButton value="Buscar" actionListener= "#{accesoEspecialController.listarAccesosFueraHora}" styleClass="btnEstilos" ></h:commandButton>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="4">
            	<rich:column width="" style="border: none">
            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Rango de fecha :"  styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtInicioFHBusq}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
                <rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtFinFHBusq}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid columns="2" style="z-index: 10">
	            <rich:column width="35" style="border: none">
	            </rich:column>
	            <rich:column style="border: none">
	                <rich:scrollableDataTable id="tbAccesosFueraHora" value="#{accesoEspecialController.beanListAccesosFueraHora}" rows="10" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty accesoEspecialController.beanListAccesosFueraHora}"
	                            	onRowClick="jsSelectAccesosEspeciales(#{item.intIdEmpresa},#{item.intIdUsuario},'#{item.strFechaInicio}','accesosFH');" width="645px" height="165px"> 
	                	<rich:column width="29px">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="160px">
	                        <f:facet name="header">
	                            <h:outputText value="Nombre de Usuario"></h:outputText>
	                        </f:facet>
	                        <h:outputText id="lblAFHUsuario" value="#{item.strFullNameUsu}"></h:outputText>
	                        <rich:toolTip for="lblAFHUsuario" value="#{item.strFullNameUsu}"></rich:toolTip>
	                    </rich:column>
	                    <rich:column sortBy="#{item.intIdEstado}">
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEstado}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Empresa"></h:outputText>
	                        </f:facet>
	                        <h:outputText id="lblAFHEmpresa" value="#{item.strEmpresa}"></h:outputText>
	                        <rich:toolTip for="lblAFHEmpresa" value="#{item.strEmpresa}"></rich:toolTip>
	                    </rich:column>
	                    <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Tipo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strTipoUsuario}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="200px">
	                        <f:facet name="header">
	                            <h:outputText value="Fechas"></h:outputText>
	                        </f:facet>
	                        <h:outputText id="lblAFHRangoFecha" value="#{item.strRangoFechas}"></h:outputText>
	                        <rich:toolTip for="lblAFHRangoFecha" value="#{item.strRangoFechas}"></rich:toolTip>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Autorizado por"></h:outputText>
	                        </f:facet>
	                        <h:outputText id="lblAFHResp"  value="#{item.strFullNameResp}"></h:outputText>
	                        <rich:toolTip for="lblAFHResp" value="#{item.strFullNameResp}"></rich:toolTip>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha de Registro"></h:outputText>
	                        </f:facet>
	                        <h:outputText id="lblAFHRegPc" value="#{item.strFechaRegistro}"></h:outputText>
	                        <rich:toolTip for="lblAFHRegPc" value="#{item.strFechaRegistro}"></rich:toolTip>
	                    </rich:column>
	                </rich:scrollableDataTable>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="2">
            	<rich:column width="35" style="border: none">
            	</rich:column>
            	<rich:column style="border: none">
            		<h:outputLink value="#" id="linkAccesosFH">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelAccesos" attachTo="linkAccesosFH" operation="show" event="onclick"/>
					</h:outputLink>
		            <h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>                                     
            </h:panelGrid>                                                                        
           <br/>  
           <h:panelGrid columns="3">
           		<h:commandButton value="Nuevo" actionListener="#{accesoEspecialController.habilitarGrabarAccesoFH}" styleClass="btnEstilos"/>
               	<h:commandButton value="Guardar" actionListener="#{accesoEspecialController.grabarAccesoEspecialFH}" styleClass="btnEstilos"/>
               	<h:commandButton value="Cancelar" actionListener="#{accesoEspecialController.cancelarGrabarAccesoFH}" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel rendered="#{accesoEspecialController.blnAccesosFH}" style="width: 735px ;background-color: #DEEBF5;border:2px solid #17356f;">
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Empresa : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboEmpAccesoFH" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadCboUsuario}"
				                   	value="#{accesoEspecialController.beanAccesoFueraHora.intIdEmpresa}">      
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							<a4j:support event="onchange" reRender="cboUsuAccesoFH, cboResponsableFH" ajaxSingle="true" />
						</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Usuario : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboUsuAccesoFH" style="width:220px;"
                			value="#{accesoEspecialController.beanAccesoFueraHora.intIdUsuario}"
                			valueChangeListener="#{accesoEspecialController.loadSucursalxUsuarioFH}">
			               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			               	<a4j:support event="onchange" reRender="pgSucursalPerfilFH, tbUsuSucursalFH, tbUsuPerfilFH" ajaxSingle="true" />
			        	</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none"/>
                	<rich:column style="border: none">
	                	<h:panelGrid id="pgSucursalPerfilFH" columns="2">
	                		<rich:column width="190" style="border: none">
		                		<rich:dataTable id="tbUsuSucursalFH" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
			                            	value="#{accesoEspecialController.beanListSucursalesUsuFH}"
			                            	rendered="#{not empty accesoEspecialController.beanListSucursalesUsuFH}"> 
				                	<rich:column width="19">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column>
				                        <f:facet name="header">
				                            <h:outputText value="Sucursales" styleClass="tamanioLetra"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.strSucursal}"></h:outputText>
				                    </rich:column>
				                </rich:dataTable>
			                </rich:column>
			                <rich:column style="border: none;">
			                	<rich:dataTable id="tbUsuPerfilFH" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
		                            	value="#{accesoEspecialController.beanListPerfilesUsuFH}"
		                            	rendered="#{not empty accesoEspecialController.beanListPerfilesUsuFH}"> 
			                	<rich:column width="19">
			                        <h:outputText value="#{rowKey + 1}"></h:outputText>
			                    </rich:column>
			                    <rich:column>
			                        <f:facet name="header">
			                            <h:outputText value="Perfiles" styleClass="tamanioLetra"></h:outputText>
			                        </f:facet>
			                        <h:outputText value="#{item.strDescPerfil}"></h:outputText>
			                    </rich:column>
			                </rich:dataTable>
			                </rich:column>
			            </h:panelGrid>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Inicio : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoFueraHora.dtFechaInicio}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Fin : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoFueraHora.dtFechaFin}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Día de la semana" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: 1px solid #17356f">
	            		<h:selectManyCheckbox value="#{accesoEspecialController.listDiasFH}" styleClass="tamanioLetra">
		            		<f:selectItems value="#{accesoEspecialController.beanListDiasFH}"></f:selectItems>
	                    </h:selectManyCheckbox>
	            	</rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox value="#{accesoEspecialController.beanAccesoFueraHora.blnAccesoFeriados}"></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Considerar Feriados de Calendario" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" style="border: 1px solid #17356f;">
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="padding-bottom: 30px">
		                	<h:outputText value="Observación : " styleClass="tamanioLetra"></h:outputText>
		                </rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border-left: 1px solid #17356f; border-right: 1px solid #17356f;">
                		<rich:column style="border: none">
	                		<h:outputText value="Motivo : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu value="#{accesoEspecialController.beanAccesoFueraHora.intIdMotivo}" style="width: 120px">
	                			<f:selectItems value="#{parametersController.cboMotivosAcceso}"></f:selectItems>
	                		</h:selectOneMenu>
	                	</rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="border: none">
	                		<h:outputText value="Autorizado por : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu id="cboResponsableFH" style="width:220px;"
	                			value="#{accesoEspecialController.beanAccesoFueraHora.intIdResponsable}">
				               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        		</h:selectOneMenu>
	                	</rich:column>
                	</h:panelGrid> 
                </h:panelGrid>
                <h:panelGrid style="border: 1px solid #17356f; border-top: none;">
                	<rich:column style="border: none">
	               		<h:inputTextarea value="#{accesoEspecialController.beanAccesoFueraHora.strObservacion}" cols="105" rows="4"></h:inputTextarea>
	               	</rich:column>
                </h:panelGrid>
  				          
            </rich:panel>           
  		</rich:panel>
  		
  	</rich:tab>
  	
  	<rich:tab  id="tbAccesosSucursal" label="Accesos desde Otra Sucursal"  styleClass="tamanioLetra">
 		
        <rich:panel style="width: 760px ;background-color: #DEEBF5">
          
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Empresa :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="230" style="border: none">
            		<h:selectOneMenu id="cboEmpAccesoSucBusq" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadCboUsuario}"
				                   	value="#{accesoEspecialController.intCboEmpAccesoSuc}">      
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						<a4j:support event="onchange" reRender="cboUsuAccesoSucBusq, cboResponsableSucBusq" ajaxSingle="true" />
					</h:selectOneMenu>
            	</rich:column >
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Estado :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="100" style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboEstadoSuc}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Motivo :" value="Motivo :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboMotivoSuc}" style="width: 120px">
	                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOSACCESO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                </h:selectOneMenu>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Usuario :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="240" style="border: none">
            		<h:selectOneMenu id="cboUsuAccesoSucBusq" value="#{accesoEspecialController.intCboUsuAccesoSuc}"
	               			style="width:220px;">
			               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Autorización :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:selectOneMenu id="cboResponsableSucBusq" style="width:220px;"
	                	value="#{accesoEspecialController.intCboResponsableSuc}">
				        <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:commandButton value="Buscar" actionListener= "#{accesoEspecialController.listarAccesosSucursales}" styleClass="btnEstilos" ></h:commandButton>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="4">
            	<rich:column width="" style="border: none">
            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Rango de fecha :"  styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtRangoInicioSuc}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
                <rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtRangoFinSuc}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid columns="2">
	            <rich:column width="35" style="border: none">
	            </rich:column>
	            <rich:column style="border: none">
	                <rich:scrollableDataTable id="tbAccesosSucursales" value="#{accesoEspecialController.beanListAccesoSucursales}" rows="10" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty accesoEspecialController.beanListAccesoSucursales}"
	                            	onRowClick="jsSelectAccesosEspeciales(#{item.intIdEmpresa},#{item.intIdUsuario},'#{item.strFechaInicio}','accesosSucursales');" width="645px" height="165px"> 
	                	<rich:column width="29">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Nombre de Usuario"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFullNameUsu}"></h:outputText>
	                    </rich:column>
	                    <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEstado}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="100">
	                        <f:facet name="header">
	                            <h:outputText value="Empresa"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEmpresa}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="80">
	                        <f:facet name="header">
	                            <h:outputText value="Tipo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strTipoUsuario}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="120">
	                        <f:facet name="header">
	                            <h:outputText value="Sucursal"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strSucursal}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="190">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strRangoFechas}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Autorizado por"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFullNameResp}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="120">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha de Registro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFechaRegistro}"></h:outputText>
	                    </rich:column>
	                    <f:facet name="footer">   
				        	<rich:datascroller for="tbAccesosSucursales" maxPages="5"/>   
				        </f:facet>
	                </rich:scrollableDataTable>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="2">
            	<rich:column width="35" style="border: none">
            	</rich:column>
            	<rich:column style="border: none">
            		<h:outputLink value="#" id="linkAccesosSuc">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelAccesos" attachTo="linkAccesosSuc" operation="show" event="onclick"/>
					</h:outputLink>
		            <h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>                                     
            </h:panelGrid>                                                                        
           <br/>  
           <h:panelGrid columns="3">
           		<h:commandButton value="Nuevo" actionListener="#{accesoEspecialController.habilitarGrabarAccesoSuc}" styleClass="btnEstilos"/>
               	<h:commandButton value="Guardar" actionListener="#{accesoEspecialController.grabarAccesoSucursal}" styleClass="btnEstilos"/>
               	<h:commandButton value="Cancelar" actionListener="#{accesoEspecialController.cancelarGrabarAccesoSuc}" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel rendered="#{accesoEspecialController.blnAccesosSuc}" style="width: 735px ;background-color: #DEEBF5;border:2px solid #17356f;">
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Empresa : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboEmpAccesoSuc" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadUsuariosSucursalesxEmpresa}"
				                   	value="#{accesoEspecialController.beanAccesoSucursal.intIdEmpresa}">      
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							<a4j:support event="onchange" reRender="cboUsuAccesoSuc, cboResponsableSuc,cboSucAccesoSuc" ajaxSingle="true" />
						</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Usuario : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboUsuAccesoSuc" style="width:220px;"
                			value="#{accesoEspecialController.beanAccesoSucursal.intIdUsuario}"
                			valueChangeListener="#{accesoEspecialController.loadSucursalxUsuarioSuc}">
			               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			               	<a4j:support event="onchange" reRender="pgSucursalPerfilSuc, tbUsuSucursalSuc, tbUsuPerfilSuc" ajaxSingle="true" />
			        	</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none"/>
                	<rich:column style="border: none">
	                	<h:panelGrid id="pgSucursalPerfilSuc" columns="2">
	                		<rich:column width="190" style="border: none">
		                		<rich:dataTable id="tbUsuSucursalSuc" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
			                            	value="#{accesoEspecialController.beanListSucursalesUsuSuc}"
			                            	rendered="#{not empty accesoEspecialController.beanListSucursalesUsuSuc}"> 
				                	<rich:column width="19">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column>
				                        <f:facet name="header">
				                            <h:outputText value="Sucursales" styleClass="tamanioLetra"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.strSucursal}"></h:outputText>
				                    </rich:column>
				                    <f:facet name="footer">   
							        	<rich:datascroller for="tbUsuSucursalSuc" maxPages="5"/>   
							        </f:facet>
				                </rich:dataTable>
			                </rich:column>
			                <rich:column style="border: none;">
			                	<rich:dataTable id="tbUsuPerfilSuc" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
		                            	value="#{accesoEspecialController.beanListPerfilesUsuSuc}"
		                            	rendered="#{not empty accesoEspecialController.beanListPerfilesUsuSuc}"> 
				                	<rich:column width="19">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column>
				                        <f:facet name="header">
				                            <h:outputText value="Perfiles" styleClass="tamanioLetra"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.strDescPerfil}"></h:outputText>
				                    </rich:column>
				                    <f:facet name="footer">   
							        	<rich:datascroller for="tbUsuPerfilSuc" maxPages="5"/>   
							        </f:facet>
				                </rich:dataTable>
			                </rich:column>
			            </h:panelGrid>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Inicio : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoSucursal.dtFechaInicio}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Fin : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoSucursal.dtFechaFin}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Día de la semana" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: 1px solid #17356f">
	            		<h:selectManyCheckbox value="#{accesoEspecialController.listDiasSuc}" styleClass="tamanioLetra">
		            		<f:selectItems value="#{accesoEspecialController.beanListDiasSuc}"></f:selectItems>
	                    </h:selectManyCheckbox>
	            	</rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="3">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox value="#{accesoEspecialController.beanAccesoSucursal.blnAccesoFeriados}"></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Considerar Feriados de Calendario" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3">
                	<rich:column style="border: none">
                		<h:outputText value="Sucursal : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboSucAccesoSuc" style="width: 220px;"
				                   	value="#{accesoEspecialController.beanAccesoSucursal.intIdSucursal}"> 
							<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listSucursal}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreComercial}"/>
						</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" style="border: 1px solid #17356f;">
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="padding-bottom: 30px">
		                	<h:outputText value="Observación : " styleClass="tamanioLetra"></h:outputText>
		                </rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border-left: 1px solid #17356f; border-right: 1px solid #17356f;">
                		<rich:column style="border: none">
	                		<h:outputText value="Motivo : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu value="#{accesoEspecialController.beanAccesoSucursal.intIdMotivo}" style="width: 120px">
	                			<f:selectItems value="#{parametersController.cboMotivosAcceso}"></f:selectItems>
	                		</h:selectOneMenu>	                		
	                	</rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="border: none">
	                		<h:outputText value="Autorizado por : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu id="cboResponsableSuc" style="width:220px;"
	                			value="#{accesoEspecialController.beanAccesoSucursal.intIdResponsable}">
				               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        		</h:selectOneMenu>
	                	</rich:column>
                	</h:panelGrid> 
                </h:panelGrid>
                <h:panelGrid style="border: 1px solid #17356f; border-top: none;">
                	<rich:column style="border: none">
	               		<h:inputTextarea value="#{accesoEspecialController.beanAccesoSucursal.strObservacion}" cols="105" rows="4"></h:inputTextarea>
	               	</rich:column>
                </h:panelGrid>
  				          
            </rich:panel>           
  		</rich:panel>
  		
  	</rich:tab>
  	
  	<rich:tab  id="tbAccesosCabina" label="Accesos por Cabina"  styleClass="tamanioLetra">
 		
        <rich:panel style="width: 760px ;background-color: #DEEBF5">
          
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Empresa :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="230" style="border: none">
            		<h:selectOneMenu id="cboEmpAccesoCabBusq" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadCboUsuario}"
				                   	value="#{accesoEspecialController.intCboEmpAccesoCab}">      
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						<a4j:support event="onchange" reRender="cboUsuAccesoCabBusq, cboResponsableCabBusq" ajaxSingle="true" />
					</h:selectOneMenu>
            	</rich:column >
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Estado :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="100" style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboEstadoCab}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
            	</rich:column>
            	<rich:column width="70" style="border: none">
            		<h:outputText value="Motivo :" value="Motivo :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:selectOneMenu value="#{accesoEspecialController.intCboMotivoCab}" style="width: 120px">
	                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                    	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOSACCESO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	                </h:selectOneMenu>            		
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="6">
            	<rich:column width="74" style="border: none">
            		<h:outputText value="Usuario :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="240" style="border: none">
            		<h:selectOneMenu id="cboUsuAccesoCabBusq" value="#{accesoEspecialController.intCboUsuAccesoCab}"
	               			style="width:220px;">
			               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Autorización :" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:selectOneMenu id="cboResponsableCabBusq" style="width:220px;"
	                	value="#{accesoEspecialController.intCboResponsableCab}">
				        <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        </h:selectOneMenu>
            	</rich:column>
            	<rich:column style="border: none">
            		<h:commandButton value="Buscar" actionListener= "#{accesoEspecialController.listarAccesosCabinas}" styleClass="btnEstilos" ></h:commandButton>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="4">
            	<rich:column width="" style="border: none">
            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
            	</rich:column>
            	<rich:column width="" style="border: none">
            		<h:outputText value="Rango de fecha :"  styleClass="tamanioLetra"></h:outputText>
            	</rich:column>
            	<rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtRangoInicioCab}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
                <rich:column style="border: none">
               		<rich:calendar value="#{accesoEspecialController.dtRangoFinCab}" datePattern="dd/MM/yyyy" inputStyle="width: 70px" style="width: 170px"></rich:calendar>
                </rich:column>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid columns="2">
	            <rich:column width="35" style="border: none">
	            </rich:column>
	            <rich:column style="border: none">
	                <rich:scrollableDataTable id="tbAccesosCabinas" value="#{accesoEspecialController.beanListAccesoCabinas}" rows="10" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single" rendered="#{not empty accesoEspecialController.beanListAccesoCabinas}"
	                            	onRowClick="jsSelectAccesosEspeciales(#{item.intIdEmpresa},#{item.intIdUsuario},'#{item.strFechaInicio}','accesosCabinas');" width="645px" height="165px"> 
	                	<rich:column width="29">
	                        <h:outputText value="#{rowKey + 1}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Nombre de Usuario"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFullNameUsu}"></h:outputText>
	                    </rich:column>
	                    <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Estado"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEstado}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="100">
	                        <f:facet name="header">
	                            <h:outputText value="Empresa"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strEmpresa}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="80">
	                        <f:facet name="header">
	                            <h:outputText value="Tipo"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strTipoUsuario}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="190">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strRangoFechas}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="140">
	                        <f:facet name="header">
	                            <h:outputText value="Autorizado por"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFullNameResp}"></h:outputText>
	                    </rich:column>
	                    <rich:column width="120">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha de Registro"></h:outputText>
	                        </f:facet>
	                        <h:outputText value="#{item.strFechaRegistro}"></h:outputText>
	                    </rich:column>
	                    <f:facet name="footer">   
				        	<rich:datascroller for="tbAccesosCabinas" maxPages="5"/>   
				        </f:facet>
	                </rich:scrollableDataTable>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="2">
            	<rich:column width="35" style="border: none">
            	</rich:column>
            	<rich:column style="border: none">
            		<h:outputLink value="#" id="linkAccesosCab">
						<h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
						<rich:componentControl for="mpUpDelAccesos" attachTo="linkAccesosCab" operation="show" event="onclick"/>
					</h:outputLink>
		            <h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" styleClass="tamanioLetra"></h:outputText>
            	</rich:column>                                     
            </h:panelGrid>                                                                        
           <br/>  
           <h:panelGrid columns="3">
           		<h:commandButton value="Nuevo" actionListener="#{accesoEspecialController.habilitarGrabarAccesoCab}" styleClass="btnEstilos"/>
               	<h:commandButton value="Guardar" actionListener="#{accesoEspecialController.grabarAccesoCabina}" styleClass="btnEstilos"/>
               	<h:commandButton value="Cancelar" actionListener="#{accesoEspecialController.cancelarGrabarAccesoCab}" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel rendered="#{accesoEspecialController.blnAccesosCab}" style="width: 735px ;background-color: #DEEBF5;border:2px solid #17356f;">
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Empresa : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboEmpAccesoCab" required="true" style="width: 220px;" 
                  					valueChangeListener="#{controllerFiller.reloadCboUsuario}"
				                   	value="#{accesoEspecialController.beanAccesoCabina.intIdEmpresa}">      
							<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							<a4j:support event="onchange" reRender="cboUsuAccesoCab, cboResponsableCab,cboSucAccesoCab" ajaxSingle="true" />
						</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Usuario : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
                		<h:selectOneMenu id="cboUsuAccesoCab" style="width:220px;"
                			value="#{accesoEspecialController.beanAccesoCabina.intIdUsuario}"
                			valueChangeListener="#{accesoEspecialController.loadSucursalxUsuarioCab}">
			               		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			               	<a4j:support event="onchange" reRender="pgSucursalCab, tbUsuSucursalCab, tbUsuPerfilCab" ajaxSingle="true" />
			        	</h:selectOneMenu>
                	</rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none"/>
                	<rich:column style="border: none">
	                	<h:panelGrid id="pgSucursalCab" columns="2">
	                		<rich:column width="190" style="border: none">
		                		<rich:dataTable id="tbUsuSucursalCab" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
			                            	value="#{accesoEspecialController.beanListSucursalesUsuCab}"
			                            	rendered="#{not empty accesoEspecialController.beanListSucursalesUsuCab}"> 
				                	<rich:column width="19">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column>
				                        <f:facet name="header">
				                            <h:outputText value="Sucursales" styleClass="tamanioLetra"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.strSucursal}"></h:outputText>
				                    </rich:column>
				                    <f:facet name="footer">   
							        	<rich:datascroller for="tbUsuSucursalCab" maxPages="5"/>   
							        </f:facet>
				                </rich:dataTable>
			                </rich:column>
			                <rich:column style="border: none;">
			                	<rich:dataTable id="tbUsuPerfilCab" rows="5" var="item" rowKeyVar="rowKey" sortMode="single"
		                            	value="#{accesoEspecialController.beanListPerfilesUsuCab}"
		                            	rendered="#{not empty accesoEspecialController.beanListPerfilesUsuCab}"> 
				                	<rich:column width="19">
				                        <h:outputText value="#{rowKey + 1}"></h:outputText>
				                    </rich:column>
				                    <rich:column>
				                        <f:facet name="header">
				                            <h:outputText value="Perfiles" styleClass="tamanioLetra"></h:outputText>
				                        </f:facet>
				                        <h:outputText value="#{item.strDescPerfil}"></h:outputText>
				                    </rich:column>
				                    <f:facet name="footer">   
							        	<rich:datascroller for="tbUsuPerfilSuc" maxPages="5"/>   
							        </f:facet>
				                </rich:dataTable>
			                </rich:column>
			            </h:panelGrid>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Inicio : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoCabina.dtFechaInicio}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                	
                	<rich:column style="border: none">
                		<h:outputText value="Fecha Hora Fin : " styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none">
	               		<rich:calendar value="#{accesoEspecialController.beanAccesoCabina.dtFechaFin}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 120px" style="width: 170px"></rich:calendar>
	                </rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" border="0">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Día de la semana" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: 1px solid #17356f">
	            		<h:selectManyCheckbox value="#{accesoEspecialController.listDiasCab}" styleClass="tamanioLetra">
		            		<f:selectItems value="#{accesoEspecialController.beanListDiasCab}"></f:selectItems>
	                    </h:selectManyCheckbox>
	            	</rich:column>
                </h:panelGrid>
                
                <h:panelGrid columns="3">
                	<rich:column width="" style="border: none">
	            		<h:selectBooleanCheckbox value="#{accesoEspecialController.beanAccesoCabina.blnAccesoFeriados}"></h:selectBooleanCheckbox>
	            	</rich:column>
                	<rich:column style="border: none">
                		<h:outputText value="Considerar Feriados de Calendario" styleClass="tamanioLetra"></h:outputText>
                	</rich:column>
                	<rich:column style="border: none"/>
                </h:panelGrid>
                
                <h:panelGrid columns="3" style="border: 1px solid #17356f;">
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="padding-bottom: 30px">
		                	<h:outputText value="Observación : " styleClass="tamanioLetra"></h:outputText>
		                </rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border-left: 1px solid #17356f; border-right: 1px solid #17356f;">
                		<rich:column style="border: none">
	                		<h:outputText value="Motivo : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu value="#{accesoEspecialController.beanAccesoCabina.intIdMotivo}" style="width: 120px">
	                			<f:selectItems value="#{parametersController.cboMotivosAcceso}"></f:selectItems>
	                		</h:selectOneMenu>	                		
	                	</rich:column>
                	</h:panelGrid>
                	<h:panelGrid columns="1" style="border: none">
                		<rich:column style="border: none">
	                		<h:outputText value="Autorizado por : " styleClass="tamanioLetra"></h:outputText>
	                	</rich:column>
	                	<rich:column style="border: none">
	                		<h:selectOneMenu id="cboResponsableCab" style="width:220px;"
	                			value="#{accesoEspecialController.beanAccesoCabina.intIdResponsable}">
				               	<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{controllerFiller.cboUsuario}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
			        		</h:selectOneMenu>
	                	</rich:column>
                	</h:panelGrid> 
                </h:panelGrid>
                <h:panelGrid style="border: 1px solid #17356f; border-top: none;">
                	<rich:column style="border: none">
	               		<h:inputTextarea value="#{accesoEspecialController.beanAccesoCabina.strObservacion}" cols="105" rows="4"></h:inputTextarea>
	               	</rich:column>
                </h:panelGrid>
  				          
            </rich:panel>           
  		</rich:panel>
  		
  	</rich:tab>
  </rich:tabPanel>
</h:form>
	