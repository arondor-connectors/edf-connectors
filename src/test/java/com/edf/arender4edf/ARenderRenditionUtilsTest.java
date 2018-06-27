package com.edf.arender4edf;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by The ARender Team on 27/06/2018.
 */
public class ARenderRenditionUtilsTest
{

    private static final String DOC_TEST_PATH = "src/test/resources/documents/";
    private static final String CONVERTED_DOCUMENTS_TEST_PATH = DOC_TEST_PATH + "converted/";

    public static final String RENDITION_CLIENT_ADRESS = "http://localhost:1990/";

    @BeforeClass
    public static void setUp() throws IOException
    {
        FileUtils.deleteDirectory(new File(CONVERTED_DOCUMENTS_TEST_PATH));
    }

    @Test public void getPDFDocumentFromWord() throws Exception
    {
        String fileName = "AccessiWeb_bonnes_pratiques_pdf_accessibles_5mars2009.doc";
        String mimeType = "application/msword";
        InputStream pdfDocument = doRendition(fileName, mimeType);
        Assert.assertNotNull(pdfDocument);
        FileUtils.copyInputStreamToFile(pdfDocument, new File(CONVERTED_DOCUMENTS_TEST_PATH + FilenameUtils.getBaseName(fileName) + ".pdf"));
    }

    @Test public void getPDFDocumentFromPPT() throws Exception
    {
        String fileName = "Baujagd.ppt";
        String mimeType = "application/vnd.ms-powerpoint";
        InputStream pdfDocument = doRendition(fileName, mimeType);
        Assert.assertNotNull(pdfDocument);
        FileUtils.copyInputStreamToFile(pdfDocument, new File(CONVERTED_DOCUMENTS_TEST_PATH + FilenameUtils.getBaseName(fileName) + ".pdf"));
    }

    @Test public void getPDFDocumentFromPNG() throws Exception
    {
        String fileName = "rejected.png";
        String mimeType = "image/png";
        InputStream pdfDocument = doRendition(fileName, mimeType);
        Assert.assertNotNull(pdfDocument);
        FileUtils.copyInputStreamToFile(pdfDocument, new File(CONVERTED_DOCUMENTS_TEST_PATH + FilenameUtils.getBaseName(fileName) + ".pdf"));
    }

    @Test public void getPDFDocumentFromTIFFZip() throws Exception
    {
        String fileName = "arender-tiff-100.tiff.zip";
        String mimeType = "application/zip";
        InputStream pdfDocument = doRendition(fileName, mimeType);
        Assert.assertNotNull(pdfDocument);
        FileUtils.copyInputStreamToFile(pdfDocument, new File(CONVERTED_DOCUMENTS_TEST_PATH + FilenameUtils.getBaseName(fileName) + ".pdf"));
    }

    @Test(expected = ProcessingException.class) public void getPDFDocumentWrongClient() throws Exception
    {
        String fileName = "AccessiWeb_bonnes_pratiques_pdf_accessibles_5mars2009.doc";
        File file = new File(DOC_TEST_PATH + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        Assert.assertNotNull(fileInputStream);
        ARenderRenditionUtils aRenderRenditionUtils = new ARenderRenditionUtils();
        aRenderRenditionUtils
                .getPDFDocument("http://localhostFAIL:1990/", IOUtils.toByteArray(fileInputStream),"application/msword", fileName, file.length());
    }

    private InputStream doRendition(String fileName, String mimeType) throws IOException
    {
        File file = new File(DOC_TEST_PATH + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        Assert.assertNotNull(fileInputStream);
        ARenderRenditionUtils aRenderRenditionUtils = new ARenderRenditionUtils();
        InputStream pdfDocument = aRenderRenditionUtils
                .getPDFDocument(RENDITION_CLIENT_ADRESS, IOUtils.toByteArray(fileInputStream),"image/png", fileName, file.length());
        return pdfDocument;
    }

}