<%@ page import="com.qwerty.Tenant" %>


<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'accountOwner', 'error')} required">
	<label for="accountOwner">
		<g:message code="tenant.accountOwner.label" default="Select Api Keys" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="apiKeys" name='apiKeys' value="${tenantInstance?.apiKeys?.id}"
			  noSelection="${['':'--Select One--']}"
			  from='${com.qwerty.TenantKeys.list()}'
			  optionKey="id" optionValue="${{it.accessKey1 +','+it.accessKey2}}" multiple="true">
	</g:select>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'accountOwner', 'error')} required">
	<label for="accountOwner">
		<g:message code="tenant.accountOwner.label" default="Account Owner" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="accountOwner" type="number" value="${tenantInstance.accountOwner}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'companyUrl', 'error')} required">
	<label for="companyUrl">
		<g:message code="tenant.companyUrl.label" default="Company Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="companyUrl" required="" value="${tenantInstance?.companyUrl}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'defaultClassName', 'error')} required">
	<label for="defaultClassName">
		<g:message code="tenant.defaultClassName.label" default="Default Class Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="defaultClassName" required="" value="${tenantInstance?.defaultClassName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'logoUrl', 'error')} required">
	<label for="logoUrl">
		<g:message code="tenant.logoUrl.label" default="Logo Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="logoUrl" required="" value="${tenantInstance?.logoUrl}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="tenant.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${tenantInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'orgId', 'error')} required">
	<label for="orgId">
		<g:message code="tenant.orgId.label" default="Org Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="orgId" required="" value="${tenantInstance?.orgId}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantInstance, field: 'tagLine', 'error')} required">
	<label for="tagLine">
		<g:message code="tenant.tagLine.label" default="Tag Line" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tagLine" required="" value="${tenantInstance?.tagLine}"/>

</div>

