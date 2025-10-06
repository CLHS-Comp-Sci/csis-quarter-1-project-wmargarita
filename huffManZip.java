package prog.huffman;

import java.io.*;
import java.util.*;

public class huffManZip {

    static PriorityQueue<TREE> pq = new PriorityQueue<>();
    static int[] freq = new int[300];
    static String[] ss = new String[300];
    static int exbits;

    static class TREE implements Comparable<TREE> {
        TREE Lchild;
        TREE Rchild;
        public int Bite;
        public int Freqnc;

        @Override
        public int compareTo(TREE T) {
            return Integer.compare(this.Freqnc, T.Freqnc);
        }

        public boolean isLeaf() {
            return (Lchild == null && Rchild == null);
        }
    }

    static TREE Root;

    public static void calFreq(String fName) throws IOException {
        Arrays.fill(freq, 0);
        File file = new File(fName);
        if (!file.exists()) throw new FileNotFoundException(fName + " not found.");
        try (FileInputStream fin = new FileInputStream(file)) {
            int b;
            while ((b = fin.read()) != -1) {
                freq[b]++;
            }
        }
    }

    public static void makeTree() {
        pq.clear();
        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                TREE t = new TREE();
                t.Bite = i;
                t.Freqnc = freq[i];
                pq.add(t);
            }
        }
        if(pq.isEmpty()){
            Root=null;
            return;
        }
        while (pq.size() > 1) {
            TREE t1 = pq.poll();
            TREE t2 = pq.poll();
            TREE parent = new TREE();
            parent.Freqnc = t1.Freqnc + t2.Freqnc;
            parent.Lchild = t1;
            parent.Rchild = t2;
            pq.add(parent);
        }

        Root = pq.poll();
    }

    public static void getCodes(TREE node, String s) {
        if (node == null) return;
        if (node.isLeaf()) {
            ss[node.Bite] = (s.isEmpty()) ? "0" : s;
            return;
        } else {
            getCodes(node.Lchild, s + "0");
            getCodes(node.Rchild, s + "1");
        }
    }

    public static void compress(String inputFile, String outputFile) throws IOException {
        calFreq(inputFile);
        makeTree();
        if (Root == null) {
            System.out.println("Input file is empty.");
            try (DataOutputStream dout = new DataOutputStream(new FileOutputStream(outputFile))) {
                for (int i = 0; i < 256; i++) dout.writeInt(0);
                dout.writeInt(0);
            }
            return;
        }
        getCodes(Root, "");

        StringBuilder bitString = new StringBuilder();
        try (FileInputStream fin = new FileInputStream(inputFile)) {
            int b;
            while ((b = fin.read()) != -1) {
                bitString.append(ss[b]);
            }
        }

        // pad to byte multiple
        exbits = (8 - (bitString.length() % 8)) % 8;
        for (int i = 0; i < exbits; i++) bitString.append("0");

        try (DataOutputStream dout = new DataOutputStream(new FileOutputStream(outputFile))) {
            for (int i = 0; i < 256; i++) dout.writeInt(freq[i]);
            dout.writeInt(exbits);

            for (int i = 0; i < bitString.length(); i += 8) {
                String byteStr = bitString.substring(i, i + 8);
                int val = Integer.parseInt(byteStr, 2);
                dout.write(val);
            }
        }

        System.out.println("File compressed → " + outputFile);
    }

    public static void decompress(String inputFile, String outputFile) throws IOException {
        try (DataInputStream din = new DataInputStream(new FileInputStream(inputFile))) {
            for (int i = 0; i < 256; i++) freq[i] = din.readInt();
            exbits = din.readInt();
            makeTree();

            if (Root == null) {
                System.out.println("Compressed file was empty.");
                new File(outputFile).createNewFile();
                return;
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int bt;
            StringBuilder bits = new StringBuilder();
            while ((bt = din.read()) != -1) {
                String bStr = String.format("%8s", Integer.toBinaryString(bt & 0xFF)).replace(' ', '0');
                bits.append(bStr);
            }

            // Remove padding
            if (exbits > 0 && exbits <= bits.length()) {
                bits.setLength(bits.length() - exbits);
            }

            try (FileOutputStream fout = new FileOutputStream(outputFile)) {
                if (Root.isLeaf()) {
                    // Single-character file
                    for (int i = 0; i < Root.Freqnc; i++) fout.write(Root.Bite);
                    return;
                }

                TREE node = Root;
                for (int i = 0; i < bits.length(); i++) {
                    char c = bits.charAt(i);
                    node = (c == '0') ? node.Lchild : node.Rchild;

                    if (node.isLeaf()) {
                        fout.write(node.Bite);
                        node = Root;
                    }
                }
            }
        }

        System.out.println("File decompressed → " + outputFile);
    }
}
