/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

import java.io.IOException;

/**
 *
 * @author santi
 */
public class BayesSpamFilter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Filter filter = new Filter();
        {
            System.out.println("Imput Path Ham");
            ReadDocuments mReader = new ReadDocuments("\\ham-anlern");

            filter.learn(mReader.ReadMails(), true);
        }
        {
            System.out.println("Imput Path Spam");
            //"E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\spam-anlern"
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//            String line = buffer.readLine();
            ReadDocuments mReader = new ReadDocuments("\\spam-anlern");
            filter.learn(mReader.ReadMails(), false);
            filter.fninishLearn();
            filter.fninishLearn(); //Doppelte ausführung zusätzlich Elemente können entfernt werden.

        }
        {
            long ham=0;
            long spam=0;
            //"E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\ham-kallibrierung"
            //ReadDocuments mReader = new ReadDocuments("E:\\Santino\\OneDrive\\FH\\Dist\\Uebungen\\Bayes-SpamFilter\\spam-anlern");
            ReadDocuments mReader = new ReadDocuments("\\spam-kallibrierung");
            for (String mail : mReader.ReadMails()) {
                if (mail!=null) {
                 if(filter.decideIsHam(mail))
                 {
                     ham++;
                 }
                 else{
                     spam++;
                 }
                }
                
            }
            System.out.println("bayes.spam.filter.BayesSpamFilter.main()");
            
        }
        {
            long ham=0;
            long spam=0;

            ReadDocuments mReader = new ReadDocuments("\\ham-kallibrierung");
            for (String mail : mReader.ReadMails()) {
                if (mail!=null) {
                 if(filter.decideIsHam(mail))
                 {
                     ham++;
                 }
                 else{
                     spam++;
                 }
                }
                
            }
            System.out.println("bayes.spam.filter.BayesSpamFilter.main()");
            
        }
    }

}
