package br.com.apidigitalfinanceiro.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;

import br.com.apidigitalfinanceiro.config.security.UserSS;
import br.com.apidigitalfinanceiro.config.services.UserService;
import br.com.apidigitalfinanceiro.domain.Empresas;
import br.com.apidigitalfinanceiro.service.security.exceptions.AuthorizationException;
import br.com.apidigitalfinanceiro.services.EmpresaService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

//@Log4j2
public class UtilFile implements FilesService, Serializable {
	@Value("${bucketBaseCompany}")
	private String bucket;

	@Autowired
	ResourceLoader resourceLoader;

	@Value("${spring.couchbase.bucket.name}")
	private String bucketdounload;

	@Value("${spring.cloud.gcp.config_projectId}")
	private String projectId;

	@Value("${spring.cloud.gcp.credentials.location}")
	private String credencial;

	@Autowired
	private Storage storage;

	@Autowired
	EmpresaService empresaService;

	private Path rootLocation;

	@Override
	public byte[] ViewPdf(Map<String, Object> parameters, List<?> source, String templates)
			throws JRException, IOException {
		return createPdfReport(parameters, source, templates);
	}

	public byte[] createPdfReport(Map<String, Object>  parameters, List<?> source, String templates)
			throws JRException, IOException {
		UserSS user = UserService.authenticated();
		
		Empresas company = empresaService.find(user.getEmpresa().getId());
		List<Empresas> listCompany = new LinkedList<>();
		listCompany.add(company);
		parameters.put("Empresa", company);
		parameters.put("company", listCompany);

		parameters.put("heard",  loadPathJasperFile("Cabecalho"));
		
		
		byte[] bytes = null;
		JasperReport jasperReport = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
			// Check if a compiled report exists
			if ( jasperFileExists(templates)) {
				jasperReport = (JasperReport) JRLoader
						.loadObject( loadJasperFile(templates));
			}
			// Compile report from source and save
			else {
				String jrxml = loadJrxmlFile(templates);
				//log.info("{} loaded. Compiling report", jrxml);
				jasperReport = JasperCompileManager.compileReport(jrxml);
				// Save compiled report. Compiled report is loaded next time
				JRSaver.saveObject(jasperReport,
						loadJasperFile(templates));
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					 new JRBeanCollectionDataSource(source));
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException | IOException e) {
			e.printStackTrace();
			//log.error("Encountered error when loading jasper file", e);
		}

		return bytes;
		/*Empresas company = empresaService.find(user.getEmpresa().getId());
		String img = company.getAvatarView();
		String v = bucket + "/";
		String s = company.getAvatarView();// bucket + "/" + company.getAvatar();// s = profileImage
											// company.setAvatar(bucket + "/" +
											// company.getAvatar());
		s = s.substring(v.length());

		String urlCabecalho = null;
		Resource res = resourceLoader.getResource("classpath:templates/report/heard/");
		try {
			urlCabecalho = res.getURI().getPath();
			System.out.println(res.getURI());
		} catch (IOException e) {

			e.printStackTrace();
		}
		List<Empresas> listCompany = new LinkedList<>();
		listCompany.add(company);
		parameters.put("Empresa", company);

		parameters.put("heard", urlCabecalho + "/Cabecalho.jasper");

		byte[] bytes = null;

		res = resourceLoader.getResource(templates);
		InputStream stream = getClass().getResourceAsStream(templates);
		File file = ResourceUtils.getFile(templates);
		// Compile the Jasper report from .jrxml to .japser
		final JasperReport report = JasperCompileManager.compileReport(templates);

		// Fetching the employees from the data source.
		final JRBeanCollectionDataSource source1 = new JRBeanCollectionDataSource(source);

		// Filling the report with the employee data and additional parameters
		// information.
		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source1);

		// Users can change as per their project requrirements or can take it as request
		// input requirement.
		// For simplicity, this tutorial will automatically place the file under the
		// "c:" drive.
		// If users want to download the pdf file on the browser, then they need to use
		// the "Content-Disposition" technique.
		final String filePath = "\\";
		// Export the report to a PDF file.
		// JasperExportManager.exportReportToPdfFile(print, filePath +
		// "Employee_report.pdf");

		return bytes = JasperExportManager.exportReportToPdf(print);
*/
		/*
		 * Empresas company = empresaService.find(user.getEmpresa().getId()); String img
		 * = company.getAvatarView(); String v = bucket + "/"; String s =
		 * company.getAvatarView();// bucket + "/" + company.getAvatar();// s =
		 * profileImage // company.setAvatar(bucket + "/" + // company.getAvatar()); s =
		 * s.substring(v.length());
		 * 
		 * String urlCabecalho = null; Resource res =
		 * resourceLoader.getResource("classpath:templates/report/heard/"); try {
		 * urlCabecalho = res.getURI().getPath(); System.out.println(res.getURI()); }
		 * catch (IOException e) {
		 * 
		 * e.printStackTrace(); } List<Empresas> listCompany = new LinkedList<>();
		 * listCompany.add(company); parameters.put("Empresa", company);
		 * System.out.println(urlCabecalho + "Cabecalho.jasper");
		 * parameters.put("heard", urlCabecalho + "Cabecalho.jasper");
		 * 
		 * InputStream jasperStream = this.getClass().getResourceAsStream(templates);
		 * byte[] bytes = null; // Cria o objeto JaperReport com o Stream do arquivo
		 * jasper JasperReport jasperReport = (JasperReport)
		 * JRLoader.loadObject(jasperStream);
		 * 
		 * JRBeanCollectionDataSource source$ = new JRBeanCollectionDataSource(source);
		 * 
		 * // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados,
		 * no // caso uma conexão ao banco de dados JasperPrint jasperPrint =
		 * JasperFillManager.fillReport(jasperReport, parameters, source$);
		 * 
		 * return bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		 * 
		 * // Faz a exportação do relatório para o HttpServletResponse // final
		 * OutputStream outStream = response.getOutputStream(); //
		 * JasperExportManager.exportReportToPdfStream(jasperPrint, outStream); /* //
		 * Fetching the .jrxml file from the resources folder. final InputStream stream
		 * = this.getClass().getResourceAsStream(templates);
		 * 
		 * // Compile the Jasper report from .jrxml to .japser final JasperReport report
		 * = JasperCompileManager.compileReport(templates);
		 * 
		 * // Fetching the employees from the data source. final
		 * JRBeanCollectionDataSource source$ = new JRBeanCollectionDataSource(source);
		 * 
		 * // Adding the additional parameters to the pdf.
		 * 
		 * // Filling the report with the employee data and additional parameters // //
		 * information. final JasperPrint print = JasperFillManager.fillReport(report,
		 * parameters, source$);
		 * 
		 * final String filePath = "\\"; // Export the report to a PDF file.
		 * 
		 * return bytes = JasperExportManager.exportReportToPdf(print);
		 */

	}

	@Override
	public String downloadFile(String fileName) throws IOException {

		Resource res = resourceLoader.getResource("classpath:templates/tmp");
		File ins = ResourceUtils.getFile(credencial);
		// InputStream in = new FileInputStream(ins);

		// ="cliente5ef7668b24f13062db255a24.png";

		URL signedUrl = null;
		try {
			signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketdounload, fileName).build(), 1, TimeUnit.DAYS,
					SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new FileInputStream(ins))));
		} catch (UnknownHostException e) {
			return "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signedUrl.toString();
	}

	@Override
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		String bucketName = bucketdounload;
		try {

			storage.delete(bucketName, multipartFile.getOriginalFilename());

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			File ins = ResourceUtils.getFile(credencial);
			InputStream in = new FileInputStream(ins);

			BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder(bucketName, multipartFile.getOriginalFilename())
							.setContentType(multipartFile.getContentType()).build(), // get original file name
					multipartFile.getBytes()// the file

			);
		} catch (IllegalStateException | IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	@Override
	public void deleteimage(String filename) {
		String bucketName = bucket;
		try {

			storage.delete(bucketName, filename);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public byte[] viewListPatrimonio() throws JRException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	@Override
	public String _uploadProfilePicturefile(String fileName, String filename2) {

		Resource res = resourceLoader.getResource("classpath:static/assets/" + fileName);
//application/pdf
		File convFile;
		try {
			convFile = res.getFile();// new File(res.getURI());

			FileOutputStream fos = new FileOutputStream(convFile);

			FileInputStream input = new FileInputStream(convFile);
			Path path = Paths.get(convFile.getPath());
			String name = fileName;
			String originalFileName = fileName;
			String contentType = "application/pdf";
			byte[] content = null;
			try {
				content = Files.readAllBytes(path);
			} catch (final IOException e) {
			}
			MultipartFile result = new MockMultipartFile(name, originalFileName, contentType,
					org.apache.commons.io.IOUtils.toByteArray(input));

			String extension = FilenameUtils.getExtension(convFile.getName());
			if (filename2 == "" || filename2.equals("")) {
				filename2 = getAlphaNumericString(10) + "." + extension;
			}
			try {
				String bucketName = bucketdounload;
				String f = filename2;
				File ins = ResourceUtils.getFile(credencial);
				InputStream in = new FileInputStream(ins);

				BlobInfo blobInfo = storage.create(
						BlobInfo.newBuilder(bucketName, filename2).setContentType(result.getContentType()).build(),
						result.getBytes()// the file

				);
			} catch (IllegalStateException | IOException e) {
				System.out.println(e.getMessage());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename2;
	}

	@Override
	public String _uploadProfilePicture(MultipartFile multipartFile, String filename) {
		String bucketName = bucketdounload;
		String fileName = "";
		String f = "";
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		if (filename == "" || filename.equals("")) {
			fileName = getAlphaNumericString(10) + "." + extension;
		} else {
			fileName = filename;

		}

		try {
			f = fileName;
			File ins = ResourceUtils.getFile(credencial);
			InputStream in = new FileInputStream(ins);

			BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder(bucketName, fileName).setContentType(multipartFile.getContentType()).build(),
					multipartFile.getBytes()// the file

			);
		} catch (IllegalStateException | IOException e) {
			System.out.println(e.getMessage());
		}

		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.juliuskrah.jasper.storage.StorageService#jrxmlFileExists(java.lang.
	 * String)
	 */
	@Override
	public boolean jrxmlFileExists(String file) {
		// @formatter:off
		Resource res = resourceLoader.getResource("classpath:templates/report/");
		// this.rootLocation = Paths.get(res.getURL().getPath());
		try {
			Path reportFile = Paths.get(res.getURI().getPath());
			reportFile = reportFile.resolve(file + ".jrxml");
			if (Files.exists(reportFile))
				return true;
		} catch (IOException e) {
	//		log.error("Error while trying to get file URI", e);
			return false;
		}
		// @formatter:on
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.juliuskrah.jasper.storage.StorageService#jasperFileExists(java.lang.
	 * String)
	 */
	@Override
	public boolean jasperFileExists(String file) {
		Resource res = resourceLoader.getResource("classpath:templates/report/");
		try {
			this.rootLocation = new File(res.getURL().getPath()).toPath();// Paths.get(res.getURL() );
			Path reportFile = rootLocation;
			reportFile = reportFile.resolve(file + ".jasper");
			if (Files.exists(reportFile))
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.juliuskrah.jasper.storage.StorageService#loadJrxmlFile(java.lang.String)
	 */
	@Override
	public String loadJrxmlFile(String file) {
		// @formatter:off
		Resource res = resourceLoader.getResource("classpath:templates/report/");

		try {
			Path reportFile= new File(res.getURL().getPath()).toPath();// Paths.get(res.getURL() );
			 
			reportFile = reportFile.resolve(file + ".jrxml");
			return reportFile.toString();
		} catch (IOException e) {
			//log.error("Error while trying to get file prefix", e);
			throw new AuthorizationException("Could not load file", e);
		}
		// @formatter:on
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.juliuskrah.jasper.storage.StorageService#loadJasperFile(java.lang.String)
	 */
	@Override
	public File loadJasperFile(String file) {
		Resource res = resourceLoader.getResource("classpath:templates/report/");

		try {
			Path reportFile = Paths.get(res.getURI());
			reportFile = reportFile.resolve(file + ".jasper");
			return reportFile.toFile();
		} catch (Exception e) {
			//log.error("Error while trying to get file prefix", e);
			throw new AuthorizationException("Could not load file", e);
		}
	}	
	@Override
	public String loadPathJasperFile(String file) {
		Resource res = resourceLoader.getResource("classpath:templates/report/");

		try {
			Path reportFile= new File(res.getURL().getPath()).toPath();// Paths.get(res.getURL() );
			 
			reportFile = reportFile.resolve(file + ".jasper");
			return reportFile.toString();
		} catch (IOException e) {
			//log.error("Error while trying to get file prefix", e);
			throw new AuthorizationException("Could not load file", e);
		}
		// @formatter:on
	}

}
