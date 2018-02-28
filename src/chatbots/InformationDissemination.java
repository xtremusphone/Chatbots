package chatbots;

import java.util.ArrayList;
import java.util.HashMap;
import nlp.Chunker;

public class InformationDissemination {
    
    private HashMap<String,String> tagged;
    private ArrayList<String> sentence;
    private String sentence_build = "";
    
    public InformationDissemination(HashMap<String,String> tag,ArrayList<String> sntc){
        tagged = tag;
        sentence = sntc;
        for(String x:sentence){
            sentence_build += x + " ";
        }
        sentence_build = sentence_build.substring(0,sentence_build.length() - 1);
    }
    
    public void splitSentence(){
        //splitting sentence
        ArrayList<String> sentence_list = new ArrayList<>();
        mergeNNP();
        if(sentence.contains("and")){
            int and_index = sentence.indexOf("and");
            if(tagged.get(sentence.get(and_index - 1)).equals("NNP") && tagged.get(sentence.get(and_index + 1)).equals("NNP")){
                //if(tagged.get(and_index) + )
            }
        }
        
    }
    
    private void mergeNNP(){
        Chunker ch = new Chunker();
        ArrayList<String> NNPs = (ArrayList<String>) ch.getNouns(tagged, sentence);
        for(String person:NNPs){
            String[] word = person.split(" ");
            int pointer = sentence.indexOf(word[0]);
            for(String x:word){
                tagged.remove(x);
                sentence.remove(x);
            }
            tagged.put(person, "NNP");
            sentence.add(pointer, person);
        }
        System.out.println(sentence.toString());
    }
}
