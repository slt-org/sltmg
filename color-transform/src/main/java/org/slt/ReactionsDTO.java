package org.slt;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReactionsDTO {
    private String id;
    private String name;
    private String bearing;
    private String hex;

    public ReactionsDTO() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    // this method takes a line of input from the data file and fills the object members
    public void parseInputData(String data){
        // ex. data comma delimited fields
        // copper, 000016, turquoise opaque Rod
        List<String> values =Arrays.asList(data.split(","));
        // System.out.println("size of values : "+values.size());
        this.bearing = values.get(0);
        this.id = values.get(1);
        this.name = values.get(2);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // String colorId, String colorName, String hexColor
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.id+", ");
        sb.append(this.bearing+", ");
        sb.append(this.hex+", ");
        sb.append(this.name);
        return  sb.toString();
    }




}
