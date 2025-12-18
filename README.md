**Image Text Extractor (Android)**

An Android application built with Jetpack Compose that extracts text from images using Google ML Kit OCR. Users can select images from the gallery or capture photos using the camera, extract text, and copy only selected text to the clipboard.

**Features**

 Capture image using device camera

 Select image from internal storage (gallery)

 Extract text using Google ML Kit Text Recognition (OCR)

 Select specific text from extracted content

 Copy only selected text to clipboard

 Loading indicator while OCR is processing

 Modern UI built with Jetpack Compose (Material 3)

 **Tech Stack**

Language: Kotlin

UI: Jetpack Compose (Material 3)

OCR: Google ML Kit Text Recognition

Image Loading: Coil

Architecture: Single-Activity, Compose-based

Minimum SDK: 21+

**Libraries Used**
// ML Kit OCR
implementation("com.google.mlkit:text-recognition:16.0.0")

// Jetpack Compose
implementation(platform("androidx.compose:compose-bom"))
implementation("androidx.compose.material3:material3")

// Image loading
implementation("io.coil-kt:coil-compose:2.4.0")

**Permissions**

The app requires the following permissions:

<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

##App Flow
**App Flow**

User selects or captures an image

Image is processed using ML Kit OCR

Extracted text is displayed in a selectable text field

User selects required text

**UI Overview**

App title and icon at the top

Image preview after selection

Scrollable content area

Fixed bottom card with action buttons

Clean and responsive layout

Selected text is copied to clipboard

**Use Cases**

Extract text from documents

Copy notes from images

Scan printed text quickly

Useful for students and professionals

**Limitations**

Works best with clear images

No offline language switching (default Latin OCR)

No PDF support (image-only)

**Future Improvements** 

 Multi-language OCR support

 PDF text extraction

 Cloud sync

 Save extracted history

 Enhanced animations and themes

**Developer**

Roshan Kumar
Android Developer | Jetpack Compose | ML Kit

**ScreenShots**

<img width="389" height="840" alt="Screenshot 2025-12-17 113104" src="https://github.com/user-attachments/assets/a0f9a46a-46ac-48da-8cd0-c60e6a2d7f8a" />

<img width="393" height="848" alt="Screenshot 2025-12-17 113216" src="https://github.com/user-attachments/assets/c6d210e8-fa16-486a-a5e9-fe56d789d1f1" />
<img width="368" height="823" alt="Screenshot 2025-12-17 113138" src="https://github.com/user-attachments/assets/59820f41-395f-4308-8c59-e92d06a4ee6c" />


<img width="379" height="844" alt="Screenshot 2025-12-17 113239" src="https://github.com/user-attachments/assets/df22f83d-e4cb-4447-af2b-480d769a9929" />



https://github.com/user-attachments/assets/aef28abe-ea86-4e35-b98c-accfd68f5a60


