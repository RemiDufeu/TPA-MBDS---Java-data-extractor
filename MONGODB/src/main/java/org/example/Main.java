package org.example;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationOptions;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;

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

        MongoCollection<Document> collection = database.getCollection("Catalogue");

        createCollCatalogue(database);

        ArrayList<String[]> catalogueArr = new ArrayList<String[]>();

        try {
            catalogueArr = getCSV("C:\\Users\\dufeu\\Documents\\Codage\\ExtractionDataTPA\\MONGODB\\M2_DMA_Catalogue\\Catalogue.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        for (String[] vehicule : catalogueArr) {
            Document doc = new Document()
                    .append("Marque",vehicule[0])
                    .append("Nom",vehicule[1])
                    .append("Puissance",Float.parseFloat(vehicule[2]))
                    .append("Longueur",vehicule[3])
                    .append("NbPlaces",Integer.parseInt(vehicule[4]))
                    .append("NbPortes",Integer.parseInt(vehicule[5]))
                    .append("Couleur",vehicule[6])
                    .append("Occasion",Boolean.parseBoolean(vehicule[7]))
                    .append("Prix",Float.parseFloat(vehicule[8]));

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

    public static void createCollCatalogue(MongoDatabase db) {

        Bson marqueType = Filters.type("Marque", BsonType.STRING);
        Bson nomType = Filters.type("Nom", BsonType.STRING);
        Bson puissanceType = Filters.type("Puissance", BsonType.DOUBLE);
        Bson longueurType = Filters.type("Longueur", BsonType.STRING);
        Bson nbPlacesType = Filters.type("NbPlaces", BsonType.INT32);
        Bson nbPortesType = Filters.type("NbPortes", BsonType.INT32);
        Bson Couleurtype = Filters.type("Couleur", BsonType.STRING);
        Bson Occasiontype = Filters.type("Occasion", BsonType.BOOLEAN);
        Bson Prixtype = Filters.type("Prix", BsonType.DOUBLE);


        Bson validator = Filters.and(marqueType, nomType, puissanceType,longueurType,nbPlacesType,nbPortesType,Couleurtype,Occasiontype,Prixtype);

        db.getCollection("Catalogue").drop();
        db.createCollection("Catalogue", new CreateCollectionOptions().validationOptions(new ValidationOptions()
                .validator(validator)));
    }

}