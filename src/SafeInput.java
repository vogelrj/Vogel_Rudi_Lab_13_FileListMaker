import java.util.Scanner;

public class SafeInput {

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";  // Set this to zero length. Loop runs until it isn’t
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;

    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return user input int
     */
    public static int getInt(Scanner pipe, String prompt) {
        Scanner in = new Scanner(System.in);
        int retInt = 0;
        String TrashBag = "";
        do {
            System.out.print("\n" + prompt + ": ");
            if (in.hasNextInt()) {
                retInt = in.nextInt();
                in.nextLine();
                TrashBag = "";
            } else {
                TrashBag = in.nextLine();
                System.out.println("Invalid Entry. Please Try Again.");
            }
        }
        while (TrashBag.length() > 0);
        return retInt;
    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return user input double
     */
    public static double getDouble(Scanner pipe, String prompt) {
        Scanner in = new Scanner(System.in);
        double retDouble = 0;
        String Trashbag = "";
        do {
            System.out.print("\n" + prompt + ": ");
            if (in.hasNextDouble()) {
                retDouble = in.nextDouble();
                in.nextLine();
                Trashbag = "";
            } else {
                Trashbag = in.nextLine();
                System.out.println("Invalid Entry. Please Try Again.");
            }
        }
        while (Trashbag.length() > 0);
        return retDouble;
    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low    low range for int value
     * @param high   high range for int value
     * @return user input int
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        Scanner in = new Scanner(System.in);
        String Trashbag = "";
        int rangedInt = 0;
        do {
            System.out.print("\n" + prompt + "between " + low + " and " + high + ": ");
            if (in.hasNextInt()) {
                rangedInt = in.nextInt();
                Trashbag = "";
                in.nextLine();
                do {
                    if (rangedInt >= low && rangedInt <= high) {
                        Trashbag = "";
                    } else {
                        System.out.println("Invalid Entry [" + rangedInt + "] Number out of range.");
                        rangedInt = 0;
                        Trashbag = "full";
                    }
                }
                while (rangedInt <= low && rangedInt >= high);
            } else {
                Trashbag = in.nextLine();
                System.out.println("Invalid Entry. Please Try Again.");
            }
        }
        while (Trashbag.length() > 0);
        return rangedInt;
    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low    low range for double value
     * @param high   high range for double value
     * @return
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double
            low, double high) {
        Scanner in = new Scanner(System.in);
        double rangedDouble = 0;
        String TrashBag = "";
        do {
            System.out.print("\n" + prompt + "between " + low + " and " + high + ": ");
            if (in.hasNextDouble()) {
                rangedDouble = in.nextDouble();
                TrashBag = "";
                in.nextLine();
                do {
                    if (rangedDouble >= low && rangedDouble <= high) {
                        TrashBag = "";
                    } else {
                        System.out.println("Invalid Entry. Number [" + rangedDouble + "] out of range.");
                        rangedDouble = 0;
                        TrashBag = "full";
                    }
                }
                while (rangedDouble <= low && rangedDouble >= high);
            } else {
                TrashBag = in.nextLine();
                System.out.println("Invalid Entry. Please Try Again.");
            }
        }
        while (TrashBag.length() > 0);
        return rangedDouble;
    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        Scanner in = new Scanner(System.in);
        String ConfirmMsg = "";
        boolean blockVal = true;
        do {
            System.out.println(prompt);
            if (in.hasNextLine()) {
                ConfirmMsg = (in.nextLine());
                if (ConfirmMsg.equalsIgnoreCase("Y")) {
                    blockVal = true;
                } else if (ConfirmMsg.equalsIgnoreCase("N")) {
                    blockVal = (!blockVal);
                } else {
                    System.out.println("Invalid Entry [" + ConfirmMsg + "].");
                    ConfirmMsg = "aaa";
                }
            }
        }
        while (ConfirmMsg.length() > 1);
        return blockVal;
    }

    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt message to display as the prompt for the input.
     * @param regEx  pattern in java String format to use for matching
     * @return
     */
    public static String getRegExString(Scanner pipe, String prompt, String
            regEx) {
        Scanner in = new Scanner(System.in);
        String UserInput = "";
        boolean validInput = false;
        do {
            System.out.println(prompt + ": ");
            UserInput = in.nextLine();
            if (UserInput.matches(regEx)) {
                validInput = true;
            }
            else {
                System.out.println("\nInvalid input: " + UserInput);
            }

        }
        while (!validInput);
        return UserInput;
    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param CenterText text to be displayed
     * @retur
     */
    public static String PrettyHeader(Scanner pipe,String CenterText) {
        int rightBorder = (54 - CenterText.length()) / 2;
        int leftBorder = 0;
        for (int topBorder = 0; topBorder < 60; topBorder++) {
            System.out.print("*");
        }
        System.out.print("\n***");
        if (CenterText.length()%2 == 0) {
            leftBorder = (54 - CenterText.length()) / 2;
        }
        else {
            leftBorder = 1 + (54 - CenterText.length()) / 2;
        }
        for (int leftSpace = 0; leftSpace < rightBorder; leftSpace++) {
            System.out.print(" ");
        }
        System.out.print(CenterText);
        for (int rightSpace = 0; rightSpace < leftBorder; rightSpace++) {
            System.out.print(" ");
        }
        System.out.println("***");
        for (int bottomBorder = 0; bottomBorder < 60; bottomBorder++) {
            System.out.print("*");
        }
        return CenterText;
    }
}

