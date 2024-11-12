package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.FilesImage;
import fpt.aptech.pjs4.repositories.FilesImageRepository;
import fpt.aptech.pjs4.services.FilesImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesImageServiceImpl implements FilesImageService {
    @Autowired
    private FilesImageRepository filesImageRepository;

    @Override
    public FilesImage createFilesImage(FilesImage filesImage) {
        return filesImageRepository.save(filesImage);
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
