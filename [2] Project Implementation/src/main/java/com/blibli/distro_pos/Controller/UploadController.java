package com.blibli.distro_pos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/Users/raditia/Pictures/temp/";

    @GetMapping("/upload")
    public ModelAndView upload() {

        return new ModelAndView("upload/upload");
    }

    @PostMapping("/upload")
    public String imageUpload(@RequestParam("file")MultipartFile file,
                              RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {

            redirectAttributes.addFlashAttribute("message", "Please select an image to upload");

            return "redirect:/uploadStatus";
        }

        try {

            //Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
        catch (Exception e) {

            e.toString();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public ModelAndView uploadStatus() {

        return new ModelAndView("upload/uploadStatus");
    }
}
