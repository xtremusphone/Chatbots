package chatbots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nlp.ViterbiAlgorithm;
import nlp.WordTokenizer;
import nlp.Chunker;

public class CoreArchitecture implements Serializable{
    
    private ViterbiAlgorithm va = new ViterbiAlgorithm();
    public ArrayList<String> answer = new ArrayList<>();
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> mapped_q = new ArrayList<>();
    private ArrayList<String> root_sent = new ArrayList<>();
    private ViterbiAlgorithm tmp_tag = new ViterbiAlgorithm();
    
    private TFIDF tfid = new TFIDF();
    private WordTokenizer w_tokenizer = new WordTokenizer();
    
    public CoreArchitecture(){
        
    }
    
    public String botReply(String input){
        String reply = "";
        HashMap<String,String> tagged = va.getPOSTagging(input);
        WordTokenizer wt = new WordTokenizer();
        Chunker ch = new Chunker();
        List<String> tokenized = wt.tokenizer(input);
        QuestionClassification qc = new QuestionClassification();
        if(tokenized.contains("?") || containsQuestion((ArrayList<String>) tokenized)){
            if(tokenized.contains("and") && tagged.get(tokenized.get(tokenized.indexOf("and") + 1)).startsWith("W")){
            int and_index = tokenized.indexOf("and");
            String temp1 = "";
            for(int i = 0; i < and_index;i++){
                temp1 += tokenized.get(i) + " ";
            }
            temp1 = temp1.substring(0,temp1.length() - 1);
            System.out.println("First sent:" + temp1);
            String ans1 = botReply(temp1).replace("Bot: ", " ");
            String temp2 = "";
            for(int i = and_index + 1; i < tokenized.size();i++){
                temp2 += tokenized.get(i) + " ";
            }
            temp2 = temp2.substring(0,temp2.length() - 1);
            System.out.println("Second sent:" + temp2);
            String ans2 = botReply(temp2).replace("Bot: ", " ");
            return "Bot: " + ans1 + " and " + ans2;
            }
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
            else if(qc.isWhat(tagged, (ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("What", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                
                if(!checkNegative((ArrayList<String>) tokenized)){
                    for(String key_answer:temp.keySet()){
                        if(temp.get(key_answer) > score){
                            System.out.println(answer_p);
                            answer_p = key_answer;
                            score = temp.get(key_answer);
                        }
                        else if(temp.get(key_answer) == score){
                                    //ArrayList<String> sample = (ArrayList<String>) wt.tokenizer(answer_p);
                                    boolean check_verbs = false;
                                    HashMap<String,String> checker = va.getPOSTagging(key_answer);
                                    for(String tsting:checker.keySet()){
                                        System.out.println(tsting);
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
                else{
                    for(String key_answer:temp.keySet()){
                        if(temp.get(key_answer) < score){
                            System.out.println(answer_p);
                            answer_p = key_answer;
                            score = temp.get(key_answer);
                        }
                        else if(temp.get(key_answer) == score){
                                    //ArrayList<String> sample = (ArrayList<String>) wt.tokenizer(answer_p);
                                    boolean check_verbs = false;
                                    HashMap<String,String> checker = va.getPOSTagging(key_answer);
                                    for(String tsting:checker.keySet()){
                                        System.out.println(tsting);
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
                
                
                reply += answer_p;
                if(answer_p.equals(""))
                    reply += "I am not sure about that";
                }
                System.out.println(temp.toString());
            }
            else if(qc.isWhen(tagged,(ArrayList<String>) tokenized)){
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
            else if(qc.isWhere(tagged, (ArrayList<String>) tokenized)){
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
            else if(qc.isWhy(tagged,(ArrayList<String>) tokenized)){
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
            else if(qc.isWho(tagged,(ArrayList<String>) tokenized)){
                HashMap<String,Double> temp = getReply("Who", (ArrayList<String>) tokenized);
                if(temp.isEmpty())
                    reply += "I am sorry but I don't recall anything about that in my mind.";
                else{
                String answer_p = "";
                double score = 0;
                //need to check if there is NNP and Verb
                for(String key_answer:temp.keySet()){
                    if(temp.get(key_answer) > score){
                        String check = root_sent.get(answer.indexOf(key_answer));
                        HashMap<String,String> tg = va.getPOSTagging(check);
                        if(tg.containsValue("NNP")){
                            for(String ck:w_tokenizer.tokenizer(check)){
                                if(tg.get(ck).equals("NNP") && ck.equals(key_answer)){
                                    answer_p = key_answer;
                                    score = temp.get(key_answer);
                                    break;
                                }
                            }
                        }
                        else{
                            answer_p = key_answer;
                            score = temp.get(key_answer);
                        }
                    }
                    else if(temp.get(key_answer) == score){
                        String check = root_sent.get(answer.indexOf(key_answer));
                        HashMap<String,String> tg = va.getPOSTagging(check);
                        if(tg.containsValue("NNP")){
                            for(String ck:w_tokenizer.tokenizer(check)){
                                if(tg.get(ck).equals("NNP") && !answer_p.contains(key_answer) && ck.equals(key_answer)){
                                    answer_p += " and " + key_answer;
                                    score = temp.get(key_answer);
                                    break;
                                }
                            }
                        }
                        //answer_p += " and " + key_answer;
                    }
                }
                reply += answer_p;
                }
                System.out.println(temp.toString());
            }
            else if(qc.isDid(tagged, (ArrayList<String>) tokenized) || tokenized.get(0).equalsIgnoreCase("Is") || tokenized.get(tokenized.size() - 1).equals("?")){
                reply += proveFact((ArrayList<String>) tokenized,tagged);
            }
        }
        else{
            for(String word : tokenized){
            reply += word + " [" + tagged.get(word) + "], ";
            }
            reply += "\n";
            InformationDissemination id = new InformationDissemination(tagged, (ArrayList<String>) tokenized);
            id.addInformation(id.splitSentenceConnector(),"");
            id.ans.stream().forEach((x) -> {
                answer.add(x);
            });
            id.keys.stream().forEach((x) -> {
                
                keywords.add(x);
                System.out.println(answer.get(keywords.indexOf(x)) + " :: "+ x );
            });
            id.map_q.stream().forEach((x) -> {
                mapped_q.add(x);
                System.out.println(x);
            });
            id.root_sentence.stream().forEach((x) -> {
                root_sent.add(x);
                ArrayList<String> temp = (ArrayList<String>) wt.tokenizer(x);
                tfid.sentence_list.add(temp);
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
        if(keys.get(0).equalsIgnoreCase("Is"))
            keys.remove(0);
        
        
        
        System.out.println("");
        ArrayList<String> ans_cand = new ArrayList<>();
        ArrayList<Integer> candidate = new ArrayList<>();
        for(String ans:root_sent){
            for(String kys:keys){
                if(ans.contains(kys)){
                    ans_cand.add(ans);
                }
            }
        }
        
        boolean ans_flag = false;
        String ans_buffer = "";
        for(String answers:ans_cand){
            String[] ans = answers.split(" ");
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(ans));
            System.out.println(answers);
            if(keys.size() < ans.length){
                
                for(int i = 0;i < keys.size();i++){
                    if(!temp.contains(keys.get(i)))
                        break;
                    else if(temp.contains(keys.get(i)) && i == keys.size() - 1 && checkNegative(temp))
                        return "No";
                    else if(temp.contains(keys.get(i)) && i == keys.size() - 1){
                        if(tags.containsValue("NNP") || tags.containsValue("NN")){
                            HashMap<String,String> ans_tags = tmp_tag.getPOSTagging(answers);
                            for(String ans_keys:temp){
                                if(ans_tags.get(ans_keys).startsWith("NN")){
                                    for(String q_keys:keys){
                                        if(tags.get(q_keys).startsWith("NN")){
                                            if(ans_keys.equalsIgnoreCase(q_keys)){
                                                System.out.println("definit:" + temp + " " + keys);
                                                System.out.println("Fired from 1st");
                                                return "Yes";
                                            }
                                            else{
                                                ans_flag = true;
                                                ans_buffer = "No";
                                                break;
                                            }
                                        }
                                    }
                                }
                                return "No";
                            }
                        }
                        else
                            return "Yes";
                    }
                }
            }
            else{
                for(int i = 0;i < temp.size();i++){
                    if(!temp.contains(keys.get(i)))
                        break;
                    else if(temp.contains(keys.get(i)) && i == ans.length - 1 && checkNegative(temp))
                        return "No";
                    else if(temp.contains(keys.get(i)) && i == keys.size() - 1){
                        if(tags.containsValue("NNP") || tags.containsValue("NN")){
                            
                            HashMap<String,String> ans_tags = tmp_tag.getPOSTagging(answers);
                            for(String ans_keys:temp){
                                if(ans_tags.get(ans_keys).startsWith("NN")){
                                    for(String q_keys:keys){
                                        if(tags.get(q_keys).startsWith("NN")){
                                            if(ans_keys.equalsIgnoreCase(q_keys)){
                                                System.out.println("Fired from 2nd");
                                                return "Yes";
                                            }
                                            else{
                                                ans_flag = true;
                                                ans_buffer = "No";
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        else
                            return "Yes";
                    }
                }
            }
        }
        if(ans_flag)
            return ans_buffer;
        return "I am not sure about that";
    }
    
    //Deprecated code
    /*
    public HashMap<String,Double> getReplyTF(String question_type,ArrayList<String> keys){
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
    }*/
    
    public HashMap<String,Double> getReply(String question_type,ArrayList<String> keys){
        HashMap<String,Double> max = new HashMap();
        ArrayList<Integer> candidate = new ArrayList<>();
        
        for(int i = 0; i < mapped_q.size();i++){
            if(mapped_q.get(i).equals(question_type)){
                System.out.println(i);
                candidate.add(i);
            }
        }
        
        for(Integer x:candidate){
            String[] temp_str = keywords.get(x).split(",");
            ArrayList<String> word_list = new ArrayList<>(Arrays.asList(temp_str));
            
            double score = 0;
            for(String tmp_word:word_list){              
                for(String word:keys){
                    if(tmp_word.equalsIgnoreCase(word)){
                        if(!checkNegative(word_list)){
                            System.out.println(word + " adding " + tfid.getCumulativeTFIDF(word));
                            score += tfid.getCumulativeTFIDF(word);
                            break;
                        }
                        else{
                            System.out.println(word + " removing " + tfid.getCumulativeTFIDF(word));
                            score -= tfid.getCumulativeTFIDF(word);
                            break;
                        }
                    }
                }
            }
            
            
            if(question_type.equalsIgnoreCase("What")){
                ArrayList<String> more_data = (ArrayList<String>) w_tokenizer.tokenizer(root_sent.get(x));
                for(String tmp_word:more_data){              
                    for(String word:keys){
                        if(tmp_word.equalsIgnoreCase(word)){
                            if(!checkNegative(more_data)){
                                score += tfid.getCumulativeTFIDF(word);
                                break;
                            }
                            else{
                                System.out.println(word + " removing " + tfid.getCumulativeTFIDF(word));
                                score -= tfid.getCumulativeTFIDF(word);
                            break;
                            }
                        }
                    }
                }
            }
            
            if(score != 0){
                max.put(answer.get(x), score / keywords.get(x).length());
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
        if(tokens.get(tokens.size() - 1).equals("?"))
            return true;
        return false;
    }
    
    public boolean checkNegative (ArrayList<String> sentence){
        if(sentence.contains("not") || sentence.contains("Not")){
            return true;
        }
        return false;
    }
}
