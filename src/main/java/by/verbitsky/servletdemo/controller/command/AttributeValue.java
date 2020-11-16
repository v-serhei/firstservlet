package by.verbitsky.servletdemo.controller.command;

public class AttributeValue {
    public static final String DEFAULT_COMMAND_ERROR_MESSAGE = "default.command.error.message";
    public static final String EMPTY_COMMAND_ERROR_MESSAGE = "empty.command.error.message";
    /*  values for result page  */
    public static final String CREATE_ORDER = "operation.create.order";
    public static final String PAY_ORDER = "operation.pay.order";
    public static final String OPERATION_COMPLETED = "operation.completed";
    public static final String OPERATION_FAILED = "operation.failed";
    public static final String ORDER_PAYMENT_SUCCESSFUL = "operation.message.successful.order.payment";
    public static final String BUTTON_CAPTION_PROFILE = "operation.btn.caption.profile";
    public static final String BUTTON_CAPTION_BACK = "operation.btn.caption.back";
    public static final String REMOVE_SONG_FROM_ORDER = "operation.remove.song.from.order";


    public static final String DOWNLOAD_ORDER = "operation.download.order";
    public static final String DOWNLOAD_ORDER_ERROR = "operation.message.order.download.failed";


    public static final String REMOVE_ORDER = "operation.remove.order";
    public static final String OPEN_ORDER = "operation.open.order";
    public static final String ORDER_REMOVE_SUCCESSFUL = "operation.message.successful.order.removing";
    public static final String ORDER_REMOVE_FAIL = "operation.message.order.removing.fail";
    public static final String COMPILATION_EMPTY_LIST = "operation.message.empty.compilation.list";

    public static final String BUTTON_CAPTION_REVIEW = "operation.btn.caption.review";
    public static final String REVIEW_CREATION = "operation.create.review";
    public static final String REVIEW_ADD_SUCCESSFUL = "operation.message.successful.review.add";
    public static final String REVIEW_ADD_FAIL = "operation.message.failed.review.add";

    public static final String REVIEW_DELETE = "operation.delete.review";
    public static final String REVIEW_DELETE_SUCCESSFUL = "operation.message.successful.review.delete";
    public static final String REVIEW_DELETE_FAIL = "operation.message.failed.review.delete";
    public static final String REVIEW_NOT_EXIST = "operation.message.review.not.exist";

    public static final String EMPTY_ORDER_LIST = "operation.message.empty.order.list";
    public static final String ORDER_NOT_EXIST = "operation.message.order.not.exist";
    public static final String ORDER_ALREADY_PAID = "operation.message.order.already.paid";

    public static final String LOGIN = "operation.login";
    public static final String LOGIN_BLOCKED = "operation.message.user.blocked";
    public static final String LOGIN_FAIL = "operation.login.fail";

    public static final String ADMIN_USER_UPDATE_SUCCESSFUL = "admin.operation.successful.user.update";
    public static final String ADMIN_USER_UPDATE_NOT_FOUND = "admin.operation.user.update.not.found";
    public static final String ADMIN_USER_UPDATE_SQL_ERROR = "admin.operation.user.update.sql.error";
    public static final String ADMIN_REVIEW_DELETE_SUCCESSFUL = "admin.operation.successful.delete.user.review";
    public static final String ADMIN_REVIEW_DELETE_NOT_FOUND = "admin.operation.delete.review.failed.not.found";
    public static final String ADMIN_REVIEW_DELETE_SQL_ERROR = "admin.operation.delete.review.failed.sql.error";

    public static final String ADMIN_CONTENT_UPDATE_SUCCESSFUL = "admin.operation.successful.update.content";
    public static final String ADMIN_CONTENT_UPDATE_NOT_FOUND = "admin.operation.update.content.failed.not.found";
    public static final String ADMIN_CONTENT_UPDATE_WRONG_PARAMETERS = "admin.operation.update.content.failed.wrong.parameters";
    public static final String ADMIN_CONTENT_DELETE_SUCCESSFUL = "admin.operation.successful.delete.content";
    public static final String ADMIN_CONTENT_DELETE_NOT_FOUND = "admin.operation.delete.content.failed.not.found";
    public static final String ADMIN_CONTENT_DELETE_WRONG_PARAMETERS = "admin.operation.delete.content.failed.wrong.parameters";
    public static final String ADMIN_CONTENT_DELETE_SQL_ERROR = "admin.operation.delete.content.failed.sql.error";
    public static final String ADMIN_CONTENT_UPDATE_SQL_ERROR = "admin.operation.update.content.failed.sql.error";
    public static final String ADMIN_CONTENT_CREATE_SUCCESSFUL = "admin.operation.successful.create.content";
    public static final String ADMIN_CONTENT_CREATE_WRONG_PARAMETERS = "admin.operation.create.content.wrong.parameters";
    public static final String ADMIN_CONTENT_CREATE_SQL_ERROR = "admin.operation.create.content.sql.error";



    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int MAIN_PAGE_SONG_PER_PAGE = 11;
    public static final int REVIEW_PAGE_REVIEW_PER_PAGE = 4;
    public static final int COMPILATION_PAGE_REVIEW_PER_PAGE = 1;

    public static final int MAX_COMPILATION_SONG_VALUE = 50;

    private AttributeValue() {
    }
}
