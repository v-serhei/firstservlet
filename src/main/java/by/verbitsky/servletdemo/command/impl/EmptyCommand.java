package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.WebCommand;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements WebCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/main";
    }
}
