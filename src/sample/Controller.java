package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import static java.lang.StrictMath.abs;

public class Controller {

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







    public static void init(char[][] tab, int height, int size){

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

    public static char[] Szyfrowanie(char[] password, int key){
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



}
