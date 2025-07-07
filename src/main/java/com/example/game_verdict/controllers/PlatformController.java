package com.example.game_verdict.controllers;

import com.example.game_verdict.dtos.PlatformDTO;
import com.example.game_verdict.services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService platformService;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getAllPlatforms() {
        List<PlatformDTO> platforms = platformService.getAllPlatforms();
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformDTO> getPlatformById(@PathVariable Long id) {
        PlatformDTO platform = platformService.getPlatformById(id);
        return ResponseEntity.ok(platform);
    }

    @PostMapping
    public ResponseEntity<PlatformDTO> createPlatform(@RequestBody PlatformDTO platformDTO) {
        PlatformDTO createdPlatform = platformService.createPlatform(platformDTO);
        return new ResponseEntity<>(createdPlatform, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformDTO> updatePlatform(@PathVariable Long id,
                                                      @RequestBody PlatformDTO platformDTO) {
        PlatformDTO updatedPlatform = platformService.updatePlatform(id, platformDTO);
        return ResponseEntity.ok(updatedPlatform);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        platformService.deletePlatform(id);
        return ResponseEntity.noContent().build();
    }
}