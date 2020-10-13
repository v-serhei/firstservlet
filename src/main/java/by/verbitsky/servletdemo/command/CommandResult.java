package by.verbitsky.servletdemo.command;

public class CommandResult {
    private final String resultPage;
    private final boolean isRedirect;

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