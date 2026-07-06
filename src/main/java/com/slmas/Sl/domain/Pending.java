package com.slmas.Sl.domain;

import java.util.Date;

public class Pending {
    private Long id;
    private Date pendingDate;
    private String notes;
    private boolean done;

    public Pending() {
    }

    public Pending(Long id, Date pendingDate, String notes, boolean done) {
        this.id = id;
        this.pendingDate = pendingDate;
        this.notes = notes;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPendingDate() {
        return pendingDate;
    }

    public void setPendingDate(Date pendingDate) {
        this.pendingDate = pendingDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
