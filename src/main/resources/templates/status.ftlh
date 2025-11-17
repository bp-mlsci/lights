<htmL>
<body>
<p>
<a href="/">Home</a>
</p>

<h2>Errors</br></h2>
<#list lights as light> 
<p><b>${light.bulb.name()}</b>
<#list light.history as h> 
<#if h??> 
  <#if h.result() != '200 OK'> 
  <br> ${h.result()}
  </#if>
</#if>
</#list>
</p>
</#list>



<h2>Light Status<br></h2>
<#list lights as light> 
<p>${light.toString()}<br></p>
<#else>
<p>NO LIGHTS<br></p>
</#list>


<h2>Chase<br></h2>
<#list chase as light> 
<p>${light.toString()}<br></p>
<#else>
<p>NO Chase LIGHTS<br></p>
</#list>




</body>

</html>