package com.slmas.Sl.domain;

public class Guide {
    private Long id;
    private String title;
    private String guideText;
    private boolean adminOnly;

    public Guide() {
    }

    public Guide(Long id, String title, String guideText, boolean adminOnly) {
        this.id = id;
        this.title = title;
        this.guideText = guideText;
        this.adminOnly = adminOnly;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuideText() {
        return guideText;
    }

    public void setGuideText(String guideText) {
        this.guideText = guideText;
    }

    public boolean isAdminOnly() {
        return adminOnly;
    }

    public void setAdminOnly(boolean adminOnly) {
        this.adminOnly = adminOnly;
    }
}
