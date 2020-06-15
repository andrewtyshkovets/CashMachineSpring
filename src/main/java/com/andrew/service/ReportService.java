package com.andrew.service;

import com.andrew.models.Report;

public interface ReportService {
    public Report makeXReport(Long cashBoxId);
    public Report makeZReport(Long cashBoxId);
}
