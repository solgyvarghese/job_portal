package com.example.jobportal.controller;

import com.example.jobportal.entity.Job;
import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.JobService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    public AdminController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value="page", defaultValue="0") int page, Model model) {
        Page<Job> jobs = jobService.search(null, PageRequest.of(page, 8));
        model.addAttribute("jobs", jobs);
        return "admin/dashboard";
    }

    @GetMapping("/jobs/new")
    public String newJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "admin/job-form";
    }

    @PostMapping("/jobs/save")
    public String saveJob(@ModelAttribute Job job) {
        jobService.save(job);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/jobs/edit/{id}")
    public String editJob(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/admin/dashboard";
        model.addAttribute("job", job);
        return "admin/job-form";
    }

    @GetMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/jobs/toggle/{id}")
    public String toggleJobStatus(@PathVariable Long id) {
        Job job = jobService.findById(id).orElse(null);
        if (job != null) {
            job.setOpen(!job.isOpen());
            jobService.save(job);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/jobs/{id}/applicants")
    public String viewApplicants(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return "redirect:/admin/dashboard";
        List<JobApplication> apps = applicationService.findByJob(job);
        model.addAttribute("job", job);
        model.addAttribute("apps", apps);
        return "admin/applicants";
    }

    @GetMapping("/jobs/{id}/export")
    public ResponseEntity<byte[]> exportCsv(@PathVariable Long id) throws Exception {
        Job job = jobService.findById(id).orElse(null);
        if (job == null) return ResponseEntity.notFound().build();

        List<JobApplication> apps = applicationService.findByJob(job);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Name","Email","Resume","Applied At"));

        for (JobApplication a : apps) {
            csvPrinter.printRecord(a.getApplicantName(), a.getApplicantEmail(), a.getResumeText(), a.getAppliedAt());
        }
        csvPrinter.flush();

        byte[] bytes = out.toByteArray();
        String filename = "applicants_job_" + job.getId() + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }
}
