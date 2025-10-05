package prog.huffman;

public class HuffmanUtils {
    //byte to binary conversion
    public static int to(Byte b){
        int ret = b;
        if(ret < 0){
            ret = ~b;
            ret = ret + 1;
            ret = ret ^ 255;
            ret += 1;
        }
        return ret;
    }

}
