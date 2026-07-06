package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Guide;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.GuideRepository;
import com.slmas.Sl.service.GuideService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImpl implements GuideService {

    GuideRepository guideRepository;

    public GuideServiceImpl(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    @Override
    public String createGuide(Guide guide) throws RepositoryException {
        Integer response = guideRepository.createGuide(guide);
        if (response == 1) {
            return "Guia creada con exito.";
        } else {
            throw new RuntimeException("Error al crear la guia");
        }
    }

    @Override
    public String updateGuide(Guide guide) throws RepositoryException {
        Integer response = guideRepository.updateGuide(guide);
        if (response == 1) {
            return "Guia editada con exito.";
        } else {
            throw new RuntimeException("Error al editar la guia");
        }
    }

    @Override
    public List<Guide> getAllGuides() throws RepositoryException {
       return guideRepository.getAllGuides();
    }

    @Override
    public List<Guide> getUserGuides() throws RepositoryException {
        return guideRepository.getUserGuides();
    }

    @Override
    public Guide getById(Long id) throws RepositoryException {
        return guideRepository.getById(id);
    }

    @Override
    public String deleteById(Long id) throws RepositoryException {
        Integer response = guideRepository.deleteById(id);
        if (response == 1) {
            return "Guia eliminada con exito.";
        } else {
            throw new RuntimeException("Error al eliminar la guia");
        }
    }
}
