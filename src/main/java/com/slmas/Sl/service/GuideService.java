package com.slmas.Sl.service;

import com.slmas.Sl.domain.Guide;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.List;

public interface GuideService {
    String createGuide (Guide guide) throws RepositoryException;
    String updateGuide (Guide guide) throws RepositoryException;
    List<Guide> getAllGuides () throws RepositoryException;
    List<Guide> getUserGuides () throws RepositoryException;
    Guide getById(Long id) throws RepositoryException;
    String deleteById(Long id) throws RepositoryException;
}
