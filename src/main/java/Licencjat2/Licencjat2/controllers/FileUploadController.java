package Licencjat2.Licencjat2.controllers;

import Licencjat2.Licencjat2.model.File;
import Licencjat2.Licencjat2.model.Kurs;
import Licencjat2.Licencjat2.model.MagazynKursu;
import Licencjat2.Licencjat2.model.MagazynPlikow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping
public class FileUploadController {
    @Autowired
    MagazynPlikow mp;
    @Autowired
    MagazynKursu mk;

    @PostMapping("/addFile")
    public RedirectView uploadFile(@RequestParam("file")MultipartFile file,@RequestParam("courseId")Long courseId){
        try {
           File files = new Licencjat2.Licencjat2.model.File((String) file.getOriginalFilename(), file.getContentType(), file.getBytes());
           mk.znajdz(courseId).getFile().add(files);
           files.setKurs(mk.znajdz(courseId));
           mp.save(files);
        }catch (Exception e){e.printStackTrace();}

        return new RedirectView("course?courseId="+courseId);

    }
    @PostMapping("/addCourseImage")
    public RedirectView uploadImage(@RequestParam("file")MultipartFile file,@RequestParam("courseId")Long courseId){
        try {
            mk.znajdz(courseId).setData(file.getBytes());
            mk.save(mk.znajdz(courseId));
        }catch (Exception e){e.printStackTrace();}

        return new RedirectView("course?courseId="+courseId);

    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
        File file =mp.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getDocName()+"\"")
                .body(new ByteArrayResource(file.getData()));



    }
}
