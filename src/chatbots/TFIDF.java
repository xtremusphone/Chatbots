package chatbots;

import java.io.Serializable;
import java.util.ArrayList;

public class TFIDF implements Serializable{
    
    //variables to store our Chatbot Knowledge Base to calculate variables
    public ArrayList<ArrayList<String>> sentence_list = new ArrayList<>();
    
    public TFIDF(){

    }
    
    private double TF(String word,ArrayList<ArrayList<String>> sentence_list){
        double frequency = 0;
        double vocab_count = 0;
        /*for(String wrd:sentence_list{
            if(wrd.equalsIgnoreCase(word))
                ++frequency;
        }*/
        for(ArrayList<String> sentence:sentence_list){
            for(String sent_word:sentence){
                if(sent_word.equalsIgnoreCase(word))
                    ++frequency;
                ++vocab_count;
            }
        }
        return frequency / vocab_count;
    }
    
    private double IDF(String word,ArrayList<ArrayList<String>> sentc_list){
        int document_freq = 0;
        for(ArrayList<String> sent:sentc_list){
            for(String x:sent){
                if(x.equalsIgnoreCase(word)){
                    ++document_freq;
                    break;
                }
            }
        }
            double idf;
            idf = Math.log10((double)sentc_list.size() / (double)document_freq);
            return idf;
    }
    
    public double getCumulativeTFIDF(String word){
        return TF(word, sentence_list) * IDF(word, sentence_list);
    }
    
}
