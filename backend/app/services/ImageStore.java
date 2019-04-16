package services;

import models.Image;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;


public interface ImageStore<E,K> {

    String save(Path source,String extension);
    File getImageById(String id);
    boolean deleteImageById(String id);
    //Image create(Image image);
    //Optional<E> read(K id);
    //Collection<E> all();

}