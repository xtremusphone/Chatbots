/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nlp.WordTokenizer;

/**
 *
 * @author User
 */

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField inputField;
    
    @FXML
    private TextArea chatWindow;
    
    @FXML
    private Button enterButton;
    
    private String chatLogs = "";
    
    private WordTokenizer tokenizer;
   
    private CoreArchitecture core;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        onEnter();
    }
    
    @FXML
    private void handleEnterButton(ActionEvent event){
        onEnter();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            ObjectInputStream read = new ObjectInputStream(new FileInputStream("Knowledge Base.dt"));
            this.core = (CoreArchitecture) read.readObject();
            System.out.println("Knowledge Base loaded..." + this.core.answer.get(0));
        }
        catch(IOException e){
            System.out.println("No Knowledge Base found...");
            this.core = new CoreArchitecture();
            core.botReply("Your creator is Amir");
            core.botReply("You are Xtremus Bot");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void onEnter(){
        tokenizer = new WordTokenizer();
        chatLogs += "User: "+ inputField.getText() + "\n" + core.botReply(inputField.getText()) + "\n\n";
        SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm:ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());
        chatWindow.setText(chatLogs);
        inputField.setText("");
    }
    
    public void saveChatLog(){
        try {
            SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss_SSS");
            String current_time_str = time_formatter.format(System.currentTimeMillis());
            PrintWriter wrt = new PrintWriter(new File("Log " + current_time_str + ".txt"));
            wrt.println(chatLogs);
            wrt.close();
        } catch (Exception e) {
            System.out.println("Unable to file to path");
        }
    }
    
    public void saveDatabase(){
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Knowledge Base.dt"));
            writer.writeObject(core);
            writer.close();
            System.out.println("Database saved successfully...");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
