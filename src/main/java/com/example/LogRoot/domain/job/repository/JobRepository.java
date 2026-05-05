package com.example.LogRoot.domain.job.repository;

import com.example.LogRoot.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
}
