# **🛒 Android Product Management App**
Built using **Kotlin** and **Jetpack Compose**  
Developed in **2 days**  
Assignment provided by **Swipe@YC**

##  How to Run This Project

1. **Clone the repository**
https://github.com/revanthkumarJ/swipe-assignment

2. **Open the project in Android Studio**

3. **Sync the Gradle files**  
Android Studio will automatically prompt you to sync—click *Sync Now*.

4. **Run the project**  
- Use an Android emulator  
- **OR** connect a real device with USB debugging enabled  
- Click the **Run ▶️** button at the top

That's it! The app will install and launch automatically.

##  What is this Project?

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

## 📱 App Screenshots

### 🔹 Onboarding Flow and Notifications Pop up

| Onboarding 1 | Onboarding 2 | Notfication popup | Notfications |
|----------|-----------|-----------|-----------|
| <img width="200"  alt="image" src="https://github.com/user-attachments/assets/f572b8da-0ce9-40ed-b4f0-f3e96e616521" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/36aeb748-937b-40e6-a64e-18ebd487bac7" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/c4cb6565-5725-4acf-963c-f3c34ada253c" /> |  <img width="200"  alt="image" src="https://github.com/user-attachments/assets/585b6911-324f-4650-9564-9bbf5e896266" /> |

### 🔹 Products List, Product Detail & Search

| Product List | Product Details | Search Product |
|----------|-----------|-----------|
| <img width="200"  alt="image" src="https://github.com/user-attachments/assets/5406dfc4-c583-42af-8e0a-780ed580fe04" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/b56b8c7c-6e00-4f08-8049-ef2c081dc319" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/2fd41ded-ed6a-4f9c-a0b7-9424483ce6ba" /> |

### 🔹 Sync States

| Network Unavailable | Syncing | No Items to Sync |
|----------------------|---------|-------------------|
| <img width="200"  alt="image" src="https://github.com/user-attachments/assets/8c859140-ab71-443f-a842-58345e2ab62e" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/9804d400-a5e2-43c6-8ca3-a8a60441573e" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/72585b98-f641-4ece-ab4a-27376fd87c8c" /> |


### 🔹 Add New Product & Upload States

| Screen 1 | Screen 2 | Screen 3 | Screen 4 |
|----------|-----------|-----------|-----------|
| <img width="200"  alt="image" src="https://github.com/user-attachments/assets/4450db30-6d05-418d-bd4b-9c8fcb4f6a92" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/910af596-cdaf-498e-98b2-e09cd136087d" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/e7a52432-62c5-4aec-871d-50513ed0a35a" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/6d825ef8-07eb-4c69-9617-3cdfe9817f36" /> |


### 🔹 Settings Flow

| Settings Screen | Theme Screen | Help & Support | FAQ | About Us | App Info |
|----------|-----------|-----------|-----------|-----------|-----------|
| <img width="200"  alt="image" src="https://github.com/user-attachments/assets/2f7f8606-6f8a-4758-90fa-53e095162d1b" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/4a7b195d-00c1-4afc-aa16-4cf5aac559f5" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/fa1742a5-842b-45a5-a196-1e43b61f9906" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/6cb305c3-a5b6-404f-83a2-443da61c30ae" /> | <img width="200"  alt="image" src="https://github.com/user-attachments/assets/afce8277-372e-4df1-8988-ee6d2d2f433f" /> |<img width="200"  alt="image" src="https://github.com/user-attachments/assets/f420e6cc-095a-4bb5-8382-3c96ccfd840c" /> |



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

### 🔹 Developer-Friendly Previews

The project includes **Jetpack Compose Previews** for all major UI components and screens.  
These previews make it easy for new developers to:

- Quickly understand the UI layout  
- Modify or update screens without running the app  
- Visualize light/dark themes
  
<img width="600"  alt="image" src="https://github.com/user-attachments/assets/9f39d849-f430-4667-8da5-c4668943d4bf" />


## 🏁 Conclusion

This project was developed as part of an assignment provided by **Swipe@YC**, showcasing skills in modern Android development, clean architecture, state management, offline support, and UI/UX design.

The entire application — including product listing, product creation, offline sync, onboarding, settings, and detailed navigation architecture — was designed and built within **2 days**, demonstrating rapid development capability and strong understanding of Android best practices.

Thank you for reviewing this project!
