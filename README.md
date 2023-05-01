<div align="center">
<h1>Compose Custom Scaffold</h1>
<h4>This is a custom compose scaffold library that draws a custom navigation drawer and puts the rest of the App UI on top of the drawer at the
screen edge instead of using the default ModalDrawer that comes with the default Scaffold, which draws the drawer on top of the App UI with a 
Scrim that blocks interaction with the rest of the App UI.</h4>

<h5>This library is base on https://github.com/razaghimahdi/Card-Drawer</5>


  [![](https://jitpack.io/v/isaacjadrey/Custom-Compose-Scaffold.svg)](https://jitpack.io/#isaacjadrey/Custom-Compose-Scaffold)
  [![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
  [![PRWelcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/isaacjadrey/Custom-Compose-Scaffold/pulls)
  ![Language](https://img.shields.io/badge/language-Kotlin-orange.svg)
  [![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/isaacjadrey/Custom-Compose-Scaffold/LICENSE)

</div>

## Quick Start:
To get the Git project in to your build:

1. Add the JitPack repository to your settings.properties file 
```Kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency in your app.module gradle file
```kotlin
dependencies {
  implementation 'com.github.isaacjadrey:Custom-Compose-Scaffold:1.0.0'
}
```

## Demo: 

| Closed | Open | Navigation
| --- | --- | --- |
| <img src="src/drawer_closed.jpg" width="300"/> | <img src="src/drawer_open.jpg" width="300"/> | <img src="src/drawer_demo.gif" width="300"> |

