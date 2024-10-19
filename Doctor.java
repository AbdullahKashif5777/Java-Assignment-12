package hospital.managment.syatem;

public class Doctor {
    private String doctorId;
    private String name;
    private String qualification;
    private String designation;
    private double salary;
    private String department;

    // Constructor
    public Doctor(String doctorId, String name, String qualification, String designation, double salary, String department) {
        this.doctorId = doctorId;
        this.name = name;
        this.qualification = qualification;
        this.designation = designation;
        setSalary(salary); // Using setter for validation
        this.department = department;
    }

    // Getters and Setters
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getSalary() { return salary; }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}


