import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Vector;
class struct{
    int idx;char c;
    struct(int idx,char c){
        this.idx=idx;
        this.c=c;
    }
    void print(){
        System.out.println(idx+ " "+c);
    }
}
public class LZ78 {

    static String convert(Vector<struct> v){
        String a = "";
        for (int i = 0; i < v.size(); i++) {
          var idx = Integer.toString(v.get(i).idx);
            while (idx.length()<4){
                idx = '0'+idx;}

          a+=idx+v.get(i).c;
        }
        return a;
    }
    static String compress (String a){
        Vector<String> dict = new Vector<String> ();
        dict.add("");String temp="";int idx = 0;
        var Compressed = new Vector<struct>();

        for (int i = 0; i < a.length(); i++) {
            temp= String.valueOf(a.charAt(i));idx = 0;
            while (true){
                if (dict.contains(temp)==true) {
                    i++;
                    if(i==a.length()){idx = dict.indexOf(temp);Compressed.add(new struct(idx,'\0'));break;}
                    idx = dict.indexOf(temp);
                    temp+=a.charAt(i);
                }else {
                    dict.add(temp);
                    Compressed.add(new struct(idx,a.charAt(i)));
                    break;
                }


            }
        } //Printing The Dictionary
        for (int i = 1; i < dict.size(); i++) {
            System.out.println( dict.elementAt(i) + "  "+i); ;
        }
        var res = convert(Compressed);
        //Printing the Compressed String
        //System.out.println(res);
        return res;
    }

    static Vector<struct> convert(String Compressed){
        int i = 0;var v = new Vector<struct>();
        while (true){
            var idx =Integer.parseInt(Compressed.substring(i,i+4),10);
            i+=4;
            v.add(new struct(idx,Compressed.charAt(i)));
            if (1+i==Compressed.length())break;
            i++;
        }
        //Printing the Compressed Vector after converting it into vector
       /* for (int j = 0; j < v.size(); j++) {
             v.get(j).print();
        }*/
        return v;
    }
    static String deCompress(String a){
        var v = convert(a);//the last space is cuz of the null
        Vector<String> dict = new Vector<String> ();var idx=0;char c;
        dict.add("");String res ="",temp ="";
        for (struct struct : v) {
            temp=dict.get(struct.idx)+struct.c;
            res+=temp;
            dict.add(temp);
        }
        return res;
    }
    public static void main(String[] args) throws IOException {
        while (true){
        try {
            System.out.println("Enter the name of the file");
            var input = new Scanner(System.in).next();
            String text = Files.readString(Path.of(input+".txt"));
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
