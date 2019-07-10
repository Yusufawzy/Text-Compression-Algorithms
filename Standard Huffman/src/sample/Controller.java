package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import static sample.Huffman.*;

public class Controller {


    @FXML
    private TextArea txtEntry;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    String txt, table;

    @FXML
    void compClick(ActionEvent event) {
        Compressed comp = Compress(txtEntry.getText()); //Get's the code from textEntry and compress it
        txt = comp.compressed;
        table = comp.table;
        lbl1.setText("The Compressed Code is \""+txt + "\"");
    }

    @FXML
    void deCompClick(ActionEvent event) {
        //    public static String Decompress(String comp, String table)
        lbl2.setText("The Decompressed Text is \"" +Decompress(txt, table) + "\"");
    }

}
