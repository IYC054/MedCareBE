package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.FilesImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilesImageService {
    FilesImage createFilesImage(FilesImage filesImage);

    FilesImage getFilesImageById(int id);

    List<FilesImage> getAllFilesImages();
    List<FilesImage> getAllFilesImagesbyPatientFileId(int id);
    List<FilesImage> createFileWithImage(List<MultipartFile> files, int patient_file_id);
    FilesImage updateFilesImage(int id, FilesImage filesImage);

    void deleteFilesImage(int id);
}
