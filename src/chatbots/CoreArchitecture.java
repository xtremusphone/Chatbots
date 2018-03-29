package chatbots;

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
    private ArrayList<String> root_sent = new ArrayList<>();
    
    private TFIDF tfid = new TFIDF();
    
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
        if(tokenized.contains("?") || containsQuestion((ArrayList<String>) tokenized)){
            reply += "Bot: ";
            if(qc.isHow(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("How", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isWhat(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("What", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        for(String inputs:wt.tokenizer(key_answer)){
                            System.out.println(inputs);
                            if(tagged.get(inputs).startsWith("VB")){
                                //ArrayList<String> sample = (ArrayList<String>) wt.tokenizer(answer_p);
                                boolean check_verbs = false;
                                HashMap<String,String> checker = va.getPOSTagging(key_answer);
                                for(String tsting:checker.keySet()){
                                    if(checker.get(tsting).startsWith("VB") && !tsting.equalsIgnoreCase("is") && !tsting.equalsIgnoreCase("are") && !tsting.equalsIgnoreCase("was") && !tsting.equalsIgnoreCase("were"))
                                        check_verbs = true;
                                }
                                if(check_verbs == true){
                                    answer_p += " and " + key_answer;
                                    break;
                                }
                            }
                        }
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isWhen(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("When", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isWhere(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Where", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isWhy(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Why", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isWho(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Who", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        answer_p = key_answer;
                        score = temp.get(key_answer);
                    }
                    else if(temp.get(key_answer) == score){
                        answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            if(qc.isDid(tagged, (ArrayList<String>) tokenized)){
                reply += proveFact((ArrayList<String>) tokenized,tagged);
            }
        }
        else{
            InformationDissemination id = new InformationDissemination(tagged, (ArrayList<String>) tokenized);
            id.addInformation(id.splitSentenceConnector());
            id.ans.stream().forEach((x) -> {
                answer.add(x);
            });
            id.keys.stream().forEach((x) -> {
                keywords.add(x);
            });
            id.map_q.stream().forEach((x) -> {
                mapped_q.add(x);
            });
            id.root_sentence.stream().forEach((x) -> {
                
                root_sent.add(x);
                ArrayList<String> temp = (ArrayList<String>) wt.tokenizer(x);
                tfid.sentence_list.add(temp);
                System.out.println(temp.get(0) + " : " + tfid.getCumulativeTFIDF(temp.get(0)));
            });
            reply += "Bot: Thank you for informing me ;)";
        }
        return reply;
    }
    
    public String proveFact(ArrayList<String> keys,HashMap<String,String> tags){
        if(keys.contains("did"))
            keys.remove("did");
        if(keys.contains("Did"))
            keys.remove("Did");
        if(keys.contains("?"))
            keys.remove("?");
        
        System.out.println("");
        ArrayList<String> ans_cand = new ArrayList<>();
        ArrayList<Integer> candidate = new ArrayList<>();
        for(String ans:answer){
            for(String kys:keys){
                if(ans.contains(kys)){
                    candidate.add(answer.indexOf(ans));
                    String temp = keywords.get(answer.indexOf(ans)).replace(",", " ");
                    ans_cand.add(ans + " " + temp);
                }
            }
        }
        
        for(String answers:ans_cand){
            String[] ans = answers.split(" ");
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(ans));
            System.out.println(answers);
            if(keys.size() < ans.length){
                for(int i = 0;i < keys.size();i++){
                    if(!temp.contains(keys.get(i)))
                        break;
                    else if(temp.contains(keys.get(i)) && i == keys.size() - 1 && temp.contains("not"))
                        return "No";
                    else if(temp.contains(keys.get(i)) && i == keys.size() - 1)
                        return "Yes";
                }
            }
            else{
                for(int i = 0;i < ans.length;i++){
                    if(!temp.contains(keys.get(i)) && !temp.contains("not"))
                        break;
                    else if(temp.contains(keys.get(i)) && i == ans.length - 1 && temp.contains("not"))
                        return "No";
                    else if(temp.contains(keys.get(i)) && i == ans.length - 1)
                        return "Yes";
                }
            }
        }
        return "I am not sure about that";
        
        /*
        System.out.println("Keywords " + candidate.size());
        System.out.println(candidate.toString());
        for(int i = 0; i < candidate.size(); i++){
            System.out.println(candidate.get(i) + " :" + keywords.get(candidate.get(i)));
            String[] temps = keywords.get(candidate.get(i)).split(",");
            ArrayList<String> word_list = new ArrayList<>(Arrays.asList(temps));
            for(String wrd:word_list){
                if(!keys.contains(wrd))
                    break;
                if(keys.contains(wrd) && !tags.get(wrd).equals("TO") && !tags.get(wrd).equals("DT") && !tags.get(wrd).equals("IN") 
                && !wrd.equalsIgnoreCase("is") && !wrd.equalsIgnoreCase("are") && !wrd.equalsIgnoreCase("was") && 
                !wrd.equalsIgnoreCase("were") && !wrd.equalsIgnoreCase("has") && !wrd.equalsIgnoreCase("have") && !wrd.equalsIgnoreCase("had") && !keys.contains("not") ){
                    return "Yes";
                }
            }
            
        }
        return "No";*/
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
                if(keys.contains(words)){
                    ++score;
                }
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
    
    public boolean containsQuestion(ArrayList<String> tokens){
        if(tokens.contains("who") || tokens.contains("Who"))
            return true;
        if(tokens.contains("whom") || tokens.contains("Whom"))
            return true;
        if(tokens.contains("where") || tokens.contains("Where"))
            return true;
        if(tokens.contains("why") || tokens.contains("Why"))
            return true;
        if(tokens.contains("when") || tokens.contains("When"))
            return true;
        if(tokens.contains("how") || tokens.contains("How"))
            return true;
        if(tokens.contains("what") || tokens.contains("What"))
            return true;
        if(tokens.get(0).equalsIgnoreCase("did"))
            return true;
        return false;
    }
}
