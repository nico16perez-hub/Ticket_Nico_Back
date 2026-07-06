package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.Pending;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.PendingRepository;
import com.slmas.Sl.service.PendingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingServiceImpl implements PendingService {

    PendingRepository pendingRepository;

    public PendingServiceImpl(PendingRepository pendingRepository) {
        this.pendingRepository = pendingRepository;
    }

    @Override
    public String createPending(String notes) throws RepositoryException {
        Integer response = pendingRepository.createPending(notes);
        if (response == 1) {
            return "Tarea creada con exito.";
        } else {
            throw new RuntimeException("Error al crear la tarea");
        }
    }

    @Override
    public String deletePending(Long id) throws RepositoryException {
        Integer response = pendingRepository.deletePending(id);
        if (response == 1) {
            return "Tarea completada con exito.";
        } else {
            throw new RuntimeException("Error al completar la tarea");
        }
    }

    @Override
    public List<Pending> getAll() throws RepositoryException {
        return pendingRepository.getAllPending();
    }
}
