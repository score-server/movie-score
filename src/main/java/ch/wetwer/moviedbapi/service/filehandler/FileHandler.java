package ch.wetwer.moviedbapi.service.filehandler;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Paths;


/**
 * @author Wetwer
 * @project movie-score
 */
public class FileHandler {

    private String filePath;
    private File file;

    /**
     * Class to handle Files easily
     *
     * @param filePath path to File to edit/read
     */
    public FileHandler(String filePath) {
        setFilePath(filePath);
    }

    /**
     * Class to handle Files easily
     *
     * @param file to edit/read
     */
    public FileHandler(File file) {
        setFilePath(file.getPath());
    }

    /**
     * @param path change the file currently in use
     */
    public void setFilePath(String path) {
        this.filePath = path;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @return path of current file
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * @return rows in file
     */
    public int getRowSize() {
        return readRows().length;
    }

    /**
     * @return file name with ending
     */
    public String getName() {
        return file.getName();
    }

    /**
     * @return String of all text in file
     */
    public String read() {
        try {
            return new String(java.nio.file.Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * starting row is 1
     *
     * @param row to to read
     *
     * @return returns String of row
     */
    public String read(int row) {
        String content = read();
        String[] rows = content.split("\n");
        content = null;
        return rows[row - 1];
    }

    /**
     * @return Array with all rows in file
     */
    public String[] readRows() {
        String content = read();
        return content.split("\\n");
    }

    /**
     * @param text to be writen on a new line at the end of the file
     */
    public void write(String text) {
        String history = read();
        try {
            if (!history.equals("")) {
                Files.write(history + "\n", file, Charset.forName("UTF-8"));
            }
            Files.write(text, file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            try {
                file.createNewFile();
                write(text);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param text to be writen
     * @param row  line on witch writen
     */
    public void write(String text, int row) {
        String rows[] = readRows();
        rows[row] = text;
        write(rows);
    }

    /**
     * @param rows to be writen at the end of the file
     */
    public void write(String[] rows) {
        String text = "";
        for (String row : rows) {
            if (text.equals("")) {
                text = row;
            } else {
                text = text + "\n" + row;
            }
            write(text);
        }

        try {
            Files.write(text, file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes all the letters from a line, but doesn delete the line.
     */
    public String spaceOutRow(int row) {
        String[] rows = readRows();
        String spaced = rows[row - 1];
        rows[row - 1] = " ";
        write(rows);
        return spaced;
    }

    /**
     * @param row to be moved 1 down
     *
     * @return ?
     */
    public String spaceInRow(int row) {
        String[] rows = readRows();
        String spaced = rows[row - 1];
        rows[row - 1] = " \n";
        write(rows);
        return spaced;
    }

    public boolean isFile() {
        return file.isFile();
    }

    /**
     * Deletes all the text in file
     */
    public void clear() {
        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes file
     *
     * @return delete successfull
     */
    public boolean delete() {
        return file.delete();
    }

    /**
     * @returns File object of this File
     */
    public File getFile() {
        return file;
    }

    /**
     * @returns Parent folder path of this file
     */
    public String getParentPath() {
        return file.getParent();
    }

    /**
     * @return if is folder -> files from folder
     */
    public File[] getSubFiles() {
        return file.listFiles();
    }

    public long getSize() {
        return file.length();
    }
}
