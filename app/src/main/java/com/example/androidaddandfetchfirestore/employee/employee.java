package com.example.androidaddandfetchfirestore.employee;

public class employee {
    private String name;
    private  String department;
    private String email;

    public employee(String name, String department, String email) {
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public employee() {}

    // Getter and Setter for the employee activity
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

}
