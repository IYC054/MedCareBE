package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.FilesImage;

import java.util.List;

public interface FilesImageService {
    FilesImage createFilesImage(FilesImage filesImage);

    FilesImage getFilesImageById(int id);

    List<FilesImage> getAllFilesImages();

    FilesImage updateFilesImage(int id, FilesImage filesImage);

    void deleteFilesImage(int id);
}
