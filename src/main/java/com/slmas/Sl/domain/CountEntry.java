package com.slmas.Sl.domain;

public class CountEntry {
    private String name;
    private Integer count;

    public CountEntry(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
