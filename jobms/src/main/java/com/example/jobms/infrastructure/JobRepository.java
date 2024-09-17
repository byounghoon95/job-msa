package com.example.jobms.infrastructure;

import com.example.jobms.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
