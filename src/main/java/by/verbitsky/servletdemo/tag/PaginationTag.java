package by.verbitsky.servletdemo.tag;

import by.verbitsky.servletdemo.controller.command.AttributeName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * Pagination tag class describes custom jsp tag. It draws page numerations for content
 * and page controls: next page, previous page, last page, first page and specific page number
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PaginationTag extends TagSupport {
    private static Logger logger = LogManager.getLogger();
    private String linkValue;
    private int currentPage;
    private int totalPageCount;

    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        Locale locale = (Locale) session.getAttribute(AttributeName.SESSION_LOCALE);
        if (currentPage <= totalPageCount) {
            try {
                if (totalPageCount > 1) {
                    pageContext.getOut().write("<div class=\"btn-toolbar btn-group-sm justify-content-center song-pagination-block\">\n");
                    if (locale.equals(Locale.ENGLISH)) {
                        pageContext.getOut().write("<span class=\"pagination-info\">Page " + currentPage + " from " + totalPageCount + " : </span>\n");
                    } else {
                        pageContext.getOut().write("<span class=\"pagination-info\">Cтраница " + currentPage + " из " + totalPageCount + ": </span>\n");
                    }
                    pageContext.getOut().write("<div class=\"pagination-controls\">\n");
                    if (totalPageCount > 4) {
                        pageContext.getOut().write(" <div class=\"btn-group  btn-group-sm\" role=\"group\" aria-label=\"Third group\">\n");
                        if (currentPage == 1) {
                            pageContext.getOut().write("<span class=\"pagination-link inactive-page-link\"> &lt;&lt; </span>");
                        } else {
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber=1\"> &lt;&lt; </a>\n");
                        }
                        pageContext.getOut().write("</div>\n");
                    }
                    pageContext.getOut().write("<div class=\"btn-group mr-2\" role=\"group\" aria-label=\"First group\">\n");
                    if (currentPage == 1) {
                        pageContext.getOut().write("<span class=\"pagination-link inactive-page-link\"> &lt; </span>");
                    } else {
                        pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage - 1) +"\"> &lt; </a>\n");
                    }
                    pageContext.getOut().write("</div>\n");
                    pageContext.getOut().write("<div class=\"btn-group mr-2 btn-group-sm\" role=\"group\" aria-label=\"Second group\">\n");
                    if (totalPageCount == 2) {
                        if (currentPage == 1) {
                            pageContext.getOut().write("<span class=\"pagination-link active-page-link\">" + currentPage + "</span>\n");
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage + 1) +"\">" + (currentPage + 1) + "</a>\n");
                            pageContext.getOut().write("</div>\n");

                        } else {
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage - 1) +"\">" + (currentPage - 1) + "</a>\n");
                            pageContext.getOut().write("<span class=\"pagination-link active-page-link\">" + currentPage + "</span>\n");
                            pageContext.getOut().write("</div>\n");
                        }
                    } else {
                        if (currentPage == 1) {
                            pageContext.getOut().write("<span class=\"pagination-link active-page-link\">" + currentPage + "</span>\n");
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage + 1) +"\">" + (currentPage + 1) + "</a>\n");
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage +2) +"\">" + (currentPage + 2) + "</a>\n");
                            pageContext.getOut().write("</div>\n");
                        }
                        if (currentPage == totalPageCount) {
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage - 2) +"\">" + (currentPage - 2) + "</a>\n");
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage - 1) +"\">" + (currentPage - 1) + "</a>\n");
                            pageContext.getOut().write("<span class=\"pagination-link active-page-link\">" + currentPage + "</span>\n");
                            pageContext.getOut().write("</div>\n");
                        }
                        if (currentPage > 1 && currentPage < totalPageCount) {
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage - 1) +"\">" + (currentPage - 1) + "</a>\n");
                            pageContext.getOut().write("<span class=\"pagination-link active-page-link\">" + currentPage + "</span>\n");
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage + 1) +"\">" + (currentPage + 1) + "</a>\n");
                            pageContext.getOut().write("</div>\n");
                        }
                    }
                    pageContext.getOut().write("<div class=\"btn-group  btn-group-sm\" role=\"group\" aria-label=\"Third group\">\n");
                    if (currentPage == totalPageCount) {
                        pageContext.getOut().write("<span class=\"pagination-link inactive-page-link\"> &gt; </span>");
                    } else {
                        pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ (currentPage + 1) +"\"> &gt; </a>\n");
                    }
                    pageContext.getOut().write("</div>\n");
                    if (totalPageCount > 4) {
                        pageContext.getOut().write("<div class=\"btn-group mr-2\" role=\"group\" aria-label=\"First group\">\n");
                        if (currentPage == totalPageCount) {
                            pageContext.getOut().write("<span class=\"pagination-link inactive-page-link\"> &gt;&gt; </span>");
                        } else {
                            pageContext.getOut().write("<a class=\"pagination-link\" href=\"" + linkValue + "&pageNumber="+ totalPageCount +"\"> &gt;&gt; </a>\n");
                        }
                        pageContext.getOut().write("</div>\n");
                    }
                    pageContext.getOut().write("</div>\n");
                    pageContext.getOut().write("</div>\n");
                }
            } catch (IOException e) {
                logger.log(Level.ERROR,"Pagination tag: error while processing tag body", e);
                throw new JspException("Pagination tag: error while processing tag body", e);
            }
        }
        return EVAL_BODY_INCLUDE;
    }
}