package org.example;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        String uri = "hdfs://localhost:9000/myDir";
        Configuration conf = new Configuration();
        FileSystem fs =FileSystem.get(URI.create(uri), conf);

        //String csvFile = "C:/Users/dufeu/Documents/Codage/ExtractionDataTPA/M2_DMA_Catalogue/Catalogue.csv";
        //CSVReader.read(csvFile);

        ArrayList<String[]> catalogueArr = new ArrayList<String[]>();

        try {
            catalogueArr = getCSV("C:\\Users\\Romain\\grails\\TPA-MBDS---Java-data-extractor\\hdfs\\M2_DMA_Catalogue\\CO2.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        /*
        for (String[] vehicule : catalogueArr) {
            Document doc = new Document()
                    .append("marque",vehicule[0])
                    .append("nom",vehicule[1])
                    .append("puissance",vehicule[2])
                    .append("longueur",vehicule[3])
                    .append("nbPlaces",vehicule[4])
                    .append("nbPortes",vehicule[5])
                    .append("couleur",vehicule[6])
                    .append("occasion",vehicule[7])
                    .append("prix",vehicule[8]);
        }
        */

    }

    public static ArrayList<String[]> getCSV(String pathfile) throws FileNotFoundException, UnsupportedEncodingException {
        return getCSV(pathfile,true);
    }
    public static ArrayList<String[]> getCSV(String pathfile, boolean skipHeader) throws FileNotFoundException, UnsupportedEncodingException {

        FileInputStream is = new FileInputStream(new File(pathfile));
        InputStreamReader isr = new InputStreamReader(is, "WINDOWS-1252");
        Scanner sc = new Scanner(isr);
        sc.useDelimiter("\\n");   //sets the delimiter pattern


        ArrayList<String[]> arraytmp = new ArrayList<String[]>();

        if(skipHeader) {
            sc.next();
        }

        while (sc.hasNext())  //returns a boolean value
        {

            String line = sc.next();
            String[] lineArray = line.split(",");
            arraytmp.add(lineArray);
            for (String s : lineArray) {
                System.out.println(s);
            }
        }
        sc.close();
        return  arraytmp;
    }

}