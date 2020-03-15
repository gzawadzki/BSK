package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.StrictMath.abs;
import static javafx.scene.paint.Color.RED;

public class Controller implements Initializable {

    public Label result_label;
    public Label result2_label;
    public RadioButton radio_railfence;
    public RadioButton radio_macierzeA;
    public RadioButton radio_macierzeB;
    public TextField klucz_tf;
    public Label klucz_label;
    public Label slowo_label;
    public TextField slowo_tf;
    public Button end_button;
    public Button randomButton;
    public Label warning;

    ToggleGroup toggleGroup = new ToggleGroup();
    String[] words;

    public void endButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) end_button.getScene().getWindow();
        stage.close();
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

            } /*Szyfrowanie metodą macierzową B*/ else if (radio_macierzeB.equals(selected)) {

            } else {
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

                String result = DeszyfrowanieRailFence(word.toCharArray(), key);
                result2_label.setText(result);

            } /*Deszyfrowanie metodą macierzową A*/ else if (radio_macierzeA.equals(selected)) {

            } /*Deszyfrowanie metodą macierzową B*/ else if (radio_macierzeB.equals(selected)) {

            } else {
                warning.setText("Wybierz metodę deszyfrowania");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        radio_railfence.setToggleGroup(toggleGroup);
        radio_macierzeB.setToggleGroup(toggleGroup);
        radio_macierzeA.setToggleGroup(toggleGroup);

    }

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

    public static String DeszyfrowanieRailFence(char[] password, int key){
        char[][] szyfr = new char[key][password.length];
        StringBuilder output = new StringBuilder(" ");
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
          if(i%new_key == 0 || (i+key-1)%new_key == 0)
              height *= -1;

          String abc = String.valueOf(szyfr[j][i]);
          output.append(abc);
      }

        return output.toString();
    }

    public void randomButtonPressed(ActionEvent actionEvent) {
    }
}
