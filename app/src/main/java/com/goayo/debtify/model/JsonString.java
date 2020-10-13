package com.goayo.debtify.model;

public abstract class JsonString {

    private final String json;

    public JsonString(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public static class GroupJsonString extends JsonString{
        public GroupJsonString(String json) {
            super(json);
        }
    }

    public static class GroupArrayJsonString extends JsonString{
        public GroupArrayJsonString(String json) {
            super(json);
        }
    }

    public static class UserArrayJsonString extends JsonString{
        public UserArrayJsonString(String json) {
            super(json);
        }
    }

    public static class UserJsonString extends JsonString{
        public UserJsonString(String json) {
            super(json);
        }
    }
}
