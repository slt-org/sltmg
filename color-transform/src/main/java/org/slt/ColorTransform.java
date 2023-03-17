package org.slt;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;


public class ColorTransform {
    public static void main(String[] args) {
        String colorName = "";
        String colorId = "";
        String hexColor = "";

        Gson gson = new Gson();

        // create a reader
        try {
            Reader reader = Files.newBufferedReader(Path.of("C:\\Users\\sterr\\IdeaProjects\\mgapp\\color-transform\\colorsHex.json"));
            // System.out.println();
            ColorDTO[] colorDTOS = gson.fromJson(reader,ColorDTO[].class);
            System.out.println("Number of glass ids with color hex values : "+ colorDTOS.length);

            for(ColorDTO colorDTO : colorDTOS){
                System.out.println(colorDTO.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

