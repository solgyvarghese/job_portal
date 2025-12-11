package com.example.jobportal.service;

import com.example.jobportal.entity.Job;
import com.example.jobportal.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Page<Job> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return jobRepository.findAll(pageable);
        }
        String term = q;
        return jobRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCaseOrEmploymentTypeContainingIgnoreCase(term, term, term, pageable);
    }

    @Override
    public Optional<Job> findById(Long id){
        return jobRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        jobRepository.deleteById(id);
    }
}
