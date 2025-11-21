<@m.page title="Map of Lights">

<style>
table {
  border-collapse: collapse; /* Prevents double borders between cells */
}

th, td {
  border-style: solid; /* Applies a solid border to all table headers and data cells */
  border-width: 1px;
  border-color: lightgray;
  padding: 6px;
  vertical-align: top;
}
</style>



<table>
<#list grid.gridRows as gridRow>
<tr>
<#list gridRow.gridItems as gridItem>
<td>
<#if gridItem.light??>
<#assign light= gridItem.light/>
<#assign bulb = light.bulb/>
<@m.link href="/light/${bulb.name()}" label=bulb.display/> <br>

 ${light.lightMode.label}

   <#assign errs = light.errorCount />
   
   <#if errs gt 0 > <span style="color:red;">${errs} errors</span></#if>
   <#assign lstate = light.getLightState().name() >
   <#switch lstate>
   	 <#case 'NEW'> <i>new</i><#break>
   	 <#case 'WHITE'> <i>White temp ${light.colorTemp} bright ${light.brightness}</i><#break>
   	 <#case 'OFF'> <i>Off</i><#break>
   	 <#case 'RGB'> <span ><i><span style="border-color:black; border-style: solid;border-width: 1px; background-color:rgb(${light.red},${light.green},${light.blue});">&nbsp;&nbsp;&nbsp;</span> bright ${light.brightness}</i></span><#break>
   </#switch>
 </#if> 

</td>
</#list>
</tr>
</#list>
</table>


</@m.page>
