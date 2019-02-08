Memory Manager allows you to monitor changes in FPS and consumption of memory within the application.

![](https://media.giphy.com/media/1n4I0qFxn613bqUvy3/giphy.gif)

# Usage

Add following code to ``` build.gradle ```

```gradle
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.kirilamenski:memorymanager:0.1.8'
}

```

You can use this library with ``` activity ``` or with ``` application ```. 
But to use it with ``` application ``` you need add in AndroidManifest:

```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

and also allow "Display over other apps" in the settings:

<img src="https://i.imgur.com/y4w6edM.png" width="250" height="430" />

Kotlin

```kotlin
  val memoryManager = MemoryManager.init(applicationContext)
  memoryManager?.delay = 1000
  memoryManager?.x = 10
  memoryManager?.y = 300
```

to destroy manager call 
```kotlin
MemoryManager.destroy()
```
