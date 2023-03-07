package main.java;

public class ColorDTO {
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

   // String colorId, String colorName, String hexColor
    @Override
    public String toString() {
        return  "  <div class=\"column\" style=\"background-color:"+getHex()+";\">\n" +
                "    <h4>"+getName()+"</h4>\n" +
                "    <p>"+getId()+" hex: "+getHex()+"</p>\n" +
                "  </div>\n";
    }




}
