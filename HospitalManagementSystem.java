
package hospital.managment.syatem;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class HospitalManagementSystem {
    private JFrame frame;
    private JTable patientTable, doctorTable, appointmentTable, billingTable;
    private DefaultTableModel patientTableModel, doctorTableModel, appointmentTableModel, billingTableModel;
    private Database database;

    // Patient fields
    private JTextField patientIdField = new JTextField();
    private JTextField patientNameField = new JTextField();
    private JTextField patientAgeField = new JTextField();
    private JTextField patientDiseaseField = new JTextField();
    private JTextField patientPhoneField = new JTextField();

    // Doctor fields
    private JTextField doctorIdField = new JTextField();
    private JTextField doctorNameField = new JTextField();
    private JTextField doctorSpecialtyField = new JTextField();
    private JTextField doctorPhoneField = new JTextField();

    // Appointment fields
    private JTextField appointmentIdField = new JTextField();
    private JTextField appointmentPatientIdField = new JTextField();
    private JTextField appointmentDoctorIdField = new JTextField();
    private JTextField appointmentDateField = new JTextField();

    // Billing fields
    private JTextField billingIdField = new JTextField();
    private JTextField billingPatientIdField = new JTextField();
    private JTextField billingAmountField = new JTextField();

    public HospitalManagementSystem() {
        database = new Database();
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Initialize tables
        initializeTables();

        // Tabs for different management sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patients", createManagementPanel(patientTable, createPatientForm(), createPatientButtonPanel(), patientTableModel));
        tabbedPane.addTab("Doctors", createManagementPanel(doctorTable, createDoctorForm(), createDoctorButtonPanel(), doctorTableModel));
        tabbedPane.addTab("Appointments", createManagementPanel(appointmentTable, createAppointmentForm(), createAppointmentButtonPanel(), appointmentTableModel));
        tabbedPane.addTab("Billing", createManagementPanel(billingTable, createBillingForm(), createBillingButtonPanel(), billingTableModel));

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);

        loadPatients();
        loadDoctors();
        loadAppointments();
        loadBillings();
    }

    private void initializeTables() {
        // Patient management
        patientTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Disease", "Phone"}, 0);
        patientTable = new JTable(patientTableModel);

        // Doctor management
        doctorTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Specialty", "Phone"}, 0);
        doctorTable = new JTable(doctorTableModel);

        // Appointment management
        appointmentTableModel = new DefaultTableModel(new String[]{"ID", "Patient ID", "Doctor ID", "Date"}, 0);
        appointmentTable = new JTable(appointmentTableModel);

        // Billing management
        billingTableModel = new DefaultTableModel(new String[]{"ID", "Patient ID", "Amount"}, 0);
        billingTable = new JTable(billingTableModel);
    }

    private JPanel createManagementPanel(JTable table, JPanel formPanel, JPanel buttonPanel, DefaultTableModel model) {
        JScrollPane tableScrollPane = new JScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Patient Form and Button Panel
    private JPanel createPatientForm() {
        return createFormPanel(new JTextField[]{patientIdField, patientNameField, patientAgeField, patientDiseaseField, patientPhoneField},
                new String[]{"ID:", "Name:", "Age:", "Disease:", "Phone:"});
    }

    private JPanel createPatientButtonPanel() {
        return createButtonPanel(
                new String[]{"Add Patient", "Update Patient", "Delete Patient", "View Patients"},
                new Runnable[]{this::addPatient, this::updatePatient, this::deletePatient, this::loadPatients});
    }

    // Doctor Form and Button Panel
    private JPanel createDoctorForm() {
        return createFormPanel(new JTextField[]{doctorIdField, doctorNameField, doctorSpecialtyField, doctorPhoneField},
                new String[]{"ID:", "Name:", "Specialty:", "Phone:"});
    }

    private JPanel createDoctorButtonPanel() {
        return createButtonPanel(
                new String[]{"Add Doctor", "Update Doctor", "Delete Doctor", "View Doctors"},
                new Runnable[]{this::addDoctor, this::updateDoctor, this::deleteDoctor, this::loadDoctors});
    }

    // Appointment Form and Button Panel
    private JPanel createAppointmentForm() {
        return createFormPanel(new JTextField[]{appointmentIdField, appointmentPatientIdField, appointmentDoctorIdField, appointmentDateField},
                new String[]{"ID:", "Patient ID:", "Doctor ID:", "Date:"});
    }

    private JPanel createAppointmentButtonPanel() {
        return createButtonPanel(
                new String[]{"Add Appointment", "Update Appointment", "Delete Appointment", "View Appointments"},
                new Runnable[]{this::addAppointment, this::updateAppointment, this::deleteAppointment, this::loadAppointments});
    }

    // Billing Form and Button Panel
    private JPanel createBillingForm() {
        return createFormPanel(new JTextField[]{billingIdField, billingPatientIdField, billingAmountField},
                new String[]{"ID:", "Patient ID:", "Amount:"});
    }

    private JPanel createBillingButtonPanel() {
        return createButtonPanel(
                new String[]{"Add Billing", "Update Billing", "Delete Billing", "View Billings"},
                new Runnable[]{this::addBilling, this::updateBilling, this::deleteBilling, this::loadBillings});
    }

    private JPanel createFormPanel(JTextField[] fields, String[] labels) {
        JPanel formPanel = new JPanel(new GridLayout(labels.length, 2));
        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i]));
            formPanel.add(fields[i]);
        }
        return formPanel;
    }

    private JPanel createButtonPanel(String[] buttonTexts, Runnable[] actions) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, buttonTexts.length, 10, 10));
        for (int i = 0; i < buttonTexts.length; i++) {
            buttonPanel.add(createGreyButton(buttonTexts[i], actions[i]));
        }
        return buttonPanel;
    }

    private JButton createGreyButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(e -> action.run());
        return button;
    }

    // Patient Methods
    private void addPatient() {
        if (validatePatientFields()) {
            database.addPatient(patientIdField.getText(), patientNameField.getText(), patientAgeField.getText(),
                    patientDiseaseField.getText(), patientPhoneField.getText());
            loadPatients();
            clearPatientFields();
        }
    }

    private void updatePatient() {
        if (validatePatientFields()) {
            database.updatePatient(patientIdField.getText(), patientNameField.getText(), patientAgeField.getText(),
                    patientDiseaseField.getText(), patientPhoneField.getText());
            loadPatients();
            clearPatientFields();
        }
    }

    private void deletePatient() {
        String patientId = patientIdField.getText();
        if (!patientId.isEmpty()) {
            database.deletePatient(patientId);
            loadPatients();
            clearPatientFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a patient ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPatients() {
        clearTable(patientTableModel);
        try {
            ResultSet resultSet = database.getPatients();
            while (resultSet.next()) {
                patientTableModel.addRow(new Object[]{
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("age"),
                        resultSet.getString("disease"),
                        resultSet.getString("phone")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validatePatientFields() {
        if (patientNameField.getText().isEmpty() || patientAgeField.getText().isEmpty() ||
                patientDiseaseField.getText().isEmpty() || patientPhoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearPatientFields() {
        patientIdField.setText("");
        patientNameField.setText("");
        patientAgeField.setText("");
        patientDiseaseField.setText("");
        patientPhoneField.setText("");
    }

    // Doctor Methods
    private void addDoctor() {
        if (validateDoctorFields()) {
            database.addDoctor(doctorIdField.getText(), doctorNameField.getText(),
                    doctorSpecialtyField.getText(), doctorPhoneField.getText());
            loadDoctors();
            clearDoctorFields();
        }
    }

    private void updateDoctor() {
        if (validateDoctorFields()) {
            database.updateDoctor(doctorIdField.getText(), doctorNameField.getText(),
                    doctorSpecialtyField.getText(), doctorPhoneField.getText());
            loadDoctors();
            clearDoctorFields();
        }
    }

    private void deleteDoctor() {
        String doctorId = doctorIdField.getText();
        if (!doctorId.isEmpty()) {
            database.deleteDoctor(doctorId);
            loadDoctors();
            clearDoctorFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a doctor ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDoctors() {
        clearTable(doctorTableModel);
        try {
            ResultSet resultSet = database.getDoctors();
            while (resultSet.next()) {
                doctorTableModel.addRow(new Object[]{
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialty"),
                        resultSet.getString("phone")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateDoctorFields() {
        if (doctorNameField.getText().isEmpty() || doctorSpecialtyField.getText().isEmpty() ||
                doctorPhoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearDoctorFields() {
        doctorIdField.setText("");
        doctorNameField.setText("");
        doctorSpecialtyField.setText("");
        doctorPhoneField.setText("");
    }

    // Appointment Methods
    private void addAppointment() {
        if (validateAppointmentFields()) {
            database.addAppointment(appointmentIdField.getText(), appointmentPatientIdField.getText(),
                    appointmentDoctorIdField.getText(), appointmentDateField.getText());
            loadAppointments();
            clearAppointmentFields();
        }
    }

    private void updateAppointment() {
        if (validateAppointmentFields()) {
            database.updateAppointment(appointmentIdField.getText(), appointmentPatientIdField.getText(),
                    appointmentDoctorIdField.getText(), appointmentDateField.getText());
            loadAppointments();
            clearAppointmentFields();
        }
    }

    private void deleteAppointment() {
        String appointmentId = appointmentIdField.getText();
        if (!appointmentId.isEmpty()) {
            database.deleteAppointment(appointmentId);
            loadAppointments();
            clearAppointmentFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter an appointment ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAppointments() {
        clearTable(appointmentTableModel);
        try {
            ResultSet resultSet = database.getAppointments();
            while (resultSet.next()) {
                appointmentTableModel.addRow(new Object[]{
                        resultSet.getString("id"),
                        resultSet.getString("patient_id"),
                        resultSet.getString("doctor_id"),
                        resultSet.getString("date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateAppointmentFields() {
        if (appointmentPatientIdField.getText().isEmpty() || appointmentDoctorIdField.getText().isEmpty() ||
                appointmentDateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearAppointmentFields() {
        appointmentIdField.setText("");
        appointmentPatientIdField.setText("");
        appointmentDoctorIdField.setText("");
        appointmentDateField.setText("");
    }

    // Billing Methods
    private void addBilling() {
        if (validateBillingFields()) {
            database.addBilling(billingIdField.getText(), billingPatientIdField.getText(),
                    billingAmountField.getText());
            loadBillings();
            clearBillingFields();
        }
    }

    private void updateBilling() {
        if (validateBillingFields()) {
            database.updateBilling(billingIdField.getText(), billingPatientIdField.getText(),
                    billingAmountField.getText());
            loadBillings();
            clearBillingFields();
        }
    }

    private void deleteBilling() {
        String billingId = billingIdField.getText();
        if (!billingId.isEmpty()) {
            database.deleteBilling(billingId);
            loadBillings();
            clearBillingFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a billing ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBillings() {
        clearTable(billingTableModel);
        try {
            ResultSet resultSet = database.getBillings();
            while (resultSet.next()) {
                billingTableModel.addRow(new Object[]{
                        resultSet.getString("id"),
                        resultSet.getString("patient_id"),
                        resultSet.getString("amount")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateBillingFields() {
        if (billingPatientIdField.getText().isEmpty() || billingAmountField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearBillingFields() {
        billingIdField.setText("");
        billingPatientIdField.setText("");
        billingAmountField.setText("");
    }

    private void clearTable(DefaultTableModel model) {
        model.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalManagementSystem::new);
    }
}






