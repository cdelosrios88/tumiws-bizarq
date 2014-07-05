<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Eyder Danier Uceda Lopez -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Solicitud de Cambio      -->			
	<!-- Fehca     : 201108191122             -->
		
 <h:form id="frmAdminUsuPanel">
        <rich:panel style="width: 706px ;background-color: #DEEBF5">
        <f:facet name="header">
        	<h:outputLink value="Administración Ususario CPanel"></h:outputLink>
        </f:facet>
         
		<rich:separator height="5px"></rich:separator>
        <br/>     
            <h:panelGrid columns="3">
                <h:outputText  value="Empresa :" styleClass="tamanioLetra"/> 
                <h:inputText size="60" />
                <h:commandButton value="Buscar" styleClass="btnEstilos" ></h:commandButton>
            </h:panelGrid>
            <rich:spacer height="4px"/><rich:spacer height="4px"/>
            <h:panelGrid columns="1">
                <rich:dataTable id="dtTabla" var="item" rowKeyVar="rowkek">
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Apellido Paterno" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                        <h:outputText value="dfgdfdfhhdg"></h:outputText>
                    </rich:column>
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Apellido Materno" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                        <h:outputText value="sdfsdf"/>
                    </rich:column>
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Nombres" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                        <h:outputText value="cdhdhhgh"></h:outputText>
                    </rich:column>
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Tipo de Usuasrio" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                        <h:outputText value="dsgfdg"></h:outputText>
                    </rich:column>
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Estado" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                        <h:outputText value="xbdfxbvdf"></h:outputText>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller for="dtMenu1" maxPages="15" ></rich:datascroller>
            </h:panelGrid>
            
            <h:panelGrid columns="2">
              <a4j:commandLink disabled="true">
               <h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px ;margin-top:1px ;" ></h:graphicImage>                        
              </a4j:commandLink>
              <h:outputText value="Para Eliminar o Modificar Hacer click en Registro" style="color:#8ca0bd" styleClass="tamanioLetra"/>                                     
            </h:panelGrid>
           <br/>  
           <h:panelGrid columns="2">
	           <h:commandButton id="btnGuadar" value="Nuevo" styleClass="btnEstilos"/>
	           <h:commandButton id="btnNuevo" value="Guadar" styleClass="btnEstilos"/>
		   </h:panelGrid>         
            <rich:panel style="width: 608px ;background-color: #DEEBF5;border:2px solid #17356f;">
                <h:panelGrid columns="2" border="0" style="width: 209px;height: 50px;">
                    <h:outputText value="Tipo de Usuario :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
                    <h:selectOneMenu value="#">
                        <f:selectItem itemValue="'-1" itemLabel="Seleccionar.."/>
                        <f:selectItem itemValue="'2" itemLabel="Desarrollo1"/>
                        <f:selectItem itemValue="'3" itemLabel="Desarrollo1"/>
                        <f:selectItem itemValue="'4" itemLabel="Desarrollo2"/>
                    </h:selectOneMenu>
                     <h:outputText value="Tipo Persona :"   style="padding-left:10px;" styleClass="tamanioLetra"></h:outputText>
                    <h:selectOneMenu value="#">
                        <f:selectItem itemValue="'-1" itemLabel="Seleccionar.."/>
                        <f:selectItem itemValue="'2" itemLabel="Natural"/>
                        <f:selectItem itemValue="'3" itemLabel="Jurídica"/>
                    </h:selectOneMenu>
                </h:panelGrid>
  				 
       <rich:panel style="border:1px solid #17356f; background-color: #DEEBF5;"> 
            <h:panelGrid columns="2" border="0" style="width: 530px" cellspacing="10"  >
                <h:panelGrid columns="4" border="0" style="width: 435px;margin-top: 1px;height: 60px;" >
                        <h:outputText value="Apellido Paterno :" styleClass="tamanioLetra"></h:outputText>
                        <h:inputText size="60"></h:inputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value="Apellido Materno :" styleClass="tamanioLetra" ></h:outputText >
                        <h:inputText size="60"></h:inputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value="Nombres :" styleClass="tamanioLetra"></h:outputText>
                        <h:inputText size="60"></h:inputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value=""></h:outputText>

                  </h:panelGrid>
                  <h:panelGrid border="0" cellspacing="0" cellpadding="0">
                    <h:inputTextarea value="foto" style="height: 70px ;margin-top: 12px;"></h:inputTextarea>
                   </h:panelGrid>
              </h:panelGrid>        
            <h:panelGrid columns="3" border="0" style="width: 380px ;margin-top: 1px" cellspacing="0" cellpadding="0">
                <h:outputText value="Nro Documento :" styleClass="tamanioLetra"></h:outputText>
                <h:selectOneMenu  >
                    <f:selectItem itemValue="-1" itemLabel="Seleccionar.."></f:selectItem>
                    <f:selectItem itemValue="2" itemLabel="DNI"></f:selectItem>
                    <f:selectItem itemValue="3" itemLabel="Pasaporte"></f:selectItem>
                    <f:selectItem itemValue="4" itemLabel="Carnet de Extrangería"></f:selectItem>
                </h:selectOneMenu>
                <h:inputText size="20"></h:inputText>
           </h:panelGrid>                    
          </rich:panel>
                <rich:panel style="border:1px solid #17356f;background-color: #DEEBF5">
                    <h:panelGrid columns="3"   style="height: 80px" >
                        <h:outputText value="Servidor :" styleClass="tamanioLetra"></h:outputText>
                        <h:inputText size="30"></h:inputText>
                        <h:commandButton value="Examinar" styleClass="btnEstilos"></h:commandButton>
                        <h:outputText value="Usuario :" styleClass="tamanioLetra"></h:outputText>
                        <h:inputText size="30"></h:inputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value="Password :" styleClass="tamanioLetra" ></h:outputText>
                        <h:inputText size="30"></h:inputText>
                        <h:outputText value=""></h:outputText>
                        <h:outputText value="Verificar Password :"styleClass="tamanioLetra"></h:outputText>
                        <h:inputText size="30"></h:inputText>
                    </h:panelGrid>
                </rich:panel>                
           <br><br/>
                <h:outputText value="Permisos" styleClass="tamanioLetra"></h:outputText>
                <h:panelGrid columns="1">
                    <rich:dataTable id="dtMenu1" var="item" rowKeyVar="keyvar">
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Menú1" styleClass="tamanioLetra"></h:outputText>
                            </f:facet>
                            <h:outputText value="dfbdfgds"></h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Menú2" styleClass="tamanioLetra"></h:outputText>
                            </f:facet>
                            <h:outputText value="sdfbhdfsf"></h:outputText>
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Menú3" styleClass="tamanioLetra"></h:outputText>
                            </f:facet>
                            <h:outputText value="xcvbnxb"></h:outputText>
                        </rich:column>

                        <rich:column>
                        <f:facet name="header">
                          <h:outputText value="Menú4" styleClass="tamanioLetra"></h:outputText>
                        </f:facet>
                          <h:outputText value="xcbbvxc"></h:outputText>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller for="dtMenu1" maxPages="15" ></rich:datascroller>
                </h:panelGrid>
            </rich:panel>           
  </rich:panel>
        </h:form>
	