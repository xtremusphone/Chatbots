package chatbots;

import java.util.ArrayList;

public class TFIDF {
    
    //variables to store our Chatbot Knowledge Base to calculate variables
    public ArrayList<ArrayList<String>> sentence_list = new ArrayList<>();
    
    public TFIDF(){

    }
    
    private double TF(String word,ArrayList<String> sentence){
        double frequency = 0;
        for(String wrd:sentence){
            if(wrd.equalsIgnoreCase(word))
                ++frequency;
        }
        return frequency / sentence.size();
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
        if(document_freq != 0){
           double idf;
            idf = Math.log10((double)sentence_list.size() / (double)document_freq);
            return document_freq;
        }
        return 0.0;
    }
    
    public double getTFIDF(String word,ArrayList<String> sentence){
        return TF(word, sentence) * IDF(word, sentence_list);
    }
    
    public double getCumulativeTFIDF(String word){
        double cumulative_score = 0;
        for(ArrayList<String> sentence_bank:sentence_list){
            cumulative_score += getTFIDF(word, sentence_bank);
        }      
        return cumulative_score;
    }
    
}
