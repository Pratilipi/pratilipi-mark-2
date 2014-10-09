package com.pratilipi.pagecontent.author;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;

public class AuthorContentFactory
		implements PageContentFactory<AuthorContent, AuthorContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author Content";
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		
		AuthorContentEntity authorContentEntity = new AuthorContentEntity();
		authorContentEntity.setAuthorId( authorId );
		
		return authorContentEntity;
		
	}

}
