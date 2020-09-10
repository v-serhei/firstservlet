package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/main";
    }
}
