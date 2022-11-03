package seedu.duke.commands;

import seedu.duke.Module;
import seedu.duke.ModuleList;
import seedu.duke.exceptions.*;

import static seedu.duke.exceptions.InvalidGradeException.checkGradeFormat;
import static seedu.duke.exceptions.InvalidGradeException.checkValidGrade;
import static seedu.duke.exceptions.InvalidMcException.invalidMc;
import static seedu.duke.exceptions.InvalidSemesterException.*;

public class Add extends Command {
    private Module mod;

    /**
     * Contructor of Add class to initialize an object of class Add.
     * @param input the input message to be used to initialize the variables
     * @throws InvalidInputFormatException exception which is thrown if the format of the input is wrong
     * @throws InvalidInputContentException exception to be thrown if the input content is empty
     */
    public Add(String input) throws InvalidInputFormatException, InvalidInputContentException, InvalidMcException, InvalidGradeException, InvalidSemesterException {
        checkFormat(input);
        int[] indexes = positions(input);
        checkContent(input, indexes);
        addition(input, indexes);
    }

    /**
     * Function to extract details from input text and create a new module with the details extracted.
     * Extracted details include course, semester, mc, and grade.
     * @param input the input entered by the user
     * @param indexes an array containing the positions from which the details need to be extracted
     */
    private void addition(String input, int[] indexes) throws InvalidMcException, InvalidGradeException, InvalidSemesterException {
        input = input.toUpperCase();
        String course = extractingContent(input, indexes[0], indexes[1]);
        String semester = extractingContent(input, indexes[2], indexes[3]);
        checkYear(semester);
        String mcString = extractingContent(input, indexes[4], indexes[5]);
        checkMcString(mcString);
        int mc = Integer.parseInt(mcString);
        checkMc(mc);
        String grade = extractingContent(input, indexes[6], indexes[7]);
        checkGrade(grade);

        this.mod = new Module(course, semester, grade, mc);
    }

    /**
     * Function to extract content from startIndex to endIndex out of input
     * @param input The input from which the content needs to be extracted. Format: String
     * @param startIndex The starting index. Format: int
     * @param endIndex The ending index. Format: int
     * @return a string which is a substring (extracted) of input.
     */
    private String extractingContent(String input, int startIndex, int endIndex) {
        if (endIndex == -1) {
            return input.substring(startIndex);
        } else {
            return input.substring(startIndex, endIndex);
        }
    }

    /**
     * Function to check if the format of input is correct or not
     * @param input input entered by user. Format: String
     * @throws InvalidInputFormatException exception thrown if format of input is incorrect
     */

    public void checkFormat(String input) throws InvalidInputFormatException {
        boolean isRight;
        isRight = InvalidInputFormatException.checkMod(input);
        checkFormatException(isRight);
        isRight = InvalidInputFormatException.checkSem(input);
        checkFormatException(isRight);
        isRight = InvalidInputFormatException.checkMC(input);
        checkFormatException(isRight);
        isRight = InvalidInputFormatException.checkGrade(input);
        checkFormatException(isRight);
    }

    public void checkFormatException(boolean isRight) throws InvalidInputFormatException {
        if (!isRight) {
            throw new InvalidInputFormatException();
        }
    }

    /**
     * Function to check if content entered by user is empty or not
     * @param input input entered by user. Format: String
     * @param idx a collection of indexes where the details should be present. If these are empty, an exception should be thrown
     * @throws InvalidInputContentException exception thrown if content of input is empty
     */
    public void checkContent(String input, int[] idx) throws InvalidInputContentException {
        boolean isSame;
        isSame = InvalidInputContentException.emptyContent(idx[0], idx[1], input);
        checkContentException(isSame);
        isSame = InvalidInputContentException.emptyContent(idx[2], idx[3], input);
        checkContentException(isSame);
        isSame = InvalidInputContentException.emptyContent(idx[4], idx[5], input);
        checkContentException(isSame);
        isSame = InvalidInputContentException.emptyContent(idx[6], idx[7], input);
        checkContentException(isSame);
    }

    public void checkContentException(boolean isSame) throws InvalidInputContentException {
        if (isSame) {
            throw new InvalidInputContentException();
        }
    }

    public void checkMcString(String mcString) throws InvalidMcException {
        if (mcString.length() > 2) {
            throw new InvalidMcException();
        }
        if (!mcString.matches("[0-9]+")) {
            throw new InvalidMcException();
        }

    }

    public void checkYear(String semester) throws InvalidSemesterException {
        if(invalidFormat(semester)) {
            throw new InvalidSemesterException();
        }
        if (invalidYearNumber(semester) || invalidSemesterNumber(semester)) {
            throw new InvalidSemesterException();
        }
    }

    public void checkMc(int mcs) throws InvalidMcException {
        if (invalidMc(mcs)) {
            throw new InvalidMcException();
        }
    }

    public void checkGrade(String grade) throws InvalidGradeException {
        if (!checkGradeFormat(grade)) {
            throw new InvalidGradeException();
        }
        if (!checkValidGrade(grade)) {
            throw new InvalidGradeException();
        }
    }

    /**
     * function to return the positions of the details in input
     * @param input the input given by user. Format: String
     * @return an integer array containing the positions of the details given by user
     */
    public int[] positions(String input) {
        int[] idx = new int[8];
        idx[0] = input.indexOf("m/") + 2;
        idx[1] = input.indexOf(" ", idx[0]);
        idx[2] = input.indexOf("s/") + 2;
        idx[3] = input.indexOf(" ", idx[2]);
        idx[4] = input.indexOf("mc/") + 3;
        idx[5] = input.indexOf(" ", idx[4]);
        idx[6] = input.indexOf("g/") + 2;
        idx[7] = input.indexOf(" ", idx[6]);
        return idx;
    }

    @Override
    public void execute(ModuleList modulelist) {
        modulelist.add(this.mod, false);
    }
}
