package prog.huffman;

import com.sun.source.tree.Tree;

import java.io.*;
import java.util.PriorityQueue;

public class huffManZip {

    static PriorityQueue<TREE> pq = new PriorityQueue<TREE>();
    static int[] freq = new int[300];
    static String[] ss = new String[300];
    static int exbits;
    static byte bt;
    static int cnt;


    static class TREE implements Comparable<TREE> {
        TREE Lchild;
        TREE Rchild;

        public String deb;
        public int Bite;
        public int Freqnc;

        @Override
        public int compareTo(TREE T) {
            if(this.Freqnc < T.Freqnc){
                return -1;
            }
            if(this.Freqnc > T.Freqnc){
                return 1;
            }
            return 0;
        }
    }
    static TREE Root;


    public static void calFreq(String fName){
        File file = null;
        Byte bt;

        file = new File(fName);
        try {
            FileInputStream file_input = new FileInputStream(file);
            DataInputStream data_in = new DataInputStream(file_input);
            while(true){
                try{
                    bt = data_in.readByte();

                }
            }
        }
    }
}
