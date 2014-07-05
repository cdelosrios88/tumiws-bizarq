<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
  
  
  <rich:panel id="formTramite" style="width: 950px;" rendered="#{cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '3' && cobranzaController.habilitarGestion}">
           <h:panelGrid  columns="7" style="width:940px;">
	         <rich:column style="width:80px; border: none">
         		<h:outputText   value="Fecha :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
	         	<rich:calendar value="#{cobranzaController.beanGestionCobranza.dtFechaGestion}"  disabled="#{cobranzaController.formTramFechaDisabled}"  datePattern="dd/MM/yyyy" style="width:30px"></rich:calendar>
         	</rich:column>
         	 <rich:column style="width:70px; border: none">
         		<h:outputText   value="Hora Inicio:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraInicio}" disabled="#{cobranzaController.formTramHoraInicioDisabled}" >
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
	            <h:selectOneMenu  value="#{cobranzaController.intMinInicio}" disabled="#{cobranzaController.formTramMinInicioDisabled}" >
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
	         		<f:selectItem itemLabel="59" itemValue="59"/>
	            </h:selectOneMenu>
	            hh:mm		
	         </rich:column>
         	 <rich:column style="width:50px; border: none">
         		<h:outputText   value="Hora Fin:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:160px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraFin}" disabled="#{cobranzaController.formTramHoraFinDisabled}" >
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
	            <h:selectOneMenu  value="#{cobranzaController.intMinFin}"  disabled="#{cobranzaController.formTramMinFinDisabled}" >
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
	         		<f:selectItem itemLabel="59" itemValue="59"/>
	            </h:selectOneMenu>
	            hh:mm		
         	</rich:column>
         	  <rich:column style="width:280px; border: none">
         		<h:outputText   value="Nota: Solo permite el ingreso de la fecha actual o del día Siguiente."  > </h:outputText>
         	</rich:column>
        </h:panelGrid>
	    
	   
	    
	      <h:panelGrid  columns="2" style="width:710px;">
	        <rich:column style="width:135px; border: none">
         		<h:outputText   value="Sub Tipo de Gestion:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.beanGestionCobranza.intSubTipoGestionCobCod}"  disabled="#{cobranzaController.formTramSubTipoGestionCobCod}">
	         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_SUBTIPOGESTION}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
         	</rich:column>
	    </h:panelGrid>
	    
	    <h:panelGrid styleClass="tableCellBorder4" style="margin-top:15px">
           			<rich:columnGroup>
           			    <rich:column>
           					<h:outputText value="Entidad/Socio"></h:outputText>
           				</rich:column>
           				<rich:column>
           					 <h:selectOneMenu  value="#{cobranzaController.beanGestionCobranza.intOpcSocioEntidad}" disabled="#{cobranzaController.formTramOpcSocioEntidad}">
				         		<f:selectItem itemLabel="Entidad" itemValue="1"/>
				         		<f:selectItem itemLabel="Socio"   itemValue="2"/>
					       </h:selectOneMenu>
           				</rich:column>
           				<rich:column>
           					<h:outputText value="Busqueda y Selección de Entidad/Socio"></h:outputText>
           				</rich:column>
           				<rich:column>

           					<a4j:commandButton disabled="#{cobranzaController.formTramBotonAgregarDisabled}" value="Agregar" styleClass="btnEstilos1" actionListener="#{cobranzaController.buscarEntidadSocio}"
           									oncomplete="if(#{cobranzaController.beanGestionCobranza.intOpcSocioEntidad == 2}) {Richfaces.showModalPanel('mpBusqSocio')}
           									            if(#{cobranzaController.beanGestionCobranza.intOpcSocioEntidad == 1}) {Richfaces.showModalPanel('mpGestionCobranzaDetalle')}" reRender="divBusqSocio,divGestionCobranzaDetalle"></a4j:commandButton>
           				</rich:column>
           			</rich:columnGroup>
        </h:panelGrid>
         <!-- Detalle de Cobranza a Entidades "     -->
         <rich:spacer height="10px"></rich:spacer>
         <h:panelGroup id="divTblGestionCobranzaDet" >
           			<rich:extendedDataTable id="tblModeloDetalle" enableContextMenu="false" style="margin:0 auto"
           						rendered="#{not empty cobranzaController.beanGestionCobranza.listaGestionCobranzaDetalle}"
		                        var="item" value="#{cobranzaController.beanGestionCobranza.listaGestionCobranzaDetalle}" onRowClick="selecModeloDetalle('#{rowKey}')"
				  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="753px" height="205px">
				    		        	<rich:column width="31"  filterExpression="#{item.isDeleted==null || !item.isDeleted}">
							                <h:outputText rendered="#{item.class.name == 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="#{rowKey + 1}"></h:outputText>
							                <h:outputText rendered="#{item.class.name != 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="#{rowKey + 1}"></h:outputText>
							            </rich:column>
							            <rich:column  width="150">
					                        <f:facet name="header">
					                            <h:outputText value="Tipo"></h:outputText>
					                        </f:facet>
					                            <h:outputText rendered="#{item.class.name == 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt' && item.intNivel == 1}" value="Entidad-Institucion"></h:outputText>
					                            <h:outputText rendered="#{item.class.name == 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="Entidad-Unidad Ejecutiva"></h:outputText>
					                            <h:outputText rendered="#{item.class.name != 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="Socio"></h:outputText>
					                            
					                   </rich:column> 
							           <rich:column width="480">
					                        <f:facet name="header">
					                            <h:outputText value="Nombre Entidad/Socio"></h:outputText>
					                        </f:facet>
											<h:outputText rendered="#{item.class.name == 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="#{item.estructura.juridica.strRazonSocial}"></h:outputText>
											<h:outputText rendered="#{item.class.name != 'pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt'}" value="#{item.socio.strNombreSoc} #{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}"></h:outputText>
							           </rich:column>
					          <rich:column>
		                        <f:facet name="header">
		                            <h:outputText value="Opción"></h:outputText>
		                        </f:facet>
		                        <a4j:commandLink rendered ="#{cobranzaController.formTramBotonEliminarDisabled}"  value="Eliminar" actionListener="#{cobranzaController.eliminarGestionCobranzaDet}"
		                        				 reRender="divTblGestionCobranzaDet">
		                        	<f:param name="rowGestionCobranzaEnt" value="#{rowKey}"></f:param>
		                        </a4j:commandLink>
				            </rich:column>
		            </rich:extendedDataTable>
           		</h:panelGroup>
         <rich:spacer height="5px"></rich:spacer>
         <h:panelGrid  columns="2" style="width:623px;">
	         <rich:column style="width:82px; border: none">	
         		<h:outputText   value="Contacto :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	     <h:inputText size="80" value="#{cobranzaController.beanGestionCobranza.strContacto}" disabled="#{cobranzaController.formTramContactoDisabled}" ></h:inputText>
	     	</rich:column>
        </h:panelGrid>
        <h:panelGrid  columns="2" style="width:601px;">
	     	 <rich:column style="width:80px; border: none">
         		<h:outputText   value="Observación :"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:400px; border: none">
         	     <h:inputText size="100"  value="#{cobranzaController.beanGestionCobranza.strObservacion}" disabled="#{cobranzaController.formTramObservacionDisabled }"></h:inputText>
         	</rich:column>
        </h:panelGrid>
</rich:panel>