package tw.everest.test.flicker;

import java.util.Collection;
import java.util.Collections;

import org.w3c.dom.Element;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.people.User;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.Photosets;
import com.flickr4java.flickr.photosets.PhotosetsInterface;
import com.flickr4java.flickr.test.TestInterface;

public class Run {

	public static void main(String[] args) throws Exception {
		String apiKey = "f01f305a1623fa7d9cc2c86dfffd6a68";
		String sharedSecret = "f9b5752dc52e5a04";
		Flickr f = new Flickr(apiKey, sharedSecret, new REST());
		PeopleInterface pi=f.getPeopleInterface();
		User u=pi.findByUsername("everest.qb");
		System.out.println(u.getId());
		
		PhotosetsInterface pseti=f.getPhotosetsInterface();
		Photosets sets=pseti.getList(u.getId());
		
		for(Photoset set :sets.getPhotosets()){
			System.out.println(set.getTitle());			
			
			PhotoList<Photo> pList=pseti.getPhotos(set.getId(), 10, 1);
			for(Photo p:pList){
				System.out.println(p.getTitle());
				System.out.println(p.getThumbnailUrl());
				System.out.println(p.getUrl());
			}
			
		}
		
	}

}
