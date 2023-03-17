package org.slt;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ColorMerge {
    Gson gson = new Gson();


    public static void main(String[] args) throws IOException {
        ColorMerge cm = new ColorMerge();

        // Reader reader = Files.newBufferedReader(Path.of("C:\\Users\\sterr\\IdeaProjects\\mgapp\\color-transform\\colorsHex.json"));
        Reader reader1 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colorsHex.json"));
        System.out.println();
        ColorDTO[] colorDTOS = cm.gson.fromJson(reader1,ColorDTO[].class);
        System.out.println("Number of glass ids with color hex values : "+ colorDTOS.length);

        ArrayList<ColorDTO> colors = new ArrayList<>();
        for (ColorDTO c : colorDTOS) {
            colors.add(c);
        }

        // Reader reader = Files.newBufferedReader(Path.of("C:\\Users\\sterr\\IdeaProjects\\mgapp\\color-transform\\colorsHex.json"));
        Reader reader2 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colorsHexFrit.json"));
        System.out.println();
        ColorDTO[] colorFritDTOS = cm.gson.fromJson(reader2,ColorDTO[].class);
        System.out.println("Number of glass ids with color hex values : "+ colorDTOS.length);

        for (ColorDTO c : colorFritDTOS) {
            colors.add(c);
        }

        Map<String,ColorDTO> colorMerge = new HashMap<String,ColorDTO>();

        for (ColorDTO c: colors) {
            colorMerge.put(c.getId(),c);
        }

        // Writer writer1 = Files.newBufferedWriter(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colors-combined-std-frit.txt"));

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, ColorDTO> mapColor : colorMerge.entrySet()) {
            String key = mapColor.getKey();
            ColorDTO color = (mapColor.getValue());
            sb.append(color.toCommaString()+"\n");
        }
        System.out.println(sb.toString());

        FileOutputStream myFileOutStream
                = new FileOutputStream("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colors-combined-std-frit.txt");

        ObjectOutputStream myObjectOutStream
                = new ObjectOutputStream(myFileOutStream);

        myObjectOutStream.writeObject(colorMerge);

        // closing FileOutputStream and
        // ObjectOutputStream
        myObjectOutStream.close();
        myFileOutStream.close();


    }

    public void writeColorMapToFile(){

    }
}
