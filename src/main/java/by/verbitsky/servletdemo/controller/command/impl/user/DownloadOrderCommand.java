package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

import java.util.Optional;

public class DownloadOrderCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            user.getBasket().clear();
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        try {
            long currentOrderId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_ID));
            Optional<String> downLoadFilePath = OrderServiceImpl.INSTANCE.prepareOrderDownloadLink(currentOrderId);
            if (downLoadFilePath.isPresent()) {
                content.addRequestAttribute(AttributeName.DOWNLOAD_FILE_PATH, downLoadFilePath.get());
                return new CommandResult(PagePath.FORWARD_SERVLET_DOWNLOAD, false);
            }else {
                content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.DOWNLOAD_ORDER);
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.DOWNLOAD_ORDER_ERROR);
                content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_PROFILE);
                content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_ORDER_PAGE);
                return new CommandResult(PagePath.FORWARD_RESULT_PAGE, false);
            }
        } catch (NumberFormatException ex) {
            throw new CommandException("DownloadOrderCommand: wrong parameter orderId", ex);
        } catch (ServiceException e) {
            throw new CommandException("DownloadOrderCommand: error while removing order from db", e);
        }
    }
}