package chatbots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nlp.ViterbiAlgorithm;
import nlp.WordTokenizer;
import nlp.Chunker;

public class CoreArchitecture{
    
    private ViterbiAlgorithm va = new ViterbiAlgorithm();
    private ArrayList<String> answer = new ArrayList<>();
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> mapped_q = new ArrayList<>();
    
    public CoreArchitecture(){
        
    }
    
    public String botReply(String input){
        String reply = "";
        HashMap<String,String> tagged = va.getPOSTagging(input);
        WordTokenizer wt = new WordTokenizer();
        Chunker ch = new Chunker();
        List<String> tokenized = wt.tokenizer(input);
        for(String word : tokenized){
            reply += word + " [" + tagged.get(word) + "], ";
        }
        reply += "\n";
        QuestionClassification qc = new QuestionClassification();
        if(tokenized.contains("?")){
            if(qc.isHow(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("How", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
            if(qc.isWhat(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("What", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
            if(qc.isWhen(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("When", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
            if(qc.isWhere(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Where", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
            if(qc.isWhy(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Why", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
            if(qc.isWho(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Who", (ArrayList<String>) tokenized);
                if(temp.size() == 1)
                    reply += temp.keySet();
                System.out.println(temp.toString());
            }
        }
        else{
            InformationDissemination id = new InformationDissemination(tagged, (ArrayList<String>) tokenized);
            id.addInformation(id.splitSentenceConnector());
            for(String x:id.ans){
                answer.add(x);
            }
            for(String x:id.keys){
                keywords.add(x);
            }
            for(String x:id.map_q){
                mapped_q.add(x);
            }
        }
        return reply;
    }
    
    public HashMap<String,Double> getReply(String question_type,ArrayList<String> keys){
        HashMap<String,Double> max = new HashMap<>();
        ArrayList<Integer> candidate = new ArrayList<>();
        
        for(int i = 0; i < mapped_q.size();i++){
            if(mapped_q.get(i).equals(question_type)){
                candidate.add(i);
            }
        }
        
        for(Integer index:candidate){
            String[] temp = keywords.get(index).split(",");
            ArrayList<String> word_list = new ArrayList<>(Arrays.asList(temp));
            double score = 0;
            for(String words:word_list){
                if(keys.contains(words))
                    ++score;
            }
            if(max.containsKey(answer.get(index))){
                if(max.get(answer.get(index)) < score)
                    max.put(answer.get(index), score/word_list.size());
            }
            else{
                max.put(answer.get(index), score/word_list.size());    
            }  
        }
        
        return max;
    }
}
