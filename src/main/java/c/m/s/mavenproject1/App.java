package c.m.s.mavenproject1;

import clinic.main.ClinicApp;
import clinic.model.Doctor;
import clinic.model.Patient;
import clinic.model.Appointment;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import clinic.model.*;

public class App extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        ClinicApp.loadAllData(); // WAKE UP THE DATA
        
        
        
        showLoginScreen();
    }

    // --- 1. LOGIN SCREEN ---
    private void showLoginScreen() {
        Label title = new Label("🏥 Clinic Management System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField userField = new TextField(); userField.setPromptText("Username");
        PasswordField passField = new PasswordField(); passField.setPromptText("Password");
        Button loginBtn = new Button("Login");
        Label status = new Label("");

        loginBtn.setOnAction(e -> {
            String u = userField.getText();
            String p = passField.getText();

            // 1. ADMIN LOGIN (Hardcoded for simplicity)
if (u.equals("SuperAdmin") && p.equals("admin123")) {
    showAdminDashboard();
    return;
}

            // PATIENT LOGIN
            for (Patient pat : ClinicApp.patients) {
                if (pat.getName().equalsIgnoreCase(u) && pat.getPassword().equals(p)) {
                    showPatientDashboard(pat);
                    return;
                }
            }
            for (Doctor doc : ClinicApp.doctors) {
                if (doc.getName().equalsIgnoreCase(u) && doc.getPassword().equals(p)) {
                    showDoctorDashboard(doc); // <--- We will write this next
                    return;
                }
            }
            
            status.setText("Invalid Credentials!");
            status.setTextFill(Color.RED);
        });

        VBox layout = new VBox(15, title, userField, passField, loginBtn, status);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        primaryStage.setScene(new Scene(layout, 400, 350));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // --- 2. ADMIN DASHBOARD (WITH TABS & FIXES) ---
    private void showAdminDashboard() {
        TabPane tabPane = new TabPane();

        // === TAB 1: DOCTORS ===
        Tab docTab = new Tab("Manage Doctors");
        docTab.setClosable(false);
        BorderPane docLayout = new BorderPane();

        // Doctor Table (Modern Java Fix)
        TableView<Doctor> docTable = new TableView<>();
        
        TableColumn<Doctor, Number> dIdCol = new TableColumn<>("ID");
        dIdCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()));
        
        TableColumn<Doctor, String> dNameCol = new TableColumn<>("Name");
        dNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        
        TableColumn<Doctor, String> dSpecCol = new TableColumn<>("Specialty");
        dSpecCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSpecialty()));

        docTable.getColumns().addAll(dIdCol, dNameCol, dSpecCol);
        docTable.setItems(FXCollections.observableArrayList(ClinicApp.doctors));

        // Doctor Inputs
        TextField dNameIn = new TextField(); dNameIn.setPromptText("Name");
        TextField dSpecIn = new TextField(); dSpecIn.setPromptText("Specialty");
        Button dAddBtn = new Button("Add Doctor");
        
        dAddBtn.setOnAction(e -> {
            if (!dNameIn.getText().isEmpty() && !dSpecIn.getText().isEmpty()) {
                ClinicApp.doctors.add(new Doctor(ClinicApp.doctors.size() + 1, dNameIn.getText(), "pass123", dSpecIn.getText()));
                ClinicApp.saveAllData();
                docTable.setItems(FXCollections.observableArrayList(ClinicApp.doctors)); // Refresh
                dNameIn.clear(); dSpecIn.clear();
            }
        });

        HBox docControls = new HBox(10, dNameIn, dSpecIn, dAddBtn);
        docControls.setPadding(new Insets(10));
        docLayout.setCenter(docTable);
        docLayout.setBottom(docControls);
        docTab.setContent(docLayout);

        // === TAB 2: PATIENTS (The Missing Link!) ===
        Tab patTab = new Tab("Manage Patients");
        patTab.setClosable(false);
        BorderPane patLayout = new BorderPane();

        // Patient Table
        TableView<Patient> patTable = new TableView<>();
        
        TableColumn<Patient, Number> pIdCol = new TableColumn<>("ID");
        pIdCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()));
        
        TableColumn<Patient, String> pNameCol = new TableColumn<>("Name");
        pNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

        patTable.getColumns().addAll(pIdCol, pNameCol);
        patTable.setItems(FXCollections.observableArrayList(ClinicApp.patients));

        // Patient Inputs
        TextField pNameIn = new TextField(); pNameIn.setPromptText("Patient Name");
        Button pAddBtn = new Button("Add Patient");

        pAddBtn.setOnAction(e -> {
            if (!pNameIn.getText().isEmpty()) {
                // Default password is 'pass123'
                ClinicApp.patients.add(new Patient(ClinicApp.patients.size() + 1, pNameIn.getText(), "pass123"));
                ClinicApp.saveAllData();
                patTable.setItems(FXCollections.observableArrayList(ClinicApp.patients)); // Refresh
                pNameIn.clear();
            }
        });

        HBox patControls = new HBox(10, pNameIn, pAddBtn);
        patControls.setPadding(new Insets(10));
        patLayout.setCenter(patTable);
        patLayout.setBottom(patControls);
        patTab.setContent(patLayout);

        // === ASSEMBLE ===
        tabPane.getTabs().addAll(docTab, patTab);

        BorderPane mainRoot = new BorderPane();
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showLoginScreen());
        
        HBox header = new HBox(10, new Label("Admin Dashboard"), logoutBtn);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_RIGHT);

        mainRoot.setTop(header);
        mainRoot.setCenter(tabPane);

        primaryStage.setScene(new Scene(mainRoot, 600, 450));
        primaryStage.setTitle("Admin Dashboard");
    }

    // --- 3. PATIENT DASHBOARD (FINAL VERSION) ---
    private void showPatientDashboard(Patient p) {
        TabPane tabPane = new TabPane();

        // === TAB 1: BOOK APPOINTMENT (Existing Logic) ===
        Tab bookTab = new Tab("Book Appointment");
        bookTab.setClosable(false);
        VBox bookLayout = new VBox(15);
        bookLayout.setPadding(new Insets(20));
        bookLayout.setAlignment(Pos.CENTER);

        Label welcome = new Label("Welcome, " + p.getName());
        ComboBox<Doctor> docBox = new ComboBox<>();
        docBox.setItems(FXCollections.observableArrayList(ClinicApp.doctors)); // Load Doctors
        TextField dateField = new TextField(); dateField.setPromptText("Date (e.g. Tomorrow)");
        Button bookBtn = new Button("Book Appointment");
        Label bookStatus = new Label("");

        bookBtn.setOnAction(e -> {
            if (docBox.getValue() != null && !dateField.getText().isEmpty()) {
                ClinicApp.appointments.add(new Appointment(ClinicApp.appointments.size()+1, docBox.getValue().getId(), p.getId(), dateField.getText()));
                ClinicApp.saveAllData();
                bookStatus.setText("✅ Booked Successfully!");
                bookStatus.setTextFill(Color.GREEN);
            }
        });

        bookLayout.getChildren().addAll(welcome, new Label("Select Doctor:"), docBox, dateField, bookBtn, bookStatus);
        bookTab.setContent(bookLayout);

        // === TAB 2: MEDICAL RECORDS (New!) ===
        Tab recTab = new Tab("My History");
        recTab.setClosable(false);
        VBox recLayout = new VBox(10);
        recLayout.setPadding(new Insets(10));

        // Table 1: Diagnoses
        Label diagLbl = new Label("🩺 Medical Records");
        diagLbl.setStyle("-fx-font-weight: bold");
        TableView<MedicalRecord> recTable = new TableView<>();
        
        TableColumn<MedicalRecord, String> diagCol = new TableColumn<>("Diagnosis");
        diagCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDiagnosis()));
        
        TableColumn<MedicalRecord, String> treatCol = new TableColumn<>("Treatment");
        treatCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTreatment()));
        
        recTable.getColumns().addAll(diagCol, treatCol);
        
        // Filter Records for this Patient
        ObservableList<MedicalRecord> myRecs = FXCollections.observableArrayList();
        for(MedicalRecord r : ClinicApp.records) {
            if(r.getPatientId() == p.getId()) myRecs.add(r);
        }
        recTable.setItems(myRecs);

        // Table 2: Prescriptions
        Label medLbl = new Label("💊 Prescriptions");
        medLbl.setStyle("-fx-font-weight: bold");
        TableView<Prescription> medTable = new TableView<>();
        
        TableColumn<Prescription, String> drugCol = new TableColumn<>("Medication");
        drugCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMedication()));
        
        TableColumn<Prescription, String> doseCol = new TableColumn<>("Dosage");
        doseCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDosage()));
        
        medTable.getColumns().addAll(drugCol, doseCol);

        // Filter Prescriptions for this Patient
        ObservableList<Prescription> myMeds = FXCollections.observableArrayList();
        for(Prescription pr : ClinicApp.prescriptions) {
            if(pr.getPatientId() == p.getId()) myMeds.add(pr);
        }
        medTable.setItems(myMeds);
        
        recLayout.getChildren().addAll(diagLbl, recTable, medLbl, medTable);
        recTab.setContent(recLayout);

        // === TAB 3: BILLING (New!) ===
        Tab billTab = new Tab("My Bills");
        billTab.setClosable(false);
        VBox billLayout = new VBox(10);
        billLayout.setPadding(new Insets(10));

        TableView<Billing> billTable = new TableView<>();
        
        TableColumn<Billing, Number> amtCol = new TableColumn<>("Amount ($)");
        amtCol.setCellValueFactory(cell -> new SimpleIntegerProperty((int)cell.getValue().getAmount()));
        
        TableColumn<Billing, String> statCol = new TableColumn<>("Status");
        statCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));
        
        billTable.getColumns().addAll(amtCol, statCol);

        // Filter Bills
        ObservableList<Billing> myBills = FXCollections.observableArrayList();
        for(Billing b : ClinicApp.bills) {
            if(b.getPatientId() == p.getId()) myBills.add(b);
        }
        billTable.setItems(myBills);

        Button payBtn = new Button("💳 Pay Selected Bill");
        Label payStatus = new Label("");
        
        payBtn.setOnAction(e -> {
            Billing selected = billTable.getSelectionModel().getSelectedItem();
            if(selected != null) {
                selected.setStatus("Paid"); // Update Object
                ClinicApp.saveAllData();    // Save to File
                billTable.refresh();        // Update Table Visuals
                payStatus.setText("Payment Successful! Thank you.");
                payStatus.setTextFill(Color.GREEN);
            }
        });

        billLayout.getChildren().addAll(new Label("Outstanding Invoices"), billTable, payBtn, payStatus);
        billTab.setContent(billLayout);

        // === ASSEMBLE ===
        tabPane.getTabs().addAll(bookTab, recTab, billTab);

        BorderPane mainRoot = new BorderPane();
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showLoginScreen());
        
        HBox header = new HBox(10, new Label("Patient Dashboard"), logoutBtn);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_RIGHT);

        mainRoot.setTop(header);
        mainRoot.setCenter(tabPane);

        primaryStage.setScene(new Scene(mainRoot, 600, 500));
        primaryStage.setTitle("Patient Dashboard");
    }

    public static void main(String[] args) {
        launch();
    }

    // --- 4. DOCTOR DASHBOARD (UPDATED) ---
    private void showDoctorDashboard(Doctor d) {
        BorderPane root = new BorderPane();

        // HEADER
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        Label title = new Label("👨‍⚕️ Dr. " + d.getName());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> showLoginScreen());
        header.getChildren().addAll(title, logoutBtn);
        root.setTop(header);

        // TABLE
        TableView<Appointment> table = new TableView<>();
        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDate()));
        TableColumn<Appointment, Number> patIdCol = new TableColumn<>("Patient ID");
        patIdCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPatientId()));
        
        table.getColumns().addAll(dateCol, patIdCol);

        // Filter Appointments for this Doctor
        ObservableList<Appointment> myAppts = FXCollections.observableArrayList();
        for (Appointment a : ClinicApp.appointments) {
            if (a.getDoctorId() == d.getId()) myAppts.add(a);
        }
        table.setItems(myAppts);
        root.setCenter(table);

        // --- THE NEW "BOTTOM HALF" FEATURES ---
        VBox consultBox = new VBox(10);
        consultBox.setPadding(new Insets(15));
        consultBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd;");
        
        Label actionLabel = new Label("Select an appointment above to perform actions:");
        
        // INPUTS
        TextField diagIn = new TextField(); diagIn.setPromptText("Diagnosis (e.g. Flu)");
        TextField treatIn = new TextField(); treatIn.setPromptText("Treatment (e.g. Rest)");
        TextField medIn = new TextField(); medIn.setPromptText("Prescription Meds");
        TextField costIn = new TextField(); costIn.setPromptText("Bill Amount ($)");
        
        Button submitBtn = new Button("💾 Save Consultation Data");

        submitBtn.setOnAction(e -> {
            Appointment selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int pId = selected.getPatientId();
                
                // 1. Create Medical Record
                if(!diagIn.getText().isEmpty()) {
                    ClinicApp.records.add(new MedicalRecord(ClinicApp.records.size()+1, pId, d.getId(), diagIn.getText(), treatIn.getText()));
                }
                
                // 2. Create Prescription
                if(!medIn.getText().isEmpty()) {
                    ClinicApp.prescriptions.add(new Prescription(ClinicApp.prescriptions.size()+1, pId, medIn.getText(), "1 Daily", "After meals"));
                }
                
                // 3. Create Bill
                if(!costIn.getText().isEmpty()) {
                    double cost = Double.parseDouble(costIn.getText());
                    ClinicApp.bills.add(new Billing(ClinicApp.bills.size()+1, pId, cost, "Unpaid"));
                }
                
                ClinicApp.saveAllData();
                diagIn.clear(); treatIn.clear(); medIn.clear(); costIn.clear();
                actionLabel.setText("✅ Data Saved for Patient ID: " + pId);
                actionLabel.setTextFill(Color.GREEN);
            } else {
                actionLabel.setText("⚠️ Please select an appointment first!");
            }
        });

        consultBox.getChildren().addAll(actionLabel, diagIn, treatIn, medIn, costIn, submitBtn);
        root.setBottom(consultBox);

        Scene scene = new Scene(root, 600, 550);
        primaryStage.setTitle("Doctor Portal");
        primaryStage.setScene(scene);
    }
}