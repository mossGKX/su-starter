<#--
 * 对应shiro的hasPermission标签。
 -->
<#macro has name>
	<@shiro.hasPermission name="${name}">
	<#nested>
	</@shiro.hasPermission>
</#macro>

<#--
 * 对应shiro的lacksPermission标签。
 -->
<#macro not name>
	<@shiro.lacksPermission name="${name}">
	<#nested>
	</@shiro.lacksPermission>
</#macro>

<#--
 * 对应shiro的hasAnyRoles标签。
 -->
<#macro any name>
	<@shiro.hasAnyRoles name="${name}">
	<#nested>
	</@shiro.hasAnyRoles>
</#macro>