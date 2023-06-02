# Cassiamon Code Generation - Project Goals

Ursprüngliche Ziele
* Erweiterbarkeit des Frameworks durch Plugins (jeder Layer einer App durch andere Open-Source Library)

Project Goals für klassisches Templating mit Freemarker (FTL)
* Durschaubares Datenmodell. Sämtliche Outputs kommen von einem Input (und können überschrieben werden)
* Hohe Flexibilität bei der Definition von Grundwerten wie z.B. Pfade/Directories
* Graph-Berechnung wird VOR dem Templating durchgeführt, um fehlende Daten zu finden (nicht während dem Templating).
* Graph-Nodes haben nur ein kleines/klares Set von Eigenschaften, d.h. wenige Typen (String, Boolean, Integer, Enums) und festgelegte Namen (template facets)
* Möglichst wenig Boilerplate Code bei der Konfiguration, d.h. schöne DSLs
* Möglichst sprechende Exceptions, damit man die Fehlerursache gut findet (z.B. XML Zeile/Spalte)


Project Goals für Kotlin
* Hoher Grad an Typisierung um Fehler zu vermeiden, d.h. Einführung von typisiertem Zugriff auf Facets (Input/Template)
* Hoher Grad an Typisierung um Fehler zu vermeiden, d.h. Verwendung von Interfaces pro Concept 
* 
