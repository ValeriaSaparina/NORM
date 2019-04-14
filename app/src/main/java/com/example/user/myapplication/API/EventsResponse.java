package com.example.user.myapplication.API;

import java.util.List;

public class EventsResponse {
    int total;
    List<EventResponse> value;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EventResponse> getValue() {
        return value;
    }
}
