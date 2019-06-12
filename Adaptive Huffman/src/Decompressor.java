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

public class Decompressor {
	static String comp;	static String decoded ;
	static ReadWrite in ;	static FileOutputStream out ;
	static void SetFiles() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Names of the Compressed File followed by \".txt\""); //compressed.txt
		comp =input.nextLine();
		decoded = "decoded.txt";
		System.out.println("The Decompressed File name is \"decoded.txt\"");

		try {
			in = new ReadWrite(new FileInputStream(comp));
			out = new FileOutputStream(decoded);
		} catch (FileNotFoundException e) {
			System.out.println("Al File msh mawgod");
		}

	}
	static void Decompress() throws IOException {
		var tree = new Tree();
		try {
			int c ;int val ;
			if(tree.isEmpty()) { //No NYT will be added
				var bitBuffer =readByte(in);
				out.write(bitBuffer);
				tree.insert(bitBuffer);
			}
			var n = tree.root;
			while(0xffffffff < (c = in.read())) {
				if(c == 1) n = n.right;
				if(c == 0) n = n.left;

				if(n.NYT()) {
					val = readByte(in);tree.insert(val);
					n = tree.root;
					out.write(val);

				}
				if(n.Leaf()) {
					val = n.val;tree.insert(val);
					n = tree.root;
					out.write(val);
				}
			}
		}
		catch (IOException exception) {System.err.println("Can not Input from the Decoded file");}
		finally {if (in !=null) in.close();if(out != null) out.close();}
	}
	static void getRatio(){
		float c = new File(comp).length();float d = new File(decoded).length();
		var p = "Compressed size is : " + c + " bytes\n" + "Deompressed size is : " + d + " bytes\n" +
				"Compression ratio is: " + d/c;
		System.out.println(p);

	}
	public static void main(String[] args) throws IOException {
		//=============Input File Names=============//
		SetFiles();

		//=============Compress Method=============//
		Decompress();

		//============Compression Ratio============//
		getRatio();
    }


    

	static private int  readByte(ReadWrite in) throws IOException {
		int bitBuffer = 0;
		int c;
		for(int i = 0; i<8;i++) {
			c = in.read();
			bitBuffer |= c;
			if(i!=7) bitBuffer <<= 1;
			
		}
		return bitBuffer;
	}

}
