package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.Image;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageStore;
import daos.*;
import play.Logger;

import java.beans.Transient;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

public class ImagesController extends Controller {


    private String extension ;
    private final static Logger.ALogger LOGGER = Logger.of(ImagesController.class);
    Image image;
    private ImageDao imageDao;

    private ImageStore imageStore;

    @Inject
    public ImagesController(String extension,ImageStore imageStore, ImageDao imageDao) {
        this.extension = extension;
        this.imageStore = imageStore;
        this.imageDao = imageDao;

    }


    @Transactional
    public Result uploadImage() {

        final Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        if (null == body) {
            return badRequest("Not multipart request");
        }

        final Http.MultipartFormData.FilePart<File> image = body.getFile("file");
        if (null == image) {
            return badRequest("No file found");
        }

        if (image.getContentType().equals("image/png")) {

            extension = ".png";
            LOGGER.debug("extension is " + extension);
        }

        else if(image.getContentType().equals("image/jpg")){
            extension = ".jpg";
            LOGGER.debug("extension is " + extension);
        }

        else if(image.getContentType().equals("image/jpeg")){
            extension = ".jpeg";
            LOGGER.debug("extension is " + extension);
        }

        final Path source = image.getFile().toPath();

        String imageId = imageStore.save(source,extension);

        final String downloadUrl = routes.ImagesController.downloadImage(imageId).absoluteURL(request());


        final ObjectNode result = Json.newObject();
        //result.put("imageId", imageId);
        result.put("image_url",downloadUrl);
        //Image image_to_create = Json.fromJson(result,Image.class);
        // Image newImage = imageStore.create(image_to_create);
        //final JsonNode result_new = Json.toJson(newImage);



        return ok(result);
    }

    @Transactional
    public Result downloadImage(String id) {

        final File file = imageStore.getImageById(id);
        if (null == file) {
            return notFound("Image not found");
        }

        return ok(file);
    }

    @Transactional
    public Result deleteImage(String id) {

        final boolean deleted = imageStore.deleteImageById(id);
        if (!deleted) {
            return notFound("Image not found");
        }

        return ok();
    }

    @Transactional
    public Result getImagesByAttractionId(Integer id) {
        Collection<Image> images = imageDao.searchByAttractionId(id);
        final JsonNode result = Json.toJson(images);
        return ok(result);
    }

}
