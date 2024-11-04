package fpt.aptech.projectapi.services.impl;

import fpt.aptech.projectapi.entities.FilesImage;
import fpt.aptech.projectapi.repository.FilesImagesRepository;
import fpt.aptech.projectapi.services.FilesImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesImagesServiceImpl implements FilesImagesService {
    @Autowired
    private FilesImagesRepository filesImagesRepository;


    @Override
    public FilesImage createFilesImage(FilesImage filesImage) {
        return filesImagesRepository.save(filesImage);
    }

    @Override
    public FilesImage getFilesImageById(int id) {
        return filesImagesRepository.findById(id).orElse(null);
    }

    @Override
    public List<FilesImage> getAllFilesImages() {
        return filesImagesRepository.findAll();
    }

    @Override
    public FilesImage updateFilesImage(int id, FilesImage filesImage) {
        if (filesImagesRepository.existsById(id)) {
            filesImage.setId(id);
            return filesImagesRepository.save(filesImage);
        }
        return null;
    }

    @Override
    public void deleteFilesImage(int id) {
        filesImagesRepository.deleteById(id);
    }
}
