
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Vector;
import java.nio.file.Files;

public class LZW {
    void preprocess(String a){

    }
    static public String compress(String a) {
        a += " ";
        Vector <String> dict = new Vector <>();
        Vector <Integer> code = new Vector <>();
        var ascii= " !\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\n";
        for (var i = 0; i < ascii.length();i++){
            dict.add(Character.toString(ascii.charAt(i)));
        }
        a=a.replace("\n","");
      for (int i = 0; i < a.length(); ) {
            int idx = 0;
            int next = i + 1;
            String temp = String.valueOf(a.charAt(i));
            while (true) {
                if (dict.contains(temp)) {
                    idx = dict.indexOf(temp); //put the index of the found word into the idx variable and check if we added another char
                    temp += a.charAt(next);
                    next++;
                } else {
                    code.add(idx);
                    i += temp.length() - 1;
                    break;
                }
                if (next == a.length()) {
                    code.add(idx);
                    dict.add(temp);
                    i++;
                    break;
                }
            }
            if (next == a.length()) break;
        }
        String res = "";
        for (Integer integer : code) {
            var x = integer.toString();
            while (x.length() < 3) {
                x = '0' + x;
            }
            res += x;
        }
        return res;
    }

    static public String deCompress(String a) {
        Vector <String> dict = new Vector <>();
        String res = "";

        var ascii= " !\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        for (var i = 0; i < ascii.length();i++){
            dict.add(Character.toString(ascii.charAt(i)));
        }
        String prev = dict.get(Integer.parseInt(a.substring(0, 3)));
        res += prev;
        for (int i = 3; i < a.length(); i += 3) {
            int idx = Integer.parseInt(a.substring(i, i + 3));
            dict.add(prev);
            String current = dict.get(idx);
            prev += current.charAt(0);
            dict.removeElementAt(dict.size() - 1);
            dict.add(prev);
            prev = current;
            res += dict.elementAt(idx);
            dict.add(res);

        }

        return res;
    }

    public static void main(String[] args) {
        while (true){
            try {
                System.out.println("Enter the name of the file");
                var input = new Scanner(System.in).next();
                String text = Files.readString(Path.of(input+".txt"));
                System.out.println(text);
                var Compressed = compress(text);
                System.out.println("The Compressed is "+Compressed);
                var outC = new PrintWriter(new FileWriter("compressed.txt"));
                outC.println(Compressed);
                var DeCompressed = deCompress(Compressed);
                System.out.println("The Decompressed Text is \""+ DeCompressed+"\"");
                var outD = new PrintWriter(new FileWriter("decompressed.txt"));
                outD.println(DeCompressed);
                outD.close();
                outC.close();
            }
            catch (IOException e){
                System.out.println("We can't open the file at all");
            }

        }}
}


