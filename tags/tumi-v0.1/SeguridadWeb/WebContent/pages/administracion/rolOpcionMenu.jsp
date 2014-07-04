<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Opciones de Menu por Rol"/>
	<tiles:put name="body" value="/pages/administracion/rolOpcionMenuBody.jsp" type="page"/>
</tiles:insert>