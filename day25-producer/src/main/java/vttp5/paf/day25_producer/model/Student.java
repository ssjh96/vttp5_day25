package vttp5.paf.day25_producer.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class Student 
{
    private int id;
    
    private String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy") // Ensures a consistent date format
    private Date dob;

    public Student() {
    }

    public Student(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    
}
