<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : Gestion Cobranza         -->			
	<!-- Fecha     : 22/10/2012               -->

<script type="text/javascript">	  
	  
// 	function jsSeleccionArea(itemIdEmp,itemIdSuc,itemIdArea){
// 	    document.getElementById("frmAreaModalPanel:hiddenIdEmpArea").value=itemIdEmp;
// 	    document.getElementById("frmAreaModalPanel:hiddenIdSucArea").value=itemIdSuc;
// 	    document.getElementById("frmAreaModalPanel:hiddenIdArea").value=itemIdArea;
// 	}


      function soloNumeros(tecla)
      { 
    	
         // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57   
         var keys = nav4 ? tecla.which : tecla.keyCode; 
        
         return ((keys <= 13 || (keys >= 50 && keys <= 57));
      }
</script>



     <rich:panel id="pMarcoCobranza" style="border:1px solid #17356f ;width: 1530px; background-color:#DEEBF5">
	     
	     <!-- INI - Filtro de Busqueda -->
         <h:panelGrid columns="11" style="width: 1120px" border="0" >
         	    <rich:column style="width:30px; border: none">
         		<h:outputText  id="lblTipoGestionCobranza" value="Tipo :"  > </h:outputText>
         	    </rich:column>
         	    <rich:column style="width:40px; border: none">
	         	<h:selectOneMenu id="cboFilTipo" value="#{cobranzaController.intCboTipoGestionCobranza}">
	         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOGESTION}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
         	    </rich:column>
             	<rich:column style="width:75px; border: none">
         		<h:outputText id="lblSucursal" value="Sucursal :"> </h:outputText>
         	    </rich:column>
         	    <rich:column style="width:100px; border: none">	
				<h:selectOneMenu id="cboSucursalBusqueda" style="width: 150px;"
				   value="#{cobranzaController.intCboSucursal}">
				   <f:selectItem itemValue="-1" itemLabel="Todas las Sucursales"/>
				   <f:selectItem itemValue="-3" itemLabel="Todas las Agencias"/>
				   <f:selectItem itemValue="-6" itemLabel="Todas las Sedes"/>
				   <f:selectItem itemValue="-4" itemLabel="Todas las Filiales"/>
				   <f:selectItem itemValue="-5" itemLabel="Todas las Oficinas Principales"/>
				   <tumih:selectItems var="sel" value="#{cobranzaController.listJuridicaSucursal}"
				   itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				</h:selectOneMenu>
                </rich:column>
         	    <rich:column style="width:70px; border: none">
         		<h:outputText id="lblGestorDni" value="Gestor DNI:" > </h:outputText>
         	    </rich:column>
         	    <rich:column style="width:90px; border: none">
         		<h:inputText id="txtGestorDni" size="20" value="#{cobranzaController.strTxtNumeroIdentidad}"></h:inputText>
            	</rich:column>
                <rich:column style="width:44px; border: none">
	         		<h:outputText id="lblFecha" value="Fecha:" > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:160px; border: none">
	         		<rich:calendar value="#{cobranzaController.dtTxtFechaIni}" 
	    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
	         	</rich:column>
	         	<rich:column style="width:160px; border: none">
	         		<rich:calendar value="#{cobranzaController.dtTxtFechaFin}" 
	    							datePattern="dd/MM/yyyy" style="width:76px"></rich:calendar>
	         	</rich:column>
	         	<rich:column style="width:60px; border: none">
         		<h:outputText id="lblEstadoEmpresa" value="Estado :" > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:80px; border: none">
	         		<h:selectOneMenu id="cboFilEstado" value="#{cobranzaController.cboEstadoCobranza}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
	         	</rich:column>
	     </h:panelGrid>
	     <h:panelGrid columns="1" style="width: 850px" border="0" >
	         	<rich:column style="width:280px; border: none">
	         		<a4j:commandButton rendered="#{cobranzaController.habilitarBotones}" value="Buscar" styleClass="btnEstilos" actionListener="#{cobranzaController.listarCobranzas}" reRender="pgFilArea"/>
	         	</rich:column>
	     </h:panelGrid>
         
         
          <!-- FIN - Filtro de Busqueda -->
		
     	<h:panelGroup id="pgFilArea" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilGestionCobranza" value="#{cobranzaController.beanListCobranzas}" 
                                 rendered="#{not empty cobranzaController.beanListCobranzas}" 
                            	 var="item"  onRowClick="selecGestionCobranza('#{rowKey}')"
                            	 rowKeyVar="rowkey" sortMode="single"  width="1420px" height="240px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.id.intItemGestionCobranza}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEncaNombArea" value="Gestión" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValNombArea" value="#{item.id.intItemGestionCobranza}"></h:outputText>
                                    <rich:toolTip for="lblValNombArea" value="#{item.id.intItemGestionCobranza}"></rich:toolTip>
                                </rich:column>

                                <rich:column width="170px" sortBy="#{item.intTipoGestionCobCod}">
                                    <f:facet name="header">
                                        <h:outputText id="lblTipo" value="Tipo" ></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.intTipoGestionCobCod}" disabled="true">
		                    			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOGESTION}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.dtFechaGestion}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEncaFecha" value="Fecha" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValFechaGestion" value="#{item.dtFechaGestion}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:outputText>
                                    <rich:toolTip for="lblValFechaGestion" value="#{item.dtFechaGestion}"></rich:toolTip>
                                </rich:column>
                                <rich:column width="100px" >
                                    <f:facet name="header">
                                        <h:outputText id="lblTipoEntidaSocio" value="Tipo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="a" rendered="#{item.strTipoSocio != 'Socio'}"  value="#{item.strTipoEnitidad}"></h:outputText>
                                    <h:outputText id="b" rendered="#{item.strTipoSocio == 'Socio'}"  value="#{item.strTipoSocio}"></h:outputText>
                                    
                                </rich:column>
                                <rich:column width="260px" sortBy="#{item.intTipoGestionCobCod}" >
                                    <f:facet name="header">
                                        <h:outputText id="lblstrDescripcion" value="Nombre" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="valstrDescripcionx"  rendered="#{not empty item.strDescEntidad}"  value="#{item.strDescEntidad}"></h:outputText>
                                    <h:outputText id="valstrDescripciony"  rendered="#{not empty item.strDescSocio}"  value="#{item.strDescSocio}"></h:outputText>
                                 </rich:column>
                                  <rich:column width="120px">
                                    <f:facet name="header">
                                        <h:outputText id="lblstrSucursal" value="Sucursal" ></h:outputText>
                                    </f:facet>
                                       <h:outputText id="lblValStrSucursal" value="#{item.gestorCobranza.sucursal.juridica.strRazonSocial}"></h:outputText>
                                  </rich:column>
                                
                                <rich:column width="160px" sortBy="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEncaGestor" value="Gestor" ></h:outputText>
                                    </f:facet>
                                    <h:outputFormat></h:outputFormat>
                                    <h:outputText id="lblValGestor" value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}" />
                                    <rich:toolTip for="lblValGestor" value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}"></rich:toolTip>
                                </rich:column>
                                 <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText id="lblstrDocCobranza" value="Doc. Cobranza" ></h:outputText>
                                    </f:facet>
                                      <h:outputText id="lblValocCobranza" value="#{item.docCobranza.strNombre}" />
                                 </rich:column>
                                 
                                 <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText id="lblstrIngreso" value="Ingreso" ></h:outputText>
                                    </f:facet>
                                 </rich:column>
                                 
                                 <rich:column width="100px">
                                    <f:facet name="header">
                                        <h:outputText id="lblstrMonto" value="Monto" ></h:outputText>
                                    </f:facet>
                                 </rich:column>
                                 
                                <rich:column width="100px" sortBy="#{item.intEstado}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEstado" value="Estado" ></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.intEstado}" disabled="true">
                                        <f:selectItem itemLabel="Anulado" itemValue="0"/> 
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>
                                <a4j:support event="onRowClick" actionListener="#{cobranzaController.setSelectedGestionCobranza}" reRender="mpUpDelGestionCobranza">
				                        	<f:attribute name="item" value="#{item}"/>
				                </a4j:support>

                                 <f:facet name="footer">
							   		 <h:panelGroup layout="block">
								   		 <rich:datascroller for="tblFilGestionCobranza" maxPages="10"/>
								   		 <h:panelGroup layout="block" style="margin:0 auto; width:410px">
								   		 	<a4j:commandLink action="#" oncomplete="Richfaces.showModalPanel('mpUpDelGestionCobranza')">
												<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
											</a4j:commandLink>
											<h:outputText value="Para Modificar o Eliminar el Modelo Contable hacer click en el Registro" style="color:#8ca0bd"/>
								   		 </h:panelGroup>
							   		 </h:panelGroup>  
						       </f:facet>       
                            </rich:extendedDataTable>      
               </h:panelGroup>
       
	
		<h:panelGrid id="idBotones"  columns="4" rendered="#{cobranzaController.habilitarBotones}">
	        <h:commandButton value="Nuevo" styleClass="btnEstilos" actionListener="#{cobranzaController.habilitarGrabarCobranza}" >
	        </h:commandButton>  
	        <a4j:commandButton rendered="#{cobranzaController.habilitarGestion}" disabled="#{cobranzaController.botonGrabarDisabled}" value="Grabar" actionListener="#{cobranzaController.grabarCobranza}" styleClass="btnEstilos"
	              				reRender="divFormCobranza,pgFilArea" ></a4j:commandButton>
	              				
	        <a4j:commandButton rendered="#{cobranzaController.habilitarCierre}" value="Grabar" actionListener="#{cobranzaController.grabarCierreCobranza}" styleClass="btnEstilos"
	                           reRender="divFormCobranza" ></a4j:commandButton>
	        <a4j:commandButton value="Cancelar" actionListener="#{cobranzaController.cancelarGrabarCobranza}" styleClass="btnEstilos"
	              				reRender="divFormCobranza" ></a4j:commandButton>
	        <a4j:commandButton value="Cierre Diario Gestión" actionListener="#{cobranzaController.irCerrarGestiones}" 
	              				reRender="divFormCobranza" ></a4j:commandButton>
	 
	   </h:panelGrid>

	
	<!-- Formulario de ingreso de datos cobranza -->
	<h:panelGroup id="divFormCobranza" layout="block" > 
	 <rich:panel style="width: 1515px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{cobranzaController.cobranzaRendered}">
	
	 
	    <h:panelGrid  columns="6" style="width:710px;" rendered="#{cobranzaController.habilitarGestion}">
	         <rich:column style="width:100px; border: none">
         		<h:outputText  value="Tipo de Gestión :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:5px; border: none"  >
	         	<h:selectOneMenu  value="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod}" disabled="#{cobranzaController.formTipoDisabled}">
	         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOGESTION}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
	     	</rich:column>
         	 <rich:column style="width:40px; border: none">
         		<h:outputText   value="Sucursal :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:120px; border: none">
         		<h:outputText  value="#{cobranzaController.strDescSucursal}"  > </h:outputText>
         	</rich:column>
         	 <rich:column style="width:20px; border: none">
         		<h:outputText   value="Gestor :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
         	    <h:outputText  value="#{cobranzaController.strNombreApellidosGestor}"  > </h:outputText>
         	</rich:column>
	   </h:panelGrid>	
	   
	    <h:panelGrid  columns="6" style="width:710px;" rendered="#{cobranzaController.habilitarCierre}">
	         <rich:column style="width:140px; border: none">
         		<h:outputText  value="Fecha de Cierre Gestión:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none"  >
	         	<rich:calendar value="#{cobranzaController.beanGestCobCierre.id.dtFechaCierre}" 
	         	 datePattern="dd/MM/yyyy" style="width:76px" disabled="#{cobranzaController.dtFechaCierreDisabled}" >
	         	 </rich:calendar>
	     	</rich:column>
         	 <rich:column style="width:40px; border: none">
         		<h:outputText   value="Sucursal :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:120px; border: none">
         		<h:outputText  value="#{cobranzaController.strDescSucursal}"  > </h:outputText>
         	</rich:column>
         	 <rich:column style="width:20px; border: none">
         		<h:outputText   value="Gestor :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
         	    <h:outputText  value="#{cobranzaController.strNombreApellidosGestor}"  > </h:outputText>
         	</rich:column>
	     </h:panelGrid>	
	     <h:panelGrid  columns="6" style="width:260px;" rendered="#{cobranzaController.habilitarCierre}">
	         <rich:column style="width:260px; border: none">
	            <h:outputText  value="Fecha de último cierre ejecutado: "/>
         	    <h:outputText  value="#{cobranzaController.beanGestCobCierre.dtUltFechaCierre}"> 
         	     <f:convertDateTime pattern="dd/MM/yyyy"/></h:outputText>
         	 </rich:column>
	     </h:panelGrid>
	    
	    <h:panelGrid  columns="2" style="width:810px;" rendered="#{cobranzaController.habilitarGestion}">
	         <rich:column style="width:10px; border: none">
	         <h:outputText   value=""  > </h:outputText>
	         </rich:column>
	         <rich:column style="width:780px; border: none">
	                   <h:commandButton rendered="#{cobranzaController.botonValidarDisabled}" style="width:760px" 
	                     value="Validar" styleClass="btnEstilos" actionListener="#{cobranzaController.seleccionarTipoGestionCobranza}">
                       </h:commandButton>
	         </rich:column>	
	    </h:panelGrid>    
	    
	    <h:panelGrid  columns="2" style="width:1300px;" rendered="#{cobranzaController.habilitarCierre}">
	         <rich:column style="width:10px; border: none">
	         <h:outputText   value=""  > </h:outputText>
	         </rich:column>
	         <rich:column style="width:1300px; border: none">
	                   <h:commandButton rendered="#{cobranzaController.botonValidarDisabled}" style="width:1200px" 
	                     value="Validar" styleClass="btnEstilos" actionListener="#{cobranzaController.validar}">
                       </h:commandButton>
	         </rich:column>	
	    </h:panelGrid>     
              
             <a4j:include  viewId="/pages/gestion/cobranza/formPromocion.jsp"/>
             <a4j:include  viewId="/pages/gestion/cobranza/formTramite.jsp"/>
             <a4j:include  viewId="/pages/gestion/cobranza/formCobranza.jsp"/>
             
             <a4j:include rendered="#{cobranzaController.habilitarCierre}" viewId="/pages/gestion/cobranza/formCierreGestion.jsp"/>
             
        
	  </rich:panel>     
	</h:panelGroup>     
  </rich:panel>
