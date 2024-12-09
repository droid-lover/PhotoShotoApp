# PhotoShotoApp
PhotoShotoApp is an interesting AI project which shows all the images from phone (which has a valid face).

We process images in batches, one batch has 100 images, so it works like this:
- we fetch all the images
- at run time we perform face detection of these images Uri.
- we only keep images which has a valid face.
- we repeat for next batches. 

Project Demo Video is here 
https://drive.google.com/file/d/1-CrT2sxSslqwRRxjMoVIoPUhxGdO7dt4/view?usp=drive_link


## This Project is built using :
* Android, Kotlin
* Jetpack Compose
* Clean Architecture (MVVM + Usecases)
* Dependency Injections HILT
* Coroutines
* Custom AppLogger.
* google.mlkit for face-detection
* and TOML for central dependencies management.

  And As we progress we can enhance it further, Project is scalable based on the architecture and approach used,
  all setups are ready and Porject can be used as its to enhance and build other feature going forward.
  <br/>&
  We can integrate analytics crashlytics, and scale it futher with implementation of other features. 





## Best Regards
### Sachin

## Instruction to run this Project:
* Clone the Repository: <br/>
     git clone https://github.com/droid-lover/PhotoShotoApp.git
  
* Open Project in Android Studio: <br/>

* Build the Project:<br/>
     Android Studio will automatically sync and build the project.<br/>
     If there are any errors, resolve them before proceeding.<br/>
     
* Run the App:<br/>
     Connect an Android device or start an emulator.<br/>
     Click the "Run" button (green triangle icon) in the toolbar.<br/>
     Select your device or emulator from the list.<br/>
     The app will be installed and launched on the selected device.<br/>

  



