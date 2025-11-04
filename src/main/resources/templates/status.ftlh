<htmL>
<body>
<h2>Light Status<br></h2>
<#list lights as light> 
<p>${light.toString()}<br></p>
<#else>
<p>NO LIGHTS<<br></p>
</#list>


<h2>Chase<br></h2>
<#list chase as light> 
<p>${light.toString()}<br></p>
<#else>
<p>NO Chase LIGHTS<<br></p>
</#list>


</body>

</html>