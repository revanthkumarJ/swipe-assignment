# 🛒 Android Product Management App

A modern Android application built as part of an assignment to demonstrate **product listing**, **product creation**, **offline support**, and **clean architecture** using the latest Android development practices.

## 📌 What is this Project?

This project is an Android application that includes:

- **Onboarding Screens** → Shown only to first-time users to guide them through the app features.
- **Product Listing Screen** → Fetches products from a public API, displays them with images, search functionality, and loading indicators.  
- **Add Product Screen (BottomSheet)** → Allows adding new products with validations, image selection, and API submission using POST.  
- **Offline Functionality** → Products created offline are saved locally using Room and automatically synced once an internet connection is available.  
- **Settings Screen** → Includes theme switching, About Us, App Info, FAQ, and Help & Support sections for improved user experience.

It is built using **Jetpack Compose**, **Koin**, **Retrofit**, **Room**, **Coroutines**, and follows **MVVM Architecture**.

## 🛠 Technologies Used

- **Kotlin** – Primary programming language  
- **Jetpack Compose** – Modern declarative UI toolkit  
- **Koin** – Dependency Injection framework  
- **Room** – Local database for offline storage  
- **Retrofit** – Networking and API communication  
- **Coil** – Image loading library  
- Additional Android & Jetpack libraries as required


## 🏛 App Architecture

This project follows the **MVVM architecture pattern** combined with a clean and organized package structure.  
Since the app is simple, multi-module architecture was intentionally not used to avoid unnecessary complexity.  
Instead, a structured and scalable **three-layer package organization** is followed:

---

### 📁 Project Structure

core/
navigation/
feature/


---

### 🧩 **core**  
Contains all the shared and reusable logic across the app.

Inside `core`, the following sub-packages exist:

- **common/** → Common utilities such as extensions, helpers, constants, and reusable logic  
- **data/** → Repositories and their implementations (Repo + RepoImpl)  
- **database/** → Room Database, DAO, and entity definitions  
- **models/** → All data classes used across the app  
- **network/** → Retrofit setup, API services, interceptors  
- **ui/** → Commonly used UI components, composables, and theme elements  

---

### 🧩 **feature**  
Contains all feature-based modules (grouped by screen).

- **home/** → Product listing and related UI/logic  
- **onboarding/** → First-time user onboarding screens  
- **settings/** → Theme switching, About Us, App Info, FAQ, Help & Support screens  

---

### 🧩 **navigation**  
Handles navigation and dependency injection bindings.

- **koin modules** → All DI modules are declared here  
- **RootNav** → Contains the NavHost and list of all composable destinations  
- **RootNavViewModel** → ViewModel controlling app-level navigation state  

---

This architecture keeps the project **simple, scalable, and clean**, avoiding the complexity of a multi-module setup while still following clean architecture principles.


## 🧭 Screen, ViewModel & Navigation Management

The app follows a clean and modular approach to managing UI screens, ViewModels, and navigation. This structure ensures scalability, separation of concerns, and predictable state management.

### 🔹 ViewModel Management
Each screen in the application is backed by its own ViewModel.  
The ViewModels are responsible for:

- Managing UI state through `StateFlow`
- Emitting one-time events using `SharedFlow`
- Handling user actions through a defined action structure
- Communicating with repositories for data operations
- Providing only immutable state to the UI

All ViewModels are injected using **Koin**, enabling easy dependency management without manual instantiation.

### 🔹 Navigation Structure
Navigation is implemented using a **route-based** and **type-safe** system where each screen is represented by a distinct route object.  
The navigation graph:

- Defines all destinations in a structured and centralized manner  
- Cleanly maps routes to their respective Composable screens  
- Supports navigation between feature-based modules  
- Allows passing callbacks or navigation lambdas wherever required  

This approach eliminates hard-coded string routes and makes the navigation system easy to maintain as the app grows.

### 🔹 Screen Architecture
Screens are purely responsible for rendering the UI.  
They:

- Observe ViewModel state using lifecycle-aware state collectors  
- Listen for one-time events such as navigation triggers or dialogs  
- Update the UI based on the current state  
- Forward user interactions to the ViewModel through action handlers  

This ensures the UI remains reactive and always reflects the latest data.

### 🔹 Unidirectional Data Flow
The app uses a consistent unidirectional data flow pattern:

- **Actions** → From UI to ViewModel  
- **State** → From ViewModel to UI  
- **Events** → One-time effects like navigation or showing messages  

This pattern provides:

- Predictable UI behavior  
- Clear flow of data and logic  
- Ease of testing and debugging  

---

Overall, this layered approach ensures a clean separation between UI, business logic, and navigation, making the project robust, maintainable, and scalable.

