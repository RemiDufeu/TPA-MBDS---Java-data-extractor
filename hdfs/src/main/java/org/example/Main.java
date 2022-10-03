package org.example;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/");
        MongoClient mongoClient = new MongoClient(connectionString);


        MongoDatabase database = mongoClient.getDatabase("MBDSTPA");

        MongoCollection<Document> collection = database.getCollection("catalogue");

        //String csvFile = "C:/Users/dufeu/Documents/Codage/ExtractionDataTPA/M2_DMA_Catalogue/Catalogue.csv";
        //CSVReader.read(csvFile);

        ArrayList<String[]> catalogueArr = new ArrayList<String[]>();

        try {
            catalogueArr = getCSV("C:\\Users\\Romain\\grails\\TPA-MBDS---Java-data-extractor\\M2_DMA_Catalogue\\Catalogue.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

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

            collection.insertOne(doc);
        }

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
        }
        sc.close();
        return  arraytmp;
    }

}