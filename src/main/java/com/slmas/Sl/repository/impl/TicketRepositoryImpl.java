package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.TicketRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    JdbcTemplate jdbcTemplate;

    public TicketRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createTicket(Ticket ticket) throws RepositoryException {
        String CREATE_TICKET = "INSERT INTO Tickets (user_id, user_name, area, ticket_date, title, type, description, image) VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE_TICKET, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, ticket.getUserId());
                ps.setString(2, ticket.getUserName());
                ps.setString(3, ticket.getArea());
                ps.setTimestamp(4, new Timestamp(ticket.getDate().getTime()));
                ps.setString(5, ticket.getTitle());
                ps.setString(6, ticket.getType());
                ps.setString(7, ticket.getDescription());
                ps.setBytes(8, ticket.getImage());
                return ps;
            }, keyHolder);
            Long ticketId = Objects.requireNonNull(keyHolder.getKey()).longValue();

            String RELATE_USER_TICKET = "INSERT INTO UserTickets (user_id, ticket_id) VALUES (?,?)";
            jdbcTemplate.update(RELATE_USER_TICKET, ticket.getUserId(), ticketId);
            return ticketId;
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getUserTickets(Date startDate, Date endDate, Long userId) throws RepositoryException {
        String GET_TICKETS = "SELECT * FROM Tickets WHERE ticket_date BETWEEN ? AND ? AND user_id = ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), userId};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT};
        try {
            return jdbcTemplate.query(GET_TICKETS, params, types, new TicketsRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getImportantTickets() throws RepositoryException {
        String GET_TICKETS = "SELECT * FROM Tickets WHERE important = true";
        try {
            return jdbcTemplate.query(GET_TICKETS, new TicketsRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }


    @Override
    public Ticket getTicketById(Long id) throws RepositoryException {
        String GET_TICKETS = "SELECT * FROM Tickets WHERE id = ?";
        int[] types = {Types.BIGINT};
        try {
            return jdbcTemplate.queryForObject(GET_TICKETS, new Object[]{id}, types, new fullTicketRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getFilteredTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException {
        String GET_FILTERED_TICKETS = "SELECT * FROM Tickets WHERE ticket_date BETWEEN ? AND ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime())};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP};
        if (!area.isEmpty()) {
            GET_FILTERED_TICKETS = GET_FILTERED_TICKETS + " AND area = ?";
            Object[] newParams = Arrays.copyOf(params, params.length + 1);
            newParams[newParams.length - 1] = area;
            params = newParams;
            int[] newTypes = Arrays.copyOf(types, types.length + 1);
            newTypes[newTypes.length - 1] = Types.VARCHAR;
            types = newTypes;
        }
        if (!closed.isEmpty()) {
            boolean isClosed;
            isClosed = closed.equals("true");
            GET_FILTERED_TICKETS = GET_FILTERED_TICKETS + " AND closed = ?";
            Object[] newParams = Arrays.copyOf(params, params.length + 1);
            newParams[newParams.length - 1] = isClosed;
            params = newParams;
            int[] newTypes = Arrays.copyOf(types, types.length + 1);
            newTypes[newTypes.length - 1] = Types.BOOLEAN;
            types = newTypes;
        }
        try {
            return jdbcTemplate.query(GET_FILTERED_TICKETS, params, types, new TicketsRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> downloadTickets(Date startDate, Date endDate, String area, String closed) throws RepositoryException {
        String GET_FILTERED_TICKETS = "SELECT * FROM Tickets WHERE ticket_date BETWEEN ? AND ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime())};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP};
        if (!area.isEmpty()) {
            GET_FILTERED_TICKETS = GET_FILTERED_TICKETS + " AND area = ?";
            Object[] newParams = Arrays.copyOf(params, params.length + 1);
            newParams[newParams.length - 1] = area;
            params = newParams;
            int[] newTypes = Arrays.copyOf(types, types.length + 1);
            newTypes[newTypes.length - 1] = Types.VARCHAR;
            types = newTypes;
        }
        if (!closed.isEmpty()) {
            boolean isClosed;
            isClosed = closed.equals("true");
            GET_FILTERED_TICKETS = GET_FILTERED_TICKETS + " AND closed = ?";
            Object[] newParams = Arrays.copyOf(params, params.length + 1);
            newParams[newParams.length - 1] = isClosed;
            params = newParams;
            int[] newTypes = Arrays.copyOf(types, types.length + 1);
            newTypes[newTypes.length - 1] = Types.BOOLEAN;
            types = newTypes;
        }
        try {
            return jdbcTemplate.query(GET_FILTERED_TICKETS, params, types, new fullTicketRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer closeTicket(Ticket ticket) throws RepositoryException {
        String CLOSE_TICKET = "UPDATE Tickets SET solution = ?, solved_by = ?, solved_date = ?, closed = true, important = ? WHERE id = ?";
        Timestamp solvedDate = Timestamp.valueOf(LocalDateTime.now());
        try {
            return jdbcTemplate.update(CLOSE_TICKET, ticket.getSolution(), ticket.getSolvedBy(), solvedDate, ticket.isImportant(), ticket.getId());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer removeFromImportant (Long id) throws RepositoryException {
        String REMOVE_FROM_IMPORTANT = "UPDATE Tickets SET important = false WHERE id = ?";
        try {
            return jdbcTemplate.update(REMOVE_FROM_IMPORTANT, id);
        } catch (Exception e){
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer editTicketSolution(String solution, Long ticketId) throws RepositoryException {
        String EDIT_SOLUTION = "UPDATE Tickets SET solution = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(EDIT_SOLUTION, solution, ticketId);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getClosedByMeTickets(Date startDate, Date endDate, String solvedBy) throws RepositoryException {
        String GET_FILTERED_TICKETS = "SELECT * FROM Tickets WHERE ticket_date BETWEEN ? AND ? AND solved_by = ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), solvedBy};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR};
        try {
            return jdbcTemplate.query(GET_FILTERED_TICKETS, params, types, new TicketsRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    static class TicketsRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setDate(rs.getTimestamp("ticket_date"));
            ticket.setTitle(rs.getString("title"));
            ticket.setArea(rs.getString("area"));
            ticket.setClosed(rs.getBoolean("closed"));

            return ticket;
        }
    }


    static class fullTicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setUserId(rs.getLong("user_id"));
            ticket.setUserName(rs.getString("user_name"));
            ticket.setArea(rs.getString("area"));
            ticket.setDate(rs.getTimestamp("ticket_date"));
            ticket.setTitle(rs.getString("title"));
            ticket.setType(rs.getString("type"));
            ticket.setDescription(rs.getString("description"));
            ticket.setSolution(rs.getString("solution"));
            ticket.setSolvedBy(rs.getString("solved_by"));
            ticket.setSolvedDate(rs.getTimestamp("solved_date"));
            ticket.setImage(rs.getBytes("image"));
            ticket.setClosed(rs.getBoolean("closed"));

            return ticket;
        }
    }
}
