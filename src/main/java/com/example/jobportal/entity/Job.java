package com.example.jobportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;
    private String employmentType; // e.g., Full-time, Part-time
    @Column(length = 4000)
    private String description;
    private boolean open = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Job() {}

    // getters & setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }
    public String getLocation(){ return location; }
    public void setLocation(String location){ this.location = location; }
    public String getEmploymentType(){ return employmentType; }
    public void setEmploymentType(String employmentType){ this.employmentType = employmentType; }
    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
    public boolean isOpen(){ return open; }
    public void setOpen(boolean open){ this.open = open; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }
}
