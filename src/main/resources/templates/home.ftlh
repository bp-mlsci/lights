<@m.page>

<p>
<a href="/status">See Status</a>
</p>

<p>
<a href="/mapping">Mapping</a>
</p>

<#list colorOptions as colorOption> 
<p>${colorOption.label} 
<#list brightOptions as brightOption>
 <a href="/all/${colorOption.name()}/${brightOption.name()}">${brightOption.label}</a>
</#list>
</#list>

<#list rooms as room> 
<p><a href="/room/${room.name()}">Room ${room.name()}</a></p>
</#list>
<p>
<a href="/allwarmwhite">All Warm White</a>
</p>

<p>
<a href="/resume">Resume Program</a>
</p>


<p>
<a href="/allred">All Red</a>
</p>

<p>
<a href="/allgreen">All Green</a>
</p>

<p>
<a href="/allblue">All Blue</a>
</p>

<p>
<a href="/allpurple">All Purple</a>
</p>

<p>
<a href="/alloff">All OFF</a>
</p>


</@m.page>