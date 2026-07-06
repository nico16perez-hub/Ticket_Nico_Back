package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.domain.Work;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.request.WorkRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.dto.response.WorkResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.TicketRepository;
import com.slmas.Sl.repository.WorkRepository;
import com.slmas.Sl.service.WorkService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {
    WorkRepository workRepository;

    public WorkServiceImpl(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }
    @Override
    public Long createWork(WorkRequestDto workRequestDto, Images images) throws RepositoryException {
        Work work = MapDtoToWork(workRequestDto);
        work.setImage(images.getFullImage());
        return workRepository.createWork(work);
    }

    @Override
    public WorkResponseDto getWorkById(Long id) throws RepositoryException {
        Work work = workRepository.getWorkById(id);
        return MapWorkToDto(work);
    }

    @Override
    public List<WorkResponseDto> getFilteredWorks(Date startDate, Date endDate) throws RepositoryException {
        List<Work> works = workRepository.getFilteredWorks(startDate, endDate);
        return works.stream().map(this::MapWorkToDto).collect(Collectors.toList());
    }

    @Override
    public List<WorkResponseDto> downloadWorks(Date startDate, Date endDate) throws RepositoryException {
        List<Work> tickets = workRepository.downloadWorks(startDate, endDate);
        return tickets.stream().map(this::MapWorkToDto).collect(Collectors.toList());
    }

    @Override
    public List<WorkResponseDto> getClosedByMeWorks(Date startDate, Date endDate, String solvedBy) throws RepositoryException {
        return List.of();
    }

    @Override
    public List<WorkResponseDto> getUserWorks(Date startDate, Date endDate, Long userId) throws RepositoryException {
        List<Work> works = workRepository.getUserWorks(startDate, endDate, userId);
        return works.stream().map(this::MapWorkToDto).collect(Collectors.toList());
    }

    @Override
    public String editWork(String description, Long workId) throws RepositoryException {
        Integer response = workRepository.editWork(description, workId);
        if (response == 1) {
            return "Trabajo editado con éxito.";
        } else {
            throw new RuntimeException("Error al editar el trabajo");
        }
    }

    private WorkResponseDto MapWorkToDto(Work work) {
        WorkResponseDto workResponseDto = new WorkResponseDto();
        workResponseDto.setId(work.getId());
        workResponseDto.setUserId(work.getUserId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(work.getDate());
        workResponseDto.setDate(formattedDate);
        workResponseDto.setTitle(work.getTitle());
        workResponseDto.setDescription(work.getDescription());
        workResponseDto.setImage(work.getImage());
        workResponseDto.setUserName(work.getUserName());
        return workResponseDto;
    }

    private Work MapDtoToWork(WorkRequestDto workRequestDto) {
        Work work = new Work();
        work.setId(workRequestDto.getId());
        work.setUserId(workRequestDto.getUserId());
        work.setUserName(workRequestDto.getUserName());
        work.setDate(new Date());
        work.setTitle(workRequestDto.getTitle());
        work.setDescription(workRequestDto.getDescription());
        return work;
    }
}
