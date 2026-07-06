package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Pending;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.List;

public interface PendingRepository {
    public Integer createPending(String notes) throws RepositoryException;
    public Integer deletePending(Long id) throws RepositoryException;
    public List<Pending> getAllPending() throws RepositoryException;
}
