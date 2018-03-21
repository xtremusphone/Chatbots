package chatbots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import nlp.Chunker;
import nlp.WordTokenizer;

public class InformationDissemination {
    
    //ST indicates that the sentence used special token which will be broken down later when it is keyed in to the KNOWLEDGE BASE
    
    private WordTokenizer tk = new WordTokenizer();
    private HashMap<String,String> tagged;
    private ArrayList<String> sentence;
    
    private String[] special_token = {"#WHILE","#BECAUSE","#FROM","#TO","#IN","#FOR","#AT","#ON","#AFTER","#BEFORE","#BY","#WITH"};
    
    public ArrayList<String> ans = new ArrayList<>();
    public ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> map_q = new ArrayList<>();
    
    public InformationDissemination(HashMap<String,String> tag,ArrayList<String> sntc){
        tagged = tag;
        sentence = sntc;
    }
    
    public ArrayList<ArrayList<String>> splitSentenceConnector(){
        ArrayList<ArrayList<String>> sentence_list = new ArrayList<>();
        sentence_list.add(sentence);

        ListIterator<ArrayList<String>> iterate = sentence_list.listIterator();
        while(iterate.hasNext()){
            ArrayList<String> current_sentence = iterate.next();
            current_sentence = mergeNNP(current_sentence);

            if(current_sentence.contains("and")){
                int and_index = current_sentence.indexOf("and");
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
                System.out.println(first_sentence);
                System.out.println(second_sentence);
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
                System.out.println(first_sentence);
                System.out.println(second_sentence);
                iterate.add((ArrayList<String>) tk.tokenizer(first_sentence));
                iterate.add((ArrayList<String>) tk.tokenizer(second_sentence));
                while(iterate.hasPrevious()){
                   iterate.previous();
                }
            }
        }
        return sentence_list;
    }
    
    public void addInformation(ArrayList<ArrayList<String>> sentences){
        //sentences = checkOverlap(sentences);
        for(ArrayList<String> x:sentences){
            System.out.println(x.toString());
            if(x.contains("#WHILE")){
                int while_index = x.indexOf("#WHILE");
                String temp = "while ";
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
                keys.add(temp_key);
                map_q.add("What");
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
                keys.add(temp_key);
                map_q.add("Why");
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
                keys.add(temp_key);
                map_q.add("Why");
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
                keys.add(first_sentence);
                map_q.add("Where");
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
                keys.add(first_sentence);
                map_q.add("When");
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
                keys.add(first_sentence);
                map_q.add("How");
            }
            
            ArrayList<String> person_list = new ArrayList<>();
            for(String person:x){
                if(tagged.get(person).startsWith("NNP")){
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
                keys.add(temp);
                map_q.add("Who");
                ans.add(person);
                keys.add(temp);
                map_q.add("What");
                temp = temp.replace(",", " ");
                ans.add(temp);
                keys.add(person);
                map_q.add("What");
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
}
