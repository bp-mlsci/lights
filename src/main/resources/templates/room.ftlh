<@m.page>

<h3>Room ${room.name()}</h3>

<#list colorOptions as colorOption> 
<p>${colorOption.label} 
<#list brightOptions as brightOption>
 <a href="/roomall/${room.name()}/${colorOption.name()}/${brightOption.name()}">${brightOption.label}</a>
</#list>
</#list>



</@m.page>