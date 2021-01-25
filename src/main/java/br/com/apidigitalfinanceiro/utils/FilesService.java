package br.com.apidigitalfinanceiro.utils;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import net.sf.jasperreports.engine.JRException;

public interface FilesService {

	public byte[] ViewPdf(Map<String, Object> parameters, List<?> source, String templates)
			throws JRException, IOException;

	public byte[] viewListPatrimonio() throws JRException, IOException;

	public String downloadFile(String fileName) throws IOException;

	public URI uploadProfilePicture(MultipartFile multipartFile);

	public void deleteimage(String filename);

	public String _uploadProfilePicture(MultipartFile multipartFile, String filename);

	public String _uploadProfilePicturefile(String fileName, String filename);
}
