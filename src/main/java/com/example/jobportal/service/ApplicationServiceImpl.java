package com.example.jobportal.service;

import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final JobApplicationRepository repository;

    public ApplicationServiceImpl(JobApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public JobApplication apply(JobApplication application) {
        return repository.save(application);
    }

    @Override
    public List<JobApplication> findByJob(Job job) {
        return repository.findByJob(job);
    }
}
