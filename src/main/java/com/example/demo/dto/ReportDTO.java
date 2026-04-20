package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReportDTO {

    private Long reportID;

    private String type;
    private LocalDate generationDate;
    private LocalTime generationTime;

    private String format;

    private Long userID;

    public ReportDTO() {}

    public ReportDTO(Long reportID, String type, LocalDate generationDate,
                     LocalTime generationTime, String format, Long userID) {
        this.reportID = reportID;
        this.type = type;
        this.generationDate = generationDate;
        this.generationTime = generationTime;
        this.format = format;
        this.userID = userID;
    }

    // getters & setters
    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    public LocalTime getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(LocalTime generationTime) {
        this.generationTime = generationTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}