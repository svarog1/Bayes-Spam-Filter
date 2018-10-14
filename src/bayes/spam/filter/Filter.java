package bayes.spam.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public double schwellenwert =0.85;
    /**
     * min weight a word has to have, before it is recognized by the filter as significant
     */
    double alpha = 0.4;
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
            //Wird benötigt um die nicht ausagekräftigen Wörter zu eliminieren
            if (value.ham < hamCounter * alpha && value.spam < spamCounter * alpha) {
                toRemove.add(value.word);
            } else {
                if (value.ham < hamCounter * alpha) {
                    value.ham += hamCounter * alpha;
                }

                if (value.spam < spamCounter * alpha) {
                    value.spam += spamCounter * alpha;
                }
            }
        }
        System.out.println("Removing " + toRemove.size() + " word from the dictionary for not being meaningful");
        //Entfenrt die nicht aussagekräftigen Elemente
        for (String element : toRemove) {
            dictionary.remove(element);
        }
    }

    /**
     * Takes a mail, separates it into words, analyses them and calculates the probability that the mail is spam or ham.
     * @param mail
     * @return true if the mail is ham
     */
    public boolean decideIsHam(String mail) {
        double spamValue = 0;
        double hamValue = 0;
        for (String oWord : mail.split("\\W+")) {
            String kWord = oWord.toLowerCase();
            //calculates the spam/ham probability of the mail, according to the formula by bayes
            if (dictionary.containsKey(kWord)) {
                Word word = dictionary.get(kWord);
                if (spamValue == 0) {
                    spamValue = (double) word.spam / (double) this.spamCounter;
                } else {
                    spamValue = ((double) word.spam / (double) this.spamCounter) * spamValue;
                }
                if (hamValue == 0) {
                    hamValue = (double) word.ham / (double) this.hamCounter;
                } else {
                    hamValue = ((double) word.ham / (double) this.hamCounter) * hamValue;
                }
                //Berechnen der ham und Spam values Seperated.

            }

        }
        double spam = (double) spamValue / (double) ((double) spamValue + (double) hamValue);
        if (spam >= schwellenwert) {
            return false;
        } else {
            return true;
        }
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
            detectionrate = ham / (double) mailcollection.length;
        }
        else{
            detectionrate = spam / (double) mailcollection.length;
        }
        //System.out.println( "AUSWERTUNG " + (is_ham? "HAM:":"SPAM:") +" Schwellenwert: "+ schwellenwert +
        //        ", Alpha: "+alpha + ", Erkennungsrate: " + detectionrate );
        return detectionrate;
    }
}
