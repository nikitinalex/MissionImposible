package it.sevenbits;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public interface OutStream extends Stream {
    public void recordSymbol(char newSymb);
}

class FileOutStream implements OutStream {

    private String fileName;
    private FileOutputStream fileStream;

    public FileOutStream(String fileName) {
        this.fileName = fileName;
        try {
            fileStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new FileProblem("Problems with output file, check the name");
        }
    }

    public String getFileName() {
        return fileName;
    }


    @Override
    public boolean closeStream() throws IOException {
        if (fileStream == null) {
            return false;
        }
        fileStream.close();
        return true;
    }

    @Override
    public void recordSymbol(char newSymb) {
        try {
            fileStream.write((int) newSymb);
        } catch (IOException e) {
            throw new FileProblem("It doesn't recording");
        }
    }
}

class StringOutStream implements OutStream {
    private StringBuilder outStringStream;
    private int pointer;

    public StringOutStream(StringBuilder workingStream) {
        outStringStream = workingStream;
        pointer = outStringStream.length() - 1;
    }

    public void recordSymbol(char newSymb) {
        outStringStream.append(newSymb);
        pointer++;
    }

    @Override
    public boolean closeStream() throws IOException {
        return true;
    }
}
