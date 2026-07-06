package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

public interface TicketRepository {
    Long createTicket (Ticket ticket) throws RepositoryException;
    List<Ticket> getUserTickets(Date startDate, Date endDate, Long userId) throws RepositoryException;
    Ticket getTicketById (Long id) throws RepositoryException;
    List<Ticket> getFilteredTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException;
    List<Ticket> downloadTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException;
    List<Ticket> getClosedByMeTickets(Date startDate, Date endDate, String solvedBy) throws RepositoryException;
    Integer closeTicket (Ticket ticket) throws RepositoryException;
    Integer editTicketSolution (String solution, Long ticketId) throws RepositoryException;
    List<Ticket> getImportantTickets() throws RepositoryException;
    Integer removeFromImportant(Long id) throws RepositoryException;
}
