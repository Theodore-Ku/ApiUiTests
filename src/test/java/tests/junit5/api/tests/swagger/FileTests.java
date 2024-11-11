package tests.junit5.api.tests.swagger;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.junit5.api.assertions.Conditions;
import tests.junit5.api.services.FileService;

import java.io.File;

public class FileTests extends BaseApiTest {
    private final FileService fileService = new FileService();

    @Attachment(value = "downloaded", type = "image/png")
    private byte[] attachFile(byte[] bytes) {
        return bytes;
    }

    @Test
    public void positiveDownloadTest() {
        byte[] file = fileService.downloadBaseImage().asResponse().asByteArray();
        attachFile(file);
        File expectedFile = new File("src/test/resources/qa.jpeg");
        Assertions.assertEquals(expectedFile.length(), file.length);
    }

    @Test
    public void positiveUploadTest() {
        File expectedFile = new File("src/test/resources/qa.jpeg");
        fileService.uploadFile(expectedFile)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasMessage("file uploaded to server"));

        byte[] actualFile = fileService.downloadLastFile().asResponse().asByteArray();
        Assertions.assertTrue(actualFile.length != 0);
        Assertions.assertEquals(expectedFile.length(), actualFile.length);
    }
}