package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.PagePath;

public class MainPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {

 /*
 todo удалить

      CommandResult  result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
      The requested resource [/audiobox/pages/mainpage] is not available

      CommandResult  result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, false);
      The requested resource [/audiobox/pages/mainpage] is not available

      CommandResult  result = new CommandResult(ProjectPages.FORWARD_MAIN_PAGE, false);
      так работает, но ссылка кривая
 */
        //todo тут сделать пагинацию

        CommandResult result = new CommandResult(PagePath.MAIN_PAGE, true);
        return result;
    }
}
