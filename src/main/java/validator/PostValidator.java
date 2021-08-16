package validator;

public class PostValidator {

    private final static int MAX_SIZE_CONTENT = 240;

    public boolean contentIsValid(String content) {

        return !content.isEmpty() && content.length() <= MAX_SIZE_CONTENT;
    }

}
