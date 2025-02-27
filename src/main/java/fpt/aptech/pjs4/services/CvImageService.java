package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.CvImage;
import fpt.aptech.pjs4.entities.FilesImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CvImageService {
    CvImage createCvImages(CvImage filesImage);

    CvImage getCvImageById(int id);

    List<CvImage> getAllCvImages();
    List<CvImage> getAllCvImagesbyPatientFileId(int id);
    List<CvImage> createFileWithImage(List<MultipartFile> files, int patient_file_id);
    CvImage updateCvImage(int id, CvImage filesImage);

    void deleteCvImage(int id);
}
