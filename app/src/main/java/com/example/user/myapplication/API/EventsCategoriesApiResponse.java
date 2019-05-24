package com.example.user.myapplication.API;

import java.util.List;

public class EventsCategoriesApiResponse {
    private List<EventCategoryIncludeApiResponse> value;

    public List<EventCategoryIncludeApiResponse> getValue() {
        return value;
    }

    public void setValue(List<EventCategoryIncludeApiResponse> value) {
        this.value = value;
    }
}
