package prog.huffman;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Huffman File Compressor ===");
        System.out.println("1. Compress file");
        System.out.println("2. Decompress file");
        System.out.print("Enter choice: ");
        int ch = sc.nextInt();
        sc.nextLine();

        try {
            if (ch == 1) {
                System.out.print("Enter input file path: ");
                String in = sc.nextLine();
                System.out.print("Enter output (.huff) file path: ");
                String out = sc.nextLine();
                huffManZip.compress(in, out);
            } else if (ch == 2) {
                System.out.print("Enter compressed (.huff) file path: ");
                String in = sc.nextLine();
                System.out.print("Enter output file path: ");
                String out = sc.nextLine();
                huffManZip.decompress(in, out);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

