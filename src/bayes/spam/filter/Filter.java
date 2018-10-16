package bayes.spam.filter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

/**
 * This class is in charge of deciding, if a mail is spam or ham. in order to do so it has a learning method which
 * fills up an intern dictionary, with the significant keywords it needs to make the ham/spam decision. it does so
 * based on probabilities.
 * @author santi
 */
public class Filter {
    /**
     * Stores all the significant keywords, and the word itself to calculate later on, if a word has a high spam or ham probability
     */
    HashMap<String, Word> dictionary = new HashMap<String, Word>();
    /**
     * probability, that the mail is ham
     */
    long hamCounter = 0;
    /**probability, that the mail is spam
     *
     */
    long spamCounter = 0;
    /**
     * min value for a mail to be classified spam
     */
    public double schwellenwert =0.99; //0.99
    /**
     * offset, that is added to prevent absolute probabilities
     */
    public double alpha =0.0013; //0.01
    /**
     * min weight a word has to have, before it is recognized by the filter as significant
     */
    double aussagekräftigAb = 0.1;
    /**
     * fills up the dictionary with all the significant words, it learned from the spam/ham mails it had to
     * analise
     * @param mails list of mails as strings
     * @param ham determines, if it is analyzing spam or ham mails
     */
    public void learn(String[] mails, boolean ham) {
        for (String mail : mails) {
            if (mail == null) {
                //Ist hier weil die Mails die Beim einlesen übersprungen wurden auch hier übersprungen werden. Weil null
                System.out.println("Mail konnte nicht eingelesen werden Folgefehler");
                continue;
            }
            if (ham) {
                hamCounter++;
            } else {
                spamCounter++;
            }
            HashMap<String, Integer> words = new HashMap<String, Integer>(); //Enthält alle wörter die in dem e-Mail vorkommen ohne duplikate;
            for (String word : mail.split("\\W+")) {
                String lWord = word.toLowerCase();
                if (!words.containsKey(lWord)) {
                    words.put(word, 1);
                }


            }
            for (String mKey : words.keySet()) {
                if (!dictionary.containsKey(mKey)) {
                    Word w = new Word(mKey);
                    dictionary.put(mKey, w);
                }

                if (ham) {
                    dictionary.get(mKey).ham += 1;
                } else {
                    dictionary.get(mKey).spam += 1;
                }

            }
        }

    }


    /**
     * tries to make the dictionary expressive, by removing all the words, under a certain threshold
     */
    public void fninishLearn() {
        List<String> toRemove = new ArrayList();
        System.out.println("Dictionary size: " + dictionary.size());
        for (Word value : dictionary.values()) {

                if(value.ham ==0){
                    value.wordspamvalue = 1- alpha;
                    continue;
                }
                else if (value.spam == 0){
                    value.wordspamvalue = alpha;
                    continue;
                }
                double spamValue;
                double hamValue;

                spamValue = ((double) value.spam / (double) this.spamCounter);
                hamValue = ((double) value.ham / (double) this.hamCounter);

                value.wordspamvalue = spamValue / (spamValue + hamValue);
        }
    }



    /**
     * Takes a mail, separates it into words, analyses them and calculates the probability that the mail is spam or ham.
     * @param mail
     * @return true if the mail is ham
     */
    public boolean decideIsHam(String mail) {
        double output = 1;

        for (String oWord : mail.split("\\W+")) {
            String kWord = oWord.toLowerCase();

            //calculates the spam/ham probability of the mail, according to the formula by bayes
            if (dictionary.containsKey(kWord)) {
                Word word = dictionary.get(kWord);
                    output = output*(word.wordspamvalue/(1-word.wordspamvalue));

            }
                //Berechnen der ham und Spam values Seperated.
        }
        //oppositeSpamProduct.divide(spamProduct, MathContext.DECIMAL32).doubleValue()
        if (output >= schwellenwert) {
            return false;
        } else {
            return true;
        }
    }

    public boolean altDecideIsSpam(String mail) {
        List<Double> spamProbsOfAWords  = new ArrayList<>();
        int count =0;
        for (String oWord : mail.split("\\W")){
            String kWord = oWord.toLowerCase();
            if( dictionary.containsKey(kWord)){

            }

        }
        return false;
    }

    /**
     * evaluates a mailcollection
     * @param mailcollection
     * @param is_ham boolean value if it should evaluate on ham, or on spam
     * @return the detectionrate
     */
    public double evaluate(String[] mailcollection, boolean is_ham){
        long ham=0;
        long spam=0;

        for (String mail : mailcollection) {
            if (mail!=null) {
                if(decideIsHam(mail))
                {
                    ham++;
                }
                else{
                    spam++;
                }
            }
        }
        double detectionrate =0;
        if(is_ham){
            detectionrate = spam / (double) mailcollection.length;
        }
        else{
            detectionrate = spam / (double) mailcollection.length;
        }
        System.out.println( "AUSWERTUNG " + (is_ham? "HAM:":"SPAM:") +" Schwellenwert: "+ schwellenwert +
                ", Alpha: "+ alpha + ", Als spam erkannt: " + detectionrate );
        return detectionrate;
    }
}
