package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Work;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

public interface WorkRepository {
    Long createWork (Work ticket) throws RepositoryException;
    List<Work> getUserWorks(Date startDate, Date endDate, Long userId) throws RepositoryException;
    Work getWorkById (Long id) throws RepositoryException;
    List<Work> getFilteredWorks(Date startDate, Date endDate) throws RepositoryException;
    List<Work> downloadWorks(Date startDate, Date endDate) throws RepositoryException;
    List<Work> getClosedByMeWorks(Date startDate, Date endDate, String solvedBy) throws RepositoryException;
    Integer editWork (String description, Long workId) throws RepositoryException;
}
