package com.example.jobms.mapper;

import com.example.jobms.domain.Job;
import com.example.jobms.dto.JobDTO;
import com.example.jobms.external.Company;
import com.example.jobms.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setMinSalary(job.getMinSalary());
        dto.setCompanyId(job.getCompanyId());
        dto.setCompany(company);
        dto.setReviews(reviews);
        return dto;
    }
}
