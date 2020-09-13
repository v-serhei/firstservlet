package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return null;
    }
}
