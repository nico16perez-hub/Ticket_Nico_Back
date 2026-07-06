package com.slmas.Sl.service;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

public interface TicketService {
    Long createTicket(TicketRequestDto ticketRequestDto, Images images) throws RepositoryException;

    TicketResponseDto getTicketById(Long id) throws RepositoryException;

    List<TicketResponseDto> getFilteredTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException;
    List<TicketResponseDto> downloadTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException;
    List<TicketResponseDto> getClosedByMeTickets(Date startDate, Date endDate, String solvedBy) throws RepositoryException;
    List<TicketResponseDto> getImportantTickets() throws RepositoryException;
    List<TicketResponseDto> getUserTickets(Date startDate, Date endDate, Long userId) throws RepositoryException;

    String closeTicket(TicketRequestDto ticketRequestDto) throws RepositoryException;
    String removeFromImportant(Long id) throws RepositoryException;
    String editTicketSolution(String solution, Long ticketId) throws RepositoryException;

}
