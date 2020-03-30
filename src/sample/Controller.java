package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.StrictMath.abs;
import static javafx.scene.paint.Color.RED;

public class Controller implements Initializable {

    public Label result_label;
    public Label result2_label;
    public Label klucz_label;
    public Label slowo_label;
    public Label warning;

    public RadioButton radio_railfence;
    public RadioButton radio_macierzeA;
    public RadioButton radio_macierzeB;
    public RadioButton cezar;
    public RadioButton radio_macierzeC;
    public RadioButton radio_vigener;

    public TextField klucz_tf;
    public TextField slowo_tf;

    public Button end_button;
    public Button randomButton;

    ToggleGroup toggleGroup = new ToggleGroup();
    ArrayList<String> words;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        radio_railfence.setToggleGroup(toggleGroup);
        radio_macierzeB.setToggleGroup(toggleGroup);
        radio_macierzeA.setToggleGroup(toggleGroup);
        radio_macierzeC.setToggleGroup(toggleGroup);
        cezar.setToggleGroup(toggleGroup);
        radio_vigener.setToggleGroup(toggleGroup);

        words = new ArrayList<>();
        File file = new File("src/sample/words.txt");
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()){
                words.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void endButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) end_button.getScene().getWindow();
        stage.close();
    }

    public void randomButtonPressed(ActionEvent actionEvent) {
            Random variable = new Random();
            int k = variable.nextInt(words.size());

            slowo_tf.setText(words.get(k));
    }

    public void szyfrujButtonClicked(ActionEvent actionEvent) {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();

        if (klucz_tf.getText().isEmpty() || slowo_tf.getText().isEmpty()) {
            warning.setText("Pola 'Klucz' oraz 'Słowo' nie mogą być puste ");
        } else {

            /*Szyfrowanie metodą rail fence*/
            if (radio_railfence.equals(selected)) {
                String word = slowo_tf.getText();
                int key = Integer.parseInt(klucz_tf.getText());

                char[] output = SzyfrowanieRailFence(word.toCharArray(), key);
                result2_label.setText(String.valueOf(output));

            } /*Szyfrowanie metodą macierzową A*/ else if (radio_macierzeA.equals(selected)) {
                String word= slowo_tf.getText();
                String key= String.valueOf((klucz_tf.getText()));
                String result = SzyfrowanieMacierzoweA(word,key);
                result2_label.setText(result);


            } /*Szyfrowanie metodą macierzową B*/ else if (radio_macierzeB.equals(selected)) {

            } /*Szyfr Cezara*/ else if(cezar.equals(selected)){
                String word = slowo_tf.getText();
                int key = Integer.parseInt(klucz_tf.getText());
                String result = Cezar(word,key,0);

                result2_label.setText(result);
            } /*Szyfrowanie metodą macierzową C*/ else if(radio_macierzeC.equals(selected)){

            } /*Szyfrowanie Vigenere'a*/ else if(radio_vigener.equals(selected)){

            }
            else {
                warning.setText("Wybierz metodę szyfrowania");
            }

        }
    }

    public void deszyfrujButtonClicked(ActionEvent actionEvent) {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();

        if (klucz_tf.getText().isEmpty() || slowo_tf.getText().isEmpty()) {
            warning.setText("Pola 'Klucz' oraz 'Słowo' nie mogą być puste ");
        } else {

            /*Deszyfrowanie metodą rail fence*/
            if (radio_railfence.equals(selected)) {
                String word = slowo_tf.getText();
                int key = Integer.parseInt(klucz_tf.getText());

                char[] result = DeszyfrowanieRailFence(word.toCharArray(), key);
                result2_label.setText(String.valueOf(result));

            } /*Deszyfrowanie metodą macierzową A*/ else if (radio_macierzeA.equals(selected)) {

            } /*Deszyfrowanie metodą macierzową B*/ else if (radio_macierzeB.equals(selected)) {

            } /*Deszyfrowanie Cezara*/ else if(cezar.equals(selected)){
                String word = slowo_tf.getText();
                int key = Integer.parseInt(klucz_tf.getText());
                String result = Cezar(word,key,1);

                result2_label.setText(result);
            } /*Deszyfrowanie metodą macierzową C*/ else if(radio_macierzeC.equals(selected)) {

            } /*Deszyfrowanie metodą vigener'a*/ else if(radio_vigener.equals(selected)){

            }
            else {
                warning.setText("Wybierz metodę deszyfrowania");
            }
        }
    }


    /* Rail fence poniżej */
    public static void init(char[][] tab, int height,int size){

        for(int y=0;y<height;y++){
            for(int x=0;x<size;x++) {
                tab[y][x] = '0';
            }
        }

    }

    public static char[] result(char[]output, int size, char[][] password,int key){
        int line=0;

        for(int i=0;i<key;i++)
            for(int x=0;x<size;x++){
                if(password[i][x] != '0'){
                    output[line] = password[i][x];
                    line++;
                }
            }

        return output;
    }
    public static String SzyfrowanieMacierzoweA(String password,String key) {

        int[] keyArr = Stream.of(key.replaceAll("-", "").split("")).mapToInt(Integer::parseInt).toArray();
        double cols = keyArr.length;
        int i = 0;
        int j = 0;
        char[] e;
        e = password.toCharArray();
        double rows = Math.ceil(password.length() / cols);
        char[][] cipher = new char[(int) rows][(int) cols];
        init(cipher, (int) rows, (int) cols);
        for (i = 0; i < e.length; i++) {
            cipher[j][(int) (i % rows)] = e[i];
            if (i % cols == cols - 1) {
                j++;
            }
        }
        StringBuilder answer = new StringBuilder();
        for (i = 0; i < rows; i++) {
            for (int k : keyArr) {
                char next = cipher[i][k - 1];
                if (next != '0') {
                    answer.append(cipher[i][k - 1]);
                }

            }
        }
            return answer.toString();
    }




    public static char[] SzyfrowanieRailFence(char[] password, int key){
        char[][] szyfr = new char[key][password.length];
        char[] output = new char[password.length];
        int new_key = 2*key - 2;
        int j = 0, height = 1;

        init(szyfr, key,password.length);

        for(int i=0;i<password.length;i++){
            szyfr[abs(j)][i] = password[i];

            if(i%new_key == 0){
                height *= 1;
            }
            if((i+key-1)%new_key == 0){
                height *= -1;
            }
            j += height;
        }

        result(output, password.length, szyfr, key);

        return output;
    }

    public static char[] DeszyfrowanieRailFence(char[] password, int key){
        char[][] szyfr = new char[key][password.length];
        char[] output = new char[password.length];
        int new_key = 2*key - 2;
        int j = 0, height = -1, n=0;

        init(szyfr, key,password.length);

      for(int i=0;i<password.length;i++){
          szyfr[abs(j)][i] = '*';
          if(i%new_key == 0 || (i+key-1)%new_key == 0)
               height *= -1;

          j += height;
      }

      for(int y=0;y<key;y++)
          for(int x=0;x<password.length;x++){
              if(szyfr[y][x] == '*'){
                  szyfr[y][x] = password[n];
                  n++;
              }
          }

      j=0;
      height = -1;

        for(int i=0;i<password.length;i++){
             output[i] =szyfr[abs(j)][i];

            if(i%new_key == 0){
                height *= 1;
            }
            if((i+key-1)%new_key == 0){
                height *= -1;
            }
            j += height;
        }

        return output;
    }


    /* Szyfr Cezara*/

    public static String Cezar(String password, int key,int param){
       StringBuilder sb = new StringBuilder();

       switch (param){
           case 0:
               for(int i=0;i<password.length();i++){
                   sb.append(szyfrujZnak(password.substring(i,i+1),key));
               }
               break;
           case 1:
               for(int i=0;i<password.length();i++){
                   sb.append(deszyfrujZnak(password.substring(i,i+1),key));
               }
               break;
       }

        return sb.toString();
    }

    public static String szyfrujZnak(String s, int key){
        int index = 0;
        String[] alphabet = {"A","B", "C",  "D", "E",  "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for(int i=0;i<alphabet.length;i++){
            if(s.equalsIgnoreCase(alphabet[i])) {
                index = (i+key)%(alphabet.length);
                break;
            }
            if(s.equals(" ") || s.equals(",")){
                return s;
            }
        }

        return alphabet[index];
    }

    public static String deszyfrujZnak(String s, int key){
        int index = 0;
        String[] alphabet = {"A","B", "C",  "D", "E",  "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for(int i=0;i<alphabet.length;i++){

            if(s.equalsIgnoreCase(alphabet[i])) {
                index = i - key;

                if(index <0){
                    index = alphabet.length + index;
                }
                break;
            }
            if(s.equals(" ") || s.equals(",")){
                return s;
            }
        }

        return alphabet[index];
    }
}
