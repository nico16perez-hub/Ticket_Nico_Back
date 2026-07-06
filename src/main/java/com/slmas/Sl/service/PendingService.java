package com.slmas.Sl.service;

import com.slmas.Sl.domain.Pending;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.List;

public interface PendingService {
    public String createPending(String notes) throws RepositoryException;
    public String deletePending(Long id) throws RepositoryException;
    public List<Pending> getAll() throws RepositoryException;
}
