/*******************************************************************************
 * Copyright 2018, Julius Krah
 * by the @authors tag. See the LICENCE in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.apidigitalfinanceiro.mail.storage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.sun.istack.NotNull;

import br.com.apidigitalfinanceiro.mail.mail.JasperReportsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Julius Krah
 *
 */
@Data
@Validated
@NoArgsConstructor 
public class EmailProperties {

  

	@Value("${spring.mail.username}")
	private String username;

	/**
	 * Cron expression to schedule HTML mail
	 */
	@NotBlank
	private String cron;
	/**
	 * Cron expression to schedule inline HTML mail
	 */
	@NotBlank
	private String inlineCron;
	/**
	 * The base path where reports will be stored after compilation
	 */
	@NotNull
	private Resource storageLocation;
	/**
	 * The location of JasperReports source files
	 */
	@NotNull
	private Resource reportLocation;

	private String html;

	private String personal;

	private String messageSubject;

	@Email
	@NotNull
	private String from;

	 
	private String[] to;

	public void getEmailProperties(String messageSubject, String from ,String[] to) {
		EmailProperties emailProperties = new EmailProperties();
		// emailProperties.getEmailProperties(messageSubject, from, inputFileName,
		// params, dataSource);
		setTo(to);
		setPersonal(from);
		setFrom(from);
		setMessageSubject(messageSubject);
		 
	}
}
