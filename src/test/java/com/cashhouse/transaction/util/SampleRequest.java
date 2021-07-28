package com.cashhouse.transaction.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleRequest {

	static final String VERSION = "/api/v1";

	private static final String FORMAT_LOG = "Url %s with body: %s";
	private static final String MESSAGE_NO_CONTENT = "Undeclared content. Use first ContentHelper.generate()";

	@Autowired
	private MockMvc mockMvc;

	private ContentHelper content;

	public ContentHelper body() {
		this.content = ContentHelper.generate();
		return content;
	}
	
	public ResultMatcher redirectedUrl(String url) {
		UriComponents resource = ServletUriComponentsBuilder.fromCurrentRequest().path(VERSION+url).build();
		return MockMvcResultMatchers.redirectedUrl(resource.toUri().toString());
	}
	
	public Integer getJsonProperty(ResultActions result, String variable) throws Exception {
		String content = result.andReturn().getResponse().getContentAsString();
		return JsonPath.read(content, variable);
	}
	
	public String getUrlWithContect(ResultActions ra, String uri, String variable) throws Exception {
		String content = ra.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.read(content, variable);
		return ServletUriComponentsBuilder.fromCurrentRequest().path(VERSION+uri)
		.buildAndExpand(id).toUri().toString();
	}

	public ResultActions get(String url) throws Exception {
		if (content == null) {
			body();
		}
		String contentJson = this.content.toJson();
		log.debug(String.format(FORMAT_LOG, url, contentJson));
		this.content = null;
		return get(url, contentJson);
	}

	public ResultActions post(String url) throws Exception {
		if (content == null) {
			throw new IllegalArgumentException(MESSAGE_NO_CONTENT);
		}
		String contentJson = this.content.toJson();
		log.debug(String.format(FORMAT_LOG, url, contentJson));
		this.content = null;
		return post(url, contentJson);
	}

	public ResultActions put(String url) throws Exception {
		if (content == null) {
			throw new IllegalArgumentException(MESSAGE_NO_CONTENT);
		}
		String contentJson = this.content.toJson();
		log.debug(String.format(FORMAT_LOG, url, contentJson));
		this.content = null;
		return put(url, contentJson);
	}

	public ResultActions patch(String url) throws Exception {
		if (content == null) {
			throw new IllegalArgumentException(MESSAGE_NO_CONTENT);
		}
		String contentJson = this.content.toJson();
		log.debug(String.format(FORMAT_LOG, url, contentJson));
		this.content = null;
		return patch(url, contentJson);
	}
	
	public ResultActions invokeGet(MockHttpServletRequestBuilder builder) throws Exception {
		return mockMvc.perform(builder);
	}
	
	public MockHttpServletRequestBuilder customGet(String url) {
		return MockMvcRequestBuilders.get(VERSION + url);
	}

	public ResultActions get(String url, String content) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.get(VERSION + url).content(content).contentType(MediaType.APPLICATION_JSON));
	}

	public ResultActions post(String url, String content) throws Exception {
		return mockMvc.perform(
				// TODO: Implementar composicao de headers e teste de idioma
//				MockMvcRequestBuilders.post(VERSION + url).content(content).header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR").contentType(MediaType.APPLICATION_JSON));
				MockMvcRequestBuilders.post(VERSION + url).content(content).contentType(MediaType.APPLICATION_JSON));
	}

	public ResultActions put(String url, String content) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.put(VERSION + url).content(content).contentType(MediaType.APPLICATION_JSON));
	}

	public ResultActions patch(String url, String content) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.patch(VERSION + url).content(content).contentType(MediaType.APPLICATION_JSON));
	}

	public ResultActions delete(String url) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.delete(VERSION + url));
	}

}
