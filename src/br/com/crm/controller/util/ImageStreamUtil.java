package br.com.crm.controller.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

/**
 * Utilit�rio para imagem como redimensionar ou grava��o em disco
 * @author Solkam
 * @since 01 MAR 2015
 */
public class ImageStreamUtil {

	// dimensao padrao de imagens
	private static final int DIM_DEFAULT = 75;
	
	public static final String UPLOAD_IMAGE_PATH = "resources/upload_img/";
	
	
	public byte[] getBinaryDimensionated(InputStream inputStream, String extension) throws IOException {
		return createBinary(inputStream, extension, DIM_DEFAULT, DIM_DEFAULT);
	}
	
	public byte[] getBinaryDimensionated(InputStream inputStream, String extension, final int W_DIM, final int H_DIM) throws IOException {
		return createBinary(inputStream, extension, W_DIM, H_DIM);
	}
	
	
	private byte[] createBinary(InputStream inputStream, String extension, final int W_DIM, final int H_DIM) throws IOException {
		BufferedImage imagemOriginal = ImageIO.read( inputStream );
		
		BufferedImage imagemRedim = new BufferedImage(W_DIM, H_DIM, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imagemRedim.createGraphics();
		g.drawImage(imagemOriginal, 0, 0, W_DIM, H_DIM, null);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(imagemRedim, extension, outputStream);
		return outputStream.toByteArray();
	}
	
	
	
	
	
	
	
	
	public void redimensionate(InputStream inputStream, String extension, OutputStream outputStream) throws IOException {
		BufferedImage imagemOriginal = ImageIO.read( inputStream );
		
		BufferedImage imagemRedim = new BufferedImage(DIM_DEFAULT, DIM_DEFAULT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imagemRedim.createGraphics();
		g.drawImage(imagemOriginal, 0, 0, DIM_DEFAULT, DIM_DEFAULT, null);
		
		ImageIO.write(imagemRedim, extension, outputStream);
	}
	
	
	
	public String extractExtension(String fileName) {
		int indexOfPonto = fileName.indexOf(".");
		String extensao = fileName.substring( indexOfPonto + 1 );
		return extensao;
	}
	
	
	
	public void writeInFileSystem(byte[] binary, String fileName) throws IOException {
		File theFile = new File(getRealFolder(), fileName );
		FileOutputStream fos = new FileOutputStream( theFile );
		fos.write( binary );
		fos.flush();
		fos.close();
	}
	
	
	private String getRealFolder() {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(UPLOAD_IMAGE_PATH);
	}
	
	
	

}
