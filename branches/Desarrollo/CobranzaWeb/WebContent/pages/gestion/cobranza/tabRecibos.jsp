<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi               -->
	<!-- Modulo    : Enlace y Desenlace de Recibos  -->
	<!-- Prototipo : Gestion Cobranza               -->			
	<!-- Fecha     : 26/10/2012                     -->
	
<script type="text/javascript">	  
      function soloNumeros(tecla)
      { 
        // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57   
         var keys = nav4 ? tecla.which : tecla.keyCode; 
        
         return ((keys <= 13 || (keys >= 50 && keys <= 57));
      }
</script>

<rich:panel id="pMarcoEnlaceRecibo" style="border:1px solid #17356f ;width: 1075px; background-color:#DEEBF5">
   
          <!-- INI - Filtro de Busqueda -->
	           <h:panelGrid columns="7" border="0" style="width: 1000px">
	         	    <rich:column style="width:30px; border: none">
	         		  <h:outputText  id="lblpSucursal" value="Sucursal :"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:100px; border: none">
		         	 <h:selectOneMenu id="cbopSucursal" style="width: 150px;"
							   value="#{enlaceReciboController.intCboSucursal}">
							   <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							   <tumih:selectItems var="sel" value="#{enlaceReciboController.listJuridicaSucursal}"
							   itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				      </h:selectOneMenu>
	         	    </rich:column>
	         	    <rich:column style="width:140px; border: none">
	         		  <h:outputText  id="lblpEstadoRecibo" value="Estado de Recibo :"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         	  		<h:selectOneMenu id="cbopFilEstadoRecibo" value="#{enlaceReciboController.intCboEstadoRecibo}" style="width:100px">
		         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_ESTADO_RECIBO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
		            </rich:column>
	         	    <rich:column style="width:140px; border: none">
	         		  <h:outputText  id="lblpNroRecibo" value="Número de Recibo :"  > </h:outputText>
	         	    </rich:column>
	         	    <rich:column style="width:115px; border: none">
	         		  <h:inputText  id="lblpValNroRecibo" value="#{enlaceReciboController.intInNroRecibo}"> </h:inputText>
	         	    </rich:column>
	         	    <rich:column style="width:80px; border: none">
	         		  <a4j:commandButton  value="Buscar" styleClass="btnEstilos" actionListener="#{enlaceReciboController.buscar}" reRender="pgFilEnlaceRecibo"/>
	         	    </rich:column>
	         </h:panelGrid>
        <!-- FIN - Filtro de Busqueda -->
        
        
            <h:panelGroup id="pgFilEnlaceRecibo" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilEnlaceRecibo" value="#{enlaceReciboController.beanListRecManDet}" 
                                 rendered="#{not empty enlaceReciboController.beanListRecManDet}" 
                            	 var="item" rowKeyVar="rowkey" sortMode="single"  width="1000px" height="240px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblNroRecibo" value="Nro Recibo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValNroRecibo" value="#{item.intNumeroRecibo}"></h:outputText>
                               </rich:column>
                               <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblNroIngreso" value="Nro Ingreso" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValNroIngreso" value="#{item.ingreso.intItemPeriodoIngreso}-#{item.intItemIngresoGeneral}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.ingreso.dtFechaIngreso}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblFechaIngreso" value="Fecha" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValFechaIngreso" value="#{item.ingreso.dtFechaIngreso}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:outputText>
                                    <rich:toolTip for="lblValFechaIngreso" value="#{item.ingreso.dtFechaIngreso}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="100px" >
                                    <f:facet name="header">
                                        <h:outputText id="lblMonto" value="Monto" ></h:outputText>
                                    </f:facet>
                                        <h:outputText id="lblValMonto" value="#{item.ingreso.bdMontoTotal}" ></h:outputText>
                                </rich:column>
                                <rich:column width="160px" sortBy="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblGestor" value="Gestor" ></h:outputText>
                                    </f:facet>
                                    <h:outputFormat></h:outputFormat>
                                    <h:outputText id="lblValGestor" value="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}" />
                                    <rich:toolTip for="lblValGestor" value="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblSucursal" value="Sucursal" ></h:outputText>
                                    </f:facet>
                                      <h:outputText id="lblValSucursal" value="#{item.strDesSucursal}" />
                                 </rich:column>
                                 <rich:column width="160px">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstadoCierreCaja" value="Estado de Cierre Caja" ></h:outputText>
                                    </f:facet>
                                 </rich:column>
                                 
                                 <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblEstadoRecibo" value="Estado de Recibo" ></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.reciboManual.intParaEstadoCierre}" style="width:110px" disabled="true">
						         		 	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_ESTADO_RECIBO}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
									</h:selectOneMenu>
                                 </rich:column>
                                <a4j:support event="onRowClick" actionListener="#{enlaceReciboController.seleccionar}" >
				                        	<f:attribute name="item" value="#{item}"/>
				                </a4j:support>
                                <f:facet name="footer">
							   		 <h:panelGroup layout="block">
								   		 <rich:datascroller for="tblFilEnlaceRecibo" maxPages="10"/>
								   		 <h:panelGroup layout="block" style="margin:0 auto; width:410px">
								   		 	<a4j:commandLink action="#">
												<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
											</a4j:commandLink>
											<h:outputText style="color:#8ca0bd">
											   Para
											   <a4j:commandButton actionListener="#{enlaceReciboController.desenlazar}" value="Desenlazar"
											                      reRender="divFormEnlaceRecibo,btnGrabar">
											   </a4j:commandButton>
											   un recibo cobranza hacer click en el Registro
											</h:outputText>
							   		 </h:panelGroup>
							   		 </h:panelGroup>  
						       </f:facet>       
                            </rich:extendedDataTable>      
               </h:panelGroup>
              
      <!-- Ini - Formulario-->   
      <h:panelGrid id="idBotones"  columns="4">
		 <a4j:commandButton value="Nuevo" styleClass="btnEstilos" actionListener="#{enlaceReciboController.nuevo}" 
		                            reRender="divFormEnlaceRecibo" >
		 </a4j:commandButton>
		 <a4j:commandButton id="btnGrabar"  disabled="#{enlaceReciboController.btnGrabarDisabled}" value="Grabar" actionListener="#{enlaceReciboController.grabar}" styleClass="btnEstilos"
		              				reRender="divFormEnlaceRecibo,pgFilEnlaceRecibo,btnGrabar" >
		 </a4j:commandButton>
		 <a4j:commandButton value="Cancelar" actionListener="#{enlaceReciboController.cancelar}" styleClass="btnEstilos"
		              				reRender="divFormEnlaceRecibo,btnGrabar" >
		 </a4j:commandButton>
	  </h:panelGrid>
	   
	  <!-- Form Enlace -->      
	 <h:panelGroup id="divFormEnlaceRecibo" layout="block" > 
	     <rich:panel style="width: 1060px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{enlaceReciboController.enlaceReciboRendered}">
	       <h:panelGrid  columns="8">
	         <rich:column style="width:100px; border: none">
         		<h:outputText  value="Sucursal :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:221px; border: none">
	         	<h:selectOneMenu id="cboSucursal" style="width: 220px;" 
					valueChangeListener="#{enlaceReciboController.listarSubSucursalPorSuc}"
					value="#{enlaceReciboController.beanRecManDet.reciboManual.intSucuIdSucursal}"
					disabled="#{enlaceReciboController.cboSucursalDisabled}">
					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
					<tumih:selectItems var="sel" value="#{enlaceReciboController.listJuridicaSucursal}"
					itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" reRender="cboSubSucursal" ajaxSingle="true"></a4j:support>
	             </h:selectOneMenu>
	     	</rich:column>
         	 <rich:column style="width:100px; border: none">
         		<h:outputText   value="Sub Sucursal:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:181px; border: none">
         		<h:selectOneMenu id="cboSubSucursal" style="width: 180px;"
						value="#{enlaceReciboController.beanRecManDet.reciboManual.intSudeIdSubsucursal}"
						disabled="#{enlaceReciboController.cboSubSucursalDisabled}">
						<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{enlaceReciboController.listJuridicaSubsucursal}"
						itemValue="#{sel.id.intIdSubSucursal}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
         	</rich:column>
         	<rich:column style="width:100px; border: none">
         		<h:outputText   value="Nro Recibo:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:140px; border: none">
         	    <h:inputText  value="#{enlaceReciboController.beanRecManDet.intNumeroRecibo}" onkeypress="return soloNumeros(event);" 
         	                  disabled="#{enlaceReciboController.texNumeroReciboDisabled}" /> 
         	</rich:column>
         	
         	<rich:column style="width:60px; border: none">
         	   <h:outputText rendered="#{enlaceReciboController.texGestorRedered}"   value="Gestor:"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:221px; border: none">
         	   <h:selectOneMenu id="cboGestor" style="width: 220px;" rendered="#{enlaceReciboController.texGestorRedered}" 
         	                    value="#{enlaceReciboController.beanRecManDet.intPersPersonaGestor}" >
         	   			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
						<tumih:selectItems var="sel" value="#{enlaceReciboController.listGestor}"
						itemValue="#{sel.empresaUsuario.usuario.persona.natural.intIdPersona}"
						itemLabel="#{sel.empresaUsuario.usuario.persona.natural.intIdPersona}-#{sel.empresaUsuario.usuario.persona.natural.strNombres} #{sel.empresaUsuario.usuario.persona.natural.strApellidoPaterno} #{sel.empresaUsuario.usuario.persona.natural.strApellidoMaterno}"/>
				</h:selectOneMenu>
         	</rich:column>
         </h:panelGrid>
	      
	       <h:panelGrid  columns="1">	
	         <rich:column style="width:800px; border: none">
	                   <a4j:commandButton rendered="#{enlaceReciboController.btnValidarRendered}" style="width:800px" 
	                     value="Validar" styleClass="btnEstilos" actionListener="#{enlaceReciboController.validar}" 
	                     reRender="divFormEnlaceRecibo,btnGrabar">
                       </a4j:commandButton>
	         </rich:column>	
	      </h:panelGrid>  
	      
	      <h:panelGrid  columns="3" rendered="#{enlaceReciboController.texGestorRedered}">
	         <rich:column style="width:80px; border: none">
         		<h:outputText  value="Ingreso :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:250px; border: none" id="txtDescIngreso">
         	    <h:inputText  value="#{enlaceReciboController.beanRecManDet.ingreso.intItemPeriodoIngreso}-#{enlaceReciboController.beanRecManDet.ingreso.intItemIngreso}-#{enlaceReciboController.strFechIngreso}-Monto:#{enlaceReciboController.beanRecManDet.ingreso.bdMontoTotal}" style="width:250px" disabled="true">
         	    </h:inputText>
         	 </rich:column>
         	<rich:column style="width:100px; border: none">
         	    <a4j:commandButton 
	                     value="Buscar" styleClass="btnEstilos" actionListener="#{enlaceReciboController.buscarIngresos}"
	                     oncomplete="Richfaces.showModalPanel('mpIngresos')" reRender="divIngresos">
                </a4j:commandButton>
         	</rich:column>
       </h:panelGrid> 	 
       
       <h:panelGrid  columns="3" rendered="#{enlaceReciboController.texGestorRedered}">
	         <rich:column style="width:80px; border: none">
         		<h:outputText  value="Razón :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
	         		<h:selectOneMenu id="cboFilRazon" value="#{enlaceReciboController.beanRecManDet.intParaTipoEnlace}" style="width:160px">
	         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORAZONENLACE}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	        </rich:column>
       </h:panelGrid> 
       
       <h:panelGrid  columns="3" rendered="#{enlaceReciboController.texGestorRedered}">
	         <rich:column style="width:80px; border: none">
         		<h:outputText  value="Observación :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:250px; border: none" >
         	    <h:inputText  value="#{enlaceReciboController.beanRecManDet.strObservacionAnula}" style="width:250px" >
         	    </h:inputText>
         	</rich:column>
         	
       </h:panelGrid> 	
       
     
    </rich:panel>
    <!-- Form desenlace -->
     <rich:panel style="width: 1060px;border:1px solid #880f;background-color:#DEEBF5;" rendered="#{!enlaceReciboController.enlaceReciboRendered && not empty enlaceReciboController.beanSelListRecManDet}">
     <h:panelGroup id="pgFilSelEnlaceRecibo" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilSelEnlaceRecibo" value="#{enlaceReciboController.beanSelListRecManDet}" 
                                 rendered="#{not empty enlaceReciboController.beanSelListRecManDet}" 
                            	 var="item" rowKeyVar="rowkey" sortMode="single"  width="1000px" height="55px" rows="1">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblSelNroRecibo" value="Nro Recibo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblSelValNroRecibo" value="#{item.intNumeroRecibo}"></h:outputText>
                               </rich:column>
                               <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblSelNroIngreso" value="Nro Ingreso" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblSelValNroIngreso" value="#{item.ingreso.intItemPeriodoIngreso}-#{item.intItemIngresoGeneral}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.ingreso.dtFechaIngreso}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblSelFechaIngreso" value="Fecha" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblSelValFechaIngreso" value="#{item.ingreso.dtFechaIngreso}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:outputText>
                                    <rich:toolTip for="lblSelValFechaIngreso" value="#{item.ingreso.dtFechaIngreso}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="100px" >
                                    <f:facet name="header">
                                        <h:outputText id="lblSelMonto" value="Monto" ></h:outputText>
                                    </f:facet>
                                        <h:outputText id="lblSelValMonto" value="#{item.ingreso.bdMontoTotal}" ></h:outputText>
                                </rich:column>
                                <rich:column width="160px" sortBy="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblSelGestor" value="Gestor" ></h:outputText>
                                    </f:facet>
                                    <h:outputFormat></h:outputFormat>
                                    <h:outputText id="lblSelValGestor" value="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}" />
                                    <rich:toolTip for="lblSelValGestor" value="#{item.gestor.strNombres} #{item.gestor.strApellidoPaterno} #{item.gestor.strApellidoMaterno}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblSelSucursal" value="Sucursal" ></h:outputText>
                                    </f:facet>
                                      <h:outputText id="lblSelValSucursal" value="#{item.strDesSucursal}" />
                                 </rich:column>
                                 <rich:column width="160px">
                                    <f:facet name="header">
                                        <h:outputText id="lblSelEstadoCierreCaja" value="Estado de Cierre Caja" ></h:outputText>
                                    </f:facet>
                                 </rich:column>
                                 
                                 <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblSelEstadoRecibo" value="Estado de Recibo" ></h:outputText>
                                    </f:facet>
                                      <h:selectOneMenu value="#{item.reciboManual.intParaEstadoCierre}" style="width:110px" disabled="true">
						         		 	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_ESTADO_RECIBO}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
												tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
									  </h:selectOneMenu>
                                 </rich:column>
                            </rich:extendedDataTable>      
               </h:panelGroup>
               
       <h:panelGrid  columns="3">
	         <rich:column style="width:80px; border: none">
         		<h:outputText  value="Razón :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
	         		<h:selectOneMenu value="#{enlaceReciboController.beanRecManDet.intParaTipoDesenlace}" style="width:160px">
	         		    <f:selectItem itemValue="0" itemLabel="Seleccione"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORAZONDESENLACE}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	        </rich:column>
       </h:panelGrid> 
       <h:panelGrid  columns="3" >
	         <rich:column style="width:80px; border: none">
         		<h:outputText  value="Observación :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:250px; border: none" >
         	    <h:inputText  value="#{enlaceReciboController.beanRecManDet.strObservacionAnula}" style="width:250px" >
         	    </h:inputText>
         	</rich:column>
      </h:panelGrid> 	
     </rich:panel>
</h:panelGroup>      	   
	        
</rich:panel>	