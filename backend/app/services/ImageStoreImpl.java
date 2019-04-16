package services;

import play.Logger;
import controllers.ImagesController;
import models.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import java.util.Collection;
import java.util.Optional;


public class ImageStoreImpl implements ImageStore {

    private final static Logger.ALogger LOGGER = Logger.of(ImageStoreImpl.class);


    //final JPAApi jpaApi;
    //public static ImageStore imageStore;

    private static final Path STORAGE_ROOT = Paths.get("/Users/meghana/Documents/dev/uploadedimages");

    public ImageStoreImpl() {

        File rootDir = STORAGE_ROOT.toFile();

        if (rootDir.exists()) {
            return;
        }

        if (!rootDir.mkdirs()) {
            LOGGER.error("Failed to create image stored root directory");
        }

    }

   /* @Inject
    public ImageStoreImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }*/

    @Override
    public String save(Path source,String extension) {

        final String imageId = generateImageId();
        final Path destination = STORAGE_ROOT.resolve(imageId + extension);
        LOGGER.debug("Source: {} Destination: {}", source, destination);

        try {
            Files.move(source, destination);
            return imageId.concat(extension);
        } catch (IOException e) {
            LOGGER.debug("Failed to move uploaded image into folder {}", destination);
            e.printStackTrace();
            return null;
        }

    }


    private String generateImageId() {
        final UUID uuid = UUID.randomUUID();
        LOGGER.debug("id is "+uuid);
        return uuid.toString();

    }

    @Override
    public File getImageById(String id) {

        final File file = STORAGE_ROOT.resolve(id).toFile();
        if (!file.isFile()) {
            return null;
        }

        return file;

    }

    @Override
    public boolean deleteImageById(String id) {

        final File file = STORAGE_ROOT.resolve(id).toFile();
        if (!file.isFile()) {
            return false;
        }

        try {
            boolean deleted = Files.deleteIfExists(file.toPath());
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }



}

