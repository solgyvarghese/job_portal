package com.example.jobportal.service;

import com.example.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobService {
    Job save(Job job);
    Page<Job> search(String q, Pageable pageable);
    Optional<Job> findById(Long id);
    void deleteById(Long id);
}

