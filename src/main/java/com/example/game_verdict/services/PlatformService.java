package com.example.game_verdict.services;

import com.example.game_verdict.dtos.PlatformDTO;
import com.example.game_verdict.entities.Platform;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.PlatformMapper;
import com.example.game_verdict.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final PlatformMapper platformMapper;

    @Autowired
    public PlatformService(PlatformRepository platformRepository,
                           PlatformMapper platformMapper) {
        this.platformRepository = platformRepository;
        this.platformMapper = platformMapper;
    }

    public List<PlatformDTO> getAllPlatforms() {
        List<Platform> platforms = platformRepository.findAll();
        return platformMapper.toDTOs(platforms);
    }

    public PlatformDTO getPlatformById(Long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not found with id: " + id));
        return platformMapper.toDTO(platform);
    }

    public PlatformDTO createPlatform(PlatformDTO platformDTO) {
        Platform platform = platformMapper.toEntity(platformDTO);
        Platform savedPlatform = platformRepository.save(platform);
        return platformMapper.toDTO(savedPlatform);
    }

    public PlatformDTO updatePlatform(Long id, PlatformDTO platformDTO) {
        Platform existingPlatform = platformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Platform not found with id: " + id));

        existingPlatform.setName(platformDTO.getName());
        Platform updatedPlatform = platformRepository.save(existingPlatform);
        return platformMapper.toDTO(updatedPlatform);
    }

    public void deletePlatform(Long id) {
        if (!platformRepository.existsById(id)) {
            throw new ResourceNotFoundException("Platform not found with id: " + id);
        }
        platformRepository.deleteById(id);
    }
}