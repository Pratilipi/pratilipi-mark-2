<#setting time_zone="${ timeZone }">
<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: Pratilipi :: Start -->

<#assign shareUrl="http://${ domain }${ pratilipiData.getPageUrl() }">

<div class="container">
	<#if pratilipiData.getState() != "PUBLISHED">
		<div id="PageContent-Pratilipi-Publish" class="col-sm-12" style="text-align:center; margin-top: 10px;">
			<span class="alert alert-danger" role="alert" style="padding: 6px 10px;margin-bottom: 20px;">
				This ${ pratilipiData.getType().getName() } is not published. Click this button to publish.
			</span>
		</div>
		<div id="PageContent-Pratilipi-Unpublish" class="col-sm-12" style="text-align: center; margin-top: 10px; display: none;">
			<span class="alert alert-success" role="alert" style="padding: 6px 10px;margin-bottom: 20px;">
				This ${ pratilipiData.getType().getName() } is published. Click this button to mark it unpublished.
			</span>
		</div>
	</#if>
	


	<div class="row">

		<#-- Cover Image -->
		<div class="col-lg-2 col-md-2 col-sm-3 col-xs-4" style="margin-top:25px; margin-bottom:15px;">
			<img id="PageContent-Pratilipi-CoverImage" class="img-responsive" src="${ pratilipiCoverUrl }">
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions"></div>
			</#if>
			<div style="margin-top:10px; margin-bottom:10px; text-align:center">
				<@social.vToolbar shareUrl=shareUrl/>
			</div>
		</div>
		
		<#-- Title, Author Name, Genre List, Summary and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-8" style="padding-bottom:15px;">
			<h1 id="PageContent-Pratilipi-Title">${ pratilipiData.getTitle() }</h1>
			<h4><a href="${ pratilipiData.getAuthorData().getPageUrl() }" id="PageContent-Pratilipi-AuthorName">${ pratilipiData.getAuthorData().getFullName() }</a></h4>
			
			<h5 id="PageContent-Pratilipi-GenreList">
				<#list pratilipiData.getGenreNameList() as genreName>
					${ genreName }<#if genreName_has_next>,</#if>
				</#list>
			</h5>
			
			<div id="PageContent-Pratilipi-Summary" style="margin-top:20px; margin-bottom:10px;">
				${ pratilipi.getSummary()! }
			</div>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-Summary-EditOptions" style="text-align:right;"></div>
			</#if>
				
			<button type="button" class="btn btn-success" onclick="window.location.href='${ pratilipiReaderUrl }'">Read For Free</button>
			<#if showReviewedMessage>
				<button type="button" class="btn btn-info visible-xs-inline-block" onclick="window.location.href='#Reviews'">
					<span class="glyphicon glyphicon-ok"></span> Reviewed
				</button>
			</#if>
			<#if showReviewOption>
				<button type="button" class="btn btn-primary visible-xs-inline-block" onclick="window.location.href='#Review'">Review This ${ pratilipi.getType().getName() }</button>
			</#if>
		</div>

	</div> <#-- END of row -->

</div> <#-- END of container -->



<div class="container">

	<div id="Reviews" class="well" style="margin-top:25px;">
		<#list reviewList as review >
			<div class="hr-below">
				<h4 style="display:inline-block">${ userIdNameMap[ review.getUserId()?string("#") ] } Says,</h4>
				<span class="pull-right"> ${ review.getReviewDate()?date }</span>
				<p>
					${ review.getReview() }
				</p>
			</div>
		</#list>
		<#if showReviewOption>
			<div id="Review">
				<h4 style="display:inline-block">${ userName } Says,</h4>
				<div id="PageContent-Pratilipi-Review"></div>
				<div id="PageContent-Pratilipi-Review-AddOptions" style="padding-top:15px"></div>
			</div>
		</#if>
	</div> <#-- END of well -->
	
</div> <#-- END of container -->



<#if showEditOptions>
	<div id="PageContent-Pratilipi-EncodedData" style="display:none;">${ pratilipiDataEncodedStr }</div>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi.witheditoptions/pagecontent.pratilipi.witheditoptions.nocache.js" defer></script>
<#else>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi/pagecontent.pratilipi.nocache.js" defer></script>
</#if>

<!-- PageContent :: Pratilipi :: End -->