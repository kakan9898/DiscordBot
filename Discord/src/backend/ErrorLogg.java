/*
 * Copyright 2017 Glenn Olsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ErrorLogg {
	public ErrorLogg(Exception exception, String content, String id, MessageReceivedEvent event) {

		if(System.getProperty("os.name").toLowerCase().contains("linux")){
			//if linux (RasPi)
			String currentContent = "", newContent = "";

			new Print("An error was caught", true);

			//Parses the stackTrace as string for it to be saved
			StringWriter errors = new StringWriter();
			exception.printStackTrace(new PrintWriter(errors));


			//Saves title and such to newContent, including current time
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("d/M - HH:mm:ss");
			String currentTime =sdf.format(cal.getTime());

			String eventMessage=null, guild=null;

			if(event!=null){
				if(!event.getChannel().getType().equals(ChannelType.PRIVATE)){
					guild=", in the "+event.getGuild().getName()+" guild";
				}
				else{
					guild =", in a Private message group";
				}

				eventMessage="\nThe sender was "+event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator()+" in the " +event.getChannel().getName()+ 
						" channel"+guild;
			}

			newContent="##New error at "+currentTime+"\nMessage was: "+content+"\nId: "+id+eventMessage+"\n"+errors.toString()+"\n---------------\n\n";

			try{
				String path="/var/lib/tomcat7/webapps/ROOT/";
				FileReader reader = new FileReader(path+"Errorlog.md");
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(reader); 
				Iterator<String> iterator= br.lines().iterator();
				//Saves the earlier content to currentContent string
				while (iterator.hasNext()) {
					currentContent+=iterator.next()+"\n";
				}
				//Writes old + new content
				
				currentContent=currentContent.replace("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><plaintext>", "");
				
				try(FileWriter file = new FileWriter(path+"Errorlog.md")){
					file.write("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><plaintext>"+newContent+""+currentContent);
				}
				catch (Exception e) {
					// FIXME: handle exception
					new Error(e, "Error writing file", content, id);
				}
				
				new Print("Successfully edited", false);

			}
			catch (Exception e) {
				// FIXME: handle exception
				new Error(e, "Error with reading, maybe", content, id);
			}
		}
		else {
			//if windows
			exception.printStackTrace();
		}
	}	
}