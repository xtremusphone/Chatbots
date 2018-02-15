/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbots;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
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
        // TODO
    }

    private void onEnter(){
        tokenizer = new WordTokenizer();
        SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm:ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());
        chatLogs += "[" + current_time_str + "] User: "+ inputField.getText() + "\n" + tokenizer.tokenizer(inputField.getText()) + "\n\n";
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
}
