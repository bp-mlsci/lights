<#assign bulb=light.bulb/>
<@m.page title="Light ${bulb.display} Control">


<@m.colorControl href="/lighton/${bulb.name()}">

<@m.link label="Turn Light ${bulb.display} Off" href="/lightoff/${bulb.name()}"/>

<@m.link label="Light ${bulb.display} On Automatic Schedule" href="/lightresume/${bulb.name()}"/>

<@m.link label="Go To Light ${bulb.display}" href="http://${bulb.ip}" target="_blank"/>

</@m.colorControl>

<#list light.history as h> 
<#if h??> 
 <p>${h.result()}</p> 
</#if>
</#list>


</@m.page>