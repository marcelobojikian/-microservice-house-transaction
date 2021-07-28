package com.cashhouse.transaction.util;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

public abstract class DefaultTest {

	void emptyBody(ResultActions action) throws Exception {
		action.andExpect(status().isNotFound());
	}

	public void defaultTestPost(ResultActions action, String urlExppect, String idExpectInJson) throws Exception {

		String uriExprect = getUri(action, urlExppect, idExpectInJson);

		action.andExpect(status().isCreated());
		action.andExpect(redirectedUrl(uriExprect));
	}

	public void emptyBodyPost(String uri) throws Exception {
		body();
		emptyBody(post(SampleRequest.VERSION + uri));
	}

	public void emptyBodyPut(String uri) throws Exception {
		body();
		emptyBody(put(SampleRequest.VERSION + uri));
	}

	public void emptyBodyPatch(String uri) throws Exception {
		body();
		emptyBody(patch(SampleRequest.VERSION + uri));
	}
	
	public abstract ContentHelper body();
	
	public abstract String getUri(ResultActions ra, String uri, String variable) throws Exception;

	public abstract ResultActions get(String url) throws Exception;

	public abstract ResultActions post(String url) throws Exception;

	public abstract ResultActions put(String url) throws Exception;

	public abstract ResultActions patch(String url) throws Exception;

	public abstract ResultActions delete(String url) throws Exception;

}
