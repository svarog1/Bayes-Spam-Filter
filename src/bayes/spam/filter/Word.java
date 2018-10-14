/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes.spam.filter;

/**
 * Declares words, and has two variables who can store how many times it showed up in spam/ham mails
 * @author santi
 */
public class Word {
    /**
     * how many times the word showed up in a ham mail
     */
    public int ham;
    /**
     * how many times the word showed up in a spam mail
     */
    public int spam;
    /**
     * final, the actual word as a Strin
     */
    public final String word;

    /**
     * constructor
     * @param word
     */
    public Word(String word) {
        this.word = word;
    }
    
    


}
