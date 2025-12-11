package com.example.jobportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;
    private String applicantEmail;
    private String resumeText; // simple text field for this example
    private LocalDateTime appliedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public JobApplication(){}

    // getters & setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getApplicantName(){ return applicantName; }
    public void setApplicantName(String applicantName){ this.applicantName = applicantName; }
    public String getApplicantEmail(){ return applicantEmail; }
    public void setApplicantEmail(String applicantEmail){ this.applicantEmail = applicantEmail; }
    public String getResumeText(){ return resumeText; }
    public void setResumeText(String resumeText){ this.resumeText = resumeText; }
    public LocalDateTime getAppliedAt(){ return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt){ this.appliedAt = appliedAt; }
    public Job getJob(){ return job; }
    public void setJob(Job job){ this.job = job; }
}
