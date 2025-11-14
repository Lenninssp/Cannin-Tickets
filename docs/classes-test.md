```mermaid
classDiagram
  direction TB
  namespace Domain {
    class Image {
      +string path
      +string fileName
      +ref imageRef
      +double fileSize

      +isFileFormatCorrect()
      +isNameCorrectLength()
      +isFileCorrectSize()
    }
    class ImageFactory {
      +Image create(string path, string fileName)
    }
  }

```