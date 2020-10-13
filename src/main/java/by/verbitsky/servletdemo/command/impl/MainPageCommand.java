package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class MainPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {

       // CommandResult  result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
/*
        The requested resource [/audiobox/pages/mainpage] is not available
     */

 /*       CommandResult  result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, false);
        The requested resource [/audiobox/pages/mainpage] is not available

* */


        //так работает, но ссылка УГ
       // CommandResult  result = new CommandResult(ProjectPages.FORWARD_MAIN_PAGE, false);

        CommandResult  result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);


        //todo тут сделать пагинацию
        return result;
    }
}
