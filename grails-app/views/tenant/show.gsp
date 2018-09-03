
<%@ page import="com.qwerty.Tenant" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tenant.label', default: 'Tenant')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tenant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tenant" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tenant">
			
				<g:if test="${tenantInstance?.accountOwner}">
				<li class="fieldcontain">
					<span id="accountOwner-label" class="property-label"><g:message code="tenant.accountOwner.label" default="Account Owner" /></span>
					
						<span class="property-value" aria-labelledby="accountOwner-label"><g:fieldValue bean="${tenantInstance}" field="accountOwner"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantInstance?.companyUrl}">
				<li class="fieldcontain">
					<span id="companyUrl-label" class="property-label"><g:message code="tenant.companyUrl.label" default="Company Url" /></span>
					
						<span class="property-value" aria-labelledby="companyUrl-label"><g:fieldValue bean="${tenantInstance}" field="companyUrl"/></span>
					
				</li>
				</g:if>
				<g:if test="${tenantInstance?.apiKeys}">
					<li class="fieldcontain">
						<span id="defaultClassName-label" class="property-label"><g:message code="tenant.defaultClassName.label" default="Api Keys" /></span>
						<g:each in="${tenantInstance.apiKeys}" var="apiK">
							<span class="property-value" aria-labelledby="defaultClassName-label">
								${apiK.accessKey1},${apiK.accessKey2}<br/>
							</span>
						</g:each>
					</li>
				</g:if>
			
				<g:if test="${tenantInstance?.defaultClassName}">
				<li class="fieldcontain">
					<span id="defaultClassName-label" class="property-label"><g:message code="tenant.defaultClassName.label" default="Default Class Name" /></span>
					
						<span class="property-value" aria-labelledby="defaultClassName-label"><g:fieldValue bean="${tenantInstance}" field="defaultClassName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantInstance?.logoUrl}">
				<li class="fieldcontain">
					<span id="logoUrl-label" class="property-label"><g:message code="tenant.logoUrl.label" default="Logo Url" /></span>
					
						<span class="property-value" aria-labelledby="logoUrl-label"><g:fieldValue bean="${tenantInstance}" field="logoUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="tenant.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${tenantInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantInstance?.orgId}">
				<li class="fieldcontain">
					<span id="orgId-label" class="property-label"><g:message code="tenant.orgId.label" default="Org Id" /></span>
					
						<span class="property-value" aria-labelledby="orgId-label"><g:fieldValue bean="${tenantInstance}" field="orgId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantInstance?.tagLine}">
				<li class="fieldcontain">
					<span id="tagLine-label" class="property-label"><g:message code="tenant.tagLine.label" default="Tag Line" /></span>
					
						<span class="property-value" aria-labelledby="tagLine-label"><g:fieldValue bean="${tenantInstance}" field="tagLine"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form action="delete" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" id="${tenantInstance.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:hiddenField name="id" value="${tenantInstance.id}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
