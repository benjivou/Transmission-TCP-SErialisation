package test;

import serialize.FileManager;
import ui.inputActivity.FrameInput;

import java.io.IOException;

public class Test_UiInput {
	public static void main(String[] args) throws IOException {
        FileManager fm = new FileManager("./tutu.txt");
	    FrameInput fi = new FrameInput(fm);
	}
}
