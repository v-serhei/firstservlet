package by.verbitsky.servletdemo.controller.command;

public class AttributeValue {
    public static final String DEFAULT_COMMAND_ERROR_MESSAGE = "default.command.error.message";
    public static final String EMPTY_COMMAND_ERROR_MESSAGE = "empty.command.error.message";
    //values for result page
    public static final String CREATE_ORDER = "operation.create.order";
    public static final String PAY_ORDER = "operation.pay.order";
    public static final String OPERATION_COMPLETED = "operation.completed";
    public static final String OPERATION_FAILED = "operation.failed";
    public static final String EMPTY_ORDER_LIST = "operation.message.empty.order.list";
    public static final String ORDER_PAYMENT_SUCCESSFUL = "operation.message.successful.order.payment";
    public static final String BUTTON_CAPTION_PROFILE = "operation.btn.caption.profile";
    public static final String BUTTON_CAPTION_BACK = "operation.btn.caption.back";

    public static final String REMOVE_SONG_FROM_ORDER = "operation.remove.song.from.order";
    public static final String ORDER_NOT_EXIST = "operation.message.order.not.exist";




    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int MAIN_PAGE_SONG_PER_PAGE = 11;
    public static final int REVIEW_PAGE_REVIEW_PER_PAGE = 4;
    public static final int COMPILATION_PAGE_REVIEW_PER_PAGE = 1;

    public static final int MAX_COMPILATION_SONG_VALUE = 50;

    private AttributeValue() {
    }
}
