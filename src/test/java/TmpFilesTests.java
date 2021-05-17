import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TmpFilesTests {

    private TmpFiles tmpFiles;

    @Before
    public void init() {
        tmpFiles = new TmpFiles();
    }

    @Test
    public void clean_arraySize_zero() {
        tmpFiles.createAndAddFile("caz1");
        tmpFiles.createAndAddFile("caz2");
        tmpFiles.createAndAddFile("caz3");
        tmpFiles.clean();
        int size = tmpFiles.getFiles().size();
        Assert.assertEquals(0, size);
    }

    @Test
    public void createAndAddFile_empty2ndParam_AssignsCorrectName() {
        tmpFiles.createAndAddFile("ceA");
        String fileName = tmpFiles.getFiles().get(0).getName();
        Assert.assertEquals("tmp0.txt", fileName);
        tmpFiles.clean();
    }

    @Test
    public void createAndAddFile_bothParamsFilled_AssignsCorrectName() {
        tmpFiles.createAndAddFile("ceA", "correctName");
        String fileName = tmpFiles.getFiles().get(0).getName();
        Assert.assertEquals("correctName.txt", fileName);
        Assert.assertEquals("correctName.txt", fileName);
        tmpFiles.clean();
    }

    @Test
    public void createFile_3rdParamFalse_filesArraySizeEquals0() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        File file = tmpFiles.createFile(list, "numbers");
        int size = tmpFiles.getFiles().size();
        Assert.assertEquals(0, size);
        file.delete();
    }

    @Test
    public void createFile_3rdParamTrue_filesArraySizeEquals1() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        File file = tmpFiles.createFile(list, "numbers", true);
        int size = tmpFiles.getFiles().size();
        Assert.assertEquals(1, size);
        file.delete();
    }

    @Test
    public void createFile_3rdParamTrue_filesArrayCorrectlyPlacedInFile() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        tmpFiles.createFile(list, "numbers", true);
        File file = tmpFiles.getFiles().get(0);
        String content = "";
        try {
            content = Files.readString(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("one\ntwo\nthree\n", content);
        tmpFiles.clean();
    }
}
