# BoardGameWork "Pyramid" Application

## Einleitung
In diesem Einzelprojekt wurde das Brettspiel "Pyramide" implementiert.

## Spielbeschreibung:
Das Spiel wird mit zwei Spielern gespielt, die nacheinander (beginnend mit dem "Player 1") dran sind.
Zu Beginn des Spiels haben die beiden Spieler null Punkte.
Auf dem Spieltisch befindet sich eine Pyramide aus 28 Karten, wobei die Karten am Rand aufgedeckt sind und die Karten dazwischen zugedeckt sind.
Außerdem gibt es einen Nachziehstapel und einen Reservestapel, der zu Beginn des Spiels leer ist.

Ein Spielzug sieht wie folgt aus:
Der Spieler kann zwei aufgedeckten Karten aus der Pyramide entfernen, wenn deren Summe 15 ergibt.
Der Spieler kann eine aufgedeckte Karte aus der Pyramide und die oberste Karte aus dem Nachziehstapel entfernen, wenn der Stapel nicht leer ist und wenn die Summe der Karten 15 ergibt.
Der Spieler kann eine Karte umdrehen, wenn der Reservestapel nicht leer ist, wobei die oberste Karte vom Reservestapel umgedreht und auf den Nachziehstapel gelegt wird.
Der Spieler kann seinen Zug passen.

Der Spieler kann nur eine von diesen Aktivitäten während des Zuges ausführen.

Das Spiel endet, wenn die Pyramide vollständig abgebaut wurde oder wenn beide Spieler nacheinender gepasst haben.

Der Gewinner ist der Spieler, der meisten Punkte gesammelt hat.

Dabei werden die Karten wie folgt bewertet:
Die Karten 2-10 besitzen den Wert 2-10 entsprechend der Zahl.
Bube hat den Wer 11.
Dame hat den Wert 12.
König hat den Wert 13.
Ace kann mit jeder beliebigen Karte gepaart werden, allerdings mit keinem weiteren Ace.

Für ein Paar entfernter Karten bekommt der Spieler 2 Punkte, falls das Paar kein Ace enthält, und 1 Punkt, falls das Paar ein Ace enthält.

## Anforderungsanalyse
In der Anforderungsanalyse werden die funktionalen Anforderungen an das Programm aus der Aufgabenstellung extrahiert und mit UML modelliert.

### Anwendungsfalldiagramm
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/a67f65dd-b808-4e88-bdca-034e3adcce72)

### Aktivitätsdiagramme
Die Abläufe innerhalb der einzelnen Anwendungsfälle werden mittels Aktivitätsdiagrammen modelliert.

#### Aktivität "Passen"
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/9630888c-50ec-4902-bb4a-0b92c8201367)
Akteure: Spieler, Software    
Auslöser: Aktivität wird ausgelöst, indem Spieler "passen" gedrückt hat.    

Standardablauf:

Spieler wählt Aktion "Passen". Software überprüft, ob der Gegner davor schon gepasst hat. Ist das der Fall, wird die Aktivität "Spiel beenden" aufgerufen. Ist das nicht der Fall, wird es in der Variable opponentPassed gemerkt, dass dieser Spieler gepasst hat, indem sie auf 'true' gesetzt wird. Der Spieler wird gewechselt.


#### Aktivität "Paar entfernen"
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/6bd1ecec-79d4-4d64-9e35-47f4040dc629)
Akteure: Spieler, Software
Auslöser: Spieler wählt eine Karte aus.

Standardablauf:
Spieler wählt erste Karte und Software prüft Validität der ersten Karte.    
Spieler wählt zweite Karte und Software prüft Validität der zweiten Karte.  
Software prüft Validität des Paars.  
Hat das Paar die Überprüfung bestanden, werden die zwei ausgewählten Karten entfernt und Punkte werden dem aktuellen Spieler gutgeschrieben. Die Software überprüft, ob die Pyramide vollständig abgebaut ist. In dem Fall, wird die "Spiel beenden" Aktivität aufgerufen. Ist das nicht der Fall, werden die Karte am Rande der Pyramide umgedreht. Es wird gemerkt, dass der Spieler in diesem ZUg nicht gepasst hat. Spieler werden gewechselt.

Falls Kartenauswahl nicht valide war, wird ein Fehler angezeigt und der Spieler wird aufgefordert, den Zug von vorne zu starten.

#### Aktivität "Spiel beenden"
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/5593f925-21be-40b2-a941-21ce6bf71065)  
Akteure: Software
Auslöser: Das Spiel endet, wenn die Pyramide vollständig abgebaut wurde oder wenn beide Spieler direkt nacheinander gepasst haben.

Standardablauf:
Wenn eine der Bedingungen vorliegt, wird der Punktenstand der Spieler aufgerufen. Die Punktenzahlen werden miteinander vergliechen. Gibt es einen Sieger, so wird sein Namen gemerkt. Ansonsten endet das Spiel mit "Unentschieden". Das Ergebnis des Spiels wird für die Spieler und die Zuschauer angezeigt. Das Spiel wird beendet. 

### Entity-Modell
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/e93e0b54-cd37-488b-9969-4e9bbdd56309)

## Design
Die in der Analysephase herausgearbeiteten Anforderungen werden in der Designphase zu Modellen der konkreten technischen Lösung ausgearbeitet.
### Klassendiagramm
Die mit der Implementierung umzusetzende Software-Architektur wird in Form eines Klassendiagramms modelliert. In Anlehnung an Domain-Driven Design (DDD) existieren hierbei Schichten für Entities und Services, die gemeinsam das Domänenmodell bilden. Darüber existiert eine GUI-Schicht, die den Spielzustand anzeigt und Eingaben des Nutzers an die Services übergibt.
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/a3ee74d1-a2f7-4cd6-af4d-f250e9d44554)

### Pseudo-Code
Einzelne Methoden der Service-Schicht, die Anwendungsfälle aus der Analysephase realisieren, werden in Pseudocode formuliert. Dies erzeugt eine Zwischenstufe von Aktivitätsdiagrammen zu konkreter Implementierung, die eine Validierung der korrekten Umsetzung der Spiellogik vereinfacht.

#### "Paar entfernen"
<img width="334" alt="entfernen" src="https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/a4000949-f572-4a06-b79a-b5b6983ac70e">

#### "Karte aufdecken"
<img width="363" alt="aufdecken" src="https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/3f4cfd0f-c8b1-45fe-9d15-6452cd95505d">

#### "Passen"
<img width="537" alt="passen" src="https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/90f1cff5-3f3c-4a7c-b216-8f5781ba1e08">

### GUI Konzept
![image](https://github.com/OlgaVysh/Pyramid_BoardGameApplication/assets/75023035/62976707-db83-4e03-b7f5-7faf642f1c73)

## Implementierung und Test
Implementierung und Test erfolgt selbst noch einmal in drei Phasen.

### Entity Layer
Als erstes wird das Entity-Modell implementiert und mit Unit-Tests validiert.
Siehe hierzu das entity-Package der Implementierung sowie der Tests im Repository.

### Service Layer
Im zweiten Schritt der Implementierung wird die Service-Schicht umgesetzt und getestet. Sie bildet gemeinsam mit der Entity Schicht das Domänenmodell der Anwendung. Da in der Service-Schicht die gesamte Spiellogik abgebildet ist, können die
Tests vollständige Probespiele (ohne GUI) durchführen und damit die Einhaltung der Spielregeln validieren.
Siehe das service-Package der Implementierung sowie der Tests im Repository.

### View Layer und Produkttest
Als letzter Schritt der Implementierung wird die View-Schicht mithilfe des BoardGameWork (BGW) realisiert.
Im Produkttest wird dann das fertige Programm auf korrekte Funktionalität überprüft, indem es mit der Anforderungsanalyse abgeglichen wird.
