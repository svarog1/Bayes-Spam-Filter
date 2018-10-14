/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

import java.io.BufferedReader;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * has the functionality to read all documents from a folder, and extract the data
 * @author santi
 */
public class ReadDocuments {
    /**
     * Charset used to decode the documents
     */
    private Charset charset = Charset.forName("ISO-8859-1");
    /**
     * path to the folder, where the documents are stored
     */
    public final String path;

    /**
     * constructor
     * @param path path of the folder where the documents are stored
     */
    public ReadDocuments(String path) {
        this.path = path;
    }

    /**
     * Reads the mails from the folder
     * @return an array of strings, where all the mails are stored
     */
    public String[] ReadMails() {

        File folder = new File("./data/" + path );
        System.out.println(folder.toString());
        File[] listOfFiles = folder.listFiles();

        String[] stringFiles = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                File file = listOfFiles[i];
                List<String> linsInFile = Files.readAllLines(Paths.get(file.getPath()), charset);
                for (String element : linsInFile) {
                    stringFiles[i] += element + " ";
                }
            } catch (IOException e) {
                System.out.println("Somting went Wrong wit File: "+listOfFiles[i].getAbsolutePath());
            } catch (Exception e) {
                System.out.println("critical failure");
            }

        }
        return stringFiles;
    }
}
