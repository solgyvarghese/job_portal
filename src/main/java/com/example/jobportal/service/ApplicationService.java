package com.example.jobportal.service;

import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.JobApplication;

import java.util.List;

public interface ApplicationService {
    JobApplication apply(JobApplication application);
    List<JobApplication> findByJob(Job job);
}
