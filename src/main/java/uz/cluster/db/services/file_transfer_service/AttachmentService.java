package uz.cluster.db.services.file_transfer_service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.cluster.db.entity.references.model.Attachment;
import uz.cluster.db.repository.user_info.AttachmentRepository;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.util.LanguageManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    private static final String uploadDirectory = "./uploadedAttachments";

    @SneakyThrows
    public ApiResponse uploadFileToFileSystem(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        Map<Integer, Attachment> attachmentList = new HashMap<>();
        int i = 0;
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());

            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                long fileSize = file.getSize();
                if (!Objects.requireNonNull(file.getOriginalFilename()).isEmpty() || file.getSize() != 0) {
                    Attachment attachment = new Attachment();
                    attachment.setOriginalFileName(originalFilename);
                    attachment.setSize(fileSize);
                    attachment.setContentType(file.getContentType());

                    assert originalFilename != null;
                    String[] split = originalFilename.split("\\.");
                    String randomName = UUID.randomUUID() + "." + split[split.length - 1];

                    Path path = Paths.get("/opt/test/" + uploadDirectory + "/" + randomName);
                    System.out.println("--------------------------------------------");
                    System.out.println("--------------------------------------------");
                    System.out.println("--------------------------------------------");
                    System.out.println(originalFilename);
                    System.out.println(path);
                    System.out.println("--------------------------------------------");
                    System.out.println("--------------------------------------------");
                    System.out.println("--------------------------------------------");
                    try {
                        Files.copy(file.getInputStream(), path);
                    }catch (Exception e){
                        System.out.println("-------------------------------------");
                        System.out.println("-------------------------------------");
                        System.out.println("-------------------------------------");
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        System.out.println("--------------------------------------");
                        System.out.println("--------------------------------------");
                        System.out.println("--------------------------------------");

                    }

                    attachment.setName(randomName);
                    Attachment savedAttachment = attachmentRepository.save(attachment);
                    i++;
                    attachmentList.put(i, savedAttachment);
                }
            }
        }
        boolean isAttachmentListEmpty = attachmentList.isEmpty();
        return new ApiResponse(
                !isAttachmentListEmpty,
                !isAttachmentListEmpty ? attachmentList : null,
                !isAttachmentListEmpty ? LanguageManager.getLangMessage("yes_photo") : LanguageManager.getLangMessage("no_photo")
        );
    }

    @Transactional
    public ApiResponse deleteFile(List<Attachment> attachmentList) {
        ApiResponse apiResponse;
        try {
            attachmentRepository.deleteAll(attachmentList);
            apiResponse = new ApiResponse(true, LanguageManager.getLangMessage("images_deleted"));
        } catch (Exception exception) {
            apiResponse = new ApiResponse(false, exception.getMessage());
        }
        return apiResponse;
    }
}
