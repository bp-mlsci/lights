<@m.page>

<h3>${room.label}</h3>

<#list colorOptions as colorOption> 
<p>${colorOption.label} 
<#list brightOptions as brightOption>
 <a href="/roomall/${room.name()}/${colorOption.name()}/${brightOption.name()}">${brightOption.label}</a>
</#list>
</#list>

<p><a href="/roomoff/${room.name()}">${room.label} Off</a></p>


<p><a href="/roomresume/${room.name()}">${room.label} Resume Automatic</a></p>


</@m.page>