package chatbots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import nlp.Chunker;
import nlp.WordTokenizer;

public class InformationDissemination {
    private ArrayList<String> answers;
    private ArrayList<String> keyword;
    private ArrayList<String> q_type;
    //ST indicates that the sentence used special token which will be broken down later when it is keyed in to the KNOWLEDGE BASE
    
    private WordTokenizer tk = new WordTokenizer();
    private HashMap<String,String> tagged;
    private ArrayList<String> sentence;
    private String sentence_build = "";
    
    private String[] special_token = {"#WHILE","#BECAUSE","#FROM","#TO","#IN","#FOR","#AT","#ON","#AFTER","#BEFORE","#BY","#WITH"};
    
    public ArrayList<String> ans = new ArrayList<>();
    public ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> map_q = new ArrayList<>();
    public ArrayList<String> root_sentence = new ArrayList<>();
    
    public InformationDissemination(HashMap<String,String> tag,ArrayList<String> sntc){
        tagged = tag;
        sentence = sntc;
        answers = new ArrayList<>();
        keyword = new ArrayList<>();
        q_type = new ArrayList<>();
        for(String x:sentence){
            sentence_build += x + " ";
        }
    }
    
    public ArrayList<ArrayList<String>> splitSentenceConnector(){
        ArrayList<ArrayList<String>> sentence_list = new ArrayList<>();
        sentence_list.add(sentence);

        ListIterator<ArrayList<String>> iterate = sentence_list.listIterator();
        while(iterate.hasNext()){
            ArrayList<String> current_sentence = iterate.next();
            current_sentence = mergeNNP(current_sentence);
            //System.out.println(current_sentence.toString());
            if(current_sentence.contains("and")){
                int and_index = current_sentence.indexOf("and");
                System.out.println(tagged.toString());
                if(!tagged.get(current_sentence.get(and_index - 1)).equals("NNP") && tagged.get(current_sentence.get(and_index + 1)).equals("NNP")){
                    String first_sentence = "";
                    String second_sentence = "";
                    for(int i = 0; i < and_index;i++){
                        first_sentence += current_sentence.get(i) + " ";
                    }
                    first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                    for(int i = and_index + 1; i < current_sentence.size(); i++){
                        second_sentence += current_sentence.get(i) + " ";
                    }
                    second_sentence = second_sentence.substring(0, second_sentence.length() - 1);
                    iterate.remove();
                    iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                    iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                    while(iterate.hasPrevious()){
                       iterate.previous();
                    }
                    
                }
                else {
                    System.out.println("Entered here");
                    String sentence_before = "";
                    String sentence_after = "";
                    String first_sentence = "";
                    String second_sentence = "";
                    for(int i = 0; i < and_index - 1;i++){
                        if(tagged.get(current_sentence.get(i)).startsWith("PRP")){
                            continue;
                        }
                        sentence_before += current_sentence.get(i) + " ";
                    }
                    
                    for(int i = and_index + 2; i < current_sentence.size();i++){
                        if(tagged.get(current_sentence.get(i)).startsWith("PRP")){
                            continue;
                        }
                        sentence_after += current_sentence.get(i) + " ";
                    }
                    if(!sentence_after.equals(""))
                        sentence_after = sentence_after.substring(0,sentence_after.length() - 1);
                    first_sentence = sentence_before + current_sentence.get(and_index - 1) + " " + sentence_after;
                    second_sentence = sentence_before + current_sentence.get(and_index + 1) + " " + sentence_after;
                    iterate.remove();
                    iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                    iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                    while(iterate.hasPrevious()){
                        iterate.previous();
                    }
                }
            }
            else if(current_sentence.contains("while")){
                int while_index = current_sentence.indexOf("while");
                String tag_next = tagged.get(current_sentence.get(while_index + 1));
                if(tag_next.equalsIgnoreCase("NNP") || tag_next.startsWith("PRP")){
                    String first_sentence = "";
                    String second_sentence = "";
                    for(int i = 0; i < while_index;i++){
                        first_sentence += current_sentence.get(i) + " ";
                    }
                    first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                    for(int i = while_index + 1; i < current_sentence.size(); i++){
                        second_sentence += current_sentence.get(i) + " ";
                    }
                    second_sentence = second_sentence.substring(0, second_sentence.length() - 1);
                    iterate.remove();
                    iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                    iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                    while(iterate.hasPrevious()){
                       iterate.previous();
                    }
                }
                else{
                    String first_sentence = "";
                    String second_sentence = "";
                    for(int i = 0; i < while_index; i++){
                        first_sentence += current_sentence.get(i) + " ";
                    }
                    first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                    for(int i = while_index + 1;i < current_sentence.size();i++){
                        second_sentence += current_sentence.get(i) + " ";
                    }
                    second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                    second_sentence = first_sentence + " #WHILE " + second_sentence;
                    tagged.put("#WHILE", "ST");
                    iterate.remove();
                    iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                    iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                    while(iterate.hasPrevious()){
                       iterate.previous();
                    }
                }
            }
            else if(current_sentence.contains("because")){
                int because_index = current_sentence.indexOf("because");
                String first_sentence = "";
                String second_sentence = "";
                for(int i = 0; i < because_index; i++){
                    first_sentence += current_sentence.get(i) + " ";
                }
                first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                for(int i = because_index + 1;i < current_sentence.size();i++){
                    second_sentence += current_sentence.get(i) + " ";
                }
                second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                second_sentence = first_sentence + " #BECAUSE " + second_sentence;
                tagged.put("#BECAUSE", "ST");
                iterate.remove();
                iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                while(iterate.hasPrevious()){
                   iterate.previous();
                }
            }
            else if(current_sentence.contains("for")){
                int for_index = current_sentence.indexOf("for");
                String first_sentence = "";
                String second_sentence = "";
                for(int i = 0; i < for_index; i++){
                    first_sentence += current_sentence.get(i) + " ";
                }
                first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                for(int i = for_index + 1;i < current_sentence.size();i++){
                    second_sentence += current_sentence.get(i) + " ";
                }
                second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                second_sentence = first_sentence + " #FOR " + second_sentence;
                tagged.put("#FOR", "ST");
                iterate.remove();
                iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                while(iterate.hasPrevious()){
                   iterate.previous();
                }
            }
        }
        System.out.println("Sentence splitted : " + sentence_list.toString());
        return sentence_list;
    }
    
    public void addInformation(ArrayList<ArrayList<String>> sentences,String extras){
        for(ArrayList<String> x:sentences){
            boolean flag_unknown = true;
            String stitch = "";
                for(String wrd:x){
                    stitch += wrd + " ";
                }
            stitch = stitch.substring(0,stitch.length() - 1);
            
            if(x.contains("#WHILE")){
                int while_index = x.indexOf("#WHILE");
                String temp = "";
                for(int i = while_index + 1; i < x.size();i++){
                    temp += x.get(i) + " ";
                }
                temp = temp.substring(0,temp.length() - 1);
                String temp_key = "";
                for(int i = 0; i < while_index;i++){
                    temp_key += x.get(i) + ",";
                }
                temp_key = temp_key.substring(0,temp_key.length()- 1);
                ans.add(temp);
                keys.add(temp_key + extras);
                map_q.add("What");
                root_sentence.add(stitch);
                
                String temp_keys2 = "";
                ArrayList<Integer> ans_pos = new ArrayList<>();
                
                String temp_ans2 = "";
                boolean verb_flag = false;
                for(int i = 0; i < x.indexOf("#WHILE");i++){
                    if(tagged.get(x.get(i)).startsWith("VB") && !x.get(i).equalsIgnoreCase("is") && !x.get(i).equalsIgnoreCase("are") && !x.get(i).equalsIgnoreCase("was") && !x.get(i).equalsIgnoreCase("were") && !x.get(i).equalsIgnoreCase("has") && !x.get(i).equalsIgnoreCase("have") && !x.get(i).equalsIgnoreCase("had")){
                        verb_flag = true;
                        ans_pos.add(i);
                        temp_ans2 += x.get(i) + " ";
                    }
                    else if(verb_flag == true){
                        ans_pos.add(i);
                        temp_ans2 += x.get(i) + " ";
                    }
                }
                
                temp_ans2 = temp_ans2.substring(0,temp_ans2.length() - 1);
                
                for(int i = 0; i < x.size();i++){
                    if(!ans_pos.contains(i))
                        temp_keys2 += x.get(i) + ",";
                }
                
                temp_keys2 = temp_keys2.substring(0,temp_keys2.length() - 1);
                ans.add(temp_ans2);
                keys.add(temp_keys2 + extras);
                map_q.add("What");
                root_sentence.add(stitch);
                continue;
            }
            
            if(x.contains("#BECAUSE")){
                int because_index = x.indexOf("#BECAUSE");
                String temp = "because ";
                for(int i = because_index + 1; i < x.size();i++){
                    temp += x.get(i) + " ";
                }
                temp = temp.substring(0,temp.length() - 1);
                String temp_key = "";
                for(int i = 0; i < because_index;i++){
                    temp_key += x.get(i) + ",";
                }
                temp_key = temp_key.substring(0,temp_key.length()- 1);
                ans.add(temp);
                keys.add(temp_key + extras);
                map_q.add("Why");
                root_sentence.add(stitch);
                flag_unknown = false;
                
                ArrayList<ArrayList<String>> tempArr = new ArrayList<>();
                ArrayList<String> tempStr = new ArrayList<>();
                for(int i = because_index + 1; i <x.size();i++){
                    tempStr.add(x.get(i));
                }
                tempArr.add(tempStr);
                addInformation(tempArr, "," + temp_key);
                
                continue;
            }
            
            if(x.contains("#FOR")){
                int while_index = x.indexOf("#FOR");
                String temp = "for ";
                for(int i = while_index + 1; i < x.size();i++){
                    temp += x.get(i) + " ";
                }
                temp = temp.substring(0,temp.length() - 1);
                String temp_key = "";
                for(int i = 0; i < while_index;i++){
                    temp_key += x.get(i) + ",";
                }
                temp_key = temp_key.substring(0,temp_key.length()- 1);
                ans.add(temp);
                keys.add(temp_key + extras);
                map_q.add("Why");
                root_sentence.add(stitch);
                flag_unknown = false;
                
                ArrayList<ArrayList<String>> tempArr = new ArrayList<>();
                ArrayList<String> tempStr = new ArrayList<>();
                for(int i = while_index + 1; i <x.size();i++){
                    tempStr.add(x.get(i));
                }
                tempArr.add(tempStr);
                addInformation(tempArr, "," + temp_key);
                
                continue;
            }
            
            if(x.contains("from") || x.contains("to") || x.contains("in")){
                String contains;
                int contain_index;
                if(x.contains("from")){
                    contains = "from";
                    contain_index = x.indexOf("from");
                }
                else if(x.contains("to")){
                    contains = "to";
                    contain_index = x.indexOf("to");
                }
                else{
                    contains = "in";
                    contain_index = x.indexOf("in");
                }
                String first_sentence = "";
                String second_sentence = "";
                for(int i = 0; i < contain_index; i++){
                    first_sentence += x.get(i) + ",";
                }
                first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                for(int i = contain_index ;i < x.size();i++){
                    second_sentence += x.get(i) + " ";
                }
                second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                ans.add(second_sentence);
                keys.add(first_sentence + extras);
                map_q.add("Where");
                root_sentence.add(stitch);
                flag_unknown = false;
            }
            
            if(x.contains("at") || x.contains("on") || x.contains("after") || x.contains("before")){
                String contains;
                int contain_index;
                if(x.contains("at")){
                    contains = "at";
                    contain_index = x.indexOf("at");
                }
                else if(x.contains("on")){
                    contains = "on";
                    contain_index = x.indexOf("on");
                }
                else if(x.contains("after")){
                    contains = "after";
                    contain_index = x.indexOf("after");
                }
                else{
                    contains = "after";
                    contain_index = x.indexOf("after");
                }
                
                String first_sentence = "";
                String second_sentence = "";
                for(int i = 0; i < contain_index; i++){
                    first_sentence += x.get(i) + ",";
                }
                first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                for(int i = contain_index;i < x.size();i++){
                    second_sentence += x.get(i) + " ";
                }
                second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                ans.add(second_sentence);
                keys.add(first_sentence + extras);
            map_q.add("When");
                root_sentence.add(stitch);
                flag_unknown = false;
            }
            
            if(x.contains("by") || x.contains("with")){
                String contains;
                int contain_index;
                if(x.contains("by")){
                    contains = "by";
                    contain_index = x.indexOf("by");
                }
                else{
                    contains = "with";
                    contain_index = x.indexOf("with");
                }
                
                String first_sentence = "";
                String second_sentence = "";
                for(int i = 0; i < contain_index; i++){
                    first_sentence += x.get(i) + ",";
                }
                first_sentence = first_sentence.substring(0,first_sentence.length() - 1);
                for(int i = contain_index + 1;i < x.size();i++){
                    second_sentence += x.get(i) + " ";
                }
                second_sentence = second_sentence.substring(0,second_sentence.length() - 1);
                ans.add(second_sentence);
                keys.add(first_sentence + extras);
                map_q.add("How");
                root_sentence.add(stitch);
                flag_unknown = false;
            }
            
            ArrayList<String> person_list = new ArrayList<>();
            for(String person:x){
                if(tagged.get(person).startsWith("NN")){
                    person_list.add(person);
                }
            }
            for(String person:person_list){
                int person_index = x.indexOf(person);
                String temp = "";
                for(int i = 0; i < x.size();i++){
                    if(i != person_index)
                        temp += x.get(i) + ",";
                }
                temp = temp.substring(0,temp.length() - 1);
                ans.add(person);
                keys.add(temp + extras);
                map_q.add("Who");
                root_sentence.add(stitch);
                ans.add(person);
                keys.add(temp + extras);
                map_q.add("What");
                root_sentence.add(stitch);
                temp = temp.replace(",", " ");
                ans.add(temp);
                keys.add(person + extras);
                map_q.add("What");
                root_sentence.add(stitch);
                flag_unknown = false;
            }
        }
    }
    
    public ArrayList<ArrayList<String>> checkOverlap(ArrayList<ArrayList<String>> sentences){
        ArrayList<ArrayList<String>> remove = new ArrayList<>();
        for(ArrayList<String> sent:sentences){
            for(ArrayList<String> sent_2:sentences){
                if(sent.toString().equalsIgnoreCase(sent_2.toString())){
                    remove.add(sent_2);
                }
            }
        }
        for(ArrayList<String> temp:remove ){
            sentences.remove(temp);
        }
        return sentences;
    }
    
    private ArrayList<String> mergeNNP(ArrayList<String> sentence){
        Chunker ch = new Chunker();
        ArrayList<String> NNPs = (ArrayList<String>) ch.getNouns(tagged, sentence);
        for(String person: NNPs){
            if(sentence.contains(person)){
                continue;
            }
            String[] word = person.split(" ");
            int pointer = sentence.indexOf(word[0]);
            for(String x: word){
                sentence.remove(x);
            }
            tagged.put(person, "NNP");
            sentence.add(pointer,person);
        }
        return sentence;
    }

    private ArrayList<String> mergeNounPhrase(ArrayList<String> sentence){
        Chunker ch = new Chunker();
        ArrayList<String> NPh = (ArrayList<String>) ch.getNounPhrase(tagged, sentence);
        for(String phrase:NPh){
            if(sentence.contains(phrase)){
                continue;
            }
            String[] word = phrase.split(" ");
            int pointer = sentence.indexOf(word[0]);
            for(String x: word){
                sentence.remove(x);
            }
            tagged.put(phrase, "NNP");
            sentence.add(pointer,phrase);
        }
        return sentence;
    }
}
