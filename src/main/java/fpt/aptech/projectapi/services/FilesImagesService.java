package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.entities.FilesImage;

import java.util.List;

public interface FilesImagesService {
    FilesImage createFilesImage(FilesImage filesImage);
    FilesImage getFilesImageById(int id);
    List<FilesImage> getAllFilesImages();
    FilesImage updateFilesImage(int id, FilesImage filesImage);
    void deleteFilesImage(int id);
}
