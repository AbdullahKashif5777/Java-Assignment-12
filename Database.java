/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.managment.syatem;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_management"; // Change to your DB URL
    private static final String USER = "your_username"; // Change to your DB username
    private static final String PASS = "your_password"; // Change to your DB password

    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.err.println("Connection to database failed: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }

    // Patient Methods
    public void addPatient(String id, String name, String age, String disease, String phone) {
        String query = "INSERT INTO patients (id, name, age, disease, phone) VALUES (?, ?, ?, ?, ?)";
        executeUpdate(query, id, name, age, disease, phone);
    }

    public void updatePatient(String id, String name, String age, String disease, String phone) {
        String query = "UPDATE patients SET name = ?, age = ?, disease = ?, phone = ? WHERE id = ?";
        executeUpdate(query, name, age, disease, phone, id);
    }

    public void deletePatient(String id) {
        String query = "DELETE FROM patients WHERE id = ?";
        executeUpdate(query, id);
    }

    public ResultSet getPatients() {
        return executeQuery("SELECT * FROM patients");
    }

    // Doctor Methods
    public void addDoctor(String id, String name, String specialty, String phone) {
        String query = "INSERT INTO doctors (id, name, specialty, phone) VALUES (?, ?, ?, ?)";
        executeUpdate(query, id, name, specialty, phone);
    }

    public void updateDoctor(String id, String name, String specialty, String phone) {
        String query = "UPDATE doctors SET name = ?, specialty = ?, phone = ? WHERE id = ?";
        executeUpdate(query, name, specialty, phone, id);
    }

    public void deleteDoctor(String id) {
        String query = "DELETE FROM doctors WHERE id = ?";
        executeUpdate(query, id);
    }

    public ResultSet getDoctors() {
        return executeQuery("SELECT * FROM doctors");
    }

    // Appointment Methods
    public void addAppointment(String id, String patientId, String doctorId, String dateTime) {
        String query = "INSERT INTO appointments (id, patient_id, doctor_id, appointment_time) VALUES (?, ?, ?, ?)";
        executeUpdate(query, id, patientId, doctorId, dateTime);
    }

    public void updateAppointment(String id, String patientId, String doctorId, String dateTime) {
        String query = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_time = ? WHERE id = ?";
        executeUpdate(query, patientId, doctorId, dateTime, id);
    }

    public void deleteAppointment(String id) {
        String query = "DELETE FROM appointments WHERE id = ?";
        executeUpdate(query, id);
    }

    public ResultSet getAppointments() {
        return executeQuery("SELECT * FROM appointments");
    }

    // Billing Methods
    public void addBilling(String id, String patientId, double amount, String paymentMethod) {
        String query = "INSERT INTO billing (id, patient_id, amount, payment_method) VALUES (?, ?, ?, ?)";
        executeUpdate(query, id, patientId, amount, paymentMethod);
    }

    public void updateBilling(String id, String patientId, double amount, String paymentMethod) {
        String query = "UPDATE billing SET patient_id = ?, amount = ?, payment_method = ? WHERE id = ?";
        executeUpdate(query, patientId, amount, paymentMethod, id);
    }

    public void deleteBilling(String id) {
        String query = "DELETE FROM billing WHERE id = ?";
        executeUpdate(query, id);
    }

    public ResultSet getBillings() {
        return executeQuery("SELECT * FROM billing");
    }

    // Helper methods
    private void executeUpdate(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            System.out.println("Query executed: " + query);
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        }
    }

    private ResultSet executeQuery(String query) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
            return null;
        }
    }
}



