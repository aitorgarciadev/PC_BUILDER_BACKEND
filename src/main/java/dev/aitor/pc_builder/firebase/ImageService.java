package dev.aitor.pc_builder.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("pcbuilder-c4c11.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("firebase.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/pcbuilder-c4c11.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public Optional<String> uploadBase64(String fileEncoded) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(fileEncoded);
            String fileName = UUID.randomUUID().toString();
            BlobId blobId = BlobId.of("pcbuilder-c4c11.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

            InputStream inputStream = ImageService.class.getClassLoader()
                    .getResourceAsStream("firebase.json");
            GoogleCredentials credentials;

            credentials = GoogleCredentials.fromStream(inputStream);

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            storage.create(blobInfo, decodedBytes);

            String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/pcbuilder-c4c11.appspot.com/o/%s?alt=media";
            return Optional.of(String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
