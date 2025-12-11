package com.example.jobportal.controller;

import com.example.jobportal.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.entity.Job;

@Controller
public class HomeController {

    private final JobService jobService;
    public HomeController(JobService jobService) { this.jobService = jobService; }

    @GetMapping({"/", "/index"})
    public String index(@RequestParam(value="q", required=false) String q,
                        @RequestParam(value="page", defaultValue="0") int page,
                        Model model){
        Page<Job> jobs = jobService.search(q, PageRequest.of(page, 6));
        model.addAttribute("jobs", jobs);
        model.addAttribute("q", q);
        return "index";
    }
}
