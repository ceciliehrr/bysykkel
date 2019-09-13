# Bysykkel - OSLO
En liten Android 9.0(Pie) applikasjon skrevet i Java som henter informasjon fra [Urbansharing - oslo bysykkel](https://gbfs.urbansharing.com/oslobysykkel.no/).
Appliksjonen bruker klassebiblioteket Volley for asynkron henting av JSON-data, og viser dataene i et recyclerview. Filtrerer recycleviewet basert på søk.

Åpner liste med sykkelstasjoner i Google kart.


## Installasjon
Bruk [Android Studio](https://developer.android.com/studio/) som består av Java JDK, Android SDK og Android Studio for å se appen i emulator.
Under SDK Tools i Android Studio bør du kontrollere at Intel x86 Emulator Accelerator (HAXM installer) og
Google USB Driver er installert i tillegg til det som er default.

Appen startes fra MainActivity.java som ligger i sykkelinfo\app\src\main\java\com\example\sykkelinfo

Den kan testet på to forskjellige måter:
* På en virtuell enhet (AVD) i Android-emulatorene som følger med Android SDK.
  
* På en fysisk Android-enhet som du kobler til utviklings-PCen med USB-kabel.

## Test
Det er laget en unit-test som er plassert i sykkelinfo\app\src\test\java\com\example\sykkelinfo\Model\SykkelstasjonTest.java , 
og en MainActivityTest i Espresso som tester tittel og knapp(ny aktivitet), er plassert: sykkelinfo\app\src\androidTest\java\com\example\sykkelinfo\MainActivityTest.java
