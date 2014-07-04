<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
  
  
  <rich:panel id="formPromocion" style="width: 980px;" rendered="#{(cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '1' || cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '2') && cobranzaController.habilitarGestion}">
           <h:panelGrid  columns="7" >
	         <rich:column style="width:80px; border: none">
         		<h:outputText   value="Fecha       :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
	         	<rich:calendar value="#{cobranzaController.beanGestionCobranza.dtFechaGestion}"  disabled="#{cobranzaController.formPromFechaDisabled}"  datePattern="dd/MM/yyyy" style="width:30px"></rich:calendar>
         	</rich:column>
         	 <rich:column style="width:70px; border: none">
         		<h:outputText   value="Hora Inicio:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraInicio}" disabled="#{cobranzaController.formPromHoraInicioDisabled}" >
         	        <f:selectItem itemLabel=" " itemValue=""/>
	         		<f:selectItem itemLabel="00" itemValue="00"/>
	         		<f:selectItem itemLabel="01" itemValue="01"/>
	         		<f:selectItem itemLabel="02" itemValue="02"/>
	         		<f:selectItem itemLabel="03" itemValue="03"/>
	         		<f:selectItem itemLabel="04" itemValue="04"/>
	         		<f:selectItem itemLabel="05" itemValue="05"/>
	         		<f:selectItem itemLabel="06" itemValue="06"/>
	         		<f:selectItem itemLabel="07" itemValue="07"/>
	         		<f:selectItem itemLabel="08" itemValue="08"/>
	         		<f:selectItem itemLabel="09" itemValue="09"/>
	         		<f:selectItem itemLabel="10" itemValue="10"/>
	         		<f:selectItem itemLabel="11" itemValue="11"/>
	         		<f:selectItem itemLabel="12" itemValue="12"/>
	         		<f:selectItem itemLabel="13" itemValue="13"/>
	         		<f:selectItem itemLabel="14" itemValue="14"/>
	         		<f:selectItem itemLabel="15" itemValue="15"/>
	         		<f:selectItem itemLabel="16" itemValue="16"/>
	         		<f:selectItem itemLabel="17" itemValue="17"/>
	         		<f:selectItem itemLabel="18" itemValue="18"/>
	         		<f:selectItem itemLabel="19" itemValue="19"/>
	         		<f:selectItem itemLabel="20" itemValue="20"/>
	         		<f:selectItem itemLabel="21" itemValue="21"/>
	         		<f:selectItem itemLabel="22" itemValue="22"/>
	         		<f:selectItem itemLabel="23" itemValue="23"/>
	            </h:selectOneMenu>	
	            :	
	            <h:selectOneMenu  value="#{cobranzaController.intMinInicio}" disabled="#{cobranzaController.formPromMinInicioDisabled}" >
	             	<f:selectItem itemLabel="00" itemValue="00"/>
	         		<f:selectItem itemLabel="01" itemValue="01"/>
	         		<f:selectItem itemLabel="02" itemValue="02"/>
	         		<f:selectItem itemLabel="03" itemValue="03"/>
	         		<f:selectItem itemLabel="04" itemValue="04"/>
	         		<f:selectItem itemLabel="05" itemValue="05"/>
	         		<f:selectItem itemLabel="06" itemValue="06"/>
	         		<f:selectItem itemLabel="07" itemValue="07"/>
	         		<f:selectItem itemLabel="08" itemValue="08"/>
	         		<f:selectItem itemLabel="09" itemValue="09"/>
	         		<f:selectItem itemLabel="10" itemValue="10"/>
	         		<f:selectItem itemLabel="11" itemValue="11"/>
	         		<f:selectItem itemLabel="12" itemValue="12"/>
	         		<f:selectItem itemLabel="13" itemValue="13"/>
	         		<f:selectItem itemLabel="14" itemValue="14"/>
	         		<f:selectItem itemLabel="15" itemValue="15"/>
	         		<f:selectItem itemLabel="16" itemValue="16"/>
	         		<f:selectItem itemLabel="17" itemValue="17"/>
	         		<f:selectItem itemLabel="18" itemValue="18"/>
	         		<f:selectItem itemLabel="19" itemValue="19"/>
	         		<f:selectItem itemLabel="20" itemValue="20"/>
	         		<f:selectItem itemLabel="21" itemValue="21"/>
	         		<f:selectItem itemLabel="22" itemValue="22"/>
	         		<f:selectItem itemLabel="23" itemValue="23"/>
	         		<f:selectItem itemLabel="24" itemValue="24"/>
	         		<f:selectItem itemLabel="25" itemValue="25"/>
	         		<f:selectItem itemLabel="26" itemValue="26"/>
	         		<f:selectItem itemLabel="27" itemValue="27"/>
	         		<f:selectItem itemLabel="28" itemValue="28"/>
	         		<f:selectItem itemLabel="29" itemValue="29"/>
	         		<f:selectItem itemLabel="30" itemValue="30"/>
	         		<f:selectItem itemLabel="31" itemValue="31"/>
	         		<f:selectItem itemLabel="32" itemValue="32"/>
	         		<f:selectItem itemLabel="33" itemValue="33"/>
	         		<f:selectItem itemLabel="34" itemValue="34"/>
	         		<f:selectItem itemLabel="35" itemValue="35"/>
	         		<f:selectItem itemLabel="36" itemValue="36"/>
	         		<f:selectItem itemLabel="37" itemValue="37"/>
	         		<f:selectItem itemLabel="38" itemValue="38"/>
	         		<f:selectItem itemLabel="39" itemValue="39"/>
	         		<f:selectItem itemLabel="40" itemValue="40"/>
	         		<f:selectItem itemLabel="42" itemValue="41"/>
	         		<f:selectItem itemLabel="42" itemValue="42"/>
	         		<f:selectItem itemLabel="43" itemValue="43"/>
	         		<f:selectItem itemLabel="44" itemValue="44"/>
	         		<f:selectItem itemLabel="45" itemValue="45"/>
	         		<f:selectItem itemLabel="46" itemValue="46"/>
	         		<f:selectItem itemLabel="47" itemValue="47"/>
	         		<f:selectItem itemLabel="48" itemValue="48"/>
	         		<f:selectItem itemLabel="49" itemValue="49"/>
	         		<f:selectItem itemLabel="50" itemValue="50"/>
	         		<f:selectItem itemLabel="51" itemValue="51"/>
	         		<f:selectItem itemLabel="52" itemValue="52"/>
	         		<f:selectItem itemLabel="53" itemValue="53"/>
	         		<f:selectItem itemLabel="54" itemValue="54"/>
	         		<f:selectItem itemLabel="55" itemValue="55"/>
	         		<f:selectItem itemLabel="56" itemValue="56"/>
	         		<f:selectItem itemLabel="57" itemValue="57"/>
	         		<f:selectItem itemLabel="58" itemValue="58"/>
	         		<f:selectItem itemLabel="89" itemValue="59"/>
	            </h:selectOneMenu>
	            hh:mm		
	         </rich:column>
         	 <rich:column style="width:50px; border: none">
         		<h:outputText   value="Hora Fin:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraFin}" disabled="#{cobranzaController.formPromHoraFinDisabled}" >
         	    	<f:selectItem itemLabel=" " itemValue=""/>
	         		<f:selectItem itemLabel="00" itemValue="00"/>
	         		<f:selectItem itemLabel="01" itemValue="01"/>
	         		<f:selectItem itemLabel="02" itemValue="02"/>
	         		<f:selectItem itemLabel="03" itemValue="03"/>
	         		<f:selectItem itemLabel="04" itemValue="04"/>
	         		<f:selectItem itemLabel="05" itemValue="05"/>
	         		<f:selectItem itemLabel="06" itemValue="06"/>
	         		<f:selectItem itemLabel="07" itemValue="07"/>
	         		<f:selectItem itemLabel="08" itemValue="08"/>
	         		<f:selectItem itemLabel="09" itemValue="09"/>
	         		<f:selectItem itemLabel="10" itemValue="10"/>
	         		<f:selectItem itemLabel="11" itemValue="11"/>
	         		<f:selectItem itemLabel="12" itemValue="12"/>
	         		<f:selectItem itemLabel="13" itemValue="13"/>
	         		<f:selectItem itemLabel="14" itemValue="14"/>
	         		<f:selectItem itemLabel="15" itemValue="15"/>
	         		<f:selectItem itemLabel="16" itemValue="16"/>
	         		<f:selectItem itemLabel="17" itemValue="17"/>
	         		<f:selectItem itemLabel="18" itemValue="18"/>
	         		<f:selectItem itemLabel="19" itemValue="19"/>
	         		<f:selectItem itemLabel="20" itemValue="20"/>
	         		<f:selectItem itemLabel="21" itemValue="21"/>
	         		<f:selectItem itemLabel="22" itemValue="22"/>
	         		<f:selectItem itemLabel="23" itemValue="23"/>
	            </h:selectOneMenu>	
	            :	
	            <h:selectOneMenu  value="#{cobranzaController.intMinFin}"  disabled="#{cobranzaController.formPromMinFinDisabled}" >
	                <f:selectItem itemLabel="00" itemValue="00"/>
	         		<f:selectItem itemLabel="01" itemValue="01"/>
	         		<f:selectItem itemLabel="02" itemValue="02"/>
	         		<f:selectItem itemLabel="03" itemValue="03"/>
	         		<f:selectItem itemLabel="04" itemValue="04"/>
	         		<f:selectItem itemLabel="05" itemValue="05"/>
	         		<f:selectItem itemLabel="06" itemValue="06"/>
	         		<f:selectItem itemLabel="07" itemValue="07"/>
	         		<f:selectItem itemLabel="08" itemValue="08"/>
	         		<f:selectItem itemLabel="09" itemValue="09"/>
	         		<f:selectItem itemLabel="10" itemValue="10"/>
	         		<f:selectItem itemLabel="11" itemValue="11"/>
	         		<f:selectItem itemLabel="12" itemValue="12"/>
	         		<f:selectItem itemLabel="13" itemValue="13"/>
	         		<f:selectItem itemLabel="14" itemValue="14"/>
	         		<f:selectItem itemLabel="15" itemValue="15"/>
	         		<f:selectItem itemLabel="16" itemValue="16"/>
	         		<f:selectItem itemLabel="17" itemValue="17"/>
	         		<f:selectItem itemLabel="18" itemValue="18"/>
	         		<f:selectItem itemLabel="19" itemValue="19"/>
	         		<f:selectItem itemLabel="20" itemValue="20"/>
	         		<f:selectItem itemLabel="21" itemValue="21"/>
	         		<f:selectItem itemLabel="22" itemValue="22"/>
	         		<f:selectItem itemLabel="23" itemValue="23"/>
	         		<f:selectItem itemLabel="24" itemValue="24"/>
	         		<f:selectItem itemLabel="25" itemValue="25"/>
	         		<f:selectItem itemLabel="26" itemValue="26"/>
	         		<f:selectItem itemLabel="27" itemValue="27"/>
	         		<f:selectItem itemLabel="28" itemValue="28"/>
	         		<f:selectItem itemLabel="29" itemValue="29"/>
	         		<f:selectItem itemLabel="30" itemValue="30"/>
	         		<f:selectItem itemLabel="31" itemValue="31"/>
	         		<f:selectItem itemLabel="32" itemValue="32"/>
	         		<f:selectItem itemLabel="33" itemValue="33"/>
	         		<f:selectItem itemLabel="34" itemValue="34"/>
	         		<f:selectItem itemLabel="35" itemValue="35"/>
	         		<f:selectItem itemLabel="36" itemValue="36"/>
	         		<f:selectItem itemLabel="37" itemValue="37"/>
	         		<f:selectItem itemLabel="38" itemValue="38"/>
	         		<f:selectItem itemLabel="39" itemValue="39"/>
	         		<f:selectItem itemLabel="40" itemValue="40"/>
	         		<f:selectItem itemLabel="42" itemValue="41"/>
	         		<f:selectItem itemLabel="42" itemValue="42"/>
	         		<f:selectItem itemLabel="43" itemValue="43"/>
	         		<f:selectItem itemLabel="44" itemValue="44"/>
	         		<f:selectItem itemLabel="45" itemValue="45"/>
	         		<f:selectItem itemLabel="46" itemValue="46"/>
	         		<f:selectItem itemLabel="47" itemValue="47"/>
	         		<f:selectItem itemLabel="48" itemValue="48"/>
	         		<f:selectItem itemLabel="49" itemValue="49"/>
	         		<f:selectItem itemLabel="50" itemValue="50"/>
	         		<f:selectItem itemLabel="51" itemValue="51"/>
	         		<f:selectItem itemLabel="52" itemValue="52"/>
	         		<f:selectItem itemLabel="53" itemValue="53"/>
	         		<f:selectItem itemLabel="54" itemValue="54"/>
	         		<f:selectItem itemLabel="55" itemValue="55"/>
	         		<f:selectItem itemLabel="56" itemValue="56"/>
	         		<f:selectItem itemLabel="57" itemValue="57"/>
	         		<f:selectItem itemLabel="58" itemValue="58"/>
	         		<f:selectItem itemLabel="89" itemValue="59"/>
	            </h:selectOneMenu>
	            hh:mm		
         	</rich:column>
         	<rich:column style="width:280px; border: none">
         		<h:outputText   value="Nota: Solo permite el ingreso de la fecha actual o del día Siguiente."  > </h:outputText>
         	</rich:column>
        </h:panelGrid>
        
        <!-- INI - Datos de cobranza cheque-->
        
        <h:panelGrid  columns="3" rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '2'}">
	         <rich:column style="width:80px; border: none">
         		<h:outputText   value="Entidad    :"  > </h:outputText>
         	</rich:column>
         	<rich:column id="idDescEntidad" style="width:400px; border: none">
         	     <h:inputText size="80" value="#{cobranzaController.beanGestionCobranza.strDescEntidad}" disabled="true" ></h:inputText>
	     	</rich:column>
	     	<rich:column>
            <a4j:commandButton rendered="#{cobranzaController.formPromBotonAgregarDisabled}" value="Buscar" styleClass="btnEstilos1" actionListener="#{cobranzaController.buscarEntidad}" disabled="#{cobranzaController.formBotonBusqCobrChDisabled}"
           									oncomplete="Richfaces.showModalPanel('mpGestionCobranzaDetalle')" reRender="divGestionCobranzaDetalle"></a4j:commandButton>
           	</rich:column>
        </h:panelGrid>
        <!-- FIN - Datos de cobranza cheque-->
        <!-- Datos de Promocion -->
	    <h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px"  rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '1'}">
           			<rich:columnGroup>
           				<rich:column>
           					<h:outputText value="Busqueda y Selección de Entidad"></h:outputText>
           				</rich:column>
           				<rich:column>
           					<a4j:commandButton rendered="#{cobranzaController.formPromBotonAgregarDisabled}" value="Agregar Entidad" styleClass="btnEstilos1" actionListener="#{cobranzaController.buscarEntidad}"
           									oncomplete="Richfaces.showModalPanel('mpGestionCobranzaDetalle')" reRender="divGestionCobranzaDetalle"></a4j:commandButton>
           				</rich:column>
           			</rich:columnGroup>
        </h:panelGrid>
         <!-- INI-Detalle de Cobranza a Entidades -->
         <rich:spacer height="10px" rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '1'}"></rich:spacer>
         <h:panelGroup id="divTblGestionCobranzaEnt" rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '1'}">
           			<rich:extendedDataTable id="tblModeloDetalle" enableContextMenu="false" style="margin:0 auto"
           						rendered="#{not empty cobranzaController.beanGestionCobranza.listaGestionCobranzaEnt}"
		                        var="item" value="#{cobranzaController.beanGestionCobranza.listaGestionCobranzaEnt}" onRowClick="selecModeloDetalle('#{rowKey}')"
				  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="753px" height="205px">
				    		
				   			<rich:column width="31" filterExpression="#{item.isDeleted==null || !item.isDeleted}">
				                <h:outputText value="#{rowKey + 1}"></h:outputText>
				            </rich:column>
				                    
				            <rich:column  width="150">
		                        <f:facet name="header">
		                            <h:outputText value="Nivel"></h:outputText>
		                        </f:facet>
		                           <c:choose>
		                             <c:when test="#{item.intNivel eq '1'}">
		                               <h:outputText value="Institucion"></h:outputText>
		                             </c:when>
		                             <c:otherwise>
		                               <h:outputText value="Unidad Ejecutiva"></h:outputText>
		                             </c:otherwise>
		                           </c:choose>
		                   </rich:column> 
				            <rich:column width="480">
		                        <f:facet name="header">
		                            <h:outputText value="Nombre Entidad"></h:outputText>
		                        </f:facet>
								<h:outputText value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
				            </rich:column>
				           
				            <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Opción"></h:outputText>
		                        </f:facet>
		                        <a4j:commandLink rendered="#{cobranzaController.formPromBotonEliminarDisabled}"  value="Eliminar" actionListener="#{cobranzaController.eliminarGestionCobranzaEnt}"
		                        				 reRender="divTblGestionCobranzaEnt">
		                        	<f:param name="rowGestionCobranzaEnt" value="#{rowKey}"></f:param>
		                        </a4j:commandLink >
				            </rich:column>
		            </rich:extendedDataTable>
           		</h:panelGroup>
         <rich:spacer height="5px" rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '1'}"></rich:spacer>
         <!--FIN-Datos de Promocio-->
         
         <h:panelGrid  columns="2" >
	         <rich:column style="width:80px; border: none">
         		<h:outputText   value="Contacto     :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	     <h:inputText size="100" value="#{cobranzaController.beanGestionCobranza.strContacto}" disabled="#{cobranzaController.formPromContactoDisabled}" ></h:inputText>
	     	</rich:column>
        </h:panelGrid>
        <h:panelGrid  columns="2" >
	     	 <rich:column style="width:80px; border: none">
         		<h:outputText   value="Observación :"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	     <h:inputText size="110"  value="#{cobranzaController.beanGestionCobranza.strObservacion}" disabled="#{cobranzaController.formPromObservacionDisabled }"></h:inputText>
         	</rich:column>
         	
        </h:panelGrid>
        <h:panelGrid  columns="3"  rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '2'}">
             <rich:column style="width:80px; border: none">
         		<h:outputText   value="Resultado   :"> </h:outputText>
         	</rich:column>
	        <rich:column style="width:40px; border: none">
		         	<h:selectOneMenu  id="cboFilTipoResultado" value="#{cobranzaController.beanGestionCobranza.intParaTipoResultado}"
		         	                 valueChangeListener = "#{cobranzaController.mostrarBotonGenerarIngreso}"
		         	                 disabled="#{cobranzaController.formCobrChTipoResDisabled}"
		         	                 style="width:185px">
		         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORESULTADOCHEQUE}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						<a4j:support event="onchange" reRender="idBotonGenerarIngreso" ajaxSingle="true" />
			
					</h:selectOneMenu>
	       </rich:column>
	       <rich:column style="width:135px; border: none" id="idBotonGenerarIngreso">
	         			<a4j:commandButton  rendered="#{cobranzaController.habilitarBotonGeneararIngreso}" disabled="#{cobranzaController.formBotonGenIngCobrChDisabled}"  value="Generar Ingreso" styleClass="btnEstilos1" />
           </rich:column>	
       </h:panelGrid>
        
        
        
</rich:panel>