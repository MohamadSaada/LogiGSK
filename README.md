# LogiGSK

A Java based software package to control Logitech G series keyboards, the following keyboards are supported:

G910 Orion Spectrum and Orion Spark </br>
G810 Orion Spectrum </br>
G610 Orion (Brown and Red) </br>
G410 Atlas Spectrum </br>

The code to control the keyboard leds is based on code from <a href="https://github.com/MatMoul">MatMoul</a> <a href="https://github.com/MatMoul/g810-led">g810-led</a> project.

<b>Features:</b> </br>
The software contains the following features:
<ul>
  <li>A freestyle selector, where any key can be selected individually and given any colour.</li>
  <li>An effect selector where one of multiple effects can be selected, current working effects are:
    <ul>
      <li>Fixed Colour effect</li>      
      <li>Breathing effect with speed slider and colour chooser</li>
      <li>Colour Cycle effect with speed slider</li>
      <li>Colour Wave effect (Horizontal, Vertical, Centre Out) with speed slider</li>
      <li>Star effect with star and sky colour chooser</li>
    </ul>
  </li>
  <li>A zone based selector, where individual zones can be given different colours</li>
  <li>A startup effect selector, where keyboard startup effect can be set to one of the following:
    <ul>
      <li>Colour Wave</li>
      <li>Fixed Colour</li>
    </ul>
  </li>
  <li>A brightness slider to control brightness of selected profile (doesn't work with animated effects)</li>
  <li>Save profile and Load profile options (works with fixed colour profiles and animated effect profiles)</li>
  <li>Set button, used for setting current profile for current user</li>
</ul>

<b>Service</b> </br>
The software comes with a service part (daemon) which is responsible for applying animated effect profiles or fixed colour profiles on startup or after resume from suspend or hibernate. The Service is implemented through a daemon program which is also implemented using Java but with the use of apache commons JSVC. 
The main job of the service is to detect whether a supported keyboard is connected and if so apply the animated effect profiles or fixed colour profiles on startup or after resume from suspend or hibernate. 
Both main program and daemon feature a hotplug feature where it detects if a supported keyboard is connected and applies a profile if one has been chosen previously for this type of keyboard. </br>

The daemon part will be responsible in connecting to a future android app that will also control keyboard colours and effects. It will also retrieve useful information from the computer (CPU, memory, etc...).

<b>Free Style</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware.png"> </br>

<b>Fixed Colour Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_FixedColour.png"> </br>

<b>Breathing Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_BreathingEffect.png"> </br>

<b>Star Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_StarEffect.png"> </br>

<b>Zones</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Zones.png"> </br>
