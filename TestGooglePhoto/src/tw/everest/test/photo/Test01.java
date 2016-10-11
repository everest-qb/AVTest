package tw.everest.test.photo;

import java.net.URL;
import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;

public class Test01 {

	public static void main(String[] args) throws Exception, ServiceException {		
		PicasawebService service = new PicasawebService("Google-photo-everest-test");
		
		
		
		//Credential.Builder();
		
		String name="everest.qb";
		
		//PicasawebClient client=new PicasawebClient(service);
	
		URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+name+"?kind=album");
		
		
		UserFeed myUserFeed = service.getFeed(feedUrl, UserFeed.class);
				
		
		for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {	
			System.out.println("===================================");
		    System.out.println(myAlbum.getTitle().getPlainText());
		    //System.out.println(myAlbum.getName());
		    //System.out.println(myAlbum.getNickname());
		    //System.out.println(myAlbum.getId());
		    
		    System.out.println("   ");
		   

		    AlbumFeed feed = myAlbum.getFeed();

		    for(PhotoEntry photo : feed.getPhotoEntries()) {
		    	System.out.println("Title: " + photo.getTitle().getPlainText());
		    	System.out.println("Description: " + photo.getDescription().getPlainText());
		    	System.out.println("ID: " + photo.getId());
		    	//System.out.println("Camera Model: " + photo.getExifTags().getCameraModel());
		    	System.out.println(photo.getHtmlLink().getHref());
		    	
		    }
		}
		
	
		/*List<AlbumEntry> list=client.getAlbums("everest.qb@gmail.com");
		
		for(AlbumEntry a:list){
			System.out.println("===========");
			System.out.println("name:"+a.getName());
			System.out.println("gphotoid:"+a.getGphotoId());
			System.out.println("id:"+a.getId());
			System.out.println("location:"+a.getLocation());
			System.out.println("nickname"+a.getNickname());
			System.out.println("username:"+a.getUsername());
			System.out.println("versionid:"+a.getVersionId());
			System.out.println("photo used:"+a.getPhotosUsed());
			System.out.println("date:"+a.getDate());
			
			List<PhotoEntry> pList=client.getPhotos(a);
			for(PhotoEntry p:pList){
				System.out.println("....");
				
			}
		}*/
		
		
		
		//PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");		

		
	}

}
