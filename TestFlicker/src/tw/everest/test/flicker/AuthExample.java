package tw.everest.test.flicker;

import java.util.Scanner;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;

public class AuthExample {

	public static void main(String[] args) throws Exception {
		String apiKey = "f01f305a1623fa7d9cc2c86dfffd6a68";
		String secret = "f9b5752dc52e5a04";
		Flickr flickr=new Flickr(apiKey,secret,new REST());
		  Flickr.debugStream=false;
		  AuthInterface authInterface=flickr.getAuthInterface();
		  Scanner scanner=new Scanner(System.in);
		  Token token=authInterface.getRequestToken();
		  System.out.println("token: " + token);
		  String url=authInterface.getAuthorizationUrl(token,Permission.READ);
		  System.out.println("Follow this URL to authorise yourself on Flickr");
		  System.out.println(url);
		  System.out.println("Paste in the token it gives you:");
		  System.out.print(">>");
		  String tokenKey=scanner.nextLine();
		  Token requestToken=authInterface.getAccessToken(token,new Verifier(tokenKey));
		  System.out.println("Authentication success");
		  Auth auth=authInterface.checkToken(requestToken);
		  System.out.println("Token: " + requestToken.getToken());
		  System.out.println("nsid: " + auth.getUser().getId());
		  System.out.println("Realname: " + auth.getUser().getRealName());
		  System.out.println("Username: " + auth.getUser().getUsername());
		  System.out.println("Permission: " + auth.getPermission().getType());
		  
		  

	}

}
