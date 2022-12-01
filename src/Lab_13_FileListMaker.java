import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

public class Lab_13_FileListMaker {
    static ArrayList<String> usersList = new ArrayList<>();


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final String MenuChoice = "\nPlease select an option for the list: Add [A], Delete [D], Open [O], Clear [C], Save [S], View [V], or Quit [Q] ";
        boolean goAgain = false;
        boolean newFile = true;
        boolean toBeSaved = false;
        String fileName = "";
        String userCmd = "";
        do {
            displayusersList();
            userCmd = SafeInput.getRegExString(in, MenuChoice, "[AaDdVvQqOoSsCc]");
            userCmd = userCmd.toUpperCase();
            switch (userCmd) {
                case "A":
                    addListItem();
                    toBeSaved = true;
                    break;
                case "D":
                    deleteListItem();
                    toBeSaved = true;
                    break;
                case "V":
                    displayusersList();;
                    break;
                case "Q":
                    toBeSaved = checkDirty(toBeSaved, newFile, usersList, "quitting", fileName);
                    System.exit(0);
                    break;
                case "C":
                    clearList();
                    break;
                case "O":
                    toBeSaved = checkDirty(toBeSaved, newFile, usersList, "loading", fileName);
                    fileName = getFileName();
                    usersList = loadData(fileName);
                    newFile = false;
                    break;
                case "S":
                    saveFile(usersList, newFile, fileName);
                    toBeSaved = false;
                    break;
            }
        }
        while (!goAgain);

    }

    private static void displayusersList() {
        String emptyList = "";
        Scanner in = new Scanner(System.in);
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (usersList.size() != 0) {
            for (int i = 0; i < usersList.size(); i++) {
                System.out.printf("%-3d%-35s%n", i + 1, usersList.get(i));
            }
        } else {
            emptyList = SafeInput.PrettyHeader(in, "List is Empty.");
        }
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void addListItem() {
        Scanner in = new Scanner(System.in);
        String addItem = "";
        addItem = SafeInput.getNonZeroLenString(in, "What would you like to add to the list? ");
        usersList.add(addItem);

    }

    private static void deleteListItem() {
        Scanner in = new Scanner(System.in);
        int deleteItem = 0;
        deleteItem = SafeInput.getRangedInt(in, "What item number would you like to delete? ", 1, usersList.size());
        deleteItem = deleteItem - 1;
        usersList.remove(deleteItem);
    }


    private static boolean checkDirty(boolean toBeSaved, Boolean newFile, ArrayList usersList, String Action, String fileName){
        Scanner in = new Scanner(System.in);
        boolean blockVal;
        if (toBeSaved == true){
            blockVal = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save before " + Action + " [Y/N]?");
            if (!blockVal) {
                System.out.println("Okay!");
            }
            else {
                System.out.println("Okay. Let's save your work.");
                saveFile(usersList, newFile, fileName);
                toBeSaved = false;
            }
        }
        return false;
    }

    private static void clearList() {
        Scanner in = new Scanner(System.in);
        boolean goAgain = false;
        goAgain = SafeInput.getYNConfirm(in, "Are you sure you want to clear the list? All Data will be removed. [Y/N] ");
        if (goAgain) {
            usersList.clear();
            System.out.println("\nData Cleared.");
        }
        else {
            System.out.println("Okay. Let's Keep Going.");
        }
    }

    private static ArrayList<String> loadData(String fileName) {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path inputFile = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);
        ArrayList<String> newFile = new ArrayList<>();
        try {
            Scanner reader = new Scanner(inputFile);
            while (reader.hasNextLine()) {
                String lines = reader.nextLine();
                newFile.add(lines);
            }
            reader.close();
            System.out.println("\nFile " + fileName + " successfully loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("Error!");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFile;
    }
    private static String getFileName()  {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String fileName = "";
        try {
            System.out.println("Please select a file to load ");
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                fileName = selectedFile.getName();
            }
        }
        catch (HeadlessException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    private static void saveFile(ArrayList usersList, boolean newFile, String fileName) {
        Scanner in = new Scanner(System.in);
        if (newFile){
            fileName = SafeInput.getNonZeroLenString(in, "Please enter a file name (no spaces or special characters) ");
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);
            int size = usersList.size();
            try {
                OutputStream out = new BufferedOutputStream(Files.newOutputStream(file,CREATE));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                for (int i=0; i<size; i++){
                    String data = usersList.get(i).toString();
                    writer.write(data);
                    writer.newLine();
                }
                writer.close();
                System.out.println("File " + fileName + " Saved!");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);
            int size = usersList.size();
            try {
                OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                for (int i = 0; i < size; i++) {
                    String data = usersList.get(i).toString();
                    writer.write(data);
                    writer.newLine();
                }
                writer.close();
                System.out.println("File " + fileName + " Saved!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


