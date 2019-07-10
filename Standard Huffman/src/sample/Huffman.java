package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class Node implements Comparable <Node> {
    public char c;
    public int p;
    public Node left;
    public Node right;

    public Node(char c, int p, Node l, Node r) {
        this.c = c;
        this.p = p;
        left = l;
        right = r;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(p, o.p);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }


}

class Compressed {
    public String compressed;
    public String table;

    Compressed(String compressed, String t) {
        this.table = t;
        this.compressed = compressed;
    }

    @Override
    public String toString() {
        String a = "The Compressed text is " + compressed;
        a += "\nThe code Table is " + table;
        return a;
    }
}

public class Huffman {
    /* Compress Methods */
    static String Table = "";

    public static Compressed Compress(String a) {
        //return type isn't string as we need to return the string + table of codes we generated so can decode
        String res = "";
        var p = createProb(a); //P is a vector that have the probabilities
        var PrQu = new PriorityQueue <Node>();
        //Getting the chars inserted into the priority queue
        for (int i = 0; i < p.size(); i++) {
            if (p.elementAt(i) >= 1)
                PrQu.add(new Node((char) i, p.elementAt(i), null, null));
        }
        //reordering and making trees
        if (PrQu.size() == 1) {
            PrQu.add(new Node((char) 27, 1, null, null)); //27 is a null
        }
        while (PrQu.size() != 1) {
            var x = PrQu.poll();
            var y = PrQu.poll();
            PrQu.add(new Node((char) 27, x.p + y.p, x, y));
        }

        var Tree = new HashMap <Character, String>();
        //===============Tree has the value of the code================//

        var root = PrQu.peek();
        Table = "";
        recTable(root, "", Tree); //entirely edits the "Table"
        for (int i = 0; i < a.length(); i++) {

            var c = a.charAt(i);
            res += Tree.get(c);
        }
        var Final = new Compressed(res, Table);
        return Final;
    }

    static void recTable(Node n, String a, HashMap <Character, String> t) {

        if (n.isLeaf()) t.put(n.c, a);
        else {
            recTable(n.right, a + '1', t);
            if (n.right.isLeaf()) {
                System.out.println(n.right.c + "  " + a + '1');
                Table += (char) n.right.c + "\t" + a + "1\b";
            }
            recTable(n.left, a + '0', t);
            if (n.left.isLeaf()) {
                System.out.println(n.left.c + "  " + a + '0');
                Table += (char) n.left.c + "\t" + a + "0\b";
            }
        }
    }

    public static Vector <Integer> createProb(String a) {
        Vector <Integer> Probability = new Vector <>();
        for (int i = 0; i < 256; i++) {
            Probability.add(0);
        }
        for (int i = 0; i < a.length(); i++) {
            var x = a.charAt(i);
            Probability.setElementAt(Probability.elementAt((int) x) + 1, (int) x);//increment the probability of given character index 1++
        }
        return Probability;
    }

    /* Decompress Methods */
    static HashMap <String, String> convertToMap(String a) {
        HashMap <String, String> t = new HashMap <>();

        var list = a.substring(0, a.length() - 1).split("\b");
        for (int i = 0; i < list.length; i++) {
            var x = list[i].split("\t");
            t.put(x[1], x[0]);
        }
        return t;
    }

    public static String Decompress(String comp, String table) {
        HashMap <String, String> t = convertToMap(table); //"t" now have the character mapped to its code
        System.out.println(t);
        String res = "";
        String temp = "";
        for (int i = 0; i < comp.length(); i++) {
            temp += comp.charAt(i);
            if (t.containsKey(temp)) {
                res += t.get(temp);
                temp = "";
            }
        }
        return res;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter the Name of your text file");
            try {
                var input = new Scanner(System.in).nextLine();
                String text = Files.readString(Path.of(input + ".txt"));
                var resultComp = Compress(text);
                System.out.println(resultComp);
                /* Write to the File */ //moshklt in mafy4 7aga bttktb fe el files
                var outC = new PrintWriter(new FileWriter("compressed.txt"));
                outC.println(resultComp.compressed);
                var outT = new PrintWriter(new FileWriter("table.txt"));
                outT.println(Table);
                outC.flush();
                outT.flush();

                /* Decompress then write to the Decompressed file */
                var GetText = new BufferedReader(new FileReader("compressed.txt")).readLine();
                var GetTable = new BufferedReader(new FileReader("table.txt")).readLine();
                var DeCompressed = Decompress(GetText, GetTable);
                //   System.out.println("The Decompressed Text is " + DeCompressed);
                var outD = new PrintWriter(new FileWriter("decompressed.txt"));
                outD.println(DeCompressed);
                System.out.println("The Decompressed Text is \"" + DeCompressed + "\"");

                outD.flush();

            } catch (IOException e) {
                System.out.println("We can\'t open the file at all");
            }
        }
    }

}
