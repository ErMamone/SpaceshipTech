package com.meroz.spaceship.config.openapi;

import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;

public class GroupInternalCustomiser extends SpecFilter implements OpenApiCustomiser {

	private String title;
	private String description;
	private String version;
	private String termsOfService;

	public GroupInternalCustomiser(String title, String description, String version, String termsOfService) {
		super();
		this.title = title;
		this.description = description;
		this.version = version;
		this.termsOfService = termsOfService;
	}

	@Override
	public void customise(OpenAPI openApi) {
		openApi.setInfo(getInfo());
	}

	private Info getInfo() {
		Info info = new Info();
		info.setTitle(title);
		info.setDescription(description);
		info.setVersion("v" + version);
		info.setTermsOfService(termsOfService);

		return info;
	}
}
