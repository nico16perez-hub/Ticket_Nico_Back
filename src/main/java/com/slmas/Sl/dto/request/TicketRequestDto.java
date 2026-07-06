package com.slmas.Sl.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Lob;
import java.util.Date;

public class TicketRequestDto {
    @Override
    public String toString() {
        return "TicketRequestDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", area='" + area + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", solution='" + solution + '\'' +
                ", solvedBy='" + solvedBy + '\'' +
                ", solvedDate=" + solvedDate +
                ", closed=" + closed +
                '}';
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public interface Create {}
    public interface Update{}
    @NotNull(groups = Update.class)
    Long id;
    @NotNull(groups = {Create.class})
    Long userId;
    @NotBlank(groups = {Create.class})
    String userName;
    @NotBlank(groups = {Create.class})
    String area;
    @NotBlank(groups = {Create.class})
    String title;
    @NotBlank(groups = {Create.class})
    String type;
    @NotBlank(groups = {Create.class})
    String description;
    @NotBlank(groups = {Update.class})
    String solution;
    @NotBlank(groups = {Update.class})
    String solvedBy;
    @NotBlank(groups = {Update.class})
    Date solvedDate;
    @NotNull(groups = {Update.class})
    boolean closed;
    boolean important;

    public TicketRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(String solvedBy) {
        this.solvedBy = solvedBy;
    }

    public Date getSolvedDate() {
        return solvedDate;
    }

    public void setSolvedDate(Date solvedDate) {
        this.solvedDate = solvedDate;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
