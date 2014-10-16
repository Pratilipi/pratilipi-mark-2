package com.pratilipi.pagecontent.pratilipis.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiList extends InfiniteScrollPanel {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	private final Panel panelToHide;
	private final PratilipiFilter pratilipiFilter;
	
	private boolean hidden = false;
	private String cursor = null;
	private int resultCount = 50;

	private Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );
	
	
	public PratilipiList( Panel panelToHide, PratilipiFilter pratilipiFilter ) {
		
		this.panelToHide = panelToHide;
		this.pratilipiFilter = pratilipiFilter;
		
		setStyleName( "row" );
	}

	
	@Override
	protected void loadItems() {
		
		add( loadingImage );
		
		pratilipiService.getPratilipiList(
				new GetPratilipiListRequest( pratilipiFilter, cursor, resultCount ),
				new AsyncCallback<GetPratilipiListResponse>() {
			
			@Override
			public void onSuccess( GetPratilipiListResponse response ) {

				loadingImage.removeFromParent();
				
				if( ! hidden ) {
					panelToHide.setVisible( false );
					hidden = true;
				}
					
				for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
					PratilipiView pratilipiView = new PratilipiViewThumbnailImpl();
					pratilipiView.setPratilipiData( pratilipiData );
					add( pratilipiView );
				}
				
				cursor = response.getCursor();
				if( response.getPratilipiDataList().size() < resultCount )
					loadFinished();
				loadSuccessful();
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				loadingImage.removeFromParent();
				loadFailed();
			}
			
		});
	}

}
