package chatbots;

import java.util.ArrayList;
import java.util.HashMap;
import nlp.Chunker;
import nlp.WordTokenizer;

public class InformationDissemination {
    
    private ArrayList<String> answers;
    private ArrayList<String> keyword;
    private ArrayList<String> q_type;
    
    private HashMap<String,String> tagged;
    private ArrayList<String> sentence;
    private String sentence_build = "";
    
    public InformationDissemination(HashMap<String,String> tag,ArrayList<String> sntc){
        tagged = tag;
        sentence = sntc;
        answers = new ArrayList<>();
        keyword = new ArrayList<>();
        q_type = new ArrayList<>();
        for(String x:sentence){
            sentence_build += x + " ";
        }
        sentence_build = sentence_build.substring(0,sentence_build.length() - 1);
    }
    
    public ArrayList<String> splitSentence(){
        //splitting sentence
        ArrayList<String> sentence_list = new ArrayList<>();
        mergeNNP();
        if(sentence.contains("and")){
            int and_index = sentence.indexOf("and");
            if(tagged.get(sentence.get(and_index - 1)).equals("NNP") && tagged.get(sentence.get(and_index + 1)).equals("NNP")){
                String root_sentence = "";
                String first_sentence = "";
                String second_sentence = "";
                System.out.println(and_index + 1);
                System.out.println(sentence.size());
                if(and_index + 3 < sentence.size()){                   
                    for(int i = and_index + 2; i < sentence.size(); i ++){
                        if(tagged.get(sentence.get(i)).equals("PRP") || tagged.get(sentence.get(i)).equals("PRP$") || tagged.get(sentence.get(i)).equals("PRP")){
                            continue;
                        }
                        root_sentence += sentence.get(i) + " ";
                    }
                    root_sentence = root_sentence.substring(0, root_sentence.length() - 1);
                    first_sentence = sentence.get(and_index - 1) + " " + root_sentence;
                    sentence_list.add(first_sentence);
                    second_sentence = sentence.get(and_index + 1) + " " + root_sentence;
                    sentence_list.add(second_sentence);
                }
                else{
                    for(int i = 0; i < and_index - 1;i++){
                        if(tagged.get(sentence.get(i)).equals("PRP") || tagged.get(sentence.get(i)).equals("PRP$") || tagged.get(sentence.get(i)).equals("PRP")){
                            continue;
                        }
                        root_sentence += sentence.get(i) + " "; 
                    }
                    root_sentence = root_sentence.substring(0, root_sentence.length() - 1);
                    first_sentence = root_sentence + " " + sentence.get(and_index - 1);
                    sentence_list.add(first_sentence);
                    second_sentence = root_sentence + " " + sentence.get(and_index + 1);
                    sentence_list.add(second_sentence);                 
                }
                System.out.println(first_sentence);
                System.out.println(second_sentence);
            }
            
            if(tagged.get(sentence.get(and_index - 1)).startsWith("VB") && tagged.get(sentence.get(and_index + 1)).startsWith("VB")){
                String root_sentence = "";
                String first_sentence = "";
                String second_sentence = "";
                System.out.println(and_index + 1);
                System.out.println(sentence.size());
                if(and_index + 3 < sentence.size()){                   
                    for(int i = and_index + 2; i < sentence.size(); i ++){
                        root_sentence += sentence.get(i) + " ";
                    }
                    root_sentence = root_sentence.substring(0, root_sentence.length() - 1);
                    first_sentence = sentence.get(and_index - 1) + " " + root_sentence;
                    sentence_list.add(first_sentence);
                    second_sentence = sentence.get(and_index + 1) + " " + root_sentence;
                    sentence_list.add(second_sentence);
                }
                else{
                    for(int i = 0; i < and_index - 1;i++){
                        root_sentence += sentence.get(i) + " "; 
                    }
                    root_sentence = root_sentence.substring(0, root_sentence.length() - 1);
                    first_sentence = root_sentence + " " + sentence.get(and_index - 1);
                    sentence_list.add(first_sentence);
                    second_sentence = root_sentence + " " + sentence.get(and_index + 1);
                    sentence_list.add(second_sentence);                 
                }               
                System.out.println(first_sentence);
                System.out.println(second_sentence);
            }
            
            if(tagged.get(sentence.get(and_index - 1)).equals("NNP") && tagged.get(sentence.get(and_index + 1)).equals("NNP")){
                
            }
        }
        return sentence_list;
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
    }
    
    private void disseminateInformation(ArrayList<String> sentence_list){
        //answers
        //keyword
        //q_type
        WordTokenizer tk = new WordTokenizer();
        for(String sentence:sentence_list){
            //who
            String kw = "";
            for(String word:tk.tokenizer(sentence)){
                if(tagged.get(word).equals("NNP"))
                    answers.add(word);
                else
                    kw += word + " ";
            }
            keyword.add(kw.substring(0,kw.length() - 1));
            q_type.add("Who");
            
            //what
            kw = "";
            for(String word:tk.tokenizer(sentence)){
                if(tagged.get(word).startsWith("VB"))
                    answers.add(word);
                else
                    kw += word;
            }
            keyword.add(kw.substring(0,kw.length() - 1));
            q_type.add("What");
        
            
            //where
            //why
        }
    }
}
