package chatbots;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionClassification {
    
    public QuestionClassification(){
        
    }
    
    //Who question always ask PERSON
    //[12] who/whom --> PERSON
    //[13] when --> TIME/DATE
    //[14] where/what place --> LOCATION
    //[15] what time (of day) --> TIME
    //[16] what day (of the week) --> DAY
    //[17] what/which month --> MONTH
    //[18] what age/how old --> AGE
    //[19] what brand --> PRODUCT
    //[20] what --> NAME
    //[21] how far/tall/high --> LENGTH
    //[22] how large/hig/small --> AREA
    //[23] how heavy --> WEIGHT
    //[24] how rich --> MONEY
    //[25] how often --> FREQUENCY
    //[26] how many --> NUMBER
    //[27] how long --> LENGTH/DURATION
    //[28] why/for what --> REASON
    public boolean isWho(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("who") || sentence.contains("Who"))
            return true;
        if(sentence.contains("whom") || sentence.contains("Whom"))
            return true;
        return false;
    }
    
    //Where always ask for location
    public boolean isWhere(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("where") || sentence.contains("Where"))
            return true;
        return false;
    }
    
    //Why always ask for reason
    public boolean isWhy(HashMap<String,String> tagged,ArrayList<String> sentence){
        if(sentence.contains("why") || sentence.contains("Why"))
            return true;
        return false;
    }
    
    //When always ask for time
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
