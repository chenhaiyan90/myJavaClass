package exampleTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import static java.lang.System.out;

/**
 * Created by chenhaiyan on 2016/12/22.
 */
public class TestLineNumberReader {
    public static void main(String[] args) throws FileNotFoundException {
        LineNumberReader lineNumberReader=new LineNumberReader(new FileReader("testDataOutPutStream.txt"));
        try {
            out.println(lineNumberReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                lineNumberReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
