package org.slt;

import java.util.Locale;

class StringFormatter {
    public static String capitalizeWord(String str){
        str = str.toLowerCase();
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            // System.out.println();
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }
}