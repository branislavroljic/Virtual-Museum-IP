package org.unibl.etf.services;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.models.dto.Artifact;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.responses.VirtualVisitResponse;

import java.io.IOException;
import java.util.List;

public interface VirtualVisitService {

    List<VirtualVisitResponse> getAll();

    VirtualVisitResponse addVirtualVisit(VirtualVisitEntity virtualVisit, MultipartFile[] images, MultipartFile mp4Video, String ytVideoLink) throws IOException;

    Long getVisitRemainingTime(Integer id);

    void deleteVirtualVisit(Integer visitId) throws IOException;

    List<Artifact> getVirtualVisitArtifacts(Integer visitId, Authentication authentication);
}
