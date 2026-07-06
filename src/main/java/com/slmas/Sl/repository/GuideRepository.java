package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Guide;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.List;

public interface GuideRepository {
    public Integer createGuide(Guide guide) throws RepositoryException;
    public Integer updateGuide(Guide guide) throws RepositoryException;
    public List<Guide> getAllGuides() throws RepositoryException;
    public List<Guide> getUserGuides() throws RepositoryException;
    public Guide getById (Long id) throws RepositoryException;
    public Integer deleteById (Long id) throws RepositoryException;
}
