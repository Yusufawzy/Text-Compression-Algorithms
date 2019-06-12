/*
*
* Program :  Adaptive Huffman - Compressor and Decompressor
* Name :     Yusuf Fawzy
* ID :       20160299
* Group :    CS_1
*
* */

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Compressor {

    static String original;
    static String comp;
    static FileInputStream in;
    static ReadWrite out;


    static void SetFiles() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Names of the Original File followed by \".txt\""); //original.txt
        //=============We should take the input from the user=============//
        original =input.nextLine();
        System.out.println("The Compressed File name is \"compressed.txt\"");
        comp = "compressed.txt";

        try {
            in = new FileInputStream(original);
            out = new ReadWrite(new FileOutputStream(comp)); //BitByteStream so we can convert from bytes to chars
        } catch (FileNotFoundException e) {
            System.out.println("There is no File of this name.");
        }
    }

    static void compress() throws IOException {
        Tree tree = new Tree();
        try {
          //  System.out.print("The Compressed code in binary is ");
            int c;
            while (0x0 < (c = in.read())) {
                var code = new Vector <Boolean>();
                int n;
                var flag = !tree.exists(c);
                tree.Add(c, code);
                n = code.size();
                while (0x0 <= --n) out.writeBit(code.get(n)); //we take bit by bit 0 or 1 but from the end to the start
                if (flag) out.writeByte(c);
            }
            //making sure nothing is in the buffer no more
            out.flush();
            System.out.println();
        }
        catch (Exception e){}
        finally {
            if (!(in == null)) in.close();
            if (!(out == null)) out.close();
        }

    }

    static void getRatio() {
        float orig = new File(original).length();
        float done = new File(comp).length();
        var p = "Original size is : " + orig + " bytes\n" + "Compressed size is : " + done + " bytes\n" +
                "Compression ratio is: " + orig / done;
        System.out.println(p);
    }

    public static void main(String[] args) throws IOException {

        //=============Input File Names=============//
        SetFiles();

        //=============Compress Method=============//
        compress();

        //============Compression Ratio============//
        getRatio();

    }


}
