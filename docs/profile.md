# Profile

### Modify user info
```mermaid
sequenceDiagram
  actor User
  participant UI as App (profile view)
  participant Controller as ModifiedUserController
  participant Session as SessionService (JWT)
  participant UseCase as ModifyUserUseCase
  participant User as User (Domain Entity)
  participant UserRepo as UserRepository

  User->>UI: modify personal info and clicks "create"
  UI->>Controller: PUT /profile/modify (UpdateProfileDTO + Bearer JWT)
  Controller->>Session: validateAndExtractClaims(jwt)
  alt Invalid/expired token
    Controller-->>UI: 401 Unauthorized
    UI-->>User: Redirect to Login
  else Valid token
    Session-->>Controller: claims {userId, role}
    Controller->>UseCase: execute(claims, UpdateProfileDTO)
    UseCase->>UserRepo: findById(claims.userId)
    UserRepo-->>UseCase: UserEntity
    UseCase->>UserEntity: applyProfileChanges(UpdateProfileDTO)
    alt Domain validation fails
      User-->>UseCase: DomainError
      UseCase-->>Controller: DomainError
      Controller-->>UI: 400 bad request + error message
    else
      UserEntity-->>UseCase: User (modified)
      UseCase->>UserRepo: modify(User)
      User-->>UseCase: Success
      UseCase->>Controller: Success
      Controller-->>UI: 200 OK "User modified Successfully"
    end
  end
```

### Change user password
```mermaid
sequenceDiagram
  actor User
  participant UI as App (Profile View)
  participant Controller as ChangePasswordController
  participant Session as SessionService (JWT)
  participant UseCase as ChangePasswordUseCase
  participant UserEntity as User (Domain Entity)
  participant UserRepo as UserRepository
  participant Hashing as PasswordService

  User->>UI: fills form & clicks "change password"
  UI->>Controller: PUT /profile/change-password (ChangePasswordDTO + Bearer JWT)

  Controller->>Session: validateAndExtractClaims(jwt)
  alt Invalid/expired token
      Controller-->>UI: 401 Unauthorized
      UI-->>User: Redirect to Login
  else Valid token
      Session-->>Controller: claims {userId}
      Controller->>UseCase: execute(claims, ChangePasswordDTO)

      UseCase->>UserRepo: findById(claims.userId)
      UserRepo-->>UseCase: UserEntity

      UseCase->>Hashing: compare(ChangePasswordDTO.oldPassword, UserEntity.hashedPassword)
      alt Old password mismatch
          Hashing-->>UseCase: false
          UseCase-->>Controller: DomainError("Incorrect password")
          Controller-->>UI: 400 Bad Request + message
      else Match
          Hashing-->>UseCase: true
          UseCase->>UserEntity: User.changePassword(ChangePasswordDTO.newPassword)

          alt Domain validation fails (min length, etc)
              UserEntity-->>UseCase: DomainError
              UseCase-->>Controller: 400 Bad Request
          else Valid
              UseCase->>UserRepo: save(UserEntity)
              UserRepo-->>UseCase: Success

              UseCase-->>Controller: Success
              Controller-->>UI: 204 No Content
          end
      end
  end

```

### Change user image
```mermaid
sequenceDiagram
  actor User
  participant UI
  participant Controller as ProfilePictureController
  participant Session as SessionService (JWT)
  participant UseCase as ProfilePictureUseCase
  participant UserRepo as UserRepository
```