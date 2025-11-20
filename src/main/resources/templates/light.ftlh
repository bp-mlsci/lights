<@m.page title="Light">

<#assign bulb=light.bulb/>

<h3>${bulb.name()} ${bulb.description}</h3>

<#list colorOptions as colorOption> 
<p>${colorOption.label} 
<#list brightOptions as brightOption>
 <a href="/lighton/${bulb.name()}/${colorOption.name()}/${brightOption.name()}">${brightOption.label}</a>
</#list>
</#list>

<p><a href="/lightoff/${bulb.name()}">${bulb.name()} ${bulb.description} Off</a></p>


<p><a href="/lightresume/${bulb.name()}">${bulb.name()} ${bulb.description} Resume Automatic</a></p>


<p><a target="_blank" href="http://${bulb.ip}">Go To Bulb</a></p>

<#list light.history as h> 
<#if h??> 
 <p>${h.result()}</p> 
</#if>
</#list>


<@m.home/>

</@m.page>