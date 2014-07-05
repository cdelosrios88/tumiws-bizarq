<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Administracion de Sistemas"/>
	<tiles:put name="body" value="/pages/administracion/sistemaBody.jsp" type="page"/>
</tiles:insert>