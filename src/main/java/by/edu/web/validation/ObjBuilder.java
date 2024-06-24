package by.edu.web.validation;

import by.edu.web.service.ServiceException;

public interface ObjBuilder <T> {
    T build() throws ServiceException;
}

