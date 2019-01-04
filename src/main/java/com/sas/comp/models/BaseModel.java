package com.sas.comp.models;

/**
 * Base class for any Models we create in our services that have an ID parameter
 * Created by phsabo on 8/19/16.
 */
public abstract class BaseModel {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
