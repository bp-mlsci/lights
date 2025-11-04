<htmL>
<body>
<h2>Light Status<br></h2>
<#list lights as light> 
<p>${light.toString()}<br></p>
<#else>
<p>NO LIGHTS<<br></p>
</#list>

</body>

</html>