package org.unibl.etf.services.impl;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.exceptions.HttpException;
import org.unibl.etf.exceptions.NotFoundException;
import org.unibl.etf.models.dto.Artifact;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.responses.VirtualVisitResponse;
import org.unibl.etf.repositories.ArtifactRepository;
import org.unibl.etf.repositories.VirtualVisitEntityRepository;
import org.unibl.etf.services.VirtualVisitService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualVisitServiceImpl implements VirtualVisitService {


    private final ArtifactRepository artifactRepository;
    private final ModelMapper modelMapper;
    private final VirtualVisitEntityRepository virtualVisitEntityRepository;
    private static final String ARTIFACTS_FOLDER = "artifacts";

    public VirtualVisitServiceImpl(ArtifactRepository artifactRepository, VirtualVisitEntityRepository virtualVisitEntityRepository, ModelMapper modelMapper) {
        this.artifactRepository = artifactRepository;
        this.virtualVisitEntityRepository = virtualVisitEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VirtualVisitResponse> getAll() {
        return virtualVisitEntityRepository.findAll().stream().map(v -> modelMapper.map(v, VirtualVisitResponse.class)).collect(Collectors.toList());
    }

    @Override
    public VirtualVisitResponse addVirtualVisit(VirtualVisitEntity virtualVisit, MultipartFile[] images, MultipartFile mp4Video, String ytVideoLink) throws IOException {
        VirtualVisitEntity virtualVisitEntity = virtualVisitEntityRepository.save(virtualVisit);
        Path artifacts = Paths.get(ARTIFACTS_FOLDER + "/" + virtualVisitEntity.getId());
        FileUtils.deleteDirectory(artifacts.toFile());
        artifacts.toFile().mkdirs();
        System.out.println(artifacts);
        for (MultipartFile image : images) {
            Files.copy(image.getInputStream(), artifacts.resolve(image.getOriginalFilename()));
        }
        if (mp4Video != null) {
            Files.copy(mp4Video.getInputStream(), artifacts.resolve(mp4Video.getOriginalFilename()));
        } else {
            PrintWriter pw = new PrintWriter(new FileWriter(artifacts.resolve("video.yt").toFile()));
            pw.println(ytVideoLink);
            pw.close();
        }

        return modelMapper.map(virtualVisitEntity, VirtualVisitResponse.class);
    }



    @Override
    public Long getVisitRemainingTime(Integer id) {
        VirtualVisitEntity virtualVisit = virtualVisitEntityRepository.findById(id).orElseThrow(() -> new NotFoundException("Virtual visit not found!"));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.of(virtualVisit.getDate().toLocalDate(), virtualVisit.getStartTime().toLocalTime()).plusMinutes(virtualVisit.getDuration());

        return Duration.between(now, endDateTime).getSeconds()*1000;
    }

    @Override
    public void deleteVirtualVisit(Integer visitId) throws IOException {
        Path artifacts = Paths.get(ARTIFACTS_FOLDER + "/" + visitId);
        FileUtils.deleteDirectory(artifacts.toFile());
        virtualVisitEntityRepository.deleteById(visitId);
    }

    @Override
    public List<Artifact> getVirtualVisitArtifacts(Integer visitId, Authentication authentication) {
        try {
            return artifactRepository.getVirtualVisitArtifacts(visitId);
        } catch (IOException e) {
            throw new HttpException("An error occurred while reading artifacts.");
        }
    }
}
