package org.unibl.etf.repositories;

import org.springframework.stereotype.Repository;
import org.unibl.etf.models.dto.Artifact;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArtifactRepository {

    private static final String ARTIFACTS_PATH = "artifacts";

    public List<Artifact> getVirtualVisitArtifacts(Integer visitId) throws IOException {
        List<Artifact> retList = new ArrayList<>();
        File artifactsDir = new File(ARTIFACTS_PATH + File.separator + visitId);
        System.out.println(artifactsDir.getAbsolutePath());
        for (File artifact : artifactsDir.listFiles()) {
            if ("video.yt".equals(artifact.getName())) {
                String uri = Files.readAllLines(artifact.toPath()).get(0);
                retList.add(new Artifact("ytvideo", "ytvideo", null, uri));
            }else if (artifact.getName().endsWith("mp4")){
                byte[] bytes = Files.readAllBytes(artifact.toPath());
                 retList.add(new Artifact(artifact.getName(), "mp4", bytes, null));
            }else{
                byte[] bytes = Files.readAllBytes(artifact.toPath());
                retList.add(new Artifact(artifact.getName(), "image", bytes, null));
            }
        }
        return retList;
    }
}
