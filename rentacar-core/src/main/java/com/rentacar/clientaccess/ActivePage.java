package com.rentacar.clientaccess;


public enum ActivePage {
    CALENDAR ("calendar"), DAY("day"), BOOKING("booking"), TRIPS("trips"), DATA("data");

    private final String active;

    ActivePage(String active) {
        this.active = active;
    }

    public String get(){
        return active;
    }
}
