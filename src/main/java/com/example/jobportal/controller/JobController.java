package com.example.jobportal.controller;

import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    public JobController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/";
        model.addAttribute("job", job);
        model.addAttribute("application", new JobApplication());
        return "jobs/details";
    }

    @PostMapping("/{id}/apply")
    public String apply(@PathVariable Long id, @ModelAttribute JobApplication application, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/";
        application.setJob(job);
        applicationService.apply(application);
        model.addAttribute("success", "Application submitted successfully");
        return "redirect:/jobs/" + id + "?applied";
    }
}
