package com.slmas.Sl.service;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.request.WorkRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.dto.response.WorkResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

public interface WorkService {
    Long createWork(WorkRequestDto workRequestDto, Images images) throws RepositoryException;
    WorkResponseDto getWorkById(Long id) throws RepositoryException;
    List<WorkResponseDto> getFilteredWorks(Date startDate, Date endDate) throws RepositoryException;
    List<WorkResponseDto> downloadWorks(Date startDate, Date endDate) throws RepositoryException;
    List<WorkResponseDto> getClosedByMeWorks(Date startDate, Date endDate, String solvedBy) throws RepositoryException;
    List<WorkResponseDto> getUserWorks(Date startDate, Date endDate, Long userId) throws RepositoryException;
    String editWork(String solution, Long workId) throws RepositoryException;

}
