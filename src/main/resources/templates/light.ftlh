<#assign bulb=light.bulb/>
<@m.page title="Light ${bulb.display} Control">


<@m.colorControl href="/lighton/${bulb.name()}">

<@m.link label="Turn Light ${bulb.display} Off" href="/lightoff/${bulb.name()}"/>

<@m.link label="Light ${bulb.display} On Automatic Schedule" href="/lightresume/${bulb.name()}"/>

<@m.link label="Go To Light ${bulb.display}" href="http://${bulb.ip}" target="_blank"/>

</@m.colorControl>

<table>
<#list light.history as h> 
<#if h??> 
 <tr><td>${h.result()}</td><td>${h.time()}</td><td>${h.url()}</td></tr> 
</#if>
</#list>
</table>

</@m.page>