package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.CvImage;
import fpt.aptech.pjs4.entities.Doctor;
import fpt.aptech.pjs4.entities.PatientFile;
import fpt.aptech.pjs4.repositories.CvImageRepository;
import fpt.aptech.pjs4.services.CvImageService;
import fpt.aptech.pjs4.services.DoctorService;
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
public class CvImageServiceImpl implements CvImageService {
    @Autowired
    private CvImageRepository filesImageRepository;
    @Autowired
    private DoctorService patientFileService;
    @Override
    public CvImage createCvImages(CvImage filesImage) {
        return filesImageRepository.save(filesImage);
    }
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    public List<CvImage> createFileWithImage(List<MultipartFile> files, int patient_file_id) {
        List<CvImage> filesImages = new ArrayList<>();
        Doctor patientFile = patientFileService.getDoctorById(patient_file_id);
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
                // Tạo đối tượng CvImage
                CvImage filesImage = new CvImage();
                filesImage.setUrlImage(imageUrl);
                filesImage.setDoctorFile(patientFile);
                filesImages.add(filesImage);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving image file");
            }
        }
        return filesImageRepository.saveAll(filesImages); // Lưu tất cả vào cơ sở dữ liệu
    }
    @Override
    public CvImage getCvImageById(int id) {
        return filesImageRepository.findById(id).orElse(null);
    }

    @Override
    public List<CvImage> getAllCvImages() {
        return filesImageRepository.findAll();
    }

    @Override
    public List<CvImage> getAllCvImagesbyPatientFileId(int id) {
        return filesImageRepository.findFileImageByCvImageId(id);
    }

    @Override
    public CvImage updateCvImage(int id, CvImage filesImage) {
        if (filesImageRepository.existsById(id)) {
            filesImage.setId(id);
            return filesImageRepository.save(filesImage);
        }
        return null;
    }

    @Override
    public void deleteCvImage(int id) {
        filesImageRepository.deleteById(id);
    }
}
