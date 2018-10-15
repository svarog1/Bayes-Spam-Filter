/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

import java.io.IOException;
import java.nio.file.Path;

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
                //learn HAM
                System.out.println("Imput Path Ham");
                ReadDocuments hamReader = new ReadDocuments("\\ham-anlern");
                filter.learn(hamReader.ReadMails(), true);

                //learn SPAM
                System.out.println("Imput Path Spam");
                ReadDocuments spamReader = new ReadDocuments("\\spam-anlern");
                filter.learn(spamReader.ReadMails(), false);

                //filter.tweakDictionary();
                filter.fninishLearn();
                //filter.fninishLearn(); //Doppelte ausführung zusätzlich Elemente können entfernt werden.



                ReadDocuments hReader = new ReadDocuments("\\ham-test");
                String[] ham_mails = hReader.ReadMails();
                ReadDocuments sReader = new ReadDocuments("\\spam-test");
                String[] spam_mails = sReader.ReadMails();
                filter.evaluate(ham_mails, true);
                filter.evaluate(spam_mails, false);

            }


/**
        {   //tryouts
            ReadDocuments hReader = new ReadDocuments("\\ham-kallibrierung");
            String[] ham_mails = hReader.ReadMails();
            ReadDocuments sReader = new ReadDocuments("\\spam-kallibrierung");
            String[] spam_mails = sReader.ReadMails();

            double[] alpha_schwell_maxdetrate = new double[]{0,0,0};

            for( double a = 0.02; a <0.4 ; a=a+0.02 )
            {
                filter = new Filter();
                filter.aussagekräftigAb = a;

                System.out.println("Imput Path Ham");
                ReadDocuments haReader = new ReadDocuments("\\ham-anlern");
                filter.learn(haReader.ReadMails(), true);

                System.out.println("Imput Path Spam");
                ReadDocuments mReader = new ReadDocuments("\\spam-anlern");
                filter.learn(mReader.ReadMails(), false);

                filter.fninishLearn();

                for (double s = 0.9; s<=1; s = s+0.01){
                    filter.schwellenwert=s;
                    double curr = filter.evaluate(ham_mails, true) + filter.evaluate(spam_mails, false);
                    if(curr>alpha_schwell_maxdetrate[2]){
                        alpha_schwell_maxdetrate[0] = a;
                        alpha_schwell_maxdetrate[1] = s;
                        alpha_schwell_maxdetrate[2] = curr;
                        System.out.println("---------------------------------------");
                    }
                    System.out.println(s);
                }
                System.out.println(a);
            }
            System.out.println("A: " + alpha_schwell_maxdetrate[0] + " Schwell: "+ alpha_schwell_maxdetrate[1] +" Max: "+ alpha_schwell_maxdetrate[2]);


        }

*/

}
}
