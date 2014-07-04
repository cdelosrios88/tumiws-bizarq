<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
  
  
  <rich:panel id="formCobranza" style="width: 1000px;" rendered="#{(cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '4' || cobranzaController.beanGestionCobranza.intTipoGestionCobCod == '5')  &&  cobranzaController.habilitarGestion}">
           <h:panelGrid  columns="7" >
	         <rich:column style="width:80px; border: none">
         		<h:outputText   value="Fecha :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
	         	<rich:calendar value="#{cobranzaController.beanGestionCobranza.dtFechaGestion}"  disabled="#{cobranzaController.formCobrFechaDisabled}"  datePattern="dd/MM/yyyy" style="width:30px"></rich:calendar>
         	</rich:column>
         	 <rich:column style="width:70px; border: none">
         		<h:outputText   value="Hora Inicio:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:180px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraInicio}" disabled="#{cobranzaController.formCobrHoraInicioDisabled}" >
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
	            <h:selectOneMenu  value="#{cobranzaController.intMinInicio}" disabled="#{cobranzaController.formCobrMinInicioDisabled}" >
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
         	    <h:selectOneMenu  value="#{cobranzaController.intHoraFin}" disabled="#{cobranzaController.formCobrHoraFinDisabled}" >
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
	            <h:selectOneMenu  value="#{cobranzaController.intMinFin}"  disabled="#{cobranzaController.formCobrMinFinDisabled}" >
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
	    
	     <h:panelGrid  columns="4" >
	        <rich:column style="width:80px; border: none">
         		<h:outputText   value="Socio:"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:95px; border: none">
         	    <h:selectOneMenu  value="#{cobranzaController.intTipoSocio}" disabled="#{cobranzaController.formCobrTipoSocioDisabled}"
         	                              valueChangeListener = "#{cobranzaController.limpiarSocio}"  >
         	    
	         		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_SOCIO}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="inputStrSocioNombre,cboItemExpediente" ajaxSingle="true" />
			
				</h:selectOneMenu>
         	</rich:column>
         	 <rich:column  style="width:300px; border: none">
         		<h:inputText id="inputStrSocioNombre" size="50" disabled="true"  value="#{cobranzaController.beanGestionCobranza.strSocioNombre}" > </h:inputText>
         	</rich:column>
         	
         	<rich:column>
				<a4j:commandButton disabled="#{cobranzaController.formCobrBotonAgregarDisabled}" value="Buscar" styleClass="btnEstilos1" actionListener="#{cobranzaController.buscarEntidadSocio}"
           								oncomplete="Richfaces.showModalPanel('mpBusqSocio')" reRender="divBusqSocio"></a4j:commandButton>
           	</rich:column>
	     </h:panelGrid>
	      <h:panelGrid  columns="4" >
		        <rich:column style="width:80px; border: none">
	         		<h:outputText   value="Lugar Gest. :"  > </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:80px; border: none">
	         	    <h:selectOneMenu  value="#{cobranzaController.beanGestionCobranza.intParaLugarGestion}" disabled="#{cobranzaController.formCobrParaLugarGestionDisabled}">
		         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_LUGARGESTION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	         	</rich:column>
	         	 <rich:column style="width:40px; border: none">
	         		<h:outputText value="Crédito : "> </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:560px; border: none">
	         	   <h:selectOneMenu id="cboItemExpediente" style="width: 550px;" value="#{cobranzaController.intItemExpediente}" disabled="#{cobranzaController.formCobrItemExpedienteDisabled}">
					 <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					 <tumih:selectItems var="sel" value="#{cobranzaController.listaExpCredito}"
								itemValue="#{sel.id.intItemExpediente}" itemLabel="#{sel.strDescripcion}"/>
				   </h:selectOneMenu>
	         	</rich:column>
	     </h:panelGrid>
	    
	     <h:panelGrid  columns="4" >
		        <rich:column style="width:80px; border: none">
	         		<h:outputText   value="Resultado :"> </h:outputText>
	         	</rich:column>
	         	<rich:column style="width:135px; border: none">
	         	    <h:selectOneMenu  value = "#{cobranzaController.beanGestionCobranza.intParaTipoResultado}" 
	         	                      valueChangeListener = "#{cobranzaController.reloadCboSubResultado}" disabled="#{cobranzaController.formCobrParaTipoResultadoDisabled}">
		         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPORESULTADO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="cboFilSubResultado,idBotonGenerar,IDColDocumento2,IDColDocumento3,IDColDocumento4,IDColDocumento5,IDColDocumento6,IDColDocumento7" ajaxSingle="true" />
					</h:selectOneMenu>
	         	</rich:column>
	         	<rich:column style="width:135px; border: none">
	         		 <h:selectOneMenu id="cboFilSubResultado" style="width: 200px;" value="#{cobranzaController.beanGestionCobranza.intParaTipoSubResultado}"
	         		                  disabled="#{cobranzaController.formCobrParaTipoSubResultadoDisabled}">
					 <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					 <tumih:selectItems var="sel" value="#{cobranzaController.listaSubResultado}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			     	</h:selectOneMenu>
	         	</rich:column>
	         	<rich:column style="width:135px; border: none" id="idBotonGenerar">
	         					<a4j:commandButton id="idBotonGenerarUno"  rendered="#{cobranzaController.formCobrBotonGenIngresoDisabled}"  value="Generar Ingreso" styleClass="btnEstilos1">
           						</a4j:commandButton>
           						<a4j:commandButton id="idBotonGenerarDos" rendered="#{cobranzaController.formCobrBotonGenDocuDisabled}"  value="Generar Documento" styleClass="btnEstilos1" 
           						   actionListener="#{cobranzaController.generarDocCobranza}"
           						   disabled="#{cobranzaController.formCobrBotonGenDocuDisabled2}"
				           				reRender="IDColDocumento2,IDColDocumento3,IDColDocumento4,IDColDocumento5,IDColDocumento6,IDColDocumento7">
				           	    </a4j:commandButton>
     
	          </rich:column>
	         	
	     </h:panelGrid>
	    

	      <rich:spacer height="5px"></rich:spacer>
         <h:panelGrid  columns="2" >
	         <rich:column style="width:80px; border: none">	
         		<h:outputText   value="Contacto :"  > </h:outputText>
         	</rich:column>
         	<rich:column style="width:400px; border: none">
         	     <h:inputText size="80" value="#{cobranzaController.beanGestionCobranza.strContacto}" disabled="#{cobranzaController.formCobrContactoDisabled}" ></h:inputText>
	     	</rich:column>
        </h:panelGrid>
        <h:panelGrid  columns="2" >
	     	 <rich:column style="width:80px; border: none">
         		<h:outputText   value="Observación :"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	     <h:inputText size="110"  value="#{cobranzaController.beanGestionCobranza.strObservacion}" disabled="#{cobranzaController.formCobrObservacionDisabled }"></h:inputText>
         	</rich:column>
        </h:panelGrid>
        <!--Ini-Datos del Documento-->
         <h:panelGrid>
	         <rich:column  id="IDColDocumento2" width="700"  >
		     		<h:outputText  value="Datos Documento" rendered="#{!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled}"> </h:outputText>
        	 </rich:column>
	     </h:panelGrid>
	      <h:panelGrid  columns="2" >
	         <rich:column  id="IDColDocumento3" style="width:100px; border: none">	
         		<h:outputText   value="Tipo Dcto :" rendered="#{!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled}"> </h:outputText>
         	</rich:column>
         	<rich:column  id="IDColDocumento4" style="width:600px; border: none">
               <h:selectOneMenu style="width: 200px;" value="#{cobranzaController.beanGestionCobranza.intItemDocCob}"
                       disabled="#{cobranzaController.formCobrItemDocCobDisabled}" rendered="#{!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled}">
				 <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				 <tumih:selectItems var="sel" value="#{cobranzaController.listaDocCobranza}"
			  	itemValue="#{sel.id.intItemDocCob}" itemLabel="#{sel.strNombre}"/>
		       </h:selectOneMenu>
            </rich:column>
          </h:panelGrid>
          <h:panelGrid columns="4" id="panelDocCobranzaG" >
				<rich:column id="IDColDocumento5" width="100" >
		     		<h:outputText   value="Adjuntar Dcto :" rendered="#{!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled}"> </h:outputText>
        		</rich:column>
				<rich:column id="IDColDocumento6" width="150">
					<h:inputText rendered="#{empty cobranzaController.archivoDocCobranza  && (!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled)}" 
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;" />
					<h:inputText rendered="#{not empty cobranzaController.archivoDocCobranza && (!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled)}"
						value="#{cobranzaController.archivoDocCobranza.strNombrearchivo}"
						size="77"
						readonly="true" 
						style="background-color: #BFBFBF;"/>
				</rich:column>
				<rich:column id="IDColDocumento7" width="150">
					<a4j:commandButton
						rendered="#{empty cobranzaController.archivoDocCobranza && (!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled)}"
						styleClass="btnEstilos"
                		value="Adjuntar Documento Cobranza"
                		action = "#{cobranzaController.adjuntarDocCobranza}"
                		reRender="panelDocCobranzaG,idFormFileUpload"
                		oncomplete="Richfaces.showModalPanel('pAdjuntarDocCobranza')"
               		    style="width:180px"
               		    disabled="#{cobranzaController.formCobrBotonAdjuntarArchDisabled}"
               		    />
                	<a4j:commandButton
						rendered="#{not empty cobranzaController.archivoDocCobranza && (!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled)}"
						disabled="#{cobranzaController.formCobrBotonQuitarArchDisabled}"
                		styleClass="btnEstilos"
                		value="Quitar Documento Cobranza"
                		reRender="panelDocCobranzaG"
                		action="#{cobranzaController.quitarDocCobranza}"
                		style="width:180px"/>
                		
                		
				</rich:column>
				<rich:column width="140px" 
					rendered="#{(not empty cobranzaController.archivoDocCobranza)&&(!cobranzaController.formCobrBotonGenIngresoDisabled && cobranzaController.formCobrBotonGenDocuDisabled && cobranzaController.formGenerarDocumentoDisabled)}">
					<h:commandLink  value="   Descargar"		
						actionListener="#{fileUploadController.descargarArchivo}">
						<f:attribute name="archivo" value="#{cobranzaController.archivoDocCobranza}"/>		
					</h:commandLink>
				</rich:column>
		   </h:panelGrid>
       <!--Fin-Datos del Documento-->  
       
</rich:panel>


