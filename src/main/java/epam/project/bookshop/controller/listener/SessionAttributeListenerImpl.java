package epam.project.bookshop.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
    static Logger logger= LogManager.getLogger();

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        logger.info(" ----------> attributeAdded: " + sbe.getSession().getAttribute("username"));
        logger.info(" ----------> attributeAdded: " + sbe.getSession().getAttribute("password"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        logger.info(" ----------> attributeReplaced: " + sbe.getSession().getAttribute("username"));
        logger.info(" ----------> attributeReplaced: " + sbe.getSession().getAttribute("password"));
    }
}
