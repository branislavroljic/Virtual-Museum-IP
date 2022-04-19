package org.unibl.etf.admin_app.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.unibl.etf.admin_app.beans.VirtualVisitBean;
import org.unibl.etf.admin_app.dao.VirtualVisitDAO;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class VirtualVisitService implements Serializable {

    public static final String YT_FILE_NAME = "video.yt";

    public List<VirtualVisitBean> getVirtualVisitsOfMuseum(String museumId) {
        try {
            return VirtualVisitDAO.selectAll(museumId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean addVirtualVisit(VirtualVisitBean visit) {
        try {
            return VirtualVisitDAO.add(visit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVirtualVisit(String id){
        try {
            return VirtualVisitDAO.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void insertArtifacts(HashMap<String, FileItem> fileItems, String visitId) throws Exception {


            URL visitDirUrl = getClass().getClassLoader().getResource("../../artifacts" + File.separator + visitId);
            File dir = new File(visitDirUrl.getPath());
            FileUtils.deleteDirectory(dir);
            if(!dir.exists())
                dir.mkdirs();

            if(fileItems.get("ytvideo") != null){
                URL ytFileUrl = getClass().getClassLoader().getResource(visitDirUrl.toString() + File.separator + YT_FILE_NAME);
                PrintWriter writer = new PrintWriter(new FileOutputStream(new File(ytFileUrl.getPath())));
                writer.println(fileItems.get("ytvideo"));
                writer.close();
                fileItems.remove("ytvideo");
            }
            for (HashMap.Entry<String, FileItem> entry : fileItems.entrySet()) {

                URL fileUrl = getClass().getClassLoader().getResource(visitDirUrl.toString() + File.separator + entry.getKey());
                File file = new File(fileUrl.getPath());
                System.out.println(file.getAbsolutePath());
                entry.getValue().write(file);
            }

    }

    public Map<String, List<String>> getArtifactsPaths(String visitId) {
        Map<String, List<String>> paths = new HashMap<>();
        File dir = new File("C:\\Users\\legion\\Desktop\\IV godina BANE\\Internet programiranje\\artifacts\\" + visitId);
        try {
            if (dir.exists()) {
                Files.list(dir.toPath()).forEach(filePath -> {
                    System.out.println("Filepath: " + filePath);
                    String ext = FilenameUtils.getExtension(filePath.toString());
                    System.out.println("fileExt: "  + ext);
                    if (ext.equals("yt")) {
                        try {
                            paths.put("yt", Collections.singletonList(Files.readAllLines(filePath).get(0)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if(ext.equals("mp4")){
                        paths.put("mp4", Collections.singletonList(filePath.toString()));
                    }else{
                        paths.computeIfAbsent("img", k -> new ArrayList<>());
                        paths.get("img").add(filePath.toString());
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
