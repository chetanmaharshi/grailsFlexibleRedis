
<%@ page import="com.qwerty.Tenant" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tenant.label', default: 'Tenant')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tenant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-tenant" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="accountOwner" title="${message(code: 'tenant.accountOwner.label', default: 'Account Owner')}" />
					
						<g:sortableColumn property="companyUrl" title="${message(code: 'tenant.companyUrl.label', default: 'Company Url')}" />
					
						<g:sortableColumn property="defaultClassName" title="${message(code: 'tenant.defaultClassName.label', default: 'Default Class Name')}" />
					
						<g:sortableColumn property="logoUrl" title="${message(code: 'tenant.logoUrl.label', default: 'Logo Url')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'tenant.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="orgId" title="${message(code: 'tenant.orgId.label', default: 'Org Id')}" />

						<g:sortableColumn property="orgId" title="${message(code: 'tenant.orgId.label', default: 'Keys')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tenantInstanceList}" status="i" var="tenantInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tenantInstance.id}">${fieldValue(bean: tenantInstance, field: "accountOwner")}</g:link></td>
					
						<td>${fieldValue(bean: tenantInstance, field: "companyUrl")}</td>
					
						<td>${fieldValue(bean: tenantInstance, field: "defaultClassName")}</td>
					
						<td>${fieldValue(bean: tenantInstance, field: "logoUrl")}</td>
					
						<td>${fieldValue(bean: tenantInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: tenantInstance, field: "orgId")}</td>
						<td>
						<g:each in="${tenantInstance.apiKeys}" var="apiK">
							${apiK.accessKey1},${apiK.accessKey2}<br/>
						</g:each>
						</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tenantInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
