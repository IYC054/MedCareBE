package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.repositories.FilesImageRepository;
import fpt.aptech.pjs4.services.FilesImageService;
import fpt.aptech.pjs4.services.PatientFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FilesImageServiceImpl implements FilesImageService {
    @Autowired
    private FilesImageRepository filesImageRepository;
    @Autowired
    private PatientFileService patientFileService;
    @Override
    public FilesImage createFilesImage(FilesImage filesImage) {
        return filesImageRepository.save(filesImage);
    }
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    public List<FilesImage> createFileWithImage(List<MultipartFile> files, int patient_file_id) {
        List<FilesImage> filesImages = new ArrayList<>();
        PatientFile patientFile = patientFileService.getPatientFileById(patient_file_id);
        for (MultipartFile file : files) {
            try {
                // Lưu file vào thư mục
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(fileUpload + fileName);
                Files.copy(file.getInputStream(), path);
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/image/")
                        .path(fileName)
                        .toUriString();
                // Tạo đối tượng FilesImage
                FilesImage filesImage = new FilesImage();
                filesImage.setUrlImage(imageUrl);
                filesImage.setPatientsFiles(patientFile);
                filesImages.add(filesImage);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving image file");
            }
        }
        return filesImageRepository.saveAll(filesImages); // Lưu tất cả vào cơ sở dữ liệu
    }
    @Override
    public FilesImage getFilesImageById(int id) {
        return filesImageRepository.findById(id).orElse(null);
    }

    @Override
    public List<FilesImage> getAllFilesImages() {
        return filesImageRepository.findAll();
    }

    @Override
    public List<FilesImage> getAllFilesImagesbyPatientFileId(int id) {
        return filesImageRepository.findFileImageByPatientFileId(id);
    }

    @Override
    public FilesImage updateFilesImage(int id, FilesImage filesImage) {
        if (filesImageRepository.existsById(id)) {
            filesImage.setId(id);
            return filesImageRepository.save(filesImage);
        }
        return null;
    }

    @Override
    public void deleteFilesImage(int id) {
        filesImageRepository.deleteById(id);
    }
}
