package com.example.user.myapplication.API;

import java.util.List;

public class EventsResponse {
    int total;
    List<EventResponse> values;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EventResponse> getValues() {
        return values;
    }

    public void setValues(List<EventResponse> values) {
        this.values = values;
    }
}
