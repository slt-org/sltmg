package org.slt;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;


public class ReactionsTransform {

    Gson gson = new Gson();

    public static void main(String[] args) {
        ReactionsTransform rt = new ReactionsTransform();
        String colorName = "";
        String colorId = "";
        String hexColor = "";

        // create a reader
        try {
            // Reader reader = Files.newBufferedReader(Path.of("C:\\Users\\sterr\\IdeaProjects\\mgapp\\color-transform\\colorsHex.json"));
            Reader reader1 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colorsHex.json"));
            System.out.println();
            ColorDTO[] colorDTOS = rt.gson.fromJson(reader1,ColorDTO[].class);
            System.out.println("Number of glass ids with color hex values : "+ colorDTOS.length);

            ArrayList<ColorDTO> colors = new ArrayList<>();
            for (ColorDTO c : colorDTOS) {
                colors.add(c);
            }

            Reader reader2 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\reactions.json"));
            ReactionsDTO[] reactionsDTOS = rt.gson.fromJson(reader2,ReactionsDTO[].class);
            System.out.println("Number of glass ids with reaction bearing value : "+ reactionsDTOS.length);
            System.out.println();

            ArrayList<ReactionsDTO> reactions = new ArrayList<>();
            for (ReactionsDTO r : reactionsDTOS) {
                reactions.add(r);
            }

            // Reader reader3 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\reactionWithHexColor.json"));
            // ReactionsDTO[] fullReactions = rt.gson.fromJson(reader3,ReactionsDTO[].class);

            ArrayList<ColorDTO> colorsNotFound = rt.getColorIdsNotFoundInReactionIds(colors,reactions);
            ArrayList<ReactionsDTO> reactionsNotFound = rt.getReactionIdsNotFoundInColorIds(colors,reactions);

            // add color not found in reactions to reaction list
            for (ColorDTO color: colorsNotFound) {
                ReactionsDTO r = color.asReactionsDTO();
                reactions.add(r);
            }

            // add color values to all reactions
            for (ReactionsDTO r : reactions) {
                r.setName(StringFormatter.capitalizeWord(r.getName()));
                // r.setId(Integer.valueOf(r.getId()).toString());
                ColorDTO c = colors.stream()
                        .filter( colorDTO -> colorDTO.getId().equals(r.getId()))
                        .findAny()
                        .orElse(null);
                if(c != null){
                    r.setHex(c.getHex());
                }else{
                    System.out.println("no color object returned for reaction id : "+r.getId());
                }
            }
            System.out.println();
            // final trim of glass id
            for (ReactionsDTO r : reactions) {
                r.setId(Integer.valueOf(r.getId()).toString());
                // System.out.println(rt.gson.toJson(r));
            }

            Collections.sort(reactions, new Comparator<ReactionsDTO>() {
                @Override
                public int compare(ReactionsDTO o1, ReactionsDTO o2) {
                    return Integer.valueOf(o1.getId()) - Integer.valueOf(o2.getId()) ;
                }
            });

            for (ReactionsDTO r : reactions) {
                // System.out.println(rt.gson.toJson(r));
                System.out.println("<option value="+r.getId()+">"+r.getId()+", "+r.getName()+"</option>");
            }

            System.out.println();

            System.out.println("[");
            for (ReactionsDTO r : reactions) {
                System.out.println(rt.gson.toJson(r)+",");
                // System.out.println("<option>"+r.getId()+", "+r.getName()+"</option>");
            }
            System.out.println("]");

            System.out.println();

            Collections.sort(reactions, new Comparator<ReactionsDTO>() {
                @Override
                public int compare(ReactionsDTO o1, ReactionsDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            for (ReactionsDTO r : reactions) {
                // System.out.println(rt.gson.toJson(r));
                System.out.println("<option value="+r.getId()+">"+r.getName()+", "+r.getId()+"</option>");
            }

            System.out.println();

            Reader reader3 = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-reactions-merge.json"));
            ReactionsDTO[] finalList = rt.gson.fromJson(reader3,ReactionsDTO[].class);

            ArrayList<ReactionsDTO> mylist = new ArrayList<>();
            for (ReactionsDTO r : finalList) {
                mylist.add(r);
            }

            Collections.sort(mylist, new Comparator<ReactionsDTO>() {
                @Override
                public int compare(ReactionsDTO o1, ReactionsDTO o2) {
                    return o1.getBearing().compareTo(o2.getBearing());
                }
            });

            for (ReactionsDTO ml : mylist) {
                System.out.println(rt.gson.toJson(ml));
                //System.out.println("<option value="+r.getId()+">"+r.getName()+", "+r.getId()+"</option>");
            }

            System.out.println();




            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public StringBuffer prepSelectionList(ReactionsDTO[] fullReactions){
        // create new options list
        StringBuffer options = new StringBuffer();
        for(ReactionsDTO reactionsDTO : fullReactions) {
            options.append("<option>"+reactionsDTO.getName().trim()+", "+reactionsDTO.getId()+"</option>\n");
        }
        System.out.println();
        System.out.println("**************************************************************************************");
        System.out.println();

        for(ReactionsDTO reactionsDTO : fullReactions) {
            options.append("<option>"+reactionsDTO.getId()+", "+reactionsDTO.getName().trim()+"</option>\n");
        }

      return options;
    }

    public ArrayList<ReactionsDTO> getReactionIdsNotFoundInColorIds(ArrayList<ColorDTO> colors, ArrayList<ReactionsDTO> reactions) {
        ArrayList<ReactionsDTO> reactionsNotFound = new ArrayList();
        for(ReactionsDTO reactionsDTO : reactions){
            // lookup reaction dto id in colorDTOS
            // and should always be found but add hex value
            boolean reactionFound = false;
            String reactionId = reactionsDTO.getId();
            for(ColorDTO colorDTO : colors){
                if(colorDTO.getId().equals(reactionId)) {
                    reactionFound=true; break;
                }
            }
            if(reactionFound == false) {
                reactionsNotFound.add(reactionsDTO);
                System.out.println("reaction id not found in color ids " + reactionsDTO.getId());
            }

        }
        System.out.println();
        return reactionsNotFound;
    }

    public ArrayList<ColorDTO> getColorIdsNotFoundInReactionIds(ArrayList<ColorDTO> colors, ArrayList<ReactionsDTO> reactions){
        ArrayList<ColorDTO> colorsNotFound = new ArrayList();
        // Gson gson = new Gson();
        for(ColorDTO colorDTO : colors){
            // lookup reaction dto id in colorDTOS
            // and should always be found but add hex value
            boolean colorFound = false;
            String colorId = colorDTO.getId();
            for(ReactionsDTO reactionsDTO : reactions){
                if(reactionsDTO.getId().equals(colorId)) {
                    colorFound=true; break;
                }
            }
            if(colorFound == false) {
                colorsNotFound.add(colorDTO);
                System.out.println("color id not found in reaction ids " + colorDTO.getId());
            }
        }
        System.out.println();
        return colorsNotFound;
    }
}

