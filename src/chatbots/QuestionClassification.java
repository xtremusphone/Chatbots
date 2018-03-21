package chatbots;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionClassification {
    
    public QuestionClassification(){
        
    }
    
    public boolean isWho(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("who") || sentence.contains("Who"))
            return true;
        if(sentence.contains("whom") || sentence.contains("Whom"))
            return true;
        return false;
    }
    
    public boolean isWhere(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("where") || sentence.contains("Where"))
            return true;
        return false;
    }
    
    public boolean isWhy(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("why") || sentence.contains("Why"))
            return true;
        return false;
    }
    
    public boolean isWhen(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("when") || sentence.contains("When"))
            return true;
        return false;
    }
    
    public boolean isHow(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("how") || sentence.contains("How"))
            return true;
        return false;
    }
    
    public boolean isWhat(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("what") || sentence.contains("What"))
            return true;
        return false;
    }
    
    public boolean isDid(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.get(0).equalsIgnoreCase("did"))
            return true;
        return false;
    }
}
