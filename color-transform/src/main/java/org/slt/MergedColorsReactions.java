package org.slt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MergedColorsReactions {
    private static final String nl = ",\n";

    public static void main(String[] args) {
        MergedColorsReactions mcr = new MergedColorsReactions();

        // deserialize the colorDTOs that merged frit and standard colors
        HashMap<String, ColorDTO> colorHashMap = new HashMap<>();
        colorHashMap = mcr.getColorData(colorHashMap);

        // pull in the combined standard rod stringer and ribbon reactions data
        ArrayList<ReactionsDTO> reactionsDTOArrayList = new ArrayList<>();

        // fill the reactions list with reactionDTOS
        mcr.getInitialReactionData(reactionsDTOArrayList);

        // we have raw reactions with ungroomed names and no hex values
        mcr.groomReactionNamesAndIds(reactionsDTOArrayList);
        // we can now add color values to all reactions ????
        for (ReactionsDTO r : reactionsDTOArrayList) {
            // lookup r id in colorHashMap
            ColorDTO c = colorHashMap.get(r.getId());
            if(c != null){
                r.setHex(c.getHex());
            }else{
                System.out.println("no color data for this reaction id "+r.getId()+" "+r.getName()+" "+r.getBearing());
            }
        }

        mcr.stripLeadingZerosFromId(reactionsDTOArrayList);
        // the data is clean(?) and can be used to generate the total color/reactions table

        mcr.sortOnId(reactionsDTOArrayList);

        // mcr.verifyOutput(reactionsDTOArrayList);

        mcr.jsonOutput(reactionsDTOArrayList);

        // write univeral table to json file to include in html reactions page
        mcr.writeUniveralTableToFile(reactionsDTOArrayList);

        // write id options list for reactions page
        mcr.writeIdSelectionToFile(reactionsDTOArrayList);

        // write name options list for reactions page
        mcr.writeNameSelectionToFile(reactionsDTOArrayList);

        //System.out.println();

    }

    public void verifyOutput(ArrayList<ReactionsDTO> reactions){
        System.out.println();
        System.out.println(" Verify basic output");
        for (ReactionsDTO r : reactions) {
            System.out.println(r.toString());
        }
    }

    public void jsonOutput(ArrayList<ReactionsDTO> reactions){
        System.out.println();
        System.out.println(" Verify Json output");
        for (ReactionsDTO r : reactions) {
            System.out.println(r.toJson());
        }
    }

    public void sortOnId(ArrayList<ReactionsDTO> reactions){
        Collections.sort(reactions, new Comparator<ReactionsDTO>() {
            @Override
            public int compare(ReactionsDTO o1, ReactionsDTO o2) {
                return Integer.valueOf(o1.getId()) - Integer.valueOf(o2.getId()) ;
            }
        });
    }

    public void sortOnName(ArrayList<ReactionsDTO> reactions){
        Collections.sort(reactions, new Comparator<ReactionsDTO>() {
            @Override
            public int compare(ReactionsDTO o1, ReactionsDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void writeUniveralTableToFile(ArrayList<ReactionsDTO> reactions){
        StringBuffer sb = new StringBuffer();
        sortOnId(reactions);

        sb.append("[");
        for (ReactionsDTO r : reactions) {
            sb.append(r.toJson()+nl);
        }
        sb.append("]");
        try {
            BufferedWriter writer = Files.newBufferedWriter(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\univeralTable.json"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIdSelectionToFile(ArrayList<ReactionsDTO> reactions){
        StringBuffer sb = new StringBuffer();
        sortOnId(reactions);

        for (ReactionsDTO r : reactions) {
            // System.out.println(rt.gson.toJson(r));
            sb.append("<option value="+r.getId()+">"+r.getId()+", "+r.getName()+"</option>"+"\n");
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\idOptionTable.html"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNameSelectionToFile(ArrayList<ReactionsDTO> reactions){
        StringBuffer sb = new StringBuffer();
        sortOnName(reactions);

        for (ReactionsDTO r : reactions) {
            // System.out.println(rt.gson.toJson(r));
            sb.append("<option value="+r.getId()+">"+r.getName().trim()+", "+r.getId()+"</option>\n");
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\nameOptionTable.html"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stripLeadingZerosFromId(ArrayList<ReactionsDTO> reactionsDTOArrayList){
        for (ReactionsDTO r : reactionsDTOArrayList) {
            // remove leading zeros
            r.setId(Integer.valueOf(r.getId()).toString());
        }
    }

    public void groomReactionNamesAndIds(ArrayList<ReactionsDTO> reactionsDTOArrayList){
        for (ReactionsDTO r : reactionsDTOArrayList) {
            r.setName(StringFormatter.capitalizeWord(r.getName().trim()));
            r.setId(r.getId().trim());
        }
    }

    public void getInitialReactionData(ArrayList<ReactionsDTO> reactionsDTOArrayList){
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\reactions-combined-std-rod-stringer-ribbon-raw.txt"));
            String dataLine = reader.readLine();
            System.out.println();
            System.out.println("Verifing data file read back for reactions");
            System.out.println();
            // turn these data lines into ReactionDTO object
            while (dataLine != null){
                // System.out.println(dataLine);
                ReactionsDTO r = new ReactionsDTO();
                r.parseInputData(dataLine);
                dataLine = reader.readLine();
                reactionsDTOArrayList.add(r);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HashMap<String, ColorDTO> getColorData(HashMap<String, ColorDTO> colorHashMap){
        try {
            FileInputStream fileInput = new FileInputStream("G:\\My Drive\\development\\sltmg\\color-transform\\dataFiles\\colors-combined-std-frit.txt");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            colorHashMap = (HashMap)objectInput.readObject();

            objectInput.close();
            fileInput.close();
        } catch (IOException obj1) {
            obj1.printStackTrace();
            return null;
        } catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            obj2.printStackTrace();
            return null;
        }

        System.out.println("Deserializing  HashMap..");

        // Displaying content in "newHashMap.txt" using
        // Iterator
        Set set = colorHashMap.entrySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            ColorDTO colorDTO = (ColorDTO) entry.getValue();
            System.out.println(colorDTO.toCommaString());
        }
        return colorHashMap;
    }


}
