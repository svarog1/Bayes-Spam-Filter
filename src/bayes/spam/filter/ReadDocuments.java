/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

import java.io.BufferedReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author santi
 */
public class ReadDocuments {

    public final String path;

    public ReadDocuments(String path) {
        this.path = path;
    }

    public String[] ReadMails() {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String[] stringFiles = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                File file = listOfFiles[i];
                List<String> linsInFile = Files.readAllLines(Paths.get(file.getPath()));
                for (String element : linsInFile) {
                    stringFiles[i] += element + " ";
                }
            } catch (IOException e) {
                System.out.println("Somting went Wrong wit File: "+listOfFiles[i].getAbsolutePath());
            } catch (Exception e) {
            }

        }
        return stringFiles;
    }
}
