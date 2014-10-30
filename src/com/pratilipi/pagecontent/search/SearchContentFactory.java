package com.pratilipi.pagecontent.search;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.search.gae.SearchContentEntity;
import com.pratilipi.pagecontent.search.shared.SearchContentData;


public class SearchContentFactory extends PageContentHelper<SearchContent, 
											SearchContentData, SearchContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "File Browser";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static SearchContent newSearchContent() {
		return new SearchContentEntity();
	}
}
