package controllers;

import helpers.ProjectPath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	static String projectPath = ProjectPath.getProjectPath();
	static String uploadedVideosPath = projectPath
			+ Play.application().configuration().getString("videoUploadPath");

	public static Result index() {

		System.out.println("Player started");
//		String fileName = "play.m3u8";
		String fileName = "videovlc";
		File file = new File(uploadedVideosPath + fileName); // for local

		if (file.exists()) {
			System.out.println("file exists : " + file);
		}
		List<String> player = new ArrayList<>();
/*
		player.add("/usr/bin/cvlc");
		player.add(file.toString());
		player.add("--playlist-enqueue");
		player.add("--loop");
		player.add("--sout");
		player.add("#transcode{vcodec=h264,vb=2000,fps=25,--width=640,--height=360,scale=Auto,acodec=mp3,ab=128,channels=1,samplerate=22050}:std{access=http{user='login',pwd='password'},mux=ts,dst=localhost:1234/stream}");
		// for remote
		// player.add("#transcode{vcodec=h264,vb=2000,fps=25,--width=640,--height=360,scale=Auto,acodec=mp3,ab=128,channels=1,samplerate=22050}:std{access=http{user='login',pwd='password'},mux=ts,dst=128.199.149.46:1234/stream}'");
*/
		
		// '#gather:transcode{vcodec=h264,vb=2000,fps=25,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=http{user='login',pwd='password'},mux=ts,rc-buffer-size=1000,dst=localhost:5050/stream}'
		
		player.add("xargs");
		player.add("-a");
		player.add(file.toString());
		player.add("-I");
		player.add("{}");
		player.add("cvlc");
		player.add("--playlist-enqueue");
//		player.add("--play-and-exit");
		player.add("--loop");
		player.add("{}");
		player.add("--sout");
		player.add("#gather:transcode{vcodec=h264,vb=2000,fps=25,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=http{user='login',pwd='password'},mux=ts,rc-buffer-size=1000,dst=localhost:5050/stream}");
		
		ProcessBuilder pb = new ProcessBuilder(player);
		Process process;
		pb.redirectErrorStream(true);
		try {
			process = pb.start();
			process.getErrorStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ok(index.render("Live Streaming."));
	}
}
