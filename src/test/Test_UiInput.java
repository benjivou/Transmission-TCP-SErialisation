package test;

import serialize.FileManager;
import ui.inputActivity.FrameInput;
import ui.outputActivity.FrameOutputMessage;

import java.io.IOException;

public class Test_UiInput {
	public static void main(String[] args) throws IOException {

        FrameOutputMessage fmo  = new FrameOutputMessage();
        FileManager fm = new FileManager("./tutu.txt");
        new Thread(fmo).start();
	    FrameInput fi = new FrameInput(fm);
	}
}
