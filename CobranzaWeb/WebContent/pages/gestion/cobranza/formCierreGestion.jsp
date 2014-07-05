<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
  
  
  <rich:panel id="formCierreGestion" style="width: 1500px;" rendered="#{cobranzaController.habilitarFormCierre}">
  
                <h:panelGroup id="pgFilGestionCobranzaCie" layout="block" style="padding:15px">
                            <rich:extendedDataTable id="tblFilGestionCobranzaCie" value="#{cobranzaController.beanListCobranzasCierre}" 
                                 rendered="#{not empty cobranzaController.beanListCobranzasCierre}" 
                            	 var="item"   rowKeyVar="rowkey" sortMode="single"  width="1470px" height="195px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="100px" sortBy="#{item.id.intItemGestionCobranza}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEncaNombArea" value="Nro Gestión" ></h:outputText>
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
                                <rich:column width="170px" sortBy="#{item.intTipoGestionCobCod}">
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
                                 
                                <rich:column width="110px">
                                     <f:facet name="header">
                                         <h:outputText  id="lblHInicio" value="H. Incio" ></h:outputText>
                                     </f:facet>
                                     <h:outputText rendered="#{item.dtHoraFin != null}"   
                                     	value="#{item.dtHoraInicio}" >
                                        <f:convertDateTime pattern="hh:mm a" />
                                     </h:outputText>
                                     <h:selectOneMenu rendered="#{item.dtHoraFin == null}"  
                                     	value="#{item.intHoraInicio}">
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
						            <h:outputText rendered="#{item.dtHoraFin == null}">:</h:outputText>
						            <h:selectOneMenu  rendered="#{item.dtHoraFin == null}" value="#{item.intMiniInicio}">
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
	           
                                </rich:column>
                                <rich:column width="110px">
                                    <f:facet name="header">
                                        <h:outputText  id="lblHFin" value="Hora.Fin" ></h:outputText>
                                    </f:facet>
                                         <h:outputText rendered="#{item.dtHoraFin != null}"   value="#{item.dtHoraFin}" >
                                            <f:convertDateTime pattern="hh:mm a" />
                                         </h:outputText>
                                      <h:selectOneMenu rendered="#{item.dtHoraFin == null}" 
                                      	value="#{item.intHoraFin}">
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
						            <h:outputText rendered="#{item.dtHoraFin == null}">:</h:outputText>
						            <h:selectOneMenu  rendered="#{item.dtHoraFin == null}" value="#{item.intMiniFin}">
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
	                           </rich:column>
                                
				                 <f:facet name="footer">
							   		 <h:panelGroup layout="block">
								   		 <rich:datascroller for="tblFilGestionCobranzaCie" maxPages="10"/>
									 </h:panelGroup>  
						       </f:facet>  
                 </rich:extendedDataTable>      
              </h:panelGroup>
          
  
          <h:panelGrid  columns="2" style="width:710px;">
	     	 <rich:column style="width:80px; border: none">
         		<h:outputText   value="Observación :"> </h:outputText>
         	</rich:column>
         	<rich:column style="width:600px; border: none">
         	     <h:inputText size="110"  value="#{cobranzaController.beanGestCobCierre.strObservacion}" ></h:inputText>
         	</rich:column>
        </h:panelGrid>
</rich:panel>