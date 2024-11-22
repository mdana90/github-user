## Github User App
A simple Android app to explore GitHub users and their repositories.


### 🚀 Features 
 - **List Users**: View a list of GitHub users.

    <img src="https://github.com/user-attachments/assets/2aa950a4-e70f-41bd-8d49-ec04dab042c7" width="250" alt="user list">

 - **User Repositories**: View repositories of a selected GitHub user.

    <img src="https://github.com/user-attachments/assets/a29b3ae1-2c39-4a78-8373-0f059a64029f" width="250" alt="user repository">   

### 🔆 Tech

### Architecture
This app addopt the [Google App Architecture](https://developer.android.com/topic/architecture) 

<img src="https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png" alt="Architecture Overview" width="300"/>

### Stack

#### Presentation
 - Jetpack Compose
 - ViewModel

#### Concurrency
 - Coroutine 

#### Dependency Injection
  - Hilt

#### Networking
  - Retrofit

#### Testing
  - Unit test: junit5 & mockk
  - Compose UI test
    
#### Other Tools
  - [Flipper]([url](https://github.com/facebook/flipper))
  - Compose Preview

### 🎨 Github Api
 - https://docs.github.com/en/rest

### 🥇 Run Project

#### Add personal access token
- Open or create the `local.properties` file in the root of your project.
- Add your token:
  If you don't have one, goto [GitHub Settings](https://github.com/settings/tokens) to create one
```
// local.properties

API_KEY=your_personal_access_token
```
