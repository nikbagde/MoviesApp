package com.nik.movieslistapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rating() {
    }

    /**
     *
     * @param source
     * @param value
     */
    public Rating(String source, String value) {
        super();
        this.source = source;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
