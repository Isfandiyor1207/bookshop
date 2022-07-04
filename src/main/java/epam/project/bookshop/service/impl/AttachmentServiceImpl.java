package epam.project.bookshop.service.impl;

import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AttachmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger logger= LogManager.getLogger();
    private static final AttachmentServiceImpl instance = new AttachmentServiceImpl();

    public static AttachmentServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(Map<String, String> update) throws ServiceException {
        return false;
    }

    @Override
    public List<Genre> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<Genre> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean add(Map<String, String> entity) throws ServiceException {
        return false;
    }
}
