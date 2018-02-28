package chatbots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nlp.ViterbiAlgorithm;
import nlp.WordTokenizer;
import nlp.Chunker;

public class CoreArchitecture {
    

    
    private ViterbiAlgorithm va = new ViterbiAlgorithm();
    
    public CoreArchitecture(){
        
    }
    
    public CoreArchitecture(String input){
        
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
        InformationDissemination id = new InformationDissemination(tagged, (ArrayList<String>) tokenized);
        id.splitSentence();
    //1.  Who am I? You are Amir
    //2.  How old am I? You are 22 years old
    //3.  Where do I live? You live in KL
    //4.  When were I born? You were born in 1996
    //5.  Who is my mother? Your mother is Zarina
    //6.  Who is my father? Your father is Rahim
    //7.  What is my hobby? Your hobby is playing dota
    //8.  What is my favourite food? Your favourite food is Nasi Lemak
    //9.  What is my favourite drink? Your favourite drink is CoolBlog
    //10. How tall am I? You are 170cm
        if(input.equalsIgnoreCase("Who am I?")){
            reply += "\n" + "You are Amir";
        }
        
        if(input.equalsIgnoreCase("How old am I?")){
            reply += "\n" + "You are 22 years old";
        }
        
        if(input.equalsIgnoreCase("Where do I live?")){
            reply += "\n" + "You live in KL";
        }
        
        if(input.equalsIgnoreCase("When were I born?")){
            reply += "\n" + "You were born in 31 August 1996";
        }
        
        if(input.equalsIgnoreCase("Who is my mother?")){
            reply += "\n" + "Your mother is Zarina";
        }
        
        if(input.equalsIgnoreCase("Who is my father?")){
            reply += "\n" + "Your father is Rahim";
        }
        
        if(input.equalsIgnoreCase("What is my hobby?")){
            reply += "\n" + "Your hobby is playing dota";
        }
        
        if(input.equalsIgnoreCase("What is my favourite food?")){
            reply += "\n" + "Your favourite food is Nasi Lemak";
        }
        
        if(input.equalsIgnoreCase("What is my favourite drink?")){
            reply += "\n" + "Your favourite drink is CoolBlog";
        }
        
        if(input.equalsIgnoreCase("How tall am I?")){
            reply += "\n" + "You are 170cm";
        }
        
        return reply;
    }
    

}
