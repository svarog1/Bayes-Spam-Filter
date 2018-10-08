package bayes.spam.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author santi
 */
public class Filter {

    HashMap<String, Word> dictionary = new HashMap<String, Word>();
    long hamCounter = 0;
    long spamCounter = 0;

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
            HashMap<String, Integer> words = new HashMap<String, Integer>(); //Enthält alle wörter die in dem e-Mail vorkommen;                    
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

    public void fninischLearn() {
        List<String> toRemove = new ArrayList();
        for (Word value : dictionary.values()) {
            //Wird benötigt um die nicht ausagekräftigen Wörter zu eliminieren
            if ((value.ham + value.spam) > 20) {
                toRemove.add(value.word);
            }
            if (value.ham < 1) {
                value.ham++;
            }

            if (value.spam < 1) {
                value.spam++;
            }
        }

        //Entfenrt die nicht aussagekräftigen Elemente
        for (String element : toRemove) {
            dictionary.remove(element);
        }
    }

    public boolean decide(String mail) {
        double spamValue = 0;
        double hamValue = 0;
        for (String oWord : mail.split("\\W+")) {
            String kWord = oWord.toLowerCase();
            if (dictionary.containsKey(kWord)) {
                Word word = dictionary.get(kWord);
                if (spamValue == 0) {
                    spamValue = word.spam / this.spamCounter;
                }
                if (hamValue == 0) {
                    hamValue = word.ham / this.hamCounter;
                }
                //Berechnen der ham und Spam values Seperated.
                spamValue = (word.spam / this.spamCounter) * spamValue;
                hamValue = (word.ham / this.hamCounter) * hamValue;

            }

        }
        return true;
    }
}
