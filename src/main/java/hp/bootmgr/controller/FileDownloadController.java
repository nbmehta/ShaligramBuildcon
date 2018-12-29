package hp.bootmgr.controller;

import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.ProjectFile;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class FileDownloadController {

    @Autowired
    private ProjectPropertyPlanService projectPropertyPlanService;

    @Autowired
    private ProjectService projectService;

    private static Logger logger = Logger.getLogger(FileDownloadController.class);

    @RequestMapping("/uploads/{id}/{project_block}/{fileName:.+}")
    public void downloadFile(HttpSession session, HttpServletResponse response, @PathVariable("project_block") String path, @PathVariable("fileName") String fileName, @PathVariable("id") Integer id) {
        logger.debug("Requested: " + path + fileName);
        logger.debug("Mapped file to: " + session.getServletContext().getRealPath("/") + "uploads" + File.separator + "property_plan" + File.separator + path + File.separator + fileName);
        File file = new File(session.getServletContext().getRealPath("/") + "uploads" + File.separator + "property_plan" + File.separator + path + File.separator + fileName);
        try {
            if(!file.exists()) {
                logger.error("File not found. Aborting download...!!!");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested file was not found on server");
                return;
            }
            response.setContentLength((int) file.length());
            // Set Content-Disposition to inline so that browser can show file whenever possible instead of downloading file
            //response.setHeader("Content-Disposition","attachment; filename=\"" + file.getName() +"\"");
            response.setHeader("Content-Disposition","inline; filename=\"" + file.getName() +"\"");
            response.setContentType(projectPropertyPlanService.getById(id).getMimeType());
            InputStream inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            logger.debug("Successfully sent file...!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"admin/fetchProjectFile/{projectName}/{id}", "user/fetchProjectFile/{projectName}/{id}"})
    public void fetchProjectFile(@PathVariable("projectName") String projectName, @PathVariable("id") Integer fileId, HttpSession session, HttpServletResponse response) {
        logger.debug("ProjectFile Request: " + projectName + " " + fileId);
        ProjectFile file = projectService.getProjectFile(fileId);
        String path = session.getServletContext().getRealPath("/") + "uploads" + File.separator + "project_files" + File.separator + projectName + File.separator + file.getFilename();
        File fileTodownload = new File(path);
        try {
            if(!fileTodownload.exists()) {
                logger.error("File not found. Aborting download...!!!");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested file was not found on server");
                return;
            }
            response.setContentLength((int) fileTodownload.length());
            // Set Content-Disposition to inline so that browser can show file whenever possible instead of downloading file
            //response.setHeader("Content-Disposition","attachment; filename=\"" + file.getName() +"\"");
            response.setHeader("Content-Disposition","inline; filename=\"" + fileTodownload.getName() +"\"");
            response.setContentType(file.getMimeType());
            InputStream inputStream = new FileInputStream(fileTodownload);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            logger.debug("Successfully sent file...!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
