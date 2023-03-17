package org.slt;

import java.io.Serializable;

public class ColorDTO implements Serializable {
    private String id;
    private String name;
    private String cmyk;
    private String rgb;
    private String hex;

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

    public String getCmyk() {
        return cmyk;
    }

    public void setCmyk(String cmyk) {
        this.cmyk = cmyk;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }


public ReactionsDTO asReactionsDTO(){
        ReactionsDTO tmp = new ReactionsDTO();
        tmp.setId(this.id);
        tmp.setName(this.name);
        tmp.setBearing("?");
        tmp.setHex(this.getHex());
        return tmp;
}

public String toCommaString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.id +",");
        sb.append(this.hex +",");
        sb.append(this.name);
        return sb.toString();
}

   // String colorId, String colorName, String hexColor
/*
    @Override
    public String toString() {
        return  "  <div class=\"column\" style=\"background-color:"+getHex()+";\">\n" +
                "    <h4>"+getName()+"</h4>\n" +
                "    <p>"+getId()+" hex: "+getHex()+"</p>\n" +
                "  </div>\n";
    }
*/

}
