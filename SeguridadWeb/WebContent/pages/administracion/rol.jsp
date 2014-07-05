<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Roles de Usuario"/>
	<tiles:put name="body" value="/pages/administracion/rolBody.jsp" type="page"/>
</tiles:insert>