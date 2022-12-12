

--- Dinge so ausgeben, wie man sie schreibt ----------------------------------------------
Um eine Variable ${foo} effektiv so im Endresultat zu belassen, ${r"${foo}"} verwenden.

siehe https://freemarker.apache.org/docs/dgui_template_exp.html -> raw string literal
siehe https://stackoverflow.com/questions/5207613/how-to-output-expression-in-freemarker-without-it-being-interpreted

--- Kommentare --------------------------------------------------------------------------
Um einen Kommentar zu schreiben, welcher nicht im Endresultat erscheinen soll:
<#-- Hier steht der Kommentar -->

--- Aufteilen eines grossen Templates in mehrere Kleine ---------------------------------
<#include "mein-kleines-template.ftl" encoding="UTF-8" parse=true>

--- Eine reine Textdatei inkludieren ----------------------------------------------------
<#include "mein-reine-textdatei.txt" encoding="UTF-8" parse=false>


--- Boolean/Integer ohne Formatierungen -------------------------------------------------
Bei Booleans muss man zwingend angeben, dass man die true/false-Notation verwenden möchte. Dies gibt man mit dem ?c Parameter an,
z.B. ${isMyFlagUsed?c}.
Bei Zahlen kann man dies auch angeben, dann werden die Zahlen nicht formattiert (z.B. 10000 statt 10'000).

--- If/ If-Else -------------------------------------------------------------------------

<#if myList.length gt 0> print myList! </#if>

<#if x == 1>
  x is 1
<#else>
  x is not 1
</#if>

siehe https://freemarker.apache.org/docs/ref_directive_if.html

--- Eine List (Values) auflisten ---------------------------------------------------------

<ul>
<#list myList as listEntry>
    <li>${listEntry}</li>
</#list>
</ul>

--- Eine Map (Keys/Values) auflisten -----------------------------------------------------

<#list siteConfig.wildfly.systemProperties as systemProperty, systemPropertyValue>
    <property name="${systemProperty}" value="${systemPropertyValue}" />
</#list>
