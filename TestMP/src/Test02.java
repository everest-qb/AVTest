import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


import tw.everest.mp.rest.json.ResponeStatus;
import tw.everest.mp.rest.json.UserInfo;

public class Test02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();	
		//client.register(ResponeStatusWriter.class);
		//client.register(ResponeStatusReader.class);
		WebTarget base = client.target("http://localhost:8080/mp");
		WebTarget addUsertarget = base.path("ws").path("user").path("add");
		
		UserInfo user=new UserInfo();
		user.setAddress("�銝剖�正��銝剖熒銵�205���");
		user.setIdNumber("L002251335");
		user.setMail("test05@gmail.com");
		user.setName("test05");
		user.setPasswd("123456789");
		user.setPhone("0923000335");
		user.setSex((byte)0);
		user.setTel("0422334335");
		user.setBirth("1978-05-05");
		
		ResponeStatus status =
				addUsertarget.request(MediaType.APPLICATION_JSON_TYPE)
				    .post(Entity.entity(user, MediaType.APPLICATION_JSON),
				    		ResponeStatus.class);
		
		System.out.println(status.getStatus());
		
		/*String s=addUsertarget.request(MediaType.TEXT_HTML_TYPE)
		.post(Entity.json(user),String.class);
		System.out.println(s);*/
		
		/*WebTarget loginTarget=base.path("ws").path("user").path("login")
				.queryParam("name", "tenfu")
				.queryParam("passwd", "12345678");	
		
		LoginStatus s=loginTarget.request(MediaType.APPLICATION_JSON_TYPE)
		.get(LoginStatus.class);
		
		System.out.println(s.getStatus());*/
	}

}
