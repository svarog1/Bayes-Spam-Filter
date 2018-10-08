/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author santi
 */
public class BayesSpamFilter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Filter filter = new Filter();
        {
            System.out.println("Imput Path Ham");
            // "E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\ham-anlern"
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//            String line = buffer.readLine();
            ReadDocuments mReader = new ReadDocuments("E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\ham-anlern");
            filter.learn(mReader.ReadMails(), true);
        }
        {
            System.out.println("Imput Path Spam");
            //"E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\spam-anlern"
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//            String line = buffer.readLine();
            ReadDocuments mReader = new ReadDocuments("E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\spam-anlern");
            filter.learn(mReader.ReadMails(), false);
            filter.fninischLearn();
            filter.fninischLearn(); //Doppelte ausführung zusätzlich Elemente können entfernt werden. 

        }
        {
            ReadDocuments mReader = new ReadDocuments("E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\spam-anlern");
            for (String mail : mReader.ReadMails()) {
                filter.decide(mail);
            }
            
        }

    }

}
