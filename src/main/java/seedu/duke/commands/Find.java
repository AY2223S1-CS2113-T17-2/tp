package seedu.duke.commands;

import seedu.duke.exceptions.InvalidInputContentException;
import seedu.duke.ModuleList;

public class Find extends Command {
    private final String keyword;

    /**
     * Constructor to create an object of the Find Command class
     * @param input the keyword inputted by the user. Format: String
     * @throws InvalidInputContentException if the user does not add a keyword
     */
    public Find(String input) throws InvalidInputContentException {
        this.keyword = input.toUpperCase().trim();
        checkContent(keyword);
    }

    /**
     * function to check if input content is empty or not
     * @param input input entered by user. Format: String
     * @throws InvalidInputContentException exception thrown if input content is empty
     */
    public void checkContent(String input) throws InvalidInputContentException {
        if (input.equals("")) {
            throw new InvalidInputContentException();
        }
    }

    @Override
    public void execute(ModuleList moduleList) {
        moduleList.find(keyword);
    }

}
