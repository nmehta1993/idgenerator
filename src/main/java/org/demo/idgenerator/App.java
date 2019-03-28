package org.demo.idgenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class App 
{
	// [0] : File path
	// [1] : Image Folder
	// [2] : template Path
	// [3] : Event Name
	// [4] : Logo Path
	// [5] : Host
	// [6] : Duration
	// [7] : Address
	// [8] : Allowed Items
	// [9] : Group
    public static void main( String[] args ) throws IOException
    {
        List<Identity> identities = Files.parseFile(new File(args[0]));
        identities.remove(0);
        
        Event e = new Event();
        e.setAddress(args[7]);
        e.setAllowedItems(args[8]);
        e.setDuration(args[6]);
        e.setGroup(args[9]);
        e.setHost(args[5]);
        e.setLogo(args[4]);
        e.setName(args[3]);
        
        App a = new App();
        a.generate(e, identities, new File(args[2]), args[1]);
        
        System.out.println();
    }
    
    public void generate(Event e, List<Identity> identities, File template, String imageFolder) throws IOException {
    	String masterPage = "<!DOCTYPE html><html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"></head><body><div class=\"container\"><div class=\"row\">${CONTENT}</div></div></body></html>";
    	
    	String templateContent = new String(java.nio.file.Files.readAllBytes(template.toPath()));
    	
    	StringBuilder builder = new StringBuilder();
    	String templateFilled = 
    		templateContent
    			.replaceAll("\\$\\{event\\}", e.getName())
    			.replaceAll("\\$\\{logo\\}", e.getLogo().replace("\\", "\\\\"))
    			.replaceAll("\\$\\{host\\}", e.getHost())
    			.replaceAll("\\$\\{duration\\}", e.getDuration())
    			.replaceAll("\\$\\{visiting_area\\}", e.getAddress())
    			.replaceAll("\\$\\{allowed_items\\}", e.getAllowedItems())
    			.replaceAll("\\$\\{group\\}", e.getGroup());
    	for(Identity i : identities) {
    		String content = templateFilled.replaceAll("\\$\\{user_name\\}", i.getName())
    			.replaceAll("\\$\\{user_affilcation\\}", i.getAffilation())
    			.replaceAll("\\$\\{user_place\\}", i.getPlace())
    			.replaceAll("\\$\\{user_country\\}", i.getCountry())
    			.replaceAll("\\$\\{user_pic\\}", (imageFolder + File.separatorChar + i.getId().replace(" ", "-") + ".jpg").replace("\\", "\\\\"));
    		
    		builder.append(content);
    	}
    	
    	String finalStr = masterPage.substring(0, masterPage.indexOf("${CONTENT}")) + builder + masterPage.substring(masterPage.indexOf("${CONTENT}") + 1, masterPage.length());
    	java.nio.file.Files.write(new File("out.html").toPath(), finalStr.getBytes());
    }
}
