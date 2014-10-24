package com.claymus.data.access;

import java.util.ArrayList;
import java.util.List;

import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.Role;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.pagecontent.blogpost.BlogPostContent;

@SuppressWarnings("serial")
public class DataAccessorWithMemcache implements DataAccessor {
	
	private final static String PREFIX_USER = "User-";
	private final static String PREFIX_USER_LIST = "UserList-";
	private final static String PREFIX_ROLE = "Role-";
	private final static String PREFIX_USER_ROLE_LIST = "UserRoleList-";
	private final static String PREFIX_ROLE_ACCESS = "RoleAccess-";
	private final static String PREFIX_PAGE_CONTENT = "PageConent-";
	
	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}

	
	@Override
	public User newUser() {
		return dataAccessor.newUser();
	}
	
	@Override
	public User getUser( Long id ) {
		User user = memcache.get( PREFIX_USER + id );
		if( user == null ) {
			user = dataAccessor.getUser( id );
			if( user != null )
				memcache.put( PREFIX_USER + id, user );
		}
		return user;
	}
	
	@Override
	public User getUserByEmail( String email ) {
		User user = memcache.get( PREFIX_USER + email );
		if( user == null ) {
			user = dataAccessor.getUserByEmail( email );
			if( user != null )
				memcache.put( PREFIX_USER + email, user );
		}
		return user;
	}
	
	@Override
	public List<User> getUserList() {
		List<User> userList = memcache.get( PREFIX_USER_LIST );
		if( userList == null ) {
			userList = dataAccessor.getUserList();
			memcache.put(
					PREFIX_USER_LIST,
					new ArrayList<>( userList ) );
		}
		return userList;
	}
	
	@Override
	public User createOrUpdateUser( User user ) {
		user = dataAccessor.createOrUpdateUser( user );
		memcache.put( PREFIX_USER + user.getId(), user );
		memcache.put( PREFIX_USER + user.getEmail(), user );
		memcache.remove( PREFIX_USER_LIST );
		return user;
	}

	
	@Override
	public Role newRole() {
		return dataAccessor.newRole();
	}

	@Override
	public Role getRole( Long id ) {
		Role role = memcache.get( PREFIX_ROLE + id );
		if( role == null ) {
			role = dataAccessor.getRole( id );
			if( role != null )
				memcache.put( PREFIX_ROLE + id, role );
		}
		return role;
	}

	@Override
	public Role createOrUpdateRole( Role role ) {
		role = dataAccessor.createOrUpdateRole( role );
		memcache.put( PREFIX_ROLE + role.getId(), role );
		return role;
	}


	@Override
	public UserRole newUserRole() {
		return dataAccessor.newUserRole();
	}

	@Override
	public List<UserRole> getUserRoleList( Long userId ) {
		List<UserRole> userRoleList = memcache.get( PREFIX_USER_ROLE_LIST + userId );
		if( userRoleList == null ) {
			userRoleList = dataAccessor.getUserRoleList( userId );
			memcache.put(
					PREFIX_USER_ROLE_LIST + userId,
					new ArrayList<>( userRoleList ) );
		}
		return userRoleList;
	}
	
	@Override
	public UserRole createOrUpdateUserRole( UserRole userRole ) {
		memcache.remove( PREFIX_USER_ROLE_LIST + userRole.getUserId() );
		return dataAccessor.createOrUpdateUserRole( userRole );
	}

	
	@Override
	public RoleAccess newRoleAccess() {
		return dataAccessor.newRoleAccess();
	}

	@Override
	public RoleAccess getRoleAccess( String roleId, String accessId ) {
		RoleAccess roleAccess = memcache.get( PREFIX_ROLE_ACCESS + roleId + "-" + accessId );
		if( roleAccess == null ) {
			roleAccess = dataAccessor.getRoleAccess( roleId, accessId );
			if( roleAccess != null )
				memcache.put( PREFIX_ROLE_ACCESS + roleAccess.getId(), roleAccess );
		}
		return roleAccess;
	}

	@Override
	public RoleAccess createOrUpdateRoleAccess( RoleAccess roleAccess ) {
		roleAccess = dataAccessor.createOrUpdateRoleAccess( roleAccess );
		memcache.put( PREFIX_ROLE_ACCESS + roleAccess.getId(), roleAccess );
		return roleAccess;
	}

	
	@Override
	public Page newPage() {
		return dataAccessor.newPage();
	}
	
	@Override
	public Page getPage( String uri ) {
		// TODO; enable caching
		return dataAccessor.getPage( uri );
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		// TODO; enable caching
		return dataAccessor.createOrUpdatePage( page );
	}

	
	@Override
	public PageContent getPageContent( Long id ) {
		PageContent pageContent = memcache.get( PREFIX_PAGE_CONTENT + id );
		if( pageContent == null ) {
			pageContent = dataAccessor.getPageContent( id );
			if( pageContent != null )
				memcache.put( PREFIX_PAGE_CONTENT + id, pageContent );
		}
		return pageContent;
	}
	
	@Override
	public List<PageContent> getPageContentList( Long pageId ) {
		// TODO; enable caching
		return dataAccessor.getPageContentList( pageId );
	}

	@Override
	public DataListCursorTuple<BlogPostContent> getBlogPostContentList(
			Long blogId, String cursorStr, int resultCount ) {
		
		return dataAccessor.getBlogPostContentList( blogId, cursorStr, resultCount );
	}
	
	@Override
	public PageContent createOrUpdatePageContent( PageContent pageContent ) {
		pageContent = dataAccessor.createOrUpdatePageContent( pageContent );
		memcache.put( PREFIX_PAGE_CONTENT + pageContent.getId(), pageContent );
		return pageContent;
	}


	@Override
	public PageLayout newPageLayout() {
		return dataAccessor.newPageLayout();
	}

	@Override
	public PageLayout getPageLayout( Long id ) {
		// TODO; enable caching
		return dataAccessor.getPageLayout( id );
	}

	@Override
	public PageLayout createOrUpdatePageLayout( PageLayout pageLayout ) {
		// TODO; enable caching
		return dataAccessor.createOrUpdatePageLayout( pageLayout );
	}

	
	@Override
	public EmailTemplate newEmailTemplate() {
		// TODO; enable caching
		return dataAccessor.newEmailTemplate();
	}
	

	@Override
	public void destroy() {
		dataAccessor.destroy();
	}
	
}
