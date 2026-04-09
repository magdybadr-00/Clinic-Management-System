# 🏥 Clinic Management System (JavaFX)

A comprehensive healthcare management solution built with **Java 17** and **JavaFX**. This system is designed to digitalize clinic workflows, providing secure, role-based access for Administrators, Doctors, and Patients.

-----

## 🚀 Key Features

  * **Role-Based Dashboards:** \* **Admins:** Manage doctor schedules and patient registration.
      * **Doctors:** View appointments and record diagnoses, treatments, and prescriptions.
      * **Patients:** Access their personal medical history and billing information.
  * **Appointment Management:** Real-time scheduling that links patients to specific doctors.
  * **Medical & Pharmacy Records:** Integrated system for attaching prescriptions and medical notes to patient profiles.
  * **Billing System:** Automated generation of invoices for patient visits with payment status tracking.
  * **Persistent Storage:** Custom file-handling system that saves all data into encrypted local `.dat` files.

-----

## 🧬 Object-Oriented Programming (OOP) Application

This project was built as a demonstration of clean, modular software design using core OOP principles:

  * **Inheritance & Abstraction:** Utilizes an abstract `User` class to define core security and identity attributes. Specific logic for `Admin`, `Doctor`, and `Patient` is then inherited and expanded, ensuring a "DRY" (Don't Repeat Yourself) codebase.
  * **Encapsulation:** All data models (e.g., `Appointment`, `Billing`, `Prescription`) use private fields and public getters/setters. This protects the integrity of medical data and prevents illegal state changes.
  * **Polymorphism:** Method overriding (such as custom `toString()` implementations) allows for dynamic rendering of objects within the JavaFX UI components.
  * **Data Persistence:** Uses Java **Object Serialization**. The `FileManager` utility class provides a generic way to save and load complex object graphs to the local disk.

-----

## 🛠️ Technical Stack

  * **Language:** Java 17
  * **GUI:** JavaFX (OpenJFX)
  * **Build Tool:** Maven
  * **Persistence:** Java Object Serialization

-----

## ⚙️ Installation & Running

1.  **Prerequisites:**

      * Ensure **JDK 17** and **Maven** are installed on your system.
      * Add Maven's `bin` folder to your System Environment Variables (PATH).

2.  **Clone the Project:**

    ```bash
    git clone https://github.com/magdybadr-00/Clinic-Management-System-.git
    ```

3.  **Run via Maven:**
    Navigate to the project root and run:

    ```bash
    mvn clean javafx:run
    ```

-----

## 📁 Project Structure

  * `src/main/java/clinic/model`: Data entities and logic.
  * `src/main/java/clinic/utils`: File management and serialization tools.
  * `src/main/java/c/m/s/mavenproject1`: JavaFX controllers and UI layout.
  * `pom.xml`: Project dependencies and JavaFX plugin configuration.

-----

### 📄 License

Distributed under the **MIT License**. See `LICENSE` for more information.

-----

### 📧 Contact

**Magdy Badr** - [magdybadr-00](https://www.google.com/search?q=https://github.com/magdybadr-00)
