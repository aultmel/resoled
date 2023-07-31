package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Brand;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
