
package hospital.managment.syatem;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String disease;
    private String phone;

    // Constructor
    public Patient(String patientId, String name, int age, String disease, String phone) {
        this.patientId = patientId;
        this.name = name;
        setAge(age); // Using setter for validation
        this.disease = disease;
        setPhone(phone); // Using setter for validation
    }

    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        this.age = age;
    }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public String getPhone() { return phone; }
    
    public void setPhone(String phone) {
        if (!phone.matches("\\d{10}")) { // Example validation for a 10-digit phone number
            throw new IllegalArgumentException("Phone number must be 10 digits.");
        }
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", disease='" + disease + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

