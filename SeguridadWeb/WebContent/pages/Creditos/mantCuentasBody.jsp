<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         				-->
<!-- Autor     : Paul Rivera		    				-->
<!-- Modulo    : CrÃ©ditos                 				-->
<!-- Prototipo : MANTENIMIENTO DE CUENTAS-->
<!-- Fecha     : 21/02/2012                                             -->
<script language="JavaScript" src="/tumi/js/main.js" type="text/javascript">
 < 
</script>
<script type="text/javascript">
  function jsSeleccionMantCuenta(itemIdEmpresa, itemIdTipoCaptacion, itemIdCodigo) {
      document.getElementById("frmMantCuentaModalPanel:hiddenIdEmpresa").value = itemIdEmpresa;
      document.getElementById("frmMantCuentaModalPanel:hiddenIdTipoCaptacion").value = itemIdTipoCaptacion;
      document.getElementById("frmMantCuentaModalPanel:hiddenIdCodigo").value = itemIdCodigo;
  }
</script>
<rich:modalPanel id="panelUpdateDeleteMantCuenta" width="400" height="155" resizeable="false"
                 style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
            <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Cuenta"/>
            </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkMantCuenta"/>
            <rich:componentControl for="panelUpdateDeleteMantCuenta" attachTo="hidelinkMantCuenta" operation="hide"
                                   event="onclick"/>
        </h:panelGroup>
    </f:facet>
    <h:form id="frmMantCuentaModalPanel">
        <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">
            <h:panelGrid columns="2" border="0" cellspacing="4">
                <h:outputText value="¿Desea Ud. Actualizar o Eliminar una Cuenta?" style="padding-right: 35px;"/>
            </h:panelGrid>
            <rich:spacer height="16px"/>
            <h:panelGrid columns="2" border="0" style="width: 200px;">
                <h:commandButton value="Actualizar" actionListener="#{mantCuentasController.modificarMantCuenta}"
                                 styleClass="btnEstilos"/>
                <h:commandButton value="Eliminar" actionListener="#{mantCuentasController.eliminarMantCuenta}"
                                 onclick="if (!confirm('Confirma que desea eliminar este registro?')) return false;"
                                 styleClass="btnEstilos"/>
            </h:panelGrid>
            <h:inputHidden id="hiddenIdEmpresa"/>
            <h:inputHidden id="hiddenIdTipoCaptacion"/>
            <h:inputHidden id="hiddenIdCodigo"/>
        </rich:panel>
        <rich:spacer height="4px"/>
        <rich:spacer height="8px"/>
    </h:form>
</rich:modalPanel>
<h:form id="frmPrincipal">
    <rich:tabPanel>
        <rich:tab label="Mantenimiento de Cuenta">
            <rich:panel id="pMarco1" style="border:1px solid #17356f ;width:867px; background-color:#DEEBF5">
                <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
                    <h:panelGrid columns="1" style="width: 824px; text-align: center;" border="0">
                        <rich:column style="text-align:center; font-weight:bold;">
                            <h:outputText value="MANTENIMIENTO DE CUENTA"/>
                        </rich:column>
                    </h:panelGrid>
                    <h:panelGrid columns="8" id="pgBusq1" style="width: 824px" border="0">
                        <rich:column width="210px">
                            <h:outputText id="lblbusqTipoConv1" value="Estado de Solicitud de Mnto.:"/>
                        </rich:column>
                        <rich:column>
                            <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdEstSolicitud}">
                            	<f:selectItem itemLabel="Todos" itemValue="0"/>
                                <tumih:selectItems var="mon"
                                                   cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}"
                                                   itemValue="#{mon.intIdDetalle}" itemLabel="#{mon.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column width="110px" style="text-align:right;">
                            <h:outputText id="lblbusqTipoConv2" value="Tipo de Cuenta:"/>
                        </rich:column>
                        <rich:column>
                            <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intTipoCuenta}">
                            	<f:selectItem itemLabel="Todos" itemValue="0"/>
                                <tumih:selectItems var="mon" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}"
                                                   itemValue="#{mon.intIdDetalle}" itemLabel="#{mon.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="Nombre de Mnto."/>
                        </rich:column>
                        <rich:column>
                            <h:inputText value="#{mantCuentasController.strNombreMantCuenta}" size="30"/>
                        </rich:column>
                        <rich:column>
                            <h:selectBooleanCheckbox/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="Todos"/>
                        </rich:column>
                    </h:panelGrid>
                    <h:panelGrid id="pgBusq2" columns="13" border="0">
                        <rich:column width="150px">
                            <h:outputText value="Condición de Socio :"/>
                        </rich:column>
                        <rich:column>
                            <h:selectOneMenu value="#{mantCuentasController.intIdCondicionMantCuenta}">
                            	<f:selectItem itemLabel="Todas las condiciones" itemValue="0"/>
                                <tumih:selectItems var="config"
                                                   cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
                                                   itemValue="#{config.intIdDetalle}"
                                                   itemLabel="#{config.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column style="padding-left:0px;" width="150">
                            <h:outputText value="Tipo Configuración"/>
                        </rich:column>
                        <rich:column style="padding-left:0px;">
                            <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoConfig}">
                            	<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                                <tumih:selectItems var="config"
                                                   cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}"
                                                   itemValue="#{config.intIdDetalle}"
                                                   itemLabel="#{config.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column width="150px">
                            <h:outputText id="lblbusqTipoConv4" value="Condición Laboral:"/>
                        </rich:column>
                        <rich:column>
                            <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoCondicionLaboral}">
                            	<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                                <tumih:selectItems var="condi"
                                                   cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}"
                                                   itemValue="#{condi.intIdDetalle}"
                                                   itemLabel="#{condi.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column width="200px">
                            <h:outputText value="Tipo de Persona :"/>
                        </rich:column>
                        <rich:column>
                            <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoPersona}">
                            	<f:selectItem itemLabel="Todos los tipos" itemValue="0"/>
                                <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}"
                                                   itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
                            </h:selectOneMenu>
                        </rich:column>
                    </h:panelGrid>
                    <h:panelGrid id="pgBusq3" columns="10" style="width: 824px" border="0">
                        <rich:column style="padding-left:0px;">
                            <h:selectBooleanCheckbox value="#{mantCuentasController.chkFechas}">
                                <a4j:support event="onclick"
                                             actionListener="#{mantCuentasController.enableDisableControls}"
                                             reRender="pgBusq3"/>
                            </h:selectBooleanCheckbox>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="Fecha"/>
                        </rich:column>
                        <rich:column style="padding-left:0px;">
                            <h:selectOneMenu disabled="#{mantCuentasController.enabDisabFechasMantCuenta}">
                        		<f:selectItem itemLabel="Inicio" itemValue="1"/>
                        		<f:selectItem itemLabel="Fin" 	 itemValue="2"/>                            
                            </h:selectOneMenu>
                        </rich:column>
                        <rich:column style="border:none; padding-left:0px;">
                            <rich:calendar popup="true" enableManualInput="true"
                                           disabled="#{mantCuentasController.enabDisabFechasMantCuenta}"
                                           value="#{mantCuentasController.daFecIni}" datePattern="dd/MM/yyyy"
                                           inputStyle="width:70px;" cellWidth="10px" cellHeight="20px"/>
                        </rich:column>
                        <rich:column style="border:none; padding-left:0px;">
                            <rich:calendar popup="true" enableManualInput="true"
                                           disabled="#{mantCuentasController.enabDisabFechasMantCuenta}"
                                           value="#{mantCuentasController.daFecFin}" datePattern="dd/MM/yyyy"
                                           inputStyle="width:70px;" cellWidth="10px" cellHeight="20px"/>
                        </rich:column>
                        <rich:column style="padding-left:10px;">
                            <h:selectBooleanCheckbox/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="Mantenimiento Activos"/>
                        </rich:column>
                        <rich:column style="padding-left:10px;">
                            <h:selectBooleanCheckbox/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="Mantenimiento Caduco"/>
                        </rich:column>
                        <rich:column style="padding-left:10px;">
                            <a4j:commandButton value="Buscar"
                                               actionListener="#{mantCuentasController.listarMantCuentas}"
                                               styleClass="btnEstilos" reRender="pgListCuentas"/>
                        </rich:column>
                    </h:panelGrid>
                    <h:panelGrid id="pgListCuentas" border="0">
                        <rich:extendedDataTable id="edtMantCuentas" value="#{mantCuentasController.beanListMantCuentas}"
                                                rendered="#{not empty mantCuentasController.beanListMantCuentas}"
                                                enableContextMenu="false" var="item" rowKeyVar="rowKey" width="810px"
                                                height="140px" reRender="paginadoTabla"
                                                rows="#{mantCuentasController.rows}"
                                                onRowClick="jsSeleccionMantCuenta(#{item.intIdEmpresa}, #{item.intIdTipoCaptacion}, #{item.intIdCodigo});">
                            <rich:column width="40px">
                                <div align="center">
                                    <h:outputText value="#{rowKey+1}"/>
                                </div>
                            </rich:column>
                            <rich:column width="176px">
                                <f:facet name="header">
                                    <h:outputText value="Nombre"/>
                                </f:facet>
                                <h:outputText value="#{item.strDescripcion}"/>
                            </rich:column>
                            <rich:column width="65px" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Inicio"/>
                                </f:facet>
                                <h:outputText value="#{item.daFecIni}"/>
                            </rich:column>
                            <rich:column width="65px" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Fin"/>
                                </f:facet>
                                <h:outputText value="#{item.daFecFin}"/>
                            </rich:column>
                            <rich:column width="50px" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Estado"/>
                                </f:facet>
                                <h:outputText value="#{item.intIdEstSolicitud}"/>
                            </rich:column>
                            <rich:column width="100px">
                                <f:facet name="header">
                                    <h:outputText value="Cond. de Socio"/>
                                </f:facet>
                                <h:outputText value="#{item.intIdCondicionSocio}"/>
                            </rich:column>
                            <rich:column width="100px" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Tipo de Conf."/>
                                </f:facet>
                                <h:outputText value="#{item.intIdTipoConfig}"/>
                            </rich:column>
                            <rich:column width="115px">
                                <f:facet name="header">
                                    <h:outputText value="Ctas. Consideradas"/>
                                </f:facet>
                                <h:outputText value=""></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Fec. de Reg."/>
                                </f:facet>
                                <h:outputText value="#{item.strFecRegistro}"/>
                            </rich:column>
                        </rich:extendedDataTable>
                        <rich:datascroller id="paginadoTabla" for="edtMantCuentas" maxPages="1"
                                           renderIfSinglePage="false">
                            <a4j:support event="onpagechange"/>
                        </rich:datascroller>
                    </h:panelGrid>
                    <h:panelGrid columns="2" style="text-align:center">
                        <div align="center">
                            <h:outputLink value="#" id="linkMantCuenta">
                                <h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
                                <rich:componentControl for="panelUpdateDeleteMantCuenta" attachTo="linkMantCuenta"
                                                       operation="show" event="onclick"/>
                            </h:outputLink>
                             
                            <h:outputText value="Para Eliminar, Modificar o Imprimir las CUENTAS hacer click en el Registro"
                                          style="color:#8ca0bd"/>
                        </div>
                    </h:panelGrid>
                </rich:panel>
                <h:panelGrid columns="3">
                    <a4j:commandButton value="Nuevo"
                                       actionListener="#{mantCuentasController.habilitarGrabarMantCuentas}"
                                       styleClass="btnEstilos" reRender="pMantCuentas"/>
                    <h:commandButton id="btnGuardarHojaPlan" value="Grabar"
                                     actionListener="#{mantCuentasController.grabarMantCuentas}"
                                     styleClass="btnEstilos"/>
                    <a4j:commandButton value="Cancelar"
                                       actionListener="#{mantCuentasController.cancelarGrabarMantCuentas}"
                                       styleClass="btnEstilos" reRender="pMantCuentas"/>
                </h:panelGrid>
                <h:panelGrid id="pMantCuentas">
                    <rich:panel rendered="#{mantCuentasController.formMantCuentasRendered}"
                                style="width: 780px;border:1px solid #17356f;background-color:#DEEBF5;">
                        <h:panelGrid columns="5">
                            <rich:column width="160px">
                                <h:outputText value="Nombre del Mantenimiento"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:inputText value="#{mantCuentasController.beanMantCuenta.strDescripcion}" size="40"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Estado Solicitud :" style="padding-left:10px;"/>
                            </rich:column>
                            <rich:column style="text-align:center;border:1px solid #17356f;background-color:#DEEBF5;">
                                <h:outputText value="PENDIENTE" style="padding-left:10px;padding-right:10px;"/>
                            </rich:column>                            
                        </h:panelGrid>
                        <h:outputText value="#{mantCuentasController.msgTxtDescripcion}" styleClass="msgError"/>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Estado del Mantenimiento"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdEstado}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="sel"
                                                       cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}"
                                                       itemValue="#{sel.intIdDetalle}"
                                                       itemLabel="#{sel.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{mantCuentasController.msgTxtEstadoMantCuenta}"
                                              styleClass="msgError"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid id="pgRanFec" columns="6">
                            <rich:column width="160px">
                                <h:outputText value="Fecha de Inicio"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <rich:calendar popup="true" enableManualInput="true"
                                               value="#{mantCuentasController.daFechaIni}" datePattern="dd/MM/yyyy"
                                               inputStyle="width:70px;" cellWidth="10px" cellHeight="20px"/>
                            </rich:column>
                            <rich:column width="120px" style="text-align:right;">
                                <h:outputText value="Fecha de Fin :"/>
                            </rich:column>
                            <rich:column>
                                <rich:calendar popup="true" enableManualInput="true"
                                               rendered="#{mantCuentasController.fecFinMantCuentaRendered}"
                                               value="#{mantCuentasController.daFechaFin}" datePattern="dd/MM/yyyy"
                                               inputStyle="width:70px;" cellWidth="10px" cellHeight="20px"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneRadio value="#{mantCuentasController.rbFecIndeterm}"
                                                  valueChangeListener="#{mantCuentasController.showFecFin}">
                                    <f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
                                    <f:selectItem itemLabel="Indeterminado" itemValue="2"/>
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgRanFec"/>
                                </h:selectOneRadio>
                            </rich:column>
                        </h:panelGrid>
                        <h:outputText value="#{mantCuentasController.msgTxtFechaIni}" styleClass="msgError"/>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Tipo de Persona"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoPersona}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="sel"
                                                       cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}"
                                                       itemValue="#{sel.intIdDetalle}"
                                                       itemLabel="#{sel.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{mantCuentasController.msgTxtTipoPersona}" styleClass="msgError"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Condición de Socio"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneRadio value="#{mantCuentasController.rbCondSocio}">
                                    <f:selectItem itemLabel="Todos" itemValue="1"/>
                                    <f:selectItem itemLabel="Elegir" itemValue="2"/>
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.listarCondSocio}"
                                                 reRender="dtCondSocio"/>
                                </h:selectOneRadio>
                            </rich:column>
                            <rich:column>
                                <iframe frameborder="1" scrolling="yes" width="200px" height="20"/>
                            </rich:column>                            
                        </h:panelGrid>
                        <h:panelGrid id="dtCondSocio">
                            <rich:dataTable value="#{mantCuentasController.beanListCondSocio}" rows="10"
                                            rendered="#{not empty mantCuentasController.beanListCondSocio}" var="item"
                                            rowKeyVar="rowKey" sortMode="single" width="500px">
                                <rich:column width="15px;">
                                    <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
                                </rich:column>
                                <rich:column width="500px">
                                    <h:outputText value="xxx"/>
                                </rich:column>
                            </rich:dataTable>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Tipo de Condición Laboral"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoCondicionLaboral}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="condi"
                                                       cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}"
                                                       itemValue="#{condi.intIdDetalle}"
                                                       itemLabel="#{condi.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{mantCuentasController.msgTxtCondicionLaboral}"
                                              styleClass="msgError"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Tipo de Descuento"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoDcto}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="dscto"
                                                       cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}"
                                                       itemValue="#{dscto.intIdDetalle}"
                                                       itemLabel="#{dscto.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{mantCuentasController.msgTxtTipoDscto}" styleClass="msgError"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Tipo de Moneda"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdMoneda}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="mon"
                                                       cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}"
                                                       itemValue="#{mon.intIdDetalle}"
                                                       itemLabel="#{mon.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{mantCuentasController.msgTxtMoneda}" styleClass="msgError"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="6">
                            <rich:column width="160px">
                                <h:outputText value="Tipo de Configuración"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdTipoConfig}">
                                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                                    <tumih:selectItems var="config"
                                                       cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}"
                                                       itemValue="#{config.intIdDetalle}"
                                                       itemLabel="#{config.strDescripcion}"/>
                                </h:selectOneMenu>
                            </rich:column>
                            <rich:column>
                                <h:selectOneRadio value="#{mantCuentasController.strValManCuenta}">
                                    <f:selectItem itemLabel="Importe" itemValue="1"/>
                                    <f:selectItem itemLabel="%" itemValue="2"/>
                                </h:selectOneRadio>
                            </rich:column>
                            <rich:column>
                                <h:inputText value="#{mantCuentasController.beanMantCuenta.flValorConfig}"
                                             onblur="extractNumber(this,2,false);"
                                             onkeyup="extractNumber(this,2,false);" size="10"/>
                            </rich:column>
                            <rich:column>
                                <h:selectOneMenu value="#{mantCuentasController.beanMantCuenta.intIdAplicacion}">
                                    <f:selectItem itemLabel="RMV" itemValue="1"/>
                                </h:selectOneMenu>
                            </rich:column>
                        </h:panelGrid>
                        <h:outputText value="#{mantCuentasController.msgTxtTipoConfig}" styleClass="msgError"/>
                        <h:panelGrid id="pgLimiteEdad" columns="4">
                            <rich:column width="160px">
                                <h:outputText value="Cuentas Consideradas"/>
                            </rich:column>
                            <rich:column width="6px">
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Aportaciones"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="3">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Créditos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="3">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Ahorros"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="3">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Depósitos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="3">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Cuenta Corriente"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid id="pgModelosContables" columns="5">
                            <rich:column width="160px">
                                <h:outputText value="Modelos Contables"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Solicitar" styleClass="btnEstilos"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Estado"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=":"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column width="210px">
                                <h:outputText value="Provisión de Mantenimiento"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Detalle" styleClass="btnEstilos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column width="210px">
                                <h:outputText value="Extorno de Mantenimiento"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Detalle" styleClass="btnEstilos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column width="210px">
                                <h:outputText value="Cancelación con Transferencia"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Detalle" styleClass="btnEstilos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column width="210px">
                                <h:outputText value="Cancelación por Planilla de Sueldos"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Detalle" styleClass="btnEstilos"/>
                            </rich:column>
                        </h:panelGrid>
                        <h:panelGrid columns="4">
                            <rich:column width="174px"></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox value="#{mantCuentasController.chkLimiteEdad}">
                                    <a4j:support event="onclick"
                                                 actionListener="#{mantCuentasController.enableDisableControls}"
                                                 reRender="pgLimiteEdad"/>
                                </h:selectBooleanCheckbox>
                            </rich:column>
                            <rich:column width="210px">
                                <h:outputText value="Cancelación por Caja"/>
                            </rich:column>
                            <rich:column>
                                <a4j:commandButton value="Detalle" styleClass="btnEstilos"/>
                            </rich:column>
                        </h:panelGrid>
                    </rich:panel>
                </h:panelGrid>
            </rich:panel>
        </rich:tab>
    </rich:tabPanel>
</h:form>