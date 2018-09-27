package com.kodilla.library.domain;

public enum LibraryBookStatus {
        AVAILABLE("A"), RENTED("R"), LOST("L");
    private String value;
    LibraryBookStatus(String l) {
        this.value = l;
    }

    public String getValue() {
        return value;
    }
}
