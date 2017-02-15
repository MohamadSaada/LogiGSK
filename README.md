# LogiGSK

A Java based software package to control Logitech G series keyboards, the following keyboards are supported:

G910 Orion Spectrum and Orion Spark </br>
G810 Orion Spectrum </br>
G610 Orion (Brown and Red) </br>
G410 Atlas Spectrum </br>

The code to control the keyboard leds is based on code from <a href="https://github.com/MatMoul">MatMoul</a> <a href="https://github.com/MatMoul/g810-led">g810-led</a> project.

Ready to deploy jars can be found at <a href="https://sites.google.com/mohamadsaada.com/logigsk/home">LogiGSK</a>

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



<a href="https://github.com/MohamadSaada/LogiGSK/wiki/Installation"><h2><b>Installation</b></h2></a>

### Using provided package creator script file (best way)
* Clone and extract repository 
* To get a .deb package run `sudo ./logigsk-buildpackage deb`
* To get a .rpm package run `sudo ./logigsk-buildpackage rpm` (Please note that rpm are created using alien tool through converting .deb package to .rpm)
* To get both packages run `sudo ./logigsk-buildpackage`
* Install the resulting package
* To uninstall do so through your package manager, example in Ubuntu `sudo apt-get remove logigsk`.

Please note that the package creator script `logigsk-buildpackage` uses Apache Ant, java, alien and dpkg tools to compile the source code to create executable jars which in turn are used along with other scripts to create the final package, you can trace every step in `logigsk-buildpackage` for a better understanding

### Using provided package files (second best way)
* [.deb package](https://drive.google.com/open?id=0B9Jw3nU_1quKcnlMTEZsbFl3Q0E)
* [.rpm package](https://drive.google.com/open?id=0B9Jw3nU_1quKMWNzRm5LOXAwVzQ)

### Using provided INSTALL script (third best way)
* Clone and extract repository
* Run the script by typing `sudo ./INSTALL`
* To uninstall run `sudo logigsk UNINSATLL`

Please note that this script is very similar to the package creating script but it doesn't create a package, but rather installs the files directly to the directories bypassing the package installation through package manager step. it also differs from the package installation step in that it has an UNINSTALL script which can be executed by running `sudo logigsk UNINSATLL`.

<b>Free Style</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_1.png"> </br>

<b>Effects</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_2.png"> </br>

<b>Zones</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_3.png"> </br>

<b>Startup Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_4.png"> </br>

<b>Fixed Colour Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_FixedColour.png"> </br>

<b>Breathing Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Breathing.png"> </br>

<b>Colour Wave</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_ColourWave.png"> </br>

<b>Colour Cycle</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_ColourCycle.png"> </br>

<b>Star Effect</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Star.png"> </br>

<b>Settings</b> </br></br>
<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Settings_1.png"> </br>

<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Settings_2.png"> </br>

<img src="https://raw.githubusercontent.com/MohamadSaada/LogiGSK/master/SampleImages/LogitechGSeriesKeyboardSoftware_Settings_3.png"> </br>
