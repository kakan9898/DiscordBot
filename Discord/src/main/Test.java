package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import backend.*;
import backend.ReadWrite.JSONDocument;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.events.message.priv.*;
import net.dv8tion.jda.core.hooks.*;

public class Test extends ListenerAdapter{

	public static void main(String[] args) {
		// FIXME Auto-generated method stub

		JDA jda = null;
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(ReadWrite.getKey("oath",JSONDocument.secret)).addListener(new Test()).buildBlocking();

		} catch (Exception e) {

			new Logg(e, "JDA Fail in Test", "JDA Fail in Test", null);

		}
		TextChannel channels=jda.getGuildsByName("Kakanistan",true).get(0).getTextChannels().get(0);
		String idKakan="165507757519273984", idKakansBot="282116563266437120";



	}
	public Test(){

	}

	public void onMessageReceived(MessageReceivedEvent event){
		if(event.getAuthor().getName().equals("Kakan")){
			EmbedBuilder builder = new EmbedBuilder().
					setImage("https://i.reddituploads.com/049fc05d0edd4cefab124d3ebdc8c980?fit=max&h=1536&w=1536&s=10f001d326f2f13ba134e7ece4d9d408")
					.setThumbnail("https://i.reddituploads.com/049fc05d0edd4cefab124d3ebdc8c980?fit=max&h=1536&w=1536&s=10f001d326f2f13ba134e7ece4d9d408");

			event.getChannel().sendMessage(builder.build()).queue();
		}
	}
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event){

		String content = event.getMessage().getContent().toLowerCase();

		System.out.println();

		if(content.contains(";testing")&&content.substring(0, ";testing".length()).equals(";testing")){
			event.getChannel().sendMessage(event.getJDA().getUserById("165507757519273984").getAsMention()).queue();
		}

		if(content.contains(";sup")&&content.substring(0, ";sup".length()).equals(";sup")){
			System.out.println();
		}

		if(content.contains(";send")&&content.substring(0, ";send".length()).equals(";send")){
			String user= event.getAuthor().getName().toLowerCase() + "#"+event.getAuthor().getDiscriminator().toLowerCase();
			if (user.equals("kakan#2926")) {
				System.err.println("AUTHORIZED");
				if(!content.contains(" ")){
					event.getChannel().sendMessage("-- YOU CAN MESSAGE THE FOLLOWING CHANNELS --").queue();
					List<TextChannel> channels = event.getJDA().getTextChannels();

					List<String> channelNames=new ArrayList<>();

					for (TextChannel channel : event.getJDA().getTextChannels()) {
						channelNames.add(channel.getGuild().getName() +" - "+ channel.getName());

					}

					Collections.sort(channelNames, new Comparator<String>() {
						@Override
						public int compare(String s1, String s2) {
							return s1.compareToIgnoreCase(s2);
						}
					});
					int i=0;
					for (String string : channelNames) {
						i++;
						event.getChannel().sendMessage(i+". "+string).queue();
					}

					event.getChannel().sendMessage("-- THAT IS ALL --").queue();
				}
				else if (content.split(" ").length==2) {
					String arg = content.split(" ")[1];

					List<TextChannel> channels = event.getJDA().getTextChannels();
					List<String> channelNames=new ArrayList<>();

					for (TextChannel channel : event.getJDA().getTextChannels()) {
						channelNames.add(channel.getGuild().getName() +" - "+ channel.getName());

					}

					Collections.sort(channelNames, new Comparator<String>() {
						@Override
						public int compare(String s1, String s2) {
							return s1.compareToIgnoreCase(s2);
						}
					});

					System.out.println(channelNames.get(Integer.parseInt(arg)-1));

				}
			}
		}

	}

}




