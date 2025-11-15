<!-- 
Controllers only receive HTTP requests and return HTTP responses.

Use Cases (Application layer) enforce business rules using Domain logic.

Repositories assume that all rules have already been checked before saving.

Services are the bitches of the use cases 
-->

# AUTH

### Start
```mermaid
flowchart TD
    Start([App launched])
    
    Start --> Splash[Show splash screen]

    Splash --> Req["Send request: GET <a href="#get-authme">/auth/me</a> (Bearer JWT)"]

    Req -->|200 OK| AppStart[Show Home / Main App]
    Req -->|401 Unauthorized| LoginScreen[Show Login Screen]

    LoginScreen --> Decision{User has no account?}
    
    Decision -- Yes --> ShowSignup[Show Signup Form]
    ShowSignup --> SubmitSignup[User submits form]
    SubmitSignup --> ServerSignup[POST <a href="#post-authsignup">/auth/signup</a>]
    ServerSignup -->|OK| AppStart

    Decision -- No --> ShowLogin[Show Login Form]
    ShowLogin --> SubmitLogin[User submits form]
    SubmitLogin --> ServerLogin[POST <a href="#post-authlogin">/auth/login</a>]
    ServerLogin -->|OK| AppStart
```

### GET /auth/me 
```mermaid
sequenceDiagram
  actor User
  participant UI as APP (Splash Screen)
  participant Controller as AuthController
  participant SessionService as SessionService

  User->>UI: Starts app
  UI->>Controller: POST /auth/me 
```

### POST /auth/signup

```mermaid
sequenceDiagram
  actor User
  participant UI as App (Signup Form)
  participant Controller as AuthController
  participant UseCase as SignupUseCase
  participant UserEntity as User (Domain Entity)
  participant UserRepo as UserRepository
  participant TokenService as TokenService (JWT)

  User->>UI: clicks Signup button
  UI->>Controller: POST /auth/signup (email, password, name)
  Controller->>UseCase: execute(signupDTO)
  UseCase->>UserEntity: User(signupDTO)
  UserEntity-->>UseCase: UserInstance
  UseCase->>UserEntity: isValid()
  UserEntity-->>UseCase: validation result
  UseCase->>UserRepo: save(UserEntity)
  UserRepo-->>UseCase: success
  UseCase-->>Controller: success
  Controller-->>UI: 200 + OK
  UI-->>User: display success message
```


### POST /auth/login

```mermaid
sequenceDiagram
  actor User
  participant UI as App (login form)
  participant Controller as AuthController
  participant UseCase as LoginUseCase
  participant Session as SessionService
  participant User as UserEntity
  participant UserRepo as UserRepository
  participant TokenService as TokenService (JWT)

  User->>UI: clicks login button
  UI->>Controller: POST /auth/login (email, password)


```
