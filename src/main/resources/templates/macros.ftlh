
<#macro page title="CCL Lights">
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${title}</title>
  <link rel='stylesheet' href='/webjars/bootstrap//5.3.8/css/bootstrap.min.css'>
</head>
<body>
<div class="container"><!-- BEGIN BOOTSTRAP CONTAINER -->
<div class="pt-2"><@link label="Home" href="/"/> <@link label="Map" href="/mapping"/>
<#list rooms as room> <@link label=room.label href="/room/${room.name()}"/> </#list>
<#--  <@link label="Status" href="/status"/> -->
</div>
<h3 class="pt-3">${title}</h3>
<#nested>
<br>
<img src="/logo.jpg"/>
</div> <!-- END BOOTSTAP CONTAINER -->
<script src="/webjars/bootstrap/5.3.8/js/bootstrap.bundle.min.js"></script> <#--  MAKE BOOTSTRAP GO -->
</body>
</html>
</#macro>


<#macro link label href type='primary' target=''>
<a class="btn mt-2 btn-${type}" href="${href}"<#if target?has_content> target="${target}"</#if>>${label}</a>
</#macro> 

<#macro home><@link label="Home" href="/"/></#macro>


<#macro colorControl href>
<table>
<#list colorOptions as colorOption> 
<tr><td><span style="background-color:${colorOption.htmlColor}">&nbsp;&nbsp;&nbsp;</span> ${colorOption.label}</td><td> 
<#list brightOptions as brightOption>
 <a class="btn btn-secondary" href="${href}/${colorOption.name()}/${brightOption.name()}">${brightOption.label}</a>
</#list>
</td></tr>
</#list>
</table>
<div class="pt-3">
<#nested>
</div>
  <form action="/rgb${href}" method="get" >
    <label for="myColor" class="form-label">Choose Custom Color:</label>
    <input type="color" class="form-control form-control-color" id="myColor" value="#CCCCCC" title="Choose a color" name="colorHex">
  	<label for="myBright" class="form-label">Choose Custom Brightness:</label>
    <input input type="number" min="0" max="255" step="1" value="255"  class="form-control" id="myBright" title="Choose a brightness" name="bright">
  	<button class="btn btn-primary pt-1" type="submit">Set Custom Color</button>
  </form>
</#macro>