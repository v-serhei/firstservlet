package by.verbitsky.servletdemo.controller.command;

/**
 * The type Command result.
 * Class contains fields with command executing results
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public class CommandResult {
    private final String resultPage;
    private final boolean isRedirect;

    /**
     * Constructor
     *
     * @param resultPage - String parameter which contains url result
     * @param isRedirect - flag to define servlet response method: forward or redirect
     *
     *
     * @see javax.servlet.http.HttpServletRequest
     */
    public CommandResult(String resultPage, boolean isRedirect) {
        this.resultPage = resultPage;
        this.isRedirect = isRedirect;
    }

    public String getResultPage() {
        return resultPage;
    }

    public boolean isRedirect() {
        return isRedirect;
    }
}