package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.TicketRepository;
import com.slmas.Sl.service.TicketService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Long createTicket(TicketRequestDto ticketRequestDto, Images images) throws RepositoryException {
        Ticket ticket = MapDtoToTicket(ticketRequestDto);
        ticket.setImage(images.getFullImage());
        return ticketRepository.createTicket(ticket);
    }

    @Override
    public TicketResponseDto getTicketById(Long id) throws RepositoryException {
        Ticket ticket = ticketRepository.getTicketById(id);
        return MapTicketToDto(ticket);
    }

    @Override
    public List<TicketResponseDto> getFilteredTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getFilteredTickets(startDate, endDate, area, closed);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> downloadTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.downloadTickets(startDate, endDate, area, closed);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> getUserTickets(Date startDate, Date endDate, Long userId) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getUserTickets(startDate, endDate, userId);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDto> getImportantTickets() throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getImportantTickets();
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    @Override
    public String removeFromImportant(Long id) throws RepositoryException {
        Integer response = ticketRepository.removeFromImportant(id);
        if (response == 1) {
            return "Ticket quitado con exito.";
        } else {
            throw new RuntimeException("Error al quitar el ticket");
        }
    }

    @Override
    public String closeTicket (TicketRequestDto ticketRequestDto) throws RepositoryException {
        Integer response = ticketRepository.closeTicket(MapDtoToTicket(ticketRequestDto));
        if (response == 1) {
            return "Ticket cerrado con exito.";
        } else {
            throw new RuntimeException("Error al cerrar el ticket");
        }
    }

    @Override
    public String editTicketSolution (String solution, Long ticketId) throws RepositoryException {
        Integer response = ticketRepository.editTicketSolution(solution, ticketId);
        if (response == 1) {
            return "Ticket editado con éxito.";
        } else {
            throw new RuntimeException("Error al editar el ticket");
        }
    }

    @Override
    public List<TicketResponseDto> getClosedByMeTickets(Date startDate, Date endDate, String solvedBy) throws RepositoryException {
        List<Ticket> tickets = ticketRepository.getClosedByMeTickets(startDate, endDate, solvedBy);
        return tickets.stream().map(this::MapTicketToDto).collect(Collectors.toList());
    }

    private TicketResponseDto MapTicketToDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setUserId(ticket.getUserId());
        ticketResponseDto.setArea(ticket.getArea());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(ticket.getDate());
        ticketResponseDto.setDate(formattedDate);
        ticketResponseDto.setTitle(ticket.getTitle());
        ticketResponseDto.setType(ticket.getType());
        ticketResponseDto.setDescription(ticket.getDescription());
        ticketResponseDto.setSolution(ticket.getSolution());
        ticketResponseDto.setSolvedBy(ticket.getSolvedBy() == null || ticket.getSolvedBy().isBlank() ? "-" : ticket.getSolvedBy());
        if (ticket.getSolvedDate() != null) {
            SimpleDateFormat solvedDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedSolvedDate = solvedDateFormat.format(ticket.getSolvedDate());
            ticketResponseDto.setSolvedDate(formattedSolvedDate);
        }
        ticketResponseDto.setImage(ticket.getImage());
        ticketResponseDto.setClosed(ticket.isClosed());
        ticketResponseDto.setImportant(ticket.isImportant());
        ticketResponseDto.setUserName(ticket.getUserName());
        return ticketResponseDto;
    }

    private Ticket MapDtoToTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketRequestDto.getId());
        ticket.setUserId(ticketRequestDto.getUserId());
        ticket.setUserName(ticketRequestDto.getUserName());
        ticket.setArea(ticketRequestDto.getArea());
        ticket.setDate(new Date());
        ticket.setTitle(ticketRequestDto.getTitle());
        ticket.setType(ticketRequestDto.getType());
        ticket.setDescription(ticketRequestDto.getDescription());
        ticket.setSolution(ticketRequestDto.getSolution());
        ticket.setSolvedBy(ticketRequestDto.getSolvedBy());
        ticket.setSolvedDate(ticketRequestDto.getSolvedDate());
        boolean hasSolution = ticketRequestDto.getSolution() != null && !ticketRequestDto.getSolution().isBlank();
        ticket.setClosed(ticketRequestDto.isClosed() || hasSolution);
        if (ticket.isClosed() && ticket.getSolvedDate() == null) {
            ticket.setSolvedDate(new Date());
        }
        ticket.setImportant(ticketRequestDto.isImportant());
        return ticket;
    }
}
