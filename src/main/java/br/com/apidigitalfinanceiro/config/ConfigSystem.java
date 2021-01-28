package br.com.apidigitalfinanceiro.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import br.com.apidigitalfinanceiro.mail.mail.JasperReportsService;
import br.com.apidigitalfinanceiro.utils.FilesService;
import br.com.apidigitalfinanceiro.utils.UtilFile;
@Configuration
public class ConfigSystem {

	@Value("${spring.cloud.gcp.credentials.location}")
	private String credencial;
	
	@Value("${spring.cloud.gcp.config_projectId}")
	private String projectId;
	
//	 @Autowired(required=true)
//		private GenTable genegationTable;
//	 
	 
	
	@Bean
	public boolean instantiateDatabase()  {
//		GenTable genegationTable=new GenTable();
//	  genegationTable.generationTable(); 
	// genPatrimonio.gen();
		return true;
	}
	@Bean
	public Storage storage() {

		try {
			File ins = ResourceUtils.getFile(credencial);			
			
			InputStream in = new FileInputStream(ins);
			
			Credentials credential = GoogleCredentials.fromStream(in);
			
			Storage storage =   StorageOptions.newBuilder().setCredentials(credential).setProjectId(projectId)
					.build().getService();

			 return storage;
			 
		} catch (IllegalStateException | IOException e) {
		}
		return  StorageOptions.getDefaultInstance().getService();

	}

	 
 	@Bean
	public FilesService filesService() {
		 return new UtilFile();
	} 
	
 
 	 
}
