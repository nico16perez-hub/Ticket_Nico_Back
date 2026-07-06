package com.slmas.Sl.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class WorkRequestDto {

    public interface Create {
    }

    public interface Update {
    }

    @NotNull(groups = WorkRequestDto.Update.class)
    Long id;
    @NotNull(groups = {WorkRequestDto.Create.class})
    Long userId;
    @NotBlank(groups = {WorkRequestDto.Create.class})
    String userName;

    public @NotBlank(groups = {WorkRequestDto.Create.class}) String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(groups = {WorkRequestDto.Create.class}) String title) {
        this.title = title;
    }

    @NotBlank(groups = {WorkRequestDto.Create.class})
    String title;
    @NotBlank(groups = {WorkRequestDto.Create.class})
    String description;


    public WorkRequestDto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
